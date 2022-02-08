package scripts.QuestPackages.TearsOfGuthix;

import dax.shared.helpers.questing.Quest;
import dax.walker_engine.interaction_handling.NPCInteraction;
import org.tribot.api2007.Player;
import org.tribot.api2007.Skills;
import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSTile;
import org.tribot.script.sdk.GameState;
import org.tribot.script.sdk.Inventory;
import org.tribot.script.sdk.Waiting;
import scripts.*;
import scripts.GEManager.GEItem;
import scripts.QuestPackages.TouristTrap.TouristTrap;
import scripts.QuestSteps.*;
import scripts.Requirements.*;
import scripts.Requirements.Util.ConditionalStep;
import scripts.Requirements.Util.Conditions;
import scripts.Tasks.Priority;

import java.util.*;

public class TearsOfGuthix implements QuestTask {

    private static TearsOfGuthix quest;

    public static TearsOfGuthix get() {
        return quest == null ? quest = new TearsOfGuthix() : quest;
    }

    //Items Required
    ItemRequirement litSapphireLantern, chisel, tinderbox, pickaxe, rope, litSapphireLanternHighlighted,
            ropeHighlighted, tinderboxHighlighted, pickaxeHighlighted, chiselHighlighted, rockHighlighted, stoneBowl;

    UseItemOnNpcStep useLanternOnLightCreature;
    UseItemOnObjectStep addRope;

    RSArea swamp = new RSArea(new RSTile(3138, 9536, 0), new RSTile(3261, 9601, 0)),
            TEARS_CAVE = new RSArea(new RSTile(3212, 9532, 2), new RSTile(3262, 9489, 2)),
            TEARS_UPPER_AREA =
                    new RSArea(new RSTile(3212, 9532, 2), new RSTile(3239, 9524, 2));

    RSArea junaRoom = new RSArea(new RSTile(3205, 9484, 0), new RSTile(3263, 9537, 2));
    RSArea rocks = new RSArea(new RSTile(3209, 9486, 2), new RSTile(3238, 9508, 2));

    AreaRequirement inSwamp = new AreaRequirement(swamp);
    AreaRequirement upperTearsCave = new AreaRequirement(TEARS_UPPER_AREA);
    AreaRequirement inTearsCave = new AreaRequirement(TEARS_CAVE, TEARS_UPPER_AREA, junaRoom);
    AreaRequirement inJunaRoom = new AreaRequirement(junaRoom);
    AreaRequirement atRocks = new AreaRequirement(rocks);

    VarbitRequirement addedRope = new VarbitRequirement(279, 1);

    // 452 = 1, gone through Juna's first dialog
    QuestStep enterSwamp, enterJunaRoom, talkToJuna, mineRock, useChiselOnRock,
            talkToJunaToFinish;


    public Map<Integer, QuestStep> loadSteps() {
        Map<Integer, QuestStep> steps = new HashMap<>();
        setupItemRequirements();
        setupSteps();

        ConditionalStep goTalkToJuna = new ConditionalStep(talkToJuna);
        goTalkToJuna.addStep(inJunaRoom, talkToJuna);
        steps.put(0, goTalkToJuna);

        ConditionalStep goGetRock = new ConditionalStep(enterJunaRoom);
        goGetRock.addStep(new Conditions(stoneBowl, inJunaRoom), talkToJunaToFinish);
        goGetRock.addStep(rockHighlighted, useChiselOnRock);
        goGetRock.addStep(atRocks, mineRock);
        goGetRock.addStep(inJunaRoom, useLanternOnLightCreature);
        steps.put(1, goGetRock);

        return steps;
    }

    ArrayList<GEItem> itemsToBuy = new ArrayList<GEItem>(
            Arrays.asList(
                    new GEItem(ItemID.TINDERBOX, 1, 500),
                    new GEItem(ItemID.CHISEL, 1, 500),
                    new GEItem(ItemID.STEEL_PICKAXE, 1, 500),
                    new GEItem(ItemID.STAMINA_POTION[0], 2, 15),
                    new GEItem(ItemID.AMULET_OF_GLORY4, 1, 30),
                    new GEItem(ItemID.ROPE, 1, 300),
                    new GEItem(ItemID.SAPPHIRE, 1, 50),
                    new GEItem(ItemID.BULLSEYE_LANTERN, 1, 500)
            )
    );

