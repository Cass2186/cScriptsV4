package scripts.QuestPackages.FremennikIsles;

import org.tribot.api2007.Skills;
import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSTile;
import org.tribot.script.sdk.*;
import scripts.*;
import scripts.GEManager.GEItem;
import scripts.QuestPackages.ShiloVillage.ShilloVillage;
import scripts.QuestSteps.*;
import scripts.Requirements.*;
import scripts.Requirements.Items.ItemCollections;
import scripts.Requirements.Items.ItemRequirements;
import scripts.Requirements.Quest.QuestRequirement;
import scripts.Requirements.Util.ConditionalStep;
import scripts.Requirements.Util.Conditions;
import scripts.Requirements.Util.LogicType;
import scripts.Tasks.Priority;

import java.util.*;

public class FremennikIsles implements QuestTask {

    private static FremennikIsles quest;

    public static FremennikIsles get() {
        return quest == null ? quest = new FremennikIsles() : quest;
    }

    //Items Required
    ItemRequirement tuna, ores, jesterHat, jesterTights, jesterTop, jesterBoots, arcticLogs8, splitLogs8,
            knife, rope8, rope4, splitLogs4, yakTop, yakBottom, royalDecree, roundShield, yakTopWorn, yakBottomWorn,
            shieldWorn, meleeWeapon, food, head, needle, thread, coins15, bronzeNail, hammer, rope, axe, rope9;

    Requirement inIslands, inJatizso, inNeitiznot, inTrollLands, hasJesterOutfit, jestering1, repairedBridge1,
            repairedBridge2, inNeitiznotOrTrollLands, collectedFlosi, collectedHring, collectedSkuli,
            collectedValigga, collectedKeepa, collectedRaum, inTrollCave, inKingCave, killedTrolls;

    QuestStep talkToMord, travelToJatizso, talkToGjuki, continueTalkingToGjuki, bringOreToGjuki,
            talkToGjukiAfterOre, getJesterOutfit, talkToSlug, travelToNeitiznot, returnToRellekkaFromJatizso,
            goSpyOnMawnis, performForMawnis, tellSlugReport1, talkToMawnis, talkToMawnisWithLogs, talkToMawnisAfterItems,
            repairBridge1, repairBridge2, repairBridge1Second, talkToMawnisAfterRepair, talkToGjukiToReport,
            travelToJatizsoToReport, leaveNeitiznotToReport, collectFromHring, collectFromSkuli, collectFromVanligga, collectFromKeepa,
            talkToGjukiAfterCollection1, collectFromHringAgain, collectFromRaum, collectFromSkuliAgain, collectFromKeepaAgain, collectFromFlosi,
            talkToGjukiAfterCollection2, travelToNeitiznotToSpyAgain, talkToSlugToSpyAgain, returnToRellekkaFromJatizsoToSpyAgain, performForMawnisAgain,
            goSpyOnMawnisAgain, reportBackToSlugAgain, returnToRellekkaFromNeitiznotAfterSpy2, travelToJatizsoAfterSpy2, talkToGjukiAfterSpy2,
            returnToRellekkaFromJatizsoWithDecree, travelToNeitiznotWithDecree, talkToMawnisWithDecree, getYakArmour, returnToRellekkaFromJatizsoAfterDecree,
            travelToNeitiznotAfterDecree, talkToMawnisAfterDecree, makeShield, enterCave, killTrolls, enterKingRoom, killKing, decapitateKing, finishQuest, finishQuestGivenHead;

    //RSAreas
    RSArea islands, jatizso1, jatizso2, neitiznot1, neitiznot2, trollLands, trollCave, kingCave;


    List<ItemRequirement> items;

    ArrayList<GEItem> itemsToBuy = new ArrayList<GEItem>(
            Arrays.asList(
                    new GEItem(ItemID.RAW_TUNA, 1, 500),
                    new GEItem(ItemID.ROPE, 8, 500),
                    new GEItem(ItemID.NEITIZNOT_SHIELD, 1, 100),
                    new GEItem(ItemID.KNIFE, 1, 500),
                    new GEItem(ItemID.SPLIT_LOG, 8, 300),
                    new GEItem(ItemID.COAL, 7, 300),
                    new GEItem(ItemID.MITHRIL_ORE, 6, 300),
                    new GEItem(ItemID.YAKHIDE_ARMOUR, 1, 200),
                    new GEItem(ItemID.YAKHIDE_ARMOUR_10824, 1, 200),
                    new GEItem(ItemID.SHARK, 20, 30),
                    new GEItem(ItemID.CAMELOT_TELEPORT, 10, 50),
                    new GEItem(ItemID.MONKFISH, 12, 50),
                    new GEItem(ItemID.AMULET_OF_GLORY4, 2, 30),
                    new GEItem(ItemID.STAMINA_POTION[0], 5, 15),
                    new GEItem(ItemID.RING_OF_WEALTH[0], 1, 25)
            )
    );

