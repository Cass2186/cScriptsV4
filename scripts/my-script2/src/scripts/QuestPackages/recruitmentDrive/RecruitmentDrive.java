package scripts.QuestPackages.recruitmentDrive;

import dax.walker.utils.AccurateMouse;
import dax.walker_engine.interaction_handling.NPCInteraction;
import org.tribot.api.General;
import org.tribot.api2007.Interfaces;
import org.tribot.api2007.Objects;
import org.tribot.script.sdk.*;
import org.tribot.script.sdk.query.Query;
import org.tribot.script.sdk.types.*;
import scripts.*;
import scripts.QuestPackages.WitchsPotion.WitchsPotion;
import scripts.QuestSteps.QuestTask;
import scripts.Requirements.ItemRequirement;
import scripts.Requirements.Requirement;
import scripts.Tasks.Priority;

import java.util.List;
import java.util.Optional;

public class RecruitmentDrive implements QuestTask {
    private static RecruitmentDrive quest;

    public static RecruitmentDrive get() {
        return quest == null ? quest = new RecruitmentDrive() : quest;
    }

    int COINS = 995;
    int FALADOR_TELEPORT = 8009;
    int SHEARS = 5603;
    int CHISEL = 5601;
    int PAN = 5592;
    int WIRE = 5602;
    int VIAL = 229;
    int METAL_SPADE = 5586;
    int MAGNET = 5604;
    int KNIFE = 5605;
    int BUNSEN_BURNER = 7332;
    int ACETIC_ACID = 5578;
    int VIAL_OF_LIQUID = 5582;
    int CUPRIC_SULFATE = 5577;
    int GYPSUM = 5579;
    int SODIUM_CHLORIDE = 5580;
    int NITROUS_OXIDE = 5581;
    int TIN_ORE_POWDER = 5583;
    int CUPRIC_ORE_POWDER = 5584;
    int SPADE_WITHOUT_HANDLE = 5587;
    int STONE_DOOR = 7342;
    int PAN_WITH_KEY_IMPRINT = 5594;
    int TIN_WITH_ORES = 5597;
    int KEY = 5585;
    int FOX = 7275;
    int GRAIN = 7282;
    int CHICKEN = 7279;
    int BRIDGE_BEFORE_ID = 7286;
    int BRIDGE_RETURN_ID = 7287;


    Area QUEST_START_AREA = Area.fromRectangle(new WorldTile(2956, 3340, 2), new WorldTile(2964, 3335, 2));
    Area FALADOR_PARK = Area.fromRectangle(new WorldTile(2995, 3373, 0), new WorldTile(3000, 3376, 0));
    Area MAKEOVER_MAGE = Area.fromRectangle(new WorldTile(2921, 3322, 0), new WorldTile(2914, 3324, 0));
    Area TRAINING_ROOM_1 = Area.fromRectangle(new WorldTile(2480, 4953, 0), new WorldTile(2472, 4959, 0));
    Area TRAINING_ROOM_2 = Area.fromRectangle(new WorldTile(2467, 4944, 0), new WorldTile(2479, 4936, 0));
    Area LADY_TABLE_AREA = Area.fromRectangle(new WorldTile(2460, 4975, 0), new WorldTile(2446, 4983, 0));
    Area SIR_SPISHYUS_AREA = Area.fromRectangle(new WorldTile(2490, 4967, 0), new WorldTile(2471, 4976, 0));
    Area SIR_REN_AREA = Area.fromRectangle(new WorldTile(2438, 4959, 0), new WorldTile(2448, 4952, 0));
    Area MISS_CHEEVERS_AREA = Area.fromRectangle(new WorldTile(2466, 4944, 0), new WorldTile(2479, 4935, 0));
    Area SIR_KUAM_AREA = Area.fromRectangle(new WorldTile(2454, 4967, 0), new WorldTile(2464, 4959, 0));
    Area MS_HYNN_AREA = Area.fromRectangle(new WorldTile(2447, 4944, 0), new WorldTile(2455, 4935, 0));
    Area WHOLE_AREA = Area.fromRectangle(new WorldTile(2497, 4932, 0), new WorldTile(2437, 4988, 0));
    Area STATUE_AREA = Area.fromRectangle(new WorldTile(2458, 4976, 0), new WorldTile(2448, 4982, 0));

