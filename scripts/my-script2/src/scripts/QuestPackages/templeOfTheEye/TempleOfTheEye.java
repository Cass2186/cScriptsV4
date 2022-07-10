package scripts.QuestPackages.templeOfTheEye;

import com.google.errorprone.annotations.Var;
import lombok.Getter;
import org.tribot.api2007.Game;
import org.tribot.api2007.Skills;
import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSTile;
import org.tribot.script.sdk.*;
import org.tribot.script.sdk.query.Query;
import org.tribot.script.sdk.types.*;
import org.tribot.script.sdk.util.TribotRandom;
import org.tribot.script.sdk.walking.LocalWalking;
import scripts.*;
import scripts.GEManager.GEItem;
import scripts.QuestPackages.HeroesQuest.HeroesQuestBlackArmsGang;
import scripts.QuestPackages.WitchsPotion.WitchsPotion;
import scripts.QuestSteps.*;
import scripts.Requirements.*;
import scripts.Requirements.Items.ItemCollections;
import scripts.Requirements.Util.ConditionalStep;
import scripts.Requirements.Util.Conditions;
import scripts.Requirements.Util.Operation;
import scripts.Tasks.Priority;

import java.util.*;

/*
        VARBITS
        -------
        13738: Quest Step
        13739: Told to see Herbert
        13740: One-time abyss teleport
        13741: Received cup of tea
        13742: Apprentice Tamara puzzle
        13743: Apprentice Felix puzzle
        13744: Apprentice Cordelia puzzle
        13745: Quest offered
        13747 - 13752: Energy orbs
        13753: One-time Wizard's Tower teleport
        13759: Guardians of the Rift Tutorial progress
        */
public class TempleOfTheEye implements QuestTask {

    private static TempleOfTheEye quest;

    public static TempleOfTheEye get() {
        return quest == null ? quest = new TempleOfTheEye() : quest;
    }

    //1 = law
    //2 = earth
    // 3 = cosmic
    //4 = fire
    // 5 = nature
    //6 = death

    int LAW_ENERGY = 43846;
    int EARTH_ENERGY = 43842;
    int COSMIC_ENERGY = 43843;
    int FIRE_ENERGY = 43847;
    int NATURE_ENERGY = 43845;
    int DEATH_ENERGY = 43844;

    BALLS ball1;
    BALLS ball2;
    BALLS ball3;
    BALLS ball4;
    BALLS ball5;
    BALLS ball6;
    int[] allBallIds = {
            LAW_ENERGY, //law
            EARTH_ENERGY, //earth
            COSMIC_ENERGY, //cosmic
            FIRE_ENERGY, //fire
            NATURE_ENERGY, //nature
            DEATH_ENERGY// death
    };


    private enum BALLS {

        LAW_ENERGY(43846),
        EARTH_ENERGY(43842),
        COSMIC_ENERGY(43843),
        FIRE_ENERGY(43847),
        NATURE_ENERGY(43845),
        DEATH_ENERGY(43844);

        BALLS(int id) {
            this.id = id;
        }

        @Getter
        private int id;

    }

    ArrayList<BALLS> knownBalls = new ArrayList<>(List.of(
    ));

    ArrayList<BALLS> possibleBalls = new ArrayList<>(List.of(
            BALLS.LAW_ENERGY, //law
            BALLS.EARTH_ENERGY, //earth
            BALLS.COSMIC_ENERGY, //cosmic
            BALLS.FIRE_ENERGY, //fire
            BALLS.NATURE_ENERGY, //nature
            BALLS.DEATH_ENERGY// death
    ));

    private void handleBalls() {
        if (ball1 == null && numberOfWhiteBalls() == 0) {
            ball1 = getWhiteBallId();
            Log.info("Got ball 1 ID " + ball1);
            possibleBalls.remove(ball1);
            knownBalls.add(ball1);
        } else if (ball1 != null && numberOfWhiteBalls() == 0) {
            checkBall(ball1.getId());
        }
        if (ball2 == null && numberOfWhiteBalls() == 1) {
            ball2 = getWhiteBallId();
            Log.info("Got ball 2 ID " + ball2);
            possibleBalls.remove(ball2);
            knownBalls.add(ball2);
        } else if (ball2 != null && numberOfWhiteBalls() == 1) {
            checkBall(ball2.getId());
        }
        if (ball3 == null && numberOfWhiteBalls() == 2) {
            ball3 = getWhiteBallId();
            Log.info("Got ball 3 ID " + ball3);
            possibleBalls.remove(ball3);
            knownBalls.add(ball3);
        } else if (ball3 != null && numberOfWhiteBalls() == 2) {
            checkBall(ball3.getId());
        }
        if (ball4 == null && numberOfWhiteBalls() == 3) {
            ball4 = getWhiteBallId();
            Log.info("Got ball 4 ID " + ball4);
            possibleBalls.remove(ball4);
            knownBalls.add(ball4);
        } else if (ball4 != null && numberOfWhiteBalls() == 3) {
            checkBall(ball4.getId());
        }
        if (ball5 == null && numberOfWhiteBalls() == 4) {
            ball5 = getWhiteBallId();
            Log.info("Got ball 5 ID " + ball5);
            possibleBalls.remove(ball5);
            knownBalls.add(ball5);
        } else if (ball5 != null && numberOfWhiteBalls() == 4) {
            checkBall(ball5.getId());
        }
        if (ball6 == null && numberOfWhiteBalls() == 5) {
            ball6 = getWhiteBallId();
            Log.info("Got ball 6 ID " + ball6);
            possibleBalls.remove(ball6);
            knownBalls.add(ball6);
        } else if (ball6 != null && numberOfWhiteBalls() == 5) {
            checkBall(ball6.getId());
        }


    }

    // touchRunes = new ObjectStep(43768, "Touch");
    //returns true if the ball disapears
    private boolean checkBall(int ballId) {
        return Query.gameObjects().maxDistance(6)
                .actionContains("Touch")
                .idEquals(ballId)
                .findClosestByPathDistance()
                .map(b -> b.interact("Touch")).orElse(false) &&
                Waiting.waitUntil(TribotRandom.uniform(5000, 7000), TribotRandom.uniform(400, 600),
                        () -> isBallWhite(ballId));
    }

