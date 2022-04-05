package scripts.NmzData;

import org.tribot.api.General;
import org.tribot.api2007.Skills;
import org.tribot.script.sdk.Log;
import org.tribot.script.sdk.Skill;
import org.tribot.script.sdk.antiban.PlayerPreferences;
import scripts.Timer;
import scripts.Utils;
import scripts.Varbits;

import java.util.HashMap;

public class Vars {
    private static Vars vars;

    public static Vars get() {
        return vars == null ? vars = new Vars() : vars;
    }


    public static void reset() {
        vars = new Vars();
    }

    public boolean scriptStatus = true;
    /**
     * Strings
     */
    public String target = null;

    public String status = "Initializing";

    public long currentTime;

    public long startTime = System.currentTimeMillis();

    public long lastAction = System.currentTimeMillis();

    /**
     * Integers
     */
    public int xpHr = 0;
    public int add = General.random(5, 10);
    public int drinkAt = General.random(7, 25);
    public int eatAt = General.random(3, 10);
    public double drinkPrayAtPercentage =General.randomSD(6, 65, 40, 12);
    public int eatRockCakeAt =   General.randomSD(2, 6, 3, 2);

    public int drinkAbsorptionAt = General.random(100, 250);
    public int drinkAbsorptionUpTo = General.random(400, 800);
    public HashMap<Skills.SKILLS, Integer> skillStartXpMap = new HashMap<>();


    /**
     * Booleans
     */
    public boolean usingAbsorptions = false;
    public  boolean usingPrayerPots = false;
    public  boolean usingSuperCombat = false;
    public  boolean usingRangingPotion = false;
    public boolean usingOverloadPots = false;
    public boolean isOverloadActive = false;
    public boolean showDetailedPaint = false;

    public Timer overloadTimer = new Timer(0);  //5 min

    public int getGainedNmzPoints(){
        return Utils.getVarBitValue(Varbits.NMZ_POINTS.getId()) - Const.STARTING_NMZ_POINTS;
    }
    public  int getNmzPointsHr(long startTimeMs) {
        return (int) ((getGainedNmzPoints())
                / ((double) (System.currentTimeMillis() - startTimeMs) / 3600000));
    }

    // the mean we'll boost at will be from 25-50% of the max boost from the ranged pot
    public int rangedPotionAddMean = PlayerPreferences.preference(
            "rangedPotionAddMean", g -> getBoostAddMean(Skill.RANGED));

    // the SD we'll boost at will be from 5-15% of the max boost from the ranged pot
    public int rangedPotionAddSd = PlayerPreferences.preference(
            "rangedPotionAddSd", g ->  getBoostAddSd(Skill.RANGED));

    // the mean we'll boost at will be from 25-50% of the max boost from the SUPER ranged pot
    public int superRangedPotionAddMean = PlayerPreferences.preference(
            "rangedPotionAddMean", g -> getSuperBoostAddMean(Skill.RANGED));

    // the SD we'll boost at will be from 5-15% of the max boost from the SUPER ranged pot
    public int superRangedPotionAddSd = PlayerPreferences.preference(
            "rangedPotionAddSd", g ->  getSuperBoostAddSd(Skill.RANGED));

    public int superCombatPotionAddMean = PlayerPreferences.preference(
            "superCombatPotionAddMean", g -> getSuperBoostAddMean(Skill.STRENGTH));

    public int superCombatPotionAddSd = PlayerPreferences.preference(
            "superCombatPotionAddSd", g -> getSuperBoostAddSd(Skill.STRENGTH));

    public int superCombatAdd = General.randomSD(superCombatPotionAddMean, superCombatPotionAddSd);


    public int getSuperBoostAddSd(Skill skill){
        return  (int) (Utils.getSuperLevelBoost(Skill.RANGED) *
                Utils.random(0.10, 0.20)+1);
    }

    // generates uniform distribution
    public int getSuperBoostAddMean(Skill skill){
        return (int) (Utils.getSuperLevelBoost(Skill.RANGED) *
                Utils.random(0.3, 0.5));
    }


    public int getBoostAddSd(Skill skill){
      return  (int) (Utils.getLevelBoost(Skill.RANGED) *
               Utils.random(0.10, 0.25)+1);
    }

    // generates uniform distribution
    public int getBoostAddMean(Skill skill){
        return (int) (Utils.getLevelBoost(Skill.RANGED) *
                Utils.random(0.25, 0.5));
    }

    public int gainedAtkLvl = Skill.ATTACK.getActualLevel() - Const.startAttLvl;
    public int gainedStrLvl = Skill.STRENGTH.getActualLevel() - Const.startStrLvl;
    public int gainedDefLvl = Skill.DEFENCE.getActualLevel() - Const.startDefLvl;
    public int gainedHPLvl = Skill.HITPOINTS.getActualLevel() - Const.startHPLvl;
    public int gainedRangedLvl = Skill.RANGED.getActualLevel() - Const.startRangeLvl;
    public int gainedMagicLvl = Skill.MAGIC.getActualLevel() - Const.startMageLvl;
}