    public void buyItems() {
        cQuesterV2.status = "Buying Items";
        Log.info("Buying Items");

    }

    public void getItems() {
        cQuesterV2.status = "Getting Items";
        Log.info(cQuesterV2.status);
        BankManager.open(true);
        BankManager.depositAll(true);
        BankManager.depositEquipment();
        BankManager.withdraw(3000, true, COINS);
        BankManager.withdraw(3, true, FALADOR_TELEPORT);
        BankManager.close(true);
    }


    private boolean isFemale() {
        return GameState.getSetting(261) == 1;
    }

    public void startQuest() {
        cQuesterV2.status = "Starting Quest";
        Log.info(cQuesterV2.status);
        PathingUtil.walkToArea(QUEST_START_AREA);
        if (NpcChat.talkToNPC("Sir Amik Varze")) {
            NpcChat.handle(true, "Yes please", "Yes.", "Ok, I'll do my best.");
        }


    }

    public void step2() {
        if (!isFemale()) {
            cQuesterV2.status = "Step 2 - Changing Sex";
            Log.info(cQuesterV2.status);
            PathingUtil.walkToArea(MAKEOVER_MAGE);
            if (MAKEOVER_MAGE.containsMyPlayer()) {
                if (Utils.clickNPC("Makeover mage", "Makeover")) {
                    NpcChat.handle("Pay 3,000 coins");
                    Timer.waitCondition(() -> Widgets.isVisible(205), 4000, 8000);
                }
                if (Widgets.isVisible(205, 6)) {
                    Optional<Widget> female = Query.widgets().inIndexPath(205).actionContains("Female").findFirst();
                    if (female.map(c -> c.click()).orElse(false)) {
                        Waiting.waitNormal(1500, 125);
                    }
                    Optional<Widget> confirm = Query.widgets().inIndexPath(205).actionContains("Confirm").findFirst();
                    if (confirm.map(c -> c.click()).orElse(false)) {
                        Waiting.waitUntil(3500, 500, () -> Widgets.isVisible(205));
                    }
                }
            }
        }
    }

    public void step3() {
        if (isFemale()) {
            if (!WHOLE_AREA.containsMyPlayer()) {
                if (!FALADOR_PARK.containsMyPlayer()) {
                    cQuesterV2.status = "Step 3 - Going to falador park";
                    Log.info(cQuesterV2.status);
                    PathingUtil.walkToArea(FALADOR_PARK);
                }
            }
            if (FALADOR_PARK.containsMyPlayer()) {
                cQuesterV2.status = "Talking to Tiffy Cashien";
                Log.info(cQuesterV2.status);

                if (NpcChat.talkToNPC("Sir Tiffy Cashien")) {
                    NpcChat.handle(true, "Yes, let's go!");
                    Utils.modSleep();
                    NpcChat.handle(true);
                }
            }
        }
    }

    public void sirTinley() {
        if (TRAINING_ROOM_1.containsMyPlayer()) {
            cQuesterV2.status = "Talking to Sir Tinley";
            Log.info(cQuesterV2.status);
            if (NpcChat.talkToNPC("Sir Tinley")) {
                NpcChat.handle(true);
                Waiting.waitUntil(15000, 675, () -> ChatScreen.isOpen());
                NpcChat.handle(true);
            }
            if (Utils.clickObj(7320, "Open")) {
                Waiting.waitUntil(10000, 1250, () -> !TRAINING_ROOM_1.containsMyPlayer());
                Waiting.waitNormal(2000, 200);
            }
        }
    }

    private boolean hasMissCheeversItems() {
        return Inventory.contains(CUPRIC_SULFATE, VIAL_OF_LIQUID,
                PAN, GYPSUM, CUPRIC_ORE_POWDER);
    }

