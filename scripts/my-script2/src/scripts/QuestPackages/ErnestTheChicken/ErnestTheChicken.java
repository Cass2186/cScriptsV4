package scripts.QuestPackages.ErnestTheChicken;

import dax.walker.utils.AccurateMouse;
import dax.walker_engine.interaction_handling.NPCInteraction;
import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api2007.*;
import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSGroundItem;
import org.tribot.api2007.types.RSObject;
import org.tribot.api2007.types.RSTile;
import scripts.*;
import scripts.GEManager.GEItem;
import scripts.QuestPackages.AnimalMagnetism.AnimalMagnetism;
import scripts.QuestSteps.BuyItemsStep;
import scripts.QuestSteps.QuestTask;
import scripts.Requirements.AreaRequirement;
import scripts.Requirements.InventoryRequirement;
import scripts.Requirements.ItemReq;
import scripts.Requirements.VarbitRequirement;
import scripts.Tasks.Priority;

import java.util.ArrayList;
import java.util.Arrays;

public class ErnestTheChicken implements QuestTask {
    private static ErnestTheChicken quest;

    public static ErnestTheChicken get() {
        return quest == null ? quest = new ErnestTheChicken() : quest;
    }


    int SPADE = 952;
    int FISH_FOOD = 272;
    int POISONED_FISH_FOOD = 274;
    int POISON = 273;
    int PRESSURE_GUAGE = 271;
    int RUBBER_TUBE = 276;
    int OIL_CAN = 277;
    int KEY = 275;

    RSArea QUEST_START_AREA = new RSArea(new RSTile(3114, 3326, 0), new RSTile(3107, 3330, 0));
    RSArea PROFESSOR_ODDSTEIN_ROOM = new RSArea(new RSTile(3108, 3362, 2), new RSTile(3112, 3370, 2));
    RSArea FISH_FOOD_ROOM = new RSArea(new RSTile(3110, 3354, 1), new RSTile(3107, 3361, 1));

    RSArea POISON_ROOM = new RSArea(new RSTile(3101, 3364, 0), new RSTile(3097, 3366, 0));
    RSArea FOUNTAIN_AREA = new RSArea(new RSTile(3091, 3334, 0), new RSTile(3087, 3338, 0));
    RSArea COMPOST_AREA = new RSArea(new RSTile(3086, 3361, 0), new RSTile(3090, 3358, 0));
    RSArea TUBE_ROOM = new RSArea(new RSTile(3112, 3366, 0), new RSTile(3109, 3368, 0));
    RSArea WHOLE_BASEMENT = new RSArea(new RSTile(3118, 9744, 0), new RSTile(3090, 9768, 0));
    RSArea NE_ROOM = new RSArea(new RSTile(3112, 9758, 0), new RSTile(3105, 9767, 0));
    RSArea AFTER_DOOR2 = new RSArea(new RSTile(3104, 9758, 0), new RSTile(3100, 9762, 0));
    RSArea MAIN_ROOM = new RSArea(new RSTile(3100, 9757, 0), new RSTile(3118, 9750, 0));
    RSArea AFTER_DOOR4 = new RSArea(new RSTile(3099, 9758, 0), new RSTile(3096, 9762, 0));
    RSArea NW_ROOM = new RSArea(new RSTile(3099, 9763, 0), new RSTile(3096, 9767, 0));
    RSArea NORTH_CENTRE_ROOM = new RSArea(new RSTile(3104, 9763, 0), new RSTile(3100, 9767, 0));
    RSArea OIL_ROOM = new RSArea(new RSTile(3099, 9753, 0), new RSTile(3090, 9757, 0));


    RSArea manorGround1 = new RSArea(new RSTile(3097, 3354, 0), new RSTile(3119, 3373, 0));
    RSArea secretRoom = new RSArea(new RSTile(3090, 3354, 0), new RSTile(3096, 3363, 0));
    RSArea manorGround3 = new RSArea(new RSTile(3120, 3354, 0), new RSTile(3126, 3360, 0));
    RSArea firstFloor = new RSArea(new RSTile(3090, 3350, 1), new RSTile(3126, 3374, 1));
    RSArea secondFloor = new RSArea(new RSTile(3090, 3350, 2), new RSTile(3126, 3374, 2));
    RSArea basement = new RSArea(new RSTile(3090, 9745, 0), new RSTile(3118, 9767, 0));

