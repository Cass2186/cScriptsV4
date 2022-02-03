package scripts.QuestPackages.icthlarinslittlehelper;

import dax.walker_engine.interaction_handling.NPCInteraction;
import org.tribot.api2007.*;
import org.tribot.api2007.types.*;
import org.tribot.script.sdk.Log;
import org.tribot.script.sdk.Waiting;
import scripts.*;
import scripts.GEManager.GEItem;
import scripts.QuestPackages.DragonSlayer.DragonSlayer;
import scripts.QuestSteps.*;
import scripts.Requirements.AreaRequirement;
import scripts.Requirements.InventoryRequirement;
import scripts.Requirements.ItemReq;
import scripts.Requirements.Util.Operation;
import scripts.Requirements.VarbitRequirement;
import scripts.Tasks.Priority;
import scripts.Tasks.Task;

import java.util.ArrayList;
import java.util.Arrays;

public class Icthlarinslittlehelper implements QuestTask {
    private static Icthlarinslittlehelper quest;

    public static Icthlarinslittlehelper get() {
        return quest == null ? quest = new Icthlarinslittlehelper() : quest;
    }


    //Items Required
    ItemReq cat, tinderbox, coins600, bagOfSaltOrBucket, willowLog, bucketOfSap, waterskin4, food, jar,
            coinsOrLinen, coins30, linen, holySymbol, unholySymbol, combatGear, prayerPotions, antipoison;

    ItemReq sphinxsToken = new ItemReq("Sphinx's token", ItemID.SPHINXS_TOKEN);
    //  Requirement catFollower;

    //Requirement inSoph, inPyramid, inNorthPyramid, puzzleOpen, givenToken, hasScarabasJar, hasCrondisJar, hasHetJar, hasApmekenJar,
    //          killedGuardian, talkedToEmbalmer, givenLinen, givenSalt, givenSap, givenEmbalmerAllItems, talkedToCarpenter,
    //       givenCarpenterLogs, inEastRoom, posessedPriestNearby;

    NPCStep talkToWanderer = new NPCStep(4194, new RSTile(3316, 2849, 0), waterskin4, tinderbox);
    NPCStep talkToWandererAgain = new NPCStep(4194, new RSTile(3316, 2849, 0), waterskin4, tinderbox);
    NPCStep talkToSphinx = new NPCStep(4209, new RSTile(3301, 2785, 0));
    //        catFollower);
    NPCStep talkToHighPriest = new NPCStep(6192, new RSTile(3281, 2772, 0), sphinxsToken);
    NPCStep talkToHighPriestWithoutToken = new NPCStep(6192, new RSTile(3281, 2772, 0));

    ObjectStep enterRock = new ObjectStep(6621, new RSTile(3324, 2858, 0),
            "Enter");
    ObjectStep touchPyramidDoor = new ObjectStep(6614, new RSTile(3295, 2780, 0),
            "Touch");
    ObjectStep jumpPit = new ObjectStep(ObjectID.PIT, new RSTile(3292, 9193, 0),
            "Jump-Across");

    ObjectStep openWestDoor = new ObjectStep(6643, new RSTile(3280, 9199, 0),
            "Open");

    ObjectStep openPyramidDoor = new ObjectStep(6614, new RSTile(3295, 2780, 0),
            "Open");// catFollower);
    public void openPyramidDoorStep(){
        RSItem[] kitten = Inventory.find("Kitten");
        if (kitten.length > 0 && kitten[0].click("Drop")){
            Timer.waitCondition(()->Inventory.find("Kitten").length ==0 ,2500,3000);
        }
        cQuesterV2.status = "Opening Pyramid Door";
        openPyramidDoor.execute();
        Timer.waitCondition(()-> inPyramid.check(), 5000,7000);
    }

//    ObjectStep   jumpPitAgain = new ObjectStep( ObjectID.PIT, new RSTile(3292, 9193, 0), "Follow the path again until you reach a pit, and jump it. Move using the minimap to avoid all the traps.");