    //when it turns white, the action  dissapears so we use that to count the available ones
    private boolean isBallWhite(int id) {
        return Query.gameObjects().maxDistance(6)
                .actionNotContains("Touch")
                .idEquals(id)
                .isAny();
    }

    private void clickKnownBalls() {
        for (BALLS b : knownBalls) {
            Log.info("Clicking known balls");
            checkBall(b.getId());
        }

    }

    private int numberOfWhiteBalls() {
        return Query.gameObjects().maxDistance(6)
                .actionNotContains("Touch")
                .idEquals(allBallIds)
                .count();
    }

    private BALLS getWhiteBallId() {
        for (BALLS b : possibleBalls) {
            if (checkBall(b.getId())) {
                Log.info("Ball is white");
                return b;
            } else {
                clickKnownBalls();
            }
        }
        return null;
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

        int varbit = GameState.getVarbit(QuestVarbits.QUEST_TEMPLE_OF_THE_EYE.getId());
        Map<Integer, QuestStep> steps = loadSteps();
        if (varbit == 0 && !initialItemReqs.check()) {
            buyStep.buyItems();
            initialItemReqs.withdrawItems();
        } else if (varbit == 35) {
            handleBalls();
            return;
        } else if (varbit == 75) {
            Log.info("Handling Apprentices");
            if (felixPuzzleNotSeen.check()) talkToFelix.execute();
            if (closeWindow()) Utils.idleNormalAction();
            if (tamaraPuzzleNotSeen.check()) talkToTamara.execute();
            if (closeWindow()) Utils.idleNormalAction();
            if (cordeliaPuzzleNotSeen.check()) talkToCordelia.execute();
            if (closeWindow()) Utils.idleNormalAction();
        } else if (varbit == 80) {
            cQuesterV2.status = "Handing Trailborn step (varbit - 80)";
            PathingUtil.walkToTile(new RSTile(3112, 3162, 1));
            if (NpcChat.talkToNPC("Wizard Traiborn")) {
                NpcChat.handle(true, "I think I know what a thingummywut is!");
                Log.info("Typing answer");
                Utils.idlePredictableAction();
                Keyboard.typeString("11");
                Utils.idlePredictableAction();
                Keyboard.pressEnter();
                NpcChat.handle(true, "I think I know what a thingummywut is!");
            }
            varbit = GameState.getVarbit(QuestVarbits.QUEST_TEMPLE_OF_THE_EYE.getId());
        } else if (varbit > 100 && !GameState.isInInstance()) {
            //todo eneter portal
        } else if (varbit == 115) {
            cQuesterV2.status = "Handling Tutorial";
            //todo the tutorial
            handleTutorial();

            if (Utils.inCutScene())
                Utils.cutScene();

            if (ChatScreen.isOpen())
                NpcChat.handle();
            return;
        }


        Optional<QuestStep> step = Optional.ofNullable(steps.get(varbit));
        cQuesterV2.status = step.map(s -> s.toString()).orElse("Unknown Step Name: Varbit = " + varbit);
        step.ifPresent(QuestStep::execute);

        if (Utils.inCutScene())
            Utils.cutScene();

        if (ChatScreen.isOpen())
            NpcChat.handle();

        Waiting.waitNormal(900, 50);
    }

    @Override
    public String questName() {
        return "Temple of the Eye (vb: " +
                GameState.getVarbit(QuestVarbits.QUEST_TEMPLE_OF_THE_EYE.getId()) + " || GS: " +
                GameState.getVarbit(tutorialSetting) + ")";
    }

    @Override
    public boolean checkRequirements() {
        return Skill.RUNECRAFT.getActualLevel() >= 10;
    }


    @Override
    public List<ItemRequirement> getBuyList() {
        return null;
    }

    @Override
    public boolean isComplete() {
        return  GameState.getVarbit(QuestVarbits.QUEST_TEMPLE_OF_THE_EYE.getId())  == 130;
    }

    //Items Required
    ItemRequirement bucketOfWater, strongTea, eyeAmulet, chisel, pickaxe, abyssalIncantation;

    //Items Recommended
    ItemRequirement varrockTeleport, alKharidTeleport;

    Requirement inAbyss, canTeleportFromHerbert, thrownBucket, givenAmuletBack, inWizardBasement, canTeleportFromPersten,
            inWizardFloorOne, felixPuzzleNotSeen, tamaraPuzzleNotSeen, cordeliaPuzzleNotSeen,
            felixRiftTalk, tamaraRiftTalk, cordeliaRiftTalk, mysteriousVisionSeen, inTempleOfTheEyeTutorial;

    QuestStep talkToPersten1, finishTalkToPersten1, talkToMage1, getTeaForMage, talkToMage2, finishTalkToMage2,
            teleportViaHerbert, talkToDarkMage1, finishTalkToDarkMage1, talkToDarkMage2, talkToPersten2,
            finishTalkToPersten2, teleportToArchmage, goDownToArchmage, talktoArchmage1, finishTalkingToArchmage1,
            goUpToTraibornBasement, goUpToTraiborn, talktoTrailborn1, talkToFelix, talkToTamara, talkToCordelia,
            talktoTrailborn2, goDownToArchmageFloorOne, goDownToArchmage2, talktoArchmage2, performIncantation,
            enterWizardBasement, enterPortal, templeCutscene1, talkToFelix2, talkToTamara2, talkToCordelia2, talkToPersten3,
            templeCutscene2, debrief, guardiansTutorial, templeCutscene3, finishQuest;
    NPCStep talkToMageInWildy;
    ObjectStep touchRunes;

    //RSArea()s
    RSArea abyss, wizardBasement, wizardFloorOne, templeOfTheEye, templeOfTheEye2;


    private boolean closeWindow() {
        return Query.widgets().actionContains("Close")
                .findFirst().map(c -> c.click()).orElse(false);
    }


