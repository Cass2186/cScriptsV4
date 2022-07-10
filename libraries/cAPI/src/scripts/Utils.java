package scripts;

import dax.walker.utils.AccurateMouse;
import dax.walker.utils.camera.DaxCamera;
import dax.walker_engine.interaction_handling.NPCInteraction;

import org.tribot.api.DynamicClicking;
import org.tribot.api.General;
import org.tribot.api.interfaces.Positionable;

import org.tribot.api2007.*;
import org.tribot.api2007.Objects;
import org.tribot.api2007.Player;
import org.tribot.api2007.ext.Filters;
import org.tribot.api2007.types.*;
import org.tribot.script.Script;
import org.tribot.script.sdk.*;
import org.tribot.script.sdk.Camera;
import org.tribot.script.sdk.Equipment;
import org.tribot.script.sdk.GameTab;
import org.tribot.script.sdk.Inventory;
import org.tribot.script.sdk.Login;
import org.tribot.script.sdk.Options;
import org.tribot.script.sdk.WorldHopper;
import org.tribot.script.sdk.cache.BankCache;
import org.tribot.script.sdk.input.Mouse;
import org.tribot.script.sdk.interfaces.Tile;
import org.tribot.script.sdk.query.Query;
import org.tribot.script.sdk.types.*;
import org.tribot.script.sdk.types.definitions.ItemDefinition;
import org.tribot.script.sdk.walking.GlobalWalking;
import org.tribot.script.sdk.walking.LocalWalking;
import scripts.EntitySelector.Entities;
import scripts.EntitySelector.finders.prefabs.ObjectEntity;
import scripts.Items.PotionEnum;
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
import java.util.regex.Pattern;
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

    private static final Pattern TAG_REGEXP = Pattern.compile("<[^>]*>");

    /**
     * Removes all tags from the given string.
     *
     * @param str The string to remove tags from.
     * @return The given string with all tags removed from it.
     */
    public static String removeTags(String str) {
        return TAG_REGEXP.matcher(str).replaceAll("");
    }

    /**
     * In addition to removing all tags, replaces all &lt;br&gt; delimited text with spaces and all multiple continuous
     * spaces with single space
     *
     * @param str The string to sanitize
     * @return sanitized string
     */
    public static String sanitizeMultilineText(String str) {
        return removeTags(str
                .replaceAll("-<br>", "-")
                .replaceAll("<br>", " ")
                .replaceAll("[ ]+", " "));
    }

    public static boolean turnEscToCloseOn() {
        if (!Options.isEscapeClosingEnabled() && openSettingsWindow()) {
            Optional<Widget> escOption = Query.widgets().inIndexPath(134, 18)
                    .textContains("Esc closes the current")
                    .findFirst();
            Log.warn("Turning on Esc to Close");
            if (escOption.map(e -> e.scrollTo() && e.click()).orElse(false) &&
                    Waiting.waitUntil(4000, 150,
                            Options::isEscapeClosingEnabled)) {
                return Query.widgets()
                        .actionContains("Close")
                        .findFirst()
                        .map(Widget::click)
                        .orElse(false);
            }

        }
        Log.warn("Turning on Esc to Close Failed");
        return Options.isEscapeClosingEnabled();
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


    public static LocalTile getLocalTileFromRSTile(RSTile tile) {
        return new LocalTile(tile.getX(), tile.getY(), tile.getPlane());
    }

    public static WorldTile getWorldTileFromRSTile(RSTile tile) {
        return new WorldTile(tile.getX(), tile.getY(), tile.getPlane());
    }

    public static RSTile getRSTileFromWorldTile(WorldTile tile) {
        return new RSTile(tile.getX(), tile.getY(), tile.getPlane());
    }

    public static RSTile getRSTileFromLocalTile(LocalTile tile) {
        return new RSTile(tile.getX(), tile.getY(), tile.getPlane());
    }

    public static void setChatScreenConfig(boolean efficient) {
        ChatScreen.setConfig(ChatScreen.Config.builder()
                .holdSpaceForContinue(efficient)
                .useKeysForOptions(efficient)
                .build());
    }

    public static Area getAreaFromRSArea(RSArea area) {
        List<RSTile> sortedTileList =
                Arrays.stream(area.getAllTiles())
                        .sorted(Comparator.comparingInt(RSTile::getX))
                        .sorted(Comparator.comparingInt(RSTile::getY))
                        .collect(Collectors.toList());

        List<Tile> worldTiles = new ArrayList<>();
        for (RSTile t : sortedTileList) {
            worldTiles.add(getWorldTileFromRSTile(t));
        }
        return Area.fromPolygon(worldTiles);
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
        return GameState.getSetting(101);
    }

    public static void cutScene() {
        while (inCutScene()) {
            Log.info("[Debug]: Cutscene Idle");
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

        int num = Utils.random(5, 7);
        for (int i = 0; i < num; i++) {
            // org.tribot.script.sdk.input.Mouse.setClickMethod(org.tribot.script.sdk.input.Mouse.ClickMethod.ACCURATE_MOUSE);
            if (Utils.unselectItem())
                Waiting.wait(General.randomSD(20, 250, 120, 50));

            if (i == 3) { // force camera adjustment if it's failed 2x prior to click
                Log.info("[Utils]: Force focusing camera on NPC");
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

            int num = Utils.random(5, 7);
            for (int i = 0; i < num; i++) {
                if (i == 2) { // force camera adjustment if it's failed 2x prior to click
                    Log.info("[Utils]: Force focusing camera on NPC");
                    DaxCamera.focus(npc);
                }
                if (Utils.unselectItem())
                    Waiting.wait(General.randomSD(20, 250, 120, 50));

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

        int num = Utils.random(4, 6);
        for (int i = 0; i < num; i++) {
            if (i == 2) { // force camera adjustment if it's failed 2x prior to click
                Log.info("[Utils]: Force focusing camera on GameObject");
                obj.get().adjustCameraTo();
            }
            if (Utils.unselectItem())
                Waiting.wait(General.randomSD(20, 250, 120, 50));

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
            if (!Inventory.contains(ItemID.RING_OF_DUELING) &&
                    !Equipment.contains(ItemID.RING_OF_DUELING)) {

                Log.info("[Utils]: Getting Ring of Dueling");
                BankManager.open(true);
                BankManager.withdraw(1, true, ItemID.RING_OF_DUELING);
                BankManager.close(true);
                Utils.shortSleep();
            }
            Optional<EquipmentItem> ring = Query.equipment().idEquals(ItemID.RING_OF_DUELING)
                    .findClosestToMouse();
            Optional<InventoryItem> invRing = QueryUtils.getItem(ItemID.RING_OF_DUELING);
            if (invRing.map(r -> r.click("Ferox Enclave")).orElse(false)) {
                Utils.modSleep();
            } else if (ring.map(r -> r.click("Ferox Enclave")).orElse(false)) {
                Utils.modSleep();
            }

        }
        PathingUtil.walkToArea(FEROX_POOL_AREA);
        Optional<GameObject> pool_of_refreshment = QueryUtils.getObject("Pool of Refreshment");
        if (QueryUtils.interactObject(pool_of_refreshment, "Drink"))
            Timer.slowWaitCondition(() -> MyPlayer.getAnimation() != -1, 8000, 12000);

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
                        Log.info("[Utils]: " + questName + " is not started");
                        return QUEST_STATUS.NOT_STARTED;
                    }
                    if (Interfaces.get(399, 7, i).getTextColour() == IN_PROGRESS_ID) {
                        Log.info("[Utils]: " + questName + " is in progress");
                        return QUEST_STATUS.IN_PROGRESS;
                    }
                    if (Interfaces.get(399, 7, i).getTextColour() == COMPLETED_ID) {
                        Log.info("[Utils]: " + questName + " is complete");
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

            int num = Utils.random(5, 7);
            for (int i = 0; i < num; i++) {
                if (i == 2) { // force camera adjustment if it's failed 2x prior to click
                    Log.info("[Utils]: Force focusing camera on RSObject");
                    DaxCamera.focus(obj);
                }
                if (Utils.unselectItem())
                    Waiting.wait(General.randomSD(20, 250, 120, 50));

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
            if (obj.getTile().distanceTo(MyPlayer.getPosition()) > Utils.random(9, 12)) {
                Log.info("[Utilities]: NPC is far away, walking to it");

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
                Log.info("[Utilities]: NPC is far away, walking to obj");

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
            if (obj.getPosition().distanceTo(Player.getPosition()) > Utils.random(10, 12)) {
                Log.info("[Utilities]: Object is far away, walking to obj");
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
        Optional<GroundItem> gItem = Query.groundItems()
                .idEquals(ItemID)
                .isReachable()
                .findBestInteractable();
        if (gItem.map(g -> g.interact("Take")).orElse(false))
            return Timer.waitCondition(() -> org.tribot.script.sdk.Inventory.getCount(ItemID) >= stock + 1, 7000, 9000);
        return false;
    }

    public static boolean clickGroundItem(int[] ItemIDs) {
        int stock = org.tribot.script.sdk.Inventory.getCount(ItemIDs);
        Optional<GroundItem> gItem = Query.groundItems().idEquals(ItemIDs)
                .isReachable()
                .findBestInteractable();
        if (gItem.map(g -> g.interact("Take")).orElse(false))
            return Timer.waitCondition(() -> org.tribot.script.sdk.Inventory.getCount(ItemIDs) >= stock + 1, 7000, 9000);
        return false;
    }

    public static boolean clickGroundItem(String itemName) {
        int stock = org.tribot.script.sdk.Inventory.getCount(itemName);
        Optional<GroundItem> gItem = Query.groundItems().nameContains(itemName)
                .isReachable()
                .findBestInteractable();
        if (gItem.map(g -> g.interact("Take")).orElse(false))
            return Timer.waitCondition(() -> org.tribot.script.sdk.Inventory.getCount(itemName) >= stock + 1, 7000, 9000);
        return false;
    }


    //TODO Delete and find all usages
    public static void pickupItem(int ItemID) {
        RSItem[] stock = org.tribot.api2007.Inventory.find(ItemID);
        RSGroundItem[] item = GroundItems.find(ItemID);
        if (item.length > 0) {
            if (!item[0].isClickable())
                DaxCamera.focus(item[0]);

            if (AccurateMouse.click(item[0], "Take"))
                Waiting.waitUntil(9000, 250, () -> Inventory.getCount(ItemID) == stock.length + 1);
        }
    }


    private static JFrame findTRiBotFrame() {
        Frame[] frames = JFrame.getFrames();
        for (Frame tempFrame : frames) {
            if (tempFrame.getTitle().contains("TRiBot")) {
                return (JFrame) tempFrame;
            }
            Waiting.wait(100);
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
                Waiting.wait(General.randomSD(20, 200, 100, 50));
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

    public static boolean useItemOnItem(int ItemId, int ItemId2) {
        Optional<InventoryItem> item1 = Query.inventory().idEquals(ItemId).findClosestToMouse();
        Optional<InventoryItem> item2 = Query.inventory().idEquals(ItemId2).findRandom();
        if (item1.isEmpty() || item2.isEmpty()) {
            Log.info("[Utils]: Missing item with either id: " + ItemId + " or ID: " + ItemId2);
            return false;
        }
        Log.info("[Utils]: Using " + getItemName(ItemId) + " on " + getItemName(ItemId2));
        return item2.map(i2 -> item1.map(i1 -> i1.useOn(i2)).orElse(false)).orElse(false);
    }

    public static boolean useItemOnItem(int ItemId, int ItemId2, boolean useRandom) {
        Optional<InventoryItem> item1 =
                Query.inventory().idEquals(ItemId).findClosestToMouse();
        Optional<InventoryItem> item2 =
                Query.inventory().idEquals(ItemId2).findClosestToMouse();
        if (item1.isEmpty() || item2.isEmpty()) {
            Log.info("[Utils]: Missing item with either id: " + ItemId + " or ID: " + ItemId2);
            return false;
        }
        Log.info("[Utils]: Using " + getItemName(ItemId) + " on " + getItemName(ItemId2));
        return item2.map(i2 -> item1.map(i1 -> i1.useOn(i2)).orElse(false)).orElse(false);
    }



  /*  public static void setNPCAttackPreference(boolean hidden) {
        if (Interfaces.get(116, 7, 4) != null) { // don't use is substantiated

            if (!hidden && Interfaces.get(116, 7, 4).getText().contains("Left-click where available")) {
                    Log.info("[Utils]: Attack preferences already set");
                return;
            } else if (hidden && Interfaces.get(116, 7, 4).getText().contains("Hidden")) {
                    Log.info("[Utils]: Attack preferences already set to Hidden");
                return;
            }
        }
            Log.info("[Utils]: Setting NPC Attach preferences");
        if (GameTab.getOpen() != GameTab.TABS.OPTIONS) {
            GameTab.open(GameTab.TABS.OPTIONS);
            Waiting.wait(General.randomSD(100, 750, 400, 150));
        }
        if (GameTab.getOpen() == GameTab.TABS.OPTIONS) {

            if (Interfaces.get(109, 6) != null) { // selects tab
                Interfaces.get(109, 6).click();
                Waiting.wait(General.randomSD(100, 750, 400, 150));
            }

            if (Interfaces.get(116, 7, 3) != null) { // selects drop down
                Interfaces.get(116, 7, 3).click();
                Waiting.wait(General.randomSD(100, 750, 400, 150));
            }
            if (hidden) {
                InterfaceUtil.clickInterfaceText(116, 36, "Hidden");
                Waiting.wait(General.randomSD(100, 750, 400, 150));

            } else if (Interfaces.get(116, 36, 3) != null) { // selects 'left click when availible'
                InterfaceUtil.clickInterfaceText(116, 36, "Left-click where available");
                Waiting.wait(General.randomSD(100, 750, 400, 150));
            }
        }
    } */

    public static RSTile getCornerTile(RSArea area) {

        RSTile[] allTiles = area.getAllTiles();

        RSTile cornerTile = allTiles[0];

        Log.info("[Debug]: Corner tile is: " + allTiles[0]);
        return cornerTile;

    }

    public static void debug(String words) {
        Log.info("[Debug]: " + words);
    }

    public static void debug(String words, String stageVar) {
        Log.info("[Debug]: " + words);
        stageVar = words;
    }

    public static int getNotedItemID(int ItemID) {
        RSItemDefinition id = RSItemDefinition.get(ItemID);
        return id != null ? id.getNotedItemID() : -1;
    }

    ArrayList<Integer> toBuy = new ArrayList<>();
    ArrayList<Integer> invList = new ArrayList<>();

    public void checkInventoryItems(ArrayList<Integer> list) {
        RSItem[] invItems = org.tribot.api2007.Inventory.getAll();
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
            //     Log.info(whole);
            Image img = getItemImage(whole);
            if (img != null)
                return img;
        }
        Log.info("[Utils]: null error getting npc image");
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
        if (Camera.getAngle() < 45) {
            int angle = Utils.random(90, 100);
            Log.info("[Utils]: Setting Camera Angle to " + angle);
            Camera.setAngle(angle);
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
            if (obj.getPosition().distanceTo(Player.getPosition()) > Utils.random(8, 12)) {
                Log.info("[Utils]: Object is far away, walking closer");
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
                    obj.getPosition().distanceTo(Player.getPosition()) > Utils.random(8, 12)) {
                Log.info("[Utils]: Object is far away, walking closer");
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
            Log.info("[Utils]: AFKing for " + lengthSec + "s");
            Mouse.leaveScreen();
            Timer.waitCondition(() -> MyPlayer.isHealthBarVisible(), lengthMS, lengthMS + 1);
            return true;
        }
        return false;
    }

    public static void afk(int lengthMS) {
        int lengthSec = lengthMS / 1000;
        Log.info("[Utils]: AFKing for " + lengthSec + "s");
        Mouse.leaveScreen();
        Timer.waitCondition(() -> MyPlayer.isHealthBarVisible(), lengthMS, lengthMS + 1);
    }

    public static void dropItem(int item) {
        Log.info("[Utils]: Dropping item(s)");
        Inventory.drop(item);
        unselectItem();
    }


    public static void dropItem(int[] item) {
        Log.info("[Utils]: Dropping items");
        Inventory.drop(item);
        unselectItem();
    }

    /**
     * @return
     */
    public static boolean unselectItem() {
        if (GameState.isAnyItemSelected()) {
            String itemName = GameState.getSelectedItemName();
            Log.info("[Utils]: Un-select item failsafe activated, clicking item to unselect");
            Optional<InventoryItem> invItem = QueryUtils.getItem(itemName);
            if (invItem.map(i -> i.click()).orElse(false))
                return Timer.waitCondition(() ->
                        !GameState.isAnyItemSelected(), 1500, 2500);

        }
        return !GameState.isAnyItemSelected();
    }


    public static boolean hoverXp(Skills.SKILLS skill, int chance) {
        int num = Utils.random(0, 100);
        if (chance > num) {
            Log.info("[Antiban]: Hovering XP");
            skill.hover();
            Waiting.wait(Utils.random(300, 1200));
            return true;
        }
        return false;
    }

    public static String getItemName(int id) {
        RSItemDefinition def = RSItemDefinition.get(id);
        return def != null ? def.getName() : "";
    }

    public static void setCamera(int angle, int angleAllowance, int rotation, int rotnAllowance) {
        int a = Camera.getAngle();
        Log.info("Angle is " + a);
        if (Camera.getRotation() > (rotation + rotnAllowance) ||
                Camera.getRotation() < (rotation - rotnAllowance)) {
            int half = rotnAllowance / 2;
            int i = Utils.random(rotation - half, rotation + half);
            Log.info("[Camera]: Setting camera rotation to " + i);
            Camera.setRotation(i);
        }
        if (a > (angle + angleAllowance) ||
                a < (angle - angleAllowance)) {
            if (a < (angle - angleAllowance)) {
                Log.info("Debug]: Angle is less than " + (angle - angleAllowance));
            }
            int half = angleAllowance / 2;
            int i = Utils.random(angle - half, angle + half);
            Log.info("[Camera]: Setting camera angle to " + i);
            Camera.setAngle(i);
        }
    }

    public static boolean handleDoor(RSTile doorTile, boolean open) {
        Optional<GameObject> door;
        if (open)
            door = Query.gameObjects()
                    .nameContains("Door")
                    .actionContains("Open")
                    .tileEquals(getWorldTileFromRSTile(doorTile))
                    .findClosestByPathDistance();
        else
            door = Query.gameObjects()
                    .nameContains("Door")
                    .actionContains("Close")
                    .tileEquals(getWorldTileFromRSTile(doorTile))
                    .findClosestByPathDistance();


        for (int i = 0; i < 3; i++) {
            if (door.isEmpty())
                return false;

            if (open && door.map(d -> d.interact("Open")).orElse(false)) {
                Log.info("[Utils]: Door handled");
                return true;
            } else if (door.map(d -> d.interact("Close")).orElse(false)) {
                Log.info("[Utils]: Door handled");
                return true;
            } else {
                Log.info("[Utils]: Failed to handle door, waiting up to 2s and trying again");
                Waiting.wait(Utils.random(500, 2500));
            }

        }
        return false;
    }

    public static void setGameSettings() {
        if (GameState.getSetting(281) == 1000) {

            if (Options.isRoofsEnabled()) {
                Options.setRemoveRoofsEnabled(true);
                Utils.shortSleep();
            }

            if (!Options.isShiftClickDropEnabled()) {
                Options.setShiftClickDrop(true);
                Utils.shortSleep();
            }

            if (Options.isResizableModeEnabled()) {
                Options.setResizableModeType(Options.ResizableType.FIXED);
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

    //TODO Delete this and usages
    public static boolean isItemInInventory(int item) {
        return Inventory.contains(item);
    }

    public static void setNPCAttackPreference() {

        //already set, break;
        //  if (GameState.getSetting(1306) == 2)
        //      return;

        Options.AttackOption.setNpcAttackOption(Options.AttackOption.LEFT_CLICK_WHERE_AVAILABLE);

    }

    public static boolean waitCondtion(BooleanSupplier condition) {
        return Waiting.waitUntil(Utils.random(4200, 6500), 300,
                condition);
    }

    public static void microSleep() {
        int sleepTime = General.randomSD(100, 1000, 500, 200);
        Log.info("[Debug]: Sleeping for " + sleepTime + "ms");
        Waiting.wait(sleepTime);
    }

    public static void shortSleep() {
        int sleepTime = General.randomSD(1000, 3200, 1600, 200);
        Log.info("[Debug]: Sleeping for " + sleepTime + "ms");
        Waiting.wait(sleepTime);
    }

    public static void modSleep() {
        int sleepTime = General.randomSD(2500, 8000, 5000, 600);
        Log.info("[Debug]: Sleeping for " + sleepTime + "ms");
        Waiting.wait(sleepTime);
    }

    public static void longSleep() {
        int sleepTime = General.randomSD(8000, 20000, 14000, 2500);
        Log.info("[Debug]: Sleeping for " + sleepTime + "ms");
        Waiting.wait(sleepTime);
    }

    public static boolean handleRecoilMessage(String message) {
        if (message.contains("Your Ring of Recoil has shattered.")) {
            Log.info("[Message Listener]: Ring of recoil shattered message received.");
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

                    RSItem[] item = org.tribot.api2007.Inventory.find(potion);

                    if (item != null && item.length > 0) {

                        if (item[0].click("Drink"))
                            Utils.waitCondtion(() -> MyPlayer.getAnimation() == 829);
                    }
                }
            }
        }
    }

    public static boolean drinkPotion(int[] potionList) {
       return Query.inventory().idEquals(potionList).findClosestToMouse()
                .map(i -> i.click("Drink")).orElse(false) &&
                Utils.waitCondtion(() -> MyPlayer.getAnimation() == 829);
    }

    public static double getLevelBoost(Skill skill) {
        int lvl = skill.getActualLevel();
        double boost = Math.floor(lvl * .10 + 4);
        //        Log.info("[Utils]: Boost is " + Math.round(boost));
        return Math.round(boost);
    }

    public static double getLevelBoost(Skills.SKILLS skill) {
        int lvl = skill.getActualLevel();
        double boost = Math.floor(lvl * .10 + 4);
        //        Log.info("[Utils]: Boost is " + Math.round(boost));
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

    public static void idleNormalAction(boolean print) {
        int sleep = (int) ReactionGenerator.get().nextReactionTime(200, 50, 0.007, 0.2,
                100, 2000);
        if (print)
            Log.info("[Utils]: Sleeping (normal rxn) for: " + sleep);
        Waiting.wait(sleep);
    }

    public static void idleNormalAction() {
        int sleep = (int) ReactionGenerator.get().nextReactionTime(200, 50, 0.007, 0.2,
                100, 2000);
        // Log.info("[Debug]: Sleeping (normal rxn) for: " + sleep);
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
        Waiting.waitUntil(sleep, 75, supplier);
    }


    public static void idle(int low, int high) {
        int sleep = Utils.random(low, high);
        Log.info("[Debug]: Sleeping for: " + sleep);
        Waiting.wait(sleep);
    }


    public static void breakUtil(Timer timer, Script script) { // breaks for 10-20min
        if (!timer.isRunning()) {
            int length = Utils.random(600000, 1200000);
            int min = length / 60000;
            script.setLoginBotState(false);
            Log.info("[Debug]: Breaking for ~" + min + "min");
            Waiting.wait(length);
            timer.reset();

            Log.info("[Debug]: Resuming from break");
            script.setLoginBotState(true);
            Login.login();

        }
    }

    public static void breakUtil(Timer timer) { // breaks for 10-20min
        if (!timer.isRunning()) {
            int length = Utils.random(600000, 1200000);
            int min = length / 60000;
            Log.info("[Debug]: Breaking for ~" + min + "min");
            Waiting.wait(length);
            timer.reset();
            Log.info("[Debug]: Resuming from break");
            Login.login();

        }
    }


    public static boolean waitCondtion(BooleanSupplier condition, int min, int max) {
        return Waiting.waitUntil(Utils.random(min, max), 175, () ->
                condition.getAsBoolean());
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
            AntiBan.generateTrackers(Utils.random(500, 1600), false);

        if (!waitTimes.isEmpty())
            AntiBan.generateTrackers(average(waitTimes), false);

        final int reactionTime = (int) (AntiBan.getReactionTime() * FACTOR);
        waitTimes.add(reactionTime);
        Log.info("[ABC2 Delay]: Sleeping for " + reactionTime + "ms");
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
        Timer.waitCondition(() -> !timer.isRunning() || MyPlayer.getCurrentHealthPercent() < 20, sleepTime, sleepTime + 500);

        if (MyPlayer.getCurrentHealthPercent() < 20) {
            EatUtil.eatFood();
        }

    }

    public static boolean clickInventoryItem(int itemId) {
        return QueryUtils.getItem(itemId).map(InventoryItem::click).orElse(false);
    }

    public static boolean clickInventoryItem(int itemId, String string) {
        return QueryUtils.getItem(itemId).map(i -> i.click(string)).orElse(false);
    }


    public static ArrayList<Integer> worldsAttempted = new ArrayList<>();

    public static boolean hopWorlds() {
        Optional<World> random = Query.worlds().isMainGame().isMembers()
                .isNotDangerous().isLowPing().findRandom();
        return random.map(w -> WorldHopper.hop(w.getWorldNumber())).orElse(false);
    }

    public static void abc2ReactionSleep(long time, boolean eatIfLowHp) {
        if (MyPlayer.getCurrentHealthPercent() < 40 && eatIfLowHp) {
            EatUtil.eatFood();
            return;
        }
        AntiBan.generateTrackers((int) (System.currentTimeMillis() - time), false);
        abc2waitTimes.add(AntiBan.getReactionTime());
        abc2waitTimes = Utils.sleep(abc2waitTimes);
    }


    public static Optional<Npc> getTargetEntity() {
        Optional<Npc> interactable = Query.npcs()
                .isMyPlayerInteractingWith()
                .isInteractingWithMe()
                .findBestInteractable();
        return interactable.isPresent() ? interactable : Query.npcs()
                .isMyPlayerInteractingWith()
                .findBestInteractable();
    }

    public static void abc2ReactionSleep(long time) {
        if (MyPlayer.getCurrentHealthPercent() < 40) {
            EatUtil.eatFood();
            return;
        }

        Optional<Npc> target = Utils.getTargetEntity();
        if (target.map(t -> t.getHealthBarPercent() > 0).orElse(false)) {
            Log.info("[ABC2]: Skipping sleep as we are still in combat");
            Waiting.waitNormal(700, 92);
            return;
        }
       /* if (MyPlayer.isHealthBarVisible()) {
                Log.info("[ABC2]: Skipping sleep as we are still in combat");
            Utils.microSleep();
            return;
        } */

        AntiBan.generateTrackers((int) (System.currentTimeMillis() - time), false);
        abc2waitTimes.add(AntiBan.getReactionTime());
        abc2waitTimes = Utils.sleep(abc2waitTimes);
    }

    public static int getInventoryValue() {
        int value = 0;
        List<InventoryItem> inv = Inventory.getAll();
        for (InventoryItem i : inv) {
            if (i.getDefinition().isNoted()) {
                int stack = i.getStack();

                value = value + scripts.rsitem_services.GrandExchange.getPrice(i.getId() - 1) * stack;

            } else if (i.getDefinition().isStackable()) {
                int stack = i.getStack();
                value = value + (scripts.rsitem_services.GrandExchange.getPrice(i.getId()) * stack);
            } else
                value = value + scripts.rsitem_services.GrandExchange.getPrice(i.getId());
        }

        return value;
    }

    /**
     * Not complete
     *
     * @return
     */
    public static boolean turnOnEscToClose() {
        if (!Options.isEscapeClosingEnabled())
            Log.warn("Attempting to turn on escpe to clsoe windows, but method isn't defined");

        return Options.isEscapeClosingEnabled();
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
                    Equipment.contains(itemOptional.get().getId())) {
                //already equipped and not stackable (e.g. arrows)
                return true;
            }
            ItemDefinition def = itemOptional.get().getDefinition();
            if (def.getActions().stream().anyMatch(s -> s.contains("Wield")) &&
                    itemOptional.map(i -> i.click("Wield")).orElse(false)) {
                return Timer.waitCondition(() -> Equipment.contains(def.getId()), 1200, 2000);
            } else if (def.getActions().stream().anyMatch(s -> s.contains("Wear")) &&
                    itemOptional.map(i -> i.click("Wear")).orElse(false)) {
                return Timer.waitCondition(() -> Equipment.contains(def.getId()), 1200, 2000);
            } else if (itemOptional.map(InventoryItem::click).orElse(false)) {
                return Timer.waitCondition(() -> Equipment.contains(def.getId()), 1200, 2000);
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
        //        Log.info("[Utils]: Boost is " + Math.round(boost));
        return Math.round(boost);
    }

    public static double getSuperLevelBoost(Skill skill) {
        int lvl = skill.getActualLevel();
        double boost = Math.floor(lvl * 0.15 + 5);
        //        Log.info("[Utils]: Boost is " + Math.round(boost));
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

    public static boolean equipItem(int itemID, String method) {
        Optional<InventoryItem> inv = QueryUtils.getItem(itemID);

        if (inv.map(i -> i.click(method)).orElse(false))
            return Timer.waitCondition(() -> Equipment.contains(itemID), 2000, 4000);

        return Equipment.contains(itemID);
    }

    //TODO redundant now, delete
    public static int getCombatLevel() {
        return MyPlayer.getCombatLevel();
    }

    public static String getItemNameFromID(int id) {
        Optional<ItemDefinition> first = Query.itemDefinitions().idEquals(id).findFirst();
        return first.map(f -> f.getName()).orElse("");
    }

    // TODO delete, redundant now
    public static String getCharacterName() {
        return MyPlayer.getUsername();
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
    public static int ALL_SETTINGS_BUTTON = 75;

    public static boolean openInterface(int parent, int child) {
        if (Interfaces.isInterfaceSubstantiated(parent, child))
            return Interfaces.get(parent, child).click();

        return false;
    }

    public static boolean openSettingsWindow() {
        if (Widgets.isVisible(SETTINGS_WINDOW_PARENT)) {
            Log.info("Settings is open");
            return true;
        } else {
            Log.info("Opening Settings");
            if (GameTab.OPTIONS.open())
                Waiting.waitUntil(3500, 400, () -> GameTab.OPTIONS.isOpen());

            if (openInterface(SETTINGS_TAB_PARENT_ID, 59)) // clicks the tab with the gear on it
                Utils.microSleep();

            if (Query.widgets().inIndexPath(SETTINGS_TAB_PARENT_ID, ALL_SETTINGS_BUTTON).textContains("All settings")
                    .findFirst().map(Widget::click).orElse(false))
                return Waiting.waitUntil(3500, 400, () -> Widgets.isVisible(SETTINGS_WINDOW_PARENT));

        }
        return Widgets.isVisible(SETTINGS_WINDOW_PARENT);
    }


    public boolean checkForInventoryItems(ArrayList<Integer> ItemIDList) {
        for (Integer i : ItemIDList) {
            if (!Inventory.contains(i)) {
                RSItemDefinition def = RSItemDefinition.get(i);
                if (def != null) {
                    Log.info("[Utils]: Missing and item: " + def.getName());
                    return false;
                }
                return false;
            }
        }
        Log.info("[Utils]: We have all items in ArrayList (x1)");
        return true;
    }

    public static void forceDownwardCameraAngle() {
        if (Camera.getAngle() < 80) {
            Log.info("[Utils]: Adjusting camera angle");
            Camera.setAngle(Utils.random(80, 100));
        }
    }

    public static boolean setCameraZoom(double zoomLevel) {
        Camera.setZoomMethod(Camera.ZoomMethod.MOUSE_SCROLL);
        if (Camera.getZoomPercent() < zoomLevel) {
            Log.info("[Utils]: Adjusting camera zoom to " + zoomLevel);
            return Camera.setZoomPercent(zoomLevel);
        }
        return Camera.getZoomPercent() >= zoomLevel;
    }


    public static boolean setCameraZoomAboveDefault() {
        Camera.setZoomMethod(Camera.ZoomMethod.MOUSE_SCROLL);
        if (Camera.getZoomPercent() > 65) {
            double setTo = General.randomDouble(0, 35);
            Log.info("[Utils]: Adjusting camera zoom to " + setTo);
            return Camera.setZoomPercent(setTo);
        }
        return Camera.getZoomPercent() <= 65;
    }

    public static boolean useItemOnObject(Optional<InventoryItem> invItem, Optional<GameObject> obj) {
        if (invItem.isPresent() && obj.isPresent()) {
            for (int i = 0; i < 3; i++) {
                Log.info("[Debug]: Using: " + invItem.get().getName());

                if (!GameState.isAnyItemSelected()) {
                    if (!obj.get().isVisible() || i == 2)
                        obj.get().adjustCameraTo();
                    return obj.map(o -> invItem.map(it -> it.useOn(o)).orElse(false)).orElse(false);

                } else if (isItemSelected(invItem.get().getId())) {
                    Log.info("[Utils]: Item is already selected");
                    if (!obj.get().isVisible() || i == 2)
                        obj.get().adjustCameraTo();

                    if (obj.get().click("Use " + invItem.get().getName()))
                        return true;
                }

            }
        }
        Log.info("[Utils]: failed to use item on object 3x");
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
            Log.info("[Utils]: Cannot find ItemID: " + ItemID + " || or ObjId: " + objID);
            return false;
        }
        return useItemOnObject(invItem, gameObj);
    }

    public static boolean useItemOnObject(int itemID, RSObject obj) {

        Optional<InventoryItem> invItem = QueryUtils.getItem(itemID);

        for (int i = 0; i < 3; i++) {
            if (obj != null) {
                Optional<GameObject> gameObject = Query.gameObjects().idEquals(obj.getID())
                        .tileEquals(getWorldTileFromRSTile(obj.getPosition()))
                        .findClosestByPathDistance();

                Log.info("[Utils]: Using: " + itemID);
                if (gameObject.map(o -> invItem.map(inv -> inv.useOn(o)).orElse(false)).orElse(false)) {
                    return true;
                }
                if (!isItemSelected(itemID)) {

                    if (invItem.map(inv -> inv.click("Use")).orElse(false))
                        AntiBan.waitItemInteractionDelay();

                    if (!obj.isClickable() || i == 2)
                        DaxCamera.focus(obj);

                    if (Waiting.waitUntil(500, 15, () -> obj.click(rsMenuNode ->
                            rsMenuNode != null &&
                                    rsMenuNode.getAction().equals("Use") && rsMenuNode.getTarget().toLowerCase().
                                    contains(obj.getDefinition().getName().toLowerCase()))))
                        return true;

                } else if (isItemSelected(itemID)) {

                    if (!obj.isClickable() || i == 2)
                        DaxCamera.focus(obj);

                    if (Waiting.waitUntil(500, 10, () -> obj.click(rsMenuNode ->
                            rsMenuNode != null &&
                                    rsMenuNode.getAction().equals("Use") && rsMenuNode.getTarget().toLowerCase().
                                    contains(obj.getDefinition().getName().toLowerCase()))))
                        return true;
                }

            }
        }
        return false;
    }

    public static boolean useItemOnObject(String itemName, int objectId) {
        Optional<InventoryItem> invItem = QueryUtils.getItem(itemName);
        Optional<GameObject> obj = QueryUtils.getObject(objectId);
        return useItemOnObject(invItem, obj);
    }

    public static boolean useItemOnObject(int ItemID, String objectName) {
        Optional<InventoryItem> invItem = QueryUtils.getItem(ItemID);
        Optional<GameObject> obj = QueryUtils.getObject(objectName);
        return useItemOnObject(invItem, obj);
    }

    public static boolean useItemOnObject(String itemName, String objectName) {
        Optional<InventoryItem> invItem = QueryUtils.getItem(itemName);
        Optional<GameObject> obj = QueryUtils.getObject(objectName);
        return useItemOnObject(invItem, obj);

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
        Area area = Area.fromRadius(MyPlayer.getTile(), rad);
        return Query.players().inArea(area).nameNotContains(MyPlayer.getUsername()).count();
    }

    public static void sleepAfkOrNormal(int chanceAfk) {
        int chance = Utils.random(0, 100);
        //Log.info("chance is " + chance);
        if (chanceAfk >= chance) {
            Utils.idleAfkAction();
        } else {
            Utils.idleNormalAction();
        }
    }

    public static String getRuntimeString(long runtime) {
        long millis = runtime;
        long second = (millis / 1000) % 60;
        long minute = (millis / (1000 * 60)) % 60;
        long hour = (millis / (1000 * 60 * 60)); //% 24;
        return String.format("%02d:%02d:%02d", hour, minute, second);
    }


    public static int roundToNearestThousand(int initial) {
        return
                (int) Math.round(initial / 1000.0) * 1000;
    }

    public static int roundToNearest(int initial, int roundTo) {

        return Math.round(initial / roundTo) * roundTo;
    }

    /**
     * Rounds to nearest whole number specified
     *
     * @param initial double provided
     * @param roundTo rounds to nearest specified value
     * @return the rounded number
     */
    public static int roundToNearest(double initial, int roundTo) {
        return (int) ((Math.round(initial) / roundTo) * roundTo);
    }


    public static boolean isItemSelected(int ItemID) {
        RSItemDefinition itemDef = RSItemDefinition.get(ItemID);
        if (itemDef != null && GameState.isAnyItemSelected()) {
            String itemString = itemDef.getName();
            return GameState.getSelectedItemName().contains(itemString);
        }
        return GameState.isAnyItemSelected();
    }

    public static boolean isItemSelected(String itemName) {
        return GameState.getSelectedItemName().contains(itemName);
    }

    public static boolean selectInvItem(int id) {
        Optional<InventoryItem> invItem = QueryUtils.getItem(id);
        return invItem.map(i -> i.click("Use")).orElse(false);
    }

    public static boolean useItemOnNPC(int itemID, String NPCName) {
        Optional<InventoryItem> invItem = QueryUtils.getItem(itemID);
        String itemString = getItemName(itemID);
        Optional<Npc> npc = QueryUtils.getNpc(NPCName);
        Log.info("[Debug]: Using: " + itemString);
        return npc.map(n -> invItem.map(i -> i.useOn(n)).orElse(false)).orElse(false);

    }


    public static boolean useItemOnNPC(int itemID, RSNPC rsnpc) {
        Optional<InventoryItem> invItem = QueryUtils.getItem(itemID);
        String itemString = getItemName(itemID);

        if (invItem.isPresent() && rsnpc != null) {
            Log.info("[Debug]: Using: " + itemString);

            if (selectInvItem(itemID))
                AntiBan.waitItemInteractionDelay();

            if (!rsnpc.isClickable())
                DaxCamera.focus(rsnpc);

            return Waiting.waitUntil(800, 15, () -> rsnpc.click(rsMenuNode ->
                    rsMenuNode != null &&
                            rsMenuNode.getAction().equals("Use")
                            && rsMenuNode.getTarget().toLowerCase().contains(rsnpc.getDefinition().getName().toLowerCase())));


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
        Optional<InventoryItem> invItem = QueryUtils.getItem(itemName);
        Optional<Npc> npc = QueryUtils.getNpc(NPCName);
        Log.info("[Debug]: Using: " + itemName);
        return npc.map(n -> invItem.map(i -> i.useOn(n)).orElse(false)).orElse(false);
    }


    public static boolean useItemOnNPC(int itemID, String[] NpcNames) {
        Optional<InventoryItem> invItem = QueryUtils.getItem(itemID);
        String itemString = getItemName(itemID);
        Optional<Npc> npc = Query.npcs().nameContains(NpcNames).findClosestByPathDistance();
        Log.info("[Utils]: Using: " + itemString);
        return npc.map(n -> invItem.map(i -> i.useOn(n)).orElse(false)).orElse(false);
    }

    public static boolean useItemOnNPC(int ItemID, int npcId) {
        Optional<InventoryItem> item = Query.inventory().idEquals(ItemID)
                .findClosestToMouse();
        Optional<Npc> npc = Query.npcs().idEquals(npcId)
                .findBestInteractable();

        if (npc.isEmpty() || item.isEmpty()) {
            Log.info("[Utils]: Cannot find ItemID: " + ItemID + " || or NpcID: " + npcId);
            return false;
        }
        //adjust camera if needed
        return item.get().useOn(npc.get());
    }


    public static boolean useItemOnNPC(int[] itemID, int NpcId) {
        Optional<InventoryItem> invItem = Query.inventory().idEquals(itemID).findClosestToMouse();
        Optional<Npc> npc = QueryUtils.getNpc(NpcId);
        return npc.map(n -> invItem.map(i -> i.useOn(n)).orElse(false)).orElse(false);
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
                //     Log.info("[Debug]: " + s.toString() + " has increased experience since start: " +currentXp );
                return true;
            }
        }
        return false;
    }


    public static boolean shouldDecantPotion(String potionNameBase) {
        int oneDose = 0;
        int twoDose = 0;
        int threeDose = 0;
        int totalDoses = 0;

        if (!BankCache.isInitialized()) {

        }

        if (potionNameBase.toLowerCase().contains("stamina")) {
            oneDose = BankCache.getStack(ItemID.STAMINA_POTION[3]);
            twoDose = BankCache.getStack(ItemID.STAMINA_POTION[2]);
            threeDose = BankCache.getStack(ItemID.STAMINA_POTION[1]);
            totalDoses = (oneDose) + (twoDose * 2) + (threeDose * 3);
            Log.debug("We have " + totalDoses + " total doses of stamina potions");
            return totalDoses >= 4;
        }
        return false;
    }

    public static void rink() {
        if (Waiting.waitUntil(4000, 500, MyPlayer::isAnimating)) {
            Waiting.waitUntil(6000, 500, () -> !MyPlayer.isAnimating());
        }
    }

    public static void animationIdle(int timeout) {
        if (Waiting.waitUntil(4000, 500, MyPlayer::isAnimating)) {
            Waiting.waitUntil(timeout, 500, () -> !MyPlayer.isAnimating());
        }
    }
}