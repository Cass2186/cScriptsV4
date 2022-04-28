package scripts.Tasks.BloodCrafting;

import org.tribot.api2007.types.RSItem;
import org.tribot.script.sdk.*;
import org.tribot.script.sdk.query.Query;
import org.tribot.script.sdk.types.WorldTile;
import scripts.Data.Const;
import scripts.Data.Vars;
import scripts.ItemID;
import scripts.PathingUtil;
import scripts.Tasks.Priority;
import scripts.Tasks.Task;
import scripts.Timer;
import scripts.Utils;

public class GoToBloodAltar implements Task {

    String message = "Going to blood altar";

    public static boolean hasChiselAndPickaxe() {
        boolean pick = Query.equipment().nameContains("pickaxe").isAny();
        return Inventory.contains(ItemID.CHISEL) && pick;
    }

    public boolean hasDenseEssenceShardsAndDarkEss() {
        return Inventory.getAll().size() > 24 &&
                Inventory.getCount(ItemID.DARK_ESSENCE_FRAGMENTS) > 0 &&
                Inventory.getCount(ItemID.DARK_ESSENCE_BLOCK) > 10;
    }

    public void goToBloodAltar() {
        message = "Going to blood altar";
        if (!Options.isRunEnabled())
            Options.setRunEnabled(true);
        PathingUtil.walkToTile(new WorldTile(1719, 3828, 0));
        int dist = Utils.random(8, 10);
        Waiting.waitUntil(6000, 350, () ->
                Query.gameObjects()
                        .idEquals(Const.BLOOD_ALTAR_ID).maxPathDistance(dist).isAny());
    }

    public void bindRunes() {
        message = "Binding runes";
        if (Inventory.contains(ItemID.DARK_ESSENCE_FRAGMENTS) &&
                Utils.clickObj(Const.BLOOD_ALTAR_ID, "Bind"))
            Timer.waitCondition(() -> MyPlayer.getAnimation() == Const.BINDING_ANIMATION_ID, 7000, 9000);

        if (Inventory.contains(ItemID.DARK_ESSENCE_BLOCK)) {
            message = "Chiselling fragments";
            GoToDarkAltar.chipDarkEss();
            Timer.waitCondition(() -> !Inventory.contains(ItemID.DARK_ESSENCE_BLOCK), 49000, 53000);
        }
        if (Inventory.getCount(ItemID.DARK_ESSENCE_FRAGMENTS) > 0) {
            if (GameState.isAnyItemSelected())
                Utils.unselectItem();

            if (Utils.clickObj("Blood Altar", "Bind")) {
                Timer.waitCondition(() -> MyPlayer.getAnimation() == Const.BINDING_ANIMATION_ID, 7000, 9000);
                Vars.get().goToBloodAltar = false;
            }
        }

    }


    @Override
    public String toString() {
        return message;
    }

    @Override
    public Priority priority() {
        return Priority.MEDIUM;
    }

    @Override
    public boolean validate() {
        return (hasChiselAndPickaxe() && hasDenseEssenceShardsAndDarkEss())
                || Vars.get().goToBloodAltar;
    }

    @Override
    public void execute() {
        goToBloodAltar();
        bindRunes();
    }
}
