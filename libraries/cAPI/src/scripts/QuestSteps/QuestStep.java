package scripts.QuestSteps;

import scripts.Quests;

import java.util.Collection;

public interface QuestStep {

   void execute();

   void addDialogStep(String... dialog);

   void addSubSteps(QuestStep... substep);

   void addSubSteps(Collection<QuestStep> substeps);




}
