package scripts.Tasks.MakeTabs;

import dax.walker_engine.interaction_handling.NPCInteraction;
import org.tribot.api2007.Game;
import org.tribot.api2007.Player;
import org.tribot.script.sdk.ChatScreen;
import org.tribot.script.sdk.Inventory;
import org.tribot.script.sdk.Log;
import org.tribot.script.sdk.Waiting;
import org.tribot.script.sdk.query.Query;
import org.tribot.script.sdk.types.InventoryItem;
import org.tribot.script.sdk.types.Npc;
import org.tribot.script.sdk.walking.LocalWalking;
import scripts.InterfaceUtil;
import scripts.ItemID;
import scripts.Tasks.Priority;
import scripts.Tasks.Task;
import scripts.Timer;
import scripts.Utils;

import java.util.Optional;

public class UnnoteClay implements Task {

    public static boolean hasAnyClay() {
        return Inventory.contains(ItemID.SOFT_CLAY) ||
                Inventory.contains(ItemID.getNotedId(ItemID.SOFT_CLAY));
    }

    public Optional<Npc> getPhials() {
        return Query
                .npcs()
                .nameContains("Phials")
                .stream()
                .findFirst();
    }

    public void unNoteClay() {
        if (!MakeTabs.atLecturn()) {
            Log.debug("Unnoting clay");

            Optional<InventoryItem> notedClay = Query.inventory()
                    .nameContains("Soft clay")
                    .isNoted()
                    .findFirst();
            Optional<Npc> phials = getPhials();

            if (phials.isPresent() && notedClay.isPresent()) {
                if (!phials.get().isVisible()) {
                    LocalWalking.walkTo(phials.get().getTile().translate(0, 1));
                    Waiting.waitNormal(1250, 220);
                }
                if (notedClay.map(c -> c.useOn(phials.get())).orElse(false) &&
                        Timer.waitCondition(Player::isMoving, 1200, 1800)) {
                    Timer.waitCondition(NPCInteraction::isConversationWindowUp, 6000, 8000);

                }
            }
            if (NPCInteraction.isConversationWindowUp()) {
                if (InterfaceUtil.clickInterfaceText(219, 1, "Exchange All"))
                    Timer.waitCondition(() -> org.tribot.script.sdk.Inventory.contains(ItemID.SOFT_CLAY),
                            2000, 4000);
                else if (InterfaceUtil.clickInterfaceText(219, 1, "Exchange 5"))
                    Timer.waitCondition(() ->
                                    org.tribot.script.sdk.Inventory.contains(ItemID.SOFT_CLAY),
                            2000, 4000);

                if (NPCInteraction.isConversationWindowUp())
                    ChatScreen.handle();
            }
        } else {
            leaveHouse();
        }
    }

    public static void leaveHouse() {
        if (Game.isInInstance() && Utils.clickObject(4525, "Enter", false)) {
            Timer.waitCondition(() -> !Game.isInInstance() &&
                            Query.npcs().nameContains("Phials").stream().findFirst().isPresent(),
                    7000, 9000);
        }
    }


    @Override
    public Priority priority() {
        return Priority.MEDIUM;
    }

    @Override
    public boolean validate() {
        return !Inventory.contains(ItemID.SOFT_CLAY);
    }

    @Override
    public void execute() {
        unNoteClay();
    }

    @Override
    public String toString() {
        return "Unnoting Clay";
    }
}
