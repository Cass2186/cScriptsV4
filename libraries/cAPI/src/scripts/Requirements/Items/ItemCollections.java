package scripts.Requirements.Items;

import com.google.common.collect.ImmutableList;
import lombok.Getter;
import scripts.ItemID;

import java.util.List;
import com.google.common.collect.ImmutableList;
import java.util.List;
import lombok.Getter;

/**
 * These are useful item collections.
 * These lists are ORDERED.
 * Order items from highest tier to lowest tier,
 * or highest dose to lowest dose, etc.
 * As long as the list is consistent in how it is ordered.
 */
public class ItemCollections {

    // Tools

    @Getter
    private static final List<Integer> axes = ImmutableList.of(
            ItemID.CRYSTAL_AXE,
            ItemID._3RD_AGE_AXE,
            ItemID.TRAILBLAZER_AXE,
            ItemID.INFERNAL_AXE_OR,
            ItemID.INFERNAL_AXE,
            ItemID.DRAGON_AXE_OR,
            ItemID.DRAGON_AXE,
            ItemID.RUNE_AXE,
            ItemID.GILDED_AXE,
            ItemID.ADAMANT_AXE,
            ItemID.MITHRIL_AXE,
            ItemID.BLACK_AXE,
            ItemID.STEEL_AXE,
            ItemID.IRON_AXE,
            ItemID.BRONZE_AXE
    );

    @Getter
    private static final List<Integer> pickaxes = ImmutableList.of(
            ItemID.INFERNAL_PICKAXE_OR,
            ItemID.TRAILBLAZER_PICKAXE,
            ItemID.CRYSTAL_PICKAXE,
            ItemID.CRYSTAL_PICKAXE_INACTIVE,
            ItemID._3RD_AGE_PICKAXE,
            ItemID.INFERNAL_PICKAXE,
            ItemID.DRAGON_PICKAXE_OR_25376,
            ItemID.DRAGON_PICKAXE_12797,
            ItemID.DRAGON_PICKAXE_OR,
            ItemID.DRAGON_PICKAXE,
            ItemID.RUNE_PICKAXE,
            ItemID.GILDED_PICKAXE,
            ItemID.ADAMANT_PICKAXE,
            ItemID.MITHRIL_PICKAXE,
            ItemID.BLACK_PICKAXE,
            ItemID.STEEL_PICKAXE,
            ItemID.IRON_PICKAXE,
            ItemID.BRONZE_PICKAXE
    );

    @Getter
    private static final List<Integer> harpoons = ImmutableList.of(
            ItemID.INFERNAL_HARPOON_OR,
            ItemID.TRAILBLAZER_HARPOON,
            ItemID.INFERNAL_HARPOON,
            ItemID.DRAGON_HARPOON,
            ItemID.BARBTAIL_HARPOON,
            ItemID.HARPOON
    );

    @Getter
    private static final List<Integer> machete = ImmutableList.of(
            ItemID.RED_TOPAZ_MACHETE,
            ItemID.JADE_MACHETE,
            ItemID.OPAL_MACHETE,
            ItemID.MACHETE
    );

    @Getter
    private static final List<Integer> hammer = ImmutableList.of(
            ItemID.HAMMER,
            ItemID.IMCANDO_HAMMER
    );

    @Getter
    private static final List<Integer> saw = ImmutableList.of(
            ItemID.SAW,
            ItemID.AMYS_SAW,
            ItemID.CRYSTAL_SAW
    );

    @Getter
    private static final List<Integer> nails = ImmutableList.of(
            ItemID.STEEL_NAILS,
            ItemID.IRON_NAILS,
            ItemID.BRONZE_NAILS,
            ItemID.BLACK_NAILS,
            ItemID.MITHRIL_NAILS,
            ItemID.ADAMANTITE_NAILS,
            ItemID.RUNE_NAILS
    );

    @Getter
    private static final List<Integer> bows = ImmutableList.of(
            ItemID.MAGIC_SHORTBOW,
            ItemID.MAGIC_SHORTBOW_I,
            ItemID.DARK_BOW,
            ItemID.MAGIC_LONGBOW,
            ItemID.YEW_SHORTBOW,
            ItemID.YEW_LONGBOW,
            ItemID.MAPLE_SHORTBOW,
            ItemID.MAPLE_LONGBOW,
            ItemID.WILLOW_SHORTBOW,
            ItemID.WILLOW_LONGBOW,
            ItemID.OAK_SHORTBOW,
            ItemID.OAK_LONGBOW,
            ItemID.SHORTBOW,
            ItemID.LONGBOW
    );

    @Getter
    private static final List<Integer> crossbows = ImmutableList.of(
            ItemID.ARMADYL_CROSSBOW,
            ItemID.DRAGON_HUNTER_CROSSBOW,
            ItemID.KARILS_CROSSBOW,
            ItemID.KARILS_CROSSBOW_100,
            ItemID.KARILS_CROSSBOW_75,
            ItemID.KARILS_CROSSBOW_50,
            ItemID.KARILS_CROSSBOW_25,
            ItemID.HUNTERS_CROSSBOW,
            ItemID.DORGESHUUN_CROSSBOW,
            ItemID.BLURITE_CROSSBOW,
            ItemID.DRAGON_CROSSBOW,
            ItemID.RUNE_CROSSBOW,
            ItemID.ADAMANT_CROSSBOW,
            ItemID.MITHRIL_CROSSBOW,
            ItemID.STEEL_CROSSBOW,
            ItemID.IRON_CROSSBOW,
            ItemID.BRONZE_CROSSBOW,
            ItemID.PHOENIX_CROSSBOW,
            ItemID.CROSSBOW
    );

    @Getter
    private static final List<Integer> swords = ImmutableList.of(
            ItemID.BRONZE_SWORD,
            ItemID.BRONZE_LONGSWORD,
            ItemID.IRON_SWORD,
            ItemID.IRON_LONGSWORD,
            ItemID.STEEL_SWORD,
            ItemID.STEEL_LONGSWORD,
            ItemID.BLACK_SWORD,
            ItemID.BLACK_LONGSWORD,
            ItemID.WHITE_SWORD,
            ItemID.WHITE_LONGSWORD,
            ItemID.MITHRIL_SWORD,
            ItemID.MITHRIL_LONGSWORD,
            ItemID.ADAMANT_SWORD,
            ItemID.ADAMANT_LONGSWORD,
            ItemID.WILDERNESS_SWORD_1,
            ItemID.WILDERNESS_SWORD_2,
            ItemID.WILDERNESS_SWORD_3,
            ItemID.WILDERNESS_SWORD_4
    );

    // Teleports

    @Getter
    private static final List<Integer> metalArrows = ImmutableList.of(
            ItemID.RUNE_ARROW,
            ItemID.ADAMANT_ARROW,
            ItemID.MITHRIL_ARROW,
            ItemID.STEEL_ARROW,
            ItemID.IRON_ARROW,
            ItemID.BRONZE_ARROW
    );

    @Getter
    private static final List<Integer> arrows = ImmutableList.of(
            ItemID.DRAGON_ARROW,
            ItemID.AMETHYST_ARROW,
            ItemID.RUNE_ARROW,
            ItemID.ADAMANT_ARROW,
            ItemID.MITHRIL_ARROW,
            ItemID.STEEL_ARROW,
            ItemID.IRON_ARROW,
            ItemID.BRONZE_ARROW
    );

    @Getter
    private static final List<Integer> brutalArrows = ImmutableList.of(
            ItemID.RUNE_BRUTAL,
            ItemID.ADAMANT_BRUTAL,
            ItemID.MITHRIL_BRUTAL,
            ItemID.BLACK_BRUTAL,
            ItemID.STEEL_BRUTAL,
            ItemID.IRON_BRUTAL,
            ItemID.BRONZE_BRUTAL
    );

    @Getter
    private static final List<Integer> fireArrows = ImmutableList.of(
            ItemID.DRAGON_FIRE_ARROW,
            ItemID.DRAGON_FIRE_ARROW_LIT,
            ItemID.AMETHYST_FIRE_ARROW,
            ItemID.AMETHYST_FIRE_ARROW_LIT,
            ItemID.RUNE_FIRE_ARROW,
            ItemID.RUNE_FIRE_ARROW_LIT,
            ItemID.ADAMANT_FIRE_ARROW,
            ItemID.ADAMANT_FIRE_ARROW_LIT,
            ItemID.MITHRIL_FIRE_ARROW,
            ItemID.MITHRIL_FIRE_ARROW_LIT,
            ItemID.STEEL_FIRE_ARROW,
            ItemID.STEEL_FIRE_ARROW_LIT,
            ItemID.IRON_FIRE_ARROW,
            ItemID.IRON_FIRE_ARROW_LIT,
            ItemID.BRONZE_FIRE_ARROW,
            ItemID.BRONZE_FIRE_ARROW_LIT
    );

    @Getter
    private static final List<Integer> specialArrows = ImmutableList.of(
            ItemID.BROAD_ARROWS,
            ItemID.OGRE_ARROW,
            ItemID.TRAINING_ARROWS,
            ItemID.ICE_ARROWS
    );

    @Getter
    private static final List<Integer> arrowtips = ImmutableList.of(
            ItemID.DRAGON_ARROWTIPS,
            ItemID.AMETHYST_ARROWTIPS,
            ItemID.RUNE_ARROWTIPS,
            ItemID.ADAMANT_ARROWTIPS,
            ItemID.MITHRIL_ARROWTIPS,
            ItemID.STEEL_ARROWTIPS,
            ItemID.IRON_ARROWTIPS,
            ItemID.BRONZE_ARROWTIPS
    );

    @Getter
    private static final List<Integer> airRune = ImmutableList.of(
            ItemID.AIR_RUNE,
            ItemID.MIST_RUNE,
            ItemID.SMOKE_RUNE,
            ItemID.DUST_RUNE
    );