    ArrayList<GEItem> itemsToBuy = new ArrayList<GEItem>(
            Arrays.asList(
                    new GEItem(ItemID.BUCKET_OF_WATER, 1, 500),
                    new GEItem(ItemID.CHISEL, 1, 600),
                    new GEItem(ItemID.IRON_PICKAXE, 1, 500),
                    new GEItem(ItemID.VARROCK_TELEPORT, 5, 50),
                    new GEItem(ItemID.NECKLACE_OF_PASSAGE[0], 1, 50),
                    new GEItem(ItemID.AMULET_OF_GLORY[2], 1, 30),
                    new GEItem(ItemID.RING_OF_DUELING[0], 1, 30),
                    new GEItem(ItemID.STAMINA_POTION[0], 2, 30),
                    new GEItem(ItemID.RING_OF_WEALTH[0], 1, 25)
            )
    );


    BuyItemsStep buyStep = new BuyItemsStep(itemsToBuy);
    InventoryRequirement initialItemReqs = new InventoryRequirement(new ArrayList<>(
            Arrays.asList(
                    new ItemReq(ItemID.BUCKET_OF_WATER, 1, 1),
                    new ItemReq(ItemID.CHISEL, 1, 1),
                    new ItemReq(ItemID.IRON_PICKAXE, 1, 1),
                    new ItemReq(ItemID.VARROCK_TELEPORT, 5, 1),
                    new ItemReq(ItemID.NECKLACE_OF_PASSAGE5, 1, 0),
                    new ItemReq(ItemID.AMULET_OF_GLORY[2], 1, 0, true, true),
                    new ItemReq(ItemID.STAMINA_POTION[0], 2, 0),
                    new ItemReq(ItemID.RING_OF_DUELING[0], 1, 0),
                    new ItemReq(ItemID.RING_OF_WEALTH[0], 1, 0, true)
            )
    ));

    private LocalTile getArrowTile() {
        Optional<LocalTile> pos = GameState.getHintArrowPosition();
        pos.ifPresent(p -> Log.info("Found arrow Tile at " + p.toString()));
        return pos.orElseGet(() -> MyPlayer.getTile().toLocalTile());
    }

    private Optional<Npc> getNpcFromArrow() {
        LocalTile t = getArrowTile();
        return Query.npcs().inArea(Area.fromRadius(t, 2)).findClosestByPathDistance();
    }

    private Optional<GameObject> getObjFromArrow() {
        LocalTile t = getArrowTile();
        List<GameObject> list = Query.gameObjects()
                .inArea(Area.fromRadius(t, 2)).toList();
        for (GameObject obj : list) {
            if (obj.getActions().size() > 0) {
                Log.info("Found obj with action size " + obj.getActions().size());
                return Optional.of(obj);
            }
        }
        return Query.gameObjects().tileEquals(t)
                // .isNotInteractive() //todo check
                .findBestInteractable();
    }

    int tutorialSetting = 13759;

