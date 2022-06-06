package scripts.NmzData;

import org.tribot.api.General;
import org.tribot.api2007.Skills;
import org.tribot.script.sdk.Log;
import org.tribot.script.sdk.Prayer;
import org.tribot.script.sdk.Skill;
import org.tribot.script.sdk.antiban.PlayerPreferences;
import org.tribot.script.sdk.util.TribotRandom;
import scripts.Requirements.InventoryRequirement;
import scripts.Timer;
import scripts.Utils;
import scripts.Varbits;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

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


    public Optional<InventoryRequirement> invRequirement = Optional.empty();

    /**
     * Integers
     */
    public int overloadDosesToGet = 36;
    public int absorptionDosesToGet = 72;
    public int endDreamNumber = 1;
    public int add = General.random(5, 10);
    public int drinkAt = General.random(7, 25);
    public int eatAt = General.random(3, 10);
    public double drinkPrayAtPercentage = General.randomSD(6, 65, 40, 12);

    public int drinkAbsorptionAt = General.random(100, 250);
    public int drinkAbsorptionUpTo = General.random(400, 800);
    public HashMap<Skills.SKILLS, Integer> skillStartXpMap = new HashMap<>();

    public List<Prayer> boostPrayerList = new ArrayList<>();

    /**
     * Booleans
     */
    public boolean usingAbsorptions = false;
    public boolean usingPrayerPots = false;
    public boolean usingSuperCombat = false;
    public boolean usingRangingPotion = false;
    public boolean usingOverloadPots = false;
    public boolean usingLocatorOrb = false;
    public boolean usingMeleeBoost = false;
    public boolean usingRangingBoost = false;
    public boolean usingMagicBoost = false;

    public boolean isOverloadActive = false;
    public boolean showDetailedPaint = false;

    public int eatRockCakeAt = usingLocatorOrb ? General.randomSD(2, 6, 3, 1) :
            General.randomSD(2, 6, 3, 2);

    public int getEatRockCakeAt(){
        return General.randomSD(2, 6, 3, 1);
    }

    public Timer overloadTimer = new Timer(0);  //5 min

    public int getGainedNmzPoints() {
        return Utils.getVarBitValue(Varbits.NMZ_POINTS.getId()) - Const.STARTING_NMZ_POINTS;
    }

    public int getNmzPointsHr(long startTimeMs) {
        return (int) ((getGainedNmzPoints())
                / ((double) (System.currentTimeMillis() - startTimeMs) / 3600000));
    }

    // the mean we'll boost at will be from 25-50% of the max boost from the ranged pot
    public int rangedPotionAddMean = PlayerPreferences.preference(
            "rangedPotionAddMean", g -> getBoostAddMean(Skill.RANGED));

    // the SD we'll boost at will be from 5-15% of the max boost from the ranged pot
    public int rangedPotionAddSd = PlayerPreferences.preference(
            "rangedPotionAddSd", g -> getBoostAddSd(Skill.RANGED));

    // the mean we'll boost at will be from 25-50% of the max boost from the SUPER ranged pot
    public int superRangedPotionAddMean = PlayerPreferences.preference(
            "rangedPotionAddMean", g -> getSuperBoostAddMean(Skill.RANGED));

    // the SD we'll boost at will be from 5-15% of the max boost from the SUPER ranged pot
    public int superRangedPotionAddSd = PlayerPreferences.preference(
            "rangedPotionAddSd", g -> getSuperBoostAddSd(Skill.RANGED));

    public int superCombatPotionAddMean = PlayerPreferences.preference(
            "superCombatPotionAddMean", g -> getSuperBoostAddMean(Skill.STRENGTH));

    public int superCombatPotionAddSd = PlayerPreferences.preference(
            "superCombatPotionAddSd", g -> getSuperBoostAddSd(Skill.STRENGTH));

    public int superCombatAdd = TribotRandom.normal(superCombatPotionAddMean, superCombatPotionAddSd);


    public int getSuperBoostAddSd(Skill skill) {
        return (int) (Utils.getSuperLevelBoost(Skill.RANGED) *
                Utils.random(0.10, 0.20) + 1);
    }

    // generates uniform distribution
    public int getSuperBoostAddMean(Skill skill) {
        return (int) (Utils.getSuperLevelBoost(skill) *
                Utils.random(0.45, 0.60) +2);
    }


    public int getBoostAddSd(Skill skill) {
        return (int) (Utils.getLevelBoost(skill) *
                Utils.random(0.10, 0.25) + 1);
    }

    // generates uniform distribution
    public int getBoostAddMean(Skill skill) {
        return (int) (Utils.getLevelBoost(skill) *
                Utils.random(0.25, 0.5));
    }

    public int gainedAtkLvl = Skill.ATTACK.getActualLevel() - Const.startAttLvl;
    public int gainedStrLvl = Skill.STRENGTH.getActualLevel() - Const.startStrLvl;
    public int gainedDefLvl = Skill.DEFENCE.getActualLevel() - Const.startDefLvl;
    public int gainedHPLvl = Skill.HITPOINTS.getActualLevel() - Const.startHPLvl;
    public int gainedRangedLvl = Skill.RANGED.getActualLevel() - Const.startRangeLvl;
    public int gainedMagicLvl = Skill.MAGIC.getActualLevel() - Const.startMageLvl;
}
