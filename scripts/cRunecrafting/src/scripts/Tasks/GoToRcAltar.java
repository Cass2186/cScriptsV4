package scripts.Tasks;

import org.tribot.script.sdk.GameTab;
import org.tribot.script.sdk.Inventory;
import org.tribot.api.General;
import org.tribot.api2007.Equipment;
import org.tribot.api2007.Objects;
import org.tribot.api2007.ext.Filters;
import org.tribot.api2007.types.RSItem;
import org.tribot.api2007.types.RSObject;
import org.tribot.script.sdk.Skill;
import org.tribot.script.sdk.query.GameObjectQuery;
import org.tribot.script.sdk.query.Query;
import scripts.Data.Const;

import scripts.Data.Vars;
import scripts.ItemID;
import scripts.PathingUtil;

import scripts.Timer;
import scripts.Utils;

public class GoToRcAltar implements Task {
    public void goToEarthAltar() {
        if (((Inventory.contains(ItemID.PURE_ESSENCE)
                || Inventory.contains("Tiara"))
                && Equipment.isEquipped(ItemID.EARTH_TIARA)) && !CraftRunes.atAltar()) {

            PathingUtil.walkToArea(Const.EARTH_ALTAR_AREA, false);

            if (Utils.clickObj("Mysterious ruins", "Enter")) {
                Timer.waitCondition(() -> Objects.findNearest(30, "Altar").length > 0, 7000, 12000);
            }
        }
    }


    public void goToFireAltar() {
        RSObject[] obj = Objects.findNearest(10,
                Filters.Objects.nameContains("Bank chest"));

        if (obj.length > 0 && rodTele("Duel Arena")) {
            Timer.waitCondition(() -> Objects.findNearest(10,
                    Filters.Objects.nameContains("Bank chest")).length == 0, 3000, 5000);
        }

        RSObject[] altar = Objects.findNearest(30, Filters.Objects.nameContains("Altar").and(Filters.Objects.actionsNotContains("Pray")));
        if (altar.length == 0) {
            General.println("[Debug] Going to fire altar");
            PathingUtil.walkToTile(Const.FIRE_ALTAR_TILE_BEFORE_RUINS, 8, false);

            if (Vars.get().usingLunarImbue)
                GameTab.MAGIC.open();

            General.println("[Debug] Entering Ruins");
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
            PathingUtil.walkToArea(Const.ZANARIS_ALTAR, false);

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
                    return Timer.waitCondition(() -> !CraftRunes.atAltar(), 6000, 8000);
            }
        }

        return false;
    }

    public void leaveAltarPortal() {
        if (CraftRunes.atAltar() && Utils.clickObj("Portal", "Use"))
            Timer.waitCondition(() -> !CraftRunes.atAltar(), 9000, 15000);
    }



    @Override
    public Priority priority() {
        return Priority.MEDIUM;
    }

    @Override
    public boolean validate() {
        //TODO add support for RC Abyss
        if (Vars.get().abyssCrafting)
            return false;
        if (!Vars.get().usingLunarImbue && RunecraftBank.getLevel() < 14) {
            return !CraftRunes.atAltar()
                    //    && Inventory.find(Filters.Items.nameContains("talisman")).length > 0
                    && Inventory.contains(ItemID.PURE_ESSENCE)
                    && Equipment.isEquipped(ItemID.EARTH_TIARA);
        } else if (!Vars.get().usingLunarImbue && RunecraftBank.getLevel() >= 19) {
            return !CraftRunes.atAltar()
                    && Query.inventory().nameContains("tiara").isAny()
                    && Inventory.contains(ItemID.PURE_ESSENCE);
        }
        return !CraftRunes.atAltar()
                && Inventory.contains(ItemID.PURE_ESSENCE);

    }

    @Override
    public void execute() {
        if (Skill.RUNECRAFT.getActualLevel() < 14 || Vars.get().mudRuneCrafting) {
            goToEarthAltar();

        } else if (Skill.RUNECRAFT.getActualLevel()< 19) {
            goToFireAltar();

        } else if (!Vars.get().zanarisCrafting) {
            goToFireAltar();

        } else if (Vars.get().zanarisCrafting)
            goToCosmicAltar();
    }

    @Override
    public String toString() {
        return "Going to Altar";
    }

}
