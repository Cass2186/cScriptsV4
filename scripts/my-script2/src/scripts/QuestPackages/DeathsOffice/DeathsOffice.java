package scripts.QuestPackages.DeathsOffice;

import org.tribot.api2007.Game;
import org.tribot.api2007.NPCs;
import org.tribot.api2007.types.RSNPC;
import scripts.QuestPackages.MonkeyMadnessI.MonkeyMadnessI;
import scripts.QuestSteps.QuestTask;
import scripts.Requirements.ItemRequirement;
import scripts.Requirements.Requirement;
import scripts.Tasks.Priority;

import java.util.List;

public class DeathsOffice implements QuestTask {

    private static DeathsOffice quest;

    public static DeathsOffice get() {
        return quest == null ? quest = new DeathsOffice() : quest;
    }


    private static String[] FIRST_TIME_ARRAY = {
            "How do I pay a gravestone fee?",
            "How long do I have to return to my gravestone?",
            "How do I know what will happen to my items when I die?",
            "I think I'm done here."
    };

    public static boolean shouldHandleDeath() {
        RSNPC[] death = NPCs.findNearest("Death");
        return death.length > 0 || Game.getSetting(1697) > 0;

    }


    @Override
    public Priority priority() {
        return Priority.HIGH;
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
        return null;
    }

    @Override
    public boolean checkRequirements() {
        return false;
    }
    @Override
    public List<Requirement> getGeneralRequirements() {
        return null;
    }

    @Override
    public List<ItemRequirement> getBuyList() {
        return null;
    }
}
