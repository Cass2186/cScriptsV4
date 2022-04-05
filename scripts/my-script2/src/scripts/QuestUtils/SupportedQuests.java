package scripts.QuestUtils;

import com.allatori.annotations.DoNotRename;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.tribot.script.sdk.GameState;
import org.tribot.script.sdk.Quest;
import scripts.QuestPackages.APorcineOfInterest.APorcineOfInterest;
import scripts.QuestPackages.AnimalMagnetism.AnimalMagnetism;
import scripts.QuestPackages.AscentOfArceuus.AscentOfArceuus;
import scripts.QuestPackages.Barcrawl.BarCrawl;
import scripts.QuestPackages.BigChompyBirdHunting.BigChompyBirdHunting;
import scripts.QuestPackages.Biohazard.Biohazard;
import scripts.QuestPackages.BlackKnightsFortress.BlackKnightsFortress;
import scripts.QuestPackages.BoneVoyage.BoneVoyage;
import scripts.QuestPackages.ClientOfKourend.ClientOfKourend;
import scripts.QuestPackages.Contact.Contact;
import scripts.QuestPackages.CooksAssistant.CooksAssistant;
import scripts.QuestPackages.DeathPlateau.DeathPlateau;
import scripts.QuestPackages.DemonSlayer.DemonSlayer;
import scripts.QuestPackages.DepthsofDespair.DepthsOfDespair;
import scripts.QuestPackages.DigSite.DigSite;
import scripts.QuestPackages.DoricsQuest.DoricsQuest;
import scripts.QuestPackages.DragonSlayer.DragonSlayer;
import scripts.QuestPackages.DreamMentor.DreamMentor;
import scripts.QuestPackages.DruidicRitual.DruidicRitual;
import scripts.QuestPackages.DwarfCannon.DwarfCannon;
import scripts.QuestPackages.ElementalWorkshopI.ElementalWorkshop;
import scripts.QuestPackages.EnakhrasLament.EnakhrasLament;
import scripts.QuestPackages.EnterTheAbyss.EnterTheAbyss;
import scripts.QuestPackages.ErnestTheChicken.ErnestTheChicken;
import scripts.QuestPackages.FairyTalePt1.FairyTalePt1;
import scripts.QuestPackages.FamilyCrest.FamilyCrest;
import scripts.QuestPackages.FightArena.FightArena;
import scripts.QuestPackages.FishingContest.FishingContest;
import scripts.QuestPackages.FremTrials.FremTrials;
import scripts.QuestPackages.GertrudesCat.GertrudesCat;
import scripts.QuestPackages.GhostsAhoy.GhostsAhoy;
import scripts.QuestPackages.GoblinDiplomacy.GoblinDiplomacy;
import scripts.QuestPackages.GrandTree.GrandTree;
import scripts.QuestPackages.HauntedMine.HauntedMine;
import scripts.QuestPackages.HazeelCult.HazeelCult;
import scripts.QuestPackages.HeroesQuest.HeroesQuestBlackArmsGang;
import scripts.QuestPackages.HolyGrail.HolyGrail;
import scripts.QuestPackages.HorrorFromTheDeep.HorrorFromTheDeep;
import scripts.QuestPackages.ImpCatcher.ImpCatcher;
import scripts.QuestPackages.JunglePotion.JunglePotion;
import scripts.QuestPackages.KnightsSword.KnightsSword;
import scripts.QuestPackages.LostCity.LostCity;
import scripts.QuestPackages.LostTribe.LostTribe;
import scripts.QuestPackages.MerlinsCrystal.MerlinsCrystal;
import scripts.QuestPackages.MonkeyMadnessI.MonkeyMadnessI;
import scripts.QuestPackages.MonksFriend.MonksFriend;
import scripts.QuestPackages.MountainDaughter.MountainDaughter;
import scripts.QuestPackages.MurderMystery.MurderMystery;
import scripts.QuestPackages.NatureSpirit.NatureSpirit;
import scripts.QuestPackages.PlagueCity.PlagueCity;
import scripts.QuestPackages.PriestInPeril.PriestInPeril;
import scripts.QuestPackages.PrinceAliRescue.PrinceAliRescue;
import scripts.QuestPackages.RestlessGhost.RestlessGhost;
import scripts.QuestPackages.RomeoAndJuliet.RomeoAndJuliet;
import scripts.QuestPackages.RuneMysteries.RuneMysteries;
import scripts.QuestPackages.SeaSlug.SeaSlug;
import scripts.QuestPackages.ShadowOfTheStorm.ShadowOfTheStorm;
import scripts.QuestPackages.SheepShearer.SheepShearer;
import scripts.QuestPackages.ShieldOfArrav.BlackArmsGang;
import scripts.QuestPackages.TearsOfGuthix.TearsOfGuthix;
import scripts.QuestPackages.TempleOfIkov.TempleOfIkov;
import scripts.QuestPackages.TheFeud.TheFeud;
import scripts.QuestPackages.TheGolem.TheGolem;
import scripts.QuestPackages.TouristTrap.TouristTrap;
import scripts.QuestPackages.TreeGnomeVillage.TreeGnomeVillage;
import scripts.QuestPackages.TribalTotem.TribalTotem;
import scripts.QuestPackages.TrollStronghold.TrollStronghold;
import scripts.QuestPackages.UnderGroundPass.UndergroundPass;
import scripts.QuestPackages.VampyreSlayer.VampyreSlayer;
import scripts.QuestPackages.WaterfallQuest.WaterfallQuest;
import scripts.QuestPackages.WitchsHouse.WitchsHouse;
import scripts.QuestPackages.WitchsPotion.WitchsPotion;
import scripts.QuestPackages.XMarksTheSpot.XMarksTheSpot;
import scripts.QuestPackages.ZogreFleshEaters.ZogreFleshEaters;
import scripts.QuestPackages.icthlarinslittlehelper.Icthlarinslittlehelper;
import scripts.QuestPackages.lairoftarnrazorlor.TarnRoute;
import scripts.QuestSteps.QuestTask;

