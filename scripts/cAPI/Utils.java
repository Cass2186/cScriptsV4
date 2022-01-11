import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.input.Mouse;
import org.tribot.api.interfaces.Positionable;
import org.tribot.api2007.*;
import org.tribot.api2007.Objects;
import org.tribot.api2007.ext.Doors;
import org.tribot.api2007.ext.Filters;
import org.tribot.api2007.types.*;
import org.tribot.script.Script;
import scripts.wastedbro.api.rsitem_services.GrandExchange;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.text.NumberFormat;
import java.util.List;
import java.util.*;
import java.util.Map.Entry;
import java.util.function.BooleanSupplier;
import java.util.function.Predicate;

/**
 * Modified/added to from original author (@Elon)
 */
public class Utils {

    private static List<Integer> abc2waitTimes = new ArrayList<>();
    public static double FACTOR = 0.5;
    private static final String[] NAMES_TO_REMOVE = {"Divine", "Super", "super"};

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

    public static void setNPCAttackPreference(boolean hidden) {
        if (Interfaces.get(116, 7, 4) != null) { // don't use is substantiated

            if (!hidden && Interfaces.get(116, 7, 4).getText().contains("Left-click where available")) {
                General.println("[Utils]: Attack preferences already set");
                return;
            } else if (hidden && Interfaces.get(116, 7, 4).getText().contains("Hidden")) {
                General.println("[Utils]: Attack preferences already set to Hidden");
                return;
            }
        }
        General.println("[Utils]: Setting NPC Attach preferences");
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
    }

    public static RSTile getCornerTile(RSArea area) {

        RSTile[] allTiles = area.getAllTiles();

        RSTile cornerTile = allTiles[0];

        General.println("[Debug]: Corner tile is: " + allTiles[0]);
        return cornerTile;

    }

    public static void debug(String words) {
        General.println("[Debug]: " + words);
    }

    public static void debug(String words, String stageVar) {
        General.println("[Debug]: " + words);
        stageVar = words;
    }

