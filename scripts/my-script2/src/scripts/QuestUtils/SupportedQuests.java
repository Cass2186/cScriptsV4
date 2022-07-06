package scripts.QuestUtils;

import org.tribot.script.sdk.Log;
import org.tribot.script.sdk.Quest;


public enum SupportedQuests {

    //   IN_SEARCH_OF_KNOWLEDGE(3143, 8403, true),
    //   MAGE_ARENA_I(3146, 267, false),
       SKIPPY_AND_THE_MOGRES(3153, 1344, true, 0),
    //   THE_GENERALS_SHADOW(325, 3330, true),
    //  ANOTHER_SLICE_OF_H_A_M(332, 3550, true),
    //  ARCHITECTURAL_ALLIANCE(320, 4982, true),
    //  A_KINGDOM_DIVIDED(2971, 12296, true),
    //  A_NIGHT_AT_THE_THEATRE(949, 12276, true),
    //  A_SOULS_BANE(426, 2011, true),
    //  A_TASTE_OF_HOPE(432, 6396, true),
    //  BEAR_YOUR_SOUL(1275, 5078, true),
    //  BELOW_ICE_MOUNTAIN(2874, 12063, true),
    //  DRAGON_SLAYER_II(348, 6104, true),
    //  EADGARS_RUSE(352, 335, false),
    //  EAGLES_PEAK(353, 2780, true),
    //  ELEMENTAL_WORKSHOP_II(355, 2639, true),
    //  ENLIGHTENED_JOURNEY(357, 2866, true),
    //  FAIRYTALE_II_CURE_A_QUEEN(360, 2326, true),
    //  GARDEN_OF_TRANQUILLITY(369, 961, true),
    //  GETTING_AHEAD(752, 693, true),
    //  GRIM_TALES(376, 2783, true),
    //  IN_AID_OF_THE_MYREQUE(384, 1990, true),
    //  IN_SEARCH_OF_THE_MYREQUE(385, 387, false),
      KINGS_RANSOM(387, 3888, true, 1),
    //  MAKING_FRIENDS_WITH_MY_ARM(392, 6528, true),
    //  MONKEY_MADNESS_II(396, 5027, true),
    //  MOURNINGS_END_PART_I(3147, 517, false),
    //  MOURNINGS_END_PART_II(3148, 1103, true),