    RSArea roomCD = new RSArea(new RSTile(3105, 9767, 0), new RSTile(3112, 9758, 0));
    RSArea emptyRoom = new RSArea(new RSTile(3100, 9762, 0), new RSTile(3104, 9758, 0));

    VarbitRequirement isLeverADown = new VarbitRequirement(1788, 1);
    VarbitRequirement isLeverBDown = new VarbitRequirement(1789, 1);
    VarbitRequirement isLeverCDown = new VarbitRequirement(1790, 1);
    VarbitRequirement isLeverDDown = new VarbitRequirement(1791, 1);
    VarbitRequirement isLeverEDown = new VarbitRequirement(1792, 1);
    VarbitRequirement isLeverFDown = new VarbitRequirement(1793, 1);
    VarbitRequirement isLeverAUp = new VarbitRequirement(1788, 0);
    VarbitRequirement isLeverBUp = new VarbitRequirement(1789, 0);
    VarbitRequirement isLeverCUp = new VarbitRequirement(1790, 0);
    VarbitRequirement isLeverDUp = new VarbitRequirement(1791, 0);
    VarbitRequirement isLeverEUp = new VarbitRequirement(1792, 0);
    VarbitRequirement isLeverFUp = new VarbitRequirement(1793, 0);
    AreaRequirement inBasement = new AreaRequirement(basement);
    AreaRequirement inRoomCD = new AreaRequirement(roomCD);
    AreaRequirement inEmptyRoom = new AreaRequirement(emptyRoom);

    RSTile A_LEVER = new RSTile(3108, 9745, 0);
    RSTile B_LEVER = new RSTile(3118, 9752, 0);
    RSTile C_LEVER = new RSTile(3111, 9759, 0);
    RSTile D_LEVER = new RSTile(3108, 9767, 0);
    RSTile E_LEVER = new RSTile(3097, 9767, 0);
    RSTile F_LEVER = new RSTile(3096, 9765, 0);
    RSTile DOOR2 = new RSTile(3105, 9760, 0);
    RSTile DOOR3 = new RSTile(3102, 9759, 0);
    RSTile DOOR4 = new RSTile(3100, 9760, 0);
    RSTile DOOR5 = new RSTile(3097, 9762, 0);
    RSTile DOOR6 = new RSTile(3099, 9765, 0);
    RSTile DOOR7 = new RSTile(3104, 9765, 0);
    RSTile DOOR8 = new RSTile(3102, 9763, 0);
    RSTile DOOR9 = new RSTile(3100, 9755, 0);


    public void getOilCan() {
        if (isLeverADown.check() && isLeverEDown.check()) {
            handleLever("Lever E");
            //pullUpE
        } else if (isLeverBDown.check() && isLeverCDown.check()
                && isLeverDDown.check()
                && isLeverFDown.check()
                && isLeverEDown.check()) {
            handleLever("Lever B");
            //pullUpLeverB
        } else if (isLeverDUp.check()
                && isLeverFDown.check()
                && isLeverEDown.check()) {
            handleLever("Lever D");
            //pullUpLeverD
        } else if (isLeverBUp.check()
                && isLeverCDown.check()
                && isLeverFUp.check()
                && inRoomCD.check()) {
            handleLever("Lever F");
            //pullDownLeverF
        } else if (isLeverFDown.check()
                && isLeverEDown.check()
                && isLeverCDown.check()) {
            handleLever("Lever E");
            //pullUpLeverE
        } else if (isLeverFDown.check() && isLeverEUp.check() && isLeverCDown.check()) {

            if (handleDoor(141, OIL_ROOM))
                Utils.clickGroundItem(OIL_CAN);

            if (Inventory.find(OIL_CAN).length > 0 &&
                    handleDoor(DOOR9, MAIN_ROOM)) {
                Walking.blindWalkTo(new RSTile(3116, 9753, 0));
                Timer.waitCondition(() -> Objects.findNearest(30, "Ladder")[0].getPosition().distanceTo(Player.getPosition()) < 5, 8000, 12000);
                if (Utils.clickObj("Ladder", "Climb-up"))
                    Timer.waitCondition(() -> !inBasement.check(), 4000, 6500);
            }

        } else if (isLeverFDown.check()
                && isLeverEDown.check()) {
            handleLever("Lever C");
            //pullDownLeverC
        } else if (inRoomCD.check()
                && isLeverCDown.check()) {
            handleLever("Lever C");
            //pullUpLeverC
        } else if ((inRoomCD.check() || inEmptyRoom.check())
                && isLeverDUp.check()) {
            handleLever("Lever D");
            //pullDownLeverD
        } else if (isLeverBDown.check()
                && isLeverDDown.check()) {
            handleLever("Lever B");
            //pullUpLeverB
        } else if (isLeverADown.check()
                && isLeverDDown.check()) {
            handleLever("Lever A");
            //pullUpLeverA
        } else if (isLeverDDown.check()
                && isLeverEDown.check()) {
            handleLever("Lever F");
            //pullDownLeverF
        } else if (isLeverDDown.check()) {
            handleLever("Lever E");
            //pullDownLeverE
        } else if (isLeverADown.check()
                && isLeverBDown.check()) {
            handleLever("Lever D");
            //pullDownLeverD
        } else if (isLeverADown.check()) {
            handleLever("Lever B");
            //pullDownLeverB
        } else if (inBasement.check()) {
            handleLever("Lever A");
            //pullDownLeverA
        }

    }

