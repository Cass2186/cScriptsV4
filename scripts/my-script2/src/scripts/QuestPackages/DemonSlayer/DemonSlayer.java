package scripts.QuestPackages.DemonSlayer;

import dax.walker.utils.AccurateMouse;
import dax.walker_engine.interaction_handling.NPCInteraction;
import org.tribot.api.General;
import org.tribot.api2007.*;
import org.tribot.api2007.types.*;
import scripts.*;
import scripts.GEManager.GEItem;
import scripts.QuestPackages.ShadowOfTheStorm.ShadowOfTheStorm;
import scripts.QuestSteps.BuyItemsStep;
import scripts.QuestSteps.QuestTask;
import scripts.Requirements.ItemRequirement;
import scripts.Requirements.Requirement;
import scripts.Tasks.Priority;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DemonSlayer implements QuestTask {

    private static DemonSlayer quest;

    public static DemonSlayer get() {
        return quest == null ? quest = new DemonSlayer() : quest;
    }


    public String getWord1() {
        if (RSVarBit.get(2562).getValue() == 0) {
            return "Carlem";
        }
        if (RSVarBit.get(2562).getValue() == 1) {
            return "Aber";
        }
        if (RSVarBit.get(2562).getValue() == 2) {
            return "Camerinthum";
        }
        if (RSVarBit.get(2562).getValue() == 3) {
            return "Purchai";
        }
        if (RSVarBit.get(2562).getValue() == 4) {
            return "Gabindo";
        }
        return "";
    }


    public String getWord2() {
        if (RSVarBit.get(2563).getValue() == 0) {
            return "Carlem";
        }
        if (RSVarBit.get(2563).getValue() == 1) {
            return "Aber";
        }
        if (RSVarBit.get(2563).getValue() == 2) {
            return "Camerinthum";
        }
        if (RSVarBit.get(2563).getValue() == 3) {
            return "Purchai";
        }
        if (RSVarBit.get(2563).getValue() == 4) {
            return "Gabindo";
        }
        return "";
    }

    public String getWord3() {
        if (RSVarBit.get(2564).getValue() == 0) {
            return "Carlem";
        }
        if (RSVarBit.get(2564).getValue() == 1) {
            return "Aber";
        }
        if (RSVarBit.get(2564).getValue() == 2) {
            return "Camerinthum";
        }
        if (RSVarBit.get(2564).getValue() == 3) {
            return "Purchai";
        }
        if (RSVarBit.get(2564).getValue() == 4) {
            return "Gabindo";
        }
        return "";
    }

    public String getWord4() {
        if (RSVarBit.get(2565).getValue() == 0) {
            return "Carlem";
        }
        if (RSVarBit.get(2565).getValue() == 1) {
            return "Aber";
        }
        if (RSVarBit.get(2565).getValue() == 2) {
            return "Camerinthum";
        }
        if (RSVarBit.get(2565).getValue() == 3) {
            return "Purchai";
        }
        if (RSVarBit.get(2565).getValue() == 4) {
            return "Gabindo";
        }
        return "";
    }

    public String getWord5() {
        if (RSVarBit.get(2566).getValue() == 0) {
            return "Carlem";
        }
        if (RSVarBit.get(2566).getValue() == 1) {
            return "Aber";
        }
        if (RSVarBit.get(2566).getValue() == 2) {
            return "Camerinthum";
        }
        if (RSVarBit.get(2566).getValue() == 3) {
            return "Purchai";
        }
        if (RSVarBit.get(2566).getValue() == 4) {
            return "Gabindo";
        }
        return "";
    }


    public int key1 = 2400;
    public int key2 = 2399;
    int key3 = 2401;
    public int varrockTab = 8007;
    public int bucketOfWater = 1929;
    public int bones = 526;
    public int silverlight = 2402;
    public int lobster = 379;

    int stage0 = 0;
    int stage1 = 35713; // fetches from game
    int stage2 = stage1 + 1;
    int stage3 = stage2 + 2097152;
    int stage4 = stage3 + 2097152;
    int stage5 = stage4 + 1048576;
    int stage6 = stage5 + 8338608;
    int stage7 = stage6 + 16777216;
    int stage8 = stage7 - 8388608;
    int stage9 = stage8 + 1;


    RSArea GYPSY_TENT_AREA = new RSArea(new RSTile(3205, 3422, 0), new RSTile(3201, 3426, 0));
    RSArea sirPrysinArea = new RSArea(new RSTile(3205, 3478, 0), new RSTile(3211, 3471, 0));
    RSArea captainRovinArea = new RSArea(new RSTile(3205, 3494, 2), new RSTile(3200, 3500, 2));
    RSArea wizardTower2ndFloor = new RSArea(new RSTile(3110, 3165, 1), new RSTile(3114, 3160, 1));
    RSArea sewerEntrance = new RSArea(new RSTile(3235, 3460, 0), new RSTile(3240, 3455, 0));
    RSArea sewerArea = new RSArea(new RSTile(3226, 9900, 0), new RSTile(3232, 9897, 0));
    RSArea kitchenArea = new RSArea(new RSTile(3225, 3497, 0), new RSTile(3226, 3494, 0));
    RSArea stoneCircle = new RSArea(new RSTile(3225, 3372, 0), new RSTile(3231, 3366, 0));


    String code;
    String removeBreak;

    String code1;
    String code2;
    String code3;
    String code4;
    String code5;

    RSNPC[] gypsy = NPCs.find("Gypsy Aris");
    RSNPC[] sirPrysin = NPCs.find("Sir Prysin");
    RSNPC[] delrith = NPCs.find("Delrith");

    RSObject[] rustyKey = Objects.findNearest(20, 17431);

    public static int gameSetting = 0;

    ArrayList<GEItem> itemsToBuy = new ArrayList<GEItem>(Arrays.asList(
            new GEItem(ItemID.VARROCK_TELEPORT, 6, 50),
            new GEItem(bucketOfWater, 1, 500),
            new GEItem(bones, 25, 50),
            new GEItem(ItemID.COMBAT_BRACELET[0], 1, 25),
            new GEItem(ItemID.AMULET_OF_GLORY[2], 1, 20),
            new GEItem(ItemID.SKILLS_NECKLACE[0], 1, 20),
            new GEItem(ItemID.STAMINA_POTION[0], 2, 20)
    ));

    public void buyItems() {
        cQuesterV2.status = "Buying Items";
        General.println("[Debug]: " + cQuesterV2.status);
        BuyItemsStep buyStep = new BuyItemsStep(itemsToBuy);
        buyStep.buyItems();
    }

    public void getItems() {
        cQuesterV2.status = "Getting items";
        General.println("[Debug]: " + cQuesterV2.status);
        BankManager.open(true);
        Banking.depositAll();
        BankManager.checkEquippedGlory();
        BankManager.checkCombatBracelet();
        BankManager.withdraw(5, true, varrockTab);
        BankManager.withdraw(1, true,
                ItemID.NECKLACE_OF_PASSAGE[0]);
        BankManager.withdraw(5, true, 995);
        BankManager.withdraw(1, true,
                ItemID.STAMINA_POTION[0]);
        Banking.close();
    }

    public void getItems2() {
        if (Inventory.find(key2).length < 1) {
            cQuesterV2.status = "Getting bones";
            General.println("[Debug]: " + cQuesterV2.status);
            BankManager.open(true);
            BankManager.checkEquippedGlory();
            Banking.depositAll();
            BankManager.withdraw2(3, true, varrockTab);
            BankManager.withdraw2(1, true,
                    ItemID.NECKLACE_OF_PASSAGE[0]);
            BankManager.withdraw2(25, true, bones);
            BankManager.withdraw2(1, true,
                    ItemID.STAMINA_POTION[0]);
            Banking.close();
        }
    }

    String[] GYPSY_START_STRINGS = {
            "The Demon Slayer Quest",
            "Yes.",
            "Ok, here you go.",
            "Okay, where is he? I'll kill him for you!",
            "So how did Wally kill Delrith?"
    };

    public void startQuest() {
        cQuesterV2.status = "Starting Quest";
        PathingUtil.walkToArea(GYPSY_TENT_AREA);
        gypsy = NPCs.find(5082);
        if (NpcChat.talkToNPC("Gypsy Aris")) {
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation(GYPSY_START_STRINGS);
            NPCInteraction.handleConversation();
            General.sleep(General.random(3500, 5000));
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation();
            General.sleep(General.random(3500, 5000));
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation();
            Utils.cutScene();
        }
    }

    String[] PRYSON_CHAT1 = {
            "Gypsy Aris said I should come and talk to you.",
            "I need to find Silverlight.",
            "He's back and unfortunately I've got to deal with him.",
            "So give me the keys!"
    };

    public void talkToPrysinFirst() {
        cQuesterV2.status = "Talking to Sir Prysin";
        General.println("[Debug]: " + cQuesterV2.status);
        PathingUtil.walkToArea(sirPrysinArea);
        RSNPC[] prysin = NPCs.findNearest("Sir Prysin");
        if (prysin.length > 0) {
            PathingUtil.walkToTile(prysin[0].getPosition());
            Utils.idle(800, 3000);
        }
        if (NpcChat.talkToNPC("Sir Prysin")) {
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation(PRYSON_CHAT1);
        }
    }

    String[] ROVIN_CHAT1 = {
            "Yes I know, but this is important.",
            "There's a demon who wants to invade this city.",
            "Yes, very.",
            "It's not them who are going to Fight the demon, it's me.",
            "Sir Prysin said you would give me the key.",
            "Otherwise the demon will destroy the city!",
            "Gypsy Aris said I was destined to kill the demon.",
            "Sir Prysin said you would give me the key",
            "Why did he give you one of the keys then?"
    };

    public void step3() {
        if (Inventory.find(key1).length < 1) {
            cQuesterV2.status = "Talking to Captain Rovin";
            General.println("[Debug]: " + cQuesterV2.status);
            PathingUtil.walkToArea(captainRovinArea);
            if (NpcChat.talkToNPC("Captain Rovin")) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation(ROVIN_CHAT1);
                NPCInteraction.handleConversation();
            }
        }
    }

    public void step4() {
        if (Inventory.find(key2).length < 1) {
            cQuesterV2.status = "Banking for bones";
            General.println("[Debug]: " + cQuesterV2.status);
            BankManager.open(true);
            BankManager.depositAll(true);
            BankManager.checkEquippedGlory();
            BankManager.withdraw(5, true, varrockTab);
            BankManager.withdraw(25, true, bones);
            BankManager.withdraw(1, true,
                    ItemID.NECKLACE_OF_PASSAGE[0]);
            BankManager.withdraw(1, true,
                    ItemID.STAMINA_POTION[0]);
            BankManager.close(true);
        }
    }

    public void step5() {
        if (Inventory.find(key2).length < 1) {
            if (!BankManager.checkInventoryItems(bones)) {
                buyItems();
                step4();
            }
            cQuesterV2.status = "Talking to Wizard";
            General.println("[Debug]: " + cQuesterV2.status);
            PathingUtil.walkToArea(wizardTower2ndFloor);

            if (NpcChat.talkToNPC("Traiborn")) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation("I need to get a key given to you by Sir Prysin.");
                NPCInteraction.handleConversation("Well, have you got any keys knocking around?");
                NPCInteraction.handleConversation(); //need this
                NPCInteraction.handleConversation("I'll get the bones for you.");
                NPCInteraction.handleConversation();
            }
            if (Interfaces.get(231, 4) == null && Interfaces.get(217, 3) == null) {
                if (NpcChat.talkToNPC("Traiborn")) {
                    NPCInteraction.waitForConversationWindow();
                    NPCInteraction.handleConversation();
                    NPCInteraction.waitForConversationWindow();
                    NPCInteraction.handleConversation();
                    Timer.waitCondition(() -> Inventory.find(key2).length > 0, 10000);
                }
            }
        }
    }

    public void step6() {
        cQuesterV2.status = "Getting items for last key";
        BankManager.open(true);
        Banking.depositAll();
        BankManager.checkEquippedGlory();
        BankManager.withdraw(5, true, varrockTab);
        BankManager.withdraw(1, true, bucketOfWater);
        BankManager.withdraw(1, true, key1);
        BankManager.withdraw(1, true, key2);
        BankManager.withdraw(1, true,
                ItemID.STAMINA_POTION[0]);
        BankManager.close(true);
    }

    public void dumpWater() {
        cQuesterV2.status = "Dumping water down drain";
        PathingUtil.walkToArea(kitchenArea);
        if (kitchenArea.contains(Player.getPosition())) {
            if (Utils.useItemOnObject(bucketOfWater, "Drain")) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation();
            }
        }
    }

    public void step8() {
        if (Inventory.find(key3).length < 1) {
            cQuesterV2.status = "Getting last key";
            PathingUtil.walkToArea(sewerArea);
            rustyKey = Objects.findNearest(20, 17431);
            if (rustyKey.length > 0) {

                if (!rustyKey[0].isClickable())
                    rustyKey[0].adjustCameraTo();

                if (AccurateMouse.click(rustyKey[0], "Take"))
                    Timer.abc2WaitCondition(() -> Inventory.find(2401).length > 0, 5000, 9000);
            }
        }
    }


    public void step9() {
        cQuesterV2.status = "Talking to Sir Prysin";
        PathingUtil.walkToArea(sirPrysinArea);
        if (NpcChat.talkToNPC("Sir Prysin")) {
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation();
            General.sleep(General.random(5000, 7000));
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation();
            Utils.equipItem(silverlight);
        }
    }

    public void step10() {
        cQuesterV2.status = "Banking for Fight";
        BankManager.open(true);
        Banking.depositAll();
        BankManager.checkEquippedGlory();
        BankManager.withdraw(1, true, silverlight);
        Utils.equipItem(silverlight);
        Banking.depositAll();
        BankManager.withdraw(2, true, varrockTab);
        BankManager.withdraw(25, true, lobster);
        BankManager.withdraw(1, true, ItemID.STAMINA_POTION[0]);
        BankManager.close(true);
    }

    private void waitForChat() {
        Timer.waitCondition(() -> !Interfaces.isInterfaceSubstantiated(219, 1), 3500, 4000);
        Timer.waitCondition(() -> Interfaces.isInterfaceSubstantiated(219, 1), 4000, 6000);
    }

    public void getIncantation() {
        code1 = getWord1();
        code2 = getWord2();
        code3 = getWord3();
        code4 = getWord4();
        code5 = getWord5();
    }


    public void killDemon() {
        getIncantation();
        if (!Equipment.isEquipped(silverlight)) {
            if (Inventory.find(silverlight).length > 0) {
                Utils.equipItem(silverlight);
            } else {
                step10();
            }

        }
        cQuesterV2.status = "Killing demon";
        RSNPC[] delrith = NPCs.find("Delrith");
        if (!stoneCircle.contains(Player.getPosition()) && delrith.length == 0) {
            PathingUtil.walkToArea(stoneCircle);
            Utils.idle(5000, 8000);
            Utils.cutScene();
            if (NPCInteraction.waitForConversationWindow())
                NPCInteraction.handleConversation();
            if (NPCInteraction.waitForConversationWindow())
                NPCInteraction.handleConversation();
        }

        delrith = NPCs.find("Delrith");
        if (NPCInteraction.waitForConversationWindow())
            NPCInteraction.handleConversation();

        if (NPCInteraction.waitForConversationWindow())
            NPCInteraction.handleConversation();

        while (delrith.length > 0) {
            General.sleep(General.random(100, 300));
            delrith = NPCs.find("Delrith");
            if (Combat.getHPRatio() < General.random(51, 61)) {
                RSItem[] invItem1 = Inventory.find(lobster);
                if (invItem1.length > 0) {
                    AccurateMouse.click(invItem1[0], "Eat");
                }
            }
            if (delrith.length > 0 && !Combat.isUnderAttack()) {
                General.println("[Debug]: Attacking Demon");
                if (AccurateMouse.click(delrith[0], "Attack")) {
                    Timer.waitCondition(Combat::isUnderAttack, 5000, 7000);
                    General.sleep(General.random(300, 2000));
                }
            }
            List<String> codeList = new ArrayList<>(
                    Arrays.asList(code1, code2, code3, code4, code5));
            if (NPCs.find(5080).length > 0) { // weakened delrith
                NPCInteraction.waitForConversationWindow();
                int i = 0;
                for (String s : codeList) {
                    i++;
                    General.println("[Debug]: Handling code (" + i + "): " + s);
                    NPCInteraction.handleConversation(s);
                    // wait forchat interface with options to be null
                    Timer.waitCondition(() -> Interfaces.get(219, 1) == null, 1500, 2500);
                    Timer.waitCondition(() -> Interfaces.get(219, 1) != null, 3500, 4500);
                }
                Utils.shortSleep();
            }

        }
    }


    @Override
    public void execute() {

        General.println("Game Setting: " + Game.getSetting(222));
        General.println("2561:  " + Utils.getVarBitValue(2561));
        General.println("2562:  " + Utils.getVarBitValue(2562));
        General.println("2563:  " + Utils.getVarBitValue(2563));
        General.println("2564:  " + Utils.getVarBitValue(2564));
        General.println("2568:  " + Utils.getVarBitValue(2568));
        if (Game.getSetting(222) == stage0) {
            buyItems();
            getItems();
            startQuest();
            stage1 = Game.getSetting(222);
        }
        if (Game.getSetting(222) == 69985 || Game.getSetting(222) == 185345 ||
                (RSVarBit.get(2561).getValue() == 1)) { //&&
            // (RSVarBit.get(2563).getValue() == 0 || Utils.getVarBitValue(2563) == 4 || Utils.getVarBitValue(2563) == 2)
            //   && RSVarBit.get(2568).getValue() == 0)) { //2564 can be 0 or 1
            if (code == null)
                getIncantation();
            talkToPrysinFirst();
        }

        if (RSVarBit.get(2561).getValue() == 2 && RSVarBit.get(2568).getValue() == 0) {
            step3();
            getItems2();
            step5();
            step6(); // bank for water
            dumpWater(); // dump water causes 2568 to change from 0->1
            Utils.modSleep();
        }
        if (RSVarBit.get(2568).getValue() == 1) {
            General.println("Game Setting: " + Game.getSetting(222));
            General.println("stage setting thinks it's: " + stage3);
            step8(); // getting last key; causes 2568 toc change from 1 to 2

        }
        if (RSVarBit.get(2568).getValue() == 2 && RSVarBit.get(2567).getValue() == 0) {
            if (code == null)
                getIncantation();

            step9(); // talking to prysin for silverlight (2567 changes from 0 -> 1
        }
        if (RSVarBit.get(2568).getValue() == 2 && RSVarBit.get(2567).getValue() == 1) {
            if (code == null)
                getIncantation();

            step10();
            killDemon();
        }
        if (RSVarBit.get(2569).getValue() == 0 && RSVarBit.get(2569).getValue() == 1) {
            General.println("Game Setting: " + Game.getSetting(222));
            General.println("stage setting thinks it's: " + stage6 + " or " + stage7);
            if (code == null)
                getIncantation();

            step10();
            killDemon();
        }
        if (Game.getSetting(222) == stage8) {
            if (Interfaces.isInterfaceSubstantiated(219, 1)) {
                NPCInteraction.handleConversation(code5);
                Utils.shortSleep();
            }
            General.println("Game Setting: " + Game.getSetting(222));
            NPCInteraction.handleConversation();
        }
        if (Game.getSetting(222) == stage9 || Game.getSetting(222) == 22090083
                || RSVarBit.get(2561).getValue() == 3) {
            General.println("Game Setting: " + Game.getSetting(222));
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
        return "Demon Slayer";
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
