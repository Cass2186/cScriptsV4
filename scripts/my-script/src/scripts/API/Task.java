package scripts.API;

public interface Task {

    Priority priority();

    boolean validate();

    void execute();

    String course();

}