    int OPEN_DOOR_ONE_CHEEVERS = 7342;

    public void missCheevers() {
        if (MISS_CHEEVERS_AREA.containsMyPlayer()) {
            Utils.idle(1200, 2500);
            cQuesterV2.status = "Miss Cheevers";
            Log.info(cQuesterV2.status);// first door opened
            Optional<GroundItem> closest = Query.groundItems().idEquals(VIAL).findClosest();
            if (!Inventory.contains(VIAL) && closest.map(c -> c.interact("Take")).orElse(false)) {
                Timer.waitCondition(() -> Inventory.contains(VIAL), 7000);
            }

            Optional<GroundItem> closest2 = Query.groundItems().idEquals(METAL_SPADE).findClosest();
            if (!Inventory.contains(METAL_SPADE) && closest2.map(c -> c.interact("Take")).orElse(false)) {
                Timer.waitCondition(() -> Inventory.contains(METAL_SPADE), 7000);
            }

            for (int i = 7327; i < 7331; i++) {
                if (Objects.findNearest(25, i).length > 0) {
                    if (!Objects.findNearest(25, i)[0].isClickable()) {
                        Objects.findNearest(25, i)[0].adjustCameraTo();
                    }
                    if (AccurateMouse.click(Objects.findNearest(25, i)[0], "Search")) {
                        NpcChat.handle(true, "YES");
                        Utils.idleNormalAction(true);
                    }
                }
            }
            for (int i = 7333; i < 7341; i++) {
                Optional<GameObject> obj = Query.gameObjects().idEquals(i).findClosest();
                if (obj.map(o -> o.interact("Search")).orElse(false)) {
                    NPCInteraction.waitForConversationWindow();
                    NPCInteraction.handleConversation("YES", "Take both vials.", "Take all three vials");
                    Utils.idleNormalAction(true);
                }
            }

            if (!hasMissCheeversItems()) {
                searchItem(7347, 0);
                searchItem(7348, 0);
                // searchItem(7348, 1);
                searchItem(7349, 0);
                // searchItem(7347, 1);
                //   searchItem(7348, 0);
                //   searchItem(7349, 0);
                searchItem(7350, "Open");
                searchItem(7351, "Search");
            }
            if (Utils.useItemOnObject(METAL_SPADE, BUNSEN_BURNER))
                Timer.waitCondition(() -> Inventory.contains(SPADE_WITHOUT_HANDLE), 7000);

            if (Utils.useItemOnObject(SPADE_WITHOUT_HANDLE, STONE_DOOR)) {
                Timer.waitCondition(() -> !Inventory.contains(SPADE_WITHOUT_HANDLE), 7000);
                Utils.idleNormalAction(true);
            }
            if (Utils.useItemOnObject(CUPRIC_SULFATE, STONE_DOOR)) {
                Timer.waitCondition(() -> !Inventory.contains(CUPRIC_SULFATE), 7000);
                Utils.idleNormalAction(true);
            }

            int invLiquid = Inventory.getCount(VIAL_OF_LIQUID);
            if (Utils.useItemOnObject(VIAL_OF_LIQUID, STONE_DOOR)) {
                Timer.waitCondition(() -> Inventory.getCount(CUPRIC_SULFATE) < invLiquid, 7000);
                Utils.idleNormalAction(true);
            }

            Optional<GameObject> door = Query.gameObjects()
                    .idEquals(STONE_DOOR)
                    .actionContains("Pull-spade").findClosest();
            if (door.map(d -> d.interact("Pull-spade")).orElse(false))
                Waiting.waitUntil(5500, 800, () ->
                        Query.gameObjects().idEquals(OPEN_DOOR_ONE_CHEEVERS).isAny());
        }

        if (Query.gameObjects().idEquals(OPEN_DOOR_ONE_CHEEVERS).isAny()) { // first door opened
            if (Utils.useItemOnItem(PAN, VIAL_OF_LIQUID))
                General.sleep(1500, 3000);

            if (Utils.useItemOnItem(PAN, GYPSUM))
                General.sleep(1500, 3000);

            if (Utils.useItemOnObject(5593, 7346))
                Timer.waitCondition(() -> Inventory.contains(PAN_WITH_KEY_IMPRINT), 10000);

            if (Utils.useItemOnItem(PAN_WITH_KEY_IMPRINT, CUPRIC_ORE_POWDER))
                General.sleep(General.random(500, 3000));

            if (Utils.useItemOnItem(5596, TIN_ORE_POWDER))
                General.sleep(General.random(500, 3000));

            if (Utils.useItemOnObject(TIN_WITH_ORES, BUNSEN_BURNER))
                Timer.waitCondition(() -> Inventory.contains(5598), 10000);

            if (Utils.useItemOnItem(CHISEL, 5598))
                Utils.idleNormalAction(true);

            if (Utils.useItemOnItem(KNIFE, 5598))
                Timer.waitCondition(() -> Inventory.contains(KEY), 10000);

            Optional<GameObject> walk = Query.gameObjects().idEquals(7342).findClosest();
            if (walk.map(w -> w.interact("Walk-through")).orElse(false)) {
                PathingUtil.movementIdle();
            }

            if (Utils.useItemOnObject(KEY, 7326)) // second door
                General.sleep(4500, 6000);
        }


    }

