package scripts.Data;

import org.tribot.api.General;
import org.tribot.api2007.Combat;
import org.tribot.api2007.Player;
import org.tribot.api2007.Skills;
import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSItem;
import org.tribot.api2007.types.RSTile;
import scripts.ItemID;
import scripts.Timer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Vars {

    private static Vars vars;

    public static Vars get() {
        return vars == null ? vars = new Vars() : vars;
    }

    public static void reset() {
        vars = new Vars();
    }

    /**
     * Strings
     */
    public String[] targets = this.killingScarabs ?
            new String[]{"Scarab mage", "Undead Druid"} : new String[]{"Scarab mage", "Undead Druid"};


    public String status = "Initializing...";


    public ArrayList<String> potionNames = new ArrayList<>();

    public HashMap<Integer, Integer> restockList = new HashMap<>();
    public boolean killingScarabs = false;
    public boolean killingUndeadDruids = true;

    public HashMap<Skills.SKILLS, Integer> skillStartXpMap = new HashMap<>();

    public int startAgilityXp = Skills.getXP(Skills.SKILLS.AGILITY);
    public int startCraftingXp = Skills.getXP(Skills.SKILLS.CRAFTING);
    public int startConstructionXp = Skills.getXP(Skills.SKILLS.CONSTRUCTION);
    public int startCoookingXp = Skills.getXP(Skills.SKILLS.COOKING);
    public int startFishingXp = Skills.getXP(Skills.SKILLS.FISHING);
    public int startHerbloreXp = Skills.getXP(Skills.SKILLS.HERBLORE);
    public int startFiremakingXp = Skills.getXP(Skills.SKILLS.FIREMAKING);
    public int startMagicXp = Skills.getXP(Skills.SKILLS.MAGIC);
    public int startMiningXp = Skills.getXP(Skills.SKILLS.MINING);
    public int startWoodcuttingXp = Skills.getXP(Skills.SKILLS.WOODCUTTING);
    public int startHunterXp = Skills.getXP(Skills.SKILLS.HUNTER);

    /**
     * Tiles and Areas
     */
    public RSTile combatTile, bankTile;

    public RSTile safeTile = new RSTile(3208, 3812, 0);

    public RSArea fightArea = Areas.UNDEAD_DRUID_AREA;

    public List<Integer> gearIdList;

    public boolean needBootsOfStone = false;
    /**
     * Intergers
     */
    public int playerCount, npcItemID, remainingKills;

    public int eatAtHP = Combat.getMaxHP() - General.random(22, 40);
    public int drinkPrayerAt = General.random(8, 20);

    public int lootValue = 0;

    public int customFoodId = ItemID.MONKFISH;

    public int attXp, strXp, defXp, mageXp, rangeXp, hpXp, slayerXp, rcXp;

    public int[] itemsToLoot;

    public int taskStreak = 0;

    public int restockNumber = 6;

    public int minLootValue = 700;
    public int kills = 0;

    public int afkLengthMin = 30000;

    public int afkLengthMax = 120000;

    public int[] potionToUse = ItemID.RANGING_POTION;

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

    public boolean cannoning = true;

    public boolean hasCannon = false;

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

    public boolean needDustyKey;

    public boolean shouldWorldHop = false;

    public boolean needsIceCoolers = false;

    public boolean magicMeleeGear = false;

    public boolean usingSpecialItem = false;

    public boolean shouldSkipTask = false;

    public boolean needRockHammer = false;

    public boolean needsGem = false;

    public boolean slayerShopRestock = false;

    public boolean wallbeastTask = false;


    public boolean alchItems = Skills.getActualLevel(Skills.SKILLS.MAGIC) >= 55;

    /**
     * Other
     */
    public Timer antifireTimer = new Timer(0);
    public long currentTime, startTime;

    // public Tabs tab = Paint.Tabs.INFO;

    /**
     * GEAR
     */

    public String[] fonts = {
            "Arial Nova",
            "Source Sans Pro",
            "Trebuchet MS"
    };


    public RSItem HEAD_GEAR;
    public RSItem CAPE_GEAR;
    public RSItem NECK_GEAR;
    public RSItem AMMO_GEAR;
    public RSItem WEAPON_GEAR;
    public RSItem BODY_GEAR;
    public RSItem SHIELD_GEAR;
    public RSItem LEG_GEAR;
    public RSItem BOOT_GEAR;
    public RSItem GLOVES_GEAR;
    public RSItem RING_GEAR;

    /**
     * CANNON
     */
    public boolean use_cannon = true;

    public boolean otherCannonInOurArea = false;

    public int fill_cannon_at = General.random(0, 20);

    public RSTile cannon_location;

}
