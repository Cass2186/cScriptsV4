package scripts.QuestPackages.TouristTrap;

import dax.walker.utils.AccurateMouse;
import dax.walker_engine.interaction_handling.NPCInteraction;
import lombok.Getter;
import org.tribot.api.General;
import org.tribot.api2007.*;
import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSItem;
import org.tribot.api2007.types.RSObject;
import org.tribot.api2007.types.RSTile;
import org.tribot.script.sdk.MyPlayer;
import org.tribot.script.sdk.Quest;
import org.tribot.script.sdk.Waiting;
import org.tribot.script.sdk.query.Query;
import org.tribot.script.sdk.types.Area;
import scripts.*;
import scripts.GEManager.GEItem;
import scripts.QuestPackages.Barcrawl.BarCrawl;
import scripts.QuestSteps.BuyItemsStep;
import scripts.QuestSteps.QuestTask;
import scripts.Requirements.*;
import scripts.Requirements.Util.Conditions;
import scripts.Requirements.Util.Operation;
import scripts.Tasks.Priority;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.zip.InflaterInputStream;

public class TouristTrap implements QuestTask {

    private static TouristTrap quest;

    public static TouristTrap get() {
        return quest == null ? quest = new TouristTrap() : quest;
    }

    ItemRequirement shantayPass = new ItemRequirement(ItemID.SHANTAY_PASS, 10, 1);
    ItemRequirement bronzeBar = new ItemRequirement(ItemID.BRONZE_BAR, 3, 1);
    ItemRequirement hammer = new ItemRequirement(ItemID.HAMMER, 1, 1);

    public int feather = 314; //need 60
    public int waterskin4 = 1823; // need 6

    int lobster = 379; // need 10
    int metalKey = 1839;
    int jailKey = 1840;
    int CHEST_KEY = 1852;

    int PLANS = 1850;
    int prototypeDartTip = 1853;
    int prototypeDart = 1849;
    int PINEAPPLE_ID = 1851;
    int BARREL_ID = 1841;
    int ANA_IN_BARREL = 1842;
    int KNIFE = 946;

    enum REWARDS {
        AGILITY("Agility."),
        SMITHING("Smithing."),
        FLETCHING("Fletching."),
        THIEVING("Thieving");

        @Getter
        private String text;

        REWARDS(String string) {
            this.text = string;
        }
    }


    public static String skill1 = REWARDS.AGILITY.getText();
    public static String skill2 = REWARDS.AGILITY.getText();


    String[] CAPTAIN_CHAT_ARRAY = {
            "Wow! A real captain!",
            "I'd love to work for a tough guy like you!",
            "Can't I do something for a strong Captain like you?",
            "Sorry Sir, I don't think I can do that.",
            "It's a funny captain who can't Fight his own battles!"
    };

    String[] SLAVE_CHAT_1 = {
            "I've just arrived.",
            "Oh yes, that sounds interesting.",
            "What's that then?",
            "I can try to undo them for you.",
            "It's funny you should say that..."
    };


    String[] slave6 = {"Yeah, okay, let's give it a go.", "Yeah, I'll give it another go."};
    String[] slave8 = {"Yes, I'll trade.", "Yeah, I'll trade."};

    String[] CAPTAIN_SIAD = {"I wanted to have a chat?",
            "You seem to have a lot of books!",
            "So, you're interested in sailing?",
            "I could tell by the cut of your jib."
    };

    String[] START_QUEST_CHAT = {"What's the matter?",
            "When did she go into the desert?",
            "I'll look for your daughter.",
            "Okay Irena, calm down. I'll get your daughter back for you.",
            "Yes, I'll go on this quest!",
            "Yes."
    };

    String[] GUARD_ARRAY = {
            "I'd like to mine in a different area.",
            "Yes sir, you're quite right sir.",
            "Yes sir, we understand each other perfectly."
    };

    //*******NPCS**********//

    RSArea MERCENARY_CAPTAIN_AREA = new RSArea(new RSTile(3270, 3027, 0), new RSTile(3262, 3033, 0));
    RSArea START_AREA = new RSArea(new RSTile(3301, 3114, 0), new RSTile(3304, 3110, 0));
    RSArea WATER_BOWL_ROOM = new RSArea(new RSTile(3288, 3034, 0), new RSTile(3293, 3031, 0));
    RSArea MALE_SLAVE_AREA = new RSArea(new RSTile(3304, 3026, 0), new RSTile(3301, 3027, 0));
    RSArea MINE_ENTRANCE = new RSArea(new RSTile(3299, 3036, 0), new RSTile(3304, 3035, 0));
    RSArea INSIDE_MINE_DOOR = new RSArea(new RSTile(3277, 9427, 0), new RSTile(3280, 9429, 0));
    RSArea END_OF_MINE = new RSArea(new RSTile(3276, 9416, 0), new RSTile(3280, 9412, 0));
    RSArea ALSHABIM_AREA = new RSArea(new RSTile(3168, 3031, 0), new RSTile(3174, 3024, 0));
    RSArea SECOND_FLOOR = new RSArea(new RSTile(3293, 3031, 1), new RSTile(3284, 3037, 1));
    RSArea ANVIL_AREA = new RSArea(new RSTile(3167, 3049, 0), new RSTile(3172, 3046, 0));
    RSArea AFTER_MINE_CAVE = new RSArea(new RSTile(3284, 9422, 0), new RSTile(3293, 9410, 0));
    RSArea BARREL_AREA = new RSArea(new RSTile(3300, 9420, 0), new RSTile(3309, 9415, 0));
    RSArea AFTER_CART_RIDE_1 = new RSArea(new RSTile(3316, 9434, 0), new RSTile(3322, 9428, 0));
    RSArea ANNA_AREA = new RSArea(new RSTile(3300, 9468, 0), new RSTile(3306, 9464, 0));
    RSArea WINCH_AREA = new RSArea(new RSTile(3290, 9424, 0), new RSTile(3293, 9421, 0));
    RSArea OUTSIDE_WINCH = new RSArea(new RSTile(3283, 3018, 0), new RSTile(3278, 3021, 0));
    RSArea LANDING_AREA = new RSArea(new RSTile(3255, 3027, 0), new RSTile(3260, 3033, 0));
    RSArea JAIL_AREA = new RSArea(new RSTile(3287, 3031, 0), new RSTile(3284, 3037, 0));
    RSArea OUTSIDE_JAIL_WINDOW = new RSArea(new RSTile(3283, 3033, 0), new RSTile(3282, 3037, 0));
    RSArea BEFORE_CLIFF = new RSArea(new RSTile(3279, 3035, 0), new RSTile(3279, 3039, 0));
    RSArea ON_CLIFF = new RSArea(new RSTile(3277, 3037, 0), new RSTile(3274, 3040, 0));
    RSArea OFF_CLIFF = new RSArea(new RSTile(3269, 3041, 0), new RSTile(3265, 3037, 0));
    RSArea WHOLE_LEFT_SIDE_OF_MINE = new RSArea(new RSTile(3281, 9410, 0), new RSTile(3265, 9465, 0));

