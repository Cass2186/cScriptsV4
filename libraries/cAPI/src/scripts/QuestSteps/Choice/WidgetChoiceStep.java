package scripts.QuestSteps.Choice;

import lombok.Getter;
import lombok.Setter;
import org.tribot.script.sdk.Widgets;
import org.tribot.script.sdk.types.Widget;
import scripts.InterfaceUtil;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class WidgetChoiceStep {

    //  protected final QuestHelperConfig config;

    @Getter
    private final String choice;

    protected List<String> excludedStrings;
    protected int excludedGroupId;
    protected int excludedChildId;

    private final int choiceById;

    @Getter
    protected final int groupId;
    protected final int childId;

    protected boolean shouldNumber = false;

    @Setter
    @Getter
    private int groupIdForChecking;

    public WidgetChoiceStep(String choice, int groupId, int childId) {
        //  this.config = config;
        this.choice = choice;
        this.choiceById = -1;
        this.groupId = groupId;
        this.groupIdForChecking = groupId;
        this.childId = childId;
    }

    public WidgetChoiceStep(int choiceId, int groupId, int childId) {
        //  this.config = config;
        this.choice = null;
        this.choiceById = choiceId;
        this.groupId = groupId;
        this.groupIdForChecking = groupId;
        this.childId = childId;
    }

    public WidgetChoiceStep(int choiceId, String choice, int groupId, int childId) {
        // this.config = config;
        this.choice = choice;
        this.choiceById = choiceId;
        this.groupId = groupId;
        this.groupIdForChecking = groupId;
        this.childId = childId;
    }

    public void addExclusion(int excludedGroupId, int excludedChildId, String excludedString) {
        this.excludedStrings = Collections.singletonList(excludedString);
        this.excludedGroupId = excludedGroupId;
        this.excludedChildId = excludedChildId;
    }

    public void addExclusions(int excludedGroupId, int excludedChildId, String... excludedStrings) {
        this.excludedStrings = Arrays.asList(excludedStrings);
        this.excludedGroupId = excludedGroupId;
        this.excludedChildId = excludedChildId;
    }

    //modified to select instead of highlight
    public void highlightChoice() {
        Optional<Widget> exclusionDialogChoice = Widgets.get(excludedGroupId, excludedChildId);
        if (exclusionDialogChoice.isPresent()) {
            Widget[] exclusionChoices = exclusionDialogChoice.get().getChildren().toArray(new Widget[0]);

            for (Widget currentExclusionChoice : exclusionChoices) {
                if (excludedStrings.contains(currentExclusionChoice.getText())) {
                    return;
                }
            }
        }
        Optional<Widget> dialogChoice = Widgets.get(groupId, childId);
        if (dialogChoice.isPresent()) {
            Widget[] choices = dialogChoice.get().getChildren().toArray(new Widget[0]);
            checkWidgets(choices);
            //   Widget[] nestedChildren = dialogChoice.get().getNestedChildren();
            // checkWidgets(nestedChildren);
        }
    }

    protected void checkWidgets(Widget[] choices) {
        if (choices != null && choices.length > 0) {
            if (choiceById != -1 && choices[choiceById] != null) {
                if (choice == null || choice.equals(choices[choiceById].getText())) {
                    choices[choiceById].click();
                    // highlightText(choices[choiceById], choiceById);
                }
            } else {
                for (int i = 0; i < choices.length; i++) {
                    if (choices[i].getText().equals(choice)) {
                        choices[i].click();
                        return;
                    }
                }
            }
        }
    }


}
