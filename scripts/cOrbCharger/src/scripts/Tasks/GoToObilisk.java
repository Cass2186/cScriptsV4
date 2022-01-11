package scripts.Tasks;

import org.tribot.script.sdk.walking.GlobalWalking;
import scripts.Data.Const;

public class GoToObilisk implements Task{
    @Override
    public Priority priority() {
        return Priority.LOW;
    }

    @Override
    public boolean validate() {
        return Const.bankTask.isSatisfied();
    }

    @Override
    public void execute() {
        GlobalWalking.walkTo(Const.OBILISK_TILE);
    }
    @Override
    public String toString() {
        return "Walking";
    }

}
