package scripts;

import lombok.Getter;
import lombok.Setter;
import scripts.Tasks.Task;

import java.util.ArrayList;
import java.util.List;

public enum Quests {


    BELOW_ICE_MOUNTAIN(0, 0, false, 1, "Below Ice Mountain"),
    BLACK_KNIGHTS_FORTRESS(130, 0, false, 3, "Black Knights' Fortress"),
    COOKS_ASSISTANT(29, 0, false, 1, "Cook's Assistant"),
    THE_CORSAIR_CURSE(0, 0, false, 2, "The Corsair Curse"),
    DEMON_SLAYER(0, 0, false, 3, "Demon Slayer"),
    DORICS_QUEST(31, 0, false, 1, "Doric's Quest"),
    DRAGON_SLAYER_I(176, 0, false, 2, "Dragon Slayer I"),
    ERNEST_THE_CHICKEN(32, 0, false, 4, "Ernest the Chicken"),
    GOBLIN_DIPLOMACY(62, 0, false, 5, "Goblin Diplomacy"),
    IMP_CATCHER(160, 0, false, 1, "Imp Catcher"),
    THE_KNIGHTS_SWORD(122, 0, false, 1, "The Knight's Sword"),
    MISTHALIN_MYSTERY(0, 0, false, 1, "Misthalin Mystery"),
    PIRATES_TREASURE(71, 0, false, 2, "Pirate's Treasure"),
    PRINCE_ALI_RESCUE(273, 0, false, 3, "Prince Ali Rescue"),
    THE_RESTLESS_GHOST(107, 0, false, 1, "The Restless Ghost"),
    ROMEO_AND_JULIET(144, 0, false, 5, "Romeo & Juliet"),
    RUNE_MYSTERIES(63, 0, false, 1, "Rune Mysteries"),
    SHEEP_SHEARER(179, 0, false, 1, "Sheep Shearer"),
    SHIELD_OF_ARRAV(0, 0, false, 1, "Shield of Arrav"),
    VAMPYRE_SLAYER(178, 0, false, 3, "Vampyre Slayer"),
    WITCHS_POTION(67, 0, false, 1, "Witch's Potion"),
    X_MARKS_THE_SPOT(0, 8063, true, 1, "X Marks the Spot"),


