package scripts.QuestSteps;

import com.trilezstudios.updater.hooks.NPC;
import dax.walker_engine.interaction_handling.NPCInteraction;
import dax.walker_engine.local_pathfinding.Reachable;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.tribot.api.General;
import org.tribot.api2007.Player;
import org.tribot.api2007.Walking;
import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSNPC;
import org.tribot.api2007.types.RSTile;
import org.tribot.script.sdk.Log;
import org.tribot.script.sdk.MyPlayer;
import org.tribot.script.sdk.Prayer;
import org.tribot.script.sdk.Quest;
import org.tribot.script.sdk.types.WorldTile;
import scripts.*;
import scripts.Requirements.Requirement;
import scripts.Timer;

import java.util.*;

public class NPCStep extends DetailedQuestStep  {

    @Getter
    private int npcID;
    private RSArea area;
    public RSTile npcTile;
    private List<String> listChatOptions;
    private String npcName;
    private RSNPC rsnpc;
    @Setter
    public boolean isCombatStep = false;

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
    private Optional<Prayer> prayer;


    private String description = "";

    @Getter
    protected final List<Requirement> requirements = new ArrayList<>();

    @Getter
    private final List<QuestStep> substeps = new ArrayList<>();

    private List<WorldTile> markedTiles = new ArrayList<>();

    public NPCStep(int npcID, RSArea npcArea) {
        this.npcID = npcID;
        this.area = npcArea;
    }

    public NPCStep(int npcID, RSTile npcTile, String description) {
        this.npcID = npcID;
        this.npcTile = npcTile;
        this.description = description;
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

    //TODO implement this
    public void addAlternateNpcs(int... ids) {

    }

    public void addSafeSpots(WorldTile... worldPoints) {
        markedTiles.addAll(Arrays.asList(worldPoints));
    }

    //call this on the step to make it kill the npc instead of making a method
    //then call .execute as you normall would
    public void setAsKillNpcStep(Optional<Prayer> prayer) {
        this.isCombatStep = true;
        this.setInteractionString("Attack");
        this.prayer = prayer;
    }

    public void setAsKillNpcStep() {
        setAsKillNpcStep(Optional.empty());
    }

    @Override
    public void addDialogStep(String... dialog) {
        if (listChatOptions == null) {
            listChatOptions = new ArrayList<>();
        }
       // Log.debug("Adding dialog " + dialog);
        Collections.addAll(listChatOptions, dialog);
    }


    @Override
    public void addSubSteps(QuestStep... substep) {
        this.substeps.addAll(Arrays.asList(substep));
    }

    @Override
    public void addSubSteps(Collection<QuestStep> substeps) {
        this.substeps.addAll(substeps);
    }

    @Override
    public String toString() {
        if (this.description != null)
            return this.description;

        return "NPC Step";
    }

    @Override
    public void execute() {
        if (requirements.stream().anyMatch(r -> !r.check())) {
            General.println("[NPCStep]: We failed a requirement to execute this NPCStep");
            return;
        }
        if (this.substeps.size() > 0) {
            General.println("[NPCStep]: There are substeps for this NPCStep, executing them");
            for (QuestStep sub : this.substeps) {
                sub.execute();
            }
        }

        if (this.area != null && !area.contains(Player.getPosition())) {
            if (useBlindWalk) {
                General.println("[NPCStep]: Blind walking to location");
                Walking.blindWalkTo(this.area.getRandomTile());
            }
            //attempt to generate a local path first, if that fails call dax walker
            if (!PathingUtil.walkToArea(this.area, false))
                PathingUtil.localNavigation(this.area.getRandomTile());


            // check we're moving before we do a longer timeout for movement
            if (Timer.waitCondition(Player::isMoving, 1500, 2250))
                Timer.waitCondition(() -> this.area.contains(Player.getPosition()), 6500, 9500);
        } else if (this.npcTile != null) {
            //attempt to generate a local path first, if that fails call dax walker
//            if (!PathingUtil.localNavigation(this.npcTile))
            PathingUtil.walkToTile(this.npcTile, radius, false);


            // check we're moving before we do a longer timeout for movement
            if (Timer.waitCondition(Player::isMoving, 1500, 2250))
                Timer.slowWaitCondition(() -> this.npcTile.isClickable(), 8500, 9500);
            Log.log("[NpcStep]: Done walking");
        }

        if (this.isCombatStep) {
            // interact to start dialog or combat if not already in combat
            if (!MyPlayer.isHealthBarVisible() && NpcChat.talkToNPC(this.npcID, this.interactionString))
                Timer.waitCondition(() -> MyPlayer.isHealthBarVisible() ||
                        NPCInteraction.isConversationWindowUp(), 4500, 5500);

            // handle dialog if up
            if (NPCInteraction.isConversationWindowUp()) {
                if (listChatOptions != null && listChatOptions.size() > 0) {
                    Log.debug("Handling chat");
                    NPCInteraction.handleConversation(listChatOptions.toArray(String[]::new));
                }else
                    NPCInteraction.handleConversation();
            }
            if (this.prayer.isPresent()) {
                if (Prayer.getPrayerPoints() < 15) {
                    Utils.drinkPotion(ItemID.PRAYER_POTION);
                }
                this.prayer.map(Prayer::enable);
            }

            // handle safespot
            if (this.markedTiles != null) {
                WorldTile tile = this.markedTiles.get(0);
                if (!tile.equals(MyPlayer.getPosition()) && tile.interact("Walk here")) {
                    PathingUtil.movementIdle();
                    //re-engage NPC
                    NpcChat.talkToNPC(this.npcID, this.interactionString);
                }
            }


            // handle food


            return;
        }


        if (NpcChat.talkToNPC(this.rsnpc, this.interactionString) ||
                (this.npcName != null && NpcChat.talkToNPC(this.npcName)) ||
                NpcChat.talkToNPC(this.npcID, this.interactionString)) {
            Log.log("Waiting for conversation window");
            NPCInteraction.waitForConversationWindow();
            if (listChatOptions != null && listChatOptions.size() > 0 &&
                    NPCInteraction.isConversationWindowUp()) {
                Log.debug("Handling chat");
                NPCInteraction.handleConversation(listChatOptions.toArray(String[]::new));

            }

            if (NPCInteraction.isConversationWindowUp())
                NPCInteraction.handleConversation();
        }

        Log.log("[NPCStep]: Execution finished");
    }


}
