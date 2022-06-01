package scripts.Tasks.Slayer.SlayerConst;

import lombok.Getter;
import org.tribot.api.General;
import org.tribot.script.sdk.MyPlayer;
import org.tribot.script.sdk.types.WorldTile;
import org.tribot.script.sdk.Log;
import org.tribot.script.sdk.types.Area;
import scripts.ItemID;
import scripts.PrayerType;
import scripts.Requirements.ItemReq;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public enum Assign {

    ABBERANT_SPECTRES(new Builder()
            .inArea(Areas.ABBERANT_SPECTRES_AREA)
            .usingCannon(true)
            .useCannonTile(Areas.ABBERANT_SPECTRES_AREA.getRandomTile())
            .usePrayerType(PrayerType.MAGIC)
            .nameContains("Abberant spectre")
            .useGearType(GearType.MAGIC_MELEE)),

    ABYSSAL_DEMON(new Builder()
            .inArea(Areas.ABYSSAL_DEMON_AREA)
            .nameContains("Abyssal demon")
            .useGearType(GearType.MELEE)),

    ADAMANT_DRAGON(new Builder()
            // .inArea(Areas.BASILISK_AREA)
            .nameContains("Adamant dragon")
            .useSpecialItem(true)
            .specialItemID(ItemID.ANTIDRAGON_SHIELD)
            .useGearType(GearType.MELEE)),

    ANKOU(new Builder()
            .inArea(Areas.ANKOU_AREA)
            .nameContains("Ankou")
            .useGearType(GearType.MELEE)),

    AVIANSIE(new Builder()
            //  .inArea(Areas.)//TODO Add this area
            .nameContains("Aviansie")
            .useGearType(GearType.RANGED)),

    BANSHEE(new Builder()
            .inArea(Areas.BANSHEE_AREA)
            .nameContains("Banshee")
            .useSpecialItem(true)
            .setHopPlayerThreshold(2)
            .specialItemID(ItemID.EARMUFFS)
            .useGearType(GearType.MELEE)),

    BATS(new Builder()
            .inArea(Areas.BAT_AREA)
            .nameContains("Bat")
            .setHopPlayerThreshold(2)
            .useGearType(GearType.MELEE)),

    BASILISK(new Builder()
            .inArea(Areas.BASILISK_AREA)
            .nameContains("Basilisk")
            .setHopPlayerThreshold(3)
            .useSpecialItem(true)
            .specialItemID(ItemID.MIRROR_SHIELD)
            .useGearType(GearType.MELEE)),

    BEAR(new Builder()
            .inArea(Areas.BEAR_AREA)
            .nameContains("Bear")
            .setHopPlayerThreshold(2)
            .useGearType(GearType.MELEE)),

    BIRDS(new Builder()
            .inArea(Areas.BIRD_AREA)
            .nameContains("Bird")
            .nameContains("Chicken")
            .setHopPlayerThreshold(3)
            .useGearType(GearType.MELEE)),

    BLACK_DRAGON(new Builder()
            .inArea(Areas.BABY_BLACK_DRAGON_AREA)
            .nameContains("black dragon")
            .useSpecialItem(true)
            .specialItemID(ItemID.ANTIDRAGON_SHIELD)
            .useGearType(GearType.MELEE)),

    BLACK_DEMON(new Builder()
            .inArea(Areas.BLACK_DEMON_AREA)
            .nameContains("Black demon")
            .useHopTile(Areas.BLACK_DEMON_HOP_TILE)
            .setHopPlayerThreshold(3)
            .usePrayerType(PrayerType.MELEE)
            .useGearType(GearType.MELEE)),

    BLOODVELD(new Builder()
            .inArea(Areas.BLOODVELD_AREA)
            .usingCannon(true)
            .useCannonTile(Areas.BLOODVELD_CANNON_TILE)
            .nameContains("Bloodveld")
            .useGearType(GearType.MAGIC_MELEE)),

    BLUE_DRAGON(new Builder()
            .inArea(Areas.BLUE_DRAGON_AREA)
            .usingCannon(true)
            .useCannonTile(Areas.BLUE_DRAGON_CANNON_TILE)
            .nameContains("Baby blue dragon")
            .useSpecialItem(true)
            .setHopPlayerThreshold(3)
            .specialItemID(ItemID.ANTIDRAGON_SHIELD)
            .useGearType(GearType.MELEE)),


    CAVE_BUG(new Builder()
            .inArea(Areas.CAVE_BUG_AREA)
            .nameContains("Cave bug")
            .setHopPlayerThreshold(2)
            .specialItemID(ItemID.LIT_CANDLE)
            .specialItemID(ItemID.TINDERBOX)
            .useGearType(GearType.MELEE)),

    CAVE_CRAWLER(new Builder()
            .inArea(Areas.CAVE_CRAWLER_AREA)
            .nameContains("Cave crawler")
            .setHopPlayerThreshold(2)

            .specialItemID( new ItemReq.Builder()
                    .id(ItemID.ANTIDOTE_PLUS_PLUS[0])
                    .alternateItemIDs(List.of(ItemID.ANTIDOTE_PLUS_PLUS[1],
                            ItemID.ANTIDOTE_PLUS_PLUS[2], ItemID.ANTIDOTE_PLUS_PLUS[3]))
                    .amount(1)
                    .build())
            .useGearType(GearType.MELEE)),

    CRAWLING_HAND(new Builder()
            .inArea(Areas.CRAWLING_HANDS_AREA)
            .nameContains("Crawling hand")
            .setHopPlayerThreshold(3)
            .useGearType(GearType.MELEE)),

    CAVE_SLIME(new Builder()
            .inArea(Areas.CAVE_SLIME_AREA)
            .nameContains("Cave slime")
            .setHopPlayerThreshold(2)
            .specialItemID( new ItemReq.Builder()
                    .id(ItemID.ANTIDOTE_PLUS_PLUS[0])
                    .alternateItemIDs(List.of(ItemID.ANTIDOTE_PLUS_PLUS[1],
                            ItemID.ANTIDOTE_PLUS_PLUS[2], ItemID.ANTIDOTE_PLUS_PLUS[3]))
                    .amount(1)
                    .build())
            .specialItemID(ItemID.LIT_CANDLE)
            .specialItemID(ItemID.TINDERBOX)
            .useGearType(GearType.MELEE)),

    CAVE_HORROR(new Builder()
            // .inArea(Areas.BRINE_RAT_AREA) //TODO Add this area
            .usingCannon(true)
            //add cannon tile
            .nameContains("Cave horror")
            .useGearType(GearType.MELEE)),

    COCKATRICE(new Builder()
            .inArea(Areas.COCKATRICE_AREA)
            .useSpecialItem(true)
            .setHopPlayerThreshold(2)
            .specialItemID(ItemID.MIRROR_SHIELD)
            .nameContains("Cockatrice")
            .useGearType(GearType.MELEE)),

    COW(new Builder()
            .inArea(Areas.COW_AREA)
            .nameContains("Cow")
            .setHopPlayerThreshold(4)
            .useGearType(GearType.MELEE)),


    CROCODILE(new Builder()
            .inArea(Areas.CROCODILE_AREA)
            .nameContains("Crocodile")
            .useGearType(GearType.MELEE)),

    DAGANNOTH(new Builder()
            .inArea(Areas.DAGGANOTH_AREA)
            .usingCannon(true)
            .useCannonTile(Areas.DAGGANOTH_CANNON_TILE)
            .nameContains("Dagannoth")
            .useGearType(GearType.MELEE)),

    DWARF(new Builder()
            .inArea(Areas.DWARF_AREA)
            .nameContains("Dwarves") // need this to identify task from name
            .nameContains("Dwarf")
            .nameContains("Black Guard")
            .useGearType(GearType.MELEE)),

    DRAKE(new Builder()
            //.inArea(Areas.DAGGANOTH_AREA) //TODO Add this area
            .usingCannon(true)
            .useCannonTile(Areas.DAGGANOTH_CANNON_TILE)
            .nameContains("Drake")
            .useGearType(GearType.MELEE)),

    DUST_DEVIL(new Builder()
           .inArea(Areas.DUST_DEVIL_AREA)
            .nameContains("Dust devil")
            .useSpecialItem(true)
            .specialItemID(ItemID.FACEMASK)
            .useGearType(GearType.MELEE)),



    ELVES(new Builder()
            .inArea(Areas.ELF_AREA)
            .usingCannon(true)
            .useCannonTile(Areas.ELF_CANNON_TILE)
            .nameContains("Elves")
            .nameContains("Iorwerth Archer")
            .nameContains("Iorwerth Warrior")
            .setHopPlayerThreshold(2)
            .useGearType(GearType.MELEE)),

    FIRE_GIANT(new Builder()
            .inArea(Areas.FIRE_GIANT_AREA)
            .usingCannon(true)
            .useCannonTile(Areas.FIRE_GIANT_CANNON_TILE)
            .nameContains("Fire giant")
            .useGearType(GearType.MELEE)),


    GARGOYLE(new Builder()
            .inArea(Areas.GARGOYLE_AREA)
            .useSpecialItem(true)
            .specialItemID(ItemID.ROCK_HAMMER)
            .nameContains("Gargoyle")
            .setHopPlayerThreshold(4)
            .useGearType(GearType.MELEE)),

    GREATER_DEMON(new Builder()
            .inArea(Areas.GREATER_DEMON_AREA)
            .nameContains("Greater demon")
            .usePrayerType(PrayerType.MELEE)
            .useGearType(GearType.MELEE)),

    GREEN_DRAGON(new Builder()
            .inArea(Areas.BABY_GREEN_DRAGON_AREA)
            .nameContains("Green dragon")
            .nameContains("Baby green dragon")
            .useGearType(GearType.MELEE)),

    GHOST(new Builder()
            .inArea(Areas.GHOST_AREA)
            .nameContains("Ghost")
            .useGearType(GearType.MELEE)),


    GHOUL(new Builder()
            .inArea(Areas.GHOUL_AREA)
            .nameContains("Ghoul")
            .setHopPlayerThreshold(2)
            .useGearType(GearType.MELEE)),


    GOBLIN(new Builder()
            .inArea(Areas.GOBLIN_AREA)
            .nameContains("Goblin")
            .setHopPlayerThreshold(3)
            .nameNotContains("Hobgoblin")
            .useGearType(GearType.MELEE)),

    HOBGOBLIN(new Builder()
            .inArea(Areas.HOBGOBLIN_AREA)
            .nameContains("Hobgoblin")
            .setHopPlayerThreshold(2)
            .useGearType(GearType.MELEE)),

    HARPIE_BUG_SWARM(new Builder()
            .inArea(Areas.HARPIE_BUG_SWARM_AREA)
            .nameContains("Harpie Bug Swarm")
            .useSpecialItem(true)
            .setHopPlayerThreshold(2)
            .specialItemID(ItemID.LIT_BUG_LANTERN)
            .useGearType(GearType.MELEE)),

    HELLHOUND(new Builder()
            .inArea(Areas.HELLHOUND_AREA)
            .usingCannon(true)
            .useCannonTile(Areas.HELLHOUND_CANNON_TILE)
            .nameContains("Hellhound")
            .useGearType(GearType.MELEE)),

    HILL_GIANT(new Builder()
            .inArea(Areas.HILL_GIANT_AREA)
            .nameContains("Hill giant")
            .setHopPlayerThreshold(3)
            .useGearType(GearType.MELEE)),

    ICE_WARRIOR(new Builder()
            .inArea(Areas.ICE_WARRIOR_AREA)
            .nameContains("Ice warrior")
            .setHopPlayerThreshold(3)
            .useGearType(GearType.MELEE)),

    ICE_GIANT(new Builder()
            .inArea(Areas.ICE_GIANT_AREA)
            .nameContains("Ice giant")
            .setHopPlayerThreshold(3)
            .useGearType(GearType.MELEE)),

    ICEFIEND(new Builder()
            .inArea(Areas.ICEFIEND_AREA)
            .nameContains("Icefiend")
            .setHopPlayerThreshold(2)
            .useGearType(GearType.MELEE)),
    INFERNAL_MAGE(new Builder()
            .inArea(Areas.INFERNAL_MAGE_AREA)
            .nameContains("Infernal mage")
            .setHopPlayerThreshold(2)
            .usePrayerType(PrayerType.MAGIC)
            .useGearType(GearType.MELEE)),

    JELLY(new Builder()
            .inArea(Areas.JELLY_AREA)
            .nameContains("Jellie")
            .nameContains("Jelly")
            .setHopPlayerThreshold(2)
            .useGearType(GearType.MELEE)),


    IRON_DRAGON(new Builder()
            //  .inArea(Areas.FIRE_GIANT_AREA) //TODO Add this area
            .nameContains("Iron dragon")
            .useGearType(GearType.MELEE)),

    KALPHITE(new Builder()
            .inArea(Areas.KALPHITE_AREA)
            .setHopPlayerThreshold(2)
           // .usingCannon(true)
            //.useCannonTile(Areas.KALPHITE_CANNON_TILE)
            .nameContains("Kalphite Worker")
            .useGearType(GearType.MELEE)),

    KURASK(new Builder()
            .inArea(Areas.KURASK_AREA)
            .setHopPlayerThreshold(3)
            .nameContains("Kurask")
            .useGearType(GearType.MELEE)),

    LESSER_DEMON(new Builder()
            .inArea(Areas.LESSER_DEMON_AREA)
            .nameContains("Lesser demon")
            .useGearType(GearType.MELEE)),

    LIZARD(new Builder()
            .inArea(Areas.LIZARD_AREA)
            .nameContains("Small Lizard")
            .nameContains("Lizard")
            .nameContains("Desert Lizard")
            .nameNotContains("Lizardman")
            .useSpecialItem(true)
            .specialItemID(ItemID.ICE_COOLER)
            .useGearType(GearType.MELEE)),

    LIZARDMAN(new Builder()
            .inArea(Areas.KALPHITE_AREA) //TODO fix
            .usingCannon(true)
            .useCannonTile(Areas.KALPHITE_CANNON_TILE) //TODO fix
            .nameContains("Lizardman")

            .nameContains("Lizardman brute") //TODO CHECK
            .useGearType(GearType.MELEE)),

    LOAR_SHADES(new Builder()
            .inArea(Areas.SHADES_AREA)
            .nameContains("Shade")
            .nameContains("Loar Shade")
            .nameContains("Loar Shadow")
            .setHopPlayerThreshold(3)
            .useGearType(GearType.MELEE)),

    MONKEY(new Builder()
            .inArea(Areas.MONKEY_AREA)
            .nameContains("Monkeys")
            .nameContains("Monkey")
            .useGearType(GearType.MELEE)),

    MOSS_GIANT(new Builder()
            .inArea(Areas.MOSS_GIANT_AREA)
            .nameContains("Moss giant")
            .setHopPlayerThreshold(2)
            .useGearType(GearType.MELEE)),

    MOSS_GIANT_FISHING_GUILD(new Builder()
            .inArea(Areas.MOSS_GIANT_FISHING_GUILD_AREA)
            .nameContains("Moss giant")
            .useGearType(GearType.MELEE)),


    MINOTAUR(new Builder()
            .inArea(Areas.MINOTAUR_AREA)
            .nameContains("Minotaur")
            .useGearType(GearType.MELEE)),

    WALL_BEAST(new Builder()
            .inArea(Areas.WALL_BEAST_AREA)
            .nameContains("Wall beast")
            .setHopPlayerThreshold(2)
            .useGearType(GearType.MELEE)),

    MUTATED_ZYGOMITES(new Builder()
            .inArea(Areas.MUTATED_ZYGOMITE_AREA)
            .useSpecialItem(true)
            .specialItemID(ItemID.FUNGICIDE_SPRAYER[0])
            .nameContains("Mutated zygomite") //TODO CHECK
            .useGearType(GearType.MELEE)),

    OTHERWORLDLY_BEINGS(new Builder()
            .inArea(Areas.OTHERWORLDLY_BEING_AREA)
            .nameContains("Otherworldly being")
            .specialItemID(ItemID.DRAMEN_STAFF)
            .setHopPlayerThreshold(2)
            .useGearType(GearType.MELEE)),

    NECHRYAEL(new Builder()
            //  .inArea(Areas.) //TODO Add this area
            .nameContains("Nechryael")
            .setHopPlayerThreshold(3)
            .useGearType(GearType.MELEE)),

    RATS(new Builder()
            .inArea(Areas.RAT_AREA)
            .nameContains("Giant rat")
            .nameNotContains("Brine")
            .setHopPlayerThreshold(3)
            .useGearType(GearType.MELEE)),

    BRINE_RAT(new Builder()
            .inArea(Areas.BRINE_RAT_AREA)
            .nameContains("Brine rat")
            .useGearType(GearType.MELEE)),

    RED_DRAGONS(new Builder()
            //  .inArea(Areas.) //TODO Add this area
            .nameContains("Red dragon")
            .useGearType(GearType.MELEE)),

    ROCKSLUG(new Builder()
            .inArea(Areas.ROCKSLUG_AREA)
            .nameContains("Rockslug")
            .useSpecialItem(true)
            .setHopPlayerThreshold(2)
            .specialItemID(ItemID.BAG_OF_SALT)
            .useGearType(GearType.MELEE)),


    RUNE_DRAGON(new Builder()
            //  .inArea(Areas.)
            .nameContains("Rune dragon") //TODO CHECK
            .useGearType(GearType.MELEE)),

    SCARAB_MAGES(new Builder()
            // .inArea(Areas.)
            .nameContains("minions of Scabara") // for gem check
            .nameContains("Scarab mage") //TODO CHECK
            .usePrayerType(PrayerType.MAGIC)
            .useGearType(GearType.RANGED)),


    SCORPIONS(new Builder()
            .inArea(Areas.SCORPION_AREA)
            .nameContains("Scorpion")
            .setHopPlayerThreshold(3)
            .useGearType(GearType.MELEE)),

    SKELETONS(new Builder()
            .inArea(Areas.SKELETON_AREA)
            .nameContains("Skeleton")
            .useGearType(GearType.MELEE)),

    SOURHOGS(new Builder()
            .inArea(Areas.SOURHOG_AREA)
            .nameContains("Sourhog")
            .setHopPlayerThreshold(2)
            .useGearType(GearType.MELEE)),

    SPIDERS(new Builder()
            .inArea(Areas.SPIDER_AREA)
            .nameContains("Spider")
            .setHopPlayerThreshold(3)
            .nameContains("Giant spider")
            .useGearType(GearType.MELEE)),

    SMOKE_DEVIL(new Builder()
            //  .inArea(Areas.)
            .nameContains("Smoke devil") //TODO CHECK
            .useGearType(GearType.MELEE)),

    SPIRITUAL_CREATURE(new Builder()
           // .inArea(Areas.SPIRI) //TODO Add this area
            .nameContains("Spirita")
            .useGearType(GearType.MELEE)),

    STEEL_DRAGON(new Builder()
            //  .inArea(Areas.) //TODO add area
            .nameContains("Steel dragon")
            .useGearType(GearType.MAGIC)),

    SUQAH(new Builder()
            .inArea(Areas.SUQAH_AREA)
            .usingCannon(true)
            .usePrayerType(PrayerType.MAGIC)
            .useCannonTile(Areas.SUQAH_CANNON_TILE)
            .nameContains("Suqah")
            .useGearType(GearType.MELEE)),

    DOG(new Builder()
            .inArea(Areas.JACKAL_AREA)
            .nameContains("Guard dog") // need this to identify task from name
            .nameContains("Jackal")
            .useGearType(GearType.MELEE)),


    WOLVES(new Builder()
            .inArea(Areas.WOLF_AREA)
            .nameContains("Wolves") // need this to identify task from name
            .nameContains("Wolf")
            .useGearType(GearType.MELEE)),

    VAMPYRE(new Builder()
            .inArea(Areas.VAMPYRE_AREA)
            .nameContains("Vampyre") // need this to identify task from name
            .nameContains("Feral Vampyre")
            .useGearType(GearType.MELEE)),

    TROLL(new Builder()
            .inArea(Areas.TROLL_AREA)
            .nameContains("Mountain troll")
            .setHopPlayerThreshold(3)
            .useGearType(GearType.MELEE)),

    TUROTH(new Builder()
            .inArea(Areas.TUROTH_AREA)
            .nameContains("Turoth")
            .setHopPlayerThreshold(3)
            .useGearType(GearType.MELEE)),

    ORGRE(new Builder()
            .inArea(Areas.OGRE_AREA)
            .nameContains("Ogre")
            .useGearType(GearType.MELEE)),

    PYREFIEND(new Builder()
            .inArea(Areas.PYREFIEND_AREA)
            .nameContains("Pyrefiend")
            .setHopPlayerThreshold(3)
            .useGearType(GearType.MELEE)),

    WYRM(new Builder()
            .inArea(Areas.WYRM_AREA)
            .nameContains("Wyrm")
            .specialItemID(ItemID.BOOTS_OF_STONE)
            .setHopPlayerThreshold(3)
            .useHopTile(Areas.WYRM_HOP_TILE)
            .useGearType(GearType.MELEE)),

    ZOMBIE(new Builder()
            .inArea(Areas.ZOMBIE_AREA)
            .nameContains("Zombie")
            .useGearType(GearType.MELEE));
    @Getter
    private Area area;

    @Getter
    private boolean useCannon = false;

    @Getter
    private PrayerType prayerType = PrayerType.NONE;

    @Getter
    private boolean useSpecialItem = false;
    @Getter
    private WorldTile cannonTile = null;

    @Getter
    private List<String> nameList;


    @Getter
    private List<String> nameNotContainsList;



    @Getter
    private List<Integer> customGearList;

    @Getter
    private int specialItemID = -1;

    @Getter
    private List<ItemReq> specialItemList = new ArrayList<>();

    @Getter
    private GearType gearType = GearType.MELEE;

    @Getter
    private WorldTile hopTile = MyPlayer.getTile();

    @Getter
    private int hopPlayerThreshold = 1;

    private Assign(Builder builder) {
        this.area = builder.area;
        this.useCannon = builder.useCannon;
        this.prayerType = builder.prayerType;
        this.useSpecialItem = builder.useSpecialItem;
        this.cannonTile = builder.cannonTile;
        this.nameList = builder.nameList;
        this.specialItemID = builder.specialItemID;
        this.specialItemList = builder.specialItemList;
        this.gearType = builder.gearType;
        this.hopTile = builder.hopTile;
        this.customGearList = builder.customGearList;
        this.nameNotContainsList = builder.nameNotContainsList;
        this.hopPlayerThreshold = builder.hopPlayerThreshold;
    }


    public static class Builder {
        private Area area;
        private boolean useCannon = false;
        private PrayerType prayerType = PrayerType.NONE;
        private boolean useSpecialItem = false;
        private WorldTile cannonTile;
        private List<String> nameList;
        private List<String> nameNotContainsList;
        private int specialItemID;
        private  List<ItemReq> specialItemList = new ArrayList<>();
        public List<String> specialItemsList;
        private int hopPlayerThreshold = 1;

        private GearType gearType;
        private WorldTile hopTile = MyPlayer.getTile();
        private List<Integer> customGearList;

        public Builder inArea(Area area) {
            this.area = area;
            return this;
        }

        public Builder addCustomGear(List<Integer> customGearList) {
            this.customGearList = customGearList;
            return this;
        }

        public Builder usingCannon(boolean use) {
            this.useCannon = use;

            return this;
        }

        public Builder usePrayerType(PrayerType prayerType) {
            this.prayerType = prayerType;
            return this;
        }

        public Builder useSpecialItem(boolean useSpecialItem) {
            this.useSpecialItem = useSpecialItem;
            return this;
        }

        public Builder specialItemID(int specialItemID) {
            this.useSpecialItem = true;
            this.specialItemID = specialItemID;
            return this;
        }
        public Builder specialItemID(ItemReq... specialItemID) {
            this.useSpecialItem = true;
            this.specialItemList = List.of(specialItemID);
            return this;
        }
        public Builder useCannonTile(WorldTile tile) {
            this.cannonTile = tile;
            return this;
        }

        public Builder nameContains(String npcName) {
            if (this.nameList == null)
                this.nameList = new ArrayList<>();

            this.nameList.add(npcName);

            return this;
        }

        public Builder nameNotContains(String npcName) {
            if (this.nameNotContainsList == null)
                this.nameNotContainsList = new ArrayList<>();

            this.nameNotContainsList.add(npcName);

            return this;
        }


        public Builder useHopTile(WorldTile tile) {
            this.hopTile = tile;
            return this;
        }
        public Builder setHopPlayerThreshold(int players) {
            this.hopPlayerThreshold = players;
            return this;
        }
        public Builder useGearType(GearType gearType) {
            this.gearType = gearType;
            return this;
        }

    }





}