    public void searchItem(int id, int index) {
        List<GameObject> gameObjects = Query.gameObjects().idEquals(id).sortedByDistance().toList();
        for (GameObject g : gameObjects) {
            int inv = Inventory.getAll().size();
            if (g.interact("Search")) {
                Waiting.waitUntil(4000, 700, () -> g.getTile().distance() < 2);
                Waiting.waitUntil(2000, 125, () -> Inventory.getAll().size() > inv);
            }
            //  General.sleep(4000, 5000);
        }
      /*  if (gameObjects.size() > 0) {

            if (gameObjects.get(index).interact("Search"))
                General.sleep(4000, 5000);
        }*/
    }

    public void searchItem(int id, String action) {
        List<GameObject> gameObjects = Query.gameObjects().idEquals(id).sortedByDistance().toList();
        if (gameObjects.size() > 0) {

            if (gameObjects.get(0).interact(action))
                General.sleep(4000, 5000);
        }
    }


    public void ladyTable() {
        if (LADY_TABLE_AREA.containsMyPlayer()) {
            cQuesterV2.status = "Lady Table";
            Log.info(cQuesterV2.status);

            WorldTile moveTo = MyPlayer.getTile().translate(-6, 0);
            Log.info("Moving to centre tile");
            moveTo.adjustCameraTo();
            if (moveTo.interact("Walk here"))
                PathingUtil.movementIdle();

            int missing = 0;
            GameObject missingStatue;
            int[] statueIDs = {7290, 7291, 7292, 7293, 7294, 7295, 7296, 7297, 7298, 7299, 7300, 7301};
            List<GameObject> initial = Query.gameObjects().inArea(Area.fromRectangle(new WorldTile(2458, 4976, 0),
                            new WorldTile(2448, 4982, 0)))
                    .nameContains("Null")
                    .idEquals(statueIDs)
                    .toList();
            Log.info("Initial filter identified " + initial.size() + " objects that fit criteria.");

            for (int i = 0; i < initial.size(); i++) {
                for (int b = 0; b < statueIDs.length; b++) {
                    if (initial.get(i).getId() == statueIDs[b])
                        missing = initial.get(i).getId();
                }
            }

            Log.info("Filter identified " + missing + " as the missing statue");
            //initialStatues = initialStatues;
            cQuesterV2.status = "Waiting...";
            Timer.waitCondition(() -> ChatScreen.getMessage().isPresent() &&
                            ChatScreen.getMessage().get()
                                    .contains("Please touch the statue you think") ||
                            Query.widgets().inIndexPath(231).textContains("Please touch the statue you think").isAny(),
                    35000, 40000);
            // Utils.longSleep();

            Optional<GameObject> missingObj = Query.gameObjects().idEquals(missing).findClosest();

            if (missingObj.map(m -> m.interact("Touch")).orElse(false)) {
                PathingUtil.movementIdle();
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation();
            }

            if (Utils.clickObj(7302, "Open"))
                Timer.waitCondition(() -> !LADY_TABLE_AREA.containsMyPlayer(), 15000);
        }
    }


