package scripts.Tasks.Construction;

import dax.walker_engine.interaction_handling.NPCInteraction;
import org.tribot.api2007.Game;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Player;
import org.tribot.api2007.Skills;
import org.tribot.script.sdk.ChatScreen;
import org.tribot.script.sdk.Log;
import org.tribot.script.sdk.Waiting;
import org.tribot.script.sdk.query.Query;
import org.tribot.script.sdk.types.InventoryItem;
import org.tribot.script.sdk.types.Npc;
import org.tribot.script.sdk.walking.LocalWalking;
import scripts.*;
import scripts.API.Priority;
import scripts.API.Task;
import scripts.Data.SkillTasks;
import scripts.Data.Vars;
import scripts.Tasks.Construction.MahoganyHomes.ConsVars;

import java.util.Optional;

public class UnnotePlanks implements Task {


    public void leaveHouse() {
        if (Game.isInInstance() && Utils.clickObject(4525, "Enter", false)) {
            Timer.slowWaitCondition(() -> !Game.isInInstance() &&
                            Query.npcs().nameContains("Phials").stream().findFirst().isPresent(),
                    9000, 12000);
            Waiting.waitNormal(1100, 75);
        }
    }

    public static void unnotePlanks(int plankId) {
        Optional<InventoryItem> item = Query.inventory()
                .nameContains("plank")
                .idEquals(plankId + 1)
                .isNoted()
                .findFirst();
        if (item.isEmpty())
            Log.warn("No planks");

        Optional<Npc> phials = Query.npcs().nameContains("Phials").findFirst();
        // walk closer if needed
        if (phials.map(p -> !p.isVisible() &&
                        LocalWalking.walkTo(p.getTile().translate(0, 1)))
                .orElse(false)) {
            Waiting.waitNormal(750, 220);
        }
        if (phials.map(p -> item.map(i -> i.useOn(p)).orElse(false)).orElse(false)
                && Timer.waitCondition(Player::isMoving, 1200, 1800)) {
            Timer.waitCondition(ChatScreen::isOpen, 6000, 8000);
        } else {
            Log.warn("Failed");
        }

        if (ChatScreen.isOpen()) {
            if (NpcChat.handle("Exchange All"))
                Timer.waitCondition(() -> Query.inventory().nameContains("plank")
                                .isNotNoted().findFirst().isPresent(),
                        2000, 4000);
            else if (InterfaceUtil.clickInterfaceText(219, 1, "Exchange 5"))
                Timer.waitCondition(() -> Query.inventory().nameContains("plank")
                                .isNotNoted().findFirst().isPresent(),
                        2000, 4000);

            if (ChatScreen.isOpen())
                NpcChat.handle();
        }
    }


    @Override
    public String toString() {
        return "Unnoting Planks";
    }

    @Override
    public Priority priority() {
        return Priority.MEDIUM;
    }

    @Override
    public boolean validate() {
        if (Vars.get().currentTask != null && Vars.get().currentTask.equals(SkillTasks.CONSTRUCTION) &&
                !ConsVars.get().isDoingHomes) {
            Optional<InventoryItem> item = Query.inventory().nameContains("plank").isNoted().findFirst();
            if (item.isEmpty())
                return false;
            Optional<FURNITURE> currentItem = FURNITURE.getCurrentItem();

            if (currentItem.isPresent())
                return currentItem.map(c -> Inventory.find(c.getPlankId()).length <
                        c.getPlankNum()).orElse(false);

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
        if (!Construction.RIMMINGTON.contains(Player.getPosition()) && !Game.isInInstance()) {
            PathingUtil.walkToArea(Construction.RIMMINGTON);
        }
        Optional<FURNITURE> furn = FURNITURE.getCurrentItem();
        furn.ifPresent(f -> unnotePlanks(f.getPlankId()));
    }

    @Override
    public String taskName() {
        return "Construction";
    }
}
