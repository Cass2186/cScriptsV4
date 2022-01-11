package scripts.Data;

import lombok.Getter;
import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSTile;
import scripts.ItemId;
import scripts.PrayerType;
import scripts.PrayerUtil;

import java.util.Collections;
import java.util.List;

public enum Assign {

    ABBERANT_SPECTRES(new Assign.Builder()
            .inArea(Areas.ABBERANT_SPECTRES_AREA)
            .usingCannon(true)
            .useCannonTile(Areas.ABBERANT_SPECTRES_AREA.getRandomTile())
            .usePrayerType(PrayerType.MAGIC)
            .nameContains("Abberant spectre")
            .useGearType(GearType.MAGIC_MELEE)),

    ABYSSAL_DEMON(new Assign.Builder()
            .inArea(Areas.ABYSSAL_DEMON_AREA)
            .usingCannon(false)
            .nameContains("Abyssal demon")
            .useGearType(GearType.MELEE)),

    ADAMANT_DRAGON(new Assign.Builder()
            // .inArea(Areas.BASILISK_AREA)
            .usingCannon(false)
            .nameContains("Adamant dragon")
            .useSpecialItem(true)
            .specialItemId(ItemId.ANTI_DRAGON_SHIELD)
            .useGearType(GearType.MELEE)),

    ANKOU(new Assign.Builder()
            .inArea(Areas.ANKOU_AREA)
            .usingCannon(false)
            .nameContains("Ankou")
            .useGearType(GearType.MELEE)),

    AVIANSIE(new Assign.Builder()
            //  .inArea(Areas.)
            .usingCannon(false)
            .nameContains("Aviansie")
            .useGearType(GearType.RANGED)),

    BANSHEE(new Assign.Builder()
            .inArea(Areas.BANSHEE_AREA)
            .usingCannon(false)
            .nameContains("Banshee")
            .useSpecialItem(true)
            .specialItemId(ItemId.EAR_MUFFS)
            .useGearType(GearType.MELEE)),

    BASILISK(new Assign.Builder()
            .inArea(Areas.BASILISK_AREA)
            .usingCannon(false)
            .nameContains("Basilisk")
            .useSpecialItem(true)
            .specialItemId(ItemId.MIRROR_SHIELD)
            .useGearType(GearType.MELEE)),


    BLACK_DRAGON(new Assign.Builder()
            .inArea(Areas.BABY_BLACK_DRAGON_AREA)
            .usingCannon(false)
            .nameContains("Baby black dragon")
            .useSpecialItem(true)
            .specialItemId(ItemId.ANTIDRAGON_SHIELD)
            .useGearType(GearType.MELEE)),

    BLACK_DEMON(new Assign.Builder()
            .inArea(Areas.BLACK_DEMON_AREA)
            .usingCannon(false)
            .nameContains("Black demon")
            .usePrayerType(PrayerType.MELEE)
            .useGearType(GearType.MELEE)),

    BLOODVELD(new Assign.Builder()
            .inArea(Areas.BLOODVELD_AREA)
            .usingCannon(true)
            .useCannonTile(Areas.BLOODVELD_CANNON_TILE)
            .nameContains("Bloodveld")
            .useGearType(GearType.MAGIC_MELEE)),

    BLUE_DRAGON(new Assign.Builder()
            .inArea(Areas.BLUE_DRAGON_AREA)
            .usingCannon(true)
            .useCannonTile(Areas.BLUE_DRAGON_CANNON_TILE)
            .nameContains("Baby blue dragon")
            .useSpecialItem(true)
            .specialItemId(ItemId.ANTI_DRAGON_SHIELD)
            .useGearType(GearType.MELEE)),

    BRINE_RAT(new Assign.Builder()
            .inArea(Areas.BRINE_RAT_AREA)
            .usingCannon(false)
            .nameContains("Brine rat")
            .useGearType(GearType.MELEE)),

    CAVE_HORROR(new Assign.Builder()
            // .inArea(Areas.BRINE_RAT_AREA)
            .usingCannon(true)
            //add cannon tile
            .nameContains("Cave horror")
            .useGearType(GearType.MELEE)),

    DAGANNOTH(new Builder()
            .inArea(Areas.DAGGANOTH_AREA)
            .usingCannon(true)
            .useCannonTile(Areas.DAGGANOTH_CANNON_TILE)
            .nameContains("Dagannoth")
            .useGearType(GearType.MELEE)),

    DARK_BEAST(new Builder()
            // .inArea(Areas.DAGGANOTH_AREA)
            //    .usingCannon(true)
            //   .useCannonTile(Areas.DAGGANOTH_CANNON_TILE)
            //  .nameContains("Dagannoth")
            .useGearType(GearType.MELEE)),