    @Getter
    private static final List<Integer> airStaff = ImmutableList.of(
            ItemID.AIR_BATTLESTAFF,
            ItemID.MYSTIC_AIR_STAFF,
            ItemID.STAFF_OF_AIR,
            ItemID.SMOKE_BATTLESTAFF,
            ItemID.MYSTIC_SMOKE_STAFF,
            ItemID.DUST_BATTLESTAFF,
            ItemID.MYSTIC_DUST_STAFF,
            ItemID.MIST_BATTLESTAFF,
            ItemID.MYSTIC_MIST_STAFF
    );

    @Getter
    private static final List<Integer> fireRune = ImmutableList.of(
            ItemID.FIRE_RUNE,
            ItemID.LAVA_RUNE,
            ItemID.SMOKE_RUNE,
            ItemID.STEAM_RUNE
    );

    @Getter
    private static final List<Integer> fireStaff = ImmutableList.of(
            ItemID.FIRE_BATTLESTAFF,
            ItemID.MYSTIC_FIRE_STAFF,
            ItemID.STAFF_OF_FIRE,
            ItemID.SMOKE_BATTLESTAFF,
            ItemID.MYSTIC_SMOKE_STAFF,
            ItemID.LAVA_BATTLESTAFF,
            ItemID.MYSTIC_LAVA_STAFF,
            ItemID.STEAM_BATTLESTAFF,
            ItemID.MYSTIC_STEAM_STAFF
    );

    @Getter
    private static final List<Integer> waterRune = ImmutableList.of(
            ItemID.WATER_RUNE,
            ItemID.MUD_RUNE,
            ItemID.MIST_RUNE,
            ItemID.STEAM_RUNE
    );

    @Getter
    private static final List<Integer> waterStaff = ImmutableList.of(
            ItemID.FIRE_BATTLESTAFF,
            ItemID.MYSTIC_FIRE_STAFF,
            ItemID.STAFF_OF_FIRE,
            ItemID.MUD_BATTLESTAFF,
            ItemID.MYSTIC_MUD_STAFF,
            ItemID.MIST_BATTLESTAFF,
            ItemID.MYSTIC_MIST_STAFF,
            ItemID.STEAM_BATTLESTAFF,
            ItemID.MYSTIC_STEAM_STAFF
    );

    @Getter
    private static final List<Integer> earthRune = ImmutableList.of(
            ItemID.EARTH_RUNE,
            ItemID.MUD_RUNE,
            ItemID.LAVA_RUNE,
            ItemID.DUST_RUNE
    );

    @Getter
    private static final List<Integer> earthStaff = ImmutableList.of(
            ItemID.EARTH_BATTLESTAFF,
            ItemID.MYSTIC_EARTH_STAFF,
            ItemID.STAFF_OF_EARTH,
            ItemID.MUD_BATTLESTAFF,
            ItemID.MYSTIC_MUD_STAFF,
            ItemID.DUST_BATTLESTAFF,
            ItemID.MYSTIC_DUST_STAFF,
            ItemID.LAVA_BATTLESTAFF,
            ItemID.MYSTIC_LAVA_STAFF
    );

    // Potions

    @Getter
    private static final List<Integer> antipoisons = ImmutableList.of(
            ItemID.ANTIVENOM4_12913,
            ItemID.ANTIVENOM3_12915,
            ItemID.ANTIVENOM4_12913,
            ItemID.ANTIVENOM2_12917,
            ItemID.ANTIVENOM1_12919,
            ItemID.ANTIVENOM4,
            ItemID.ANTIVENOM3,
            ItemID.ANTIVENOM2,
            ItemID.ANTIVENOM1,
            ItemID.ANTIDOTE4_5952,
            ItemID.ANTIDOTE3_5954,
            ItemID.ANTIDOTE2_5956,
            ItemID.ANTIDOTE1_5958,
            ItemID.ANTIDOTE4,
            ItemID.ANTIDOTE3,
            ItemID.ANTIDOTE2,
            ItemID.ANTIDOTE1,
            ItemID.SUPERANTIPOISON4,
            ItemID.SUPERANTIPOISON3,
            ItemID.SUPERANTIPOISON2,
            ItemID.SUPERANTIPOISON1,
            ItemID.ANTIPOISON4,
            ItemID.ANTIPOISON3,
            ItemID.ANTIPOISON2,
            ItemID.ANTIPOISON1,
            ItemID.RELICYMS_BALM4,
            ItemID.RELICYMS_BALM3,
            ItemID.RELICYMS_BALM2,
            ItemID.RELICYMS_BALM1,
            ItemID.SANFEW_SERUM4,
            ItemID.SANFEW_SERUM3,
            ItemID.SANFEW_SERUM2,
            ItemID.SANFEW_SERUM1,
            ItemID.RELICYMS_MIX2,
            ItemID.RELICYMS_MIX1,
            ItemID.ANTIPOISON_MIX2,
            ItemID.ANTIPOISON_MIX1,
            ItemID.ANTIDOTE_MIX2,
            ItemID.ANTIDOTE_MIX1
    );

    @Getter
    private static final List<Integer> antivenoms = ImmutableList.of(
            ItemID.ANTIVENOM4_12913,
            ItemID.ANTIVENOM3_12915,
            ItemID.ANTIVENOM2_12917,
            ItemID.ANTIVENOM1_12919,
            ItemID.ANTIVENOM4,
            ItemID.ANTIVENOM3,
            ItemID.ANTIVENOM2,
            ItemID.ANTIVENOM1
    );

    @Getter
    private static final List<Integer> antidisease = ImmutableList.of(
            ItemID.INOCULATION_BRACELET,
            ItemID.RELICYMS_BALM4,
            ItemID.RELICYMS_BALM3,
            ItemID.RELICYMS_BALM2,
            ItemID.RELICYMS_BALM1,
            ItemID.SANFEW_SERUM4,
            ItemID.SANFEW_SERUM3,
            ItemID.SANFEW_SERUM2,
            ItemID.SANFEW_SERUM1,
            ItemID.RELICYMS_MIX2,
            ItemID.RELICYMS_MIX1
    );

    @Getter
    private static final List<Integer> antifire = ImmutableList.of(
            ItemID.EXTENDED_SUPER_ANTIFIRE4,
            ItemID.EXTENDED_SUPER_ANTIFIRE3,
            ItemID.EXTENDED_SUPER_ANTIFIRE2,
            ItemID.EXTENDED_SUPER_ANTIFIRE1,
            ItemID.EXTENDED_SUPER_ANTIFIRE_MIX2,
            ItemID.EXTENDED_SUPER_ANTIFIRE_MIX1,
            ItemID.SUPER_ANTIFIRE_POTION4,
            ItemID.SUPER_ANTIFIRE_POTION3,
            ItemID.SUPER_ANTIFIRE_POTION2,
            ItemID.SUPER_ANTIFIRE_POTION1,
            ItemID.SUPER_ANTIFIRE_MIX2,
            ItemID.SUPER_ANTIFIRE_MIX1,
            ItemID.EXTENDED_ANTIFIRE4,
            ItemID.EXTENDED_ANTIFIRE3,
            ItemID.EXTENDED_ANTIFIRE2,
            ItemID.EXTENDED_ANTIFIRE1,
            ItemID.EXTENDED_ANTIFIRE_MIX2,
            ItemID.EXTENDED_ANTIFIRE_MIX1,
            ItemID.ANTIFIRE_POTION4,
            ItemID.ANTIFIRE_POTION3,
            ItemID.ANTIFIRE_POTION2,
            ItemID.ANTIFIRE_POTION1,
            ItemID.ANTIFIRE_MIX2,
            ItemID.ANTIFIRE_MIX1
    );

    @Getter
    private static final List<Integer> prayerPotions = ImmutableList.of(
            ItemID.PRAYER_POTION4,
            ItemID.PRAYER_POTION3,
            ItemID.PRAYER_POTION2,
            ItemID.PRAYER_POTION1
    );

    @Getter
    private static final List<Integer> restorePotions = ImmutableList.of(
            ItemID.SUPER_RESTORE4,
            ItemID.SUPER_RESTORE3,
            ItemID.SUPER_RESTORE2,
            ItemID.SUPER_RESTORE1,
            ItemID.RESTORE_POTION4,
            ItemID.RESTORE_POTION3,
            ItemID.RESTORE_POTION2,
            ItemID.RESTORE_POTION1
    );

    @Getter
    private static final List<Integer> superRestorePotions = ImmutableList.of(
            ItemID.SUPER_RESTORE4,
            ItemID.SUPER_RESTORE3,
            ItemID.SUPER_RESTORE2,
            ItemID.SUPER_RESTORE1
    );

    @Getter
    private static final List<Integer> saradominBrews = ImmutableList.of(
            ItemID.SARADOMIN_BREW4,
            ItemID.SARADOMIN_BREW3,
            ItemID.SARADOMIN_BREW2,
            ItemID.SARADOMIN_BREW1
    );

    @Getter
    private static final List<Integer> runRestoreItems = ImmutableList.of(
            ItemID.AGILITY_CAPE,
            ItemID.AGILITY_CAPET,
            ItemID.EXPLORERS_RING_4,
            ItemID.EXPLORERS_RING_3,
            ItemID.EXPLORERS_RING_2,
            ItemID.EXPLORERS_RING_1,
            ItemID.STAMINA_POTION4,
            ItemID.STAMINA_POTION3,
            ItemID.STAMINA_POTION2,
            ItemID.STAMINA_POTION1,
            ItemID.MINT_CAKE,
            ItemID.STRANGE_FRUIT,
            ItemID.STAMINA_MIX2,
            ItemID.STAMINA_MIX1,
            ItemID.SUPER_ENERGY4,
            ItemID.SUPER_ENERGY3,
            ItemID.SUPER_ENERGY2,
            ItemID.SUPER_ENERGY1,
            ItemID.ENERGY_POTION4,
            ItemID.ENERGY_POTION3,
            ItemID.ENERGY_POTION2,
            ItemID.ENERGY_POTION1,
            ItemID.PURPLE_SWEETS,
            ItemID.WHITE_TREE_FRUIT,
            ItemID.SUMMER_PIE,
            ItemID.HALF_A_SUMMER_PIE,
            ItemID.GUTHIX_REST4,
            ItemID.GUTHIX_REST3,
            ItemID.GUTHIX_REST2,
            ItemID.GUTHIX_REST1,
            ItemID.PAPAYA_FRUIT
    );

