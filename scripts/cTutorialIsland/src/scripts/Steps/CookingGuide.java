package scripts.Steps;

import org.tribot.api.Timing;
import org.tribot.api2007.Game;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.types.RSTile;
import scripts.Data.Const;
import scripts.QuestSteps.NPCStep;
import scripts.QuestSteps.ObjectStep;
import scripts.QuestSteps.UseItemOnItemStep;
import scripts.Tasks.Priority;
import scripts.Tasks.Task;
import scripts.Timer;
import scripts.Utils;

public class CookingGuide implements Task {

    NPCStep talkToCook = new NPCStep("Master Chef", new RSTile(3076, 3084, 0));
    UseItemOnItemStep makeDoughStep = new UseItemOnItemStep(Const.BUCKET_OF_WATER, Const.POT_OF_FLOUR,
            Inventory.find("Bread dough").length > 0);
    ObjectStep cookRange = new ObjectStep(9736, new RSTile(3076, 3084, 0), "Cook",
            Inventory.find("Bread").length > 0);

    @Override
    public String toString() {
        return "Cooking Guide";
    }

    @Override
    public Priority priority() {
        return Priority.LOW;
    }

    @Override
    public boolean validate() {
        return Game.getSetting(Const.GAME_SETTING) >= 100 &&
                Game.getSetting(Const.GAME_SETTING) < 170;
    }

    @Override
    public void execute() {
        if (Game.getSetting(Const.GAME_SETTING) >= 100 &&
                Game.getSetting(Const.GAME_SETTING) < 150) {
            talkToCook.execute();
        } else if (Game.getSetting(Const.GAME_SETTING) == 150) {
            makeDoughStep.execute();
        } else if (Game.getSetting(Const.GAME_SETTING) == 160) {
            cookRange.execute();
        }
    }
}
