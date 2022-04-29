package scripts.Tasks.Prayer.Wilderness;

import org.tribot.script.sdk.Equipment;
import org.tribot.script.sdk.Inventory;
import org.tribot.script.sdk.MyPlayer;
import org.tribot.script.sdk.types.Area;
import org.tribot.script.sdk.types.WorldTile;
import org.tribot.script.sdk.walking.GlobalWalking;
import scripts.API.Priority;
import scripts.API.Task;
import scripts.Data.SkillTasks;
import scripts.Data.Vars;
import scripts.ItemID;

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

    WorldTile ALTAR_WALK_TARGET = new WorldTile(2949, 3821, 0);

    private boolean shouldGoToAltar() {
        return Inventory.contains(ItemID.DRAGON_BONES) &&
                Equipment.getAll().size() <= 1 &&
                !altarBuilding.contains(MyPlayer.getTile());
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
        GlobalWalking.walkTo(ALTAR_WALK_TARGET);
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