    @Getter
    private static final List<Integer> staminaPotions = ImmutableList.of(
            ItemID.STAMINA_POTION4,
            ItemID.STAMINA_POTION3,
            ItemID.STAMINA_POTION2,
            ItemID.STAMINA_POTION1,
            ItemID.STAMINA_MIX2,
            ItemID.STAMINA_MIX1
    );

    @Getter
    private static final List<Integer> agilityPotions = ImmutableList.of(
            ItemID.AGILITY_POTION4,
            ItemID.AGILITY_POTION3,
            ItemID.AGILITY_POTION2,
            ItemID.AGILITY_POTION1
    );


    @Getter
    private static final List<Integer> antiFirePotions = ImmutableList.of(
            ItemID.ANTIFIRE_POTION4,
            ItemID.ANTIFIRE_POTION3,
            ItemID.ANTIFIRE_POTION2,
            ItemID.ANTIFIRE_POTION1,
            ItemID.SUPER_ANTIFIRE_POTION4,
            ItemID.SUPER_ANTIFIRE_POTION3,
            ItemID.SUPER_ANTIFIRE_POTION2,
            ItemID.SUPER_ANTIFIRE_POTION1,
            ItemID.EXTENDED_ANTIFIRE4,
            ItemID.EXTENDED_ANTIFIRE3,
            ItemID.EXTENDED_ANTIFIRE2,
            ItemID.EXTENDED_ANTIFIRE1,
            ItemID.EXTENDED_SUPER_ANTIFIRE4,
            ItemID.EXTENDED_SUPER_ANTIFIRE3,
            ItemID.EXTENDED_SUPER_ANTIFIRE2,
            ItemID.EXTENDED_SUPER_ANTIFIRE1,
            ItemID.ANTIFIRE_MIX2,
            ItemID.ANTIFIRE_MIX1,
            ItemID.SUPER_ANTIFIRE_MIX2,
            ItemID.SUPER_ANTIFIRE_MIX1
    );

    // Food
    @Getter
    private static final List<Integer> goodEatingFood = ImmutableList.of(
            ItemID.DARK_CRAB,
            ItemID.TUNA_POTATO,
            ItemID.MANTA_RAY,
            ItemID.SEA_TURTLE,
            ItemID.PINEAPPLE_PIZZA,
            ItemID.SHARK,
            ItemID.MUSHROOM_POTATO,
            ItemID.UGTHANKI_KEBAB_1885,
            ItemID.CURRY,
            ItemID.COOKED_KARAMBWAN,
            ItemID.ANCHOVY_PIZZA,
            ItemID.ANGLERFISH,
            ItemID.MONKFISH,
            ItemID.POTATO_WITH_CHEESE,
            ItemID.MEAT_PIZZA,
            ItemID.POTATO_WITH_BUTTER,
            ItemID.SWORDFISH,
            ItemID.PLAIN_PIZZA,
            ItemID.BASS,
            ItemID.LOBSTER,
            ItemID.CHOCOLATE_CAKE,
            ItemID.CAKE,
            ItemID.STEW,
            ItemID.TUNA,
            ItemID.SALMON,
            ItemID.PIKE,
            ItemID.COD,
            ItemID.TROUT,
            ItemID.MACKEREL,
            ItemID.HERRING,
            ItemID.BREAD,
            ItemID.SARDINE,
            ItemID.COOKED_MEAT,
            ItemID.COOKED_CHICKEN,
            ItemID.SHRIMPS
    );


    @Getter
    private static final List<Integer> fishFood = ImmutableList.of(
            ItemID.DARK_CRAB,
            ItemID.MANTA_RAY,
            ItemID.ANGLERFISH,
            ItemID.SEA_TURTLE,
            ItemID.SHARK,
            ItemID.COOKED_KARAMBWAN,
            ItemID.MONKFISH,
            ItemID.COOKED_JUBBLY,
            ItemID.LAVA_EEL,
            ItemID.SWORDFISH,
            ItemID.BASS,
            ItemID.LOBSTER,
            ItemID.RAINBOW_FISH,
            ItemID.TUNA,
            ItemID.CAVE_EEL,
            ItemID.SALMON,
            ItemID.PIKE,
            ItemID.COD,
            ItemID.TROUT,
            ItemID.MACKEREL,
            ItemID.HERRING,
            ItemID.SARDINE,
            ItemID.SHRIMPS
    );

    @Getter
    private static final List<Integer> gnomeFood = ImmutableList.of(
            ItemID.TANGLED_TOADS_LEGS,
            ItemID.TANGLED_TOADS_LEGS_9551,
            ItemID.CHOCOLATE_BOMB,
            ItemID.CHOCOLATE_BOMB_9553,
            ItemID.WORM_HOLE,
            ItemID.WORM_HOLE_9547,
            ItemID.VEG_BALL,
            ItemID.VEG_BALL_9549,
            ItemID.FRUIT_BATTA,
            ItemID.FRUIT_BATTA_9527,
            ItemID.TOAD_BATTA,
            ItemID.TOAD_BATTA_9529,
            ItemID.WORM_BATTA,
            ItemID.WORM_BATTA_9531,
            ItemID.VEGETABLE_BATTA,
            ItemID.VEGETABLE_BATTA_9533,
            ItemID.CHEESETOM_BATTA,
            ItemID.CHEESETOM_BATTA_9535,
            ItemID.FRUIT_BLAST,
            ItemID.FRUIT_BLAST_9514,
            ItemID.PINEAPPLE_PUNCH,
            ItemID.PINEAPPLE_PUNCH_9512,
            ItemID.TOAD_CRUNCHIES,
            ItemID.TOAD_CRUNCHIES_9538,
            ItemID.WORM_CRUNCHIES,
            ItemID.WORM_CRUNCHIES_9542,
            ItemID.SPICY_CRUNCHIES,
            ItemID.SPICY_CRUNCHIES_9540,
            ItemID.CHOCCHIP_CRUNCHIES,
            ItemID.CHOCCHIP_CRUNCHIES_9544,
            ItemID.BLURBERRY_SPECIAL,
            ItemID.BLURBERRY_SPECIAL_9520,
            ItemID.WIZARD_BLIZZARD,
            ItemID.WIZARD_BLIZZARD_9487,
            ItemID.WIZARD_BLIZZARD_9489,
            ItemID.WIZARD_BLIZZARD_9508,
            ItemID.SHORT_GREEN_GUY,
            ItemID.SHORT_GREEN_GUY_9510,
            ItemID.DRUNK_DRAGON,
            ItemID.DRUNK_DRAGON_9516,
            ItemID.CHOC_SATURDAY,
            ItemID.CHOC_SATURDAY_9518
    );

    @Getter
    private static final List<Integer> stews = ImmutableList.of(
            ItemID.STEW,
            ItemID.CURRY
    );

    @Getter
    private static final List<Integer> pizzas = ImmutableList.of(
            ItemID.PINEAPPLE_PIZZA,
            ItemID.ANCHOVY_PIZZA,
            ItemID.MEAT_PIZZA,
            ItemID.PLAIN_PIZZA
    );


    @Getter
    private static final List<Integer> potatoFood = ImmutableList.of(
            ItemID.TUNA_POTATO,
            ItemID.MUSHROOM_POTATO,
            ItemID.EGG_POTATO,
            ItemID.POTATO_WITH_CHEESE,
            ItemID.CHILLI_POTATO,
            ItemID.POTATO_WITH_BUTTER,
            ItemID.BAKED_POTATO
    );

    @Getter
    private static final List<Integer> pies = ImmutableList.of(
            ItemID.SUMMER_PIE,
            ItemID.WILD_PIE,
            ItemID.DRAGONFRUIT_PIE,
            ItemID.ADMIRAL_PIE,
            ItemID.MUSHROOM_PIE,
            ItemID.BOTANICAL_PIE,
            ItemID.FISH_PIE,
            ItemID.GARDEN_PIE,
            ItemID.APPLE_PIE,
            ItemID.MUD_PIE,
            ItemID.MEAT_PIE,
            ItemID.REDBERRY_PIE
    );


    // Teleport items

    @Getter
    private static final List<Integer> gamesNecklaces = ImmutableList.of(
            ItemID.GAMES_NECKLACE8,
            ItemID.GAMES_NECKLACE7,
            ItemID.GAMES_NECKLACE6,
            ItemID.GAMES_NECKLACE5,
            ItemID.GAMES_NECKLACE4,
            ItemID.GAMES_NECKLACE3,
            ItemID.GAMES_NECKLACE2,
            ItemID.GAMES_NECKLACE1
    );

    @Getter
    private static final List<Integer> ringOfDuelings = ImmutableList.of(
            ItemID.RING_OF_DUELING8,
            ItemID.RING_OF_DUELING7,
            ItemID.RING_OF_DUELING6,
            ItemID.RING_OF_DUELING5,
            ItemID.RING_OF_DUELING4,
            ItemID.RING_OF_DUELING3,
            ItemID.RING_OF_DUELING2,
            ItemID.RING_OF_DUELING1
    );

    @Getter
    private static final List<Integer> burningAmulets = ImmutableList.of(
            ItemID.BURNING_AMULET5,
            ItemID.BURNING_AMULET4,
            ItemID.BURNING_AMULET3,
            ItemID.BURNING_AMULET2,
            ItemID.BURNING_AMULET1
    );

    @Getter
    private static final List<Integer> necklaceOfPassages = ImmutableList.of(
            ItemID.NECKLACE_OF_PASSAGE5,
            ItemID.NECKLACE_OF_PASSAGE4,
            ItemID.NECKLACE_OF_PASSAGE3,
            ItemID.NECKLACE_OF_PASSAGE2,
            ItemID.NECKLACE_OF_PASSAGE1
    );

