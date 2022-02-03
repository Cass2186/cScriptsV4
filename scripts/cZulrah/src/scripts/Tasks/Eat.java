package scripts.Tasks;

import org.tribot.api.General;
import org.tribot.script.sdk.MyPlayer;
import scripts.EatUtil;
import scripts.ItemID;
import scripts.Utils;

public class Eat implements Task {


    @Override
    public Priority priority() {
        return Priority.HIGH;
    }

    @Override
    public boolean validate() {
        return MyPlayer.isVenomed() || MyPlayer.getCurrentHealthPercent() < General.random(50, 60);
    }

    @Override
    public void execute() {
        if (MyPlayer.isVenomed()) {
            Utils.drinkPotion(ItemID.ANTI_VENOM_PLUS);
        }
        if (MyPlayer.getCurrentHealthPercent() < General.random(50, 60))
            EatUtil.eatFood(true);
    }
}