    final RSTile[] PATH_TO_ANNA = new RSTile[]{new RSTile(3319, 9431, 0), new RSTile(3316, 9433, 0), new RSTile(3313, 9435, 0), new RSTile(3310, 9435, 0), new RSTile(3307, 9435, 0), new RSTile(3307, 9438, 0), new RSTile(3307, 9442, 0), new RSTile(3304, 9444, 0), new RSTile(3301, 9447, 0), new RSTile(3299, 9450, 0), new RSTile(3297, 9453, 0), new RSTile(3296, 9456, 0), new RSTile(3296, 9460, 0), new RSTile(3297, 9463, 0), new RSTile(3300, 9465, 0)};
    final RSTile[] NEW_PATH_TO_ANNA = new RSTile[]{new RSTile(3319, 9431, 0), new RSTile(3318, 9431, 0), new RSTile(3317, 9431, 0), new RSTile(3317, 9432, 0), new RSTile(3316, 9433, 0), new RSTile(3315, 9434, 0), new RSTile(3314, 9434, 0), new RSTile(3313, 9435, 0), new RSTile(3312, 9435, 0), new RSTile(3311, 9435, 0), new RSTile(3310, 9435, 0), new RSTile(3309, 9435, 0), new RSTile(3308, 9435, 0), new RSTile(3307, 9435, 0), new RSTile(3307, 9436, 0), new RSTile(3307, 9437, 0), new RSTile(3307, 9438, 0), new RSTile(3307, 9439, 0), new RSTile(3307, 9440, 0), new RSTile(3307, 9442, 0), new RSTile(3306, 9442, 0), new RSTile(3305, 9443, 0), new RSTile(3304, 9444, 0), new RSTile(3303, 9445, 0), new RSTile(3302, 9446, 0), new RSTile(3301, 9447, 0), new RSTile(3300, 9448, 0), new RSTile(3299, 9449, 0), new RSTile(3299, 9450, 0), new RSTile(3299, 9451, 0), new RSTile(3298, 9453, 0), new RSTile(3297, 9454, 0), new RSTile(3296, 9455, 0), new RSTile(3296, 9456, 0), new RSTile(3296, 9457, 0), new RSTile(3296, 9458, 0), new RSTile(3296, 9459, 0), new RSTile(3296, 9460, 0), new RSTile(3296, 9461, 0), new RSTile(3296, 9462, 0), new RSTile(3297, 9463, 0), new RSTile(3298, 9464, 0), new RSTile(3299, 9465, 0), new RSTile(3300, 9466, 0), new RSTile(3301, 9466, 0), new RSTile(3302, 9466, 0), new RSTile(3303, 9466, 0), new RSTile(3304, 9466, 0)};
    ArrayList<RSTile> tilePath = new ArrayList<RSTile>(Arrays.asList(NEW_PATH_TO_ANNA));

    /**
     * Runelite Areas
     */
    RSArea jail = new RSArea(new RSTile(3284, 3031, 0), new RSTile(3287, 3037, 0));
    RSArea camp = new RSArea(new RSTile(3274, 3014, 0), new RSTile(3305, 3037, 1));
    RSArea upstairs = new RSArea(new RSTile(3284, 3031, 1), new RSTile(3293, 3037, 1));
    RSArea slope = new RSArea(new RSTile(3282, 3032, 0), new RSTile(3283, 3037, 0));
    RSArea cliff = new RSArea(new RSTile(3279, 3037, 0), new RSTile(3281, 3038, 0));
    RSArea secondCliff = new RSArea(new RSTile(3273, 3035, 0), new RSTile(3278, 3039, 0));

    RSArea mine1 = new RSArea(new RSTile(3266, 9410, 0), new RSTile(3282, 9466, 0));
    RSArea deepMine = new RSArea(new RSTile(3282, 9408, 0), new RSTile(3326, 9470, 0));
    RSArea deepMineP1 = new RSArea(new RSTile(3283, 9409, 0), new RSTile(3314, 9427, 0));
    RSArea deepMineP2P1 = new RSArea(new RSTile(3315, 9416, 0), new RSTile(3326, 9470, 0));
    RSArea deepMineP2P2 = new RSArea(new RSTile(3293, 9429, 0), new RSTile(3314, 9470, 0));
    RSArea miningRoom = new RSArea(new RSTile(3283, 9427, 0), new RSTile(3292, 9454, 0));


    AreaRequirement inJail = new AreaRequirement(jail);
    AreaRequirement inCamp = new AreaRequirement(camp);
    AreaRequirement inMine1 = new AreaRequirement(mine1);
    AreaRequirement inUpstairs = new AreaRequirement(upstairs);
    AreaRequirement inDeepMine = new AreaRequirement(deepMine);
    AreaRequirement inDeepMineP1 = new AreaRequirement(deepMineP1);
    AreaRequirement inDeepMineP2 = new AreaRequirement(deepMineP2P1, deepMineP2P2);
    AreaRequirement inMiningRoom = new AreaRequirement(miningRoom);

    AreaRequirement onSlope = new AreaRequirement(slope);
    AreaRequirement onCliff = new AreaRequirement(cliff);
    AreaRequirement onSecondCliff = new AreaRequirement(secondCliff);
    AreaRequirement inJailEscape = new AreaRequirement(jail, slope, cliff, secondCliff);

    //    hasSlaveClothes = new ItemRequirements(slaveTop, slaveBoot, slaveRobe);

    Conditions searchedBookcase = new Conditions(true, new WidgetTextRequirement(WidgetInfo.DIALOG_SPRITE_TEXT, "You notice several books on the subject of sailing."));
    Conditions distractedSiad = new Conditions(true, new WidgetTextRequirement(229, 1, "The captain starts rambling on about his days as a salty sea dog. He<br>looks quite distracted..."));

    VarbitRequirement anaPlacedOnCartOfLift = new VarbitRequirement(2805, 1);

    // TODO: Better detection of if Ana is on the surface or in the underground barrel
    VarplayerRequirement anaOnSurface = new VarplayerRequirement(197, 22, Operation.GREATER_EQUAL);

    // TODO: This only gets set the first time. If you somehow lose Ana between here and the cart it remains set. Need to add more logic around this
    VarbitRequirement anaOnSurfaceInBarrel = new VarbitRequirement(2808, 1);
    VarbitRequirement anaOnCart = new VarbitRequirement(2809, 1);
    VarbitRequirement anaFree = new VarbitRequirement(3733, 1);


    public void checkLevel() {
        if (Skills.getActualLevel(Skills.SKILLS.FLETCHING) < 10) {
            SHOULD_FLETCH = true;
        }
        if (Skills.getActualLevel(Skills.SKILLS.SMITHING) < 20) {
            General.println("[Debug]: Don't have required Smithing level");
        }
    }

