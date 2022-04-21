package scripts.Tasks;

import org.tribot.script.sdk.*;
import org.tribot.script.sdk.query.Query;
import org.tribot.script.sdk.types.InventoryItem;
import scripts.ItemID;
import scripts.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class AlchItems implements Task {

    private static List<Integer> alchableIds = new ArrayList<>(Arrays.asList(
            ItemID.FIRE_BATTLESTAFF,
            ItemID.AIR_BATTLESTAFF,
            ItemID.AMULET_OF_STRENGTH,
            ItemID.AMULET_OF_MAGIC,
            ItemID.AMULET_OF_DEFENCE,
            ItemID.EARTH_BATTLESTAFF
    ));

    public static boolean hasAlchables() {
        Optional<InventoryItem> fireRune = Query.inventory().idEquals(ItemID.FIRE_RUNE).findClosestToMouse();
        Optional<InventoryItem> natureRune = Query.inventory().idEquals(ItemID.NATURE_RUNE).findClosestToMouse();
        return fireRune.map(fire -> fire.getStack() >= 5).orElse(false) &&
                natureRune.isPresent() &&
                Inventory.getAll().stream().anyMatch(i -> alchableIds.contains(i.getId()));
    }

    private boolean alchItem() {
        Optional<InventoryItem> first = Inventory.getAll().stream().filter(i -> alchableIds.contains(i.getId())).findFirst();
        return first.map(f -> Magic.castOn("High level alchemy", f)).orElse(false);
    }

    @Override
    public Priority priority() {
        return Priority.MEDIUM;
    }

    @Override
    public boolean validate() {
        return Skill.MAGIC.getActualLevel() > 55 && hasAlchables();
    }

    @Override
    public void execute() {
        if (alchItem() &&
            Waiting.waitUntil(1550, 125, ()-> GameTab.MAGIC.isOpen())){
            Utils.idleNormalAction();
        }
    }
}
