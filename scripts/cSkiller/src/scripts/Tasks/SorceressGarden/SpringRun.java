package scripts.Tasks.SorceressGarden;

import org.tribot.api2007.Game;
import org.tribot.api2007.Player;
import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSTile;
import org.tribot.script.sdk.MyPlayer;
import org.tribot.script.sdk.Waiting;
import org.tribot.script.sdk.input.Mouse;
import org.tribot.script.sdk.query.Query;
import org.tribot.script.sdk.types.Area;
import org.tribot.script.sdk.types.GameObject;
import org.tribot.script.sdk.types.WorldTile;
import scripts.API.Priority;
import scripts.API.Task;
import scripts.Data.SkillTasks;
import scripts.Data.Vars;
import scripts.Timer;
import scripts.Utils;


import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class SpringRun implements Task {

    RSArea WHOLE_SPRING_GARDEN = new RSArea(new RSTile(2922, 5477, 0), new RSTile(2935, 5458, 0));
    RSArea CENTRE_OF_GARDEN = new RSArea(new RSTile(2903, 5480, 0), new RSTile(2920, 5463, 0));
    ElementalCollisionDetector collisionDetector = new ElementalCollisionDetector();


    public void moveToTile() {

    }

    double fruitXp = 337.5;

    WorldTile intialTile = new WorldTile(2921, 5473, 0);
    WorldTile tile1 = new WorldTile(2923, 5471, 0);
    WorldTile tile2 = new WorldTile(2923, 5465, 0); //index 0 for RUN_TILES
    WorldTile tile3 = new WorldTile(2923, 5459, 0);
    WorldTile tile4 = new WorldTile(2926, 5468, 0);
    WorldTile tile5 = new WorldTile(2928, 5470, 0);
    WorldTile tile6 = new WorldTile(2930, 5470, 0);

    List<WorldTile> tileList = Arrays.asList(tile1, tile2, tile3, tile4, tile5);


    // these tiles must be open (ie no npc) to trigger movement to the named tile
    // i.e. if this area has no npc, walk to tile 2
    Area tile2Trigger = Area.fromRectangle(new WorldTile(2922, 5473, 0), new WorldTile(2922, 5465, 0));
    Area tile3Trigger = Area.fromRectangle(new WorldTile(2922, 5465, 0), new WorldTile(2922, 5458, 0));
    // this area is more north so it confirms the 2 npcs are actually close to the player, but by the time
    Area tile4Trigger = Area.fromPolygon(
            new WorldTile[]{
                    new WorldTile(2926, 5464, 0),
                    new WorldTile(2926, 5458, 0),
                    new WorldTile(2930, 5458, 0),
                    new WorldTile(2930, 5464, 0)
            }
    );
    Area tile5Trigger = Area.fromPolygon(
            new WorldTile[]{
                    new WorldTile(2925, 5468, 0),
                    new WorldTile(2925, 5471, 0),
                    new WorldTile(2929, 5471, 0),
                    new WorldTile(2929, 5470, 0),
                    new WorldTile(2926, 5470, 0),
                    new WorldTile(2926, 5468, 0)
            }
    );

    Area customTrigger1 = Area.fromRectangle(new WorldTile(2925, 5464, 0), new WorldTile(2925, 5463, 0));

    public boolean collectFruit(WorldTile startTile) {
        Optional<GameObject> tree = Query.gameObjects().actionContains("Pick-Fruit")
                .sortedByDistance()
                .findClosest();

        if (tree.isEmpty()) return false;
        if (startTile.equals(MyPlayer.getPosition()) && Waiting.waitUntil(() -> {
            Waiting.waitUniform(15, 30);
            if (!tree.get().getModel().get().getBounds().get().contains(org.tribot.api.input.Mouse.getPos()))
            tree.get().hover();
            return collisionDetector.correctPosition(
                    Query.npcs()
                            .stream()
                            .filter(ElementalCollisionDetector::isSpringElemental)
                            .collect(Collectors.toList()), 5);
        })) {
            if (tree.map(t -> t.interact("Pick-Fruit")).orElse(false)) {
                return Timer.waitCondition(()-> CENTRE_OF_GARDEN.contains(Player.getPosition()), 5500, 7000);
            }
        }
        return false;
    }

    public boolean moveToTileAndHoverNext(WorldTile startTile, int index, WorldTile moveTo) {
        Mouse.setClickMethod(Mouse.ClickMethod.ACCURATE_MOUSE);

        if (startTile.equals(MyPlayer.getPosition()) && Waiting.waitUntil(() -> {
            Waiting.waitUniform(15, 30);
            if (!moveTo.getBounds().get().contains(org.tribot.api.input.Mouse.getPos()))
                moveTo.hover();
            return collisionDetector.correctPosition(
                    Query.npcs()
                            .stream()
                            .filter(ElementalCollisionDetector::isSpringElemental)
                            .collect(Collectors.toList()), index);
        })) {
            if (moveTo.click()) {
                return Timer.waitCondition(()-> !startTile.equals(MyPlayer.getPosition()), 1500, 2200);
            }
        }
        return false;
    }

    public boolean moveToTileAndHoverNext(WorldTile startTile, Area triggerArea,
                                          WorldTile moveTo, boolean customCond) {
        Mouse.setClickMethod(Mouse.ClickMethod.ACCURATE_MOUSE);
        if (startTile.equals(MyPlayer.getPosition()) && Waiting.waitUntil(45000, () -> {
            Waiting.waitUniform(20, 50);
            if (!moveTo.getBounds().get().contains(org.tribot.api.input.Mouse.getPos()))
                moveTo.hover();
            return Query.npcs().inArea(triggerArea).stream().findAny().isEmpty() && customCond;
        })) {
            if (moveTo.click()) {
                return Timer.waitCondition(()-> !startTile.equals(MyPlayer.getPosition()), 1500, 2200);
            }
        }

        return false;
    }

    @Override
    public Priority priority() {
        return Priority.HIGHEST;
    }

    @Override
    public boolean validate() {
        return Vars.get().currentTask != null &&
                Vars.get().currentTask == SkillTasks.THIEVING && Vars.get().doGarden;
    }

    @Override
    public void execute() {
        Mouse.setSpeed(165);
        if (CENTRE_OF_GARDEN.contains(Player.getPosition())) {
           Waiting.waitNormal(1250,250);
            if (Utils.clickObj(12719, "Open")) {
                Timer.waitCondition(() -> !CENTRE_OF_GARDEN.contains(Player.getPosition()), 6500, 8500);
                tile1.interact("Walk here");
            }
        }
        //moveToTileAndHoverNext(intialTile, tile2Trigger, tile1);
        moveToTileAndHoverNext(tile1, 0, tile2);
        moveToTileAndHoverNext(tile2, 1, tile3);
        moveToTileAndHoverNext(tile3, 2, tile4);
        moveToTileAndHoverNext(tile4, 3, tile5);
        moveToTileAndHoverNext(tile5, 4, tile6);
        collectFruit(tile6);
        //moveToTileAndHoverNext(tile3, tile4Trigger, tile4,
        //        Query.npcs().inArea(customTrigger1).stream().findAny().isEmpty());
    }

    @Override
    public String taskName() {
        return "Spring Garden";
    }
}
