package scripts.Tasks;

import org.tribot.script.sdk.Log;
import org.tribot.script.sdk.Prayer;
import scripts.Data.CaveNPCs;
import scripts.Data.Wave;
import scripts.Utils;

import java.io.IOException;

public class PrayerTask implements Task {


    @Override
    public String toString() {
        return "Handling PrayerTask";
    }

    @Override
    public Priority priority() {
        return Priority.HIGH;
    }

    @Override
    public boolean validate() {
        return ((!CaveNPCs.shouldProtectMage(Wave.getCurrentWave())
                && !CaveNPCs.shouldProtectRange(Wave.getCurrentWave())
                && (Prayer.isAllEnabled(Prayer.PROTECT_FROM_MISSILES)
                || Prayer.isAllEnabled(Prayer.PROTECT_FROM_MAGIC))
                || (CaveNPCs.shouldProtectMage(Wave.getCurrentWave())
                && !Prayer.isAllEnabled(Prayer.PROTECT_FROM_MAGIC))
                || (CaveNPCs.shouldProtectRange(Wave.getCurrentWave()) && !CaveNPCs.shouldProtectMage(Wave.getCurrentWave())
                && !Prayer.isAllEnabled(Prayer.PROTECT_FROM_MISSILES))));
    }


    @Override
    public void execute() {
        Log.info("Setting Prayer");
        if ((!CaveNPCs.shouldProtectMage(Wave.getCurrentWave()) && !CaveNPCs.shouldProtectRange(Wave.getCurrentWave()) && (Prayer.isAllEnabled(Prayer.PROTECT_FROM_MISSILES) || Prayer.isAllEnabled(Prayer.PROTECT_FROM_MAGIC)))) {
            Prayer.disableAll(Prayer.PROTECT_FROM_MISSILES,
                    Prayer.PROTECT_FROM_MAGIC);
        }
        if (CaveNPCs.shouldProtectMage(Wave.getCurrentWave())) {
            setPrayer(Prayer.PROTECT_FROM_MAGIC);
        } else if (CaveNPCs.shouldProtectRange(Wave.getCurrentWave()) && !CaveNPCs.shouldProtectMage(Wave.getCurrentWave())) {
            setPrayer(Prayer.PROTECT_FROM_MISSILES);
        }
    }


    public boolean setPrayer(Prayer prayer) {
        if (Prayer.getPrayerPoints() == 0) {
            return false;
        }
        if (!Prayer.isAllEnabled(prayer)) {
            Prayer.enableAll(prayer);
            Utils.idlePredictableAction();
        }
        return Prayer.isAllEnabled(prayer);
    }

}

