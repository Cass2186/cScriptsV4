package scripts.Requirements.Quest;

import lombok.Getter;
import org.tribot.script.sdk.Quest;
import scripts.Requirements.Requirement;

@Getter
public class QuestRequirement implements Requirement {

    private final Quest quest;
    private final Quest.State requiredState;
    private final Integer minimumVarValue;
    private String displayText = null;

    /**
     *
     * @param quest         the quest to check
     * @param requiredState the required quest state
     */
    public QuestRequirement(Quest quest, Quest.State requiredState) {
        this.quest = quest;
        this.requiredState = requiredState;
        this.minimumVarValue = null;
      //  shouldCountForFilter = true;
    }

    /**

     *
     * @param quest         the quest to check
     * @param requiredState the required quest state
     * @param displayText   display text
     */
    public QuestRequirement(Quest quest, Quest.State requiredState, String displayText) {
        this(quest, requiredState);
        this.displayText = displayText;
    }

    /**

     *
     * @param quest           the quest to check
     * @param minimumVarValue the required quest state
     */
    public QuestRequirement(Quest quest, int minimumVarValue) {
        this.quest = quest;
        this.requiredState = null;
        this.minimumVarValue = minimumVarValue;
       // shouldCountForFilter = true;
    }

    /**

     *
     * @param quest           the quest to check
     * @param minimumVarValue the required quest state
     * @param displayText     display text
     */
    public QuestRequirement(Quest quest, int minimumVarValue, String displayText) {
        this(quest, minimumVarValue);
        this.displayText = displayText;
    }

    @Override
    public boolean check() {
        if (minimumVarValue != null) {
            return quest.getStep() >= minimumVarValue;
        }

        Quest.State state = quest.getState();
        if (requiredState == Quest.State.IN_PROGRESS && state == Quest.State.COMPLETE) {
            return true;
        }
        return state == requiredState;
    }


    public String getDisplayText() {
        if (displayText != null && !displayText.isEmpty()) {
            return displayText;
        }
        String text = Character.toUpperCase(requiredState.name().charAt(0)) + requiredState.name().toLowerCase().substring(1);
        if (requiredState == Quest.State.IN_PROGRESS) {
            text = "Started ";
        }
        return text.replaceAll("_", " ") + " " + quest.name();
    }
}