    ArrayList<GEItem> itemsToBuy = new ArrayList<GEItem>(
            Arrays.asList(
                    new GEItem(ItemId.SPADE, 1, 300),
                    new GEItem(ItemId.STAMINA_POTION[0], 3, 15),
                    new GEItem(ItemId.AMULET_OF_GLORY[2], 2, 15),
                    new GEItem(ItemId.RING_OF_WEALTH[0], 1, 25)
            )
    );

    InventoryRequirement initialItemReqs = new InventoryRequirement(new ArrayList<>(
            Arrays.asList(
                    new ItemReq(ItemId.STAMINA_POTION[0], 3, 0),
                    new ItemReq(ItemId.AMULET_OF_GLORY[2], 2, 0),
                    new ItemReq(ItemId.SPADE, 1, 1),
                    new ItemReq(ItemId.RING_OF_WEALTH[0], 1, 0, true)
            )
    ));

    BuyItemsStep buyStep = new BuyItemsStep(itemsToBuy);


    public void buyItems() {
        cQuesterV2.status = "Ernest The Chicken: Buying Items";
        General.println("[Debug]: " + cQuesterV2.status);
        if (!initialItemReqs.check())
            buyStep.buyItems();
    }

    public void getItems() {
        if (Inventory.find(SPADE).length < 1) {
            cQuesterV2.status = "Ernest The Chicken: Getting Items";
            General.println("[Debug]: " + cQuesterV2.status);
            BankManager.open(true);
            BankManager.depositAll(true);
            BankManager.depositEquipment();
            BankManager.checkEquippedGlory();
            BankManager.checkCombatBracelet();
            BankManager.withdraw(1, true, SPADE);
            BankManager.withdraw(2, true, ItemId.STAMINA_POTION[0]);
            BankManager.withdraw(2, true, ItemId.AMULET_OF_GLORY[0]);
            BankManager.close(true);
        }
    }

    public void startQuest() {
        cQuesterV2.status = "Starting Quest";
        General.println("[Debug]: " + cQuesterV2.status);
        PathingUtil.walkToArea(QUEST_START_AREA);
        if (QUEST_START_AREA.contains(Player.getPosition())) {
            NpcChat.talkToNPC("Veronica");
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation();
            NPCInteraction.handleConversation("Aha, sounds like a quest. I'll help.");
            NPCInteraction.handleConversation();
        }
    }

    public void step1() {
        cQuesterV2.status = "Going to professor Oddstein";
        General.println("[Debug]: " + cQuesterV2.status);
        PathingUtil.walkToArea(PROFESSOR_ODDSTEIN_ROOM);
        if (NpcChat.talkToNPC("Professor Oddenstein")) {
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation("I'm looking for a guy called Ernest.");
            NPCInteraction.handleConversation("Change him back this instant!");
            NPCInteraction.handleConversation();
        }
    }

