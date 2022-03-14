package scripts.Tasks;

import org.tribot.api2007.Skills;
import org.tribot.api2007.types.RSArea;
import org.tribot.script.sdk.Skill;
import scripts.Data.Const;
import scripts.Data.Vars;

import java.util.LinkedList;
import java.util.List;

public class ExpHandler {

    private static ExpHandler xpHandler;
    public static ExpHandler get() {
        return xpHandler == null ? xpHandler = new ExpHandler() : xpHandler;
    }


    public int gainedAtkLvl = Skill.ATTACK.getActualLevel() - Const.startAttLvl;
    public int gainedStrLvl = Skill.STRENGTH.getActualLevel() - Const.startStrLvl;
    public int gainedDefLvl = Skill.DEFENCE.getActualLevel() - Const.startDefLvl;
    public int gainedHPLvl = Skill.HITPOINTS.getActualLevel() - Const.startHPLvl;
    public int gainedRangedLvl = Skill.RANGED.getActualLevel() - Const.startRangeLvl;
    public int gainedMagicLvl = Skill.MAGIC.getActualLevel() - Const.startMageLvl;

    public static void updateStartXpAndLevel() {
        Const.startSlayerXP = Skills.getXP(Skills.SKILLS.SLAYER);
        Const.startAttXp = Skills.getXP(Skills.SKILLS.ATTACK);
        Const.startStrXp = Skills.getXP(Skills.SKILLS.STRENGTH);
        Const.startDefXp = Skills.getXP(Skills.SKILLS.DEFENCE);
        Const.startRangeXp = Skills.getXP(Skills.SKILLS.RANGED);
        Const.startMageXp = Skills.getXP(Skills.SKILLS.MAGIC);
        Const.startHPXP = Skills.getXP(Skills.SKILLS.HITPOINTS);

    }

}