    InventoryRequirement initialItemReqs = new InventoryRequirement(new ArrayList<>(
            Arrays.asList(

                    new ItemReq(ItemID.RAW_TUNA, 1),
                    new ItemReq(ItemID.NEITIZNOT_SHIELD, 1),
                    new ItemReq(ItemID.CAMELOT_TELEPORT, 5),
                    new ItemReq(ItemID.YAKHIDE_ARMOUR, 1),
                    new ItemReq(ItemID.YAKHIDE_ARMOUR_10824, 1),
                    new ItemReq(ItemID.MONKFISH, 3),
                    new ItemReq(ItemID.AMULET_OF_GLORY4, 2, 0, true, true),
                    new ItemReq(ItemID.STAMINA_POTION[0], 2, 0),
                    new ItemReq(ItemID.RING_OF_WEALTH[0], 1, 0, true),
                    new ItemReq.Builder()
                            .id(ItemID.MITHRIL_ORE)
                            .isItemNoted(true)
                            .amount(6)
                            .build(),
                    new ItemReq.Builder().id(ItemID.SPLIT_LOG)
                            .isItemNoted(true)
                            .amount(8)
                            .build(),
                    new ItemReq.Builder().id(ItemID.ROPE)
                            .isItemNoted(true)
                            .amount(8)
                            .build()
            )
    ));

    BuyItemsStep buyStep = new BuyItemsStep(itemsToBuy);