    ANIMAL_MAGNETISM(939, 0, false, 1, "Animal Magnetism"),
    ANOTHER_SLICE_OF_HAM(0, 0, false, 1, "Another Slice of H.A.M."),
    THE_ASCENT_OF_ARCEUUS(2071, 0, false, 1, "The Ascent of Arceuus"),
    BETWEEN_A_ROCK(0, 0, false, 2, "Between a Rock..."),
    BIG_CHOMPY_BIRD_HUNTING(293, 0, false, 2, "Big Chompy Bird Hunting"),
    BIOHAZARD(68, 0, false, 3, "Biohazard"),
    BONE_VOYAGE(1630, 0, false, 1, "Bone Voyage"),
    CABIN_FEVER(0, 0, false, 2, "Cabin Fever"),
    CLIENT_OF_KOUREND(1566, 0, false, 1, "Client of Kourend"),
    CLOCK_TOWER(0, 0, false, 1, "Clock Tower"),
    COLD_WAR(0, 0, false, 1, "Cold War"),
    CONTACT(0, 3274, true, 1, "Contact!"),
    CREATURE_OF_FENKENSTRAIN(0, 0, false, 2, "Creature of Fenkenstrain"),
    DARKNESS_OF_HALLOWVALE(0, 0, false, 2, "Darkness of Hallowvale"),
    DEATH_PLATEAU(314, 0, false, 1, "Death Plateau"),
    DEATH_TO_THE_DORGESHUUN(0, 0, false, 1, "Death to the Dorgeshuun"),
    THE_DEPTHS_OF_DESPAIR(1671, 0, false, 1, "The Depths of Despair"),
    DESERT_TREASURE(0, 0, false, 3, "Desert Treasure"),
    DEVIOUS_MINDS(0, 0, false, 1, "Devious Minds"),
    THE_DIG_SITE(131, 0, false, 2, "The Dig Site"),
    DRAGON_SLAYER_II(0, 0, false, 5, "Dragon Slayer II"),
    DREAM_MENTOR(0, 0, false, 2, "Dream Mentor"),
    DRUIDIC_RITUAL(80, 0, false, 4, "Druidic Ritual"),
    DWARF_CANNON(0, 0, false, 1, "Dwarf Cannon"),
    EADGARS_RUSE(0, 0, false, 1, "Eadgar's Ruse"),
    EAGLES_PEAK(0, 0, false, 2, "Eagles' Peak"),
    ELEMENTAL_WORKSHOP_I(299, 2067, true, 1, "Elemental Workshop I"),
    ELEMENTAL_WORKSHOP_II(0, 0, false, 1, "Elemental Workshop II"),
    ENAKHRAS_LAMENT(0, 0, false, 2, "Enakhra's Lament"),
    ENLIGHTENED_JOURNEY(0, 0, false, 1, "Enlightened Journey"),
    THE_EYES_OF_GLOUPHRIE(0, 0, false, 2, "The Eyes of Glouphrie"),
    FAIRYTALE_I_GROWING_PAINS(671, 1803, true, 2, "Fairytale I - Growing Pains"),
    FAIRYTALE_II_CURE_A_QUEEN(0, 2326, true, 2, "Fairytale II - Cure a Queen"),
    FAMILY_CREST(148, 0, false, 1, "Family Crest"),
    THE_FEUD(435, 0, false, 1, "The Feud"),
    FIGHT_ARENA(17, 0, false, 2, "Fight Arena"),
    FISHING_CONTEST(11, 0, false, 1, "Fishing Contest"),
    FORGETTABLE_TALE(0, 0, false, 2, "Forgettable Tale..."),
    THE_FORSAKEN_TOWER(0, 0, false, 1, "The Forsaken Tower"),
    THE_FREMENNIK_EXILES(0, 0, false, 2, "The Fremennik Exiles"),
    THE_FREMENNIK_ISLES(0, 0, false, 1, "The Fremennik Isles"),
    THE_FREMENNIK_TRIALS(347, 0, false, 3, "The Fremennik Trials"),
    GARDEN_OF_TRANQUILLITY(0, 0, false, 2, "Garden of Tranquillity"),
    GERTRUDES_CAT(180, 0, false, 1, "Gertrude's Cat"),
    GETTING_AHEAD(0, 0, false, 1, "Getting Ahead"),
    GHOSTS_AHOY(0, 217, true, 2, "Ghosts Ahoy"),
    THE_GIANT_DWARF(0, 0, false, 2, "The Giant Dwarf"),
    THE_GOLEM(437, 0, false, 1, "The Golem"),
    THE_GRAND_TREE(137, 0, false, 5, "The Grand Tree"),
    THE_GREAT_BRAIN_ROBBERY(0, 0, false, 2, "The Great Brain Robbery"),
    GRIM_TALES(0, 0, false, 1, "Grim Tales"),
    THE_HAND_IN_THE_SAND(0, 0, false, 1, "The Hand in the Sand"),
    HAUNTED_MINE(0, 0, false, 2, "Haunted Mine"),
    HAZEEL_CULT(223, 0, false, 1, "Hazeel Cult"),
    HEROES_QUEST(188, 0, false, 1, "Heroes' Quest"),
    HOLY_GRAIL(5, 0, false, 2, "Holy Grail"),
    HORROR_FROM_THE_DEEP(351, 0, false, 2, "Horror from the Deep"),
    ICTHLARINS_LITTLE_HELPER(0, 0, false, 2, "Icthlarin's Little Helper"),
    IN_AID_OF_THE_MYREQUE(0, 0, false, 2, "In Aid of the Myreque"),
    IN_SEARCH_OF_THE_MYREQUE(0, 0, false, 2, "In Search of the Myreque"),
    JUNGLE_POTION(175, 0, false, 1, "Jungle Potion"),
    KINGS_RANSOM(0, 0, false, 1, "King's Ransom"),
    A_KINGDOM_DIVIDED(0, 0, false, 2, "A Kingdom Divided"),
    LEGENDS_QUEST(0, 0, false, 4, "Legends' Quest"),
    LOST_CITY(147, 0, false, 3, "Lost City"),
    THE_LOST_TRIBE(0, 532, true, 1, "The Lost Tribe"),
    LUNAR_DIPLOMACY(0, 0, false, 2, "Lunar Diplomacy"),
    MAKING_FRIENDS_WITH_MY_ARM(0, 0, false, 2, "Making Friends with My Arm"),
    MAKING_HISTORY(0, 0, false, 3, "Making History"),
    MERLINS_CRYSTAL(14, 0, false, 6, "Merlin's Crystal"),
    MONKS_FRIEND(30, 0, false, 1, "Monk's Friend"),
    MONKEY_MADNESS_I(365, 0, false, 3, "Monkey Madness I"),
    MONKEY_MADNESS_II(0, 0, false, 4, "Monkey Madness II"),
    MOUNTAIN_DAUGHTER(0, 260, true, 2, "Mountain Daughter"),
    MOURNINGS_END_PART_I(0, 0, false, 2, "Mourning's End Part I"),
    MOURNINGS_END_PART_II(0, 0, false, 2, "Mourning's End Part II"),
    MURDER_MYSTERY(192, 0, false, 3, "Murder Mystery"),
    MY_ARMS_BIG_ADVENTURE(0, 0, false, 1, "My Arm's Big Adventure"),
    NATURE_SPIRIT(307, 0, false, 2, "Nature Spirit"),
    A_NIGHT_AT_THE_THEATRE(0, 0, false, 2, "A Night at the Theatre"),
    OBSERVATORY_QUEST(0, 0, false, 2, "Observatory Quest"),
    OLAFS_QUEST(0, 0, false, 1, "Olaf's Quest"),
    ONE_SMALL_FAVOUR(0, 0, false, 2, "One Small Favour"),
    PLAGUE_CITY(165, 0, false, 1, "Plague City"),
    A_PORCINE_OF_INTEREST(2748, 10582, true, 1, "A Porcine of Interest"),
    PRIEST_IN_PERIL(302, 0, false, 1, "Priest in Peril"),
    THE_QUEEN_OF_THIEVES(1672, 0, false, 1, "The Queen of Thieves"),
    RAG_AND_BONE_MAN_I(0, 0, false, 1, "Rag and Bone Man I"),
    RAG_AND_BONE_MAN_II(0, 0, false, 1, "Rag and Bone Man II"),
    RATCATCHERS(0, 0, false, 2, "Ratcatchers"),
    RFD_ANOTHER_COOKS_QUEST(681, 0, false, 1, "RFD/Another Cook's Quest"),
    RFD_DEFEATING_THE_CULINAROMANCER(0, 0, false, 1, "RFD/Defeating the Culinaromancer"),
    RFD_FREEING_EVIL_DAVE(0, 1878, true, 1, "RFD/Freeing Evil Dave"),
    RFD_FREEING_KING_AWOWOGEI(0, 1914, true, 1, "RFD/Freeing King Awowogei"),
    RFD_FREEING_PIRATE_PETE(0, 0, false, 1, "RFD/Freeing Pirate Pete"),
    RFD_FREEING_SIR_AMIK_VARZE(0, 1910, true, 1, "RFD/Freeing Sir Amik Varze"),
    RFD_FREEING_SKRACH_UGLOGWEE(0, 1904, true, 1, "RFD/Freeing Skrach Uglogwee"),
    RFD_FREEING_THE_GOBLIN_GENERALS(0, 679, true, 1, "RFD/Freeing the Goblin generals"),
    RFD_FREEING_THE_LUMBRIDGE_GUIDE(0, 1896, true, 1, "RFD/Freeing the Lumbridge Guide"),
    RFD_FREEING_THE_MOUNTAIN_DWARF(0, 681, true, 1, "RFD/Freeing the Mountain Dwarf"),
    RECRUITMENT_DRIVE(0, 657, true, 1, "Recruitment Drive"),
    REGICIDE(0, 0, false, 3, "Regicide"),
    ROVING_ELVES(0, 0, false, 1, "Roving Elves"),
    ROYAL_TROUBLE(0, 0, false, 1, "Royal Trouble"),
    RUM_DEAL(0, 0, false, 2, "Rum Deal"),
    SCORPION_CATCHER(0, 0, false, 1, "Scorpion Catcher"),
    SEA_SLUG(159, 0, false, 1, "Sea Slug"),
    SHADES_OF_MORTTON(339, 0, false, 3, "Shades of Mort'ton"),
    SHADOW_OF_THE_STORM(0, 1372, true, 1, "Shadow of the Storm"),
    SHEEP_HERDER(60, 0, false, 4, "Sheep Herder"),
    SHILO_VILLAGE(116, 0, false, 2, "Shilo Village"),
    SINS_OF_THE_FATHER(0, 0, false, 2, "Sins of the Father"),
    THE_SLUG_MENACE(0, 0, false, 1, "The Slug Menace"),
    SONG_OF_THE_ELVES(0, 0, false, 4, "Song of the Elves"),
    A_SOULS_BANE(0, 0, false, 1, "A Soul's Bane"),
    SPIRITS_OF_THE_ELID(0, 0, false, 2, "Spirits of the Elid"),
    SWAN_SONG(0, 0, false, 2, "Swan Song"),
    TAI_BWO_WANNAI_TRIO(0, 0, false, 2, "Tai Bwo Wannai Trio"),
    A_TAIL_OF_TWO_CATS(0, 0, false, 2, "A Tail of Two Cats"),
    TALE_OF_THE_RIGHTEOUS(0, 0, false, 1, "Tale of the Righteous"),
    A_TASTE_OF_HOPE(0, 0, false, 1, "A Taste of Hope"),
    TEARS_OF_GUTHIX(0, 0, false, 1, "Tears of Guthix"),
    TEMPLE_OF_IKOV(0, 0, false, 1, "Temple of Ikov"),
    THRONE_OF_MISCELLANIA(0, 0, false, 1, "Throne of Miscellania"),
    THE_TOURIST_TRAP(197, 0, false, 2, "The Tourist Trap"),
    TOWER_OF_LIFE(0, 0, false, 2, "Tower of Life"),
    TREE_GNOME_VILLAGE(111, 0, false, 2, "Tree Gnome Village"),
    TRIBAL_TOTEM(0, 0, false, 1, "Tribal Totem"),
    TROLL_ROMANCE(0, 0, false, 2, "Troll Romance"),
    TROLL_STRONGHOLD(317, 0, false, 1, "Troll Stronghold"),
    UNDERGROUND_PASS(162, 0, false, 5, "Underground Pass"),
    WANTED(0, 0, false, 1, "Wanted!"),
    WATCHTOWER(0, 0, false, 4, "Watchtower"),
    WATERFALL_QUEST(65, 0, false, 1, "Waterfall Quest"),
    WHAT_LIES_BELOW(992, 0, false, 1, "What Lies Below"),
    WITCHS_HOUSE(226, 0, false, 4, "Witch's House"),
    ZOGRE_FLESH_EATERS(507, 0, true, 1, "Zogre Flesh Eaters"),
    MAGE_ARENA_1(267, 0, false, 0, "MAGE_ARENA_1");


