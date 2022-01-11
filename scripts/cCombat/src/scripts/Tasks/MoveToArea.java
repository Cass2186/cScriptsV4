package scripts.Tasks;

import org.tribot.api2007.Player;
import org.tribot.api2007.Skills;
import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSTile;
import org.tribot.script.sdk.Log;
import org.tribot.script.sdk.Prayer;
import org.tribot.script.sdk.Waiting;
import scripts.Data.Vars;
import scripts.PathingUtil;
import scripts.Timer;
import scripts.Utils;

public class MoveToArea implements Task {

    RSArea DUNGEON_AREA = new RSArea(new RSTile(3541, 10484, 0), new RSTile(3557, 10442, 0));
    RSTile BEFORE_DOOR_TILE = new RSTile(3549, 10482, 0);

    RSArea WHOLE_DRAGON_AREA = new RSArea(new RSTile(1598, 5059, 0), new RSTile(1538, 5116, 0));
    public static RSArea RUNE_DRAGON_AREA = new RSArea(new RSTile(1596, 5063, 0), new RSTile(1576, 5086, 0));

    String s = "Lithkren";

    public void goToRuneDragons() {
        if (!DUNGEON_AREA.contains(Player.getPosition()) && !WHOLE_DRAGON_AREA.contains(Player.getPosition())) {
            //use necklace
        }
        if (DUNGEON_AREA.contains(Player.getPosition())) {
            if (PathingUtil.localNavigation(new RSTile(3549, 10466, 0))) {
                PathingUtil.movementIdle();
                if (Utils.clickObject(32113, "Climb", false)) {
                    PathingUtil.movementIdle();
                }
            }
            if (PathingUtil.localNavigation(BEFORE_DOOR_TILE))
                Waiting.waitNormal(3000, 500);
            if (Utils.clickObject("Broken Grandiose Doors", "Enter", false)) {
                Timer.waitCondition(() -> WHOLE_DRAGON_AREA.contains(Player.getPosition()), 5500, 7000);
                Waiting.waitNormal(3000, 500);
            }
        }
        if (WHOLE_DRAGON_AREA.contains(Player.getPosition())) {
            if (!RUNE_DRAGON_AREA.contains(Player.getPosition())) {

                //doesn't need local nav, dax handles fine
                if (activateRuneDragonPrayer() &&
                        PathingUtil.walkToArea(RUNE_DRAGON_AREA, false))
                    Timer.waitCondition(() -> RUNE_DRAGON_AREA.contains(Player.getPosition()), 3000, 4000);

            }
        }

    }

    public static boolean activateRuneDragonPrayer() {
        return Skills.getActualLevel(Skills.SKILLS.PRAYER) >= 70 ?
                Prayer.enableAll(Prayer.PROTECT_FROM_MAGIC, Prayer.PIETY) :
                Prayer.enableAll(Prayer.PROTECT_FROM_MAGIC, Prayer.CHIVALRY);
    }

    @Override
    public String toString() {
        return "Move to area";
    }


    @Override
    public Priority priority() {
        return Priority.HIGH;
    }

    @Override
    public boolean validate() {
        return !Vars.get().safeTile.equals(Player.getPosition());

    }

    @Override
    public void execute() {
        Log.log("Move to area");
        //goToRuneDragons();
        if (Vars.get().safeTile.isClickable()) {
            Vars.get().safeTile.click("Walk here");
            if (Timer.waitCondition(() -> Player.isMoving(), 1500, 2250))
                Timer.waitCondition(() -> Vars.get().safeTile.equals(Player.getPosition()), 2000, 2250);
        } else
            PathingUtil.walkToTile(Vars.get().safeTile);

    }
}
