package scripts.QuestPackages.icthlarinslittlehelper;

import com.trilezstudios.updater.hooks.NPC;
import dax.walker_engine.interaction_handling.NPCInteraction;
import org.tribot.api.General;
import org.tribot.api2007.*;
import org.tribot.api2007.Combat;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.types.*;
import org.tribot.script.sdk.*;
import org.tribot.script.sdk.query.Query;
import org.tribot.script.sdk.types.Npc;
import scripts.*;
import scripts.GEManager.GEItem;
import scripts.QuestPackages.DragonSlayer.DragonSlayer;
import scripts.QuestSteps.*;
import scripts.Requirements.*;
import scripts.Requirements.Conditional.NpcCondition;
import scripts.Requirements.Items.ItemCollections;
import scripts.Requirements.Util.ConditionalStep;
import scripts.Requirements.Util.Conditions;
import scripts.Requirements.Util.Operation;
import scripts.Tasks.Priority;
import scripts.Tasks.Task;
import scripts.Timer;

import java.util.*;

public class Icthlarinslittlehelper implements QuestTask {
    private static Icthlarinslittlehelper quest;

    public static Icthlarinslittlehelper get() {
        return quest == null ? quest = new Icthlarinslittlehelper() : quest;
    }


    // ItemRequirement cat = new ItemRequirement( ItemCollections.getCats(), NpcCollections.getCats());

    FollowerRequirement catFollower = new FollowerRequirement("Any cat following you", NpcCollections.getCats());
    ItemReq tinderbox = new ItemReq("Tinderbox", ItemID.TINDERBOX);
    ItemReq waterskin4 = new ItemReq(ItemID.WATERSKIN[0], 2);
    ItemReq coins600 = new ItemReq(ItemID.COINS_995, 600);
    ItemReq bagOfSaltOrBucket = new ItemReq(ItemID.BAG_OF_SALT);

    ItemReq coinsOrLinen = new ItemReq("Linen or 30 coins to buy some", ItemID.LINEN);

    ItemReq coins30 = new ItemReq(ItemID.COINS_995, 30);

    ItemReq willowLog = new ItemReq("Willow logs", ItemID.WILLOW_LOGS);
    ItemReq bucketOfSap = new ItemReq("Bucket of sap", ItemID.BUCKET_OF_SAP);
    //    bucketOfSap.setTooltip("You can get this by using a knife on an evergreen tree with a bucket in your " + "inventory");

    ItemReq food = new ItemReq(ItemID.SHARK, 15, 2);

    ItemReq prayerPotions = new ItemReq(ItemID.PRAYER_POTION_4, 3, 0);
    ItemReq antipoison = new ItemReq(ItemID.ANTIDOTE_PLUS_PLUS[0], 1);
    ItemReq combatGear = new ItemReq("Combat equipment", -1, -1);


    //   sphinxsToken.setTooltip("You can get another from the Sphinx");
    ItemReq jar = new ItemReq("Canopic jar", ItemID.CANOPIC_JAR);


    ItemReq linen = new ItemReq("Linen", ItemID.LINEN);

    ItemReq holySymbol = new ItemReq("Holy symbol", ItemID.HOLY_SYMBOL_4682);
    //   holySymbol.setTooltip("You can get another from the Carpenter in Sophanem");

    ItemReq unholySymbol = new ItemReq("Unholy symbol", 4683);

    ItemReq sphinxsToken = new ItemReq("Sphinx's token", ItemID.SPHINXS_TOKEN);
    //  Requirement catFollower;


    NPCStep talkToWanderer = new NPCStep(6187, new RSTile(3316, 2849, 0),

            tinderbox);
    NPCStep talkToWandererAgain = new NPCStep(6187, new RSTile(3316, 2849, 0),
            waterskin4, tinderbox);
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
            "Open", openPyramidDoorStep());// catFollower);

    public boolean openPyramidDoorStep() {
        RSItem[] kitten = Inventory.find("Kitten");
        if (kitten.length > 0 && kitten[0].click("Drop")) {
            return Timer.waitCondition(() -> Inventory.find("Kitten").length == 0, 2500, 3000);
        }
        return false;
        //   cQuesterV2.status = "Opening Pyramid Door";
        //   openPyramidDoor.execute();
        // return Timer.waitCondition(() -> inPyramid.check(), 5000, 7000);
    }

