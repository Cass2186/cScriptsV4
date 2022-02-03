package scripts.Tasks;

import org.tribot.script.sdk.Log;
import org.tribot.script.sdk.Prayer;
import scripts.Data.Phase.ZulrahPhase;
import scripts.Data.TickObserver;
import scripts.Data.Vars;

public class ChangePrayer implements Task {

    public boolean setPrayer() {
        if (TickObserver.instance == null) {
            return false;
        }

        ZulrahPhase currentPhase = TickObserver.instance.getPhase();
        ZulrahPhase nextPhase = TickObserver.instance.getNextPhase();

        if (currentPhase == null || nextPhase == null) {
            return false;
        }
        if (TickObserver.zulrah != null) { //&& //zulrah.equals(actor) &&
            //  zulrah.getAnimation() == AnimationID.ZULRAH_PHASE) {
            Prayer prayer = nextPhase.getPrayer();

            if (prayer == null) {
                return false;
            }

            switch (prayer) {
                case PROTECT_FROM_MAGIC:
                    //  Log.log("Should pray Magic");
                    return Prayer.enableAll(Prayer.PROTECT_FROM_MAGIC);
                case PROTECT_FROM_MISSILES:
                    // Log.log("Should pray Missles");
                    return Prayer.enableAll(Prayer.PROTECT_FROM_MISSILES);

            }
        }
        return Prayer.disableAll();
    }

    @Override
    public String toString() {
        return "Changing Prayer";
    }

    @Override
    public Priority priority() {
        return Priority.HIGH;
    }

    @Override
    public boolean validate() {
        if (TickObserver.instance == null) {
            return false;
        }

        ZulrahPhase currentPhase = TickObserver.instance.getPhase();
        ZulrahPhase nextPhase = TickObserver.instance.getNextPhase();

        if (currentPhase == null || nextPhase == null) {
            return false;
        }
        if (TickObserver.zulrah != null) { //&& //zulrah.equals(actor) &&
            //  zulrah.getAnimation() == AnimationID.ZULRAH_PHASE) {
            Prayer prayer = nextPhase.getPrayer();

            if (prayer == null) {
                return false;
            }

            switch (prayer) {
                case PROTECT_FROM_MAGIC:
                    //  Log.log("Should pray Magic");
                    return !Prayer.isAllEnabled(Prayer.PROTECT_FROM_MAGIC);
                case PROTECT_FROM_MISSILES:
                    // Log.log("Should pray Missles");
                    return !Prayer.isAllEnabled(Prayer.PROTECT_FROM_MISSILES);

            }
        }
        return false;
    }

    @Override
    public void execute() {
        setPrayer();
    }
}
