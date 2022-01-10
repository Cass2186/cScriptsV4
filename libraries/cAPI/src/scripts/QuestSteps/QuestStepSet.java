package scripts.QuestSteps;

import scripts.Tasks.Task;

import java.util.List;

public abstract class QuestStepSet {

    List<QuestStep> steps;

    QuestStepSet(List<QuestStep> steps) {
        this.steps = steps;
    }

  /*  public Task getValidStep() {
        for (Task task : this) {
            if (task.validate()) {
                return task;
            }
        }
        return null;
    }*/
}