import java.util.Locale;


public enum SupportedQuests {

    //   IN_SEARCH_OF_KNOWLEDGE(3143, 8403, true),
    //   MAGE_ARENA_I(3146, 267, false),
    //   SKIPPY_AND_THE_MOGRES(3153, 1344, true),
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
    //  KINGS_RANSOM(387, 3888, true),
    //  MAKING_FRIENDS_WITH_MY_ARM(392, 6528, true),
    //  MONKEY_MADNESS_II(396, 5027, true),
    //  MOURNINGS_END_PART_I(3147, 517, false),
    //  MOURNINGS_END_PART_II(3148, 1103, true),
    //  OBSERVATORY_QUEST(3149, 112, false),
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
    // CREATURE_OF_FENKENSTRAIN(340, 399, false),
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
    // PIRATES_TREASURE(310, 71, false),
    // RAG_AND_BONE_MAN_I(3152, 714, false),
    // RATCATCHERS(412, 1404, true),
    // RECRUITMENT_DRIVE(414, 657, true),
    // ROVING_ELVES(416, 402, false),
    // ROYAL_TROUBLE(417, 2140, true),
    // RUM_DEAL(418, 600, false),
    // SCORPION_CATCHER(419, 76, false),
    // SHEEP_HERDER(423, 60, false),
    // SINS_OF_THE_FATHER(1276, 7255, true),
    // SONG_OF_THE_ELVES(603, 9016, true),
    // SWAN_SONG(428, 2098, true),
    // TAI_BWO_WANNAI_TRIO(429, 320, false),
    // THE_FREMENNIK_EXILES(3141, 9459, true),
    // THE_GIANT_DWARF(372, 571, true),
    // THE_GREAT_BRAIN_ROBBERY(375, 980, false),
    // THE_HAND_IN_THE_SAND(377, 1527, true),
    // THE_QUEEN_OF_THIEVES(409, 6037, true),
    // THE_SLUG_MENACE(425, 2610, true),
    // WHAT_LIES_BELOW(447, 3523, true),
    //COLD_WAR(338, 3293, true),
    //MY_ARMS_BIG_ADVENTURE(402, 2790, true),
    //THE_FREMENNIK_ISLES(367, 3311, true),
    //TOWER_OF_LIFE(437, 3337, true),
    //WANTED(444, 1051, true),
    //WATCHTOWER(445, 212, false),
    ALFRED_GRIMHANDS_BARCRAWL(322, 77, false), // new BarCrawl()),
    ANIMAL_MAGNETISM(331, 3185, true), // new AnimalMagnetism()),
    A_PORCINE_OF_INTEREST(3151, 10582, true), // new APorcineOfInterest()),
    BIG_CHOMPY_BIRD_HUNTING(334, 293, false), //new BigChompyBirdHunting()),
    BIOHAZARD(335, 68, false), //new Biohazard()),
    BLACK_KNIGHTS_FORTRESS(299, 130, false), // new BlackKnightsFortress()),
    BONE_VOYAGE(3135, 5795, true),// new BoneVoyage()),
    CLIENT_OF_KOUREND(3136, 5619, true),
    CONTACT(339, 3274, true),
    COOKS_ASSISTANT(300, 29, false),
    DEATH_PLATEAU(342, 314, false),
    DEMON_SLAYER(302, 2561, true), //, DemonSlayer.get()),
    DORICS_QUEST(3138, 31, false), //, DoricsQuest.get()),
    DRAGON_SLAYER_I(3139, 176, false), //, DragonSlayer.get()),
    DREAM_MENTOR(349, 3618, true), //, DreamMentor.get()),
    DRUIDIC_RITUAL(350, 80, false), //, DruidicRitual.get()),
    DWARF_CANNON(351, 0, false), //, DwarfCannon.get()),
    ENAKHRAS_LAMENT(356, 1560, true), //, EnakhrasLament.get()),
    ENTER_THE_ABYSS(3140, 492, false), //, EnterTheAbyss.get()),
    ERNEST_THE_CHICKEN(305, 32, false), //, ErnestTheChicken.get()),
    FAIRYTALE_I_GROWING_PAINS(359, 1803, true), //, FairyTalePt1.get()),
    FAMILY_CREST(361, 148, false), //, FamilyCrest.get()),
    FIGHT_ARENA(363, 17, false), //, FightArena.get()),
    FISHING_CONTEST(364, 11, false), //, FishingContest.get()),
    GERTRUDES_CAT(370, 180, false), //, GertrudesCat.get()),
    GHOSTS_AHOY(371, 217, true), //, GhostsAhoy.get()),
    GOBLIN_DIPLOMACY(306, 2378, true), //, GoblinDiplomacy.get()),
    HAUNTED_MINE(378, 382, false), //, HauntedMine.get()),
    HAZEEL_CULT(379, 223, false), //, HazeelCult.get()),
    HEROES_QUEST(3142, 188, false), //, HeroesQuestBlackArmsGang.get()),
    HOLY_GRAIL(381, 5, false), //, HolyGrail.get()),