    public void determineRewards() {
        if (Skills.getActualLevel(Skills.SKILLS.AGILITY) < 35) {
            skill1 = "Agility.";
            skill2 = "Agility.";
        } else {

            skill1 = "Thieving";
            skill2 = "Thieving";

        }
    }

    boolean SHOULD_FLETCH = Skills.getActualLevel(Skills.SKILLS.FLETCHING) < 10;

    public void makeArrowShafts() {
        cQuesterV2.status = "Making arrow shafts";
        General.println("[Debug]: " + cQuesterV2.status);
        if (Inventory.find(ItemID.LOGS).length < 1) {
            BankManager.open(true);
            BankManager.depositAllExcept(true, KNIFE, ARROW_SHAFTS);

            if (Inventory.find(KNIFE).length < 1)
                BankManager.withdraw(1, true, KNIFE);
            if (BankManager.getCount(ARROW_SHAFTS) > 400)
                BankManager.withdraw(0, ARROW_SHAFTS);
            else {
                BankManager.withdraw(0, true, ItemID.LOGS);
                BankManager.close(true);
            }
        }

        if (Utils.useItemOnItem(KNIFE, ItemID.LOGS)) {
            Timer.waitCondition(() -> Interfaces.isInterfaceSubstantiated(270, 14), 4000, 6000);

            if (Interfaces.isInterfaceSubstantiated(270, 14)) {

                if (Interfaces.get(270, 14).click())
                    Timer.abc2WaitCondition(() -> (Interfaces.get(233, 2) != null || Inventory.find(
                            ItemID.LOGS).length < 1), 35000, 40000);
            }
        }
    }

    public void trainFletching() {
        while (SHOULD_FLETCH && Skills.getActualLevel(Skills.SKILLS.FLETCHING) < 10) {
            General.sleep(50, 150);
            fletching();
        }
    }

    ArrayList<GEItem> itemsToBuy = new ArrayList<GEItem>(Arrays.asList(
            new GEItem(ItemID.KNIFE, 1, 500),
            new GEItem(ItemID.BRONZE_BAR, 3, 500),
            new GEItem(ItemID.FEATHERS, 25, 500),
            new GEItem(ItemID.SHANTAY_PASS, 12, 500),
            new GEItem(ItemID.DESERT_SHIRT, 1, 500),
            new GEItem(ItemID.DESERT_BOOTS, 1, 500),
            new GEItem(ItemID.DESERT_ROBE, 1, 500),
            new GEItem(ItemID.WATERSKIN[0], 6, 500),
            new GEItem(ItemID.LOBSTER, 20, 50),
            new GEItem(ItemID.AMULET_OF_GLORY[0], 2, 20),
            new GEItem(ItemID.STAMINA_POTION[0], 5, 30),
            new GEItem(ItemID.HAMMER, 1, 500),
            new GEItem(ItemID.MIND_RUNE, 300, 50),
            new GEItem(ItemID.FIRE_RUNE, 900, 50),
            new GEItem(ItemID.STAFF_OF_AIR, 1, 200),
            new GEItem(ItemID.PRAYER_POTION_4, 4, 20)
    ));


    public void buyItems() {
        cQuesterV2.status = "Buying Items";
        General.println("[Debug]: " + cQuesterV2.status);
        if (Skills.SKILLS.FLETCHING.getActualLevel() < 10) {
            int xpTo10 = Skills.getXPToLevel(Skills.SKILLS.FLETCHING, 10);
            itemsToBuy.add(new GEItem(ItemID.LOGS, ((xpTo10 / 5) + 5), 50));
        }
        BuyItemsStep buyStep = new BuyItemsStep(itemsToBuy);
        buyStep.buyItems();
    }

    public void getItems() {
        cQuesterV2.status = "Getting Items";
        General.println("[Debug]: Getting Items");
        BankManager.open(true);
        BankManager.depositAll(true);
        BankManager.depositEquipment();
        BankManager.withdraw(1, true, ItemID.DESERT_BOOTS);
        BankManager.withdraw(1, true, ItemID.DESERT_ROBE);
        BankManager.withdraw(1, true, ItemID.DESERT_SHIRT);
        Utils.equipItem(ItemID.DESERT_BOOTS);
        Utils.equipItem(ItemID.DESERT_ROBE);
        Utils.equipItem(ItemID.DESERT_SHIRT);
        BankManager.withdraw(3, true, waterskin4);
        BankManager.withdraw(1, true, ItemID.AMULET_OF_GLORY[0]);
        BankManager.withdraw(1, true, ItemID.HAMMER);
        BankManager.withdraw(300, true, ItemID.MIND_RUNE);
        BankManager.withdraw(900, true, ItemID.FIRE_RUNE);
        BankManager.withdraw(3, true, ItemID.BRONZE_BAR);
        BankManager.withdraw(50, true, feather);
        BankManager.withdraw(5, true, ItemID.SHANTAY_PASS);
        BankManager.withdraw(1, true, ItemID.STAFF_OF_AIR);
        BankManager.withdraw(7, true, ItemID.LOBSTER);
        BankManager.withdraw(3, true, ItemID.STAMINA_POTION[0]);
        BankManager.withdraw(1, true, ItemID.RING_OF_DUELING[0]);
        BankManager.withdraw(2, true, ItemID.PRAYER_POTION[0]);
        BankManager.close(true);
        Utils.modSleep();
        Utils.equipItem(ItemID.AMULET_OF_GLORY[0]);
        Utils.equipItem(ItemID.RING_OF_DUELING[0]);
        Utils.equipItem(ItemID.STAFF_OF_AIR);
        if (Prayer.getPrayerPoints() < 30 && Skills.getCurrentLevel(Skills.SKILLS.PRAYER) > 42) {
            Utils.clanWarsReset();
        }
    }


    public void startQuest() {
        if (Prayer.getPrayerPoints() < 30 && Skills.getCurrentLevel(Skills.SKILLS.PRAYER) > 42) {
            cQuesterV2.status = "Going to Clan Wars reset";
            Utils.clanWarsReset();
        }

        cQuesterV2.status = "Going to Irena";
        General.println("[Debug]: " + cQuesterV2.status);
        PathingUtil.walkToArea(START_AREA);
        if (NpcChat.talkToNPC("Irena")) {
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation(START_QUEST_CHAT);
            NPCInteraction.handleConversation(START_QUEST_CHAT[1]);
            NPCInteraction.handleConversation(START_QUEST_CHAT[2]);
            NPCInteraction.handleConversation(START_QUEST_CHAT[3]);
            NPCInteraction.handleConversation(START_QUEST_CHAT[4]);
            NPCInteraction.handleConversation();
            Utils.modSleep();
        }
    }

    int ARROW_SHAFTS = 52;
    int LOGS = 1511;
    int HEADLESS_ARROW = 53;

