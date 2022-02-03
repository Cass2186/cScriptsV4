package scripts.QuestPackages.ShadowOfTheStorm;

import dax.walker.utils.AccurateMouse;
import dax.walker.utils.camera.DaxCamera;
import dax.walker_engine.interaction_handling.NPCInteraction;
import org.apache.commons.lang3.ArrayUtils;
import org.tribot.api.General;
import org.tribot.api2007.*;
import org.tribot.api2007.types.*;
import org.tribot.script.sdk.Log;
import org.tribot.script.sdk.Waiting;
import org.tribot.script.sdk.tasks.BankTask;
import scripts.*;
import scripts.GEManager.GEItem;
import scripts.QuestPackages.VarrockMuseum.VarrockMuseum;
import scripts.QuestSteps.BuyItemsStep;
import scripts.QuestSteps.QuestTask;
import scripts.Tasks.Priority;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class ShadowOfTheStorm implements QuestTask {

    private static ShadowOfTheStorm quest;

    public static ShadowOfTheStorm get() {
        return quest == null ? quest = new ShadowOfTheStorm() : quest;
    }

    /**
     * (0, "Caldar");
     * (1, "Nahudu");
     * (2, "Agrith-Naar");
     * (3, "Camerinthum");
     * (4, "Tarren");
     */
    public int FIRST_WORD_VARBIT = 1373;
    public int SECOND_WORD_VARBIT = 1374;
    public int THIRD_WORD_VARBIT = 1375;
    public int FOURTH_WORD_VARBIT = 1376;
    public int FIFTH_WORD_VARBIT = 1377;

    public int SILVERLIGHT = 2402;
    public int DARK_SILVERLIGHT = 6745;
    public int strangeImplement = 4619;
    public int blackMushroom = 4620;
    public int VIAL = 229;
    public int blackMushroomInk = 4622;
    public int blackDesertRobe = 6752;
    public int blackDesertShirt = 6750;
    public int BLACK_CAPE = 1019;
    public int SILVER_BAR = 2355;
    public int coins = 995;
    public int prayerPotion4 = 2434;
    public int waterskin4 = 1823;
    public int chisel = 1755;
    public int hammer = 2347;
    public int superCombat4 = 12695;
    public int monkfish = 7946;
    int pestle = 233;
    int shantyPass = 1854; //need 10
    int passage5 = 21146;
    int demonicSigilMould = 6747;
    int demonicSigil = 6748;
    int demonicTomb = 6749;
    int setting = 1343785128;
    int RUNE_SCIMITAR = 1333;

    int GOLEM_VARBIT = 353;
    int GOLEM_ID = 6277;
    int DRAGON_SWORD = 21009;

    RSArea startArea = new RSArea(new RSTile(3265, 3160, 0), new RSTile(3276, 3156, 0));
    RSArea uzerArea = new RSArea(new RSTile(3483, 3095, 0), new RSTile(3490, 3086, 0));
    RSArea topOfStairs = new RSArea(new RSTile(3488, 3091, 0), new RSTile(3491, 3088, 0));
    RSArea bottomOfStairs = new RSArea(new RSTile(2726, 4883, 0), new RSTile(2717, 4906, 0));
    RSArea dungeonEntranceArea = new RSArea(new RSTile(2719, 4914, 0), new RSTile(2724, 4907, 0));
    RSArea edgevilleFurnace = new RSArea(new RSTile(3105, 3501, 0), new RSTile(3110, 3496, 0));
    RSArea edgevillBank = new RSArea(new RSTile(3091, 3498, 0), new RSTile(3098, 3488, 0));
    RSArea kiln1 = new RSArea(new RSTile(3475, 3084, 0), new RSTile(3477, 3081, 0));
    RSArea kiln2 = new RSArea(new RSTile(3473, 3092, 0), new RSTile(3476, 3090, 0));
    RSArea kiln3 = new RSArea(new RSTile(3465, 3125, 0), new RSTile(3469, 3122, 0));
    RSArea kiln4 = new RSArea(new RSTile(3501, 3087, 0), new RSTile(3504, 3082, 0));
    RSArea underGroundWholeArea = new RSArea(new RSTile(2708, 4907, 0), new RSTile(2735, 4883, 0));
    String code;

    RSObject[] stairs = Objects.findNearest(20, "Staircase");
    RSObject[] kiln = Objects.find(20, 10243);
    RSObject[] door = Objects.findNearest(20, "Door");

    String code1;
    String code2;
    String code3;
    String codeX;

    String code4;
    String code5;
    String code6;

    private final HashMap<Integer, String> words = new HashMap<Integer, String>() {{
        put(0, "Caldar");
        put(1, "Nahudu");
        put(2, "Agrith-Naar");
        put(3, "Camerinthum");
        put(4, "Tarren");
    }};

    private boolean reverse;
    private String[] incantationOrder;
    private int incantationPosition = 0;


    ArrayList<GEItem> itemsToBuy = new ArrayList<GEItem>(Arrays.asList(
            new GEItem(ItemID.VIAL, 1, 500),
            new GEItem(ItemID.PESTLE_AND_MORTAR, 1, 500),
            new GEItem(blackDesertRobe, 1, 500),
            new GEItem(ItemID.SHANTAY_PASS, 15, 500),
            new GEItem(blackDesertShirt, 1, 500),
            new GEItem(ItemID.SILVER_BAR, 3, 500),
            new GEItem(ItemID.BLACK_CAPE, 1, 500),
            new GEItem(ItemID.WATERSKIN[0], 5, 500),
            new GEItem(ItemID.HAMMER, 1, 500),
            new GEItem(ItemID.CHISEL, 1, 500),
            new GEItem(ItemID.MONKFISH, 25, 50),
            new GEItem(ItemID.NECKLACE_OF_PASSAGE[0], 2, 50),
            new GEItem(ItemID.WATERSKIN[0], 6, 500),
            new GEItem(ItemID.LOBSTER, 20, 50),
            new GEItem(ItemID.AMULET_OF_GLORY[0], 2, 20),
            new GEItem(ItemID.STAMINA_POTION[0], 5, 30),
            new GEItem(ItemID.PRAYER_POTION_4, 4, 20),
            new GEItem(ItemID.SUPER_COMBAT_POTION[0], 1, 20)
    ));


    public void buyItems() {

        cQuesterV2.status = "Buying Items";
        General.println("[Debug]: Buying Items");
        if (Skills.getActualLevel(Skills.SKILLS.ATTACK) >= 60) {
            itemsToBuy.add(new GEItem(DRAGON_SWORD, 1, 50));
        } else {
            itemsToBuy.add(new GEItem(ItemID.RUNE_SCIMITAR, 1, 50));
        }
        BuyItemsStep buyStep = new BuyItemsStep(itemsToBuy);
        buyStep.buyItems();
    }

    public void getItems1() {
        cQuesterV2.status = "Getting Items";
        General.println("[Debug]: Getting Items");
        BankManager.open(true);
        BankManager.depositAll(true);
        BankManager.checkEquippedGlory();
        BankManager.withdraw(1, true, blackDesertRobe);
        BankManager.withdraw(1, true, blackDesertShirt);
        BankManager.withdraw(1, true, BLACK_CAPE);
        Utils.equipItem(blackDesertRobe);
        Utils.equipItem(blackDesertShirt);
        Utils.equipItem(BLACK_CAPE);
        BankManager.withdraw(2, true, SILVER_BAR);
        BankManager.withdraw(1, true, SILVERLIGHT);
        BankManager.withdraw(1, true, blackMushroomInk);
        BankManager.withdraw(500, true, coins);
        BankManager.withdraw(3, true, waterskin4);
        BankManager.withdraw(1, true, ItemID.AMULET_OF_GLORY[0]);
        BankManager.withdraw(1, true, passage5);
        BankManager.withdraw(5, true, shantyPass);
        BankManager.withdraw(1, true, SILVERLIGHT);
        BankManager.withdraw(1, true, strangeImplement);
        BankManager.withdraw(1, true, BankManager.STAMINA_POTION[0]);
        BankManager.close(true);


    }

    public void startQuest() {
        cQuesterV2.status = "Starting Quest";
        PathingUtil.walkToArea(startArea);
        if (NpcChat.talkToNPC("Father Reen")) {
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation("That's me!");
            NPCInteraction.handleConversation("Yes.");
            NPCInteraction.handleConversation();
        }
    }

    public void makeInk() {
        cQuesterV2.status = "Making Ink";
        BankManager.open(true);
        BankManager.withdraw(1, true, VIAL);
        BankManager.withdraw(1, true, pestle);
        BankManager.withdraw(1, true, SILVERLIGHT);
        BankManager.withdraw(1, true, blackMushroom);
        BankManager.close(true);

        makeBlackSilverLightAndEquip();

    }

    public boolean makeBlackSilverLightAndEquip() {
        if (Inventory.find(blackMushroomInk).length == 0 && Inventory.find(VIAL).length >= 1) {
            // if we try without a vial we lose the mushroom
            cQuesterV2.status = "Making black ink";
            if (Utils.useItemOnItem(pestle, blackMushroom))
                Timer.waitCondition(() -> Inventory.find(blackMushroomInk).length == 1, 2500, 3500);
        }

        cQuesterV2.status = "Using ink on sword";
        if (Utils.useItemOnItem(blackMushroomInk, SILVERLIGHT)) {
            Timer.waitCondition(() -> Inventory.find(DARK_SILVERLIGHT).length == 1, 2500, 3500);
            General.sleep(250, 800);
        }

        if (Utils.equipItem(DARK_SILVERLIGHT))
            return Timer.waitCondition(() -> Equipment.isEquipped(DARK_SILVERLIGHT), 2500, 3500);

        return Equipment.isEquipped(DARK_SILVERLIGHT);
    }

    public void step2() {
        cQuesterV2.status = "Talking to Father Badden";
        PathingUtil.walkToArea(uzerArea);
        if (NpcChat.talkToNPC("Father Badden")) {
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation("Reen sent me.");
            NPCInteraction.handleConversation("Tell me more about Agrith-Naar.");
            NPCInteraction.handleConversation("Tell me more about Denath.");
            NPCInteraction.handleConversation("So what do you want me to do?");
            NPCInteraction.handleConversation("How can I do that?");
            NPCInteraction.handleConversation();
        }
    }

    public void step3() {
        if (!Equipment.isEquipped(DARK_SILVERLIGHT))
            makeBlackSilverLightAndEquip();

        cQuesterV2.status = "Going to Evil Dave";

        if (NPCs.findNearest("Evil Dave").length == 0)
            PathingUtil.walkToArea(topOfStairs, false);

        if (Utils.clickObj("Staircase", "Climb-down"))
            Timer.waitCondition(() -> bottomOfStairs.contains(Player.getPosition()), 7000, 9000);

        if (bottomOfStairs.contains(Player.getPosition()))
            PathingUtil.walkToTile(new RSTile(2721, 4908, 0), 2, true);

        if (makeBlackSilverLightAndEquip() && NpcChat.talkToNPC("Evil Dave")) {
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation("I want to join your group.");
            NPCInteraction.handleConversation("I'm evil!");
            NPCInteraction.handleConversation();
        }
    }


    public void step4() {
        cQuesterV2.status = "Sleeping before cutscene";
        Timer.waitCondition(() -> Utils.inCutScene(), 15000);
        cQuesterV2.status = "Cutscene idle";
        Utils.cutScene();

        cQuesterV2.status = "Getting code";
        NPCInteraction.waitForConversationWindow();
        if (NPCInteraction.isConversationWindowUp())
            NPCInteraction.handleConversation("What do I have to do?");

        if (!NPCInteraction.isConversationWindowUp()) {
            // talk to person, likely disconnected
        }
        getIncantation(false);
       /* if (NPCInteraction.isConversationWindowUp()) {
            for (int i = 0; i < 5; i++) {
                if (!NPCInteraction.isConversationWindowUp())
                    NpcChat.talkToNPC("Denath");

                RSInterface optionsInter = Interfaces.get(219, 1);

                RSInterface textInter = Interfaces.get(231, 5);
                RSInterface continueInter = Interfaces.get(231, 4);
                if (textInter != null &&
                        textInter.getText().contains("Tarren")) {
                    code = textInter.getText();
                    General.println("[Debug]: Code is: " + code);

                    return;
                } else if (optionsInter != null) {
                    InterfaceUtil.clickInterfaceText(219, 1, "I forgot the incantation.");
                    General.sleep(1000, 1500);
                } else if (continueInter != null) {
                    General.println("[Debug]: Clicking continue; i = " + i);
                    if (continueInter.click())
                        General.sleep(1500, 2500);
                }
            }
        }*/
    }

    boolean didWeGetCodeFromBook = false;

    /*public void parseCode() {
        if (code != null) {
            if (code.contains("The incantation is"))
                code1 = code.split("The incantation is ")[1];
            else
                code1 = code.replaceAll("'", "");

            code1 = code1.replaceAll("<br>", " "); //don't use general.stripformatting()
            code1 = General.stripFormatting(code1);
            General.println("[Debug]: Code (stripped): " + code1);
            General.println("[Debug]: Code[] Length: " + code1.length());
            if (code1.length() > 4) {
                code2 = code1.split(" ")[0];
                General.println("[Debug]: Code 1 " + code2);
                code3 = code1.split(" ")[1];
                General.println("[Debug]: Code 2 " + code3);
                code4 = code1.split(" ")[2];
                General.println("[Debug]: Code 3 " + code4);
                if (code1.split(" ")[3].contains("Agrith")) {
                    code5 = "Agrith-Naar";
                    General.println("[Debug]: Code 4 " + code5);

                    code6 = code1.split("Naar ")[1];
                    General.println("[Debug]: Code 5 " + code6);
                } else {
                    code5 = code1.split(" ")[3];
                    General.println("[Debug]: Code 4 " + code5);

                    code6 = code1.split(" ")[4].replaceAll("\\.", "");
                    General.println("[Debug]: Code 5 " + code6);
                }
            }
        }
    }*/


    public void getIncantation(boolean reverse) { // called at the ceremony
        cQuesterV2.status = "Getitng incantation";
        General.println("[Debug]: Getting incantation.");

        /*RSNPC[] denath = NPCs.find("Denath");
        if (denath.length > 0 && !denath[0].isClickable()) {
            if (PathingUtil.localNavigation(denath[0].getPosition())) {
                PathingUtil.movementIdle();
                General.sleep(250, 500);
            }
        }*/

        if (incantationOrder != null || (Utils.getVarBitValue(1374) == 0 && Utils.getVarBitValue(1375) == 0)) {
            return;
        }
        incantationOrder = new String[]{
                words.get(Utils.getVarBitValue(1373)),
                words.get(Utils.getVarBitValue(1374)),
                words.get(Utils.getVarBitValue(1375)),
                words.get(Utils.getVarBitValue(1376)),
                words.get(Utils.getVarBitValue(1377))
        };
        if (reverse) {
            ArrayUtils.reverse(incantationOrder);
        }
        String incantString = "Say the following in order: " + String.join(", ", incantationOrder);
        Log.log("[Debug]: " + incantString);
      /*  if (NpcChat.talkToNPC("Denath"))
            NPCInteraction.waitForConversationWindow();

        for (int i = 0; i < 10; i++) {
            if (!NPCInteraction.isConversationWindowUp())
                NpcChat.talkToNPC("Denath");

            RSInterface optionsInter = Interfaces.get(219, 1);

            RSInterface textInter = Interfaces.get(231, 5);
            RSInterface continueInter = Interfaces.get(231, 4);
            RSInterface myChat = Interfaces.get(217, 4);
            if (textInter != null &&
                    textInter.getText().contains("Tarren")) {
                code = textInter.getText();
                General.println("[Debug]: Code is: " + code);
                parseCode();
                return;
            } else if (optionsInter != null) {
                InterfaceUtil.clickInterfaceText(219, 1, "I forgot the incantation.");
                Timer.waitCondition(() -> Interfaces.get(217, 4) != null,
                        1500, 2500);
            } else if (continueInter != null) {
                General.println("[Debug]: Clicking continue; i = " + i);
                InterfaceUtil.clickInterfaceText(231, "Click here to continue");

                // if (continueInter.click())
                General.sleep(1500, 2500);
            } else if (myChat != null) {
                InterfaceUtil.clickInterfaceText(217, "Click here to continue");
                Timer.waitCondition(() -> Interfaces.get(217, 4) == null,
                        1500, 2500);
                General.sleep(1500, 2500);
            }*/
        General.sleep(250, 550);

    }

    public void step5() {
        //if (code == null)
        //   getIncantation(false);

        cQuesterV2.status = "Talking to Jennifer";
        if (Inventory.find(demonicSigilMould).length < 1) {
            RSNPC[] jennifer = NPCs.find("Jennifer");
            if (jennifer.length > 0) {
                if (PathingUtil.localNavigation(jennifer[0].getPosition())) {
                    PathingUtil.movementIdle();
                    General.sleep(250, 500);
                }
                if (NpcChat.talkToNPC("Jennifer")) {
                    NPCInteraction.waitForConversationWindow();
                    NPCInteraction.handleConversation("Do you have the demonic sigil mould?");
                    NPCInteraction.handleConversation();
                }
            }
        }
        cQuesterV2.status = "Talking to Matthew";
        RSNPC[] matthew = NPCs.find("Matthew");
        if (matthew.length > 0) {
            if (PathingUtil.localNavigation(matthew[0].getPosition())) {
                PathingUtil.movementIdle();
            }
            if (NpcChat.talkToNPC("Matthew")) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation("Do you know what happened to Josef?");
                NPCInteraction.handleConversation();
            }
        }
    }

    public void step6() {
        if (Inventory.find(demonicSigilMould).length == 0) {
            step5();
        }
        cQuesterV2.status = "Making Demonic sigil";
        General.println("[Debug]: " + cQuesterV2.status);
        if (Inventory.find(demonicSigil).length < 1 && Inventory.find(demonicSigilMould).length > 0) {
            if (Inventory.find(SILVER_BAR).length < 1) {
                PathingUtil.walkToArea(edgevillBank);
                BankManager.open(true);
                BankManager.withdraw(2, true, SILVER_BAR);
                BankManager.withdraw(1, true, ItemID.RING_OF_DUELING[0]);
                BankManager.close(true);
            }
            if (Inventory.find(SILVER_BAR).length > 0) {
                PathingUtil.walkToArea(edgevilleFurnace);

                if (Utils.clickObj("Furnace", "Smelt"))
                    Timer.waitCondition(() -> Interfaces.get(6, 30) != null, 5000, 8000);

                if (Interfaces.get(6, 30) != null && Interfaces.get(6, 30).click())
                    Timer.abc2WaitCondition(() -> Inventory.find(demonicSigil).length > 1, 7000, 10000);

            }
        }
    }

    public void step7() {
        if (Inventory.find(demonicSigil).length > 0) {
            cQuesterV2.status = "Going to uzer";
            General.println("[Debug]: " + cQuesterV2.status);
            PathingUtil.walkToArea(uzerArea);

            cQuesterV2.status = "Talking to The Golem";
            General.println("[Debug]: " + cQuesterV2.status);
            if (NpcChat.talkToNPC("Clay golem")) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation("Did you see anything happen last night?");
                NPCInteraction.handleConversation();
            }
        }
    }

    public void step8() {

        if (Inventory.find(demonicTomb).length < 1) {
            cQuesterV2.status = "Getting Demonic Tomb";
            PathingUtil.walkToArea(kiln1);
            kiln = Objects.find(20, 10243);
            if (kiln.length > 0) {
                if (AccurateMouse.click(kiln[0], "Look-in"))
                    Timer.waitCondition(() -> Inventory.find(demonicTomb).length > 0, 5000, 8000);
            }
        }
        if (Inventory.find(demonicTomb).length < 1) {
            PathingUtil.walkToArea(kiln2);
            kiln = Objects.find(20, 10244);
            if (kiln.length > 0) {
                if (AccurateMouse.click(kiln[0], "Look-in"))
                    Timer.waitCondition(() -> Inventory.find(demonicTomb).length > 0, 5000, 8000);
            }
        }
        if (Inventory.find(demonicTomb).length < 1) {
            PathingUtil.walkToArea(kiln3);
            kiln = Objects.find(20, 10242);
            if (kiln.length > 0 && AccurateMouse.click(kiln[0], "Look-in"))
                Timer.waitCondition(() -> Inventory.find(demonicTomb).length > 0, 5000, 8000);

        }
        if (Inventory.find(demonicTomb).length < 1) {
            PathingUtil.walkToArea(kiln4);
            kiln = Objects.find(20, 10245);
            if (kiln.length > 0 && AccurateMouse.click(kiln[0], "Look-in"))
                Timer.waitCondition(() -> Inventory.find(demonicTomb).length > 0, 5000, 8000);

        }
    }

    public void step9() {
        RSNPC[] matthew = NPCs.find("Matthew");
        if (matthew.length < 1) {
            cQuesterV2.status = "Going inside tomb";
            General.println("[Debug]: " + cQuesterV2.status);
            if (Inventory.find(demonicTomb).length > 0) {
                if (!topOfStairs.contains(Player.getPosition()) && !bottomOfStairs.contains(Player.getPosition()) && !dungeonEntranceArea.contains(Player.getPosition())) {
                    PathingUtil.walkToArea(topOfStairs);
                    stairs = Objects.findNearest(20, "Staircase");
                    if (stairs.length > 0) {
                        if (!stairs[0].isClickable())
                            stairs[0].adjustCameraTo();

                        if (AccurateMouse.click(stairs[0], "Climb-down"))
                            Timer.abc2WaitCondition(() -> bottomOfStairs.contains(Player.getPosition()), 7000, 10000);
                    }
                }
                if (bottomOfStairs.contains(Player.getPosition()) && Inventory.find(demonicTomb).length > 0) {
                    AccurateMouse.click(Inventory.find(demonicTomb)[0], "Read");
                    General.sleep(General.random(1500, 3000));
                    PathingUtil.walkToTile(new RSTile(2721, 4908, 0));
                }
                if (dungeonEntranceArea.contains(Player.getPosition())) {
                    if (Utils.clickObj("Door", "Enter")) {
                        Timer.waitCondition(() -> !dungeonEntranceArea.contains(Player.getPosition()), 7000, 9000);
                        Timer.waitCondition(() -> NPCs.find("Matthew").length > 0, 7000, 9000);
                    }
                }
            }
        }

        cQuesterV2.status = "Going to Matthew";
        matthew = NPCs.find("Matthew");
        if (matthew.length == 0) {
            PathingUtil.localNavigation(Player.getPosition().translate(0, 15));
            PathingUtil.movementIdle();
        }
        matthew = NPCs.find("Matthew");

        if (matthew.length > 0) {
            PathingUtil.localNavigation(matthew[0].getPosition());
            PathingUtil.movementIdle();
            cQuesterV2.status = "Talking to Matthew";
            if (NpcChat.talkToNPC("Matthew")) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation("Do you know what happened to Josef?");
                NPCInteraction.handleConversation();
            }

        }

    }

    public void step10() {
        NPCInteraction.waitForConversationWindow();
        if (NPCInteraction.isConversationWindowUp())
            NPCInteraction.handleConversation();


        getIncantation(false);

        cQuesterV2.status = "Going to chanting tile";
        RSNPC[] partrik = NPCs.find("Patrick");
        if (partrik.length > 0) {
            Walking.blindWalkTo(partrik[0].getPosition().translate(1, 2));
            General.sleep(General.random(2000, 3500));
        }

        List<String> codeList = new ArrayList<>(
                Arrays.asList(incantationOrder));

        for (String s : codeList) {
            General.println("[Debug]: " + s);
        }

        RSItem[] sigil = Inventory.find(demonicSigil);
        if (sigil.length > 0 && sigil[0].click("Chant")) {
            String[] strr = new String[codeList.size()];
            NPCInteraction.waitForConversationWindow();
            Waiting.waitNormal(1000, 100);
            int i = 0;
            for (String s : codeList) {
                i++;
                General.println("[Debug]: Handling code (" + i + "): " + s);
                NPCInteraction.handleConversation(s);
                // wait forchat interface with options to be null
                Timer.waitCondition(() -> Interfaces.get(219, 1) == null, 1500, 2500);
                Timer.waitCondition(() -> Interfaces.get(219, 1) != null, 3500, 4500);

            }
            General.sleep(5000, 6000);
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation();
        }
    }

    public void step11() {
        cQuesterV2.status = "Leaving";
        if (Utils.clickGroundItem(demonicSigil))
            Timer.abc2WaitCondition(() -> Inventory.find(demonicSigil).length > 1, 6000, 9000);

        if (!underGroundWholeArea.contains(Player.getPosition())) {
            door = Objects.findNearest(20, "Portal");
            if (door.length > 0) {
                Walking.blindWalkTo(door[0].getPosition());
                General.sleep(General.random(4000, 6000));

                if (Utils.clickObj("Portal", "Enter")) {
                    Timer.waitCondition(() -> dungeonEntranceArea.contains(Player.getPosition()), 7000, 10000);
                    General.sleep(General.random(4000, 6000));
                }
            }
        }
    }

    public void step11b() {
        cQuesterV2.status = "Talking to evil dave";
        Utils.clickGroundItem(demonicSigil);

        RSNPC[] evilDave = NPCs.find("Evil Dave");
        if (evilDave.length > 0 && PathingUtil.localNavigation(evilDave[0].getPosition()))
            PathingUtil.movementIdle();

        if (NpcChat.talkToNPC("Evil Dave")) {
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation("You've got to get back to the throne room!");
            NPCInteraction.handleConversation();
            Timer.waitCondition(() -> Utils.getVarBitValue(1382) == 1 &&
                    Utils.getVarBitValue(1380) == 2, 7000, 9000);
            Utils.shortSleep();
            // NPCInteraction.handleConversation();
        }
    }

    public void step12() {
        cQuesterV2.status = "Going outside";
        PathingUtil.walkToArea(bottomOfStairs);

        if (Utils.clickObj("Staircase", "Climb-up"))
            Timer.abc2WaitCondition(() -> topOfStairs.contains(Player.getPosition()), 7000, 11000);

        PathingUtil.walkToArea(uzerArea);

        talkToFatherReen();
        talkToFatherBadden();


    }

    public void talkToFatherReen() {
        cQuesterV2.status = "Talking to Father Reen";
        PathingUtil.walkToArea(uzerArea);
        if (NpcChat.talkToNPC("Father Reen")) {
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation("Oh, don't be so simple-minded!");
            NPCInteraction.handleConversation();
        }
    }

    public void talkToFatherBadden() {
        cQuesterV2.status = "Talking to Father Badden";
        PathingUtil.walkToArea(uzerArea);
        if (NpcChat.talkToNPC("Father Badden")) {
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation();
            NPCInteraction.handleConversation();
        }
    }

    public void step12b() {
        PathingUtil.walkToArea(uzerArea);
        General.println("[Debug]: Varbit for Golem (353) is " + Utils.getVarBitValue(GOLEM_VARBIT));
        cQuesterV2.status = "Using implement on golem";
        RSNPC[] golem = NPCs.findNearest(GOLEM_ID);
        if (golem.length > 0 &&
                Utils.useItemOnNPC(strangeImplement, GOLEM_ID)) {
            Timer.slowWaitCondition(() -> golem[0].getPosition().distanceTo(Player.getPosition()) < 1.5,
                    5500, 7000);
        }
        cQuesterV2.status = "Talking to golem";
        if (NpcChat.talkToNPC("Clay golem")) {
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation();
        }
    }


    public void getItemsFight() {
        if (NPCs.find("Matthew").length < 1) {
            cQuesterV2.status = "Getting Items";
            General.println("[Debug]: Getting Items");
            BankManager.open(true);
            BankManager.checkEquippedGlory();
            BankManager.depositAll(true);
            BankManager.withdraw(1, true, waterskin4);
            BankManager.withdrawArray(ItemID.NECKLACE_OF_PASSAGE, 1);
            BankManager.withdraw(1, true, BankManager.STAMINA_POTION[0]);
            BankManager.withdraw(3, true, prayerPotion4);
            BankManager.withdraw(1, true, demonicTomb);
            BankManager.withdraw(1, true, demonicSigil);
            BankManager.withdraw(1, true, superCombat4);
            BankManager.withdraw(1, true, DARK_SILVERLIGHT);
            if (Skills.getActualLevel(Skills.SKILLS.ATTACK) >= 60) {
                BankManager.withdraw(1, true, DRAGON_SWORD);
                Utils.equipItem(DRAGON_SWORD);
            } else {
                BankManager.withdraw(1, true, RUNE_SCIMITAR);
                Utils.equipItem(RUNE_SCIMITAR);
            }
            BankManager.withdraw(17, true, monkfish);
            BankManager.close(true);

            if (Prayer.getPrayerPoints() < 40) {
                Utils.clanWarsReset();
            }
        }
    }

    public void step13() {
        cQuesterV2.status = "Entering dungeon";
        /*** to Enter Dungeon****/
        if (NPCs.find("Matthew").length < 1) {
            if (topOfStairs.contains(Player.getPosition()) && !bottomOfStairs.contains(Player.getPosition()) && !dungeonEntranceArea.contains(Player.getPosition())) {
                PathingUtil.walkToArea(topOfStairs);
                stairs = Objects.findNearest(20, "Staircase");
                if (stairs.length > 0) {

                    if (!stairs[0].isClickable())
                        stairs[0].adjustCameraTo();

                    if (AccurateMouse.click(stairs[0], "Climb-down"))
                        Timer.abc2WaitCondition(() -> bottomOfStairs.contains(Player.getPosition()), 7000, 10000);
                }
            }
            if (bottomOfStairs.contains(Player.getPosition())) {
                if (AccurateMouse.click(Inventory.find(demonicTomb)[0], "Read"))
                    General.sleep(General.random(1500, 3000));

                PathingUtil.walkToTile(new RSTile(2721, 4908, 0));
            }

            if (dungeonEntranceArea.contains(Player.getPosition())) {
                if (Utils.clickObj("Door", "Enter"))
                    Timer.abc2WaitCondition(() -> !dungeonEntranceArea.contains(Player.getPosition()), 7000, 12000);
            } else {

                PathingUtil.walkToArea(dungeonEntranceArea);

                if (Utils.clickObj("Door", "Enter"))
                    General.sleep(General.random(4000, 7000));
            }
        }
    }

    public void step14() {
        cQuesterV2.status = "Talking to Matthew";
        RSNPC[] matthew = NPCs.find("Matthew");
        if (matthew.length < 1) {
            PathingUtil.localNavigation(Player.getPosition().translate(0, 10));
            PathingUtil.movementIdle();

        }
        matthew = NPCs.find("Matthew");
        if (matthew.length > 0) {
            cQuesterV2.status = "Going to Matthew";
            Walking.blindWalkTo(matthew[0].getPosition());
            RSNPC[] finalMatthew = matthew;
            Timer.waitCondition(() -> finalMatthew[0].getPosition().distanceTo(Player.getPosition()) < 3, 5000, 9000);
            if (NpcChat.talkToNPC("Matthew")) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation("Yes.");
                NPCInteraction.handleConversation();
            }
        }
    }

    int BOOK_INTERFACE = 392;
    int PAGE_NUM_INTERFACE = 10;
    int NEXT_PAGE_BUTTON = 78;

 /*   public void getIncantationFromBook() {
        if (code == null) {
            didWeGetCodeFromBook = true;
            RSInterface bookParent = Interfaces.get(BOOK_INTERFACE);
            RSItem[] tomb = Inventory.find(demonicTomb);
            if (bookParent == null && tomb.length > 0 && tomb[0].click("Read")) {
                Timer.waitCondition(() -> Interfaces.get(BOOK_INTERFACE) != null, 5000, 7000);
            }
            bookParent = Interfaces.get(BOOK_INTERFACE);
            if (bookParent != null) {
                RSInterface nextButton = Interfaces.get(BOOK_INTERFACE, NEXT_PAGE_BUTTON);

                for (int i = 0; i < 5; i++) {
                    General.sleep(100, 200);
                    if (!InterfaceUtil.checkText(BOOK_INTERFACE, PAGE_NUM_INTERFACE, "6")) {
                        // not on the right page
                        if (nextButton != null && nextButton.click())
                            General.sleep(1750, 3200);
                    }
                    if (InterfaceUtil.checkText(BOOK_INTERFACE, PAGE_NUM_INTERFACE, "6"))
                        break;
                }

                if (InterfaceUtil.checkText(BOOK_INTERFACE, PAGE_NUM_INTERFACE, "6")) {
                    // on the right page
                    // getting the code bottom up so we can use the same parse() method.
                    RSInterface line1 = Interfaces.get(BOOK_INTERFACE, 63);
                    RSInterface line2 = Interfaces.get(BOOK_INTERFACE, 64);
                    RSInterface line3 = Interfaces.get(BOOK_INTERFACE, 65);
                    if (line1 != null && line2 != null && line3 != null) {
                        String fullCode = (line1.getText() + " " + line2.getText() + " " + line3.getText());
                        General.println("[Debug]: Full code from book is: " + fullCode);
                        code = fullCode;
                        parseCode();
                    }
                }

            }

        }
    }*/


    public void step15() {
        getIncantation(true);
        cQuesterV2.status = "Chanting incantation";
        RSNPC[] matthew = NPCs.find("Matthew");
        Combat.setAutoRetaliate(false);

        if (matthew.length > 0) {
            Walking.blindWalkTo(matthew[0].getPosition().translate(2, 1));
            General.sleep(General.random(5900, 8000));
        }

        if (Skills.getActualLevel(Skills.SKILLS.ATTACK) >= 60) {
            Utils.equipItem(DRAGON_SWORD);
            General.sleep(100, 400);
        } else {
            Utils.equipItem(RUNE_SCIMITAR);
            General.sleep(100, 400);
        }


        RSItem[] sigil = Inventory.find(demonicSigil);

        //backwards list
        getIncantation(true);
        List<String> codeList = new ArrayList<>(
                Arrays.asList(incantationOrder));


        for (String s : codeList)
            General.println("[Debug]: " + s);

        if (sigil.length > 0 && sigil[0].click("Chant")) {

            NPCInteraction.waitForConversationWindow();
            Waiting.waitUniform(1000, 1200);
            int i = 0;
            for (String s : codeList) {
                i++;
                General.println("[Debug]: Handling code (" + i + "): " + s);
                NPCInteraction.handleConversation(s);
                // wait forchat interface with options to be null
                Timer.waitCondition(() -> Interfaces.get(219, 1) == null, 1500, 2500);
                Timer.waitCondition(() -> Interfaces.get(219, 1) != null, 3500, 4500);

            }
            General.sleep(5000, 6000);
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation();
        }
    }

    public void step16Fight() {
        cQuesterV2.status = "Fight";
        Combat.setAutoRetaliate(false);
        Prayer.enable(Prayer.PRAYERS.PROTECT_FROM_MAGIC);
        RSNPC[] demon = NPCs.find("Agrith Naar");
        RSObject[] pillar = Objects.findNearest(20, 724);
        Utils.setNPCAttackPreference();
        RSTile walkTile = null;
        if (pillar.length > 0)
            walkTile = pillar[0].getPosition().translate(0, 1);

        while (demon.length > 0 && Utils.getVarBitValue(1372) == 120 && pillar.length > 0) {
            General.sleep(General.random(50, 150));
            Prayer.enable(Prayer.PRAYERS.PROTECT_FROM_MAGIC);
            if (demon[0].getHealthPercent() < 0.12 && !Equipment.isEquipped(DARK_SILVERLIGHT))
                Utils.equipItem(DARK_SILVERLIGHT);

            if (walkTile == null)
                walkTile = pillar[0].getPosition().translate(0, 1);

            if (AccurateMouse.click(demon[0], "Attack")) {
                AccurateMouse.hoverScreenTileWalkHere(walkTile);
                General.sleep(General.random(700, 1100));
            }


            if (!pillar[0].isClickable())
                DaxCamera.focus(pillar[0]);

            if (AccurateMouse.walkScreenTile(pillar[0].getPosition().translate(0, 1))) {
                Timer.waitCondition(() -> Player.getPosition().equals(pillar[0].getPosition()
                                .translate(0, 1)),
                        3000, 4000);
            } else if (Walking.clickTileMS(pillar[0].getPosition().translate(0, 1), "Walk here"))
                Timer.waitCondition(() -> Player.getPosition().equals(pillar[0].getPosition().translate(-1, 1)),
                        3000, 4000);


            if (Combat.getHPRatio() < General.random(30, 50) && Inventory.find(monkfish).length > 0)
                if (AccurateMouse.click(Inventory.find(monkfish)[0], "Eat"))
                    General.sleep(General.random(300, 500));

            if (Prayer.getPrayerPoints() < General.random(10, 20)) {
                AccurateMouse.click(Inventory.find(ItemID.PRAYER_POTION)[0], "Drink");
                General.sleep(General.random(300, 500));
            }
            demon = NPCs.find("Agrith Naar");
            // add drinking super combat
        }
    }

    private void finishQuest() {
        Prayer.disable(Prayer.PRAYERS.PROTECT_FROM_MAGIC);
        General.sleep(400, 1200);
        if (Equipment.isEquipped(6746)) {
            AccurateMouse.click(Equipment.find(6746)[0], "Remove");
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation();
        }
        //need to choose reward
        NPCInteraction.waitForConversationWindow();
        selectReward();
    }


    public void selectReward() {
        if (NPCInteraction.isConversationWindowUp()) {
            if (!InterfaceUtil.clickInterfaceText(219, "Ranged")) {
                if (InterfaceUtil.clickInterfaceText(219, "More"))
                    General.sleep(2000, 4000);
                InterfaceUtil.clickInterfaceText(219, "Ranged");
            }
        }
    }

    @Override
    public void execute() {
        checkRequirements();
        General.sleep(100);
        General.println("Game setting = " + Game.getSetting(602));
        General.println("VarBit 1372 = " + RSVarBit.get(1372).getValue());
        General.println("VarBit 1379 = " + RSVarBit.get(1379).getValue());
        General.println("VarBit 1380 = " + RSVarBit.get(1380).getValue());
        General.println("VarBit 1381 = " + RSVarBit.get(1381).getValue());
        General.println("VarBit 1382 = " + RSVarBit.get(1382).getValue());
        if (Game.getSetting(602) == 0) {
            buyItems();
            makeInk();
            getItems1();
            startQuest();
        }
        if (Game.getSetting(602) == 268435466) {
            step2();
        }
        if (Game.getSetting(602) == 268435476) {
            step3();
        }
        if (Game.getSetting(602) == 1342177310 || RSVarBit.get(1372).getValue() == 20) { //setting is consistent until here, then it changes after step 4
            step4();
            setting = Game.getSetting(602);
        }
        if (RSVarBit.get(1372).getValue() == 30 || RSVarBit.get(1372).getValue() == 40) {
            General.println("step 5: Game setting = " + Game.getSetting(602));
            step5();
        }
        if (Game.getSetting(602) == setting + 10 || RSVarBit.get(1372).getValue() == 50) {
            step6(); // making sigil game setting 2224 changes from 0->1 here
            step7();
        }
        if (RSVarBit.get(1372).getValue() == 60) { //13829662) {
            step8();
            step9();
        }
        if (RSVarBit.get(1372).getValue() == 70 &&
                RSVarBit.get(1379).getValue() == 0
                && RSVarBit.get(1380).getValue() == 0
                && RSVarBit.get(1381).getValue() == 1
                && RSVarBit.get(1382).getValue() == 1) {
            step8();
            step9();
        } else if (RSVarBit.get(1372).getValue() == 80) {// the one that is 10 before this is just between the time you're done talking to mathew and when dave starts takling
            getIncantation(false);
            step10(); // doing chant
        } else if (RSVarBit.get(1372).getValue() == 90) {
            step11(); // get gr1ound sigil
        } else if (RSVarBit.get(1372).getValue() == 100 &&
                (RSVarBit.get(1380).getValue() == 2 ||
                        Utils.getVarBitValue(1380) == 1)) {// 80938566) {difference of 1,246,730
            General.println("[Debug]: Step 11b");
            step11b();
        } else if (Utils.getVarBitValue(1372) == 100 &&
                RSVarBit.get(1379).getValue() == 0 &&
                RSVarBit.get(1380).getValue() == 3 &&
                RSVarBit.get(1381).getValue() == 1 &&
                RSVarBit.get(1382).getValue() == 1) {// 215156294) {difference of 1,246,730
            General.println("Step 12");
            step12();
            talkToFatherReen();
        } else if (RSVarBit.get(1372).getValue() == 100 &&
                RSVarBit.get(1379).getValue() == 0 &&
                RSVarBit.get(1380).getValue() == 3 &&
                RSVarBit.get(1381).getValue() == 2 &&
                RSVarBit.get(1382).getValue() == 1) {
            talkToFatherBadden();
        } else if (RSVarBit.get(1372).getValue() == 100 &&
                RSVarBit.get(1379).getValue() == 0 &&
                RSVarBit.get(1380).getValue() == 3 &&
                RSVarBit.get(1381).getValue() == 3 &&
                RSVarBit.get(1382).getValue() == 3) {
            General.println("Step 12b");
            step12b();
        } else if (RSVarBit.get(1372).getValue() == 100
                && RSVarBit.get(1379).getValue() < 3
                && RSVarBit.get(1380).getValue() == 3
                && RSVarBit.get(1381).getValue() == 3
                && RSVarBit.get(1382).getValue() == 3) {// 215156294) {difference of 1,246,730
            General.println("[Debug]: Step 12b");
            step12b();
        } else if (RSVarBit.get(1372).getValue() == 100
                && RSVarBit.get(1379).getValue() == 3
                && RSVarBit.get(1380).getValue() == 3
                && RSVarBit.get(1381).getValue() == 3
                && RSVarBit.get(1382).getValue() == 3) {
            getItemsFight();
            General.println("[Debug]: Step 13/14");
            step13();

            step14();
        } else if (RSVarBit.get(1372).getValue() == 110) {

            step15();
        }
        if (RSVarBit.get(1372).getValue() == 120) {
            step16Fight();
        }
        if (RSVarBit.get(1372).getValue() == 124) {
            finishQuest(); // unequip darklight
        }
        if (RSVarBit.get(1372).getValue() == 125) {
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
        return "Shadow Of The Storm";
    }

    @Override
    public boolean checkRequirements() {
        if (Game.getSetting(437) != 413290 ||
                Skills.getActualLevel(Skills.SKILLS.ATTACK) < 40) {
            General.println("Debug]: Missing a requirement");
            return false;
        }
        return true;
    }
}
