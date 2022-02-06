package scripts.QuestPackages.LegendsQuest;

import com.sun.javafx.charts.Legend;
import dax.walker.utils.AccurateMouse;
import dax.walker.utils.camera.DaxCamera;
import dax.walker_engine.interaction_handling.NPCInteraction;
import org.tribot.api.General;
import org.tribot.api2007.*;
import org.tribot.api2007.Objects;
import org.tribot.api2007.ext.Filters;
import org.tribot.api2007.types.*;
import org.tribot.script.sdk.Bank;
import org.tribot.script.sdk.GameState;
import org.tribot.script.sdk.Log;
import org.tribot.script.sdk.Widgets;
import org.tribot.script.sdk.tasks.Amount;
import org.tribot.script.sdk.tasks.BankTask;
import org.tribot.script.sdk.tasks.EquipmentReq;
import org.tribot.script.sdk.walking.GlobalWalking;
import scripts.*;
import scripts.Listeners.InterfaceListener;
import scripts.Listeners.InterfaceObserver;
import scripts.Listeners.InventoryObserver;
import scripts.QuestPackages.KourendFavour.ArceuusLibrary.State;
import scripts.QuestPackages.KourendFavour.ArceuusLibrary.Statistics;
import scripts.QuestPackages.MurderMystery.MurderMystery;
import scripts.QuestSteps.*;
import scripts.Requirements.*;
import scripts.Requirements.Util.ConditionalStep;
import scripts.Requirements.Util.Conditions;
import scripts.Requirements.Util.LogicType;
import scripts.Tasks.Priority;
import scripts.Timer;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.function.Predicate;
import java.util.regex.Matcher;

public class LegendsQuest implements QuestTask, InterfaceListener {


    private static LegendsQuest quest;

    public static LegendsQuest get() {
        return quest == null ? quest = new LegendsQuest() : quest;
    }


    ItemReq axe = new ItemReq(ItemID.RUNE_AXE);
    ItemReq machete = new ItemReq(ItemID.MACHETE);

    ItemReq radimusNotes = new ItemReq(ItemID.RADIMUS_NOTES);
    //	radimusNotes.setTooltip("You can get another from Radimus in the Legends' Guild");
    ItemReq papyrus3 = new ItemReq(ItemID.PAPYRUS, 3);
    ItemReq charcoal3 = new ItemReq(ItemID.CHARCOAL);
    ItemReq radimusNotesHighlight = new ItemReq(ItemID.RADIMUS_NOTES);
    //radimusNotesHighlight.setTooltip("You can get another from Radimus in the Legends' Guild");


    ItemReq ItemReqcompleteNotes = new ItemReq(715);
    //	completeNotes.setTooltip("You can get another from Radimus in the Legends' Guild, and you'll need to re-sketch the jungle");

    ItemReq completeNotesHighlighted = new ItemReq(715);
    //	completeNotesHighlighted.setTooltip("You can get another from Radimus in the Legends' Guild, and you'll need to re-sketch the jungle");


    ItemReq anyNotes = new ItemReq(ItemID.RADIMUS_NOTES);
    //	anyNotes.addAlternates(ItemID.RADIMUS_NOTES_715);

    ItemReq sketch = new ItemReq(ItemID.SKETCH);
    //	sketch.setTooltip("You can get another by summoning Gujuo with the bull roarer again");

    ItemReq papyrus = new ItemReq(ItemID.PAPYRUS);
    ItemReq charcoal = new ItemReq(ItemID.CHARCOAL);

    ItemReq bullRoarer = new ItemReq(ItemID.BULL_ROARER);
    //	bullRoarer.setTooltip("You can get another by using a complete Radimus notes on a Jungle Forester");
    ItemReq bullRoarerHighlight = new ItemReq("Bull roarer", ItemID.BULL_ROARER);
    //	bullRoarerHighlight.setTooltip("You can get another by using a complete Radimus notes on a Jungle Forester");

    ItemReq lockpick = new ItemReq("Lockpick", ItemID.LOCKPICK);
    ItemReq soulRune = new ItemReq("Soul rune", ItemID.SOUL_RUNE);
    ItemReq soulRuneHighlight = new ItemReq("Soul rune", ItemID.SOUL_RUNE);

    ItemReq mindRune = new ItemReq("Mind rune", ItemID.MIND_RUNE);
    ItemReq mindRuneHighlight = new ItemReq("Mind rune", ItemID.MIND_RUNE);

    ItemReq earthRune = new ItemReq("Earth rune", ItemID.EARTH_RUNE);
    ItemReq earthRuneHighlight = new ItemReq("Earth rune", ItemID.EARTH_RUNE);

    ItemReq lawRune2 = new ItemReq("Law rune", ItemID.LAW_RUNE, 2);
    ItemReq lawRuneHighlight = new ItemReq("Law rune", ItemID.LAW_RUNE);

    ItemReq opal = new ItemReq("Opal", ItemID.OPAL);
    ItemReq jade = new ItemReq("Jade", ItemID.JADE);
    ItemReq topaz = new ItemReq("Red topaz", ItemID.RED_TOPAZ);
    ItemReq sapphire = new ItemReq("Sapphire", ItemID.SAPPHIRE);
    ItemReq emerald = new ItemReq("Emerald", ItemID.EMERALD);
    ItemReq ruby = new ItemReq("Ruby", ItemID.RUBY);
    ItemReq diamond = new ItemReq("Diamond", ItemID.DIAMOND);

    ItemReq opalHighlighted = new ItemReq("Opal", ItemID.OPAL);
    ItemReq jadeHighlighted = new ItemReq("Jade", ItemID.JADE);
    ItemReq topazHighlighted = new ItemReq("Red topaz", ItemID.RED_TOPAZ);
    ItemReq sapphireHighlighted = new ItemReq("Sapphire", ItemID.SAPPHIRE);
    ItemReq emeraldHighlighted = new ItemReq("Emerald", ItemID.EMERALD);
    ItemReq rubyHighlighted = new ItemReq("Ruby", ItemID.RUBY);
    ItemReq diamondHighlighted = new ItemReq("Diamond", ItemID.DIAMOND);


    ItemReq pickaxe = new ItemReq(ItemID.RUNE_PICKAXE);

    ItemReq bindingBook = new ItemReq("Binding book", ItemID.BINDING_BOOK);
    ItemReq bindingBookHighlighted = new ItemReq("Binding book", ItemID.BINDING_BOOK);

    ItemReq goldBar2 = new ItemReq("Gold bar", ItemID.GOLD_BAR, 2);

    ItemReq hammer = new ItemReq(ItemID.HAMMER);

    ItemReq goldBowl = new ItemReq("Gold bowl", ItemID.GOLD_BOWL);
    //goldBowl.addAlternates(ItemID.GOLDEN_BOWL,ItemID.GOLDEN_BOWL_724,ItemID.GOLDEN_BOWL_725,ItemID.GOLDEN_BOWL_726,ItemID.BLESSED_GOLD_BOWL);
    ItemReq goldBowlHighlighted = new ItemReq("Gold bowl", ItemID.GOLD_BOWL);


    ItemReq combatGear = new ItemReq("Combat gear, food and potions", -1, -1);


    ItemReq goldBowlBlessed = new ItemReq("Blessed gold bowl", ItemID.BLESSED_GOLD_BOWL);

    ItemReq goldBowlFull = new ItemReq("Golden bowl", ItemID.GOLDEN_BOWL_726);
    ItemReq goldBowlFullHighlighted = new ItemReq("Golden bowl", ItemID.GOLDEN_BOWL_726);
    //	goldBowlFullHighlighted.setTooltip("You can fill another gold bowl from the water pool using a reed");
//
    ItemReq reed = new ItemReq("Hollow reed", ItemID.HOLLOW_REED);

    ItemReq yommiSeeds = new ItemReq("Yommi tree seeds", ItemID.YOMMI_TREE_SEEDS);


    ItemReq germinatedSeeds = new ItemReq("Yommi tree seeds", ItemID.YOMMI_TREE_SEEDS_736);
    //germinatedSeeds.setTooltip("You can get more seeds from Ungadulu, and use sacred water on them");
    ItemReq germinatedSeedsHighlighted = new ItemReq("Yommi tree seeds", ItemID.YOMMI_TREE_SEEDS_736);
    //	germinatedSeedsHighlighted.setTooltip("You can get more seeds from Ungadulu, and use sacred water on them");

    ItemReq runeOrDragonAxe = new ItemReq(ItemID.RUNE_AXE);

    ItemReq ardrigal = new ItemReq("Ardrigal", ItemID.ARDRIGAL);
    //	ardrigal.setTooltip("You can find some in the palm trees north east of Tai Bwo Wannai");

    ItemReq snakeWeed = new ItemReq("Snake weed", ItemID.SNAKE_WEED);
    //snakeWeed.setTooltip("You can find some in the marshy jungle vines south west of Tai Bwo Wannai");

    ItemReq vialOfWater = new ItemReq("Vial of water", ItemID.VIAL_OF_WATER);

    ItemReq cosmic3 = new ItemReq("Cosmic rune", ItemID.COSMIC_RUNE, 3);

    ItemReq elemental30 = new ItemReq("Water runes", ItemID.WATER_RUNE, 30);
    //elemental30.addAlternates(ItemID.FIRE_RUNE,ItemID.EARTH_RUNE,ItemID.AIR_RUNE);


    ItemReq unpoweredOrb = new ItemReq("Unpowered orb", ItemID.UNPOWERED_ORB);

    ItemReq ardrigalMixture = new ItemReq("Ardrigal mixture", ItemID.ARDRIGAL_MIXTURE);
    ItemReq braveryPotion = new ItemReq("Bravery potion", ItemID.BRAVERY_POTION);
    ItemReq braveryPotionHighlighted = new ItemReq("Bravery potion", ItemID.BRAVERY_POTION);
    ItemReq snakeMixture = new ItemReq("Snakeweed mixture", ItemID.SNAKEWEED_MIXTURE);
    ItemReq rope = new ItemReq("Rope", ItemID.ROPE);
    ItemReq ropeHighlighted = new ItemReq("Rope", ItemID.ROPE);
    ItemReq lumpCrystal = new ItemReq("Lump of crystal", ItemID.LUMP_OF_CRYSTAL);
    ItemReq chunkCrystal = new ItemReq("Chunk of crystal", ItemID.CHUNK_OF_CRYSTAL);
    ItemReq hunkCrystal = new ItemReq("Hunk of crystal", ItemID.HUNK_OF_CRYSTAL);
    ItemReq heartCrystal = new ItemReq("Heart crystal", ItemID.HEART_CRYSTAL);
    //heartCrystal.setTooltip("You'll have to kill the 3 skeletons for the pieces and forge them in the furnace");
    ItemReq heartCrystal2 = new ItemReq("Heart crystal", ItemID.HEART_CRYSTAL_745);
    ItemReq darkDagger = new ItemReq("Dark dagger", ItemID.DARK_DAGGER);
    //	darkDagger.setTooltip("You can get another from Echned at the source");
    ItemReq glowingDagger = new ItemReq("Glowing dagger", ItemID.GLOWING_DAGGER);
    ItemReq force = new ItemReq("Holy force", ItemID.HOLY_FORCE);
    ItemReq ItemReqforceHighlighted = new ItemReq(ItemID.HOLY_FORCE);
    ItemReq yommiTotem = new ItemReq("Yommi totem", ItemID.YOMMI_TOTEM);
    //yommiTotem.setTooltip("You'll need to grow another if you've lost it");
    ItemReq yommiTotemHighlighted = new ItemReq("Yommi totem", ItemID.YOMMI_TOTEM);
    //yommiTotemHighlighted.setTooltip("You'll need to grow another if you've lost it");
    ItemReq gildedTotem = new ItemReq("Gilded totem", ItemID.GILDED_TOTEM);
    //	gildedTotem.setTooltip("You can get another from Gujuo");


    ItemOnTileRequirement sapphirePlaced = (new ItemOnTileRequirement(sapphire, new RSTile(2781, 9291, 0)));
    ItemOnTileRequirement emeraldPlaced = (new ItemOnTileRequirement(emerald, new RSTile(2757, 9297, 0)));
    ItemOnTileRequirement rubyPlaced = (new ItemOnTileRequirement(ruby, new RSTile(2767, 9289, 0)));
    ItemOnTileRequirement diamondPlaced = (new ItemOnTileRequirement(diamond, new RSTile(2774, 9287, 0)));
    ItemOnTileRequirement opalPlaced = (new ItemOnTileRequirement(opal, new RSTile(2764, 9309, 0)));
    ItemOnTileRequirement jadePlaced = (new ItemOnTileRequirement(jade, new RSTile(2771, 9303, 0)));
    ItemOnTileRequirement topazPlaced = (new ItemOnTileRequirement(topaz, new RSTile(2772, 9295, 0)));

    ObjectCondition sacredWaterNearby = new ObjectCondition(ObjectID.SACRED_WATER);

    ObjectCondition saplingNearby = new ObjectCondition(ObjectID.YOMMI_TREE_SAPLING);

    ObjectCondition adultNearby = new ObjectCondition(ObjectID.ADULT_YOMMI_TREE);
    ObjectCondition felledNearby = new ObjectCondition(ObjectID.FELLED_YOMMI_TREE);
    ObjectCondition trimmedNearby = new ObjectCondition(ObjectID.TRIMMED_YOMMI);
    ObjectCondition totemNearby = new ObjectCondition(2954);
    NPCStep talkToGuard = new NPCStep(NpcID.LEGENDS_GUARD, new RSTile(2729, 3348, 0),
            new String[]{"Can I speak to someone in charge?", "Can I go on the quest?",
                    "Yes, I'd like to talk to Grand Vizier Erkle."});
    NPCStep talkToRadimus = new NPCStep(NpcID.RADIMUS_ERKLE, new RSTile(2725, 3368, 0),
            new String[]{"Yes actually, what's involved?", "Yes, it sounds great!"});