    public void pressureGuage() {
        if (Inventory.find(PRESSURE_GUAGE).length < 1) {
            if (Inventory.find(POISONED_FISH_FOOD).length < 1 && Inventory.find(FISH_FOOD).length < 1) {
                cQuesterV2.status = "Ernest: Getting Fish food";
                General.println("[Debug]: " + cQuesterV2.status);
                if (!FISH_FOOD_ROOM.contains(Player.getPosition())) {
                    PathingUtil.walkToTile(new RSTile(3108, 3356, 1));
                    Timer.waitCondition(()-> FISH_FOOD_ROOM.contains(Player.getPosition()), 9000, 12000);
                }

                if (FISH_FOOD_ROOM.contains(Player.getPosition())) {
                    RSGroundItem[] fishfood = GroundItems.find(FISH_FOOD);
                    if (fishfood.length > 0) {
                        if (!fishfood[0].isClickable())
                            fishfood[0].adjustCameraTo();

                        if (AccurateMouse.click(fishfood[0], "Take"))
                            Timer.abc2WaitCondition(() -> Inventory.find(FISH_FOOD).length > 0, 12000, 16000);
                    }
                }
            }
            if (Inventory.find(POISONED_FISH_FOOD).length < 1 && Inventory.find(POISON).length < 1 && Inventory.find(FISH_FOOD).length > 0) {
                cQuesterV2.status = "Getting Poison";
                General.println("[Debug]: " + cQuesterV2.status);
                PathingUtil.walkToArea(POISON_ROOM);
                if (POISON_ROOM.contains(Player.getPosition())) {
                    RSGroundItem[] poison = GroundItems.find(POISON);
                    if (poison.length > 0) {
                        if (!poison[0].isClickable())
                            poison[0].adjustCameraTo();

                        if (AccurateMouse.click(poison[0], "Take"))
                            Timer.abc2WaitCondition(() -> Inventory.find(POISON).length > 0, 12000, 16000);
                    }
                }
            }
            if (Inventory.find(POISONED_FISH_FOOD).length < 1 && Inventory.find(PRESSURE_GUAGE).length < 1) {
                if (Inventory.find(FISH_FOOD).length > 0 && Inventory.find(POISON).length > 0) {
                    cQuesterV2.status = "Making poisoned fish food";
                    General.println("[Debug]: " + cQuesterV2.status);
                    if (Utils.useItemOnItem(FISH_FOOD, POISON))
                        Timer.abc2WaitCondition(() -> Inventory.find(POISONED_FISH_FOOD).length > 0, 6000, 9000);
                }
            }
            if (Inventory.find(POISONED_FISH_FOOD).length > 0 && Inventory.find(PRESSURE_GUAGE).length < 1) {
                cQuesterV2.status = "Going to fountain";
                General.println("[Debug]: " + cQuesterV2.status);
                PathingUtil.walkToArea(FOUNTAIN_AREA);

                cQuesterV2.status = "Poisoning Fish";
                General.println("[Debug]: " + cQuesterV2.status);
                RSObject[] fountain = Objects.findNearest(20, "Fountain");
                if (fountain.length > 0) {
                    if (!fountain[0].isClickable())
                        fountain[0].adjustCameraTo();

                    if (Utils.useItemOnObject(POISONED_FISH_FOOD, "Fountain")) {
                        Timer.abc2WaitCondition(() -> Inventory.find(POISONED_FISH_FOOD).length < 1, 12000, 16000);
                        Utils.modSleep();
                    }
                    if (Utils.clickObj("Fountain", "Search")) {
                        NPCInteraction.waitForConversationWindow();
                        NPCInteraction.handleConversation();
                        Utils.modSleep();
                    }
                }
            }
        }

    }

