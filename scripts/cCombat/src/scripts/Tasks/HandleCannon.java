package scripts.Tasks;

public class HandleCannon  implements Task{

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
        return "Handling Cannon";
    }
}