    @Getter
    private static final List<Integer> skillsNecklaces = ImmutableList.of(
            ItemID.SKILLS_NECKLACE6,
            ItemID.SKILLS_NECKLACE5,
            ItemID.SKILLS_NECKLACE4,
            ItemID.SKILLS_NECKLACE3,
            ItemID.SKILLS_NECKLACE2,
            ItemID.SKILLS_NECKLACE1
    );

    @Getter
    private static final List<Integer> ringOfWealths = ImmutableList.of(
            ItemID.RING_OF_WEALTH_5,
            ItemID.RING_OF_WEALTH_I5,
            ItemID.RING_OF_WEALTH_4,
            ItemID.RING_OF_WEALTH_I4,
            ItemID.RING_OF_WEALTH_3,
            ItemID.RING_OF_WEALTH_I3,
            ItemID.RING_OF_WEALTH_2,
            ItemID.RING_OF_WEALTH_I2,
            ItemID.RING_OF_WEALTH_1,
            ItemID.RING_OF_WEALTH_I1
    );

    @Getter
    private static final List<Integer> combatBracelets = ImmutableList.of(
            ItemID.COMBAT_BRACELET6,
            ItemID.COMBAT_BRACELET5,
            ItemID.COMBAT_BRACELET4,
            ItemID.COMBAT_BRACELET3,
            ItemID.COMBAT_BRACELET2,
            ItemID.COMBAT_BRACELET1
    );

    @Getter
    private static final List<Integer> amuletOfGlories = ImmutableList.of(
            ItemID.AMULET_OF_ETERNAL_GLORY,
            ItemID.AMULET_OF_GLORY6,
            ItemID.AMULET_OF_GLORY_T6,
            ItemID.AMULET_OF_GLORY5,
            ItemID.AMULET_OF_GLORY_T5,
            ItemID.AMULET_OF_GLORY4,
            ItemID.AMULET_OF_GLORY_T4,
            ItemID.AMULET_OF_GLORY3,
            ItemID.AMULET_OF_GLORY_T3,
            ItemID.AMULET_OF_GLORY2,
            ItemID.AMULET_OF_GLORY_T2,
            ItemID.AMULET_OF_GLORY1,
            ItemID.AMULET_OF_GLORY_T1
    );

    @Getter
    private static final List<Integer> digsitePendants = ImmutableList.of(
            ItemID.DIGSITE_PENDANT_5,
            ItemID.DIGSITE_PENDANT_4,
            ItemID.DIGSITE_PENDANT_3,
            ItemID.DIGSITE_PENDANT_2,
            ItemID.DIGSITE_PENDANT_1
    );

    @Getter
    private static final List<Integer> slayerRings = ImmutableList.of(
            ItemID.SLAYER_RING_ETERNAL,
            ItemID.SLAYER_RING_8,
            ItemID.SLAYER_RING_7,
            ItemID.SLAYER_RING_6,
            ItemID.SLAYER_RING_5,
            ItemID.SLAYER_RING_4,
            ItemID.SLAYER_RING_3,
            ItemID.SLAYER_RING_2,
            ItemID.SLAYER_RING_1
    );

    @Getter
    private static final List<Integer> pharoahSceptre = ImmutableList.of(
            ItemID.PHARAOHS_SCEPTRE_8,
            ItemID.PHARAOHS_SCEPTRE_7,
            ItemID.PHARAOHS_SCEPTRE_6,
            ItemID.PHARAOHS_SCEPTRE_5,
            ItemID.PHARAOHS_SCEPTRE_4,
            ItemID.PHARAOHS_SCEPTRE_3,
            ItemID.PHARAOHS_SCEPTRE_2,
            ItemID.PHARAOHS_SCEPTRE_1
    );

    // Logs

    @Getter
    private static final List<Integer> logsForFire = ImmutableList.of(
            ItemID.LOGS,
            ItemID.OAK_LOGS,
            ItemID.WILLOW_LOGS,
            ItemID.TEAK_LOGS,
            ItemID.REDWOOD_LOGS,
            ItemID.MAPLE_LOGS,
            ItemID.MAHOGANY_LOGS,
            ItemID.YEW_LOGS,
            ItemID.MAGIC_LOGS,
            ItemID.BLISTERWOOD_LOGS,
            ItemID.ARCTIC_PINE_LOGS,
            ItemID.ACHEY_TREE_LOGS,
            ItemID.REDWOOD_PYRE_LOGS,
            ItemID.MAGIC_PYRE_LOGS,
            ItemID.YEW_PYRE_LOGS,
            ItemID.MAHOGANY_PYRE_LOGS,
            ItemID.MAPLE_PYRE_LOGS,
            ItemID.ARCTIC_PYRE_LOGS,
            ItemID.TEAK_PYRE_LOGS,
            ItemID.WILLOW_PYRE_LOGS,
            ItemID.OAK_PYRE_LOGS,
            ItemID.PYRE_LOGS,
            ItemID.GREEN_LOGS,
            ItemID.RED_LOGS,
            ItemID.PURPLE_LOGS,
            ItemID.WHITE_LOGS,
            ItemID.BLUE_LOGS
    );

    @Getter
    private static final List<Integer> bones = ImmutableList.of(
            ItemID.BONES,
            ItemID.BURNT_BONES,
            ItemID.WOLF_BONES,
            ItemID.BAT_BONES,
            ItemID.BIG_BONES,
            ItemID.JOGRE_BONES,
            ItemID.BABYDRAGON_BONES,
            ItemID.DRAGON_BONES,
            ItemID.DAGANNOTH_BONES
    );

    // Other

    @Getter
    private static final List<Integer> greegrees = ImmutableList.of(
            ItemID.KARAMJAN_MONKEY_GREEGREE,
            ItemID.GORILLA_GREEGREE,
            ItemID.ANCIENT_GORILLA_GREEGREE,
            ItemID.BEARDED_GORILLA_GREEGREE,
            ItemID.KRUK_MONKEY_GREEGREE,
            ItemID.NINJA_MONKEY_GREEGREE,
            ItemID.ZOMBIE_MONKEY_GREEGREE,
            ItemID.ZOMBIE_MONKEY_GREEGREE_4030,
            ItemID.NINJA_MONKEY_GREEGREE_4025
    );

    @Getter
    private static final List<Integer> antifireShields = ImmutableList.of(
            ItemID.DRAGONFIRE_SHIELD,
            ItemID.DRAGONFIRE_SHIELD_11284,
            ItemID.DRAGONFIRE_WARD,
            ItemID.DRAGONFIRE_WARD_22003,
            ItemID.ANCIENT_WYVERN_SHIELD,
            ItemID.ANCIENT_WYVERN_SHIELD_21634,
            ItemID.ANTIDRAGON_SHIELD,
            ItemID.ANTIDRAGON_SHIELD_8282
    );

    @Getter
    private static final List<Integer> antiWyvernShields = ImmutableList.of(
            ItemID.DRAGONFIRE_SHIELD,
            ItemID.DRAGONFIRE_SHIELD_11284,
            ItemID.DRAGONFIRE_WARD,
            ItemID.DRAGONFIRE_WARD_22003,
            ItemID.ANCIENT_WYVERN_SHIELD,
            ItemID.ANCIENT_WYVERN_SHIELD_21634,
            ItemID.MIND_SHIELD,
            ItemID.ELEMENTAL_SHIELD
    );

    @Getter
    private static final List<Integer> ghostspeak = ImmutableList.of(
            ItemID.GHOSTSPEAK_AMULET,
            ItemID.GHOSTSPEAK_AMULET_4250,
            ItemID.MORYTANIA_LEGS_2,
            ItemID.MORYTANIA_LEGS_3,
            ItemID.MORYTANIA_LEGS_4
    );

    @Getter
    private static final List<Integer> lightSources = ImmutableList.of(
            ItemID.FIREMAKING_CAPET,
            ItemID.FIREMAKING_CAPE,
            ItemID.MAX_CAPE,
            ItemID.BRUMA_TORCH,
            ItemID.KANDARIN_HEADGEAR_4,
            ItemID.KANDARIN_HEADGEAR_3,
            ItemID.KANDARIN_HEADGEAR_2,
            ItemID.KANDARIN_HEADGEAR_1,
            ItemID.BULLSEYE_LANTERN_4550,
            ItemID.SAPPHIRE_LANTERN_4702,
            ItemID.EMERALD_LANTERN_9065,
            ItemID.OIL_LANTERN_4539,
            ItemID.CANDLE_LANTERN_4531,
            ItemID.CANDLE_LANTERN_4534,
            ItemID.MINING_HELMET_5014,
            ItemID.OIL_LAMP_4524,
            ItemID.LIT_TORCH,
            ItemID.LIT_CANDLE,
            ItemID.LIT_BLACK_CANDLE
    );

    @Getter
    private static final List<Integer> cats = ImmutableList.of(
            ItemID.WILY_HELLCAT,
            ItemID.WILY_CAT,
            ItemID.WILY_CAT_6556,
            ItemID.WILY_CAT_6557,
            ItemID.WILY_CAT_6558,
            ItemID.WILY_CAT_6559,
            ItemID.WILY_CAT_6560,

            ItemID.LAZY_HELL_CAT,
            ItemID.LAZY_CAT,
            ItemID.LAZY_CAT_6550,
            ItemID.LAZY_CAT_6551,
            ItemID.LAZY_CAT_6552,
            ItemID.LAZY_CAT_6553,
            ItemID.LAZY_CAT_6554,

            ItemID.HELL_CAT,
            ItemID.PET_CAT,
            ItemID.PET_CAT_1562,
            ItemID.PET_CAT_1563,
            ItemID.PET_CAT_1564,
            ItemID.PET_CAT_1565,
            ItemID.PET_CAT_1566,

            ItemID.HELLKITTEN,
            ItemID.PET_KITTEN,
            ItemID.PET_KITTEN_1556,
            ItemID.PET_KITTEN_1557,
            ItemID.PET_KITTEN_1558,
            ItemID.PET_KITTEN_1559,
            ItemID.PET_KITTEN_1560,
            // Overgrown cats
            ItemID.OVERGROWN_HELLCAT,
            ItemID.PET_CAT_1567,
            ItemID.PET_CAT_1568,
            ItemID.PET_CAT_1569,
            ItemID.PET_CAT_1570,
            ItemID.PET_CAT_1571,
            ItemID.PET_CAT_1572
    );

