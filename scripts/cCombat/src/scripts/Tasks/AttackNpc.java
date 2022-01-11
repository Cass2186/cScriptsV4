package scripts.Tasks;

import org.tribot.api.General;
import org.tribot.api.input.Mouse;
import org.tribot.api2007.*;
import org.tribot.api2007.types.RSCharacter;
import org.tribot.api2007.types.RSItem;
import org.tribot.api2007.types.RSNPC;
import org.tribot.script.sdk.Waiting;
import scripts.*;
import scripts.API.AntiPKThread;
import scripts.Data.Vars;
import scripts.EntitySelector.Entities;
import scripts.EntitySelector.finders.prefabs.NpcEntity;

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
        if (!AntiPKThread.isWorldVisible(AntiPKThread.nextWorld)){
            AntiPKThread.scrollToWorldNoClick(AntiPKThread.nextWorld);
        }
        if (targ.isPresent()) {


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
        return Vars.get().safeTile.equals(Player.getPosition());//MoveToArea.RUNE_DRAGON_AREA.contains(Player.getPosition());
    }

    @Override
    public void execute() {
        fight();
    }
}
