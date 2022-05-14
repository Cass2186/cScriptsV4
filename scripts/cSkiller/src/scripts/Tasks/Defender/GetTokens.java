package scripts.Tasks.Defender;

import org.tribot.api.General;
import org.tribot.script.sdk.Combat;
import org.tribot.script.sdk.Inventory;
import org.tribot.script.sdk.Log;
import org.tribot.script.sdk.MyPlayer;
import org.tribot.script.sdk.query.Query;
import scripts.*;
import scripts.API.Priority;
import scripts.API.Task;
import scripts.Data.Vars;
import scripts.Tasks.Defender.Data.DefenderConst;
import scripts.Tasks.Defender.Data.DefenderVars;

import java.util.List;

public class GetTokens implements Task {


    public void animate(int... gearIds) {
        if (!MyPlayer.isHealthBarVisible() && Inventory.contains(ItemID.BLACK_PLATEBODY) &&
                Inventory.contains(ItemID.BLACK_PLATELEGS) &&
                Inventory.contains(ItemID.BLACK_FULL_HELM)) {
            Log.info("Animating");

            if (!DefenderConst.LEFT_ANIMATOR_TILE_AREA.contains(MyPlayer.getTile()))
                PathingUtil.walkToTile(DefenderConst.LEFT_ANIMATOR_TILE);

            if (Utils.clickObj(DefenderConst.LEFT_ANIMATOR_ID, "Animate"))
                Timer.waitCondition(() -> MyPlayer.isHealthBarVisible(), 10000, 15000);
        }
        if (MyPlayer.getCurrentHealthPercent() < DefenderVars.get().eatAtPercent) {
            EatUtil.eatFood();
            DefenderVars.get().eatAtPercent = Utils.random(35, 65);
        }
        if (MyPlayer.isHealthBarVisible()) {
            Vars.get().currentTime = System.currentTimeMillis();
            if (CombatUtil.waitUntilOutOfCombat(General.random(40, 60)))
                Utils.abc2ReactionSleep(Vars.get().currentTime);
        } if (!MyPlayer.isHealthBarVisible() &&
                Query.groundItems().idEquals(ItemID.WARRIOR_GUILD_TOKEN).isAny()
                || Query.groundItems().idEquals(gearIds).isAny()) {
            Log.info("Looting tokens");;

            if (Utils.clickGroundItem(ItemID.BLACK_PLATEBODY))
                Timer.waitCondition(() -> !Query.groundItems().idEquals(ItemID.BLACK_PLATELEGS).isAny(), 3000, 5000);
            if (Utils.clickGroundItem(ItemID.BLACK_PLATELEGS))
                Timer.waitCondition(() -> !Query.groundItems().idEquals(ItemID.BLACK_PLATELEGS).isAny(), 3000, 5000);
            if (Utils.clickGroundItem(ItemID.BLACK_FULL_HELM))
                Timer.waitCondition(() -> !Query.groundItems().idEquals(ItemID.BLACK_FULL_HELM).isAny(), 2000, 5000);
            if (Inventory.isFull())
                EatUtil.eatFood();
            Utils.clickGroundItem(ItemID.WARRIOR_GUILD_TOKEN);
            Utils.shortSleep();
        }
    }


    @Override
    public Priority priority() {
        return null;
    }

    @Override
    public boolean validate() {
        return false;
    }

    @Override
    public void execute() {
        animate();
    }

    @Override
    public String toString() {
        return "Getting Tokens";
    }

    @Override
    public String taskName() {
        return "Defender";
    }
}