    public Map<Integer, QuestStep> loadSteps() {
        loadAreas();
        setupItemRequirements();
        setupConditions();
        setupSteps();
        Map<Integer, QuestStep> steps = new HashMap<>();

        steps.put(0, talkToMord);

        ConditionalStep goTalkToGjuki = new ConditionalStep(travelToJatizso);
        goTalkToGjuki.addStep(inIslands, talkToGjuki);

        steps.put(5, goTalkToGjuki);
        steps.put(10, goTalkToGjuki);

        ConditionalStep finishGjukiDialog = new ConditionalStep(travelToJatizso);
        finishGjukiDialog.addStep(inIslands, continueTalkingToGjuki);
        steps.put(20, finishGjukiDialog);

        ConditionalStep getMithrilOre = new ConditionalStep(travelToJatizso);
        getMithrilOre.addStep(inIslands, bringOreToGjuki);
        steps.put(30, getMithrilOre);

        ConditionalStep continueTalkingToGjukiAfterOre = new ConditionalStep(travelToJatizso);
        continueTalkingToGjukiAfterOre.addStep(inIslands, talkToGjukiAfterOre);

        steps.put(40, continueTalkingToGjukiAfterOre);

        ConditionalStep spyOnMawnis = new ConditionalStep(travelToJatizso);
        spyOnMawnis.addStep(jestering1, performForMawnis);
        spyOnMawnis.addStep(new Conditions(hasJesterOutfit, inNeitiznot), talkToSlug);
        spyOnMawnis.addStep(new Conditions(new Conditions(LogicType.OR, inJatizso, inTrollLands), hasJesterOutfit), returnToRellekkaFromJatizso);
        spyOnMawnis.addStep(hasJesterOutfit, travelToNeitiznot);
        spyOnMawnis.addStep(inJatizso, getJesterOutfit);

        steps.put(50, spyOnMawnis);

        ConditionalStep spyOnMawnisP2 = new ConditionalStep(travelToJatizso);
        spyOnMawnisP2.addStep(jestering1, performForMawnis);
        spyOnMawnisP2.addStep(new Conditions(hasJesterOutfit, inNeitiznot), goSpyOnMawnis);
        spyOnMawnisP2.addStep(new Conditions(new Conditions(LogicType.OR, inJatizso, inTrollLands), hasJesterOutfit), returnToRellekkaFromJatizso);
        spyOnMawnisP2.addStep(hasJesterOutfit, travelToNeitiznot);
        spyOnMawnisP2.addStep(inJatizso, getJesterOutfit);

        steps.put(55, spyOnMawnisP2);

        ConditionalStep reportToSlug = new ConditionalStep(travelToNeitiznot);
        reportToSlug.addStep(inNeitiznot, tellSlugReport1);

        steps.put(60, reportToSlug);
        steps.put(70, reportToSlug);
        steps.put(80, reportToSlug);

        ConditionalStep talkToMawnisToHelp = new ConditionalStep(travelToNeitiznot);
        talkToMawnisToHelp.addStep(new Conditions(inNeitiznot), talkToMawnis);
        talkToMawnisToHelp.addStep(new Conditions(LogicType.OR, inJatizso, inTrollLands), returnToRellekkaFromJatizso);

        steps.put(90, talkToMawnisToHelp);

        ConditionalStep bringMawnisItems = new ConditionalStep(travelToNeitiznot);
        bringMawnisItems.addStep(new Conditions(inNeitiznot), talkToMawnisWithLogs);
        bringMawnisItems.addStep(new Conditions(LogicType.OR, inJatizso, inTrollLands), returnToRellekkaFromJatizso);

        steps.put(100, bringMawnisItems);
        steps.put(110, bringMawnisItems);
        steps.put(120, bringMawnisItems);

        ConditionalStep talkToMawnisAfterItemsSteps = new ConditionalStep(travelToNeitiznot);
        talkToMawnisAfterItemsSteps.addStep(new Conditions(inNeitiznot), talkToMawnisAfterItems);
        talkToMawnisAfterItemsSteps.addStep(new Conditions(LogicType.OR, inJatizso, inTrollLands), returnToRellekkaFromJatizso);

        steps.put(130, talkToMawnisAfterItemsSteps);

        ConditionalStep repairBridges = new ConditionalStep(travelToNeitiznot);
        repairBridges.addStep(new Conditions(inNeitiznotOrTrollLands, repairedBridge1, repairedBridge2), talkToMawnisAfterRepair);
        repairBridges.addStep(new Conditions(inNeitiznotOrTrollLands, repairedBridge1), repairBridge2);
        repairBridges.addStep(new Conditions(inNeitiznotOrTrollLands), repairBridge1);
        repairBridges.addStep(new Conditions(inJatizso), returnToRellekkaFromJatizso);

        steps.put(140, repairBridges);

        ConditionalStep reportAfterBridgeRepair = new ConditionalStep(travelToNeitiznot);
        reportAfterBridgeRepair.addStep(inNeitiznotOrTrollLands, talkToMawnisAfterRepair);
        reportAfterBridgeRepair.addStep(inJatizso, returnToRellekkaFromJatizso);

        steps.put(150, reportAfterBridgeRepair);

        ConditionalStep reportBackToGjuki = new ConditionalStep(travelToJatizsoToReport);
        reportBackToGjuki.addStep(inNeitiznotOrTrollLands, leaveNeitiznotToReport);
        reportBackToGjuki.addStep(inJatizso, talkToGjukiToReport);

        steps.put(160, reportBackToGjuki);
        steps.put(170, reportBackToGjuki);
        steps.put(180, reportBackToGjuki);
        steps.put(190, reportBackToGjuki);

        ConditionalStep collectTax = new ConditionalStep(travelToJatizsoToReport);
        collectTax.addStep(new Conditions(inJatizso, collectedValigga, collectedKeepa, collectedSkuli, collectedHring), talkToGjukiAfterCollection1);
        collectTax.addStep(new Conditions(inJatizso, collectedValigga, collectedKeepa, collectedSkuli), collectFromHring);
        collectTax.addStep(new Conditions(inJatizso, collectedValigga, collectedKeepa), collectFromSkuli);
        collectTax.addStep(new Conditions(inJatizso, collectedKeepa), collectFromVanligga);
        collectTax.addStep(inJatizso, collectFromKeepa);

        steps.put(200, collectTax);

        ConditionalStep collectTaxOnBeards = new ConditionalStep(travelToJatizsoToReport);
        collectTaxOnBeards.addStep(new Conditions(inJatizso, collectedHring, collectedRaum, collectedSkuli, collectedKeepa, collectedFlosi), talkToGjukiAfterCollection2);
        collectTaxOnBeards.addStep(new Conditions(inJatizso, collectedHring, collectedRaum, collectedSkuli, collectedKeepa), collectFromFlosi);
        collectTaxOnBeards.addStep(new Conditions(inJatizso, collectedHring, collectedRaum, collectedSkuli), collectFromKeepaAgain);
        collectTaxOnBeards.addStep(new Conditions(inJatizso, collectedHring, collectedRaum), collectFromSkuliAgain);
        collectTaxOnBeards.addStep(new Conditions(inJatizso, collectedHring), collectFromRaum);
        collectTaxOnBeards.addStep(inJatizso, collectFromHringAgain);

        steps.put(210, collectTaxOnBeards);

        ConditionalStep returnToSpyAgain = new ConditionalStep(travelToNeitiznotToSpyAgain);
        returnToSpyAgain.addStep(inNeitiznotOrTrollLands, talkToSlugToSpyAgain);
        returnToSpyAgain.addStep(inJatizso, returnToRellekkaFromJatizsoToSpyAgain);

        steps.put(230, returnToSpyAgain);

        ConditionalStep spyOnMawnisAgain = new ConditionalStep(travelToNeitiznotToSpyAgain);
        spyOnMawnisAgain.addStep(jestering1, performForMawnisAgain);
        spyOnMawnisAgain.addStep(inNeitiznotOrTrollLands, goSpyOnMawnisAgain);
        spyOnMawnisAgain.addStep(inJatizso, returnToRellekkaFromJatizsoToSpyAgain);

        steps.put(235, spyOnMawnisAgain);

        ConditionalStep reportBackToSlug = new ConditionalStep(travelToNeitiznotToSpyAgain);
        reportBackToSlug.addStep(inNeitiznotOrTrollLands, reportBackToSlugAgain);
        reportBackToSlug.addStep(inJatizso, returnToRellekkaFromJatizsoToSpyAgain);

        steps.put(240, reportBackToSlug);
        steps.put(250, reportBackToSlug);

        ConditionalStep reportBackToGjukiAgain = new ConditionalStep(travelToJatizsoAfterSpy2);
        reportBackToGjukiAgain.addStep(inJatizso, talkToGjukiAfterSpy2);
        reportBackToGjukiAgain.addStep(inNeitiznotOrTrollLands, returnToRellekkaFromNeitiznotAfterSpy2);

        steps.put(260, reportBackToGjukiAgain);

        ConditionalStep reportBackToMawnisWithDecree = new ConditionalStep(travelToNeitiznotWithDecree);
        reportBackToMawnisWithDecree.addStep(inNeitiznotOrTrollLands, talkToMawnisWithDecree);
        reportBackToMawnisWithDecree.addStep(inJatizso, returnToRellekkaFromJatizsoWithDecree);

        steps.put(270, reportBackToMawnisWithDecree);

        ConditionalStep reportBackToMawnisAfterDecree = new ConditionalStep(travelToNeitiznotAfterDecree);
        reportBackToMawnisAfterDecree.addStep(inNeitiznotOrTrollLands, talkToMawnisAfterDecree);
        reportBackToMawnisAfterDecree.addStep(inJatizso, returnToRellekkaFromJatizsoAfterDecree);

        steps.put(275, reportBackToMawnisAfterDecree);

        ConditionalStep makeArmour = new ConditionalStep(travelToNeitiznotAfterDecree);
        makeArmour.addStep(inNeitiznotOrTrollLands, getYakArmour);
        makeArmour.addStep(inJatizso, returnToRellekkaFromJatizsoAfterDecree);

        steps.put(280, makeArmour);

        ConditionalStep makeShieldSteps = new ConditionalStep(travelToNeitiznotAfterDecree);
        makeShieldSteps.addStep(inNeitiznotOrTrollLands, makeShield);
        makeShieldSteps.addStep(inJatizso, returnToRellekkaFromJatizsoAfterDecree);

        steps.put(290, makeShieldSteps);

        ConditionalStep goToKillKing = new ConditionalStep(travelToNeitiznotAfterDecree);
        goToKillKing.addStep(new Conditions(inKingCave), killKing);
        goToKillKing.addStep(new Conditions(inTrollCave, killedTrolls), enterKingRoom);
        goToKillKing.addStep(inTrollCave, killTrolls);
        goToKillKing.addStep(inIslands, enterCave);

        steps.put(300, goToKillKing);
        steps.put(310, goToKillKing);

        ConditionalStep removeHead = new ConditionalStep(travelToNeitiznotAfterDecree);
        removeHead.addStep(new Conditions(inKingCave), decapitateKing);
        removeHead.addStep(new Conditions(inTrollCave, killedTrolls), enterKingRoom);
        removeHead.addStep(inTrollCave, killTrolls);
        removeHead.addStep(inIslands, enterCave);

        steps.put(320, removeHead);

        steps.put(325, finishQuest);
        steps.put(330, finishQuestGivenHead);
        steps.put(331, finishQuestGivenHead);
        steps.put(332, finishQuestGivenHead);

        return steps;
    }

