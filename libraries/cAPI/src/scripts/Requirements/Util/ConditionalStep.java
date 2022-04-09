package scripts.Requirements.Util;

import dax.shared.helpers.questing.Quest;
import lombok.Getter;
import lombok.Setter;
import org.tribot.api.General;
import org.tribot.script.sdk.Log;
import scripts.QuestSteps.QuestStep;
import scripts.Requirements.Requirement;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Optional;

public class ConditionalStep extends QuestStep {

    protected boolean started = false;

    /* Locking applies to ConditionalSteps. Intended to be used as a method of
     forcing a step to run if it's been locked */
    private boolean locked;

    protected final LinkedHashMap<Requirement, QuestStep> steps = new LinkedHashMap<>();
    //   protected final List<ChatMessageRequirement> chatConditions = new ArrayList<>();
    // protected final List<NpcCondition> npcConditions = new ArrayList<>();

    @Getter
    @Setter
    private boolean isLockable;

    @Getter
    @Setter
    private boolean blocker;

    @Getter
    private boolean unlockable = true;

    @Getter
    @Setter
    private Requirement lockingCondition;

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


    @Override
    public QuestStep getActiveStep() {
        if (currentStep != null) {
            return currentStep.getActiveStep();
        }

        return this;

    }

    public Collection<Requirement> getConditions(){
        return steps.keySet();
    }

  /*  protected void updateSteps() {
        Requirement lastPossibleCondition = null;

        for (Requirement conditions : steps.keySet()) {
            if (conditions != null && conditions.check()) {
                startUpStep(steps.get(conditions));
                return;
            }
        }

        //if (!steps.get(null).isLocked())
        startUpStep(steps.get(null));
    }*/

    protected void updateSteps()
    {
        Requirement lastPossibleCondition = null;

        for (Requirement conditions : steps.keySet())
        {
            boolean stepIsLocked = steps.get(conditions).isLocked();
            if (conditions != null && conditions.check() && !stepIsLocked)
            {
                startUpStep(steps.get(conditions));
                return;
            }
            else if (steps.get(conditions).isBlocker() && stepIsLocked)
            {
                startUpStep(steps.get(lastPossibleCondition));
                return;
            }
            else if (conditions != null && !stepIsLocked)
            {
                lastPossibleCondition = conditions;
            }
        }

        if (!steps.get(null).isLocked())
        {
            startUpStep(steps.get(null));
        }
        else
        {
            startUpStep(steps.get(lastPossibleCondition));
        }
    }


    protected void startUpStep(QuestStep step) {
        if (currentStep == null) {
            step.startUp();
            currentStep = step;
            return;
        }

        if (!step.equals(currentStep)) {
            shutDownStep();
            step.startUp();
            currentStep = step;
        }
    }

    protected void shutDownStep() {
        if (currentStep != null) {
            currentStep = null;
        }
    }


    public void setLockedManually(boolean isLocked) {
        locked = isLocked;
    }


    @Override
    public void execute() {
        updateSteps();

        if (requirements != null && Arrays.stream(requirements).anyMatch(r -> !r.check())) {
            General.println("[Conditional Step]: We failed a requirement to execute this Conditional step");
            return;
        }

        this.getActiveStep().execute();
    }

    @Override
    public void addDialogStep(String... dialog) {
        Log.error("Attempting to add Dialog step to Requirements.Util.ConditionalStep. This method isn't defined" );

    }

    @Override
    public void addSubSteps(QuestStep... substep) {
        Log.error("Attempting to add substeps to Requirements.Util.ConditionalStep. This method isn't defined" );
    }

    @Override
    public void addSubSteps(Collection<QuestStep> substeps) {
        Log.error("Attempting to add substeps to Requirements.Util.ConditionalStep. This method isn't defined" );

    }

}
