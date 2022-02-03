package scripts.QuestSteps.Choice;

import lombok.Getter;

import java.util.ArrayList;
import java.util.Collections;

public class DialogChoiceSteps {
    @Getter
    final private ArrayList<DialogChoiceStep> choices = new ArrayList<>();

    public DialogChoiceSteps(DialogChoiceStep... choices) {
        Collections.addAll(this.choices, choices);
    }

    public void addChoice(DialogChoiceStep choice) {
        choices.add(choice);
    }

    public void addDialogChoiceWithExclusion(DialogChoiceStep choice, String exclusionString)
    {
        choice.addExclusion(219, 1, exclusionString);
        addChoice(choice);
    }

    public void addDialogChoiceWithExclusions(DialogChoiceStep choice, String... exclusionStrings)
    {
        choice.addExclusions(219, 1, exclusionStrings);
        addChoice(choice);
    }

    public void checkChoices() {
        if (choices.size() == 0) {
            return;
        }

        for (DialogChoiceStep currentChoice : choices) {
            currentChoice.highlightChoice();
        }
    }

    /**
     * Clears all choices previously set for this dialogs step.
     */
    public void resetChoices() {
        choices.clear();
    }
}
