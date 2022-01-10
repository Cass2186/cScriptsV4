package scripts;

import org.tribot.api.General;
import org.tribot.api2007.Player;
import org.tribot.api2007.Prayer;
import org.tribot.api2007.Skills;
import org.tribot.api2007.types.RSItem;
import scripts.EntitySelector.Entities;
import scripts.EntitySelector.finders.prefabs.ItemEntity;

public class PrayerUtil {


    public static final int[] PRAYER_POTION = {2434, 139, 141, 143};


    public static boolean canPray() {
        return Prayer.getPrayerPoints() > 1;
    }

    public static boolean prayMelee() {
        if (!canPray())
            drinkPrayerPotion();

        if (canPray() && Skills.getCurrentLevel(Skills.SKILLS.PRAYER) >= 43) {
            return Prayer.enable(Prayer.PRAYERS.PROTECT_FROM_MELEE);
        }
        General.println("[Debug]: Failed to pray melee");
        return false;
    }

    public static boolean drinkPrayerPotion(){
        RSItem pot = Entities.find(ItemEntity::new)
                .idEquals(PRAYER_POTION)
                .getFirstResult();

        if (pot != null && pot.click("Drink"))
            return Timer.waitCondition(()-> Player.getAnimation() == 829, 1250, 2400);

        return false;
    }

    public static boolean isPraying() {
        return Player.getRSPlayer().getPrayerIcon() != -1;
    }

    public static boolean setPrayer(PrayerType type) {
        if (Prayer.getPrayerPoints() > 0 && type != PrayerType.NONE && !isPraying()) {
            General.println("[CombatUtils]: Enabling Prayer");
            switch (type) {
                case MELEE:
                    return Prayer.enable(Prayer.PRAYERS.PROTECT_FROM_MELEE);

                case MAGIC:
                    return Prayer.enable(Prayer.PRAYERS.PROTECT_FROM_MAGIC);

                case MISSLES:
                    return Prayer.enable(Prayer.PRAYERS.PROTECT_FROM_MISSILES);

            }
        }
        return type == PrayerType.NONE;
    }

}