    ObjectStep pickUpCrondisJar = new ObjectStep(6636, new RSTile(3286, 9195, 0), "Take");
    ObjectStep pickUpScarabasJar = new ObjectStep(6638, new RSTile(3286, 9196, 0), "Take");
    ObjectStep pickUpApmekenJar = new ObjectStep(6640, new RSTile(3286, 9193, 0), "Take");
    ObjectStep pickUpHetJar = new ObjectStep(6634, new RSTile(3286, 9194, 0), "Take");

    ObjectStep pickUpCrondisJarAgain = new ObjectStep(6636, new RSTile(3286, 9195, 0), "Take"
    );
    ObjectStep pickUpScarabasJarAgain = new ObjectStep(6638, new RSTile(3286, 9196, 0), "Take"
    );
    ObjectStep pickUpApmekenJarAgain = new ObjectStep(6640, new RSTile(3286, 9193, 0), "Take"
    );
    ObjectStep pickUpHetJarAgain = new ObjectStep(6634, new RSTile(3286, 9194, 0), "Take"
    );


    ObjectStep returnOverPit = new ObjectStep(ObjectID.PIT, new RSTile(3292, 9196, 0),
            "Jump-Across");
    ObjectStep jumpOverPitAgain = new ObjectStep(ObjectID.PIT, new RSTile(3292, 9194, 0),
            "Jump-Across");

    NPCStep talkToCarpenterAgain = new NPCStep(NpcID.CARPENTER, new RSTile(3313, 2770, 0));
    NPCStep talkToCarpenterOnceMore = new NPCStep(NpcID.CARPENTER, new RSTile(3313, 2770, 0));

    NPCStep buyLinen = new NPCStep(NpcID.RAETUL, new RSTile(3311, 2787, 0), coinsOrLinen);

    ObjectStep enterRockWithItems = new ObjectStep(6621, new RSTile(3324, 2858, 0),
            "Enter",
            bucketOfSap, bagOfSaltOrBucket, coinsOrLinen, willowLog);

    ObjectStep openPyramidDoorWithSymbol = new ObjectStep(6614, new RSTile(3295, 2779, 0),
            "Open", holySymbol);

    ObjectStep jumpPitWithSymbol = new ObjectStep(ObjectID.PIT, new RSTile(3292, 9194, 0), "Follow the path again until you reach a pit, and jump it. Move using the minimap to avoid all the traps.", holySymbol);

    ObjectStep enterEastRoom = new ObjectStep(6643, new RSTile(3306, 9199, 0),
            "Enter");
    ObjectStep useSymbolOnSarcopagus = new ObjectStep(6630, new RSTile(3312, 9197, 0),
            "Use the unholy symbol on a sarcophagus.", unholySymbol);

    ObjectStep leaveEastRoom = new ObjectStep(6643, new RSTile(3306, 9199, 0), "Leave the east room.");
    ObjectStep jumpPitWithSymbolAgain = new ObjectStep(ObjectID.PIT, new RSTile(3292, 9194, 0), "Jump over the pit.", holySymbol);

    ObjectStep enterEastRoomAgain = new ObjectStep(6643, new RSTile(3306, 9199, 0),
            "Enter");

    //  NPCStep killPriest = new NPCStep(NpcID.POSSESSED_PRIEST, new RSTile(3306, 9196, 0), "Kill the posessed priest.");

    NPCStep talkToHighPriestInPyramid = new NPCStep(NpcID.HIGH_PRIEST_4206, new RSTile(3306, 9196, 0));

    ObjectStep leavePyramidToFinish = new ObjectStep(6645, new RSTile(3277, 9172, 0),
            "Leave the pyramid and return to the High Priest.");
    NPCStep talkToHighPriestToFinish = new NPCStep(NpcID.HIGH_PRIEST_4206, new RSTile(3281, 2772, 0));
    ObjectStep leavePyramid = new ObjectStep(6645, new RSTile(3277, 9172, 0), "Leave the pyramid and return to the High Priest.");
    NPCStep returnToHighPriest = new NPCStep(NpcID.HIGH_PRIEST_4206,
            new RSTile(3281, 2772, 0));

