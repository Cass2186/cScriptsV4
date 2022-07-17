package scripts.Tasks.Runecrafting;

import org.tribot.api.General;
import org.tribot.api2007.Equipment;
import org.tribot.api2007.GameTab;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Objects;
import org.tribot.api2007.ext.Filters;
import org.tribot.api2007.types.RSItem;
import org.tribot.api2007.types.RSObject;
import org.tribot.api2007.types.RSTile;
import org.tribot.script.sdk.Log;
import scripts.API.Priority;
import scripts.API.Task;
import scripts.Data.SkillTasks;
import scripts.Data.Vars;
import scripts.ItemID;
import scripts.PathingUtil;
import scripts.Tasks.Runecrafting.RunecraftData.RcConst;
import scripts.Tasks.Runecrafting.RunecraftData.RcVars;
import scripts.Timer;
import scripts.Utils;

public class GoToRcAltar implements Task {

    public void goToEarthAltar() {
        if (((Inventory.find(ItemID.PURE_ESSENCE).length > 0 ||
                Inventory.find("Tiara").length > 0)
                && Equipment.isEquipped(ItemID.EARTH_TIARA)) && !atAltar()) {

            PathingUtil.walkToTile(new RSTile(3305, 3472, 0), 3, false);

            if (Utils.clickObj("Mysterious ruins", "Enter")) {
                Timer.waitCondition(() -> Objects.findNearest(30, "Altar").length > 0, 7000, 12000);
            }
        }
    }


    public void goToFireAltar() {
        RSObject[] obj = Objects.findNearest(10,
                Filters.Objects.nameContains("Bank chest"));

        if (obj.length > 0 && rodTele("PVP Arena")) {
            Timer.waitCondition(() -> Objects.findNearest(10,
                    Filters.Objects.nameContains("Bank chest")).length == 0, 3000, 5000);
        }

        RSObject[] altar = Objects.findNearest(30,
                Filters.Objects.nameContains("Altar")
                        .and(Filters.Objects.actionsNotContains("Pray")));
        if (altar.length == 0) {
            General.println("[Debug] Going to fire altar");
            PathingUtil.walkToTile(RcConst.FIRE_ALTAR_TILE_BEFORE_RUINS, 8, false);

            if (RcVars.get().usingLunarImbue)
                GameTab.open(GameTab.TABS.MAGIC);

            Log.info("Entering Ruins");
            if (Utils.clickObj("Mysterious ruins", "Enter")) {
                Timer.waitCondition(() -> Objects.findNearest(30, "Altar").length > 0, 7000, 12000);
                General.sleep(500);
            }
        }
    }

    public void goToCosmicAltar() {
        RSObject[] altar = Objects.findNearest(30, Filters.Objects.nameContains("Altar").
                and(Filters.Objects.actionsNotContains("Pray")));
        if (altar.length == 0) {
            General.println("[Debug]: Going to Cosmic altar - Zanaris");
            PathingUtil.walkToArea(RcConst.ZANARIS_ALTAR, false);

        }
        if (Utils.clickObj("Mysterious ruins", "Enter"))
            Timer.waitCondition(() -> Objects.findNearest(30, "Altar").length > 0, 7000, 12000);


    }

    public boolean rodTele(String location) {
        RSItem[] rod = Equipment.find(ItemID.RING_OF_DUELING);
        if (rod.length > 0) {
            for (int i = 0; i < 3; i++) {
                General.println("[Teleport Manager]: Going to " + location);
                if (rod[0].click(location))
                    return Timer.waitCondition(() -> !atAltar(), 6000, 8000);
            }
        }

        return false;
    }

    public void leaveAltarPortal() {
        if (atAltar() && Utils.clickObj("Portal", "Use"))
            Timer.waitCondition(() -> !atAltar(), 9000, 15000);
    }

    public static boolean atAltar() {
        RSObject[] altar = Objects.findNearest(30, "Altar");
        return altar.length > 0;
    }


    @Override
    public Priority priority() {
        return Priority.MEDIUM;
    }

    @Override
    public boolean validate() {
        if (Vars.get().currentTask != null &&
                Vars.get().currentTask.equals(SkillTasks.RUNECRAFTING) &&
                !RcVars.get().usingOuraniaAlter) {
            if (RcVars.get().abyssCrafting)
                return false;
            if (!RcVars.get().usingLunarImbue && RunecraftBank.getLevel() < 14) {
                return !CraftRunes.atAltar()
                    //    && Inventory.find(Filters.Items.nameContains("talisman")).length > 0
                        && Inventory.find(ItemID.PURE_ESSENCE).length > 0
                        && Equipment.isEquipped(ItemID.EARTH_TIARA);
            } else if (!RcVars.get().usingLunarImbue && RunecraftBank.getLevel() >= 19) {
                return !CraftRunes.atAltar()
                        && Inventory.find(Filters.Items.nameContains("talisman")).length > 0
                        && Inventory.find(ItemID.PURE_ESSENCE).length > 0;
            }
            return !CraftRunes.atAltar()
                    && Inventory.find(ItemID.PURE_ESSENCE).length > 0;
        }
        return false;
    }

    @Override
    public void execute() {
        if (RunecraftBank.getLevel() < 14 || RcVars.get().mudRuneCrafting) {
            goToEarthAltar();

        } else if (RunecraftBank.getLevel() < 19) {
            goToFireAltar();

        } else if (!RcVars.get().zanarisCrafting) {
            goToFireAltar();

        } else if (RcVars.get().zanarisCrafting)
            goToCosmicAltar();
    }

    @Override
    public String toString() {
        return "Going to Altar";
    }

    @Override
    public String taskName() {
        return "Runecrafting";
    }
}