    DRAKE(new Builder()
            //.inArea(Areas.DAGGANOTH_AREA)
            .usingCannon(true)
            .useCannonTile(Areas.DAGGANOTH_CANNON_TILE)
            .nameContains("Drake")
            .useGearType(GearType.MELEE)),

    DUST_DEVIL(new Builder()
            // .inArea(Areas)
            // .usingCannon(true)
            //  .useCannonTile(Areas.DAGGANOTH_CANNON_TILE)
            .nameContains("Dust devil")
            .useGearType(GearType.MELEE)),

    ELVES(new Builder()
            .inArea(Areas.ELF_AREA)
            .usingCannon(true)
            .useCannonTile(Areas.ELF_CANNON_TILE)
            .nameContains("Iorwerth Archer")
            .nameContains("Iorwerth Warrior")
            .useGearType(GearType.MELEE)),

    FIRE_GIANT(new Builder()
            .inArea(Areas.FIRE_GIANT_AREA)
            .usingCannon(true)
            .useCannonTile(Areas.FIRE_GIANT_CANNON_TILE)
            .nameContains("Fire giant")
            .useGearType(GearType.MELEE)),
    LIZARD(new Builder()
            .inArea(Areas.LIZARD_AREA)
            .nameContains("Small Lizard")
            .nameContains("Lizard")
            .nameContains("Desert Lizard")
            .useGearType(GearType.MELEE)),

    GARGOYLE(new Builder()
            .inArea(Areas.GARGOYLE_AREA)
            .usingCannon(false)
            .useSpecialItem(true)
            .specialItemId(ItemId.ROCK_HAMMER)
            .nameContains("Gargoyle")
            .useGearType(GearType.MELEE)),

    COCKATRICE(new Builder()
            .inArea(Areas.COCKATRICE_AREA)
            .useSpecialItem(true)
            .specialItemId(ItemId.MIRROR_SHIELD)
            .nameContains("Cockatrice")
            .useGearType(GearType.MELEE)),

    CAVE_CRAWLER(new Builder()
            .inArea(Areas.CAVE_CRAWLER_AREA)
            .nameContains("Cave crawler")
            .useGearType(GearType.MELEE)),

    CAVE_SLIME(new Builder()
            .inArea(Areas.CAVE_SLIME_AREA)
            .nameContains("Cave slime")
            .useGearType(GearType.MELEE)),

    CROCODILE(new Builder()
            .inArea(Areas.CROCODILE_AREA)
            .nameContains("Crocodile")
            .useGearType(GearType.MELEE)),

    HILL_GIANT(new Builder()
            .inArea(Areas.HILL_GIANT_AREA)
            .nameContains("Hill giant")
            .useGearType(GearType.MELEE)),

    JELLY(new Builder()
            .inArea(Areas.JELLY_AREA)
            .nameContains("Jelly")
            .useGearType(GearType.MELEE)),

    LESSER_DEMON(new Builder()
            .inArea(Areas.LESSER_DEMON_AREA)
            .nameContains("Lesser demon")
            .useGearType(GearType.MELEE)),

    ROCKSLUG(new Builder()
            .inArea(Areas.ROCKSLUG_AREA)
            .nameContains("Rockslug")
            .useSpecialItem(true)
            .specialItemId(ItemId.BAG_OF_SALT)
            .useGearType(GearType.MELEE)),

    GREATER_DEMON(new Builder()
            .inArea(Areas.GREATER_DEMON_AREA)
            .nameContains("Greater demon")
            .usePrayerType(PrayerType.MELEE)
            .useGearType(GearType.MELEE)),

    HELLHOUND(new Builder()
            .inArea(Areas.HELLHOUND_AREA)
            .usingCannon(true)
            .useCannonTile(Areas.HELLHOUND_CANNON_TILE)
            .nameContains("Hellhound")
            .useGearType(GearType.MELEE)),

    IRON_DRAGON(new Builder()
            //  .inArea(Areas.FIRE_GIANT_AREA)
            //     .nameContains("Fire giant")
            .useGearType(GearType.MELEE)),

    KALPHITE(new Builder()
            .inArea(Areas.KALPHITE_AREA)
            .usingCannon(true)
            .useCannonTile(Areas.KALPHITE_CANNON_TILE)
            .nameContains("Kalphite warrior") //TODO CHECK
            .useGearType(GearType.MELEE)),

    KURASK(new Builder()
            .inArea(Areas.KURASK_AREA)
            .nameContains("Kurask")
            .useGearType(GearType.MELEE)),

    HARPIE_BUG_SWARM(new Builder()
            .inArea(Areas.HARPIE_BUG_SWARM_AREA)
            .nameContains("Harpie Bug Swarm")
            .useSpecialItem(true)
            .specialItemId(ItemId.LIT_BUG_LANTERN)
            .useGearType(GearType.MELEE)),

