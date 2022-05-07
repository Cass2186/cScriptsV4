package scripts.QuestPackages.RumDeal;

import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSTile;
import scripts.ItemID;
import scripts.NpcID;
import scripts.ObjectID;
import scripts.QuestSteps.*;
import scripts.Requirements.AreaRequirement;
import scripts.Requirements.ItemRequirement;
import scripts.Requirements.Requirement;
import scripts.Requirements.Util.ConditionalStep;

import java.util.Arrays;
import java.util.Collection;

public class SlugSteps extends QuestStep {

    QuestStep addSluglings, talkToPete, goDownFromTop, fish5Slugs, goDownToSluglings, goUpFromSluglings, goUpToDropSluglings, goUpF1ToPressure, goUpToF2ToPressure, pressure;
    ConditionalStep getSluglings, pressureSluglings, pullPressureLever;

    RSArea islandF0, islandF1, islandF2;

    Requirement onIslandF0, onIslandF1, onIslandF2;

    ItemRequirement sluglings;
    ItemRequirement sluglingsHighlight;
    ItemRequirement netBowl;

    protected void setupSteps()
    {
        sluglings = new ItemRequirement("Sluglings or Karamthulu", ItemID.SLUGLINGS, 5);
        sluglingsHighlight = new ItemRequirement("Sluglings or Karamthulu", ItemID.SLUGLINGS, 5);
        netBowl = new ItemRequirement("Fishbowl and net", ItemID.FISHBOWL_AND_NET);
        //netBowl.setTooltip("You can get another from Captain Braindeath, or make it with a fishbowl and large net");
        sluglingsHighlight.addAlternateItemID(ItemID.KARAMTHULHU, ItemID.KARAMTHULHU_6717);
        sluglings.addAlternateItemID(ItemID.KARAMTHULHU, ItemID.KARAMTHULHU_6717);

        islandF0 = new RSArea(new RSTile(2110, 5054, 0), new RSTile(2178, 5185, 0));
        islandF1 = new RSArea(new RSTile(2110, 5054, 1), new RSTile(2178, 5185, 1));
        islandF2 = new RSArea(new RSTile(2110, 5054, 2), new RSTile(2178, 5185, 2));
        onIslandF0 = new AreaRequirement(islandF0);
        onIslandF1 = new AreaRequirement(islandF1);
        onIslandF2 = new AreaRequirement(islandF2);

        talkToPete = new NPCStep( NpcID.PIRATE_PETE, new RSTile(3680, 3537, 0), "Talk to Pirate Pete north east of the Ectofuntus.");
        talkToPete.addDialogStep("Okay!");
        addSluglings = new ObjectStep( ObjectID.PRESSURE_BARREL, new RSTile(2142, 5102, 2),
                "Add the sea creatures to the pressure barrel on the top floor.", sluglings);

        goDownFromTop = new ObjectStep( ObjectID.LADDER_10168, new RSTile(2163, 5092, 2), "Go down the ladder and fish for sea creatures.");

        fish5Slugs = new NPCStep(NpcID.FISHING_SPOT,
                new RSTile(2150, 5088, 0), //TODO update this guess
              //  "Fish 5 sluglings or karamthulu from around the coast of the island.",
                netBowl);
        goDownToSluglings = new ObjectStep( ObjectID.WOODEN_STAIR_10137, new RSTile(2150, 5088, 1), "Go fish 5 sluglings.", netBowl);
        goUpFromSluglings = new ObjectStep( ObjectID.WOODEN_STAIR, new RSTile(2150, 5088, 0),
                "Add the sea creatures to the pressure barrel on the top floor.", sluglings);

        fish5Slugs.addSubSteps(goDownFromTop, goDownToSluglings, talkToPete);

        goUpToDropSluglings = new ObjectStep( ObjectID.LADDER_10167, new RSTile(2163, 5092, 1),
                "Add the sea creatures to the pressure barrel on the top floor.", sluglings);

        goUpFromSluglings = new ObjectStep( ObjectID.WOODEN_STAIR, new RSTile(2150, 5088, 0),
                "Go to the top floor to pull the pressure lever.", sluglings);
        goUpF1ToPressure = new ObjectStep( ObjectID.WOODEN_STAIR, new RSTile(2150, 5088, 0),
                "Go to the top floor to pull the pressure lever.");
        goUpToF2ToPressure = new ObjectStep( ObjectID.LADDER_10167, new RSTile(2163, 5092, 1),
                "Go to the top floor to pull the pressure lever.");

        pressure = new ObjectStep(10164, new RSTile(2141, 5103, 2), "Pull the pressure lever.");
        pressure.addSubSteps(goUpToF2ToPressure, goUpF1ToPressure);

        getSluglings = new ConditionalStep( talkToPete);
        getSluglings.addStep(onIslandF0, fish5Slugs);
        getSluglings.addStep(onIslandF1, goDownToSluglings);
        getSluglings.addStep(onIslandF2, goDownToSluglings);

        pressureSluglings = new ConditionalStep( talkToPete);
        pressureSluglings.addStep(onIslandF2, addSluglings);
        pressureSluglings.addStep(onIslandF1, goUpToDropSluglings);
        pressureSluglings.addStep(onIslandF0, goUpFromSluglings);

        pullPressureLever = new ConditionalStep( talkToPete);
        pullPressureLever.addStep(onIslandF2, pressure);
        pullPressureLever.addStep(onIslandF1, goUpToF2ToPressure);
        pullPressureLever.addStep(onIslandF0, goUpF1ToPressure);
    }

    @Override
    public void execute() {

    }

    @Override
    public void addDialogStep(String... dialog) {

    }

    public Collection<QuestStep> getSteps() {
        return Arrays.asList(talkToPete, goDownToSluglings, fish5Slugs, goUpFromSluglings, goUpToDropSluglings, addSluglings,
                getSluglings, pressureSluglings, goUpF1ToPressure, goUpToF2ToPressure, pressure, pullPressureLever);
    }

}
