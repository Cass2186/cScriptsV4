package scripts.Tasks;

import org.tribot.script.sdk.*;
import org.tribot.api.General;
import org.tribot.api2007.Equipment;
import org.tribot.api2007.Objects;
import org.tribot.api2007.ext.Filters;
import org.tribot.api2007.types.RSItem;
import org.tribot.api2007.types.RSObject;
import org.tribot.script.sdk.query.GameObjectQuery;
import org.tribot.script.sdk.query.Query;
import org.tribot.script.sdk.types.GameObject;
import org.tribot.script.sdk.types.InventoryItem;
import org.tribot.script.sdk.types.WorldTile;
import org.tribot.script.sdk.walking.LocalWalking;
import scripts.Data.Const;

import scripts.Data.Vars;
import scripts.ItemID;
import scripts.PathingUtil;

import scripts.Timer;
import scripts.Utils;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

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
        if (!CraftRunes.atAltar() && useRingOfElements()) {
            Waiting.waitUntil(1500, ()->Query.gameObjects()
                    .nameContains("Mysterious ruins")
                    .actionContains("Enter")
                    .findBestInteractable().isPresent());

            if (Vars.get().usingLunarImbue)
                GameTab.MAGIC.open();

            Log.debug("Entering Ruins");
            Optional<GameObject> ruins = Query.gameObjects()
                    .nameContains("Mysterious ruins")
                            .actionContains("Enter")
                                    .findBestInteractable();
            if (ruins.map(r->r.interact("Enter")).orElse(false)){
                Log.debug("Interacting");
                Timer.waitCondition(CraftRunes::atAltar, 7000, 12000);
                Waiting.waitUniform(400,700);
                return;
            }
            /*LocalWalking.walkTo(Const.FIRE_ALTAR_RING_TELE_TILE);

            if (Utils.clickObj("Mysterious ruins", "Enter")) {
                Timer.waitCondition(CraftRunes::atAltar, 7000, 12000);
                Waiting.waitUniform(400,700);
                return;
            }*/
            return;
        }
        RSObject[] obj = Objects.findNearest(10,
                Filters.Objects.nameContains("Bank chest"));

        if (!Vars.get().useRingOfElements && obj.length > 0 && rodTele("Duel Arena")) {
            Timer.waitCondition(() -> Objects.findNearest(10,
                    Filters.Objects.nameContains("Bank chest")).length == 0, 3000, 5000);
        }

        if (!CraftRunes.atAltar()) {
            Log.debug("Going to fire altar");
            PathingUtil.walkToTile(Const.FIRE_ALTAR_TILE_BEFORE_RUINS, 6, false);

            if (Vars.get().usingLunarImbue)
                GameTab.MAGIC.open();

            Log.debug("Entering Ruins");
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

    public boolean useRingOfElements() {
        Optional<InventoryItem> ring = Query.inventory()
                .idEquals(ItemID.CHARGED_RING_OF_ELEMENTS)
                .findClosestToMouse();

        WorldTile myTile = MyPlayer.getPosition();

     /*   if (ring.map(r -> r.click("Rub")).orElse(false) && Waiting.waitUntil(3250,
                ChatScreen::isOpen) && ChatScreen.selectOption("Fire Altar")) {
            return Timer.waitCondition(() -> !MyPlayer.getPosition().equals(myTile), 4000, 5500);
        } else */if (ring.map(r -> r.click("Last Destination")).orElse(false)) {
             if(Timer.waitCondition(() -> !MyPlayer.getPosition().equals(myTile), 4000, 5500)){
                 Waiting.waitNormal(500,76);
                return LocalWalking.walkTo(Const.FIRE_ALTAR_RING_TELE_TILE);

             }
        }
        return false;
    }


    @Override
    public Priority priority() {
        return Priority.MEDIUM;
    }

    @Override
    public boolean validate() {
        Log.debug("At altar? " + CraftRunes.atAltar());
        //TODO add support for RC Abyss
        if (Vars.get().abyssCrafting)
            return false;
        if (!Vars.get().usingLunarImbue && RunecraftBank.getLevel() < 14) {
            return !CraftRunes.atAltar()
                    //    && Inventory.find(Filters.Items.nameContains("talisman")).length > 0
                    && Inventory.contains(ItemID.PURE_ESSENCE)
                    && Equipment.isEquipped(ItemID.EARTH_TIARA);
        } else if (!Vars.get().usingLunarImbue && RunecraftBank.getLevel() >= 19) {
            Log.debug("Go to RC altar ");
            return !CraftRunes.atAltar()
                    && Query.equipment().nameContains("tiara").isAny()
                  //  && Query.equipment().nameContains("talisman").isAny()
                    && Inventory.contains(ItemID.PURE_ESSENCE);
        }
        return !CraftRunes.atAltar()
                && Inventory.contains(ItemID.PURE_ESSENCE)
                && Query.equipment().nameContains("tiara").isAny();

    }

    @Override
    public void execute() {
        if (Skill.RUNECRAFT.getActualLevel() < 14 || Vars.get().mudRuneCrafting) {
            goToEarthAltar();

        } else if (Skill.RUNECRAFT.getActualLevel() < 19) {
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