    //  enterJungle =new DetailedQuestStep( "Travel to the Khazari Jungle in south Karamja. You'll need to cut through some trees and bushes to enter.",radimusNotes, axe, machete, papyrus3, charcoal3);

    ClickItemStep sketchEast = new ClickItemStep(radimusNotesHighlight.getId(),
            "Write", new RSTile(2944, 2916, 0),
            radimusNotesHighlight, papyrus, charcoal);

    ClickItemStep sketchMiddle = new ClickItemStep(radimusNotesHighlight.getId(),
            "Write", new RSTile(2852, 2915, 0),
            radimusNotesHighlight, papyrus, charcoal);

    ClickItemStep sketchWest = new ClickItemStep(radimusNotesHighlight.getId(), "Write",
            new RSTile(2791, 2917, 0),
            papyrus, charcoal);


    UseItemOnNpcStep useNotes = new UseItemOnNpcStep(ItemID.RADIMUS_NOTES_715, 3954,
            new RSTile(2867, 2942, 0),
            completeNotesHighlighted);

    // enterJungleWithRoarer =new DetailedQuestStep( "Re-enter the Khazari Jungle. You'll need to cut through some trees and bushes to enter.",completeNotes, bullRoarer, axe, machete, lockpick, pickaxe, soulRune, mindRune, earthRune, lawRune2, opal, jade, topaz, sapphire, emerald, ruby, diamond);
    RSArea guild1 = new RSArea(new RSTile(2726, 3350, 0), new RSTile(2731, 3382, 2));
    RSArea guild2 = new RSArea(new RSTile(2721, 3363, 0), new RSTile(2725, 3382, 2));
    RSArea guild3 = new RSArea(new RSTile(2731, 3363, 0), new RSTile(2736, 3382, 2));
    RSArea khazari1 = new RSArea(new RSTile(2941, 2875, 0), new RSTile(2985, 2948, 0));
    RSArea khazari2 = new RSArea(new RSTile(2753, 2873, 0), new RSTile(2940, 2938, 0));
    RSArea khazari3 = new RSArea(new RSTile(2801, 2939, 0), new RSTile(2814, 2939, 0));
    RSArea khazari4 = new RSArea(new RSTile(2757, 2939, 0), new RSTile(2784, 2939, 0));

    RSArea eastKhazari = new RSArea(new RSTile(2880, 2880, 0), new RSTile(2985, 2940, 0));
    RSArea westKhazari = new RSArea(new RSTile(2753, 2880, 0), new RSTile(2815, 2940, 0));
    RSArea middleKhazari = new RSArea(new RSTile(2816, 2880, 0), new RSTile(2879, 2940, 0));
    RSArea caveRoom1P1 = new RSArea(new RSTile(2780, 9317, 0), new RSTile(2803, 9335, 0));
    RSArea caveRoom1P2 = new RSArea(new RSTile(2770, 9336, 0), new RSTile(2797, 9343, 0));
    RSArea caveRoom2P1 = new RSArea(new RSTile(2804, 9332, 0), new RSTile(2812, 9342, 0));
    RSArea caveRoom2P2 = new RSArea(new RSTile(2799, 9336, 0), new RSTile(2803, 9341, 0));
    RSArea caveRoom3 = new RSArea(new RSTile(2807, 9314, 0), new RSTile(2812, 9331, 0));
    RSArea caveRoom4P1 = new RSArea(new RSTile(2785, 9276, 0), new RSTile(2823, 9313, 0));
    RSArea caveRoom4P2 = new RSArea(new RSTile(2779, 9300, 0), new RSTile(2784, 9313, 0));
    RSArea caveRoom4P3 = new RSArea(new RSTile(2774, 9307, 0), new RSTile(2778, 9313, 0));
    RSArea caveRoom5P1 = new RSArea(new RSTile(2753, 9281, 0), new RSTile(2785, 9299, 0));
    RSArea caveRoom5P2 = new RSArea(new RSTile(2754, 9300, 0), new RSTile(2778, 9307, 0));
    RSArea caveRoom5P3 = new RSArea(new RSTile(2754, 9308, 0), new RSTile(2771, 9313, 0));
    RSArea allCaveRoom5 = new RSArea(
            new RSTile[]{
                    new RSTile(2753, 9315, 0),
                    new RSTile(2772, 9315, 0),
                    new RSTile(2776, 9306, 0),
                    new RSTile(2781, 9301, 0),
                    new RSTile(2788, 9289, 0),
                    new RSTile(2786, 9281, 0),
                    new RSTile(2767, 9280, 0),
                    new RSTile(2753, 9293, 0)
            }
    );
    RSArea caveRoom6P1 = new RSArea(new RSTile(2754, 9316, 0), new RSTile(2771, 9340, 0));
    RSArea caveRoom6P2 = new RSArea(new RSTile(2772, 9316, 0), new RSTile(2779, 9330, 0));
    RSArea caves = new RSArea(new RSTile(2749, 9275, 0), new RSTile(2817, 9345, 0));
    RSArea fire1 = new RSArea(new RSTile(2789, 9325, 0), new RSTile(2796, 9332, 0));
    RSArea fire2 = new RSArea(new RSTile(2788, 9328, 0), new RSTile(2797, 9329, 0));
    RSArea fire3 = new RSArea(new RSTile(2792, 9324, 0), new RSTile(2793, 9333, 0));
    RSArea challengeCave = new RSArea(new RSTile(2369, 4672, 0), new RSTile(2430, 4736, 0));
    RSArea FIRST_AREA_OF_CAVE = new RSArea(
            new RSTile[]{
                    new RSTile(2768, 9344, 0),
                    new RSTile(2796, 9343, 0),
                    new RSTile(2800, 9337, 0),
                    new RSTile(2806, 9332, 0),
                    new RSTile(2805, 9325, 0),
                    new RSTile(2796, 9315, 0),
                    new RSTile(2786, 9315, 0),
                    new RSTile(2775, 9332, 0),
                    new RSTile(2770, 9337, 0)
            }
    );
    RSArea SECOND_AREA_BEFORE_FIRST_GATE = new RSArea(
            new RSTile[]{
                    new RSTile(2798, 9342, 0),
                    new RSTile(2813, 9342, 0),
                    new RSTile(2813, 9332, 0),
                    new RSTile(2806, 9332, 0),
                    new RSTile(2800, 9337, 0)
            }
    );

    RSArea SECOND_AREA_BEFORE_SECOND_GATE = new RSArea(
            new RSTile[]{
                    new RSTile(2808, 9332, 0),
                    new RSTile(2808, 9328, 0),
                    new RSTile(2813, 9328, 0),
                    new RSTile(2813, 9332, 0)
            }
    );
    RSArea SECOND_AREA_BEFORE_THIRD_GATE = new RSArea(
            new RSTile[]{
                    new RSTile(2807, 9327, 0),
                    new RSTile(2812, 9327, 0),
                    new RSTile(2812, 9323, 0),
                    new RSTile(2807, 9323, 0)
            }
    );
    RSArea SECOND_AREA_BEFORE_FOURTH_GATE = new RSArea(
            new RSTile[]{
                    new RSTile(2807, 9323, 0),
                    new RSTile(2813, 9323, 0),
                    new RSTile(2813, 9319, 0),
                    new RSTile(2807, 9319, 0)
            }
    );
    RSArea SECOND_AREA_BEFORE_FIFTH_GATE = new RSArea(
            new RSTile[]{
                    new RSTile(2807, 9319, 0),
                    new RSTile(2813, 9319, 0),
                    new RSTile(2813, 9314, 0),
                    new RSTile(2807, 9314, 0)
            }
    );
    RSArea THIRD_AREA_PT1 = new RSArea(
            new RSTile[]{
                    new RSTile(2815, 9314, 0),
                    new RSTile(2805, 9314, 0),
                    new RSTile(2795, 9308, 0),
                    new RSTile(2793, 9302, 0),
                    new RSTile(2791, 9297, 0),
                    new RSTile(2788, 9294, 0),
                    new RSTile(2791, 9281, 0),
                    new RSTile(2803, 9280, 0),
                    new RSTile(2810, 9284, 0),
                    new RSTile(2814, 9283, 0),
                    new RSTile(2815, 9298, 0)
            }
    );

    RSArea JUST_AFTER_GATE_AREA = new RSArea(new RSTile(2817, 9313, 0), new RSTile(2797, 9291, 0));
    RSArea BOTTOM_U_OF_ROOM = new RSArea(new RSTile(2789, 9289, 0), new RSTile(2817, 9280, 0));
    RSArea AFTER_JAGGED_WALL = new RSArea(
            new RSTile[]{
                    new RSTile(2791, 9297, 0),
                    new RSTile(2788, 9294, 0),
                    new RSTile(2772, 9314, 0),
                    new RSTile(2791, 9314, 0)
            }
    );
    RSArea BOULDER_AREA = new RSArea(
            new RSTile[]{
                    new RSTile(2433, 4691, 0),
                    new RSTile(2400, 4691, 0),
                    new RSTile(2381, 4699, 0),
                    new RSTile(2374, 4703, 0),
                    new RSTile(2367, 4701, 0),
                    new RSTile(2367, 4671, 0),
                    new RSTile(2432, 4672, 0)
            }
    );
    RSTile OUTSIDE_LEGENDS_GUILD = new RSTile(2729, 3348, 0);
    RSArea INSIDE_LEGENDS_GUILD = new RSArea(
            new RSTile[]{
                    new RSTile(2732, 3350, 0),
                    new RSTile(2732, 3364, 0),
                    new RSTile(2737, 3364, 0),
                    new RSTile(2737, 3385, 0),
                    new RSTile(2721, 3385, 0),
                    new RSTile(2721, 3364, 0),
                    new RSTile(2726, 3364, 0),
                    new RSTile(2726, 3350, 0)
            }
    );

    RSArea FIRST_LEDGE_AREA = new RSArea(
            new RSTile[]{
                    new RSTile(2377, 4717, 0),
                    new RSTile(2380, 4717, 0),
                    new RSTile(2380, 4712, 0),
                    new RSTile(2378, 4711, 0),
                    new RSTile(2376, 4711, 0)
            }
    );
    RSArea SECOND_LEDGE_AREA = new RSArea(
            new RSTile[]{
                    new RSTile(2379, 4717, 0),
                    new RSTile(2375, 4717, 0),
                    new RSTile(2373, 4719, 0),
                    new RSTile(2373, 4723, 0),
                    new RSTile(2375, 4725, 0),
                    new RSTile(2375, 4727, 0),
                    new RSTile(2378, 4727, 0),
                    new RSTile(2378, 4723, 0),
                    new RSTile(2376, 4721, 0)
            }
    );
    RSArea THIRD_LEDGE_AREA = new RSArea(
            new RSTile[]{
                    new RSTile(2378, 4727, 0),
                    new RSTile(2376, 4729, 0),
                    new RSTile(2378, 4731, 0),
                    new RSTile(2382, 4731, 0),
                    new RSTile(2382, 4728, 0),
                    new RSTile(2380, 4729, 0)
            }
    );

    RSArea FOURTH_LEDGE_AREA = new RSArea(
            new RSTile[]{
                    new RSTile(2381, 4728, 0),
                    new RSTile(2381, 4731, 0),
                    new RSTile(2383, 4731, 0),
                    new RSTile(2385, 4729, 0),
                    new RSTile(2386, 4729, 0),
                    new RSTile(2387, 4729, 0),
                    new RSTile(2387, 4726, 0),
                    new RSTile(2384, 4726, 0)
            }
    );
    RSArea FIFTH_LEDGE_AREA = new RSArea(
            new RSTile[]{
                    new RSTile(2387, 4727, 0),
                    new RSTile(2387, 4729, 0),
                    new RSTile(2388, 4731, 0),
                    new RSTile(2390, 4731, 0),
                    new RSTile(2391, 4730, 0),
                    new RSTile(2392, 4728, 0),
                    new RSTile(2392, 4724, 0),
                    new RSTile(2389, 4724, 0),
                    new RSTile(2389, 4727, 0)
            }
    );

    RSTile[] PATH_DOWN_CLIF = {
            // new RSTile(2377, 4711, 0),
            new RSTile(2377, 4712, 0),
            new RSTile(2378, 4713, 0),
            new RSTile(2378, 4714, 0),
            new RSTile(2378, 4715, 0),
            new RSTile(2378, 4716, 0),
            new RSTile(2377, 4717, 0),
            new RSTile(2376, 4717, 0),
            new RSTile(2375, 4718, 0),
            new RSTile(2374, 4719, 0),
            new RSTile(2374, 4720, 0),
            new RSTile(2374, 4721, 0),
            new RSTile(2374, 4722, 0),
            new RSTile(2375, 4723, 0),
            new RSTile(2376, 4724, 0),
            new RSTile(2376, 4725, 0),
            new RSTile(2376, 4726, 0),
            new RSTile(2376, 4727, 0),
            new RSTile(2377, 4728, 0),
            new RSTile(2378, 4729, 0),
            new RSTile(2379, 4730, 0),
            new RSTile(2380, 4730, 0),
            new RSTile(2381, 4730, 0),
            new RSTile(2382, 4729, 0),
            new RSTile(2383, 4728, 0),
            new RSTile(2384, 4727, 0),
            new RSTile(2385, 4726, 0),
            new RSTile(2386, 4727, 0),
            new RSTile(2387, 4728, 0),
            new RSTile(2388, 4729, 0),
            new RSTile(2389, 4729, 0),
            new RSTile(2390, 4728, 0),
            new RSTile(2390, 4727, 0),
            new RSTile(2390, 4726, 0),
            new RSTile(2390, 4725, 0),
            new RSTile(2390, 4724, 0),
            new RSTile(2390, 4723, 0),
            new RSTile(2390, 4722, 0),
            new RSTile(2391, 4721, 0),
            new RSTile(2391, 4720, 0),
            new RSTile(2390, 4719, 0),
            new RSTile(2390, 4718, 0),
            new RSTile(2390, 4717, 0),
            new RSTile(2389, 4716, 0),
            new RSTile(2388, 4715, 0),
            new RSTile(2387, 4714, 0),
            new RSTile(2386, 4713, 0),
            new RSTile(2385, 4712, 0),
            new RSTile(2384, 4711, 0)
    };