    private void handleTutorial() {
        if (GameState.getVarbit(tutorialSetting) == 15) {
            if (QueryUtils.getNpc("Apprentice Felix")
                    .map(f -> f.interact("Talk-to")).orElse(false)) {
                NpcChat.handle(true);
            }
        } else if (GameState.getVarbit(tutorialSetting) == 20) {
            cQuesterV2.status = "Taking Cells";
            if (!Inventory.contains(26883) &&
                    Query.gameObjects().nameContains("Weak cells")
                            .actionContains("Take").findClosest()
                            .map(o -> o.interact("Take")).orElse(false))
                Waiting.waitUntil(5000, 500, () ->
                        Inventory.contains(26883));
            cQuesterV2.status = "Talking to Tamara";
            if (Inventory.contains(26883) &&
                    QueryUtils.getNpc("Apprentice Tamara")
                            .map(f -> f.interact("Talk-to")).orElse(false)) {
                NpcChat.handle(true);
            }
        } else if (GameState.getVarbit(tutorialSetting) == 25) {
            cQuesterV2.status = "Placing Cell";
            if (Query.gameObjects().actionContains("Place-cell").findClosest()
                    .map(o -> o.interact("Place-cell")).orElse(false))
                Waiting.waitUntil(5000, 500, () -> GameState.getVarbit(tutorialSetting) == 70);


        } else if (GameState.getVarbit(tutorialSetting) == 35) {
            cQuesterV2.status = "Talking to Tamara";
            if (Inventory.contains(26883) &&
                    QueryUtils.getNpc("Apprentice Tamara")
                            .map(f -> f.interact("Talk-to")).orElse(false)) {
                NpcChat.handle(true);
            }
        } else if (GameState.getVarbit(tutorialSetting) == 40) {
            cQuesterV2.status = "Talking to Felix";
            if (QueryUtils.getNpc("Apprentice Felix")
                    .map(f -> f.interact("Talk-to")).orElse(false)) {
                NpcChat.handle(true);
            }
        } else if (GameState.getVarbit(tutorialSetting) == 45) {
            cQuesterV2.status = "Taking Cells";
            if (!Inventory.contains(26883) &&
                    Query.gameObjects().nameContains("Weak cells")
                            .actionContains("Take").findClosest()
                            .map(o -> o.interact("Take")).orElse(false))
                Waiting.waitUntil(5000, 500, () ->
                        Inventory.contains(26883));

            cQuesterV2.status = "Assembling Cell";
            if (Inventory.contains(26883) && Query.gameObjects().actionContains("Assemble")
                    .nameContains("elemental")
                    .findClosest()
                    .map(o -> o.interact("Assemble")).orElse(false))
                Waiting.waitUntil(5000, 500, () ->
                        !Inventory.contains(26883));

            if (!Inventory.contains(26883) &&
                    Query.gameObjects().nameContains("Weak cells")
                            .actionContains("Take").findClosest()
                            .map(o -> o.interact("Take")).orElse(false))
                Waiting.waitUntil(5000, 500, () ->
                        Inventory.contains(26883));

            cQuesterV2.status = "Assembling Cell";
            if (Inventory.contains(26883) && Query.gameObjects().actionContains("Assemble")
                    .nameContains("catalytic")
                    .findClosest()
                    .map(o -> o.interact("Assemble")).orElse(false))
                Waiting.waitUntil(5000, 500, () ->
                        GameState.getVarbit(tutorialSetting) == 50);

        } else if (GameState.getVarbit(tutorialSetting) == 50) {
            cQuesterV2.status = "Talking to Tamara";
            if (QueryUtils.getNpc("Apprentice Tamara")
                    .map(f -> f.interact("Talk-to")).orElse(false)) {
                NpcChat.handle(true);
            }
        }
        if (GameState.getVarbit(tutorialSetting) == 55) {
            if (Inventory.getCount("Guardian fragments") < 5 && Inventory.getCount("Guardian essence") < 5 &&
                    Query.gameObjects().actionContains("Mine").findClosest()
                            .map(o -> o.interact("Mine")).orElse(false))
                Waiting.waitUntil(25000, 500, () -> Inventory.getCount("Guardian fragments") == 5);

            if (Inventory.getCount("Guardian fragments") == 5 && Inventory.getCount("Guardian essence") < 5 &&
                    Query.gameObjects().actionContains("Work-at").findClosest()
                            .map(o -> o.interact("Work-at")).orElse(false))
                Waiting.waitUntil(25000, 500, () -> Inventory.getCount("Guardian essence") == 5);

            if (Inventory.getCount("Guardian essence") == 5 &&
                    Inventory.getCount("Uncharged cell") == 0 &&
                    Query.gameObjects().actionContains("Take").nameContains("Uncharged").findClosest()
                            .map(o -> o.interact("Take")).orElse(false))
                Waiting.waitUntil(8000, 500, () -> Inventory.getCount("Uncharged cell") == 1);

            cQuesterV2.status = "Talking to Tamara";
            if (Inventory.getCount("Guardian essence") == 5 &&
                    Inventory.getCount("Uncharged cell") == 0 &&
                    QueryUtils.getNpc("Apprentice Tamara")
                            .map(f -> f.interact("Talk-to")).orElse(false)) {
                NpcChat.handle(true);
            }
        }
        if (GameState.getVarbit(tutorialSetting) == 60) {
            //enter water portal
            if (Inventory.getCount("Guardian essence") == 5
                    && !Query.gameObjects().actionContains("Craft-rune").isAny()
                    && Inventory.getCount("Uncharged cell") == 1
                    && Query.gameObjects().actionContains("Enter")
                    .nameContains("Guardian of Mind")
                    .findClosest()
                    .map(o -> o.interact("Enter")).orElse(false))
                Waiting.waitUntil(8000, 500,
                        () -> Query.gameObjects().actionContains("Craft-rune").isAny());

            //make runes
            if (Inventory.getCount("Guardian essence") == 5 &&
                    Inventory.getCount("Uncharged cell") == 1 &&
                    Query.gameObjects().actionContains("Craft-rune").findClosest()
                            .map(o -> o.interact("Craft-rune")).orElse(false))
                Waiting.waitUntil(8000, 500, () -> Inventory.getCount("Catalytic guardian stone") == 5);

            //leave water rune area
            if (Query.gameObjects().actionContains("Craft-rune").isAny() &&
                    Inventory.getCount("Catalytic guardian stone") == 5 &&
                    Query.gameObjects().actionContains("Use")
                            .nameContains("Portal").findClosest()
                            .map(o -> o.interact("Use")).orElse(false))
                Waiting.waitUntil(8000, 500, () -> !Query.gameObjects().actionContains("Craft-rune").isAny());

            cQuesterV2.status = "Talking to Tamara";
            if (Inventory.getCount("Catalytic guardian stone") == 5  &&
                    QueryUtils.getNpc("Apprentice Tamara")
                            .map(f -> f.interact("Talk-to")).orElse(false)) {
                NpcChat.handle(true);
            }
        } else if (GameState.getVarbit(tutorialSetting) == 65) {
            cQuesterV2.status = "Placing Cell";
            if (Query.gameObjects().actionContains("Place-cell").findClosest()
                    .map(o -> o.interact("Place-cell")).orElse(false))
                Waiting.waitUntil(5000, 500, () -> GameState.getVarbit(tutorialSetting) == 70);
        }
        if (GameState.getVarbit(tutorialSetting) == 70) {
            cQuesterV2.status = "Powering up";
            if (Query.npcs().actionContains("Power-up")
                    .findClosest().map(n -> n.interact("Power-up")).orElse(false))
                Waiting.waitUntil(5000, 500, () -> GameState.getVarbit(tutorialSetting) == 75);
        }
        if (GameState.getVarbit(tutorialSetting) == 75) {
            cQuesterV2.status = "Talking to Tamara";
            if (Query.npcs().nameContains("Apprentice Tamara").findClosest()
                    .map(n -> n.interact("Talk-to")).orElse(false))
                Waiting.waitUntil(5000, 500, () -> ChatScreen.isOpen());
            NpcChat.handle();
        }
        if (GameState.getVarbit(tutorialSetting) == 80) {
            if (Inventory.getCount("Guardian fragments") < 5 && Inventory.getCount("Guardian essence") < 5 &&
                    Query.gameObjects().actionContains("Mine").findClosest()
                            .map(o -> o.interact("Mine")).orElse(false))
                Waiting.waitUntil(25000, 500, () -> Inventory.getCount("Guardian fragments") == 5);

            if (Inventory.getCount("Guardian fragments") == 5 && Inventory.getCount("Guardian essence") < 5 &&
                    Query.gameObjects().actionContains("Work-at").findClosest()
                            .map(o -> o.interact("Work-at")).orElse(false))
                Waiting.waitUntil(25000, 500, () -> Inventory.getCount("Guardian essence") == 5);

            if (Inventory.getCount("Guardian essence") == 5 &&
                    Inventory.getCount("Uncharged cell") == 0 &&
                    Query.gameObjects().actionContains("Take").nameContains("Uncharged").findClosest()
                            .map(o -> o.interact("Take")).orElse(false))
                Waiting.waitUntil(8000, 500, () -> Inventory.getCount("Uncharged cell") == 1);

            //enter water portal
            if (Inventory.getCount("Guardian essence") == 5
                    && !Query.gameObjects().actionContains("Craft-rune").isAny()
                    && Inventory.getCount("Uncharged cell") == 1
                    && Query.gameObjects().actionContains("Enter")
                    .nameContains("Guardian of Water")
                    .findClosest()
                    .map(o -> o.interact("Enter")).orElse(false))
                Waiting.waitUntil(8000, 500, () ->
                        Query.gameObjects().actionContains("Craft-rune").isAny());

            //make runes
            if (Inventory.getCount("Guardian essence") == 5 &&
                    Inventory.getCount("Uncharged cell") == 1 &&
                    Query.gameObjects().actionContains("Craft-rune").findClosest()
                            .map(o -> o.interact("Craft-rune")).orElse(false))
                Waiting.waitUntil(8000, 500, () ->
                        Inventory.getCount("Portal talisman") == 1);

            //leave water rune area
            if (Query.gameObjects().actionContains("Craft-rune").isAny() &&
                    Inventory.getCount("Portal talisman (water)") == 1 &&
                    Query.gameObjects().actionContains("Use")
                            .nameContains("Portal").findClosest()
                            .map(o -> o.interact("Use")).orElse(false))
                Waiting.waitUntil(8000, 500, () ->
                        !Query.gameObjects().actionContains("Craft-rune").isAny());

            //power up
            cQuesterV2.status = "Powering up";
            if (Query.npcs().actionContains("Power-up")
                    .findClosest().map(n -> n.interact("Power-up")).orElse(false))
                Waiting.waitUntil(5000, 500, () -> GameState.getVarbit(tutorialSetting) == 85);
        }
        if (GameState.getVarbit(tutorialSetting) == 85) {
            cQuesterV2.status = "Talking to Tamara";
            if (Query.npcs().nameContains("Apprentice Tamara").findClosest()
                    .map(n -> n.interact("Talk-to")).orElse(false))
                Waiting.waitUntil(5000, 500, () -> ChatScreen.isOpen());
            NpcChat.handle();
        }
        if (GameState.getVarbit(tutorialSetting) == 90) {
            cQuesterV2.status = "Assembling Guardian";
            if (Query.gameObjects().actionContains("Assemble").findClosest()
                    .map(o -> o.interact("Assemble")).orElse(false))
                Waiting.waitUntil(8000, 500, () -> GameState.getVarbit(tutorialSetting) == 95);
        }
        if (GameState.getVarbit(tutorialSetting) == 95) {
            cQuesterV2.status = "Talking to Tamara";
            if (Query.npcs().nameContains("Apprentice Tamara").findClosest()
                    .map(n -> n.interact("Talk-to")).orElse(false))
                Waiting.waitUntil(5000, 500, () -> ChatScreen.isOpen());
            NpcChat.handle();
        }
        if (GameState.getVarbit(tutorialSetting) == 100) {
            cQuesterV2.status = "Getting fragments";
            if (Inventory.getCount("Guardian fragments") < 5 && Inventory.getCount("Guardian essence") < 5 &&
                    Query.gameObjects().actionContains("Mine").findClosest()
                            .map(o -> o.interact("Mine")).orElse(false))
                Waiting.waitUntil(25000, 500, () -> Inventory.getCount("Guardian fragments") == 5);

            cQuesterV2.status = "Getting essence";
            if (Inventory.getCount("Guardian fragments") == 5 && Inventory.getCount("Guardian essence") < 5 &&
                    Query.gameObjects().actionContains("Work-at").findClosest()
                            .map(o -> o.interact("Work-at")).orElse(false))
                Waiting.waitUntil(25000, 500, () -> Inventory.getCount("Guardian essence") == 5);

            cQuesterV2.status = "Getting cell";
            if (Inventory.getCount("Guardian essence") == 5 &&
                    Inventory.getCount("Uncharged cell") == 0 &&
                    Query.gameObjects().actionContains("Take")
                            .nameContains("Uncharged").findClosest()
                            .map(o -> o.interact("Take")).orElse(false))
                Waiting.waitUntil(8000, 500, () -> Inventory.getCount("Uncharged cell") == 1);

            //enter water portal
            cQuesterV2.status = "Using cell on guardian";
            if (Inventory.getCount("Guardian essence") == 5
                    && !Query.gameObjects().actionContains("Craft-rune").isAny()
                    && Inventory.getCount("Uncharged cell") == 1) {
                Optional<InventoryItem> tal = Query.inventory().nameContains("Portal talisman").findClosestToMouse();
                if (Query.gameObjects()
                        .nameContains("Guardian of Water").findClosest()
                        .map(o -> tal.map(t -> t.useOn(o)).orElse(false)).orElse(false))
                    Waiting.waitUntil(8000, 800, () -> Query.gameObjects().actionContains("Craft-rune").isAny());
            }
            //make runes
            if (Inventory.getCount("Guardian essence") == 5 &&
                    Inventory.getCount("Uncharged cell") == 1 &&
                    Query.gameObjects().actionContains("Craft-rune").findClosest()
                            .map(o -> o.interact("Craft-rune")).orElse(false))
                Waiting.waitUntil(8000, 500, () -> Inventory.getCount("Portal talisman") == 1);

            //leave water rune area
            if (Inventory.getCount("Medium cell") == 1
                    && Query.gameObjects().actionContains("Craft-rune").isAny()
                    && Query.gameObjects().actionContains("Use").nameContains("Portal").findClosest()
                    .map(o -> o.interact("Use")).orElse(false))
                Waiting.waitUntil(8000, 500, () -> !Query.gameObjects().actionContains("Craft-rune").isAny());

            cQuesterV2.status = "Talking to Tamara";
            if (Inventory.getCount("Medium cell") == 1 &&
                    Query.npcs().nameContains("Apprentice Tamara").findClosest()
                            .map(n -> n.interact("Talk-to")).orElse(false))
                Waiting.waitUntil(5000, 500, () -> ChatScreen.isOpen());
            NpcChat.handle();

            //place cell
            if (Inventory.getCount("Medium cell") == 1
                    && Query.gameObjects().actionContains("Place-cell").isAny()
                    && Query.gameObjects().actionContains("Place-cell")
                    .nameContains("Weak cell").findClosest()
                    .map(o -> o.interact("Place-cell")).orElse(false))
                Waiting.waitUntil(8000, 500, () -> GameState.getVarbit(tutorialSetting) == 105);

        }
        if (GameState.getVarbit(tutorialSetting) == 105) {
            cQuesterV2.status = "Talking to Tamara";
            if (Query.npcs().nameContains("Apprentice Tamara").findClosest()
                    .map(n -> n.interact("Talk-to")).orElse(false))
                Waiting.waitUntil(5000, 500, () -> ChatScreen.isOpen());
            NpcChat.handle();
        }
        if (GameState.getVarbit(tutorialSetting) == 110) {
            //power up
            cQuesterV2.status = "Powering up";
            if (Query.npcs().actionContains("Power-up").nameContains("The Great")
                    .findClosest().map(n -> n.interact("Power-up")).orElse(false))
                Waiting.waitUntil(5000, 500, () -> Utils.inCutScene());
            Utils.cutScene();
            Waiting.waitUntil(5000, 500, () -> !GameState.isInInstance());
        }
    }