    LIZARDMAN(new Builder()
            .inArea(Areas.KALPHITE_AREA)
            .usingCannon(true)
            .useCannonTile(Areas.KALPHITE_CANNON_TILE)
            .nameContains("Kalphite warrior") //TODO CHECK
            .useGearType(GearType.MELEE)),

    SCARAB_MAGES(new Builder()
            //  .inArea(Areas.)
            .nameContains("Scarab mage") //TODO CHECK
            .usePrayerType(PrayerType.MAGIC)
            .useGearType(GearType.RANGED)),

    MUTATED_ZYGOMITES(new Builder()
            .inArea(Areas.MUTATED_ZYGOMITE_AREA)
            .useSpecialItem(true)
            .specialItemId(ItemId.FUNGICIDE_SPRAYER[0])
            .nameContains("Mutated zygomite") //TODO CHECK
            .useGearType(GearType.MELEE)),

    NECHRYAEL(new Builder()
            //  .inArea(Areas.)
            .nameContains("Nechryael")
            .useGearType(GearType.MELEE)),

    RED_DRAGONS(new Builder()
            //  .inArea(Areas.)
            .nameContains("Red dragon")
            .useGearType(GearType.MELEE)),

    RUNE_DRAGON(new Builder()
            //  .inArea(Areas.)
            .nameContains("Rune dragon") //TODO CHECK
            .useGearType(GearType.MELEE)),

    SMOKE_DEVIL(new Builder()
            //  .inArea(Areas.)
            .nameContains("Smoke devil") //TODO CHECK
            .useGearType(GearType.MELEE)),

    SPIRITUAL_CREATURE(new Builder()
            //  .inArea(Areas.)
            .nameContains("Kalphite Worker") //TODO CHECK
            .useGearType(GearType.MELEE)),

    STEEL_DRAGON(new Builder()
            //  .inArea(Areas.)
            .nameContains("Steel dragon")
            .useGearType(GearType.MAGIC)),

    SUQAH(new Builder()
            .inArea(Areas.SUQAH_AREA)
            .usingCannon(true)
            .useCannonTile(Areas.SUQAH_CANNON_TILE)
            .nameContains("Suqah")
            .useGearType(GearType.MELEE)),

    TROLL(new Builder()
            .inArea(Areas.TROLL_AREA)
            .nameContains("Mountain troll")
            .useGearType(GearType.MELEE)),

    TUROTH(new Builder()
            .inArea(Areas.TUROTH_AREA)
            .nameContains("Turoth") //TODO CHECK
            .useGearType(GearType.MELEE)),

    WYRM(new Builder()
            .inArea(Areas.WYRM_AREA)
            .nameContains("Wyrm") //TODO CHECK
            .useGearType(GearType.MELEE));
    @Getter
    private RSArea area;
    @Getter
    private boolean useCannon = false;
    @Getter
    private
    PrayerType prayerType = PrayerType.NONE;
    @Getter
    private boolean useSpecialItem = false;
    @Getter
    private RSTile cannonTile = null;

    @Getter
    private List<String> nameList;

    @Getter
    private List<Integer> customGearList;

    @Getter
    private int specialItemId = -1;
    @Getter
    private GearType gearType = GearType.MELEE;

    @Getter
    private RSTile hopTile;

    private Assign(Builder builder) {
        this.area = builder.area;
        this.useCannon = builder.useCannon;
        this.prayerType = builder.prayerType;
        this.useSpecialItem = builder.useSpecialItem;
        this.cannonTile = builder.cannonTile;
        this.nameList = builder.nameList;
        this.specialItemId = builder.specialItemId;
        this.gearType = builder.gearType;
        this.hopTile = builder.hopTile;
        this.customGearList = builder.customGearList;
    }


    public static class Builder {
        private RSArea area;
        private boolean useCannon;
        private PrayerType prayerType;
        private boolean useSpecialItem;
        private RSTile cannonTile;
        private List<String> nameList;
        private int specialItemId;
        private GearType gearType;
        private RSTile hopTile;
        private List<Integer> customGearList;

        public Builder inArea(RSArea area) {
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

        public Builder specialItemId(int specialItemId) {
            this.specialItemId = specialItemId;
            return this;
        }

        public Builder useCannonTile(RSTile tile) {
            this.cannonTile = tile;
            return this;
        }

        public Builder nameContains(String npcName) {
            if (this.nameList == null)
                this.nameList = Collections.singletonList(npcName);
            else
                this.nameList.add(npcName);

            return this;
        }

        public Builder useHopTile(RSTile tile) {
            this.hopTile = tile;
            return this;
        }

        public Builder useGearType(GearType gearType) {
            this.gearType = gearType;
            return this;
        }

    }
}