    AreaRequirement inGuild = new AreaRequirement(guild1, guild2, guild3);
    AreaRequirement inKharazi = new AreaRequirement(khazari1, khazari2, khazari3, khazari4);
    AreaRequirement inWest = new AreaRequirement(westKhazari);
    AreaRequirement inMiddle = new AreaRequirement(middleKhazari);
    AreaRequirement inEast = new AreaRequirement(eastKhazari);
    AreaRequirement inCaveRoom1 = new AreaRequirement(caveRoom1P1, caveRoom1P2);
    AreaRequirement inCaves = new AreaRequirement(caves, challengeCave);
    AreaRequirement inCaveRoom2 = new AreaRequirement(caveRoom2P1, caveRoom2P2);
    AreaRequirement inCaveRoom3 = new AreaRequirement(caveRoom3);
    AreaRequirement inCaveRoom4 = new AreaRequirement(caveRoom4P1, caveRoom4P2, caveRoom4P3);
    AreaRequirement inCaveRoom5 = new AreaRequirement(allCaveRoom5);
    AreaRequirement inCaveRoom6 = new AreaRequirement(caveRoom6P1, caveRoom6P2);
    AreaRequirement inFire = new AreaRequirement(fire1, fire2, fire3);
    AreaRequirement inChallengeCave = new AreaRequirement(challengeCave);
    AreaRequirement beforeJaggedWall = new AreaRequirement(THIRD_AREA_PT1);
    AreaRequirement afterBarrier = new AreaRequirement(BOULDER_AREA);


    /**
     * STEPS
     */

    // game setting 0
    public void startQuest() {
        cQuesterV2.status = "Starting quest";
        ConditionalStep startQuest = new ConditionalStep(talkToGuard);
        startQuest.addStep(inGuild, talkToRadimus);
        startQuest.execute();
        if (!inGuild.check()) {

        }
    }


    public void sketchJungle() {
        if (LegendsUtils.enterForest()) {
            if (new Conditions(inKharazi, completeWest, completeMiddle).check()) {
                cQuesterV2.status = "Sketching East";
                sketchEast.execute();
            } else if (new Conditions(inKharazi, completeWest).check()) {
                cQuesterV2.status = "Sketching Middle";
                sketchMiddle.execute();
            } else if (inKharazi.check()) {
                cQuesterV2.status = "Sketching West";
                sketchWest.execute();
            }
        }
    }

    public void talkWithGujuo() {
        if (LegendsUtils.enterForest()) {
            cQuesterV2.status = "Talking to Gujuo";
            if (spinBull()) {
                talkToGujuo.execute();
            }
        }
    }

    WidgetTextRequirement ungaduluTextOne = new WidgetTextRequirement(229, 1,
            "The Shaman throws himself down on the floor and starts convulsing.");

    WidgetTextRequirement ungaduluTextTwo = new WidgetTextRequirement(119, 3, true,
            "is acting weird and talking a lot of nonsense");
    Conditions talkedToUngadulu = new Conditions(true, LogicType.OR, ungaduluTextOne, ungaduluTextTwo);

    Conditions hadSketch = new Conditions(true, LogicType.OR, sketch);

    WidgetTextRequirement searchedMarkedWall = new WidgetTextRequirement(229, 1, "You can see a message on the wall");

    Conditions addedSoulRune = new Conditions(true, LogicType.OR,
            new WidgetTextRequirement(229, 1, "You slide the Soul Rune into the first"),
            new WidgetTextRequirement(229, 1, "You find the letter 'S'"));
    Conditions addedMindRune = new Conditions(true, LogicType.OR,
            new WidgetTextRequirement(229, 1, "You slide the Mind Rune into the second"),
            new WidgetTextRequirement(229, 1, "You find the letters 'SM'"));
    Conditions addedEarthRune = new Conditions(true, LogicType.OR,
            new WidgetTextRequirement(229, 1, "You slide the Earth Rune into the third"),
            new WidgetTextRequirement(229, 1, "You find the letters 'SME'"));

    Conditions addedLawRune = new Conditions(true, LogicType.OR,
            new WidgetTextRequirement(229, 1, "You slide the Law Rune into the fourth"),
            new WidgetTextRequirement(229, 1, "You find the letters 'SMEL'"));

    Conditions addedLawRune2 = new Conditions(true, LogicType.OR,
            new WidgetTextRequirement(229, 1, "You slide the Law Rune into the fifth"),
            new WidgetTextRequirement(229, 1, "You find the letters 'SMELL'"));

    // bookAppearing = new ChatMessageRequirement("You feel a powerful force picking you up....");
    ItemOnTileRequirement bookNearby = new ItemOnTileRequirement(bindingBook);

    // nezNearby = new NpcInteractingRequirement(NpcID.NEZIKCHENED);

    Conditions addedRope = new Conditions(true, new ObjectCondition(ObjectID.WINCH_2935,
            new RSTile(2761, 9331, 0)));
    //  echnedNearby = new NpcCondition(NpcID.ECHNED_ZEKIN);
    // viyeldiNearby = new NpcCondition(NpcID.VIYELDI);


    // NpcCondition  ranalphNearby = new NpcCondition(NpcID.RANALPH_DEVERE);
    //   NpcCondition  irvigNearby = new NpcCondition(NpcID.IRVIG_SENAY);
    //  NpcCondition sanNearby = new NpcCondition(NpcID.SAN_TOJALON);

    ObjectStep leaveCave = new ObjectStep(ObjectID.CAVE_ENTRANCE_2903,
            new RSTile(2773, 9342, 0), "Walk through", !inCaves.check());

    public boolean spinBull() {
        RSItem[] bull = Inventory.find(bullRoarer.getId());
        if (NPCs.find("Gujuo").length == 0 &&
                bull.length > 0 && bull[0].click("Swing")) {
            return Timer.waitCondition(() -> NPCs.find("Gujuo").length > 0, 5000);
        }
        return false;
    }

    public void blessBowl() {
        if (Inventory.find(ItemID.BLESSED_GOLD_BOWL).length == 0) {
            spinBull();
            talkToGujuoWithBowl.execute();
            if (Timer.waitCondition(() -> NPCInteraction.isConversationWindowUp(), 7000, 9000))
                NPCInteraction.handleConversation();
            Timer.waitCondition(() -> Inventory.find(ItemID.BLESSED_GOLD_BOWL).length > 0, 10000);
            if (NPCInteraction.isConversationWindowUp())
                NPCInteraction.handleConversation();
        }

    }

    NPCStep talkToGujuo = new NPCStep("Gujuo", new RSTile(2784, 2936, 0),
            new String[]{"I was hoping to attract the attention of a native.",
                    "I want to develop friendly relations with your people.",
                    "Can you get your people together?",
                    "What can we do instead then?",
                    "How do we make the totem pole?",
                    "I will release Ungadulu..."});

    NPCStep talkToGujuoAgain = new NPCStep(NpcID.GUJUO, new RSTile(2784, 2936, 0),
            new String[]{"I need some pure water to douse some magic flames.", "What kind of a vessel?"});

    /*   ObjectStep leaveCave = new ObjectStep(2903, new RSTile(2773, 9342, 0));


       ObjectStep enterMossyRockAgain = new ObjectStep(ObjectID.MOSSY_ROCK, new RSTile(2782, 2937, 0),
               new String[]{"Yes, I'll crawl through, I'm very athletic."});
   */
    ObjectStep enterBookcase = new ObjectStep(2911, new RSTile(2796, 9338, 0), "Search",
            NPCInteraction.isConversationWindowUp());


    ObjectStep enterGate1 = new ObjectStep(ObjectID.ANCIENT_GATE, new RSTile(2810, 9332, 0),
            "Search", NPCInteraction.isConversationWindowUp(), lockpick);

    ObjectStep enterGate2 = new ObjectStep(2922, new RSTile(2810, 9314, 0), "Open",
            NPCInteraction.isConversationWindowUp(), pickaxe);
    ObjectStep smashRocks = new ObjectStep(2919, new RSTile(2810, 9329, 0), "Smash-to-bits",
            NPCInteraction.isConversationWindowUp(), pickaxe);
    ObjectStep smashRocks2 = new ObjectStep(2920, new RSTile(2810, 9326, 0), "Smash-to-bits",
            NPCInteraction.isConversationWindowUp(), pickaxe);
    ObjectStep smashRocks3 = new ObjectStep(2921, new RSTile(2810, 9322, 0), "Smash-to-bits",
            NPCInteraction.isConversationWindowUp(), pickaxe);
    ObjectStep jumpOverJaggedWall = new ObjectStep(2926, new RSTile(2790, 9293, 0),
            "Jump-over", AFTER_JAGGED_WALL.contains(Player.getPosition()), beforeJaggedWall);
    ObjectStep searchMarkedWall = new ObjectStep(ObjectID.MARKED_WALL, new RSTile(2779, 9305, 0),
            "Search", inCaveRoom4);


    UseItemOnObjectStep useSoul = new UseItemOnObjectStep(soulRuneHighlight.getId(), ObjectID.MARKED_WALL,
            new RSTile(2779, 9305, 0), Inventory.find(soulRuneHighlight.getId()).length == 0);

    UseItemOnObjectStep useMind = new UseItemOnObjectStep(mindRuneHighlight.getId(), ObjectID.MARKED_WALL,
            new RSTile(2779, 9305, 0), "Use a Mind Rune on the marked wall.");

    UseItemOnObjectStep useEarth = new UseItemOnObjectStep(ItemID.EARTH_RUNE, ObjectID.MARKED_WALL,
            new RSTile(2779, 9305, 0), "Use a Earth Rune on the marked wall.");

    UseItemOnObjectStep useLaw = new UseItemOnObjectStep(ItemID.LAW_RUNE, ObjectID.MARKED_WALL,
            new RSTile(2779, 9305, 0), "Use a Law Rune on the marked wall.");

    UseItemOnObjectStep useLaw2 = new UseItemOnObjectStep(ItemID.LAW_RUNE, ObjectID.MARKED_WALL,
            new RSTile(2779, 9305, 0), "Use another Law Rune on the marked wall.");


    UseItemOnObjectStep useSapphire = new UseItemOnObjectStep(sapphireHighlighted.getId(),
            ObjectID.CARVED_ROCK,
            new RSTile(2781, 9292, 0), "Use a sapphire on the south-east carved rock." +
            " If you have already, search it instead.", sapphireHighlighted);

    UseItemOnObjectStep useEmerald = new UseItemOnObjectStep(emeraldHighlighted.getId(),
            ObjectID.CARVED_ROCK,
            new RSTile(2756, 9299, 0), "Use a emerald on the west carved rock. " +
            "If you have already, search it instead.", emeraldHighlighted);

    UseItemOnObjectStep useRuby = new UseItemOnObjectStep(rubyHighlighted.getId(), ObjectID.CARVED_ROCK,
            new RSTile(2767, 9288, 0), "Use a ruby on the south-west carved rock. " +
            "If you have already, search it instead.", rubyHighlighted);

    UseItemOnObjectStep useDiamond = new UseItemOnObjectStep(diamondHighlighted.getId(), ObjectID.CARVED_ROCK,
            new RSTile(2774, 9288, 0), "Use a diamond on the south carved rock." +
            " If you have already, search it instead.", diamondHighlighted);

    UseItemOnObjectStep useOpal = new UseItemOnObjectStep(opalHighlighted.getId(), ObjectID.CARVED_ROCK,
            new RSTile(2765, 9309, 0), "Use an opal on the far west carved rock. " +
            "If you have already, search it instead.", opalHighlighted);

    UseItemOnObjectStep useJade = new UseItemOnObjectStep(jadeHighlighted.getId(), ObjectID.CARVED_ROCK,
            new RSTile(2772, 9303, 0),
            jadeHighlighted);

    UseItemOnObjectStep useTopaz = new UseItemOnObjectStep(topazHighlighted.getId(), ObjectID.CARVED_ROCK,
            new RSTile(2771, 9295, 0), topazHighlighted);


    // waitForBook =new DetailedQuestStep( "Wait for the binding book to appear.");

    // pickUpBook =new ItemStep( "Pick up the binding book",bindingBook);
    //	pickUpBook.addSubSteps(waitForBook);

    // makeBowl =new DetailedQuestStep( "Travel to an anvil and make a gold bowl.",goldBar2, sketch, hammer);

    // enterJungleWithBowl =new DetailedQuestStep( "Return to the Khazari Jungle with your gold bowl, and be prepared for a fight.",bullRoarer, goldBowl, bindingBook, axe, machete, combatGear);

    // spinBullToBless =new DetailedQuestStep( "Spin the bull roarer until Gujuo appears.",bullRoarerHighlight, goldBowl);

