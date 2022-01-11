package scripts.QuestPackages.TearsOfGuthix;

import dax.shared.helpers.questing.Quest;
import dax.walker_engine.interaction_handling.NPCInteraction;
import org.tribot.api2007.Skills;
import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSTile;
import scripts.ItemId;
import scripts.NpcID;
import scripts.ObjectID;
import scripts.QuestPackages.TouristTrap.TouristTrap;
import scripts.QuestSteps.*;
import scripts.Requirements.AreaRequirement;
import scripts.Requirements.ItemRequirement;
import scripts.Requirements.Requirement;
import scripts.Requirements.VarbitRequirement;
import scripts.Tasks.Priority;
import scripts.cQuesterV2;

import java.util.HashMap;
import java.util.Map;

public class TearsOfGuthix implements QuestTask {
    private static TearsOfGuthix quest;

    public static TearsOfGuthix get() {
        return quest == null ? quest = new TearsOfGuthix() : quest;
    }

    //Items Required
    ItemRequirement litSapphireLantern, chisel, tinderbox, pickaxe, rope, litSapphireLanternHighlighted,
            ropeHighlighted, tinderboxHighlighted, pickaxeHighlighted, chiselHighlighted, rockHighlighted, stoneBowl;


    RSArea swamp = new RSArea(new RSTile(3138, 9536, 0), new RSTile(3261, 9601, 0));
    RSArea junaRoom = new RSArea(new RSTile(3205, 9484, 0), new RSTile(3263, 9537, 2));
    RSArea rocks = new RSArea(new RSTile(3209, 9486, 2), new RSTile(3238, 9508, 2));

    AreaRequirement inSwamp = new AreaRequirement(swamp);
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
/*
        ConditionalStep goTalkToJuna = new ConditionalStep(getToJunaRoom);
        goTalkToJuna.addStep(inJunaRoom, talkToJuna);
        steps.put(0, goTalkToJuna);

        ConditionalStep goGetRock = new ConditionalStep(getToJunaRoom);
        goGetRock.addStep(new Conditions(stoneBowl.alsoCheckBank(questBank), inJunaRoom), talkToJunaToFinish);
        goGetRock.addStep(rockHighlighted, useChiselOnRock);
        goGetRock.addStep(atRocks, mineRock);
        goGetRock.addStep(inJunaRoom, useLanternOnLightCreature);
        steps.put(1, goGetRock);
*/
        return steps;
    }

    public void goToJunaRoom() {
        if (!addedRope.check()) {
            cQuesterV2.status = "Adding rope";
            addRope.useItemOnObject();
        }
        if (!inSwamp.check() && !inJunaRoom.check() && !atRocks.check()) {
            //enter cave
            cQuesterV2.status = "Entering Swamp";
            enterSwamp.execute();
        }
        if (inSwamp.check()) {
            cQuesterV2.status = "Entering Juna room";
            enterJunaRoom.execute();
        }
        if (inJunaRoom.check()){
            cQuesterV2.status = "Talking to Juna";
            talkToJuna.execute();
        }
    }


    private void setupItemRequirements() {
        litSapphireLantern = new ItemRequirement("Sapphire lantern", ItemId.SAPPHIRE_LANTERN_4702);
        // litSapphireLantern.setTooltip("You can make this by using a cut sapphire on a bullseye lantern");
        chisel = new ItemRequirement("Chisel", ItemId.CHISEL);
        tinderbox = new ItemRequirement("Tinderbox", ItemId.TINDERBOX);
        pickaxe = new ItemRequirement("Any pickaxe", ItemId.STEEL_PICKAXE);
        rope = new ItemRequirement("Rope", ItemId.ROPE);


        litSapphireLanternHighlighted = new ItemRequirement("Sapphire lantern", ItemId.SAPPHIRE_LANTERN_4702);
        // litSapphireLanternHighlighted.setTooltip("You can make this by using a cut sapphire on a bullseye lantern");
        chiselHighlighted = new ItemRequirement("Chisel", ItemId.CHISEL);

        tinderboxHighlighted = new ItemRequirement("Tinderbox", ItemId.TINDERBOX);

        pickaxeHighlighted = new ItemRequirement("Any pickaxe", ItemId.STEEL_PICKAXE);

        ropeHighlighted = new ItemRequirement("Rope", ItemId.ROPE);

        rockHighlighted = new ItemRequirement("Magic stone", ItemId.MAGIC_STONE);


        stoneBowl = new ItemRequirement("Stone bowl", ItemId.STONE_BOWL);
    }


    UseItemOnNpcStep useLanternOnLightCreature = new UseItemOnNpcStep(litSapphireLanternHighlighted.getId(),
            5783, new RSTile(3228, 9518, 2),
            litSapphireLanternHighlighted);

    UseItemOnObjectStep addRope = new UseItemOnObjectStep(ropeHighlighted.getId(), ObjectID.DARK_HOLE,
            new RSTile(3169, 3172, 0), ropeHighlighted, litSapphireLantern, chisel, pickaxe, tinderbox);

    private void setupSteps() {

        enterSwamp = new ObjectStep(ObjectID.DARK_HOLE, new RSTile(3169, 3172, 0),
                "Enter the hole to Lumbridge swamp.", litSapphireLantern, chisel, pickaxe, tinderbox);
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

        goToJunaRoom();
    }

    @Override
    public String questName() {
        return "Tears Of Guthix";
    }

    @Override
    public boolean checkRequirements() {
        return Skills.SKILLS.FIREMAKING.getActualLevel() >= 49;
    }
}
