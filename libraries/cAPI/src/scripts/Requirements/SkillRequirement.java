package scripts.Requirements;

import org.apache.commons.lang3.StringUtils;
import org.tribot.api2007.Skills;

import java.util.Locale;

public class SkillRequirement implements Requirement{

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

    @Override
    public String toString(){
        return "Missing Skill requirement of level " + this.minLevel + " for " +
                StringUtils.capitalize(this.skill.toString().toLowerCase(Locale.ROOT));

    }
    @Override
    public boolean check() {
        return meetsSkillRequirement();
    }
}