    NPCStep talkToGujuoWithBowl = new NPCStep(NpcID.GUJUO, Player.getPosition());


    UseItemOnObjectStep useMacheteOnReeds = new UseItemOnObjectStep(machete.getId(), ObjectID.TALL_REEDS,
            new RSTile(2836, 2916, 0),
            Inventory.find(reed.getId()).length > 0, machete);
    UseItemOnObjectStep useReedOnPool = new UseItemOnObjectStep(reed.getId(), ObjectID.WATER_POOL,
            new RSTile(2838, 2916, 0),
            Inventory.find(goldBowlBlessed.getId()).length == 0,
            reed, goldBowlBlessed);

    public void fillBowl() {
        if (goldBowlBlessed.check()) {
            cQuesterV2.status = "Going to fill bowl";
            useMacheteOnReeds.useItemOnObject();
            useReedOnPool.useItemOnObject();
        }
    }


    ObjectStep searchMosttRockWithBowl = new ObjectStep(ObjectID.MOSSY_ROCK,
            new RSTile(2782, 2933, 0),
            "Search", NPCInteraction.isConversationWindowUp(), goldBowlFull);
    ObjectStep searchMossyRockWithBravery = new ObjectStep(ObjectID.MOSSY_ROCK,
            new RSTile(2782, 2933, 0),
            "Search", NPCInteraction.isConversationWindowUp(), braveryPotion);
    ObjectStep enterMossyRock = new ObjectStep(ObjectID.MOSSY_ROCK,
            new RSTile(2782, 2937, 0),
            "Search", NPCInteraction.isConversationWindowUp());
    ObjectStep enterMossyRockWithBowl = new ObjectStep(ObjectID.MOSSY_ROCK,
            new RSTile(2782, 2937, 0),
            "Search", NPCInteraction.isConversationWindowUp(), goldBowlFull);
    ObjectStep enterMossyRockWithBravery = new ObjectStep(ObjectID.MOSSY_ROCK,
            new RSTile(2782, 2937, 0),
            "Search", NPCInteraction.isConversationWindowUp(), braveryPotion);
    UseItemOnObjectStep useBowlOnFireWall = new UseItemOnObjectStep(goldBowlFull.getId(), ObjectID.FIRE_WALL,
            new RSTile(2789, 9333, 0),
            "Use the golden bowl on the wall of fire.", goldBowlFull);


    UseItemOnNpcStep useBindingBookOnUngadulu = new UseItemOnNpcStep(bindingBook.getId(),
            NpcID.UNGADULU, new RSTile(2792, 9328, 0), "Use the binding book on Ungadulu.",
            bindingBookHighlighted);

    public void enterFire() {
        if (!inFire.check()) {
            cQuesterV2.status = "Entering fire";
            useBowlOnFireWall.useItemOnObject();
        }
    }

    //useBindingBookOnUngadulu.addAlternateNpcs(NpcID.UNGADULU_3958);
/*

    NPCStep fightNezikchenedInFire = new NPCStep(NpcID.NEZIKCHENED, new RSTile(2793, 9329, 0), "Fight Nezikchened.");

    ObjectStep enterMossyRockAfterFight = new ObjectStep(ObjectID.MOSSY_ROCK,
            new RSTile(2782, 2937, 0),
            "Search and then enter the Mossy Rocks in the north west of the Khazari.", goldBowlFull);


    ObjectStep enterFireAfterFight = new ObjectStep(ObjectID.FIRE_WALL,
            new RSTile(2790, 9333, 0), "Touch", NPCInteraction.isConversationWindowUp());


*/
    NPCStep talkToUngadulu = new NPCStep(NpcID.UNGADULU, new RSTile(2792, 9328, 0), inFire);

    public void talkToUngaduluAfterFight() {
        if (!inFire.check()) {
            enterFire();
        }
        cQuesterV2.status = "Talking to Ungadulu";
        talkToUngadulu.execute();
    }

    public void addWaterToSeeds() {
        cQuesterV2.status = "Adding water to seeds";
        useBowlOnSeeds.setHandleChat(true);
        useBowlOnSeeds.execute();
        if (InterfaceUtil.click(11, 4))
            Timer.waitCondition(() -> Interfaces.get(11) == null, 2000);
    }

    public void plantSeeds() {
        cQuesterV2.status = "Leaving cave with seeds";
        leaveCaveWithSeed.setUseLocalNav(true);
        leaveCaveWithSeed.execute();
        useMacheteOnReedsAgain.useItemOnObject();
        useReedOnPoolAgain.useItemOnObject();
        cQuesterV2.status = "Planting seeds";
        plantSeed.useItemOnObject();
    }

    UseItemOnItemStep useBowlOnSeeds = new UseItemOnItemStep(goldBowlFull.getId(), yommiSeeds.getId(),
            NPCInteraction.isConversationWindowUp(), yommiSeeds, goldBowlFull);

    ObjectStep leaveCaveWithSeed = new ObjectStep(2903, new RSTile(2773, 9341, 0),
            "Walk through",
            Player.getPosition().distanceTo(new RSTile(2773, 9342, 0)) > 10,
            germinatedSeedsHighlighted, inCaveRoom1);

    UseItemOnObjectStep plantSeed = new UseItemOnObjectStep(germinatedSeedsHighlighted.getId(),
            ObjectID.FERTILE_SOIL, new RSTile(2779, 2917, 0),
            NPCInteraction.isConversationWindowUp(), germinatedSeedsHighlighted, goldBowlFull);

    UseItemOnObjectStep useMacheteOnReedsAgain = new UseItemOnObjectStep(machete.getId(), ObjectID.TALL_REEDS,
            new RSTile(2836, 2916, 0), Inventory.find(reed.getId()).length == 0, machete);

    UseItemOnObjectStep useReedOnPoolAgain = new UseItemOnObjectStep(reed.getId(), ObjectID.WATER_POOL,
            new RSTile(2838, 2916, 0), Inventory.find(goldBowlBlessed.getId()).length == 0,
            reed, goldBowlBlessed);


    ClickItemStep spinBullAfterSeeds = new ClickItemStep(bullRoarerHighlight.getId(), "Swing", bullRoarer, germinatedSeeds);
    RSArea SNAKE_WEED_AREA = new RSArea(new RSTile(2761, 3047, 0), new RSTile(2767, 3042, 0));

    RSArea ARDRIGAL_AREA = new RSArea(new RSTile(2871, 3125, 0), new RSTile(2878, 3118, 0));
    int PALM_TREE_ID = 2577;
    int GRIMY_SNAKE_WEED = 1525;
    int SNAKE_WEED = 1526;
    int GRIMY_ARDRIGAL = 1527;
    int ARDRIGAL = 1528;

    public void bank(BankTask tsk) {
        if (!WHOLE_CAVE_AREA_2.contains(Player.getPosition()) && !WHOLE_CAVE_AREA.contains(Player.getPosition())) {
            if (!Bank.isNearby()) {
                cQuesterV2.status = "Going to Bank";
                GlobalWalking.walkToBank();
            }
            cQuesterV2.status = "Banking";
            tsk.execute();
        }
    }

    InventoryRequirement gemInventory = new InventoryRequirement(new ArrayList<>(List.of(
            new ItemReq(ItemID.MACHETE, 1),
            new ItemReq(ItemID.RADIMUS_NOTES_715, 1),
            new ItemReq(ItemID.RUNE_AXE, 1),
            new ItemReq(ItemID.RUNE_PICKAXE, 1),
            new ItemReq(ItemID.LOCKPICK, 2),
            new ItemReq(ItemID.SAPPHIRE, 1),
            new ItemReq(ItemID.RUBY, 1),
            new ItemReq(ItemID.DIAMOND, 1),
            new ItemReq(ItemID.RED_TOPAZ, 1),
            new ItemReq(ItemID.JADE, 1),
            new ItemReq(ItemID.OPAL, 1),
            new ItemReq(ItemID.LAW_RUNE, 5),
            new ItemReq(ItemID.SOUL_RUNE, 5),
            new ItemReq(ItemID.MIND_RUNE, 5),
            new ItemReq(ItemID.EARTH_RUNE, 5),
            new ItemReq(ItemID.EMERALD, 1),
            new ItemReq(ItemID.ARDOUGNE_TELEPORT, 5, 1),
            new ItemReq(ItemID.STAMINA_POTION[0], 3, 1),
            new ItemReq(ItemID.COINS_995, 3500, 100)
    )));

    BankTask gearTask = BankTask.builder()
            .addEquipmentItem(EquipmentReq.slot(org.tribot.script.sdk.Equipment.Slot.BODY)
                    .item(ItemID.RUNE_PLATEBODY, Amount.of(1)))
            .addEquipmentItem(EquipmentReq.slot(org.tribot.script.sdk.Equipment.Slot.WEAPON)
                    .item(21009, Amount.of(1))) //d sword
            .addEquipmentItem(EquipmentReq.slot(org.tribot.script.sdk.Equipment.Slot.HEAD)
                    .item(1163, Amount.of(1))) //rune full helm
            .addEquipmentItem(EquipmentReq.slot(org.tribot.script.sdk.Equipment.Slot.LEGS)
                    .item(1079, Amount.of(1))) //rune legs
            .addEquipmentItem(EquipmentReq.slot(org.tribot.script.sdk.Equipment.Slot.FEET)
                    .item(4131, Amount.of(1))).build();


    BankTask bankTask = BankTask.builder()
            .addEquipmentItem(EquipmentReq.slot(org.tribot.script.sdk.Equipment.Slot.BODY)
                    .item(ItemID.RUNE_PLATEBODY, Amount.of(1)))
            .addEquipmentItem(EquipmentReq.slot(org.tribot.script.sdk.Equipment.Slot.WEAPON)
                    .item(21009, Amount.of(1))) //d sword
            .addEquipmentItem(EquipmentReq.slot(org.tribot.script.sdk.Equipment.Slot.HEAD)
                    .item(1163, Amount.of(1))) //rune full helm
            .addEquipmentItem(EquipmentReq.slot(org.tribot.script.sdk.Equipment.Slot.LEGS)
                    .item(1079, Amount.of(1))) //rune legs
            .addEquipmentItem(EquipmentReq.slot(org.tribot.script.sdk.Equipment.Slot.FEET)
                    .item(4131, Amount.of(1))) //rune boots
            .addInvItem(ItemID.UNPOWERED_ORB, Amount.of(2))
            .addInvItem(ItemID.COSMIC_RUNE, Amount.of(6))
            .addInvItem(ItemID.MACHETE, Amount.of(1))
            .addInvItem(ItemID.WATER_RUNE, Amount.of(60))
            .addInvItem(ItemID.LOCKPICK, Amount.of(2))
            .addInvItem(ItemID.BRAVERY_POTION, Amount.of(1))
            .addInvItem(ItemID.STAMINA_POTION[0], Amount.of(2))
            .addInvItem(ItemID.PRAYER_POTION[0], Amount.of(3))
            .addInvItem(ItemID.ROPE, Amount.of(1))
            .addInvItem(ItemID.VIAL_OF_WATER, Amount.of(1))
            .addInvItem(ItemID.RUNE_AXE, Amount.of(1))
            .addInvItem(ItemID.RUNE_PICKAXE, Amount.of(1))
            .addInvItem(ItemID.COINS_995, Amount.of(10000))
            .addInvItem(ItemID.BLESSED_GOLD_BOWL, Amount.of(1))
            .addInvItem(ItemID.RADIMUS_NOTES_715, Amount.of(1))
            .addInvItem(ItemID.ARDOUGNE_TELEPORT, Amount.fill(5))
            .addInvItem(ItemID.SHARK, Amount.fill(2))
            .build();

    public void getArdrigal() {
        if (!ardrigal.check() && !braveryPotion.check() && !ardrigalMixture.check()) {
            cQuesterV2.status = "Going to get Ardrigal";
            General.println("[Debug]: " + cQuesterV2.status);
            PathingUtil.walkToArea(ARDRIGAL_AREA, false);
            if (Inventory.isFull())
                EatUtil.eatFood();
            if (Utils.clickObject(PALM_TREE_ID, "Search", false)) {
                cQuesterV2.status = "Going to get Ardrigal";
                General.println("[Debug]: " + cQuesterV2.status);
                Timer.waitCondition(() -> Inventory.find(GRIMY_ARDRIGAL).length > 0, 12000, 30000);
            }
            cleanHerb(GRIMY_ARDRIGAL, ARDRIGAL);
        }

    }

    public void getSnakeWeed() {
        if (!snakeWeed.check() && !snakeMixture.check() && !braveryPotion.check()) {
            cQuesterV2.status = "Getting vine";
            General.println("[Debug]: " + cQuesterV2.status);
            PathingUtil.walkToArea(SNAKE_WEED_AREA, false);


            RSObject[] vine = Objects.findNearest(20, "Marshy jungle vine");
            if (vine.length > 0) {

                if (!vine[0].isClickable())
                    vine[0].adjustCameraTo();

                if (Inventory.isFull())
                    EatUtil.eatFood();

                if (AccurateMouse.click(vine[0], "Search")) {
                    cQuesterV2.status = "Waiting while searching...";
                    General.println("[Debug]: " + cQuesterV2.status);
                    Timer.waitCondition(() -> Inventory.find(GRIMY_SNAKE_WEED).length > 0, 180000, 240000);
                }

            }
            cleanHerb(GRIMY_SNAKE_WEED, SNAKE_WEED);
        }
    }

    public void cleanHerb(int grimyID, int cleanID) {
        if (Inventory.find(cleanID).length < 1 && Inventory.find(grimyID).length > 0) {
            cQuesterV2.status = "Cleaning herb";
            General.println("[Debug]: " + cQuesterV2.status);
            if (AccurateMouse.click(Inventory.find(grimyID)[0], "Clean"))
                Timer.waitCondition(() -> Inventory.find(cleanID).length > 0, 6000, 9000);
        }
    }


