package scripts.Tasks.Agility.Tasks;

import dax.walker_engine.interaction_handling.NPCInteraction;
import org.tribot.api.General;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.ext.Filters;
import org.tribot.api2007.types.RSItem;
import scripts.API.Priority;
import scripts.API.Task;
import scripts.Data.SkillTasks;
import scripts.Data.Vars;
import scripts.Timer;
import scripts.Utils;

public class KittenTraining implements Task {


    int SHRIMPS = 315;
    int BALL_OF_WOOL = 1759;


    String[] CAT_NAMES = {"Pet cat", "Pet kitten", "Hell-cat", "Hell-kitten", "Hellcat", "Hell-kitten", "Lazy cat", "Wily cat"};

    Timer feedTimer = new Timer(General.random(1200000, 1680000)); //20-28m
    Timer playTimer = new Timer(General.random(2400000, 2880000)); //40-48min

    public void playWithCat() {
        RSItem[] wool = Inventory.find(BALL_OF_WOOL);
        if (wool.length > 0 && Utils.useItemOnNPC(BALL_OF_WOOL, CAT_NAMES)) {
            NPCInteraction.waitForConversationWindow();
            playTimer.reset();
        } else {
            //use interaction menu or  pick up
        }

    }

    public void feedCat() {
        RSItem[] food = Inventory.find(Filters.Items.actionsContains("Eat"));
        if (food.length > 0 && Utils.useItemOnNPC(food[0].getID(), CAT_NAMES)) {
            NPCInteraction.waitForConversationWindow();
            feedTimer.reset();
        } else {
            // pick up

        }

    }

    @Override
    public String toString() {
        return "Interacting with Kitten";
    }

    @Override
    public Priority priority() {
        return Priority.HIGHEST;
    }

    @Override
    public boolean validate() {
        return Vars.get().currentTask != null &&
                Vars.get().currentTask.equals(SkillTasks.AGILITY) &&
                !feedTimer.isRunning() || !playTimer.isRunning();
    }

    @Override
    public void execute() {
        if(!feedTimer.isRunning()){
            feedCat();
        } else if (!playTimer.isRunning()){
            playWithCat();
        }
    }

    @Override
    public String taskName() {
        return "Agility - Kitten Training";

    }
}