    public void setupItemRequirements() {
        needle = new ItemRequirement("Needle", ItemID.NEEDLE);
        thread = new ItemRequirement("Thread", ItemID.THREAD);
        coins15 = new ItemRequirement(ItemCollections.getCoins(), 15);
        bronzeNail = new ItemRequirement("Bronze nail", ItemID.BRONZE_NAILS);
        hammer = new ItemRequirement("Hammer", ItemCollections.getHammer());
        rope = new ItemRequirement("Rope", ItemID.ROPE);
        rope9 = new ItemRequirement("Rope", ItemID.ROPE, 9);
        yakTopWorn = new ItemRequirement(ItemID.YAKHIDE_ARMOUR, 1, true);
        yakBottomWorn = new ItemRequirement(ItemID.YAKHIDE_ARMOUR_10824, 1, true);
        shieldWorn = new ItemRequirement(ItemID.NEITIZNOT_SHIELD, 1, true);
        meleeWeapon = new ItemRequirement("Melee gear", -1, -1);
        //meleeWeapon.setDisplayItemId(BankSlotIcons.getCombatGear());
        food = new ItemRequirement(ItemID.SHARK, 10);
        tuna = new ItemRequirement("Raw tuna", ItemID.RAW_TUNA);
        axe = new ItemRequirement("Any axe", ItemCollections.getAxes());

        // tuna.setTooltip("You can buy some from Flosi in east Jatizso, or fish some from the pier.");
        if (Skill.MINING.getActualLevel() >= 55) {
            ores = new ItemRequirement("Mithril ore", ItemID.MITHRIL_ORE, 6);
        } else if (Skill.MINING.getActualLevel() >= 10) {
            ores = new ItemRequirement("Coal", ItemID.COAL, 7);
        } else {
            ores = new ItemRequirement("Tin ore", ItemID.TIN_ORE, 8);
        }
        // ores.setTooltip("You can mine some in the underground mine north west of Jatizso.");

        jesterHat = new ItemRequirement(ItemID.SILLY_JESTER_HAT, 1, true);
        jesterTop = new ItemRequirement(ItemID.SILLY_JESTER_TOP, 1, true);
        jesterTights = new ItemRequirement(ItemID.SILLY_JESTER_TIGHTS, 1, true);
        jesterBoots = new ItemRequirement(ItemID.SILLY_JESTER_BOOTS, 1, true);
        arcticLogs8 = new ItemRequirement("Arctic pine logs", ItemID.ARCTIC_PINE_LOGS, 8);
        splitLogs8 = new ItemRequirement("Split log", ItemID.SPLIT_LOG, 8);
        splitLogs4 = new ItemRequirement("Split log", ItemID.SPLIT_LOG, 4);
        yakTop = new ItemRequirement("Yak-hide armour (top)", ItemID.YAKHIDE_ARMOUR);
        yakBottom = new ItemRequirement("Yak-hide armour (bottom)", ItemID.YAKHIDE_ARMOUR_10824);
        roundShield = new ItemRequirement("Neitiznot shield", ItemID.NEITIZNOT_SHIELD);


        knife = new ItemRequirement("Knife", ItemID.KNIFE);
        rope8 = new ItemRequirement("Rope", ItemID.ROPE, 8);
        rope4 = new ItemRequirement("Rope", ItemID.ROPE, 4);

        royalDecree = new ItemRequirement("Royal decree", ItemID.ROYAL_DECREE);
        // royalDecree.setTooltip("You can get another from Gjuki on Jatizso");

        head = new ItemRequirement("Decapitated head", ItemID.DECAPITATED_HEAD_10842);
        //  head.setTooltip("You can get another from the corpse of the Ice Troll King");

        // protectRanged = new PrayerRequirement("Protect from Missiles", Prayer.PROTECT_FROM_MISSILES);
    }

