package scripts.QuestPackages.LegendsQuest;

import org.tribot.api.General;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Player;
import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSObject;
import org.tribot.api2007.types.RSTile;
import scripts.EntitySelector.Entities;
import scripts.EntitySelector.finders.prefabs.ObjectEntity;
import scripts.*;

public class LegendsUtils {

    public static int LEGENDS_GAME_SETTING = 139;
    public static String SAN = "San Tojalon";
    public static String IRVIG = "Irvig Senay";
    public static String RANDALPH = "";


    static RSTile FOREST_BUSH_TILE_PRE = new RSTile(2797, 2943, 0);
    static RSTile FOREST_BUSH_TILE_ONE = new RSTile(2797, 2942, 0);
    static RSTile FOREST_PLAYER_TILE_ONE = new RSTile(2797, 2941, 0);
    static RSTile FOREST_BUSH_TILE_TWO = new RSTile(2798, 2941, 0);
    static RSTile FOREST_PLAYER_TILE_TWO = new RSTile(2799, 2941, 0);
    static RSTile FOREST_BUSH_TILE_THREE = new RSTile(2799, 2940, 0);
    static RSTile FOREST_PLAYER_TILE_THREE = new RSTile(2799, 2939, 0);
    static RSTile FOREST_BUSH_TILE_FOUR = new RSTile(2799, 2938, 0);
    static RSTile FOREST_BUSH_TILE_FINISHED = new RSTile(2795, 2939, 0);
    static RSArea WHOLE_FOREST_AREA = new RSArea(
            new RSTile[]{
                    new RSTile(2759, 2937, 0),
                    new RSTile(2770, 2941, 0),
                    new RSTile(2790, 2941, 0),
                    new RSTile(2791, 2939, 0),
                    new RSTile(2802, 2939, 0),
                    new RSTile(2803, 2941, 0),
                    new RSTile(2814, 2941, 0),
                    new RSTile(2816, 2939, 0),
                    new RSTile(2816, 2936, 0),
                    new RSTile(2825, 2936, 0),
                    new RSTile(2829, 2938, 0),
                    new RSTile(2859, 2938, 0),
                    new RSTile(2859, 2935, 0),
                    new RSTile(2873, 2935, 0),
                    new RSTile(2873, 2941, 0),
                    new RSTile(2887, 2942, 0),
                    new RSTile(2887, 2890, 0),
                    new RSTile(2771, 2890, 0)
            }
    );

    public static boolean enterForest() {
        //check if in forest and has machete
        if (!WHOLE_FOREST_AREA.contains(Player.getPosition())) {
            cQuesterV2.status = "Going to Forest";
            General.println("[Debug]: Going to Forest");
            // prevents teleporting when we are navigating bushes if it loops
            if (Player.getPosition().distanceTo(FOREST_BUSH_TILE_PRE) > 5)
                PathingUtil.walkToTile(FOREST_BUSH_TILE_PRE, 1, false);
            cutBush(FOREST_BUSH_TILE_PRE, FOREST_BUSH_TILE_ONE);
            cutBush(FOREST_PLAYER_TILE_ONE, FOREST_BUSH_TILE_TWO);
            cutBush(FOREST_PLAYER_TILE_TWO, FOREST_BUSH_TILE_THREE);
            cutBush(FOREST_PLAYER_TILE_THREE, FOREST_BUSH_TILE_FOUR);
        }
        Inventory.drop(ItemId.LOG_IDS[0]);
        return WHOLE_FOREST_AREA.contains(Player.getPosition());
    }


    private static boolean cutBush(RSTile playerTile, RSTile bushTile) {
        if (!playerTile.equals(Player.getPosition()) && playerTile.distanceTo(Player.getPosition()) <3) {
            PathingUtil.clickScreenWalk(playerTile);
            General.sleep(800, 1250);
        }
        if (playerTile.equals(Player.getPosition())) {
            General.println("[Debug]: Cutting bush");
            RSObject bush = Entities.find(ObjectEntity::new)
                    .tileEquals(bushTile)
                    .actionsContains("Chop-down")
                    .sortByDistance()
                    .getFirstResult();

            if (bush != null && Utils.clickObject(bush, "Chop-down")) {
                return Timer.slowWaitCondition(() -> !Player.getPosition().equals(playerTile) && !Player.isMoving(), 7000, 10500);
            }
        }

        return false;
    }
}
