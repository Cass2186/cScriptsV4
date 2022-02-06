package scripts.QuestPackages.PriestInPeril;

import dax.api_lib.models.RunescapeBank;
import dax.walker.utils.AccurateMouse;
import dax.walker_engine.interaction_handling.NPCInteraction;
import org.tribot.api.General;
import org.tribot.api2007.*;
import org.tribot.api2007.types.*;
import scripts.*;
import scripts.GEManager.GEItem;
import scripts.QuestPackages.ImpCatcher.ImpCatcher;
import scripts.QuestSteps.BuyItemsStep;
import scripts.QuestSteps.NPCStep;
import scripts.QuestSteps.QuestTask;
import scripts.Requirements.InventoryRequirement;
import scripts.Requirements.ItemReq;
import scripts.Requirements.ItemRequirement;
import scripts.Requirements.Requirement;
import scripts.Tasks.Priority;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PriestInPeril implements QuestTask {

    int PURE_ESSENCE = 7936;
    int BUCKET = 1925;
    int ADAMANT_SCIMITAR = 1331;
    int RUNE_SCIMITAR = 1333;
    int VARROCK_TAB = 8007;
    int LOBSTER = 379;
    int GOLDEN_KEY = 2944;
    int MURKY_WATER = 2953;
    int IRON_KEY = 2945;
    int BLESSED_WATER = 2954;

    RSArea KING_RONALD_ROOM = new RSArea(new RSTile(3224, 3469, 0), new RSTile(3220, 3478, 0));
    RSArea CHURCH_DOORS = new RSArea(new RSTile(3408, 3487, 0), new RSTile(3404, 3490, 0));
    RSArea DUNGEON_ENTRANCE = new RSArea(new RSTile(3405, 3507, 0), new RSTile(3405, 3503, 0));
    RSArea CHURCH_BOTTOM_FLOOR = new RSArea(new RSTile(3409, 3493, 0), new RSTile(3418, 3484, 0));
    RSArea CHURCH_SECOND_FLOOR = new RSArea(new RSTile(3406, 3495, 1), new RSTile(3420, 3482, 1));
    RSArea CHURCH_OUTSIDE_JAIL = new RSArea(new RSTile(3415, 3484, 2), new RSTile(3409, 3493, 2));
    RSArea WELL_AREA = new RSArea(new RSTile(3419, 9893, 0), new RSTile(3427, 9886, 0));
    RSArea GATE_AREA = new RSArea(new RSTile(3427, 9900, 0), new RSTile(3431, 9893, 0));
    RSArea DREZEL_UNDERGROUND = new RSArea(new RSTile(3432, 9892, 0), new RSTile(3443, 9902, 0));


    private static PriestInPeril quest;

    public static PriestInPeril get() {
        return quest == null ? quest = new PriestInPeril() : quest;
    }


    ArrayList<GEItem> itemsToBuy = new ArrayList<GEItem>(
            Arrays.asList(
                    new GEItem(ItemID.PURE_ESSENCE, 50, 30),
                    new GEItem(ItemID.EMPTY_BUCKET, 1, 500),
                    new GEItem(ItemID.ADAMANT_SCIMITAR, 1, 100),
                    new GEItem(ItemID.STAMINA_POTION[0], 3, 15),
                    new GEItem(ItemID.LOBSTER, 15, 30),
                    new GEItem(ItemID.VARROCK_TELEPORT, 10, 40),
                    new GEItem(ItemID.RING_OF_WEALTH[0], 1, 25)
            )
    );

    InventoryRequirement initialItemReqs = new InventoryRequirement(new ArrayList<>(
            Arrays.asList(
                    new ItemReq(ItemID.LOBSTER, 15, 2),
                    new ItemReq(ItemID.ADAMANT_SCIMITAR, 1, 1, true, true),
                    new ItemReq(ItemID.VARROCK_TELEPORT, 5, 1),
                    new ItemReq(ItemID.EMPTY_BUCKET, 1, 1),
                    new ItemReq(ItemID.STAMINA_POTION[0], 2, 0),
                    new ItemReq(ItemID.NECKLACE_OF_PASSAGE[0], 1, 0, true),
                    new ItemReq(ItemID.RING_OF_WEALTH[0], 1, 0, true)
            )
    ));

    BuyItemsStep buyStep = new BuyItemsStep(itemsToBuy);


    public void buyItems() {
        if (!initialItemReqs.check()) {
            cQuesterV2.status = "Buying Items";
            General.println("[Debug]: Buying Items");
            buyStep.buyItems();
        }
    }

    public void getItems1() {
        if (!initialItemReqs.check()) {
            cQuesterV2.status = "Getting Items";

            General.println("[Debug]: Getting Items");
            BankManager.open(true);
            BankManager.checkEquippedGlory();
            BankManager.depositAll(true);
            initialItemReqs.withdrawItems();
            BankManager.withdraw(1, true, IRON_KEY);
        }
    }

    public void getItems2() {
        cQuesterV2.status = "Getting Essence";
        General.println("[Debug]: Getting Items");
        PathingUtil.walkToTile(RunescapeBank.VARROCK_EAST.getPosition(), 2, false);
        BankManager.open(true);
        BankManager.checkEquippedGlory();
        BankManager.depositAll(true);
        BankManager.withdraw(25, true, PURE_ESSENCE);
        BankManager.getPotion(
                ItemID.STAMINA_POTION);
        BankManager.withdraw(3, true, VARROCK_TAB);
        BankManager.close(true);
    }

    public void goToKing() {
        cQuesterV2.status = "Going to King";
        PathingUtil.walkToArea(KING_RONALD_ROOM);
    }

    public void startQuest() {
        goToKing();
        cQuesterV2.status = "Starting Quest";
        if (KING_RONALD_ROOM.contains(Player.getPosition())) {
            if (NpcChat.talkToNPC("King Roald")) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation(); // need this
                NPCInteraction.handleConversation("I'm looking for a quest!"); // changed
                NPCInteraction.handleConversation("Yes."); // changed
                NPCInteraction.handleConversation();
            }
        }
    }

    public void step2() {
        if (!CHURCH_DOORS.contains(Player.getPosition())) {
            cQuesterV2.status = " Going to chruch";
            PathingUtil.walkToArea(CHURCH_DOORS);
        }

        cQuesterV2.status = "Talking at door";
        if (Utils.clickObject("Large door", "Open", false)) { // changed from knock-at
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation("Roald sent me to check on Drezel.");
            NPCInteraction.handleConversation("Sure. I'm a helpful person!"); // changed from "sure."
            NPCInteraction.handleConversation();

        }
    }


    public void step3() {
        if (NPCs.findNearest("Temple guardian").length < 1) {
            cQuesterV2.status = "Going to kill guardian";
            PathingUtil.walkToArea(DUNGEON_ENTRANCE);

            if (Utils.clickObject(1579, "Open", false))
                Timer.waitCondition(() -> Objects.findNearest(20, 1581).length > 0, 5000);

            if (Utils.clickObject(1581, "Climb-down", false)) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation();
                NPCInteraction.handleConversation("Yes."); // changed from "Yes"
                Timer.waitCondition(() -> NPCs.findNearest("Temple guardian").length > 0, 5000);
            }
        }
    }

    public void step4() {
        while (NPCs.findNearest("Temple guardian").length > 0) {
            General.sleep(100);
            if (Combat.getHPRatio() < General.random(35, 65)) {
                cQuesterV2.status = "Eating...";
                AccurateMouse.click(Inventory.find(LOBSTER)[0], "Eat");
                General.sleep(General.random(150, 300));
            }
            RSNPC[] guardian = NPCs.findNearest("Temple guardian");
            if (!Combat.isUnderAttack() && guardian.length > 0 && CombatUtil.clickTarget(guardian[0])) {
                Timer.slowWaitCondition(Combat::isUnderAttack, 4000, 6000);

            } else {
                cQuesterV2.status = "Idling...";
                General.sleep(General.random(1000, 2000));
            }
        }

        Utils.idle(3000, 8000); //idle after killing
    }

    public void step5() {
        goToKing();
        if (NpcChat.talkToNPC("King Roald")) {
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation();
            NPCInteraction.handleConversation();
        }
    }

    public void getGoldenKey() {
        if (Inventory.find(GOLDEN_KEY).length < 1 && !CHURCH_BOTTOM_FLOOR.contains(Player.getPosition())) {
            cQuesterV2.status = "Going to chruch";
            PathingUtil.walkToArea(CHURCH_BOTTOM_FLOOR);
        }
        if (Inventory.find(GOLDEN_KEY).length < 1 && CHURCH_BOTTOM_FLOOR.contains(Player.getPosition())) {
            if (GroundItems.find(GOLDEN_KEY).length > 0) {
                AccurateMouse.click(GroundItems.find(GOLDEN_KEY)[0], "Take");
                Timer.waitCondition(() -> Inventory.find(GOLDEN_KEY).length > 0, 5000, 8000);
            }
            RSNPC[] target = NPCs.findNearest(3486);
            if (target.length > 0 && !Combat.isUnderAttack() && Inventory.find(GOLDEN_KEY).length < 1) {
                cQuesterV2.status = "Priest in Peril: Attacking";
                if (AccurateMouse.click(target[0], "Attack"))
                    Timer.waitCondition(() -> Combat.isUnderAttack(), 5000, 8000);
                while (Combat.isUnderAttack()) {
                    General.sleep(100);
                    if (Combat.getHPRatio() < General.random(35, 65)) {
                        cQuesterV2.status = "Priest in Peril: Eating";
                        AccurateMouse.click(Inventory.find(LOBSTER)[0], "Eat");
                        General.sleep(General.random(150, 300));
                    } else {
                        cQuesterV2.status = "Priest in Peril: Idling";
                        General.sleep(General.random(1000, 2000));
                    }
                }
                if (GroundItems.find(GOLDEN_KEY).length > 0) {
                    AccurateMouse.click(GroundItems.find(GOLDEN_KEY)[0], "Take");
                    Timer.abc2WaitCondition(() -> Inventory.find(GOLDEN_KEY).length > 0, 5000, 8000);
                }
            }
        }
    }

    public void step6() {
        if (Inventory.find(LOBSTER).length < 8) {
            getItems1();
        }
        getGoldenKey();
    }

    public void goToJail() {
        if (!CHURCH_OUTSIDE_JAIL.contains(Player.getPosition())) {
            cQuesterV2.status = "Going to Drezel";
            if (!CHURCH_BOTTOM_FLOOR.contains(Player.getPosition()) && !CHURCH_SECOND_FLOOR.contains(Player.getPosition())) {
                PathingUtil.walkToArea(CHURCH_BOTTOM_FLOOR);
            }

            if (CHURCH_BOTTOM_FLOOR.contains(Player.getPosition())) {
                RSObject[] staircase = Objects.findNearest(20, "Staircase");
                if (staircase.length > 0) {
                    if (!staircase[0].isClickable())
                        staircase[0].adjustCameraTo();

                    if (AccurateMouse.click(staircase[0], "Climb-up"))
                        Timer.waitCondition(() -> CHURCH_SECOND_FLOOR.contains(Player.getPosition()), 8000, 12000);
                }
            }

            if (CHURCH_SECOND_FLOOR.contains(Player.getPosition())) {
                RSObject[] ladder = Objects.findNearest(20, "Ladder");
                if (ladder.length > 0) {
                    if (!ladder[0].isClickable())
                        ladder[0].adjustCameraTo();
                }
                if (Utils.clickObject("Ladder", "Climb-up", false))
                    Timer.abc2WaitCondition(() -> CHURCH_OUTSIDE_JAIL.contains(Player.getPosition()), 8000, 12000);
            }
        }
    }

    public void step7() {
        goToJail();
        if (CHURCH_OUTSIDE_JAIL.contains(Player.getPosition())) {
            if (Utils.clickNPC("Drezel", "Talk-to")) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation(); // kepp this
                NPCInteraction.handleConversation("So, what now?"); // changed from Tell me anyway.
                NPCInteraction.handleConversation("Yes, of course."); // changed from yes
                NPCInteraction.handleConversation();
            }
        }
    }



    public void getIronKey() {
        if (Inventory.find(IRON_KEY).length < 1 || Inventory.find(MURKY_WATER).length < 1) {
            if (Inventory.find(BUCKET).length < 1 && Inventory.find(MURKY_WATER).length < 1)
                getItems1();

            if (Inventory.find(GOLDEN_KEY).length < 1 && Inventory.find(IRON_KEY).length < 1)
                getGoldenKey();

            if (CHURCH_OUTSIDE_JAIL.contains(Player.getPosition())) {
                if (Utils.clickObject("Ladder", "Climb-down", false))
                    Timer.waitCondition(() -> CHURCH_SECOND_FLOOR.contains(Player.getPosition()), 8000, 12000);
            }

            if (CHURCH_SECOND_FLOOR.contains(Player.getPosition())) {
                if (Utils.clickObject("Staircase", "Climb-down", false))
                    Timer.waitCondition(() -> CHURCH_BOTTOM_FLOOR.contains(Player.getPosition()), 8000, 12000);
            }

            if (CHURCH_BOTTOM_FLOOR.contains(Player.getPosition())) {
                RSObject[] door = Objects.findNearest(20, "Large door");
                if (door.length > 0) {
                    Walking.blindWalkTo(door[0].getPosition());
                    Timer.waitCondition(() -> door[0].isClickable(), 8000, 12000);
                    if (Utils.clickObject("Large door", "Open", false))
                        Timer.abc2WaitCondition(() -> CHURCH_OUTSIDE_JAIL.contains(Player.getPosition()), 8000, 12000);
                }
            }

            PathingUtil.walkToArea(WELL_AREA);
            if (WELL_AREA.contains(Player.getPosition())) {
                RSObject[] well = Objects.findNearest(20, "Well");
                cQuesterV2.status = "Getting water";
                if (well.length > 0 && Inventory.find(BUCKET).length > 0) {
                    if (Utils.useItemOnObject(BUCKET, "Well"))
                        Timer.abc2WaitCondition(() -> Inventory.find(BUCKET).length < 1, 8000, 12000);
                }
                if (Inventory.find(GOLDEN_KEY).length > 0) {
                    cQuesterV2.status = "Getting Iron key";
                    RSObject[] monument = Objects.findNearest(25, "Monument");

                    for (int i = 0; i < monument.length; i++) {
                        General.sleep(100, 200);

                        if (!monument[i].isClickable())
                            monument[i].adjustCameraTo();

                        if (AccurateMouse.click(monument[i], "Study")) {
                            Timer.waitCondition(() -> Interfaces.get(272, 8) != null, 8000, 12000);
                            Utils.idle(500, 2000);
                        }
                        if (Interfaces.isInterfaceSubstantiated(272, 8)) {
                            if (Interfaces.get(272, 8).getComponentItem() == 2945) {

                                if (Interfaces.get(272, 1, 11).click())
                                    Timer.waitCondition(() -> Interfaces.get(272, 4) == null, 8000, 12000);

                                if (AccurateMouse.click(Inventory.find(GOLDEN_KEY)[0], "Use"))
                                    if (AccurateMouse.click(monument[i], "Use"))
                                        Timer.waitCondition(() -> Inventory.find(GOLDEN_KEY).length < 1, 8000, 12000);



                            } else if (Interfaces.get(272, 1, 11) != null) {
                                if (Interfaces.get(272, 1, 11).click())
                                    Timer.waitCondition(() -> Interfaces.get(272, 4) == null, 8000, 12000);
                            }
                        }
                        if (Inventory.find(GOLDEN_KEY).length < 1)
                            break;
                    }
                }
            }
        }
    }

    public void step9() {
        if (Inventory.find(IRON_KEY).length > 0 && Inventory.find(MURKY_WATER).length > 0) {
            goToJail();
            if (CHURCH_OUTSIDE_JAIL.contains(Player.getPosition())) {

                if (Utils.useItemOnObject(IRON_KEY, "Cell door")) {
                    NPCInteraction.waitForConversationWindow();
                    NPCInteraction.handleConversation();
                    Utils.shortSleep();
                }
                if (Utils.clickNPC("Drezel", "Talk-to")) {
                    NPCInteraction.waitForConversationWindow();
                    NPCInteraction.handleConversation();
                }
            }
        }
    }

    public void step10() {
        if (Inventory.find(BLESSED_WATER).length > 0) {
            goToJail();
            if (CHURCH_OUTSIDE_JAIL.contains(Player.getPosition())) {
                cQuesterV2.status = "Using water on coffin";
                if (Utils.useItemOnObject(BLESSED_WATER, "Coffin")) {
                    Timer.abc2WaitCondition(() -> Inventory.find(BUCKET).length > 0, 5000, 8000);
                }
            }
        }

        if (Utils.clickNPC("Drezel", "Talk-to")) {
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation();
            Utils.modSleep();
        }
    }

    public void step11() {
        goToJail();
        if (Utils.clickNPC("Drezel", "Talk-to")) {
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation();
            Utils.modSleep();
        }
    }

    public void step12() {
        if (Inventory.find(PURE_ESSENCE).length > 24) {
            if (!GATE_AREA.contains(Player.getPosition()) && !DREZEL_UNDERGROUND.contains(Player.getPosition())) {
                cQuesterV2.status = "Going to Drezel";
                PathingUtil.walkToArea(GATE_AREA);
            }
            RSObject[] gate = Objects.findNearest(20, "Gate");
            if (gate.length > 0) {
                if (!gate[0].isClickable())
                    gate[0].adjustCameraTo();

                if (AccurateMouse.click(gate[0], "Open"))
                    Timer.waitCondition(() -> DREZEL_UNDERGROUND.contains(Player.getPosition()), 9000, 12000);
            }

            if (DREZEL_UNDERGROUND.contains(Player.getPosition())) {
                cQuesterV2.status = "Giving Drezel essence";
                if (NpcChat.talkToNPC("Drezel")) {
                    NPCInteraction.waitForConversationWindow();
                    NPCInteraction.handleConversation();
                    NPCInteraction.handleConversation();
                }
            }
        } else {
            getItems2();
        }
    }

    int GAME_SETTING = 302;


    @Override
    public void execute() {
        if (Game.getSetting(302) == 0) {
            buyItems();
            getItems1();
            startQuest();
        } else if (Game.getSetting(302) == 1) {
            step2();
        } else if (Game.getSetting(302) == 2) {
            step3();
            step4();
        } else if (Game.getSetting(302) == 3) {
            step5();
        } else if (Game.getSetting(302) == 4) {
            step6();
            step7();
        } else if (Game.getSetting(302) == 5) {
            getIronKey();
            step9();
        } else if (Game.getSetting(302) == 6) {
            step10();
        } else if (Game.getSetting(302) == 7) {
            step11();
        } else if (Game.getSetting(302) == 8) {
            step12();
        } else if (Game.getSetting(302) == 10) {
            step12();
        } else if (Game.getSetting(302) == 35) {
            step12();
        }
        if (Game.getSetting(302) == 60) {
            Utils.closeQuestCompletionWindow();
            Utils.continuingChat();
            NPCInteraction.handleConversation();
            if (!NPCInteraction.isConversationWindowUp()) {
                if (NpcChat.talkToNPC("Drezel") && NPCInteraction.waitForConversationWindow())
                    NPCInteraction.handleConversation();
            }
        }
        if (Game.getSetting(302) >= 61) {
            cQuesterV2.taskList.remove(this);
        }

    }

    @Override
    public Priority priority() {
        return Priority.LOW;
    }

    @Override
    public boolean validate() {
        return cQuesterV2.taskList.get(0).equals(this);
    }

    @Override
    public String questName() {
        return "Priest in Peril";
    }

    @Override
    public boolean checkRequirements() {
        return true;
    }

    @Override
    public List<Requirement> getGeneralRequirements() {
        return null;
    }

    @Override
    public List<ItemRequirement> getBuyList() {
        return null;
    }
}