    public void loadAreas() {
        islands = new RSArea(new RSTile(2298, 3771, 0), new RSTile(2432, 3913, 3));
        neitiznot1 = new RSArea(new RSTile(2304, 3775, 0), new RSTile(2368, 3842, 3));
        neitiznot2 = new RSArea(new RSTile(2368, 3823, 0), new RSTile(2383, 3842, 0));
        jatizso1 = new RSArea(new RSTile(2369, 3777, 0), new RSTile(2432, 3825, 3));
        jatizso2 = new RSArea(new RSTile(2402, 2825, 0), new RSTile(2434, 3842, 0));
        trollLands = new RSArea(new RSTile(2306, 3843, 0), new RSTile(2431, 3907, 0));
        trollCave = new RSArea(new RSTile(2373, 10263, 1), new RSTile(2412, 10300, 1));
        kingCave = new RSArea(new RSTile(2374, 10242, 1), new RSTile(2396, 10261, 1));
    }

    public void setupConditions() {
        inIslands = new AreaRequirement(islands);
        inNeitiznot = new AreaRequirement(neitiznot1, neitiznot2);
        inNeitiznotOrTrollLands = new AreaRequirement(neitiznot1, neitiznot1, trollLands);
        inJatizso = new AreaRequirement(jatizso1, jatizso2);
        inTrollLands = new AreaRequirement(trollLands);
        inTrollCave = new AreaRequirement(trollCave);
        inKingCave = new AreaRequirement(kingCave);
        hasJesterOutfit = new ItemRequirements(jesterBoots, jesterHat, jesterTights, jesterTop);
        jestering1 = new VarbitRequirement(6719, 2);
        repairedBridge1 = new VarbitRequirement(3313, 1);
        repairedBridge2 = new VarbitRequirement(3314, 1);

        collectedHring = new VarbitRequirement(3321, 1);
        collectedSkuli = new VarbitRequirement(3320, 1);
        collectedValigga = new VarbitRequirement(3324, 1);
        collectedKeepa = new VarbitRequirement(3325, 1);
        collectedRaum = new VarbitRequirement(3323, 1);
        collectedFlosi = new VarbitRequirement(3322, 1);

        killedTrolls = new VarbitRequirement(3312, 0);
    }

