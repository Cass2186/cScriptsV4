package scripts.QuestSteps;

import scripts.Quests;

public interface QuestStep {

   void execute();

   void addDialogStep(String... dialog);



}