    //  OLAFS_QUEST(3150, 3534, true),
    //  ONE_SMALL_FAVOUR(406, 416, false),
    //  RAG_AND_BONE_MAN_II(411, 714, false),
    //  REGICIDE(415, 328, false),
    //  SHADES_OF_MORTTON(421, 339, false),
    //  SPIRITS_OF_THE_ELID(427, 1444, true),
    //  TALE_OF_THE_RIGHTEOUS(431, 6358, true),
    //  THE_EYES_OF_GLOUPHRIE(358, 2497, true),
    //  THE_FORSAKEN_TOWER(543, 7796, true),
    //  THRONE_OF_MISCELLANIA(435, 359, false),
    //  TROLL_ROMANCE(440, 385, false),
    // A_TAIL_OF_TWO_CATS(430, 1028, true),
    // BETWEEN_A_ROCK(333, 299, true),
    // CABIN_FEVER(336, 655, false),
    // CLOCK_TOWER(337, 10, false),
     CREATURE_OF_FENKENSTRAIN(340, 399, false, 2),
    // DADDYS_HOME(1688, 10570, true),
    // DARKNESS_OF_HALLOWVALE(341, 2573, true),
    // DEATH_TO_THE_DORGESHUUN(343, 2258, true),
    // DESERT_TREASURE(345, 358, true),
    // DEVIOUS_MINDS(346, 1465, true),
    // FAMILY_PEST(329, 5347, true),
    // FORGETTABLE_TALE(365, 822, true),
    // LEGENDS_QUEST(3145, 139, false),
    LUNAR_DIPLOMACY(391, 2448, true),
    // MAGE_ARENA_II(330, 6067, true),
    // MAKING_HISTORY(393, 1383, true),
    // MISTHALIN_MYSTERY(309, 3468, true),
   PIRATES_TREASURE(310, 71, false, Quest.PIRATES_TREASURE, 2),
    // RAG_AND_BONE_MAN_I(3152, 714, false),
    // RATCATCHERS(412, 1404, true),
    // RECRUITMENT_DRIVE(414, 657, true),
    // ROVING_ELVES(416, 402, false),
    // ROYAL_TROUBLE(417, 2140, true),
     RUM_DEAL(418, 600, false, 2),
    // SCORPION_CATCHER(419, 76, false),
    // SHEEP_HERDER(423, 60, false),
    // SINS_OF_THE_FATHER(1276, 7255, true),
    // SONG_OF_THE_ELVES(603, 9016, true),
    // SWAN_SONG(428, 2098, true),
    // TAI_BWO_WANNAI_TRIO(429, 320, false),
    // THE_FREMENNIK_EXILES(3141, 9459, true),
    // THE_GIANT_DWARF(372, 571, true),
    // THE_GREAT_BRAIN_ROBBERY(375, 980, false),
    THE_HAND_IN_THE_SAND(377, 1527, true, 1),
    // THE_QUEEN_OF_THIEVES(409, 6037, true),
    // THE_SLUG_MENACE(425, 2610, true),
    // WHAT_LIES_BELOW(447, 3523, true),
    //COLD_WAR(338, 3293, true),
    //MY_ARMS_BIG_ADVENTURE(402, 2790, true),
    //THE_FREMENNIK_ISLES(367, 3311, true),
    //TOWER_OF_LIFE(437, 3337, true),
    //WANTED(444, 1051, true),
    //WATCHTOWER(445, 212, false),
    ALFRED_GRIMHANDS_BARCRAWL(322, 77, false, 0), // new BarCrawl()),
    ANIMAL_MAGNETISM(331, 3185, true, Quest.ANIMAL_MAGNETISM,1), // new AnimalMagnetism()),
    A_PORCINE_OF_INTEREST(3151, 10582, true, Quest.A_PORCINE_OF_INTEREST, 1), // new APorcineOfInterest()),
    BIG_CHOMPY_BIRD_HUNTING(334, 293, false, Quest.BIG_CHOMPY_BIRD_HUNTING, 2), //new BigChompyBirdHunting()),
    BIOHAZARD(335, 68, false, Quest.BIOHAZARD, 3), //new Biohazard()),
    BLACK_KNIGHTS_FORTRESS(299, 130, false, Quest.BLACK_KNIGHTS_FORTRESS, 3), // new BlackKnightsFortress()),
    BONE_VOYAGE(3135, 5795, true, Quest.BONE_VOYAGE, 1),// new BoneVoyage()),
    CLIENT_OF_KOUREND(3136, 5619, true, Quest.CLIENT_OF_KOUREND, 1),
    CONTACT(339, 3274, true, Quest.CONTACT, 1),
    COOKS_ASSISTANT(300, 29, false, Quest.COOKS_ASSISTANT, 1),
    DEATH_PLATEAU(342, 314, false, Quest.DEATH_PLATEAU, 1),
    DEMON_SLAYER(302, 2561, true, Quest.DEMON_SLAYER, 3), //, DemonSlayer.get()),
    DORICS_QUEST(3138, 31, false, Quest.DORICS_QUEST, 1), //, DoricsQuest.get()),
    DRAGON_SLAYER_I(3139, 176, false, Quest.DRAGON_SLAYER_I, 2 ), //, DragonSlayer.get()),
    DREAM_MENTOR(349, 3618, true, Quest.DREAM_MENTOR, 2), //, DreamMentor.get()),
    DRUIDIC_RITUAL(350, 80, false, Quest.DRUIDIC_RITUAL, 4), //, DruidicRitual.get()),
    DWARF_CANNON(351, 0, false, Quest.DWARF_CANNON, 1), //, DwarfCannon.get()),
    ENAKHRAS_LAMENT(356, 1560, true, Quest.ENAKHRAS_LAMENT, 2), //, EnakhrasLament.get()),
    ENTER_THE_ABYSS(3140, 492, false), //, EnterTheAbyss.get()),
    ERNEST_THE_CHICKEN(305, 32, false, Quest.ERNEST_THE_CHICKEN, 4), //, ErnestTheChicken.get()),
    FAIRYTALE_I_GROWING_PAINS(359, 1803, true, Quest.FAIRYTALE_I_GROWING_PAINS, 2), //, FairyTalePt1.get()),
    FAMILY_CREST(361, 148, false, Quest.FAMILY_CREST, 1), //, FamilyCrest.get()),
    FIGHT_ARENA(363, 17, false, Quest.FIGHT_ARENA, 2), //, FightArena.get()),
    FISHING_CONTEST(364, 11, false, Quest.FISHING_CONTEST, 1), //, FishingContest.get()),
    GERTRUDES_CAT(370, 180, false, Quest.GERTRUDES_CAT, 1), //, GertrudesCat.get()),
    GHOSTS_AHOY(371, 217, true, Quest.GHOSTS_AHOY ,2 ), //, GhostsAhoy.get()),
    GOBLIN_DIPLOMACY(306, 2378, true, Quest.GOBLIN_DIPLOMACY, 5), //, GoblinDiplomacy.get()),
    HAUNTED_MINE(378, 382, false, Quest.HAUNTED_MINE, 2), //, HauntedMine.get()),
    HAZEEL_CULT(379, 223, false, Quest.HAZEEL_CULT, 1), //, HazeelCult.get()),
    HEROES_QUEST(3142, 188, false, Quest.HEROES_QUEST, 1), //, HeroesQuestBlackArmsGang.get()),
    HOLY_GRAIL(381, 5, false, Quest.HOLY_GRAIL, 2), //, HolyGrail.get()),