    public Map<Integer, QuestStep> loadSteps() {
        setupItemRequirements();
        setupAreas();
        setupConditions();
        setupSteps();
        Map<Integer, QuestStep> steps = new HashMap<>();

        steps.put(0, talkToPersten1);
        steps.put(5, finishTalkToPersten1);
        steps.put(10, talkToMage1);

        ConditionalStep fetchHerbertsTea = new ConditionalStep(getTeaForMage);
        fetchHerbertsTea.addStep(new Conditions(strongTea, bucketOfWater, eyeAmulet), talkToMage2);
        steps.put(15, fetchHerbertsTea);

        steps.put(20, finishTalkToMage2);

        ConditionalStep teleportAbyss = new ConditionalStep(talkToMageInWildy);
        teleportAbyss.addStep(inAbyss, talkToDarkMage1);
        teleportAbyss.addStep(canTeleportFromHerbert, teleportViaHerbert);
        // teleportAbyss.addRequirement(bucketOfWater);
        steps.put(25, teleportAbyss);

        ConditionalStep goTalkToDarkMage1 = new ConditionalStep(talkToMageInWildy);
        goTalkToDarkMage1.addStep(inAbyss, finishTalkToDarkMage1);
        steps.put(30, goTalkToDarkMage1);

        ConditionalStep goTouchRunes = new ConditionalStep(talkToMageInWildy);
        goTouchRunes.addStep(inAbyss, touchRunes);
        steps.put(35, goTouchRunes);

        ConditionalStep goTalkToDarkMage2 = new ConditionalStep(talkToMageInWildy);
        goTalkToDarkMage2.addStep(inAbyss, talkToDarkMage2);
        steps.put(40, goTalkToDarkMage2);

        steps.put(45, talkToPersten2);
        steps.put(50, talkToPersten2);
        steps.put(55, finishTalkToPersten2);

        ConditionalStep goTalkToArchmage = new ConditionalStep(talktoArchmage1);
        // goTalkToArchmage.addStep(canTeleportFromPersten, teleportToArchmage);
        // goTalkToArchmage.addStep(inWizardBasement, talktoArchmage1);
        // goTalkToArchmage.addRequirement(abyssalIncantation);
        steps.put(60, goTalkToArchmage);

        steps.put(65, finishTalkingToArchmage1);

        steps.put(70, talktoTrailborn1);

        ConditionalStep solveTraibornsPuzzle = new ConditionalStep(goUpToTraiborn);
        solveTraibornsPuzzle.addStep(new Conditions(felixPuzzleNotSeen), talkToFelix);
        solveTraibornsPuzzle.addStep(new Conditions(tamaraPuzzleNotSeen), talkToTamara);
        solveTraibornsPuzzle.addStep(new Conditions(cordeliaPuzzleNotSeen), talkToCordelia);
        steps.put(75, solveTraibornsPuzzle);

        ConditionalStep goTalkToTraiborn2 = new ConditionalStep(talktoTrailborn2);
        //  goTalkToTraiborn2.addStep(inWizardBasement, goUpToTraibornBasement);
        //  goTalkToTraiborn2.addStep(inWizardFloorOne, talktoTrailborn2);
        steps.put(80, goTalkToTraiborn2);

        ConditionalStep goTalkToArchmage2 = new ConditionalStep(talktoArchmage2);
        //   goTalkToArchmage2.addStep(inWizardFloorOne, goDownToArchmageFloorOne);
        // goTalkToArchmage2.addStep(inWizardBasement, talktoArchmage2);
        steps.put(85, goTalkToArchmage2);

        ConditionalStep goBeginIncantation = new ConditionalStep(goDownToArchmage2);
        goBeginIncantation.addStep(inWizardBasement, performIncantation);
        steps.put(90, goBeginIncantation);

        steps.put(95, templeCutscene1);

        ConditionalStep investigateTemple = new ConditionalStep(talkToFelix2, new Conditions(felixRiftTalk));
        //    investigateTemple.addStep(inWizardBasement, enterPortal);
        //   investigateTemple.addStep(, talkToFelix2);
        investigateTemple.addStep(new Conditions(tamaraRiftTalk), talkToTamara2);
        investigateTemple.addStep(new Conditions(cordeliaRiftTalk), talkToCordelia2);
        steps.put(100, investigateTemple);

        ConditionalStep goTalkToPerstenTemple = new ConditionalStep(talkToPersten3);
        //  goTalkToPerstenTemple.addStep(inWizardBasement, enterPortal);
        //goTalkToPerstenTemple.addStep( talkToPersten3);
        goTalkToPerstenTemple.addStep(mysteriousVisionSeen, templeCutscene2);
        steps.put(105, goTalkToPerstenTemple);

        ConditionalStep debriefAfterVision = new ConditionalStep(debrief);
        //   debriefAfterVision.addStep(inWizardBasement, enterPortal);
        //debriefAfterVision.addStep( debrief);
        steps.put(110, debriefAfterVision);

        ConditionalStep doGuardiansTutorial = new ConditionalStep(guardiansTutorial);
        // doGuardiansTutorial.addStep(inWizardBasement, enterPortal);
        //doGuardiansTutorial.addStep(inTempleOfTheEyeTutorial, guardiansTutorial);
        steps.put(115, doGuardiansTutorial);    // At this stage, the player is handheld by in-game tutorial.

        ConditionalStep finishGuardiansTutorial = new ConditionalStep(enterWizardBasement);
        finishGuardiansTutorial.addStep(inWizardBasement, enterPortal);
        finishGuardiansTutorial.addStep(inTempleOfTheEyeTutorial, templeCutscene3);
        steps.put(120, finishGuardiansTutorial);

        ConditionalStep doFinishQuest = new ConditionalStep(enterWizardBasement);
        doFinishQuest.addStep(inWizardBasement, finishQuest);
        steps.put(125, doFinishQuest);

        return steps;
    }

