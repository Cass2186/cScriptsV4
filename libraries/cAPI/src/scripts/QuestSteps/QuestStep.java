package scripts.QuestSteps;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import scripts.QuestSteps.Choice.DialogChoiceStep;
import scripts.QuestSteps.Choice.DialogChoiceSteps;
import scripts.QuestSteps.Choice.WidgetChoiceStep;
import scripts.QuestUtil;
import scripts.Requirements.Requirement;


public abstract class QuestStep {

    public abstract void execute();

    public abstract void addDialogStep(String... dialog);


    @Getter
    protected List<String> text;

    @Getter
    protected List<String> overlayText = new ArrayList<>();

    protected int ARROW_SHIFT_Y = 15;

    /* Locking applies to ConditionalSteps. Intended to be used as a method of forcing a step to run if it's been locked */
    private boolean locked;

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

    private int currentCutsceneStatus = 0;
    protected boolean inCutscene;

    @Setter
    protected boolean allowInCutscene = false;

    protected int iconItemID = -1;
    protected BufferedImage icon;

    //@Getter
    //protected final

    @Getter
    protected DialogChoiceSteps choices = new DialogChoiceSteps();

    //  @Getter
    // protected WidgetChoiceSteps widgetChoices = new WidgetChoiceSteps();

    @Getter
    private final List<QuestStep> substeps = new ArrayList<>();

    @Getter
    private Requirement conditionToHide;

    @Getter
    @Setter
    private boolean showInSidebar = true;

    public QuestStep()
    {

    }

    public QuestStep(String text) {
        // use explicit ArrayList because we need the 'text' list to be mutable
        this.text = new ArrayList<>(Collections.singletonList(text));
    }

    public QuestStep(List<String> text) {
        this.text = text;
    }

    public void setOverlayText(String text) {
        this.overlayText.add(text);
    }

    public void setOverlayText(String... text) {
        this.overlayText.addAll(Arrays.asList(text));
    }


    public void startUp() {
        //clientThread.invokeLater(this::highlightChoice);
        /// clientThread.invokeLater(this::highlightWidgetChoice);

        //  setupIcon();
    }

    public void shutDown() {

    }

    public void addSubSteps(QuestStep... substep) {
        this.substeps.addAll(Arrays.asList(substep));
    }

    public void addSubSteps(Collection<QuestStep> substeps) {
        this.substeps.addAll(substeps);
    }

    // @Subscribe
   /* public void onVarbitChanged(VarbitChanged event)
    {
        if (!allowInCutscene)
        {
            int newCutsceneStatus = client.getVarbitValue(QuestVarbits.CUTSCENE.getId());
            if (currentCutsceneStatus == 0 && newCutsceneStatus == 1)
            {
                enteredCutscene();
            }
            else if (currentCutsceneStatus == 1 && newCutsceneStatus == 0)
            {
                leftCutscene();
            }
            currentCutsceneStatus = newCutsceneStatus;
        }
    }

  //  @Subscribe
    public void onWidgetLoaded(WidgetLoaded event)
    {
        if (event.getGroupId() == WidgetID.DIALOG_OPTION_GROUP_ID) // 219
        {
            clientThread.invokeLater(this::highlightChoice);
        }

        for (WidgetChoiceStep choice : widgetChoices.getChoices())
        {
            if (event.getGroupId() == choice.getGroupIdForChecking())
            {
                clientThread.invokeLater(this::highlightWidgetChoice);
            }
        }
    }*/

    public void addText(String newLine) {
        if (text == null) {
            text = new ArrayList<>();
        }

        text.add(newLine);
    }

    public void enteredCutscene() {
        inCutscene = true;
    }

    public void leftCutscene() {
        inCutscene = false;
    }

    public void highlightChoice() {
        choices.checkChoices();
    }

    public void setText(String text) {
        this.text = QuestUtil.toArrayList(text);
    }

    public void setText(List<String> text) {
        this.text = text;
    }

    public void highlightWidgetChoice() {
        // widgetChoices.checkChoices(client);
    }


    public void addDialogChange(String choice, String newText) {
        //   choices.addChoice(new DialogChoiceChange( choice, newText));
    }

    public void addWidgetChoice(String text, int groupID, int childID) {
        //  widgetChoices.addChoice(new WidgetChoiceStep( text, groupID, childID));
    }

    public void addWidgetChoice(String text, int groupID, int childID, int groupIDForChecking) {
        WidgetChoiceStep newChoice = new WidgetChoiceStep(text, groupID, childID);
        newChoice.setGroupIdForChecking(groupIDForChecking);
        //  widgetChoices.addChoice(newChoice);

    }

    public void addWidgetChoice(int id, int groupID, int childID) {
        // widgetChoices.addChoice(new WidgetChoiceStep( id, groupID, childID));
    }

    public void addWidgetChange(String choice, int groupID, int childID, String newText) {
        //widgetChoices.addChoice(new WidgetTextChange( choice, groupID, childID, newText));
    }


    public void setLockedManually(boolean isLocked) {
        locked = isLocked;
    }

    public boolean isLocked() {
        boolean autoLocked = lockingCondition != null && lockingCondition.check();
        unlockable = !autoLocked;
        if (autoLocked) {
            locked = true;
        }
        return locked;
    }

    public QuestStep getActiveStep() {
        return this;
    }

    public QuestStep getSidePanelStep() {
        return getActiveStep();
    }


    public void conditionToHideInSidebar(Requirement hideCondition) {
        conditionToHide = hideCondition;
    }

}
