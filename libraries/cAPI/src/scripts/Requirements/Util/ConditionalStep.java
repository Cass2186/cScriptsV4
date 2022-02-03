package scripts.Requirements.Util;

import dax.shared.helpers.questing.Quest;
import org.tribot.api.General;
import scripts.QuestSteps.QuestStep;
import scripts.Requirements.Requirement;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Optional;

public class ConditionalStep implements QuestStep {

    protected boolean started = false;

    protected final LinkedHashMap<Requirement, QuestStep> steps = new LinkedHashMap<>();
    //   protected final List<ChatMessageRequirement> chatConditions = new ArrayList<>();
    // protected final List<NpcCondition> npcConditions = new ArrayList<>();

    protected QuestStep currentStep;

    protected Requirement[] requirements;

    public ConditionalStep(QuestStep step, Requirement... requirements) {
        this.requirements = requirements;
        this.steps.put(null, step);
    }


    public void addStep(Requirement requirement, QuestStep step) {
        this.steps.put(requirement, step);
    }

    public void addStep(Conditions cond, QuestStep step) {
        this.steps.put(cond, step);
    }


    public Optional<QuestStep> getActiveStep() {
        return Optional.ofNullable(currentStep);
    }

    protected void updateSteps() {
        Requirement lastPossibleCondition = null;

        for (Requirement conditions : steps.keySet()) {
            if (conditions != null && conditions.check()) {
                startUpStep(steps.get(conditions));
                return;
            }
        }

       // if (!steps.get(null).isLocked())
        startUpStep(steps.get(null));

    }

    protected void startUpStep(QuestStep step) {
        if (currentStep == null) {
            currentStep = step;
            return;
        }

        if (!step.equals(currentStep)) {
            shutDownStep();
            currentStep = step;
        }
    }


    protected void shutDownStep() {
        if (currentStep != null) {
            currentStep = null;
        }
    }
    @Override
    public void execute() {
        updateSteps();

        if (requirements != null && Arrays.stream(requirements).anyMatch(r -> !r.check())) {
            General.println("[Conditional Step]: We failed a requirement to execute this Conditional step");
            return;
        }

        this.getActiveStep().ifPresent(QuestStep::execute);
    }

    @Override
    public void addDialogStep(String... dialog) {

    }

    @Override
    public void addSubSteps(QuestStep... substep) {

    }

    @Override
    public void addSubSteps(Collection<QuestStep> substeps) {

    }
}
