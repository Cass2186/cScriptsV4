package scripts.QuestPackages.RfdCook;

import dax.walker.utils.AccurateMouse;
import dax.walker_engine.interaction_handling.NPCInteraction;
import org.tribot.api.General;
import org.tribot.api2007.*;
import org.tribot.api2007.types.*;
import scripts.*;
import scripts.GEManager.GEItem;
import scripts.QuestPackages.FishingContest.FishingContest;
import scripts.QuestSteps.BuyItemsStep;
import scripts.QuestSteps.QuestTask;
import scripts.Requirements.ItemRequirement;
import scripts.Requirements.Requirement;
import scripts.Tasks.Priority;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RfdCook implements QuestTask {


    private static RfdCook quest;

    public static RfdCook get() {
        return quest == null ? quest = new RfdCook() : quest;
    }


    int eyeOfNewt = 221;
    int greenmansAle = 1909;
    int rottenTomato = 2518;
    int dirtyBlast = 7497;
    int fruitBlast = 2084;
    int ashes = 592;
    int camelotTab = 8010;
    int lumbridgeTab = 8008;
    int coins = 995;
    int potOfFlour = 1933;
    int bowlOfWater = 1921;
    int asgarnianAles = 1905;
    int bucketOfMilk = 1927;
    int egg = 1944;
    int leatherGloves = 1059;
    int faladorTab = 8009;
    int hotRockCake = 7509;
    int coldRockCake = 7510;
    int staffOfAir = 1381;
    int mindRune = 558;
    int firerune = 554;
    int lobster = 379;
    int asgoldianAle = 7508;

    RSArea kitchenArea = new RSArea(new RSTile(3212, 3212, 0), new RSTile(3205, 3217, 0));
    RSArea tomatoArea = new RSArea(new RSTile(3223, 3417, 0), new RSTile(3232, 3412, 0));
    RSArea faladorBar = new RSArea(new RSTile(2953, 3369, 0), new RSTile(2958, 3374, 0));
    RSArea falador = new RSArea(new RSTile(2979, 3365, 0), new RSTile(2954, 3387, 0));
    RSArea rockCakeArea = new RSArea(new RSTile(2859, 9879, 0), new RSTile(2871, 9873, 0));
    RSArea ENTRANCE_TO_UNDERGROUND = new RSArea(new RSTile(2818, 3487, 0), new RSTile(2823, 3482, 0));
    RSArea ICE_MOUNTAIN_AREA = new RSArea(new RSTile(3004, 3479, 0), new RSTile(3015, 3471, 0));
    RSArea BOTTOM_OF_STAIRS = new RSArea(new RSTile(2812, 9888, 0), new RSTile(2833, 9876, 0));
    RSArea ROHAK_AREA = new RSArea(new RSTile(2859, 9880, 0), new RSTile(2871, 9873, 0));

    RSObject[] dwarf = Objects.find(20, 12330);

    public static final RSTile[] undergroundPath = new RSTile[]{new RSTile(2820, 9882, 0), new RSTile(2823, 9878, 0), new RSTile(2825, 9874, 0), new RSTile(2827, 9870, 0), new RSTile(2830, 9867, 0), new RSTile(2833, 9864, 0), new RSTile(2837, 9863, 0), new RSTile(2841, 9864, 0), new RSTile(2845, 9867, 0), new RSTile(2846, 9871, 0), new RSTile(2846, 9875, 0), new RSTile(2847, 9879, 0), new RSTile(2851, 9881, 0), new RSTile(2855, 9881, 0), new RSTile(2860, 9881, 0), new RSTile(2864, 9879, 0)};

    ArrayList<GEItem> itemsToBuy = new ArrayList<GEItem>(
            Arrays.asList(
                    new GEItem(eyeOfNewt, 1, 500),
                    new GEItem(greenmansAle, 1, 500),
                    new GEItem(fruitBlast, 1, 350),
                    new GEItem(ashes, 1, 350),
                    new GEItem(ItemID.LUMBRIDGE_TELEPORT, 5, 35),
                    new GEItem(ItemID.CAMELOT_TELEPORT, 5, 35),
                    new GEItem(ItemID.FALADOR_TELEPORT, 4, 35),
                    new GEItem(potOfFlour, 2, 500),
                    new GEItem(bowlOfWater, 2, 500),
                    new GEItem(asgarnianAles, 6, 1000),
                    new GEItem(bucketOfMilk, 2, 800),
                    new GEItem(egg, 2, 500),

                    new GEItem(ItemID.LEATHER_GLOVES, 1, 500),
                    new GEItem(ItemID.MIND_RUNE, 400, 20),
                    new GEItem(ItemID.FIRE_RUNE, 800, 20),
                    new GEItem(ItemID.STAFF_OF_AIR, 1, 100),
                    new GEItem(ItemID.LOBSTER, 20, 30),

                    new GEItem(ItemID.COMBAT_BRACELET[0], 1, 20),
                    new GEItem(ItemID.STAMINA_POTION[0], 3, 15),
                    new GEItem(ItemID.RING_OF_WEALTH[0], 1, 25)
            )
    );


    BuyItemsStep buyStep = new BuyItemsStep(itemsToBuy);


    public void buyItems() {
        cQuesterV2.status = "Buying Items";
        General.println("[Debug]: Buying Items");

        buyStep.buyItems();
    }

    public void getItems() {
        cQuesterV2.status = "Getting Items";
        General.println("[Debug]: Getting Items");
        BankManager.open(true);
        BankManager.depositEquipment();
        BankManager.checkEquippedGlory();
        BankManager.depositAll(true);
        BankManager.withdraw2(1, true, eyeOfNewt);
        BankManager.withdraw2(1, true, greenmansAle);
        BankManager.withdraw2(1, true, fruitBlast);
        BankManager.withdraw2(1, true, ashes);
        BankManager.withdraw2(600, true, coins);
        BankManager.withdraw2(4, true, lumbridgeTab);
        BankManager.withdraw2(4, true, faladorTab);
        BankManager.withdraw2(4, true, camelotTab);
        BankManager.withdraw2(2, true, potOfFlour);
        BankManager.withdraw2(1, true, BankManager.STAMINA_POTION[0]);
        BankManager.withdraw2(3, true, bucketOfMilk);
        BankManager.withdraw2(2, true, bowlOfWater);
        BankManager.withdraw2(4, true, asgarnianAles);
        BankManager.withdraw2(2, true, egg);
        BankManager.withdraw2(1, true, leatherGloves);
        BankManager.withdraw2(1, true, staffOfAir);
        Utils.equipItem(leatherGloves);
        Utils.equipItem(staffOfAir);
        BankManager.withdraw2(200, true, mindRune);
        BankManager.withdraw2(600, true, firerune);
        BankManager.withdraw2(4, true, lobster);
        BankManager.withdraw2(1, true, rottenTomato);
        BankManager.close(true);
    }

    public void getRottenTomato() {
        cQuesterV2.status = "RFD Cook: Getting Rotten tomato";
        if (!BankManager.checkInventoryItems(potOfFlour, eyeOfNewt, greenmansAle, ashes, coins, camelotTab, faladorTab, bucketOfMilk, bowlOfWater, asgarnianAles, egg)) {
            buyItems();
            getItems();

        } else if (Inventory.find(rottenTomato).length < 1) {
            if (!tomatoArea.contains(Player.getPosition())) {
                PathingUtil.walkToTile(new RSTile(3227, 3415), 1, false);
                Timer.abc2WaitCondition(() -> tomatoArea.contains(Player.getPosition()), 8000, 12000);
                Utils.shortSleep();
            }
            RSObject[] crate = Objects.findNearest(20, 20885);
            if (crate.length > 0) {
                if (!crate[0].isClickable())
                    crate[0].adjustCameraTo();

                if (AccurateMouse.click(crate[0], "Buy")) {
                    Timer.slowWaitCondition(() -> Interfaces.isInterfaceSubstantiated(300, 16, 1), 7000, 12000);
                    if (Interfaces.isInterfaceSubstantiated(300, 16, 1)) {
                        if (AccurateMouse.click(Interfaces.get(300, 16, 1), "Buy 1")) {
                            Timer.abc2WaitCondition(() -> Inventory.find(rottenTomato).length > 0, 5000, 7000);
                            Utils.shortSleep();
                        }
                        if (Interfaces.get(300, 1, 11).click())
                            Utils.modSleep();
                    }
                }
            }
        }
    }

    public void startQuest() {
        if (Inventory.find(rottenTomato).length > 0) {
            cQuesterV2.status = "RFD Cook: Starting quest";
            PathingUtil.walkToArea(kitchenArea);

            if (NpcChat.talkToNPC("Cook")) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation("Do you have any other quests for me?");
                NPCInteraction.handleConversation("I don't really care to be honest.");
                NPCInteraction.handleConversation("What seems to be the problem?");
                NPCInteraction.handleConversation("YES", "Yes.");
                NPCInteraction.handleConversation();
            }
        }
    }

    public void step2() {
        PathingUtil.walkToArea(kitchenArea);

        if (Inventory.find(dirtyBlast).length < 1) {
            Utils.useItemOnItem(fruitBlast, ashes);
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation();
        }
        if (Inventory.find(eyeOfNewt, greenmansAle, rottenTomato, dirtyBlast).length > 3) {
            if (NpcChat.talkToNPC("Cook")) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation();
            }
        } else {
            buyItems();
            getItems();
        }
    }

    public void step3() {
        cQuesterV2.status = "Starting Cut scene";
        RSObject[] door = Objects.find(20, 12348);
        if (door.length > 0) {
            if (AccurateMouse.click(door[0], "Open")) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation();
            }
        }
        cQuesterV2.status = "Waiting for cut scene";
        Utils.cutScene();
        Timer.waitCondition(() -> Game.getSetting(1021) != 16576, 90000, 100000);
        Utils.modSleep();
    }

    public void step4() {
        dwarf = Objects.find(20, 12330);
        if (dwarf.length > 0) {
            if (!dwarf[0].isClickable()) {
                Walking.blindWalkTo(dwarf[0].getPosition());
                General.sleep(General.random(3000, 6000));
            }

            if (AccurateMouse.click(dwarf[0], "Inspect")) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation();
                Utils.shortSleep();
            }
        }
    }

    int GOLDEN_ALE = 7508;

    public void step5() {
        cQuesterV2.status = "Making Drinks";
        if (Inventory.find(GOLDEN_ALE).length < 4) {

            PathingUtil.walkToArea(faladorBar);

            if (NpcChat.talkToNPC("Kaylee")) {
                NPCInteraction.waitForConversationWindow();
                Utils.shortSleep();
                NPCInteraction.handleConversation("What can you tell me about dwarves and ale?");
                NPCInteraction.handleConversation("I could offer you some in return, how about 200 gold?");
                NPCInteraction.handleConversation();
                Utils.shortSleep();
            }

            for (int i =0; i< 4; i++) {
                int b = i;
                if (Utils.useItemOnItem(coins, asgarnianAles)) {
                    Timer.quickWaitCondition(() -> Inventory.find(GOLDEN_ALE).length > b, 750, 1250);
                    Utils.idlePredictableAction();
                }
            }
        }
    }

    public void goToRohak() {
        cQuesterV2.status = "Going to Rohak";
        if (!BOTTOM_OF_STAIRS.contains(Player.getPosition()) && !ROHAK_AREA.contains(Player.getPosition())) {
            PathingUtil.walkToArea(ENTRANCE_TO_UNDERGROUND);

            if (Utils.clickObject("Stairs", "Climb-down", false))
                Timer.abc2WaitCondition(() -> BOTTOM_OF_STAIRS.contains(Player.getPosition()), 9000, 12000);
        }
        if (BOTTOM_OF_STAIRS.contains(Player.getPosition())) {
            Walking.walkPath(undergroundPath);
            Timer.waitCondition(() -> ROHAK_AREA.contains(Player.getPosition()), 15000, 20000);
        }
    }

    public void step6() {
        if (Inventory.find(7508).length > 3) {
            goToRohak();
        }
        if (ROHAK_AREA.contains(Player.getPosition())) {
            NpcChat.talkToNPC("An old Dwarf");
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation();
            Utils.shortSleep();
        }
    }

    public void step7() {
        if (Inventory.find(7508).length > 3)
            goToRohak();

        if (rockCakeArea.contains(Player.getPosition())) {
            cQuesterV2.status = "Talking to Rohak ";
            if (NpcChat.talkToNPC("Rohak")) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation();
                Utils.shortSleep();
            }
        }
    }

    public void step8() {
        cQuesterV2.status = "Giving Rohak the ingredients";
        if (Inventory.find(hotRockCake).length < 1) {
            if (!ROHAK_AREA.contains(Player.getPosition()))
                goToRohak();

            if (ROHAK_AREA.contains(Player.getPosition())) {
                if (NpcChat.talkToNPC("Rohak")) {
                    NPCInteraction.waitForConversationWindow();
                    Utils.shortSleep();
                    NPCInteraction.handleConversation();
                    Utils.shortSleep();
                }
            }

            Timer.abc2WaitCondition(() -> GroundItems.find(hotRockCake).length > 0, 35000, 40000);
            if (GroundItems.find(hotRockCake).length > 0)
                if (AccurateMouse.click(GroundItems.find(hotRockCake)[0], "Take")) {
                    Timer.slowWaitCondition(() -> Inventory.find(hotRockCake).length > 0, 25000, 35000);
                }
        }
    }

    public void step9() {
        if (Inventory.find(hotRockCake).length < 2 && Inventory.find(hotRockCake).length > 0) {
            cQuesterV2.status = "Giving Rohak the ingredients";

            if (rockCakeArea.contains(Player.getPosition())) {

                if (AccurateMouse.click(Inventory.find(hotRockCake)[0], "Drop"))
                    Timer.slowWaitCondition(() -> Inventory.find(hotRockCake).length < 1, 25000, 35000);

                if (NpcChat.talkToNPC("Rohak")) {
                    NPCInteraction.waitForConversationWindow();
                    NPCInteraction.handleConversation();
                    Timer.abc2WaitCondition(() -> GroundItems.find(hotRockCake).length > 1, 25000, 35000);

                    if (AccurateMouse.click(GroundItems.find(hotRockCake)[0], "Take"))
                        Timer.slowWaitCondition(() -> Inventory.find(hotRockCake).length == 1, 25000, 35000);

                }

                if (GroundItems.find(hotRockCake).length > 0)
                    if (AccurateMouse.click(GroundItems.find(hotRockCake)[0], "Take")) {
                        Timer.slowWaitCondition(() -> Inventory.find(hotRockCake).length > 1, 25000, 35000);
                    }
            }
        }
    }

    public void step10() { //cooling cakes
        if (Inventory.find(hotRockCake).length == 2 || (Inventory.find(hotRockCake).length == 1 && Inventory.find(coldRockCake).length == 1)) {
            cQuesterV2.status = "Cooling Cakes";
            if (Inventory.find(coldRockCake).length < 2) {

                PathingUtil.walkToArea(ICE_MOUNTAIN_AREA);

                if (ICE_MOUNTAIN_AREA.contains(Player.getPosition())) {
                    if (Inventory.find(coldRockCake).length < 2) {
                        cQuesterV2.status = "Attacking icefiend";
                        if (!Autocast.isAutocastEnabled(Autocast.FIRE_STRIKE))
                            Autocast.enableAutocast(Autocast.FIRE_STRIKE);

                        if (Autocast.isAutocastEnabled(Autocast.FIRE_STRIKE)) {
                            RSNPC[] icefiend = NPCs.findNearest("Icefiend");
                            if (icefiend.length > 0 && !Combat.isUnderAttack()) {
                                if (AccurateMouse.click(icefiend[0], "Attack")) {
                                    Timer.waitCondition(Combat::isUnderAttack, 12000, 18000);
                                    CombatUtil.waitUntilOutOfCombat("Icefiend", General.random(30, 60));
                                    Utils.shortSleep();
                                }
                            }
                            if (Combat.getHPRatio() < General.random(30, 60)) {
                                if (Inventory.find(379).length > 0) {
                                    AccurateMouse.click(Inventory.find(379)[0], "Eat");
                                    General.sleep(General.random(200, 600));
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public void finishQuest() {
        if (Inventory.find(coldRockCake).length > 1) {
            cQuesterV2.status = "Finishing quest";
            PathingUtil.walkToArea(kitchenArea);
            if (kitchenArea.contains(Player.getPosition())) {

                RSObject[] door = Objects.find(20, 12348);
                if (door.length > 0)
                    if (AccurateMouse.click(door[0], "Open")) {
                        Timer.abc2WaitCondition(() -> Game.getSetting(1021) == 32, 8000, 10000);
                        Utils.shortSleep();
                    }
            }

            dwarf = Objects.find(20, 12330);
            if (dwarf.length > 0) {
                if (dwarf[0].isClickable()) {
                    Walking.blindWalkTo(dwarf[0].getPosition());
                    General.sleep(General.random(3000, 6000));
                }

                if (Utils.useItemOnObject(coldRockCake, 12330)) {
                    NPCInteraction.waitForConversationWindow();
                    NPCInteraction.handleConversation();
                    Utils.modSleep();
                }
            }

        }
    }

    public void checkLevel() {
        if (Skills.getActualLevel(Skills.SKILLS.COOKING) < 10 || Skills.getActualLevel(Skills.SKILLS.FISHING) < 10) {
            General.println("[Debug]: Missing Skill requirement, cannot complete RFD Cook");
            cQuesterV2.taskList.remove(RfdCook.get());
        }
    }


    int GAME_SETTING = 681;

    @Override
    public void execute() {
        checkLevel();

        if (Game.getSetting(678) == 0) {
            buyItems();
            getItems();
            getRottenTomato();
            startQuest();
        }
        if (Game.getSetting(678) == 1) {
            step2();
        }
        if (Game.getSetting(678) == 2 || Game.getSetting(678) == 3 && Game.getSetting(681) == 0) {
            Utils.closeQuestCompletionWindow();
            NPCInteraction.handleConversation();
            step3();
        }
        if (Game.getSetting(681) == 0) {
            step4();
        }
        if (Game.getSetting(681) == 10 && RSVarBit.get(1893).getValue() == 0) {
            step5();
            step6();
        }
        if (Game.getSetting(681) == 20) { // give first beer
            step7();
        }
        if (Game.getSetting(681) == 134217748 || RSVarBit.get(1893).getValue() == 2) { // give 2nd beer
            step7();
        }
        if (Game.getSetting(681) == 268435476 || RSVarBit.get(1893).getValue() == 3) { // give 3rd beer
            step7();
        }
        if (Game.getSetting(681) == 402653204 || RSVarBit.get(1893).getValue() == 4) {// give 4th beer
            step7();
        }
        if (Game.getSetting(681) == 536870932) {// talk to him drunk
            step7();
        }
        if (Game.getSetting(681) == 536870932 || RSVarBit.get(1892).getValue() == 20) {// talk to him drunk
            step7();
        }
        if (Game.getSetting(681) == 536870952 || RSVarBit.get(1892).getValue() == 30) {// talk to him sober
            step7();
        }
        if (Game.getSetting(681) == 402653214 || Game.getSetting(681) == 402653234 || RSVarBit.get(1892).getValue() == 40) {//got 1st cake
            step8();
        }
        if (Game.getSetting(681) == 134217778 || Game.getSetting(681) == 268435506 || RSVarBit.get(1892).getValue() == 50) { // get 2nd
            step9();
        }
        if (Game.getSetting(681) == 40) {// talk to sober and give ingridients
            step8();
        }
        if (Game.getSetting(681) == 50) {// get 2nd
            step9();
            step10();
            finishQuest();
        }
        if (Game.getSetting(681) == 134217778 || RSVarBit.get(1892).getValue() == 50) {
            step10();
            finishQuest();
        }
        if (Game.getSetting(681) == 60 || RSVarBit.get(1892).getValue() == 60) {
            Utils.closeQuestCompletionWindow();
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation();
            cQuesterV2.taskList.remove(RfdCook.get());
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
        return "RFD Cook";
    }

    @Override
    public boolean checkRequirements() {
        return false;
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
