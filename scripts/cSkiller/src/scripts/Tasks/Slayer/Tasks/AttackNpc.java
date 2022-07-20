package scripts.Tasks.Slayer.Tasks;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.input.Mouse;
import org.tribot.api2007.*;
import org.tribot.api2007.ChooseOption;
import org.tribot.api2007.Combat;
import org.tribot.api2007.types.RSCharacter;
import org.tribot.api2007.types.RSNPC;
import org.tribot.script.sdk.*;
import org.tribot.script.sdk.Equipment;
import org.tribot.script.sdk.Prayer;
import org.tribot.script.sdk.interfaces.Character;
import org.tribot.script.sdk.query.Query;

import org.tribot.script.sdk.types.Area;
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

            else if (assign.getPrayerType().equals(PrayerType.NONE) &&
                    Prayer.getActivePrayers().size() > 0) {
                Prayer.disableAll();
            }
        }
    }

    private static boolean checkAggro() {
        return Waiting.waitUntil(Utils.random(25, 450), 5, () ->
                Query.npcs().isMyPlayerInteractingWith().isAny());
    }

    public static void expeditiousMessage(String message) {
        if (message.contains("helps you progress your slayer task")) {
            Utils.microSleep();
            SlayerVars.get().remainingKills--;
        }
    }

    private static void equipExpeditiousIfNeeded() {
        if (Equipment.Slot.HANDS.getItem().isEmpty()) {
            Utils.idleNormalAction(); //idle before doing it
            Utils.equipItem(ItemID.EXPEDITIOUS_BRACELET);
        }
    }

    private Optional<Npc> setCurrentTargetSDK(String[] monsterStrings) {
        if (SlayerVars.get().fightArea == null) {
            Log.info("[AttackNPC]: Fight area is null");
            return Optional.empty();
        }

        if (monsterStrings == null)
            return Optional.empty();

        var target = Query.npcs().isMyPlayerInteractingWith()
                .findClosestByPathDistance();
        //Optional<Character> target = MyPlayer.get().flatMap(player -> player.getInteractingCharacter());

        if (!target.isEmpty()) {
            // Log.info("Interacting Target is empty, finding a new one");
            int i = 0;
            List<Npc> potentialTargets = Query.npcs()
                    .nameContains(monsterStrings)
                    .isNotBeingInteractedWith()
                    // .inArea(SlayerVars.get().fightArea)
                    .actionContains("Attack")
                    .isReachable()
                    .sortedByInteractionCost()
                    .toList();


            for (Npc targ : potentialTargets) {
                i++;
                // we're close to the target
                if (targ.getTile().distanceTo(MyPlayer.getTile()) <= General.random(5, 7) &&
                        targ.getHealthBarPercent() != 0) {
                    //Log.info("returning target at index " + i);
                    return Optional.of(targ);

                }
                // we're NOT close to the target, walk to it
                else if (LocalWalking.walkTo(targ.getTile()) && targ.getHealthBarPercent() != 0) {
                    return Optional.of(targ);
                }
            }
        } else if (target.map(t -> t.getHealthBarPercent() == 0).orElse(false)) {
            // NPC we're interacting with is dead, look for next one
            List<Npc> potentialTargets = Query.npcs()
                    .nameContains(monsterStrings)
                    .isNotBeingInteractedWith()
                    .isHealthBarNotVisible()
                    .isReachable()
                    .sortedByDistance()
                    // .inArea(SlayerVars.get().fightArea)
                    .toList();
            if (potentialTargets.size() > 0)
                return Optional.ofNullable(potentialTargets.get(0));

        } else return target;

        return Optional.empty();
    }


    private void wallBeastTask() {

        if (!Combat.isAutoRetaliateOn())
            Combat.setAutoRetaliate(true);

        if (Areas.ABOVE_LUMBRIDGE_SWAMP_ENTRANCE.contains(MyPlayer.getTile()))
            if (Utils.clickObj("Dark hole", "Climb-down"))
                Timer.slowWaitCondition(() -> !Areas.ABOVE_LUMBRIDGE_SWAMP_ENTRANCE.contains(MyPlayer.getTile()), 7000, 10000);

        if (Areas.WALL_BEAST_AREA.contains(MyPlayer.getTile()) &&
                !MyPlayer.getTile().equals(Areas.WALL_BEAST_TILE)) {
            Log.info("Going to wall beast tile");
            if (Areas.WALL_BEAST_TILE.interact("Walk here")) {
                PathingUtil.movementIdle();
                Timer.slowWaitCondition(()
                        -> MyPlayer.isHealthBarVisible(), 12000, 15000);
            }
        }
        if (MyPlayer.isHealthBarVisible()) {
            Log.info("Fighting Wall beast");
            if (CombatUtil.waitUntilOutOfCombat(AntiBan.getEatAt()))
                Waiting.waitNormal(1500, 375);

        }

        if (Areas.WALL_BEAST_AREA.contains(MyPlayer.getTile()) && !MyPlayer.isHealthBarVisible()
                && MyPlayer.getTile().equals(Areas.WALL_BEAST_TILE)) {
            Log.info("Resetting Wall beast");
            if (Utils.clickObj("Climbing rope", "Climb"))
                Timer.waitCondition(() -> Areas.ABOVE_LUMBRIDGE_SWAMP_ENTRANCE.contains(MyPlayer.getTile()), 9000, 15000);
        }

    }

    private int i = 0;

    private void fightNew() {
        if (!Combat.isAutoRetaliateOn())
            Combat.setAutoRetaliate(true);

        Optional<Npc> targ = setCurrentTargetSDK(SlayerVars.get().targets);

        if (targ.isEmpty()) {
            Log.info("[Fight]: Unable to set target");
            Waiting.waitNormal(200, 50);
            i++;
            Area slayArea = SlayerVars.get().fightArea;
            if (i >= 10 && slayArea != null && !slayArea.contains(MyPlayer.getTile()) &&
                    PathingUtil.walkToTile(slayArea.getRandomTile())) {
                Log.info("[Fight]: Walking to area");
                i = 0;
            }
            return;
        }
        if ((!checkAggro() &&
                (targ.map(t -> !t.isInteractingWithMe() ||
                        !t.isHealthBarVisible()).orElse(false))) &&
                targ.map(t -> t.interact("Attack")).orElse(false)) {
            SlayerVars.get().status = "Attacking Target";
            SlayerVars.get().currentTime = System.currentTimeMillis();
            Waiting.waitUntil(4000, 75,
                    () -> Query.npcs().isMyPlayerInteractingWith().isAny());


        } else if (targ.map(t -> t.isInteractingWithMe()).orElse(false)) { //already fighting
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
                Log.info("Next drinking potion at Strength lvl " + SlayerVars.get().drinkCombatPotion);
            }
        }

        if (SlayerVars.get().shouldPrayMelee) {
            //Log.info("[Fight]: Using long timeout on fight b/c prayer is being used");
            if (waitUntilOutOfCombatNew(targ, AntiBan.getEatAt(), 75000)) {
                sleep(targ.get());
            }
        } else if (waitUntilOutOfCombatNew(targ, AntiBan.getEatAt(), 45000)) {
            //Log.info("Sleeping after WaitUntilAfterCombat()");
            sleep(targ.get());

        } else
            Utils.idleNormalAction(true);

    }


    public static boolean waitUntilOutOfCombatNew(Optional<Npc> npcOptional, int eatAt, int longTimeOut) {
        int eatAtHP = eatAt + General.random(1, 10);//true if praying
        Assign assign = SlayerVars.get().assignment;
        return Waiting.waitUntil(longTimeOut, 25, () -> {

            AntiBan.timedActions();

            if (EatUtil.hpPercent() <= eatAtHP)
                EatUtil.eatFood();

            if (MyPlayer.isPoisoned())
                Utils.drinkPotion(ItemID.ANTIDOTE_PLUS_PLUS);

            equipExpeditiousIfNeeded();

            if ((Prayer.getActivePrayers().size() > 0 || (assign != null &&
                    assign.getPrayerType().equals(PrayerType.MELEE))) &&
                    org.tribot.script.sdk.Prayer.getPrayerPoints() <
                            General.random(7, 27)) {
                Log.info("[CombatUtil]: WaitUntilOutOfCombat -> Drinking Prayer potion");
                EatUtil.drinkPotion(ItemID.PRAYER_POTION);
            }
            if (assign != null &&
                    assign.getPrayerType().equals(PrayerType.MELEE) && Prayer.getPrayerPoints() > 0 &&
                    Prayer.getActivePrayers().size() == 0) {
                Prayer.enableAll(Prayer.PROTECT_FROM_MELEE);
            }
            if (assign != null && assign.isUseSpecialItem() && npcOptional.isPresent()) {
                //  Log.log("Using special item");
                if (checkSpecialItem())
                    Waiting.waitNormal(1100, 150);

            }
            return
                    !Query.npcs().isMyPlayerInteractingWith().isAny() ||
                            npcOptional.map(npc -> npc.getHealthBarPercent() == 0).orElse(false) ||
                            !EatUtil.hasFood() ||
                            (Prayer.getActivePrayers().size() > 0 && Prayer.getPrayerPoints() < 6);
        });
    }

    public static boolean checkSpecialItem() {
        Optional<InventoryItem> i = Query.inventory().idEquals(ItemID.SLAYER_SPECIAL_ITEMS)
                .findClosestToMouse();

        Optional<Npc> n = Query.npcs().isInteractingWithMe().stream().findFirst();
        return n.map(npc -> npc.getHealthBarPercent() <= 0.15 &&
                i.map(item -> item.useOn(npc)).orElse(false)).orElse(false) &&
                Waiting.waitUntil(790, 150, () -> MyPlayer.getAnimation() != -1);
    }


    private void sleep(Npc currentTarget) {
        if (checkAggro()) //this adds 500ms of sleep to all
            return;

        if (currentTarget != null && currentTarget.getHealthBarPercent() == 0) {
            // General.sleep(General.random(100, 500));
            if (SlayerVars.get().abc2Delay && SlayerVars.get().abc2Chance < 40) {
                SlayerVars.get().status = "ABC2 Sleeping...";
                Utils.abc2ReactionSleep(SlayerVars.get().currentTime);
                SlayerVars.get().abc2Chance = General.random(0, 100);

            } else {
                SlayerVars.get().status = "Sleeping...";
                Waiting.waitNormal(100, 10); //adds ~100ms to the base sleep, which is fast
                Utils.idleNormalAction(true);
                SlayerVars.get().abc2Chance = General.random(0, 100);
            }
        }
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
                Utils.idleNormalAction();
                //int sleepSD = General.randomSD(150, 4000, 1150, 325);
                // Log.debug("[AttackNpc]: Sleeping for " + sleepSD);
                // Waiting.wait(sleepSD);
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
                SlayerVars.get().fightArea.contains(MyPlayer.getTile()) &&
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
            fightNew();
    }

    @Override
    public String taskName() {
        return "Slayer";
    }
}
