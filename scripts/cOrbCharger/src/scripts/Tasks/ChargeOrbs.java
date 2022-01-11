package scripts.Tasks;

import org.tribot.script.sdk.Magic;
import org.tribot.script.sdk.MyPlayer;
import scripts.Data.Const;

public class ChargeOrbs implements Task{


    @Override
    public Priority priority() {
        return Priority.LOW;
    }

    @Override
    public boolean validate() {
        return Const.OBILISK_TILE.distanceTo(MyPlayer.getPosition()) < 3;
    }

    @Override
    public void execute() {

       // if (Magic.castOn("Charge Air Orb", ))
    }
}
