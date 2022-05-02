package scripts.QuestPackages.PiratesTreasure;

import dax.walker_engine.interaction_handling.NPCInteraction;
import org.tribot.api.General;
import org.tribot.api2007.*;
import org.tribot.api2007.Combat;
import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSItem;
import org.tribot.api2007.types.RSTile;
import org.tribot.script.sdk.*;
import org.tribot.script.sdk.Inventory;
import org.tribot.script.sdk.types.LocalTile;
import org.tribot.script.sdk.types.WorldTile;
import scripts.*;
import scripts.QuestPackages.OlafsQuest.OlafsQuest;
import scripts.QuestSteps.*;
import scripts.Requirements.AreaRequirement;
import scripts.Requirements.ItemRequirement;
import scripts.Requirements.Items.ItemCollections;
import scripts.Requirements.Requirement;
import scripts.Requirements.Util.ConditionalStep;
import scripts.Requirements.Util.Conditions;
import scripts.Requirements.Util.LogicType;
import scripts.Requirements.WidgetTextRequirement;
import scripts.Tasks.Priority;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PiratesTreasure implements QuestTask {
    private static PiratesTreasure quest;

    public static PiratesTreasure get() {
        return quest == null ? quest = new PiratesTreasure() : quest;
    }

    //ItemRequirements
    ItemRequirement sixtyCoins, spade, karamjanRum, tenBananas,
            whiteApron, whiteApronEquipped, whiteApronHanging;

    private NPCStep speakToRedbeard;


    private QuestStep readPirateMessage;

    private ObjectStep climbStairs;
    UseItemOnObjectStep openChest;

    private ClickItemStep digUpTreasure;

    RSArea blueMoonFirst, karamjaZone1, karamjaZone2, karamjaBoat;

    AreaRequirement inBlueMoonFirst;
    private Requirement onKaramja;
    private Conditions atStart;
    private Conditions employed;
    private Conditions stashedRum;
    private Conditions haveShippedRum;
    private Requirement verifiedAState;
    private Requirement hasRumOffKaramja;
    private Conditions hadRumOffKaramja;
    private Conditions lostRum;
    private Conditions filledCrateWithBananasAndRum;
    //  private ChatMessageRequirement crateSent;
    //   private ChatMessageRequirement fillCrateWithBananasChat;

    private QuestStep talkToCustomsOfficer, getRumFromCrate, getWhiteApron, addBananasToCrate, addRumToCrate, talkToZambo, talkToLuthas, talkToLuthasAgain, goToKaramja, bringRumToRedbeard;


    private void setupZones() {
        karamjaZone1 = new RSArea(new RSTile(2688, 3235, 0), new RSTile(2903, 2879, 0));
        karamjaZone2 = new RSArea(new RSTile(2903, 2879, 0), new RSTile(2964, 3187, 0));
        karamjaBoat = new RSArea(new RSTile(2964, 3138, 0), new RSTile(2951, 3144, 1));
    }

    private void setupConditions() {
        onKaramja = new AreaRequirement(karamjaZone1, karamjaZone2, karamjaBoat);
    /*    Requirement offKaramja = new AreaRequirement(false, karamjaZone1, karamjaZone2, karamjaBoat);
        Requirement inPirateTreasureMenu = new WidgetTextRequirement(WidgetInfo.DIARY_QUEST_WIDGET_TITLE, getQuestHelper().getQuest().getName());

        hasRumOffKaramja = new Conditions(LogicType.AND, karamjanRum, offKaramja);
        hadRumOffKaramja = new Conditions(true, karamjanRum, offKaramja);
        lostRum = new Conditions(LogicType.AND, inPirateTreasureMenu, new WidgetTextRequirement(119, 8, "I seem to have lost it."));

        Requirement haveRumFromWidget = new Conditions(inPirateTreasureMenu, new WidgetTextRequirement(119, 8, "I should take it to"));

        Requirement agreedToGetRum = new WidgetTextRequirement(WidgetInfo.DIALOG_PLAYER_TEXT, "Ok, I will bring you some rum.");
        Requirement atStartFromWidget = new Conditions(inPirateTreasureMenu, new WidgetTextRequirement(119, 8, "I need to go to"));
        atStart = new Conditions(true, LogicType.OR, agreedToGetRum, atStartFromWidget, lostRum, hadRumOffKaramja, haveRumFromWidget);

        crateSent = new ChatMessageRequirement("Luthas hands you 30 coins.");

        Requirement employedFromWidget = new Conditions(inPirateTreasureMenu, new WidgetTextRequirement(119, 8, "I have taken employment"));

        /* Filled crate but not sent it and employed */
       /* Requirement employedByWydinFromWidget = new Conditions(inPirateTreasureMenu, new WidgetTextRequirement(119, 8, "I have taken a job at"));

        Requirement employedFromDialog = new Conditions(new WidgetTextRequirement(WidgetInfo.DIALOG_NPC_TEXT, "If you could fill it up with bananas, I'll pay you 30<br>gold.", "Have you completed your task yet?", "you should see the old crate"));
        employed = new Conditions(true, LogicType.OR, employedFromDialog, employedFromWidget, employedByWydinFromWidget);

        Requirement stashedRumFromWidget = new Conditions(inPirateTreasureMenu, new WidgetTextRequirement(119, 12, "I have hidden my"));
        Requirement stashedRumFromDialog = new Conditions(new WidgetTextRequirement(229, 1, "You stash the rum in the crate."));
        Requirement stashedRumFromChat = new Conditions(new ChatMessageRequirement("There is also some rum stashed in here too.", "There's already some rum in here...",
                "There is some rum in here, although with no bananas to cover it. It is a little obvious."));
        stashedRum = new Conditions(true, LogicType.OR, stashedRumFromDialog, stashedRumFromWidget, stashedRumFromChat, employedByWydinFromWidget);

        Requirement fillCrateBananas = new Conditions(new WidgetTextRequirement(229, 1, "You fill the crate with bananas."));
        fillCrateWithBananasChat = new ChatMessageRequirement("The crate is full of bananas.", "The crate is already full.");
        Requirement filledCrateWithBananas = new Conditions(false, LogicType.OR, fillCrateWithBananasChat, fillCrateBananas);
        filledCrateWithBananasAndRum = new Conditions(true, LogicType.AND, filledCrateWithBananas, stashedRum);

        Requirement shippedRumFromWidget = new Conditions(inPirateTreasureMenu, new WidgetTextRequirement(119, 15, "the crate has been shipped"));
        Requirement shippedRumFromDialog = new Conditions(stashedRum, crateSent);
        Requirement shippedRumFromChat = new ChatMessageRequirement("There is already some rum in Wydin's store, I should go and get that first.");
        haveShippedRum = new Conditions(true, LogicType.OR, shippedRumFromWidget, shippedRumFromDialog, shippedRumFromChat);
*/
        //  verifiedAState = new Conditions(true, LogicType.OR, atStart, employedFromWidget, employedByWydinFromWidget, stashedRumFromWidget, shippedRumFromWidget, lostRum, hadRumOffKaramja, haveRumFromWidget);

    }

    private void setupItemRequirements() {
        karamjanRum = new ItemRequirement("Karamjan rum", ItemID.KARAMJAN_RUM);
        tenBananas = new ItemRequirement("Banana", ItemID.BANANA, 10);
        whiteApron = new ItemRequirement("White apron", ItemID.WHITE_APRON);
        whiteApronEquipped = new ItemRequirement(ItemID.WHITE_APRON, 1, true);
        whiteApronHanging = new ItemRequirement("White apron", ItemID.WHITE_APRON_7957);
        whiteApronHanging.addAlternateItemID(ItemID.WHITE_APRON);
    }

    public Map<Integer, QuestStep> loadSteps() {
        sixtyCoins = new ItemRequirement(ItemID.COINS_995, 500, 60);
        spade = new ItemRequirement("Spade", ItemID.SPADE);

        Map<Integer, QuestStep> steps = new HashMap<>();

        speakToRedbeard = new NPCStep("Redbeard Frank", new RSTile(3053, 3251, 0));
        speakToRedbeard.addDialogStep("I'm in search of treasure.");
        speakToRedbeard.addDialogStep("Ok, I will bring you some rum");

        steps.put(0, speakToRedbeard);

        //  smuggleRum = new RumSmugglingStep(this);

        //  steps.put(1, smuggleRum);

        ItemRequirement pirateMessage = new ItemRequirement("Pirate message", ItemID.PIRATE_MESSAGE);
        ItemRequirement chestKey = new ItemRequirement("Chest key", ItemID.CHEST_KEY);
        //    chestKey.setTooltip("You can get another one from Redbeard Frank");

        readPirateMessage = new ClickItemStep(ItemID.PIRATE_MESSAGE, "Read", pirateMessage);
        climbStairs = new ObjectStep(ObjectID.STAIRCASE_11796, new RSTile(3228, 3393, 0),
                "Climb up the stairs in The Blue Moon Inn in Varrock.");
        openChest = new UseItemOnObjectStep(ItemID.CHEST_KEY, ObjectID.CHEST_2079,
                new RSTile(3219, 3396, 1), ChatScreen.isOpen(),
                chestKey);
        openChest.addDialogStep("Ok thanks, I'll go and get it.");


        inBlueMoonFirst = new AreaRequirement(blueMoonFirst);
        blueMoonFirst = new RSArea(new RSTile(3213, 3405, 1), new RSTile(3234, 3391, 1));

        ConditionalStep getTreasureMap = new ConditionalStep(climbStairs);
        getTreasureMap.addStep(new Conditions(chestKey, inBlueMoonFirst), openChest);
        getTreasureMap.addStep(pirateMessage, readPirateMessage);

        steps.put(2, getTreasureMap);

        digUpTreasure = new ClickItemStep(ItemID.SPADE, "Dig", new RSTile(2999, 3383, 0));

        steps.put(3, digUpTreasure);
        return steps;
    }


    RSArea START_AREA = new RSArea(new RSTile(3055, 3249, 0), new RSTile(3050, 3254, 0));
    RSArea BAR_AREA = new RSArea(new RSTile(2929, 3142, 0), new RSTile(2922, 3147, 0));
    RSArea HUT_AREA = new RSArea(new RSTile(2935, 3156, 0), new RSTile(2941, 3152, 0));
    RSArea CRATE_AREA = new RSArea(new RSTile(2940, 3151, 0), new RSTile(2943, 3150, 0));
    RSArea SMALL_FISHING_SHOP_AREA = new RSArea(new RSTile(3017, 3223, 0), new RSTile(3011, 3229, 0));
    RSArea WHOLE_FISHING_SHOP = new RSArea(new RSTile(3017, 3220, 0), new RSTile(3011, 3229, 0));
    RSArea WHOLE_FOOD_STORE = new RSArea(new RSTile(3016, 3203, 0), new RSTile(3012, 3210, 0));
    RSArea SMALL_FOOD_STORE_AREA = new RSArea(new RSTile(3016, 3203, 0), new RSTile(3012, 3207, 0));
    RSArea BACK_OF_SHOP = new RSArea(new RSTile(3011, 3206, 0), new RSTile(3009, 3208, 0));
    RSArea UPPER_TAVERN = new RSArea(new RSTile(3222, 3393, 1), new RSTile(3218, 3396, 1));
    RSArea PARK_AREA = new RSArea(new RSTile(2999, 3384, 0), new RSTile(3001, 3382, 0));
    RSArea KARAMJA = new RSArea(new RSTile(2897, 3164, 0), new RSTile(2960, 3135, 0));
    RSArea KARAMJA_DOCK = new RSArea(new RSTile(2953, 3147, 0), new RSTile(2955, 3146, 0));
    RSArea TAVERN_MAIN_FLOOR = new RSArea(new RSTile(3228, 3394, 0), new RSTile(3226, 3396, 0));
    RSArea UPPER_FLOOR_AROUND_STAIRS = new RSArea(new RSTile(3223, 3396, 1), new RSTile(3232, 3393, 1));
    RSArea WHOLE_UPPER_FLOOR = new RSArea(new RSTile(3233, 3392, 1), new RSTile(3217, 3396, 1));

    public void getItems() {
        cQuesterV2.status = "Getting items";
        General.println("[Debug]: " + cQuesterV2.status);
        BankManager.open(true);
        BankManager.checkEquippedGlory();
        BankManager.depositAll(true);
        BankManager.withdraw(1, true, ItemID.SPADE);
        BankManager.withdraw(1, true, ItemID.WHITE_APRON);
        BankManager.withdraw(10, true, ItemID.BANANA);
        BankManager.withdraw(1000, true, 995);
        BankManager.withdraw(3, true,
                ItemID.FALADOR_TELEPORT);
        BankManager.withdraw(3, true,
                ItemID.VARROCK_TELEPORT);
        BankManager.withdraw(1, true,
                ItemID.STAMINA_POTION[0]);
        BankManager.withdraw(1, true,
                ItemID.AMULET_OF_GLORY[0]);
        Banking.close();
    }

    public void buyRum() {
        cQuesterV2.status = "Buying Rum";
        General.println("[Debug]: " + cQuesterV2.status);
        if (!Inventory.contains(ItemID.KARAMJAN_RUM) && Inventory.contains(ItemID.BANANA)) {
            PathingUtil.walkToArea(BAR_AREA);
            if (!Shop.isOpen() && NpcChat.talkToNPC(3205, "Trade")) {
                Timer.waitCondition(Shop::isOpen, 8000);
            }
            if (Shop.isOpen() &&
                    Shop.buy(Shop.Quantity.ONE, ItemID.KARAMJAN_RUM)) {
                Waiting.waitUntil(3000, 120, () -> Inventory.contains(ItemID.KARAMJAN_RUM));
            }
        }
    }

    public void getJob() {
        if (Inventory.getCount(ItemID.BANANA) > 9) {
            cQuesterV2.status = "Going to Hut to get Job";
            General.println("[Debug]: " + cQuesterV2.status);
            PathingUtil.walkToArea(HUT_AREA);
            if (NpcChat.talkToNPC("Luthas") && NpcChat.waitForChatScreen()) {
                ChatScreen.handle("Could you offer me employment on your plantation?");

                Walking.clickTileMS(new RSTile(2942, 3151, 0), "Walk here");
            }
        }
    }

    public void fillCrate() {
        if (Inventory.getCount(ItemID.BANANA) > 9) {
            cQuesterV2.status = "Depositing rum and bananas";
            if (Utils.useItemOnObject(ItemID.KARAMJAN_RUM, 2072)) {
                PathingUtil.movementIdle();
                Waiting.waitUntil(1500, 200, MyPlayer::isAnimating);
            }
            if (Utils.clickObj(2072, "Fill")) {
                PathingUtil.movementIdle();
                Waiting.waitUntil(1500, 200, MyPlayer::isAnimating);
            }

        }
    }


    public void returnToHut() {
        cQuesterV2.status = "Returning to Hut";
        General.println("[Debug]: " + cQuesterV2.status);
        PathingUtil.walkToArea(HUT_AREA);
        if (NpcChat.talkToNPC("Luthas")) {
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation("So where are these bananas going to be delivered to?");
            NPCInteraction.handleConversation();
        }
    }

    public void leaveKaramja() {
        if (!Inventory.contains(ItemID.KARAMJAN_RUM) && Inventory.getCount(ItemID.BANANA) < 2) {
            cQuesterV2.status = "Going to Food shop";
            General.println("[Debug]: " + cQuesterV2.status);
            if (!SMALL_FOOD_STORE_AREA.contains(Player.getPosition())) {
                if (KARAMJA.contains(Player.getPosition())) {
                    PathingUtil.walkToArea(KARAMJA_DOCK);
                    if (NpcChat.talkToNPC(3648)) {
                        NPCInteraction.waitForConversationWindow();
                        NPCInteraction.handleConversation("Can I journey on this ship?");
                        NPCInteraction.handleConversation("Search away, I have nothing to hide.", "Ok.");
                        NPCInteraction.handleConversation("Ok.");
                        NPCInteraction.handleConversation();
                    }
                }
            }
        }
    }

    public void goToFoodShop() {
        if (!Inventory.contains(ItemID.KARAMJAN_RUM)) {
            PathingUtil.walkToArea(SMALL_FOOD_STORE_AREA);
            cQuesterV2.status = "Getting a Job";
            if (WHOLE_FOOD_STORE.contains(Player.getPosition()) &&
                    NpcChat.talkToNPC("Wydin")) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation("Can I get a job here?");
                NPCInteraction.handleConversation();
            }
        }
        if (!Inventory.contains(ItemID.KARAMJAN_RUM)) {
            Utils.equipItem(ItemID.WHITE_APRON);
            PathingUtil.walkToArea(BACK_OF_SHOP);
            cQuesterV2.status = "Searching for rum";
            if (Utils.clickObj(2071, "Search"))
                Timer.waitCondition(() -> Inventory.contains(ItemID.KARAMJAN_RUM), 15000);
        }
    }

    public void talkToFrank() {
        if (Inventory.contains(ItemID.KARAMJAN_RUM)) {
            cQuesterV2.status = "Going to pirate";
            General.println("[Debug]: " + cQuesterV2.status);
            PathingUtil.walkToArea(START_AREA);
            if (NpcChat.talkToNPC("Redbeard Frank")) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation("Ok thanks, I'll go and get it.");
                NPCInteraction.handleConversation();
            }
        }

    }


    public void step6() {

        if (!UPPER_TAVERN.contains(Player.getPosition())) {
            if (!WHOLE_UPPER_FLOOR.contains(Player.getPosition())) {
                cQuesterV2.status = "Going to tavern";
                PathingUtil.walkToArea(TAVERN_MAIN_FLOOR);
                if (Utils.clickObj(11796, "Climb-up"))
                    Timer.waitCondition(() -> Player.getPosition().getPlane() == 1, 5000, 7000);
            }
          /*  if (UPPER_FLOOR_AROUND_STAIRS.contains(Player.getPosition())) {
                PathingUtil.localNavigation(new RSTile(3223, 3395, 1));
                Utils.clickObject(11775, "Open", 2);

                scripts.Fremmy.Constants.idle(2000, 5000);
            } */
            if (PathingUtil.localNav(new LocalTile(3219, 3395, 1)))
                PathingUtil.movementIdle();

            //scripts.Fremmy.Constants.idle(2000, 5000);
        }
        if (UPPER_TAVERN.contains(Player.getPosition()) && org.tribot.api2007.Inventory.find(433).length < 1) {
            if (Utils.useItemOnObject(432, 2079))
                Timer.waitCondition(() -> org.tribot.api2007.Inventory.find(433).length > 0, 8000);
        }
        RSItem[] scroll = org.tribot.api2007.Inventory.find(433);
        if (scroll.length > 0 && scroll[0].click("Read"))
            Utils.shortSleep();
    }


    public void step7() {
        if (!PARK_AREA.contains(Player.getPosition())) {
            PathingUtil.walkToTile(new RSTile(3000, 3383, 0), 2, false);
            Timer.waitCondition(() -> PARK_AREA.contains(Player.getPosition()), 5000);
        }
        RSItem[] spade = org.tribot.api2007.Inventory.find(ItemID.SPADE);
        if (PARK_AREA.contains(Player.getPosition()) && spade.length > 0) {
            cQuesterV2.status = "Digging";
            if (spade[0].click("Dig")) {
                Timer.waitCondition(() -> Combat.isUnderAttack(), 5000);
                Timer.waitCondition(() -> !Combat.isUnderAttack(), 25000);
            }
            if (spade[0].click("Dig"))
                Timer.waitCondition(() -> Game.getSetting(71) == 4, 3000, 5000);

        }
    }

    public List<ItemRequirement> getItemRequirements() {
        ArrayList<ItemRequirement> reqs = new ArrayList<>();
        reqs.add(sixtyCoins);
        reqs.add(spade);
        return reqs;
    }

    @Override
    public Priority priority() {
        return Priority.LOW;
    }

    @Override
    public boolean validate() {
        return cQuesterV2.taskList.size() > 0 && cQuesterV2.taskList.get(0).equals(this);
    }

    @Override
    public void execute() {
        setupItemRequirements();
        setupConditions();
        setupZones();

        if (Quest.PIRATES_TREASURE.getState().equals(Quest.State.COMPLETE)) {
            cQuesterV2.taskList.remove(this);
            return;
        }
        General.sleep(100, 300);
        if (Game.getSetting(71) == 0) {
            //  buyItems();
            getItems();
            speakToRedbeard.execute();
        }
        if (Game.getSetting(71) == 1) {
            buyRum(); // gets rum
            getJob();
            fillCrate();
            returnToHut();
            //leaveKaramja();
            goToFoodShop();
            talkToFrank();
        } else if (Game.getSetting(71) == 2) {
            step6();
        } else if (Game.getSetting(71) == 3) {
            step7();
        }
    }

    @Override
    public String questName() {
        return "Pirate's Treasure";
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

    @Override
    public boolean isComplete() {
        return Quest.PIRATES_TREASURE.getState().equals(Quest.State.COMPLETE);
    }
}
