package scripts.Tasks.Slayer.Tasks;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.input.Mouse;
import org.tribot.api2007.*;
import org.tribot.api2007.types.RSCharacter;
import org.tribot.api2007.types.RSNPC;
import org.tribot.script.sdk.Log;
import org.tribot.script.sdk.MyPlayer;
import org.tribot.script.sdk.Waiting;
import org.tribot.script.sdk.interfaces.Character;
import org.tribot.script.sdk.query.Query;
import org.tribot.script.sdk.types.InventoryItem;
import org.tribot.script.sdk.types.Npc;
import org.tribot.script.sdk.walking.LocalWalking;
import scripts.*;
import scripts.API.Priority;
import scripts.API.Task;
import scripts.Data.SkillTasks;
import scripts.Data.Vars;
import scripts.EntitySelector.Entities;
import scripts.EntitySelector.finders.prefabs.NpcEntity;
import scripts.Tasks.Slayer.SlayerConst.Areas;
import scripts.Tasks.Slayer.SlayerConst.Assign;
import scripts.Tasks.Slayer.SlayerUtils.SlayerVars;


import java.util.List;
import java.util.Optional;

public class AttackNpc implements Task {

    public static void setPrayer() {
        Assign assign = SlayerVars.get().assignment;
        if (assign != null) {
            if (assign.getPrayerType().equals(PrayerType.MELEE))
                PrayerUtil.setPrayer(PrayerType.MELEE);

            else if (assign.getPrayerType().equals(PrayerType.MAGIC))
                PrayerUtil.setPrayer(PrayerType.MAGIC);

            else if (assign.getPrayerType().equals(PrayerType.NONE)) {
                if (Prayer.isPrayerEnabled(Prayer.PRAYERS.PROTECT_FROM_MELEE))
                    Prayer.disable(Prayer.PRAYERS.PROTECT_FROM_MELEE);

                if (Prayer.isPrayerEnabled(Prayer.PRAYERS.PROTECT_FROM_MAGIC))
                    Prayer.disable(Prayer.PRAYERS.PROTECT_FROM_MAGIC);

                if (Prayer.isPrayerEnabled(Prayer.PRAYERS.PROTECT_FROM_MISSILES))
                    Prayer.disable(Prayer.PRAYERS.PROTECT_FROM_MISSILES);
            }
        }
    }

    public static void expeditiousMessage(String message) {
        if (message.contains("helps you progress your slayer task")) {
            Utils.microSleep();
            SlayerVars.get().remainingKills--;
        }
    }

    public Optional<Npc> setCurrentTargetSDK(String[] monsterStrings) {
        if (SlayerVars.get().fightArea == null) {
            General.println("[AttackNPC]: Fight area is null");
            return Optional.empty();
        }

        if (monsterStrings == null)
            return Optional.empty();

        if (MyPlayer.isHealthBarVisible()) {
            Optional<Character> target = MyPlayer.get().flatMap(player -> player.getInteractingCharacter());

            if (target.isEmpty())
                return Optional.empty();

            // NPC we're interacting with is dead, look for next one
            if (target.get().getHealthBarPercent() == 0) {
                List<Npc> potentialTargets = Query.npcs()
                        .nameContains(monsterStrings)
                        .isNotBeingInteractedWith()
                        // .inArea(SlayerVars.get().fightArea)
                        .toList();

                if (potentialTargets.size() > 0) {
                    General.println("[AttackNPC]: Current target is dead, getting next target");
                    return Optional.ofNullable(potentialTargets.get(0));
                }
            } else {


            }
        } else {
            int i = 0;
            List<Npc> potentialTargets = Query.npcs()
                    .nameContains(monsterStrings)
                    .isNotBeingInteractedWith()
                    // .inArea(SlayerVars.get().fightArea)
                    .actionContains("Attack")
                    .isReachable()
                    .sortedByInteractionCost()
                    // .sortedByDistance() // not sure if this is better than interaction or not
                    .toList();

            for (Npc targ : potentialTargets) {
                i++;
                // we're close to the target
                if (targ.getTile().distanceTo(MyPlayer.getPosition()) < General.random(5, 7) &&
                        targ.getHealthBarPercent() != 0) {
                    return Optional.of(targ);

                }
                // we're NOT close to the target, walk to it
                else if (LocalWalking.walkTo(targ.getTile()) && targ.getHealthBarPercent() != 0) {
                    return Optional.of(targ);
                }
            }

        }
        return Optional.empty();
    }

