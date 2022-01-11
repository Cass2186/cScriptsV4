package scripts.Nodes;

import scripts.Tasks.Priority;
import scripts.Tasks.Task;

public class Hespori implements Task {

    String ENTANGLED_STRING = "The Hespori entangles you in some vines!";
    String FREED_FROM_VINES = "You manage to break free of the vines!";

    /**
     * Notes
     * Hespori is an object initially
     * Flowers are NPCs
     */

    @Override
    public void execute() {

    }

    @Override
    public Priority priority() {
        return Priority.LOW;
    }

    @Override
    public boolean validate() {
        return false;
    }
}
