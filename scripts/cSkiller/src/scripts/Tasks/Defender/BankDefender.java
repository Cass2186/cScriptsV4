package scripts.Tasks.Defender;

import scripts.API.Priority;
import scripts.API.Task;

public class BankDefender implements Task {

    @Override
    public Priority priority() {
        return Priority.MEDIUM;
    }

    @Override
    public boolean validate() {
        return false;
    }

    @Override
    public void execute() {

    }

    @Override
    public String toString() {
        return "Banking";
    }
    @Override
    public String taskName() {
        return "Defender";
    }
}
