package scripts.QuestPackages.LunarDiplomacy;

import dax.walker_engine.interaction_handling.NPCInteraction;
import org.tribot.api.General;
import org.tribot.api2007.Game;
import org.tribot.api2007.NPCs;
import org.tribot.api2007.Player;
import org.tribot.api2007.types.RSNPC;
import org.tribot.api2007.types.RSTile;
import org.tribot.script.sdk.Log;
import org.tribot.script.sdk.MyPlayer;
import org.tribot.script.sdk.Quest;
import org.tribot.script.sdk.query.Query;
import org.tribot.script.sdk.types.LocalTile;
import org.tribot.script.sdk.types.Npc;
import scripts.NpcID;
import scripts.PathingUtil;
import scripts.QuestSteps.EmoteStep;
import scripts.QuestSteps.NPCStep;
import scripts.QuestSteps.QuestTask;
import scripts.Requirements.ItemRequirement;
import scripts.Requirements.Requirement;
import scripts.Tasks.Priority;
import scripts.Tasks.Task;
import scripts.Timer;
import scripts.Utils;

import java.util.List;
import java.util.Optional;

public class MimicChallenge implements QuestTask {

    private static int[] xyzTranslateCoordinates = {6, -8, 0};
    // these are the .translate() coordinates for the launch pad from the centre "My life" piece

    public static LocalTile getMimicLaunchPadTile(LocalTile centreTile){
        return centreTile.translate(xyzTranslateCoordinates[0],
                xyzTranslateCoordinates[1], xyzTranslateCoordinates[2]);
    }


    EmoteStep cry = new EmoteStep("Cry", new RSTile(1769, 5058, 2));
    EmoteStep bow = new EmoteStep("Bow", new RSTile(1770, 5063, 2));
    EmoteStep dance = new EmoteStep("Dance", new RSTile(1772, 5070, 2));
    EmoteStep wave = new EmoteStep( "Wave", new RSTile(1767, 5061, 2));
    EmoteStep think = new EmoteStep("Think", new RSTile(1772, 5070, 2));
    NPCStep talk = new NPCStep(NpcID.ETHEREAL_MIMIC, Player.getPosition(),
            new String[]{"Suppose I may as well have a go."});

    private void doSteps() {
        if (Utils.getVarBitValue(2419) == 0) {
            Optional<Npc> mim = Query.npcs().idEquals(NpcID.ETHEREAL_MIMIC)
                    .maxDistance(20).isReachable()
                    .stream().findFirst();
            Log.info("Talking to mim with SDK version");
            if (mim.map(m->m.interact("Talk-to")).orElse(false)){
                Log.info("Talking to mim with SDK version - success");
                Timer.waitCondition(()-> mim.get().distanceTo(MyPlayer.getTile()) < 2, 5000,7000);
            }
          //  talk.setUseLocalNav(true);
          //  talk.execute();
            Utils.idleNormalAction();
        }

        switch (Utils.getVarBitValue(2420)) {
            case 1:
                cry.execute();
                break;
            case 2:
                bow.execute();
                break;
            case 3:
                dance.execute();
                break;
            case 4:
                wave.execute();
                break;
            case 5:
                think.execute();
                break;
            default:
                Log.info("Talking");
                talk.execute();
                break;
        }
        Timer.waitCondition(() -> Player.getAnimation() == -1, 2000,3000);

        if (NPCInteraction.isConversationWindowUp())
            NPCInteraction.handleConversation("Suppose I may as well have a go.");

    }
    @Override
    public String toString(){
        return "Doing Mimic Challenge";
    }

    @Override
    public Priority priority() {
        return Priority.MEDIUM;
    }

    @Override
    public boolean validate() {
        Optional<Npc> mim = Query.npcs()
                .idEquals(NpcID.ETHEREAL_MIMIC)
                .maxDistance(20)
                .isReachable()
                .stream().findFirst();

        RSNPC[] mimic = NPCs.findNearest(NpcID.ETHEREAL_MIMIC);
        return Game.isInInstance() && mim.isPresent(); //mimic.length > 0 && PathingUtil.canReach(mimic[0].getPosition());
    }

    @Override
    public void execute() {
        General.sleep(100,400);
        doSteps();
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