    public void fletching() {
        RSItem[] invArrowShafts = Inventory.find(ARROW_SHAFTS);
        int stack = invArrowShafts.length > 0 ? invArrowShafts[0].getStack() : 0;
        if (stack > 300) {
            if (Inventory.find(feather).length < 1 && Inventory.getAll().length < 28) {
                cQuesterV2.status = "Getting Feathers";
                General.println("[Debug]: " + cQuesterV2.status);
                BankManager.withdraw(0, true, feather);
                BankManager.close(true);

            } else if (Utils.useItemOnItem(feather, ARROW_SHAFTS)) {

                Timer.waitCondition(() -> Interfaces.isInterfaceSubstantiated(270, 14), 4000, 6000);

                if (Interfaces.isInterfaceSubstantiated(270, 14)) {
                    if (Interfaces.get(270, 14).click()) {
                        cQuesterV2.status = "Making headless arrows";
                        General.println("[Debug]: " + cQuesterV2.status);
                        Timer.abc2SkillingWaitCondition(() -> invArrowShafts[0].getStack() == (stack - 150), 15000, 25000);
                    }
                }
            }
        } else {
            makeArrowShafts();
        }
    }


    public void killCaptain() {
        if (Inventory.find(metalKey).length < 1) {
            if (Prayer.getPrayerPoints() < 30 && Skills.getCurrentLevel(Skills.SKILLS.PRAYER) > 42 && Inventory.find(ItemID.PRAYER_POTION).length < 1 && Inventory.find(metalKey).length < 1)
                Utils.clanWarsReset();

            cQuesterV2.status = "Killing Captain for key";
            PathingUtil.walkToArea(MERCENARY_CAPTAIN_AREA);
            if (!Autocast.isAutocastEnabled(Autocast.FIRE_STRIKE))
                Autocast.enableAutocast(Autocast.FIRE_STRIKE);

            if (!Combat.isUnderAttack()) {
                if (NpcChat.talkToNPC("Mercenary Captain")) {
                    NPCInteraction.waitForConversationWindow();
                    NPCInteraction.handleConversation("Wow! A real captain!");
                    NPCInteraction.handleConversation("I'd love to work for a tough guy like you!");
                    NPCInteraction.handleConversation("Can't I do something for a strong Captain like you?");
                    NPCInteraction.handleConversation("Sorry Sir, I don't think I can do that.");
                    NPCInteraction.handleConversation("It's a funny captain who can't Fight his own battles!");
                    NPCInteraction.handleConversation();
                    Timer.waitCondition(() -> Combat.isUnderAttack(), 5000, 8000);
                }
            }
            if (Combat.isUnderAttack()) {
                int drinkAt = General.random(5, 15);
                if (!Combat.isAutoRetaliateOn())
                    Combat.setAutoRetaliate(true);

                if (!Autocast.isAutocastEnabled(Autocast.FIRE_STRIKE))
                    Autocast.enableAutocast(Autocast.FIRE_STRIKE);

                if (Prayer.getPrayerPoints() > 0)
                    Prayer.enable(Prayer.PRAYERS.PROTECT_FROM_MELEE);

                Timer.waitCondition(() -> !Combat.isUnderAttack() || Prayer.getPrayerPoints() < drinkAt
                        || Combat.getHPRatio() < 50, 50000, 70000);

                if (Combat.getHPRatio() < 50 && Inventory.find(ItemID.FOOD_IDS).length > 0) {
                    Inventory.find(ItemID.FOOD_IDS)[0].click();
                    Utils.microSleep();

                } else if (Prayer.getPrayerPoints() < drinkAt && Inventory.find(ItemID.PRAYER_POTION).length > 0) {
                    Inventory.find(ItemID.PRAYER_POTION)[0].click();
                    Utils.microSleep();

                } else if (!Combat.isUnderAttack())
                    Utils.modSleep(); // called if we finish fight
            }
        }
    }

    public void removeGear() {
        if (Inventory.find(metalKey).length > 0 && !Combat.isUnderAttack()) {
            Utils.microSleep(); // in case the killing doesn't call the sleep after
            cQuesterV2.status = "Disabling prayer and unequiping items.";
            General.println("[Debug]: " + cQuesterV2.status);
            Prayer.disable(Prayer.PRAYERS.PROTECT_FROM_MELEE);
            Equipment.remove(Equipment.SLOTS.AMULET);
            Equipment.remove(Equipment.SLOTS.WEAPON);
            Equipment.remove(Equipment.SLOTS.CAPE);
            Equipment.remove(Equipment.SLOTS.HELMET);
            Equipment.remove(Equipment.SLOTS.ARROW);
            Equipment.remove(Equipment.SLOTS.SHIELD);
            Equipment.remove(Equipment.SLOTS.GLOVES);
            Utils.shortSleep();
        }
    }

    public void getNewKey() {
        killCaptain();
        removeGear();
    }

    int drinkPPot = General.random(15, 25);

    public void talkToCaptain() {
        cQuesterV2.status = "Going to Captain";
        General.println("[Debug]: " + cQuesterV2.status);
        getNewKey();
    }

    public void step3() {
        removeGear();
        getJailKey();
    }


    public void getJailKey() {
        if (Equipment.find(Equipment.SLOTS.WEAPON).length > 0) {
            if (Inventory.isFull()) {
                EatUtil.eatFood(false);
                Utils.microSleep();
                removeGear();
            }
        }
        if (Inventory.find(jailKey).length < 1 && Inventory.find(metalKey).length > 0 && Equipment.find(Equipment.SLOTS.WEAPON).length < 1) {
            cQuesterV2.status = "Getting Jail key";
            General.println("[Debug]: " + cQuesterV2.status);
            PathingUtil.walkToArea(WATER_BOWL_ROOM);
            if (WATER_BOWL_ROOM.contains(Player.getPosition())) {
                if (Utils.clickObj(18902, "Search", false)) {
                    NPCInteraction.waitForConversationWindow();
                    NPCInteraction.handleConversation();
                    Timer.waitCondition(() -> Inventory.find(jailKey).length > 0, 8000, 10000);
                }
            }
        }
    }


