package scripts.QuestPackages.DigSite;

import dax.walker.utils.AccurateMouse;
import dax.walker_engine.interaction_handling.NPCInteraction;
import org.tribot.api.General;
import org.tribot.api2007.*;
import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSNPC;
import org.tribot.api2007.types.RSObject;
import org.tribot.api2007.types.RSTile;
import org.tribot.script.sdk.Quest;
import scripts.*;
import scripts.GEManager.GEItem;
import scripts.QuestPackages.DoricsQuest.DoricsQuest;
import scripts.QuestSteps.BuyItemsStep;
import scripts.QuestSteps.QuestTask;
import scripts.Requirements.ItemRequirement;
import scripts.Requirements.Requirement;
import scripts.Tasks.Priority;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DigSite implements QuestTask {

    private static DigSite quest;

    public static DigSite get() {
        return quest == null ? quest = new DigSite() : quest;
    }



    int PESTLE_AND_MOTAR = 233;
    int VIAL = 229; // make sure walker doesn't drop it
    int CUP_OF_TEA = 1978;
    int OPAL = 1609;
    int CHARCOAL = 973;
    int LEATHER_BOOTS = 1061;
    int LEATHER_GLOVES = 1059;
    int SPECIMEN_BRUSH = 670;
    int SPECIMEN_JAR = 669;
    int PANNING_TRAY = 677;
    int TROWEL = 676;
    int ROPE = 954;
    int DIGSITE_TELEPORT = 12403;
    int TEDDY = 673;
    int ANIMAL_SKULL = 671;
    int SPADE = 952;
    int FULL_PAN = 679;
    int SPECIAL_CUP = 672;
    int ANCIENT_TALISMAN = 681;
    int TINDERBOX = 590;
    int ARCENIA_ROOT = 708;
    int CHEST_KEY = 709;
    int INVITATION_LETTER = 696;
    int CHEMICAL_POWDER = 700;
    int UNIDENTIFIED_LIQUID = 702;
    int GROUND_CHARCOAL = 704;
    int AMMONIUM_NITRATE = 701;
    int NITROGYLCERIN = 703;
    int MIXED_CHEMICALS = 705;
    int MIXED_CHEMICALS2 = 706;
    int CHEMICAL_COMPOUND = 707;
    int STONE_TABLET = 699;

    /**
     * JUNK TO DROP
     */
    int BROKEN_ARROW = 687;
    int BONES = 526;
    int DAMAGED_ARMOUR = 697;
    int BROKEN_STAFF = 689;
    int BELT_BUCKLE = 684;
    int BUTTONS = 688;
    int NEEDLE = 1733;
    int PIE_DISH = 2313;
    int IRON_KNIFE = 863;

    int BARREL_ID = 2359;
    int ARCHAEOLOGICAL_EXPERT = 3639;

    RSArea START_AREA = new RSArea(new RSTile(3366, 3334, 0), new RSTile(3360, 3340, 0));
    RSArea VARROCK_MUSEUM = new RSArea(new RSTile(3263, 3447, 0), new RSTile(3253, 3455, 0));
    RSArea TEDDY_AREA = new RSArea(new RSTile(3360, 3370, 0), new RSTile(3356, 3373, 0));
    RSArea WORKMAN_AREA = new RSArea(new RSTile(3358, 3382, 0), new RSTile(3352, 3389, 0));
    RSArea STUDENT_AREA = new RSArea(new RSTile(3349, 3424, 0), new RSTile(3345, 3430, 0));
    RSArea STUDENT2_AREA = new RSArea(new RSTile(3358, 3400, 0), new RSTile(3366, 3395, 0));
    RSArea STUDENT3_AREA = new RSArea(new RSTile(3372, 3422, 0), new RSTile(3376, 3415, 0));
    RSArea PANNING_TRAY_AREA = new RSArea(new RSTile(3372, 3377, 0), new RSTile(3369, 3380, 0));
    RSArea PANNING_AREA = new RSArea(new RSTile(3378, 3374, 0), new RSTile(3376, 3378, 0));
    RSArea NE_DIG_SITE = new RSArea(new RSTile(3377, 3437, 0), new RSTile(3369, 3442, 0));
    RSArea LARGE_EXAM_CENTRE = new RSArea(new RSTile(3357, 3348, 0), new RSTile(3367, 3332, 0));
    RSArea EATERN_DIG_SITE = new RSArea(new RSTile(3373, 3416, 0), new RSTile(3366, 3421, 0));
    RSArea WORKMAN_FOR_INVITATION = new RSArea(new RSTile(3363, 3415, 0), new RSTile(3356, 3418, 0));
    RSArea WENCH_AREA = new RSArea(new RSTile(3355, 3414, 0), new RSTile(3350, 3418, 0));
    RSArea EASTER_WENCH_AREA = new RSArea(new RSTile(3372, 3423, 0), new RSTile(3367, 3429, 0));
    RSArea UNDERGROUND_AREA2 = new RSArea(new RSTile(3356, 9814, 0), new RSTile(3346, 9824, 0));
    RSArea UNDERGROUND_AREA = new RSArea(new RSTile(3372, 9825, 0), new RSTile(3363, 9836, 0));
    RSArea STONE_WALL_AREA = new RSArea(new RSTile(3376, 9826, 0), new RSTile(3380, 9824, 0));
    RSArea WHOLE_UNDERGROUND = new RSArea(new RSTile(3394, 9800, 0), new RSTile(3348, 9856, 0));
    RSArea TABLET_AREA = new RSArea(new RSTile(3374, 9808, 0), new RSTile(3372, 9811, 0));
    RSTile[] PATH_TO_TABLET = new RSTile[]{new RSTile(3369, 9764, 0), new RSTile(3372, 9764, 0), new RSTile(3375, 9764, 0), new RSTile(3378, 9761, 0), new RSTile(3378, 9758, 0), new RSTile(3378, 9755, 0), new RSTile(3378, 9752, 0), new RSTile(3378, 9749, 0), new RSTile(3376, 9745, 0), new RSTile(3373, 9744, 0)};

    ArrayList<GEItem> itemsToBuy = new ArrayList<GEItem>(
            Arrays.asList(
                    new GEItem(ItemID.PESTLE_AND_MORTAR, 1, 500),
                    new GEItem(ItemID.VIAL, 1, 300),
                    new GEItem(ItemID.CUP_OF_TEA_1978, 1, 500),
                    new GEItem(ItemID.OPAL, 1, 500),
                    new GEItem(ItemID.ROPE, 2, 200),
                    new GEItem(ItemID.CHARCOAL, 2, 500),
                    new GEItem(ItemID.LEATHER_BOOTS, 1, 500),
                    new GEItem(ItemID.LEATHER_GLOVES, 1, 500),
                    new GEItem(ItemID.DIGSITE_TELEPORT, 4, 30),
                    new GEItem(ItemID.GAMES_NECKLACE[0], 1, 50),
                    new GEItem(ItemID.RING_OF_DUELING[0], 1, 50),
                    new GEItem(ItemID.STAMINA_POTION[0], 4, 15),
                    new GEItem(ItemID.AMULET_OF_GLORY[2], 1, 15),
                    new GEItem(ItemID.COMBAT_BRACELET[0], 1, 15),

                    new GEItem(ItemID.RING_OF_WEALTH[0], 1, 25)
            )
    );

    BuyItemsStep buyStep = new BuyItemsStep(itemsToBuy);

    public void buyItems() {
        cQuesterV2.status = "Buying Items";
        General.println("[Debug]: " + cQuesterV2.status);
        buyStep.buyItems();
    }

    public void getItems() {
        cQuesterV2.status = "Getting Items";
        General.println("[Debug]: " + cQuesterV2.status);
        BankManager.open(true);
        BankManager.checkEquippedGlory();
        BankManager.checkCombatBracelet();
        BankManager.depositAll(true);
        BankManager.withdraw(3, true, DIGSITE_TELEPORT);
        BankManager.withdraw(3, true, ItemID.VARROCK_TELEPORT);
        BankManager.withdraw(1, true, LEATHER_BOOTS);
        BankManager.withdraw(1, true, LEATHER_GLOVES);
        BankManager.withdraw(1, true, ItemID.RING_OF_DUELING[0]);
        BankManager.withdraw(1, true, ItemID.CUP_OF_TEA_1978);
        BankManager.withdraw(1, true, OPAL);
        BankManager.withdraw(2, true, CHARCOAL);
        BankManager.withdraw(2, true, ROPE);
        BankManager.withdraw(1, true, VIAL);
        BankManager.withdraw(1, true, TINDERBOX);
        BankManager.withdraw(1, true, TROWEL);
        BankManager.withdraw(1, true, SPECIMEN_BRUSH);
        BankManager.withdraw(1, true, PESTLE_AND_MOTAR);
        BankManager.withdraw(1, true, ItemID.GAMES_NECKLACE[0]);
        BankManager.withdraw(4, true, BankManager.STAMINA_POTION[0]);
        BankManager.close(true);
        Utils.equipItem(LEATHER_BOOTS);
        Utils.equipItem(LEATHER_GLOVES);
        Utils.equipItem(ItemID.RING_OF_DUELING[0]);
    }

    public void startQuest() {
        cQuesterV2.status = "Starting Quest";
        General.println("[Debug]: " + cQuesterV2.status);
        PathingUtil.walkToArea(START_AREA);
        if (NpcChat.talkToNPC("Examiner")) {
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation("Can I take an exam?", "Yes.");
            NPCInteraction.handleConversation();
        }
    }

    public void step2() {
        if (Inventory.find(683).length < 1) { // sealed letter
            cQuesterV2.status = "Going to museum";
            General.println("[Debug]: " + cQuesterV2.status);
            PathingUtil.walkToArea(VARROCK_MUSEUM);
            if (VARROCK_MUSEUM.contains(Player.getPosition())) {
                NpcChat.talkToNPC("Curator Haig Halen");
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation();
            }
        }
    }

    public void step3() {
        if (Inventory.find(683).length > 0) { // sealed letter
            cQuesterV2.status = "Going to Exam Center";
            General.println("[Debug]: " + cQuesterV2.status);
            PathingUtil.walkToArea(START_AREA);
            if (NpcChat.talkToNPC("Examiner")) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation();
                NPCInteraction.handleConversation();
            }
        }
    }

    public void step4() {
        if (Inventory.find(SPECIMEN_BRUSH).length < 1) {
            cQuesterV2.status = "Failing Quiz";
            General.println("[Debug]: " + cQuesterV2.status);
            PathingUtil.walkToArea(START_AREA);
            if (NpcChat.talkToNPC("Examiner")) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation("Yes, I certainly am.");
                NPCInteraction.handleConversation("The study of gardening, planting and fruiting vegetation.");
                NPCInteraction.handleConversation("Magic users, miners and their escorts.");
                NPCInteraction.handleConversation("Heat-resistant clothing to be worn at all times.");
                NPCInteraction.handleConversation();
                NPCInteraction.handleConversation();
            }
        }
    }

    public void step5() {
        if (Inventory.find(TEDDY).length < 1) {
            cQuesterV2.status = "Getting Teddy";
            General.println("[Debug]: " + cQuesterV2.status);
            if (PathingUtil.walkToArea(TEDDY_AREA))
                Utils.idle(1000, 4000);

            if (Objects.findNearest(25, 2358).length > 0) {
                AccurateMouse.click(Objects.findNearest(25, 2358)[0], "Search");
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation();

                if (Inventory.isFull()) {
                    Inventory.drop(SPADE);
                }
            }
        }
    }

    public void step6() {
        if ((Inventory.find(SPECIMEN_BRUSH).length < 1 || Inventory.find(ANIMAL_SKULL).length < 1) && Inventory.find(TEDDY).length > 0) {
            cQuesterV2.status = "Pickpocketing workman";
            General.println("[Debug]: " + cQuesterV2.status);
            PathingUtil.walkToArea(WORKMAN_AREA);
            if (WORKMAN_AREA.contains(Player.getPosition())) {

                while (Inventory.find(SPECIMEN_BRUSH).length < 1 || Inventory.find(ANIMAL_SKULL).length < 1) {
                    General.sleep(100);

                    if (Inventory.isFull())
                        Inventory.drop(1925);

                    int invSize = Inventory.getAll().length;
                    RSNPC[] npc = NPCs.findNearest(3630);
                    if (npc.length > 0 && AccurateMouse.click(npc[0], "Steal-from"))
                        Timer.waitCondition(() -> Inventory.getAll().length > invSize
                                || Player.getAnimation() != -1, 5000, 8000);
                }
            }
        }
    }

    public void step7() {
        if (Inventory.find(SPECIMEN_BRUSH).length > 0 && Inventory.find(ANIMAL_SKULL).length > 0
                && Inventory.find(TEDDY).length > 0) {
            cQuesterV2.status = "Going to Student 1 ";
            General.println("[Debug]: " + cQuesterV2.status);
            PathingUtil.walkToArea(STUDENT_AREA);

            if (NpcChat.talkToNPC(3634)) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation();
            }
            if (NpcChat.talkToNPC(3634)) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation();
            }

        }
    }

    public void step8() {
        if (Inventory.find(SPECIMEN_BRUSH).length > 0 && Inventory.find(ANIMAL_SKULL).length > 0
                && Inventory.find(TEDDY).length < 1) {
            cQuesterV2.status = "The Dig Site: Going to Student 2.";
            General.println("[Debug]: " + cQuesterV2.status);
            PathingUtil.walkToArea(STUDENT2_AREA);
            if (NpcChat.talkToNPC(3632)) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation();
            }
            if (NpcChat.talkToNPC(3632)) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation();
            }
        }
    }

    public void step9() {
        if (Inventory.find(SPECIMEN_BRUSH).length > 0 && Inventory.find(ANIMAL_SKULL).length < 1
                && Inventory.find(TEDDY).length < 1) {
            cQuesterV2.status = "Going to Student 3.";
            General.println("[Debug]: " + cQuesterV2.status);
            PathingUtil.walkToArea(STUDENT3_AREA);
            if (NpcChat.talkToNPC(3633)) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation();
            }
        }
    }

    public void step10() {
        if (Inventory.find(SPECIMEN_BRUSH).length > 0 && Inventory.find(FULL_PAN).length < 1
                && Inventory.find(PANNING_TRAY).length < 1 && Inventory.find(ANIMAL_SKULL).length < 1 && Inventory.find(TEDDY).length < 1) {
            cQuesterV2.status = "Going to get panning tray.";
            General.println("[Debug]: " + cQuesterV2.status);
            PathingUtil.walkToArea(PANNING_TRAY_AREA);
            if (GroundItems.find(PANNING_TRAY).length > 0) {
                if (AccurateMouse.click(GroundItems.find(PANNING_TRAY)[0], "Take"))
                    Timer.waitCondition(() -> Inventory.find(PANNING_TRAY).length > 0, 5000, 8000);

                Utils.idle(500, 2000);
            }
        }
    }

    public void step11() {
        if (Inventory.find(PANNING_TRAY).length > 0 && Inventory.find(SPECIAL_CUP).length < 1) {
            cQuesterV2.status = "Going to pan";
            General.println("[Debug]: " + cQuesterV2.status);
            PathingUtil.walkToArea(PANNING_AREA);
            if (PANNING_AREA.contains(Player.getPosition())) {
                RSObject[] panningSite = Objects.findNearest(20, 2363);

                if (panningSite.length > 0 && Inventory.find(CUP_OF_TEA).length > 0) {
                    if (AccurateMouse.click(panningSite[0], "Pan")) {
                        NPCInteraction.waitForConversationWindow();
                        NPCInteraction.handleConversation("So how do I become invited then?");
                        NPCInteraction.handleConversation();
                    }
                }

                while (Inventory.find(SPECIAL_CUP).length < 1) {
                    General.sleep(75,150);
                    panningSite = Objects.findNearest(20, 2363);

                    if (panningSite.length > 0) {
                        if (AccurateMouse.click(panningSite[0], "Pan")) {
                            Timer.waitCondition(() -> Inventory.find(FULL_PAN).length > 0, 7000, 9000);

                            Utils.idle(300, 2000);
                        }

                        if (Inventory.find(FULL_PAN).length > 0) {
                            AccurateMouse.click(Inventory.find(FULL_PAN)[0], "Search");
                            NPCInteraction.waitForConversationWindow();
                            NPCInteraction.handleConversation();
                        }

                        if (Inventory.isFull())
                            Inventory.drop(1625, 1627, 407, 680); // uncut opal, uncute jade, oyster, gold nug

                    }
                }
            }
        }
    }

    public void step12() {
        if (Inventory.find(SPECIAL_CUP).length > 0) {
            cQuesterV2.status = "Going to Student 3.";
            General.println("[Debug]: " + cQuesterV2.status);
            PathingUtil.walkToArea(STUDENT3_AREA);
            if (NpcChat.talkToNPC(3633)) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation();
            }
        }
    }

    public void step13() {
        if (Inventory.find(TEDDY).length < 1 && Inventory.find(SPECIAL_CUP).length < 1) {
            cQuesterV2.status = "Answering Quiz #1";
            General.println("[Debug]: " + cQuesterV2.status);
            PathingUtil.walkToArea(START_AREA);
            if (NpcChat.talkToNPC("Examiner")) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation("Yes, I certainly am.");
                NPCInteraction.handleConversation("The study of the earth, its contents and history.");
                NPCInteraction.handleConversation("All that have passed the appropriate Earth Sciences exam.");
                NPCInteraction.handleConversation("Gloves and boots to be worn at all times; proper tools must be used.");
                NPCInteraction.handleConversation();
            }
        }
    }

    public void step14() {
        cQuesterV2.status = "Going to Student 1 ";
        General.println("[Debug]: " + cQuesterV2.status);
        PathingUtil.walkToArea(STUDENT_AREA);
        if (NpcChat.talkToNPC(3634)) {
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation();
        }
    }

    public void step14b() {
        if (Inventory.find(OPAL).length < 1) {
            buyItems();
            getItems();
        }
        cQuesterV2.status = "Going to Student 1 ";
        General.println("[Debug]: " + cQuesterV2.status);
        for (int i = 0; i < 2; i++) {
            PathingUtil.walkToArea(STUDENT_AREA);
            if (NpcChat.talkToNPC(3634)) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation();
            }
        }
    }

    public void step15() {
        cQuesterV2.status = "Going to Student 3.";
        General.println("[Debug]: " + cQuesterV2.status);
        PathingUtil.walkToArea(STUDENT3_AREA);
        if (NpcChat.talkToNPC(3633)) {
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation();
        }
    }

    public void step16() {
        cQuesterV2.status = "Going to Student 2.";
        General.println("[Debug]: " + cQuesterV2.status);
        PathingUtil.walkToArea(STUDENT2_AREA);
        if (NpcChat.talkToNPC(3632)) {
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation();
        }
    }


    public void quiz2() {
        cQuesterV2.status = "Answering Quiz 2";
        General.println("[Debug]: " + cQuesterV2.status);
        PathingUtil.walkToArea(START_AREA);
        if (NpcChat.talkToNPC("Examiner")) {
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation("I am ready for the next exam.");
            NPCInteraction.handleConversation("Samples taken in rough form; kept only in sealed containers.");
            NPCInteraction.handleConversation("Finds must be carefully handled, and gloves worn.");
            NPCInteraction.handleConversation("Always handle with care; strike cleanly on its cleaving point.");
            NPCInteraction.handleConversation();
        }
    }

    public void quiz3() {
        cQuesterV2.status = "Answering Quiz 3";
        General.println("[Debug]: " + cQuesterV2.status);
        PathingUtil.walkToArea(START_AREA);
        if (NpcChat.talkToNPC("Examiner")) {
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation("I am ready for the last exam...");
            NPCInteraction.handleConversation("Samples cleaned, and carried only in specimen jars.");
            NPCInteraction.handleConversation("Brush carefully and slowly using short strokes.");
            NPCInteraction.handleConversation("Handle bones very carefully and keep them away from other samples.");
            NPCInteraction.handleConversation();
        }
    }

    public void getJar() {
        if (Inventory.find(SPECIMEN_JAR).length < 1) {
            cQuesterV2.status = "Getting Jar";
            General.println("[Debug]: " + cQuesterV2.status);
            PathingUtil.walkToTile(new RSTile(3355, 3333));

            Utils.idle(4000, 8000);
            if (Objects.findNearest(25, 17302).length > 0) {
                if (AccurateMouse.click(Objects.findNearest(25, 17302)[0], "Open"))
                    Timer.waitCondition(() -> Objects.findNearest(25, 17303).length > 0, 6000, 9000);

                Utils.idle(500, 2000);
            }
            if (Objects.findNearest(25, 17303).length > 0) {
                if (AccurateMouse.click(Objects.findNearest(25, 17303)[0], "Search")) {
                    NPCInteraction.waitForConversationWindow();
                    NPCInteraction.handleConversation();
                }
            }
        }
    }

    public void dig() {
        if (Inventory.find(SPECIMEN_JAR).length > 0 && Inventory.find(ANCIENT_TALISMAN).length == 0
                && Inventory.find(INVITATION_LETTER).length == 0) {
            cQuesterV2.status = "Going to Dig";
            General.println("[Debug]: " + cQuesterV2.status);
            PathingUtil.walkToArea(NE_DIG_SITE);
        }
        if (NE_DIG_SITE.contains(Player.getPosition()) && Inventory.find(ANCIENT_TALISMAN).length == 0) {
            if (Inventory.find(TROWEL).length > 0) {

                while (Inventory.find(ANCIENT_TALISMAN).length == 0) {
                    General.sleep(10, 30);
                    if (Utils.useItemOnObject(TROWEL, "Soil"))
                        Timer.waitCondition(() -> Inventory.find(ANCIENT_TALISMAN).length > 0, 3500, 7000);

                    if (Inventory.isFull()) {
                        Walking.clickTileMS(Player.getPosition().translate(1, 0), "Walk here");

                        Utils.idle(600, 1800);

                        dropJunk();
                        Walking.clickTileMS(Player.getPosition().translate(-1, 0), "Walk here");

                        Utils.idle(600, 1800);
                    }
                }
                dropJunk();
            }
        }
    }

    int CLAY = 434;
    int CERAMIC_REMAINS = 694;
    int BLACK_MED_HELM = 1151;
    int BRONZE_SPEAR = 1237;
    int OLD_TOOTH = 695;
    int BROKEN_ARMOUR = 698;

    public void dropJunk() {
        Inventory.setDroppingMethod(Inventory.DROPPING_METHOD.RIGHT_CLICK);
        Utils.dropItem(BROKEN_ARROW);
        Utils.dropItem(SPADE);
        Utils.dropItem(BONES);
        Utils.dropItem(DAMAGED_ARMOUR);
        Utils.dropItem(BROKEN_STAFF);
        Utils.dropItem(BELT_BUCKLE);
        Utils.dropItem(BUTTONS);
        Utils.dropItem(NEEDLE);
        Utils.dropItem(PIE_DISH);
        Utils.dropItem(IRON_KNIFE);
        Utils.dropItem(CLAY);
        Utils.dropItem(CERAMIC_REMAINS);
        Utils.dropItem(BLACK_MED_HELM);
        Utils.dropItem(OLD_TOOTH);
        Utils.dropItem(BRONZE_SPEAR);
        Utils.dropItem(BROKEN_ARMOUR);
    }

    public void returnTalisman() {
        if (Inventory.find(ANCIENT_TALISMAN).length > 0) {
            cQuesterV2.status = "Going to Exam Centre";
            General.println("[Debug]: " + cQuesterV2.status);
            PathingUtil.walkToArea(START_AREA);
        }
        if (LARGE_EXAM_CENTRE.contains(Player.getPosition())) {
            NpcChat.talkToNPC(ARCHAEOLOGICAL_EXPERT);
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation();

        }
    }

    public void goToWench() {
        if (Inventory.find(INVITATION_LETTER).length > 0) {
            cQuesterV2.status = "Going to Dig Site";
            General.println("[Debug]: " + cQuesterV2.status);
            PathingUtil.walkToArea(WORKMAN_FOR_INVITATION);
        }
        if (WORKMAN_FOR_INVITATION.contains(Player.getPosition())) {
            Utils.useItemOnNPC(INVITATION_LETTER, 3628);
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation();

        }
    }

    public void operateWench() {
        if (Inventory.find(NITROGYLCERIN).length > 0 || Inventory.find(CHEMICAL_POWDER).length > 0) {
            return;

        } else if (!WHOLE_UNDERGROUND.contains(Player.getPosition()) && Inventory.find(CHEST_KEY).length == 0) {
            cQuesterV2.status = "Going to Wench";
            General.println("[Debug]: " + cQuesterV2.status);
            PathingUtil.walkToArea(WENCH_AREA);
        }
        if (WENCH_AREA.contains(Player.getPosition()) && Inventory.find(CHEST_KEY).length == 0 &&
                !WHOLE_UNDERGROUND.contains(Player.getPosition())) {
            if (Utils.useItemOnObject(ROPE, 2350)) {
                Timer.abc2WaitCondition(() -> Inventory.find(ROPE).length == 1, 6000, 9000);
            }

            if (Utils.clickObj(2350, "Operate"))
                Timer.abc2WaitCondition(() -> WHOLE_UNDERGROUND.contains(Player.getPosition()), 8000, 10000);

            Utils.idle(2000, 4000);

        }
    }

    public void operateWench2() {
        if (!WHOLE_UNDERGROUND.contains(Player.getPosition()) && Inventory.find(CHEST_KEY).length < 1) {
            cQuesterV2.status = "Going back to Wench";
            General.println("[Debug]: " + cQuesterV2.status);
            PathingUtil.walkToArea(WENCH_AREA);
        }
        if (WENCH_AREA.contains(Player.getPosition()) && Inventory.find(CHEST_KEY).length < 1) {
            if (Utils.useItemOnObject(ROPE, 2350)) {
                Timer.waitCondition(() -> Inventory.find(ROPE).length == 1, 6000, 9000);
                Utils.shortSleep();
            }

            if (Utils.clickObj(2350, "Operate"))
                Timer.abc2WaitCondition(() -> WHOLE_UNDERGROUND.contains(Player.getPosition()), 8000, 10000);
        }
    }

    public void searchBricks() {
        if (WHOLE_UNDERGROUND.contains(Player.getPosition()) && !UNDERGROUND_AREA2.contains(Player.getPosition())) {
            cQuesterV2.status = "Going to Rock";
            General.println("[Debug]: " + cQuesterV2.status);
            if (GroundItems.find(ARCENIA_ROOT).length > 0 && Inventory.find(ARCENIA_ROOT).length < 1) {
                AccurateMouse.click(GroundItems.find(ARCENIA_ROOT)[0], "Take");
                Timer.waitCondition(() -> Inventory.find(ARCENIA_ROOT).length > 0, 8000, 10000);
            }
            Walking.blindWalkTo(STONE_WALL_AREA.getRandomTile());

            Utils.idle(4000, 8000);
            if (Utils.clickObj("Brick", "Search")) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation();
            }
            Walking.blindWalkTo(new RSTile(3369, 9827));

            Utils.idle(4000, 8000);
            Timer.waitCondition(() -> !Player.isMoving(), 8000, 10000);
            if (Utils.clickObj(2353, "Climb-up"))
                Timer.waitCondition(() -> WENCH_AREA.contains(Player.getPosition()), 8000, 10000);
        }
    }


    public void operateEasternWench() {
        if (Inventory.find(NITROGYLCERIN).length > 0 || Inventory.find(CHEMICAL_POWDER).length > 0) {
            return;

        }
        if (!WHOLE_UNDERGROUND.contains(Player.getPosition()) && Inventory.find(CHEST_KEY).length < 1) {
            cQuesterV2.status = "Going to Eastern Wench";
            General.println("[Debug]: " + cQuesterV2.status);
            PathingUtil.walkToArea(EASTER_WENCH_AREA);
        }
        if (Inventory.find(CHEST_KEY).length < 1) {
            if (Utils.useItemOnObject(ROPE, 2351))
                Timer.waitCondition(() -> Inventory.find(ROPE).length < 1, 6000, 8000);

            Utils.idle(500, 2000);
            if (Utils.clickObj(2351, "Operate"))
                Timer.waitCondition(() -> NPCs.find(3629).length > 0, 8000, 10000);

            Utils.idle(1500, 5000);
        }
    }

    public void talkToDougDeeping() {
        if (UNDERGROUND_AREA2.contains(Player.getPosition()) && Inventory.find(CHEST_KEY).length < 1) {
            cQuesterV2.status = "Talking to Doug Deeping";
            General.println("[Debug]: " + cQuesterV2.status);
            if (NpcChat.talkToNPC(3629)) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation("How could I move a large pile of rocks?");
                NPCInteraction.handleConversation();
                Utils.idle(1500, 5000);
            }
        }
        if (Inventory.find(ARCENIA_ROOT).length < 1) {
            if (Utils.clickGroundItem(ARCENIA_ROOT))
                Timer.waitCondition(() -> Inventory.find(ARCENIA_ROOT).length > 0, 7000, 12000);
        }
        if (UNDERGROUND_AREA2.contains(Player.getPosition()) && Inventory.find(CHEST_KEY).length > 0) {
            if (AccurateMouse.click(Objects.findNearest(20, 2352)[0], "Climb-up"))
                Timer.waitCondition(() -> EASTER_WENCH_AREA.contains(Player.getPosition()), 8000, 12000);

            Utils.idle(1500, 5000);
        }
    }


    public void makeChemical() {
        if (Inventory.find(CHEMICAL_COMPOUND).length == 0) {
            cQuesterV2.status = "Getting Chemicals";
            General.println("[Debug]: " + cQuesterV2.status);
            if (Inventory.find(CHEST_KEY).length > 0) { // lose it when ytou open chest
                PathingUtil.walkToArea(PANNING_TRAY_AREA);
                if (PANNING_TRAY_AREA.contains(Player.getPosition())) {
                    if (Utils.useItemOnObject(CHEST_KEY, 2361)) // closed chest
                        Timer.waitCondition(() -> Objects.find(20, 2360).length > 0, 8000, 12000);
                    if (Objects.find(20, 2360).length > 0) {
                        if (AccurateMouse.click(Objects.find(20, 2360)[0], "Search")) {
                            NPCInteraction.waitForConversationWindow();
                            NPCInteraction.handleConversation();
                        }
                    }
                }
            }
            if (Inventory.find(CHEMICAL_POWDER).length > 0 && Inventory.find(CHEMICAL_COMPOUND).length < 1 &&
                    Inventory.find(UNIDENTIFIED_LIQUID).length < 1 && Inventory.find(NITROGYLCERIN).length < 1) {
                if (PathingUtil.walkToTile(new RSTile(3364, 3379)))
                    Utils.idle(4000, 7000);

                cQuesterV2.status = "Searching Barrel";
                General.println("[Debug]: " + cQuesterV2.status);
                if (Utils.useItemOnObject(TROWEL, BARREL_ID)) {
                    NPCInteraction.waitForConversationWindow();
                    NPCInteraction.handleConversation();
                    Utils.shortSleep();
                    if (Utils.useItemOnObject(VIAL, BARREL_ID)) {
                        Timer.waitCondition(() -> Inventory.find(UNIDENTIFIED_LIQUID).length > 0, 6000, 9000);
                        Utils.shortSleep();
                    }
                }

            }
            if (Inventory.find(CHEMICAL_POWDER).length > 0 && Inventory.find(UNIDENTIFIED_LIQUID).length > 0) {
                if (!BankManager.checkInventoryItems(CHARCOAL, PESTLE_AND_MOTAR) && !BankManager.checkInventoryItems(GROUND_CHARCOAL, PESTLE_AND_MOTAR)) {
                    buyItems();
                    getItems();
                    Banking.withdraw(1, UNIDENTIFIED_LIQUID);
                    Banking.withdraw(1, CHEMICAL_POWDER);
                    Banking.withdraw(1, ARCENIA_ROOT);
                }
                if (Utils.useItemOnItem(CHARCOAL, PESTLE_AND_MOTAR)) {
                    Timer.waitCondition(() -> Inventory.find(GROUND_CHARCOAL).length > 0, 6000, 9000);
                    Utils.shortSleep();
                }

                if (Inventory.find(GROUND_CHARCOAL).length > 0) {
                    cQuesterV2.status = "Going to Exam Centre";
                    General.println("[Debug]: " + cQuesterV2.status);
                    if (PathingUtil.walkToArea(START_AREA))
                        Utils.modSleep();
                }

                if (LARGE_EXAM_CENTRE.contains(Player.getPosition())) {
                    if (Utils.useItemOnNPC(CHEMICAL_POWDER, ARCHAEOLOGICAL_EXPERT)) {
                        NPCInteraction.waitForConversationWindow();
                        NPCInteraction.handleConversation();
                        Utils.shortSleep();
                    }

                    if (Utils.useItemOnNPC(UNIDENTIFIED_LIQUID, ARCHAEOLOGICAL_EXPERT)) {
                        NPCInteraction.waitForConversationWindow();
                        NPCInteraction.handleConversation();
                        Utils.shortSleep();
                    }
                }
            }

            if (Inventory.find(NITROGYLCERIN).length > 0 && Inventory.find(CHEMICAL_POWDER).length > 0) {
                PathingUtil.walkToArea(START_AREA);
                if (Utils.useItemOnNPC(CHEMICAL_POWDER, ARCHAEOLOGICAL_EXPERT)) {
                    NPCInteraction.waitForConversationWindow();
                    NPCInteraction.handleConversation();
                }
            }

            if ((Inventory.find(NITROGYLCERIN).length > 0 && Inventory.find(AMMONIUM_NITRATE).length > 0)
                    || Inventory.find(MIXED_CHEMICALS).length > 0
                    || Inventory.find(MIXED_CHEMICALS2).length > 0) {
                if (Utils.useItemOnItem(NITROGYLCERIN, AMMONIUM_NITRATE))
                    Utils.idle(500, 1500);

                if (Utils.useItemOnItem(GROUND_CHARCOAL, MIXED_CHEMICALS))
                    Timer.waitCondition(() -> Inventory.find(MIXED_CHEMICALS2).length > 0, 3000, 5000);

                if (Utils.useItemOnItem(ARCENIA_ROOT, MIXED_CHEMICALS2)) {
                    NPCInteraction.handleConversation();
                    Utils.idle(500, 1500);
                }
            }
        }
    }

    public void blowUpBricks() {
        if (WHOLE_UNDERGROUND.contains(Player.getPosition()) && !UNDERGROUND_AREA2.contains(Player.getPosition())) {
            if (GroundItems.find(ARCENIA_ROOT).length > 0 && Inventory.find(ARCENIA_ROOT).length < 1) {
                if (AccurateMouse.click(GroundItems.find(ARCENIA_ROOT)[0], "Take"))
                    Timer.waitCondition(() -> Inventory.find(ARCENIA_ROOT).length > 0, 8000, 12000);
            }
            if (PathingUtil.blindWalkToTile(STONE_WALL_AREA.getRandomTile()))
                Utils.idle(4000, 8000);

            if (Objects.findNearest(10, 2362).length > 0) {
                cQuesterV2.status = "Blowing up brick";
                General.println("[Debug]: " + cQuesterV2.status);
                if (Utils.useItemOnObject(CHEMICAL_COMPOUND, 2362))
                    Timer.waitCondition(() -> Inventory.find(CHEMICAL_COMPOUND).length < 1, 8000, 12000);

                if (Utils.useItemOnObject(TINDERBOX, 2362)) {
                    NPCInteraction.waitForConversationWindow();
                    NPCInteraction.handleConversation();

                    Utils.idle(8000, 12000);
                    NPCInteraction.waitForConversationWindow();
                    NPCInteraction.handleConversation();
                }
            }
        }
    }

    public void getTablet() {
        if (Inventory.find(STONE_TABLET).length < 1) {
            Walking.walkPath(PATH_TO_TABLET);
            Utils.idle(6000, 8000);
            if (Objects.findNearest(20, 17369).length > 0) {
                if (AccurateMouse.click(Objects.findNearest(20, 17369)[0], "Take"))
                    Timer.waitCondition(() -> Inventory.find(STONE_TABLET).length > 0, 6000, 9000);
            }
        }
    }

    public void finishQuest() {
        cQuesterV2.status = "The Dig Site: Finishing Quest";
        General.println("[Debug]: " + cQuesterV2.status);
        PathingUtil.walkToArea(START_AREA);
        if (Utils.useItemOnNPC(STONE_TABLET, ARCHAEOLOGICAL_EXPERT)) {
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation();
        }
    }

    @Override
    public void execute() {

        if (Game.getSetting(131) == 0) {
            buyItems();
            getItems();
            startQuest();
        }
        if (Game.getSetting(131) == 1) {
            step2();
            step3(); // repeats to go back to exam centre
        }
        if (Game.getSetting(131) == 2) {
            step4();
            step5();
            step6();
            step7();
            step8();
            step9();
            step10();
            step11();
            step12();
            step13();
        }
        if (Game.getSetting(131) == 3) {
            step14();
            step15();
            step16();
            quiz2();
        }
        if (Game.getSetting(131) == 4) {
            step14b();
            step15();
            step16();
            quiz3();
        }
        if (Game.getSetting(131) == 5) {
            getJar();
            dig();
            returnTalisman();
            goToWench();
        }
        if (Game.getSetting(131) == 6) { // could use number of ropes in inventory to determine these steps
            if (Inventory.find(CHEMICAL_COMPOUND).length < 1) {
                operateWench();
                searchBricks();
                operateEasternWench();
                talkToDougDeeping();
                makeChemical();
            }
            if (Inventory.find(CHEMICAL_COMPOUND).length > 0) {
                operateWench2();
                blowUpBricks();
            }
        }
        if (Game.getSetting(131) == 7) {
            blowUpBricks();
        }
        if (Game.getSetting(131) == 8) {
            getTablet();
            finishQuest();
        }
        if (Game.getSetting(131) == 9) {
            Utils.closeQuestCompletionWindow();
            Utils.continuingChat();
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
        return "Dig Site";
    }

    @Override
    public boolean checkRequirements() {
        if (Skills.getActualLevel(Skills.SKILLS.AGILITY) < 10
                || Skills.getActualLevel(Skills.SKILLS.HERBLORE) < 10
                || Skills.getActualLevel(Skills.SKILLS.THIEVING) < 25) {
            General.println("[Debug]: Missing a skill requirement (25 thieving, 10 herblore; 10 agility)");
            return false;
        }
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

    @Override
    public boolean isComplete() {
        return Quest.THE_DIG_SITE.getState().equals(Quest.State.COMPLETE);
    }
}
