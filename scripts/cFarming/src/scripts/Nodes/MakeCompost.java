package scripts.Nodes;

import dax.walker_engine.interaction_handling.NPCInteraction;
import org.tribot.api2007.Interfaces;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Objects;
import org.tribot.api2007.Player;
import org.tribot.api2007.ext.Filters;
import org.tribot.api2007.types.RSItem;
import scripts.Data.Const;
import scripts.InterfaceUtil;
import scripts.Tasks.Priority;
import scripts.Tasks.Task;
import scripts.Timer;
import scripts.Utils;


public class MakeCompost implements Task {

    private void fillCompostUnit(int produceId) {
        if (Utils.useItemOnObject(produceId, "Compost bin")) {
            Timer.abc2WaitCondition(() -> Inventory.find(produceId).length < 1 ||
                    Objects.findNearest(10, Filters.Objects.actionsContains("Close")).length > 0, 15000, 25000);
        }
        if (Objects.findNearest(10, Filters.Objects.actionsContains("Close")).length > 0) {
            if (Utils.clickObject("Compost Bin", "Close", false)) {
                Timer.waitCondition(() -> Player.getAnimation() != -1, 7000, 10000);
                Utils.shortSleep();
            }
        }
    }

    private void fillBuckets() {
        if (Objects.findNearest(10, Filters.Objects.actionsContains("Take")).length > 0
                && Inventory.find(Const.BUCKET).length > 0) {
            if (Utils.clickObject("Compost Bin", "Take", false)) {
                Timer.abc2WaitCondition(() -> Inventory.find(Const.BUCKET).length < 1 ||
                        Objects.findNearest(10, Filters.Objects.actionsContains("Fill")).length == 0, 15000, 25000);

            }
        }
    }

    private void getEmptyBuckets() {
        if (Inventory.find(Const.BUCKET).length < 1) {
            if (Utils.clickNPC("Tool Leprechaun", "Exchange")) {

            }
        }
    }

    private void exchangeUltraCompost() {
        // check we have full buckets
        if (Utils.clickNPC("Tool Leprechaun", "Exchange")) {

        }
    }

    private void makeUltraCompost() {
        if (Objects.findNearest(10, Filters.Objects.actionsContains("Take")).length > 0) {
            if (Utils.useItemOnObject(Const.VOLCANIC_ASH, "Compost Bin")) {
                NPCInteraction.waitForConversationWindow();
                Utils.shortSleep();
            }
        }
    }

    public int getBottomlessUses() {
        RSItem[] i = Inventory.find(Const.BOTTOMLESS_COMPOST);
        if (i.length > 0 && i[0].click("Fill/Check"))
            Timer.waitCondition(NPCInteraction::isConversationWindowUp, 3500, 5000);

        if (NPCInteraction.isConversationWindowUp()) {
            if (InterfaceUtil.clickInterfaceText(219, 1, "Check the contents of the bucket."))
                Timer.waitCondition(() -> Interfaces.isInterfaceSubstantiated(193, 2), 3500, 5000);
        }

        if (Interfaces.isInterfaceSubstantiated(193, 2)){
            String txt = Interfaces.get(193,2).getText();
            if (txt != null){
               String[] split =  txt.split("holding ");
            }
        }

        return 0;
    }

    public void refillBottomlessCompost() {

    }


    @Override
    public void execute() {

    }

    @Override
    public Priority priority() {
        return Priority.LOW;
    }

    @Override
    public boolean validate() {
        return false;
    }
}
