package scripts.Tasks;

import javafx.print.PageLayout;
import org.tribot.api.General;
import org.tribot.api2007.Player;
import org.tribot.script.sdk.Log;
import org.tribot.script.sdk.Prayer;
import scripts.Data.Areas;
import scripts.Data.Vars;
import scripts.Timer;
import scripts.Utils;

public class RechargePrayer implements Task {

    public boolean prayAtAltar() {
        int p = Prayer.getPrayerPoints();
        Log.log("Recharging prayer");
        if (Utils.clickObj("Altar", "Pray-at")) {
            return Timer.waitCondition(() -> Prayer.getPrayerPoints() > p, 7000, 9000);
        }
        return false;
    }


    @Override
    public Priority priority() {
        return Priority.HIGHEST;
    }

    @Override
    public boolean validate() {
        return Areas.UNDEAD_DRUID_AREA.contains(Player.getPosition()) &&
                Prayer.getPrayerPoints() < General.random(17,22);
    }

    @Override
    public void execute() {
        prayAtAltar();
    }
}