package scripts.Tasks.Diaries.Ardougne.Medium;

import obf.I;
import org.tribot.api2007.Skills;
import scripts.API.Priority;
import scripts.API.Task;
import scripts.Requirements.InventoryRequirement;
import scripts.Requirements.Requirement;
import scripts.Requirements.SkillRequirement;

import java.util.ArrayList;
import java.util.List;

public class ArdougneMedium implements Task {

    private InventoryRequirement getInventoryRequirement() {
        return new InventoryRequirement(new ArrayList<>(

        ));
    }



    private List<Requirement> getSkillRequirements() {
        return List.of(
                new SkillRequirement(Skills.SKILLS.AGILITY, 39),
                new SkillRequirement(Skills.SKILLS.ATTACK, 50),
                new SkillRequirement(Skills.SKILLS.CONSTRUCTION, 10),
                new SkillRequirement(Skills.SKILLS.CRAFTING, 49),
                new SkillRequirement(Skills.SKILLS.FARMING, 31),
                new SkillRequirement(Skills.SKILLS.FIREMAKING, 50),
                new SkillRequirement(Skills.SKILLS.MAGIC, 51),
                new SkillRequirement(Skills.SKILLS.RANGED, 25),
                new SkillRequirement(Skills.SKILLS.STRENGTH, 38),
                new SkillRequirement(Skills.SKILLS.THIEVING, 38)
        );
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

    }

    @Override
    public String taskName() {
        return null;
    }
}