    HORROR_FROM_THE_DEEP(382, 34, true), //, HorrorFromTheDeep.get()),
    ICTHLARINS_LITTLE_HELPER(383, 418, true), //, Icthlarinslittlehelper.get()),
    IMP_CATCHER(307, 160, false), //, ImpCatcher.get()),
    JUNGLE_POTION(386, 175, false), //, JunglePotion.get()),
    LAIR_OF_TARN_RAZORLOR(3144, 3290, true), //, TarnRoute.get()),
    LOST_CITY(389, 147, false), //, LostCity.get()),
    MERLINS_CRYSTAL(394, 14, false), //, MerlinsCrystal.get()),
    MONKEY_MADNESS_I(395, 365, false), //, MonkeyMadnessI.get()),
    MONKS_FRIEND(397, 30, false), //, MonksFriend.get()),
    MOUNTAIN_DAUGHTER(398, 260, true), //, MountainDaughter.get()),
    MURDER_MYSTERY(401, 192, false), //, MurderMystery.get()),
    NATURE_SPIRIT(403, 307, false), //, NatureSpirit.get()),
    PLAGUE_CITY(407, 165, false), //, PlagueCity.get()),
    PRIEST_IN_PERIL(408, 302, false), //, PriestInPeril.get()),
    PRINCE_ALI_RESCUE(311, 273, false), //, PrinceAliRescue.get()),
    RFD_COOK(-1, -1, false, Quest.RECIPE_FOR_DISASTER), //, PrinceAliRescue.get()),
    ROMEO_JULIET(313, 144, false), //, RomeoAndJuliet.get()),
    RUNE_MYSTERIES(314, 63, false), //, RuneMysteries.get()),
    SEA_SLUG(420, 159, false), //, SeaSlug.get()),
    SHADOW_OF_THE_STORM(422, 1372, true), //, ShadowOfTheStorm.get()),
    SHEEP_SHEARER(315, 179, false), //, SheepShearer.get()),
    // SHILO_VILLAGE(424, 116, false, Shilo),
    TEARS_OF_GUTHIX(433, 451, true), //, TearsOfGuthix.get()),
    TEMPLE_OF_IKOV(434, 26, false), //, TempleOfIkov.get()),
    THE_ASCENT_OF_ARCEUUS(542, 7856, true), //, AscentOfArceuus.get()),
    // THE_CORSAIR_CURSE(301, 6071, true),
    THE_DEPTHS_OF_DESPAIR(344, 6027, true), //, DepthsOfDespair.get()),
    THE_DIG_SITE(347, 131, false), //, DigSite.get()),
    THE_FEUD(362, 334, true), //, TheFeud.get()),
    THE_FREMENNIK_TRIALS(368, 347, false), //, FremTrials.get()),
    THE_GOLEM(373, 346, true), //, TheGolem.get()),
    THE_GRAND_TREE(374, 150, false, Quest.THE_GRAND_TREE), //, GrandTree.get()),
    THE_KNIGHTS_SWORD(308, 122, false, Quest.THE_KNIGHTS_SWORD), //, KnightsSword.get()),
    THE_LOST_TRIBE(390, 532, true, Quest.THE_LOST_TRIBE), //, LostTribe.get()),
    THE_RESTLESS_GHOST(312, 107, false, Quest.THE_RESTLESS_GHOST), //, RestlessGhost.get()),
    THE_TOURIST_TRAP(436, 197, false, Quest.THE_TOURIST_TRAP), //, TouristTrap.get()),
    TREE_GNOME_VILLAGE(438, 111, false, Quest.TREE_GNOME_VILLAGE), //, TreeGnomeVillage.get()),
    TRIBAL_TOTEM(439, 200, false, Quest.TRIBAL_TOTEM), //, TribalTotem.get()),
    TROLL_STRONGHOLD(441, 317, false, Quest.TROLL_STRONGHOLD), //, TrollStronghold.get()),
    UNDERGROUND_PASS(442, 161, false, Quest.UNDERGROUND_PASS), //, UndergroundPass.get()),
    VAMPYRE_SLAYER(1278, 178, false, Quest.VAMPYRE_SLAYER), //, VampyreSlayer.get()),
    WATERFALL_QUEST(3154, 65, false, Quest.WATERFALL_QUEST), //, WaterfallQuest.get()),
    WITCHS_HOUSE(448, 226, false, Quest.WITCHS_HOUSE), //, WitchsHouse.get()),
    WITCHS_POTION(318, 67, false, Quest.WITCHS_POTION), //, WitchsPotion.get()),
    X_MARKS_THE_SPOT(3155, 8063, true, Quest.X_MARKS_THE_SPOT), //, XMarksTheSpot.get()),*/
    ZOGRE_FLESH_EATERS(449, 487, true, Quest.ZOGRE_FLESH_EATERS), // new ZogreFleshEaters()),

    // special cases not picked up by parser
    // see link at getStateException()
    SHIELD_OF_ARRAV(316, 145, false, Quest.SHIELD_OF_ARRAV), // BlackArmsGang.get()),
    ELEMENTAL_WORKSHOP_I(354, 299, false, Quest.ELEMENTAL_WORKSHOP_I), // ElementalWorkshop.get())
    //CURSE_OF_THE_EMPTY_LORD(3137, 821, true),
    // THE_ENCHANTED_KEY(324, 1391, true),
    ;

    private final int id;
    private final int configId;
    private final boolean isVarbit;
    private Quest quest;

    // @Getter
    //private final QuestTask task;

    SupportedQuests(int id, int configId, boolean isVarbit) {
        this.id = id;
        this.configId = configId;
        this.isVarbit = isVarbit;

        this.quest = Quest.valueOf(this.toString());
    }

    SupportedQuests(int id, int configId, boolean isVarbit, Quest quest) {
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


}