    public void msHynnTerprett() {
        if (MS_HYNN_AREA.containsMyPlayer()) {
            cQuesterV2.status = "Miss Hynn Terprett";
            Log.info(cQuesterV2.status);
            NPCInteraction.handleConversation("Bucket A (32 degrees)", "The number of false statements here is three.", "Being fed to the wolves.", "Zero.");
            if (Interfaces.get(162, 44) != null && !Interfaces.get(162, 44).isHidden()) {
                String answer = "10";
                Keyboard.typeString(answer);
                Keyboard.pressEnter();
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation();
            }

            if (Utils.clickObj("Door", "Open"))
                Timer.waitCondition(() -> !MS_HYNN_AREA.containsMyPlayer(), 15000);
        }
    }

    Area AFTER_BRIDGE = Area.fromRectangle(new WorldTile(2477, 4970, 0), new WorldTile(2472, 4974, 0));
    Area BEFORE_BRIDGE = Area.fromRectangle(new WorldTile(2490, 4967, 0), new WorldTile(2482, 4976, 0));

    public void sirSpishyus() {
        if (SIR_SPISHYUS_AREA.containsMyPlayer()) {

            if (NPCInteraction.isConversationWindowUp())
                NPCInteraction.handleConversation();

            cQuesterV2.status = "Sir Spishyus";
            Log.info(cQuesterV2.status);
            Optional<GameObject> chicken = Query.gameObjects().maxDistance(30).nameContains("Chicken").findClosest();
            Optional<GameObject> grain = Query.gameObjects().maxDistance(30).nameContains("Grain").findClosest();
            Optional<GameObject> fox = Query.gameObjects().maxDistance(30).nameContains("Fox").findClosest();
            List<GameObject> all = Query.gameObjects().maxDistance(30).nameContains("Grain", "Chicken", "Fox").toList();
            if (chicken.isPresent() && fox.isPresent() && grain.isPresent()) {
                if (BEFORE_BRIDGE.contains(chicken.get())
                        && BEFORE_BRIDGE.contains(fox.get())
                        && BEFORE_BRIDGE.contains(grain.get())) {
                    cQuesterV2.status = "Sir Spishyus - Moving Chicken across";
                    Log.info(cQuesterV2.status);
                    pickUpAndMoveToEnd(CHICKEN, "Chicken");
                    moveBackEmpty();
                }
                if (END_STORAGE.contains(chicken.get())
                        && BEFORE_BRIDGE.contains(fox.get())
                        && BEFORE_BRIDGE.contains(grain.get())) {
                    cQuesterV2.status = "Sir Spishyus - Moving Grain across";
                    Log.info(cQuesterV2.status);
                    pickUpAndMoveToEnd(GRAIN, "Grain");
                }
                if (END_STORAGE.contains(chicken.get())
                        && BEFORE_BRIDGE.contains(fox.get())
                        && END_STORAGE.contains(grain.get())) {
                    cQuesterV2.status = "Sir Spishyus - Moving Chicken back";
                    Log.info(cQuesterV2.status);
                    pickUpAndMoveToStart("Chicken", "Chicken");
                }
                if (BEFORE_BRIDGE.contains(chicken.get())
                        && BEFORE_BRIDGE.contains(fox.get())
                        && END_STORAGE.contains(grain.get())) {
                    cQuesterV2.status = "Sir Spishyus - Moving Fox across";
                    Log.info(cQuesterV2.status);
                    pickUpAndMoveToEnd(FOX, "Fox");
                    moveBackEmpty();
                }
                if (BEFORE_BRIDGE.contains(chicken.get())
                        && END_STORAGE.contains(fox.get())
                        && END_STORAGE.contains(grain.get())) {
                    cQuesterV2.status = "Sir Spishyus - Moving Chicken across 3";
                    Log.info(cQuesterV2.status);
                    pickUpAndMoveToEnd(CHICKEN, "Chicken");
                }
                if (END_STORAGE.contains(chicken.get())
                        && END_STORAGE.contains(fox.get())
                        && END_STORAGE.contains(grain.get())) {
                    if (Utils.clickObj(7274, "Open"))
                        Timer.waitCondition(() -> !SIR_SPISHYUS_AREA.containsMyPlayer(), 15000);
                }
            }
        }
    }

