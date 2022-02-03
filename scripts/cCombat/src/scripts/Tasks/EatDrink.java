package scripts.Tasks;

import org.tribot.api.General;
import org.tribot.api2007.Combat;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Prayer;
import org.tribot.api2007.Skills;
import org.tribot.api2007.types.RSItem;
import org.tribot.script.sdk.Waiting;
import scripts.Data.Vars;
import scripts.EatUtil;
import scripts.ItemId;
import scripts.Timer;

public class EatDrink implements Task {


    public boolean drinkPotion(int[] potionArray) {
        RSItem[] potion = Inventory.find(potionArray);
        return potion.length > 0 && potion[0].click("Drink");
    }

    public static boolean breakTeleTab() {
        RSItem[] tab = Inventory.find(ItemId.VARROCK_TELEPORT);
        return tab.length > 0 && tab[0].click("Break");
    }

    public void eatDrink() {
        if (!Vars.get().antifireTimer.isRunning()) {
            RSItem[] superAnti = Inventory.find(22209, 22212, 22215, 22218);

            if (superAnti.length == 0 && breakTeleTab()) return;

            if (drinkPotion(new int[]{22209, 22212, 22215, 22218})) {
                Vars.get().antifireTimer = new Timer(General.random(320000, 350000)); //never want it to expire (6min)
                Waiting.waitNormal(120, 30);
            }
        }
        if (Combat.getHP() <= Vars.get().eatAtHP) {
            EatUtil.eatFood(true);
        } else if (Prayer.getPrayerPoints() <= Vars.get().drinkPrayerAt) {
            RSItem[] prayer = Inventory.find(ItemId.PRAYER_POTION);
            if (prayer.length == 0 && breakTeleTab()) return;

            if (drinkPotion(ItemId.PRAYER_POTION))
                Waiting.waitNormal(120, 30);
        }
    }


    @Override
    public String toString() {
        return "Eating/Drinking";
    }

    @Override
    public Priority priority() {
        return Priority.HIGHEST;
    }

    @Override
    public boolean validate() {
        return (Combat.getHP() <= Vars.get().eatAtHP ||
                //!Vars.get().antifireTimer.isRunning() ||
                (Skills.SKILLS.PRAYER.getActualLevel() >= 43 &&
                        Prayer.getPrayerPoints() < Vars.get().drinkPrayerAt));
    }

    @Override
    public void execute() {
        eatDrink();
    }
}