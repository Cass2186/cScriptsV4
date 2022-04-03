package scripts.NmzData;

import org.tribot.api2007.Skills;
import org.tribot.script.sdk.Skill;
import scripts.Utils;
import scripts.Varbits;

public class Const {



   public static int NMZ_LEAVE_POTION = 26276; // use to determine you're in NMZ
    public static int[] ABSORPTION_POTION = {11734, 11735, 11736, 11737};
    public static  int[] OVERLOAD_POTION = {11730, 11731, 11732, 11733};
    public static   int[] SUPER_RANGING_POTION = {11722, 11723, 11724, 11725};
    public static    int ROCK_CAKE = 7510;
    public static final int STARTING_NMZ_POINTS = Utils.getVarBitValue(Varbits.NMZ_POINTS.getId());

 public static int startSlayerXP = Skill.SLAYER.getXp();
 public  static int startAttXp = Skill.ATTACK.getXp();
 public  static int startStrXp = Skill.STRENGTH.getXp();
 public  static int startDefXp =Skill.DEFENCE.getXp();
 public  static int startRangeXp = Skill.RANGED.getXp();
 public  static int startMageXp = Skill.MAGIC.getXp();
 public  static int startHPXP = Skill.HITPOINTS.getXp();

 public final static int startSlayerLvl= Skill.SLAYER.getActualLevel();
 public  final static int startAttLvl = Skill.ATTACK.getActualLevel();
 public  final  static int startStrLvl = Skill.STRENGTH.getActualLevel();
 public  final static int startDefLvl =Skill.DEFENCE.getActualLevel();
 public final static int startRangeLvl = Skill.RANGED.getActualLevel();
 public  final static int startMageLvl = Skill.MAGIC.getActualLevel();
 public  final static int startHPLvl = Skill.HITPOINTS.getActualLevel();
}
