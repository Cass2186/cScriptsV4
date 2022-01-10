package scripts.Requirements;

import org.tribot.api2007.Skills;

public class SkillRequirement {

    Skills.SKILLS skill;
    int minLevel = -1;

    public SkillRequirement(Skills.SKILLS skill, int minLevel) {
        this.skill = skill;
        this.minLevel = minLevel;
    }


    public boolean meetsSkillRequirement() {
        return skill != null && minLevel != -1 &&
                Skills.getActualLevel(this.skill) >= this.minLevel;

    }
}
