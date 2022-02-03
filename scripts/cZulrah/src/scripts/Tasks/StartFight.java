package scripts.Tasks;

public class StartFight implements Task {

    @Override
    public String toString() {
        return "Starting Fight";
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
}