    HORROR_FROM_THE_DEEP(382, 34, true, Quest.HORROR_FROM_THE_DEEP ,2), //, HorrorFromTheDeep.get()),
    ICTHLARINS_LITTLE_HELPER(383, 418, true, Quest.ICTHLARINS_LITTLE_HELPER ,2), //, Icthlarinslittlehelper.get()),
    IMP_CATCHER(307, 160, false, Quest.IMP_CATCHER, 1), //, ImpCatcher.get()),
    JUNGLE_POTION(386, 175, false, Quest.JUNGLE_POTION , 1), //, JunglePotion.get()),
    LAIR_OF_TARN_RAZORLOR(3144, 3290, true), //, TarnRoute.get()),
    LOST_CITY(389, 147, false, Quest.LOST_CITY, 3), //, LostCity.get()),
    MERLINS_CRYSTAL(394, 14, false, Quest.MERLINS_CRYSTAL, 6), //, MerlinsCrystal.get()),
    MONKEY_MADNESS_I(395, 365, false, Quest.MONKEY_MADNESS_I, 3), //, MonkeyMadnessI.get()),
    MONKS_FRIEND(397, 30, false, Quest.MONKS_FRIEND, 1), //, MonksFriend.get()),
    MOUNTAIN_DAUGHTER(398, 260, true, Quest.MOUNTAIN_DAUGHTER, 2), //, MountainDaughter.get()),
    MURDER_MYSTERY(401, 192, false, Quest.MURDER_MYSTERY, 3), //, MurderMystery.get()),
    NATURE_SPIRIT(403, 307, false, Quest.NATURE_SPIRIT, 2), //, NatureSpirit.get()),
    OBSERVATORY_QUEST(3149, 112, false, Quest.OBSERVATORY_QUEST, 2),
    PLAGUE_CITY(407, 165, false, Quest.PLAGUE_CITY ,1), //, PlagueCity.get()),
    PRIEST_IN_PERIL(408, 302, false, Quest.PRIEST_IN_PERIL,1), //, PriestInPeril.get()),
    PRINCE_ALI_RESCUE(311, 273, false, Quest.PRINCE_ALI_RESCUE,3), //, PrinceAliRescue.get()),
    RFD_COOK(-1, -1, false, Quest.RECIPE_FOR_DISASTER, 1), //, PrinceAliRescue.get()),
    RFD_PIRATE_PETE(-1, -1, false, Quest.RECIPE_FOR_DISASTER, 1), //, PrinceAliRescue.get()),
    RFD_SKRATCH(-1, -1, false, Quest.RECIPE_FOR_DISASTER, 1), //, PrinceAliRescue.get()),
    ROMEO_JULIET(313, 144, false, Quest.ROMEO_JULIET, 5), //, RomeoAndJuliet.get()),
    RUNE_MYSTERIES(314, 63, false, Quest.RUNE_MYSTERIES,1 ), //, RuneMysteries.get()),
    SEA_SLUG(420, 159, false, Quest.SEA_SLUG, 1), //, SeaSlug.get()),
    SHADOW_OF_THE_STORM(422, 1372, true, Quest.SHADOW_OF_THE_STORM, 1), //, ShadowOfTheStorm.get()),
    SHEEP_SHEARER(315, 179, false, Quest.SHEEP_SHEARER, 1), //, SheepShearer.get()),
    SHILO_VILLAGE(424, 116, false, Quest.SHILO_VILLAGE, 2),
    TEARS_OF_GUTHIX(433, 451, true, Quest.TEARS_OF_GUTHIX, 1), //, TearsOfGuthix.get()),
    TEMPLE_OF_IKOV(434, 26, false, Quest.TEMPLE_OF_IKOV ,1 ), //, TempleOfIkov.get()),
    THE_ASCENT_OF_ARCEUUS(542, 7856, true, Quest.THE_ASCENT_OF_ARCEUUS, 1), //, AscentOfArceuus.get()),
    // THE_CORSAIR_CURSE(301, 6071, true),
    THE_DEPTHS_OF_DESPAIR(344, 6027, true, Quest.THE_DEPTHS_OF_DESPAIR, 1), //, DepthsOfDespair.get()),
    THE_DIG_SITE(347, 131, false, Quest.THE_DIG_SITE, 2), //, DigSite.get()),
    THE_FEUD(362, 334, true, Quest.THE_FEUD ,1 ), //, TheFeud.get()),
    THE_FREMENNIK_TRIALS(368, 347, false, Quest.THE_FREMENNIK_TRIALS, 3), //, FremTrials.get()),
    THE_GOLEM(373, 346, true, Quest.THE_GOLEM, 1), //, TheGolem.get()),
    THE_GRAND_TREE(374, 150, false, Quest.THE_GRAND_TREE, 5), //, GrandTree.get()),
    THE_KNIGHTS_SWORD(308, 122, false, Quest.THE_KNIGHTS_SWORD , 1), //, KnightsSword.get()),
    THE_LOST_TRIBE(390, 532, true, Quest.THE_LOST_TRIBE, 1), //, LostTribe.get()),
    THE_RESTLESS_GHOST(312, 107, false, Quest.THE_RESTLESS_GHOST, 1), //, RestlessGhost.get()),
    THE_TOURIST_TRAP(436, 197, false, Quest.THE_TOURIST_TRAP, 2), //, TouristTrap.get()),
    TREE_GNOME_VILLAGE(438, 111, false, Quest.TREE_GNOME_VILLAGE, 2), //, TreeGnomeVillage.get()),
    TRIBAL_TOTEM(439, 200, false, Quest.TRIBAL_TOTEM, 1), //, TribalTotem.get()),
    TROLL_STRONGHOLD(441, 317, false, Quest.TROLL_STRONGHOLD, 1), //, TrollStronghold.get()),
    UNDERGROUND_PASS(442, 161, false, Quest.UNDERGROUND_PASS, 5), //, UndergroundPass.get()),
    VAMPYRE_SLAYER(1278, 178, false, Quest.VAMPYRE_SLAYER, 3), //, VampyreSlayer.get()),
    WATERFALL_QUEST(3154, 65, false, Quest.WATERFALL_QUEST, 1), //, WaterfallQuest.get()),
    WITCHS_HOUSE(448, 226, false, Quest.WITCHS_HOUSE, 1), //, WitchsHouse.get()),
    WITCHS_POTION(318, 67, false, Quest.WITCHS_POTION, 1), //, WitchsPotion.get()),
    X_MARKS_THE_SPOT(3155, 8063, true, Quest.X_MARKS_THE_SPOT, 1), //, XMarksTheSpot.get()),*/
    ZOGRE_FLESH_EATERS(449, 487, true, Quest.ZOGRE_FLESH_EATERS, 1), // new ZogreFleshEaters()),

