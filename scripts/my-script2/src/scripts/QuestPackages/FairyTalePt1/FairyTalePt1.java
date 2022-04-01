package scripts.QuestPackages.FairyTalePt1;

import dax.walker.utils.AccurateMouse;
import dax.walker_engine.interaction_handling.NPCInteraction;
import org.tribot.api.General;
import org.tribot.api.input.Mouse;
import org.tribot.api2007.*;
import org.tribot.api2007.types.*;
import org.tribot.script.sdk.Log;
import org.tribot.script.sdk.Quest;
import org.tribot.script.sdk.query.Query;
import org.tribot.script.sdk.types.Widget;
import scripts.*;
import scripts.GEManager.GEItem;
import scripts.QuestPackages.DruidicRitual.DruidicRitual;
import scripts.QuestPackages.JunglePotion.JunglePotion;
import scripts.QuestSteps.BuyItemsStep;
import scripts.QuestSteps.QuestTask;
import scripts.Requirements.ItemRequirement;
import scripts.Requirements.Requirement;
import scripts.Tasks.Priority;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class FairyTalePt1 implements QuestTask {

    private static FairyTalePt1 quest;

    public static FairyTalePt1 get() {
        return quest == null ? quest = new FairyTalePt1() : quest;
    }


    int SECATEURS = 5329;
    int MAGIC_SECATEURS = 7409;
    int DRAYNOR_SKULL = 7408;
    int SPADE = 952;
    int GHOSTSPEAK_AMULET = 552;
    int DRAMEN_STAFF = 772;
    int MORTTON_TELEPORT = 12406;
    int SYMPTOM_LIST = 7411;
    int MORT_MYRE_STEM = 2972;
    int MORT_MYRE_FUNGUS = 2970;
    int MORT_MYRE_PEAR = 2974;
    int NATURE_TALISMAN = 1462;
    int MONKFISH = 7946;
    int QUEENS_SECATEURS = 7410;
    int UNCUT_OPAL = 1625;
    int SNAPDRAGON = 3000;
    int POTATO_CACTUS = 3138;
    int CHISEL = 1755;
    int CRUSHED_GEMSTONE = 1633;
    int SALVE_TELE = 19619;
    int BABY_DRAGON_BONES = 534;
    int BLUE_DRAGON_SCALE = 243;
    int CHARCOAL = 973;
    int AVANTOE = 261;
    int IRIT_LEAF = 259;
    int VOLENCIA_MOSS = 1532;
    int EDIBLE_SEAWEED = 403;
    int FAT_SNAIL = 3367;
    int GRAPES = 1987;
    int JANGERBERRIES = 247;
    int JOGRE_BONES = 3125;
    int KING_WORM = 2162;
    int LIME = 2120;
    int PROBOSCIS = 6319;
    int RAW_CAVE_EEL = 5001;
    int RED_SPIDERS_EGGS = 223;
    int RED_VINE_WORM = 25;
    int RAW_SLIMY_EEL = 3379;
    int SUPERCOMPOST = 6034;
    int UNCUT_DIAMOND = 1617;
    int SNAPE_GRASS = 231;
    int WHITE_BERRIES = 239;
    int UNCUT_RUBY = 1619;
    int OYSTER = 407;

    /**
     * NPCs
     */
    int MARTIN = 5832;
    int FRIZZY_SKERNIP = 2684;
    int ELSTAN = 2663;
    int HESKEL = 2679;
    int FAYETH = 2681;
    int TREZNOR = 2680;
    int FAIRY_GODFATHER = 5850;
    int FAIRY_NUFF = 2698;
    int ZANDAR = 5841;
    int MALIGNUS = 1783;
    int NATURE_SPIRIT = 944;
    int TANGLEFOOT = 5848;

    String line16;
    String line17;
    String line18;

    boolean GET_MORT_MYRE_FUNGUS = false;
    boolean GET_MORT_MYRE_STEM = false;
    boolean GET_NATURE_TALISMAN = false;
    boolean GET_POTATO_CACTUS = false;
    boolean GET_SNAPDRAGON = false;
    boolean GET_CRUSHED_GEMSTONE = false;
    boolean GET_BABY_DRAGON_BONES = false;
    boolean GET_BLUE_DRAGON_SCALE = false;
    boolean GET_CHARCOAL = false;
    boolean GET_AVANTOE = false;
    boolean GET_IRIT_LEAF = false;
    boolean GET_VOLENCIA_MOSS = false;
    boolean GET_EDIBLE_SEAWEED = false;
    boolean GET_FAT_SNAIL = false;
    boolean GET_GRAPES = false;
    boolean GET_JANGERBERRIES = false;
    boolean GET_JOGRE_BONES = false;
    boolean GET_KING_WORM = false;
    boolean GET_LIME = false;
    boolean GET_MORT_MYRE_PEAR = false;
    boolean GET_PROBOSCIS = false;
    boolean GET_RAW_CAVE_EEL = false;
    boolean GET_RED_SPIDERS_EGGS = false;
    boolean GET_RED_VINE_WORM = false;
    boolean GET_RAW_SLIMY_EEL = false;
    boolean GET_SNAPE_GRASS = false;
    boolean GET_SUPERCOMPOST = false;
    boolean GET_UNCUT_DIAMOND = false;
    boolean GET_WHITE_BERRIES = false;
    boolean GET_UNCUT_RUBY = false;
    boolean GET_OYSTER = false;


    RSArea START_AREA = new RSArea(new RSTile(3081, 3255, 0), new RSTile(3076, 3258, 0));
    RSArea GARDNER_1_AREA = new RSArea(new RSTile(3057, 3259, 0), new RSTile(3063, 3254, 0));
    RSArea GARDNER_2_AREA = new RSArea(new RSTile(3049, 3312, 0), new RSTile(3060, 3300, 0));
    RSArea GARDNER_3_AREA = new RSArea(new RSTile(3005, 3372, 0), new RSTile(2998, 3378, 0));
    RSArea GARDNER_4_AREA = new RSArea(new RSTile(3197, 3228, 0), new RSTile(3191, 3234, 0));
    RSArea GARDNER_5_AREA = new RSArea(new RSTile(3225, 3460, 0), new RSTile(3230, 3454, 0));
    RSArea THRONE_ROOM = new RSArea(new RSTile(2444, 4430, 0), new RSTile(2451, 4424, 0));
    RSArea FAIRY_NUFFS_GROTTO = new RSArea(new RSTile(2384, 4474, 0), new RSTile(2389, 4470, 0));
    RSArea GRAVE_AREA = new RSArea(new RSTile(3103, 3384, 0), new RSTile(3108, 3380, 0));
    RSArea DARK_WIZARD_TOWER = new RSArea(new RSTile(2910, 3331, 2), new RSTile(2905, 3337, 2));
    RSArea MALIGNUS_AREA = new RSArea(new RSTile(2985, 3275, 0), new RSTile(2998, 3265, 0));
    RSArea BEFORE_BRIDGE = new RSArea(new RSTile(3437, 3328, 0), new RSTile(3443, 3325, 0));
    RSArea INSIDE_TREE = new RSArea(new RSTile(3447, 9733, 0), new RSTile(3435, 9744, 0));
    RSArea GROTTO_TREE_AREA = new RSArea(new RSTile(3446, 3330, 0), new RSTile(3434, 3345, 0));
    RSArea TUNNEL_ENTRANCE = new RSArea(new RSTile(2400, 4381, 0), new RSTile(2403, 4377, 0));
    RSArea BOSS_AREA = new RSArea(new RSTile(2371, 4395, 0), new RSTile(2379, 4385, 0));
    RSArea TUNNELS = new RSArea(new RSTile(2368, 4397, 0), new RSTile(2401, 4354, 0));

    RSArea SWAMP_AREA = new RSArea(new RSTile(3201, 3170, 0), new RSTile(3199, 3167, 0));
    // public RSArea WHOLE_ZANARIS = new RSArea(new RSTile(2508, 4404, 0), new RSTile(2369, 4481, 0));
    RSArea WHOLE_ZANARIS = new RSArea(new RSTile(2311, 4492, 0), new RSTile(2514, 4353, 0));

    final RSTile[] PATH_TO_BOSS = new RSTile[]{new RSTile(6684, 4891, 0), new RSTile(6681, 4891, 0), new RSTile(6679, 4888, 0), new RSTile(6678, 4885, 0), new RSTile(6678, 4882, 0), new RSTile(6680, 4879, 0), new RSTile(6683, 4876, 0), new RSTile(6685, 4873, 0), new RSTile(6685, 4870, 0), new RSTile(6682, 4870, 0), new RSTile(6679, 4870, 0), new RSTile(6675, 4870, 0), new RSTile(6672, 4871, 0), new RSTile(6669, 4873, 0), new RSTile(6667, 4876, 0), new RSTile(6667, 4879, 0), new RSTile(6666, 4882, 0), new RSTile(6665, 4885, 0), new RSTile(6665, 4888, 0), new RSTile(6663, 4891, 0), new RSTile(6663, 4894, 0), new RSTile(6663, 4897, 0), new RSTile(6663, 4900, 0)};

    ArrayList<GEItem> itemsToBuyInitial = new ArrayList<GEItem>(
            Arrays.asList(
                    new GEItem(SECATEURS, 1, 500),
                    new GEItem(ItemID.SPADE, 1, 500),
                    new GEItem(ItemID.FALADOR_TELEPORT, 10, 50),
                    new GEItem(ItemID.LUMBRIDGE_TELEPORT, 5, 50),
                    new GEItem(ItemID.MONKFISH, 20, 50),
                    new GEItem(ItemID.VARROCK_TELEPORT, 5, 50),
                    new GEItem(ItemID.MONKS_ROBE_BOTTOM, 1, 500),
                    new GEItem(ItemID.MONKS_ROBE_TOP, 1, 500),
                    new GEItem(ItemID.SALVE_GRAVEYARD_TELEPORT, 5, 50),
                    new GEItem(ItemID.PRAYER_POTION_4, 4, 20),

                    new GEItem(ItemID.COMBAT_BRACELET[0], 1, 20),
                    new GEItem(ItemID.STAMINA_POTION[0], 3, 15),
                    new GEItem(ItemID.RING_OF_WEALTH[0], 1, 25)
            )
    );

    ArrayList<GEItem> specialBuyItems = new ArrayList<GEItem>();
    BuyItemsStep buyStep = new BuyItemsStep(itemsToBuyInitial);

    public void buyItems() {
        cQuesterV2.status = "Buying Items";
        General.println("[Debug]: Buying Items");
        buyStep.buyItems();
    }

    public void getItems() {
        cQuesterV2.status = "Getting items";
        General.println("[Debug]: Withdrawing items.");
        BankManager.open(true);
        BankManager.checkEquippedGlory();
        BankManager.depositAll(true);
        BankManager.withdraw(1, true, SECATEURS);
        BankManager.withdraw(1, true, SPADE);
        BankManager.withdraw(1, true, DRAMEN_STAFF);
        BankManager.withdraw(1, true, GHOSTSPEAK_AMULET);
        BankManager.withdraw(1, true, ItemID.AMULET_OF_GLORY[0]);
        BankManager.withdraw(8, true, ItemID.FALADOR_TELEPORT);
        BankManager.withdraw(8, true, ItemID.VARROCK_TELEPORT);
        BankManager.withdraw(8, true, ItemID.LUMBRIDGE_TELEPORT);
        BankManager.withdraw(3, true, ItemID.STAMINA_POTION[0]);
        BankManager.withdraw(1, true, ItemID.MONKS_ROBE_TOP);
        BankManager.withdraw(1, true, ItemID.MONKS_ROBE_BOTTOM);
        Banking.close();
        Utils.equipItem(ItemID.MONKS_ROBE_TOP);
        Utils.equipItem(ItemID.MONKS_ROBE_BOTTOM);
        Utils.equipItem(GHOSTSPEAK_AMULET);
        Utils.equipItem(DRAMEN_STAFF);
    }

    public void startQuest() {
        cQuesterV2.status = "Going to start";
        PathingUtil.walkToArea(START_AREA);
        if (NpcChat.talkToNPC(MARTIN)) {
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation("Ask about the quest.");
            NPCInteraction.handleConversation("Anything I can help with?");
            NPCInteraction.handleConversation("Now that I think about it, you're right!", "Yes.");
            NPCInteraction.handleConversation();
        }
    }

    public void goToGardner1() {
        if (Game.getSetting(671) == 10) {
            cQuesterV2.status = "Going to first Gardener";
            PathingUtil.walkToArea(GARDNER_1_AREA);
            if (NpcChat.talkToNPC(FRIZZY_SKERNIP)) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation("Are you a member of the Group of Advanced Gardeners?");
                NPCInteraction.handleConversation();
            }
        }
    }

    public void goToGardner2() {
        if (Game.getSetting(671) == 10) {
            cQuesterV2.status = "Going to second Gardener";
            PathingUtil.walkToArea(GARDNER_2_AREA);
            if (NpcChat.talkToNPC(ELSTAN)) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation("Are you a member of the Group of Advanced Gardeners?");
                NPCInteraction.handleConversation();
            }
        }
    }

    public void goToGardner3() {
        cQuesterV2.status = "Going to third Gardener";
        PathingUtil.walkToArea(GARDNER_3_AREA);
        if (NpcChat.talkToNPC(HESKEL)) {
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation("Are you a member of the Group of Advanced Gardeners?");
            NPCInteraction.handleConversation();
        }
    }

    public void goToGardner4() {
        cQuesterV2.status = "Going to fourth Gardener";
        PathingUtil.walkToArea(GARDNER_4_AREA);
        if (NpcChat.talkToNPC(FAYETH)) {
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation("Are you a member of the Group of Advanced Gardeners?");
            NPCInteraction.handleConversation();
        }
    }

    public void goToGardner5() {
        cQuesterV2.status = "Going to fifth Gardener";
        PathingUtil.walkToArea(GARDNER_5_AREA);
        if (NpcChat.talkToNPC(TREZNOR)) {
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation("Are you a member of the Group of Advanced Gardeners?");
            NPCInteraction.handleConversation();
        }
    }

    public void goToMartin() {
        cQuesterV2.status = "Returning to Martin";
        PathingUtil.walkToArea(START_AREA);
        if (NpcChat.talkToNPC(MARTIN)) {
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation("Ask about the quest.");
            NPCInteraction.handleConversation();
        }
    }

    public void goToThroneRoom() {
        goToZanaris();
        Utils.equipItem(GHOSTSPEAK_AMULET);
        Utils.equipItem(DRAMEN_STAFF);
        cQuesterV2.status = "Going to Fairy Godfather";
        PathingUtil.walkToArea(THRONE_ROOM);
        if (NpcChat.talkToNPC(FAIRY_GODFATHER)) {
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation("Where's the Fairy Queen?");
            NPCInteraction.handleConversation();
        }
    }

    public void goToGrotto() {
        if (Inventory.find(SYMPTOM_LIST).length < 1) {
            cQuesterV2.status = "Going to Fairy Nuff";
            PathingUtil.walkToArea(FAIRY_NUFFS_GROTTO);
            if (NpcChat.talkToNPC("Fairy Nuff")) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation();
            }
        }
    }

    public void getSkull() {
        if (!BankManager.checkInventoryItems(SPADE)) {
            buyItems();
            getItems();
        }
        if (Inventory.find(DRAYNOR_SKULL).length < 1) {
            cQuesterV2.status = "Getting Draynor skull";
            PathingUtil.walkToArea(GRAVE_AREA);
            PathingUtil.walkToTile(new RSTile(3106, 3382, 0));
            if (Inventory.find(SPADE).length > 0) {
                if (AccurateMouse.click(Inventory.find(SPADE)[0], "Dig"))
                    Timer.waitCondition(() -> Inventory.find(DRAYNOR_SKULL).length > 0, 10000, 12000);
            }
        }
    }

    public void goToDarkWizard() {
        if (Inventory.find(DRAYNOR_SKULL).length > 0) {
            cQuesterV2.status = "Going to Dark Wizards";
            PathingUtil.walkToArea(DARK_WIZARD_TOWER);
            if (NpcChat.talkToNPC(ZANDAR)) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation();
            }
        }
    }

    public void goToMalignus() {
        cQuesterV2.status = "Going to Dark Wizards";
        PathingUtil.walkToArea(MALIGNUS_AREA);
        if (NpcChat.talkToNPC(MALIGNUS)) {
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation("I need help with fighting a Tanglefoot.");
            NPCInteraction.handleConversation();
        }
    }


    private static int[] QUEST_BOX_VISIBLE = {399, 6};

    public static boolean isQuestNameVisible(String questName) {
        RSInterface questBox = Interfaces.get(QUEST_BOX_VISIBLE[0], QUEST_BOX_VISIBLE[1]);
        RSInterface questListParent = Interfaces.get(399, 7);

        if (questBox != null && questListParent != null) {
            RSInterface[] quests = questListParent.getChildren();
            RSInterface questInter = null;
            if (quests != null) {
                for (RSInterface q : quests) {
                    String name = q.getComponentName();

                    if (name != null) {
                        String stripped = General.stripFormatting(name);

                        if (stripped.toLowerCase().contains(questName.toLowerCase())) {
                            return questBox.getAbsoluteBounds().contains(q.getAbsolutePosition());
                        }

                    }
                }
            }
        }
        return false;
    }

    private static int[] SCROLL_BAR_WIDGET = {399, 5, 1};

    private static void moveMouseToQuestBox() {
        RSInterfaceChild box = Interfaces.get(QUEST_BOX_VISIBLE[0], QUEST_BOX_VISIBLE[1]);
        if (box != null && !box.getAbsoluteBounds().contains(Mouse.getPos())){
            Mouse.moveBox(box.getAbsoluteBounds());
        }
    }

    public static void openQuestGuide() {
        if (Interfaces.get(119, 180) == null) {
            General.println("[Debug]: Opening quest guide");
            GameTab.open(GameTab.TABS.QUESTS);
        }
        for (int i = 0; i < 3; i++) {
            if (GameTab.getOpen() == GameTab.TABS.QUESTS) {
                int Y = General.random(306, 312); //this is teh range for the scroll bar
                Optional<Widget> fairytale = Query.widgets().inIndexPath(399, 7)
                        .nameContains("Fairytale I").findFirst();
                if (isQuestNameVisible("Fairytale I") && fairytale.map(f -> f.click()).orElse(false)) {
                    Log.debug("Clicking fairytale");
                    Timer.waitCondition(() -> Interfaces.get(119) != null, 5000, 7000);
                    General.sleep(General.random(500, 2000));
                    return;
                }
                if (Interfaces.get(399, 7) != null
                        && !Interfaces.get(399, 7).getAbsoluteBounds().contains(Mouse.getPos())) {
                    moveMouseToQuestBox();

                    while (Interfaces.get(SCROLL_BAR_WIDGET[0], SCROLL_BAR_WIDGET[1], SCROLL_BAR_WIDGET[2]) != null &&
                            Interfaces.get(SCROLL_BAR_WIDGET[0], SCROLL_BAR_WIDGET[1], SCROLL_BAR_WIDGET[2]).getAbsolutePosition().getY() < Y) {
                        moveMouseToQuestBox();

                        Mouse.scroll(false);
                        General.sleep(General.random(200, 500));
                        if (isQuestNameVisible("Fairytale I"))
                            break;
                    }
                    while (Interfaces.get(SCROLL_BAR_WIDGET[0], SCROLL_BAR_WIDGET[1], SCROLL_BAR_WIDGET[2]) != null
                            && Interfaces.get(SCROLL_BAR_WIDGET[0], SCROLL_BAR_WIDGET[1], SCROLL_BAR_WIDGET[2]).getAbsolutePosition().getY() > Y) {
                        moveMouseToQuestBox();
                        Mouse.scroll(true);
                        General.sleep(General.random(200, 500));
                        if (isQuestNameVisible("Fairytale I"))
                            break;
                    }
                }
            }
            General.sleep(300, 550); //need this after scrolling
            Optional<Widget> fairytale = Query.widgets().inIndexPath(399, 7)
                    .nameContains("Fairytale I").findFirst();
            Log.info("[Debug]: Clicking quest");
            if (isQuestNameVisible("Fairytale I") && fairytale.map(f -> f.click()).orElse(false)) {
                Timer.waitCondition(() -> Interfaces.get(119) != null, 5000, 7000);
                General.sleep(General.random(500, 2000));
                return;
            }
        }
    }


    public void checkUniqueItems() {
        cQuesterV2.status = "Checking unique items needed";
        openQuestGuide();
        if (Interfaces.get(119) != null) {
            line16 = Interfaces.get(119, 16).getText();
            General.println("Line 16 pre-strip: " + line16);
            line16 = General.stripFormatting(line16);
            General.println("Line 16 stripped: " + line16);
            line16 = line16.replaceAll("<col=000080>", "").toLowerCase();
            line16 = line16.replaceAll("<col=800000>", "").toLowerCase();
            General.println("Line 16: " + line16);
            line17 = Interfaces.get(119, 17).getText();
            line17 = line17.replaceAll("<col=000080>", "").toLowerCase();
            line17 = line17.replaceAll("<col=800000>", "").toLowerCase();
            General.println("Line 17: " + line17);

            if (line16.contains("mushroom") || line17.contains("mushroom")) {
                GET_MORT_MYRE_FUNGUS = true;
                General.println("[Debug]: Need a Mort Myre fungus");
            }
            if (line16.contains("seaweed") || line17.contains("seaweed")) {
                GET_EDIBLE_SEAWEED = true;
                General.println("[Debug]: Need some edible Seaweed");
            }
            if (line16.contains("stem") || line17.contains("stem")) {
                GET_MORT_MYRE_STEM = true;
                General.println("[Debug]: Need a Mort Myre stem");
            }
            if (line16.contains("pear") || line17.contains("pear")) {
                GET_MORT_MYRE_PEAR = true;
                General.println("[Debug]: Need a Mort Myre pear");
            }
            if (line16.contains("nature talisman") || line17.contains("nature talisman")) {
                GET_NATURE_TALISMAN = true;
                General.println("[Debug]: Need a Nature Talisman");
            }
            if (line16.contains("crushed gemstone") || line17.contains("crushed gemstone")) {
                GET_CRUSHED_GEMSTONE = true;
                General.println("[Debug]: Need some crushed gemstone");
            }
            if (line16.contains("snapdragon") || line17.contains("snapdragon")) {
                GET_SNAPDRAGON = true;
                General.println("[Debug]: Need Snapdragon");
            }
            if (line16.contains("potato cactus") || line17.contains("potato cactus")) {
                GET_POTATO_CACTUS = true;
                General.println("[Debug]: Need a Potato cactus");
            }
            if (line16.contains("eggs") || line17.contains("eggs")) {
                GET_RED_SPIDERS_EGGS = true;
                General.println("[Debug]: Need Red Spider's eggs");
            }
            if (line16.contains("fat") || line17.contains("fat")) {
                GET_FAT_SNAIL = true;
                General.println("[Debug]: Need a Fat Snail");
            }
            if (line16.contains("slimy eel") || line17.contains("slimy eel")) {
                GET_RAW_SLIMY_EEL = true;
                General.println("[Debug]: Need a Slimy eel");
            }
            if (line16.contains("oyster") || line17.contains("oyster")) {
                GET_OYSTER = true;
                General.println("[Debug]: Need an Oyster");
            }
            if (line16.contains("charcoal") || line17.contains("charcoal")) {
                GET_CHARCOAL = true;
                General.println("[Debug]: Need Charcoal");
            }
            if (line16.toLowerCase().contains("jangerberries") || line17.toLowerCase().contains("jangerberries")) {
                GET_JANGERBERRIES = true;
                General.println("[Debug]: Need Jangerberries");
            }
            if (line16.contains("scale") || line17.contains("scale")) {
                GET_BLUE_DRAGON_SCALE = true;
                General.println("[Debug]: Need blue dragon scale");
            }
            if (line16.contains("proboscis") || line17.contains("proboscis")) {
                GET_PROBOSCIS = true;
                General.println("[Debug]: Need proboscis");
            }
            if (line16.contains("moss") || line17.contains("moss")) {
                GET_VOLENCIA_MOSS = true;
                General.println("[Debug]: Need Volencia moss, adding jungle potion & Druidic ritual");
                List<QuestTask> oldQuestOrder = cQuesterV2.taskList;
                cQuesterV2.taskList.clear();
                cQuesterV2.taskList.add(0, DruidicRitual.get());
                cQuesterV2.taskList.add(1, JunglePotion.get());
                cQuesterV2.taskList.addAll(oldQuestOrder);
            }
            if (line16.contains("baby") || line17.contains("baby")) {
                GET_BABY_DRAGON_BONES = true;
                General.println("[Debug]: Need baby dragon bones");
            }
            if (line16.contains("diamond") || line17.contains("diamond")) {
                GET_UNCUT_DIAMOND = true;
                General.println("[Debug]: Need uncut diamond");
            }
            if (line16.contains("ruby") || line17.contains("ruby")) {
                GET_UNCUT_RUBY = true;
                General.println("[Debug]: Need uncut diamond");
            }
            if (line16.toLowerCase().contains("avantoe") || line17.toLowerCase().contains("avantoe")) {
                GET_AVANTOE = true;
                General.println("[Debug]: Need avantoe");
            }
            if (line16.contains("irit ") || line17.contains("irit ")) {
                GET_IRIT_LEAF = true;
                General.println("[Debug]: Need irit leaf");
            }
            if (line16.contains("vine worm") || line17.contains("vine worm")) {
                GET_RED_VINE_WORM = true;
                General.println("[Debug]: Need red vine worm");
            }
            if (line16.contains("cave eel") || line17.contains("cave eel")) {
                GET_RAW_CAVE_EEL = true;
                General.println("[Debug]: Need raw cave eel");
            }
            if (line16.toLowerCase().contains("grape") || line17.toLowerCase().contains("grape")) {
                GET_GRAPES = true;
                General.println("[Debug]: Need grapes");
            }
            if (line16.toLowerCase().contains("jogre") || line17.toLowerCase().contains("jogre")) {
                GET_JOGRE_BONES = true;
                General.println("[Debug]: Need Jogre bones");
            }
            if (line16.toLowerCase().contains("king") || line17.toLowerCase().contains("king")) {
                GET_KING_WORM = true;
                General.println("[Debug]: Need King worm");
            }
            if (line16.toLowerCase().contains("lime") || line17.toLowerCase().contains("lime")) {
                GET_LIME = true;
                General.println("[Debug]: Need a Lime");
            }
            if (line16.toLowerCase().contains("super") || line17.toLowerCase().contains("super")) {
                GET_SUPERCOMPOST = true;
                General.println("[Debug]: Need supercompost");
            }
            if (line16.toLowerCase().contains("white") || line17.toLowerCase().contains("white")) {
                GET_WHITE_BERRIES = true;
                General.println("[Debug]: Need white berries");
            }
            if (line16.toLowerCase().contains("snape") || line17.toLowerCase().contains("snape")) {
                GET_SNAPE_GRASS = true;
                General.println("[Debug]: Need snape grass");
            }
            if (InterfaceUtil.click(119, 205))
                Timer.waitCondition(() -> Interfaces.isInterfaceSubstantiated(119), 2500, 3000);
        }
    }

    public void buyItems2() {
        cQuesterV2.status = "Buying Unique Items";
        General.println("[Debug]: Buying Unique Items");
        BankManager.open(true);
        BankManager.getAllList();
        int crushedGemInBank = BankManager.getCount(CRUSHED_GEMSTONE);
        BankManager.withdraw(0, true, 995);
        BankManager.withdrawArray(ItemID.RING_OF_WEALTH, 1);
        BankManager.close(true);

        if (GET_MORT_MYRE_FUNGUS)
            specialBuyItems.add(new GEItem(MORT_MYRE_FUNGUS, 1, 200));

        if (GET_MORT_MYRE_STEM)
            specialBuyItems.add(new GEItem(MORT_MYRE_STEM, 1, 500));

        if (GET_NATURE_TALISMAN)
            specialBuyItems.add(new GEItem(NATURE_TALISMAN, 1, 260));

        if (GET_EDIBLE_SEAWEED)
            specialBuyItems.add(new GEItem(EDIBLE_SEAWEED, 1, 200));

        if (GET_POTATO_CACTUS)
            specialBuyItems.add(new GEItem(POTATO_CACTUS, 1, 250));


        if (GET_CRUSHED_GEMSTONE && crushedGemInBank < 1) {
            specialBuyItems.add(new GEItem(UNCUT_OPAL, 27, 50));
            specialBuyItems.add(new GEItem(CHISEL, 1, 500));
        }

        if (GET_SNAPDRAGON)
            specialBuyItems.add(new GEItem(SNAPDRAGON, 1, 20));

        if (GET_BABY_DRAGON_BONES)
            specialBuyItems.add(new GEItem(BABY_DRAGON_BONES, 1, 20));

        if (GET_BLUE_DRAGON_SCALE)
            specialBuyItems.add(new GEItem(BLUE_DRAGON_SCALE, 1, 200));

        if (GET_CHARCOAL)
            specialBuyItems.add(new GEItem(CHARCOAL, 1, 200));

        if (GET_AVANTOE)
            specialBuyItems.add(new GEItem(AVANTOE, 1, 200));

        if (GET_IRIT_LEAF)
            specialBuyItems.add(new GEItem(IRIT_LEAF, 1, 200));

        if (GET_EDIBLE_SEAWEED)
            specialBuyItems.add(new GEItem(EDIBLE_SEAWEED, 2, 200));

        if (GET_FAT_SNAIL)
            specialBuyItems.add(new GEItem(FAT_SNAIL, 1, 200));

        if (GET_GRAPES)
            specialBuyItems.add(new GEItem(GRAPES, 1, 200));

        if (GET_JANGERBERRIES)
            specialBuyItems.add(new GEItem(JANGERBERRIES, 1, 200));

        if (GET_JOGRE_BONES)
            specialBuyItems.add(new GEItem(JOGRE_BONES, 1, 200));

        if (GET_KING_WORM)
            specialBuyItems.add(new GEItem(KING_WORM, 1, 200));

        if (GET_LIME)
            specialBuyItems.add(new GEItem(LIME, 1, 200));

        if (GET_MORT_MYRE_PEAR)
            specialBuyItems.add(new GEItem(MORT_MYRE_PEAR, 1, 200));

        if (GET_PROBOSCIS)
            specialBuyItems.add(new GEItem(PROBOSCIS, 1, 200));

        if (GET_RAW_CAVE_EEL)
            specialBuyItems.add(new GEItem(RAW_CAVE_EEL, 1, 200));

        if (GET_RAW_SLIMY_EEL)
            specialBuyItems.add(new GEItem(RAW_SLIMY_EEL, 1, 200));

        if (GET_SNAPE_GRASS)
            specialBuyItems.add(new GEItem(SNAPE_GRASS, 1, 200));

        if (GET_SUPERCOMPOST)
            specialBuyItems.add(new GEItem(SUPERCOMPOST, 1, 200));

        if (GET_UNCUT_DIAMOND)
            specialBuyItems.add(new GEItem(UNCUT_DIAMOND, 1, 200));

        if (GET_UNCUT_RUBY)
            specialBuyItems.add(new GEItem(UNCUT_RUBY, 1, 20));


        if (GET_WHITE_BERRIES)
            specialBuyItems.add(new GEItem(WHITE_BERRIES, 1, 200));


        if (GET_RED_SPIDERS_EGGS)
            specialBuyItems.add(new GEItem(RED_SPIDERS_EGGS, 1, 200));
        ;

        if (GET_OYSTER)
            specialBuyItems.add(new GEItem(OYSTER, 1, 200));


        if (GET_KING_WORM)
            specialBuyItems.add(new GEItem(KING_WORM, 1, 200));

        BuyItemsStep buyStep2 = new BuyItemsStep(specialBuyItems);
        buyStep2.buyItems();
    }

    public void makeCrushedGem() {
        if (GET_CRUSHED_GEMSTONE) {
            BankManager.open(true);
            if (BankManager.getCount(CRUSHED_GEMSTONE) < 1) {
                BankManager.depositAll(true);
                if (Inventory.find(CHISEL).length < 1 || Inventory.find(UNCUT_OPAL).length < 1) {
                    cQuesterV2.status = "Getting items for crushed gemstone";
                    BankManager.withdraw(1, CHISEL);
                    BankManager.withdraw(0, UNCUT_OPAL);
                    BankManager.close(true);
                    Utils.shortSleep();
                }

                if (Inventory.find(CHISEL).length > 0 && Inventory.find(UNCUT_OPAL).length > 0) {
                    cQuesterV2.status = "Making crushed gemstone";
                    if (Utils.useItemOnItem(CHISEL, UNCUT_OPAL))
                        Timer.waitCondition(() -> Interfaces.isInterfaceSubstantiated(270), 7000, 9000);
                    if (InterfaceUtil.clickInterfaceAction(270, "Cut"))
                        Timer.abc2SkillingWaitCondition(() -> Inventory.find(UNCUT_OPAL).length < 1
                                || Inventory.find(CRUSHED_GEMSTONE).length > 0, 20000, 30000);
                }
            }
        }
    }

    public void getItems2() {
        if (GET_RED_VINE_WORM)
            collectWorms();

        cQuesterV2.status = "Getting items";
        General.println("[Debug]: Withdrawing items.");
        BankManager.open(true);
        BankManager.checkEquippedGlory();
        BankManager.depositAll(true);

        if (GET_MORT_MYRE_FUNGUS)
            BankManager.withdraw(1, true, MORT_MYRE_FUNGUS);

        if (GET_MORT_MYRE_STEM)
            BankManager.withdraw(1, true, MORT_MYRE_STEM);

        if (GET_NATURE_TALISMAN)
            BankManager.withdraw(1, true, NATURE_TALISMAN);

        if (GET_CRUSHED_GEMSTONE)
            BankManager.withdraw(1, true, CRUSHED_GEMSTONE);

        if (GET_SNAPDRAGON)
            BankManager.withdraw(1, true, SNAPDRAGON);

        if (GET_BABY_DRAGON_BONES)
            BankManager.withdraw(1, true, BABY_DRAGON_BONES);

        if (GET_RED_VINE_WORM)
            BankManager.withdraw(1, true, RED_VINE_WORM);

        if (GET_BLUE_DRAGON_SCALE)
            BankManager.withdraw(1, true, BLUE_DRAGON_SCALE);

        if (GET_CHARCOAL)
            BankManager.withdraw(1, true, CHARCOAL);

        if (GET_OYSTER)
            BankManager.withdraw(1, true, OYSTER);

        if (GET_AVANTOE)
            BankManager.withdraw(1, true, AVANTOE);

        if (GET_IRIT_LEAF)
            BankManager.withdraw(1, true, IRIT_LEAF);

        if (GET_EDIBLE_SEAWEED)
            BankManager.withdraw(1, true, EDIBLE_SEAWEED);

        if (GET_FAT_SNAIL)
            BankManager.withdraw(1, true, FAT_SNAIL);

        if (GET_GRAPES)
            BankManager.withdraw(1, true, GRAPES);

        if (GET_JANGERBERRIES)
            BankManager.withdraw(1, true, JANGERBERRIES);

        if (GET_JOGRE_BONES)
            BankManager.withdraw(1, true, JOGRE_BONES);

        if (GET_KING_WORM)
            BankManager.withdraw(2, true, KING_WORM);

        if (GET_LIME)
            BankManager.withdraw(1, true, LIME);

        if (GET_MORT_MYRE_PEAR)
            BankManager.withdraw(1, true, MORT_MYRE_PEAR);

        if (GET_POTATO_CACTUS)
            BankManager.withdraw(1, true, POTATO_CACTUS);

        if (GET_PROBOSCIS)
            BankManager.withdraw(1, true, PROBOSCIS);

        if (GET_RAW_CAVE_EEL)
            BankManager.withdraw(1, true, RAW_CAVE_EEL);

        if (GET_RAW_SLIMY_EEL)
            BankManager.withdraw(1, true, RAW_SLIMY_EEL);

        if (GET_SNAPE_GRASS)
            BankManager.withdraw(1, true, SNAPE_GRASS);

        if (GET_SUPERCOMPOST)
            BankManager.withdraw(1, true, SUPERCOMPOST);

        if (GET_UNCUT_DIAMOND)
            BankManager.withdraw(1, true, UNCUT_DIAMOND);

        if (GET_UNCUT_RUBY)
            BankManager.withdraw(1, true, UNCUT_RUBY);

        if (GET_WHITE_BERRIES)
            BankManager.withdraw(1, true, WHITE_BERRIES);

        if (GET_RED_SPIDERS_EGGS)
            BankManager.withdraw(1, true, RED_SPIDERS_EGGS);

        if (GET_VOLENCIA_MOSS)
            BankManager.withdraw(1, true, VOLENCIA_MOSS);

        BankManager.withdraw(1, true, SECATEURS);
        BankManager.withdraw(1, true, SPADE);
        BankManager.withdraw(1, true, DRAMEN_STAFF);
        BankManager.withdraw(1, true, GHOSTSPEAK_AMULET);
        BankManager.withdraw(1, true, SALVE_TELE);
        BankManager.withdraw(1, true,
                ItemID.AMULET_OF_GLORY[0]);
        BankManager.withdraw(5, true,
                ItemID.FALADOR_TELEPORT);
        BankManager.withdraw(5, true,
                ItemID.VARROCK_TELEPORT);
        BankManager.withdraw(5, true,
                ItemID.LUMBRIDGE_TELEPORT);
        BankManager.withdraw(1, true,
                ItemID.STAMINA_POTION[0]);
        BankManager.withdraw(11, true, MONKFISH);
        BankManager.withdraw(4, true, ItemID.PRAYER_POTION[0]);
        Banking.close();
        Utils.equipItem(GHOSTSPEAK_AMULET);
        Utils.equipItem(DRAMEN_STAFF);
    }

    public void goToSwamp() {
        if (!GROTTO_TREE_AREA.contains(Player.getPosition())) {
            RSObject[] holyBarrier = Objects.findNearest(20, "Holy barrier");
            if (holyBarrier.length > 0) {
                if (!holyBarrier[0].isClickable())
                    holyBarrier[0].adjustCameraTo();

                if (AccurateMouse.click(holyBarrier[0], "Pass-through")) {
                    Timer.waitCondition(() -> NPCs.find("Drezel").length < 1, 8000, 12000);
                    General.sleep(General.random(2000, 3000));
                }
            }
            PathingUtil.walkToArea(BEFORE_BRIDGE);
            Utils.idle(500, 3500);
        }
        if (Objects.findNearest(20, "Bridge").length > 0 && !GROTTO_TREE_AREA.contains(Player.getPosition())) {
            RSObject[] bridge = Objects.findNearest(20, "Bridge");
            if (bridge.length > 0) {
                if (AccurateMouse.click(bridge[0], "Jump")) {
                    Timer.waitCondition(() -> GROTTO_TREE_AREA.contains(Player.getPosition()) && !Player.isMoving(), 8000, 12000);
                    Utils.idle(900, 3500);
                }
            }
        }
        if (GROTTO_TREE_AREA.contains(Player.getPosition())) {
            RSObject[] grottoTree = Objects.findNearest(10, 3516);
            if (grottoTree.length > 0) {
                if (AccurateMouse.click(grottoTree[0], "Enter")) {
                    Timer.waitCondition(() -> !GROTTO_TREE_AREA.contains(Player.getPosition()) && !Player.isMoving(), 8000, 12000);
                    Utils.idle(900, 3500);
                }
            }
        }
    }

    public void talkToNatureSpirit() {
        Utils.equipItem(GHOSTSPEAK_AMULET);
        cQuesterV2.status = "Going to Nature Spirit";
        if (NPCs.find(NATURE_SPIRIT).length < 1) {
            goToSwamp();
        }
        if (NpcChat.talkToNPC(NATURE_SPIRIT)) {
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation();
        }
        Utils.modSleep();
        Utils.cutScene();
    }

    private void goToZanaris() {
        if (!WHOLE_ZANARIS.contains(Player.getPosition()) && NPCs.find("Tanglefoot").length < 1
                && NPCs.find("Baby tanglefoot").length < 1) {
            cQuesterV2.status = "Going to Zanaris";
            PathingUtil.walkToArea(SWAMP_AREA);
            Utils.equipItem(DRAMEN_STAFF);
            if (Utils.clickObj(2406, "Open")) {
                Timer.waitCondition(() -> WHOLE_ZANARIS.contains(Player.getPosition()), 7000, 12000);
                Utils.modSleep();
            }
        }
    }

    RSTile tunnelInsideTile;

    public void goToFight() {
        if (!TUNNEL_ENTRANCE.contains(Player.getPosition()) && NPCs.find("Tanglefoot").length < 1
                && NPCs.find("Baby tanglefoot").length < 1) {
            goToZanaris();
            cQuesterV2.status = "Going to Tunnels";
            PathingUtil.walkToArea(TUNNEL_ENTRANCE);
        }
        if (WHOLE_ZANARIS.contains(Player.getPosition())) {
            if (Utils.equipItem(MAGIC_SECATEURS))
                Utils.shortSleep();

            if (Equipment.find(MAGIC_SECATEURS).length > 0) {
                cQuesterV2.status = "Going to Boss";
                if (Utils.clickObj(11999, "Squeeze-through")) {
                    NPCInteraction.waitForConversationWindow();
                    NPCInteraction.handleConversation();
                    Timer.waitCondition(() -> !TUNNEL_ENTRANCE.contains(Player.getPosition()), 8000, 12000);
                    tunnelInsideTile = Player.getPosition();
                }
            }
            RSTile destination = Player.getPosition().translate(-15, 0);

            PathingUtil.localNavigation(destination);
            PathingUtil.movementIdle();
            General.println("[Walking]: Waiting to arrive at destination");
            Timer.waitCondition(() -> Player.getPosition().distanceTo(destination) <= 4, 5000, 15000);
            Utils.idle(3000, 9000);
        }

        RSNPC[] boss = NPCs.find(TANGLEFOOT);
        while (!TUNNEL_ENTRANCE.contains(Player.getPosition()) && boss.length > 0) {
            General.sleep(50);
            cQuesterV2.status = "Going to Boss";
            int eatAt = General.random(40, 65);

            if (boss[0].getPosition().distanceTo(Player.getPosition()) > 4) {
                PathingUtil.localNavigation(boss[0].getPosition());
                PathingUtil.movementIdle();
            }

            if (Prayer.getPrayerPoints() > 0)
                Prayer.enable(Prayer.PRAYERS.PROTECT_FROM_MELEE);

            if (!Combat.isUnderAttack()) {
                cQuesterV2.status = "Attacking Boss";
                if (CombatUtil.clickTarget(boss[0]))
                    Timer.waitCondition(() -> Combat.isUnderAttack() && Combat.getTargetEntity() != null
                            && Combat.getTargetEntity().equals(boss[0]), 8000, 10000);

            } else if (Combat.isUnderAttack() && Combat.getTargetEntity() != null
                    && Combat.getTargetEntity().equals(boss[0])) {
                cQuesterV2.status = "Killing boss";
                Timer.waitCondition(() -> Combat.getHPRatio() < eatAt ||
                        Prayer.getPrayerPoints() < 12
                        || GroundItems.find(QUEENS_SECATEURS).length > 0 ||
                        NPCs.find(TANGLEFOOT).length < 1, 40000, 50000);
            }

            if (Prayer.getPrayerPoints() < General.random(8, 20))
                Utils.drinkPotion(ItemID.PRAYER_POTION);

            if (GroundItems.find(QUEENS_SECATEURS).length > 0) {
                cQuesterV2.status = "Looting queens secateurs";
                if (Utils.clickGroundItem(QUEENS_SECATEURS)) {
                    Timer.waitCondition(() -> Inventory.find(QUEENS_SECATEURS).length > 0, 8000, 12000);
                    break;
                }
            }
            if (Combat.getHPRatio() < eatAt)
                EatUtil.eatFood(true);


        }

        RSGroundItem[] secateurs = GroundItems.find(QUEENS_SECATEURS);
        if (secateurs.length > 0) {
            cQuesterV2.status = "Looting queens secateurs";
            if (Utils.clickGroundItem(QUEENS_SECATEURS))
                Timer.waitCondition(() -> Inventory.find(QUEENS_SECATEURS).length > 0, 8000, 12000);
        }
        if (Inventory.find(QUEENS_SECATEURS).length > 0) {
            Prayer.disable(Prayer.PRAYERS.PROTECT_FROM_MELEE);
            PathingUtil.localNavigation(tunnelInsideTile);
            PathingUtil.movementIdle();
        }
        if (Utils.clickObj(12004, "Squeeze-through")) {
            Utils.modSleep();
            NPCInteraction.handleConversation();
        }


    }

    public void finishQuest() {
        if (GroundItems.find(QUEENS_SECATEURS).length > 0) {
            cQuesterV2.status = "Looting queens secateurs";
            if (Utils.clickGroundItem(QUEENS_SECATEURS))
                Timer.waitCondition(() -> Inventory.find(QUEENS_SECATEURS).length > 0, 8000, 12000);
        }
        Prayer.disable(Prayer.PRAYERS.PROTECT_FROM_MELEE);

        cQuesterV2.status = "Finishing quest";
        PathingUtil.walkToArea(THRONE_ROOM);
        if (NpcChat.talkToNPC(FAIRY_GODFATHER)) {
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation();
        }
    }


    RSArea WOODS = new RSArea(new RSTile(2626, 3491, 0), new RSTile(2633, 3498, 0));
    RSObject[] redVine;

    public void collectWorms() {
        cQuesterV2.status = "Getting worms";
        if (Inventory.find(SPADE).length < 1 && Inventory.find(RED_VINE_WORM).length < 1) {
            BankManager.open(true);
            if (BankManager.withdraw(1, true, RED_VINE_WORM))
                return;
            BankManager.depositAll(true);
            BankManager.withdraw(1, true, ItemID.SKILLS_NECKLACE);
            BankManager.withdraw(1, true, ItemID.RING_OF_WEALTH);
            BankManager.withdraw(1, true, SPADE);
            BankManager.withdraw(1, true, ItemID.STAMINA_POTION);

            BankManager.close(true);
        }
        if (!WOODS.contains(Player.getPosition()) && Inventory.find(SPADE).length > 0) {
            cQuesterV2.status = "Going to woods";
            General.println("[Debug]: " + cQuesterV2.status);
            PathingUtil.walkToTile(new RSTile(2630, 3494, 0));
        }
        if (WOODS.contains(Player.getPosition())) {
            redVine = Objects.findNearest(20, "Vine");
            if (redVine.length > 0) {
                if (AccurateMouse.click(Objects.findNearest(20, "Vine")[0], "Check")) {
                    Timer.waitCondition(() -> Inventory.find(RED_VINE_WORM).length > 0, 5000, 7000);
                    if (Inventory.find(RED_VINE_WORM).length > 0) {
                        if (AccurateMouse.click(redVine[0], "Check"))
                            Timer.waitCondition(() -> Inventory.find(RED_VINE_WORM)[0].getStack() == 2, 5000, 7000);
                    }
                }
            }
        }
    }

    public void talkToMartin(String string) {
        cQuesterV2.status = "Going to Martin";
        PathingUtil.walkToArea(START_AREA);
        if (NpcChat.talkToNPC(MARTIN)) {
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation("Ask about the quest.", string);
            NPCInteraction.handleConversation(string, "Yes.");
            NPCInteraction.handleConversation();
        }
    }

    public void goToGrottoPart2() {
        Utils.equipItem(DRAMEN_STAFF);
        cQuesterV2.status = "Going to Fairy Nuff";
        PathingUtil.walkToArea(FAIRY_NUFFS_GROTTO);
        Timer.waitCondition(() -> Utils.getVarBitValue(PART_2_VARBIT) == 30, 20000, 30000);
    }

    public void getCertificate() {
        RSItem[] cert = Inventory.find(HEALING_CERTIFICATE);
        if (cert.length == 0) {
            PathingUtil.walkToArea(FAIRY_NUFFS_GROTTO);
            if (Utils.clickObj("Healing certificate", "Take"))
                Timer.waitCondition(() -> Inventory.find(HEALING_CERTIFICATE).length > 0, 6000, 9000);
        } else if (Utils.getVarBitValue(2336) == 0) {
            if (cert[0].click("Study"))
                Timer.waitCondition(() -> Utils.getVarBitValue(2336) == 1, 4000, 6000);
            if (Interfaces.isInterfaceSubstantiated(424, 1)) {
                Interfaces.get(424, 1).click();
            }
        }
    }

    public void talkToChef() {
        PathingUtil.walkToTile(new RSTile(2386, 4439, 0), 3, true);
        if (NpcChat.talkToNPC("Fairy chef")) {
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation();
        }
    }

    public void goToFairyGodFather() {
        Utils.equipItem(DRAMEN_STAFF);
        cQuesterV2.status = "Going to Fairy Godfather";
        PathingUtil.walkToArea(THRONE_ROOM);
        if (NpcChat.talkToNPC(FAIRY_GODFATHER)) {
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation("Where is the Fairy Queen?",
                    "Do you have any idea who could have done this?",
                    "Where could she have been taken to?",
                    "yes, okay."
            );
            NPCInteraction.handleConversation();
        }
    }

    public void talkToCoordinator() {
        cQuesterV2.status = "Talking to Coordinator";
        PathingUtil.walkToTile(new RSTile(2453, 4452, 0), 4, true);
        if (NpcChat.talkToNPC("Co-ordinator")) {
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation("The fairy rings.");
            NPCInteraction.handleConversation();
        }
    }

    public void talkToFixIt() {
        cQuesterV2.status = "Talking to Fairy Fix it";
        PathingUtil.walkToTile(new RSTile(2413, 4436, 0), 6, true);
        if (NpcChat.talkToNPC("Fairy Fixit")) {
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation("Why are you carrying that toolbox?");
        }
    }

    public void useFairyRing() {
        cQuesterV2.status = "Talking to Fairy Fix it";
        Utils.equipItem(DRAMEN_STAFF);
        if (Utils.clickObj("Fairy ring", "Configure")) {
            Timer.waitCondition(() -> Interfaces.isInterfaceSubstantiated(398), 5000, 7000);
        }
        if (Interfaces.isInterfaceSubstantiated(398)) {
            InterfaceUtil.clickInterfaceAction(398, "Confirm");
            Utils.modSleep();
            // Main.FAIRY_TALE_PT1 = false;
            //  Main.checkScriptStatus(Main.fairyTalePart1);
        }
    }

    public void doQuestPart2() {
        if (Utils.getVarBitValue(PART_2_VARBIT) == 0) {
            talkToMartin("Ask about the quest."); // needs to wait 5 min
        } else if (Utils.getVarBitValue(PART_2_VARBIT) == 5) {
            cQuesterV2.status = "Waiting for crops to grow";
            General.println("[Debug]: " + cQuesterV2.status + " (mouse off screen)");
            Mouse.leaveGame(true);
            Timer.abc2WaitCondition(() -> Utils.getVarBitValue(PART_2_VARBIT) == 10, 120000, 180000);
            Mouse.pickupMouse();
            Mouse.randomRightClick();
        } else if (Utils.getVarBitValue(PART_2_VARBIT) == 10) {
            talkToMartin("I suppose I'd better go and see what the problem is then.");
        } else if (Utils.getVarBitValue(PART_2_VARBIT) == 20) {
            goToGrottoPart2();

        } else if (Utils.getVarBitValue(PART_2_VARBIT) == 30) {
            getCertificate(); //varbit 2327 changes from 1->2 here

            if (Utils.getVarBitValue(2337) == 0) {
                talkToChef();
            }
            if (Utils.getVarBitValue(2337) == 2 && Utils.getVarBitValue(2329) == 0) {
                goToFairyGodFather(); // 2329  from  0 -> 2
            }
        } else if (Utils.getVarBitValue(PART_2_VARBIT) == 40 && Utils.getVarBitValue(2329) == 2) {

            talkToCoordinator();
            talkToFixIt();
            useFairyRing();
            cQuesterV2.taskList.remove(this);
        }
    }

    int PART_2_VARBIT = 2326;
    int HEALING_CERTIFICATE = 9025;
    int PART_1_VARBIT = 1803;
    int GAME_SETTING = 671;


    @Override
    public void execute() {
        if (!checkRequirements())
            cQuesterV2.taskList.remove(this);


        if (Game.getSetting(671) == 0) { // varbit 1803 goes 0->10
            buyItems();
            getItems();
            startQuest();
        }
        if (Game.getSetting(671) == 10) {
            goToGardner1();
            goToGardner2();
            goToGardner3();
            goToGardner4();
            goToGardner5();
            goToMartin();

        } else if (Game.getSetting(671) == 20 || RSVarBit.get(1803).getValue() == 20) {
            goToThroneRoom();
        } else if (RSVarBit.get(1803).getValue() == 30) {
            goToGrotto();

        } else if (RSVarBit.get(1803).getValue() == 40) {
            getSkull();
            goToDarkWizard();
        } else if (Utils.getVarBitValue(1803) == 50)
            goToMalignus();

        else if (Utils.getVarBitValue(1803) == 60) {
            checkUniqueItems();
            if (GET_VOLENCIA_MOSS && Game.getSetting(175) < 12) {
                General.println("[Debug]: Need Volencia moss, adding jungle potion & Druidic ritual");
                List<QuestTask> oldQuestOrder = cQuesterV2.taskList;
                cQuesterV2.taskList.clear();
                cQuesterV2.taskList.add(0, JunglePotion.get());
                cQuesterV2.taskList.addAll(oldQuestOrder);
                return;
            }
            buyItems2();
            makeCrushedGem();
            getItems2();
            talkToNatureSpirit();

        } else if (RSVarBit.get(1803).getValue() == 70) {
            goToFight();

        } else if (RSVarBit.get(1803).getValue() == 80) {
            finishQuest();

        } else if (RSVarBit.get(1803).getValue() == 90 && Utils.getVarBitValue(PART_2_VARBIT) < 50) {
            doQuestPart2();
        } else if (Utils.getVarBitValue(PART_2_VARBIT) < 50 && Utils.getVarBitValue(2329) == 2) {
            Log.log("VARBIT" + Utils.getVarBitValue(PART_2_VARBIT));
            doQuestPart2();
        } else {
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
        return "Fairy Tale Pt.1";
    }

    @Override
    public boolean checkRequirements() {
        if (Game.getSetting(147) < 6) {
            General.println("[Debug]: Need to complete Lost City");
            return false;
        }
        if (Game.getSetting(307) < 110) {
            General.println("[Debug]: Need to complete Nature Spirit");
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
        return Quest.FAIRYTALE_I_GROWING_PAINS.getState().equals(Quest.State.COMPLETE);
    }
}