    public void setupItemRequirements() {
        bucketOfWater = new ItemRequirement("Bucket of water", ItemID.BUCKET_OF_WATER);
        chisel = new ItemRequirement("Chisel", ItemID.CHISEL);
        //chisel.canBeObtainedDuringQuest();
        pickaxe = new ItemRequirement("Pickaxe", ItemCollections.getPickaxes());
        // pickaxe.canBeObtainedDuringQuest();

        varrockTeleport = new ItemRequirement("Method of teleportation to Varrock", ItemID.VARROCK_TELEPORT);
        alKharidTeleport = new ItemRequirement("Method of teleportation to Al Kharid", ItemCollections.getRingOfDuelings());
        alKharidTeleport.addAlternateItemID(ItemCollections.getAmuletOfGlories());
        alKharidTeleport.addAlternateItemID(ItemID.LUMBRIDGE_TELEPORT);

        strongTea = new ItemRequirement("Strong Cup of Tea", ItemID.STRONG_CUP_OF_TEA);
        eyeAmulet = new ItemRequirement("Eye Amulet", ItemID.EYE_AMULET);
        //eyeAmulet.setTooltip("You can get another from Wizard Persten if you lost it");
        abyssalIncantation = new ItemRequirement("Abyssal Incantation", ItemID.ABYSSAL_INCANTATION);
        //abyssalIncantation.setTooltip("You can get another from the Dark Mage in the Abyss if you lost it. If already" +
        //        " shown to Wizard Persten, you can get another from her instead");

    }

