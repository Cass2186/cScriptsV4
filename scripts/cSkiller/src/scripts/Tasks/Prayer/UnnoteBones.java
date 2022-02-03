package scripts.Tasks.Prayer;

import dax.walker_engine.interaction_handling.NPCInteraction;
import org.tribot.api2007.Game;
import org.tribot.api2007.Player;
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
import scripts.Timer;
import scripts.Utils;

import java.util.Optional;

public class UnnoteBones implements Task {

    public void leaveHouse() {
        if (Game.isInInstance() && Utils.clickObject(4525, "Enter", false)) {
            Timer.waitCondition(() -> !Game.isInInstance() &&
                            Query.npcs().nameContains("Phials").stream().findFirst().isPresent(),
                    7000, 9000);

        }
    }

    public void unnoteBones() {
        Optional<InventoryItem> item = Query.inventory().nameContains("bones").isNoted().findFirst();
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
            Timer.waitCondition(()-> Query.inventory().nameContains("bones").isNotNoted().findFirst().isPresent(),
                    2000,4000);
        }
    }

    @Override
    public String toString(){
        return "Unnoting bones";
    }
    @Override
    public Priority priority() {
        return Priority.MEDIUM;
    }

    @Override
    public boolean validate() {
        Optional<InventoryItem> item = Query.inventory().nameContains("bones").isNotNoted().findFirst();
        return Vars.get().currentTask != null && Vars.get().currentTask.equals(SkillTasks.PRAYER) &&
                item.isEmpty();
    }

    @Override
    public void execute() {
        leaveHouse();
        unnoteBones();
    }

    @Override
    public String taskName() {
        return "Prayer training";
    }
}
