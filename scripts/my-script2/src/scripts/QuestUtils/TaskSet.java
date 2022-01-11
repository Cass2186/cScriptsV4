package scripts.QuestUtils;

import scripts.QuestSteps.QuestTask;
import scripts.Tasks.Task;

import java.util.*;

public class TaskSet extends TreeSet<QuestTask> {

    public QuestTask previousTask;

    public TaskSet() {
        super(Comparator.comparing(QuestTask::priority).thenComparing(Comparator.comparing(task -> task.getClass().getName())));
    }

    public TaskSet(QuestTask... tasks) {
        super(Comparator.comparing(QuestTask::priority).thenComparing(Comparator.comparing(task -> task.getClass().getName())));
        addAll(tasks);
    }

    public TaskSet(Comparator<? super QuestTask> comparator) {
        this();
    }

    public TaskSet(Collection<? extends QuestTask> c) {
        this(c.toArray(new QuestTask[c.size()]));
    }

    public TaskSet(SortedSet<QuestTask> s) {
        this(s.toArray(new QuestTask[s.size()]));
    }

    public boolean addAll(QuestTask... tasks) {
        return super.addAll(Arrays.asList(tasks));
    }

    public boolean addAll(List<QuestTask> tasks) {
        return super.addAll(tasks);
    }

    public QuestTask getValidTask() {
        for (QuestTask task : this) {
            if (task.validate()) {
                return task;
            }
        }
        return null;
    }

    public boolean sameAsPreviousTask(QuestTask task) {
        return previousTask != null && previousTask.equals(task);
    }

}