    public static int getNotedId(int itemId) {
        RSItemDefinition id = RSItemDefinition.get(itemId);
        if (id != null)
            return RSItemDefinition.get(itemId).getNotedItemID();

        return -1;
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


    public static boolean openWorldHopper() {
        Utilities.closeUnwantedInterfaces();

        int parentLogoutButton = Options.isResizableModeEnabled() ? 164 : 548;

        RSInterface worldList = Interfaces.get(69, 2);
        RSInterface worldSwitchButton = Interfaces.get(182, 7);

        if (!Interfaces.isInterfaceSubstantiated(worldList)) { //world hopper page
            if (!Interfaces.isInterfaceSubstantiated(worldSwitchButton)) { // world switcher button
                if (InterfaceUtil.clickInterfaceAction(parentLogoutButton, "Logout"))
                    scripts.Utility.Timer.waitCondition(() -> Interfaces.isInterfaceSubstantiated(worldList)
                            || Interfaces.isInterfaceSubstantiated(worldSwitchButton), 3500, 5500);
            }
            if (Interfaces.isInterfaceSubstantiated(worldSwitchButton)) {
                if (worldSwitchButton.click())//clicks the worldhopper button
                    return scripts.Utility.Timer.waitCondition(() -> Interfaces.isInterfaceSubstantiated(69, 2)
                            || Interfaces.isInterfaceSubstantiated(worldList), 3000, 5000);
            }
        }
        return Interfaces.isInterfaceSubstantiated(worldList);

    }

    public static boolean click(int itemId) {

        return false;
    }

    /**
     * @author dax
     */
    public static RSItem getClosestToMouse(List<RSItem> rsItems) {
        Point mouse = Mouse.getPos();
        rsItems.sort(Comparator.comparingInt(o -> (int) getCenter(o.getArea()).distance(mouse)));
        return rsItems.size() > 0 ? rsItems.get(0) : null;
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
            // General.println(whole);
            Image img = getItemImage(whole);
            if (img != null)
                return img;
        }
        General.println("[Utils]: null error getting npc image");
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

    public static boolean adjustCameraToObj(RSObject obj) {
        if (!obj.isClickable()) {
            if (obj.getPosition().distanceTo(Player.getPosition()) > General.random(8, 12)) {
                General.println("[Utils]: Object is far away, walking closer");
                Utilities.walkToAreaNoSleep(getObjectRSArea(obj));
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
                General.println("[Utils]: Object is far away, walking closer");
                Utilities.walkToAreaNoSleep(getObjectRSArea(obj));
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
            General.println("[Utils]: AFKing for " + lengthSec + "s");
            Mouse.leaveGame();
            scripts.Utility.Timer.waitCondition(() -> Combat.isUnderAttack(), lengthMS, lengthMS + 1);
            return true;
        }
        return false;
    }

    public static void afk(int lengthMS) {
        int lengthSec = lengthMS / 1000;
        General.println("[Utils]: AFKing for " + lengthSec + "s");
        Mouse.leaveGame();
        scripts.Utility.Timer.waitCondition(() -> Combat.isUnderAttack(), lengthMS, lengthMS + 1);
    }

    public static void dropItem(int item) {
        General.println("[Utils]: Dropping item(s)");
        Inventory.drop(item);
        unselectItem();
    }


    public static void dropItem(int[] item) {
        General.println("[Utils]: Dropping items");
        Inventory.drop(item);
        unselectItem();
    }

    /**
     * @return
     */
    public static boolean unselectItem() {
        if (Game.getItemSelectionState() == 1) {

            String itemName = Game.getSelectedItemName();
            General.println("[Utils]: Un-select item failsafe activated, clicking item to unselect");
            if (itemName != null) {
                RSItem[] invItem = Inventory.find(itemName);
                if (invItem.length > 0 && invItem[0].click())
                    return scripts.Utility.Timer.waitCondition(() ->
                            Game.getItemSelectionState() == 0, 1500, 2500);
            }
        }
        return Game.getItemSelectionState() == 0;
    }


    public static boolean hoverXp(Skills.SKILLS skill, int chance) {
        int num = General.random(0, 100);
        if (chance > num) {
            General.println("[Antiban]: Hovering XP");
            skill.hover();
            General.sleep(General.random(300, 1200));
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
        General.println("Angle is " + a);
        if (Camera.getCameraRotation() > (rotation + rotnAllowance) ||
                Camera.getCameraRotation() < (rotation - rotnAllowance)) {
            int half = rotnAllowance / 2;
            int i = General.random(rotation - half, rotation + half);
            General.println("[Camera]: Setting camera rotation to " + i);
            Camera.setCameraRotation(i);
        }
        if (a > (angle + angleAllowance) ||
                a < (angle - angleAllowance)) {
            if (a < (angle - angleAllowance)) {
                General.println("Debug]: Angle is less than " + (angle - angleAllowance));
            }
            int half = angleAllowance / 2;
            int i = General.random(angle - half, angle + half);
            General.println("[Camera]: Setting camera angle to " + i);
            Camera.setCameraAngle(i);
        }
    }

    public static boolean handleDoor(RSTile doorTile, boolean open) {
        RSObject[] doorTarget = Objects.findNearest(20, Filters.Objects.tileEquals(doorTile));
        if (doorTarget.length > 0) {

            for (int i = 0; i < 5; i++) {
                adjustCameraToObj(doorTarget[0]);

                if (Doors.handleDoor(doorTarget[0], open)) {
                    General.println("[Utils]: Door handled");
                    return true;

                } else {
                    General.println("[Utils]: Failed to handle door, waiting up to 2s and trying again");
                    General.sleep(General.random(500, 2500));
                }
            }

        }
        return false;
    }

    public static boolean handleDoor(int doorId, boolean open) {
        RSObject[] doorTarget = Objects.findNearest(20, doorId);
        if (doorTarget.length > 0) {

            for (int i = 0; i < 5; i++) {
                adjustCameraToObj(doorTarget[0]);

                if (Doors.handleDoor(doorTarget[0], open)) {
                    General.println("[Utils]: Door handled");
                    return true;

                } else {
                    General.println("[Utils]: Failed to handle door, waiting up to 2s and trying again");
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


    public static boolean zoomOut2ndFurthest() {
        if (Game.getViewportScale() > 350) {
            Utilities.closeUnwantedInterfaces();
            if (GameTab.open(GameTab.TABS.OPTIONS)) {
                if (openDisplayOptionsTab()) {
                    RSInterface zoombar = Interfaces.get(261, 10);
                    if (zoombar != null && !zoombar.isHidden()) {
                        if (zoombar.click()) {
                            scripts.Utility.Timer.waitCondition(() -> Game.getViewportScale() < 350, 4500, 6000);
                            return Game.getViewportScale() < 350;
                        }
                    }
                }
            }

        } else return true;

        return false;
    }

    public static boolean zoomOutMax() {
        if (GameTab.open(GameTab.TABS.OPTIONS)) {
            Utilities.closeUnwantedInterfaces();
            if (openDisplayOptionsTab()) {
                RSInterface zoombar = Interfaces.get(261, 9);
                if (zoombar != null && !zoombar.isHidden()) {
                    if (zoombar.click()) {
                        scripts.Utility.Timer.waitCondition(() -> Game.getViewportScale() < 515, 4500, 6000);
                        return Game.getViewportScale() < 515;
                    }
                }
            }

        }
        return false;
    }

    public static void adjustZoom(int zoom) { // so zoom of 1 is furthest out.
        General.println("[Debug]: Viewport Width " + Game.getViewportWidth());
        General.println("[Debug]: Viewport Scale " + Game.getViewportScale());
        GameTab.open(GameTab.TABS.OPTIONS);
        int[] ONE_TO_TWO = {150, 330};
        int[] TWO_TO_THREE = {330, 530};
        int[] THREE_TO_FOUR = {310, 530};
        General.sleep(General.random(250, 750));
        if (Interfaces.get(261, 1, 0) != null) {
            if (Interfaces.get(261, 1, 0).getTextureID() == 761) { // the screen tab is NOT selected
                Interfaces.get(261, 1, 0).click();
                General.sleep(General.random(200, 600));
            }
        }
        if (Interfaces.get(261, 8) != null) {
            Interfaces.get(261, zoom + 8).click();
        }
    }


    public static boolean isItemInInventory(int item) {
        RSItem[] inv = Inventory.find(item);
        return inv.length > 0;
    }

    public static void setNPCAttackPreference() {
        if (Interfaces.get(116, 7, 4) != null) { // don't use is substantiated
            if (Interfaces.get(116, 7, 4).getText().contains("Left-click where available")) {
                General.println("[Utils]: Attack preferences already set");
                return;
            }
        }
        General.println("[Utils]: Setting NPC Attach preferences");
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
            if (Interfaces.get(116, 36, 3) != null) { // selects 'left click when availible'
                Interfaces.get(116, 36, 3).click();
                General.sleep(General.randomSD(100, 750, 400, 150));
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
        General.println("[Debug]: Sleeping for " + sleepTime + "ms");
        General.sleep(sleepTime);
    }

    public static void shortSleep() {
        int sleepTime = General.randomSD(1000, 3000, 1500, 500);
        General.println("[Debug]: Sleeping for " + sleepTime + "ms");
        General.sleep(sleepTime);
    }

    public static void modSleep() {
        int sleepTime = General.randomSD(2500, 8000, 5000, 1000);
        General.println("[Debug]: Sleeping for " + sleepTime + "ms");
        General.sleep(sleepTime);
    }

    public static void longSleep() {
        int sleepTime = General.randomSD(8000, 20000, 14000, 2500);
        General.println("[Debug]: Sleeping for " + sleepTime + "ms");
        General.sleep(sleepTime);
    }

    public static boolean handleRecoilMessage(String message) {
        if (message.contains("Your Ring of Recoil has shattered.")) {
            General.println("[Message Listener]: Ring of recoil shattered message received.");
            if (Inventory.find(Constants.RING_OF_RECOIL).length > 0) {
                return AccurateMouse.click(Inventory.find(Constants.RING_OF_RECOIL)[0], "Wear");
            }
        }
        return false;
    }

    public static void recordGameSetting(int settingNumber) {
        Vars.get().settingValue = Game.getSetting(settingNumber);
    }

    public static void recordVarBit(int varbit) {
        Vars.get().settingValue = getVarBitValue(varbit);
    }

    public static boolean checkGameSettings(int setting) {
        return Vars.get().settingValue == Game.getSetting(setting);
    }

    public static boolean checkVarBitValue(int varbit) {
        return Vars.get().settingValue == getVarBitValue(varbit);
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


    public static boolean potionHandler(HashMap<Integer, Integer> map, ArrayList<String> list) {

        for (Entry<Integer, Integer> entry : map.entrySet()) {

            int itemID = entry.getKey();
            String itemName = RSItemDefinition.get(itemID).getName();

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


    public static void idle(int low, int high) {
        int sleep = General.random(low, high);
        General.println("[Debug]: Sleeping for: " + sleep);
        General.sleep(sleep);
    }


    public static boolean handleTaskCompleteMessage(String message) {
        if (message.contains("You've completed ")) {
            General.println("[Message Listener]: Task Completed.");
        }
        return false;
    }

    public static void breakUtil(Timer timer, Script script) { // breaks for 10-20min
        if (!timer.isRunning()) {
            int length = General.random(600000, 1200000);
            int min = length / 60000;
            script.setLoginBotState(false);
            General.println("[Debug]: Breaking for ~" + min + "min");
            General.sleep(length);
            timer.reset();

            General.println("[Debug]: Resuming from break");
            script.setLoginBotState(true);
            Login.login();

        }
    }

    public static void breakUtil(Timer timer) { // breaks for 10-20min
        if (!timer.isRunning()) {
            int length = General.random(600000, 1200000);
            int min = length / 60000;
            General.println("[Debug]: Breaking for ~" + min + "min");
            General.sleep(length);
            timer.reset();
            General.println("[Debug]: Resuming from break");
            Login.login();

        }
    }


    public static boolean handleNewTaskMessage(String message) {
        if (message.contains("You need something new to hunt.")) {
            General.println("[Message Listener]: We need a new task");
            return true;
        }
        return false;
    }


    public static void gameSettings() {
        if (Game.getRoofsEnabledStatus() != Game.RoofStatus.HIDDEN) {
            Options.setRemoveRoofsEnabled(true);
            General.sleep(General.random(100, 500));
        }
        if (!Options.isShiftClickDropEnabled()) {
            Options.setShiftClickDropEnabled(true);
            General.sleep(General.random(100, 500));
        }
        if (GameTab.getOpen() != GameTab.TABS.OPTIONS) {
            GameTab.open(GameTab.TABS.OPTIONS);
            General.sleep(General.random(250, 750));
        }
        if (Interfaces.isInterfaceSubstantiated(261, 10)) {
            Interfaces.get(261, 10).click();
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
            scripts.Utility.AntiBan.generateTrackers(General.random(500, 1600), false);

        if (!waitTimes.isEmpty())
            scripts.Utility.AntiBan.generateTrackers(average(waitTimes), false);

        final int reactionTime = (int) (scripts.Utility.AntiBan.getReactionTime() * FACTOR);
        waitTimes.add(reactionTime);
        General.println("[ABC2 Delay]: Sleeping for " + reactionTime + "ms");
        scripts.Utility.AntiBan.sleepReactionTime(reactionTime);
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
        scripts.Utility.Timer timer = new scripts.Utility.Timer(sleepTime);
        scripts.Utility.Timer.waitCondition(() -> timer.isRunning() || Combat.getHPRatio() > 20, sleepTime, sleepTime + 5000);

        if (Combat.getHPRatio() < 20) {
            EatUtil.eatFood();
        }

    }

    public static boolean clickInventoryItem(int itemId) {
        RSItem[] item = Inventory.find(itemId);
        if (item.length > 0) {
            if (item[0].click()) {
                Constants.idle(250, 900);
                return true;
            }
        }
        return false;
    }

    public static boolean clickInventoryItem(int[] itemId) {
        RSItem[] item = Inventory.find(itemId);
        if (item.length > 0) {
            if (item[0].click()) {
                Constants.idle(250, 900);
                return true;
            }
        }
        return false;
    }

    public static boolean clickInventoryItem(int itemId, String action) {
        RSItem[] item = Inventory.find(itemId);
        if (item.length > 0) {
            if (item[0].click(action)) {
                Constants.idle(250, 900);
                return true;
            }
        }
        return false;
    }

    public static boolean clickInventoryItem(int[] itemId, String action) {
        RSItem[] item = Inventory.find(itemId);
        if (item.length > 0) {
            if (item[0].click(action)) {
                Constants.idle(250, 900);
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
        return scripts.Utility.Timer.waitCondition(() -> WorldHopper.getWorld() == finalWorld, 15000, 25000);
    }


    public static void abc2ReactionSleep(long time) {
        if (Combat.getHPRatio() < 40) {
            EatUtil.eatFood();
            return;
        }
        RSCharacter target = Combat.getTargetEntity();
        if (target != null && target.getHealthPercent() != 0) {
            General.println("[ABC2]: Skipping sleep as we are still in combat");
            Utils.shortSleep();
            return;
        }
       /* if (Combat.isUnderAttack()) {
            General.println("[ABC2]: Skipping sleep as we are still in combat");
            Utils.microSleep();
            return;
        } */

        scripts.Utility.AntiBan.generateTrackers((int) (System.currentTimeMillis() - time), false);
        abc2waitTimes.add(scripts.Utility.AntiBan.getReactionTime());
        abc2waitTimes = Utils.sleep(abc2waitTimes);
    }

    public static int getInventoryValue() {
        int value = 0;
        RSItem[] inv = Inventory.getAll();
        for (RSItem i : inv) {
            if (i.getDefinition().isNoted()) {
                int stack = i.getStack();
                value = value + (GrandExchange.getPrice(i.getID() - 1) * stack);

            } else if (i.getDefinition().isStackable()) {
                int stack = i.getStack();
                value = value + (GrandExchange.getPrice(i.getID()) * stack);
            } else
                value = value + GrandExchange.getPrice(i.getID());
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
        RSItem[] inv = Inventory.find(Constants.GAMES_NECKLACE);
        if (inv.length > 0) {
            if (inv[0].click())
                return scripts.Utility.Timer.waitCondition(() -> Equipment.isEquipped(inv[0].getID()), 2000, 4000);
        }
        return false;
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

    public static boolean skillsNecklaceTeleport(int locationComponent) {
        RSItem[] equipped = Equipment.find(Const.get().SKILLS_NECKLACE);

        if (equipped.length > 0)
            if (equipped[0].click("Remove"))
                scripts.Utility.Timer.waitCondition(() -> Inventory.find(Const.get().SKILLS_NECKLACE).length > 0, 6000, 8000);

        RSItem[] inv = Inventory.find(Const.get().SKILLS_NECKLACE);
        if (inv.length > 0) {
            Interfaces.closeAll();
            if (inv[0].click("Rub"))
                scripts.Utility.Timer.waitCondition(() -> Interfaces.isInterfaceSubstantiated(187), 6000, 10000);

            if (Interfaces.isInterfaceSubstantiated(187, 3)) {
                RSTile currentTile = Player.getPosition();

                if (Interfaces.get(187, 3, locationComponent).click())
                    return scripts.Utility.Timer.waitCondition(() -> !Player.getPosition().equals(currentTile), 10000, 15000);
            }
        }
        return false;
    }


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

    public static boolean changeToFixed(boolean fixed) {
        if (fixed) {

        } else {

        }
        return false;
    }


    public boolean checkForInventoryItems(ArrayList<Integer> itemIdList) {
        for (Integer i : itemIdList) {
            if (Inventory.find(i).length == 0) {
                RSItemDefinition def = RSItemDefinition.get(i);
                if (def != null) {
                    General.println("[Utils]: Missing and item: " + def.getName());
                    return false;
                }
                return false;
            }
        }
        General.println("[Utils]: We have all items in ArrayList (x1)");
        return true;
    }

    public static boolean isCameraZoomBelowDefault() {

        RSInterface cameraBar = Interfaces.get(261, 8);
        RSInterface cameraBarNob = Interfaces.get(261, 15);

        if (cameraBarNob != null && cameraBar != null) {

            int nobX = cameraBarNob.getAbsolutePosition().x;
            int nobY = cameraBarNob.getAbsolutePosition().y;

            int barHeight = cameraBar.getAbsoluteBounds().height;
            int barWidth = cameraBar.getAbsoluteBounds().width / 2;
            int xCameraBar = cameraBar.getAbsoluteBounds().x;
            int yCameraBar = cameraBar.getAbsoluteBounds().y;

            Rectangle bar = new Rectangle(xCameraBar, yCameraBar, barWidth, barHeight);

            return !bar.contains(nobX, nobY);
        }
        return false;
    }

}
