package scripts.Tasks;

import org.tribot.api.General;
import org.tribot.api2007.NPCs;
import org.tribot.api2007.PathFinding;
import org.tribot.api2007.Player;
import org.tribot.api2007.types.RSCharacter;
import org.tribot.api2007.types.RSNPC;
import org.tribot.script.sdk.*;

import org.tribot.script.sdk.query.Query;
import org.tribot.script.sdk.types.Area;
import org.tribot.script.sdk.types.Npc;
import scripts.API.AntiPKThread;
import scripts.*;
import scripts.Data.Areas;
import scripts.Data.Vars;
import scripts.EntitySelector.Entities;
import scripts.EntitySelector.finders.prefabs.NpcEntity;
//
import java.util.List;
import java.util.Optional;
import java.util.Random;

public class AttackNpc implements Task {

    private static int SCARAB_PRE_ATTACK_ANIMATION = 5443;


    public static void setPrayer(boolean on) {
        if (on && Prayer.getPrayerPoints() > 0) {
            if (Vars.get().shouldPrayMelee)
                PrayerUtil.setPrayer(PrayerType.MELEE);

            if (Vars.get().shouldPrayMagic)
                PrayerUtil.setPrayer(PrayerType.MAGIC);

        } else if (!on) {
            if (Prayer.isAllEnabled(org.tribot.script.
                    sdk.Prayer.PROTECT_FROM_MELEE))
                Prayer.disableAll(Prayer.PROTECT_FROM_MELEE);

            if (Prayer.isAllEnabled(org.tribot.script.
                    sdk.Prayer.PROTECT_FROM_MAGIC))
                Prayer.disableAll(Prayer.PROTECT_FROM_MAGIC);

            if (Prayer.isAllEnabled(org.tribot.script.
                    sdk.Prayer.PROTECT_FROM_MISSILES))
                Prayer.disableAll(Prayer.PROTECT_FROM_MISSILES);

        }
    }

