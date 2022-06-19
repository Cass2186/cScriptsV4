package scripts.Tasks;

public class CannonTask implements Task{

    @Override
    public String toString() {
        return "Handling Cannon";
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
