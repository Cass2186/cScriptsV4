package scripts.Requirements.Items;

import org.tribot.script.sdk.query.Query;

import java.util.concurrent.atomic.AtomicInteger;

public class ChargedItems {

    public static Integer getMaxCharges(String baseName) {
        switch (baseName) {
            case "Games necklace":
            case "Ring of duelling":
                return 8;
            case "Skills necklace":
            case "Amulet of glory":
            case "Combat bracelet":
                return 6;
            case "Ring of wealth":
            case "Necklace of passage":
            case "Burning amulet":
            case "Ring of returning":
            case "Abyssal bracelet":
                return 5;
        }
        return 0;
    }

    public static Integer getChargeCountFromName(String name) {
        if (name.contains("(6)")) return 6;
        if (name.contains("(5)")) return 5;
        if (name.contains("(4)")) return 4;
        if (name.contains("(3)")) return 3;
        if (name.contains("(2)")) return 2;
        if (name.contains("(1)")) return 1;
        return 0;
    }

    public static Integer getCharges(String baseName) {
        AtomicInteger count = new AtomicInteger(0);
        Query.inventory().nameContains(baseName).forEach(item -> {
            int charges = getChargeCountFromName(item.getName());
            count.addAndGet(charges);
        });
        return count.get();
    }

}
