package scripts.Tasks.BloodCrafting;

import org.tribot.api.input.Mouse;
import org.tribot.script.sdk.*;
import org.tribot.script.sdk.types.WorldTile;
import org.tribot.script.sdk.walking.GlobalWalking;
import org.tribot.script.sdk.walking.LocalWalking;
import scripts.Data.Const;
import scripts.ItemID;
import scripts.PathingUtil;
import scripts.Tasks.Priority;
import scripts.Tasks.Task;
import scripts.Timer;
import scripts.Utils;

public class GetDenceEssence implements Task {

    public boolean isChipping() {
        return MyPlayer.getAnimation() == 624 || MyPlayer.getAnimation() == 7201;
    }

    public void moveToMine() {
        if (!Const.WHOLE_MINE_AREA.contains(MyPlayer.getTile())) {
            if (Skill.AGILITY.getActualLevel() < 73 && !isChipping()) {
                PathingUtil.walkToArea(Const.ARCEUUS_MINE, false);
                Log.info("Going to dense ess");
                if (Const.ARCEUUS_MINE.getRandomTile().distanceTo(MyPlayer.getTile()) < 35) {
                    PathingUtil.localNav(Const.ARCEUUS_MINE.getCenter());
                }
            } else if (!isChipping()) {
                if (PathingUtil.walkToTile(Const.ARCEUUS_MINE.getCenter()))
                    Waiting.waitUntil(4500, 300,
                            () -> Const.ARCEUUS_MINE.getCenter().distance() < 5);
            }
        }
    }

    private void moveFromDarkAltar() {
        if (Const.DARK_ALTAR_AREA.contains(MyPlayer.getTile())) {
            var path = LocalWalking.Map.builder()
                    .travelThroughDoors(true)
                    .build().getPath(new WorldTile(1764, 3854, 0));

            Log.info("Using global walking");
            GlobalWalking.walkTo(new WorldTile(1764, 3854, 0));
        }
    }

    public void chipEssence() {
        moveToMine();
        if (!isChipping() && Utils.clickObj("Dense runestone", "Chip")) {
            Timer.waitCondition(this::isChipping, 6000, 8000);
        }
        if (isChipping()) {
            if (shouldAfkIdle(Utils.random(35, 45))) {
                Log.info("Idling while Chipping essence (afk)");
                if(Mouse.isInBounds())
                    Mouse.leaveGame();
                Timer.abc2SkillingWaitCondition(() -> !isChipping(), 55000, 75000);
            } else {
                Log.info("Idling while Chipping essence");
                Timer.waitCondition(() -> !isChipping() || ChatScreen.isOpen(), 55000, 75000);
                Utils.idleNormalAction();
            }
        }
    }

    private boolean shouldAfkIdle(int chance) {
        int i = Utils.random(0, 100);
        return i < chance;
    }

    @Override
    public String toString() {
        return "Mining Dense Essence";
    }

    @Override
    public Priority priority() {
        return Priority.HIGH;
    }

    @Override
    public boolean validate() {
        return GoToBloodAltar.hasChiselAndPickaxe() && !Inventory.isFull()
                && Inventory.getCount(ItemID.DARK_ESSENCE_BLOCK) < 20;
    }

    @Override
    public void execute() {
        moveFromDarkAltar();
        chipEssence();
    }
}