    Area END_STORAGE = Area.fromRectangle(new WorldTile(2476, 4976, 0), new WorldTile(2470, 4968, 0));

    public void pickUpAndMoveToEnd(int object, String objectString) {
        if (BEFORE_BRIDGE.containsMyPlayer()) {
            if (Utils.clickObj(object, "Pick-up")) {
                Timer.waitCondition(() -> Equipment.contains(objectString), 7000);
                Log.info("Is Obj equiped? " + Equipment.contains(objectString));
            }
            Optional<GameObject> cross = Query.gameObjects().actionContains("Cross").findClosest();
            if (Equipment.contains(objectString) && cross.map(c -> c.interact("Cross")).orElse(false))
                Timer.abc2WaitCondition(() -> AFTER_BRIDGE.containsMyPlayer(), 13000, 15000);


        }
        if (AFTER_BRIDGE.containsMyPlayer()) {
            Equipment.remove(objectString);
            Timer.waitCondition(() -> !Equipment.contains(objectString), 7000);

            Utils.idle(900, 2500);

        }
    }

    public void moveBackEmpty() {
        if (AFTER_BRIDGE.containsMyPlayer()) {
            Optional<GameObject> cross = Query.gameObjects().actionContains("Cross").findClosest();
            if (cross.map(c -> c.interact("Cross")).orElse(false))
                Timer.waitCondition(() -> BEFORE_BRIDGE.containsMyPlayer(), 15000);

            Utils.idle(1500, 2500);
        }
    }


    public void pickUpAndMoveToStart(String object, String objectString) {
        if (AFTER_BRIDGE.containsMyPlayer()) {
            if (Utils.clickObj(object, "Pick-up"))
                Timer.waitCondition(() -> Equipment.contains(object), 7000);

            Optional<GameObject> cross = Query.gameObjects().actionContains("Cross").findClosest();
            if (Equipment.contains(object) && cross.map(c -> c.interact("Cross")).orElse(false))
                Timer.abc2WaitCondition(() -> AFTER_BRIDGE.containsMyPlayer(), 13000, 15000);
        }

        if (BEFORE_BRIDGE.containsMyPlayer()) {
            Equipment.remove(objectString);
            Timer.waitCondition(() -> !Equipment.contains(objectString), 7000);

            Utils.idle(900, 2500);

        }
    }

    String riddle;
    char letter1;
    char letter2;
    char letter3;
    char letter4;

