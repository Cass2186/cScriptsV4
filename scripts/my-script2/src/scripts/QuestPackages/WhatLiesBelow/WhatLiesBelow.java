package scripts.QuestPackages.WhatLiesBelow;

import dax.walker_engine.interaction_handling.NPCInteraction;
import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSTile;
import scripts.ItemID;
import scripts.NpcID;
import scripts.QuestSteps.NPCStep;
import scripts.QuestSteps.QuestStep;
import scripts.QuestSteps.QuestTask;
import scripts.QuestSteps.UseItemOnObjectStep;
import scripts.Quests;
import scripts.Tasks.Priority;
import scripts.Tasks.Task;

public class WhatLiesBelow implements QuestTask {

    RSArea CHAOS_ALTAR_AREA = new RSArea(new RSTile(2245, 4823, 0), new RSTile(2299, 4860, 2));

    NPCStep talkToRat = new NPCStep(NpcID.RAT_BURGISS, new RSTile(3266, 3333, 0),
	 new String[]{"Shall I get them back for you?", "Of course! Tell me what you need me to do."});

    NPCStep killOutlaws = new NPCStep("Outlaw", new RSTile(3118, 3472, 0));
            //"Go to the Bandits west of the Grand Exchange and kill 5 for intel. Put the intel into the folder Rat gave you.", true, folder, intel5);
		//killOutlaws.addAlternateNpcs(NpcID.OUTLAW_4168,NpcID.OUTLAW_4169,NpcID.OUTLAW_4170,NpcID.OUTLAW_4171,NpcID.OUTLAW_4172,NpcID.OUTLAW_4173,NpcID.OUTLAW_4174,NpcID.OUTLAW_4175,NpcID.OUTLAW_4176);

    NPCStep bringFolderToRat = new NPCStep(NpcID.RAT_BURGISS, new RSTile(3266, 3333, 0));
    NPCStep talkToRatAfterFolder = new NPCStep(NpcID.RAT_BURGISS, new RSTile(3266, 3333, 0));
    //	bringFolderToRat.addSubSteps(talkToRatAfterFolder);

    NPCStep talkToSurok = new NPCStep(NpcID.SUROK_MAGIS, new RSTile(3211, 3493, 0));

    NPCStep talkToSurokNoLetter = new NPCStep(NpcID.SUROK_MAGIS, new RSTile(3211, 3493, 0),
            new String[]{"Go on, then!", "Go on then!"});

    UseItemOnObjectStep useWandOnAltar = new UseItemOnObjectStep(ItemID.WAND, 34769,
            new RSTile(2271, 4843, 0), NPCInteraction.waitForConversationWindow());
    //wandHighlight, chaosRunes15);

    NPCStep bringWandToSurok = new NPCStep(NpcID.SUROK_MAGIS, new RSTile(3211, 3493, 0),
            new String[]{"I have the things you wanted!"});

    NPCStep talkToRatAfterSurok = new NPCStep(NpcID.RAT_BURGISS, new RSTile(3266, 3333, 0),
		new String[]{"Yes! I have a letter for you."});

    NPCStep talkToRatAfterSurokNoLetter = new NPCStep(NpcID.RAT_BURGISS, new RSTile(3266, 3333, 0));

    NPCStep talkToZaff = new NPCStep("Zaff", new RSTile(3202, 3434, 0), new String[]{"Rat Burgiss sent me!"});
    NPCStep talkToSurokToFight = new NPCStep(NpcID.SUROK_MAGIS_4160, new RSTile(3211, 3493, 0),
		 new String[]{"Bring it on!"});

    NPCStep fightRoald = new NPCStep(NpcID.KING_ROALD_4163, new RSTile(3211, 3493, 0));
    NPCStep talkToRatToFinish = new NPCStep(NpcID.RAT_BURGISS, new RSTile(3266, 3333, 0));

    @Override
    public Priority priority() {
        return Priority.MEDIUM;
    }

    @Override
    public boolean validate() {
        return false;
    }

    @Override
    public void execute() {

    }

    @Override
    public String questName() {
        return "What Lies Below";
    }

    @Override
    public boolean checkRequirements() {
        return false;
    }

}