    NPCStep talkToEmbalmer = new NPCStep(NpcID.EMBALMER, new RSTile(3287, 2755, 0), bagOfSaltOrBucket, linen, bucketOfSap);
    NPCStep talkToEmbalmerAgain = new NPCStep(NpcID.EMBALMER, new RSTile(3287, 2755, 0), bagOfSaltOrBucket, linen, bucketOfSap);
    NPCStep talkToEmbalmerAgainNoLinen = new NPCStep(NpcID.EMBALMER, new RSTile(3287, 2755, 0), bagOfSaltOrBucket, bucketOfSap);
    NPCStep talkToEmbalmerAgainNoSalt = new NPCStep(NpcID.EMBALMER, new RSTile(3287, 2755, 0), linen, bucketOfSap);
    NPCStep talkToEmbalmerAgainNoSap = new NPCStep(NpcID.EMBALMER, new RSTile(3287, 2755, 0), bagOfSaltOrBucket, linen);
    NPCStep talkToEmbalmerAgainNoLinenNoSalt = new NPCStep(NpcID.EMBALMER, new RSTile(3287, 2755, 0), bucketOfSap);
    NPCStep talkToEmbalmerAgainNoLinenNoSap = new NPCStep(NpcID.EMBALMER, new RSTile(3287, 2755, 0), bagOfSaltOrBucket);
    NPCStep talkToEmbalmerAgainNoSaltNoSap = new NPCStep(NpcID.EMBALMER, new RSTile(3287, 2755, 0), linen);
    NPCStep talkToCarpenter = new NPCStep(NpcID.CARPENTER, new RSTile(3313, 2770, 0), willowLog);


    //RSAreas

    RSArea soph = new RSArea(new RSTile(3262, 2751, 0), new RSTile(3322, 2809, 0));
    RSArea pyramid = new RSArea(new RSTile(3273, 9170, 0), new RSTile(3311, 9204, 0));
    RSArea northPyramid = new RSArea(new RSTile(3276, 9194, 0), new RSTile(3311, 9204, 0));
    RSArea northPyramid2 = new RSArea(new RSTile(3276, 9192, 0), new RSTile(3287, 9193, 0));
    RSArea eastRoom = new RSArea(new RSTile(3300, 9192, 0), new RSTile(3311, 9199, 0));
    RSArea westRoom = new RSArea(
            new RSTile[] {
                    new RSTile(3276, 9199, 0),
                    new RSTile(3279, 9199, 0),
                    new RSTile(3280, 9200, 0),
                    new RSTile(3281, 9200, 0),
                    new RSTile(3282, 9199, 0),
                    new RSTile(3288, 9199, 0),
                    new RSTile(3288, 9192, 0),
                    new RSTile(3276, 9192, 0)
            }
    );

    AreaRequirement inSoph = new AreaRequirement(soph);
    AreaRequirement inPyramid = new AreaRequirement(pyramid);
    AreaRequirement inNorthPyramid = new AreaRequirement(northPyramid, northPyramid2, eastRoom);
    AreaRequirement inEastRoom = new AreaRequirement(eastRoom);
    AreaRequirement inWestRoom = new AreaRequirement(westRoom);

    VarbitRequirement givenToken = new VarbitRequirement(450, 1);
    VarbitRequirement hasHetJar = new VarbitRequirement(397, 1);
    VarbitRequirement hasCrondisJar = new VarbitRequirement(397, 4);
    // TODO: Verify varbit values for apmeken/scarabas
    VarbitRequirement hasApmekenJar = new VarbitRequirement(397, 3);
    VarbitRequirement hasScarabasJar = new VarbitRequirement(397, 2);
    VarbitRequirement talkedToEmbalmer = new VarbitRequirement(399, 1);

    VarbitRequirement givenSalt = new VarbitRequirement(401, 1);
    VarbitRequirement givenSap = new VarbitRequirement(402, 1);
    VarbitRequirement givenLinen = new VarbitRequirement(403, 1);
    VarbitRequirement givenEmbalmerAllItems = new VarbitRequirement(400, 7);

    VarbitRequirement talkedToCarpenter = new VarbitRequirement(412, 1);
    VarbitRequirement givenCarpenterLogs = new VarbitRequirement(398, 1);

