package scripts.Tasks;

public interface Task {

    Priority priority();

    boolean validate();

    void execute();

}