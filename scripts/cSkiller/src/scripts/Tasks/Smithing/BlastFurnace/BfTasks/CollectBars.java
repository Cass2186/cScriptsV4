package scripts.Tasks.Smithing.BlastFurnace.BfTasks;


import dax.walker_engine.interaction_handling.NPCInteraction;
import org.tribot.api.General;
import org.tribot.api.input.Keyboard;
import org.tribot.api2007.Camera;
import org.tribot.api2007.Equipment;
import org.tribot.api2007.Interfaces;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.ext.Filters;
import org.tribot.api2007.types.RSItem;
import scripts.*;
import scripts.API.Priority;
import scripts.API.Task;
import scripts.Data.SkillTasks;
import scripts.Data.Vars;
import scripts.Tasks.Smithing.BlastFurnace.BfData.BfConst;

import java.awt.event.KeyEvent;

public class CollectBars implements Task {

    @Override
    public String toString() {
        return "Collecting Bars";
    }

    @Override
    public Priority priority() {
        return Priority.MEDIUM;
    }

    @Override
    public boolean validate() {
        if (Vars.get().currentTask != null && Vars.get().currentTask.equals(SkillTasks.SMITHING)) {
            if (Interfaces.isInterfaceSubstantiated(233, 2) || NPCInteraction.isConversationWindowUp()) {
                General.println("[Debug]: Handling level up interface");
                Keyboard.typeString(" ");
                NPCInteraction.handleConversation();
            }
            return Utils.getVarBitValue(Varbits.BAR_DISPENSER.getId()) > 1 && Inventory.getAll().length < 27;
        }
        return false;
    }

    @Override
    public void execute() {
        setCamera();
        General.println(Utils.getVarBitValue(Varbits.BAR_DISPENSER.getId()));
        if (Inventory.getAll().length > 25) {
            General.println("[Collect bars]: Inventory full failsafe");
            BankManager.open(true);
            BankManager.depositAllExcept(false, BfConst.ALL_COAL_BAG[0], BfConst.ALL_COAL_BAG[1],
                    BfConst.STAMINA_POTION[0], BfConst.STAMINA_POTION[1], BfConst.BUCKET_OF_WATER,
                    BfConst.STAMINA_POTION[2], BfConst.STAMINA_POTION[3], 995, BfConst.BUCKET, BfConst.ICE_GLOVES,
                    BfConst.GOLDSMITH_GAUNTLETS);

            Timer.waitCondition(() -> Inventory.find(Filters.Items.nameContains("bar")).length == 0, 3000, 5000);
            BankManager.close(true);
        }

        for (int i = 0; i < 3; i++) {
            if (Interfaces.isInterfaceSubstantiated(233, 2) || NPCInteraction.isConversationWindowUp())
                NPCInteraction.handleConversation();

            General.println("[CollectBars]: Collect Bars attempt:" + i);

            throwWater();

            if (!Equipment.isEquipped(BfConst.ICE_GLOVES))
                Utils.equipItem(BfConst.ICE_GLOVES);

            if (Utils.clickObject("Bar dispenser", "Take", false)) {
                Timer.waitCondition(() -> Interfaces.isInterfaceSubstantiated(270), 6000, 9000);
                if (Interfaces.isInterfaceSubstantiated(270)) {
                    if (InterfaceUtil.getInterfaceKeyAndPress(270, "Steel bar")) {
                        General.println("[Debug]: Button pressed");
                        if (Timer.waitCondition(() -> Inventory.find(Filters.Items
                                .nameContains("bar")).length > 5, 3500, 5000))
                            Keyboard.pressKeys(KeyEvent.VK_SPACE);

                        if (NPCInteraction.isConversationWindowUp())
                            NPCInteraction.handleConversation();

                        break;
                    }

                } else if (InterfaceUtil.getInterfaceKeyAndPress(270, "Iron bar")) {
                    General.println("[Debug]: Button pressed");
                    if (Timer.waitCondition(() -> Inventory.find(Filters.Items
                            .nameContains("bar")).length > 5, 3500, 5000))
                        Keyboard.pressKeys(KeyEvent.VK_SPACE);

                    if (NPCInteraction.isConversationWindowUp())
                        NPCInteraction.handleConversation();

                    break;
                }
                Keyboard.pressKeys(KeyEvent.VK_SPACE);
                Timer.waitCondition(() -> Inventory.find(Filters.Items.nameContains("bar")).length > 5,
                        3500, 5000);
                Keyboard.pressKeys(KeyEvent.VK_SPACE);
                if (NPCInteraction.isConversationWindowUp())
                    NPCInteraction.handleConversation();

                break;
            }
        }
    }

    @Override
    public String taskName() {
        return "Smithing - BF";
    }

    public void setCamera() {
        if (Camera.getCameraRotation() < 300 || Camera.getCameraRotation() > 335) {
            General.println("[CollectBars]: Setting camera rotation");
            Camera.setCameraRotation(General.random(300, 330));
        }
    }

    public void throwWater() {
        if (!Equipment.isEquipped(BfConst.ICE_GLOVES)) {
            RSItem[] invWater = Inventory.find(BfConst.BUCKET_OF_WATER);
            if (invWater.length == 0)
                fillWater();

            if (invWater.length > 0 && Utils.useItemOnObject(BfConst.BUCKET_OF_WATER, "Bar dispenser"))
                Timer.waitCondition(() -> Inventory.find(BfConst.BUCKET_OF_WATER).length == 0, 4500, 6000);

        }
    }

    public boolean fillWater() {
        if (!Equipment.isEquipped(BfConst.ICE_GLOVES)) {
            RSItem[] invBucket = Inventory.find(BfConst.BUCKET);

            if (invBucket.length > 0 && Utils.clickObject("Sink", "Fill-bucket", false))
                return Timer.waitCondition(() -> Inventory.find(BfConst.BUCKET_OF_WATER).length > 0, 5000, 8000);

        }
        return Inventory.find(BfConst.BUCKET_OF_WATER).length > 0;
    }

}