    VarbitRequirement killedGuardian = new VarbitRequirement(418, 11, Operation.GREATER_EQUAL);

    ObjectStep pickUpAnyJar = new ObjectStep(6638, new RSTile(3286, 9194, 0), "Take");
    ObjectStep pickUpAnyJarAgain = new ObjectStep(6638, new RSTile(3286, 9194, 0), "Take");

    public void pickUpAnyJarStep(){
        int[] jars = {6638, 6636, 6634, 6640};
        for (int i : jars){
            if (Inventory.find(i).length > 0 || Combat.isUnderAttack()){
                break;
            }
            ObjectStep pickUpAnyJar = new ObjectStep(i, new RSTile(3286, 9194, 0), "Take");
            pickUpAnyJar.execute();
            if (Inventory.find(i).length > 0 || Combat.isUnderAttack()){
                break;
            }
        }
        if (Combat.isUnderAttack()){
            CombatUtil.waitUntilOutOfCombat(50);
        }
    }

    public void setupItemReqs() {
        //   cat = new ItemReq("A cat",ItemCollections.getCats(),NpcCollections.getCats());

        //catFollower = new FollowerRequirement("Any cat following you", NpcCollections.getCats());
        tinderbox = new ItemReq("Tinderbox", ItemID.TINDERBOX);
        waterskin4 = new ItemReq(ItemID.WATERSKIN[0], 2);
        coins600 = new ItemReq(ItemID.COINS_995, 600);
        bagOfSaltOrBucket = new ItemReq(ItemID.BAG_OF_SALT);

        coinsOrLinen = new ItemReq("Linen or 30 coins to buy some", ItemID.LINEN);

        coins30 = new ItemReq(ItemID.COINS_995, 30);

        willowLog = new ItemReq("Willow logs", ItemID.WILLOW_LOGS);
        bucketOfSap = new ItemReq("Bucket of sap", ItemID.BUCKET_OF_SAP);
        //    bucketOfSap.setTooltip("You can get this by using a knife on an evergreen tree with a bucket in your " + "inventory");

        food = new ItemReq(ItemID.SHARK, 15, 2);

        prayerPotions = new ItemReq(ItemID.PRAYER_POTION_4, 3, 0);
        antipoison = new ItemReq(ItemID.ANTIDOTE_PLUS_PLUS[0], 1);
        combatGear = new ItemReq("Combat equipment", -1, -1);


        //   sphinxsToken.setTooltip("You can get another from the Sphinx");
        jar = new ItemReq("Canopic jar",ItemID.CANOPIC_JAR );
          jar.addAlternateItemID(ItemID.CANOPIC_JAR_4679, ItemID.CANOPIC_JAR_4680, ItemID.CANOPIC_JAR_4681);


        linen = new ItemReq("Linen", ItemID.LINEN);

        holySymbol = new ItemReq("Holy symbol", ItemID.HOLY_SYMBOL_4682);
        //   holySymbol.setTooltip("You can get another from the Carpenter in Sophanem");

        unholySymbol = new ItemReq("Unholy symbol", 4683);

    }


    ArrayList<GEItem> itemsToBuy = new ArrayList<GEItem>(
            Arrays.asList(
                    new GEItem(ItemID.SUMMER_PIE, 3, 50),
                    new GEItem(ItemID.WATERSKIN[0], 2, 300),
                    new GEItem(ItemID.SHARK, 6, 50),
                    new GEItem(ItemID.TINDERBOX, 1, 500),
                    new GEItem(ItemID.BUCKET_OF_SAP, 1, 500),
                    new GEItem(ItemID.LINEN, 1, 500),
                    new GEItem(ItemID.NARDAH_TELEPORT, 5, 50),
                    new GEItem(ItemID.RUNE_SCIMITAR, 1, 50),
                    new GEItem(ItemID.BAG_OF_SALT, 1, 500),
                    new GEItem(ItemID.RUNE_FULL_HELM, 1, 20),
                    //combat gear
                    new GEItem(ItemID.ANTIDOTE_PLUS_PLUS[0], 1, 30),
                    new GEItem(ItemID.STAMINA_POTION[0], 5, 15),
                    new GEItem(ItemID.RING_OF_WEALTH[0], 1, 25)
            )
    );

