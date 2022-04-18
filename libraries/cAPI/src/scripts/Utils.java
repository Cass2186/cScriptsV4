package scripts;

import dax.api_lib.DaxWalker;
import dax.walker.utils.AccurateMouse;
import dax.walker.utils.camera.DaxCamera;
import dax.walker_engine.interaction_handling.NPCInteraction;

import obf.ih;
import org.tribot.api.Clicking;
import org.tribot.api.DynamicClicking;
import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.input.Keyboard;
import org.tribot.api.input.Mouse;
import org.tribot.api.interfaces.Clickable;
import org.tribot.api.interfaces.Positionable;
import org.tribot.api.types.generic.CustomRet_2P;
import org.tribot.api2007.Camera;
import org.tribot.api2007.Combat;
import org.tribot.api2007.Equipment;
import org.tribot.api2007.GameTab;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Login;
import org.tribot.api2007.Objects;
import org.tribot.api2007.*;
import org.tribot.api2007.Options;
import org.tribot.api2007.Player;
import org.tribot.api2007.WorldHopper;
import org.tribot.api2007.ext.Doors;
import org.tribot.api2007.ext.Filters;
import org.tribot.api2007.types.*;
import org.tribot.script.Script;
import org.tribot.script.sdk.*;
import org.tribot.script.sdk.cache.BankCache;
import org.tribot.script.sdk.interfaces.Tile;
import org.tribot.script.sdk.query.Query;
import org.tribot.script.sdk.types.*;
import org.tribot.script.sdk.types.definitions.ItemDefinition;
import org.tribot.script.sdk.walking.GlobalWalking;
import org.tribot.script.sdk.walking.LocalWalking;
import scripts.EntitySelector.Entities;
import scripts.EntitySelector.finders.prefabs.ObjectEntity;
import scripts.Items.PotionEnum;
import scripts.Requirements.ItemReq;
import scripts.Requirements.ItemRequirement;
import scripts.Requirements.Requirement;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.text.NumberFormat;
import java.util.List;
import java.util.*;
import java.util.Map.Entry;
import java.util.function.BooleanSupplier;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Modified/added to from original author (@Elon)
 */
public class Utils {

    private static int QUEST_WIDGET_PARENT = 153;
    private static List<Integer> abc2waitTimes = new ArrayList<>();
    public static double FACTOR = 0.5;
    private static final String[] NAMES_TO_REMOVE = {"Divine", "Super", "super"};

    public static Object[] reverseItemArray(int[] arr) {

        List<Integer> list = Arrays.stream(arr)
                .boxed()
                .collect(Collectors.toList());
        Collections.reverse(list);
        return list.stream().toArray();
    }





    public static RSArea getObjectRSArea(RSObject object) {

        RSObjectDefinition objectDefinition = object.getDefinition();

        int xRight = object.getPosition().getX() + objectDefinition.getHeight();
        int xLeft = object.getPosition().getX() - 1;

        int yTop = object.getPosition().getY() + objectDefinition.getWidth();
        int yBottom = object.getPosition().getY() - 1;

        RSArea objectArea = new RSArea(
                new RSTile(xRight, yBottom, object.getPosition().getPlane()),
                new RSTile(xLeft, yTop, object.getPosition().getPlane())
        );

        return objectArea;
    }

    public static boolean hasActionContaining(RSObject rsObject, String action) {
        return StreamUtils.streamOptional(Optional.ofNullable(rsObject)
                        .flatMap(object -> Optional.ofNullable(object.getDefinition())))
                .flatMap(rsObjectDefinition -> Arrays.stream(rsObjectDefinition.getActions()))
                .anyMatch(e -> e.contains(action));
    }

    public static boolean checkAllRequirements(Requirement... reqs) {
        return Arrays.stream(reqs).anyMatch(r -> !r.check());
    }

    public static WorldTile getWorldTileFromRSTile(RSTile tile) {

        return new WorldTile(tile.getX(), tile.getY(), tile.getPlane());
    }

    public static RSTile getRSTileFromWorldTile(WorldTile tile) {

        return new RSTile(tile.getX(), tile.getY(), tile.getPlane());
    }

    public static Area getAreaFromRSArea(RSArea area) {
        List<Tile> wolrdTiles = new ArrayList<>();
        for (RSTile t : area.getAllTiles()) {
            wolrdTiles.add(getWorldTileFromRSTile(t));
        }
        return Area.fromPolygon(wolrdTiles);
    }

