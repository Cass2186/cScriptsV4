package scripts.QuestPackages.ZogreFleshEaters;

import dax.walker.utils.AccurateMouse;
import dax.walker.utils.camera.DaxCamera;
import dax.walker_engine.interaction_handling.NPCInteraction;
import org.tribot.api.DynamicClicking;
import org.tribot.api.General;
import org.tribot.api2007.*;
import org.tribot.api2007.ext.Filters;
import org.tribot.api2007.types.*;
import scripts.*;
import scripts.GEManager.GEItem;
import scripts.QuestPackages.RestlessGhost.RestlessGhost;
import scripts.QuestSteps.BuyItemsStep;
import scripts.QuestSteps.QuestTask;
import scripts.Requirements.ItemRequirement;
import scripts.Requirements.Requirement;
import scripts.Tasks.Priority;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class ZogreFleshEaters implements QuestTask {
    private static ZogreFleshEaters quest;

    public static ZogreFleshEaters get() {
        return quest == null ? quest = new ZogreFleshEaters() : quest;
    }

    int CRUSHED_BARACADE = 6878;
    int RUINED_BACKPACK = 4810;
    int KNIFE = 946;
    int TORN_PAGE = 4809;
    int ROTTEN_FOOD = 2959;
    int BLACK_PRISM = 4808;
    int TANKARD = 4811;
    int SITHIK_ID = 6887;
    int CHARCOAL = 973;
    int NECROMANCY_BOOK = 4837;
    int BOOK_OF_HAM = 4829;
    int BOOK_OF_PORTRAITURE = 4817;
    int PAPYRUS = 970;
    int PORTRAIT = 4814;
    int WRONG_PORTRAIT = 4815;
    int STRANGE_POTION = 4836;
    int RELICYMS_BALM_4 = 4842; //Relicym
    int[] RELICYMS_BALM = {4842, 4844, 4846, 4848};
    int[] SUPER_RESTORE = {3024, 3026, 3028, 3030};
    int RUNE_BRUTAL_ARROWS = 4803;
    int OGRE_COMP_BOW = 4827;
    int BLACK_VAMBS = 2491;
    int BLACK_BODY = 2503;
    int BLACK_CHAPS = 2497;
    int AVAS_ACCUMULATOR = 10499;
    int YEW_SHORTBOW = 857;
    int RUNE_ARROW = 892;
    int MAGIC_SHORTBOW = 861;
    int SNAKESKIN_BANDANA = 6326;
    int SNAKESKIN_BOOTS = 6328;

    int GAME_SETTING = 455;

    public static boolean isRightPortrait = false;

    RSArea START_AREA = new RSArea(new RSTile(2440, 3053, 0), new RSTile(2447, 3046, 0));
    RSArea GUARD_BEFORE_GATE = new RSArea(new RSTile(2455, 3044, 0), new RSTile(2452, 3052, 0));
    RSArea AFTER_GATE = new RSArea(new RSTile(2458, 3055, 0), new RSTile(2491, 3036, 0));
    RSArea BEFORE_DESCENT = new RSArea(new RSTile(2485, 3044, 0), new RSTile(2486, 3045, 0));
    RSArea WHOLE_DUNGEON_LEVEL_2 = new RSArea(new RSTile(2430, 9473, 2), new RSTile(2493, 9367, 2));
    RSArea IN_FRONT_OF_COFFIN = new RSArea(new RSTile(2439, 9459, 2), new RSTile(2441, 9456, 2));
    RSArea YANILE_BAR = new RSArea(new RSTile(2549, 3081, 0), new RSTile(2555, 3078, 0));
    RSArea OUTSIDE_WIZARDS_GUILD = new RSArea(new RSTile(2597, 3089, 0), new RSTile(2600, 3086, 0));
    RSArea SITHIK_ROOM = new RSArea(new RSTile(2592, 3103, 1), new RSTile(2590, 3104, 1));
    private RSArea HUGE_START_AREA = new RSArea(
            new RSTile[] {
                    new RSTile(2451, 3052, 0),
                    new RSTile(2451, 3046, 0),
                    new RSTile(2440, 3038, 0),
                    new RSTile(2427, 3050, 0),
                    new RSTile(2429, 3066, 0),
                    new RSTile(2453, 3068, 0)
            }
    );

    String Zavistic_Rarve_CHAT_1 = "I'm here about the sicks...err Zogres";
    String Sithik_Ints_CHAT_1 = "Do you mind if I look around?";
    public static String portraitString = "A classic realist charcoal portrait of Sithik.";

    @Override
    public boolean checkRequirements() {
        if (Game.getSetting(293) < 65) {
            General.println("[Debug]: Need to complete big chompy bird hunting");
            return false;
        } else if (Skills.getActualLevel(Skills.SKILLS.SMITHING) < 4 ||
                Skills.getActualLevel(Skills.SKILLS.RANGED) < 60) {
            General.println("[Debug]: have 60 ranged (my requirement) and 4 smithing");
            return false;
        } else if (Game.getSetting(175) < 12) {
            General.println("[Debug]: Need to complete Jungle potion");
            return false;
        }
        return true;
    }

    ArrayList<GEItem> itemsToBuy = new ArrayList<GEItem>(
            Arrays.asList(
                    new GEItem(ItemID.SANFEW_SERUM[0], 2, 30),
                    new GEItem(ItemID.MONKFISH, 15, 30),
                    new GEItem(ItemID.STAFF_OF_AIR, 1, 300),
                    new GEItem(RELICYMS_BALM_4, 2, 300),
                    new GEItem(ItemID.SNAKESKIN_BANDANA, 1, 300),
                    new GEItem(ItemID.SNAKESKIN_BOOTS, 1, 300),
                    new GEItem(OGRE_COMP_BOW, 1, 300),
                    new GEItem(MAGIC_SHORTBOW, 1, 50),
                    new GEItem(RUNE_ARROW, 500, 30),
                    new GEItem(RUNE_BRUTAL_ARROWS, 150, 20),
                    new GEItem(SUPER_RESTORE[0], 2, 20),
                    new GEItem(ItemID.RING_OF_DUELING[0], 2, 30),
                    new GEItem(ItemID.AMULET_OF_GLORY[2], 2, 30),
                    new GEItem(ItemID.STAMINA_POTION[0], 3, 15),
                    new GEItem(ItemID.RING_OF_WEALTH[0], 1, 25)
            )
    );

    public void buyItems() {
        cQuesterV2.status = "Buying Items";
        General.println("[Debug]: Buying Items");
        if (Skills.SKILLS.RANGED.getActualLevel() >= 70) {
            itemsToBuy.add(new GEItem(BLACK_BODY, 1, 50));
            itemsToBuy.add(new GEItem(BLACK_VAMBS, 1, 50));
            itemsToBuy.add(new GEItem(BLACK_CHAPS, 1, 50));
        } else if (Skills.SKILLS.RANGED.getActualLevel() >= 60) {
            itemsToBuy.add(new GEItem(ItemID.RED_DHIDE_BODY, 1, 50));
            itemsToBuy.add(new GEItem(ItemID.RED_DHIDE_CHAPS, 1, 50));
            itemsToBuy.add(new GEItem(ItemID.RED_DHIDE_VAMBRACES, 1, 50));
        }
        BuyItemsStep buyStep = new BuyItemsStep(itemsToBuy);
        buyStep.buyItems();

    }

    public void getItems() {
        cQuesterV2.status = "Getting Items";
        General.println("[Debug]: Getting Items");
        BankManager.open(true);
        BankManager.depositEquipment();
        BankManager.checkEquippedGlory();
        BankManager.depositAll(true);
        BankManager.withdraw(1, true, SUPER_RESTORE[0]);
        BankManager.withdraw(10, true, ItemID.MONKFISH);
        BankManager.withdraw(1, true, ItemID.RING_OF_DUELING[0]);
        BankManager.withdraw(2, true, ItemID.STAMINA_POTION[0]);
        //  BankManager.withdraw(250, true, Const.get().CHAOS_RUNE);
        //  BankManager.withdraw(600, true, Const.get().EARTH_RUNE);
        //  BankManager.withdraw(1, true, Const.get().STAFF_OF_AIR);
        BankManager.withdraw(2, true, RELICYMS_BALM_4);
        if (Skills.SKILLS.RANGED.getActualLevel() >= 70) {
            BankManager.withdraw(1, true, BLACK_VAMBS);
            BankManager.withdraw(1, true, BLACK_BODY);
            BankManager.withdraw(1, true, BLACK_CHAPS);
            Utils.equipItem(BLACK_VAMBS);
            Utils.equipItem(BLACK_BODY);
            Utils.equipItem(BLACK_CHAPS);
        } else if (Skills.SKILLS.RANGED.getActualLevel() >= 60) {
            BankManager.withdraw(1, true, ItemID.RED_DHIDE_BODY);
            BankManager.withdraw(1, true, ItemID.RED_DHIDE_VAMBRACES);
            BankManager.withdraw(1, true, ItemID.RED_DHIDE_CHAPS);
            Utils.equipItem(ItemID.RED_DHIDE_CHAPS);
            Utils.equipItem(ItemID.RED_DHIDE_BODY);
            Utils.equipItem(ItemID.RED_DHIDE_VAMBRACES);
        }
        BankManager.withdraw(1, true, SNAKESKIN_BANDANA);
        BankManager.withdraw(1, true, SNAKESKIN_BOOTS);
        BankManager.withdraw(1, true, AVAS_ACCUMULATOR);

        Utils.equipItem(SNAKESKIN_BOOTS);
        Utils.equipItem(SNAKESKIN_BANDANA);
        Utils.equipItem(AVAS_ACCUMULATOR);
        Utils.equipItem(ItemID.RING_OF_DUELING[0]);
        BankManager.withdraw(500, true, RUNE_ARROW);
        BankManager.withdraw(150, true, RUNE_BRUTAL_ARROWS);
        BankManager.withdraw(150, true, OGRE_COMP_BOW);
        BankManager.withdraw(1, true, MAGIC_SHORTBOW);
        BankManager.close(true);
        Utils.equipItem(MAGIC_SHORTBOW);
        Utils.equipItem(RUNE_ARROW);
    }


    String[] START_DIALOGUE = {
            "What do you mean sickies?",
            "Can I help in any way?",
            "Ok, I'll check things out then and report back.",
            "Yes, I'm really sure!"
    };

    private void startQuest() {
        cQuesterV2.status = "Going to Start";
        General.println("[Debug]: " + cQuesterV2.status);
        PathingUtil.walkToArea(START_AREA);
        if (NpcChat.talkToNPC("Grish")) {
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation(START_DIALOGUE);
        }

    }

    private void goToGuard() {
        cQuesterV2.status = "Going to Guard";
        General.println("[Debug]: " + cQuesterV2.status);
        PathingUtil.walkToArea(GUARD_BEFORE_GATE);
        if (NpcChat.talkToNPC("Ogre guard")) {
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation();
            Utils.modSleep(); // while he kicks down gate
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation();
        }
    }

    private void climbBaracade() {
        if (HUGE_START_AREA.contains(Player.getPosition())){
            PathingUtil.walkToArea(GUARD_BEFORE_GATE);
        }
         if (GUARD_BEFORE_GATE.contains(Player.getPosition())) {
            cQuesterV2.status = "Climbing over gate";
            if (Utils.clickObj(CRUSHED_BARACADE, "Climb-over"))
                Timer.waitCondition(() -> AFTER_GATE.contains(Player.getPosition()), 5000, 12000);
        }
    }



    private void goDownStairs() {
        climbBaracade();
        if (!WHOLE_DUNGEON_LEVEL_2.contains(Player.getPosition())) {
            cQuesterV2.status = "Going down the stairs";
            PathingUtil.walkToArea(BEFORE_DESCENT);

            if (Utils.clickObj("Stairs", "Climb-down"))
                Timer.waitCondition(() -> WHOLE_DUNGEON_LEVEL_2.contains(Player.getPosition()), 6000, 9000);

        }
    }

    private void goToCoffins() {
        //   Autocast.enableAutocast(Autocast.EARTH_BOLT);
        goDownStairs();

        if (WHOLE_DUNGEON_LEVEL_2.contains(Player.getPosition()) && !IN_FRONT_OF_COFFIN.contains(Player.getPosition())) {
            PathingUtil.localNavigation(IN_FRONT_OF_COFFIN.getRandomTile());
            Utils.cutScene();
            Utils.idle(6000, 9000);
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation();
        }
    }

    private void searchBrokenLecturn() {
        // Autocast.enableAutocast(Autocast.EARTH_BOLT);

        if (Combat.isUnderAttack()) {
            cQuesterV2.status = "Killing Skeleton";
        }

        if (Inventory.find(TORN_PAGE).length < 1) {
            cQuesterV2.status = "Searching Lecturn";
            if (Utils.clickObj("Broken lecturn", "Search")) {
                Timer.waitCondition(() -> Inventory.find(TORN_PAGE).length > 0, 6000, 9000);
            }
        }

        if (NPCs.findNearest("Zombie").length < 1) {
            cQuesterV2.status = "Searching Skeleton";
            if (Utils.clickObj("Skeleton", "Search")) {
                Timer.waitCondition(() -> NPCs.findNearest("Zombie").length > 0, 6000, 9000);
            }
        }
        if (Combat.getHPRatio() < General.random(30, 50)) {
            cQuesterV2.status = "Eating";
            RSItem[] food = Inventory.find(ItemID.MONKFISH);
            if (food.length > 0 && food[0].click()) {
                Utils.microSleep();
            }
        }

    }

    private void lootBackPack() {
        RSGroundItem[] ruinedBackpack = GroundItems.find(RUINED_BACKPACK);
        if (ruinedBackpack.length > 0) {
            if (AccurateMouse.click(ruinedBackpack[0], "Take"))
                Timer.waitCondition(() -> Inventory.find(RUINED_BACKPACK).length > 0, 6000, 9000);
        }
        Inventory.drop(ItemID.ROTTEN_FOOD);
    }

    private void openBackPack() {
        RSItem[] backpack = Inventory.find(RUINED_BACKPACK);
        if (backpack.length > 0 && Inventory.find(TANKARD).length == 0) {
            cQuesterV2.status = "Opening backpack";
            if (Inventory.getAll().length > 24) {
                RSItem[] food = Inventory.find(ItemID.MONKFISH);
                if (food.length > 0)
                    if (food[0].click())
                        Utils.microSleep();

            } else if (backpack[0].click("Open")) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation();
                Timer.waitCondition(() -> Inventory.find(KNIFE).length > 0, 4000, 6000);
            }
        }
    }

    private void lootCoffin() {
        if (Inventory.find(KNIFE).length > 0) {
            cQuesterV2.status = "Searching Coffin";
            searchCoffin();

            if (Utils.useItemOnObject(KNIFE, "Ogre Coffin")) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation();
            }
            searchCoffin();
        }
    }

    private void searchCoffin() {
        if (Inventory.find(BLACK_PRISM).length < 1) {
            cQuesterV2.status = "Searching Coffin";
            if (Utils.clickObj("Ogre Coffin", "Search")) {
                Timer.waitCondition(() -> Inventory.find(BLACK_PRISM).length > 0 || NPCInteraction.isConversationWindowUp(), 4000, 6000);
                NPCInteraction.handleConversation();
            }
        }
    }

    public void goToBar() {
        if (Inventory.find(BLACK_PRISM).length > 0) {
            cQuesterV2.status = "Going to bar";
            PathingUtil.walkToArea(YANILE_BAR);
            cQuesterV2.status = "Showing tankard to bartender";
            if (Utils.useItemOnNPC(TANKARD, "Bartender")) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation();
            }
        }
    }

    private void goToWizardsGuild() {
        cQuesterV2.status = "Talking to Zavistic Rarve";
        PathingUtil.walkToArea(OUTSIDE_WIZARDS_GUILD);
        if (Utils.clickObj("Bell", "Ring")) {
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation(Zavistic_Rarve_CHAT_1);
            NPCInteraction.handleConversation(); // doesn't seem to recognize one of the chat interfaces
            Keyboard.typeString(" ");
            NPCInteraction.handleConversation(Zavistic_Rarve_CHAT_1);
            NPCInteraction.handleConversation();
        }
    }

    private void goToSithik() {
        cQuesterV2.status = "Going to Sithik";
        PathingUtil.walkToArea(SITHIK_ROOM);
        if (Utils.clickObj(SITHIK_ID, "Talk-to")) {
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation(Sithik_Ints_CHAT_1);
            NPCInteraction.handleConversation();
        }
    }

    private void eatForSpace() {
        if (Inventory.isFull()) {
            cQuesterV2.status = "Eating for Space";
            RSItem[] food = Inventory.find(ItemID.MONKFISH);
            if (food.length > 0 && food[0].click())
                Timer.waitCondition(() -> Player.getAnimation() != -1, 2500, 3500);

            else
                EatUtil.eatFood();
        }
    }

    private void searchRoom() {
        cQuesterV2.status = "Searching Room";
        if (Inventory.find(CHARCOAL).length < 1) {
            eatForSpace();
            while (Inventory.getAll().length > 23) {
                General.sleep(General.random(150, 400));
                EatUtil.eatFood();
            }

            if (Utils.clickObj("Drawers", "Search")) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation();
            }
        }
        if (Inventory.find(CHARCOAL).length < 1) {
            eatForSpace();
            if (Utils.clickObj("Cupboard", "Search")) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation();
            }
        }
        if (Inventory.find(BOOK_OF_HAM).length < 1) {
            eatForSpace();
            if (Utils.clickObj("Wardrobe", "Search")) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation();
            }
        }
        if (Inventory.find(NECROMANCY_BOOK).length < 1) {
            eatForSpace();
            if (Utils.clickObj("Cupboard", "Search")) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation();
            }
        }
    }

    private void readBooks() {
        if (BankManager.checkInventoryItems(BOOK_OF_HAM, NECROMANCY_BOOK, BOOK_OF_PORTRAITURE)) {
            cQuesterV2.status = "Reading books";
            RSItem[] book = Inventory.find(BOOK_OF_HAM);
            if (book.length > 0)
                if (book[0].click()) {
                    NPCInteraction.waitForConversationWindow();
                    NPCInteraction.handleConversation();
                    Utils.microSleep();
                }


            book = Inventory.find(NECROMANCY_BOOK);
            if (book.length > 0)
                if (book[0].click()) {
                    NPCInteraction.waitForConversationWindow();
                    NPCInteraction.handleConversation();
                    Utils.microSleep();
                }

            book = Inventory.find(BOOK_OF_PORTRAITURE);
            if (book.length > 0)
                if (book[0].click()) {
                    NPCInteraction.waitForConversationWindow();
                    NPCInteraction.handleConversation();
                    Utils.microSleep();
                }
        }
    }

    private void useBookOnSithik() {
        if (Inventory.find(PORTRAIT).length ==0) {
            if (Utils.useItemOnObject(BOOK_OF_HAM, SITHIK_ID)) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation();
            }
            if (Utils.useItemOnObject(NECROMANCY_BOOK, SITHIK_ID)) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation();
            }
            if (Utils.useItemOnObject(BOOK_OF_PORTRAITURE, SITHIK_ID)) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation();
            }
            if (Utils.useItemOnObject(PAPYRUS, SITHIK_ID)) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation();
                Utils.modSleep();
            }
        }
        if (Utils.useItemOnObject(PORTRAIT, SITHIK_ID)) {
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation();
        }
    }

    public static void handlePortraitMessage(String message) {
        if (message.contains(portraitString)) {
            General.println("[Debug]: We have the right portrait");
            isRightPortrait = true;
        }
    }

    private void examinePortrait() {
        if (!isRightPortrait) {
            cQuesterV2.status = "Examining portrait";
            if (Inventory.find(PORTRAIT).length > 0) {
                if (Inventory.find(PORTRAIT)[0].click("Examine"))
                    Utils.modSleep();
            }
        }
    }

    private void usePortrait() {
        if (Utils.useItemOnObject(PORTRAIT, SITHIK_ID)) {
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation();
        }
    }

    public void goToBar2() {
        if (Inventory.find(PORTRAIT).length > 0) {
            Utils.dropItem(WRONG_PORTRAIT);
            cQuesterV2.status = "Going to bar";
            PathingUtil.walkToArea(YANILE_BAR);
            if (Utils.useItemOnNPC(PORTRAIT, "Bartender")) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation();
            }
        }
    }

    private void goToWizardsGuild2() {
        cQuesterV2.status = "Going to wizards Guild";
        PathingUtil.walkToArea(OUTSIDE_WIZARDS_GUILD);
        if (Utils.clickObj("Bell", "Ring")) {
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation(Zavistic_Rarve_CHAT_1, "I have some items that I'd like you to look at.");
            NPCInteraction.handleConversation();
        }
    }

    public void addPotionToTea() {
        cQuesterV2.status = "Adding strange potion to tea";
        PathingUtil.walkToArea(SITHIK_ROOM);
        RSItem[] invPotion = Inventory.find("Strange potion");
        RSGroundItem[] tea = GroundItems.findNearest(4838); // tea
        if (invPotion.length > 0 && invPotion[0].click("Use")) {
            AntiBan.waitItemInteractionDelay();

            if (tea.length > 0 && tea[0].click("Use")) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation();
            }
        }
    }

    String[] QUESTIONS = {
            "How do I remove the effects of the spell from the area?",
            "How do i get rid of the undead ogres?",
            "How do i get rid of the disease?"
    };


    public void revealOgre() {
        if (SITHIK_ROOM.contains(Player.getPosition()) || Player.getPosition().getPlane() > 0) {
            cQuesterV2.status = "Leaving floor";
            PathingUtil.walkToTile(new RSTile(2596, 3107, 0));
        }
        cQuesterV2.status = "Returning to Sithik";
        PathingUtil.walkToArea(SITHIK_ROOM);
        if (Utils.clickObj(SITHIK_ID, "Talk-to")) {
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation(QUESTIONS);
            NPCInteraction.handleConversation();
        }
    }

    private void returnToGrish() {
        cQuesterV2.status = "Going to Grish";
        General.println("[Debug]: " + cQuesterV2.status);
        PathingUtil.walkToArea(START_AREA);
        if (NpcChat.talkToNPC("Grish")) {
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation("I found who's responsible for the Zogres being here.");
            NPCInteraction.handleConversation("There must be an easier way to kill these zogres!");
        }
    }

    public void getCompBowSkills() {
        if (Utils.getVarBitValue(500) == 0) {
            cQuesterV2.status = "Going to Grish - enabling comp bow";
            General.println("[Debug]: " + cQuesterV2.status);
            PathingUtil.walkToArea(START_AREA);
            if (NpcChat.talkToNPC("Grish")) {
                NPCInteraction.waitForConversationWindow();
                //  NPCInteraction.handleConversation("I found who's responsible for the Zogres being here.");
                NPCInteraction.handleConversation("There must be an easier way to kill these zogres!");
            }
        }
    }


    public boolean isDiseased() {
        return Utils.getVarBitValue(8354) == 1313;
    }

    int OGRE_ARTIFACT = 4818;
    int ZOGRE_BONES = 4812;
    int OURG_BONES = 4834;

    public boolean equipBow() {
        RSItem[] bow = Inventory.find(OGRE_COMP_BOW);
        RSItem[] runeBrutal = Inventory.find(RUNE_BRUTAL_ARROWS);
        if (!Equipment.isEquipped(OGRE_COMP_BOW) && bow.length > 0 && bow[0].click("Wield")) {
            Timer.waitCondition(() -> Equipment.isEquipped(OGRE_COMP_BOW), 2500, 3000);
        }
        if (!Equipment.isEquipped(RUNE_BRUTAL_ARROWS) && runeBrutal.length > 0 && runeBrutal[0].click("Wield")) {
            Timer.waitCondition(() -> Equipment.isEquipped(RUNE_BRUTAL_ARROWS), 2500, 3000);
        }
        return Equipment.isEquipped(OGRE_COMP_BOW) && Equipment.isEquipped(RUNE_BRUTAL_ARROWS);
    }

    RSTile SAFE_TILE = new RSTile(2484, 9445, 0);
    RSArea WHOLE_FINAL_AREA = new RSArea(new RSTile(2438, 9459, 0), new RSTile(2491, 9414, 0));

    public void goToWestDoors() {
        if (equipBow()) {
            if (!WHOLE_FINAL_AREA.contains(Player.getPosition()))
                goDownStairs();

            cQuesterV2.status = "Going to west doors";
            if (Player.getPosition().getPlane() == 2) {
                PathingUtil.localNavigation(new RSTile(2446, 9417, 2));
                if (Utils.clickObj("Stairs", "Climb-down")) {
                    Timer.waitCondition(() -> Player.getPosition().getPlane() != 2, 5000, 7000);
                }
            }
            RSNPC[] slash = NPCs.findNearest("Slash Bash");
            if (slash.length == 0) {
                PathingUtil.localNavigation(new RSTile(2482, 9445, 0));
                if (Utils.clickObj(6897, "Search")) {
                    NPCInteraction.waitForConversationWindow();
                    NPCInteraction.handleConversation();
                    if (PathingUtil.clickScreenWalk(SAFE_TILE))
                        PathingUtil.movementIdle();
                }
            } else {
                // move to safe tile
                if (!SAFE_TILE.equals(Player.getPosition()) && PathingUtil.clickScreenWalk(SAFE_TILE))
                    PathingUtil.movementIdle();

                // check health
                if (Combat.getHPRatio() < General.random(40, 60))
                    EatUtil.eatFood();

                if (Prayer.getPrayerPoints() < General.random(5, 15)) {
                    cQuesterV2.status = "Drinking Super Restore";
                }

                if (!Prayer.isPrayerEnabled(Prayer.PRAYERS.PROTECT_FROM_MISSILES)
                        && Prayer.getPrayerPoints() > 0) {
                    cQuesterV2.status = "Enabling Prayer";
                    Prayer.enable(Prayer.PRAYERS.PROTECT_FROM_MISSILES);
                }
                RSItem[] restore = Inventory.find(SUPER_RESTORE);

                if (Prayer.getPrayerPoints() < General.random(10, 15)) {
                    General.println("[Debug]: Need to drink prayer/restore pot");
                    if (restore.length > 0 && restore[0].click("Drink")) {
                        Timer.waitCondition(() -> Player.getAnimation() != -1, 1500, 2000);
                    }
                }
                if (!slash[0].isInCombat()) {
                    General.println("[Debug]: Attacking Slash");
                    if (!slash[0].isClickable())
                        DaxCamera.focus(slash[0]);

                    if (DynamicClicking.clickRSNPC(slash[0], "Attack")) {
                        Timer.waitCondition(() -> slash[0].isInCombat(), 2000, 3000);
                    }
                }
                // cast crumble undead (reg spells don't kill fast enough)
            /*if (Magic.selectSpell("Crumble Undead")) {
                AntiBan.waitItemInteractionDelay();
                if (slash[0].click())
                    Timer.waitCondition(() -> Player.getAnimation() != -1, 3000, 5000);
            }*/


                /**
                 * Need to add pray-ranged
                 * need to drink super restores if prayer is low
                 * need to drink recylms balm if diseased (varbit 8354 = 1313 when diseased)
                 */
            }
            if (isDiseased()) {
                cQuesterV2.status = "Drinking Cure to Disease";
                RSItem[] balm = Inventory.find(RELICYMS_BALM);
                if (balm.length > 0 && balm[0].click("Drink")) {
                    Timer.waitCondition(() -> Player.getAnimation() != -1, 2500, 3000);
                }
            }
        }
    }

    public void finishQuest() {
        cQuesterV2.status = "Looting ground Items";
        if (Prayer.isPrayerEnabled(Prayer.PRAYERS.PROTECT_FROM_MISSILES)) {
            Prayer.disable(Prayer.PRAYERS.PROTECT_FROM_MISSILES);
        }
        if (Utils.clickGroundItem(OGRE_ARTIFACT)) {
            Utils.microSleep();
        }
        RSGroundItem[] bones = GroundItems.find(ZOGRE_BONES, OURG_BONES);

        for (int i = 0; i < bones.length; i++) {
            if (Inventory.isFull())
                EatUtil.eatFood();

            Utils.clickGroundItem(ZOGRE_BONES);

            if (Inventory.isFull())
                EatUtil.eatFood();

            Utils.clickGroundItem(OURG_BONES);
        }

        if (Inventory.find(OGRE_ARTIFACT).length > 0) {
            cQuesterV2.status = "Returning to start";
            PathingUtil.walkToArea(START_AREA);
            if (NpcChat.talkToNPC("Grish")) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation("Yeah, I have them here!");
            }

        }
    }


    @Override
    public void execute() {
        if (!checkRequirements()) {
            cQuesterV2.taskList.remove(this);
            return;
        }

        if (Game.getSetting(GAME_SETTING) == 0) {
            buyItems();
            getItems();
            startQuest();

        } else if (RSVarBit.get(507).getValue() == 1 && RSVarBit.get(487).getValue() == 2) {
            goToGuard();

        } else if (RSVarBit.get(487).getValue() == 3 && RSVarBit.get(505).getValue() == 0) { // also 496 goes 0 -> 1
            goToCoffins();

        } else if (RSVarBit.get(487).getValue() == 3 && RSVarBit.get(505).getValue() == 1
                && RSVarBit.get(503).getValue() == 0) {

            searchBrokenLecturn();

        } else if (RSVarBit.get(487).getValue() == 3 && RSVarBit.get(503).getValue() == 1) {
            searchBrokenLecturn();
            lootBackPack();

        } else if (RSVarBit.get(487).getValue() == 3 && RSVarBit.get(503).getValue() == 2
                && RSVarBit.get(488).getValue() <= 2 && Utils.getVarBitValue(503) == 2
                && Utils.getVarBitValue(507) == 1) {
            goToCoffins();
            lootBackPack();
            openBackPack();
            lootCoffin();


        } else if (RSVarBit.get(487).getValue() == 3 && RSVarBit.get(503).getValue() == 2
                && RSVarBit.get(488).getValue() <= 2) {
            goToCoffins();
            lootCoffin();

        } else if (RSVarBit.get(487).getValue() == 3 && Utils.getVarBitValue(488) == 3 &&
                RSVarBit.get(489).getValue() == 0 && RSVarBit.get(503).getValue() == 2) {
            goToBar();

        } else if (RSVarBit.get(487).getValue() == 3 && RSVarBit.get(503).getValue() == 2
                && RSVarBit.get(488).getValue() == 3 && RSVarBit.get(489).getValue() == 1) {

            goToWizardsGuild();

        } else if (RSVarBit.get(487).getValue() == 3 && RSVarBit.get(503).getValue() == 2
                && RSVarBit.get(488).getValue() == 4 && RSVarBit.get(489).getValue() == 1) {

            goToSithik();

        } else if (RSVarBit.get(487).getValue() == 3 && RSVarBit.get(503).getValue() == 2
                && RSVarBit.get(488).getValue() == 5 && RSVarBit.get(489).getValue() == 1
                && RSVarBit.get(490).getValue() == 0) {

            searchRoom();
            readBooks();
            useBookOnSithik();
            examinePortrait();
            usePortrait();
            goToBar2();

        } else if (RSVarBit.get(487).getValue() == 3
                && RSVarBit.get(488).getValue() == 5
                && RSVarBit.get(489).getValue() == 1
                && RSVarBit.get(490).getValue() == 1
                && RSVarBit.get(503).getValue() == 2
                && RSVarBit.get(507).getValue() == 1) {

            goToWizardsGuild2();

        } else if (RSVarBit.get(487).getValue() == 4
                && RSVarBit.get(488).getValue() == 5
                && RSVarBit.get(489).getValue() == 1
                && RSVarBit.get(490).getValue() == 1
                && RSVarBit.get(503).getValue() == 2
                && RSVarBit.get(507).getValue() == 1) {

            addPotionToTea();

        } else if (RSVarBit.get(487).getValue() == 6
                && RSVarBit.get(488).getValue() == 5
                && RSVarBit.get(489).getValue() == 1
                && RSVarBit.get(490).getValue() == 1
                && RSVarBit.get(503).getValue() == 2
                && RSVarBit.get(507).getValue() == 1) {

            revealOgre();

        } else if (RSVarBit.get(487).getValue() == 8
                && RSVarBit.get(488).getValue() == 5
                && RSVarBit.get(489).getValue() == 1
                && RSVarBit.get(490).getValue() == 1
                && RSVarBit.get(503).getValue() == 2
                && RSVarBit.get(507).getValue() == 1) {

            returnToGrish();
            getCompBowSkills();

        } else if (RSVarBit.get(487).getValue() == 10
                && RSVarBit.get(488).getValue() == 5
                && RSVarBit.get(489).getValue() == 1
                && RSVarBit.get(490).getValue() == 1
                && RSVarBit.get(503).getValue() == 2
                && RSVarBit.get(507).getValue() == 1) {

            getCompBowSkills();
            goToWestDoors();

        } else if (RSVarBit.get(487).getValue() == 12
                && RSVarBit.get(488).getValue() == 5
                && RSVarBit.get(489).getValue() == 1
                && RSVarBit.get(490).getValue() == 1
                && RSVarBit.get(503).getValue() == 2
                && RSVarBit.get(507).getValue() == 1) {

            finishQuest();

        } else if (RSVarBit.get(487).getValue() == 14
                && RSVarBit.get(488).getValue() == 5
                && RSVarBit.get(489).getValue() == 1
                && RSVarBit.get(490).getValue() == 1
                && RSVarBit.get(503).getValue() == 2
                && RSVarBit.get(507).getValue() == 1) {

            Utils.closeQuestCompletionWindow();
            cQuesterV2.taskList.remove(this);

        }
    }

    @Override
    public String questName() {
        return "Zogre Flesh Eaters";
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
    public List<Requirement> getGeneralRequirements() {
        return null;
    }

    @Override
    public List<ItemRequirement> getBuyList() {
        return null;
    }
}