    // special cases not picked up by parser
    // see link at getStateException()
    SHIELD_OF_ARRAV(316, 145, false, Quest.SHIELD_OF_ARRAV, 1), // BlackArmsGang.get()),
    ELEMENTAL_WORKSHOP_I(354, 299, false, Quest.ELEMENTAL_WORKSHOP_I, 1), // ElementalWorkshop.get())
    //CURSE_OF_THE_EMPTY_LORD(3137, 821, true),
    // THE_ENCHANTED_KEY(324, 1391, true),
    ;

    private final int id;
    private final int configId;
    private final boolean isVarbit;
    private Quest quest;
    private int questPoints = 1;

    // @Getter
    //private final QuestTask task;

    SupportedQuests(int id, int configId, boolean isVarbit) {
        this.id = id;
        this.configId = configId;
        this.isVarbit = isVarbit;

        this.quest = Quest.valueOf(this.toString());
    }

    SupportedQuests(int id, int configId, boolean isVarbit, int questPoints) {
        this.id = id;
        this.configId = configId;
        this.isVarbit = isVarbit;

        this.quest = Quest.valueOf(this.toString());
        this.questPoints = questPoints;
    }

    SupportedQuests(int id, int configId, boolean isVarbit, Quest quest) {
        this.id = id;
        this.configId = configId;
        this.isVarbit = isVarbit;
        this.quest = quest;
    }

    SupportedQuests(int id, int configId, boolean isVarbit, Quest quest, int questPoints) {
        this.id = id;
        this.configId = configId;
        this.isVarbit = isVarbit;
        this.quest = quest;
        this.questPoints = questPoints;
    }


    SupportedQuests(int id, int configId, boolean isVarbit, Quest quest, boolean isCompleteCheckOverride) {
        this.id = id;
        this.configId = configId;
        this.isVarbit = isVarbit;
        this.quest = quest;
    }

    public String getQuestName() {
        return this.toString().toLowerCase().replace("_", " ");
    }

    public boolean isComplete() {
        if (this.quest != null)
            return quest.getState().equals(Quest.State.COMPLETE);

        return false;
    }

    public static int getTotalQP(){
        int i = 0;
        for (SupportedQuests q : SupportedQuests.values()){
            i = i + q.questPoints;
        }
        Log.info("Supports " + i + " total QP");
        return i;
    }


}
