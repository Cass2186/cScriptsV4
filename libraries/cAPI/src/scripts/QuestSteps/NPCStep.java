package scripts.QuestSteps;

import dax.walker_engine.interaction_handling.NPCInteraction;
import dax.walker_engine.local_pathfinding.Reachable;
import lombok.Getter;
import lombok.Setter;
import org.tribot.api.General;
import org.tribot.api2007.Player;
import org.tribot.api2007.Walking;
import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSNPC;
import org.tribot.api2007.types.RSTile;
import org.tribot.script.sdk.Log;
import scripts.NpcChat;
import scripts.PathingUtil;
import scripts.Requirements.Requirement;
import scripts.Timer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class NPCStep implements QuestStep {

    @Getter
    private int npcID;
    private RSArea area;
    public RSTile npcTile;
    private List<String> listChatOptions;
    private String npcName;
    private RSNPC rsnpc;

    @Getter
    @Setter
    private String interactionString = "Talk-to";

    @Getter
    @Setter
    private boolean useLocalNav = false;

    @Getter
    @Setter
    private boolean useBlindWalk = false;

    @Getter
    @Setter
    private int radius = 3;

    @Getter
    protected final List<Requirement> requirements = new ArrayList<>();




    public NPCStep(int npcID, RSArea npcArea) {
        this.npcID = npcID;
        this.area = npcArea;
    }

    public NPCStep(int npcID, RSTile npcTile, String description) {
        this.npcID = npcID;
        this.npcTile = npcTile;
    }

    public NPCStep(int npcID, RSTile npcTile) {
        this.npcID = npcID;
        this.npcTile = npcTile;
    }

    public NPCStep(RSNPC rsnpc, RSTile npcTile) {
        this.rsnpc = rsnpc;
        this.npcTile = npcTile;
    }


    public NPCStep(String npcString, RSArea npcArea) {
        this.npcName = npcString;
        this.area = npcArea;
    }

    public NPCStep(String npcString, RSTile npcTile) {
        this.npcName = npcString;
        this.npcTile = npcTile;
    }


    public NPCStep(int npcID, RSArea npcArea, Requirement... requirements) {
        this.npcID = npcID;
        this.area = npcArea;
        this.requirements.addAll(Arrays.asList(requirements));
    }


    public NPCStep(RSNPC rsnpc, RSTile npcTile, Requirement... requirements) {
        this.rsnpc = rsnpc;
        this.npcTile = npcTile;
        this.requirements.addAll(Arrays.asList(requirements));
    }


    public NPCStep(String npcString, RSArea npcArea, Requirement... requirements) {
        this.npcName = npcString;
        this.area = npcArea;
        this.requirements.addAll(Arrays.asList(requirements));
    }

    public NPCStep(String npcString, RSTile npcTile, Requirement... requirements) {
        this.npcName = npcString;
        this.npcTile = npcTile;
        this.requirements.addAll(Arrays.asList(requirements));
    }

    public NPCStep(int npcID, RSTile npcTile, Requirement... requirements) {
        this.npcID = npcID;
        this.npcTile = npcTile;
        this.requirements.addAll(Arrays.asList(requirements));
    }


    public NPCStep(int npcID, RSArea npcArea, String[] chatText) {
        this.npcID = npcID;
        this.area = npcArea;
        this.listChatOptions = Arrays.asList(chatText);
    }


    public NPCStep(int npcID, RSArea npcArea, String[] chatText, Requirement... requirements) {
        this.npcID = npcID;
        this.area = npcArea;
        this.listChatOptions = Arrays.asList(chatText);
        this.requirements.addAll(Arrays.asList(requirements));
    }

    public NPCStep(String name, RSArea npcArea, String[] chatText) {
        this.npcName = name;
        this.area = npcArea;
        this.listChatOptions = Arrays.asList(chatText);

    }

    public NPCStep(String name, RSArea npcArea, String[] chatText, Requirement... requirements) {
        this.npcName = name;
        this.area = npcArea;
        this.listChatOptions = Arrays.asList(chatText);
        this.requirements.addAll(Arrays.asList(requirements));
    }


    public NPCStep(int npcID, RSTile npcTile, String[] chatText) {
        this.npcID = npcID;
        this.npcTile = npcTile;
        this.listChatOptions = Arrays.asList(chatText);
    }


    public NPCStep(int npcID, RSTile npcTile, String[] chatText, Requirement... requirements) {
        this.npcID = npcID;
        this.npcTile = npcTile;
        this.listChatOptions = Arrays.asList(chatText);
        this.requirements.addAll(Arrays.asList(requirements));
    }

    public NPCStep(String npcName, RSTile npcTile, String[] chatText) {
        this.npcName = npcName;
        this.npcTile = npcTile;
        this.listChatOptions = Arrays.asList(chatText);
    }


    public NPCStep(String npcName, RSTile npcTile, String[] chatText, Requirement... requirements) {
        this.npcName = npcName;
        this.npcTile = npcTile;
        this.listChatOptions = Arrays.asList(chatText);
        this.requirements.addAll(Arrays.asList(requirements));
    }

    @Override
    public void addDialogStep(String... dialog) {
        if (listChatOptions == null) {
            listChatOptions = new ArrayList<>();
        }
        Collections.addAll(listChatOptions, dialog);
    }

    @Override
    public void execute() {
        if (requirements.stream().anyMatch(r -> !r.check())) {
            General.println("[NPCStep]: We failed a requirement to execute this NPCStep");
            return;
        }

        if (this.area != null && !area.contains(Player.getPosition())) {
            if (useBlindWalk) {
                General.println("[NPCStep]: Blind walking to location");
                Walking.blindWalkTo(this.area.getRandomTile());
            }
               //attempt to generate a local path first, if that fails call dax walker
            if (!PathingUtil.walkToArea(this.area, false) )
                PathingUtil.localNavigation(this.area.getRandomTile());


            // check we're moving before we do a longer timeout for movement
            if (Timer.waitCondition(Player::isMoving, 1500, 2250))
                Timer.waitCondition(() -> this.area.contains(Player.getPosition()), 6500, 9500);
        } else if (this.npcTile != null){
            //attempt to generate a local path first, if that fails call dax walker
//            if (!PathingUtil.localNavigation(this.npcTile))
                PathingUtil.walkToTile(this.npcTile, radius, false);


            // check we're moving before we do a longer timeout for movement
            if (Timer.waitCondition(Player::isMoving, 1500, 2250))
                Timer.slowWaitCondition(() -> this.npcTile.isClickable(), 8500, 9500);
            Log.log("[NpcStep]: Done walking");
        }

        if (NpcChat.talkToNPC(this.rsnpc, this.interactionString) ||
                (this.npcName != null && NpcChat.talkToNPC(this.npcName)) ||
                NpcChat.talkToNPC(this.npcID, this.interactionString)) {
            Log.log("Waiting for conversation window");
            NPCInteraction.waitForConversationWindow();
            if (listChatOptions != null && listChatOptions.size() > 0 && NPCInteraction.isConversationWindowUp())
                NPCInteraction.handleConversation(listChatOptions.toArray(String[]::new));

            if (NPCInteraction.isConversationWindowUp())
                NPCInteraction.handleConversation();
        }

        Log.log("[NPCStep]: Execution finished");
    }


}
