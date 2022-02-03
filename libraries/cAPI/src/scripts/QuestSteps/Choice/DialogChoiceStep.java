package scripts.QuestSteps.Choice;

public class DialogChoiceStep extends WidgetChoiceStep {

  public DialogChoiceStep( String choice) {
        super(choice, 219, 1);
        shouldNumber = true;
    }

    public DialogChoiceStep(  int choiceId, String choice) {
        super(choiceId, choice, 219, 1);
        shouldNumber = true;
    }

    public DialogChoiceStep( int choice) {
        super( choice, 219, 1);
        shouldNumber = true;
    }

}
