package scripts.QuestPackages.WitchsHouse;

import dax.walker.utils.AccurateMouse;
import dax.walker_engine.interaction_handling.NPCInteraction;
import org.tribot.api.Clicking;
import org.tribot.api.General;
import org.tribot.api2007.*;
import org.tribot.api2007.types.*;
import org.tribot.script.sdk.Log;
import org.tribot.script.sdk.Quest;
import org.tribot.script.sdk.Waiting;
import scripts.*;
import scripts.GEManager.Exchange;
import scripts.GEManager.GEItem;
import scripts.QuestPackages.DragonSlayer.DragonSlayer;
import scripts.QuestSteps.BuyItemsStep;
import scripts.QuestSteps.QuestTask;
import scripts.Requirements.InventoryRequirement;
import scripts.Requirements.ItemReq;
import scripts.Requirements.ItemRequirement;
import scripts.Requirements.Requirement;
import scripts.Tasks.Priority;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class WitchsHouse implements QuestTask {


    private static WitchsHouse quest;

    public static WitchsHouse get() {
        return quest == null ? quest = new WitchsHouse() : quest;
    }


    public int faladorTab = 8009;
    public int houseKey = 2409;
    public int magnet = 2410;
    public int shedKey = 2411;
    public int BALL = 2407;
    public int CLOSED_CUPBOARD = 2868;
    public int OPEN_CUPBOARD = 2869;

    public final RSTile boyTile = new RSTile(2927, 3455, 0);
    public final RSTile frontDoor = new RSTile(2900, 3473, 0);
    public final RSTile cupboardTile = new RSTile(2899, 9874, 0);
    public final RSTile mouseTile = new RSTile(2902, 3466, 0);
    public final RSTile bush1 = new RSTile(2903, 3460, 0);
    public final RSTile bush2 = new RSTile(2909, 3460, 0);
    public final RSTile bush3 = new RSTile(2917, 3460, 0);
    public final RSTile bush4 = new RSTile(2925, 3460, 0);
    public final RSTile bush5 = new RSTile(2933, 3466, 0);
    public final RSTile bush6 = new RSTile(2927, 3466, 0);
    public final RSTile bush7 = new RSTile(2920, 3466, 0);
    public final RSTile fountainTile = new RSTile(2910, 3468, 0);
    public final RSTile bush1Return = new RSTile(2914, 3466, 0);
    public final RSTile bush2Return = new RSTile(2921, 3466, 0);
    public final RSTile bush3Return = new RSTile(2928, 3466, 0);
    public final RSTile doorToShed = new RSTile(2933, 3463, 0);
    public final RSTile combatSafeTile = new RSTile(2936, 3459, 0);

    RSArea LARGE_START_AREA = new RSArea(new RSTile(2932, 3454, 0), new RSTile(2924, 3458, 0));
    RSArea SMALL_START_AREA = new RSArea(new RSTile(2926, 3457, 0), new RSTile(2930, 3454, 0));
    RSArea bush1Area = new RSArea(new RSTile(2904, 3461, 0), new RSTile(2901, 3459, 0));
    RSArea bush2Area = new RSArea(new RSTile(2910, 3461, 0), new RSTile(2907, 3459, 0));
    RSArea bush3Area = new RSArea(new RSTile(2918, 3461, 0), new RSTile(2915, 3459, 0));
    RSArea bush4Area = new RSArea(new RSTile(2923, 3461, 0), new RSTile(2926, 3459, 0));
    RSArea bush5Area = new RSArea(new RSTile(2931, 3466, 0), new RSTile(2933, 3459, 0));
    RSArea bush6Area = new RSArea(new RSTile(2929, 3466, 0), new RSTile(2926, 3464, 0));
    RSArea bush7Area = new RSArea(new RSTile(2922, 3465, 0), new RSTile(2919, 3466, 0));
    RSArea fountainArea = new RSArea(new RSTile(2915, 3465, 0), new RSTile(2908, 3471, 0));
    RSArea eastTurnArea = new RSArea(new RSTile(2931, 3462, 0), new RSTile(2928, 3464, 0));

    RSArea postGate = new RSArea(new RSTile(2902, 9870, 0), new RSTile(2897, 9877, 0));
    RSArea preGate = new RSArea(new RSTile(2903, 9870, 0), new RSTile(2908, 9877, 0));
    RSArea inFrontOfHouse = new RSArea(new RSTile(2900, 3473, 0), new RSTile(2899, 3472, 0));
    RSArea insideHouse = new RSArea(new RSTile(2907, 3466, 0), new RSTile(2901, 3476, 0));
    RSArea[] outsideArea = {
            new RSArea(new RSTile(2900, 3465, 0), new RSTile(2933, 3459, 0)),
            new RSArea(new RSTile(2933, 3466, 0), new RSTile(2908, 3475, 0))
    };
    RSArea mouseRoom = new RSArea(new RSTile(2903, 3466, 0), new RSTile(2900, 3467, 0));

    public RSNPC[] witch = NPCs.findNearest(3995);
    public RSNPC[] experiment = NPCs.findNearest("Witch's experiment");


    ArrayList<GEItem> itemsToBuy = new ArrayList<GEItem>(
            Arrays.asList(
                    new GEItem(ItemID.CHEESE, 5, 300),
                    new GEItem(ItemID.LEATHER_GLOVES, 1, 300),
                    new GEItem(ItemID.FALADOR_TELEPORT, 6, 50),
                    new GEItem(ItemID.LOBSTER, 15, 50),
                    new GEItem(ItemID.STAFF_OF_AIR, 1, 50),
                    new GEItem(ItemID.MIND_RUNE, 200, 20),
                    new GEItem(ItemID.EARTH_RUNE, 600, 20),
                    new GEItem(ItemID.FIRE_RUNE, 600, 20),
                    new GEItem(ItemID.BLUE_WIZARD_HAT, 1, 20),
                    new GEItem(ItemID.AMULET_OF_GLORY[2], 1, 20),
                    new GEItem(ItemID.ZAMORAK_MONK_BOTTOM, 1, 70),
                    new GEItem(ItemID.ZAMORAK_MONK_TOP, 1, 70),
                    new GEItem(ItemID.STAMINA_POTION[0], 1, 15),
                    new GEItem(ItemID.RING_OF_WEALTH[0], 1, 25)
            )
    );


    BuyItemsStep buyStep = new BuyItemsStep(itemsToBuy);


    public void buyItems() {
        cQuesterV2.status = "Buying Items";
        buyStep.buyItems();
    }

    public void getItems() {
        cQuesterV2.status = "Getting Items";
        General.println("[Debug]: " + cQuesterV2.status);
        General.sleep(200);
        BankManager.open(true);
        BankManager.depositEquipment();
        BankManager.checkEquippedGlory();
        BankManager.depositAll(true);
        BankManager.withdraw(5, true,
                ItemID.CHEESE);
        BankManager.withdraw(1, true,
                ItemID.LEATHER_GLOVES);
        Utils.equipItem(ItemID.LEATHER_GLOVES);
        BankManager.withdraw(1, true,
                ItemID.BLUE_WIZARD_HAT);
        BankManager.withdraw(1, true,
                ItemID.ZAMORAK_MONK_TOP);
        BankManager.withdraw(1, true,
                ItemID.ZAMORAK_MONK_BOTTOM);
        Utils.equipItem(ItemID.BLUE_WIZARD_HAT);
        Utils.equipItem(ItemID.ZAMORAK_MONK_TOP);
        Utils.equipItem(ItemID.ZAMORAK_MONK_BOTTOM);
        BankManager.withdraw(4, true, ItemID.FALADOR_TELEPORT);
        BankManager.withdraw(16, true, ItemID.LOBSTER);
        BankManager.withdraw(1, true, ItemID.STAFF_OF_AIR);
        BankManager.withdraw(300, true, ItemID.MIND_RUNE);
        BankManager.withdraw(1, true,
                ItemID.RING_OF_WEALTH);
        BankManager.withdraw(1, true, houseKey);
        BankManager.withdraw(1, true,
                ItemID.STAMINA_POTION[0]);

        if (Skills.getCurrentLevel(Skills.SKILLS.MAGIC) >= 13)
            BankManager.withdraw(900, true,
                    ItemID.FIRE_RUNE);
        else
            BankManager.withdraw(600, true,
                    ItemID.EARTH_RUNE);

        Utils.modSleep();
        BankManager.close(true);
    }

    public void startQuest() {
        cQuesterV2.status = "Starting Quest";
        Utils.equipItem(
                ItemID.STAFF_OF_AIR);
        Utils.equipItem(
                ItemID.LEATHER_GLOVES);
        if (!LARGE_START_AREA.contains(Player.getPosition()))
            PathingUtil.walkToArea(SMALL_START_AREA, false);

        if (LARGE_START_AREA.contains(Player.getPosition()) && NpcChat.talkToNPC("Boy")) {
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation("What's the matter?");
            NPCInteraction.handleConversation("Ok, I'll see what I can do.", "Yes.");
            NPCInteraction.handleConversation();
            Utils.modSleep();
        }
    }

    public void enterHouse() {
        cQuesterV2.status = "Entering house";
        if ((!Equipment.isEquipped(
                ItemID.STAFF_OF_AIR) && Inventory.find(
                ItemID.STAFF_OF_AIR).length < 1) ||
                !BankManager.checkInventoryItems(
                        ItemID.CHEESE,
                        ItemID.LOBSTER,
                        ItemID.MIND_RUNE)) {
            buyItems();
            getItems();
        }
        if (!insideHouse.contains(Player.getPosition()) && !postGate.contains(Player.getPosition())
                && !preGate.contains(Player.getPosition()) && !shedArea.contains(Player.getPosition())
                && !outsideArea[0].contains(Player.getPosition()) && !outsideArea[1].contains(Player.getPosition())) {
            PathingUtil.walkToTile(frontDoor, 1, false);
            RSObject[] pottedPlant = Objects.findNearest(3, "Potted plant");
            if (Inventory.find(houseKey).length < 1) {
                if (pottedPlant.length > 0) {
                    if (AccurateMouse.click(pottedPlant[0], "Look-under")) {
                        NPCInteraction.waitForConversationWindow();
                        NPCInteraction.handleConversation();
                        Timer.abc2WaitCondition(() -> Inventory.find(houseKey).length > 0, 7000, 9000);
                    }
                }
            }
            if (Inventory.find(houseKey).length > 0) {
                if (Utils.clickObject("Door", "Open", false)) {
                    Timer.abc2WaitCondition(() -> insideHouse.contains(Player.getPosition()), 7000, 12000);
                    PathingUtil.walkToTile(new RSTile(2906, 3476, 0), 1, false);
                    Utils.modSleep();
                }
            }
        }
    }


    public void getMagnet() {
        cQuesterV2.status = "Getting Magnet";

        if (Inventory.find(houseKey).length > 0 && Inventory.find(magnet).length < 1 && Inventory.find(shedKey).length < 1) {
            if (!postGate.contains(Player.getPosition()) && !preGate.contains(Player.getPosition())) {
                PathingUtil.walkToTile(new RSTile(2906, 3476, 0), 1, false);
                if (Utils.clickObject("Ladder", "Climb-down", false))
                    Timer.abc2WaitCondition(() -> preGate.contains(Player.getPosition()), 5000, 8000);
            }
            if (preGate.contains(Player.getPosition())) {
                if (!Equipment.isEquipped(ItemID.LEATHER_GLOVES))
                    Utils.equipItem(ItemID.LEATHER_GLOVES);

                if (Utils.clickObject("Gate", "Open", false))
                    Timer.waitCondition(() -> postGate.contains(Player.getPosition()), 7000, 10000);
            }
            if (postGate.contains(Player.getPosition())) {
                if (Inventory.find(magnet).length < 1) {

                    Utils.equipItem(ItemID.STAFF_OF_AIR);

                    if (Utils.clickObject(CLOSED_CUPBOARD, "Open", false)) {
                        Timer.waitCondition(() -> Objects.findNearest(20, OPEN_CUPBOARD).length > 0, 5000, 8000);
                        General.sleep(General.random(300, 1000));
                    }
                    if (Utils.clickObject(OPEN_CUPBOARD, "Search", false)) {
                        NPCInteraction.waitForConversationWindow();
                        NPCInteraction.handleConversation();
                        Timer.abc2WaitCondition(() -> Inventory.find(magnet).length > 0, 7000, 1000);
                    }
                }
            }
        }
    }

    RSTile cheeseDropTile = new RSTile(2903, 3466, 0);

    public void GoToMouse() {
        if (!mouseRoom.contains(Player.getPosition())) {
            cQuesterV2.status = "Going to mouse";
            if (Inventory.find(magnet).length > 0) {
                if (postGate.contains(Player.getPosition())) {
                    General.println("[Debug]: Going upstairs - navigating gate");
                    Utils.clickObject("Gate", "Open", false);
                    Timer.waitCondition(() -> preGate.contains(Player.getPosition()), 7000, 9000);
                }
                if (preGate.contains(Player.getPosition())) {
                    General.println("[Debug]: Going upstairs - navigating ladder");
                    if (Utils.clickObject("Ladder", "Climb-up", false))
                        Timer.abc2WaitCondition(() -> insideHouse.contains(Player.getPosition()), 7000, 9000);
                }
                if (insideHouse.contains(Player.getPosition())) {
                    General.println("[Debug]: Going to mouse");
                    if (PathingUtil.localNavigation(cheeseDropTile))
                        PathingUtil.movementIdle();
                }
            }
        }
    }

    public void baitMouse() {
        if (mouseRoom.contains(Player.getPosition())) {
            cQuesterV2.status = "Baiting mouse";

            Camera.setCameraRotation(General.random(195, 280));
            RSItem[] invCheese = Inventory.find(ItemID.CHEESE);
            if (invCheese.length > 0 && Inventory.find(magnet).length > 0) {
                if (Clicking.click("Drop", invCheese[0]))
                    //if (Utils.useItemOnObject(ItemID.CHEESE, "Mouse hole"))
                    Timer.waitCondition(() -> NPCs.findNearest("Mouse").length > 0, 5000, 7000);

                if (Utils.useItemOnNPC(magnet, "Mouse")) {
                    NPCInteraction.waitForConversationWindow();
                    NPCInteraction.handleConversation();
                }

                if (Utils.clickObject(2862, "Open", false)) {
                    General.sleep(General.random(3000, 5000));
                    Walking.blindWalkTo(new RSTile(2902, 3460, 0));
                }
            }
        }
    }

    public void goOutsideHouse() {
        PathingUtil.walkToTile(new RSTile(2901, 3461, 0), 2, false);
    }


    public void navigateToFountain() {
        while (Inventory.find(shedKey).length < 1 && (outsideArea[0].contains(Player.getPosition()) || outsideArea[1].contains(Player.getPosition()))) {
            General.sleep(100);
            witch = NPCs.findNearest(3995);
            if (witch.length > 0) {
                if (witch[0].getPosition().distanceTo(Player.getPosition()) > 9) {
                    navigateToKey();
                }
            } else if (NPCs.findNearest("Witch").length < 1)
                navigateToKey();

            else
                General.println("[Debug]: Waiting");
        }
    }


    public void goToShed() {
        while (Inventory.find(shedKey).length > 0 && (outsideArea[0].contains(Player.getPosition()) || outsideArea[1].contains(Player.getPosition()))) {
            General.sleep(100);
            witch = NPCs.findNearest(3995);
            if (witch.length > 0) {
                if (witch[0].getPosition().distanceTo(Player.getPosition()) > 10 && !eastTurnArea.contains(witch[0].getPosition())) {
                    navigateToShed();
                }
            } else if (witch.length < 1 && Inventory.find(shedKey).length > 0) {
                General.println("Trigger 2");
                navigateToShed();
            } else {
                General.println("[Debug]: Waiting");
            }
        }
    }

    public void navigateToKey() {
        cQuesterV2.status = "Navigating to Shed";
        if (bush1Area.contains(Player.getPosition())) {
            General.println("[Debug]: Witch is >5 tiles away, moving to Bush 2");
            Walking.blindWalkTo(bush2);
            Timer.waitCondition(() -> bush2.equals(Player.getPosition()), 5000, 8000);
        } else if (bush2Area.contains(Player.getPosition())) {
            General.println("[Debug]: Witch is >5 tiles away, moving to Bush 3");
            Walking.blindWalkTo(bush3);
            Timer.waitCondition(() -> bush3.equals(Player.getPosition()), 5000, 8000);
        } else if (bush3Area.contains(Player.getPosition())) {
            General.println("[Debug]: Witch is >5 tiles away, moving to Bush 4");
            Walking.blindWalkTo(bush4);
            Timer.waitCondition(() -> bush4.equals(Player.getPosition()), 5000, 8000);
        } else if (bush4Area.contains(Player.getPosition())) {
            General.println("[Debug]: Witch is >5 tiles away, moving to Bush 5");
            Walking.blindWalkTo(new RSTile(2933, 3460, 0));
            General.sleep(General.random(4000, 6000));
            Walking.blindWalkTo(bush5);
            Timer.waitCondition(() -> bush5.equals(Player.getPosition()), 5000, 8000);
        } else if (bush5Area.contains(Player.getPosition())) {
            General.println("[Debug]: Witch is >5 tiles away, moving to Bush 6");
            Walking.blindWalkTo(bush6);
            Timer.waitCondition(() -> bush6.equals(Player.getPosition()), 5000, 8000);
        } else if (bush6Area.contains(Player.getPosition())) {
            General.println("[Debug]: Witch is >5 tiles away, moving to Fountain");
            Walking.blindWalkTo(bush7);
            Timer.waitCondition(() -> bush7.equals(Player.getPosition()), 5000, 8000);
        } else if (bush7Area.contains(Player.getPosition())) {
            General.println("[Debug]: Witch is >5 tiles away, moving to Fountain");
            Walking.blindWalkTo(fountainTile);
            General.sleep(General.random(4000, 7000));
        } else if (fountainArea.contains(Player.getPosition())) {
            General.println("[Debug]: Getting Key from Fountain");
            RSObject[] fountain = Objects.findNearest(20, "Fountain");

            if (fountain.length > 0) {
                if (!fountain[0].isClickable())
                    fountain[0].adjustCameraTo();

                if (AccurateMouse.click(fountain[0], "Check")) {
                    NPCInteraction.waitForConversationWindow();
                    NPCInteraction.handleConversation();
                    Timer.waitCondition(() -> Inventory.find(shedKey).length > 0, 6000, 9000);
                    Walking.blindWalkTo(bush1Return);
                }
            }
        }
    }

    RSArea fountainEndTileArea = new RSArea(new RSTile(2914, 3466, 0), new RSTile(2913, 3465, 0));
    RSArea bush2ReturnArea = new RSArea(new RSTile(2921, 3466, 0), new RSTile(2922, 3465, 0));
    RSArea shedDoorArea = new RSArea(new RSTile(2932, 3466, 0), new RSTile(2933, 3459, 0));
    RSArea shedArea = new RSArea(new RSTile(2934, 3466, 0), new RSTile(2937, 3459, 0));
    RSArea combatSafeArea = new RSArea(new RSTile(2934, 3459, 0), new RSTile(2937, 3459, 0));

    public void navigateToShed() {
        cQuesterV2.status = "Navigating to Shed";
        if (fountainArea.contains(Player.getPosition()) && !fountainEndTileArea.contains(Player.getPosition())) {
            General.println("[Debug]: Witch is >5 tiles away, moving...");
            Walking.blindWalkTo(fountainEndTileArea.getRandomTile());
            Timer.waitCondition(() -> fountainEndTileArea.contains(Player.getPosition()), 5000, 7000);
            General.sleep(General.random(2000, 3000));
        } else if (fountainEndTileArea.contains(Player.getPosition())) {
            General.println("[Debug]: Witch is >5 tiles away, moving...");
            Walking.blindWalkTo(bush2Return);
            Timer.waitCondition(() -> bush2ReturnArea.contains(Player.getPosition()), 5000, 7000);
            General.sleep(General.random(2000, 3000));
        } else if (bush7Area.contains(Player.getPosition())) {
            General.println("[Debug]: Witch is >5 tiles away, moving...");
            Walking.blindWalkTo(bush3Return);
            General.sleep(General.random(3000, 5000));
        } else if (bush6Area.contains(Player.getPosition())) {
            General.println("[Debug]: Witch is >5 tiles away, moving...");
            Walking.blindWalkTo(doorToShed);
            General.sleep(General.random(3000, 5000));
        } else if (shedDoorArea.contains(Player.getPosition())) {
            enterShed();
            Timer.waitCondition(() -> shedArea.contains(Player.getPosition()), 5000, 7000);
        }

    }

    public void enterShed() {
        if (Inventory.find(shedKey).length > 0) {
            cQuesterV2.status = "Entering Shed";
            if (shedDoorArea.contains(Player.getPosition())) {
                if (Utils.useItemOnObject(shedKey, "Door"))
                    Timer.abc2WaitCondition(() -> shedArea.contains(Player.getPosition()), 5000, 7000);
            }
        }
    }

    RSArea OUTSIDE_HOUSE_FAIL = new RSArea(new RSTile(2935, 3458, 0), new RSTile(2895, 3453, 0));


    public void failSafe() {
        if (OUTSIDE_HOUSE_FAIL.contains(Player.getPosition()) && Inventory.find(BALL).length < 1) {
            General.println("[Debug]: Retrying");
            Utils.shortSleep();
            if (Inventory.find(
                    ItemID.CHEESE).length < 1) {
                getItems();
            }
            enterHouse();
            getMagnet();
            GoToMouse();
            baitMouse();
            goOutsideHouse();
        }
    }

    int[] EXPERIMENT_IDS = {3996, 3997, 3998, 3999};

    RSTile SAFE_TILE = new RSTile(2936, 3459, 0);

    public boolean setUpAutocast() {
        if (!Equipment.isEquipped(ItemID.STAFF_OF_AIR))
            Utils.equipItem(ItemID.STAFF_OF_AIR);

        if (Equipment.isEquipped(ItemID.STAFF_OF_AIR)) {
            if (Skills.getCurrentLevel(Skills.SKILLS.MAGIC) >= 13 && Inventory.find(ItemID.FIRE_RUNE).length > 0) {
                Autocast.enableAutocast(Autocast.FIRE_STRIKE);
            } else if (Inventory.find(ItemID.EARTH_RUNE).length > 0)
                Autocast.enableAutocast(Autocast.EARTH_STRIKE);
            else {
                Log.log("[Debug]: Failed to set up autocast, ending script");
                cQuesterV2.isRunning.set(false);
                return false;
            }
        }
        return Autocast.isAutocastEnabled(Autocast.EARTH_STRIKE) ||
                Autocast.isAutocastEnabled(Autocast.FIRE_STRIKE);
    }

    public void killExperiment() {
        if (shedArea.contains(Player.getPosition())) {
            if (!setUpAutocast())
                return;


            experiment = NPCs.findNearest(EXPERIMENT_IDS);

            if (experiment.length > 0) {
                RSNPC[] secondForm = NPCs.findNearest(3997);

                if (secondForm.length > 0 && Prayer.getPrayerPoints() > 0 &&
                        Skills.getActualLevel(Skills.SKILLS.PRAYER) >= 43)
                    Prayer.enable(Prayer.PRAYERS.PROTECT_FROM_MELEE);

                else if (Prayer.isPrayerEnabled(Prayer.PRAYERS.PROTECT_FROM_MELEE))
                    Prayer.disable(Prayer.PRAYERS.PROTECT_FROM_MELEE);

                if (!SAFE_TILE.equals(Player.getPosition()) &&
                        PathingUtil.clickScreenWalk(SAFE_TILE)) {
                    cQuesterV2.status = "Going to safe tile";
                    Timer.waitCondition(() -> SAFE_TILE.equals(Player.getPosition()), 3500, 5000);

                } else {
                    RSItem[] lob = Inventory.find(ItemID.LOBSTER);
                    if (Combat.getHPRatio() < General.random(55, 66) &&
                            lob.length > 0) {
                        cQuesterV2.status = "Eating";
                        if (lob[0].click("Eat"))
                            Waiting.waitNormal(175, 75);
                    }

                    if (experiment.length > 0 && !Combat.isUnderAttack() && !experiment[0].isInCombat()) {
                        cQuesterV2.status = "Attacking Experiment";
                        if (Utils.clickNPC(experiment[0], "Attack", false))
                            Timer.waitCondition(() -> experiment[0].isInCombat(), 2500, 4000);

                    } else
                        Waiting.waitNormal(500, 150);

                }
            }
        }
    }

    public void finishQuest() {
        if (Prayer.isPrayerEnabled(Prayer.PRAYERS.PROTECT_FROM_MELEE))
            Prayer.disable(Prayer.PRAYERS.PROTECT_FROM_MELEE);

        RSGroundItem[] ball = GroundItems.find(BALL);
        if (ball.length > 0 && Inventory.find(BALL).length < 1) {
            cQuesterV2.status = "Getting Ball";
            General.println("[Debug]:" + cQuesterV2.status);
            if (ball[0].click("Take")) {
                Timer.slowWaitCondition(() -> Inventory.find(2407).length > 0, 5000, 7000);
            }
        }
        if (Inventory.find(BALL).length > 0) {
            if (!LARGE_START_AREA.contains(Player.getPosition())) {
                cQuesterV2.status = "Finishing Quest";
                General.println("[Debug]:" + cQuesterV2.status);
                PathingUtil.walkToArea(SMALL_START_AREA, false);
            }
            if (LARGE_START_AREA.contains(Player.getPosition())) {
                if (NpcChat.talkToNPC("Boy")) {
                    NPCInteraction.waitForConversationWindow();
                    NPCInteraction.handleConversation();
                }
            }
        }
    }

    @Override
    public Priority priority() {
        return Priority.LOW;
    }

    @Override
    public boolean validate() {
        return java.util.Objects.equals(cQuesterV2.taskList.get(0), WitchsHouse.get());
    }

    @Override
    public void execute() {
        if (!checkRequirements())
            return;

        if (Game.getSetting(226) == 0) {
            buyItems();
            getItems();
            startQuest();

        } else if (Game.getSetting(226) == 1) {
            enterHouse();
            getMagnet();

        } else if (Game.getSetting(226) == 2) {
            GoToMouse();
            baitMouse();
            goOutsideHouse();

        } else if (Game.getSetting(226) == 3) {
            enterShed();
            navigateToFountain();
            goToShed();
            enterShed();
            failSafe();
            killExperiment();

        } else if (Game.getSetting(226) == 4) {
            if (!shedArea.contains(Player.getPosition())) {
                enterShed();
                navigateToFountain();
                goToShed();
                failSafe();
            }
            killExperiment();

        } else if (Game.getSetting(226) == 5) {
            if (!shedArea.contains(Player.getPosition())) {
                enterShed();
                navigateToFountain();
                goToShed();
                failSafe();
            }
            killExperiment();

        } else if (Game.getSetting(226) == 6) {
            finishQuest();

        } else if (Game.getSetting(226) == 7) {
            Utils.closeQuestCompletionWindow();
            cQuesterV2.taskList.remove(this);
        }
    }

    @Override
    public String questName() {
        return "Witch's House (" + Game.getSetting(226) + ")";
    }

    @Override
    public boolean checkRequirements() {
        return Skills.SKILLS.MAGIC.getActualLevel() >= 9;
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
        return Quest.WITCHS_HOUSE.getState().equals(Quest.State.COMPLETE);
    }
}
