package scripts.QuestPackages.TheFeud;

import dax.walker.utils.AccurateMouse;
import dax.walker_engine.interaction_handling.NPCInteraction;
import org.tribot.api.DynamicClicking;
import org.tribot.api.General;
import org.tribot.api2007.*;
import org.tribot.api2007.types.*;
import org.tribot.script.sdk.Log;
import org.tribot.script.sdk.Quest;
import org.tribot.script.sdk.Waiting;
import scripts.*;
import scripts.GEManager.GEItem;
import scripts.QuestPackages.CreatureOfFenkenstrain.CreatureOfFenkenstrain;
import scripts.QuestSteps.BuyItemsStep;
import scripts.QuestSteps.QuestTask;
import scripts.Requirements.ItemRequirement;
import scripts.Requirements.Requirement;
import scripts.Tasks.Priority;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TheFeud implements QuestTask {


    private static TheFeud quest;

    public static TheFeud get() {
        return quest == null ? quest = new TheFeud() : quest;
    }

    int KHARIDIAN_HEADPIECE = 4591;
    int FAKE_BEARD = 4593;
    int SHANTAY_PASS = 1854;
    int COINS = 995;
    int BUCKET = 1925;
    int BEER = 1917;
    int LEATHER_GLOVES = 1059;
    int DESERT_TOP = 1833;
    int DESERT_ROBE = 1835;
    int DESERT_BOOTS = 1837;
    int WATERSKIN = 1823;
    int LOBSTER = 379;
    int DISGUISE = 4611;
    int DRUNKEN_ALI_ID = 3534;
    int RECIEPT = 4603;
    int BLACKJACK = 4599;
    int KEY = 4589;
    int JEWELS = 4590;
    int SNAKE_CHARM = 4605;
    int SNAKE_BASKET = 4606;
    int RED_HOT_SAUCE = 4610;
    int HAGS_POISON = 4604;

    RSArea START_AREA = new RSArea(new RSTile(3300, 3213, 0), new RSTile(3307, 3208, 0));
    RSArea BAR_AREA = new RSArea(new RSTile(3363, 2954, 0), new RSTile(3354, 2958, 0));
    RSArea TENT_AREA = new RSArea(new RSTile(3338, 2943, 0), new RSTile(3328, 2954, 0));
    RSArea NORTH_BANDIT_AREA = new RSArea(new RSTile(3366, 2997, 0), new RSTile(3355, 3005, 0));
    RSArea CAMEL_STORE = new RSArea(new RSTile(3351, 2964, 0), new RSTile(3346, 2968, 0));
    RSArea VILLAGER_AREA = new RSArea(new RSTile(3353, 2968, 0), new RSTile(3363, 2961, 0));
    RSArea LURE_BUILDING_1 = new RSArea(new RSTile(3351, 2953, 0), new RSTile(3348, 2956, 0));
    RSArea CACTUS_AREA = new RSArea(new RSTile(3364, 2964, 0), new RSTile(3361, 2969, 0));
    RSArea INSIDE_VILLA = new RSArea(new RSTile(3371, 2976, 0), new RSTile(3375, 2965, 0));
    RSArea SECOND_FLOOR_VILLA = new RSArea(new RSTile(3376, 2971, 1), new RSTile(3370, 2976, 1));
    RSArea HAG_HOUSE = new RSArea(new RSTile(3342, 2988, 0), new RSTile(3347, 2986, 0));
    RSArea SNAKE_CHARMER_AREA = new RSArea(new RSTile(3353, 2953, 0), new RSTile(3357, 2951, 0));
    RSArea KEBAB_AREA = new RSArea(new RSTile(3355, 2973, 0), new RSTile(3352, 2976, 0));
    RSArea CAMEL_DUNG_AREA = new RSArea(new RSTile(3344, 2961, 0), new RSTile(3339, 2963, 0));

    ArrayList<GEItem> itemsToBuy = new ArrayList<GEItem>(
            Arrays.asList(
                    new GEItem(ItemID.SHANTAY_PASS, 10, 200),
                    new GEItem(ItemID.BUCKET, 1, 500),
                    new GEItem(ItemID.BEER, 4, 500),
                    new GEItem(ItemID.LEATHER_GLOVES, 1, 300),
                    new GEItem(ItemID.DESERT_BOOTS, 1, 500),
                    new GEItem(ItemID.DESERT_ROBE, 1, 500),
                    new GEItem(ItemID.DESERT_SHIRT, 1, 500),

                    new GEItem(ItemID.WATERSKIN[0], 5, 500),
                    new GEItem(ItemID.MIND_RUNE, 300, 20),
                    new GEItem(ItemID.FIRE_RUNE, 600, 20),
                    new GEItem(ItemID.STAFF_OF_AIR, 1, 50),
                    new GEItem(ItemID.LOBSTER, 20, 50),
                    new GEItem(ItemID.AMULET_OF_GLORY[2], 2, 30),
                    new GEItem(ItemID.STAMINA_POTION[0], 3, 15),
                    new GEItem(ItemID.RING_OF_WEALTH[0], 1, 25)
            )
    );

    public void buyItems() {
        cQuesterV2.status = "Buying Items";
        General.println("[Debug]: " + cQuesterV2.status);
        BuyItemsStep buyStep = new BuyItemsStep(itemsToBuy);
        buyStep.buyItems();
    }

    public void getItems() {
        cQuesterV2.status = "Getting Items";
        General.println("[Debug]: " + cQuesterV2.status);
        BankManager.open(true);
        BankManager.depositEquipment();
        BankManager.checkEquippedGlory();
        BankManager.depositAll(true);
        BankManager.withdraw(5, true, SHANTAY_PASS);
        BankManager.withdraw(1, true, BUCKET);
        BankManager.withdraw(3, true, BEER);
        BankManager.withdraw(1, true, LEATHER_GLOVES);
        BankManager.withdraw(1, true, DESERT_BOOTS);
        BankManager.withdraw(1, true, DESERT_ROBE);
        BankManager.withdraw(1, true, DESERT_TOP);
        BankManager.withdraw(3000, true, COINS);
        BankManager.withdraw(1, true, WATERSKIN);
        BankManager.withdraw(400, true,
                ItemID.MIND_RUNE);
        BankManager.withdraw(1200, true,
                ItemID.FIRE_RUNE);
        BankManager.withdraw(1, true,
                ItemID.STAFF_OF_AIR);
        Utils.equipItem(DESERT_BOOTS);
        Utils.equipItem(DESERT_ROBE);
        Utils.equipItem(
                ItemID.STAFF_OF_AIR);
        Utils.equipItem(DESERT_TOP);
        Utils.equipItem(LEATHER_GLOVES);
        BankManager.withdraw(12, true, LOBSTER);
        BankManager.withdraw(2, true, BankManager.STAMINA_POTION[0]);

        BankManager.close(true);
    }

    public void startQuest() {
        cQuesterV2.status = "Starting Quest";
        General.println("[Debug]: " + cQuesterV2.status);
        PathingUtil.walkToArea(START_AREA);
        if (START_AREA.contains(Player.getPosition()) && Inventory.find(KHARIDIAN_HEADPIECE).length < 1) {
            NpcChat.talkToNPC("Ali Morrisane");
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation("So what are you selling then?");
            NPCInteraction.handleConversation();
            Timer.waitCondition(() -> Interfaces.get(300, 16, 9) != null, 7000, 9000);
            if (Interfaces.get(300, 16, 9) != null) {
                if (AccurateMouse.click(Interfaces.get(300, 16, 9), "Buy 1"))
                    Timer.waitCondition(() -> Inventory.find(FAKE_BEARD).length > 0, 6000, 9000);
                if (AccurateMouse.click(Interfaces.get(300, 16, 10), "Buy 1"))
                    Timer.waitCondition(() -> Inventory.find(KHARIDIAN_HEADPIECE).length > 0, 6000, 9000);
                Interfaces.get(300, 1, 11).click();
            }
        }
        if (START_AREA.contains(Player.getPosition()) && Inventory.find(KHARIDIAN_HEADPIECE).length > 0) {
            if (NpcChat.talkToNPC("Ali Morrisane")) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation("If you are, then why are you still selling goods from a stall?");
                NPCInteraction.handleConversation("I'd like to help you but.....");
                NPCInteraction.handleConversation("I'll find you your help.", "Yes.");
                NPCInteraction.handleConversation();
                Timer.waitCondition(() -> Interfaces.get(300, 16, 9) != null, 7000, 9000);
            }
            if (Interfaces.get(300, 16, 9) != null) {
                if (Inventory.find(FAKE_BEARD).length < 1)
                    if (AccurateMouse.click(Interfaces.get(300, 16, 9), "Buy 1"))
                        Timer.waitCondition(() -> Inventory.find(FAKE_BEARD).length > 0, 6000, 9000);

                if (Inventory.find(KHARIDIAN_HEADPIECE).length < 1)
                    if (AccurateMouse.click(Interfaces.get(300, 16, 10), "Buy 1"))
                        Timer.waitCondition(() -> Inventory.find(KHARIDIAN_HEADPIECE).length > 0, 6000, 9000);

                if (Inventory.find(KHARIDIAN_HEADPIECE).length > 0)
                    Interfaces.get(300, 1, 11).click();
            }
        }
    }

    public void giveBeerToAli() {
        Utils.useItemOnItem(FAKE_BEARD, KHARIDIAN_HEADPIECE);
        if (Inventory.find(ItemID.BEER).length > 2) {
            if (!BAR_AREA.contains(Player.getPosition())) {
                cQuesterV2.status = "Going to Pollnivneach";
                General.println("[Debug]: " + cQuesterV2.status);
                PathingUtil.walkToArea(BAR_AREA);
            }
            if (BAR_AREA.contains(Player.getPosition())) {
                cQuesterV2.status = "Talking to Drunken Ali";
                General.println("[Debug]: " + cQuesterV2.status);
                if (NpcChat.talkToNPC(3534)) {
                    NPCInteraction.waitForConversationWindow();
                    NPCInteraction.handleConversation();
                }
                Utils.idle(500, 2500);
                for (int i = 0; i < 3; i++) {
                    if (Utils.useItemOnNPC(BEER, "Drunken Ali")) {
                        NPCInteraction.waitForConversationWindow();
                        NPCInteraction.handleConversation();
                    }
                }
            }
        }
    }

    public void step3() {
        cQuesterV2.status = "Going to Tent Area";
        General.println("[Debug]: " + cQuesterV2.status);
        PathingUtil.walkToArea(TENT_AREA);

        cQuesterV2.status = "Talking to Thug";
        General.println("[Debug]: " + cQuesterV2.status);
        if (NpcChat.talkToNPC("Menaphite Thug")) {
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation();
        }
    }


    public void step4() {
        cQuesterV2.status = "Going to Northern Bandits";
        General.println("[Debug]: " + cQuesterV2.status);
        PathingUtil.walkToArea(NORTH_BANDIT_AREA);

        if (NORTH_BANDIT_AREA.contains(Player.getPosition())) {
            cQuesterV2.status = "Talking to Thug";
            General.println("[Debug]: " + cQuesterV2.status);
            if (NpcChat.talkToNPC("Bandit")) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation();
                NPCInteraction.handleConversation();
            }
        }
    }

    public void step5() {

        cQuesterV2.status = "Going to Camel Store";
        General.println("[Debug]: " + cQuesterV2.status);
        PathingUtil.walkToArea(CAMEL_STORE);

        if (CAMEL_STORE.contains(Player.getPosition())) {
            cQuesterV2.status = "Talking to Ali the Camel Man";
            General.println("[Debug]: " + cQuesterV2.status);
            if (NpcChat.talkToNPC("Ali the Camel Man")) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation("Are those camels around the side for sale?");
                NPCInteraction.handleConversation("What price do you want for both of them?");
                NPCInteraction.handleConversation("Would 500 gold coins for the pair of them do?");
                NPCInteraction.handleConversation();
            }
        }
    }

    public void step6() {
        cQuesterV2.status = "Going to Tent Area";
        General.println("[Debug]: " + cQuesterV2.status);
        PathingUtil.walkToArea(TENT_AREA);

        if (TENT_AREA.contains(Player.getPosition())) {
            cQuesterV2.status = "Talking to Thug";
            General.println("[Debug]: " + cQuesterV2.status);
            if (NpcChat.talkToNPC("Menaphite Thug")) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation();
            }
        }
    }

    public void step7() {
        cQuesterV2.status = "Going to Northern Bandits";
        General.println("[Debug]: " + cQuesterV2.status);
        PathingUtil.walkToArea(NORTH_BANDIT_AREA);

        if (NORTH_BANDIT_AREA.contains(Player.getPosition())) {
            cQuesterV2.status = "The Feud: Talking to Thug";
            General.println("[Debug]: " + cQuesterV2.status);
            NpcChat.talkToNPC("Bandit");
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation();
            NPCInteraction.handleConversation();
        }
    }

    public void step8() {
        cQuesterV2.status = "Going to Tent Area";
        General.println("[Debug]: " + cQuesterV2.status);
        PathingUtil.walkToArea(TENT_AREA);

        if (TENT_AREA.contains(Player.getPosition())) {
            cQuesterV2.status = "Talking to Ali the Operator";
            General.println("[Debug]: " + cQuesterV2.status);
            NpcChat.talkToNPC("Ali the Operator");
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation();
            NPCInteraction.handleConversation("Yes, of course, those bandits should be taught a lesson.");
            NPCInteraction.handleConversation();
        }
    }

    public void step9() {
        cQuesterV2.status = "Going to Pickpocket a villager";
        General.println("[Debug]: " + cQuesterV2.status);
        PathingUtil.walkToArea(VILLAGER_AREA);

        if (VILLAGER_AREA.contains(Player.getPosition())) {
            RSNPC[] villager = NPCs.findNearest("Villager");
            if (villager.length > 0) {
                if (AccurateMouse.click(villager[0], "Pickpocket"))
                    Timer.waitCondition(() -> Player.getAnimation() != -1, 8000, 10000);
                Utils.idle(2000, 5000);
            }
        }
    }

    public void step10() {
        cQuesterV2.status = "Going to Pickpocket a villager";
        General.println("[Debug]: " + cQuesterV2.status);
        PathingUtil.walkToArea(VILLAGER_AREA);

        if (VILLAGER_AREA.contains(Player.getPosition())) {
            if (NpcChat.talkToNPC(3539)) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation();
                RSNPC[] villager = NPCs.findNearest("Villager");
                if (villager.length > 0) {
                    if (AccurateMouse.click(villager[0], "Pickpocket")) {
                        Timer.waitCondition(() -> Player.getAnimation() != -1, 8000, 10000);
                        Utils.modSleep();
                    }
                }
            }
        }
    }

    public void step11() {
        if (Inventory.find("Oak Blackjack").length < 1 && Equipment.find("Oak Blackjack").length < 1) {
            cQuesterV2.status = "Going to Ali the Operator";
            General.println("[Debug]: " + cQuesterV2.status);
            PathingUtil.walkToArea(TENT_AREA);

            if (TENT_AREA.contains(Player.getPosition())) {
                cQuesterV2.status = "Talking to Ali the Operator";
                General.println("[Debug]: " + cQuesterV2.status);
                if (NpcChat.talkToNPC("Ali the Operator")) {
                    NPCInteraction.waitForConversationWindow();
                    NPCInteraction.handleConversation("Yeah, I could do with a bit of advice.");
                    NPCInteraction.handleConversation();
                }
            }
        }
    }

    public void step12() {

        if (Utils.equipItem(BLACKJACK) || Equipment.find("Oak Blackjack").length >0) {
            cQuesterV2.status = "Going to Building to lure";
            General.println("[Debug]: " + cQuesterV2.status);
            PathingUtil.walkToArea(LURE_BUILDING_1);

            if (LURE_BUILDING_1.contains(Player.getPosition())) {
                cQuesterV2.status = "Luring...";
                General.println("[Debug]: " + cQuesterV2.status);
                RSNPC[] target = NPCs.findNearest("Villager");
                if (target.length > 0) {

                    if (!target[0].isClickable())
                        target[0].adjustCameraTo();

                    if (!LURE_BUILDING_1.contains(target[0])) {
                        if (AccurateMouse.click(target[0], "Lure")) {
                            NPCInteraction.waitForConversationWindow();
                            NPCInteraction.handleConversation();
                        }
                        PathingUtil.walkToArea(LURE_BUILDING_1);
                    }
                    if (LURE_BUILDING_1.contains(target[0])) {
                        cQuesterV2.status = "Knocking out...";
                        if (AccurateMouse.click(target[0], "Knock-Out"))
                            Timer.waitCondition(() -> target[0].getAnimation() == 838, 5000, 7000);
                        if (target[0].getAnimation() == 838) {
                            if (AccurateMouse.click(target[0], "Pickpocket"))
                                Timer.waitCondition(() -> Player.getAnimation() == 881, 5000, 8000);

                            Utils.idle(2000, 5000);
                        }
                    }

                }
            }
        } else {
            step13();
        }
    }

    public void step13() {
        cQuesterV2.status = "Going to Ali the Operator";
        General.println("[Debug]: " + cQuesterV2.status);
        PathingUtil.walkToArea(TENT_AREA);

        cQuesterV2.status = "Talking to Ali the Operator";
        General.println("[Debug]: " + cQuesterV2.status);
        if (NpcChat.talkToNPC("Ali the Operator")) {
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation("Yeah, I could do with a bit of advice.");
            NPCInteraction.handleConversation();
        }
    }

    public void step14() {
        Utils.equipItem(LEATHER_GLOVES);
        Utils.equipItem(DISGUISE);

        cQuesterV2.status = "Going to Cactus";
        General.println("[Debug]: " + cQuesterV2.status);
        PathingUtil.walkToArea(CACTUS_AREA);

        if (CACTUS_AREA.contains(Player.getPosition())) {
            if (Utils.clickObj(6277, "Hide-behind")) {
                Utils.modSleep();
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation();
            }
            if (Utils.useItemOnObject(KEY, 6240))
                Timer.abc2WaitCondition(() -> INSIDE_VILLA.contains(Player.getPosition()), 15000, 20000);
        }
    }

    public void step15() {
        if (!SECOND_FLOOR_VILLA.contains(Player.getPosition())) {
            cQuesterV2.status = "Going to Second floor";
            General.println("[Debug]: " + cQuesterV2.status);
            if (PathingUtil.localNavigation(new RSTile(3374, 2975, 0)))
                Utils.idle(4500, 7000);

            RSObject[] curtain = Objects.findNearest(4, 1533); // use this otherwise it clicks front door
            if (curtain.length > 0) {
                if (AccurateMouse.click(curtain[0], "Open"))
                    Timer.waitCondition(() -> Objects.findNearest(10, 1533).length < 1, 8000, 10000);
            }

            if (Utils.clickObj("Staircase", "Climb-up")) {
                Utils.idle(4500, 7000);
                Walking.blindWalkTo(SECOND_FLOOR_VILLA.getRandomTile());
                Timer.waitCondition(() -> SECOND_FLOOR_VILLA.contains(Player.getPosition()), 7000, 10000);
                Utils.modSleep();
            }
        }
        if (SECOND_FLOOR_VILLA.contains(Player.getPosition())) {
            AccurateMouse.click(Objects.findNearest(15, 6276)[0], "Search");
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation();
            Timer.waitCondition(() -> Interfaces.get(330, 3) != null, 6000, 9000);
            if (Interfaces.get(330, 3) != null) {
                if (Interfaces.get(330, 3).click())
                    Utils.shortSleep();

                if (Interfaces.get(330, 3).click())
                    Utils.shortSleep();

                if (Interfaces.get(330, 4).click())
                    Utils.shortSleep();

                if (Interfaces.get(330, 5).click())
                    Utils.shortSleep();

                if (Interfaces.get(330, 7).click())
                    Utils.shortSleep();

                if (Interfaces.get(330, 10).click())
                    Utils.longSleep();

                if (Interfaces.get(330, 3) != null) {
                    Interfaces.get(330, 15).click();
                }
            }
        }
    }

    public void step16() {
        if (SECOND_FLOOR_VILLA.contains(Player.getPosition())) {
            Walking.blindWalkTo(new RSTile(3374, 2979, 0));

            Utils.idle(4500, 7000);
            if (Utils.clickObj("Staircase", "Climb-down"))
                Utils.modSleep();

            if (Utils.clickObj(1533, "Open"))
                Utils.modSleep();

            if (PathingUtil.localNavigation(new RSTile(3371, 2970, 0)))
                Utils.modSleep();

            if (Utils.clickObj(6238, "Open"))
                Utils.modSleep();

        }

        cQuesterV2.status = "Step 16: Going to Ali";
        General.println("[Debug]: " + cQuesterV2.status);
        PathingUtil.walkToArea(TENT_AREA);

        if (TENT_AREA.contains(Player.getPosition())) {
            cQuesterV2.status = "Step 16: Talking to Ali";
            General.println("[Debug]: " + cQuesterV2.status);
            if (NpcChat.talkToNPC("Ali the Operator")) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation("Yeah, I could do with a bit of advice.");
                NPCInteraction.handleConversation();
            }
        }

    }

    public void step17() {
        cQuesterV2.status = "Step 17: Going to Ali";
        General.println("[Debug]: " + cQuesterV2.status);
        PathingUtil.walkToArea(TENT_AREA);

        if (TENT_AREA.contains(Player.getPosition())) {
            cQuesterV2.status = "Step 17: Talking to Thug";
            General.println("[Debug]: " + cQuesterV2.status);
            NpcChat.talkToNPC("Menaphite Thug");
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation();
        }
    }

    public void step18() {
        cQuesterV2.status = "Step 18: Going to the Bar";
        General.println("[Debug]: " + cQuesterV2.status);
        PathingUtil.walkToArea(BAR_AREA);
        if (BAR_AREA.contains(Player.getPosition())) {
            cQuesterV2.status = "Step 18: Talking to Ali the barman";
            General.println("[Debug]: " + cQuesterV2.status);
            NpcChat.talkToNPC("Ali The barman");
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation("I'm looking for Traitorous Ali.");
            NPCInteraction.handleConversation();
        }
    }

    public void step19() {
        if (!HAG_HOUSE.contains(Player.getPosition())) {
            cQuesterV2.status = "Step 19: Going to Ali the Hag";
            General.println("[Debug]: " + cQuesterV2.status);
            PathingUtil.walkToArea(HAG_HOUSE);
        }
        cQuesterV2.status = "Step 19: Talking to Ali the Hag";
        General.println("[Debug]: " + cQuesterV2.status);
        NpcChat.talkToNPC("Ali The Hag");
        NPCInteraction.waitForConversationWindow();
        NPCInteraction.handleConversation("I'm looking for Traitorous Ali.");
        NPCInteraction.handleConversation();
    }

    public void step20() {
        if (Inventory.find(SNAKE_CHARM).length < 1) {
            cQuesterV2.status = "Step 20: Going to Snake Charmer";
            General.println("[Debug]: " + cQuesterV2.status);
            PathingUtil.walkToArea(SNAKE_CHARMER_AREA);

            if (SNAKE_CHARMER_AREA.contains(Player.getPosition())) {
                cQuesterV2.status = "Step 20: Getting snake charming gear";
                General.println("[Debug]: " + cQuesterV2.status);
                if (Utils.useItemOnObject(COINS, 6230)) {
                    NPCInteraction.waitForConversationWindow();
                    NPCInteraction.handleConversation();
                    Utils.idle(3000, 7000);
                }
            }
        }
        if (Inventory.find(SNAKE_CHARM).length > 0 && Inventory.find(4607).length < 1) {
            if (!TENT_AREA.contains(Player.getPosition())) {
                cQuesterV2.status = "Step 20: Going to Snake";
                General.println("[Debug]: " + cQuesterV2.status);
                PathingUtil.walkToTile(new RSTile(3333, 2952, 0));
                Timer.waitCondition(() -> TENT_AREA.contains(Player.getPosition()), 9000, 12000);
            }
            if (TENT_AREA.contains(Player.getPosition())) {
                cQuesterV2.status = "Step 20: Getting Snake";
                General.println("[Debug]: " + cQuesterV2.status);
                if (Utils.useItemOnNPC(SNAKE_CHARM, 3544)) {
                    NPCInteraction.waitForConversationWindow();
                    NPCInteraction.handleConversation();
                    Timer.waitCondition(() -> Inventory.find(4607).length > 0, 9000, 12000);
                }
            }
        }
    }

    public void step21() {
        if (Inventory.find(4607).length > 0) {
            cQuesterV2.status = "Step 21: Going to Ali the Hag";
            General.println("[Debug]: " + cQuesterV2.status);
            PathingUtil.walkToArea(HAG_HOUSE);

            cQuesterV2.status = "Step 21: Talking to Ali the Hag";
            General.println("[Debug]: " + cQuesterV2.status);
            if (NpcChat.talkToNPC("Ali The Hag")) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation();
            }
        }
    }

    public void step22() {
        if (Inventory.find(RED_HOT_SAUCE).length < 1) {
            cQuesterV2.status = "Step 22: Going to Ali the Kebab seller";
            General.println("[Debug]: " + cQuesterV2.status);
            PathingUtil.walkToArea(KEBAB_AREA);

            if (KEBAB_AREA.contains(Player.getPosition())) {
                cQuesterV2.status = "The Feud: Talking to Ali the Kebab seller";
                General.println("[Debug]: " + cQuesterV2.status);
                if (NpcChat.talkToNPC("Ali the Kebab seller")) {
                    NPCInteraction.waitForConversationWindow();
                    NPCInteraction.handleConversation("Would you sell me that bottle of special kebab sauce?");
                    NPCInteraction.handleConversation();
                }
            }
        }
        if (Inventory.find(RED_HOT_SAUCE).length > 0) {
            cQuesterV2.status = "Step 22b: Going to get Dung";
            General.println("[Debug]: " + cQuesterV2.status);
            PathingUtil.walkToArea(CAMEL_DUNG_AREA);
            if (CAMEL_DUNG_AREA.contains(Player.getPosition())) {
                cQuesterV2.status = "Step 22b: Talking to Ali the Kebab seller";
                General.println("[Debug]: " + cQuesterV2.status);
                if (Utils.useItemOnObject(RED_HOT_SAUCE, 6256))
                    Timer.waitCondition(() -> Objects.findNearest(20, 6257).length > 0, 180000, 20000);

                Utils.modSleep();
                if (Objects.findNearest(20, 6257).length > 0) {
                    Utils.useItemOnObject(BUCKET, 6257);
                    NPCInteraction.waitForConversationWindow();
                    NPCInteraction.handleConversation();
                }

            }
        }
    }

    public void step23() {
        cQuesterV2.status = "Step 23: Going to the Bar";
        General.println("[Debug]: " + cQuesterV2.status);
        PathingUtil.walkToArea(BAR_AREA);

        if (BAR_AREA.contains(Player.getPosition())) {
            cQuesterV2.status = "Step 23: Poisoning beer";
            General.println("[Debug]: " + cQuesterV2.status);
            if (Utils.useItemOnObject(HAGS_POISON, 6246))
                Utils.idle(3500, 8000);
        }
    }

    public void step24() {
        cQuesterV2.status = "Step 24: Going to Ali the Operator";
        General.println("[Debug]: " + cQuesterV2.status);
        PathingUtil.walkToArea(TENT_AREA);

        if (TENT_AREA.contains(Player.getPosition())) {
            cQuesterV2.status = "Step 24: Talking to Ali the Operator";
            if (NpcChat.talkToNPC("Ali the Operator")) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation();
            }
        }
    }

    RSTile SAFE_TILE = new RSTile(3337, 2964, 0);
    RSArea SAFE_AREA = new RSArea(new RSTile(3336, 2963, 0), new RSTile(3337, 2964, 0));

    public void step25() {
        if (Utils.equipItem(ItemID.STAFF_OF_AIR))
            Utils.shortSleep();

        if (!Autocast.isAutocastEnabled(Autocast.FIRE_STRIKE)) {
            Autocast.enableAutocast(Autocast.FIRE_STRIKE);
            Utils.shortSleep();
        }
        if (Autocast.isAutocastEnabled(Autocast.FIRE_STRIKE)) {
            if (!Combat.isUnderAttack()) {
                cQuesterV2.status = "Step 25: Going to Thug Leader";
                General.println("[Debug]: " + cQuesterV2.status);
                PathingUtil.walkToArea(TENT_AREA);
                if (TENT_AREA.contains(Player.getPosition())) {
                    cQuesterV2.status = "Step 25: Talking to Thug Leader";

                    if (NpcChat.talkToNPC("Menaphite Leader")) {
                        NPCInteraction.waitForConversationWindow();
                        NPCInteraction.handleConversation();
                        NPCInteraction.handleConversation();
                    }
                }
            }
            if (!SAFE_AREA.contains(Player.getPosition()) && NPCs.findNearest("Tough Guy").length > 0) {
                Walking.blindWalkTo(SAFE_TILE);

                Utils.idle(3000, 5000);
            }
            RSNPC[] toughGuy = NPCs.findNearest("Tough Guy");
            while (SAFE_AREA.contains(Player.getPosition()) &&
                    toughGuy.length > 0) {
                General.sleep(50);
                cQuesterV2.status = "Step 25: Fighting...";

                if (!SAFE_AREA.contains(Player.getPosition())) {
                    PathingUtil.localNavigation(SAFE_TILE);
                    PathingUtil.movementIdle();
                }

                if (DynamicClicking.clickRSNPC(toughGuy[0], "Attack")) {
                    RSNPC[] finalToughGuy = toughGuy;
                    Timer.waitCondition(() -> finalToughGuy[0].isInCombat(), 4000, 6000);
                    Timer.waitCondition(() -> !Combat.isUnderAttack() || Combat.getHPRatio() < 50, 40000, 50000);
                }

                if (Combat.getHPRatio() < General.random(40, 60))
                    EatUtil.eatFood();

                toughGuy = NPCs.findNearest("Tough Guy");
            }
        }
    }

    public void step26() {
        cQuesterV2.status = "Step 26: Going to Villager";
        General.println("[Debug]: " + cQuesterV2.status);
        PathingUtil.walkToArea(VILLAGER_AREA);

        if (VILLAGER_AREA.contains(Player.getPosition())) {
            cQuesterV2.status = "Step 26: Talking to Villager";
            General.println("[Debug]: " + cQuesterV2.status);
            if (NpcChat.talkToNPC("Villager")) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation("Ok I'll get to it.");
                NPCInteraction.handleConversation();
            }
        }
    }

    RSTile safeTile2 = new RSTile(3358, 3004, 0);
    RSArea SAFE_AREA2 = new RSArea(new RSTile(3358, 3004, 0), new RSTile(3358, 3004, 0));

    public void step27() {
        if (!NORTH_BANDIT_AREA.contains(Player.getPosition()) && !Combat.isUnderAttack()) {
            Log.log("Step 27: Going to Bandit Leader");
            PathingUtil.walkToArea(NORTH_BANDIT_AREA);
        }
        RSNPC[] banditChampion = NPCs.findNearest("Bandit champion");
        if (NORTH_BANDIT_AREA.contains(Player.getPosition()) && banditChampion.length < 1) {
            Log.log("Step 27: Talking to Bandit Leader");
            if (NpcChat.talkToNPC("Bandit Leader")) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation();
            }
        }

        banditChampion = NPCs.findNearest("Bandit champion");
        while (banditChampion.length > 0) {
            General.sleep(50);
            if (Combat.isUnderAttack()) {
                if (Combat.getHPRatio() < 45)
                    EatUtil.eatFood(false);

                if (PathingUtil.walkToTile(safeTile2))
                    Utils.idle(4000, 6000);

                if (PathingUtil.clickScreenWalk(safeTile2)) {
                    Timer.waitCondition(() -> SAFE_AREA2.contains(Player.getPosition()), 5000, 7000);
                    Utils.shortSleep();
                }
                if (!banditChampion[0].isInCombat() &&
                        AccurateMouse.click(banditChampion[0], "Attack"))
                    Utils.shortSleep();
                else {
                    Waiting.waitNormal(200, 50);
                }
            }
            banditChampion = NPCs.findNearest("Bandit champion");
        }
    }

    public void step28() {
        cQuesterV2.status = "Going to Mayor";
        PathingUtil.walkToArea(VILLAGER_AREA);
        if (VILLAGER_AREA.contains(Player.getPosition())) {
            cQuesterV2.status = "Step 28: Talking to the Mayor";
            General.println("[Debug]: " + cQuesterV2.status);
            if (NpcChat.talkToNPC("Ali the Mayor")) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation();
            }
        }
    }

    public void step29() {
        cQuesterV2.status = "Finishing Quest";
        General.println("[Debug]: " + cQuesterV2.status);
        PathingUtil.walkToArea(START_AREA);
        if (NpcChat.talkToNPC("Ali Morrisane")) {
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation();
            NPCInteraction.handleConversation();
            NPCInteraction.handleConversation();
        }
    }


    @Override
    public void execute() {
        if (!checkRequirements())
            cQuesterV2.taskList.remove(this);

        General.println("Game Setting 435 = " + Game.getSetting(435));
        if (Game.getSetting(435) == 0) {
            buyItems();
            getItems();
            startQuest();
        }
        if (Game.getSetting(435) == 1) {
            giveBeerToAli();
        }
        if (Game.getSetting(435) == 2) {
            if (RSVarBit.get(315).getValue() == 0) {
                step3();
            }
            if (RSVarBit.get(315).getValue() == 2) {
                step4();
            }
        }
        if (Game.getSetting(435) == 3) {
            if (RSVarBit.get(315).getValue() == 3 && RSVarBit.get(334).getValue() == 3) {
                step5();
            }
        }
        if (Game.getSetting(435) == 4) {
            if (RSVarBit.get(316).getValue() == 0) {
                step6();
            }
            if (RSVarBit.get(316).getValue() == 2) { // alternatively, use receipts.length < 2 to trigger this
                step7();
            }
        }
        if (Game.getSetting(435) == 5) {
            step8();
        }
        if (Game.getSetting(435) == 6) {
            step9();
        }
        if (Game.getSetting(435) == 2055 || Game.getSetting(435) == 2056) {
            step10();
        }
        if (Game.getSetting(435) == 4105) {
            step11();
            step12();

        } else if (Game.getSetting(435) == 4106 || Game.getSetting(435) == 4107) {
            step12();

        } else if (Game.getSetting(435) == 4108) {
            step13();

        } else if (Game.getSetting(435) == 4109) {
            step14();

        } else if (Game.getSetting(435) == 4110) {
            step15();

        } else if (Game.getSetting(435) == 4111) {
            step16();

        } else if (Game.getSetting(435) == 5136) {
            step17();

        } else if (Game.getSetting(435) == 37904) { // or alternatively, RSVARBit 342 is 1 and 334 = 16
            step16();

        } else if (Game.getSetting(435) == 37905) {// or alternatively, RSVARBit 342 is 1 and 334 = 17 and  328 is 0 and 345 = 0
            step18();
            step19();

        } else if (Game.getSetting(435) == 562193) {// or alternatively, RSVARBit 328 is 1 and 345 = 1 and  334 is 17
            step20();
            step21();
        }
        if (Game.getSetting(435) == 562194) {// or alternatively, RSVARBit 334 is 18
            step22();
        }
        if (Game.getSetting(435) == 562450) {
            step19();
        }
        if (Game.getSetting(435) == 562454) {// or alternatively, RSVARBit 334 is 22
            step23();
        }
        if (Game.getSetting(435) == 562519) {// or alternatively, RSVARBit 334 is 22
            step24();
        }
        if (Game.getSetting(435) == 562520) {// or alternatively, RSVARBit 334 is 22
            step25();
        }
        if (Game.getSetting(435) == 562457) {// or alternatively, RSVARBit 343 is 0
            step26();
        }
        if (Game.getSetting(435) == 628505) {// or alternatively, RSVARBit 343 is 1
            step27();
        }
        if (Game.getSetting(435) == 890650) {
            step26();
        }
        if (Game.getSetting(435) == 694042) {
            step28();
            Utils.longSleep();
        }
        if (Game.getSetting(435) == 693531) {
            step29();
        }
        if (Game.getSetting(435) == 693532) {
            Utils.modSleep();
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
        return "The Feud";
    }

    @Override
    public boolean checkRequirements() {
        return Skills.SKILLS.THIEVING.getActualLevel() >= 30;
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
        return Quest.THE_FEUD.getState().equals(Quest.State.COMPLETE);
    }
}
