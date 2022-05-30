package scripts.Requirements;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import org.tribot.api2007.Skills;

import java.util.Locale;

public class SkillRequirement implements Requirement{
    @Getter
    Skills.SKILLS skill;

    @Getter
    int minLevel = -1;

    @Getter
    private  boolean canBoost = false; //TODo incorporate into tthe check

    public SkillRequirement(Skills.SKILLS skill, int minLevel) {
        this.skill = skill;
        this.minLevel = minLevel;
    }


    public SkillRequirement(Skills.SKILLS skill, int minLevel, boolean canBoost) {
        this.skill = skill;
        this.minLevel = minLevel;
        this.canBoost = canBoost;
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
