package scripts.QuestPackages.TheGolem;

import dax.walker.utils.AccurateMouse;
import dax.walker_engine.interaction_handling.NPCInteraction;
import org.tribot.api.General;
import org.tribot.api2007.*;
import org.tribot.api2007.types.*;
import org.tribot.script.sdk.Quest;
import scripts.*;
import scripts.GEManager.GEItem;
import scripts.QuestPackages.TheFeud.TheFeud;
import scripts.QuestSteps.BuyItemsStep;
import scripts.QuestSteps.QuestTask;
import scripts.Requirements.ItemRequirement;
import scripts.Requirements.Requirement;
import scripts.Tasks.Priority;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TheGolem implements QuestTask {

    private static TheGolem quest;

    public static TheGolem get() {
        return quest == null ? quest = new TheGolem() : quest;
    }

    public int waterskin4 = 1823; // need 6
    public int desertRobe = 1835;
    public int desertBoots = 1837;
    int DESERT_SHIRT = 1833;
    int SHANTAY_PASS = 1854; //need 10
    int HAMMER = 2347;
    int vial = 229;
    int pestle = 233;
    int papyrus = 970;
    int softClay = 1761;
    int pheonixFeather = 4621;
    int varrockTab = 8007;
    int chisel = 1755;
    int passage5 = 21146;
    int digsiteTele = 12403;
    int letter = 4615;
    int blackMushroom = 4620;
    int strangeImplement = 4619;
    int expeditionNotes = 4616;
    int displayKey = 4617;
    int statuette = 4618;
    int blackMushroomInk = 4622;
    int pheonixQuillPen = 4623;
    int LOGS = 1511;

    // ******* Areas *********//
    RSArea FEATHER_AREA = new RSArea(new RSTile(3400, 3159, 0), new RSTile(3430, 3147, 0));
    RSArea START_AREA = new RSArea(new RSTile(3487, 3095, 0), new RSTile(3490, 3084, 0));
    RSArea LETTER_AREA = new RSArea(new RSTile(3481, 3089, 0), new RSTile(3476, 3093, 0));
    RSArea DIGSITE_AREA = new RSArea(new RSTile(3370, 3435, 0), new RSTile(3379, 3431, 0));
    RSArea EXAM_CENTRE_AREA = new RSArea(new RSTile(3348, 3337, 0), new RSTile(3367, 3332, 0));
    RSArea MUSEUM_AREA = new RSArea(new RSTile(3253, 3447, 0), new RSTile(3267, 3455, 0));
    RSArea STATUE_PLACEMENT_AREA = new RSArea(new RSTile(2725, 4894, 0), new RSTile(2718, 4901, 0));
    RSArea LARGE_MUSEUM_AREA = new RSArea(new RSTile(3251, 3447, 0), new RSTile(3267, 3455, 0));

    public boolean trainFletch = false;

    RSGroundItem[] groundLetter = GroundItems.find(4615);

    public boolean checkLevel() {
        if (Skills.getActualLevel(Skills.SKILLS.FLETCHING) < 10) {
            General.println("[Debug]: Fletching must be level 10 for The Golem");
            trainFletch = true;
        }
        if (Skills.getActualLevel(Skills.SKILLS.THIEVING) < 25) {
            General.println("[Debug]: Thieving must be level 25 for The Golem");
            return false;
        }
        return true;
    }

    ArrayList<GEItem> itemsToBuy = new ArrayList<GEItem>(
            Arrays.asList(
                    new GEItem(ItemID.SHANTAY_PASS, 15, 200),
                    new GEItem(ItemID.VIAL, 1, 500),
                    new GEItem(ItemID.PESTLE_AND_MORTAR, 1, 500),
                    new GEItem(ItemID.PAPYRUS, 1, 300),
                    new GEItem(ItemID.SOFT_CLAY, 4, 60),
                    new GEItem(ItemID.PAPYRUS, 1, 300),
                    new GEItem(ItemID.DESERT_BOOTS, 1, 500),
                    new GEItem(ItemID.DESERT_ROBE, 1, 500),
                    new GEItem(ItemID.DESERT_SHIRT, 1, 500),
                    new GEItem(ItemID.WATERSKIN[0], 6, 500),

                    new GEItem(ItemID.HAMMER, 1, 200),
                    new GEItem(ItemID.CHISEL, 1, 500),
                    new GEItem(ItemID.STAFF_OF_AIR, 1, 50),
                    new GEItem(ItemID.NECKLACE_OF_PASSAGE5, 1, 50),
                    new GEItem(ItemID.VARROCK_TELEPORT, 5, 50),
                    new GEItem(ItemID.AMULET_OF_GLORY[2], 2, 30),
                    new GEItem(ItemID.STAMINA_POTION[0], 3, 15),
                    new GEItem(ItemID.RING_OF_WEALTH[0], 1, 25)
            )
    );

    public void buyItems() {
        cQuesterV2.status = "Buying Items";
        General.println("[Debug]: " + cQuesterV2.status);
        if (Skills.getCurrentLevel(Skills.SKILLS.FLETCHING) < 10) {
            itemsToBuy.add(new GEItem(ItemID.LOGS, 100, 50));
            itemsToBuy.add(new GEItem(ItemID.KNIFE, 1, 500));
        }
        BuyItemsStep buyStep = new BuyItemsStep(itemsToBuy);
        buyStep.buyItems();
    }


    public void getItems() {
        cQuesterV2.status = "Getting Items";
        General.println("[Debug]: Getting Items");
        BankManager.open(true);
        BankManager.depositAll(true);
        BankManager.checkEquippedGlory();
        BankManager.withdraw2(1, true, pestle);
        BankManager.withdraw2(1, true, papyrus);
        BankManager.withdraw2(1, true, desertBoots);
        BankManager.withdraw2(1, true, desertRobe);
        BankManager.withdraw2(1, true, DESERT_SHIRT);
        BankManager.withdraw2(3, true, waterskin4);
        BankManager.withdraw2(1, true,
                ItemID.AMULET_OF_GLORY[0]);
        BankManager.withdraw2(1, true, HAMMER);
        BankManager.withdraw2(1, true, chisel);
        BankManager.withdraw2(4, true, softClay);
        BankManager.withdraw2(1, true, passage5);
        BankManager.withdraw2(5, true, SHANTAY_PASS);
        BankManager.withdraw2(1, true, pheonixFeather);
        BankManager.withdraw2(5, true, varrockTab);
        BankManager.withdraw2(3, true, BankManager.STAMINA_POTION[0]);
        Utils.equipItem(desertRobe);
        Utils.equipItem(DESERT_SHIRT);
        Utils.equipItem(desertBoots);
        BankManager.close(true);
    }


    public void trainFletching() {
        cQuesterV2.status = "Training Fletching to 10";
        General.println("[Debug]: " + cQuesterV2.status);
        if (Interfaces.get(270, 14) != null) {
            Interfaces.get(270, 14).click();
        }
    }

    public void getFeather() {
        while (Inventory.find(pheonixFeather).length < 1) {
            General.sleep(100);
            cQuesterV2.status = "Getting feather";
            if (Inventory.find(passage5).length > 0 && !FEATHER_AREA.contains(Player.getPosition())) {
                if (AccurateMouse.click(Inventory.find(passage5)[0], "Rub")) {
                    Timer.waitCondition(() -> Interfaces.get(219, 1, 2) != null, 5000, 8000);

                    if (Interfaces.get(219, 1, 2) != null)
                        if (Interfaces.get(219, 1, 3).click())
                            Timer.abc2WaitCondition(() -> FEATHER_AREA.contains(Player.getPosition()), 8000, 12000);
                }
            }

            if (FEATHER_AREA.contains(Player.getPosition())) {
                RSNPC[] pheonix = NPCs.findNearest(5137);
                if (pheonix.length > 0) {

                    if (!pheonix[0].isClickable())
                        PathingUtil.walkToTile(pheonix[0].getPosition());

                    if (AccurateMouse.click(pheonix[0], "Grab-feather"))
                        Timer.abc2WaitCondition(() -> Inventory.find(4621).length > 0, 8000, 10000);
                }
            }
        }
    }

    public void startQuest() {
        if (Inventory.find(pheonixFeather).length > 0) {
            PathingUtil.walkToArea(START_AREA);

            if (NpcChat.talkToNPC("Broken clay golem")) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation("Yes.");
                NPCInteraction.handleConversation("Shall I try to repair you?", "Yes.");
                NPCInteraction.handleConversation();
            }

            if (Utils.useItemOnNPC(softClay, 6277)) {
                General.sleep(General.random(3000, 5000));
            }
        }
    }

    public void repairGolem() {
        PathingUtil.walkToArea(START_AREA);

        if (Utils.useItemOnNPC(softClay, 6277)) {
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation();
            General.sleep(General.random(500, 3000));
        }
    }

    public void talkToGolem() {
        PathingUtil.walkToArea(START_AREA);

        if (NpcChat.talkToNPC("Clay golem")) {
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation();
            NPCInteraction.handleConversation("How do I open the portal?");
            NPCInteraction.handleConversation();
        }
    }

    public void getLetter() {
        if (Inventory.find(letter).length < 1) {
            cQuesterV2.status = "Getting letter";
            PathingUtil.walkToArea(LETTER_AREA);

            groundLetter = GroundItems.find(4615);
            if (groundLetter.length > 0) {

                if (!groundLetter[0].isClickable())
                    groundLetter[0].adjustCameraTo();

                if (AccurateMouse.click(groundLetter[0], "Take"))
                    Timer.abc2WaitCondition(() -> Inventory.find(4615).length > 0, 8000, 12000);

            }
            if (Inventory.find(letter).length > 0) {
                if (AccurateMouse.click(Inventory.find(letter)[0], "Read")) {
                    Timer.waitCondition(() -> Interfaces.get(220, 17) != null, 8000, 12000);
                    Utils.idle(500, 2500);
                }
            }
            if (Interfaces.get(220, 17) != null) {
                Interfaces.get(220, 17).click();
                Utils.idle(500, 2500);
            }
        }
    }

    public void getMushroom() {
        cQuesterV2.status = "Getting Mushroom";
        if (Inventory.find(4620).length < 2) {
            PathingUtil.walkToTile(new RSTile(2728, 4889, 0));
            RSObject[] blackMushroom = Objects.findNearest(20, 6311);
            if (blackMushroom.length > 0) {
                if (AccurateMouse.click(blackMushroom[0], "Pick"))
                    Timer.waitCondition(() -> Inventory.find(4620).length > 0, 8000, 10000);
            }
            blackMushroom = Objects.findNearest(20, 6311);
            if (blackMushroom.length > 0) {
                if (AccurateMouse.click(blackMushroom[0], "Pick"))
                    Timer.waitCondition(() -> Inventory.find(4620).length > 1, 8000, 10000);
            }
        }
    }

    public void getStrangeImplement() {
        cQuesterV2.status = "Getting Strange implement";
        General.println("[Debug}: " + cQuesterV2.status);
        if (Inventory.find(strangeImplement).length < 1) {

            PathingUtil.walkToTile(new RSTile(2713, 4913, 0));

            RSGroundItem[] strangeImplementGround = GroundItems.find(4619);
            if (strangeImplementGround.length > 0) {
                if (AccurateMouse.click(strangeImplementGround[0], "Take"))
                    Timer.waitCondition(() -> Inventory.find(4619).length > 0, 8000, 10000);
            }

            if (Inventory.find(4619).length < 1) {
                if (AccurateMouse.click(strangeImplementGround[0], "Take"))
                    Timer.waitCondition(() -> Inventory.find(4619).length > 0, 8000, 10000);
            }
        }
    }

    public void goToDigSite() {
        if (Inventory.find(strangeImplement).length > 0) {
            cQuesterV2.status = "Going to digsite";
            PathingUtil.walkToArea(DIGSITE_AREA);
            if (NpcChat.talkToNPC("Elissa")) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation("I found a letter in the desert with your name on.");
                NPCInteraction.handleConversation();
            }
        }
    }

    public void examCentre() {
        if (Inventory.find(expeditionNotes).length < 1) {
            cQuesterV2.status = "Going to Exam Centre";
            General.println("[Debug}: " + cQuesterV2.status);
            PathingUtil.walkToArea(EXAM_CENTRE_AREA);
            if (Utils.clickObj(6292, "Search")) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation();
            }
            RSItem[] invBook = Inventory.find(4616);
            if (invBook.length > 0 && invBook[0].click("Read"))
                Timer.waitCondition(() -> Interfaces.isInterfaceSubstantiated(49, 111), 8000, 10000);

            RSInterface inter = Interfaces.findWhereAction("Close", 49);
            if (inter != null && inter.click()) {
                Utils.shortSleep();
            }
        }
    }


    public void museum() {
        if (Interfaces.get(49, 111) != null) {
            Interfaces.get(49, 111).click();
            Utils.modSleep();
        }
        if (Inventory.find(expeditionNotes).length > 0) {
            cQuesterV2.status = "Going to museum";
            General.println("[Debug}: " + cQuesterV2.status);
            PathingUtil.walkToArea(MUSEUM_AREA);
            if (LARGE_MUSEUM_AREA.contains(Player.getPosition())) {
                if (NpcChat.talkToNPC(5214)) {
                    NPCInteraction.waitForConversationWindow();
                    NPCInteraction.handleConversation("I'm looking for a statuette recovered from the city of Uzer.");
                    NPCInteraction.handleConversation();
                    Utils.shortSleep();
                }
            }
        }
    }

    public void pickpocket() {
        if (Inventory.find(displayKey).length < 1) {
            cQuesterV2.status = "Going to museum";
            PathingUtil.walkToArea(MUSEUM_AREA);

            cQuesterV2.status = "Getting display key";

            RSNPC[] haigHalen = NPCs.findNearest(5214);
            if (NpcChat.talkToNPC(5214, "Pickpocket"))
                Timer.abc2WaitCondition(() -> Inventory.find(displayKey).length > 0, 8000, 12000);

        }
    }

    RSArea DISPLAY_CASE_AREA = new RSArea(new RSTile(3257, 3454, 1), 2);

    public void goToDisplayCase() {
        cQuesterV2.status = "Getting Golem From Case";
        PathingUtil.walkToArea(DISPLAY_CASE_AREA);
        if (Utils.clickObj(24626, "Open")) {
            Timer.waitCondition(() -> Inventory.find(statuette).length > 0, 5000, 8000);
            Utils.idle(500, 2500);
        }
    }


    public void getItems2() {
        if (Inventory.find(blackMushroomInk).length < 1) {
            cQuesterV2.status = "Getting Items";
            General.println("[Debug]: Getting Items");
            BankManager.open(true);
            BankManager.depositAll(true);
            BankManager.checkEquippedGlory();
            if (Inventory.getAll().length > 0) {
                BankManager.depositAll(true);
            }
            BankManager.withdraw(1, true, vial);
            BankManager.withdraw(1, true, pestle);
            BankManager.withdraw(1, true, papyrus);
            BankManager.withdraw(3, true, waterskin4);
            BankManager.withdraw(1, true, ItemID.AMULET_OF_GLORY[0]);
            BankManager.withdraw(1, true, blackMushroom);
            BankManager.withdraw(1, true, chisel);
            BankManager.withdraw(1, true, strangeImplement);
            BankManager.withdraw(1, true, HAMMER);
            BankManager.withdraw(5, true, SHANTAY_PASS);
            BankManager.withdraw(1, true, pheonixFeather);
            BankManager.withdraw(1, true, statuette);
            BankManager.withdraw(1, true, blackMushroomInk);
            BankManager.withdraw(1, true, BankManager.STAMINA_POTION[0]);
            Utils.equipItem(desertRobe);
            Utils.equipItem(DESERT_SHIRT);
            Utils.equipItem(desertBoots);
            BankManager.close(true);
        }
    }

    public void returnToUzer() {
        cQuesterV2.status = "Returning to Uzer";
        General.println("[Debug]: " + cQuesterV2.status);
        PathingUtil.walkToArea(STATUE_PLACEMENT_AREA);

        if (Utils.useItemOnObject(statuette, 6306)) {
            Timer.abc2WaitCondition(() -> Inventory.find(statuette).length < 1, 6000, 10000);
        }

        if (PathingUtil.walkToTile(new RSTile(2718, 4899, 0)))
            General.sleep(General.random(500, 4000));

       // if (Utils.clickObj(6365, "Turn"))
        //    General.sleep(1500, 2200);
    }

    public void turnStatuette1() {
        if (PathingUtil.walkToTile(new RSTile(2725, 4896, 0)))
            PathingUtil.movementIdle();

        if (Utils.clickObj(6306, "Turn"))
            General.sleep(1500, 2200);
    }

    public void turnStatuette2() {
        if (PathingUtil.walkToTile(new RSTile(2725, 4899, 0)))
            PathingUtil.movementIdle();

        if (Utils.clickObj(6305, "Turn"))
            General.sleep(1500, 2200);
    }

    public void turnStatuette3() {
        if (PathingUtil.walkToTile(new RSTile(2718, 4899, 0)))
            PathingUtil.movementIdle();

        if (Utils.clickObj(6303, "Turn"))
            General.sleep(1500, 2200);
    }

    public void turnStatuette4() { // SW
        if (PathingUtil.walkToTile(new RSTile(2718, 4896, 0)))
            PathingUtil.movementIdle();

        if (Utils.clickObj(6304, "Turn"))
            General.sleep(1500, 2200);
    }

    public void doneStatuette() {
        PathingUtil.walkToTile(new RSTile(2722, 4911, 0));
        General.sleep(General.random(3500, 5000));

        if (Utils.clickObj(6310, "Enter"))
            General.sleep(General.random(3500, 5000));
    }


    public void leavePortal() {
        cQuesterV2.status = "Leaving Portal";
        if (Utils.clickObj("Portal", "Enter"))
            General.sleep(3500, 5000);

        PathingUtil.walkToArea(START_AREA);

        if (NpcChat.talkToNPC("Clay golem")) {
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation();
        }
    }

    public void makeInk() {
        if (Inventory.find(blackMushroomInk).length < 1) {
            cQuesterV2.status = "Making Ink while at bank";

            if (Inventory.find(vial).length < 1)
                getItems2();

            if (Inventory.find(vial).length > 0 && Inventory.find(blackMushroomInk).length < 1 && Inventory.find(pheonixQuillPen).length < 1) {
                Utils.useItemOnItem(pestle, blackMushroom);
                Utils.modSleep();
            }
        }
    }

    public void makeReprogrammingStuff() {
        cQuesterV2.status = "Making Items";
        if (Inventory.find(vial).length < 1 && Inventory.find(blackMushroomInk).length < 1) {
            getItems2();
        }

        if (Inventory.find(vial).length > 0 && Inventory.find(blackMushroomInk).length < 1 && Inventory.find(pheonixQuillPen).length < 1) {
            Utils.useItemOnItem(pestle, blackMushroom);
            Utils.shortSleep();
        }

        if (Inventory.find(blackMushroomInk).length > 0 && Inventory.find(pheonixQuillPen).length < 1) {
            Utils.useItemOnItem(blackMushroomInk, pheonixFeather);
            Utils.shortSleep();
        }

        if (Inventory.find(pheonixQuillPen).length > 0 && Inventory.find(4624).length < 1) {
            Utils.useItemOnItem(pheonixQuillPen, papyrus);
            Utils.shortSleep();
        }

        if (Inventory.find(4624).length > 0) {
            cQuesterV2.status = "Going to golem";

            PathingUtil.walkToArea(START_AREA);

            if (Utils.useItemOnNPC(strangeImplement, 6277)) {
                General.sleep(General.random(2000, 3000));
            }
            if (Utils.useItemOnNPC(4624, 6277)) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation();
            }
            Utils.closeQuestCompletionWindow();
        }
    }

    int GAME_SETTING = 437;

    @Override
    public void execute() {
        if (!checkRequirements()) {
            cQuesterV2.taskList.remove(this);
            return;
        }
        General.println("[Debug]: Game setting 437 = " + Game.getSetting(437));
        if (Game.getSetting(437) == 0) {
            if (Skills.SKILLS.FLETCHING.getActualLevel() < 10) {
                trainFletching();
                return;
            }
            buyItems();
            getItems();
            getFeather();
            startQuest();
        }
        if (Game.getSetting(437) == 1) {
            startQuest();
            repairGolem();

        } else if (Game.getSetting(437) == 129) {
            repairGolem();

        } else if (Game.getSetting(437) == 257) {
            repairGolem();

        } else if (Game.getSetting(437) == 385) {
            repairGolem();

        } else if (Game.getSetting(437) == 514) {
            getLetter();

        } else if (Game.getSetting(437) == 546) {
            talkToGolem();
            getMushroom();
            getStrangeImplement();
            goToDigSite();

        } else if (Game.getSetting(437) == 262690) {
            getMushroom();
            getStrangeImplement();
            goToDigSite();

        } else if (Game.getSetting(437) == 262722) {
            examCentre();

        } else if (Game.getSetting(437) == 262754) {
            museum();

        } else if (Game.getSetting(437) == 262755) {
            pickpocket();
            goToDisplayCase();

        } else if (Game.getSetting(437) == 393827) {
            getItems2();
            makeInk();
            returnToUzer();

        } else if (Game.getSetting(437) == 412260) { // only the NW need to be turned
            turnStatuette3();

        } else if (Game.getSetting(437) == 410212) { // both the W are wrong
            turnStatuette3();
            turnStatuette4();

        } else if (Game.getSetting(437) == 402020 || Game.getSetting(437) == 405092) { // only SE is wrong
            turnStatuette1();

        } else if (Game.getSetting(437) == 406116) { // both east are wrong
            turnStatuette1();
            turnStatuette2();

        } else if (Game.getSetting(437) == 413285) {
            doneStatuette();

        } else if (Game.getSetting(437) == 413286) {
            leavePortal();

        } else if (Game.getSetting(437) == 413287) {
            getStrangeImplement();
            makeReprogrammingStuff();
            Utils.modSleep();

        } else if (Game.getSetting(437) == 413290) {
            Utils.closeQuestCompletionWindow();
            NPCInteraction.handleConversation();
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
        return "The Golem";
    }

    @Override
    public boolean checkRequirements() {
        return checkLevel();
    }

    @Override
    public List<Requirement> getGeneralRequirements() {
        return null;
    }

    @Override
    public List<ItemRequirement> getBuyList() {
        return null;
    }

    @Override
    public boolean isComplete() {
        return Quest.THE_GOLEM.getState().equals(Quest.State.COMPLETE);
    }
}