    public void sirRen() {
        if (SIR_REN_AREA.containsMyPlayer()) {
            cQuesterV2.status = "Sir Ren.";
            Log.info(cQuesterV2.status);
            if (NpcChat.talkToNPC(4684))
                Timer.waitCondition(() -> Widgets.isVisible(219, 1, 1), 7000);

            Utils.idle(600, 2200);

            if (Interfaces.get(219, 1, 1) != null)
                Interfaces.get(219, 1, 1).click();

            Timer.waitCondition(() -> Interfaces.get(231, 4) != null, 7000);

            Utils.idle(1000, 2200);
            if (Interfaces.get(231, 6) != null) {
                riddle = ChatScreen.getMessage().get();

                letter1 = riddle.charAt(0);
                letter2 = riddle.split("<br>")[1].charAt(0);
                letter3 = riddle.split("<br>")[2].charAt(0);
                letter4 = riddle.split("<br>")[3].charAt(0);
                Log.info("First character is " + letter1);
                Log.info("Second character is " + letter2);
                Log.info("Third character is " + letter3);
                Log.info("Fourth character is " + letter4);
                Utils.idle(100, 500);

            }
            Optional<GameObject> exitDoor = Query.gameObjects().tileEquals(new WorldTile(2446, 4956, 0)).findBestInteractable();

            if (exitDoor.map(e -> e.interact("Open")).orElse(false))
                Timer.waitCondition(() -> Interfaces.get(285, 43) != null, 7000);
            if (Interfaces.get(285) != null) {
                cQuesterV2.status = "Sir Ren - Answering puzzle";
                Log.info(cQuesterV2.status);
                while (!Interfaces.get(285, 43).getText().contains(Character.toString(letter1))) {
                    General.sleep(General.random(50, 300));
                    Interfaces.get(285, 48).click();
                    General.sleep(800, 1100);
                }
                while (!Interfaces.get(285, 44).getText().contains(Character.toString(letter2))) {
                    General.sleep(General.random(50, 300));
                    Interfaces.get(285, 50).click();
                    General.sleep(800, 1100);
                }
                while (!Interfaces.get(285, 45).getText().contains(Character.toString(letter3))) {
                    General.sleep(General.random(150, 300));
                    Interfaces.get(285, 52).click();
                    General.sleep(800, 1100);
                }
                while (!Interfaces.get(285, 46).getText().contains(Character.toString(letter4))) {
                    General.sleep(General.random(50, 300));
                    Interfaces.get(285, 54).click();
                    General.sleep(800, 1100);
                }
                if (Interfaces.get(285, 56) != null) {
                    Interfaces.get(285, 56).click();
                    General.sleep(General.random(1000, 2000));

                    Optional<GameObject> open = Query.gameObjects()
                            .idEquals(7356).findBestInteractable();

                    if (open.map(o -> o.interact("Open")).orElse(false))
                        NpcChat.handle(true);
                }
            }
            if (ChatScreen.isOpen()){
                NpcChat.handle();
            }
            if (exitDoor.map(e -> e.interact("Open")).orElse(false))
                Waiting.waitUntil(5500, 500, () -> !SIR_REN_AREA.containsMyPlayer());
        }
    }

    public void sirKuam() {
        if (SIR_KUAM_AREA.containsMyPlayer()) {
            cQuesterV2.status = "Sir Kuam Ferentse";
            Log.info(cQuesterV2.status);
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation();
            if (Utils.clickNPC("Sir Leye", "Attack")) {
                Timer.waitCondition(() -> MyPlayer.isHealthBarVisible(), 8000);
                Timer.waitCondition(() -> !MyPlayer.isHealthBarVisible(), 45000);
            }
            if (!Query.npcs().nameContains("Sir Leye").isAny() &&
                    Utils.clickObj("Door", "Open")) {
                Log.info("Leaving");
                Waiting.waitUntil(() -> !SIR_KUAM_AREA.containsMyPlayer());
            }

        }
    }

    public void finishQuest() {
        if (FALADOR_PARK.containsMyPlayer()) {
            cQuesterV2.status = "Talking to Tiffy Cashien";
            Log.info(cQuesterV2.status);
            if (NpcChat.talkToNPC("Sir Tiffy Cashien")) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation();
                NPCInteraction.handleConversation();
                General.sleep(General.random(3500, 5000));
            }
        }
    }

    @Override
    public void execute() {

        if (isComplete()) {
            Utils.closeQuestCompletionWindow();
            cQuesterV2.taskList.remove(this);
            return;
        }

        if (GameState.getSetting(496) == 0) {
            getItems();
            step2();
            startQuest();
        }
        step3();
        sirTinley();
        missCheevers();
        sirSpishyus();
        sirRen();
        ladyTable();
        sirKuam();
        msHynnTerprett();
        finishQuest();

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
    public String questName() {
        return "Recruitment Drive";
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

    @Override
    public boolean isComplete() {
        return Quest.RECRUITMENT_DRIVE.getState().equals(Quest.State.COMPLETE);
    }
}