    public void setupConditions() {
        inAbyss = new AreaRequirement(abyss);
        inWizardBasement = new AreaRequirement(wizardBasement);
        inWizardFloorOne = new AreaRequirement(wizardFloorOne);
        //inTempleOfTheEye = new AreaRequirement(templeOfTheEye);
        inTempleOfTheEyeTutorial = new AreaRequirement(templeOfTheEye2);

        canTeleportFromHerbert = new VarbitRequirement(13740, 0);
        thrownBucket = new VarbitRequirement(QuestVarbits.QUEST_TEMPLE_OF_THE_EYE.getId(), 30, Operation.GREATER_EQUAL);
        givenAmuletBack = new VarbitRequirement(QuestVarbits.QUEST_TEMPLE_OF_THE_EYE.getId(), 55, Operation.GREATER_EQUAL);
        canTeleportFromPersten = new VarbitRequirement(13753, 0);

        felixPuzzleNotSeen = new VarbitRequirement(13743, 0);
        tamaraPuzzleNotSeen = new VarbitRequirement(13742, 0);
        cordeliaPuzzleNotSeen = new VarbitRequirement(13744, 0);

        felixRiftTalk = new VarbitRequirement(13755, 0);
        tamaraRiftTalk = new VarbitRequirement(13754, 0);
        cordeliaRiftTalk = new VarbitRequirement(13756, 0);

        mysteriousVisionSeen = new VarbitRequirement(12139, 1);
    }

    public void setupAreas() {
        abyss = new RSArea(new RSTile(3010, 4803, 0), new RSTile(3070, 4862, 0));
        wizardBasement = new RSArea(new RSTile(3094, 9553, 0), new RSTile(3125, 9582, 0));
        wizardFloorOne = new RSArea(new RSTile(3101, 3153, 1), new RSTile(3116, 3167, 1));
        templeOfTheEye = new RSArea(new RSTile(2370, 5627, 0), new RSTile(2425, 5682, 0));
        templeOfTheEye2 = new RSArea(new RSTile(2433, 5698, 0), new RSTile(3648, 9523, 0));
    }

