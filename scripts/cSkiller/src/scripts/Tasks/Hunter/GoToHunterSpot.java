package scripts.Tasks.Hunter;

import obf.I;
import org.tribot.script.sdk.Skill;
import org.tribot.script.sdk.types.Area;
import scripts.API.Priority;
import scripts.API.Task;
import scripts.Data.SkillTasks;
import scripts.Data.Vars;
import scripts.ItemID;
import scripts.Requirements.InventoryRequirement;
import scripts.Requirements.ItemReq;
import scripts.Requirements.ItemRequirement;
import scripts.Tasks.Hunter.HunterData.HunterConst;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class GoToHunterSpot implements Task {

    public static Optional<Area> getHunterArea() {
        if (Skill.HUNTER.getActualLevel() < 29) {

        } else if (Skill.HUNTER.getActualLevel() < 43) {
            return Optional.of(HunterConst.CANIFIS_HUNTER_AREA);
        } else if (Skill.HUNTER.getActualLevel() < 47) {
            // falconry area
        } else if (Skill.HUNTER.getActualLevel() < 67) {
            return  Optional.of(HunterConst.YELLOW_SAL_AREA);
        }

        return Optional.empty();
    }

    public static Optional<InventoryRequirement> getInventoryRequirement() {
        InventoryRequirement inv;
        if (Skill.HUNTER.getActualLevel() < 29) {
            inv = new InventoryRequirement(new ArrayList<>(

            ));
        } else if (Skill.HUNTER.getActualLevel() < 43) {
            inv = new InventoryRequirement(new ArrayList<>(List.of(
                    new ItemReq(ItemID.COINS_995, 2000, 500),
                    new ItemReq(ItemID.ROPE, 4, 0),
                    new ItemReq(ItemID.SMALL_FISHING_NET, 4, 0),
                    new ItemReq(ItemID.FENKENSTRAINS_CASTLE_TELEPORT, 2, 0)
            )));
        } else if (Skill.HUNTER.getActualLevel() < 47) {
            // falconry area
            inv = new InventoryRequirement(new ArrayList<>(List.of(
                    new ItemReq(ItemID.COINS_995, 2000, 500),
                    new ItemReq(ItemID.PISCATORIS_TELEPORT, 2, 0))
            ));
        } else if (Skill.HUNTER.getActualLevel() < 67) {
            //desert salamanders
            inv = new InventoryRequirement(new ArrayList<>(List.of(
                    new ItemReq(ItemID.COINS_995, 2000, 500),
                    new ItemReq(ItemID.ROPE, 4, 0),
                    new ItemReq(ItemID.SMALL_FISHING_NET, 4, 0),
                    new ItemReq(ItemID.WATERSKIN4, 10, 1),
                    new ItemReq(ItemID.DESERT_ROBE, 1, 1, true, true)
            )));
        } else {
            inv = new InventoryRequirement(new ArrayList<>(

            ));
        }

        return Optional.of(inv);
    }

    @Override
    public Priority priority() {
        return Priority.MEDIUM;
    }

    @Override
    public boolean validate() {
        return Vars.get().currentTask != null &&
                Vars.get().currentTask.equals(SkillTasks.HUNTER);
    }

    @Override
    public void execute() {

    }

    @Override
    public String toString() {
        return "Going to Hunter Location";
    }

    @Override
    public String taskName() {
        return "Hunter";
    }
}