    public void getRubberTube() {
        if (Inventory.find(RUBBER_TUBE).length < 1) {
            if (Inventory.find(KEY).length < 1) {
                cQuesterV2.status = "Getting Door key";
                General.println("[Debug]: " + cQuesterV2.status);
                PathingUtil.walkToArea(COMPOST_AREA);
                if (AccurateMouse.click(Objects.findNearest(20, "Compost heap")[0], "Search"))
                    Timer.abc2WaitCondition(() -> Inventory.find(KEY).length > 0, 10000, 15000);

            }
            if (Inventory.find(KEY).length > 0) {
                cQuesterV2.status = "Getting tube";
                General.println("[Debug]: " + cQuesterV2.status);
                PathingUtil.walkToArea(TUBE_ROOM);
                RSGroundItem[] tube = GroundItems.findNearest(RUBBER_TUBE);
                if (tube.length > 0) {
                    if (AccurateMouse.click(tube[0], "Take"))
                        Timer.abc2WaitCondition(() -> Inventory.find(RUBBER_TUBE).length > 0, 10000, 15000);
                }
            }
        }
    }

    public void getOilCanOld() {
        if (Inventory.find(OIL_CAN).length < 1) {
            if (!WHOLE_BASEMENT.contains(Player.getPosition())) {
                cQuesterV2.status = "Getting Oil can";
                General.println("[Debug]: " + cQuesterV2.status);
                PathingUtil.walkToTile(B_LEVER, 2, false);
                Timer.abc2WaitCondition(() -> WHOLE_BASEMENT.contains(Player.getPosition()), 14000, 18000);

            }
            if (WHOLE_BASEMENT.contains(Player.getPosition())) {
                if (Game.getSetting(33) == 0) {
                    handleLever(B_LEVER, "Lever B"); // game setting 33 changes from 0 to 4
                }
                if (Game.getSetting(33) == 4) {
                    handleLever(A_LEVER, "Lever A");
                }
                if (Game.getSetting(33) == 6) {
                    handleDoor(144, NE_ROOM);
                    handleLever(D_LEVER, "Lever D");
                }
                if (Game.getSetting(33) == 22) {
                    if (NE_ROOM.contains(Player.getPosition()))
                        handleDoor(139, AFTER_DOOR2);

                    if (AFTER_DOOR2.contains(Player.getPosition()))
                        handleDoor(145, MAIN_ROOM);

                    if (MAIN_ROOM.contains(Player.getPosition()))
                        handleLever(B_LEVER, "Lever B");// game setting 33 changes from 22 to 18
                }
                if (Game.getSetting(33) == 18) {
                    handleLever(A_LEVER, "Lever A");
                }
                if (Game.getSetting(33) == 16) {
                    handleDoor(145, AFTER_DOOR2);
                    handleDoor(140, AFTER_DOOR4);
                    handleDoor(143, NW_ROOM);
                    handleLever(E_LEVER, "Lever E");
                }
                if (Game.getSetting(33) == 48) {
                    handleLever(F_LEVER, "Lever F");// game setting 33 changes from 48 to 112
                }
                if (Game.getSetting(33) == 112) {
                    handleDoor(138, NORTH_CENTRE_ROOM);
                    handleDoor(137, NE_ROOM);
                    Utils.modSleep();
                    handleLever(C_LEVER, "Lever C");

                }
                if (Game.getSetting(33) == 120) {// game setting 33 changes from 112 to 120
                    handleDoor(137, NORTH_CENTRE_ROOM);
                    if (NORTH_CENTRE_ROOM.contains(Player.getPosition())) {
                        handleDoor(138, NW_ROOM);
                        handleLever(E_LEVER, "Lever E"); // game setting 33 changes from 120 to 88
                    }
                }
                if (Game.getSetting(33) == 88) {
                    handleDoor(138, NORTH_CENTRE_ROOM);

                    if (NORTH_CENTRE_ROOM.contains(Player.getPosition()))
                        handleDoor(142, AFTER_DOOR2);

                    if (AFTER_DOOR2.contains(Player.getPosition()))
                        handleDoor(145, MAIN_ROOM);

                    if (MAIN_ROOM.contains(Player.getPosition()))
                        handleDoor(141, OIL_ROOM);
                    if (AccurateMouse.click(GroundItems.find(OIL_CAN)[0], "Take")) {
                        Timer.waitCondition(() -> Inventory.find(OIL_CAN).length > 0, 8000, 10000);
                        handleDoor(DOOR9, MAIN_ROOM);
                        Walking.blindWalkTo(new RSTile(3116, 9753, 0));
                        Timer.waitCondition(() -> Objects.findNearest(30, "Ladder")[0].getPosition().distanceTo(Player.getPosition()) < 5, 8000, 12000);
                        if (Utils.clickObj("Ladder", "Climb-up"))
                            General.sleep(General.random(2000, 4000));
                    }
                }
            }
        }
    }

