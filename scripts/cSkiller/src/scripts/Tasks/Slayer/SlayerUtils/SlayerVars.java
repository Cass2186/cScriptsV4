package scripts.Tasks.Slayer.SlayerUtils;

import org.tribot.api.General;
import org.tribot.api2007.Game;
import org.tribot.api2007.Player;
import org.tribot.api2007.Skills;
import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSItem;
import org.tribot.api2007.types.RSTile;
import org.tribot.script.sdk.types.EquipmentItem;
import scripts.ItemID;
import scripts.Tasks.Slayer.SlayerConst.Assign;
import scripts.Tasks.Slayer.SlayerConst.SlayerConst;
import scripts.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

public class SlayerVars {

    private static SlayerVars vars;

    public static SlayerVars get() {
        return vars == null ? vars = new SlayerVars() : vars;
    }

    public static void reset() {
        vars = new SlayerVars();
    }

    /**
     * Strings
     */
    public String[] targets = null;

    public String status = "Initializing...";


    public ArrayList<String> potionNames = new ArrayList<>();

    public HashMap<Integer, Integer> restockList = new HashMap<>();

    public Assign assignment;

    /**
     * Tiles and Areas
     */
    public RSTile combatTile, bankTile;

    public RSArea fightArea = null;

    public boolean needBootsOfStone = false;

    public boolean useSpecialItem = false;
    /**
     * Intergers
     */
    public int eatAtHP, playerCount, npcItemID;
    public int remainingKills = Game.getSetting(SlayerConst.REMAINING_KILLS_GAMESETTING);

    public int slayerPoints = Utils.getVarBitValue(SlayerConst.SLAYER_POINTS_VARBIT);

    public int lootValue = 0;

    public int customFoodId = ItemID.MONKFISH;

    public int attXp, strXp, defXp, mageXp, rangeXp, hpXp, slayerXp, rcXp;

    public int[] itemsToLoot;

    public int taskStreak = 0;

    public int restockNumber = 6;

    public int minLootValue = 1600;

    public int kills = 0;

    public int afkLengthMin = 30000;

    public int afkLengthMax = 120000;

    public int[] potionToUse = ItemID.SUPER_COMBAT_POTION;
    public int drinkCombatPotion = Skills.SKILLS.STRENGTH.getActualLevel() + General.random(3,6);

    public int profit = 0;

    public int startSlayerXP = Skills.getXP(Skills.SKILLS.SLAYER);
    public int startAttXp = Skills.getXP(Skills.SKILLS.ATTACK);
    public int startStrXp = Skills.getXP(Skills.SKILLS.STRENGTH);
    public int startDefXp = Skills.getXP(Skills.SKILLS.DEFENCE);
    public int startRangeXp = Skills.getXP(Skills.SKILLS.RANGED);
    public int startMageXp = Skills.getXP(Skills.SKILLS.MAGIC);
    public int startHPXP = Skills.getXP(Skills.SKILLS.HITPOINTS);
    public int startRcXp = Skills.getXP(Skills.SKILLS.RUNECRAFTING);


    /**
     * Booleans
     */

    public boolean needFungicide = false;
    public boolean setLumbridgeRope = false;

    public boolean script = true;

    public boolean bank = true;

    public boolean shouldRestock = false;

    public boolean abc2Delay = true;

    public boolean shouldPrayMelee = false;
    public boolean shouldPrayMagic = false;

    public boolean shouldLoot = false;

    public boolean getTask = false;

    public boolean cancelTask = false;

    public boolean usingAntiFire = false;

    public boolean pointBoosting = true;

    public boolean shouldBank = false;

    public boolean shouldAfk = false;

    public boolean useExpeditiousBracelet = true;

    public boolean useBraceletOfSlaughter = false;

    public boolean needToReplaceExpBracelet = false;

    public int expeditiousCharges = 0;

    public boolean drinkPotions = true;

    public boolean needDustyKey = false;

    public boolean shouldWorldHop = false;

    public boolean needsIceCoolers = false;

    public boolean magicMeleeGear = false;

    public boolean usingSpecialItem = false;

    public boolean shouldSkipTask = false;

    public boolean needRockHammer = false;

    public boolean needsGem = false;

    public boolean slayerShopRestock = false;

    public boolean wallbeastTask = false;


    public boolean shouldAlchItems = Skills.getActualLevel(Skills.SKILLS.MAGIC) >= 55;

    /**
     * Other
     */

    public long currentTime, startTime;

    /**
     * GEAR
     */

    public String[] fonts = {
            "Arial Nova",
            "Source Sans Pro",
            "Trebuchet MS"
    };
    public int abc2Chance = General.random(0, 100);

    public Optional<EquipmentItem>  HEAD_GEAR;
    public Optional<EquipmentItem> CAPE_GEAR;
    public Optional<EquipmentItem> NECK_GEAR;
    public Optional<EquipmentItem> AMMO_GEAR;
    public Optional<EquipmentItem> WEAPON_GEAR;
    public Optional<EquipmentItem> BODY_GEAR;
    public Optional<EquipmentItem> SHIELD_GEAR;
    public Optional<EquipmentItem> LEG_GEAR;
    public Optional<EquipmentItem> BOOT_GEAR;
    public Optional<EquipmentItem> GLOVES_GEAR;
    public Optional<EquipmentItem> RING_GEAR;

    /**
     * CANNON
     */
    public boolean use_cannon = false;

    public boolean otherCannonInOurArea = false;

    public int fill_cannon_at = General.random(0, 24);

    public RSTile cannon_location;

}