    @Getter
    private static final List<Integer> huntingCats = ImmutableList.of(
            ItemID.WILY_HELLCAT,
            ItemID.WILY_CAT,
            ItemID.WILY_CAT_6556,
            ItemID.WILY_CAT_6557,
            ItemID.WILY_CAT_6558,
            ItemID.WILY_CAT_6559,
            ItemID.WILY_CAT_6560,

            ItemID.LAZY_HELL_CAT,
            ItemID.LAZY_CAT,
            ItemID.LAZY_CAT_6550,
            ItemID.LAZY_CAT_6551,
            ItemID.LAZY_CAT_6552,
            ItemID.LAZY_CAT_6553,
            ItemID.LAZY_CAT_6554,

            ItemID.HELL_CAT,
            ItemID.PET_CAT,
            ItemID.PET_CAT_1562,
            ItemID.PET_CAT_1563,
            ItemID.PET_CAT_1564,
            ItemID.PET_CAT_1565,
            ItemID.PET_CAT_1566,

            ItemID.HELLKITTEN,
            ItemID.PET_KITTEN,
            ItemID.PET_KITTEN_1556,
            ItemID.PET_KITTEN_1557,
            ItemID.PET_KITTEN_1558,
            ItemID.PET_KITTEN_1559,
            ItemID.PET_KITTEN_1560
    );


    @Getter
    private static final List<Integer> flowers = ImmutableList.of(
            ItemID.RED_FLOWERS,
            ItemID.YELLOW_FLOWERS,
            ItemID.PURPLE_FLOWERS,
            ItemID.ORANGE_FLOWERS,
            ItemID.MIXED_FLOWERS,
            ItemID.ASSORTED_FLOWERS,
            ItemID.BLACK_FLOWERS,
            ItemID.WHITE_FLOWERS,
            ItemID.RED_FLOWERS_8938,
            ItemID.BLUE_FLOWERS_8936
    );

    @Getter
    private static final List<Integer> rodOfIvandis = ImmutableList.of(
            ItemID.ROD_OF_IVANDIS_10,
            ItemID.ROD_OF_IVANDIS_9,
            ItemID.ROD_OF_IVANDIS_8,
            ItemID.ROD_OF_IVANDIS_7,
            ItemID.ROD_OF_IVANDIS_6,
            ItemID.ROD_OF_IVANDIS_5,
            ItemID.ROD_OF_IVANDIS_4,
            ItemID.ROD_OF_IVANDIS_3,
            ItemID.ROD_OF_IVANDIS_2,
            ItemID.ROD_OF_IVANDIS_1
    );

    @Getter
    private static final List<Integer> salveAmulet = ImmutableList.of(
            ItemID.SALVE_AMULETEI,
            ItemID.SALVE_AMULET_E,
            ItemID.SALVE_AMULETI,
            ItemID.SALVE_AMULET,
            ItemID.SALVE_AMULETEI_25278,
            ItemID.SALVE_AMULETI_25250
    );

    @Getter
    private static final List<Integer> wateringCans = ImmutableList.of(
            ItemID.GRICOLLERS_CAN,
            ItemID.WATERING_CAN8,
            ItemID.WATERING_CAN7,
            ItemID.WATERING_CAN6,
            ItemID.WATERING_CAN5,
            ItemID.WATERING_CAN4,
            ItemID.WATERING_CAN3,
            ItemID.WATERING_CAN2,
            ItemID.WATERING_CAN1
    );

    @Getter
    private static final List<Integer> enchantedLyre = ImmutableList.of(
            ItemID.ENCHANTED_LYREI,
            ItemID.ENCHANTED_LYRE5,
            ItemID.ENCHANTED_LYRE4,
            ItemID.ENCHANTED_LYRE3,
            ItemID.ENCHANTED_LYRE2,
            ItemID.ENCHANTED_LYRE1
    );

    @Getter
    private static final List<Integer> skillCape = ImmutableList.of(
            ItemID.AGILITY_CAPE,
            ItemID.AGILITY_CAPET,
            ItemID.ATTACK_CAPE,
            ItemID.ATTACK_CAPET,
            ItemID.CONSTRUCT_CAPE,
            ItemID.CONSTRUCT_CAPET,
            ItemID.COOKING_CAPE,
            ItemID.COOKING_CAPET,
            ItemID.CRAFTING_CAPE,
            ItemID.CRAFTING_CAPET,
            ItemID.DEFENCE_CAPE,
            ItemID.DEFENCE_CAPET,
            ItemID.FARMING_CAPE,
            ItemID.FARMING_CAPET,
            ItemID.FIREMAKING_CAPE,
            ItemID.FIREMAKING_CAPET,
            ItemID.FISHING_CAPE,
            ItemID.FISHING_CAPET,
            ItemID.FLETCHING_CAPE,
            ItemID.FLETCHING_CAPET,
            ItemID.HERBLORE_CAPE,
            ItemID.HERBLORE_CAPET,
            ItemID.HITPOINTS_CAPE,
            ItemID.HITPOINTS_CAPET,
            ItemID.HUNTER_CAPE,
            ItemID.HUNTER_CAPET,
            ItemID.MAGIC_CAPE,
            ItemID.MAGIC_CAPET,
            ItemID.MINING_CAPE,
            ItemID.MINING_CAPET,
            ItemID.PRAYER_CAPE,
            ItemID.PRAYER_CAPET,
            ItemID.RANGING_CAPE,
            ItemID.RANGING_CAPET,
            ItemID.RUNECRAFT_CAPE,
            ItemID.RUNECRAFT_CAPET,
            ItemID.SLAYER_CAPE,
            ItemID.SLAYER_CAPET,
            ItemID.SMITHING_CAPE,
            ItemID.SMITHING_CAPET,
            ItemID.STRENGTH_CAPE,
            ItemID.STRENGTH_CAPET,
            ItemID.THIEVING_CAPE,
            ItemID.THIEVING_CAPET,
            ItemID.WOODCUTTING_CAPE,
            ItemID.WOODCUT_CAPET,
            ItemID.QUEST_POINT_CAPE,
            ItemID.QUEST_POINT_CAPE_T
    );

    @Getter
    private static final List<Integer> slayerHelmets = ImmutableList.of(
            ItemID.SLAYER_HELMET,
            ItemID.SLAYER_HELMET_I,
            ItemID.SLAYER_HELMET_I_25177,
            ItemID.BLACK_SLAYER_HELMET,
            ItemID.BLACK_SLAYER_HELMET_I,
            ItemID.BLACK_SLAYER_HELMET_I_25179,
            ItemID.GREEN_SLAYER_HELMET,
            ItemID.GREEN_SLAYER_HELMET_I,
            ItemID.GREEN_SLAYER_HELMET_I_25181,
            ItemID.RED_SLAYER_HELMET,
            ItemID.RED_SLAYER_HELMET_I,
            ItemID.RED_SLAYER_HELMET_I_25183,
            ItemID.PURPLE_SLAYER_HELMET,
            ItemID.PURPLE_SLAYER_HELMET_I,
            ItemID.PURPLE_SLAYER_HELMET_I_25185,
            ItemID.TURQUOISE_SLAYER_HELMET,
            ItemID.TURQUOISE_SLAYER_HELMET_I,
            ItemID.TURQUOISE_SLAYER_HELMET_I_25187,
            ItemID.HYDRA_SLAYER_HELMET,
            ItemID.HYDRA_SLAYER_HELMET_I,
            ItemID.HYDRA_SLAYER_HELMET_I_25189,
            ItemID.TWISTED_SLAYER_HELMET,
            ItemID.TWISTED_SLAYER_HELMET_I,
            ItemID.TWISTED_SLAYER_HELMET_I_25191,
            ItemID.TZKAL_SLAYER_HELMET,
            ItemID.TZKAL_SLAYER_HELMET_I,
            ItemID.TZKAL_SLAYER_HELMET_I_25914,
            ItemID.VAMPYRIC_SLAYER_HELMET,
            ItemID.VAMPYRIC_SLAYER_HELMET_I,
            ItemID.VAMPYRIC_SLAYER_HELMET_I_25908,
            ItemID.TZTOK_SLAYER_HELMET,
            ItemID.TZTOK_SLAYER_HELMET_I,
            ItemID.TZTOK_SLAYER_HELMET_I_25902
    );

    @Getter
    private static final List<Integer> rawFish = ImmutableList.of(
            ItemID.RAW_SHRIMPS,
            ItemID.RAW_SARDINE,
            ItemID.RAW_KARAMBWANJI,
            ItemID.RAW_HERRING,
            ItemID.RAW_ANCHOVIES,
            ItemID.RAW_MACKEREL,
            ItemID.RAW_TROUT,
            ItemID.RAW_COD,
            ItemID.RAW_PIKE,
            ItemID.RAW_SLIMY_EEL,
            ItemID.RAW_SALMON,
            ItemID.RAW_TUNA,
            ItemID.RAW_RAINBOW_FISH,
            ItemID.RAW_CAVE_EEL,
            ItemID.RAW_LOBSTER,
            ItemID.RAW_BASS,
            ItemID.RAW_SWORDFISH,
            ItemID.RAW_LAVA_EEL,
            ItemID.RAW_MONKFISH,
            ItemID.RAW_KARAMBWAN,
            ItemID.RAW_SHARK,
            ItemID.RAW_SEA_TURTLE,
            ItemID.RAW_MANTA_RAY,
            ItemID.RAW_ANGLERFISH,
            ItemID.RAW_DARK_CRAB
    );

    @Getter
    private static final List<Integer> compost = ImmutableList.of(
            ItemID.BOTTOMLESS_COMPOST_BUCKET_22997,
            ItemID.ULTRACOMPOST,
            ItemID.SUPERCOMPOST,
            ItemID.COMPOST
    );