//    ObjectStep   jumpPitAgain = new ObjectStep( ObjectID.PIT, new RSTile(3292, 9193, 0), "Follow the path again until you reach a pit, and jump it. Move using the minimap to avoid all the traps.");

    ObjectStep pickUpCrondisJar = new ObjectStep(6636, new RSTile(3286, 9195, 0), "Take");
    ObjectStep pickUpScarabasJar = new ObjectStep(6638, new RSTile(3286, 9196, 0), "Take");
    ObjectStep pickUpApmekenJar = new ObjectStep(6640, new RSTile(3286, 9193, 0), "Take");
    ObjectStep pickUpHetJar = new ObjectStep(6634, new RSTile(3286, 9194, 0), "Take");

    ObjectStep pickUpCrondisJarAgain = new ObjectStep(6636, new RSTile(3286, 9195, 0), "Take");
    ObjectStep pickUpScarabasJarAgain = new ObjectStep(6638, new RSTile(3286, 9196, 0), "Take");
    ObjectStep pickUpApmekenJarAgain = new ObjectStep(6640, new RSTile(3286, 9193, 0), "Take");
    ObjectStep pickUpHetJarAgain = new ObjectStep(6634, new RSTile(3286, 9194, 0), "Take");


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



    ObjectStep enterEastRoom = new ObjectStep(6643, new RSTile(3306, 9199, 0),
            "Open");
    UseItemOnObjectStep useSymbolOnSarcopagus = new UseItemOnObjectStep(ItemID.UNHOLY_SYMBOL_4683,
            6630, new RSTile(33109, 9198, 0),
            "Use the unholy symbol on a sarcophagus.", unholySymbol);

    ObjectStep leaveEastRoom = new ObjectStep(6643, new RSTile(3306, 9199, 0),
            "Open");
    ObjectStep jumpPitWithSymbolAgain = new ObjectStep(ObjectID.PIT, new RSTile(3292, 9194, 0),
            "Jump-Across", holySymbol);

    ObjectStep enterEastRoomAgain = new ObjectStep(6643, new RSTile(3306, 9199, 0),
            "Open");

    NPCStep killPriest = new NPCStep(NpcID.POSSESSED_PRIEST, new RSTile(3306, 9196, 0),
            "Kill the posessed priest.");

    NPCStep talkToHighPriestInPyramid = new NPCStep(6191, new RSTile(3306, 9196, 0));

    ObjectStep leavePyramidToFinish = new ObjectStep(6645, new RSTile(3277, 9172, 0),
            "Climb-up");
    NPCStep talkToHighPriestToFinish = new NPCStep(6192, new RSTile(3281, 2772, 0));
    ObjectStep leavePyramid = new ObjectStep(6645, new RSTile(3277, 9172, 0),
            "Climb-up");
    NPCStep returnToHighPriest = new NPCStep(6192,
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
            new RSTile[]{
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
    ObjectStep jumpPitWithSymbol = new ObjectStep(ObjectID.PIT, new RSTile(3292, 9194, 0),
            "Jump-Across", inNorthPyramid.check(), holySymbol);
    public void pickUpAnyJarStep() {
        int[] jars = {6638, 6636, 6634, 6640};
        for (int i : jars) {
            if (Inventory.find(i).length > 0 || Combat.isUnderAttack()) {
                break;
            }
            ObjectStep pickUpAnyJar = new ObjectStep(i, new RSTile(3286, 9194, 0), "Take");
            pickUpAnyJar.execute();
            if (Inventory.find(i).length > 0 || Combat.isUnderAttack()) {
                break;
            }
        }
        if (Combat.isUnderAttack()) {
            CombatUtil.waitUntilOutOfCombat(50);
        }
    }


    ArrayList<GEItem> itemsToBuy = new ArrayList<GEItem>(
            Arrays.asList(
                    new GEItem(ItemID.SUMMER_PIE, 3, 50),
                    new GEItem(ItemID.WATERSKIN[0], 5, 300),
                    new GEItem(ItemID.SHARK, 6, 50),
                    new GEItem(ItemID.TINDERBOX, 1, 500),
                    new GEItem(ItemID.BUCKET_OF_SAP, 1, 500),
                    new GEItem(ItemID.LINEN, 1, 500),
                    new GEItem(ItemID.NARDAH_TELEPORT, 5, 50),
                    new GEItem(ItemID.RUNE_SCIMITAR, 1, 50),
                    new GEItem(ItemID.BAG_OF_SALT, 1, 500),
                    new GEItem(ItemID.RUNE_FULL_HELM, 1, 20),
                    new GEItem(ItemID.RUNE_CHAINBODY, 1, 20),
                    new GEItem(ItemID.RUNE_KITESHIELD, 1, 20),
                    new GEItem(ItemID.RUNE_PLATELEGS, 1, 20),

                    //combat gear
                    new GEItem(ItemID.ANTIDOTE_PLUS_PLUS[0], 2, 30),
                    new GEItem(ItemID.STAMINA_POTION[0], 5, 15),
                    new GEItem(ItemID.RING_OF_WEALTH[0], 1, 25)
            )
    );

    InventoryRequirement initialItemReqs = new InventoryRequirement(new ArrayList<>(
            Arrays.asList(
                    new ItemReq(ItemCollections.getCats()),
                    new ItemReq(ItemID.COINS, 2500, 500),
                    new ItemReq(ItemID.STAMINA_POTION[0], 5, 0),
                    new ItemReq(ItemID.WATERSKIN[0], 3, 1),
                    new ItemReq(ItemID.BUCKET_OF_SAP, 1, 0),
                    new ItemReq(ItemID.LINEN, 1, 0),
                    new ItemReq(ItemID.NARDAH_TELEPORT, 5, 1),
                    new ItemReq(ItemID.ANTIDOTE4, 2, 0),
                    new ItemReq(ItemID.WILLOW_LOGS, 1),
                    new ItemReq(ItemID.BAG_OF_SALT, 1),
                    new ItemReq(ItemID.RUNE_SCIMITAR, 1, true, true),
                    new ItemReq(ItemID.RUNE_FULL_HELM, 1, true, true),
                    new ItemReq(ItemID.RUNE_CHAINBODY, 1, true, true),
                    new ItemReq(ItemID.RUNE_PLATELEGS, 1, true, true),
                    new ItemReq(ItemID.RUNE_KITESHIELD, 1, true, true),

                    new ItemReq(ItemID.SUMMER_PIE, 3, 1),
                    new ItemReq(ItemID.SHARK, 4, 1),
                    new ItemReq(ItemID.TINDERBOX, 1, 1),
                    //combat gear
                    new ItemReq(ItemID.RING_OF_WEALTH[0], 1, 0, true)
            )
    ));

    BuyItemsStep buyStep = new BuyItemsStep(itemsToBuy);

    //   Requirement puzzleOpen;
    Requirement posessedPriestNearby = new NpcCondition(NpcID.POSSESSED_PRIEST);


    public void setupConditions() {
        //    puzzleOpen = new WidgetModelRequirement(147, 3, 6474);
        // picked up het, 404 = 1
        // picked up apmeken, 405 = 1
    }


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

        //  talkToHighPriest.addSubSteps(talkToHighPriestWithoutToken);

        //     pickUpAnyJar.addAlternateObjects(NullObjectID.NULL_6636, NullObjectID.NULL_6638, NullObjectID.NULL_6640);
        //    pickUpAnyJar.addSubSteps(pickUpCrondisJar, pickUpScarabasJar, pickUpApmekenJar, pickUpHetJar);

/*


        solvePuzzleAgain = new DoorPuzzleStep(this);

         returnToHighPriest.addDialogStep("Sure, no problem.");
        leavePyramid.addSubSteps(returnToHighPriest);

  talkToEmbalmerAgain.addSubSteps(talkToEmbalmerAgainNoLinen, talkToEmbalmerAgainNoLinenNoSalt, talkToEmbalmerAgainNoLinenNoSap, talkToEmbalmerAgainNoSalt, talkToEmbalmerAgainNoLinenNoSap, talkToEmbalmerAgainNoSaltNoSap);

         talkToCarpenter.addDialogStep("Alright, I'll get the wood for you.");
        leavePyramidToFinish.addSubSteps(talkToHighPriestToFinish);
        */

    }


    public boolean jumpPit() {
        if (inPyramid.check()) {
            cQuesterV2.status = "Jumping Pit";
            jumpPit.setUseLocalNav(true);
            if(PathingUtil.localNavigation(new RSTile(3292, 9193, 0)))
                PathingUtil.movementIdle();
            if (MyPlayer.getRunEnergy() < 60){
                Log.debug("Drnking stamina potion for run energy");
                Utils.clickInventoryItem(ItemID.SUMMER_PIE);
                Utils.drinkPotion(ItemID.STAMINA_POTION);
            }
            jumpPit.execute();
            if (Timer.waitCondition(() -> Player.getAnimation() != -1, 3500))
                Timer.waitCondition(() -> Player.getAnimation() == -1, 6500);
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
            Timer.waitCondition(() -> inPyramid.check(), 5000, 7000);
        }
    }

    public void handleRitual(){
        int varbit = QuestVarbits.QUEST_ICTHLARINS_LITTLE_HELPER.getId();
        for (int i =0 ; i <30; i++) {
            if (Utils.getVarBitValue(varbit ) != 21)
                break;

            cQuesterV2.status = "Handling Ritual";
            if (!MyPlayer.isHealthBarVisible()) {
                cQuesterV2.status = "Handling Ritual - waiting for Converation window";
                NPCInteraction.waitForConversationWindow();
            } else {
                cQuesterV2.status = "Handling Ritual - Combat";
                CombatUtil.waitUntilOutOfCombat(50);
                continue;
            }
            if (NPCInteraction.isConversationWindowUp()) {
                cQuesterV2.status = "Handling Ritual - chat";
                NPCInteraction.handleConversation();
                Waiting.waitNormal(1200,150);
                continue;
            }
            //varbit == 21
            ConditionalStep goToRitual = new ConditionalStep(enterRock);
            goToRitual.addStep(new Conditions(inEastRoom, posessedPriestNearby), killPriest);
            goToRitual.addStep(inEastRoom, talkToHighPriestInPyramid);
            goToRitual.addStep(inNorthPyramid, enterEastRoomAgain);
            goToRitual.addStep(inPyramid, jumpPitWithSymbol);
            goToRitual.addStep(inSoph, openPyramidDoorWithSymbol);

            goToRitual.execute();
            Waiting.waitNormal(500,15);
        }


    }

    public Map<Integer, QuestStep> loadSteps() {

        Map<Integer, QuestStep> steps = new HashMap<>();

        steps.put(0, talkToWanderer);
        steps.put(1, talkToWandererAgain);
        steps.put(2, enterRock);


        ConditionalStep takeTheJar = new ConditionalStep(enterRock);
        takeTheJar.addStep(new Conditions(inNorthPyramid, jar), returnOverPit);
        takeTheJar.addStep(new Conditions(inNorthPyramid, hasHetJar, killedGuardian), pickUpHetJarAgain);
        takeTheJar.addStep(new Conditions(inNorthPyramid, hasCrondisJar, killedGuardian), pickUpCrondisJarAgain);
        takeTheJar.addStep(new Conditions(inNorthPyramid, killedGuardian), pickUpAnyJarAgain);
        takeTheJar.addStep(new Conditions(inNorthPyramid, hasHetJar), pickUpHetJar);
        takeTheJar.addStep(new Conditions(inNorthPyramid, hasCrondisJar), pickUpCrondisJar);
        takeTheJar.addStep(inNorthPyramid, pickUpAnyJar);
        //  takeTheJar.addStep(inPyramid, jumpPitAgain);
        takeTheJar.addStep(inSoph, openPyramidDoor);

        steps.put(7, takeTheJar);
        steps.put(8, takeTheJar);
        steps.put(9, takeTheJar);
        steps.put(10, takeTheJar);
        steps.put(11, takeTheJar);


        ConditionalStep prepareItems = new ConditionalStep(enterRockWithItems);
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

        killPriest.setInteractionString("Attack");

        ConditionalStep goToRitual = new ConditionalStep(enterRock);
        goToRitual.addStep(new Conditions(inEastRoom, posessedPriestNearby), killPriest);
        goToRitual.addStep(inEastRoom, talkToHighPriestInPyramid);
        goToRitual.addStep(inNorthPyramid, enterEastRoomAgain);
        goToRitual.addStep(inPyramid, jumpPitWithSymbol);
        goToRitual.addStep(inSoph, openPyramidDoorWithSymbol);

        steps.put(16, goToRitual);

        ConditionalStep placeSymbol = new ConditionalStep(enterRock);
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

        // first conditionalStep used to be enterRock but was causing teleports
        ConditionalStep finishTheQuest = new ConditionalStep(talkToHighPriestToFinish);
        finishTheQuest.addStep(new Conditions(inPyramid, inEastRoom), leaveEastRoom);
        finishTheQuest.addStep(new Conditions(inPyramid, inNorthPyramid), jumpPit);
        finishTheQuest.addStep(inPyramid, leavePyramidToFinish);
        finishTheQuest.addStep(inSoph, talkToHighPriestToFinish);

        steps.put(25, finishTheQuest);
        return steps;
    }


    ClickItemStep dropCrondisJar = new ClickItemStep("Canopic jar", "Drop",
            new RSTile(3286, 9195, 0), jar, hasCrondisJar);
    ClickItemStep dropApmekenJar = new ClickItemStep("Canopic jar", "Drop",
            new RSTile(3286, 9193, 0), jar, hasApmekenJar);
    ClickItemStep dropHetJar = new ClickItemStep("Canopic jar",
            "Drop", new RSTile(3286, 9194, 0), jar, hasHetJar);
    ClickItemStep dropScarabasJar = new ClickItemStep("Canopic jar",
            "Drop", new RSTile(3286, 9196, 0), jar, hasScarabasJar);

    public void pickupKitten() {

        Optional<Npc> kitten = Query.npcs().nameContains("Kitten")
                .isInteractingWithMe()
                .sortedByDistance().findBestInteractable();
        if (kitten.map(k -> k.interact("Pick-up")).orElse(false)) {
            cQuesterV2.status = "Picking up Cat";
            General.println("[Debug]: " + cQuesterV2.status);
            Timer.slowWaitCondition(() -> Inventory.find("Pet kitten").length > 0, 5000, 7000);
        }
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
        return cQuesterV2.taskList.get(0).equals(this);
    }

    @Override
    public void execute() {
        setupSteps();
        talkToWanderer.addDialogStep("Why? What's your problem with it?");
        talkToWanderer.addDialogStep("Ok I'll get your supplies.", "Yes.");
        talkToWandererAgain.addDialogStep("Yes. I have them all here.");
        jar.addAlternateItemID(ItemID.CANOPIC_JAR_4679, ItemID.CANOPIC_JAR_4680, ItemID.CANOPIC_JAR_4681);
        returnToHighPriest.addDialogStep("Sure, no problem.");
        leavePyramid.addSubSteps(returnToHighPriest);

        talkToEmbalmerAgain.addSubSteps(talkToEmbalmerAgainNoLinen, talkToEmbalmerAgainNoLinenNoSalt, talkToEmbalmerAgainNoLinenNoSap, talkToEmbalmerAgainNoSalt, talkToEmbalmerAgainNoLinenNoSap, talkToEmbalmerAgainNoSaltNoSap);

        talkToCarpenter.addDialogStep("Alright, I'll get the wood for you.");
        leavePyramidToFinish.addSubSteps(talkToHighPriestToFinish);
        //pickUpAnyJarAgain.addAlternateObjects(NullObjectID.NULL_6636, NullObjectID.NULL_6638, NullObjectID.NULL_6640);
        pickUpAnyJarAgain.addSubSteps(pickUpCrondisJarAgain, pickUpScarabasJarAgain, pickUpApmekenJarAgain, pickUpHetJarAgain);


        //  solveDoorPuzzle = new DoorPuzzleStep(this);

        talkToSphinx.addDialogStep("I need help.");
        talkToSphinx.addDialogStep("Okay, that sounds fair.");
        talkToSphinx.addDialogStep("9.");
        talkToSphinx.addDialogStep("Totally positive.");

        int varbit = QuestVarbits.QUEST_ICTHLARINS_LITTLE_HELPER.getId();
        if (Utils.getVarBitValue(varbit) == 26) {
            cQuesterV2.taskList.remove(this);
            return;
        }

        if (Utils.getVarBitValue(varbit) == 0) {
            if (!initialItemReqs.check()) {
                buyStep.buyItems();
                initialItemReqs.withdrawItems();
            } else {
                cQuesterV2.status = "Talking to Wanderer";
                talkToWanderer.execute();
                //needed for whne you pick up your cat
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation("Yes.");
            }
        } else if (Utils.getVarBitValue(varbit) == 1) {
            cQuesterV2.status = "Talking to Wanderer again";
            talkToWandererAgain.execute();
            Timer.waitCondition(() -> Utils.getVarBitValue(varbit) == 2, 4500, 5500);
        } else if (Utils.getVarBitValue(varbit) == 2) {
            cQuesterV2.status = "Entering Rock";
            enterRock.execute();

        } else if (Utils.getVarBitValue(varbit) == 3 ||
                Utils.getVarBitValue(varbit) == 4) {
            if (!inSoph.check() && !inPyramid.check()) {
                Log.debug("Entering rock");
                enterRock.execute();
            }

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

        } else if (Utils.getVarBitValue(varbit) > 6 && Utils.getVarBitValue(varbit) < 12) {
            if ((inNorthPyramid.check() || inWestRoom.check()) && jar.check()) {
                cQuesterV2.status = "Returning over pit";
                returnOverPit.execute();
            } else if (inNorthPyramid.check() && hasHetJar.check() && killedGuardian.check()) {
                cQuesterV2.status = "Getting Het Jar again";
                if (!inWestRoom.check())
                    handlePuzzle();
                pickUpHetJarAgain.execute();
            } else if (inNorthPyramid.check() && hasCrondisJar.check() && killedGuardian.check()) {
                cQuesterV2.status = "Getting Crondis Jar again";
                if (!inWestRoom.check())
                    handlePuzzle();
                pickUpCrondisJarAgain.execute();
            } else if (inNorthPyramid.check() && hasCrondisJar.check() && killedGuardian.check()) {
                cQuesterV2.status = "Getting Any Jar again";
                if (!inWestRoom.check())
                    handlePuzzle();
                pickUpAnyJarStep();
            } else if (inNorthPyramid.check()) {
                cQuesterV2.status = "Getting Any Jar";
                if (!inWestRoom.check())
                    handlePuzzle();
                pickUpAnyJarStep();
            } else if (inPyramid.check()) {
                cQuesterV2.status = "Jumping pit";
                jumpPit();
                handlePuzzle();
            } else if (inSoph.check()) {
                cQuesterV2.status = "Opening Pyramid door";
                openPyramidDoor.execute();
            } else {
                cQuesterV2.status = "Entering Rock";
                enterRock.execute();
            }
        } else if (Utils.getVarBitValue(varbit) == 12 || Utils.getVarBitValue(varbit) == 13) {
            if (!inWestRoom.check())
                handlePuzzle();
            if (new Conditions(inNorthPyramid, hasHetJar).check()) {
                cQuesterV2.status = "Dropping Het Jar";

                dropHetJar.execute();
            } else if (new Conditions(inNorthPyramid, hasCrondisJar).check()) {
                cQuesterV2.status = "Dropping Crondis Jar";
                dropCrondisJar.execute();
            } else if (inNorthPyramid.check()) {
                cQuesterV2.status = "Dropping Jar";
                dropCrondisJar.execute();
                dropApmekenJar.execute();
                dropHetJar.execute();
                dropScarabasJar.execute();
            } else if (inPyramid.check()) {
                jumpPit();
            } else if (inSoph.check()) {
                cQuesterV2.status = "Opening Pyramid door";
                openPyramidDoor.execute();
            } else {
                cQuesterV2.status = "Entering Rock";
                enterRock.execute();
            }

        } else if (Utils.getVarBitValue(varbit) == 14) {
            if (inPyramid.check()) {
                cQuesterV2.status = "Leaving pyramid";
                if (inWestRoom.check())
                    openWestDoor.execute();

                if (inNorthPyramid.check())
                    jumpPit();

                leavePyramid.execute();
            } else if (inSoph.check()) {
                cQuesterV2.status = "Talking to high priest";
                returnToHighPriest.execute();
            } else {
                cQuesterV2.status = "Talking to high priest";
                returnToHighPriest.execute();
            }
        } else if (Utils.getVarBitValue(varbit) == 17) {
            ConditionalStep placeSymbol = new ConditionalStep(enterRock);
            placeSymbol.addStep(inEastRoom, useSymbolOnSarcopagus);
            placeSymbol.addStep(inPyramid, enterEastRoom);

            placeSymbol.execute();
            if (NPCInteraction.waitForConversationWindow())
                NPCInteraction.handleConversation();

        } else if (Utils.getVarBitValue(varbit) == 20 || Utils.getVarBitValue(varbit) == 21
                || Utils.getVarBitValue(varbit) == 22) {
            handleRitual();
         }
        else {
            Map<Integer, QuestStep> steps = loadSteps();
            Optional<QuestStep> step = Optional.ofNullable(steps.get(Utils.getVarBitValue(varbit)));
            cQuesterV2.status = step.map(s -> s.toString()).orElse("Unknown Step");
            step.ifPresent(QuestStep::execute);
        }
        // need this for symbol step
        Waiting.waitNormal(1250,100);
        if (NPCInteraction.isConversationWindowUp()){
            NPCInteraction.handleConversation("Yes.");
        }
        if (Utils.inCutScene())
            Utils.cutScene();

        ConditionalStep prepareItems = new ConditionalStep(enterRockWithItems);
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

    }

    public List<Requirement> getRequirements() {
        SkillRequirement attack = new SkillRequirement(Skills.SKILLS.ATTACK, 50);
        SkillRequirement def = new SkillRequirement(Skills.SKILLS.ATTACK, 40);
        SkillRequirement agil = new SkillRequirement(Skills.SKILLS.AGILITY, 26);
        SkillRequirement prayer = new SkillRequirement(Skills.SKILLS.PRAYER, 40);
        return List.of(attack, def, agil, prayer);
    }

    @Override
    public String questName() {
        int varbit = QuestVarbits.QUEST_ICTHLARINS_LITTLE_HELPER.getId();
        return "Icthlarin's Little Helper (" + Utils.getVarBitValue(varbit) + ")";
    }

    @Override
    public boolean checkRequirements() {
        List<Requirement> reqs = getRequirements();
        // if (reqs.stream().filter(r-> !r.check())){

        //      return false;
        //  }
        if (Utils.getCombatLevel() < 50) {
            return false;
        } else if (!Quest.GERTRUDES_CAT.getState().equals(Quest.State.COMPLETE)) {
            Log.error("Missing Gertrudes Cat requirement");
            return false;
        }

        return true;
    }

    @Override
    public List<Requirement> getGeneralRequirements() {
        return null;
    }

    @Override
    public List<ItemRequirement> getBuyList() {
        return null;
    }
}
