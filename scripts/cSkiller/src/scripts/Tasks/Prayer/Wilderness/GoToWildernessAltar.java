package scripts.Tasks.Prayer.Wilderness;

import org.tribot.script.sdk.*;
import org.tribot.script.sdk.interfaces.Positionable;
import org.tribot.script.sdk.query.Query;
import org.tribot.script.sdk.types.Area;
import org.tribot.script.sdk.types.EquipmentItem;
import org.tribot.script.sdk.types.World;
import org.tribot.script.sdk.types.WorldTile;
import org.tribot.script.sdk.walking.GlobalWalking;
import org.tribot.script.sdk.walking.LocalWalking;
import org.tribot.script.sdk.walking.WalkState;
import scripts.API.Priority;
import scripts.API.Task;
import scripts.Data.SkillTasks;
import scripts.Data.Vars;
import scripts.ItemID;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class GoToWildernessAltar implements Task {

    int CHAOS_ALTAR_ID = 411;

    int[] BURNING_AMULET = {21166, 21169, 21171, 21173, 21175};

    Area WHOLE_ALTAR_BUILDING = Area.fromRectangle(new WorldTile(2957, 3817, 0),
            new WorldTile(2945, 3824, 0));
    Area altarBuilding = Area.fromRectangle(new WorldTile(2947, 3822, 0), new WorldTile(2957, 3819, 0));
    Area LAVA_MAZE_AREA = Area.fromRectangle(new WorldTile(3015, 3846, 0), new WorldTile(3044, 3830, 0));
    Area LUMBRIDGE_DEATH_SPAWN = Area.fromRectangle(new WorldTile(3217, 3212, 0), new WorldTile(3226, 3225, 0));
    Area WILDERNESS_HALF_WAY = Area.fromRectangle(new WorldTile(2983, 3823, 0), new WorldTile(2995, 3819, 0));
    Area DANGER_AREA = Area.fromRectangle(new WorldTile(2943, 3827, 0), new WorldTile(2966, 3812, 0));
    Area WHOLE_WILDERNESS = Area.fromRectangle(new WorldTile(2944, 3803, 0), new WorldTile(3071, 3854, 0));
    Area CHAOS_FANATIC_AREA = Area.fromRectangle(new WorldTile(2975, 3846, 0), new WorldTile(2989, 3835, 0));
    List<Positionable> PATH_TO_ALTAR = Arrays.asList(new WorldTile[]{new WorldTile(3028, 3840, 0), new WorldTile(3028, 3840, 0), new WorldTile(3027, 3839, 0), new WorldTile(3026, 3838, 0),
            new WorldTile(3025, 3837, 0), new WorldTile(3024, 3836, 0), new WorldTile(3023, 3835, 0), new WorldTile(3022, 3834, 0),
            new WorldTile(3021, 3833, 0), new WorldTile(3020, 3832, 0), new WorldTile(3019, 3831, 0), new WorldTile(3018, 3830, 0),
            new WorldTile(3017, 3829, 0), new WorldTile(3016, 3828, 0), new WorldTile(3016, 3827, 0), new WorldTile(3016, 3826, 0),
            new WorldTile(3015, 3825, 0), new WorldTile(3015, 3824, 0), new WorldTile(3015, 3823, 0), new WorldTile(3015, 3823, 0),
            new WorldTile(3014, 3823, 0), new WorldTile(3013, 3823, 0), new WorldTile(3012, 3823, 0), new WorldTile(3011, 3823, 0),
            new WorldTile(3010, 3823, 0), new WorldTile(3009, 3823, 0), new WorldTile(3008, 3823, 0), new WorldTile(3007, 3823, 0),
            new WorldTile(3006, 3823, 0), new WorldTile(3005, 3823, 0), new WorldTile(3004, 3823, 0), new WorldTile(3003, 3823, 0),
            new WorldTile(3002, 3823, 0), new WorldTile(3001, 3823, 0), new WorldTile(3000, 3823, 0), new WorldTile(2999, 3823, 0), new WorldTile(2998, 3823, 0), new WorldTile(2997, 3823, 0), new WorldTile(2996, 3823, 0),
            new WorldTile(2995, 3823, 0), new WorldTile(2994, 3823, 0), new WorldTile(2993, 3823, 0), new WorldTile(2992, 3823, 0),
            new WorldTile(2991, 3823, 0), new WorldTile(2990, 3823, 0), new WorldTile(2989, 3823, 0), new WorldTile(2988, 3823, 0),
            new WorldTile(2987, 3823, 0), new WorldTile(2986, 3823, 0), new WorldTile(2985, 3823, 0), new WorldTile(2984, 3823, 0),
            new WorldTile(2983, 3823, 0), new WorldTile(2982, 3823, 0), new WorldTile(2981, 3823, 0), new WorldTile(2980, 3823, 0),
            new WorldTile(2979, 3823, 0), new WorldTile(2978, 3823, 0), new WorldTile(2977, 3823, 0), new WorldTile(2976, 3823, 0),
            new WorldTile(2975, 3823, 0), new WorldTile(2974, 3823, 0), new WorldTile(2973, 3823, 0), new WorldTile(2972, 3823, 0),
            new WorldTile(2971, 3823, 0), new WorldTile(2970, 3823, 0), new WorldTile(2969, 3823, 0), new WorldTile(2968, 3823, 0),
            new WorldTile(2967, 3823, 0), new WorldTile(2966, 3823, 0), new WorldTile(2965, 3823, 0), new WorldTile(2964, 3823, 0),
            new WorldTile(2963, 3823, 0), new WorldTile(2962, 3823, 0), new WorldTile(2961, 3823, 0), new WorldTile(2960, 3823, 0),
            new WorldTile(2959, 3822, 0), new WorldTile(2958, 3821, 0), new WorldTile(2957, 3821, 0), new WorldTile(2956, 3821, 0), new WorldTile(2955, 3821, 0), new WorldTile(2954, 3821, 0),
            new WorldTile(2953, 3821, 0), new WorldTile(2952, 3821, 0), new WorldTile(2951, 3821, 0), new WorldTile(2950, 3820, 0), new WorldTile(2949, 3820, 0), new WorldTile(2948, 3820, 0)});


    WorldTile ALTAR_WALK_TARGET = new WorldTile(2949, 3821, 0);

    private boolean shouldGoToAltar() {
        return Inventory.getCount(ItemID.DRAGON_BONES) > 3 &&
                Equipment.getAll().size() <= 1 &&
                !altarBuilding.containsMyPlayer();
    }

    private boolean useBurning() {
        if (Combat.isInWilderness())
            return true;

        Optional<EquipmentItem> burning_amulet = Query.equipment().nameContains("Burning amulet")
                .findClosestToMouse();
        if (burning_amulet.map(b -> b.click("Lava Maze")).orElse(false)) {
            Waiting.waitUntil(2500, 125, () -> ChatScreen.isOpen());
        }
        if (ChatScreen.isOpen()) {
            WorldTile myTile = MyPlayer.getTile();
            if (ChatScreen.handle("Okay, ")) {
                Waiting.waitUntil(5000, 500,
                        () -> !MyPlayer.getTile().equals(myTile));
                return Combat.isInWilderness();
            }
        }
        return Combat.isInWilderness();
    }

    @Override
    public Priority priority() {
        return Priority.LOW;
    }

    @Override
    public boolean validate() {
        SkillTasks task = Vars.get().currentTask;
        return task != null && task.equals(SkillTasks.PRAYER) &&
                Vars.get().useWildernessAltar &&
                shouldGoToAltar();
    }

    @Override
    public void execute() {
        if (!Combat.isInWilderness() && useBurning()) {
            Waiting.waitNormal(400, 15); //otherwise tries to tele agail
            Log.info("Walking path to altar");
            LocalWalking.Map.builder().travelThroughDoors(true).build();
            if (LocalWalking.walkPath(PATH_TO_ALTAR, () -> {
                if (PkObserver.shouldHop()) {
                    Log.warn("Should hop (local)");
                    if (WorldHopper.hop(PkObserver.nextWorld)) {
                        PkObserver.nextWorld = PkObserver.getNextWorld();
                    }
                    return WalkState.FAILURE;
                }else {
              //      Log.info("No PKers");
                }

                return WalkState.CONTINUE;
            }))
                Waiting.waitUntil(5500, 75, () -> altarBuilding.containsMyPlayer());
        } else if (GlobalWalking.walkTo(ALTAR_WALK_TARGET, () -> {
                    if (PkObserver.shouldHop()) {
                        Log.warn("Should hop");
                        if (WorldHopper.hop(PkObserver.nextWorld)) {
                            PkObserver.nextWorld = PkObserver.getNextWorld();
                        }
                        return WalkState.FAILURE;
                    } else {
                        Log.info("No PKers");
                    }
                    return WalkState.CONTINUE;
                }
        ))
            Waiting.waitUntil(5500, 75, () -> altarBuilding.containsMyPlayer());
    }

    @Override
    public String taskName() {
        return "Prayer - Wilderness";
    }

    @Override
    public String toString() {
        return "Going to Wilderness Altar";
    }
}