    NPCStep talkToGujuoAfterSeeds = new NPCStep(NpcID.GUJUO, Player.getPosition());

    //  enterJungleAfterSeeds =new DetailedQuestStep( "Return to the Khazari Jungle with your bull roarer, and be prepared for some fights.",
    //                     bullRoarer, runeOrDragonAxe, machete, pickaxe, lockpick, vialOfWater, snakeWeed, ardrigal, chargeOrbRunes, unpoweredOrb, rope, goldBowlBlessed, combatGear, normalSpellbook);
    //  // useMacheteOnReedsAgain.addSubSteps(enterJungleAfterSeeds);

    // enterJungleToGoToSource =new DetailedQuestStep( "Return to the Khazari Jungle and be prepared for some fights.",
    //                   runeOrDragonAxe, machete, pickaxe, lockpick, braveryPotion, chargeOrbRunes, unpoweredOrb, rope, goldBowlBlessed,
    //                   combatGear, normalSpellbook);

    public void makePotion() {
        addArdrigal.execute();
        addSnake.execute();
        addArdrigalToSnake.execute();
        NPCInteraction.waitForConversationWindow();
        Keyboard.typeString(" ");
        NPCInteraction.handleConversation();
    }

    public void enterJungleAfterBravery() {
        if (braveryPotion.check() && !WHOLE_CAVE_AREA.contains(Player.getPosition()) &&
                !WHOLE_CAVE_AREA_2.contains(Player.getPosition())) {
            LegendsUtils.enterForest();
        }
    }


    UseItemOnItemStep addArdrigal = new UseItemOnItemStep(ardrigal.getId(), vialOfWater.getId(),
            Inventory.find(ardrigalMixture.getId()).length > 0);

    UseItemOnItemStep addSnake = new UseItemOnItemStep(ardrigalMixture.getId(), snakeWeed.getId(),
            Inventory.find(snakeMixture.getId()).length > 0);

    UseItemOnItemStep addArdrigalToSnake = new UseItemOnItemStep(snakeMixture.getId(), ardrigal.getId(),
            Inventory.find(snakeMixture.getId()).length == 0);
    //	addArdrigal.addSubSteps(addSnake,addArdrigalToSnake);

    ObjectStep enterMossyRockToSource = new ObjectStep(ObjectID.MOSSY_ROCK, new RSTile(2782, 2937, 0),
            "Search", NPCInteraction.isConversationWindowUp());

    ObjectStep enterBookcaseToSource = new ObjectStep(2911, new RSTile(2796, 9338, 0),
            "Search", NPCInteraction.isConversationWindowUp());


    ObjectStep enterGate1ToSource = new ObjectStep(ObjectID.ANCIENT_GATE, new RSTile(2810, 9332, 0),
            "Search", NPCInteraction.isConversationWindowUp(), lockpick);
    ObjectStep enterGate2ToSource = new ObjectStep(ObjectID.ANCIENT_GATE_2922, new RSTile(2810, 9314, 0),
            "Smash", NPCInteraction.isConversationWindowUp(), pickaxe);


    ObjectStep searchMarkedWallToSource = new ObjectStep(ObjectID.MARKED_WALL,
            new RSTile(2779, 9305, 0), "Use", inCaveRoom4);

    public void castSpellOnDoor() {
        if (inCaveRoom5.check() && unpoweredOrb.check() && cosmic3.check()) {
            General.println("In cave room 5", Color.RED);
            cQuesterV2.status = "Going to cast spell on door";
            RSTile tile = new RSTile(2763, 9314, 0);
            if (PathingUtil.localNavigation(tile)) {
                Timer.waitCondition(() -> tile.distanceTo(Player.getPosition()) < 5, 6000, 9000);
            } else {
                Walking.blindWalkTo(tile);
                Timer.waitCondition(() -> tile.distanceTo(Player.getPosition()) < 5, 6000, 9000);
            }
            RSObject[] gate = Objects.findNearest(10, ObjectID.ANCIENT_GATE_2930);
            if (gate.length > 0) {
                if (!gate[0].isClickable())
                    DaxCamera.focus(gate[0]);

                if (Magic.selectSpell("Charge Air Orb") && gate[0].click("Cast")) {
                    if (Timer.waitCondition(() -> Player.getAnimation() != -1, 5500, 6000))
                        Timer.waitCondition(() -> inCaveRoom6.check(), 10000, 14000);
                }
            }
        }
    }

    UseItemOnObjectStep useRopeOnWinch = new UseItemOnObjectStep(ItemID.ROPE, ObjectID.WINCH_2934,
            new RSTile(2761, 9328, 0), inCaveRoom6, ropeHighlighted);
    //       "Use a rope on the winch. If you've already done so, search it instead.", ropeHighlighted);

    ClickItemStep drinkBraveryPotion = new ClickItemStep(braveryPotion.getId(), "Drink", new RSTile(2761, 9328, 0));

    ObjectStep climbDownWinch = new ObjectStep(ObjectID.WINCH_2935,
            new RSTile(2761, 9328, 0), "Climb-down");


    public void climbDownWinch() {
        if (inCaveRoom6.check()) {
            cQuesterV2.status = "Climbing down Winch";
            if (rope.check()) {
                useRopeOnWinch.setUseLocalNav(true);
                useRopeOnWinch.useItemOnObject();
                Timer.waitCondition(() -> Objects.findNearest(10, 2935).length == 1, 5000, 7000);
            }
            if (Objects.findNearest(10, 2935).length == 1) {
                drinkBraveryPotion.execute();
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation("Yes, I'll bravely drink the bravery potion.",
                        "Yes, I'll shimmy down the rope into possible doom.");


                climbDownWinch.setUseLocalNav(true);
                climbDownWinch.execute();
                Timer.waitCondition(() -> !inCaveRoom6.check(), 8000, 9000);
            }
        }
    }


    public boolean killNpc(String name, int drop) {
        RSNPC[] targ = NPCs.findNearest(name);
        RSItem[] item = Inventory.find(drop);
        if (targ.length > 0 && item.length == 0) {
            if (!targ[0].isClickable() && PathingUtil.localNavigation(targ[0].getPosition())) {
                General.println("[Debug]: Going to " + name);
                Timer.waitCondition(() -> targ[0].isClickable(), 7000, 9000);
            }
            if (!Combat.isUnderAttack() && Utils.clickNPC(name, "Attack")) {
                Timer.waitCondition(Combat::isUnderAttack, 5000, 6000);
            }
            if (Combat.isUnderAttack()) {
                cQuesterV2.status = "Fighting";
                int eatAt = General.random(35, 60);
                return CombatUtil.waitUntilOutOfCombat(eatAt);
            }
        }
        return item.length > 0;
    }

    public void killAll() {
        if (!killNpc("Ranalph Devere", lumpCrystal.getId())) {
            PathingUtil.localNavigation(new RSTile(2407, 4707));
            General.sleep(3000, 5000);
            killNpc("Ranalph Devere", lumpCrystal.getId());
        }
        killNpc("Irvig Senay", hunkCrystal.getId());
        killNpc("San Tojalon", chunkCrystal.getId());

    }

   /* ObjectStep enterMossyRockForViyeldi = new ObjectStep(ObjectID.MOSSY_ROCK, new RSTile(2782, 2937, 0),
             machete, pickaxe, lockpick,  unpoweredOrb, goldBowlBlessed, combatGear);


       */

    UseItemOnObjectStep useLumpOnFurnace = new UseItemOnObjectStep(lumpCrystal.getId(), 2966,
            new RSTile(2427, 4725, 0),
            lumpCrystal);

    UseItemOnObjectStep useChunkOnFurnace = new UseItemOnObjectStep(chunkCrystal.getId(), 2966,
            new RSTile(2427, 4725, 0),
            chunkCrystal);

    UseItemOnObjectStep useHunkOnFurnace = new UseItemOnObjectStep(hunkCrystal.getId(), 2966,
            new RSTile(2427, 4725, 0),
            hunkCrystal);

    UseItemOnObjectStep useHeartOnRock = new UseItemOnObjectStep(heartCrystal.getId(), ObjectID.MOSSY_ROCK_2965, new RSTile(2411, 4716, 0),
            heartCrystal);

    UseItemOnObjectStep useHeartOnRecess = new UseItemOnObjectStep(heartCrystal2.getId(), ObjectID.RECESS,
            new RSTile(2422, 4693, 0),
            NPCInteraction.isConversationWindowUp(), heartCrystal2);

    ObjectStep passBarrier = new ObjectStep(2971, new RSTile(2420, 4689, 0),
            "Walk-through");


    NPCStep pushBoulder = new NPCStep(3967, new RSTile(2394, 4679, 0));

    NPCStep talkToEchned = new NPCStep(NpcID.ECHNED_ZEKIN, new RSTile(2394, 4678, 0));


    GroundItemStep pickUpHat = new GroundItemStep("Blue hat");


    //  pickUpHat.addSubSteps(killViyeldi);

    NPCStep pushBoulderAgain = new NPCStep(NpcID.BOULDER_3967, new RSTile(2394, 4679, 0),
            glowingDagger);
    NPCStep pushBoulderWithForce = new NPCStep(NpcID.BOULDER_3967, new RSTile(2394, 4679, 0),
            force);
    //pushBoulderAgain.addSubSteps(pushBoulderWithForce);

    NPCStep giveDaggerToEchned = new NPCStep(NpcID.ECHNED_ZEKIN, new RSTile(2385, 4681, 0));

    UseItemOnObjectStep useBowlOnSacredWater = new UseItemOnObjectStep(goldBowlBlessed.getId(),
            ObjectID.SACRED_WATER,
            new RSTile(2394, 4679, 0),
            goldBowlBlessed);

    UseItemOnObjectStep useMacheteOnReedsEnd = new UseItemOnObjectStep(ItemID.MACHETE, ObjectID.TALL_REEDS,
            new RSTile(2836, 2916, 0),
            goldBowlBlessed);
    UseItemOnObjectStep useReedOnPoolEnd = new UseItemOnObjectStep(reed.getId(), ObjectID.WATER_POOL,
            new RSTile(2838, 2916, 0), reed, goldBowlBlessed);


    UseItemOnObjectStep useWaterOnTree = new UseItemOnObjectStep(ObjectID.YOMMI_TREE_SAPLING,
            goldBowlFullHighlighted.getId(), new RSTile(2838, 2916, 0),
            Inventory.find(goldBowlFullHighlighted.getId()).length == 0 || NPCInteraction.isConversationWindowUp());

    /*  //  castForce =new DetailedQuestStep( "Cast holy force.");
    //	giveDaggerToEchned.addSubSteps(castForce);

    NPCStep fightNezikchenedAtSource = new NPCStep(NpcID.NEZIKCHENED, new RSTile(2385, 4681, 0), "Defeat Nezikchened.");


    ObjectStep useBowlOnSacredWater = new ObjectStep(ObjectID.SACRED_WATER, "Use the blessed bowl on the sacred water.", goldBowlBlessed);

    // returnToSurface =new DetailedQuestStep( "Return to the surface.");



    ItemReq completeNotes = new ItemReq("Radimus notes", ItemID.RADIMUS_NOTES_715);

    // enterJungleToPlant =new DetailedQuestStep( "Return to the Khazari Jungle and be prepared for some fights.",
    //                     runeOrDragonAxe, machete, goldBowlBlessed, germinatedSeeds, combatGear);



    //  NPCStep killRanalph = new NPCStep(NpcID.RANALPH_DEVERE, "Kill Ranalph.");
    ////  NPCStep killIrvig = new NPCStep(NpcID.IRVIG_SENAY, "Kill Irvig.");
    // NPCStep killSan = new NPCStep(NpcID.SAN_TOJALON, "Kill San.");
    //  NPCStep defeatDemon = new NPCStep(NpcID.NEZIKCHENED, "Defeat Nezikchened.");


    ObjectStep useTotemOnTotemAgain = new ObjectStep(ObjectID.TOTEM_POLE_2938, new RSTile(2852, 2917, 0), "Use the new totem on one of the corrupted totems.", yommiTotemHighlighted);
     */
    NPCStep returnToRadimus = new NPCStep(10712, new RSTile(2725, 3369, 0), gildedTotem);
    NPCStep talkToRadimusInGuild = new NPCStep(10713, new RSTile(2729, 3382, 0));
    NPCStep talkToRadimusInGuildAgain = new NPCStep(10713, new RSTile(2729, 3382, 0));

    //
    UseItemOnObjectStep plantSeedsStep = new UseItemOnObjectStep(germinatedSeeds.getId(), 2956,
            new RSTile(2808, 2905), Inventory.find(germinatedSeeds.getId()).length == 0);

    UseItemOnObjectStep useTotemOnTotem = new UseItemOnObjectStep(yommiTotemHighlighted.getId(),
            ObjectID.TOTEM_POLE_2938,
            new RSTile(2852, 2917, 0),
            "Put Protect from Melee on, and use the new totem on one of the corrupted totems.",
            yommiTotemHighlighted, combatGear);