    @Getter
    private static final List<Integer> fairyStaff = ImmutableList.of(
            ItemID.LUNAR_STAFF,
            ItemID.LUNAR_STAFF__PT3,
            ItemID.LUNAR_STAFF__PT2,
            ItemID.LUNAR_STAFF__PT1,
            ItemID.DRAMEN_STAFF
    );

    @Getter
    private static final List<Integer> earthAltar = ImmutableList.of(
            ItemID.ELEMENTAL_TALISMAN,
            ItemID.EARTH_TIARA,
            ItemID.EARTH_TALISMAN
    );

    @Getter
    private static final List<Integer> essenceLow = ImmutableList.of(
            ItemID.DAEYALT_ESSENCE,
            ItemID.PURE_ESSENCE,
            ItemID.RUNE_ESSENCE
    );

    @Getter
    private static final List<Integer> essenceHigh = ImmutableList.of(
            ItemID.DAEYALT_ESSENCE,
            ItemID.PURE_ESSENCE
    );

    @Getter
    private static final List<Integer> coins = ImmutableList.of(
            ItemID.COINS_995,
            ItemID.COINS_8890,
            ItemID.COINS_6964,
            ItemID.COINS
    );

    @Getter
    private  static final List<Integer> cookingGuild = ImmutableList.of(
            ItemID.CHEFS_HAT,
            ItemID.VARROCK_ARMOUR_3,
            ItemID.VARROCK_ARMOUR_4,
            ItemID.COOKING_CAPET,
            ItemID.COOKING_CAPE
    );

    @Getter
    private static final List<Integer> miningHelm = ImmutableList.of(
            ItemID.MINING_HELMET_5014,
            ItemID.MINING_HELMET
    );

    @Getter
    private static final List<Integer> gloves = ImmutableList.of(
            ItemID.FEROCIOUS_GLOVES,
            ItemID.BARROWS_GLOVES,
            ItemID.DRAGON_GLOVES,
            ItemID.GRANITE_GLOVES,
            ItemID.RUNE_GLOVES,
            ItemID.ADAMANT_GLOVES,
            ItemID.MITHRIL_GLOVES,
            ItemID.BLACK_GLOVES,
            ItemID.STEEL_GLOVES,
            ItemID.IRON_GLOVES,
            ItemID.BRONZE_GLOVES,
            ItemID.KARAMJA_GLOVES_1,
            ItemID.KARAMJA_GLOVES_2,
            ItemID.KARAMJA_GLOVES_3,
            ItemID.KARAMJA_GLOVES_4,
            ItemID.FREMENNIK_GLOVES,
            ItemID.GLOVES_OF_SILENCE,
            ItemID.HARDLEATHER_GLOVES,
            ItemID.ICE_GLOVES,
            ItemID.HAM_GLOVES,
            ItemID.LEATHER_GLOVES,
            ItemID.LUNAR_GLOVES,
            ItemID.MYSTIC_GLOVES,
            ItemID.MYSTIC_GLOVES_DARK,
            ItemID.MYSTIC_GLOVES_DUSK,
            ItemID.MYSTIC_GLOVES_LIGHT,
            ItemID.ROCKSHELL_GLOVES,
            ItemID.ROGUE_GLOVES,
            ItemID.SLAYER_GLOVES,
            ItemID.VOID_KNIGHT_GLOVES,
            ItemID.WHITE_GLOVES
    );

    @Getter
    private static final List<Integer> questCape = ImmutableList.of(
            ItemID.QUEST_POINT_CAPE_T,
            ItemID.QUEST_POINT_CAPE
    );

    @Getter
    private static final List<Integer> cosmicAltar = ImmutableList.of(
            ItemID.COSMIC_TIARA,
            ItemID.COSMIC_TALISMAN
    );

    @Getter
    private static final List<Integer> wallBeast = ImmutableList.of(
            ItemID.SPINY_HELMET,
            ItemID.SLAYER_HELMET,
            ItemID.SLAYER_HELMET_I,
            ItemID.SLAYER_HELMET_I_25177,
            ItemID.BLACK_SLAYER_HELMET,
            ItemID.BLACK_SLAYER_HELMET_I,
            ItemID.BLACK_SLAYER_HELMET_I_25179,
            ItemID.GREEN_SLAYER_HELMET,
            ItemID.GREEN_SLAYER_HELMET_I,
            ItemID.GREEN_SLAYER_HELMET_I_25181,
            ItemID.RED_SLAYER_HELMET,
            ItemID.RED_SLAYER_HELMET_I,
            ItemID.RED_SLAYER_HELMET_I_25183,
            ItemID.PURPLE_SLAYER_HELMET,
            ItemID.PURPLE_SLAYER_HELMET_I,
            ItemID.PURPLE_SLAYER_HELMET_I_25185,
            ItemID.TURQUOISE_SLAYER_HELMET,
            ItemID.TURQUOISE_SLAYER_HELMET_I,
            ItemID.TURQUOISE_SLAYER_HELMET_I_25187,
            ItemID.HYDRA_SLAYER_HELMET,
            ItemID.HYDRA_SLAYER_HELMET_I,
            ItemID.HYDRA_SLAYER_HELMET_I_25189,
            ItemID.TWISTED_SLAYER_HELMET,
            ItemID.TWISTED_SLAYER_HELMET_I,
            ItemID.TWISTED_SLAYER_HELMET_I_25191
    );

    @Getter
    private static final List<Integer> waterAltar = ImmutableList.of(
            ItemID.ELEMENTAL_TALISMAN,
            ItemID.WATER_TIARA,
            ItemID.WATER_TALISMAN
    );

    @Getter
    private static final List<Integer> fireAltar = ImmutableList.of(
            ItemID.ELEMENTAL_TALISMAN,
            ItemID.FIRE_TIARA,
            ItemID.FIRE_TALISMAN
    );

    @Getter
    private  static final List<Integer> plunderArtefacts = ImmutableList.of(
            ItemID.IVORY_COMB,
            ItemID.POTTERY_SCARAB,
            ItemID.POTTERY_STATUETTE,
            ItemID.STONE_SEAL,
            ItemID.STONE_SCARAB,
            ItemID.STONE_STATUETTE,
            ItemID.GOLD_SEAL,
            ItemID.GOLDEN_SCARAB,
            ItemID.GOLDEN_STATUETTE
    );

    @Getter
    private  static final List<Integer> waterskin = ImmutableList.of(
            ItemID.WATERSKIN4,
            ItemID.WATERSKIN3,
            ItemID.WATERSKIN2,
            ItemID.WATERSKIN1
    );

    @Getter
    private  static final List<Integer> grimyHerb = ImmutableList.of(
            ItemID.GRIMY_AVANTOE,
            ItemID.GRIMY_CADANTINE,
            ItemID.GRIMY_LANTADYME,
            ItemID.GRIMY_ARDRIGAL,
            ItemID.GRIMY_ROGUES_PURSE,
            ItemID.GRIMY_GUAM_LEAF,
            ItemID.GRIMY_HARRALANDER,
            ItemID.GRIMY_TOADFLAX,
            ItemID.GRIMY_TORSTOL,
            ItemID.GRIMY_TARROMIN,
            ItemID.GRIMY_IRIT_LEAF
    );

    @Getter
    private  static final List<Integer> blackjacks = ImmutableList.of(
            ItemID.MAPLE_BLACKJACKD,
            ItemID.MAPLE_BLACKJACKO,
            ItemID.MAPLE_BLACKJACK,
            ItemID.WILLOW_BLACKJACKD,
            ItemID.WILLOW_BLACKJACKO,
            ItemID.WILLOW_BLACKJACK,
            ItemID.OAK_BLACKJACKO,
            ItemID.OAK_BLACKJACKD,
            ItemID.OAK_BLACKJACK
    );

    @Getter
    private  static final List<Integer> keris = ImmutableList.of(
            ItemID.KERISP_10584,
            ItemID.KERISP_10583,
            ItemID.KERISP,
            ItemID.KERIS
    );

    @Getter
    private  static final List<Integer> stuffedKQHead = ImmutableList.of(
            ItemID.STUFFED_KQ_HEAD,
            ItemID.STUFFED_KQ_HEAD_TATTERED
    );

    @Getter
    private  static final List<Integer> rechargeableNeckBracelet = ImmutableList.of(
            ItemID.SKILLS_NECKLACE3,
            ItemID.COMBAT_BRACELET3,
            ItemID.SKILLS_NECKLACE2,
            ItemID.COMBAT_BRACELET2,
            ItemID.SKILLS_NECKLACE1,
            ItemID.COMBAT_BRACELET1
           // ItemID.SKILLS_NECKLACE
           // ItemID.COMBAT_BRACELET
    );

    @Getter
    private static final List<Integer> deathAltar = ImmutableList.of(
            ItemID.DEATH_TIARA,
            ItemID.DEATH_TALISMAN
    );

    @Getter
    private static final List<Integer> imbuableSalveAmulet = ImmutableList.of(
            ItemID.SALVE_AMULET_E,
            ItemID.SALVE_AMULET
    );

    @Getter
    private static final List<Integer> imbuedSalveAmulet = ImmutableList.of(
            ItemID.SALVE_AMULETEI,
            ItemID.SALVE_AMULETI,
            ItemID.SALVE_AMULETEI_25278,
            ItemID.SALVE_AMULETI_25250
    );

    @Getter
    private static final List<Integer> nonMagicTreeRoot = ImmutableList.of(
            ItemID.OAK_ROOTS,
            ItemID.WILLOW_ROOTS,
            ItemID.MAPLE_ROOTS,
            ItemID.YEW_ROOTS
    );

    @Getter
    private  static final List<Integer> ardyCloaks = ImmutableList.of(
            ItemID.ARDOUGNE_CLOAK_1,
            ItemID.ARDOUGNE_CLOAK_2,
            ItemID.ARDOUGNE_CLOAK_3,
            ItemID.ARDOUGNE_CLOAK_4,
            ItemID.ARDOUGNE_MAX_CAPE
    );