    InventoryRequirement initialItemReqs = new InventoryRequirement(new ArrayList<>(
            Arrays.asList(
                    new ItemReq(ItemID.COINS, 600, 200),
                    new ItemReq(ItemID.STAMINA_POTION[0], 5, 0),
                    new ItemReq(ItemID.RING_OF_WEALTH[0], 1, 0, true)
            )
    ));

    BuyItemsStep buyStep = new BuyItemsStep(itemsToBuy);



  /*  public Map<Integer, QuestStep> loadSteps()
    {

        Map<Integer, QuestStep> steps = new HashMap<>();

        steps.put(0, talkToWanderer);
        steps.put(1, talkToWandererAgain);
        steps.put(2, enterRock);


        ConditionalStep takeTheJar = new ConditionalStep( enterRock);
        takeTheJar.addStep(new Conditions(inNorthPyramid, jar), returnOverPit);
        takeTheJar.addStep(new Conditions(inNorthPyramid, hasHetJar, killedGuardian), pickUpHetJarAgain);
        takeTheJar.addStep(new Conditions(inNorthPyramid, hasCrondisJar, killedGuardian), pickUpCrondisJarAgain);
        takeTheJar.addStep(new Conditions(inNorthPyramid, killedGuardian), pickUpAnyJarAgain);
        takeTheJar.addStep(new Conditions(inNorthPyramid, hasHetJar), pickUpHetJar);
        takeTheJar.addStep(new Conditions(inNorthPyramid, hasCrondisJar), pickUpCrondisJar);
        takeTheJar.addStep(inNorthPyramid, pickUpAnyJar);
        takeTheJar.addStep(inPyramid, jumpPitAgain);
        takeTheJar.addStep(inSoph, openPyramidDoor);

        steps.put(7, takeTheJar);
        steps.put(8, takeTheJar);
        steps.put(9, takeTheJar);
        steps.put(10, takeTheJar);
        steps.put(11, takeTheJar);

        ConditionalStep returnTheJar = new ConditionalStep( enterRock);
        returnTheJar.addStep(puzzleOpen, solvePuzzleAgain);
        returnTheJar.addStep(new Conditions(inNorthPyramid, hasHetJar), dropHetJar);
        returnTheJar.addStep(new Conditions(inNorthPyramid, hasCrondisJar), dropCrondisJar);
        returnTheJar.addStep(inNorthPyramid, dropJar);
        returnTheJar.addStep(inPyramid, jumpOverPitAgain);
        returnTheJar.addStep(inSoph, openPyramidDoor);

        steps.put(12, returnTheJar);
        steps.put(13, returnTheJar);

        ConditionalStep afterPlacingJarSteps = new ConditionalStep( enterRock);
        afterPlacingJarSteps.addStep(inSoph, returnToHighPriest);
        afterPlacingJarSteps.addStep(inPyramid, leavePyramid);

        steps.put(14, afterPlacingJarSteps);

        ConditionalStep prepareItems = new ConditionalStep( enterRockWithItems);
        prepareItems.addStep(new Conditions(inSoph, givenEmbalmerAllItems, givenCarpenterLogs), talkToCarpenterOnceMore);
        prepareItems.addStep(new Conditions(inSoph, givenEmbalmerAllItems, talkedToCarpenter), talkToCarpenterAgain);
        prepareItems.addStep(new Conditions(inSoph, givenEmbalmerAllItems), talkToCarpenter);
        prepareItems.addStep(new Conditions(inSoph, talkedToEmbalmer, linen, givenSap, givenSalt), talkToEmbalmerAgainNoSaltNoSap);
        prepareItems.addStep(new Conditions(inSoph, talkedToEmbalmer, givenLinen, givenSap), talkToEmbalmerAgainNoLinenNoSap);
        prepareItems.addStep(new Conditions(inSoph, talkedToEmbalmer, givenLinen, givenSalt), talkToEmbalmerAgainNoLinenNoSalt);
        prepareItems.addStep(new Conditions(inSoph, talkedToEmbalmer, linen, givenSalt), talkToEmbalmerAgainNoSalt);
        prepareItems.addStep(new Conditions(inSoph, talkedToEmbalmer, linen, givenSap), talkToEmbalmerAgainNoSap);
        prepareItems.addStep(new Conditions(inSoph, talkedToEmbalmer, givenLinen), talkToEmbalmerAgainNoLinen);
        prepareItems.addStep(new Conditions(inSoph, talkedToEmbalmer, linen), talkToEmbalmerAgain);
        prepareItems.addStep(new Conditions(inSoph, linen), talkToEmbalmer);
        prepareItems.addStep(inSoph, buyLinen);

        steps.put(15, prepareItems);

        ConditionalStep goToRitual = new ConditionalStep( enterRock);
        goToRitual.addStep(new Conditions(inEastRoom, posessedPriestNearby), killPriest);
        goToRitual.addStep(inEastRoom, talkToHighPriestInPyramid);
        goToRitual.addStep(inNorthPyramid, enterEastRoomAgain);
        goToRitual.addStep(inPyramid, jumpPitWithSymbol);
        goToRitual.addStep(inSoph, openPyramidDoorWithSymbol);

        steps.put(16, goToRitual);

        ConditionalStep placeSymbol = new ConditionalStep( enterRock);
        placeSymbol.addStep(inEastRoom, useSymbolOnSarcopagus);
        placeSymbol.addStep(inPyramid, enterEastRoom);

        steps.put(17, placeSymbol);

        steps.put(18, leaveEastRoom);

        steps.put(19, goToRitual);
        steps.put(20, goToRitual);
        steps.put(21, goToRitual);

        steps.put(22, goToRitual);
        steps.put(23, goToRitual);
        steps.put(24, goToRitual);

        ConditionalStep finishTheQuest = new ConditionalStep( enterRock);
        finishTheQuest.addStep(inPyramid, leavePyramidToFinish);
        finishTheQuest.addStep(inSoph, talkToHighPriestToFinish);

        steps.put(25, finishTheQuest);
        return steps;
    }*/


