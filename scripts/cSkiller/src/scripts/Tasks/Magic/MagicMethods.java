package scripts.Tasks.Magic;

import org.tribot.api.General;
import org.tribot.api.input.Mouse;
import org.tribot.api2007.GameTab;
import org.tribot.api2007.Interfaces;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.types.RSInterface;
import org.tribot.api2007.types.RSItem;

import java.awt.*;
import java.util.Arrays;
import java.util.Optional;

public class MagicMethods {

    public static Optional<RSInterface> getSpellInterface(String spellName) {
        RSInterface book = Interfaces.get(218);
        if (book != null) {
            RSInterface[] spells = book.getChildren();
            return Arrays.stream(spells).filter(s -> s.getComponentName() != null)
                    .filter(s -> General.stripFormatting(s.getComponentName()).contains(spellName))
                    .findFirst();
        }
        return Optional.empty();
    }

    public static Rectangle getScreenPosition(String spellName) {
        Optional<RSInterface> spellInter = getSpellInterface(spellName);
        return spellInter.isPresent() ? spellInter.get().getAbsoluteBounds() : null;
    }

    public static boolean dragItemToAlch(int ItemID) {
        Rectangle hoverPoint = MagicMethods.getScreenPosition("High Level Alchemy");
        Point p = hoverPoint.getLocation();
        RSItem[] alchItem = Inventory.find(ItemID);
        if (alchItem.length > 0) {
            Rectangle itemRec = alchItem[0].getArea();
            Point itemP = new Point((int) itemRec.getMaxX(), (int) itemRec.getMaxY());
            if (!hoverPoint.contains(itemP) && GameTab.open(GameTab.TABS.INVENTORY)) {
                General.println("[Debug]: Dragging item");
                Mouse.drag(itemRec.getLocation(), p, 1);
            }
            return hoverPoint.contains(itemP);
        }
        return false;
    }


    public static boolean clickSpellString(String spellName) {
        Optional<RSInterface> spellInter = getSpellInterface(spellName);
        return spellInter.isPresent() && spellInter.get().click();
    }

}
