package scripts.QuestPackages.LunarDiplomacy;

import dax.walker_engine.interaction_handling.NPCInteraction;
import org.tribot.api.General;
import org.tribot.api2007.Game;
import org.tribot.api2007.NPCs;
import org.tribot.api2007.Player;
import org.tribot.api2007.Skills;
import org.tribot.api2007.types.RSNPC;
import org.tribot.api2007.types.RSObject;
import org.tribot.script.sdk.*;
import org.tribot.script.sdk.query.Query;
import org.tribot.script.sdk.types.InventoryItem;
import org.tribot.script.sdk.types.LocalTile;
import scripts.EntitySelector.Entities;
import scripts.EntitySelector.finders.prefabs.ObjectEntity;
import scripts.NpcID;
import scripts.PathingUtil;
import scripts.QuestSteps.NPCStep;
import scripts.QuestSteps.QuestTask;
import scripts.Requirements.ItemRequirement;
import scripts.Requirements.Requirement;
import scripts.Requirements.Util.Operation;
import scripts.Requirements.VarbitRequirement;
import scripts.Tasks.Priority;
import scripts.Tasks.Task;
import scripts.Timer;
import scripts.Utils;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class RaceChallenge implements QuestTask {


    //TODO add summer pie support if <40 agility as I kept failing with 41

    int[] xyzTranslateCoordinates = {11, 1, 0};

    // these are the .translate() coordinates for the launch pad from the centre "My life" piece
    public static LocalTile getRaceLaunchPadTile(LocalTile centreTile) {
        return centreTile.translate(11, 1, 0);
    }

    VarbitRequirement startedRaceChallenge = new VarbitRequirement(2424, 1);
    VarbitRequirement finishedRace = new VarbitRequirement(2409, 1,Operation.GREATER_EQUAL);

    NPCStep startRace = new NPCStep(NpcID.ETHEREAL_EXPERT, Player.getPosition(),
            new String[]{"Ok."});

    int JUMP_ANIMATION = 4381;

    public void eatSummerPie() {
        Optional<InventoryItem> food = Query.inventory().nameContains("summer pie")
                .findFirst();
        if (Skills.SKILLS.AGILITY.getCurrentLevel() <= Skills.SKILLS.AGILITY.getActualLevel() + 2)
            food.ifPresent(inventoryItem -> inventoryItem.click("Eat"));
    }

    public void navigateRace() {
        eatSummerPie();

        if (NPCInteraction.isConversationWindowUp())
            NPCInteraction.handleConversation("Eat my dust!", "Ok.");

        RSObject[] hurdles = Entities.find(ObjectEntity::new)
                .nameContains("Hurdle")
                .sortByDistance()
                .getResults();

        for (RSObject h : hurdles) {
            if (Player.getAnimation() == JUMP_ANIMATION)
                return;

            if (Utils.getVarBitValue(2424) == 0) //race ended
                return;

            General.println("[Debug]: in loop");

            if (h.getPosition().getY() > Player.getPosition().getY()) {
                if (Utils.clickObject(h, "Jump-over")) {
                    if (Timer.waitCondition(() -> Player.isMoving(), 800, 1400)) {
                        Timer.waitCondition(() -> Player.getPosition().equals(h
                                .getPosition().translate(0, 1)), 3000, 4000);

                    }
                    break;
                }
            }
        }
        RSNPC[] mimic = NPCs.findNearest(NpcID.ETHEREAL_EXPERT);
        if (!NPCInteraction.isConversationWindowUp() &&
                // we are past all the hurdles
                Arrays.stream(hurdles).filter(
                                h -> h.getPosition().getY() > MyPlayer.getPosition().getY())
                        .count() == 0) {
            Log.debug("Running to end!");
            if (PathingUtil.clickScreenWalk(Player.getPosition().translate(0, 5)))
                Waiting.waitUntil(3500, ()-> PathingUtil.movementIdle() && ChatScreen.isOpen());
        }
    }

    @Override
    public String toString() {
        return "Doing race challenge";
    }

    @Override
    public Priority priority() {
        return Priority.MEDIUM;
    }

    @Override
    public boolean validate() {
        RSNPC[] mimic = NPCs.findNearest(NpcID.ETHEREAL_EXPERT);

        return Game.isInInstance() &&
                !finishedRace.check() &&
                Query.npcs().idEquals(NpcID.ETHEREAL_EXPERT).isReachable().isAny();//&& PathingUtil.canReach(mimic[0].getPosition());
    }

    @Override
    public void execute() {
        if (Utils.getVarBitValue(2424) == 0) {
            startRace.execute();
        } else if (Utils.getVarBitValue(2424) == 1) {
            navigateRace();
        }

    }

    @Override
    public String questName() {
        return null;
    }

    @Override
    public boolean checkRequirements() {
        return false;
    }

    @Override
    public List<Requirement> getGeneralRequirements() {
        return null;
    }

    @Override
    public List<ItemRequirement> getBuyList() {
        return null;
    }

    @Override
    public boolean isComplete() {
        return Quest.LUNAR_DIPLOMACY.getState().equals(Quest.State.COMPLETE);
    }
}