    InventoryRequirement initialItemReqs = new InventoryRequirement(new ArrayList<>(
            Arrays.asList(
                    new ItemReq(ItemID.TINDERBOX, 1, 1),
                    new ItemReq(ItemID.CHISEL, 1, 1),
                    new ItemReq(ItemID.STEEL_PICKAXE, 1, 1),
                    new ItemReq(ItemID.ROPE, 1, 1),
                    new ItemReq(ItemID.SAPPHIRE, 1, 0),
                    new ItemReq(ItemID.BULLSEYE_LANTERN, 1, 0),
                    new ItemReq(ItemID.AMULET_OF_GLORY4, 1, 0, true, true),
                    new ItemReq(ItemID.STAMINA_POTION[0], 2, 0),
                    new ItemReq(ItemID.RING_OF_WEALTH[0], 1, 0, true)
            )
    ));


    public UseItemOnItemStep combineLantern = new UseItemOnItemStep(ItemID.SAPPHIRE, ItemID.BULLSEYE_LANTERN,
            Inventory.contains(ItemID.SAPPHIRE_LANTERN_4701));

    public UseItemOnItemStep lightLantern = new UseItemOnItemStep(ItemID.TINDERBOX, ItemID.SAPPHIRE_LANTERN_4701,
            Inventory.contains(ItemID.SAPPHIRE_LANTERN_4702));
    BuyItemsStep buyStep = new BuyItemsStep(itemsToBuy);
    private RSArea WALK_TO_STEPPING_STONE = new RSArea(new RSTile(3220, 9556, 0), new RSTile(3222, 9558, 0)),
            ENTRANCE_TO_TEARS_CAVE = new RSArea(new RSTile(3219, 9552, 0), new RSTile(3236, 9542, 0));

    ObjectStep jumpAcrossStone = new ObjectStep(ObjectID.STEPPING_STONE_5949, new RSTile(3221, 9557,0),
            "Jump-Across", ENTRANCE_TO_TEARS_CAVE.contains(Player.getPosition()));
    RSArea wholeCaveArea = new RSArea(new RSTile(3204, 9536, 2), new RSTile(3266, 9485, 2));
    ObjectStep climbRocks = new ObjectStep(6673, new RSTile(3238, 9525,2),
            "Climb");
    ObjectStep climbUpRocks = new ObjectStep(6673, new RSTile(3238, 9525,2),
            "Climb");

    public void goToJunaRoom() {
        if (!litSapphireLantern.check()) {
            combineLantern.execute();
            lightLantern.execute();
        }
        if (!addedRope.check() && !wholeCaveArea.contains(Player.getPosition())) {
            cQuesterV2.status = "Adding rope";
            addRope.execute();
        }
        if (!inSwamp.check() && !wholeCaveArea.contains(Player.getPosition()) && !atRocks.check()) {
            //enter cave
            cQuesterV2.status = "Entering Swamp";
            enterSwamp.execute();
        }
        if (inSwamp.check() && !wholeCaveArea.contains(Player.getPosition())) {
            cQuesterV2.status = "Entering Juna room";
            if (!ENTRANCE_TO_TEARS_CAVE.contains(Player.getPosition()) &&
                    PathingUtil.walkToArea(WALK_TO_STEPPING_STONE)) {
                PathingUtil.movementIdle();
                jumpAcrossStone.execute();
            }
            enterJunaRoom.execute();
        }
        if (TEARS_UPPER_AREA.contains(Player.getPosition())){
            cQuesterV2.status = "Climbing rocks";
            climbRocks.execute();
        }
     //   if (inJunaRoom.check() && inTearsCave.check()) {
     //       cQuesterV2.status = "Talking to Juna";
     //       talkToJuna.execute();
     //   }

    }




