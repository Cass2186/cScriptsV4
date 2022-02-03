package scripts.Tasks;

import org.tribot.api2007.Player;
import org.tribot.api2007.Skills;
import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSTile;
import org.tribot.script.sdk.Log;
import org.tribot.script.sdk.Prayer;
import org.tribot.script.sdk.Waiting;
import scripts.Data.Areas;
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


    public void navigateWaterBirthDungeon() {
        if (Areas.wbDungeon2.check()) {
            Log.log("[Debug]: In WB 2");
            if (PathingUtil.localNavigation(new RSTile(1918, 4367, 0)))
                PathingUtil.movementIdle();
            //if peeking
            if (Utils.clickObj("Root", "Step-over"))
                Timer.waitCondition(() -> Areas.wbDungeon3Pt4.check(), 4500, 6000);
            // go to kings
        } else if (Areas.wbDungeon3Pt4.check()) {
            Log.log("[Debug]: In WB 3 pt 4");
            // go to wbDungeon2
            if (PathingUtil.localNavigation(new RSTile(1889, 4406, 1)))
                PathingUtil.movementIdle();
            if (Utils.clickObj("Ladder", "Climb-down"))
                Timer.waitCondition(() -> Areas.wbDungeon3Pt4.check(), 4500, 6000);
        } else if (Areas.wbDungeon4Pt5.check()) {
            Log.log("[Debug]: In WB 4 pt 5");

            if (PathingUtil.localNavigation( new RSTile(1865, 4386, 2)))
                PathingUtil.movementIdle();
            if (Utils.clickObj("Ladder", "Climb-down"))
                Timer.waitCondition(() -> Areas.wbDungeon3Pt4.check(), 4500, 6000);

        }else if (Areas.wbDungeon4Pt4.check()) {
            Log.log("[Debug]: In WB 4 pt 4");

            if (PathingUtil.localNavigation(new RSTile(1812, 4394, 2)))
                PathingUtil.movementIdle();
            if (Utils.clickObj("Ladder", "Climb-down"))
                Timer.waitCondition(() -> Areas.wbDungeon3Pt4.check(), 4500, 6000);

            // go to wbDungeon3pt4
        } else if (Areas.wbDungeon3Pt3.check()) {
            Log.log("[Debug]: In WB 3 pt 3");
            if (PathingUtil.localNavigation(new RSTile(1863, 4369, 1)))
                PathingUtil.movementIdle();

            if (Utils.clickObj("Ladder", "Climb-up"))
                Timer.waitCondition(() -> Areas.wbDungeon4Pt4.check(), 4500, 6000);

        } else if (Areas.wbDungeon4Pt2.check()) {
            Log.log("[Debug]: In WB 4 pt 2");

            if (PathingUtil.localNavigation(new RSTile(1824, 4362, 2)))
                PathingUtil.movementIdle();

            if (Utils.clickObj("Ladder", "Climb-down"))
                Timer.waitCondition(() -> Areas.wbDungeon3Pt3.check(), 4500, 6000);

        } else if (Areas.wbDungeon3Pt2.check()) {
            Log.log("[Debug]: In WB 3 pt 2");
            if (PathingUtil.localNavigation(new RSTile(1803, 4369, 1)))
                PathingUtil.movementIdle();
            if (Utils.clickObj("Ladder", "Climb-up"))
                Timer.waitCondition(() -> Areas.wbDungeon4Pt2.check(), 4500, 6000);


        } else if (Areas.wbDungeon4Pt1.check()) {
            Log.log("[Debug]: In WB 4 pt 1");
            if (PathingUtil.localNavigation(new RSTile(1798, 4381, 2)))
                PathingUtil.movementIdle();
            if (Utils.clickObj("Ladder", "Climb-down"))
                Timer.waitCondition(() -> Areas.wbDungeon3Pt2.check(), 4500, 6000);

        } else if (Areas.wbDungeon3Pt1.check()) {
            Log.log("[Debug]: In WB 3 pt 1");
            if (PathingUtil.localNavigation( new RSTile(1800, 4389, 1)))
                PathingUtil.movementIdle();
            if (Utils.clickObj("Ladder", "Climb-down"))
                Timer.waitCondition(() -> Areas.wbDungeon4Pt1.check(), 4500, 6000);

        } else if (Areas.wbDungeon4Pt3.check()) {
            Log.log("[Debug]: In WB 4 pt 3");

            if (PathingUtil.localNavigation(new RSTile(1811, 4393, 2)))
                PathingUtil.movementIdle();
            if (Utils.clickObj("Ladder", "Climb-down"))
                Timer.waitCondition(() -> Areas.wbDungeon3Pt1.check(), 4500, 6000);

        } else if (Areas.wbDungeon5Pt2.check()) {
            Log.log("[Debug]: In WB 5 pt 2");
            if (PathingUtil.localNavigation(new RSTile(1834, 4391, 3)))
                PathingUtil.movementIdle();

            if (Utils.clickObj("Ladder", "Climb-down"))
                Timer.waitCondition(() -> Areas.wbDungeon4Pt3.check(), 4500, 6000);

        } else if (Areas.wbDungeon4Pt5.check()) {
            Log.log("[Debug]: In WB 4 pt 5");
            if (PathingUtil.localNavigation(new RSTile(1822, 4404, 2)))
                PathingUtil.movementIdle();
            if (Utils.clickObj("Ladder", "Climb-down"))
                Timer.waitCondition(() -> Areas.wbDungeon5Pt2.check(), 4500, 6000);

        } else if (Areas.wbDungeon5Pt1.check()) {
            Log.log("[Debug]: In WB 5 pt 1");
            if (PathingUtil.localNavigation(new RSTile(1807, 4404, 3)))
                PathingUtil.movementIdle();
            if (Utils.clickObj("Ladder", "Climb-up"))
                Timer.waitCondition(() -> Areas.wbDungeon4Pt5.check(), 4500, 6000);

        } else if (Areas.wbDungeon1Pt2.check() && Areas.wbDungeon1LadderArea.check()) {
            Log.log("[Debug]: In WB 1 ladder area");
            if (Utils.clickObj("Ladder", "Climb-down"))
                Timer.waitCondition(() -> Areas.wbDungeon5Pt1.check(), 4500, 6000);

        } else if (Areas.wbDungeon1Pt2.check() && !Areas.wbDungeon1LadderArea.check()) {
            Log.log("[Debug]: In WB 1 pt 2");

            if (PathingUtil.localNavigation(new RSTile(2545, 10148, 0)))
                PathingUtil.movementIdle();

        } else {
            Log.log("Going to waterbirth island");
            PathingUtil.walkToTile(PET_ROCK_DROP_TILE);
            if (PET_ROCK_DROP_TILE.isClickable() && PET_ROCK_DROP_TILE.click("Walk-here")){
                PathingUtil.movementIdle();
            }
            if (PET_ROCK_DROP_TILE.equals(Player.getPosition())) {
                // drop rock
            }


            //first part of dungeon is dax mapped
        }
    }
    RSTile PET_ROCK_DROP_TILE = new RSTile(2490, 10164, 0);
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
        return  !Areas.UNDEAD_DRUID_AREA.contains(Player.getPosition());//!Areas.dkArea.check();//!Vars.get().safeTile.equals(Player.getPosition());

    }

    @Override
    public void execute() {
        Log.log("[Debug]: Move to area");
        PathingUtil.walkToTile(new RSTile(1807, 9925, 0));
        org.tribot.script.sdk.Prayer.enableAll(org.tribot.script.sdk.Prayer.PROTECT_FROM_MAGIC,
                Prayer.HAWK_EYE);
        PathingUtil.walkToArea(Areas.UNDEAD_DRUID_AREA);
        //goToRuneDragons();
        //navigateWaterBirthDungeon();
       /* if (Vars.get().safeTile.isClickable()) {
            Vars.get().safeTile.click("Walk here");
            if (Timer.waitCondition(() -> Player.isMoving(), 1500, 2250))
                Timer.waitCondition(() -> Vars.get().safeTile.equals(Player.getPosition()), 2000, 2250);
        } else
            PathingUtil.walkToTile(Vars.get().safeTile);*/

    }
}
