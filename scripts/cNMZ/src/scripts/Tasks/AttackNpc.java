package scripts.Tasks;

public class AttackNpc implements Task {

    @Override
    public String toString() {
        return "Attacking NPC";
    }

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
}
