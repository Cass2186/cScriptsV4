package scripts.Data;

import lombok.Getter;
import org.tribot.api2007.Skills;
import org.tribot.script.sdk.Skill;

public class CombatTask {


    @Getter
    private Skills.SKILLS skill;

    @Getter
    private int goalLevel;

    public CombatTask(Skills.SKILLS skill, int goalLevel){
        this.skill = skill;
        this.goalLevel = goalLevel;
    }

}