    public void setupConditions() {


        //     puzzleOpen = new WidgetModelRequirement(147, 3, 6474);


        // picked up het, 404 = 1
        // picked up apmeken, 405 = 1


        //   posessedPriestNearby = new NpcCondition(NpcID.POSSESSED_PRIEST);
    }
    /*  */


    public void handlePuzzle() {
        if (!Interfaces.isInterfaceSubstantiated(147)) {
            openWestDoor.execute();
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation();
            Timer.waitCondition(() -> Interfaces.isInterfaceSubstantiated(147, 3), 5000, 6000);
        }
        RSInterfaceMaster puzzleInter = Interfaces.get(147);
        if (puzzleInter != null) {
            for (int i = 0; i < 10; i++) {
                RSInterfaceChild resetButton = Interfaces.get(147, 29);
                if (Utils.getVarBitValue(445) != 818431 && resetButton != null) {
                    if (resetButton.click())
                        Waiting.waitNormal(1250, 150);

                } else if (Utils.getVarBitValue(445) == 818431) {
                    Log.log("[Debug]: Setting is correct");
                    RSInterfaceChild clickOne = Interfaces.get(147, 10);
                    RSInterfaceChild clickTwo = Interfaces.get(147, 8);
                    RSInterfaceChild clickThree = Interfaces.get(147, 18);
                    if (clickOne != null & clickOne.click()) {
                        Waiting.waitNormal(800, 150);
                    }
                    if (clickTwo != null & clickTwo.click()) {
                        Waiting.waitNormal(800, 150);
                    }
                    if (clickThree != null & clickThree.click()) {
                        Timer.waitCondition(() -> !Interfaces.isInterfaceSubstantiated(147), 5000);
                        Waiting.waitNormal(2500, 150);
                        break;
                    }
                }
            }
        }
    }

