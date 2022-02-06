package scripts.Tasks;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.input.Mouse;
import org.tribot.api2007.*;
import org.tribot.api2007.types.RSCharacter;
import org.tribot.api2007.types.RSItem;
import org.tribot.api2007.types.RSNPC;
import org.tribot.script.sdk.Log;
import org.tribot.script.sdk.MyPlayer;
import org.tribot.script.sdk.Waiting;
import org.tribot.script.sdk.query.Query;
import org.tribot.script.sdk.types.Npc;
import scripts.*;
import scripts.API.AntiPKThread;
import scripts.Data.Areas;
import scripts.Data.Vars;
import scripts.EntitySelector.Entities;
import scripts.EntitySelector.finders.prefabs.NpcEntity;

import javax.swing.text.html.Option;
import java.util.Optional;

public class AttackNpc implements Task {

    public static void setPrayer(boolean on) {
        if (on && Prayer.getPrayerPoints() > 0) {
            if (Vars.get().shouldPrayMelee)
                PrayerUtil.setPrayer(PrayerType.MELEE);

            if (Vars.get().shouldPrayMagic)
                PrayerUtil.setPrayer(PrayerType.MAGIC);

        } else if (!on) {
            if (Prayer.isPrayerEnabled(Prayer.PRAYERS.PROTECT_FROM_MELEE))
                Prayer.disable(Prayer.PRAYERS.PROTECT_FROM_MELEE);

            if (Prayer.isPrayerEnabled(Prayer.PRAYERS.PROTECT_FROM_MAGIC))
                Prayer.disable(Prayer.PRAYERS.PROTECT_FROM_MAGIC);

            if (Prayer.isPrayerEnabled(Prayer.PRAYERS.PROTECT_FROM_MISSILES))
                Prayer.disable(Prayer.PRAYERS.PROTECT_FROM_MISSILES);
        }
    }

    public Optional<RSNPC> setCurrentTarget(String[] monsterStrings) {
        if (Combat.isUnderAttack()) {
            RSCharacter target = Combat.getTargetEntity();

            if (target != null && target.getHealthPercent() == 0) {
                RSNPC[] potentialTargets = NPCs.findNearest(monsterStrings);
                if (potentialTargets.length > 1) {
                    General.println("[AttackNPC]: Current target is dead, getting next target");
                    return Optional.ofNullable(potentialTargets[1]);
                }
            } else {
                Optional<String> name = Optional.ofNullable(Combat.getTargetEntity())
                        .map(RSCharacter::getName);

                if (name.isPresent()) {
                    RSNPC[] npc = NPCs.findNearest(name.get());
                    // name.ifPresent(n -> General.println("[AttackNPC] Target Name: " + n));
                    if (npc.length > 0)
                        return Optional.ofNullable(npc[0]);
                }

            }
        } else {
            if (monsterStrings != null) {
                int i = 0;

                RSNPC[] potentialTargets = Entities.find(NpcEntity::new)
                        .nameContains(monsterStrings)
                        .inArea(Vars.get().fightArea)
                        .sortByDistance()
                        .actionsContains("Attack") //needed
                        .getResults();

                for (RSNPC targ : potentialTargets) {
                    i++;
                    if (PathFinding.canReach(targ.getPosition(), false) &&
                            targ.getPosition().distanceTo(Player.getPosition()) < 6 &&
                            targ.getHealthPercent() != 0
                            && (!targ.isInCombat() || targ.isInteractingWithMe())) {
                        return Optional.ofNullable(targ);

                    } else if (PathFinding.canReach(targ.getPosition(), false)) {
                        if (targ.getHealthPercent() != 0 && (!targ.isInCombat() || targ.isInteractingWithMe())) {
                            General.println("[AttackNPC]: Got a target at index: " + i);
                            return Optional.ofNullable(targ);
                        }

                    } else if (PathingUtil.walkToArea(PathingUtil.makeLargerArea(targ.getPosition()), false)) { //will see if we can get a Dax path to walk to
                        if (PathFinding.canReach(targ.getPosition(), false)) {
                            if (targ.getHealthPercent() != 0 && (!targ.isInCombat() || targ.isInteractingWithMe())) {
                                General.println("[AttackNPC]: Got a target at index: " + i + "(Dax)");
                                return Optional.ofNullable(targ);
                            }
                        }
                    }

                }
            }
        }
        return Optional.empty();
    }

    public void fight() {

        if (!Combat.isAutoRetaliateOn())
            Combat.setAutoRetaliate(true);


        RSCharacter target = Combat.getTargetEntity();
        Optional<RSNPC> targ = setCurrentTarget(Vars.get().targets);

        if (!AntiPKThread.isWorldVisible(AntiPKThread.nextWorld)) {
            AntiPKThread.scrollToWorldNoClick(AntiPKThread.nextWorld);
        }
        if (targ.isPresent()) {
            org.tribot.script.sdk.Prayer.enableAll(org.tribot.script.sdk.Prayer.PROTECT_FROM_MAGIC,
                    org.tribot.script.sdk.Prayer.EAGLE_EYE);

            if (CombatUtil.clickTarget(targ.get())) {
                Vars.get().status = "Attacking Target";
                Vars.get().currentTime = System.currentTimeMillis();
            }
            if (Vars.get().drinkPotions)
                Utils.drinkPotion(Vars.get().potionNames);


            //  if (CombatUtil.waitUntilOutOfCombat(targ.get(), AntiBan.getEatAt(), 45000)) {
            //    //  if (useSpecialItem)
            //   sleep(targ.get());

            // } else
            Waiting.waitUniform(200, 400);

        } else {
            //  General.println("Cannot find target");
            Waiting.waitUniform(200, 400);
        }

    }

