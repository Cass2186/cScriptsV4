package scripts.Tasks.Runecrafting.Ourania;

import org.tribot.api.General;
import org.tribot.api2007.ext.Filters;
import org.tribot.api2007.types.RSItem;
import org.tribot.script.sdk.*;
import org.tribot.script.sdk.query.Query;
import org.tribot.script.sdk.types.*;
import org.tribot.script.sdk.walking.LocalWalking;
import scripts.*;
import scripts.API.Priority;
import scripts.API.Task;
import scripts.Data.SkillTasks;
import scripts.Data.Vars;
import scripts.Tasks.BirdHouseRuns.Nodes.Wait;
import scripts.Tasks.Runecrafting.RunecraftData.RcConst;
import scripts.Tasks.Runecrafting.RunecraftData.RcVars;

import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

public class BankOurania implements Task {
    private static int WIDGET_BANK = 106;
    private static  Area TELE_AREA = Area.fromRadius(new WorldTile(2466, 3248, 0), 6);
    private static  WorldTile BANK_TILE = new WorldTile(3015, 5625, 0);
    private static WorldTile[] PATH_TO_LADDER = {
            new WorldTile(2466, 3248, 0),
            new WorldTile(2466, 3248, 0),
            new WorldTile(2465, 3248, 0),
            new WorldTile(2464, 3248, 0),
            new WorldTile(2463, 3249, 0),
            new WorldTile(2462, 3249, 0),
            new WorldTile(2461, 3249, 0),
            new WorldTile(2460, 3249, 0),
            new WorldTile(2459, 3249, 0),
            new WorldTile(2458, 3249, 0),
            new WorldTile(2457, 3249, 0),
            new WorldTile(2456, 3249, 0),
            new WorldTile(2455, 3249, 0),
            new WorldTile(2454, 3248, 0),
            new WorldTile(2454, 3247, 0),
            new WorldTile(2454, 3246, 0),
            new WorldTile(2454, 3245, 0),
            new WorldTile(2454, 3244, 0),
            new WorldTile(2454, 3243, 0),
            new WorldTile(2454, 3242, 0),
            new WorldTile(2454, 3241, 0),
            new WorldTile(2454, 3240, 0),
            new WorldTile(2454, 3239, 0),
            new WorldTile(2454, 3238, 0),
            new WorldTile(2454, 3237, 0),
            new WorldTile(2454, 3236, 0),
            new WorldTile(2454, 3235, 0),
            new WorldTile(2454, 3234, 0),
            new WorldTile(2454, 3233, 0),
            new WorldTile(2454, 3232, 0),
            new WorldTile(2453, 3232, 0),
            new WorldTile(2453, 3231, 0)
    };

    private static boolean goToBank() {
        if (BANK_TILE.distance() > 10 && !TELE_AREA.containsMyPlayer()) {
            if (Magic.selectSpell("Ourania Teleport")) {
                Waiting.waitUntil(3500, 500, () -> TELE_AREA.containsMyPlayer());
            }

        }
        if (LocalWalking.walkPath(List.of(PATH_TO_LADDER))) {
            Log.info("Local Walking");
            Optional<GameObject> climb = Query.gameObjects().actionContains("Climb").findClosestByPathDistance();
            Waiting.waitUntil(5500, 150, () -> climb.map(c -> c.isVisible()).orElse(false));

            if (climb.map(c -> c.interact("Climb")).orElse(false))
                return Waiting.waitUntil(4500, 500, () -> BANK_TILE.distance() < 10);

        } else if(TELE_AREA.containsMyPlayer()) {
            Log.info("Walking to Bank");
            PathingUtil.walkToTile(BANK_TILE);
        }
        return BANK_TILE.distance() < 10;
    }

    public static boolean openBank() {
        if (goToBank() && !Bank.isOpen()) {
            Optional<Npc> eniola = Query.npcs().nameContains("Eniola").findClosestByPathDistance();
            if (eniola.map(e -> e.interact("Bank")).orElse(false)) {
                Waiting.waitUntil(4500, 150, () ->
                        Widgets.isVisible(WIDGET_BANK));
            }
            if (Widgets.isVisible(WIDGET_BANK)) {
                Optional<Widget> widget = Query.widgets().inIndexPath(WIDGET_BANK).nameContains("Mind Rune").findFirst();
                if (widget.map(w -> w.click()).orElse(false)) {
                    return Waiting.waitUntil(2500, 75, () -> Bank.isOpen());
                }
            }
        }
        return Bank.isOpen();
    }

    public void fillPouches() {
        List<InventoryItem> p = Query.inventory().nameContains("pouch")
                .actionContains("Fill")
                .toList();
        for (InventoryItem pouch : p) {
            if (Inventory.getCount(ItemID.PURE_ESSENCE) < 5) {
                BankManager.withdraw(0, true, ItemID.PURE_ESSENCE);
            }
            if (pouch.click("Fill"))
                AntiBan.waitItemInteractionDelay();
        }
        General.sleep(500, 900);
    }

    private void bank() {
        if (openBank()) {

            List<InventoryItem> miscRunes = Query.inventory().nameContains("Rune")
                    .nameNotContains("pouch")
                    .toList();
            for (InventoryItem rune : miscRunes) {
                Bank.deposit(rune, rune.getStack());
                AntiBan.waitItemInteractionDelay();
            }

            fillPouches();
            if (!Query.inventory().nameContains("Stamina").isAny())
                BankManager.withdrawArray(ItemID.STAMINA_POTION, 1);

            if (MyPlayer.getCurrentHealthPercent() < 70) {
                BankManager.withdraw(1, true, ItemID.MONKFISH);
                EatUtil.eatFood(false);
            }

            BankManager.withdraw(0, true, ItemID.PURE_ESSENCE);
            BankManager.close(true);
            if (Inventory.contains(ItemID.DEGRADED_LARGE_POUCH, ItemID.DEGRADED_MEDIUM_POUCH)) {
                General.println("[Debug]: We need to repair a pouch");
                RcVars.get().needToRepairPouches = true;
                return;
            }
            if (!Inventory.contains(ItemID.PURE_ESSENCE))
                throw new NullPointerException("no ess");
        }

    }


    @Override
    public Priority priority() {
        return Priority.HIGH;
    }

    @Override
    public boolean validate() {
        return Vars.get().currentTask != null &&
                Vars.get().currentTask.equals(SkillTasks.RUNECRAFTING) &&
                !Inventory.contains(ItemID.PURE_ESSENCE) &&
                RcVars.get().usingOuraniaAlter;
    }

    @Override
    public void execute() {
        bank();
    }

    @Override
    public String toString() {
        return "Banking - Ourania";
    }

    @Override
    public String taskName() {
        return "Runecrafting";
    }
}
