package scripts.Tasks.Construction;

import dax.walker_engine.interaction_handling.NPCInteraction;
import org.tribot.api2007.Game;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Player;
import org.tribot.api2007.Skills;
import org.tribot.script.sdk.Waiting;
import org.tribot.script.sdk.query.Query;
import org.tribot.script.sdk.types.InventoryItem;
import org.tribot.script.sdk.types.Npc;
import org.tribot.script.sdk.walking.LocalWalking;
import scripts.API.Priority;
import scripts.API.Task;
import scripts.Data.SkillTasks;
import scripts.Data.Vars;
import scripts.InterfaceUtil;
import scripts.ItemId;
import scripts.Timer;
import scripts.Utils;

import java.util.Optional;

public class UnnotePlanks implements Task {


    public void leaveHouse() {
        if (Game.isInInstance() && Utils.clickObject(4525, "Enter", false)) {
            Timer.waitCondition(() -> !Game.isInInstance() &&
                            Query.npcs().nameContains("Phials").stream().findFirst().isPresent(),
                    7000, 9000);

        }
    }

    public void unnotePlanks() {
        Optional<InventoryItem> item = Query.inventory().nameContains("plank").isNoted().findFirst();
        Optional<Npc> phials = Query.npcs().nameContains("Phials").stream().findFirst();
        if (phials.isPresent() && item.isPresent()) {
            if (!phials.get().isVisible()){
                LocalWalking.walkTo(phials.get().getTile().translate(0,1));
                Waiting.waitNormal(750,220);
            }
            if(Utils.useItemOnNPC(item.get().getId(), phials.get().getId()) &&
                    Timer.waitCondition(Player::isMoving, 1200, 1800)) {
                Timer.waitCondition(NPCInteraction::isConversationWindowUp, 6000, 8000);

            }
        }
        if (NPCInteraction.isConversationWindowUp()) {
            InterfaceUtil.clickInterfaceText(219,1, "Exchange All");
            Timer.waitCondition(()-> Query.inventory().nameContains("plank")
                            .isNotNoted().findFirst().isPresent(),
                    2000,4000);
        }
    }


    @Override
    public String toString(){
        return "Unnoting Planks";
    }

    @Override
    public Priority priority() {
        return Priority.MEDIUM;
    }

    @Override
    public boolean validate() {
        if(Vars.get().currentTask != null && Vars.get().currentTask.equals(SkillTasks.CONSTRUCTION)){
            if (Skills.getActualLevel(Skills.SKILLS.CONSTRUCTION) < FURNITURE.WOODEN_CHAIR.getReqLevl()) {
                return Inventory.find(FURNITURE.CRUDE_CHAIR.getPlankId()).length < FURNITURE.CRUDE_CHAIR.getPlankNum();

            } else if (Skills.getActualLevel(Skills.SKILLS.CONSTRUCTION) < FURNITURE.BOOKCASE.getReqLevl()) {
                return Inventory.find(FURNITURE.WOODEN_CHAIR.getPlankId()).length < FURNITURE.WOODEN_CHAIR.getPlankNum();

            } else if (Skills.getActualLevel(Skills.SKILLS.CONSTRUCTION) < FURNITURE.OAK_TABLE.getReqLevl()) {
                return Inventory.find(FURNITURE.BOOKCASE.getPlankId()).length < FURNITURE.BOOKCASE.getPlankNum();

            } else if (Skills.getActualLevel(Skills.SKILLS.CONSTRUCTION) < FURNITURE.OAK_LARDER.getReqLevl()) {
                return Inventory.find(FURNITURE.OAK_TABLE.getPlankId()).length < FURNITURE.OAK_TABLE.getPlankNum();

            } else
                return Inventory.find(FURNITURE.OAK_LARDER.getPlankId()).length < FURNITURE.OAK_LARDER.getPlankNum();

        }
        return false;
    }

    @Override
    public void execute() {
        leaveHouse();
        unnotePlanks();
    }

    @Override
    public String taskName() {
        return "Construction";
    }
}
