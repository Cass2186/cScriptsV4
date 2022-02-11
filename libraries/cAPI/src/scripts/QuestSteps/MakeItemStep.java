package scripts.QuestSteps;

import org.tribot.api2007.Interfaces;
import org.tribot.script.sdk.Inventory;
import org.tribot.script.sdk.MakeScreen;
import scripts.Timer;

public class MakeItemStep extends DetailedQuestStep {

    private int itemToMakeID;

    public MakeItemStep() {

    }

    public MakeItemStep(int itemToMakeID) {
        this.itemToMakeID = itemToMakeID;
    }

    @Override
    public void execute() {
        if (MakeScreen.isOpen() && MakeScreen.make(this.itemToMakeID)) {
            Timer.waitCondition(() -> Inventory.contains(this.itemToMakeID), 3500, 5000);
        }
    }
}