    public Optional<RSNPC> setCurrentTarget(String[] monsterStrings) {
        if (SlayerVars.get().fightArea == null) {
            General.println("[AttackNPC]: Fight area is null");
            return Optional.empty();
        }
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
                        .inArea(SlayerVars.get().fightArea)
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

    private void wallBeastTask() {

        if (!Combat.isAutoRetaliateOn())
            Combat.setAutoRetaliate(true);

        if (Areas.ABOVE_LUMBRIDGE_SWAMP_ENTRANCE.contains(Player.getPosition()))
            if (Utils.clickObj("Dark hole", "Climb-down"))
                Timer.slowWaitCondition(() -> !Areas.ABOVE_LUMBRIDGE_SWAMP_ENTRANCE.contains(Player.getPosition()), 7000, 10000);

        if (Areas.WALL_BEAST_AREA.contains(Player.getPosition()) &&
                !Player.getPosition().equals(Areas.WALL_BEAST_TILE)) {
            Log.log("[Debug]: Going to wall beast tile");
            if (PathingUtil.clickScreenWalk(Areas.WALL_BEAST_TILE)) {
                PathingUtil.movementIdle();
                Timer.slowWaitCondition(()
                        -> Combat.isUnderAttack(), 12000, 15000);
            }
        }
        if (Combat.isUnderAttack()) {
            Log.log("[Debug]: Fighting Wall beast");
            if (CombatUtil.waitUntilOutOfCombat(AntiBan.getEatAt()))
                Waiting.waitNormal(1500, 375);

        }

        if (Areas.WALL_BEAST_AREA.contains(Player.getPosition()) && !Combat.isUnderAttack()
                && Player.getPosition().equals(Areas.WALL_BEAST_TILE)) {
            Log.log("[Debug]: Resetting Wall beast");
            if (Utils.clickObj("Climbing rope", "Climb"))
                Timer.waitCondition(() -> Areas.ABOVE_LUMBRIDGE_SWAMP_ENTRANCE.contains(Player.getPosition()), 9000, 15000);
        }

    }

    int i = 0;

    public void fightNew() {
        if (!org.tribot.script.sdk.Combat.isAutoRetaliateOn())
            org.tribot.script.sdk.Combat.setAutoRetaliate(true);

        RSCharacter target = Combat.getTargetEntity();
        Optional<RSNPC> targ = setCurrentTarget(SlayerVars.get().targets);

        if (targ.isPresent()) {
            if (!Combat.isUnderAttack() && CombatUtil.clickTarget(targ.get())) {
                SlayerVars.get().status = "Attacking Target";
                SlayerVars.get().currentTime = System.currentTimeMillis();

            } else if (Combat.isUnderAttack() && target != null) {
                SlayerVars.get().status = "Fighting Target";
                SlayerVars.get().currentTime = System.currentTimeMillis();
            }

            if (SlayerVars.get().drinkPotions)
                Utils.drinkPotion(SlayerVars.get().potionNames);

            if (MyPlayer.isPoisoned())
                Utils.drinkPotion(ItemID.ANTIDOTE_PLUS_PLUS);

            if (Skills.SKILLS.STRENGTH.getCurrentLevel() <= SlayerVars.get().drinkCombatPotion) {
                if (Utils.drinkPotion(ItemID.SUPER_COMBAT_POTION)) {
                    SlayerVars.get().drinkCombatPotion = Skills.SKILLS.STRENGTH.getActualLevel() + General.random(3, 6);
                    Log.log("[Debug]: Next drinking potion at Strength lvl " + SlayerVars.get().drinkCombatPotion);
                }
            }

            if (SlayerVars.get().shouldPrayMelee) {
                General.println("[Fight]: Using long timeout on fight b/c prayer is being used");
                if (CombatUtil.waitUntilOutOfCombat(targ.get(), AntiBan.getEatAt(), 65000)) {
                    sleep(targ.get());
                }
            } else if (waitUntilOutOfCombat(targ.get(), AntiBan.getEatAt(), 45000)) {
                sleep(targ.get());

            } else
                Waiting.waitNormal(1100, 430);

        } else {
            General.println("[Fight]: Unable to set target");
            Waiting.waitNormal(200, 50);
            i++;
            if (i >= 10 &&
                    PathingUtil.walkToTile(SlayerVars.get().fightArea.getRandomTile())) {
                General.println("[Fight]: Walking to area");
                i = 0;
            }
        }
    }

    public void fight() {
        if (!Combat.isAutoRetaliateOn())
            Combat.setAutoRetaliate(true);

        RSCharacter target = Combat.getTargetEntity();
        Optional<RSNPC> targ = setCurrentTarget(SlayerVars.get().targets);

        if (targ.isPresent()) {
            if (!Combat.isUnderAttack() && CombatUtil.clickTarget(targ.get())) {
                SlayerVars.get().status = "Attacking Target";
                SlayerVars.get().currentTime = System.currentTimeMillis();

            } else if (Combat.isUnderAttack() && target != null) {
                SlayerVars.get().status = "Fighting Target";
                SlayerVars.get().currentTime = System.currentTimeMillis();
            }

            if (SlayerVars.get().drinkPotions)
                Utils.drinkPotion(SlayerVars.get().potionNames);

            if (MyPlayer.isPoisoned())
                Utils.drinkPotion(ItemID.ANTIDOTE_PLUS_PLUS);

            if (Skills.SKILLS.STRENGTH.getCurrentLevel() <= SlayerVars.get().drinkCombatPotion) {
                if (Utils.drinkPotion(ItemID.SUPER_COMBAT_POTION)) {
                    SlayerVars.get().drinkCombatPotion = Skills.SKILLS.STRENGTH.getActualLevel() + General.random(3, 6);
                    Log.log("[Debug]: Next drinking potion at Strength lvl " + SlayerVars.get().drinkCombatPotion);
                }
            }

            if (SlayerVars.get().shouldPrayMelee) {
                General.println("[Fight]: Using long timeout on fight b/c prayer is being used");
                if (CombatUtil.waitUntilOutOfCombat(targ.get(), AntiBan.getEatAt(), 65000)) {
                    sleep(targ.get());
                }
            } else if (waitUntilOutOfCombat(targ.get(), AntiBan.getEatAt(), 45000)) {
                sleep(targ.get());

            } else
                Waiting.waitNormal(1300, 430);

        } else {
            General.println("[Fight]: Unable to set target");
            Waiting.waitNormal(200, 50);
            i++;
            if (i >= 10 &&
                    PathingUtil.walkToTile(SlayerVars.get().fightArea.getRandomTile())) {
                General.println("[Fight]: Walking to area");
                i = 0;
            }
        }
    }

    public static boolean waitUntilOutOfCombatNew(Optional<Npc> npcOptional, int eatAt, int longTimeOut) {
        int eatAtHP = eatAt + General.random(1, 10);//true if praying

        List<org.tribot.script.sdk.Prayer> prayList = org.tribot.script.sdk.Prayer.getActivePrayers();
        return Waiting.waitUntil(longTimeOut, () -> {
            Waiting.waitNormal(500, 125);

            //AntiBan.timedActions();

            if (EatUtil.hpPercent() <= (eatAtHP))
                EatUtil.eatFood();

            if (prayList.size() > 0 && org.tribot.script.sdk.Prayer.getPrayerPoints() < General.random(7, 27)) {
                General.println("[CombatUtil]: WaitUntilOutOfCombat -> Drinking Prayer potion");
                EatUtil.drinkPotion(ItemID.PRAYER_POTION);
            }
            if (SlayerVars.get().assignment != null && SlayerVars.get().assignment.isUseSpecialItem()
                    && npcOptional.isPresent()) {
                //  Log.log("Using special item");
                if (checkSpecialItem(npcOptional.get()))
                    Waiting.waitNormal(1500, 440);

            }
            return !MyPlayer.isHealthBarVisible() ||
                    !EatUtil.hasFood() ||
                    (CombatUtil.isPraying() && Prayer.getPrayerPoints() < 5);
        });
    }

    public static boolean checkSpecialItem(Npc target) {
        if (target != null && target.getHealthBarPercent() < 14) {
            Optional<InventoryItem> i = Query.inventory().idEquals(ItemID.SLAYER_SPECIAL_ITEMS)
                    .findClosestToMouse();
            if (i.isPresent()) {
                General.println("[CombatUtils]: Using Slayer Item on NPC");
                return useSlayerItemOnNPC();
            }
        }
        return false;
    }

    private static boolean useSlayerItemOnNPC() {
        Optional<InventoryItem> i = Query.inventory().idEquals(ItemID.SLAYER_SPECIAL_ITEMS)
                .findClosestToMouse();

        Optional<Npc> n = Query.npcs().isInteractingWithMe().stream().findFirst();
        if (i.isPresent() && n.isPresent() && n.get().getHealthBarPercent() < 0.15) {
            Log.log("[AttackNPC]: Health percent is " + n.get().getHealthBarPercent());
            if (Game.getItemSelectionState() == 0 &&
                    i.get().click())
                Waiting.waitNormal(75, 20);
            if (Game.getItemSelectionState() == 1)
                return Timing.waitCondition(() -> n.get().interact("Use"), 1000);

        }
        Log.log("[AttackNPC]: UseSlayerItemOnNPC is false");
        return false;
    }


    public static boolean waitUntilOutOfCombat(RSNPC name, int eatAt, int longTimeOut) {
        int eatAtHP = eatAt + General.random(1, 10);//true if praying

        int icon = Player.getRSPlayer().getPrayerIcon();

        Timing.waitCondition(() -> {
            General.sleep(General.random(100, 500));

            AntiBan.timedActions();

            if (EatUtil.hpPercent() <= (eatAtHP)) {
                EatUtil.eatFood();
            }
            if (icon != -1 && Prayer.getPrayerPoints() < General.random(7, 27)) {
                General.println("[CombatUtil]: WaitUntilOutOfCombat -> Drinking Prayer potion");
                EatUtil.drinkPotion(ItemID.PRAYER_POTION);
            }
            if (AntiBan.getShouldHover() && Mouse.isInBounds() && name != null) {
                AntiBan.hoverNextNPC(name.getName());
                AntiBan.resetShouldHover();
            }
            if (SlayerVars.get().assignment != null && SlayerVars.get().assignment.isUseSpecialItem()) {
                // Log.log("Using special item");
                if (CombatUtil.checkSpecialItem(name)) {
                    Waiting.waitNormal(1500, 440);
                }
            }
            if (AntiBan.getShouldOpenMenu() && (Mouse.isInBounds() && (!ChooseOption.isOpen())) && name != null)
                AntiBan.openMenuNextNPC(name.getName());

            RSCharacter target = Combat.getTargetEntity();
            if (target != null) {
                if (target.getHealthPercent() == 0) {
                    Vars.get().daxTracker.trackData("Kills", 1);
                }
                return !Combat.isUnderAttack() || !EatUtil.hasFood()
                        || (CombatUtil.isPraying() && Prayer.getPrayerPoints() < 5);
            }
            return !Combat.isUnderAttack() || !EatUtil.hasFood() || Prayer.getPrayerPoints() < 5;
        }, General.random(longTimeOut - 5000, longTimeOut));

        AntiBan.resetShouldOpenMenu();

        RSCharacter target = Combat.getTargetEntity();
        if (target != null) {
            if (target.getHealthPercent() == 0) {
                Vars.get().daxTracker.trackData("Kills", 1);
            }

            return target.getHealthPercent() == 0 || !Combat.isUnderAttack()
                    || !EatUtil.hasFood() || (CombatUtil.isPraying() && Prayer.getPrayerPoints() < 5);
        }

        if (ChooseOption.isOpen() && !Combat.isUnderAttack() && EatUtil.hasFood()) {
            CombatUtil.clickAttack();
        }
        return !Combat.isUnderAttack();
    }


    private void sleep(RSNPC currentTarget) {
        if (currentTarget != null && currentTarget.getHealthPercent() == 0) {
            // General.sleep(General.random(100, 500));
            if (SlayerVars.get().abc2Delay && SlayerVars.get().abc2Chance < 60) {
                SlayerVars.get().status = "ABC2 Sleeping...";
                Utils.abc2ReactionSleep(SlayerVars.get().currentTime);
                SlayerVars.get().abc2Chance = General.random(0, 100);

            } else {
                SlayerVars.get().status = "Sleeping...";
                // int sleep = General.random(150, 3000);
                int sleepSD = General.randomSD(150, 4000, 1150, 325);
                Log.log("[AttackNpc]: Sleeping for " + sleepSD);
                Waiting.wait(sleepSD);
                SlayerVars.get().abc2Chance = General.random(0, 100);
            }
        }
    }

    @Override
    public String toString() {
        return "Attacking Target"; //(Remaining: " +  Game.getSetting(394) + ")";
    }


    @Override
    public Priority priority() {
        return Priority.LOW;
    }

    @Override
    public boolean validate() {
        return Vars.get().currentTask != null &&
                Vars.get().currentTask.equals(SkillTasks.SLAYER) &&
                SlayerVars.get().fightArea != null &&
                SlayerVars.get().fightArea.contains(Player.getPosition()) &&
                !SlayerShop.shouldSlayerShop();
    }

    @Override
    public void execute() {
        setPrayer();
        //  Log.log("Assignment is " + SlayerVars.get().assignment);
        if (SlayerVars.get().assignment != null &&
                SlayerVars.get().assignment.equals(Assign.WALL_BEAST)) {
            wallBeastTask();
        } else
            fight();
    }

    @Override
    public String taskName() {
        return "Slayer";
    }
}