    public void useTotemOnTotemStep() {
        if (yommiTotem.check()) {
            PathingUtil.walkToTile(new RSTile(2852, 2917, 0), 2, false);
            if (Prayer.getPrayerPoints() < 12)
                PrayerUtil.drinkPrayerPotion();


            Prayer.enable(Prayer.PRAYERS.PROTECT_FROM_MELEE);
            if (!Combat.isUnderAttack() && Prayer.isPrayerEnabled(Prayer.PRAYERS.PROTECT_FROM_MELEE)) {
                useTotemOnTotem.useItemOnObject();
                if (Timer.waitCondition(NPCInteraction::isConversationWindowUp, 3500, 4500))
                    NPCInteraction.handleConversation();
                if (Timer.waitCondition(() -> NPCs.findNearest("Nezikchened").length > 0, 5000, 7000))
                    Timer.waitCondition(Combat::isUnderAttack, 7000, 9000);
            }
        }

    }

    public void killGhostDudes() {
        if (GameState.getSetting(LegendsUtils.LEGENDS_GAME_SETTING) == 32) {
            RSNPC[] zombieNpc = NPCs.findNearest(3964, 3965, 3966);
            RSNPC[] nezik = NPCs.findNearest(3962);
            if (Combat.isUnderAttack()) {
                if (zombieNpc.length > 0 && !zombieNpc[0].isInCombat())
                    Utils.clickNPC(zombieNpc[0], "Attack", false);

                if (nezik.length > 0 && !nezik[0].isInCombat() &&
                        Utils.clickNPC(nezik[0], "Attack", false))
                    Timer.waitCondition(() -> nezik[0].isInCombat(), 4700, 6500);

                if (Prayer.getPrayerPoints() < 17)
                    PrayerUtil.drinkPrayerPotion();

                if (!Prayer.isPrayerEnabled(Prayer.PRAYERS.PROTECT_FROM_MELEE))
                    Prayer.enable(Prayer.PRAYERS.PROTECT_FROM_MELEE);
            }

        }
    }

    QuestStep enterMossyRockAgain, pickUpBook, waitForBook;
    ConditionalStep runePuzzle, gemPuzzle, blessBowl;

    RSArea WHOLE_CAVE_AREA = new RSArea(new RSTile(2751, 9344, 0), new RSTile(2815, 9280, 0));
    RSArea WHOLE_CAVE_AREA_2 = new RSArea(new RSTile(2435, 4669, 0), new RSTile(2367, 4735, 0));


    public boolean makeGoldBowlStep() {
        if (!new Conditions(goldBar2, sketch, hammer).check() && !goldBowl.check()) {
            //get items
            Log.debug("Need items for makeBowlStep");
        } else if (!goldBowl.check()) {
            Log.debug("Making Gold Bowl");
            UseItemOnObjectStep makeBowl = new UseItemOnObjectStep(ItemID.GOLD_BAR, ObjectID.ANVIL,
                    new RSTile(3187, 3426, 0),
                    org.tribot.script.sdk.Inventory.contains(ItemID.GOLD_BOWL),
                    goldBar2, sketch, hammer);

            makeBowl.addDialogStep("Yes.");
            makeBowl.execute();

            if (NPCInteraction.isConversationWindowUp()) {
                NPCInteraction.handleConversation("Yes.");
            }
        }
        return goldBowl.check();
    }

    public void setUpSteps() {

        Map<Integer, QuestStep> steps = new HashMap<>();


        runePuzzle = new ConditionalStep(enterMossyRockAgain);
        runePuzzle.addStep(new Conditions(inCaveRoom4, addedLawRune), useLaw2);
        runePuzzle.addStep(new Conditions(inCaveRoom4, addedEarthRune), useLaw);
        runePuzzle.addStep(new Conditions(inCaveRoom4, addedMindRune), useEarth);
        runePuzzle.addStep(new Conditions(inCaveRoom4, addedSoulRune), useMind);
        runePuzzle.addStep(new Conditions(inCaveRoom4, searchedMarkedWall), useSoul);
        runePuzzle.addStep(new Conditions(inCaveRoom4), searchMarkedWall);
        runePuzzle.addStep(new Conditions(inCaveRoom3), enterGate2);
        runePuzzle.addStep(new Conditions(inCaveRoom2), enterGate1);
        runePuzzle.addStep(new Conditions(inCaveRoom1), enterBookcase);
        runePuzzle.setLockingCondition(inCaveRoom5);
        runePuzzle.setBlocker(true);

    /*  gemPuzzle = new ConditionalStep(useSapphire);
        gemPuzzle.addStep(new Conditions(bookNearby), pickUpBook);
        gemPuzzle.addStep(new Conditions(bookAppearing), waitForBook);
        gemPuzzle.addStep(new Conditions(sapphirePlaced, diamondPlaced, rubyPlaced, topazPlaced, jadePlaced, emeraldPlaced), useOpal);
        gemPuzzle.addStep(new Conditions(sapphirePlaced, diamondPlaced, rubyPlaced, topazPlaced, jadePlaced), useEmerald);
        gemPuzzle.addStep(new Conditions(sapphirePlaced, diamondPlaced, rubyPlaced, topazPlaced), useJade);
        gemPuzzle.addStep(new Conditions(sapphirePlaced, diamondPlaced, rubyPlaced), useTopaz);
        gemPuzzle.addStep(new Conditions(sapphirePlaced, diamondPlaced), useRuby);
        gemPuzzle.addStep(new Conditions(sapphirePlaced), useDiamond);
        gemPuzzle.setLockingCondition(bindingBook.alsoCheckBank(questBank));
        gemPuzzle.setBlocker(true);

        blessBowl = new ConditionalStep(makeBowl);
        blessBowl.addStep(new Conditions(inKharazi, goldBowlBlessed.alsoCheckBank(questBank), reed), useReedOnPool);
        blessBowl.addStep(new Conditions(inKharazi, goldBowlBlessed.alsoCheckBank(questBank)), useMacheteOnReeds);
        blessBowl.addStep(new Conditions(inKharazi, goldBowl.alsoCheckBank(questBank), gujuoNearby), talkToGujuoWithBowl);
        blessBowl.addStep(new Conditions(inKharazi, goldBowl.alsoCheckBank(questBank)), spinBullToBless);
        blessBowl.addStep(goldBowl.alsoCheckBank(questBank), enterJungleWithBowl);

        ConditionalStep solvingCaves = new ConditionalStep(enterJungleWithRoarer);
        solvingCaves.addStep(new Conditions(inFire, nezNearby), fightNezikchenedInFire);
        solvingCaves.addStep(new Conditions(inFire, bindingBook.alsoCheckBank(questBank)), useBindingBookOnUngadulu);
        solvingCaves.addStep(new Conditions(inCaves, bindingBook.alsoCheckBank(questBank), goldBowlFull), useBowlOnFireWall);
        solvingCaves.addStep(new Conditions(bindingBook.alsoCheckBank(questBank), goldBowlFull), enterMossyRockWithBowl);
        solvingCaves.addStep(bindingBook.alsoCheckBank(questBank), blessBowl);
        solvingCaves.addStep(inCaveRoom5, gemPuzzle);
        solvingCaves.addStep(hadSketch, runePuzzle);
        solvingCaves.addStep(new Conditions(inKharazi, gujuoNearby), talkToGujuoAgain);
        solvingCaves.addStep(new Conditions(inKharazi), spinBullAgain);
        solvingCaves.addStep(inCaves, leaveCave);

        steps.put(8, solvingCaves);
        // No step 9 encountered, but including as may occur for some players
        steps.put(9, solvingCaves);
        steps.put(10, solvingCaves);
        steps.put(11, solvingCaves);

        ConditionalStep talkingToUngadulu = new ConditionalStep(enterMossyRockAfterFight);
        talkingToUngadulu.addStep(germinatedSeeds.alsoCheckBank(questBank), useBowlOnSeeds);
        talkingToUngadulu.addStep(inFire, talkToUngadulu);
        talkingToUngadulu.addStep(inCaves, enterFireAfterFight);

        steps.put(12, talkingToUngadulu);*/
        searchMarkedWall.addDialogStep("Investigate the outline of the door.", "Yes, I'll go through!");
        searchMosttRockWithBowl.setHandleChat(true);
        searchMosttRockWithBowl.addDialogStep("Yes, I'll crawl through, I'm very athletic.");
        talkToGujuoWithBowl.addDialogStep("Yes, I'd like you to bless my gold bowl.");
        //   useReedOnPoolAgain.addAlternateObjects(ObjectID.POLLUTED_WATER);
        enterGate2.addDialogStep("Yes, I'm very strong, I'll force them open.");
        sketchEast.addDialogStep("Start Mapping Khazari Jungle.");
        sketchMiddle.addDialogStep("Start Mapping Khazari Jungle.");
        sketchWest.addDialogStep("Start Mapping Khazari Jungle.");
        pushBoulder.setInteractionString("Push");
        // enterMossyRockAfterFight.addDialogStep("Yes, I'll crawl through, I'm very athletic.");
        talkToUngadulu.addDialogStep("I need to collect some Yommi tree seeds for Gujuo.");
        talkToGujuoAfterSeeds.addDialogStep("The water pool has dried up and I need more water.",
                "Where is the source of the spring of pure water?");

        enterMossyRockToSource.addDialogStep("Yes, I'll crawl through, I'm very athletic.");
        enterBookcaseToSource.addDialogStep("Yes please!");
        enterGate2ToSource.addDialogStep("Yes, I'm very strong, I'll force them open.");
        searchMarkedWallToSource.addDialogStep("Investigate the outline of the door.", "Yes, I'll go through!");

        //    climbDownWinch.addAlternateObjects(ObjectID.WINCH_2935);
        climbDownWinch.addDialogStep("Yes, I'll shimmy down the rope into possible doom.");
        //  enterMossyRockForViyeldi.addDialogStep("Yes, I'll crawl through, I'm very athletic.");
        talkToEchned.addDialogStep("Who's asking?", "Yes, I need it for my quest.", "What can I do about that?",
                "I'll do what I must to get the water.", "Ok, I'll do it.");
        // useTotemOnTotem.addAlternateObjects(ObjectID.TOTEM_POLE_2936);
        //  useTotemOnTotemAgain.addAlternateObjects(ObjectID.TOTEM_POLE_2936);
        //  returnToRadimus.setRadius(5);
        enterBookcase.addDialogStep("Yes please!");
        talkToRadimusInGuild.addDialogStep("Yes, I'll train now.");
        talkToRadimusInGuild.setRadius(13);
        talkToRadimusInGuildAgain.setRadius(13);

    }

    public void enterCave() {
        if (!WHOLE_CAVE_AREA.contains(Player.getPosition()) && !WHOLE_CAVE_AREA_2.contains(Player.getPosition())) {
            cQuesterV2.status = "Searching Mossy rock";
            if (braveryPotion.check() && LegendsUtils.enterForest()) {
                searchMossyRockWithBravery.setHandleChat(true);
                searchMossyRockWithBravery.addDialogStep("Yes, I'll crawl through, I'm very athletic.");
                searchMossyRockWithBravery.execute();
                Timer.waitCondition(() -> WHOLE_CAVE_AREA.contains(Player.getPosition()), 6000);
                return;

            }
            searchMosttRockWithBowl.execute();
            enterMossyRockWithBowl.execute();
        }
    }

    public void killDemon() {
        if (inFire.check()) {
            cQuesterV2.status = "USing book on Ungadulu";
            PrayerUtil.prayMelee();
            if (!Combat.isUnderAttack()) {
                useBindingBookOnUngadulu.execute();
                if (NPCInteraction.isConversationWindowUp())
                    NPCInteraction.handleConversation();
                Timer.waitCondition(Combat::isUnderAttack, 5000, 7000);
            }
            if (Combat.isUnderAttack()) {
                Timer.waitCondition(() -> !Combat.isUnderAttack() ||
                        Prayer.getPrayerPoints() < 15, 15000, 25000);
                if (Prayer.getPrayerPoints() < 15) {
                    PrayerUtil.drinkPrayerPotion();
                }
            }
        }
    }

    public void navigateDungeon() {
        if (FIRST_AREA_OF_CAVE.contains(Player.getPosition())) {
            cQuesterV2.status = "Searching Book case";
            enterBookcaseToSource.setUseLocalNav(true);
            enterBookcaseToSource.setHandleChat(true);
            enterBookcaseToSource.execute();
            Timer.waitCondition(() -> SECOND_AREA_BEFORE_FIRST_GATE.contains(Player.getPosition()), 7000);
        }
        if (SECOND_AREA_BEFORE_FIRST_GATE.contains(Player.getPosition())) {
            cQuesterV2.status = "Passing gate 1";
            enterGate1.setUseLocalNav(true);
            enterGate1.setHandleChat(true);
            enterGate1.execute();
            NPCInteraction.waitForConversationWindow();
            if (NPCInteraction.isConversationWindowUp())
                NPCInteraction.handleConversation();
            General.sleep(1000, 2000);
        }
        if (SECOND_AREA_BEFORE_SECOND_GATE.contains(Player.getPosition())) {
            cQuesterV2.status = "Passing rocks";
            smashRocks.setUseLocalNav(true);
            smashRocks.execute();
            Timer.waitCondition(() -> Player.getAnimation() != -1, 1000, 1750);
        }
        if (SECOND_AREA_BEFORE_THIRD_GATE.contains(Player.getPosition())) {
            cQuesterV2.status = "Passing gate 2";
            smashRocks2.setUseLocalNav(true);
            smashRocks2.execute();
            for (int i = 0; i < 3; i++) {
                if (Timer.waitCondition(() -> Player.getAnimation() != -1, 1000, 1750))
                    Timer.waitCondition(() -> SECOND_AREA_BEFORE_FOURTH_GATE.contains(Player.getPosition()), 2500, 4000);
                if (SECOND_AREA_BEFORE_FOURTH_GATE.contains(Player.getPosition()))
                    break;
            }
        }
        if (SECOND_AREA_BEFORE_FOURTH_GATE.contains(Player.getPosition())) {
            cQuesterV2.status = "Passing rocks 3";
            for (int i = 0; i < 3; i++) {
                General.println("smashing rock");
                smashRocks3.setUseLocalNav(true);
                smashRocks3.execute();
                if (Timer.waitCondition(() -> Player.getAnimation() != -1, 1000, 1750))
                    Timer.waitCondition(() -> SECOND_AREA_BEFORE_FIFTH_GATE.contains(Player.getPosition()), 2500, 4000);
                if (SECOND_AREA_BEFORE_FIFTH_GATE.contains(Player.getPosition()))
                    break;
            }

        }
        if (SECOND_AREA_BEFORE_FIFTH_GATE.contains(Player.getPosition())) {
            cQuesterV2.status = "Passing rocks 3";
            enterGate2.setUseLocalNav(true);
            enterGate2.setHandleChat(true);
            enterGate2.addDialogStep("Yes, I'm very strong, I'll force them open.");
            enterGate2.execute();
        } else {//if (THIRD_AREA_PT1.contains(Player.getPosition())){
            if (inCaveRoom4.check()) {
                cQuesterV2.status = "Searching marked wall";
                if (JUST_AFTER_GATE_AREA.contains(Player.getPosition()) &&
                        PathingUtil.localNavigation(new RSTile(2804, 9287, 0))) {
                    PathingUtil.movementIdle();
                    General.sleep(4000, 5000);
                }
                if (THIRD_AREA_PT1.contains(Player.getPosition())) {
                    General.sleep(4000, 5000);
                    jumpOverJaggedWall.setUseLocalNav(true);
                    jumpOverJaggedWall.execute();
                }
                if (GameState.getSetting(LegendsUtils.LEGENDS_GAME_SETTING) == 10)
                    return;
                searchMarkedWallToSource.setHandleChat(true);
                searchMarkedWallToSource.execute();
            }
        }
        Inventory.drop(1480); //rocks
        Utils.unselectItem();
    }

