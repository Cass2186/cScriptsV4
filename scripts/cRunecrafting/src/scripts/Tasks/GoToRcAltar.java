package scripts.Tasks;

import org.tribot.script.sdk.*;
import org.tribot.script.sdk.query.Query;
import org.tribot.script.sdk.types.*;
import org.tribot.script.sdk.util.TribotRandom;
import org.tribot.script.sdk.walking.LocalWalking;
import scripts.Data.Const;

import scripts.Data.Vars;
import scripts.ItemID;
import scripts.PathingUtil;

import scripts.RcApi.RcUtils;
import scripts.Timer;
import scripts.Utils;

import java.util.Optional;

public class GoToRcAltar implements Task {


    private boolean goToAltar(int tiaraId, Area altarArea) {
        if (Inventory.contains(ItemID.PURE_ESSENCE) && Equipment.contains(tiaraId) &&
                !CraftRunes.atAltar()) {
            Log.info("Going to altar");
            PathingUtil.walkToArea(altarArea, false);

            if (RcUtils.getRuins().map(ruins -> ruins.interact("Enter")).orElse(false)) {
                return RcUtils.waitForAltar();
            }
        }
        return RcUtils.waitForAltar();
    }


    public void goToFireAltar() {
        // Using Ring of elements
        if (!CraftRunes.atAltar() && useRingOfElements()) {
            Waiting.waitUntil(1500, 125, () -> RcUtils.getRuins().isPresent());

            if (Vars.get().usingLunarImbue)
                GameTab.MAGIC.open();

            Log.info("Entering Ruins");
            goToAltar(ItemID.FIRE_TIARA, Const.FIRE_ALTAR_AREA);
            return;
        }
        Log.info("Going to altar");
        // Not using Ring of elements and still at bank, tele to Duel Arena
        if (!Vars.get().useRingOfElements && Bank.isNearby() && rodTele("Duel Arena")) {
            Waiting.waitUntil(4500, 250, () -> !Bank.isNearby());
        }
        goToAltar(ItemID.FIRE_TIARA, Const.FIRE_ALTAR_AREA);
    }

    public void goToCosmicAltar() {
        Optional<GameObject> altar = RcUtils.getAltar();
        if (RcUtils.getAltar().isEmpty()) {
            Log.info("Going to Cosmic altar - Zanaris");
            PathingUtil.walkToArea(Const.ZANARIS_ALTAR, false);
        }
        if (Utils.clickObj("Mysterious ruins", "Enter"))
            RcUtils.waitForAltar();
    }

    public boolean rodTele(String location) {
        Optional<EquipmentItem> rod = Query.equipment().idEquals(ItemID.RING_OF_DUELING).findFirst();
        for (int i = 0; i < 3; i++) {
            Log.info("[Teleport Manager]: Going to " + location);
            if (rod.map(r -> r.click(location)).orElse(false))
                return Timer.waitCondition(() -> !CraftRunes.atAltar(), 6000, 8000);
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
        //TODO might be relevant to add a check for the bank here
        WorldTile myTile = MyPlayer.getPosition();

     /*   if (ring.map(r -> r.click("Rub")).orElse(false) && Waiting.waitUntil(3250,
                ChatScreen::isOpen) && ChatScreen.selectOption("Fire Altar")) {
            return Timer.waitCondition(() -> !MyPlayer.getPosition().equals(myTile), 4000, 5500);
        } else */
        if (ring.map(r -> r.click("Last Destination")).orElse(false)) {
            if (Timer.waitCondition(() -> !MyPlayer.getPosition().equals(myTile), 4000, 5500)) {
                Waiting.waitNormal(500, 76);
                return LocalWalking.walkTo(Const.FIRE_ALTAR_RING_TELE_TILE);

            }
        }
        return false;
    }

    private static boolean enterAbyss() {
        if (!Const.WHOLE_ABYSS.containsMyPlayer()) {
            Log.info("Going to abyss");
            if (PathingUtil.walkToTile(Const.WILDERNESS_TILE) &&
                    Query.npcs().actionContains("Teleport")
                            .findClosestByPathDistance()
                            .map(n -> n.interact("Teleport")).orElse(false)) {
                return Waiting.waitUntil(TribotRandom.uniform(9000, 11000), 600,
                        () -> Const.WHOLE_ABYSS.containsMyPlayer());
            }
        }
        Log.info("Already in Abyss");
        return Const.WHOLE_ABYSS.containsMyPlayer();
    }

    public static boolean enterInnerAbyss() {
        if (enterAbyss() && !Const.INNER_ABYSS.containsMyPlayer() &&
                Query.gameObjects().actionContains("Squeeze-through")
                        .findClosestByPathDistance()
                        .map(obj -> obj.interact("Squeeze-through"))
                        .orElse(false)) {
            return Waiting.waitUntil(TribotRandom.uniform(9000, 11000), 600,
                    Const.INNER_ABYSS::containsMyPlayer);
        }
        return Const.INNER_ABYSS.containsMyPlayer();
    }

    private boolean enterAbyssPortal(String portalName) {
        if (enterInnerAbyss() &&
                Query.gameObjects().actionContains("Exit-through")
                        .nameContains(portalName)
                        .findClosestByPathDistance().map(obj -> obj.interact("Exit-through"))
                        .orElse(false)) {
            return Waiting.waitUntil(TribotRandom.uniform(8000, 10000),
                    TribotRandom.normal(450, 65), () -> RcUtils.atAltar() &&
                            !Const.WHOLE_ABYSS.containsMyPlayer());
        }
        return RcUtils.atAltar() &&
                !Const.WHOLE_ABYSS.containsMyPlayer();
    }


    @Override
    public Priority priority() {
        return Priority.HIGH;
    }

    @Override
    public boolean validate() {
        if (RcUtils.getAltar().isPresent()) {
            Log.debug("Already at altar");
            return false;
        }
        //TODO add support for RC Abyss
        if (Vars.get().abyssCrafting)
            return Inventory.getAll().size() > 26;

        return Inventory.contains(ItemID.PURE_ESSENCE) &&
                Vars.get().currentRune.map(current ->
                                Equipment.contains(current.getTiaraId())
                                        && current.hasAdditionalTalisman())
                        .orElse(false);
    }

    @Override
    public void execute() {
        if (Skill.RUNECRAFT.getActualLevel() < 14 || Vars.get().mudRuneCrafting) {
            goToAltar(ItemID.EARTH_TIARA, Const.EARTH_ALTAR_AREA);

        } else if (Skill.RUNECRAFT.getActualLevel() < 19) {
            goToFireAltar();

        } else if (Vars.get().abyssCrafting) {

                enterAbyssPortal("Blood");

        } else if (Vars.get().zanarisCrafting)
            goToCosmicAltar();
        else
            goToFireAltar();
    }

    @Override
    public String toString() {
        return "Going to Altar";
    }

}
