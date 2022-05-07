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

    private Optional<Npc> getPhials() {
        return Query.npcs()
                .nameContains("Phials")
                .findClosestByPathDistance();
    }

    private void unNoteClay() {
        if (!MakeTabs.atLecturn()) {
            Log.debug("Unnoting clay");

            Optional<InventoryItem> notedClay = Query.inventory()
                    .nameContains("Soft clay")
                    .isNoted()
                    .findFirst();
            Optional<Npc> phials = getPhials();

            if (phials.map(p -> !p.isVisible() &&
                    LocalWalking.walkTo(p.getTile().translate(0, 2))).orElse(false))
                Waiting.waitNormal(1750, 200);

            if (phials.map(p -> notedClay.map(c -> c.useOn(p)).orElse(false)).orElse(false) &&
                    Waiting.waitUntil(1800, 150, Player::isMoving)) {
                Waiting.waitUntil(7500, 400, ChatScreen::isOpen);

            }
            if (ChatScreen.isOpen()) {
                if (ChatScreen.handle("Exchange All"))
                    Waiting.waitUntil(3500, 250, () -> Inventory.contains(ItemID.SOFT_CLAY));
                else if (InterfaceUtil.clickInterfaceText(219, 1, "Exchange 5"))
                    Waiting.waitUntil(3500, 250, () ->
                            Inventory.contains(ItemID.SOFT_CLAY));

                // if still open
                if (ChatScreen.isOpen())
                    ChatScreen.handle();
            }
        } else {
            leaveHouse();
        }
    }

    public static void leaveHouse() {
        if (Game.isInInstance() && Utils.clickObject(4525, "Enter", false)) {
            Waiting.waitUntil(9000, 750, () -> !Game.isInInstance() &&
                    Query.npcs().nameContains("Phials").stream().findFirst().isPresent());
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
