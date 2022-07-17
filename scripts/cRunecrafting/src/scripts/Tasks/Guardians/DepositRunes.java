package scripts.Tasks.Guardians;

import org.tribot.script.sdk.query.Query;
import scripts.Tasks.Priority;
import scripts.Tasks.Task;

public class DepositRunes implements Task {

    @Override
    public String toString() {
        return "Depositing Runes";
    }

    @Override
    public Priority priority() {
        return null;
    }

    @Override
    public boolean validate() {
        return false;
    }

    @Override
    public void execute() {

    }

    private boolean clickRuneDeposit(){
        return Query.gameObjects().actionContains("Deposit-runes")
                .findClosestByPathDistance().map(d->d.interact("Deposit-runes"))
                .orElse(false);
    }
}