    public void setupSteps() {
        talkToPersten1 = new NPCStep("Wizard Persten", new RSTile(3285, 3232, 0));
        talkToPersten1.addDialogStep("What's a wizard doing in Al Kharid?");
        talkToPersten1.addDialogStep("Yes.");

        finishTalkToPersten1 = new NPCStep("Wizard Persten", new RSTile(3285, 3232, 0));

        talkToPersten1.addSubSteps(finishTalkToPersten1);

        // Note: You also can't be wearing any other god's equipment when talking to him
        talkToMage1 = new NPCStep("Mage of Zamorak", new RSTile(3258, 3383, 0),
                eyeAmulet);
        talkToMage1.addDialogStep("I need your help with an amulet.");

        getTeaForMage = new NPCStep("Tea Seller", new RSTile(3271, 3411, 0));
        getTeaForMage.addDialogStep("Could I have a strong cup of tea?");

        talkToMage2 = new NPCStep("Mage of Zamorak", new RSTile(3258, 3383, 0),
                strongTea, bucketOfWater, eyeAmulet);
        talkToMage2.addDialogStep("Could you help me with that amulet now?");
        talkToMage2.addDialogStep("Yes.");

        finishTalkToMage2 = new NPCStep("Mage of Zamorak", new RSTile(3258, 3383, 0), eyeAmulet);
        talkToMage2.addDialogStep("Could you help me with that amulet now?");

        talkToMage2.addSubSteps(finishTalkToMage2);

        teleportViaHerbert = new NPCStep("Mage of Zamorak", new RSTile(3258, 3383, 0), eyeAmulet);
        teleportViaHerbert.addDialogStep("Could you help me with that amulet now?");
        teleportViaHerbert.addDialogStep("Yes.");

        talkToMageInWildy = new NPCStep("Mage of Zamorak", new RSTile(3102, 3557, 0), eyeAmulet);
        talkToMageInWildy.setInteractionString("Teleport");
        talkToMageInWildy.addDialogStep("Could you teleport me to the Abyss?");

        talkToDarkMage1 = new NPCStep("Dark Mage", new RSTile(3039, 4834, 0), eyeAmulet);
        talkToDarkMage1.addDialogStep("I need your help with an amulet.");

        finishTalkToDarkMage1 = new NPCStep("Dark Mage", new RSTile(3039, 4834, 0),
                eyeAmulet);

        talkToDarkMage1.addSubSteps(talkToMageInWildy, teleportViaHerbert, finishTalkToDarkMage1);

        touchRunes = new ObjectStep(43768, "Touch");
        touchRunes.addAlternateObjects(43769, 43770, 43771, 43772, 43773);

        talkToDarkMage2 = new NPCStep("Dark Mage", new RSTile(3039, 4834, 0), eyeAmulet);

        talkToPersten2 = new NPCStep("Wizard Persten", new RSTile(3285, 3232, 0),
                abyssalIncantation);
        talkToPersten2.addDialogStep("About that incantation...");

        finishTalkToPersten2 = new NPCStep("Wizard Persten", new RSTile(3285, 3232, 0),
                abyssalIncantation);

        talkToPersten2.addSubSteps(finishTalkToPersten2);

        teleportToArchmage = new NPCStep("Wizard Persten", new RSTile(3285, 3232, 0));
        teleportToArchmage.addDialogStep("Yes.");

        goDownToArchmage = new ObjectStep(ObjectID.LADDER_2147, new RSTile(3104, 3162, 0),
                "Climb-down", abyssalIncantation);

        talktoArchmage1 = new NPCStep("Archmage Sedridor", new RSTile(3104, 9571, 0), abyssalIncantation);
        talktoArchmage1.addDialogStep("I need your help with an incantation.");

        finishTalkingToArchmage1 = new NPCStep(NpcID.ARCHMAGE_SEDRIDOR_11433, new RSTile(3104, 9571, 0),
                "Finish listening to Sedridor.");
        finishTalkingToArchmage1.addDialogStep("I need your help with an incantation.",
                "Can you help me with that incantation?");

        //talktoArchmage1.addSubSteps(teleportToArchmage, goDownToArchmage, finishTalkingToArchmage1);

        goUpToTraibornBasement = new ObjectStep(ObjectID.LADDER_2148, new RSTile(3103, 9576, 0),
                "Speak to Wizard Traiborn on the Wizards' Tower 1st floor.");

        goUpToTraiborn = new ObjectStep(ObjectID.STAIRCASE_12536, new RSTile(3103, 3159, 0),
                "Speak to Wizard Traiborn on the Wizards' Tower 1st floor.");

        talktoTrailborn1 = new NPCStep("Wizard Traiborn", new RSTile(3112, 3162, 1));
        talktoTrailborn1.addDialogStep("I need your apprentices to help with an incantation.");

        //talktoTrailborn1.addSubSteps(goUpToTraibornBasement, goUpToTraiborn);

        talkToFelix = new NPCStep("Apprentice Felix", new RSTile(3112, 3162, 1));
        talkToTamara = new NPCStep("Apprentice Tamara", new RSTile(3112, 3162, 1));
        talkToCordelia = new NPCStep("Apprentice Cordelia", new RSTile(3112, 3162, 1));

        //TODO handle chat, answer is 11
        talktoTrailborn2 = new NPCStep("Wizard Traiborn", new RSTile(3112, 3162, 1));
        talktoTrailborn2.addDialogStep("I think I know what a thingummywut is!");


        goDownToArchmageFloorOne = new ObjectStep(ObjectID.STAIRCASE_12537, new RSTile(3103, 3159, 1),
                "Climb-down");
        goDownToArchmageFloorOne.addDialogStep("Climb-down");

        goDownToArchmage2 = new ObjectStep(ObjectID.LADDER_2147, new RSTile(3104, 3162, 0),
                "Climb-down");

        talktoArchmage2 = new NPCStep("Archmage Sedridor", new RSTile(3104, 9571, 0));

        //talktoArchmage2.addSubSteps(goDownToArchmageFloorOne, goDownToArchmage2);

        performIncantation = new NPCStep("Archmage Sedridor", new RSTile(3104, 9571, 0));
        performIncantation.addDialogStep("Let's do it.");
        performIncantation.addDialogStep("So we're ready to perform the incantation?");

        enterWizardBasement = new ObjectStep(ObjectID.LADDER_2147, new RSTile(3104, 3162, 0),
                "Climb-down");

        enterPortal = new ObjectStep(43765, new RSTile(3104, 9574, 0),
                "Enter");

        templeCutscene1 = new DetailedQuestStep("Enter the Temple of the Eye.");
        templeCutscene1.addSubSteps(enterPortal);

        talkToFelix2 = new NPCStep("Apprentice Felix");
        talkToTamara2 = new NPCStep("Apprentice Tamara");
        talkToCordelia2 = new NPCStep("Apprentice Cordelia");
        talkToPersten3 = new NPCStep("Wizard Persten");

        templeCutscene2 = new DetailedQuestStep("Experience the vision.");
        debrief = new DetailedQuestStep("Debrief with Wizard Persten and the apprentices.");

        templeCutscene3 = new DetailedQuestStep("Listen to the Great Guardian.");

        //guardiansTutorial.addSubSteps(templeCutscene3);

        finishQuest = new NPCStep(NpcID.ARCHMAGE_SEDRIDOR_11433, new RSTile(3104, 9571, 0),
                "Speak to Archmage Sedridor in the Wizard's Tower basement to finish the quest.");

    }


    public List<ItemRequirement> getItemRequirements() {
        ArrayList<ItemRequirement> reqs = new ArrayList<>();
        reqs.add(bucketOfWater);
        reqs.add(chisel);
        reqs.add(pickaxe);
        return reqs;
    }


    public List<ItemRequirement> getItemRecommended() {
        ArrayList<ItemRequirement> reqs = new ArrayList<>();
        reqs.add(varrockTeleport);
        reqs.add(alKharidTeleport);
        return reqs;
    }

    @Override
    public List<Requirement> getGeneralRequirements() {
        ArrayList<Requirement> req = new ArrayList<>();
        //  req.add(new QuestRequirement(QuestHelperQuest.ENTER_THE_ABYSS, QuestState.FINISHED));
        req.add(new SkillRequirement(Skills.SKILLS.RUNECRAFTING, 10));
        return req;
    }
}