    private void setupItemRequirements() {
        litSapphireLantern = new ItemRequirement("Sapphire lantern", ItemID.SAPPHIRE_LANTERN_4702);
        // litSapphireLantern.setTooltip("You can make this by using a cut sapphire on a bullseye lantern");
        chisel = new ItemRequirement("Chisel", ItemID.CHISEL);
        tinderbox = new ItemRequirement("Tinderbox", ItemID.TINDERBOX);
        pickaxe = new ItemRequirement("Any pickaxe", ItemID.STEEL_PICKAXE);
        rope = new ItemRequirement("Rope", ItemID.ROPE);


        litSapphireLanternHighlighted = new ItemRequirement("Sapphire lantern", ItemID.SAPPHIRE_LANTERN_4702);
        // litSapphireLanternHighlighted.setTooltip("You can make this by using a cut sapphire on a bullseye lantern");
        chiselHighlighted = new ItemRequirement("Chisel", ItemID.CHISEL);

        tinderboxHighlighted = new ItemRequirement("Tinderbox", ItemID.TINDERBOX);

        pickaxeHighlighted = new ItemRequirement("Any pickaxe", ItemID.STEEL_PICKAXE);

        ropeHighlighted = new ItemRequirement("Rope", ItemID.ROPE);

        rockHighlighted = new ItemRequirement("Magic stone", ItemID.MAGIC_STONE);


        stoneBowl = new ItemRequirement("Stone bowl", ItemID.STONE_BOWL);
    }


    private void setupSteps() {
        useLanternOnLightCreature = new UseItemOnNpcStep(litSapphireLanternHighlighted.getId(),
                5783, new RSTile(3228, 9518, 2), litSapphireLanternHighlighted);
        addRope = new UseItemOnObjectStep(ropeHighlighted.getId(), ObjectID.DARK_HOLE,
                new RSTile(3169, 3172, 0),
                ropeHighlighted, litSapphireLantern, chisel, pickaxe, tinderbox);

        enterSwamp = new ObjectStep(ObjectID.DARK_HOLE, new RSTile(3169, 3172, 0),
                "Climb-down", litSapphireLantern, chisel, pickaxe, tinderbox);
        // enterSwamp.addSubSteps(addRope);

        enterJunaRoom = new ObjectStep(6659, new RSTile(3226, 9540, 0),
                "Enter");
        talkToJuna = new ObjectStep(3193, new RSTile(3252, 9517, 2),
                "Talk-to");
        talkToJuna.addDialogStep("Okay...");

        mineRock = new ObjectStep(6670, new RSTile(3229, 9497, 2),
                "Mine", pickaxe);
        useChiselOnRock = new UseItemOnItemStep(chiselHighlighted.getId(), rockHighlighted.getId(),
                NPCInteraction.isConversationWindowUp(), chiselHighlighted, rockHighlighted);
        talkToJunaToFinish = new ObjectStep(3193, new RSTile(3252, 9517, 2),
                "Talk-to");

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
        setupItemRequirements();
        setupSteps();

        if (!checkRequirements()) {
            cQuesterV2.taskList.remove(this);
            return;
        }
        if (Utils.getVarBitValue(QuestVarbits.QUEST_TEARS_OF_GUTHIX.getId()) == 0 && !initialItemReqs.check()) {
            //    buyStep.buyItems();
            //    initialItemReqs.withdrawItems();
        }
        if (GameState.getSetting(QuestVarbits.QUEST_TEARS_OF_GUTHIX.getId()) == 90) {
            cQuesterV2.taskList.remove(this);
            return;
        }
        goToJunaRoom();
        setupSteps();
        Map<Integer, QuestStep> steps = loadSteps();
        Optional<QuestStep> step =
                Optional.ofNullable(steps.get(Utils.getVarBitValue(QuestVarbits.QUEST_TEARS_OF_GUTHIX.getId())));
        step.ifPresent(QuestStep::execute);
        if (NPCInteraction.isConversationWindowUp())
            NPCInteraction.handleConversation();

        Waiting.waitNormal(500, 50);


    }

    @Override
    public String questName() {
        return "Tears Of Guthix";
    }

    @Override
    public boolean checkRequirements() {
        return Skills.SKILLS.FIREMAKING.getActualLevel() >= 49 &&
                Skills.SKILLS.MINING.getActualLevel() >= 20;
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