    @Getter
    private static final List<Integer> teamCape = ImmutableList.of(
            ItemID.TEAM1_CAPE,
            ItemID.TEAM2_CAPE,
            ItemID.TEAM3_CAPE,
            ItemID.TEAM4_CAPE,
            ItemID.TEAM5_CAPE,
            ItemID.TEAM6_CAPE,
            ItemID.TEAM7_CAPE,
            ItemID.TEAM8_CAPE,
            ItemID.TEAM9_CAPE,
            ItemID.TEAM10_CAPE,
            ItemID.TEAM11_CAPE,
            ItemID.TEAM12_CAPE,
            ItemID.TEAM13_CAPE,
            ItemID.TEAM14_CAPE,
            ItemID.TEAM15_CAPE,
            ItemID.TEAM16_CAPE,
            ItemID.TEAM17_CAPE,
            ItemID.TEAM18_CAPE,
            ItemID.TEAM19_CAPE,
            ItemID.TEAM20_CAPE,
            ItemID.TEAM21_CAPE,
            ItemID.TEAM22_CAPE,
            ItemID.TEAM23_CAPE,
            ItemID.TEAM24_CAPE,
            ItemID.TEAM25_CAPE,
            ItemID.TEAM26_CAPE,
            ItemID.TEAM27_CAPE,
            ItemID.TEAM28_CAPE,
            ItemID.TEAM29_CAPE,
            ItemID.TEAM30_CAPE,
            ItemID.TEAM31_CAPE,
            ItemID.TEAM32_CAPE,
            ItemID.TEAM33_CAPE,
            ItemID.TEAM34_CAPE,
            ItemID.TEAM35_CAPE,
            ItemID.TEAM36_CAPE,
            ItemID.TEAM37_CAPE,
            ItemID.TEAM38_CAPE,
            ItemID.TEAM39_CAPE,
            ItemID.TEAM40_CAPE,
            ItemID.TEAM41_CAPE,
            ItemID.TEAM42_CAPE,
            ItemID.TEAM43_CAPE,
            ItemID.TEAM44_CAPE,
            ItemID.TEAM45_CAPE,
            ItemID.TEAM46_CAPE,
            ItemID.TEAM47_CAPE,
            ItemID.TEAM48_CAPE,
            ItemID.TEAM49_CAPE,
            ItemID.TEAM50_CAPE
    );

    @Getter
    private static final List<Integer> chaosAltar = ImmutableList.of(
            ItemID.CHAOS_TIARA,
            ItemID.CHAOS_TALISMAN
    );

    @Getter
    private static final List<Integer> runeAxeBetter = ImmutableList.of(
            ItemID.RUNE_AXE,
            ItemID.DRAGON_AXE,
            ItemID.INFERNAL_AXE_UNCHARGED,
            ItemID.INFERNAL_AXE_UNCHARGED_25371,
            ItemID.CRYSTAL_AXE_INACTIVE,
            ItemID.CRYSTAL_AXE,
            ItemID.CRYSTAL_AXE_23862
    );

    @Getter
    private static final List<Integer> godStaff = ImmutableList.of(
            ItemID.ZAMORAK_STAFF,
            ItemID.STAFF_OF_THE_DEAD,
            ItemID.STAFF_OF_THE_DEAD_23613,
            ItemID.TOXIC_STAFF_OF_THE_DEAD,
            ItemID.TOXIC_STAFF_UNCHARGED,
            ItemID.GUTHIX_STAFF,
            ItemID.VOID_KNIGHT_MACE,
            ItemID.VOID_KNIGHT_MACE_BROKEN,
            ItemID.VOID_KNIGHT_MACE_L,
            ItemID.STAFF_OF_BALANCE,
            ItemID.SARADOMIN_STAFF,
            ItemID.STAFF_OF_LIGHT
    );
    @Getter
    private static final List<Integer> snailShells = ImmutableList.of(
            ItemID.BLAMISH_BARK_SHELL,
            ItemID.BLAMISH_BLUE_SHELL,
            ItemID.BLAMISH_MYRE_SHELL,
            ItemID.BLAMISH_BLUE_SHELL_3361,
            ItemID.BLAMISH_MYRE_SHELL_3355,
            ItemID.BLAMISH_RED_SHELL,
            ItemID.BLAMISH_RED_SHELL_3357,
            ItemID.BLAMISH_OCHRE_SHELL,
            ItemID.BLAMISH_OCHRE_SHELL_3359
    );

    @Getter
    private static final List<Integer> tannableHide = ImmutableList.of(
            ItemID.BLACK_DRAGONHIDE,
            ItemID.RED_DRAGONHIDE,
            ItemID.BLUE_DRAGONHIDE,
            ItemID.GREEN_DRAGONHIDE,
            ItemID.SNAKE_HIDE_7801,
            ItemID.SNAKE_HIDE,
            ItemID.COWHIDE
    );

    @Getter
    private static final List<Integer> bonemeal = ImmutableList.of(
            ItemID.BONEMEAL,
            ItemID.BAT_BONEMEAL,
            ItemID.BURNT_BONEMEAL,
            ItemID.BIG_BONEMEAL,
            ItemID.BABY_DRAGON_BONEMEAL,
            ItemID.BEARDED_GORILLA_BONEMEAL,
            ItemID.BURNT_JOGRE_BONEMEAL,
            ItemID.DRAGON_BONEMEAL,
            ItemID.DRAKE_BONEMEAL,
            ItemID.GORILLA_BONEMEAL,
            ItemID.SMALL_NINJA_BONEMEAL,
            ItemID.SKELETON_BONEMEAL,
            ItemID.MONKEY_BONEMEAL,
            ItemID.FAYRG_BONEMEAL,
            ItemID.DAGANNOTHKING_BONEMEAL,
            ItemID.HYDRA_BONEMEAL,
            ItemID.JOGRE_BONEMEAL,
            ItemID.LARGE_ZOMBIE_MONKEY_BONEMEAL,
            ItemID.LAVA_DRAGON_BONEMEAL,
            ItemID.MEDIUM_NINJA_BONEMEAL,
            ItemID.OURG_BONEMEAL,
            ItemID.RAURG_BONEMEAL,
            ItemID.SHAIKAHAN_BONEMEAL,
            ItemID.SUPERIOR_DRAGON_BONEMEAL,
            ItemID.WYRM_BONEMEAL,
            ItemID.WOLF_BONEMEAL,
            ItemID.ZOGRE_BONEMEAL,
            ItemID.SMALL_ZOMBIE_MONKEY_BONEMEAL,
            ItemID.WYVERN_BONEMEAL
    );

    @Getter
    private static final List<Integer> earProtection = ImmutableList.of(
            ItemID.EARMUFFS,
            ItemID.SLAYER_HELMET,
            ItemID.SLAYER_HELMET_I,
            ItemID.SLAYER_HELMET_I_25177,
            ItemID.BLACK_SLAYER_HELMET,
            ItemID.BLACK_SLAYER_HELMET_I,
            ItemID.BLACK_SLAYER_HELMET_I_25179,
            ItemID.GREEN_SLAYER_HELMET,
            ItemID.GREEN_SLAYER_HELMET_I,
            ItemID.GREEN_SLAYER_HELMET_I_25181,
            ItemID.RED_SLAYER_HELMET,
            ItemID.RED_SLAYER_HELMET_I,
            ItemID.RED_SLAYER_HELMET_I_25183,
            ItemID.PURPLE_SLAYER_HELMET,
            ItemID.PURPLE_SLAYER_HELMET_I,
            ItemID.PURPLE_SLAYER_HELMET_I_25185,
            ItemID.TURQUOISE_SLAYER_HELMET,
            ItemID.TURQUOISE_SLAYER_HELMET_I,
            ItemID.TURQUOISE_SLAYER_HELMET_I_25187,
            ItemID.HYDRA_SLAYER_HELMET,
            ItemID.HYDRA_SLAYER_HELMET_I,
            ItemID.HYDRA_SLAYER_HELMET_I_25189,
            ItemID.TWISTED_SLAYER_HELMET,
            ItemID.TWISTED_SLAYER_HELMET_I,
            ItemID.TWISTED_SLAYER_HELMET_I_25191,
            ItemID.TZKAL_SLAYER_HELMET,
            ItemID.TZKAL_SLAYER_HELMET_I,
            ItemID.TZKAL_SLAYER_HELMET_I_25914,
            ItemID.VAMPYRIC_SLAYER_HELMET,
            ItemID.VAMPYRIC_SLAYER_HELMET_I,
            ItemID.VAMPYRIC_SLAYER_HELMET_I_25908,
            ItemID.TZTOK_SLAYER_HELMET,
            ItemID.TZTOK_SLAYER_HELMET_I,
            ItemID.TZTOK_SLAYER_HELMET_I_25902
    );

    @Getter
    private static final List<Integer> noseProtection = ImmutableList.of(
            ItemID.NOSE_PEG,
            ItemID.SLAYER_HELMET,
            ItemID.SLAYER_HELMET_I,
            ItemID.SLAYER_HELMET_I_25177,
            ItemID.BLACK_SLAYER_HELMET,
            ItemID.BLACK_SLAYER_HELMET_I,
            ItemID.BLACK_SLAYER_HELMET_I_25179,
            ItemID.GREEN_SLAYER_HELMET,
            ItemID.GREEN_SLAYER_HELMET_I,
            ItemID.GREEN_SLAYER_HELMET_I_25181,
            ItemID.RED_SLAYER_HELMET,
            ItemID.RED_SLAYER_HELMET_I,
            ItemID.RED_SLAYER_HELMET_I_25183,
            ItemID.PURPLE_SLAYER_HELMET,
            ItemID.PURPLE_SLAYER_HELMET_I,
            ItemID.PURPLE_SLAYER_HELMET_I_25185,
            ItemID.TURQUOISE_SLAYER_HELMET,
            ItemID.TURQUOISE_SLAYER_HELMET_I,
            ItemID.TURQUOISE_SLAYER_HELMET_I_25187,
            ItemID.HYDRA_SLAYER_HELMET,
            ItemID.HYDRA_SLAYER_HELMET_I,
            ItemID.HYDRA_SLAYER_HELMET_I_25189,
            ItemID.TWISTED_SLAYER_HELMET,
            ItemID.TWISTED_SLAYER_HELMET_I,
            ItemID.TWISTED_SLAYER_HELMET_I_25191,
            ItemID.TZKAL_SLAYER_HELMET,
            ItemID.TZKAL_SLAYER_HELMET_I,
            ItemID.TZKAL_SLAYER_HELMET_I_25914,
            ItemID.VAMPYRIC_SLAYER_HELMET,
            ItemID.VAMPYRIC_SLAYER_HELMET_I,
            ItemID.VAMPYRIC_SLAYER_HELMET_I_25908,
            ItemID.TZTOK_SLAYER_HELMET,
            ItemID.TZTOK_SLAYER_HELMET_I,
            ItemID.TZTOK_SLAYER_HELMET_I_25902
    );