    public void setupSteps() {
        talkToWanderer.addDialogStep("Why? What's your problem with it?");
        talkToWanderer.addDialogStep("Ok I'll get your supplies.");
        talkToWandererAgain.addDialogStep("Yes. I have them all here.");

        //  solveDoorPuzzle = new DoorPuzzleStep(this);

        talkToSphinx.addDialogStep("I need help.");
        talkToSphinx.addDialogStep("Okay, that sounds fair.");
        talkToSphinx.addDialogStep("9.");
        talkToSphinx.addDialogStep("Totally positive.");

        //  talkToHighPriest.addSubSteps(talkToHighPriestWithoutToken);

        //     pickUpAnyJar.addAlternateObjects(NullObjectID.NULL_6636, NullObjectID.NULL_6638, NullObjectID.NULL_6640);
        //    pickUpAnyJar.addSubSteps(pickUpCrondisJar, pickUpScarabasJar, pickUpApmekenJar, pickUpHetJar);

/*

        pickUpAnyJarAgain.addAlternateObjects(NullObjectID.NULL_6636, NullObjectID.NULL_6638, NullObjectID.NULL_6640);
        pickUpAnyJarAgain.addSubSteps(pickUpCrondisJarAgain, pickUpScarabasJarAgain, pickUpApmekenJarAgain, pickUpHetJarAgain);

        dropCrondisJar = new DetailedQuestStep(new RSTile(3286, 9195, 0), "Drop the canopic jar in the spot you took it from.", jar);
        dropApmekenJar = new DetailedQuestStep(new RSTile(3286, 9193, 0), "Drop the canopic jar in the spot you took it from.", jar);
        dropHetJar = new DetailedQuestStep(new RSTile(3286, 9194, 0), "Drop the canopic jar in the spot you took it from.", jar);
        dropScarabasJar = new DetailedQuestStep(new RSTile(3286, 9196, 0), "Drop the canopic jar in the spot you took it from.", jar);
        dropJar = new DetailedQuestStep("Drop the canopic jar in the spot you took it from.", jar);
        dropJar.addSubSteps(dropCrondisJar, dropApmekenJar, dropHetJar, dropScarabasJar);

        solvePuzzleAgain = new DoorPuzzleStep(this);

         returnToHighPriest.addDialogStep("Sure, no problem.");
        leavePyramid.addSubSteps(returnToHighPriest);

  talkToEmbalmerAgain.addSubSteps(talkToEmbalmerAgainNoLinen, talkToEmbalmerAgainNoLinenNoSalt, talkToEmbalmerAgainNoLinenNoSap, talkToEmbalmerAgainNoSalt, talkToEmbalmerAgainNoLinenNoSap, talkToEmbalmerAgainNoSaltNoSap);

         talkToCarpenter.addDialogStep("Alright, I'll get the wood for you.");
        leavePyramidToFinish.addSubSteps(talkToHighPriestToFinish);
        */

    }

    public boolean jumpPit(){
        if (inPyramid.check()) {
            cQuesterV2.status = "Jumping Pit";
            jumpPit.setUseLocalNav(true);
            jumpPit.execute();
            if (Timer.waitCondition(() -> Player.getAnimation() != -1, 2500))
                Timer.waitCondition(() -> Player.getAnimation() == -1, 5500);
        }
        return inNorthPyramid.check();
    }

    public void firstMemory() {
        // enterRock.execute();
        //

        if (Interfaces.isInterfaceSubstantiated(147, 3)) {
            // solve door puzzle
            handlePuzzle();
        }
        if (inNorthPyramid.check()) {
            cQuesterV2.status = "Opening west door";
            handlePuzzle();
        } else if (inPyramid.check()) {
            jumpPit();
        }
        if (inSoph.check()) {
            touchPyramidDoor.execute();
            Timer.waitCondition(()-> inPyramid.check(), 5000,7000);
        }
        // ConditionalStep firstMemory = new ConditionalStep( enterRock);
        // firstMemory.addStep(puzzleOpen, solveDoorPuzzle);
        // firstMemory.addStep(inNorthPyramid, openWestDoor);
        //  firstMemory.addStep(inPyramid, jumpPit);
        //  firstMemory.addStep(inSoph, touchPyramidDoor);

        //   steps.put(3, firstMemory);
        //  steps.put(4, firstMemory);

        // ConditionalStep talkToSphinxSteps = new ConditionalStep( enterRock);
        //     talkToSphinxSteps.addStep(inSoph, talkToSphinx);


    }



