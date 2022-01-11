package scripts.Tasks;


public class ExampleTask implements Task {

    @Override
    public String toString() {
        return "Example Task";
    }

    @Override
    public Priority priority() {
        return Priority.LOW;
    }

    @Override
    public boolean validate() {
        return false;
    }

    @Override
    public void execute() {

    }

}