    @Getter
    private final int gameSetting;

    @Getter
    private List<Integer> varbitList = new ArrayList<>();

    @Getter
    @Setter
    private List<Task> questTasks = new ArrayList<>();

    @Getter
    @Setter
    private  boolean useVarbit;

    @Getter
    private final int questPointReward;

    @Getter
    private final String questName;

    Quests( int gameSetting, int questPointsEarned, String name) {
        this.gameSetting = gameSetting;
        this.useVarbit = false;
        this.questPointReward = questPointsEarned;
        this.questName = name;
    }

    Quests(List<Integer> varbits, int questPointsEarned, String name) {
        this(0, questPointsEarned, name);
        this.varbitList = varbits;
        this.useVarbit = true;
    }


    Quests( int gameSetting, int varbit, boolean useVarbit, int questPointsEarned, String name) {
        this.varbitList.add(varbit);

        this.gameSetting = gameSetting;
        this.useVarbit = useVarbit;
        this.questPointReward = questPointsEarned;
        this.questName = name;
    }

    Quests(int gameSetting, List<Integer> varbits, int questPointsEarned, String name) {
        this.varbitList.addAll(varbits);
        this.gameSetting = gameSetting;
        if (this.varbitList.size() >0  && this.varbitList.get(0) == 0){
            this.useVarbit = false;
        } else {
            this.useVarbit = true;
        }
        this.questPointReward = questPointsEarned;
        this.questName = name;
    }

    Quests( int gameSetting, List<Integer> varbits, List<Task> taskList, int questPointsEarned, String name) {
        this.varbitList.addAll(varbits);
        this.gameSetting = gameSetting;

        if (this.varbitList.size() >0  && this.varbitList.get(0) == 0){
            this.useVarbit = false;
        } else {
            this.useVarbit = true;
        }
        this.questTasks = taskList;
        this.questPointReward = questPointsEarned;
        this.questName = name;
    }



}