    public void setupSteps() {
        talkToMord = new NPCStep(NpcID.MORD_GUNNARS, new RSTile(2644, 3709, 0),
                "Talk to Mord Gunnars on a pier in north Rellekka.");
        travelToJatizso = new NPCStep(NpcID.MORD_GUNNARS, new RSTile(2644, 3709, 0), "Travel to Jatizso with Mord.");
        travelToJatizso.addDialogStep("Can you ferry me to Jatizso?");

        travelToNeitiznot = new NPCStep(NpcID.MARIA_GUNNARS_1883, new RSTile(2644, 3710, 0), "Travel to Neitiznot with Maria Gunnars.");

        talkToGjuki = new NPCStep(NpcID.KING_GJUKI_SORVOTT_IV, new RSTile(2407, 3804, 0),
                tuna);
        continueTalkingToGjuki = new NPCStep(NpcID.KING_GJUKI_SORVOTT_IV, new RSTile(2407, 3804, 0), "Talk to King Gjuki Sorvott IV on Jatizso.");
        talkToGjuki.addSubSteps(continueTalkingToGjuki);

        bringOreToGjuki = new NPCStep(NpcID.KING_GJUKI_SORVOTT_IV, new RSTile(2407, 3804, 0), ores);
        talkToGjukiAfterOre = new NPCStep(NpcID.KING_GJUKI_SORVOTT_IV, new RSTile(2407, 3804, 0), "Talk to King Gjuki Sorvott IV on Jatizso.");
        bringOreToGjuki.addSubSteps(talkToGjukiAfterOre);

        returnToRellekkaFromJatizso = new NPCStep(NpcID.MORD_GUNNARS_1940, new RSTile(2420, 3781, 0), "Return to Rellekka with Mord.");
        returnToRellekkaFromJatizso.addDialogStep("Can you ferry me to Rellekka?");

        talkToSlug = new NPCStep(NpcID.SLUG_HEMLIGSSEN, new RSTile(2335, 3811, 0),
                //    "Talk to Slug Hemligssen wearing nothing but your Silly Jester outfit.",
                jesterHat, jesterTop, jesterTights, jesterBoots);
        talkToSlug.addSubSteps(returnToRellekkaFromJatizso, travelToNeitiznot);
        talkToSlug.addDialogStep("Free stuff please.");
        talkToSlug.addDialogStep("I am ready.");

        getJesterOutfit = new ObjectStep(ObjectID.CHEST_21299, new RSTile(2407, 3800, 0), "Search the chest behind Gjuki's throne for a silly jester outfit.");
        getJesterOutfit.addDialogStep("Take the jester's hat.");
        getJesterOutfit.addDialogStep("Take the jester's top.");
        getJesterOutfit.addDialogStep("Take the jester's tights.");
        getJesterOutfit.addDialogStep("Take the jester's boots.");

        performForMawnis = new DetailedQuestStep("Perform the actions that Mawnis requests of you.");
        goSpyOnMawnis = new NPCStep(NpcID.MAWNIS_BUROWGAR, new RSTile(2335, 3800, 0),
                // "Talk to Mawnis in Neitiznot to start spying on him.",
                jesterHat, jesterTop, jesterTights, jesterBoots);
        goSpyOnMawnis.addSubSteps(performForMawnis);

        tellSlugReport1 = new NPCStep(NpcID.SLUG_HEMLIGSSEN, new RSTile(2335, 3811, 0), "Report back to Slug Hemligssen.");
        tellSlugReport1.addDialogStep("Yes I have.");
        tellSlugReport1.addDialogStep("They will be ready in two days.");
        tellSlugReport1.addDialogStep("Seventeen militia have been trained.");
        tellSlugReport1.addDialogStep("There are two bridges to repair.");

        talkToMawnis = new NPCStep(NpcID.MAWNIS_BUROWGAR, new RSTile(2335, 3800, 0), "Take off the jester outfit, and talk to Mawnis.");

        talkToMawnisWithLogs = new NPCStep(NpcID.MAWNIS_BUROWGAR, new RSTile(2335, 3800, 0),
                // "Bring Mawnis the 8 split logs, 8 rope, and a knife.",
                splitLogs8, rope8, knife);

        talkToMawnisAfterItems = new NPCStep(NpcID.MAWNIS_BUROWGAR, new RSTile(2335, 3800, 0), "Talk to Mawnis.");
        talkToMawnisWithLogs.addSubSteps(talkToMawnisAfterItems);

        repairBridge1 = new ObjectStep(ObjectID.ROPE_BRIDGE_21310, new RSTile(2314, 3840, 0), "Right-click " +
                "repair the bridges to the north of Neitiznot. Protect from Missiles before doing this as you'll " +
                "automatically cross the aggressive trolls.", splitLogs8, rope8, knife);
        repairBridge1Second = new ObjectStep(ObjectID.ROPE_BRIDGE_21310, new RSTile(2314, 3840, 0),
                "Right-click repair the bridges to the north of Neitiznot. Protect from Missiles before doing this as " +
                        "you'll automatically cross the aggressive trolls. Protect from Missiles before doing this as you'll " +
                        "automatically cross the aggressive trolls.", splitLogs4, rope4, knife);
        repairBridge2 = new ObjectStep(ObjectID.ROPE_BRIDGE_21312, new RSTile(2355, 3840, 0),
                "Right-click repair the bridges to the north of Neitiznot.", splitLogs4, rope4, knife);
        repairBridge1.addSubSteps(repairBridge1Second, repairBridge2);

        talkToMawnisAfterRepair = new NPCStep(NpcID.MAWNIS_BUROWGAR, new RSTile(2335, 3800, 0), "Report back to Mawnis.");

        talkToGjukiToReport = new NPCStep(NpcID.KING_GJUKI_SORVOTT_IV, new RSTile(2407, 3804, 0), "Report back to King Gjuki Sorvott IV on Jatizso.");
        travelToJatizsoToReport = new NPCStep(NpcID.MORD_GUNNARS, new RSTile(2644, 3709, 0), "Travel to Jatizso with Mord.");
        leaveNeitiznotToReport = new NPCStep(NpcID.MARIA_GUNNARS, new RSTile(2311, 3781, 0), "Travel back to Rellekka with Maria.");

        talkToGjukiToReport.addSubSteps(travelToJatizsoToReport, leaveNeitiznotToReport);

        collectFromHring = new NPCStep(NpcID.HRING_HRING, new RSTile(2397, 3797, 0), "Collect 8000 coins from Hring Hring in south west Jatizso.");
        collectFromHring.addDialogStep("But rules are rules. Pay up!");
        collectFromSkuli = new NPCStep(NpcID.SKULI_MYRKA, new RSTile(2395, 3804, 0), "Collect 6000 coins from Skuli in north west Jatizso.");
        collectFromSkuli.addDialogStep("But, rules are rules. Pay up!");
        collectFromVanligga = new NPCStep(NpcID.VANLIGGA_GASTFRIHET, new RSTile(2405, 3813, 0), "Collect 5000 coins from Vanligga north of Gjuki's building.");
        collectFromVanligga.addDialogStep("But, rules are rules. Pay up!");
        collectFromKeepa = new NPCStep(NpcID.KEEPA_KETTILON, new RSTile(2417, 3816, 0), "Collect 5000 coins from Keepa in north east Jatizso.");
        collectFromKeepa.addDialogStep("But rules are rules. Pay up!");
        talkToGjukiAfterCollection1 = new NPCStep(NpcID.KING_GJUKI_SORVOTT_IV, new RSTile(2407, 3804, 0), "Report back to King Gjuki Sorvott IV on Jatizso.");
        collectFromHringAgain = new NPCStep(NpcID.HRING_HRING, new RSTile(2397, 3797, 0), "Collect tax from Hring Hring in south west Jatizso.");
        collectFromHringAgain.addDialogStep("But rules are rules. Pay up!");
        collectFromRaum = new NPCStep(NpcID.RAUM_URDASTEIN, new RSTile(2395, 3797, 0), "Collect tax from Raum in south west Jatizso.");
        collectFromRaum.addDialogStep("But rules are rules. Pay up!");
        collectFromSkuliAgain = new NPCStep(NpcID.SKULI_MYRKA, new RSTile(2395, 3804, 0), "Collect tax from Skuli in north west Jatizso.");
        collectFromSkuliAgain.addDialogStep("But rules are rules. Pay up!");
        collectFromKeepaAgain = new NPCStep(NpcID.KEEPA_KETTILON, new RSTile(2417, 3816, 0), "Collect tax from Keepa in north east Jatizso.");
        collectFromKeepaAgain.addDialogStep("But rules are rules. Pay up!");
        collectFromFlosi = new NPCStep(NpcID.FLOSI_DALKSSON, new RSTile(2418, 3813, 0), "Collect tax from Flossi in north east Jatizso.");
        collectFromFlosi.addDialogStep("But rules are rules. Pay up!");
        talkToGjukiAfterCollection2 = new NPCStep(NpcID.KING_GJUKI_SORVOTT_IV, new RSTile(2407, 3804, 0), "Report back to King Gjuki Sorvott IV on Jatizso.");

        travelToNeitiznotToSpyAgain = new NPCStep(NpcID.MARIA_GUNNARS_1883, new RSTile(2644, 3710, 0), "Travel to Neitiznot with Maria Gunnars.");
        returnToRellekkaFromJatizsoToSpyAgain = new NPCStep(NpcID.MORD_GUNNARS_1940, new RSTile(2420, 3781, 0), "Return to Rellekka with Mord.");
        returnToRellekkaFromJatizsoToSpyAgain.addDialogStep("Can you ferry me to Rellekka?");
        talkToSlugToSpyAgain = new NPCStep(NpcID.SLUG_HEMLIGSSEN, new RSTile(2335, 3811, 0),
                //   "Talk to Slug Hemligssen wearing nothing but your Silly Jester outfit.",
                jesterHat, jesterTop, jesterTights, jesterBoots);
        talkToSlugToSpyAgain.addSubSteps(travelToNeitiznotToSpyAgain, returnToRellekkaFromJatizsoToSpyAgain);

        performForMawnisAgain = new DetailedQuestStep("Perform the actions that Mawnis requests of you.");

        goSpyOnMawnisAgain = new NPCStep(NpcID.MAWNIS_BUROWGAR, new RSTile(2335, 3800, 0),
                /// "Talk to Mawnis to start spying on him.",
                jesterHat, jesterTop, jesterTights, jesterBoots);
        goSpyOnMawnisAgain.addSubSteps(performForMawnisAgain);

        reportBackToSlugAgain = new NPCStep(NpcID.SLUG_HEMLIGSSEN, new RSTile(2335, 3811, 0), "Report to Slug Hemligssen.");
        reportBackToSlugAgain.addDialogStep("Yes, I am.", "They are in a secluded bay, near Etceteria.", "They will be given some potions.", "I have been helping Neitiznot.");
        returnToRellekkaFromNeitiznotAfterSpy2 = new NPCStep(NpcID.MARIA_GUNNARS, new RSTile(2311, 3781, 0), "Travel back to Rellekka with Maria.");
        travelToJatizsoAfterSpy2 = new NPCStep(NpcID.MORD_GUNNARS, new RSTile(2644, 3709, 0), "Travel to Jatizso with Mord.");
        talkToGjukiAfterSpy2 = new NPCStep(NpcID.KING_GJUKI_SORVOTT_IV, new RSTile(2407, 3804, 0), "Report back to King Gjuki Sorvott IV on Jatizso.");
        talkToGjukiAfterSpy2.addSubSteps(returnToRellekkaFromNeitiznotAfterSpy2, travelToJatizsoAfterSpy2);

        returnToRellekkaFromJatizsoWithDecree = new NPCStep(NpcID.MORD_GUNNARS_1940, new RSTile(2420, 3781, 0),
                royalDecree);
        returnToRellekkaFromJatizsoWithDecree.addDialogStep("Can you ferry me to Rellekka?");

        returnToRellekkaFromJatizsoAfterDecree = new NPCStep(NpcID.MORD_GUNNARS_1940, new RSTile(2420, 3781, 0), "Return to Rellekka with Mord.");
        returnToRellekkaFromJatizsoAfterDecree.addDialogStep("Can you ferry me to Rellekka?");

        travelToNeitiznotWithDecree = new NPCStep(NpcID.MARIA_GUNNARS_1883, new RSTile(2644, 3710, 0),
                royalDecree);
        travelToNeitiznotAfterDecree = new NPCStep(NpcID.MARIA_GUNNARS_1883, new RSTile(2644, 3710, 0), "Travel to Neitiznot with Maria Gunnars.");

        talkToMawnisWithDecree = new NPCStep(NpcID.MAWNIS_BUROWGAR, new RSTile(2335, 3800, 0),
                //"Take off your jester outfit, and talk to Mawnis.",
                royalDecree);
        talkToMawnisAfterDecree = new NPCStep(NpcID.MAWNIS_BUROWGAR, new RSTile(2335, 3800, 0), "Take off your jester outfit, and talk to Mawnis.");
        talkToMawnisWithDecree.addSubSteps(talkToMawnisAfterDecree, returnToRellekkaFromJatizsoAfterDecree, returnToRellekkaFromJatizsoWithDecree, travelToNeitiznotWithDecree, travelToNeitiznotAfterDecree);

        getYakArmour = new NPCStep(NpcID.MAWNIS_BUROWGAR, new RSTile(2335, 3800, 0),
                //  "Talk to Mawnis with full yak-hide armour.",
                yakTop, yakBottom);
        makeShield = new NPCStep(NpcID.MAWNIS_BUROWGAR, new RSTile(2335, 3800, 0),
                //   "Talk to Mawnis with a Neitiznot Shield.",
                roundShield);

        enterCave = new ObjectStep(ObjectID.CAVE_21584, new RSTile(2402, 3890, 0),
                "Enter the cave in the north east of the islands. Be prepared in your yak armour, Neitiznot shield, and a melee weapon.", yakTopWorn, yakBottomWorn, shieldWorn, meleeWeapon, food);

        //   killTrolls = new KillTrolls(this);
        enterKingRoom = new ObjectStep(ObjectID.ROPE_BRIDGE_21316, new RSTile(2385, 10263, 1), "Cross the rope bridge. Be prepared to fight the Ice Troll King. Use the Protect from Magic prayer for the fight.");
        killKing = new NPCStep(NpcID.ICE_TROLL_KING, new RSTile(2386, 10249, 1), "Kill the king. Use the Protect from Magic prayer for the fight.");
        decapitateKing = new ObjectStep(ObjectID.ICE_TROLL_KING, "Decapitate the king's head.");
        finishQuest = new NPCStep(NpcID.MAWNIS_BUROWGAR, new RSTile(2335, 3800, 0),
                head);
        finishQuestGivenHead = new NPCStep(NpcID.MAWNIS_BUROWGAR, new RSTile(2335, 3800, 0), "Talk to Mawnis to complete the quest.");
        finishQuest.addSubSteps(finishQuestGivenHead);
    }