    public static double random(double min, double max) {
        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }
        Random r = new Random();
        double randomValue = min + (max - min) * r.nextDouble();
        return randomValue;
    }

    public static int random(int min, int max) {

        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }

        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }


    public static int getQuestPoints() {
        return Game.getSetting(101);
    }

    public static void cutScene() {
        while (inCutScene()) {
            Log.log("[Debug]: Cutscene Idle");
            Waiting.waitUniform(500, 2500);
            if (NPCInteraction.isConversationWindowUp())
                NPCInteraction.handleConversation();
        }
    }


    public static boolean inCutScene() {
        return GameState.getVarbit(12139) == 1;
    }

    public static boolean clickNPC(Optional<Npc> npc, String action) {

        if (npc.isEmpty()) return false;

        if (!handleUnclickableNpc(npc.get())) return false;

        int num = General.random(5, 7);
        for (int i = 0; i < num; i++) {
            // org.tribot.script.sdk.input.Mouse.setClickMethod(org.tribot.script.sdk.input.Mouse.ClickMethod.ACCURATE_MOUSE);
            if (Utils.unselectItem())
                General.sleep(General.randomSD(20, 250, 120, 50));

            if (i == 3) { // force camera adjustment if it's failed 2x prior to click
                Log.log("[Utils]: Force focusing camera on NPC");
                npc.get().adjustCameraTo();
            }

            if (npc.get().interact(action))
                return true;
        }
        return false;
    }

    public static boolean clickNPC(int npcId, String action) {
        Optional<Npc> npc = Query.npcs().idEquals(npcId)
                .findBestInteractable();
        return clickNPC(npc, action) || Interaction.interactNpc(npcId, action);
    }

    public static boolean clickNPC(String npcName, String action) {
        Optional<Npc> npc = Query.npcs().nameContains(npcName)
                .findBestInteractable();
        return clickNPC(npc, action);
    }


    public static boolean clickNPC(RSNPC npc, String action, boolean useAccurateMouse) {
        if (npc != null) {
            handleUnclickableNpc(npc);

            int num = General.random(5, 7);
            for (int i = 0; i < num; i++) {
                if (i == 2) { // force camera adjustment if it's failed 2x prior to click
                    Log.log("[Utils]: Force focusing camera on NPC");
                    DaxCamera.focus(npc);
                }
                if (Utils.unselectItem())
                    General.sleep(General.randomSD(20, 250, 120, 50));

                if (useAccurateMouse && AccurateMouse.click(npc, action))
                    return true;

                else if (DynamicClicking.clickRSNPC(npc, action))
                    return true;

            }
        }
        return false;
    }

    /********************
     * OBJECT HANDLING *
     *******************/

    private static boolean clickObj(Optional<GameObject> obj, String action, boolean useAccurateMouse) {
        if (obj.isEmpty()) return false;

        int num = General.random(4, 6);
        for (int i = 0; i < num; i++) {
            if (i == 2) { // force camera adjustment if it's failed 2x prior to click
                Log.log("[Utils]: Force focusing camera on GameObject");
                obj.get().adjustCameraTo();
            }
            if (Utils.unselectItem())
                General.sleep(General.randomSD(20, 250, 120, 50));

            if (useAccurateMouse) {
                org.tribot.script.sdk.input.Mouse.setClickMethod(
                        org.tribot.script.sdk.input.Mouse.ClickMethod.ACCURATE_MOUSE);
                if (obj.get().interact(action))
                    return true;

            } else {
                org.tribot.script.sdk.input.Mouse.setClickMethod(
                        org.tribot.script.sdk.input.Mouse.ClickMethod.TRIBOT);
                if (obj.get().interact(action))
                    return true;
            }
        }
        return false;
    }

    public static boolean clickObj(Optional<GameObject> obj, String action) {
        return clickObj(obj, action, false);
    }

    public static boolean clickObj(int objId, String action, boolean accurateMouse) {
        Optional<GameObject> obj = Query.gameObjects().idEquals(objId)
                .findClosestByPathDistance();
        return clickObj(obj, action, accurateMouse);
    }

    public static boolean clickObj(String name, String action, boolean accurateMouse) {
        Optional<GameObject> obj = Query.gameObjects().nameContains(name)
                .findClosestByPathDistance();
        return clickObj(obj, action, accurateMouse);
    }

    public static boolean clickObj(int objId, String action) {
        Optional<GameObject> obj = Query.gameObjects().idEquals(objId)
                .findClosestByPathDistance();
        return clickObj(obj, action, false);
    }

    public static boolean clickObj(String name, String action) {
        Optional<GameObject> obj = Query.gameObjects().nameContains(name)
                .findClosestByPathDistance();
        return clickObj(obj, action, false);
    }


    public static RSArea FEROX_POOL_AREA = new RSArea(new RSTile(3127, 3637, 0), new RSTile(3129, 3635, 0));
    public static RSArea FEROX_WHOLE_AREA = new RSArea(new RSTile(3127, 3637, 0), 20);

    public static void clanWarsReset() {
        if (!FEROX_WHOLE_AREA.contains(Player.getPosition())) {
            if (Inventory.find(ItemID.RING_OF_DUELING).length < 1 && Equipment.find(ItemID.RING_OF_DUELING).length < 1) {

                General.println("[Debug]: Getting Ring of Dueling");
                BankManager.open(true);
                BankManager.withdraw(1, true, ItemID.RING_OF_DUELING);
                BankManager.close(true);
                Utils.shortSleep();
            }
            if (Inventory.find(ItemID.RING_OF_DUELING).length > 0) {
                General.println("[Debug]: Going to Ferox Area");
                if (Clicking.click("Rub", Inventory.find(ItemID.RING_OF_DUELING)[0]))
                    Timer.abc2WaitCondition(() -> Interfaces.isInterfaceSubstantiated(219, 1, 3), 5000, 9000);
                if (Interfaces.isInterfaceSubstantiated(219, 1, 3) && Interfaces.get(219, 1, 3).getText().contains("Ferox")) {
                    Keyboard.typeString("3");
                    Utils.modSleep();
                }
            } else if (Equipment.find(ItemID.RING_OF_DUELING).length > 0) {
                Equipment.find(ItemID.RING_OF_DUELING)[0].click("Ferox Enclave");
                Utils.modSleep();
            }

        }
        PathingUtil.walkToArea(FEROX_POOL_AREA);
        RSObject[] poolOfRefreshment = Objects.findNearest(20, "Pool of Refreshment");
        if (poolOfRefreshment.length > 0 && clickObj("Pool of Refreshment", "Drink"))
            Timer.slowWaitCondition(() -> Player.getAnimation() != -1, 8000, 12000);

    }


    public static WorldTile getStartFmTile(Area area, int pathLengthMin) {
        List<WorldTile> tileList = area.getAllTiles();
        for (WorldTile t : tileList) {
            for (int b = 0; b < pathLengthMin; b++) {
                if (Query.gameObjects().tileEquals(t.translate(-b, 0)).isAny()) { // object on tile
                    break; //break this for loop and try next tile in area
                } else if (b == pathLengthMin - 1) { //we itterated through the potential path and no objects were found
                    return t;// return tile;
                }
            }
        }
        return MyPlayer.getPosition(); //spaceholder
    }

    public static int NOT_STARTED_ID = 16711680;
    public static int IN_PROGRESS_ID = 16776960;
    public static int COMPLETED_ID = 901389;

    public static void blindWalkToTile(RSTile position) {
    }

    public enum QUEST_STATUS {
        FAILURE,
        NOT_STARTED,
        IN_PROGRESS,
        COMPLETED;
    }


    public static QUEST_STATUS checkP2PQuestStatus(String questName) {
        for (int i = 0; i < 123; i++) {
            if (Interfaces.get(399, 7, i) != null) {
                if (Interfaces.get(399, 7, i).getText().contains(questName)) {
                    if (Interfaces.get(399, 7, i).getTextColour() == NOT_STARTED_ID) {
                        General.println("[Debug]: " + questName + " is not started");
                        return QUEST_STATUS.NOT_STARTED;
                    }
                    if (Interfaces.get(399, 7, i).getTextColour() == IN_PROGRESS_ID) {
                        General.println("[Debug]: " + questName + " is in progress");
                        return QUEST_STATUS.IN_PROGRESS;
                    }
                    if (Interfaces.get(399, 7, i).getTextColour() == COMPLETED_ID) {
                        General.println("[Debug]: " + questName + " is complete");
                        return QUEST_STATUS.COMPLETED;
                    }
                }
            }
        }
        return QUEST_STATUS.FAILURE;
    }

    private static boolean clickObj(RSObject obj, String action, boolean useAccurateMouse) {
        if (obj != null) {

            handleUnclickableObj(obj);

            int num = General.random(5, 7);
            for (int i = 0; i < num; i++) {
                if (i == 2) { // force camera adjustment if it's failed 2x prior to click
                    Log.log("[Utils]: Force focusing camera on RSObject");
                    DaxCamera.focus(obj);
                }
                if (Utils.unselectItem())
                    General.sleep(General.randomSD(20, 250, 120, 50));

                if (useAccurateMouse && AccurateMouse.click(obj, action))
                    return true;

                else if (DynamicClicking.clickRSObject(obj, action))
                    return true;

            }

        }
        return false;
    }

    public static boolean handleUnclickableNpc(Npc obj) {
        if (!obj.isVisible()) {
            if (obj.getTile().distanceTo(MyPlayer.getPosition()) > General.random(9, 12)) {
                Log.log("[Utilities]: NPC is far away, walking to it");

                if (!LocalWalking.walkTo(obj.getTile()))
                    GlobalWalking.walkTo(obj.getTile());

                else
                    Timer.waitCondition(() -> obj.getTile().distanceTo(MyPlayer.getPosition()) < 7 ||
                            obj.isVisible(), 6500, 9200);

                if (!obj.isVisible())
                    obj.adjustCameraTo();

                return obj.isVisible();

            } else
                obj.adjustCameraTo();
        }
        return obj.isVisible();
    }

    public static boolean handleUnclickableNpc(RSNPC obj) {
        if (!obj.isClickable()) {
            if (obj.getPosition().distanceTo(Player.getPosition()) > 12) {
                Log.log("[Utilities]: NPC is far away, walking to obj");

                if (!PathingUtil.localNavigation(obj.getPosition()))
                    Walking.blindWalkTo(obj.getPosition());

                else
                    Timer.waitCondition(() -> obj.getPosition().distanceTo(Player.getPosition()) < 7, 5000, 7000);

                DaxCamera.focus(obj);
                return obj.isClickable();

            } else
                DaxCamera.focus(obj);
        }
        return obj.isClickable();
    }

    public static boolean handleUnclickableObj(RSObject obj) {
        if (!obj.isClickable()) {
            if (obj.getPosition().distanceTo(Player.getPosition()) > General.random(10, 12)) {
                Log.log("[Utilities]: Object is far away, walking to obj");
                RSTile tile = obj.getPosition();
                LocalTile localTile = new LocalTile(tile.getX(), tile.getY(), MyPlayer.getPosition().getPlane());
                Optional<LocalTile> walkable = getWalkableTile(localTile);
                if (walkable.isPresent()) {
                    if (!PathingUtil.localNav(walkable.get()))
                        Walking.blindWalkTo(obj.getPosition());
                } else
                    Timer.waitCondition(() -> obj.getPosition().distanceTo(Player.getPosition()) < 7, 5000, 7000);

                DaxCamera.focus(obj);
                return obj.isClickable();

            } else
                obj.adjustCameraTo();
        }
        return obj.isClickable();
    }

    public static boolean isItemNoted(int itemID) {
        Optional<ItemDefinition> def = ItemDefinition.get(itemID);
        if (def.isEmpty())
            return false;
        return def.get().isNoted();
    }

    public static boolean itemNameContains(String referenceItem, String comparator) {
        return referenceItem.toLowerCase(Locale.ROOT).contains(comparator.toLowerCase(Locale.ROOT));
    }

    public static List<ItemRequirement> multiplyItemList(List<ItemRequirement> list, int multiplier) {
        List<ItemRequirement> newList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            newList.add(new ItemRequirement(list.get(i).getId(), list.get(i).getAmount() * multiplier));
        }
        return newList;
    }

    public static boolean clickObject(Predicate<RSObject> filter, String action) {
        RSObject[] obj = Objects.findNearest(40, filter);
        if (obj.length > 0)
            return clickObj(obj[0], action, true);

        System.out.println("[Utilities]: Cannot find obj based on applied filter."); //don't want to print to client
        return false;
    }

    public static boolean clickObject(RSObject obj, String action) {
        if (obj != null) {
            return clickObj(obj, action, true);
        }
        System.out.println("[Utilities]: Cannot find obj based on applied filter."); //don't want to print to client
        return false;
    }


    public static boolean clickGroundItem(int ItemID) {
        int stock = org.tribot.script.sdk.Inventory.getCount(ItemID);
        Optional<GroundItem> gItem = Query.groundItems().idEquals(ItemID).findBestInteractable();
        //TODO check it's reachable
        if (gItem.map(g -> g.interact("Take")).orElse(false))
            return Timer.waitCondition(() -> org.tribot.script.sdk.Inventory.getCount(ItemID) >= stock + 1, 7000, 9000);
        return false;
    }

    public static boolean clickGroundItem(int[] ItemIDs) {
        int stock = org.tribot.script.sdk.Inventory.getCount(ItemIDs);
        Optional<GroundItem> gItem = Query.groundItems().idEquals(ItemIDs).findBestInteractable();
        //TODO check it's reachable
        if (gItem.map(g -> g.interact("Take")).orElse(false))
            return Timer.waitCondition(() -> org.tribot.script.sdk.Inventory.getCount(ItemIDs) >= stock + 1, 7000, 9000);
        return false;
    }

    public static boolean clickGroundItem(String itemName) {
        int stock = org.tribot.script.sdk.Inventory.getCount(itemName);
        Optional<GroundItem> gItem = Query.groundItems().nameContains(itemName).findBestInteractable();
        //TODO check it's reachable
        if (gItem.map(g -> g.interact("Take")).orElse(false))
            return Timer.waitCondition(() -> org.tribot.script.sdk.Inventory.getCount(itemName) >= stock + 1, 7000, 9000);
        return false;
    }


    //TODO Delete and find all usages
    public static void pickupItem(int ItemID) {
        RSItem[] stock = Inventory.find(ItemID);
        RSGroundItem[] item = GroundItems.find(ItemID);
        if (item.length > 0) {
            if (!item[0].isClickable())
                DaxCamera.focus(item[0]);

            if (AccurateMouse.click(item[0], "Take"))
                Timing.waitCondition(() -> Inventory.find(ItemID).length == stock.length + 1, 9000);
        }
    }


    private static JFrame findTRiBotFrame() {
        Frame[] frames = JFrame.getFrames();
        for (Frame tempFrame : frames) {
            if (tempFrame.getTitle().contains("TRiBot")) {
                return (JFrame) tempFrame;
            }
            General.sleep(100);
        }
        return null;
    }

    public static void minimizeClient() {
        java.util.Objects.requireNonNull(findTRiBotFrame()).setState(Frame.ICONIFIED);
    }


    public static boolean clickObject(String objectName, String action, boolean useAccurateMouse) {
        RSObject[] obj = Entities.find(ObjectEntity::new)
                .nameContains(objectName)
                .actionsContains(action)
                .sortByDistance()
                .getResults();

        if (obj.length > 0) {
            return clickObj(obj[0], action, useAccurateMouse);
        }
        System.out.println("[Utils]: Cannot find obj based on passed string " + objectName); //don't want to print to client
        return false;
    }

    public static boolean clickObject(int objectId, String action, boolean useAccurateMouse) {
        RSObject[] obj = Entities.find(ObjectEntity::new)
                .idEquals(objectId)
                .actionsContains(action)
                .sortByDistance()
                .getResults();

        if (obj.length > 0)
            return clickObj(obj[0], action, useAccurateMouse);

        System.out.println("[Utils]: Cannot find obj based on passed string " + objectId); //don't want to print to client
        return false;
    }

    public static boolean clickObject(RSObject obj, String action, boolean useAccurateMouse) {
        if (obj != null) {

            handleUnclickableObj(obj);

            if (Utils.unselectItem())
                General.sleep(General.randomSD(20, 200, 100, 50));
            if (useAccurateMouse)
                return AccurateMouse.click(obj, action);
            else
                return DynamicClicking.clickRSObject(obj, action);
        }
        System.out.println("[Utils]: Cannot find obj based on passed RSObj "); //don't want to print to client
        return false;
    }


    public static boolean closeQuestCompletionWindow() {
        Optional<Widget> questWindow = Query.widgets().inIndexPath(QUEST_WIDGET_PARENT).actionContains("Close").findFirst();
        return questWindow.map(Widget::click).orElse(false);
    }


    public static void continuingChat() { // another method for continuing chat when Dax's NPCInteraction doesn't work
        if (Interfaces.isInterfaceSubstantiated(233, 3)) {
            Keyboard.holdKey(scripts.Keyboard.SPACE_KEY_CHAR, scripts.Keyboard.SPACE_KEY_CODE,
                    () -> !Interfaces.isInterfaceSubstantiated(233, 3));

        }
        if (Interfaces.isInterfaceSubstantiated(11, 4)) {
            Keyboard.holdKey(scripts.Keyboard.SPACE_KEY_CHAR, scripts.Keyboard.SPACE_KEY_CODE,
                    () -> !Interfaces.isInterfaceSubstantiated(11, 4));
        }
        if (Interfaces.isInterfaceSubstantiated(229, 2)) {
            Keyboard.holdKey(scripts.Keyboard.SPACE_KEY_CHAR, scripts.Keyboard.SPACE_KEY_CODE,
                    () -> !Interfaces.isInterfaceSubstantiated(229, 2));
        }
    }

    public static boolean useItemOnItem(int ItemID, int ItemID2) {
        Optional<InventoryItem> item1 = Query.inventory().idEquals(ItemID).findClosestToMouse();
        Optional<InventoryItem> item2 = Query.inventory().idEquals(ItemID2).findFirst();
        if (item1.isEmpty() || item2.isEmpty()) {
            Log.log("[Utils]: Missing item with either id: " + ItemID + " or ID: " + ItemID2);
            return false;
        }
        Log.log("[Utils]: Using " + getItemName(ItemID) + " on " + getItemName(ItemID2));
        return item1.map(i -> i.useOn(item2.get())).orElse(false);
    }



  /*  public static void setNPCAttackPreference(boolean hidden) {
        if (Interfaces.get(116, 7, 4) != null) { // don't use is substantiated

            if (!hidden && Interfaces.get(116, 7, 4).getText().contains("Left-click where available")) {
                    Log.log("[Utils]: Attack preferences already set");
                return;
            } else if (hidden && Interfaces.get(116, 7, 4).getText().contains("Hidden")) {
                    Log.log("[Utils]: Attack preferences already set to Hidden");
                return;
            }
        }
            Log.log("[Utils]: Setting NPC Attach preferences");
        if (GameTab.getOpen() != GameTab.TABS.OPTIONS) {
            GameTab.open(GameTab.TABS.OPTIONS);
            General.sleep(General.randomSD(100, 750, 400, 150));
        }
        if (GameTab.getOpen() == GameTab.TABS.OPTIONS) {

            if (Interfaces.get(109, 6) != null) { // selects tab
                Interfaces.get(109, 6).click();
                General.sleep(General.randomSD(100, 750, 400, 150));
            }

            if (Interfaces.get(116, 7, 3) != null) { // selects drop down
                Interfaces.get(116, 7, 3).click();
                General.sleep(General.randomSD(100, 750, 400, 150));
            }
            if (hidden) {
                InterfaceUtil.clickInterfaceText(116, 36, "Hidden");
                General.sleep(General.randomSD(100, 750, 400, 150));

            } else if (Interfaces.get(116, 36, 3) != null) { // selects 'left click when availible'
                InterfaceUtil.clickInterfaceText(116, 36, "Left-click where available");
                General.sleep(General.randomSD(100, 750, 400, 150));
            }
        }
    } */

    public static RSTile getCornerTile(RSArea area) {

        RSTile[] allTiles = area.getAllTiles();

        RSTile cornerTile = allTiles[0];

        Log.log("[Debug]: Corner tile is: " + allTiles[0]);
        return cornerTile;

    }

    public static void debug(String words) {
        Log.log("[Debug]: " + words);
    }

    public static void debug(String words, String stageVar) {
        Log.log("[Debug]: " + words);
        stageVar = words;
    }

    public static int getNotedItemID(int ItemID) {
        RSItemDefinition id = RSItemDefinition.get(ItemID);
        return id != null ? id.getNotedItemID() : -1;
    }

    ArrayList<Integer> toBuy = new ArrayList<>();
    ArrayList<Integer> invList = new ArrayList<>();

    public void checkInventoryItems(ArrayList<Integer> list) {
        RSItem[] invItems = Inventory.getAll();
        toBuy.clear();

        for (int i = 0; i < invItems.length; i++) {

            for (int d = 0; d < list.size(); d++) {
                if (invItems[i].getID() == list.get(d))
                    return;
            }

            toBuy.add(invItems[i].getID());
        }

    }

    private static Point getCenter(Rectangle rectangle) {
        return new Point(rectangle.x + rectangle.width / 2, rectangle.y + rectangle.height / 2);
    }

    /**
     * Goal is to use these for bone voyage
     * get the points and then the colour to see if hte arrow has rotated
     *
     * @param rectangle
     * @return
     */
    public static Point getTopLeftThird(Rectangle rectangle) {
        return new Point(rectangle.x + rectangle.width / 3, rectangle.y + (2 * (rectangle.height / 3)));
    }

    public static Point getTopRightThird(Rectangle rectangle) {
        return new Point(rectangle.x + (2 * (rectangle.width / 3)), rectangle.y + (2 * (rectangle.height / 3)));
    }

    public static Point getBottomRightThird(Rectangle rectangle) {
        return new Point(rectangle.x + (2 * (rectangle.width / 3)), rectangle.y + rectangle.height / 3);
    }

    public static Point getBottomLeftThird(Rectangle rectangle) {
        return new Point(rectangle.x + rectangle.width / 3, rectangle.y + rectangle.height / 3);
    }

    public static Color getInterfaceColor(int parentId, int childId) {
        RSInterface intfce = Interfaces.get(parentId, childId);
        if (Interfaces.isInterfaceSubstantiated(parentId, childId)) {
            Point pnt = getBottomLeftThird(Interfaces.get(parentId, childId).getAbsoluteBounds());
            return Screen.getColorAt(pnt);
        }
        return null;
    }


    public static Image drawNPC(int id) {
        String base = "https://raw.githubusercontent.com/osrsbox/osrsbox-db/master/docs/items-icons/";
        String num = String.valueOf(id);
        String whole = base + num + ".png";
        if (Interfaces.get(122) != null) {
            //     Log.log(whole);
            Image img = getItemImage(whole);
            if (img != null)
                return img;
        }
        Log.log("[Utils]: null error getting npc image");
        return null;
    }

    //https://github.com/osrsbox/osrsbox-db/blob/master/docs/items-icons/10000.png
    public static Image getItemImage(String url) {
        try {
            return ImageIO.read(new URL(url));
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * Determines if an object is being interacted with by the player
     *
     * @author wastedbro
     */
    public static boolean isInteractingWithObject(RSObject object) {
        RSPlayer player = Player.getRSPlayer();
        return player.getAnimation() > -1 && Arrays.stream(object.getAllTiles()).anyMatch(t -> isLookingTowards(player, t, 1));
    }

    public static boolean isInteractingWithNPC(RSNPC[] npc) {
        RSPlayer player = Player.getRSPlayer();
        return player.getAnimation() > -1 && Arrays.stream(npc).anyMatch(t -> isLookingTowards(player, t, 1));
    }

    public static boolean isInteractingWithNPC(RSNPC npc) {
        RSPlayer player = Player.getRSPlayer();
        return player.getAnimation() > -1 && isLookingTowards(player, npc.getPosition(), 1);
    }

    /**
     * Checks if the animable entity is looking towards a positionable
     *
     * @author wastedbro
     */
    public static boolean isLookingTowards(RSAnimableEntity animableEntity, Positionable positionable, int maxDist) {
        if (maxDist > 0 && positionable.getPosition().distanceTo(animableEntity) > maxDist) return false;

        final int orientation = (int) Math.round(animableEntity.getOrientation() / 256.0);
        final int dx = animableEntity.getPosition().getX() - positionable.getPosition().getX();
        final int dy = animableEntity.getPosition().getY() - positionable.getPosition().getY();
        switch (orientation) {
            case 0: //south
                return dx == 0 && dy > 0;
            case 1: //south - west
                return dx > 0 && dy > 0 && dx == dy;
            case 2: //west
                return dx > 0 && dy == 0;
            case 3: //north - west
                return dx > 0 && dy < 0 && Math.abs(dx) == Math.abs(dy);
            case 4: //north
                return dx == 0 && dy < 0;
            case 5: //north - east
                return dx < 0 && dy < 0 && dx == dy;
            case 6: //east
                return dx < 0 && dy == 0;
            case 7: //south-east
                return dx < 0 && dy > 0 && Math.abs(dx) == Math.abs(dy);
        }
        return false;
    }

    public static void setCameraAngle() {
        if (Camera.getCameraAngle() < 45) {
            int angle = General.random(90, 100);
            Log.log("[Utils]: Setting Camera Angle to " + angle);
            Camera.setCameraAngle(angle);
        }
    }

    public static Optional<LocalTile> getWalkableTile(LocalTile tile) {
        return Query.tiles()
                .inArea(Area.fromRadius(tile, 1))
                .filter(LocalTile::isWalkable)
                .findBestInteractable();
    }


    public static boolean adjustCameraToObj(RSObject obj) {
        if (!obj.isClickable()) {
            if (obj.getPosition().distanceTo(Player.getPosition()) > General.random(8, 12)) {
                Log.log("[Utils]: Object is far away, walking closer");
                RSTile tile = obj.getPosition();
                PathingUtil.walkToArea(getObjectRSArea(obj));
            }

            DaxCamera.focus(obj);
            return obj.isClickable();
        }
        return obj.isClickable();
    }

    public static boolean adjustCameraToObj(RSObject obj, boolean shouldWalk) {
        if (!obj.isClickable()) {

            if (shouldWalk &&
                    obj.getPosition().distanceTo(Player.getPosition()) > General.random(8, 12)) {
                Log.log("[Utils]: Object is far away, walking closer");
                PathingUtil.walkToArea(getObjectRSArea(obj), false);
            }

            DaxCamera.focus(obj);
            return obj.isClickable();
        }
        return obj.isClickable();
    }

    /**
     * Null checks the var bit and then returns the value to save space
     *
     * @param varBit
     * @return varbit value; -1 if null
     */
    public static int getVarBitValue(int varBit) {
        if (RSVarBit.get(varBit) != null)
            return RSVarBit.get(varBit).getValue();

        return -1;
    }

    public static boolean afk(Timer timer, int lengthMS) {
        if (!timer.isRunning()) {
            int lengthSec = lengthMS / 1000;
            Log.log("[Utils]: AFKing for " + lengthSec + "s");
            Mouse.leaveGame();
            Timer.waitCondition(() -> Combat.isUnderAttack(), lengthMS, lengthMS + 1);
            return true;
        }
        return false;
    }

    public static void afk(int lengthMS) {
        int lengthSec = lengthMS / 1000;
        Log.log("[Utils]: AFKing for " + lengthSec + "s");
        Mouse.leaveGame();
        Timer.waitCondition(() -> Combat.isUnderAttack(), lengthMS, lengthMS + 1);
    }

    public static void dropItem(int item) {
        Log.log("[Utils]: Dropping item(s)");
        Inventory.drop(item);
        unselectItem();
    }


    public static void dropItem(int[] item) {
        Log.log("[Utils]: Dropping items");
        Inventory.drop(item);
        unselectItem();
    }

    /**
     * @return
     */
    public static boolean unselectItem() {
        if (Game.getItemSelectionState() == 1) {

            String itemName = Game.getSelectedItemName();
            Log.log("[Utils]: Un-select item failsafe activated, clicking item to unselect");
            if (itemName != null) {
                RSItem[] invItem = Inventory.find(itemName);
                if (invItem.length > 0 && invItem[0].click())
                    return Timer.waitCondition(() ->
                            Game.getItemSelectionState() == 0, 1500, 2500);
            }
        }
        return Game.getItemSelectionState() == 0;
    }


    public static boolean hoverXp(Skills.SKILLS skill, int chance) {
        int num = General.random(0, 100);
        if (chance > num) {
            Log.log("[Antiban]: Hovering XP");
            skill.hover();
            General.sleep(300, 1200);
            return true;
        }
        return false;
    }

    public static String getItemName(int id) {
        RSItemDefinition def = RSItemDefinition.get(id);
        return def != null ? def.getName() : "";
    }

    public static void setCamera(int angle, int angleAllowance, int rotation, int rotnAllowance) {
        int a = Camera.getCameraAngle();
        Log.log("Angle is " + a);
        if (Camera.getCameraRotation() > (rotation + rotnAllowance) ||
                Camera.getCameraRotation() < (rotation - rotnAllowance)) {
            int half = rotnAllowance / 2;
            int i = General.random(rotation - half, rotation + half);
            Log.log("[Camera]: Setting camera rotation to " + i);
            Camera.setCameraRotation(i);
        }
        if (a > (angle + angleAllowance) ||
                a < (angle - angleAllowance)) {
            if (a < (angle - angleAllowance)) {
                Log.log("Debug]: Angle is less than " + (angle - angleAllowance));
            }
            int half = angleAllowance / 2;
            int i = General.random(angle - half, angle + half);
            Log.log("[Camera]: Setting camera angle to " + i);
            Camera.setCameraAngle(i);
        }
    }

    public static boolean handleDoor(RSTile doorTile, boolean open) {
        RSObject[] doorTarget = Objects.findNearest(20, Filters.Objects.tileEquals(doorTile));
        if (doorTarget.length > 0) {

            for (int i = 0; i < 5; i++) {
                adjustCameraToObj(doorTarget[0]);

                if (Doors.handleDoor(doorTarget[0], open)) {
                    Log.log("[Utils]: Door handled");
                    return true;

                } else {
                    Log.log("[Utils]: Failed to handle door, waiting up to 2s and trying again");
                    General.sleep(General.random(500, 2500));
                }
            }

        }
        return false;
    }

    public static void setGameSettings() {
        if (Game.getSetting(281) == 1000) {
            if (Game.getRoofsEnabledStatus() != Game.RoofStatus.HIDDEN) {
                Options.setRemoveRoofsEnabled(true);
                Utils.shortSleep();
            }

            if (!Options.isShiftClickDropEnabled()) {
                Options.setShiftClickDropEnabled(true);
                Utils.shortSleep();
            }

            if (Options.isResizableModeEnabled()) {
                Options.setResizableModeEnabled(false);
                Utils.shortSleep();
            }

            if (!Options.isShiftClickDropEnabled()) {
                Options.setShiftClickDropEnabled(true);
                Utils.shortSleep();
            }
        }
    }

    public static boolean openDisplayOptionsTab() {
        if (Interfaces.isInterfaceSubstantiated(261, 1, 0)) {
            if (Interfaces.get(261, 1, 0).getTextureID() == 761)  // the screen tab is NOT selected
                return Interfaces.get(261, 1, 0).click();

            else return true;
        }
        return false;
    }


    public static boolean isItemInInventory(int item) {
        RSItem[] inv = Inventory.find(item);
        return inv.length > 0;
    }

    public static void setNPCAttackPreference() {
        //tries 3x to set
        for (int i = 0; i < 3; i++) {
            //already set, break;
            if (GameState.getSetting(1306) == 2)
                return;

            Log.log("[Utils]: Setting NPC Attach preferences");
            if (GameTab.getOpen() != GameTab.TABS.OPTIONS) {
                GameTab.open(GameTab.TABS.OPTIONS);
                General.sleep(General.randomSD(100, 750, 400, 150));
            }

            if (GameTab.getOpen() == GameTab.TABS.OPTIONS) {
                // not in the right options tab (gear one)
                if (Utils.getVarBitValue(9683) != 0) {
                    if (Interfaces.get(109, 6) != null) { // selects tab in options
                        Interfaces.get(109, 6).click();
                        Timer.waitCondition(() -> Utils.getVarBitValue(9683) == 0, 1750, 2250);
                    }
                }

                if (Interfaces.get(116, 7, 3) != null) { // selects drop down
                    Interfaces.get(116, 7, 3).click();
                    General.sleep(General.randomSD(100, 750, 400, 150));
                }

                Optional<Widget> first = Query.widgets()
                        .textContains("Left-click where available").findFirst();
                Log.debug("is button present: " + first.isPresent());
                if (first.map(f -> f.click("Select")).orElse(false)) {
                    Timer.waitCondition(() -> GameState.getSetting(1306) == 2, 2250, 2750);
                    break;
                }
            }
        }
    }

    public static boolean waitCondtion(BooleanSupplier condition) {
        return Timing.waitCondition(() -> {
            General.sleep(100, 400);
            return (condition.getAsBoolean());
        }, General.random(4200, 6500));
    }

    public static void microSleep() {
        int sleepTime = General.randomSD(100, 1000, 500, 200);
        Log.log("[Debug]: Sleeping for " + sleepTime + "ms");
        General.sleep(sleepTime);
    }

    public static void shortSleep() {
        int sleepTime = General.randomSD(1000, 3200, 1600, 200);
        Log.log("[Debug]: Sleeping for " + sleepTime + "ms");
        General.sleep(sleepTime);
    }

    public static void modSleep() {
        int sleepTime = General.randomSD(2500, 8000, 5000, 600);
        Log.log("[Debug]: Sleeping for " + sleepTime + "ms");
        General.sleep(sleepTime);
    }

    public static void longSleep() {
        int sleepTime = General.randomSD(8000, 20000, 14000, 2500);
        Log.log("[Debug]: Sleeping for " + sleepTime + "ms");
        General.sleep(sleepTime);
    }

    public static boolean handleRecoilMessage(String message) {
        if (message.contains("Your Ring of Recoil has shattered.")) {
            Log.log("[Message Listener]: Ring of recoil shattered message received.");
            //  if (Inventory.find(Constants.RING_OF_RECOIL).length > 0)
            //      return AccurateMouse.click(Inventory.find(Constants.RING_OF_RECOIL)[0], "Wear");

        }
        return false;
    }

    public static String removeWord(String string, String word) {

        if (string.contains(word)) {
            String tempWord = word + " ";
            string = string.replaceAll(tempWord, "");
            tempWord = " " + word;
            string = string.replaceAll(tempWord, "");
        }
        return string;
    }

    public static void drinkPotion(ArrayList<String> potionList) {

        for (String potionName : potionList) {

            int actualLvl = Skills.getActualLevel(PotionEnum.valueOf(potionName.toUpperCase()).getCorrespondingSkill());
            int currentLvl = Skills.getCurrentLevel(PotionEnum.valueOf(potionName.toUpperCase()).getCorrespondingSkill());

            if (currentLvl <= actualLvl) {

                Predicate<RSItem> potion = Filters.Items.nameContains(potionName);

                if (potion != null) {

                    RSItem[] item = Inventory.find(potion);

                    if (item != null && item.length > 0) {

                        if (item[0].click("Drink"))
                            Utils.waitCondtion(() -> Player.getAnimation() == 829);
                    }
                }
            }
        }
    }

    public static boolean drinkPotion(int[] potionList) {
        for (Integer potionName : potionList) {
            Predicate<RSItem> potion = Filters.Items.idEquals(potionName);
            RSItem[] item = Inventory.find(potion);
            if (item != null && item.length > 0) {
                if (item[0].click("Drink"))
                    return Utils.waitCondtion(() -> Player.getAnimation() == 829);
            }
        }
        return false;
    }

    public static double getLevelBoost(Skill skill) {
        int lvl = skill.getActualLevel();
        double boost = Math.floor(lvl * .10 + 4);
        //        Log.log("[Utils]: Boost is " + Math.round(boost));
        return Math.round(boost);
    }
    public static double getLevelBoost(Skills.SKILLS skill) {
        int lvl = skill.getActualLevel();
        double boost = Math.floor(lvl * .10 + 4);
        //        Log.log("[Utils]: Boost is " + Math.round(boost));
        return Math.round(boost);
    }


    public static boolean potionHandler(HashMap<Integer, Integer> map, ArrayList<String> list) {

        for (Entry<Integer, Integer> entry : map.entrySet()) {

            int ItemID = entry.getKey();
            String itemName = RSItemDefinition.get(ItemID).getName();

            for (String name : NAMES_TO_REMOVE) {

                if (itemName.contains(name))
                    itemName = Utils.removeWord(itemName, name);
            }

            for (PotionEnum potion : PotionEnum.values()) {

                int spacePos = itemName.indexOf(" ");

                if (spacePos > 0) {
                    String itemNameTrimed = itemName.substring(0, spacePos);

                    if (itemNameTrimed.equalsIgnoreCase(potion.toString()))
                        list.add(itemNameTrimed);
                }
            }
        }
        return !list.isEmpty();
    }


    public boolean enableRun() {
        if (!Options.isRunEnabled())
            return Options.setRunEnabled(true);

        else return false;
    }

    public static void idlePredictableAction() {
        int sleep = (int) ReactionGenerator.get().nextReactionTime(30, 10, 0.02, 0.1,
                30, 500);
        //System.out.println("[Debug]: Sleeping (predictable rxn) for: " + sleep);
        Waiting.wait(sleep);
    }

    public static void idleNormalAction() {
        int sleep = (int) ReactionGenerator.get().nextReactionTime(200, 50, 0.007, 0.2,
                100, 2000);
       // Log.log("[Debug]: Sleeping (normal rxn) for: " + sleep);
        Waiting.wait(sleep);
    }

    public static void idleAfkAction() {
        int sleep = (int) ReactionGenerator.get().nextReactionTime(5000, 2000, 5, 0.1,
                200, 10000);
        Log.info("[Utils]: Sleeping (afk rxn) for: " + sleep);
        Waiting.wait(sleep);
    }

    public static void afkSleepWithBreak(BooleanSupplier supplier) {
        int sleep = (int) ReactionGenerator.get().nextReactionTime(5000, 2000, 5, 0.1,
                200, 10000);
        Log.info("[Utils]: Sleeping (afk rxn) for: " + sleep);
        Waiting.waitUntil(sleep, 75 , supplier);
    }



    public static void idle(int low, int high) {
        int sleep = General.random(low, high);
        Log.log("[Debug]: Sleeping for: " + sleep);
        General.sleep(sleep);
    }


    public static void breakUtil(Timer timer, Script script) { // breaks for 10-20min
        if (!timer.isRunning()) {
            int length = General.random(600000, 1200000);
            int min = length / 60000;
            script.setLoginBotState(false);
            Log.log("[Debug]: Breaking for ~" + min + "min");
            General.sleep(length);
            timer.reset();

            Log.log("[Debug]: Resuming from break");
            script.setLoginBotState(true);
            Login.login();

        }
    }

    public static void breakUtil(Timer timer) { // breaks for 10-20min
        if (!timer.isRunning()) {
            int length = General.random(600000, 1200000);
            int min = length / 60000;
            Log.log("[Debug]: Breaking for ~" + min + "min");
            General.sleep(length);
            timer.reset();
            Log.log("[Debug]: Resuming from break");
            Login.login();

        }
    }


    public static boolean waitCondtion(BooleanSupplier condition, int min, int max) {
        return Timing.waitCondition(() -> {
            General.sleep(80, 350);
            return (condition.getAsBoolean());
        }, General.random(min, max));
    }


    public static Integer getPlayerCount() {
        RSPlayer[] players = Players.getAll();
        int playersAmount = players.length - 1;
        return playersAmount;
    }


    public static RSNPC reachableNpc(String name) {
        RSNPC[] targets = NPCs.findNearest(name);
        if (targets != null && targets.length > 0) {
            for (RSNPC target : targets) {
                if (!target.isInCombat() && (PathFinding.canReach(target.getPosition(), false))) {
                    return target;
                }
            }
            return null;
        } else {
            return null;
        }
    }

    // auto retalite turn on method


    public static int average(List<Integer> times) {
        Integer total = 0;
        if (!times.isEmpty()) {
            for (Integer i : times)
                total += i;
            return total.intValue() / times.size();
        }
        return total;
    }


    public static List<Integer> sleep(List<Integer> waitTimes) {
        if (waitTimes.isEmpty())
            AntiBan.generateTrackers(General.random(500, 1600), false);

        if (!waitTimes.isEmpty())
            AntiBan.generateTrackers(average(waitTimes), false);

        final int reactionTime = (int) (AntiBan.getReactionTime() * FACTOR);
        waitTimes.add(reactionTime);
        Log.log("[ABC2 Delay]: Sleeping for " + reactionTime + "ms");
        AntiBan.sleepReactionTime(reactionTime);
        return waitTimes;

    }

    /**
     * call in the sleep() above instead of "sleepReactionTime()"
     * this will exit sleep if it needs to eat, prevents dying on sleeps
     * Uses the determined ABC2 sleep length
     *
     * @param sleepTime
     */
    private static void abc2CombatSleep(int sleepTime) {
        Timer timer = new Timer(sleepTime);
        Timer.waitCondition(() -> !timer.isRunning() || Combat.getHPRatio() < 20, sleepTime, sleepTime + 500);

        if (Combat.getHPRatio() < 20) {
            EatUtil.eatFood();
        }

    }

    public static boolean clickInventoryItem(int ItemID) {
        RSItem[] item = Inventory.find(ItemID);
        if (item.length > 0) {
            if (item[0].click()) {
                Waiting.waitUniform(25, 150);
                return true;
            }
        }
        return false;
    }


    public static ArrayList<Integer> worldsAttempted = new ArrayList<>();

    public static boolean hopWorlds() {
        int world = WorldHopper.getRandomWorld(true);
        for (int i = 0; i < worldsAttempted.size(); i++) {
            if (worldsAttempted.get(i) == world) {
                world = WorldHopper.getRandomWorld(true);
            }
        }
        worldsAttempted.add(world);
        int finalWorld = world;
        WorldHopper.changeWorld(world);
        return Timer.waitCondition(() -> WorldHopper.getWorld() == finalWorld, 15000, 25000);
    }

    public static void abc2ReactionSleep(long time, boolean eatIfLowHp) {
        if (Combat.getHPRatio() < 40 && eatIfLowHp) {
            EatUtil.eatFood();
            return;
        }
        AntiBan.generateTrackers((int) (System.currentTimeMillis() - time), false);
        abc2waitTimes.add(AntiBan.getReactionTime());
        abc2waitTimes = Utils.sleep(abc2waitTimes);
    }


    public static void abc2ReactionSleep(long time) {
        if (Combat.getHPRatio() < 40) {
            EatUtil.eatFood();
            return;
        }
        RSCharacter target = Combat.getTargetEntity();
        if (target != null && target.getHealthPercent() != 0) {
            Log.log("[ABC2]: Skipping sleep as we are still in combat");
            Utils.shortSleep();
            return;
        }
       /* if (Combat.isUnderAttack()) {
                Log.log("[ABC2]: Skipping sleep as we are still in combat");
            Utils.microSleep();
            return;
        } */

        AntiBan.generateTrackers((int) (System.currentTimeMillis() - time), false);
        abc2waitTimes.add(AntiBan.getReactionTime());
        abc2waitTimes = Utils.sleep(abc2waitTimes);
    }

    public static int getInventoryValue() {
        int value = 0;
        RSItem[] inv = Inventory.getAll();
        for (RSItem i : inv) {
            if (i.getDefinition().isNoted()) {
                int stack = i.getStack();

                value = value + scripts.rsitem_services.GrandExchange.getPrice(i.getID() - 1) * stack;

            } else if (i.getDefinition().isStackable()) {
                int stack = i.getStack();
                value = value + (scripts.rsitem_services.GrandExchange.getPrice(i.getID()) * stack);
            } else
                value = value + scripts.rsitem_services.GrandExchange.getPrice(i.getID());
        }

        return value;
    }

    /**
     * Not complete
     *
     * @return
     */
    public static boolean turnOnEscToClose() {
        if (Interfaces.isInterfaceSubstantiated(134, 17, 141)) {
            Mouse.moveBox(Interfaces.get(134).getAbsoluteBounds());
            Mouse.scroll(false);
            if (Interfaces.get(134, 17, 141).getTextureID() == 2847) {
                Interfaces.get(134, 17, 141).click();
            }
            return Timing.waitCondition(() -> Interfaces.get(134, 17, 141).getTextureID() == 2848, 5000);

        }
        return false;
    }

    public static String addCommaToNum(int num) {
        return NumberFormat.getNumberInstance(Locale.US).format(num);
    }

    public static String addCommaToNum(long num) {
        return NumberFormat.getNumberInstance(Locale.US).format(num);
    }

    public static boolean equipItem(int[] itemArray) {
        Optional<InventoryItem> closestToMouse = Query.inventory().idEquals(itemArray).findClosestToMouse();
        return equipItem(closestToMouse);
    }

    public static boolean equipItem(int ItemID) {
        Optional<InventoryItem> closestToMouse = Query.inventory().idEquals(ItemID).findClosestToMouse();
        return equipItem(closestToMouse);
    }

    public static RSTile[] getContainedTiles(RSNPC rsnpc) {
        RSArea searchArea = new RSArea(rsnpc.getPosition(), 5);

        return Arrays.asList(searchArea.getAllTiles())
                .parallelStream()
                .unordered()
                .filter(tile -> NPCs.isAt(tile, rsnpc.getID()))
                .toArray(RSTile[]::new);
    }

    public static boolean equipItem(Optional<InventoryItem> itemOptional) {
        if (itemOptional.isPresent()) {
            if (!itemOptional.get().getDefinition().isStackable() &&
                    Equipment.isEquipped(itemOptional.get().getId())) {
                //already equipped and not stackable (e.g. arrows)
                return true;
            }
            ItemDefinition def = itemOptional.get().getDefinition();
            if (def.getActions().stream().anyMatch(s -> s.contains("Wield")) &&
                    itemOptional.map(i -> i.click("Wield")).orElse(false)) {
                return Timer.waitCondition(() -> Equipment.isEquipped(def.getId()), 1200, 2000);
            } else if (def.getActions().stream().anyMatch(s -> s.contains("Wear")) &&
                    itemOptional.map(i -> i.click("Wear")).orElse(false)) {
                return Timer.waitCondition(() -> Equipment.isEquipped(def.getId()), 1200, 2000);
            } else if (itemOptional.map(InventoryItem::click).orElse(false)) {
                return Timer.waitCondition(() -> Equipment.isEquipped(def.getId()), 1200, 2000);
            }
        }
        return false;
    }

    public static String getObjectName(RSObject obj) {
        RSObjectDefinition def = obj.getDefinition();
        if (def != null) {
            Optional<String> name = Optional.ofNullable(def.getName());
            return name.orElse("");
        }
        return "";
    }

    /**
     * @param skill you want to get the boost for
     * @return the MAX boosted skill level
     */
    public static double getSuperLevelBoost(Skills.SKILLS skill) {
        int lvl = skill.getActualLevel();
        double boost = Math.floor(lvl * 0.15 + 5);
        //        Log.log("[Utils]: Boost is " + Math.round(boost));
        return Math.round(boost);
    }

    public static double getSuperLevelBoost(Skill skill) {
        int lvl = skill.getActualLevel();
        double boost = Math.floor(lvl * 0.15 + 5);
        //        Log.log("[Utils]: Boost is " + Math.round(boost));
        return Math.round(boost);
    }


    public static String getItemName(RSItem item) {
        RSItemDefinition def = item.getDefinition();
        if (def != null) {
            String name = def.getName();
            if (name != null)
                return name;
        }
        return "";
    }

    public static boolean equipItem(int ItemID, String method) {
        RSItem[] inv = Inventory.find(ItemID);
        if (inv.length > 0 && inv[0].click(method))
            return Timer.waitCondition(() -> Equipment.isEquipped(ItemID), 2000, 4000);

        return Equipment.isEquipped(ItemID);
    }

    public static int getCombatLevel() {
        RSPlayer me = Player.getRSPlayer();

        if (me != null)
            return me.getCombatLevel();

        return -1;
    }

    public static String getItemNameFromID(int id) {
        RSItemDefinition item = RSItemDefinition.get(id);
        if (item != null) {
            return item.getName();
        } else
            return "";
    }

    public static String getCharacterName() {
        String name = Player.getRSPlayer().getName();

        if (name != null)
            return name; // returns name or null

        return "Failed to get name: ";
    }

    /**
     * component 0 = fishing guild
     * 4 = WC guild
     * 5 = farming guild
     *
     * @param locationComponent
     * @return if successfully teleported
     */


    public static int SETTINGS_TAB_PARENT_ID = 116;
    public static int SETTINGS_WINDOW_PARENT = 134;
    public static int ALL_SETTINGS_BUTTON = 26;

    public static boolean openInterface(int parent, int child) {
        if (Interfaces.isInterfaceSubstantiated(parent, child))
            return Interfaces.get(parent, child).click();

        return false;
    }

    public static boolean openSettingsWindow() {
        if (Interfaces.isInterfaceSubstantiated(SETTINGS_WINDOW_PARENT)) {
            return true;
        } else {
            GameTab.open(GameTab.TABS.OPTIONS);

            if (openInterface(SETTINGS_TAB_PARENT_ID, 59)) // clicks the tab with the gear on it
                Utils.microSleep();

            return openInterface(SETTINGS_TAB_PARENT_ID, ALL_SETTINGS_BUTTON);
        }
    }


    public boolean checkForInventoryItems(ArrayList<Integer> ItemIDList) {
        for (Integer i : ItemIDList) {
            if (Inventory.find(i).length == 0) {
                RSItemDefinition def = RSItemDefinition.get(i);
                if (def != null) {
                    Log.log("[Utils]: Missing and item: " + def.getName());
                    return false;
                }
                return false;
            }
        }
        Log.log("[Utils]: We have all items in ArrayList (x1)");
        return true;
    }

    public static void forceDownwardCameraAngle() {
        if (org.tribot.script.sdk.Camera.getAngle() < 80) {
            Log.debug("[Utils]: Adjusting camera angle");
            org.tribot.script.sdk.Camera.setAngle(General.random(80, 100));
        }
    }



    public static boolean setCameraZoomAboveDefault() {
        int setTo = General.random(285, 320);
        if (Game.getViewportScale() > setTo) {
            Log.log("[Utils]: Setting camera zoom");
            RSInterface middle = Interfaces.get(163);
            if (!Game.hasFocus()) {
                Mouse.randomRightClick();
                General.sleep(100, 400);
            }

            if (middle != null && !middle.getAbsoluteBounds().contains(Mouse.getPos())) {
                int x = (Game.getViewportWidth() / 2) + General.random(0, 100);
                int y = (Game.getViewportHeight() / 2) + General.random(0, 100);
                Log.log("Width is " + Game.getViewportWidth());
                Point p = middle.getAbsolutePosition();
                p.translate(x, y);
                Mouse.move(p);
                General.sleep(100, 400);
            }


            for (int i = 0; i < 15; i++) {
                Mouse.scroll(false);
                Waiting.waitNormal(90, 35);
                if (Game.getViewportScale() <= setTo) {
                    return true;
                }
            }
        }
        return Game.getViewportScale() <= setTo;
    }

    public static boolean useItemOnObject(Optional<InventoryItem> invItem, Optional<GameObject> obj) {
        if (invItem.isPresent() && obj.isPresent()) {
            for (int i = 0; i < 3; i++) {
                Log.log("[Debug]: Using: " + invItem.get().getName());

                if (!GameState.isAnyItemSelected()) {
                    if (!obj.get().isVisible() || i == 2)
                        obj.get().adjustCameraTo();
                    if (invItem.get().useOn(obj.get()))
                        return true;

                } else if (isItemSelected(invItem.get().getId())) {
                    Log.log("[Utils]: Item is already selected");
                    if (!obj.get().isVisible() || i == 2)
                        obj.get().adjustCameraTo();

                    if (obj.get().click("Use " + invItem.get().getName()))
                        return true;
                }

            }
        }
        Log.log("[Utils]: failed to use item on object 3x");
        return false;
    }

    public static boolean useItemOnObject(int ItemID, Optional<GameObject> obj) {
        Optional<InventoryItem> invItem = Query.inventory().idEquals(ItemID).findClosestToMouse();
        return useItemOnObject(invItem, obj);
    }

    public static boolean useItemOnObject(int ItemID, int objID) {
        Optional<InventoryItem> invItem = Query.inventory().idEquals(ItemID).findClosestToMouse();
        Optional<GameObject> gameObj = Query.gameObjects().idEquals(objID).sortedByDistance().findBestInteractable();
        if (gameObj.isEmpty() || invItem.isEmpty()) {
            Log.log("[Utils]: Cannot find ItemID: " + ItemID + " || or ObjId: " + objID);
            return false;
        }
        return useItemOnObject(invItem, gameObj);
    }

    public static boolean useItemOnObject(int ItemID, RSObject obj) {
        RSItem[] invItem = Inventory.find(ItemID);
        for (int i = 0; i < 3; i++) {
            if (invItem.length > 0 && obj != null) {
                Log.log("[Debug]: Using: " + ItemID);

                if (!isItemSelected(ItemID)) {

                    if (AccurateMouse.click(invItem[0], "Use"))
                        AntiBan.waitItemInteractionDelay();

                    if (!obj.isClickable() || i == 2)
                        DaxCamera.focus(obj);


                    if (Timing.waitCondition(() -> obj.click(rsMenuNode ->
                            rsMenuNode.getAction().equals("Use") && rsMenuNode.getTarget().toLowerCase().
                                    contains(obj.getDefinition().getName().toLowerCase())), 500))
                        return true;

                } else if (isItemSelected(ItemID)) {

                    if (!obj.isClickable() || i == 2)
                        DaxCamera.focus(obj);

                    if (Timing.waitCondition(() -> obj.click(rsMenuNode ->
                            rsMenuNode.getAction().equals("Use") && rsMenuNode.getTarget().toLowerCase().
                                    contains(obj.getDefinition().getName().toLowerCase())), 500))
                        return true;
                }

            }
        }
        return false;
    }

    public static boolean useItemOnObject(String itemName, int objectId) {
        RSItem[] invItem = Inventory.find(itemName);
        RSObject[] obj = Objects.findNearest(25, objectId);
        if (invItem.length > 0 && obj.length > 0) {
            return useItemOnObject(invItem[0].getID(), obj[0]);
        }
        return false;
    }

    public static boolean useItemOnObject(int ItemID, String objectName) {
        RSItem[] invItem = Inventory.find(ItemID);
        RSObject[] obj = Objects.findNearest(25, objectName);
        if (invItem.length > 0 && obj.length > 0)
            return useItemOnObject(ItemID, obj[0]);

        return false;
    }

    public static boolean useItemOnObject(String itemName, String objectName) {
        RSItem[] invItem = Inventory.find(itemName);
        RSObject[] obj = Objects.findNearest(25, objectName);
        if (invItem.length > 0 && obj.length > 0)
            return useItemOnObject(invItem[0].getID(), obj[0]);

        return false;
    }

    public static int getPlayerCountInArea(RSArea area) {
        RSPlayer[] p = Players.getAll(Filters.Players.inArea(area).and(Filters.Players.nameNotContains(Player.getRSPlayer().getName())));
        return p.length;
    }

    public static int getPlayerCountInArea(Area area) {
        List<org.tribot.script.sdk.types.Player> pl = Query.players().inArea(area)
                .nameNotContains(MyPlayer.getUsername())
                .toList();
        return pl.size();
    }

    public static int getPlayerCountInRadius(int rad) {
        RSArea area = new RSArea(Player.getPosition(), rad);
        RSPlayer[] p = Players.getAll(Filters.Players.inArea(area).and(Filters.Players.nameNotContains(Player.getRSPlayer().getName())));
        return p.length;
    }

    public static String getRuntimeString(long runtime) {
        long millis = runtime;
        long second = (millis / 1000) % 60;
        long minute = (millis / (1000 * 60)) % 60;
        long hour = (millis / (1000 * 60 * 60)); //% 24;
        return String.format("%02d:%02d:%02d", hour, minute, second);
    }

    public static boolean isItemSelected(int ItemID) {
        RSItemDefinition itemDef = RSItemDefinition.get(ItemID);
        if (itemDef != null && Game.getItemSelectionState() == 1) {
            String itemString = itemDef.getName();
            return Game.getSelectedItemName().contains(itemString);
        }
        return Game.getItemSelectionState() == 1;
    }

    public static boolean isItemSelected(String itemName) {
        return Game.getSelectedItemName().contains(itemName);
    }

    public static boolean selectInvItem(int id) {
        RSItem[] invItem = Inventory.find(id);
        if (!isItemSelected(id) && invItem.length > 0) {
            return invItem[0].click("Use");
        }
        return isItemSelected(id);
    }

    public static boolean useItemOnNPC(int ItemID, String NPCName) {
        RSItem[] invItem = Inventory.find(ItemID);
        RSItemDefinition itemDef = RSItemDefinition.get(ItemID);
        String itemString = itemDef.getName();
        RSNPC[] npc = NPCs.findNearest(NPCName);

        if (invItem.length > 0 && npc.length > 0) {
            Log.log("[Debug]: Using: " + itemString);


            if (selectInvItem(invItem[0].getID()))
                AntiBan.waitItemInteractionDelay();

            if (!npc[0].isClickable())
                DaxCamera.focus(npc[0]);

            return Timing.waitCondition(() -> npc[0].click(rsMenuNode ->
                    rsMenuNode.getAction().equals("Use")
                            && rsMenuNode.getTarget().toLowerCase().contains(npc[0].getDefinition().getName().toLowerCase())), 500);

        }

        return false;
    }


    public static boolean useItemOnNPC(int ItemID, RSNPC rsnpc) {
        RSItem[] invItem = Inventory.find(ItemID);
        RSItemDefinition itemDef = RSItemDefinition.get(ItemID);
        String itemString = itemDef.getName();


        if (invItem.length > 0 && rsnpc != null) {
            Log.log("[Debug]: Using: " + itemString);

            if (selectInvItem(invItem[0].getID()))
                AntiBan.waitItemInteractionDelay();

            if (!rsnpc.isClickable())
                DaxCamera.focus(rsnpc);

            return Timing.waitCondition(() -> rsnpc.click(rsMenuNode ->
                    rsMenuNode.getAction().equals("Use")
                            && rsMenuNode.getTarget().toLowerCase().contains(rsnpc.getDefinition().getName().toLowerCase())), 500);


        }
        return false;
    }

    public static int[] reverseIntArray(int[] a) {
        int[] b = new int[a.length];
        int j = a.length;
        for (int i = 0; i < a.length; i++) {
            b[j - 1] = a[i];
            j = j - 1;
        }
        return b;
    }


    public static Optional<Npc> getClosestInteractingNpc() {
        return Query.npcs().isInteractingWithMe().findClosestByPathDistance();
    }

    public static boolean useItemOnNPC(String itemName, String NPCName) {
        RSItem[] invItem = Inventory.find(itemName);
        RSNPC[] npc = NPCs.findNearest(NPCName);

        if (invItem.length > 0) {
            Log.log("[Debug]: Using: " + itemName);
            if (selectInvItem(invItem[0].getID())) {
                AntiBan.waitItemInteractionDelay();

                if (!npc[0].isClickable())
                    DaxCamera.focus(npc[0]);

                return Timing.waitCondition(() -> npc[0].click(rsMenuNode ->
                        rsMenuNode.getAction().equals("Use") && rsMenuNode.getTarget().toLowerCase().
                                contains(npc[0].getDefinition().getName().toLowerCase())), 500);
            }
        }
        return false;
    }


    public static boolean useItemOnNPC(int ItemID, String[] NPCNames) {
        RSItem[] invItem = Inventory.find(ItemID);
        RSNPC[] npc = NPCs.findNearest(NPCNames);

        if (invItem.length > 0) {
            Log.log("[Debug]: Using: " + ItemID);

            if (selectInvItem(ItemID)) {
                AntiBan.waitItemInteractionDelay();

                if (!npc[0].isClickable())
                    DaxCamera.focus(npc[0]);

                return Timing.waitCondition(() -> npc[0].click(rsMenuNode ->
                        rsMenuNode.getAction().equals("Use") && rsMenuNode.getTarget().toLowerCase().
                                contains(npc[0].getDefinition().getName().toLowerCase())), 500);
            }
        }
        return false;
    }

    public static boolean useItemOnNPC(int ItemID, int npcId) {
        Optional<InventoryItem> item = Query.inventory().idEquals(ItemID)
                .findClosestToMouse();
        Optional<Npc> npc = Query.npcs().idEquals(npcId)
                .findBestInteractable();

        if (npc.isEmpty() || item.isEmpty()) {
            Log.log("[Utils]: Cannot find ItemID: " + ItemID + " || or NpcID: " + npcId);
            return false;
        }
        //adjust camera if needed
        return item.get().useOn(npc.get());
    }


    public static boolean useItemOnNPC(int[] ItemID, int NPCID) {
        RSItem[] invItem = Inventory.find(ItemID);
        RSNPC[] npc = NPCs.findNearest(NPCID);
        if (invItem.length > 0) {
            if (invItem[0].click("Use"))
                AntiBan.waitItemInteractionDelay();

            if (npc.length > 0 && isItemSelected(ItemID[0])) {

                if (!npc[0].isClickable())
                    DaxCamera.focus(npc[0]);

                return AccurateMouse.click(npc[0], "Use");
            }
        }
        return false;
    }


    public static HashMap<Skills.SKILLS, Integer> getXpForAllSkills() {
        HashMap<Skills.SKILLS, Integer> xpHashMap = new HashMap<>();
        for (Skills.SKILLS s : Skills.SKILLS.values()) {
            xpHashMap.put(s, s.getXP());
        }
        return xpHashMap;
    }

    public static boolean hasTotalXpIncreased(HashMap<Skills.SKILLS, Integer> startHashMap) {
        HashMap<Skills.SKILLS, Integer> currentHashMap = getXpForAllSkills();

        for (Skills.SKILLS s : startHashMap.keySet()) {
            int currentXp = currentHashMap.get(s);
            if (startHashMap.get(s) < currentXp) {
                //     Log.log("[Debug]: " + s.toString() + " has increased experience since start: " +currentXp );
                return true;
            }
        }
        return false;
    }


    public static boolean shouldDecantPotion(String potionNameBase){
        int oneDose = 0;
        int twoDose = 0;
        int threeDose = 0;
        int totalDoses = 0;

        if (!BankCache.isInitialized()){

        }

        if (potionNameBase.toLowerCase().contains("stamina")){
            oneDose =  BankCache.getStack(ItemID.STAMINA_POTION[3]);
            twoDose =  BankCache.getStack(ItemID.STAMINA_POTION[2]);
            threeDose =  BankCache.getStack(ItemID.STAMINA_POTION[1]);
            totalDoses = (oneDose) + (twoDose*2) + (threeDose *3);
            Log.debug("We have " + totalDoses + " total doses of stamina potions");
            return totalDoses >= 4;
        }
        return false;
    }

}