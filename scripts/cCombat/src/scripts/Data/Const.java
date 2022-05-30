package scripts.Data;

import org.tribot.api2007.Skills;
import org.tribot.script.sdk.Skill;

public class Const {
    public static int startSlayerXP = Skills.getXP(Skills.SKILLS.SLAYER);
    public  static int startAttXp = Skills.getXP(Skills.SKILLS.ATTACK);
    public  static int startStrXp = Skills.getXP(Skills.SKILLS.STRENGTH);
    public  static int startDefXp = Skills.getXP(Skills.SKILLS.DEFENCE);
    public  static int startRangeXp = Skills.getXP(Skills.SKILLS.RANGED);
    public  static int startMageXp = Skills.getXP(Skills.SKILLS.MAGIC);
    public  static int startHPXP = Skills.getXP(Skills.SKILLS.HITPOINTS);

    public final static int startSlayerLvl= Skill.SLAYER.getActualLevel();
    public  final static int startAttLvl = Skill.ATTACK.getActualLevel();
    public  final  static int startStrLvl = Skill.STRENGTH.getActualLevel();
    public  final static int startDefLvl =Skill.DEFENCE.getActualLevel();
    public final static int startRangeLvl = Skill.RANGED.getActualLevel();
    public  final static int startMageLvl = Skill.MAGIC.getActualLevel();
    public  final static int startHPLvl = Skill.HITPOINTS.getActualLevel();

    public  final static int BRUTAL_BLACK_ID = 7275;

}