    public void finishQuest() {
        if (BankManager.checkInventoryItems(OIL_CAN)) {
            cQuesterV2.status = "Ernest: Finishing quest";
            General.println("[Debug]: " + cQuesterV2.status);
            PathingUtil.walkToArea(PROFESSOR_ODDSTEIN_ROOM);
            if (PROFESSOR_ODDSTEIN_ROOM.contains(Player.getPosition())) {
                NpcChat.talkToNPC("Professor Oddenstein");
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation();
                General.sleep(General.random(3000, 5000));
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation();
            }
        }
    }

    public void handleLever(String leverName) {
        int i = Game.getSetting(33);
        General.println("[Debug]: Game setting 33 is " + i);
        //PathingUtil.localNavigation(leverTile);
        RSObject[] lever = Objects.findNearest(20, leverName);
        if (lever.length > 0) {
            Walking.blindWalkTo(lever[0].getPosition());
            if (Timer.waitCondition(() -> Objects.findNearest(20, leverName)[0].isClickable(), 9000, 12000))
                Utils.microSleep();
            if (AccurateMouse.click(lever[0], "Pull") &&
                    Timer.waitCondition(() -> Player.getAnimation() != -1, 7000, 10000))
                Timer.waitCondition(() -> Player.getAnimation() == -1, 2000, 4000);
        }
    }


    public void handleLever(RSTile leverTile, String leverName) {
        int i = Game.getSetting(33);
        General.println("[Debug]: Game setting 33 is " + i);
        //PathingUtil.localNavigation(leverTile);
        RSObject[] lever = Objects.findNearest(20, leverName);
        if (lever.length > 0) {
            Walking.blindWalkTo(lever[0].getPosition());
            if (Timer.waitCondition(() -> Objects.findNearest(20, leverName)[0].isClickable(), 9000, 12000))
                Utils.microSleep();
            if (AccurateMouse.click(lever[0], "Pull"))
                if (Timer.waitCondition(() -> Player.getAnimation() != -1, 7000, 10000))
                    Timer.waitCondition(() -> Player.getAnimation() == -1, 5000, 8000);
        }
    }

    public boolean handleDoor(RSTile doorTile, RSArea arrivalArea) {
        if (!arrivalArea.contains(Player.getPosition())) {
            Walking.blindWalkTo(doorTile);
            Timer.waitCondition(() -> Objects.findNearest(20, "Door")[0].getPosition().distanceTo(Player.getPosition()) < 1, 6000);
            General.sleep(General.random(800, 1600));
            if (AccurateMouse.click(Objects.findNearest(20, "Door")[0], "Open")) {
                return Timer.slowWaitCondition(() -> arrivalArea.contains(Player.getPosition()), 12000, 14000);
            }
        }
        return arrivalArea.contains(Player.getPosition());
    }

    public boolean handleDoor(int doorId, RSArea arrivalArea) {
        if (!arrivalArea.contains(Player.getPosition())) {
            RSObject[] door = Objects.findNearest(30, doorId);
            if (door.length > 0) {
                Utils.blindWalkToTile(door[0].getPosition());
                Utils.microSleep();

                if (!door[0].isClickable())
                    door[0].adjustCameraTo();

                if (AccurateMouse.click(door[0], "Open"))
                    return Timer.slowWaitCondition(() -> arrivalArea.contains(Player.getPosition()), 12000, 14000);
            }
        }
        return arrivalArea.contains(Player.getPosition());
    }

    int GAME_SETTING = 32;

    @Override
    public void execute() {
        if (Game.getSetting(32) == 0) {
            buyItems();
            getItems();
            startQuest();
        }
        if (Game.getSetting(32) == 1) {
            step1();
        }
        if (Game.getSetting(32) == 2) {
            pressureGuage();
            getRubberTube();
            getOilCan();
            finishQuest();
        }
        if (Game.getSetting(32) == 3) {
            Utils.closeQuestCompletionWindow();
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
        return "Ernest the chicken";
    }

    @Override
    public boolean checkRequirements() {
        return true;
    }
}
