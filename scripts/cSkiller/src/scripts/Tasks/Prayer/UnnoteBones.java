package scripts.Tasks.Prayer;

import dax.walker_engine.interaction_handling.NPCInteraction;
import org.tribot.api2007.Game;
import org.tribot.api2007.Player;
import org.tribot.script.sdk.ChatScreen;
import org.tribot.script.sdk.MyPlayer;
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
                            Query.npcs().nameContains("Phials").isAny(),
                    7000, 9000);

        }
    }

    public void unnoteBones() {
        Optional<InventoryItem> item = Query.inventory()
                .nameContains("bones").isNoted().findFirst();
        Optional<Npc> phials = Query.npcs()
                .nameContains("Phials").stream().findFirst();

        if (phials.map(p -> !p.isVisible() && LocalWalking.walkTo(p.getTile().translate(0, -2))).orElse(false)) {
            if (Waiting.waitUntil(1500, 450, MyPlayer::isMoving)) {
                Waiting.waitUntil(2500, 650, () ->
                        phials.map(p -> p.isVisible()).orElse(false));
                Utils.idleNormalAction();
            }
        }
        if (phials.map(p -> item.map(b -> b.useOn(p)).orElse(false)).orElse(false)) {
            Waiting.waitUntil(4500, 350, ChatScreen::isOpen);
        }
        if (ChatScreen.isOpen() && ChatScreen.handle("Exchange all") &&
                Timer.waitCondition(() -> Query.inventory()
                                .nameContains("bones").isNotNoted().isAny(),
                        2000, 4000)) {
            Utils.idlePredictableAction();
        }
    }

    @Override
    public String toString() {
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
                item.isEmpty() &&  !Vars.get().useWildernessAltar;
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