    @Getter
    private static final List<Integer> elitePyreLogs = ImmutableList.of(
            ItemID.MAGIC_PYRE_LOGS,
            ItemID.REDWOOD_PYRE_LOGS
    );

    @Getter
    private static final List<Integer> shadeRemains = ImmutableList.of(
            ItemID.LOAR_REMAINS,
            ItemID.PHRIN_REMAINS,
            ItemID.RIYL_REMAINS,
            ItemID.ASYN_REMAINS,
            ItemID.FIYR_REMAINS,
            ItemID.URIUM_REMAINS
    );

    @Getter
    private static final List<Integer> ahrimHood = ImmutableList.of(
            ItemID.AHRIMS_HOOD,
            ItemID.AHRIMS_HOOD_100,
            ItemID.AHRIMS_HOOD_75,
            ItemID.AHRIMS_HOOD_50,
            ItemID.AHRIMS_HOOD_25
    );

    @Getter
    private static final List<Integer> ahrimRobeSkirt = ImmutableList.of(
            ItemID.AHRIMS_ROBESKIRT,
            ItemID.AHRIMS_ROBESKIRT_100,
            ItemID.AHRIMS_ROBESKIRT_75,
            ItemID.AHRIMS_ROBESKIRT_50,
            ItemID.AHRIMS_ROBESKIRT_25
    );

    @Getter
    private static final List<Integer> ahrimRobeTop = ImmutableList.of(
            ItemID.AHRIMS_ROBETOP,
            ItemID.AHRIMS_ROBETOP_100,
            ItemID.AHRIMS_ROBETOP_75,
            ItemID.AHRIMS_ROBETOP_50,
            ItemID.AHRIMS_ROBETOP_25
    );

    @Getter
    private static final List<Integer> ahrimStaff = ImmutableList.of(
            ItemID.AHRIMS_STAFF,
            ItemID.AHRIMS_STAFF_100,
            ItemID.AHRIMS_STAFF_75,
            ItemID.AHRIMS_STAFF_50,
            ItemID.AHRIMS_STAFF_25
    );

    @Getter
    private static final List<Integer> karilCrossbow = ImmutableList.of(
            ItemID.KARILS_CROSSBOW,
            ItemID.KARILS_CROSSBOW_100,
            ItemID.KARILS_CROSSBOW_75,
            ItemID.KARILS_CROSSBOW_50,
            ItemID.KARILS_CROSSBOW_25
    );

    @Getter
    private static final List<Integer> karilCoif = ImmutableList.of(
            ItemID.KARILS_COIF,
            ItemID.KARILS_COIF_100,
            ItemID.KARILS_COIF_75,
            ItemID.KARILS_COIF_50,
            ItemID.KARILS_COIF_25
    );

    @Getter
    private static final List<Integer> karilSkirt = ImmutableList.of(
            ItemID.KARILS_LEATHERSKIRT,
            ItemID.KARILS_LEATHERSKIRT_100,
            ItemID.KARILS_LEATHERSKIRT_75,
            ItemID.KARILS_LEATHERSKIRT_50,
            ItemID.KARILS_LEATHERSKIRT_25
    );

    @Getter
    private static final List<Integer> karilTop = ImmutableList.of(
            ItemID.KARILS_LEATHERTOP,
            ItemID.KARILS_LEATHERTOP_100,
            ItemID.KARILS_LEATHERTOP_75,
            ItemID.KARILS_LEATHERTOP_50,
            ItemID.KARILS_LEATHERTOP_25
    );

    @Getter
    private static final List<Integer> dharokAxe = ImmutableList.of(
            ItemID.DHAROKS_GREATAXE,
            ItemID.DHAROKS_GREATAXE_100,
            ItemID.DHAROKS_GREATAXE_75,
            ItemID.DHAROKS_GREATAXE_50,
            ItemID.DHAROKS_GREATAXE_25
    );

    @Getter
    private static final List<Integer> dharokHelm = ImmutableList.of(
            ItemID.DHAROKS_HELM,
            ItemID.DHAROKS_HELM_100,
            ItemID.DHAROKS_HELM_75,
            ItemID.DHAROKS_HELM_50,
            ItemID.DHAROKS_HELM_25
    );

    @Getter
    private static final List<Integer> dharokBody = ImmutableList.of(
            ItemID.DHAROKS_PLATEBODY,
            ItemID.DHAROKS_PLATEBODY_100,
            ItemID.DHAROKS_PLATEBODY_75,
            ItemID.DHAROKS_PLATEBODY_50,
            ItemID.DHAROKS_PLATEBODY_25
    );

    @Getter
    private static final List<Integer> dharokLegs = ImmutableList.of(
            ItemID.DHAROKS_PLATELEGS,
            ItemID.DHAROKS_PLATELEGS_100,
            ItemID.DHAROKS_PLATELEGS_75,
            ItemID.DHAROKS_PLATELEGS_50,
            ItemID.DHAROKS_PLATELEGS_25
    );

    @Getter
    private static final List<Integer> guthanWarspear = ImmutableList.of(
            ItemID.GUTHANS_WARSPEAR,
            ItemID.GUTHANS_WARSPEAR_100,
            ItemID.GUTHANS_WARSPEAR_75,
            ItemID.GUTHANS_WARSPEAR_50,
            ItemID.GUTHANS_WARSPEAR_25
    );

    @Getter
    private static final List<Integer> guthanHelm = ImmutableList.of(
            ItemID.GUTHANS_HELM,
            ItemID.GUTHANS_HELM_100,
            ItemID.GUTHANS_HELM_75,
            ItemID.GUTHANS_HELM_50,
            ItemID.GUTHANS_HELM_25
    );

    @Getter
    private static final List<Integer> guthanBody = ImmutableList.of(
            ItemID.GUTHANS_PLATEBODY,
            ItemID.GUTHANS_PLATEBODY_100,
            ItemID.GUTHANS_PLATEBODY_75,
            ItemID.GUTHANS_PLATEBODY_50,
            ItemID.GUTHANS_PLATEBODY_25
    );

    @Getter
    private static final List<Integer> guthanSkirt = ImmutableList.of(
            ItemID.GUTHANS_CHAINSKIRT,
            ItemID.GUTHANS_CHAINSKIRT_100,
            ItemID.GUTHANS_CHAINSKIRT_75,
            ItemID.GUTHANS_CHAINSKIRT_50,
            ItemID.GUTHANS_CHAINSKIRT_25
    );

    @Getter
    private static final List<Integer> toragHammers = ImmutableList.of(
            ItemID.TORAGS_HAMMERS,
            ItemID.TORAGS_HAMMERS_100,
            ItemID.TORAGS_HAMMERS_75,
            ItemID.TORAGS_HAMMERS_50,
            ItemID.TORAGS_HAMMERS_25
    );

    @Getter
    private static final List<Integer> toragHelm = ImmutableList.of(
            ItemID.TORAGS_HELM,
            ItemID.TORAGS_HELM_100,
            ItemID.TORAGS_HELM_75,
            ItemID.TORAGS_HELM_50,
            ItemID.TORAGS_HELM_25
    );

    @Getter
    private static final List<Integer> toragBody = ImmutableList.of(
            ItemID.TORAGS_PLATEBODY,
            ItemID.TORAGS_PLATEBODY_100,
            ItemID.TORAGS_PLATEBODY_75,
            ItemID.TORAGS_PLATEBODY_50,
            ItemID.TORAGS_PLATEBODY_25
    );

    @Getter
    private static final List<Integer> toragLegs = ImmutableList.of(
            ItemID.TORAGS_PLATELEGS,
            ItemID.TORAGS_PLATELEGS_100,
            ItemID.TORAGS_PLATELEGS_75,
            ItemID.TORAGS_PLATELEGS_50,
            ItemID.TORAGS_PLATELEGS_25
    );

    @Getter
    private static final List<Integer> veracFlail = ImmutableList.of(
            ItemID.VERACS_FLAIL,
            ItemID.VERACS_FLAIL_100,
            ItemID.VERACS_FLAIL_75,
            ItemID.VERACS_FLAIL_50,
            ItemID.VERACS_FLAIL_25
    );

    @Getter
    private static final List<Integer> veracBrassard = ImmutableList.of(
            ItemID.VERACS_BRASSARD,
            ItemID.VERACS_BRASSARD_100,
            ItemID.VERACS_BRASSARD_75,
            ItemID.VERACS_BRASSARD_50,
            ItemID.VERACS_BRASSARD_25
    );

    @Getter
    private static final List<Integer> veracHelm = ImmutableList.of(
            ItemID.VERACS_HELM,
            ItemID.VERACS_HELM_100,
            ItemID.VERACS_HELM_75,
            ItemID.VERACS_HELM_50,
            ItemID.VERACS_HELM_25
    );

    @Getter
    private static final List<Integer> veracSkirt = ImmutableList.of(
            ItemID.VERACS_PLATESKIRT,
            ItemID.VERACS_PLATESKIRT_100,
            ItemID.VERACS_PLATESKIRT_75,
            ItemID.VERACS_PLATESKIRT_50,
            ItemID.VERACS_PLATESKIRT_25
    );
}