    public void jumpRocks(RSArea area, RSTile rockTile) {
        General.println("[Debug]: Jumping rocks");
        Predicate<RSObject> pred = Filters.Objects.nameContains("Rock")
                .and(Filters.Objects.tileEquals(rockTile));
        if (area.contains(Player.getPosition()))
            General.println("[Debug]: Area contaims player");
        if (area.contains(Player.getPosition()) &&
                Utils.clickObject(pred, "Climb-over")) {
            if (Timer.waitCondition(() -> Player.isMoving(), 2000, 3000)) {
                Timer.waitCondition(() -> NPCInteraction.isConversationWindowUp(), 8000, 10000);
                NPCInteraction.handleConversation("Yes, I can think of nothing more exciting!",
                        "Yes, I want to climb over the rocks.");
                Timer.waitCondition(() -> !area.contains(Player.getPosition()), 8000, 10000);
                General.sleep(500, 1250);
                if (Player.getAnimation() != -1)
                    Timer.waitCondition(() -> Player.getAnimation() == -1, 8000, 1000);
            }
        }
    }

    AreaRequirement ledge = new AreaRequirement(FIRST_LEDGE_AREA, SECOND_LEDGE_AREA, THIRD_LEDGE_AREA,
            FOURTH_LEDGE_AREA, FIFTH_LEDGE_AREA);

    public void walkDownPath() {
        if (ledge.check()) {
            RSObject[] rocks = Objects.findNearest(30, "Rocky Ledge");
            ArrayList<String> s = new ArrayList<>(Arrays.asList(
                    "Yes, I can think of nothing more exciting!",
                    "Yes, I want to climb over the rocks."));
            Predicate<RSObject> pred = Filters.Objects.nameContains("Rock");
            RSObject[] obj = Objects.findNearest(30, pred);
            PathingUtil.localNavigation(PATH_DOWN_CLIF, obj, s);
            jumpRocks(FIRST_LEDGE_AREA, new RSTile(2377, 4717));
            jumpRocks(SECOND_LEDGE_AREA, new RSTile(2377, 4728));
            jumpRocks(THIRD_LEDGE_AREA, new RSTile(2382, 4729));
            jumpRocks(FOURTH_LEDGE_AREA, new RSTile(2387, 4728));
            jumpRocks(FIFTH_LEDGE_AREA, new RSTile(2390, 4724));
        }
    }

    public void walkUpPath() {
        if (!ledge.check()) {
            cQuesterV2.status = "Climbing up path";
            RSObject[] rocks = Objects.findNearest(30, "Rocky Ledge");
            ArrayList<String> s = new ArrayList<>(Arrays.asList(
                    "Yes, I can think of nothing more exciting!",
                    "Yes, I want to climb over the rocks."));
            Predicate<RSObject> pred = Filters.Objects.nameContains("Rock");
            RSObject[] obj = Objects.findNearest(30, pred);
            Collections.reverse(Arrays.asList(PATH_DOWN_CLIF));
            PathingUtil.localNavigation(PATH_DOWN_CLIF, obj, s);
        }
    }

    public void useLumpOnFurnace() {
        cQuesterV2.status = "Going to furnace";
        useLumpOnFurnace.setUseLocalNav(true);
        useLumpOnFurnace.setWaitCond(NPCInteraction.isConversationWindowUp());
        useLumpOnFurnace.setHandleChat(true);
        useLumpOnFurnace.useItemOnObject();

        useChunkOnFurnace.setWaitCond(NPCInteraction.isConversationWindowUp());
        useChunkOnFurnace.setWaitCond(true);
        useChunkOnFurnace.useItemOnObject();

        useHunkOnFurnace.setWaitCond(NPCInteraction.isConversationWindowUp());
        useHunkOnFurnace.setWaitCond(true);
        useHunkOnFurnace.useItemOnObject();
    }

    public void pushBoulder() {
        cQuesterV2.status = "Pushing Boulder";
        if (NPCs.find("Echned Zekin").length == 0) {
            pushBoulder.setInteractionString("Push");
            pushBoulder.setUseLocalNav(true);
            pushBoulder.execute();
            Timer.waitCondition(() -> NPCs.find("Echned Zekin").length > 0, 5000, 6000);
        }
    }

    public void leaveBoulderArea() {
        if (BOULDER_AREA.contains(Player.getPosition())) {
            cQuesterV2.status = "Leaving area";
            if (PathingUtil.localNavigation(new RSTile(2420, 4689, 0))) {
                PathingUtil.movementIdle();
            }
            cQuesterV2.status = "Passing Barrier";
            passBarrier.setUseLocalNav(true);
            passBarrier.execute();
            Timer.waitCondition(() -> !BOULDER_AREA.contains(Player.getPosition()), 5000, 7000);
        }
        if (!BOULDER_AREA.contains(Player.getPosition())) {
            cQuesterV2.status = "Going to Clif";
            PathingUtil.localNavigation(new RSTile(2384, 4711, 0));

        }
    }

    UseItemOnNpcStep killViyeldi = new UseItemOnNpcStep(darkDagger.getId(), NpcID.VIYELDI, new RSTile(2379, 4712, 0), darkDagger);

    public void killViyeldi() {
        if (ledge.check() && darkDagger.check()) {
            if (!NPCInteraction.isConversationWindowUp()) {
                cQuesterV2.status = "Picking up hat";
                pickUpHat.pickUpItem();
            }
            cQuesterV2.status = "Killing Viyeldi";
            killViyeldi.execute();
            Timer.waitCondition(() -> !darkDagger.check(), 15000, 20000);
        }
    }

    public boolean enterBoulderArea() {
        if (!BOULDER_AREA.contains(Player.getPosition())) {
            cQuesterV2.status = "Passing Barrier";
            passBarrier.setUseLocalNav(true);
            passBarrier.execute();
            return Timer.waitCondition(() -> BOULDER_AREA.contains(Player.getPosition()), 5000, 7000);
        }
        return BOULDER_AREA.contains(Player.getPosition());
    }

    public void killDemonAtRocks() {
        if (Prayer.getPrayerPoints() < 15)
            PrayerUtil.drinkPrayerPotion();

        PrayerUtil.prayMelee();
        if (!Combat.isUnderAttack()) {

        }
        if (Combat.isUnderAttack()) {
            Timer.waitCondition(() -> !Combat.isUnderAttack() ||
                    Prayer.getPrayerPoints() < 15, 35000, 45000);
            if (Prayer.getPrayerPoints() < 15) {
                PrayerUtil.drinkPrayerPotion();
            }
        }
    }

    UseItemOnObjectStep useAxe = new UseItemOnObjectStep(ItemID.RUNE_AXE, ObjectID.ADULT_YOMMI_TREE,
            new RSTile(2808, 2905),
            Objects.find(20, ObjectID.FELLED_YOMMI_TREE).length > 0, runeOrDragonAxe);

    UseItemOnObjectStep useAxeAgain = new UseItemOnObjectStep(ItemID.RUNE_AXE, ObjectID.FELLED_YOMMI_TREE,
            new RSTile(2808, 2905),
            Objects.find(20, ObjectID.TRIMMED_YOMMI).length > 0, runeOrDragonAxe);


    UseItemOnObjectStep craftTree = new UseItemOnObjectStep(ItemID.RUNE_AXE, ObjectID.TRIMMED_YOMMI,
            new RSTile(2808, 2905),
            Objects.find(20, ObjectID.TOTEM_POLE_2954).length > 0, runeOrDragonAxe);


    ObjectStep pickUpTotem = new ObjectStep(ObjectID.TOTEM_POLE_2954, new RSTile(2808, 2905),
            "Lift");

    public void enterLegendsGuild() {
        if (!INSIDE_LEGENDS_GUILD.contains(Player.getPosition())) {
            Log.log("[Debug]: Going to lgends guild");
            PathingUtil.walkToTile(OUTSIDE_LEGENDS_GUILD, 1, false);
            PathingUtil.localNavigation(new RSTile(2728, 3363, 0));
        }
    }

    public void initialiseListeners() {

        InterfaceObserver interfaceObserver = new InterfaceObserver(() -> true);
        interfaceObserver.addListener(this);
        interfaceObserver.addRSInterfaceChild(229, 1);
        interfaceObserver.addRSInterfaceChild(193, 2);
        interfaceObserver.addRSInterfaceChild(231, 5); // npc's text area
        interfaceObserver.start();
    }


    public Map<Integer, QuestStep> loadSteps() {
        Map<Integer, QuestStep> steps = new HashMap<>();


        ConditionalStep plantSeedAttempt = new ConditionalStep(useMacheteOnReedsAgain);
        plantSeedAttempt.addStep(reed, useReedOnPool);
        plantSeedAttempt.addStep(inCaves, leaveCaveWithSeed);

        steps.put(13, plantSeedAttempt);


        //steps.put(35, useTotemOnTotemAgain);

        steps.put(40, returnToRadimus);
        steps.put(45, returnToRadimus);

        steps.put(50, talkToRadimusInGuild);
        steps.put(55, talkToRadimusInGuild);
        steps.put(60, talkToRadimusInGuild);
        steps.put(65, talkToRadimusInGuild);

        steps.put(70, talkToRadimusInGuildAgain);

        return steps;
    }

    @Override
    public String toString() {
        return "Legends Quest (" + GameState.getSetting(LegendsUtils.LEGENDS_GAME_SETTING) + ")";
    }

    @Override
    public Priority priority() {
        return Priority.MEDIUM;
    }

    @Override
    public boolean validate() {
        return cQuesterV2.taskList.get(0).equals(this);
    }

