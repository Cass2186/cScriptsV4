package scripts.Tasks;

import org.tribot.api.General;
import org.tribot.api2007.Combat;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Prayer;
import org.tribot.api2007.Skills;
import org.tribot.api2007.types.RSItem;
import org.tribot.script.sdk.MyPlayer;
import org.tribot.script.sdk.Skill;
import org.tribot.script.sdk.Waiting;
import scripts.Data.Vars;
import scripts.EatUtil;
import scripts.ItemID;
import scripts.Timer;
import scripts.Utils;

import java.util.Arrays;
import java.util.Collections;

public class EatDrink implements Task {


    public boolean drinkPotion(int[] potionArray) {
        int[] rev = Utils.reverseIntArray(potionArray);
        RSItem[] potion = Inventory.find(rev);
        return potion.length > 0 && potion[0].click("Drink");
    }

    public static boolean breakTeleTab() {
        RSItem[] tab = Inventory.find(ItemID.VARROCK_TELEPORT);
        return tab.length > 0 && tab[0].click("Break");
    }

    public void eatDrink() {
            if (Vars.get().killingScarabs)
                org.tribot.script.sdk.Prayer.enableAll(org.tribot.script.sdk.Prayer.PROTECT_FROM_MAGIC,
                        org.tribot.script.sdk.Prayer.HAWK_EYE);
            else
                org.tribot.script.sdk.Prayer.enableAll(org.tribot.script.sdk.Prayer.PROTECT_FROM_MAGIC,
                        org.tribot.script.sdk.Prayer.EAGLE_EYE);
            if (!Vars.get().killingUndeadDruids && !Vars.get().killingScarabs &&
                    !Vars.get().antifireTimer.isRunning()) {
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
            RSItem[] prayer = Inventory.find(ItemID.PRAYER_POTION);
            if (prayer.length == 0 && breakTeleTab()) return;

            if (drinkPotion(ItemID.PRAYER_POTION)) {
                Waiting.waitNormal(120, 30);
                Vars.get().drinkPrayerAt = General.random(5, 25);
            }
            if (Vars.get().killingUndeadDruids) {
                //re-enable if it got disabled
                org.tribot.script.sdk.Prayer.enableAll(org.tribot.script.sdk.Prayer.PROTECT_FROM_MAGIC,
                        org.tribot.script.sdk.Prayer.EAGLE_EYE);
            }
        }
        int maxLevel = (int) Utils.getLevelBoost(Skills.SKILLS.RANGED);
        if (Skill.RANGED.getCurrentLevel() <= (maxLevel - General.random(4, 6)) ||
                Skill.RANGED.getCurrentLevel() == Skill.RANGED.getActualLevel()) {
            int[] potion = ItemID.RANGING_POTION;
            Utils.drinkPotion(ItemID.RANGING_POTION);

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
        return (Combat.getHP() <= Vars.get().eatAtHP) || MyPlayer.isPoisoned();// ||
        //!Vars.get().antifireTimer.isRunning() ||
        //     (Skills.SKILLS.PRAYER.getActualLevel() >= 43 &&
        //          Prayer.getPrayerPoints() < Vars.get().drinkPrayerAt));
    }

    @Override
    public void execute() {
        eatDrink();
        if (MyPlayer.isPoisoned() && drinkPotion(ItemID.ANTIDOTE_PLUS_PLUS)) {
            Timer.waitCondition(() -> !MyPlayer.isPoisoned(), 1500, 2000);
        }

    }
}