    public Optional<RSNPC> setCurrentTarget(String[] monsterStrings) {
        if (MyPlayer.isHealthBarVisible()) {
            RSCharacter target = org.tribot.api2007.Combat.getTargetEntity();

            if (target != null && target.getHealthPercent() == 0) {
                RSNPC[] potentialTargets = NPCs.findNearest(monsterStrings);
                if (potentialTargets.length > 1) {
                    General.println("[AttackNPC]: Current target is dead, getting next target");
                    return Optional.ofNullable(potentialTargets[1]);
                }
            } else {
                Optional<String> name = Optional.ofNullable(org.tribot.api2007.Combat.getTargetEntity())
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


        RSCharacter target = org.tribot.api2007.Combat.getTargetEntity();
        Optional<RSNPC> targ = setCurrentTarget(Vars.get().targets);

        if (!AntiPKThread.isWorldVisible(AntiPKThread.nextWorld)) {
            AntiPKThread.scrollToWorldNoClick(AntiPKThread.nextWorld);
        }
        if (targ.isPresent()) {
            Prayer.enableAll(Prayer.PROTECT_FROM_MAGIC,
                    Prayer.EAGLE_EYE);

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

    public Optional<Npc> getNpcToAttack(Area area) {
        Optional<Npc> bestIntractable = Query.npcs()
                .nameContains(Vars.get().targets)
                .inArea(area)
                .isNotBeingInteractedWith()
                .isNotInteractingWithCharacter()
                .isHealthBarNotVisible()
                .findBestInteractable();

        Optional<Npc> attackingMe = Query.npcs()
                .nameContains(Vars.get().targets)
                .inArea(area)
                .isInteractingWithMe()
                .findBestInteractable();


        if (attackingMe.isPresent() && attackingMe.get().isHealthBarVisible() &&
                attackingMe.get().getHealthBarPercent() != 0) return attackingMe;

        else if (bestIntractable.isPresent()) {
            Log.debug("Returning best Interactable NPC");
            return bestIntractable;
        }

        return Optional.empty();
    }

    public boolean activateQuickPrayers(Prayer... prayers) {
        if (!Prayer.isQuickPrayersSelected(prayers))
            Prayer.selectQuickPrayers(prayers);

        if (Prayer.getPrayerPoints() == 0)
            Utils.drinkPotion(ItemID.PRAYER_POTION);

        return Prayer.enableQuickPrayer();
    }

    public static boolean shouldFlickPrayerOn() {
        return Query.npcs()
                .isAnimation(SCARAB_PRE_ATTACK_ANIMATION)
                .inArea(Utils.getAreaFromRSArea(Areas.LARGE_SCARAB_FIGHT_AREA))
                .isAny();
    }

    public void killScarabMages() {

        if (!activateQuickPrayers(Prayer.PROTECT_FROM_MAGIC, Prayer.HAWK_EYE))
            return; // failed to activate for some reason

        if (Inventory.contains(ItemID.CANDLE) &&
                Utils.useItemOnItem(ItemID.TINDERBOX, ItemID.CANDLE))
            Utils.idleNormalAction();

        //toggle autoretalite if needed
        if (!Combat.isAutoRetaliateOn())
            Combat.setAutoRetaliate(true);

        Optional<Npc> attackingMe = getNpcToAttack(Areas.scarabFightAreaSdk);

        if (attackingMe.isPresent()) {
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


    public void killUndeadDruids() {

        if (!Combat.isAutoRetaliateOn())
            Combat.setAutoRetaliate(true);

        Prayer.enableAll(Prayer.PROTECT_FROM_MAGIC,
                Prayer.EAGLE_EYE);

        Optional<Npc> attackingMe = getNpcToAttack(Areas.UNDEAD_DRUID_AREA_SDK);

        if (attackingMe.isPresent()) {

            Prayer.enableAll(Prayer.PROTECT_FROM_MAGIC,
                    Prayer.EAGLE_EYE);

            if (!attackingMe.get().isHealthBarVisible() && attackingMe.get().getHealthBarPercent() != 0 &&
                    attackingMe.map(t -> t.interact("Attack")).orElse(false)) {
                Vars.get().status = "Attacking Target";
                Vars.get().currentTime = System.currentTimeMillis();
                Waiting.waitUntil(4500, () -> attackingMe.get().isHealthBarVisible());
            } else {

            }
            General.random(0, 0);
            if (Vars.get().drinkPotions)
                Utils.drinkPotion(Vars.get().potionNames);

            if (waitUntilOutOfCombat()) {
                int num = Utils.random(0, 100);
                if (num < 22) {
                    Utils.idleAfkAction();
                } else
                    Utils.idleNormalAction();
            }


        } else {
            General.println("Waiting for target");
            Prayer.disableQuickPrayer();
            Timer.waitCondition(() -> attackingMe.isPresent(), 10000, 15000);
            Utils.idleNormalAction();
        }
    }


    public static boolean waitUntilOutOfCombat() {
        int eatAtHP = AntiBan.getEatAt() + General.random(3, 12);

        return Waiting.waitUntil(General.random(40000, 60000), () -> {
            Waiting.waitUniform(150, 450);
            if (Vars.get().killingScarabs)
                Prayer.enableQuickPrayer();

            AntiBan.timedActions();
           /* if (Vars.get().killingScarabs && shouldFlickPrayerOn()) {
                Prayer.enableQuickPrayer();
            } else if (Vars.get().killingScarabs && !shouldFlickPrayerOn()) {
                Prayer.disableQuickPrayer();
            } */

            if (EatUtil.hpPercent() <= (eatAtHP)) {
                EatUtil.eatFood();
            }
            List<Npc> attackingMe = Query.npcs()
                    .nameContains(Vars.get().targets)
                    .isInteractingWithMe()
                    .toList();

            return //!MyPlayer.isHealthBarVisible() ||
                    attackingMe.size() == 0 ||   Inventory.contains(ItemID.CANDLE) ||
                            !EatUtil.hasFood() || LootItems.getLootItem().isPresent() ||
                            (CombatUtil.isPraying() && Prayer.getPrayerPoints() < 10);
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

        RSCharacter target = org.tribot.api2007.Combat.getTargetEntity();
        if (!MyPlayer.isHealthBarVisible() && CombatUtil.clickTarget(targ.get())) {
            Vars.get().status = "Attacking Target";
            Vars.get().currentTime = System.currentTimeMillis();
            Timer.waitCondition(org.tribot.api2007.Combat::isUnderAttack, 2500, 4000);
        } else if (MyPlayer.isHealthBarVisible() || target != null) {
            Vars.get().status = "Fighting Target";
            Vars.get().currentTime = System.currentTimeMillis();
        }


        if (CombatUtil.waitUntilOutOfCombat(targ.get(), AntiBan.getEatAt(), 85000)) {
            Utils.idle(200, 1700);
            /// sleep(targ.get());
        } else {

            Waiting.waitNormal(750, 150);

        }
        int p = Prayer.getPrayerPoints();
        Log.log("Recharging prayer");
        if (p < 15 && Utils.clickObj("Altar", "Pray-at")) {
            Timer.waitCondition(() -> Prayer.getPrayerPoints() > p, 7000, 9000);
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
        if (Vars.get().killingScarabs) {
            return Areas.LARGE_SCARAB_FIGHT_AREA.contains(Player.getPosition());
        }
        return Vars.get().killingUndeadDruids ?
                Areas.UNDEAD_DRUID_AREA.contains(Player.getPosition()) :
                Areas.LARGE_SCARAB_FIGHT_AREA.contains(Player.getPosition());//MoveToArea.RUNE_DRAGON_AREA.contains(Player.getPosition());
    }

    @Override
    public void execute() {
        if (Vars.get().killingScarabs) {
            killScarabMages();
        } else if (Vars.get().killingUndeadDruids)
            killUndeadDruids();
        else
            Log.error("No execute() found for AttackNpc Class");
    }
}
