package scripts.QuestPackages.RfdLumbridgeGuide;


import com.trilezstudios.updater.hooks.NPC;
import dax.walker.utils.AccurateMouse;
import dax.walker_engine.interaction_handling.NPCInteraction;
import org.tribot.api.General;
import org.tribot.api2007.*;
import org.tribot.api2007.types.*;
import org.tribot.script.sdk.ChatScreen;
import org.tribot.script.sdk.Waiting;
import scripts.*;
import scripts.GEManager.GEItem;
import scripts.QuestPackages.WitchsPotion.WitchsPotion;
import scripts.QuestSteps.BuyItemsStep;
import scripts.QuestSteps.NPCStep;
import scripts.QuestSteps.QuestTask;
import scripts.Requirements.*;
import scripts.Tasks.Priority;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class RfdLumbridgeGuide implements QuestTask {


    private static RfdLumbridgeGuide quest;

    public static RfdLumbridgeGuide get() {
        return quest == null ? quest = new RfdLumbridgeGuide() : quest;
    }

    //   RSArea  diningRoom = new RSArea(new RSTile(1856, 5313, 0), new RSTile(1870, 5333, 0));
    RSArea upstairsTrailborn = new RSArea(new RSTile(3100, 3152, 1), new RSTile(3117, 3168, 1));
    //  RSArea quizSpot = new RSArea(new RSTile(2579, 4625, 0), new RSTile(2579, 4625, 0));
    AreaRequirement inTrailbornRoom = new AreaRequirement(upstairsTrailborn);
    int BUCKET_OF_MILK = 1927;
    int EGG = 1944;
    int POT_OF_FLOUR = 1933;
    int CAKE_TIN = 1887;
    /**
     * NPCs for Quiz
     */
    int UNFERTH = 1182;
    //int unknown = 1185;
    int RELDO = 1191;
    int ALI_MORRISANE = 1189;
    int ISLWYN = 1186;
    int VELORINA = 1990;
    int HETTY = 1187;
    int KING_LATHAS = 1184;
    int GERTRUDE = 1183;
    int PIRATE_PETE = 1185;


    int ENCHANTED_EGG = 7544;
    int ENCHANTED_FLOUR = 7546;
    int ENCHANTED_MILK = 7545;
    int RAW_GUIDE_CAKE = 7543;
    int CAKE = 7542;


    boolean eggEnchanted = Utils.getVarBitValue(1894) == 1;
    boolean flourEnchanted = Utils.getVarBitValue(1897) == 1;
    boolean milkEnchanted = Utils.getVarBitValue(1898) == 1;

    RSArea WIZARDS_TOWER_AREA = new RSArea(new RSTile(3115, 3160, 1), new RSTile(3111, 3165, 1));
    RSArea lumbridge = new RSArea(new RSTile(3213, 3225, 0), new RSTile(3216, 3219, 0));
    RSArea KITCHEN = new RSArea(new RSTile(3211, 3212, 0), new RSTile(3206, 3217, 0));
    RSArea LARGE_KITCHEN = new RSArea(new RSTile(3212, 3212, 0), new RSTile(3205, 3217, 0));

    String question1 = "combat level of goblins near lumbridge";
    String answer1 = "2";
    String question2 = "defence requirements to wear a";
    String answer2 = "20";
    String question3 = "glass vial is to 33 as a glass";
    String answer3 = "46";
    String question4 = "gold ring is to 5 as a holy symbol";
    String answer4 = "16";
    String question5 = "guild closest to fishing platform";
    String answer5 = "Legends' Guild";
    String question6 = "how much gp does it take to bribe a guard";
    String answer6 = "10";
    String question7 = "i can hear howling in one direction";
    String answer7 = " Catherby";

    String question8 = "if i'm going to need glass";
    String answer8 = "Sand, bucket, soda ash, glassblowing pipe";

    String question9 = "i'm in a bar west of pollnivneach";
    String answer9 = "Bandit Camp";
    String question10 = "Take the number of fire runes";
    String answer10 = "10";
    String question11 = "The River Salve runs from";
    String answer11 = "North to south";
    String question12 = "What doesn't lie between";
    String answer12 = "Keep Le Faye";

    String question13 = "What Ingredients are used in";
    String answer13 = "Flour, Eggs, milk";

    String question14 = "Which tower is closest to the";
    String answer14 = "Dark Wizards'";
    String question15 = "What is the defence level requirement";
    String answer15 = "20";
    String question16 = "What is the nearest guild to the Fishing?";
    String answer16 = "Legends'";

    String[] ANSWER_ARRAY = new String[]{
            answer1, answer2, answer3, answer4, answer5, answer6, answer7, answer8, answer9,
            answer10, answer11, answer12, answer13, answer14, answer15, answer16
    };

    NPCStep answerQuestions = new NPCStep("Traiborn", new RSTile(3112, 3162, 1));
    NPCStep  answerRuneQuestion = new NPCStep( "Traiborn", new RSTile(3112, 3162, 1));
    NPCStep answerWeaponQuestion = new NPCStep("Traiborn", new RSTile(3112, 3162, 1));


    public void addDialog() {
        answerQuestions.addDialogStep("Ask about helping the Lumbridge Guide.", "Okay. Let's start!", "Okay. I'm ready!", "Quiz me!");
        answerQuestions.addDialogStep("Unferth", "Gertrude", "King Lathas", "Pirate Pete", "Islwyn", "Hetty", "Gronigen", "Ali Morrisane", "Velorina", "Reldo");
        answerQuestions.addDialogStep("Bandit camp", "Flour, Eggs and milk", "20", "Sand, bucket, soda ash, glass blowing pipe", "16", "North to South", "46", "2", "Keep Le Faye", "Dark Wizards'", "Catherby", "Legends'");
        answerQuestions.addDialogStep("Trout", "Mind Talisman", "Guthix Prayer Book, Magic Logs, Pike");
        answerQuestions.addDialogStep("10"); // Answer to bribe question
        answerQuestions.addDialogStep("10"); // Answer to Fire runes question
        answerWeaponQuestion.addDialogStep("Three");
        answerRuneQuestion.addDialogStep("Two");
    }

    public String getQuizNpc() {

        while (NPCs.find("? ? ? ?").length == 0) {
            General.sleep(50, 100);
        }

        RSNPC[] n = NPCs.find("? ? ? ?");
        if (n.length > 0)
            General.println("[NPC detected]" + n[0].getID());

        for (RSNPC npc : n) {
            if (npc.getID() == RELDO) {
                General.println("[Debug]: Detected Reldo");
                General.sleep(2000);
                return "Reldo";

            } else if (npc.getID() == UNFERTH) {
                General.println("[Debug]: Detected Unferth");
                General.sleep(2000);
                return "Unferth";

            } else if (npc.getID() == ISLWYN) {
                General.println("[Debug]: Detected Islwyn");
                General.sleep(2000);
                return "Islwyn";
            } else if (npc.getID() == ALI_MORRISANE) {
                General.println("[Debug]: Detected Ali Morrisane");
                General.sleep(2000);
                return "Ali Morrisane";
            } else if (npc.getID() == VELORINA) {
                General.println("[Debug]: Detected Verlorina");
                General.sleep(2000);
                return "Velorina";
            } else if (npc.getID() == HETTY) {
                General.println("[Debug]: Detected Hetty");
                General.sleep(2000);
                return "Hetty";
            } else if (npc.getID() == KING_LATHAS) {
                General.println("[Debug]: Detected King Lathas");
                General.sleep(2000);
                return "King Lathas";
            } else if (npc.getID() == PIRATE_PETE) {
                General.println("[Debug]: Detected pirate pete");
                General.sleep(2000);
                return "Pirate Pete";
            } else if (npc.getID() == GERTRUDE) {
                General.println("[Debug]: Detected gertrude");
                General.sleep(2000);
                return "Gertrude";
            }
        }
        return null;
    }

    ArrayList<GEItem> itemsToBuy = new ArrayList<GEItem>(
            Arrays.asList(
                    new GEItem(ItemID.BUCKET_OF_MILK, 1, 500),
                    new GEItem(ItemID.EGG, 1, 500),
                    new GEItem(ItemID.CAKE_TIN, 1, 500),
                    new GEItem(ItemID.POT_OF_FLOUR, 1, 300),
                    new GEItem(ItemID.NECKLACE_OF_PASSAGE5, 1, 200),
                    new GEItem(ItemID.LUMBRIDGE_TELEPORT, 5, 300),
                    new GEItem(ItemID.STAMINA_POTION[0], 2, 15),
                    new GEItem(ItemID.AMULET_OF_GLORY[2], 1, 15),
                    new GEItem(ItemID.RING_OF_WEALTH[0], 1, 25)
            )
    );


    InventoryRequirement initialItemReqs = new InventoryRequirement(new ArrayList<>(
            Arrays.asList(
                    new ItemReq(ItemID.STAMINA_POTION[0], 2, 0),
                    new ItemReq(ItemID.AMULET_OF_GLORY[2], 1, 0, true, true),
                    new ItemReq(ItemID.BUCKET_OF_MILK, 1),
                    new ItemReq(ItemID.EGG, 1),
                    new ItemReq(ItemID.CAKE_TIN, 1),
                    new ItemReq(ItemID.POT_OF_FLOUR, 1),
                    new ItemReq(ItemID.LUMBRIDGE_TELEPORT, 3, 1),
                    new ItemReq(ItemID.NECKLACE_OF_PASSAGE5, 1, 0),
                    new ItemReq(ItemID.RING_OF_WEALTH[0], 1, 0, true)
            )
    ));
    BuyItemsStep buyStep = new BuyItemsStep(itemsToBuy);

    public void buyItems() {
        if (!initialItemReqs.check()) {
            cQuesterV2.status = "Buying Items";
            buyStep.buyItems();
        }
    }

    public void getItems() {
        if (!initialItemReqs.check()) {
            cQuesterV2.status = "Getting Items";
            General.println("[Debug]: " + cQuesterV2.status);
            initialItemReqs.withdrawItems();
        }
    }

    public void goToStart() {
        cQuesterV2.status = "Going to Start";
        General.println("[Debug]: " + cQuesterV2.status);
        if (Objects.findNearest(20, 12339).length < 1) {
            PathingUtil.walkToArea(lumbridge, true);

            RSObject[] door = Objects.findNearest(20, "Large door");
            if (door.length > 0 && Utils.clickObject(door[0], "Open")) {
                Timer.waitCondition(() -> Objects.findNearest(20, "Gypsy").length > 0, 8000);
                General.sleep(5000, 8000);
            }
        }
        RSObject[] guide = Objects.findNearest(20, 12339);
        if (guide.length > 0 && Utils.clickObject(guide[0], "Inspect")) {
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation("Yes, I'm sure I can make a cake.");
            NPCInteraction.handleConversation();

        }
    }

    public void step2() {
        cQuesterV2.status = "Going to Traiborn";
        PathingUtil.walkToArea(WIZARDS_TOWER_AREA);
        if (NpcChat.talkToNPC("Traiborn")) {
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation("Ask about helping the Lumbridge Guide.");
            NPCInteraction.handleConversation();
        }
    }

    public void startQuiz() {
        if (!inTrailbornRoom.check() && !Game.isInInstance()){
            cQuesterV2.status = "Going to Traiborn";
            PathingUtil.walkToArea(WIZARDS_TOWER_AREA);
        }


        cQuesterV2.status = "Starting Quiz";
        if (!NPCInteraction.isConversationWindowUp() && NpcChat.talkToNPC("Traiborn")) {
            NPCInteraction.waitForConversationWindow();
        }
        for (int i = 0; i < 30; i++) {
            if (NPCInteraction.isConversationWindowUp()) {
                NPCInteraction.handleConversation("Ask about helping the Lumbridge Guide.", "Okay. Let's start!", "Quiz me!");
                NPCInteraction.handleConversation();
                // NPCInteraction.handleConversation();
            }
            General.println("waiting for NPC");
            Timer.waitCondition(() -> NPCs.find("? ? ? ?").length > 0, 20000);
            String npc = getQuizNpc();
            Timer.waitCondition(() -> NPCs.find("? ? ? ?").length == 0 && !Game.isInInstance(), 20000);
            Utils.shortSleep();
            if (npc != null) {
                General.println("waiting for chat");
                NPCInteraction.waitForConversationWindow();

                General.println("Answering question with " + npc);
                if (NPCInteraction.isConversationWindowUp())
                    NPCInteraction.handleConversation(npc);
            }
            if(RSVarBit.get(1894).getValue() == 1)
                break;
        }

    }

    String question;

    public void milkQuiz() {
        cQuesterV2.status = "Milk Quiz";
        PathingUtil.walkToArea(WIZARDS_TOWER_AREA);
        if (!NPCInteraction.isConversationWindowUp() && NpcChat.talkToNPC("Traiborn")) {
            NPCInteraction.waitForConversationWindow();
        }
        if (NPCInteraction.isConversationWindowUp()) {
            NPCInteraction.handleConversation("Ask about helping the Lumbridge Guide.");
            if (InterfaceUtil.clickInterfaceText(219, 1, "Okay. I'm ready!"))
                Timer.waitCondition(() -> Interfaces.isInterfaceSubstantiated(217, 3), 5000);
            if (Interfaces.get(217, 3) != null) {
                General.println("[Debug] Clicking continue");
                for (int i = 0; i < 3; i++) {

                    InterfaceUtil.clickInterfaceText(217, "Click here to continue");
                    Timer.waitCondition(() -> Interfaces.get(231, 4) != null, 5000);
                }
            }
            Timer.waitCondition(() -> Interfaces.get(231, 4) != null, 5000);
            if (Interfaces.isInterfaceSubstantiated(231, 3)) {
                General.println("Selecting 231, 4");
                if (Interfaces.get(231, 4).click())
                    Timer.waitCondition(() -> Interfaces.get(229, 2) != null, 5000);
            }

            Timer.waitCondition(() -> Interfaces.get(229, 2) != null, 5000);
            if (Interfaces.get(229, 2) != null) {
                General.println("Selecting 229, 2");
                Interfaces.get(229, 2).click();
            }

            answerQuestion();
            answerQuestion();
            answerQuestion();
            NPCInteraction.waitForConversationWindow();

        }
    }

    public void answerQuestion() {
        General.println("[Debug]: Answering question");
        Timer.waitCondition(() -> Interfaces.isInterfaceSubstantiated(231, 5), 5000);
        RSInterface questionInterface = Interfaces.get(231, 4);
        NPCInteraction.handleConversation(ANSWER_ARRAY);
       /* if (questionInterface != null) {
            Optional<String> question = ChatScreen.getMessage();
            if (question.isEmpty()) return;

            General.println("[Debug]: Question is: " + question);
            Utils.idle(300, 1000);
            //questionInterface.click();
            ChatScreen.clickContinue();

            Utils.idle(700, 1500);

            if (General.stripFormatting(question.get()).toLowerCase().contains(question1)) {
                NPCInteraction.handleConversation(answer1);
            } else if (General.stripFormatting(question.get()).toLowerCase().contains(question2.toLowerCase())) {
                NPCInteraction.handleConversation(answer2);
            } else if (General.stripFormatting(question.get()).toLowerCase().contains(question3.toLowerCase())) {
                NPCInteraction.handleConversation(answer3);
            } else if (General.stripFormatting(question.get()).toLowerCase().contains(question4.toLowerCase())) {
                NPCInteraction.handleConversation(answer4);
            } else if (General.stripFormatting(question.get()).toLowerCase().contains(question5.toLowerCase())) {
                NPCInteraction.handleConversation(answer5);
            } else if (General.stripFormatting(question.get()).toLowerCase().contains(question6.toLowerCase())) {
                NPCInteraction.handleConversation(answer6);
            } else if (General.stripFormatting(question.get()).toLowerCase().contains(question7.toLowerCase())) {
                NPCInteraction.handleConversation(answer7);
            } else if (General.stripFormatting(question.get()).toLowerCase().contains(question8.toLowerCase())) {
                NPCInteraction.handleConversation(answer8);
            } else if (General.stripFormatting(question.get()).toLowerCase().contains(question9.toLowerCase())) {
                NPCInteraction.handleConversation(answer9);
            } else if (General.stripFormatting(question.get()).toLowerCase().contains(question10.toLowerCase())) {
                NPCInteraction.handleConversation(answer10);
            } else if (General.stripFormatting(question.get()).toLowerCase().contains(question11.toLowerCase())) {
                NPCInteraction.handleConversation(answer11);
            } else if (General.stripFormatting(question.get()).toLowerCase().contains(question12.toLowerCase())) {
                NPCInteraction.handleConversation(answer12);
            } else if (General.stripFormatting(question.get()).toLowerCase().contains(question13.toLowerCase())) {
                NPCInteraction.handleConversation(answer13);
            } else if (General.stripFormatting(question.get()).toLowerCase().contains(question14.toLowerCase())) {
                NPCInteraction.handleConversation(answer14);
            } else if (General.stripFormatting(question.get()).toLowerCase().contains(question15.toLowerCase())) {
                NPCInteraction.handleConversation(answer15);
            } else if (General.stripFormatting(question.get()).toLowerCase().contains(question16.toLowerCase())) {
                NPCInteraction.handleConversation(answer16);
            }
            else if (General.stripFormatting(question.get()).toLowerCase().contains("a gold ring is to 5 as a holy symbol is to")) {
                NPCInteraction.handleConversation("16");
            }
            else if (General.stripFormatting(question.get()).toLowerCase().contains("i can hear howling in one direction and buzzing in the other—where am i?")) {
                NPCInteraction.handleConversation("Catherby");
            }else {
                General.println("[Debug]: No match");

            }
        }
        Timer.waitCondition(() -> Interfaces.get(231, 4) != null, 5000);
        if (Interfaces.get(231, 3) != null) {
            General.println("Selecting 231, 4");
            Interfaces.get(231, 3).click();
        }

        Timer.waitCondition(() -> Interfaces.get(229, 2) != null, 5000);
        if (Interfaces.get(229, 2) != null) {
            General.println("Selecting 229, 2");
            Interfaces.get(229, 2).click();
        }*/
    }

    public void finalQuiz() {
        // needs to loop 3 times without trying to talk to him again.
        cQuesterV2.status = "Final Quiz";
        PathingUtil.walkToArea(WIZARDS_TOWER_AREA);

        if (!NPCInteraction.isConversationWindowUp() &&
                !Interfaces.isInterfaceSubstantiated(264, 24) &&
                NpcChat.talkToNPC("Traiborn")) {
            NPCInteraction.waitForConversationWindow();
        }
        if (NPCInteraction.isConversationWindowUp()) {
            NPCInteraction.handleConversation("Ask about helping the Lumbridge Guide.", "Quiz me!");
            NPCInteraction.handleConversation("Quiz me!");
            NPCInteraction.handleConversation();
            Timer.waitCondition(() -> Interfaces.isInterfaceSubstantiated(264, 24), 5000);
        }
        if (Interfaces.isInterfaceSubstantiated(264, 24)) {
            Interfaces.get(264, 24).click();
            Timer.waitCondition(() -> !Interfaces.isInterfaceSubstantiated(264, 24), 5000);
        }
        NPCInteraction.waitForConversationWindow();
        NPCInteraction.handleConversation("Mind Talisman", "Trout", "Three", "Two",
                "Guthix Prayer Book, Magic Logs, Pike");
        NPCInteraction.handleConversation();
        Utils.shortSleep();

    }

    public void step6() {
        Utils.useItemOnItem(ENCHANTED_EGG, CAKE_TIN);

        PathingUtil.walkToArea(KITCHEN, true);

        if (LARGE_KITCHEN.contains(Player.getPosition())) {
            if (Utils.useItemOnObject(RAW_GUIDE_CAKE, 114))
                Timer.waitCondition(() -> Inventory.find(RAW_GUIDE_CAKE).length < 1, 16000);

            Utils.idle(1000, 4000);
        }
    }

    int LUMBRIDGE_GUIDE_OBJECT_ID = 12339;

    public void finishQuest() {
        cQuesterV2.status = "Going to Finish";
        General.println("[Debug]: " + cQuesterV2.status);
        if (Objects.findNearest(20, 12339).length < 1) {
            if (LARGE_KITCHEN.contains(Player.getPosition())) {
                if (Utils.clickObj(12348, "Open")) {
                    Timer.waitCondition(() -> Objects.findNearest(20, "Gypsy").length > 0, 8000);
                    General.sleep(General.random(5000, 8000));
                }
            }
        }
        RSObject[] guide = Objects.findNearest(20, LUMBRIDGE_GUIDE_OBJECT_ID);
        if (guide.length > 0) {
            Walking.blindWalkTo(guide[0].getPosition());

            Utils.idle(2000, 4000);
            guide[0].adjustCameraTo();
            AccurateMouse.click(Inventory.find(CAKE)[0], "Use");
            Utils.idle(200, 800);
            AccurateMouse.click(guide[0], "Use");
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation("Yes, I'm sure I can make a cake.");
            NPCInteraction.handleConversation();

        }
    }

    @Override
    public void execute() {
        addDialog();
        General.println("Game setting 683 = " + Game.getSetting(683));
        General.println("Varbit 1894 = " + Utils.getVarBitValue(1894));
        General.println("Varbit 1896 = " + Utils.getVarBitValue(1896));
        General.println("Varbit 1898 = " + Utils.getVarBitValue(1898));
        int startVarBitID = QuestVarbits.QUEST_RECIPE_FOR_DISASTER_LUMBRIDGE_GUIDE.getId();

        if (RSVarBit.get(startVarBitID).getValue() == 0) { //RSVARBIT 1896 is 0 here
            buyItems();
            getItems();
            goToStart();
        } else if (RSVarBit.get(1898).getValue() == 0
                && RSVarBit.get(1894).getValue() == 0
                && RSVarBit.get(startVarBitID).getValue() == 1) {
            step2();

        } else if (RSVarBit.get(1898).getValue() == 0
                && RSVarBit.get(1894).getValue() == 0
                && RSVarBit.get(startVarBitID).getValue() == 2) {  // RSVARBIT 1896 is 2 here
            startQuiz();

        } else if (RSVarBit.get(1898).getValue() == 0
                && RSVarBit.get(1894).getValue() == 1
                && RSVarBit.get(startVarBitID).getValue() == 2) {

            milkQuiz();
        } else if (RSVarBit.get(1898).getValue() == 1
                && RSVarBit.get(1894).getValue() == 1
                && RSVarBit.get(startVarBitID).getValue() == 2) {
            finalQuiz();

        } else if (RSVarBit.get(1894).getValue() == 1 &&
                RSVarBit.get(1898).getValue() == 1 && RSVarBit.get(1899).getValue() == 1
                && Utils.getVarBitValue(startVarBitID) == 3) {
            step6();
        } else if (RSVarBit.get(startVarBitID).getValue() == 4) {
            finishQuest();
        } else if (RSVarBit.get(startVarBitID).getValue() == 5) {
            Utils.closeQuestCompletionWindow();
            cQuesterV2.taskList.remove(this);
        }
        Waiting.waitNormal(300, 25);
    }

    @Override
    public Priority priority() {
        return Priority.LOW;
    }

    @Override
    public boolean validate() {
        return cQuesterV2.taskList.get(0).equals(this) && checkRequirements();
    }


    @Override
    public String questName() {
        return "RFD Lumbridge Guide";
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