package scripts.QuestUtils;

import lombok.RequiredArgsConstructor;
import org.tribot.script.sdk.GameState;
import org.tribot.script.sdk.Quest;

@RequiredArgsConstructor
public enum SupportedQuests {

    BLACK_KNIGHTS_FORTRESS(299, 130, false),
    COOKS_ASSISTANT(300, 29, false),
    DEMON_SLAYER(302, 2561, true),
    DORICS_QUEST(3138, 31, false),
    DRAGON_SLAYER_I(3139, 176, false),
    ERNEST_THE_CHICKEN(305, 32, false),
    GOBLIN_DIPLOMACY(306, 2378, true),
    IMP_CATCHER(307, 160, false),
    THE_KNIGHTS_SWORD(308, 122, false),
   // PIRATES_TREASURE(310, 71, false),
    PRINCE_ALI_RESCUE(311, 273, false),
    THE_RESTLESS_GHOST(312, 107, false),
    ROMEO_JULIET(313, 144, false),
    RUNE_MYSTERIES(314, 63, false),
    SHEEP_SHEARER(315, 179, false),
    VAMPYRE_SLAYER(1278, 178, false),
    WITCHS_POTION(318, 67, false),
   // MISTHALIN_MYSTERY(309, 3468, true),
    THE_CORSAIR_CURSE(301, 6071, true),
    ANIMAL_MAGNETISM(331, 3185, true),
   // BETWEEN_A_ROCK(333, 299, true),
    BIG_CHOMPY_BIRD_HUNTING(334, 293, false),
    BIOHAZARD(335, 68, false),
    // CABIN_FEVER(336, 655, false),
    // CLOCK_TOWER(337, 10, false),
    CONTACT(339, 3274, true),
    ZOGRE_FLESH_EATERS(449, 487, true),
    // CREATURE_OF_FENKENSTRAIN(340, 399, false),
    // DARKNESS_OF_HALLOWVALE(341, 2573, true),
    // DEATH_TO_THE_DORGESHUUN(343, 2258, true),
    DEATH_PLATEAU(342, 314, false),
    // DESERT_TREASURE(345, 358, true),
    // DEVIOUS_MINDS(346, 1465, true),
    THE_DIG_SITE(347, 131, false),
    DRUIDIC_RITUAL(350, 80, false),
    DWARF_CANNON(351, 0, false),
    //  EADGARS_RUSE(352, 335, false),
    //  EAGLES_PEAK(353, 2780, true),
    //  ELEMENTAL_WORKSHOP_II(355, 2639, true),
    ENAKHRAS_LAMENT(356, 1560, true),
    //  ENLIGHTENED_JOURNEY(357, 2866, true),
    //  THE_EYES_OF_GLOUPHRIE(358, 2497, true),
    FAIRYTALE_I_GROWING_PAINS(359, 1803, true),
    //  FAIRYTALE_II_CURE_A_QUEEN(360, 2326, true),
     FAMILY_CREST(361, 148, false),
    THE_FEUD(362, 334, true),
    FIGHT_ARENA(363, 17, false),
    FISHING_CONTEST(364, 11, false),
    // FORGETTABLE_TALE(365, 822, true),
    THE_FREMENNIK_TRIALS(368, 347, false),
    WATERFALL_QUEST(3154, 65, false),
    //  GARDEN_OF_TRANQUILLITY(369, 961, true),
    GERTRUDES_CAT(370, 180, false),
    GHOSTS_AHOY(371, 217, true),
    // THE_GIANT_DWARF(372, 571, true),
    THE_GOLEM(373, 346, true),
    THE_GRAND_TREE(374, 150, false),
    // THE_HAND_IN_THE_SAND(377, 1527, true),
    HAUNTED_MINE(378, 382, false),
    HAZEEL_CULT(379, 223, false),
    HEROES_QUEST(3142, 188, false),
    HOLY_GRAIL(381, 5, false),
    HORROR_FROM_THE_DEEP(382, 34, true),
    ICTHLARINS_LITTLE_HELPER(383, 418, true),
    //  IN_AID_OF_THE_MYREQUE(384, 1990, true),
    //  IN_SEARCH_OF_THE_MYREQUE(385, 387, false),
    JUNGLE_POTION(386, 175, false),
    // LEGENDS_QUEST(3145, 139, false),
    LOST_CITY(389, 147, false),
    THE_LOST_TRIBE(390, 532, true),
    // LUNAR_DIPLOMACY(391, 2448, true),
    // MAKING_HISTORY(393, 1383, true),
    MERLINS_CRYSTAL(394, 14, false),
    MONKEY_MADNESS_I(395, 365, false),
    MONKS_FRIEND(397, 30, false),
    MOUNTAIN_DAUGHTER(398, 260, true),
    //  MOURNINGS_END_PART_I(3147, 517, false),
    //  MOURNINGS_END_PART_II(3148, 1103, true),
    MURDER_MYSTERY(401, 192, false),
    //MY_ARMS_BIG_ADVENTURE(402, 2790, true),
    NATURE_SPIRIT(403, 307, false),
    //  OBSERVATORY_QUEST(3149, 112, false),
    //  ONE_SMALL_FAVOUR(406, 416, false),
    PLAGUE_CITY(407, 165, false),
    PRIEST_IN_PERIL(408, 302, false),
    // RAG_AND_BONE_MAN_I(3152, 714, false),
    // RATCATCHERS(412, 1404, true),
    RECIPE_FOR_DISASTER(413, 1850, true),
    // RECRUITMENT_DRIVE(414, 657, true),
    //  REGICIDE(415, 328, false),
    // ROVING_ELVES(416, 402, false),
    // ROYAL_TROUBLE(417, 2140, true),
    // RUM_DEAL(418, 600, false),
    // SCORPION_CATCHER(419, 76, false),
    SEA_SLUG(420, 159, false),
    // THE_SLUG_MENACE(425, 2610, true),
    //  SHADES_OF_MORTTON(421, 339, false),
    SHADOW_OF_THE_STORM(422, 1372, true),
    // SHEEP_HERDER(423, 60, false),
    SHILO_VILLAGE(424, 116, false),
    //  A_SOULS_BANE(426, 2011, true),
    //  SPIRITS_OF_THE_ELID(427, 1444, true),
    // SWAN_SONG(428, 2098, true),
    // TAI_BWO_WANNAI_TRIO(429, 320, false),
    // A_TAIL_OF_TWO_CATS(430, 1028, true),
   TEARS_OF_GUTHIX(433, 451, true),
    TEMPLE_OF_IKOV(434, 26, false),
    //  THRONE_OF_MISCELLANIA(435, 359, false),
    THE_TOURIST_TRAP(436, 197, false),
    WITCHS_HOUSE(448, 226, false),
    TREE_GNOME_VILLAGE(438, 111, false),
    TRIBAL_TOTEM(439, 200, false),
    //  TROLL_ROMANCE(440, 385, false),
    TROLL_STRONGHOLD(441, 317, false),
    UNDERGROUND_PASS(442, 161, false),
    //WANTED(444, 1051, true),
    //WATCHTOWER(445, 212, false),
    //COLD_WAR(338, 3293, true),
    //THE_FREMENNIK_ISLES(367, 3311, true),
    //TOWER_OF_LIFE(437, 3337, true),
    // THE_GREAT_BRAIN_ROBBERY(375, 980, false),
    // WHAT_LIES_BELOW(447, 3523, true),
    //  OLAFS_QUEST(3150, 3534, true),
    //  ANOTHER_SLICE_OF_H_A_M(332, 3550, true),
    DREAM_MENTOR(349, 3618, true),
    //  GRIM_TALES(376, 2783, true),
    //  KINGS_RANSOM(387, 3888, true),
    //  MONKEY_MADNESS_II(396, 5027, true),
    CLIENT_OF_KOUREND(3136, 5619, true),
    //  RAG_AND_BONE_MAN_II(411, 714, false),
    BONE_VOYAGE(3135, 5795, true),
    // THE_QUEEN_OF_THIEVES(409, 6037, true),
    THE_DEPTHS_OF_DESPAIR(344, 6027, true),
    //  DRAGON_SLAYER_II(348, 6104, true),
    //  TALE_OF_THE_RIGHTEOUS(431, 6358, true),
    //  A_TASTE_OF_HOPE(432, 6396, true),
    //  MAKING_FRIENDS_WITH_MY_ARM(392, 6528, true),
    //  THE_FORSAKEN_TOWER(543, 7796, true),
    THE_ASCENT_OF_ARCEUUS(542, 7856, true),
    ENTER_THE_ABYSS(3140, 492, false),
    //  ARCHITECTURAL_ALLIANCE(320, 4982, true),
    //  BEAR_YOUR_SOUL(1275, 5078, true),
    ALFRED_GRIMHANDS_BARCRAWL(322, 77, false),
    //   THE_GENERALS_SHADOW(325, 3330, true),
    //   IN_SEARCH_OF_KNOWLEDGE(3143, 8403, true),
    //   SKIPPY_AND_THE_MOGRES(3153, 1344, true),
    //   MAGE_ARENA_I(3146, 267, false),
       LAIR_OF_TARN_RAZORLOR(3144, 3290, true),
    // FAMILY_PEST(329, 5347, true),
    // MAGE_ARENA_II(330, 6067, true),
    // DADDYS_HOME(1688, 10570, true),
    X_MARKS_THE_SPOT(3155, 8063, true),
    // SONG_OF_THE_ELVES(603, 9016, true),
    // THE_FREMENNIK_EXILES(3141, 9459, true),
    // SINS_OF_THE_FATHER(1276, 7255, true),
    A_PORCINE_OF_INTEREST(3151, 10582, true),
    //  GETTING_AHEAD(752, 693, true),
    //  BELOW_ICE_MOUNTAIN(2874, 12063, true),
    //  A_NIGHT_AT_THE_THEATRE(949, 12276, true),
    //  A_KINGDOM_DIVIDED(2971, 12296, true),

    // special cases not picked up by parser
    // see link at getStateException()
    SHIELD_OF_ARRAV(316, 145, false),
    ELEMENTAL_WORKSHOP_I(354, 299, false),
    CURSE_OF_THE_EMPTY_LORD(3137, 821, true),
    THE_ENCHANTED_KEY(324, 1391, true),
    ;

    private final int id;
    private final int configId;
    private final boolean isVarbit;

    /**
     * Gets the current step of this quest
     *
     * @return the current step of this quest
     */

}