    public List<ItemRequirement> getItemRequirements() {
        return items;
    }


    @Override
    public List<Requirement> getGeneralRequirements() {
        ArrayList<Requirement> req = new ArrayList<>();
        req.add(new QuestRequirement(Quest.THE_FREMENNIK_TRIALS, Quest.State.COMPLETE));
        req.add(new SkillRequirement(Skills.SKILLS.AGILITY, 40));
        req.add(new SkillRequirement(Skills.SKILLS.CONSTRUCTION, 20));
        return req;
    }

    @Override
    public Priority priority() {
        return Priority.LOW;
    }

    @Override
    public boolean validate() {
        return cQuesterV2.taskList.size() > 0 &&
                cQuesterV2.taskList.get(0).equals(this);
    }

    @Override
    public void execute() {

        int varbit = QuestVarbits.QUEST_THE_FREMENNIK_ISLES.getId();
        Log.debug("Fremennik Isles Varbit is " + Utils.getVarBitValue(varbit));

        // buy initial items on quest start
        if (Utils.getVarBitValue(varbit) == 0 && !initialItemReqs.check()) {
            buyStep.buyItems();
            initialItemReqs.withdrawItems();
        }

        // done quest
        if (isComplete()) {
            cQuesterV2.taskList.remove(this);
            return;
        }

        //load questSteps into a map
        Map<Integer, QuestStep> steps = loadSteps();
        //get the step based on the game setting key in the map
        Optional<QuestStep> step = Optional.ofNullable(steps.get(Utils.getVarBitValue(varbit)));

        // set status
        cQuesterV2.status = step.map(Object::toString).orElse("Unknown Step Name");

        //do the actual step
        step.ifPresent(QuestStep::execute);

        // handle any chats that are failed to be handled by the QuestStep (failsafe)
        if (ChatScreen.isOpen()) {
            Log.debug("Handling chat screen -test");
            ChatScreen.handle();
            // NPCInteraction.handleConversation();
        }

        //slow down looping if it gets stuck
        Waiting.waitNormal(200, 20);

    }

    @Override
    public String questName() {
        return "Fremennick Isles (" + Utils.getVarBitValue(QuestVarbits.QUEST_THE_FREMENNIK_ISLES.getId())
                + ")";
    }

    @Override
    public boolean checkRequirements() {
        return false;
    }


    @Override
    public List<ItemRequirement> getBuyList() {
        return null;
    }

    @Override
    public boolean isComplete() {
        return Quest.THE_FREMENNIK_ISLES.getState().equals(Quest.State.COMPLETE);
    }
}