    @Override
    public String toString() {
        return "Icthlarin's Little Helper " + Utils.getVarBitValue(QuestVarbits.QUEST_ICTHLARINS_LITTLE_HELPER.getId());
    }

    @Override
    public Priority priority() {
        return Priority.LOW;
    }

    @Override
    public boolean validate() {
        return
                java.util.Objects.equals(cQuesterV2.taskList.get(0), Icthlarinslittlehelper.get());
    }

    @Override
    public void execute() {
        setupSteps();
        setupItemReqs();
        int varbit = QuestVarbits.QUEST_ICTHLARINS_LITTLE_HELPER.getId();
        if (Utils.getVarBitValue(varbit) == 0) {
            cQuesterV2.status = "Talking to Wanderer";
            talkToWanderer.execute();
        } else if (Utils.getVarBitValue(varbit) == 1) {
            cQuesterV2.status = "Talking to Wanderer";
            talkToWandererAgain.execute();
        } else if (Utils.getVarBitValue(varbit) == 2) {
            cQuesterV2.status = "Entering Rock";
            enterRock.execute();

        } else if (Utils.getVarBitValue(varbit) == 3 ||
                Utils.getVarBitValue(varbit) == 4) {
            firstMemory();

        } else if (Utils.getVarBitValue(varbit) == 5) {
            if (inSoph.check())
                talkToSphinx.execute();
        } else if (Utils.getVarBitValue(varbit) == 6) {
            if (inSoph.check() && givenToken.check()) {
                cQuesterV2.status = "Talking to high priest after having given token";
                talkToHighPriestWithoutToken.execute();
            } else if (inSoph.check()) {
                cQuesterV2.status = "Talking to high priest with token";
                talkToHighPriest.execute();
            }

        } else if (Utils.getVarBitValue(varbit) > 6 &&Utils.getVarBitValue(varbit) < 12 ) {
            if (inSoph.check()){
                openPyramidDoorStep();
            } else if (!inPyramid.check()){
                enterRock.execute();
            }

            if ((inNorthPyramid.check() || inWestRoom.check()) && jar.check()) {
                cQuesterV2.status = "Returning over pit";
                returnOverPit.execute();
            } else if( inNorthPyramid.check() && hasHetJar.check() && killedGuardian.check()){
                cQuesterV2.status = "Getting Het Jar again";
                if (!inWestRoom.check())
                    handlePuzzle();
                pickUpHetJarAgain.execute();
            }else if( inNorthPyramid.check() && hasCrondisJar.check() && killedGuardian.check()){
                cQuesterV2.status = "Getting Crondis Jar again";
                if (!inWestRoom.check())
                    handlePuzzle();
                pickUpCrondisJarAgain.execute();
            }
            else if( inNorthPyramid.check() && hasCrondisJar.check() && killedGuardian.check()){
                cQuesterV2.status = "Getting Any Jar again";
                if (!inWestRoom.check())
                    handlePuzzle();
                pickUpAnyJarStep();
            } else if (inNorthPyramid.check()){
                cQuesterV2.status = "Getting Any Jar";
                if (!inWestRoom.check())
                handlePuzzle();
                pickUpAnyJarStep();
            } else if (inPyramid.check()){
                cQuesterV2.status = "Jumping pit";
                jumpPit();
                handlePuzzle();
            }

            //takeTheJar.addStep(new Conditions(inNorthPyramid, hasHetJar), pickUpHetJar);
           // takeTheJar.addStep(new Conditions(inNorthPyramid, hasCrondisJar), pickUpCrondisJar);
           // takeTheJar.addStep(inNorthPyramid, pickUpAnyJar);
           // takeTheJar.addStep(inPyramid, jumpPitAgain);
          //  takeTheJar.addStep(inSoph, openPyramidDoor);
        }


    }

    @Override
    public String questName() {
        int varbit = QuestVarbits.QUEST_ICTHLARINS_LITTLE_HELPER.getId();
        return "Icthlarin's Little Helper (" + Utils.getVarBitValue(varbit) + ")";
    }

    @Override
    public boolean checkRequirements() {
        return true;
    }
}
