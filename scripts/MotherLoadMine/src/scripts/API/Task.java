package scripts.API;

import scripts.Tasks.Priority;

public interface Task {

    Priority priority();

    boolean validate();

    void execute();

}