    @Override
    public void execute() {
        initialiseListeners();
        setUpSteps();
        for (int i = 0; i < 100; i++) {
            cQuesterV2.gameSettingInt = LegendsUtils.LEGENDS_GAME_SETTING;

            if (GameState.getSetting(LegendsUtils.LEGENDS_GAME_SETTING) == 0) {
                startQuest();
            }
            if (GameState.getSetting(LegendsUtils.LEGENDS_GAME_SETTING) == 1) {
                sketchJungle();
            } else if (GameState.getSetting(LegendsUtils.LEGENDS_GAME_SETTING) == 2) {

                if (!WHOLE_CAVE_AREA_2.contains(Player.getPosition()) && !WHOLE_CAVE_AREA.contains(Player.getPosition())) {
                    if (!gearTask.isSatisfied() || !gemInventory.check()) {
                        cQuesterV2.status = "Banking";
                        gearTask.execute();
                        gemInventory.withdrawItems();
                    }
                }
                cQuesterV2.status = "Using notes";
                useNotes.addDialogStep("Yes, go ahead make a copy!");
                useNotes.execute();
            } else if (GameState.getSetting(LegendsUtils.LEGENDS_GAME_SETTING) == 3) {
                talkWithGujuo();
            } else if (GameState.getSetting(LegendsUtils.LEGENDS_GAME_SETTING) == 4) {
                talkWithGujuo();
            } else if (GameState.getSetting(LegendsUtils.LEGENDS_GAME_SETTING) >= 5 &&
                    GameState.getSetting(LegendsUtils.LEGENDS_GAME_SETTING) < 8) {

                if (new Conditions(inKharazi, talkedToUngadulu).check()) {
                    spinBull();
                    talkToGujuoAgain.execute();
                } else if (new Conditions(inCaves, talkedToUngadulu).check()) {
                    cQuesterV2.status = "Leaveing cave";
                    Log.debug("Leaveing cave");
                    leaveCave.execute();
                } else if (!inCaves.check()) {
                    cQuesterV2.status = "Entering Mossy Rock";
                    Log.debug("Entering Mossy Rock");
                    enterMossyRock.addDialogStep("Yes, I'll crawl through, I'm very athletic.");
                    enterMossyRock.execute();
                } else {
                    Log.debug("Investigating fire");
                    ObjectStep investigateFireWall = new ObjectStep(ObjectID.FIRE_WALL,
                            new RSTile(2790, 9333, 0), "Investigate",
                            NPCInteraction.isConversationWindowUp());
                    investigateFireWall.addDialogStep("How can I extinguish the flames?",
                            "Where do I get pure water from?");
                    investigateFireWall.execute();
                }

            } else if (GameState.getSetting(LegendsUtils.LEGENDS_GAME_SETTING) == 8) {
                makeGoldBowlStep();
                cQuesterV2.status = "Blessing bowl";
                if (LegendsUtils.enterForest()) {
                    Log.debug("Blessing bowl and filling bowl");
                    blessBowl();
                }
                if (inKharazi.check())
                    fillBowl();

            } else if (GameState.getSetting(LegendsUtils.LEGENDS_GAME_SETTING) == 10) {
                if (!WHOLE_CAVE_AREA.contains(Player.getPosition()) &&
                        !WHOLE_CAVE_AREA_2.contains(Player.getPosition()))
                    LegendsUtils.enterForest();

                fillBowl();

                enterCave();

                if ((!goldBowlBlessed.check() && !goldBowlFull.check() && !goldBowl.check()) || !bindingBook.check()) {
                    navigateDungeon();
                    if (inCaveRoom4.check()) {
                        cQuesterV2.status = "Searching marked wall";
                        if (JUST_AFTER_GATE_AREA.contains(Player.getPosition()) &&
                                PathingUtil.localNavigation(new RSTile(2804, 9287, 0)))
                            PathingUtil.movementIdle();

                        jumpOverJaggedWall.setUseLocalNav(true);
                        jumpOverJaggedWall.execute();

                        searchMarkedWall.setUseLocalNav(true);
                        searchMarkedWall.execute();
                        if (inCaveRoom4.check()) {
                            useSoul.useItemOnObject();
                            useMind.useItemOnObject();
                            useEarth.useItemOnObject();
                            useLaw.useItemOnObject();
                            useLaw2.useItemOnObject();
                            if (NPCInteraction.isConversationWindowUp())
                                NPCInteraction.handleConversation();
                        }
                        searchMarkedWall.setUseLocalNav(true);
                        searchMarkedWall.execute();
                        Timer.waitCondition(()-> inCaveRoom5.check(), 2500,4000);
                    }
                    if (inCaveRoom5.check() && !bindingBook.check()) {
                        cQuesterV2.status = "Using Gems";
                        useSapphire.useItemOnObject();

                        useEmerald.useItemOnObject();
                        useRuby.useItemOnObject();

                        useDiamond.setTileRadius(1);
                        useDiamond.useItemOnObject();

                        useOpal.setTileRadius(1);
                        useOpal.useItemOnObject();

                        useJade.setTileRadius(1);
                        useJade.useItemOnObject();

                        useTopaz.setTileRadius(1);
                        useTopaz.useItemOnObject();
                        cQuesterV2.status = "Waiting for binding book to appear";
                        Timer.waitCondition(() -> GroundItems.find(bindingBook.getId()).length > 0,
                                20000, 25000);
                        Utils.clickGroundItem(bindingBook.getId());

                    }
                }
                if (goldBowlFull.check() && bindingBook.check()) {
                    if (inCaveRoom5.check() && LegendsUtils.enterForest()){
                        if (inKharazi.check()){
                            enterMossyRockWithBowl.execute();
                        }
                    }
                    enterFire();
                    killDemon();
                }

            }

            if (GameState.getSetting(LegendsUtils.LEGENDS_GAME_SETTING) == 12) {
                // after killing demon in fire circle
                if (!germinatedSeeds.check() && !yommiSeeds.check())
                    talkToUngaduluAfterFight();
                addWaterToSeeds();
            }
            if (GameState.getSetting(LegendsUtils.LEGENDS_GAME_SETTING) == 13) {
                if (inFire.check() && Utils.clickObject("Fire Wall", "Touch", true)) {
                    Timer.waitCondition(() -> !inFire.check(), 4000, 6000);
                }

                plantSeeds();
            }

            if (GameState.getSetting(LegendsUtils.LEGENDS_GAME_SETTING) == 14) {
                if (spinBull())
                    talkToGujuoAfterSeeds.execute();
            }

            if (GameState.getSetting(LegendsUtils.LEGENDS_GAME_SETTING) == 15) {
                if (!WHOLE_CAVE_AREA_2.contains(Player.getPosition())) {
                    bank(bankTask);
                    getArdrigal();
                    getSnakeWeed();
                    makePotion();
                    enterJungleAfterBravery();
                    enterCave();
                    navigateDungeon();
                    castSpellOnDoor();
                    climbDownWinch();
                }
            }
            if (GameState.getSetting(LegendsUtils.LEGENDS_GAME_SETTING) == 16) {
                walkDownPath();
                killAll();
                useLumpOnFurnace();

            } else if (GameState.getSetting(LegendsUtils.LEGENDS_GAME_SETTING) == 17) {
                cQuesterV2.status = "Going to use heart on rock";
                useHeartOnRock.setUseLocalNav(true);
                useHeartOnRock.setHandleChat(true);
                useHeartOnRock.useItemOnObject();
                cQuesterV2.status = "Going to use heart on recess";
                useHeartOnRecess.setHandleChat(true);
                useHeartOnRecess.setUseLocalNav(true);
                useHeartOnRecess.useItemOnObject();
            } else if (GameState.getSetting(LegendsUtils.LEGENDS_GAME_SETTING) == 18) {
                enterBoulderArea();
                pushBoulder();
            } else if (GameState.getSetting(LegendsUtils.LEGENDS_GAME_SETTING) == 19) {
                pushBoulder();
                cQuesterV2.status = "Talking to Echned";
                talkToEchned.setUseLocalNav(true);
                talkToEchned.addDialogStep("Who's asking?", "Yes, I need it for my quest.", "What can I do about that?",
                        "I'll do what I must to get the water.", "Ok, I'll do it.");
                talkToEchned.execute();

            } else if (GameState.getSetting(LegendsUtils.LEGENDS_GAME_SETTING) == 20) {
                if (!WHOLE_CAVE_AREA.contains(Player.getPosition()) &&
                        !WHOLE_CAVE_AREA_2.contains(Player.getPosition())) {
                    if (!LegendsUtils.WHOLE_FOREST_AREA.contains(Player.getPosition()))
                        LegendsUtils.enterForest();

                    enterCave();
                }
                if (!WHOLE_CAVE_AREA_2.contains(Player.getPosition())) {
                    navigateDungeon();
                }
                if (!ledge.check() && darkDagger.check()) {
                    leaveBoulderArea();
                    walkUpPath();
                    killViyeldi();
                }
                if (!darkDagger.check() && glowingDagger.check()) {
                    walkDownPath();
                    enterBoulderArea();
                    if (!Combat.isUnderAttack())
                        pushBoulder();
                    killDemonAtRocks();
                }
            } else if (GameState.getSetting(LegendsUtils.LEGENDS_GAME_SETTING) == 22) {
                pushBoulder();
                useBowlOnSacredWater.setUseLocalNav(true);
                useBowlOnSacredWater.useItemOnObject();
            } else if (GameState.getSetting(LegendsUtils.LEGENDS_GAME_SETTING) == 25) {
                //leave the dungeon and re-enter forest
                if (!LegendsUtils.WHOLE_FOREST_AREA.contains(Player.getPosition()))
                    LegendsUtils.enterForest();
                if (!goldBowlFull.check() && !germinatedSeeds.check()) {
                    cQuesterV2.status = "Using Machete on Reeds";
                    useMacheteOnReedsEnd.useItemOnObject();
                    cQuesterV2.status = "Using Reed on pool";
                    useReedOnPool.useItemOnObject();
                }
                if (goldBowlFull.check() && !germinatedSeeds.check()) {
                    cQuesterV2.status = "Germinating seeds";
                    useBowlOnSeeds.execute();
                    NPCInteraction.handleConversation();
                }
                if (goldBowlFull.check() && germinatedSeeds.check()) {
                    cQuesterV2.status = "Spinning Roarer";
                    //  spinBullAfterSeeds.execute();
                    //if(Timer.waitCondition(()-> NPCInteraction.isConversationWindowUp(), 4000,6000))
                    //    NPCInteraction.handleConversation();
                    cQuesterV2.status = "Planting Seeds";
                    plantSeedsStep.useItemOnObject();

                }
                useWaterOnTree.useItemOnObject();
                if (NPCInteraction.isConversationWindowUp())
                    NPCInteraction.handleConversation();
                useAxe.useItemOnObject();
                Timer.waitCondition(() -> Objects.find(20, ObjectID.FELLED_YOMMI_TREE).length > 0, 2500, 3500);
                useAxeAgain.useItemOnObject();
                craftTree.useItemOnObject();
                pickUpTotem.execute();
            } else if (GameState.getSetting(LegendsUtils.LEGENDS_GAME_SETTING) == 30) {
                useTotemOnTotemStep();
                if (NPCInteraction.isConversationWindowUp())
                    NPCInteraction.handleConversation();
            } else if (GameState.getSetting(LegendsUtils.LEGENDS_GAME_SETTING) == 32) {
                //fight
                killGhostDudes();

            } else if (GameState.getSetting(LegendsUtils.LEGENDS_GAME_SETTING) == 35) {
                // disable prayer, place totem & handle chat

            } else if (GameState.getSetting(LegendsUtils.LEGENDS_GAME_SETTING) == 40) {

                // wait for Gujuo and handle chat
            } else if (GameState.getSetting(LegendsUtils.LEGENDS_GAME_SETTING) == 45) {
                cQuesterV2.status = "Returning to radimus";
                enterLegendsGuild();
                returnToRadimus.setUseLocalNav(true);
                returnToRadimus.execute();
                // talk to Radimus Erkle in the little house in guild
            } else if (GameState.getSetting(LegendsUtils.LEGENDS_GAME_SETTING) == 50) {
                cQuesterV2.status = "Returning to radimus in guild";
                enterLegendsGuild();
                talkToRadimusInGuild.setUseLocalNav(true);
                Log.log(talkToRadimusInGuild.isUseLocalNav());
                talkToRadimusInGuild.execute();
                // talk to Radimus Erkle in guild
                // claim reward 1
            } else if (GameState.getSetting(LegendsUtils.LEGENDS_GAME_SETTING) == 55) {
                // claim reward 2
            } else if (GameState.getSetting(LegendsUtils.LEGENDS_GAME_SETTING) == 60) {
                // claim reward 3
            } else if (GameState.getSetting(LegendsUtils.LEGENDS_GAME_SETTING) == 65) {
                // claim reward 4
            } else if (GameState.getSetting(LegendsUtils.LEGENDS_GAME_SETTING) == 70) {
                //finish dialogue
            } else if (GameState.getSetting(LegendsUtils.LEGENDS_GAME_SETTING) == 75) {
                // Done Quest, close window
            }
        }
    }

    @Override
    public String questName() {
        return "Legend's Quest (" + GameState.getSetting(LegendsUtils.LEGENDS_GAME_SETTING) + ")";
    }

    @Override
    public boolean checkRequirements() {
        return false;
    }

    WidgetTextRequirement completeTextAppeared = new WidgetTextRequirement(WidgetInfo.DIALOG_SPRITE_TEXT,
            "You have already completed this part of the map.");

    WidgetTextRequirement middleWidgetText =
            new WidgetTextRequirement(WidgetInfo.DIALOG_SPRITE_TEXT,
                    "Middle Kharazi Jungle- *** Completed");

    WidgetTextRequirement easternWidgetText =
            new WidgetTextRequirement(WidgetInfo.DIALOG_SPRITE_TEXT,
                    "Eastern Kharazi Jungle- *** Completed");

    WidgetTextRequirement westWidgetText =
            new WidgetTextRequirement(WidgetInfo.DIALOG_SPRITE_TEXT,
                    "Western Kharazi Jungle- *** Completed");

    Conditions completeEast = new Conditions(true, LogicType.OR, easternWidgetText,
            new Conditions(inEast, completeTextAppeared));
    Conditions completeMiddle = new Conditions(true, LogicType.OR,
            middleWidgetText, new Conditions(inMiddle, completeTextAppeared));
    Conditions completeWest = new Conditions(true, LogicType.OR,
            westWidgetText, new Conditions(inWest, completeTextAppeared));

    @Override
    public void onAppear(RSInterfaceChild rsInterfaceChild) {
        String message = rsInterfaceChild.getText();
        if (message == null) {
            return;
        }
        if (completeTextAppeared.checkWidget()) {
            Log.debug("[LegendsQuest]: completeTextAppeared  Complete");
            completeTextAppeared.setHasPassed(true);
        }
        if (westWidgetText.checkWidget()) {
            Log.debug("[LegendsQuest]: West Map Widget Complete");
            westWidgetText.setHasPassed(true);
        }
        if (easternWidgetText.checkWidget()) {
            Log.debug("[LegendsQuest]: East Map Widget Complete");
            easternWidgetText.setHasPassed(true);
        }
        if (middleWidgetText.checkWidget()) {
            Log.debug("[LegendsQuest]: Middle Map Widget Complete");
            middleWidgetText.setHasPassed(true);
        }
        if (ungaduluTextOne.checkWidget()) {
            ungaduluTextOne.setHasPassed(true);
            Log.debug("[LegendsQuest]: ungaduluTextOne Complete");
        }
        if (ungaduluTextTwo.checkWidget()) {
            ungaduluTextTwo.setHasPassed(true);
            Log.debug("[LegendsQuest]: ungaduluTextTwo Complete");
        }

    }
}