    public void step4() {
        getJailKey();
        if (Inventory.find(jailKey).length > 0) {
            PathingUtil.walkToArea(MALE_SLAVE_AREA);
            cQuesterV2.status = "Talking to Male slave";
            General.println("[Debug]: " + cQuesterV2.status);
            if (NpcChat.talkToNPC("Male slave")) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation(SLAVE_CHAT_1);
                cQuesterV2.status = "Waiting on guards to move...";
                General.println("[Debug]: " + cQuesterV2.status);
                if (Timer.waitCondition(() ->
                        Query.npcs().inArea(Area.fromRadius(MyPlayer.getPosition(), 2))
                                .nameContains("Guard").stream().findAny().isEmpty(), 45000, 60000)) {
                    NPCInteraction.handleConversation(slave6);
                    NPCInteraction.handleConversation(slave8);
                    NPCInteraction.handleConversation();
                }
            }
        }
    }

    public void step4a() {
        if (Inventory.find(jailKey).length < 1)
            getJailKey();

        if (Inventory.find(jailKey).length > 0) {
            PathingUtil.walkToArea(MALE_SLAVE_AREA);
            cQuesterV2.status = "Talking to Male slave";
            General.println("[Debug]: " + cQuesterV2.status);
            if (NpcChat.talkToNPC("Male slave")) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation();
                cQuesterV2.status = "Waiting on guards to move...";
                General.println("[Debug]: " + cQuesterV2.status);

                if (Timer.waitCondition(() ->
                        Query.npcs().inArea(Area.fromRadius(MyPlayer.getPosition(), 2))
                                .nameContains("Guard").stream().findAny().isEmpty(), 45000, 60000)) {
                    NPCInteraction.handleConversation(slave6);
                    NPCInteraction.handleConversation(slave8);
                    NPCInteraction.handleConversation();
                }
            }
        }
    }

    public void step4b() {
        cQuesterV2.status = "Going to mine entrance";
        General.println("[Debug]: " + cQuesterV2.status);
        if (PathingUtil.localNavigation(MINE_ENTRANCE.getRandomTile()))
            Timer.abc2WaitCondition(() -> MINE_ENTRANCE.contains(Player.getPosition()), 8000, 10000);

        if (MINE_ENTRANCE.contains(Player.getPosition())) {
            if (Utils.clickObj("Mine door entrance", "Open", false))
                Timer.abc2WaitCondition(() -> INSIDE_MINE_DOOR.contains(Player.getPosition()), 8000, 10000);
        }
    }


    public void goToEndOfMine() {
        cQuesterV2.status = "Going to end of mine";
        General.println("[Debug]: " + cQuesterV2.status);
        PathingUtil.localNavigation(END_OF_MINE);
        Timer.abc2WaitCondition(() -> END_OF_MINE.contains(Player.getPosition()), 45000, 60000);
        if (NpcChat.talkToNPC("Guard")) {
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation(GUARD_ARRAY);
        }
    }

    public void leaveMine() {
        if (END_OF_MINE.contains(Player.getPosition())) {
            cQuesterV2.status = "Leaving mine";
            General.println("[Debug]: " + cQuesterV2.status);
            PathingUtil.localNavigation(INSIDE_MINE_DOOR);
        }
        if (INSIDE_MINE_DOOR.contains(Player.getPosition())) {
            if (Utils.clickObj("Mine door entrance", "Open", false))
                Timer.abc2WaitCondition(() -> MINE_ENTRANCE.contains(Player.getPosition()), 12000, 14000);
        }
    }

    public void goToAlShabim() {
        if (!ALSHABIM_AREA.contains(Player.getPosition())) {
            cQuesterV2.status = "Going to Al Shabim";
            General.println("[Debug]: " + cQuesterV2.status);
            PathingUtil.walkToArea(ALSHABIM_AREA);
        }
        if (ALSHABIM_AREA.contains(Player.getPosition())) {
            if (NpcChat.talkToNPC("Al Shabim")) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation("I am looking for a pineapple.");
                NPCInteraction.handleConversation("Yes, I'm interested.");
                NPCInteraction.handleConversation();
            }
        }
    }

    public void getPlans() {
        cQuesterV2.status = "Going to get plans";
        General.println("[Debug]: " + cQuesterV2.status);
        if (!WATER_BOWL_ROOM.contains(Player.getPosition()) && !SECOND_FLOOR.contains(Player.getPosition()))
            PathingUtil.walkToArea(WATER_BOWL_ROOM);

        if (WATER_BOWL_ROOM.contains(Player.getPosition()))
            if (Utils.clickObj("Ladder", "Climb-up", false))
                Timer.abc2WaitCondition(() -> SECOND_FLOOR.contains(Player.getPosition()), 10000, 12000);

        if (SECOND_FLOOR.contains(Player.getPosition())) {
            if (Utils.clickObj(2678, "Search", false)) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation();
                Utils.modSleep();
            }
            if (NpcChat.talkToNPC("Captain Siad")) {
                Utils.modSleep();
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation(CAPTAIN_SIAD); // leave hte following lines here or it doesn't work
                NPCInteraction.handleConversation("You seem to have a lot of books!");
                NPCInteraction.handleConversation("So, you're interested in sailing?");
                NPCInteraction.handleConversation("I could tell by the cut of your jib.");
                NPCInteraction.handleConversation();
                Utils.shortSleep();
            }

            cQuesterV2.status = "Looting chest";
            General.println("[Debug]: " + cQuesterV2.status);
            if (Inventory.isFull())
                EatUtil.eatFood();

            if (Utils.useItemOnObject(CHEST_KEY, "Chest")) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation();
                Timer.abc2WaitCondition(() -> Inventory.find(PLANS).length > 0, 8000, 12000);
            }
        }
    }

    public void returnToAlShabim() {
        if (SECOND_FLOOR.contains(Player.getPosition()))
            if (Utils.clickObj("Ladder", "Climb-down", false))
                Timer.abc2WaitCondition(() -> WATER_BOWL_ROOM.contains(Player.getPosition()), 10000, 12000);


        cQuesterV2.status = "Going to Al Shabim";
        General.println("[Debug]: " + cQuesterV2.status);
        PathingUtil.walkToArea(ALSHABIM_AREA);

        if (ALSHABIM_AREA.contains(Player.getPosition())) {
            if (Utils.useItemOnNPC(PLANS, "Al Shabim")) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation("Yes, I'm very interested.");
                NPCInteraction.handleConversation("Yes, I'm kind of curious.");
                NPCInteraction.handleConversation();
            }
        }
    }

    public void makeDart() {
        if (!ANVIL_AREA.contains(Player.getPosition())) {
            PathingUtil.walkToTile(new RSTile(3169, 3045, 0), 2, false);
            General.sleep(General.random(5000, 7000));
            if (Utils.clickObj("Tent door", "Walk-through", false)) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation();
                Timer.abc2WaitCondition(() -> ANVIL_AREA.contains(Player.getPosition()), 8000, 10000);
            }
        }
        if (ANVIL_AREA.contains(Player.getPosition()) && bronzeBar.check()) {
            cQuesterV2.status = "Making Dart";
            General.println("[Debug]: " + cQuesterV2.status);
            if (Utils.useItemOnObject(ItemID.BRONZE_BAR, "An experimental anvil")) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation("Yes. I'd like to try.");
                NPCInteraction.handleConversation();
                General.sleep(General.random(6000, 10000));
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation();
                Timer.abc2WaitCondition(() -> Inventory.find(prototypeDartTip).length > 1, 8000, 10000);
            }
        }
    }

    public void addFeatherToDartTip() {
        if (Inventory.find(prototypeDartTip).length > 0) {
            cQuesterV2.status = "Making Dart";
            General.println("[Debug]: " + cQuesterV2.status);
            if (Utils.useItemOnItem(feather, prototypeDartTip)) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation();
                Timer.abc2WaitCondition(() -> Inventory.find(prototypeDart).length > 0, 6000, 8000);
            }
        }
    }


    public void escapePrison() {
        if (JAIL_AREA.contains(Player.getPosition())) {
            if (Inventory.find(jailKey).length > 0) {
                cQuesterV2.status = "Need to Escape Prison";
                General.println("[Debug]: " + cQuesterV2.status);
                RSObject[] cellDoor = Objects.findNearest(20, 2689);
                if (cellDoor.length > 0) {
                    if (AccurateMouse.click(cellDoor[0], "Open"))
                        Timer.waitCondition(() -> !JAIL_AREA.contains(Player.getPosition()), 7000, 9000);
                }
            } else {
                cQuesterV2.status = "Need to Escape Prison";
                General.println("[Debug]: " + cQuesterV2.status);
                RSObject[] cellWallWindow = Objects.findNearest(20, 2679);
                if (cellWallWindow.length > 0) {
                    AccurateMouse.click(cellWallWindow[0], "Bend");
                    General.sleep(General.random(6500, 9000));
                    AccurateMouse.click(cellWallWindow[0], "Escape");
                    Timer.waitCondition(() -> OUTSIDE_JAIL_WINDOW.contains(Player.getPosition()), 7000, 9000);
                }
            }
            if (OUTSIDE_JAIL_WINDOW.contains(Player.getPosition())) {
                RSObject[] rock = Objects.findNearest(20, 18871);
                if (rock.length > 0) {
                    AccurateMouse.click(rock[0], "Climb");
                    Timer.waitCondition(() -> BEFORE_CLIFF.contains(Player.getPosition()), 12000, 15000);
                }
            }
            if (BEFORE_CLIFF.contains(Player.getPosition())) {
                RSObject[] rock = Objects.findNearest(20, 18923);
                if (rock.length > 0) {
                    AccurateMouse.click(rock[0], "Climb-up");
                    Timer.waitCondition(() -> ON_CLIFF.contains(Player.getPosition()), 12000, 15000);
                }
            }
            if (ON_CLIFF.contains(Player.getPosition())) {
                RSObject[] rock = Objects.findNearest(20, 18924);
                if (rock.length > 0) {
                    if (AccurateMouse.click(rock[0], "Climb-up"))
                        Timer.waitCondition(() -> OFF_CLIFF.contains(Player.getPosition()), 12000, 15000);
                }
            }
        }
    }

    public void goToAlShabimThree() {
        cQuesterV2.status = "Going to Al Shabim";
        General.println("[Debug]: " + cQuesterV2.status);

        if (ANVIL_AREA.contains(Player.getPosition()))
            if (Utils.clickObj("Tent door", "Walk-through"))
                Timer.abc2WaitCondition(() -> !ANVIL_AREA.contains(Player.getPosition()), 8000, 12000);

        PathingUtil.walkToArea(ALSHABIM_AREA);

        if (NpcChat.talkToNPC("Al Shabim")) {
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation();
            General.sleep(General.random(2000, 4000));
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation();
            NPCInteraction.handleConversation();
            Utils.longSleep();
        }
    }

    public void goToMineAndTalkToGuard() {
        cQuesterV2.status = "Going to Mine";
        General.println("[Debug]: " + cQuesterV2.status);
        if (!MINE_ENTRANCE.contains(Player.getPosition()) && !WHOLE_LEFT_SIDE_OF_MINE.contains(Player.getPosition())
                && !INSIDE_MINE_DOOR.contains(Player.getPosition())
                && !END_OF_MINE.contains(Player.getPosition())) {
            PathingUtil.walkToArea(MINE_ENTRANCE);

            if (Utils.clickObj("Mine door entrance", "Open"))
                Timer.abc2WaitCondition(() -> INSIDE_MINE_DOOR.contains(Player.getPosition()), 8000, 12000);
        }

        if (INSIDE_MINE_DOOR.contains(Player.getPosition())) {
            cQuesterV2.status = "Going to end of mine";
            General.println("[Debug]: " + cQuesterV2.status);
            if (PathingUtil.localNavigation(END_OF_MINE))
                Timer.abc2WaitCondition(() -> END_OF_MINE.contains(Player.getPosition()), 55000, 70000);
        }

        if (Inventory.find(PINEAPPLE_ID).length > 0) {
            if (NpcChat.talkToNPC("Guard")) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation();
                NPCInteraction.handleConversation();
                Utils.longSleep();
            }
        }
    }

    public void getBarrel() {
        if (END_OF_MINE.contains(Player.getPosition()) && Inventory.find(PINEAPPLE_ID).length < 1)
            if (Utils.clickObj(2699, "Walk through"))
                Timer.slowWaitCondition(() -> AFTER_MINE_CAVE.contains(Player.getPosition()), 9000, 13000);

        if (AFTER_MINE_CAVE.contains(Player.getPosition()))
            PathingUtil.localNavigation(BARREL_AREA);

        if (Inventory.find(BARREL_ID).length < 1)
            if (Utils.clickObj(2681, "Search")) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation("Yeah, cool!");
                NPCInteraction.handleConversation();
            }
    }

    public void sendBarrel() {
        getBarrel();
        if (Inventory.find(BARREL_ID).length > 0) {
            if (Utils.clickObj(2684, "Search")) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation("Yes, of course.");
                NPCInteraction.handleConversation();
                Utils.modSleep();
                if (Player.isMoving())
                    Timer.abc2WaitCondition(() -> Game.getSetting(197) == 18, 20000, 35000);
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation();
            }
        }
    }

    public void getAna() {
        if (Inventory.find(ItemID.ANA_IN_A_BARREL).length == 0) {
            cQuesterV2.status = "Going to Ana";
            General.println("[Debug]: " + cQuesterV2.status);
            if (AFTER_CART_RIDE_1.contains(Player.getPosition())) {
                Walking.walkPath(PATH_TO_ANNA);
                Timer.abc2WaitCondition(() -> ANNA_AREA.contains(Player.getPosition()), 12000, 16000);
            }
            cQuesterV2.status = "Getting Ana";
            if (Utils.useItemOnNPC(BARREL_ID, "Ana")) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation();
            }
        }
        if (Inventory.find(ItemID.ANA_IN_A_BARREL).length > 0) {
            cQuesterV2.status = "Going to Tracks";
            if (PathingUtil.localNavigation(AFTER_CART_RIDE_1))
                Timer.slowWaitCondition(() -> AFTER_CART_RIDE_1.contains(Player.getPosition()), 10000, 13000);
            else if (PathingUtil.blindWalkToArea(AFTER_CART_RIDE_1)) {
                Timer.slowWaitCondition(() -> AFTER_CART_RIDE_1.contains(Player.getPosition()), 10000, 13000);
            }
        }
    }

    public void sendAnaDownTrack() {
        //failsafe if we are still in ana area
        if (ANNA_AREA.contains(Player.getPosition())) {
            cQuesterV2.status = "Going to Tracks";
            if (PathingUtil.localNavigation(AFTER_CART_RIDE_1))
                Timer.slowWaitCondition(() -> AFTER_CART_RIDE_1.contains(Player.getPosition()), 10000, 13000);
            else if (PathingUtil.blindWalkToArea(AFTER_CART_RIDE_1)) {
                Timer.slowWaitCondition(() -> AFTER_CART_RIDE_1.contains(Player.getPosition()), 10000, 13000);
            }
        }
        cQuesterV2.status = "Sending Ana down track";
        General.println("[Debug]: " + cQuesterV2.status);
        if (Utils.useItemOnObject(ANA_IN_BARREL, 2684))
            Timer.waitCondition(() -> Game.isInInstance(), 25000, 30000);

        if (Game.isInInstance())
            Waiting.waitUntil(25000, ()-> !Game.isInInstance());
    }

    public void getAnaFromBarrel() {
        if (AFTER_CART_RIDE_1.contains(Player.getPosition())) {
            cQuesterV2.status = "Going Ana.";
            General.println("[Debug]: " + cQuesterV2.status);
            if (Utils.clickObj(2684, "Search")) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation("Yes, of course.");
                NPCInteraction.handleConversation();
                Utils.modSleep();
                if (Player.isMoving())
                    Timer.abc2WaitCondition(() -> BARREL_AREA.contains(Player.getPosition()), 35000, 46000);
            }
        }
        if (BARREL_AREA.contains(Player.getPosition())) {
            cQuesterV2.status = "Getting Ana";
            General.println("[Debug]: " + cQuesterV2.status);
            if (Utils.clickObj(2681, "Search")) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation();
            }
            cQuesterV2.status = "Going to Winch";
            General.println("[Debug]: " + cQuesterV2.status);
            if (PathingUtil.localNavigation(WINCH_AREA.getRandomTile()))
                Timer.abc2WaitCondition(() -> WINCH_AREA.contains(Player.getPosition()), 10000, 15000);

        }
    }

    public void putAnaOnWinch() {
        if (!WINCH_AREA.contains(Player.getPosition()) && Inventory.find(ANA_IN_BARREL).length > 0) {
            cQuesterV2.status = "Going to Winch";
            General.println("[Debug]: " + cQuesterV2.status);
            PathingUtil.localNavigation(new RSTile(3292, 9422,0));
        }
        if (WINCH_AREA.contains(Player.getPosition())) {
            cQuesterV2.status = "Putting Ana on winch";
            General.println("[Debug]: " + cQuesterV2.status);
            if (Utils.useItemOnObject(ANA_IN_BARREL, 18951)) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation("Yes please.");
                NPCInteraction.handleConversation("I said you were very gregarious!");
                NPCInteraction.handleConversation();
            }
        }
    }

    public void returnToSurface() {
        if (WINCH_AREA.contains(Player.getPosition())) {
            cQuesterV2.status = "Going to Surface";
            General.println("[Debug]: " + cQuesterV2.status);

            RSObject[] obj = Objects.findNearest(40, 2699);
            if (obj.length > 0) {
                PathingUtil.localNavigation(obj[0].getPosition());
                Utils.modSleep();
            }
        }
        if (AFTER_MINE_CAVE.contains(Player.getPosition())) {
            if (Utils.clickObj(2699, "Walk through"))
                Timer.abc2WaitCondition(() -> END_OF_MINE.contains(Player.getPosition()), 20000, 22000);

        }
        if (END_OF_MINE.contains(Player.getPosition())) {
            cQuesterV2.status = "Leaving mine";
            General.println("[Debug]: " + cQuesterV2.status);
            if (PathingUtil.localNavigation(INSIDE_MINE_DOOR.getRandomTile()))
                Timer.waitCondition(() -> INSIDE_MINE_DOOR.contains(Player.getPosition()), 30000, 45000);
        }

        if (INSIDE_MINE_DOOR.contains(Player.getPosition())) {
            if (Utils.clickObj("Mine door entrance", "Open"))
                Timer.slowWaitCondition(() -> MINE_ENTRANCE.contains(Player.getPosition()), 8000, 12000);
        }
    }

    public void getAnaFromWinch() {
        returnToSurface();

        if (MINE_ENTRANCE.contains(Player.getPosition())) {
            PathingUtil.walkToTile(new RSTile(3280, 3018, 0), 2, false);
            Timer.slowWaitCondition(() -> OUTSIDE_WINCH.contains(Player.getPosition()), 12000, 15000);
        }
        if (OUTSIDE_WINCH.contains(Player.getPosition())) {
            if (Utils.clickObj(18888, "Operate")) {
                Timer.abc2WaitCondition(() -> Player.getAnimation() != 5054, 15000, 20000);
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation();
                Utils.modSleep();
            }
            if (Utils.clickObj(18963, "Search")) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation();
                Utils.modSleep();
            }
        }
    }

    public void putAnaOnCart() {
        if (Inventory.find(ANA_IN_BARREL).length > 0) {
            cQuesterV2.status = "Putting Ana in cart";
            General.println("[Debug]: " + cQuesterV2.status);
            RSObject[] obj = Objects.findNearest(40, 2682);
            if (obj.length > 0) {
                PathingUtil.localNavigation(obj[0].getPosition());
                Timer.abc2WaitCondition(() -> obj[0].getPosition().distanceTo(Player.getPosition()) < 2, 18000, 22000);
                if (Utils.useItemOnObject(ANA_IN_BARREL, 2682)) {
                    NPCInteraction.waitForConversationWindow();
                    NPCInteraction.handleConversation();
                    Utils.modSleep();
                }
            }
        }
    }

    String[] cartArray = {
            "Nice cart.",
            "One wagon wheel says to the other, 'I'll see you around'.",
            "'One good turn deserves another'",
            "Fired... no, shot perhaps!",
            "In for a penny in for a pound.",
            "Well, you see, it's like this...",
            "Prison riot in ten minutes, get your cart out of here!",
            "You can't leave me here, I'll get killed!"
    };


    public void step20() {
        if (NpcChat.talkToNPC(4653)) {
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation(cartArray);
            if (NPCInteraction.isConversationWindowUp()) {
                NPCInteraction.handleConversation("One wagon wheel says to the other, 'I'll see you around'.");
                NPCInteraction.handleConversation("'One good turn deserves another'");
                NPCInteraction.handleConversation("Fired... no, shot perhaps!");
                NPCInteraction.handleConversation("In for a penny in for a pound.");
                NPCInteraction.handleConversation("Well, you see, it's like this...");
                NPCInteraction.handleConversation("Prison riot in ten minutes, get your cart out of here!");
                NPCInteraction.handleConversation("You can't leave me here, I'll get killed!");
                NPCInteraction.handleConversation();
            }

        }
        if (Utils.clickObj("Wooden cart", "Search")) {
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation("Yes, I'll get on.");
            NPCInteraction.handleConversation();
            Timer.abc2WaitCondition(() -> LANDING_AREA.contains(Player.getPosition()), 22000, 27000);
        }
    }

    public void finishQuest() {
        cQuesterV2.status = "Finishing";
        General.println("[Debug]: " + cQuesterV2.status);
        PathingUtil.walkToArea(START_AREA);
        if (NpcChat.talkToNPC("Irena")) {
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation();
            General.sleep(General.random(1500, 2500));
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation();
        }
    }

    public void claimRewards() {
        cQuesterV2.status = "Claiming rewards";
        General.println("Rewards: " + skill1 + " & " + skill2);
        General.println("[Debug]: " + cQuesterV2.status);
        if (!NPCInteraction.isConversationWindowUp()) {
            if (NpcChat.talkToNPC("Irena"))
                NPCInteraction.waitForConversationWindow();
        } else {
            NPCInteraction.handleConversation(skill1);
            InterfaceUtil.clickInterfaceText(219, skill1);
            NPCInteraction.handleConversation();
            InterfaceUtil.clickInterfaceText(219, skill1);
        }
        if (NpcChat.talkToNPC("Irena")) {
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation(skill2);
            InterfaceUtil.clickInterfaceText(219, skill2);
            NPCInteraction.handleConversation();
        }
    }


    public void bank2() {
        if (Utils.checkAllRequirements(bronzeBar, hammer, shantayPass)) {
            cQuesterV2.status = "Banking for Items";
            General.println("[Debug]: " + cQuesterV2.status);
            BankManager.open(true);
            BankManager.checkEquippedGlory();
            Banking.depositAll();
            BankManager.withdraw(1, true, hammer.getId());
            BankManager.withdraw(3, true, bronzeBar.getId());
            BankManager.withdraw(3, true, waterskin4);
            BankManager.withdraw(1, true, ItemID.STAMINA_POTION[0]);
            BankManager.withdraw(10, true, lobster);
            BankManager.withdraw(1, true, metalKey);
            BankManager.withdraw(1, true, CHEST_KEY);
            BankManager.withdraw(3, true, ItemID.SHANTAY_PASS);
            BankManager.withdraw(3, true, ItemID.BRONZE_BAR);
            BankManager.withdraw(3, true, jailKey);
            BankManager.withdraw(50, true, feather);
            BankManager.withdraw(1, true, PLANS);
            BankManager.withdraw(1, true, hammer.getId());
            BankManager.close(true);
        }
    }

    int GAME_SETTING = 197;

    @Override
    public void execute() {
        if (!checkRequirements()) {
            cQuesterV2.taskList.remove(this);
            return;
        }
        // determineRewards();
        int gameSetting = QuestVarPlayer.QUEST_THE_TOURIST_TRAP.getId();
        if (Game.getSetting(gameSetting) == 0) {
            buyItems();
            trainFletching();
            getItems();
            startQuest();
        }
        if (Game.getSetting(gameSetting) == 1) {
            talkToCaptain();
        }
        if (Game.getSetting(gameSetting) == 3) {
            talkToCaptain();
        }
        if (Game.getSetting(gameSetting) == 4) {
            step3();
        }
        if (Game.getSetting(gameSetting) == 5) {
            escapePrison();
            getNewKey();
            step4();
        }
        if (Game.getSetting(gameSetting) == 6) {
            escapePrison();
            getNewKey();
            step4a();
        }
        if (Game.getSetting(gameSetting) == 8) {
            escapePrison();
            getNewKey();
            step4b();
        }
        if (Game.getSetting(gameSetting) == 9) {
            escapePrison();
            getNewKey();
            goToEndOfMine();
        }
        if (Game.getSetting(gameSetting) == 10) {
            escapePrison();
            getNewKey();
            leaveMine();
            goToAlShabim();
        }
        if (Game.getSetting(gameSetting) == 11) {
            escapePrison();
            getNewKey();
            bank2();

            getPlans();
        }
        if (Game.getSetting(gameSetting) == 12) {
            escapePrison();
            bank2();
            returnToAlShabim();
        }
        if (Game.getSetting(gameSetting) == 13) {
            makeDart();
        }
        if (Game.getSetting(gameSetting) == 14) {
            addFeatherToDartTip();
        }
        if (Game.getSetting(gameSetting) == 15) { // can make darts at the end of this step!
            goToAlShabimThree();
        }
        if (Game.getSetting(gameSetting) == 16) {
            getNewKey();
            goToMineAndTalkToGuard();
        }
        if (Game.getSetting(gameSetting) == 17) {
            sendBarrel();
        }
        if (Game.getSetting(gameSetting) == 18) {
            getAna();
        }
        if (Game.getSetting(gameSetting) == 19) {
            sendAnaDownTrack();
        }
        if (Game.getSetting(gameSetting) == 20) {
            getAnaFromBarrel();
        }
        if (Game.getSetting(gameSetting) == 21) {
            putAnaOnWinch();
        }
        if (Game.getSetting(gameSetting) == 22) {
            getAnaFromWinch();
        }
        if (Game.getSetting(gameSetting) == 24) {
            putAnaOnCart();
        }
        if (Game.getSetting(gameSetting) == 25) {
            step20();
        }
        if (Game.getSetting(gameSetting) == 26) {
            finishQuest();
        }
        if (Game.getSetting(gameSetting) == 27) {
            claimRewards();
        }
        if (Game.getSetting(gameSetting) == 28) {
            claimRewards();
        }
        if (Game.getSetting(gameSetting) == 29) {
            claimRewards();
        }
        if (Game.getSetting(gameSetting) == 30) {
            Utils.closeQuestCompletionWindow();
            NPCInteraction.handleConversation();
            cQuesterV2.taskList.remove(TouristTrap.get());
        }

    }


    @Override
    public Priority priority() {
        return Priority.LOW;
    }

    @Override
    public boolean validate() {
        return cQuesterV2.taskList.get(0).equals(TouristTrap.get());
    }

    @Override
    public String questName() {
        return "Tourist Trap (" + Game.getSetting(197) + ")";
    }

    @Override
    public boolean checkRequirements() {
        return Skills.SKILLS.FLETCHING.getActualLevel() >= 10 &&
                Skills.SKILLS.SMITHING.getActualLevel() >= 20;
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
        return Quest.THE_TOURIST_TRAP.getState().equals(Quest.State.COMPLETE);
    }
}