    public Optional<Npc> getNpcToAttack() {
        Optional<Npc> bestIntractable = Query.npcs()
                .nameContains(Vars.get().targets)
                .isNotBeingInteractedWith()
                .isHealthBarNotVisible()
                .findBestInteractable();

        Optional<Npc> attackingMe = Query.npcs()
                .nameContains(Vars.get().targets)
                .isInteractingWithMe()
                .findBestInteractable();

        if (attackingMe.isPresent() && attackingMe.get().getHealthBarPercent() != 0) return attackingMe;
        else if (bestIntractable.isPresent()) return bestIntractable;

        return Optional.empty();
    }


    public void killUndeadDruids() {

        if (!Combat.isAutoRetaliateOn())
            Combat.setAutoRetaliate(true);

        org.tribot.script.sdk.Prayer.enableAll(org.tribot.script.sdk.Prayer.PROTECT_FROM_MAGIC,
                org.tribot.script.sdk.Prayer.EAGLE_EYE);

        Optional<Npc> attackingMe = getNpcToAttack();

        if (attackingMe.isPresent()) {

            org.tribot.script.sdk.Prayer.enableAll(org.tribot.script.sdk.Prayer.PROTECT_FROM_MAGIC,
                    org.tribot.script.sdk.Prayer.EAGLE_EYE);

            if (!attackingMe.get().isHealthBarVisible() && attackingMe.get().getHealthBarPercent() != 0 &&
                    attackingMe.map(t -> t.interact("Attack")).orElse(false)) {
                Vars.get().status = "Attacking Target";
                Vars.get().currentTime = System.currentTimeMillis();
                Waiting.waitUntil(3500, () -> attackingMe.get().isHealthBarVisible());
            } else {

            }
            if (Vars.get().drinkPotions)
                Utils.drinkPotion(Vars.get().potionNames);

            if (waitUntilOutOfCombat())
                Utils.idleNormalAction();

        } else
            //  General.println("Cannot find target");
            Waiting.waitUniform(200, 400);
    }


    public static boolean waitUntilOutOfCombat() {
        int eatAtHP = AntiBan.getEatAt() + General.random(3, 12);//true if praying

        return Waiting.waitUntil(General.random(20000, 40000), () -> {
            Waiting.waitUniform(100, 300);

            AntiBan.timedActions();

            if (EatUtil.hpPercent() <= (eatAtHP)) {
                EatUtil.eatFood();
            }

            return !Combat.isUnderAttack() || !EatUtil.hasFood() ||
                    (CombatUtil.isPraying() && Prayer.getPrayerPoints() < 7);
        });
    }


    public void killDragons() {

        /**
         * Add looting while in combat
         * add alching in combat
         * add leaving if out of food/antifire/pots
         */
        MoveToArea.activateRuneDragonPrayer();

        Optional<RSNPC> targ = setCurrentTarget(Vars.get().targets);
        if (targ.isEmpty()) return;


        if (!Combat.isAutoRetaliateOn())
            Combat.setAutoRetaliate(true);

        if (Vars.get().drinkPotions)
            Utils.drinkPotion(Vars.get().potionNames);

        RSCharacter target = Combat.getTargetEntity();
        if (!Combat.isUnderAttack() && CombatUtil.clickTarget(targ.get())) {
            Vars.get().status = "Attacking Target";
            Vars.get().currentTime = System.currentTimeMillis();
            Timer.waitCondition(Combat::isUnderAttack, 2500, 4000);
        } else if (Combat.isUnderAttack() || target != null) {
            Vars.get().status = "Fighting Target";
            Vars.get().currentTime = System.currentTimeMillis();
        }


        if (CombatUtil.waitUntilOutOfCombat(targ.get(), AntiBan.getEatAt(), 85000)) {
            Utils.idle(200, 1700);
            /// sleep(targ.get());
        } else {

            Waiting.waitNormal(750, 150);

        }
        int p = org.tribot.script.sdk.Prayer.getPrayerPoints();
        Log.log("Recharging prayer");
        if (p < 15 && Utils.clickObj("Altar", "Pray-at")) {
            Timer.waitCondition(() -> org.tribot.script.sdk.Prayer.getPrayerPoints() > p, 7000, 9000);
        }
    }

    int abc2Chance = General.random(0, 100);

    private void sleep(RSNPC currentTarget) {
        if (currentTarget != null && currentTarget.getHealthPercent() == 0) {
            // General.sleep(General.random(100, 500));
            if (Vars.get().abc2Delay && abc2Chance < 60) {
                Vars.get().status = "ABC2 Sleeping...";
                Utils.abc2ReactionSleep(Vars.get().currentTime);
                abc2Chance = General.random(0, 100);

            } else {
                Vars.get().status = "Sleeping...";
                General.sleep(General.random(150, 3000));
                abc2Chance = General.random(0, 100);
            }
        }
    }


    @Override
    public String toString() {
        return "Attacking";
    }


    @Override
    public Priority priority() {
        return Priority.MEDIUM;
    }

    @Override
    public boolean validate() {
        return Areas.UNDEAD_DRUID_AREA.contains(Player.getPosition());//MoveToArea.RUNE_DRAGON_AREA.contains(Player.getPosition());
    }

    @Override
    public void execute() {
        killUndeadDruids();
    }
}
