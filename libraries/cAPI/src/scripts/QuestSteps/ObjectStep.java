package scripts.QuestSteps;

import dax.walker_engine.interaction_handling.NPCInteraction;
import lombok.Getter;
import lombok.Setter;
import org.tribot.api.General;
import org.tribot.api2007.Objects;
import org.tribot.api2007.Player;
import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSObject;
import org.tribot.api2007.types.RSTile;
import org.tribot.script.sdk.Log;
import org.tribot.script.sdk.MyPlayer;
import org.tribot.script.sdk.query.Query;
import org.tribot.script.sdk.types.Area;
import org.tribot.script.sdk.types.LocalTile;
import org.tribot.script.sdk.walking.LocalWalking;
import scripts.PathingUtil;
import scripts.Requirements.Requirement;
import scripts.Timer;
import scripts.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.BooleanSupplier;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ObjectStep implements QuestStep {


    private RSTile tile;
    private int objectId;
    private String objectAction;
    private BooleanSupplier waitCondition;
    private Predicate<RSObject> predicate;

    @Getter
    @Setter
    private boolean waitCond;


    @Getter
    @Setter
    private boolean handleChat;

    @Getter
    @Setter
    private List<String> chat = new ArrayList<>();


    @Getter
    @Setter
    private boolean useLocalNav = false;

    @Getter
    @Setter
    private int tileRadius = 2;

    @Getter
    protected final List<Requirement> requirements = new ArrayList<>();

    public ObjectStep(int objectId, RSTile tile) {
        this.objectId = objectId;
        this.tile = tile;
        this.handleChat = false;
    }

    public ObjectStep(int objectId, RSTile tile, String objectAction, boolean waitCond) {
        this.objectId = objectId;
        this.tile = tile;
        this.objectAction = objectAction;
        this.waitCond = waitCond;
        this.handleChat = false;
    }

    public ObjectStep(int objectId, RSTile tile, String objectAction) {
        this.objectId = objectId;
        this.tile = tile;
        this.objectAction = objectAction;
        this.waitCond = NPCInteraction.isConversationWindowUp();
        this.handleChat = false;
    }

    public ObjectStep(int objectId, RSTile tile, String objectAction, Requirement... requirements) {
        this.objectId = objectId;
        this.tile = tile;
        this.objectAction = objectAction;
        this.waitCond = NPCInteraction.isConversationWindowUp();
        this.handleChat = false;
        this.requirements.addAll(Arrays.asList(requirements));
    }

    public ObjectStep(int objectId, RSTile tile, String objectAction, boolean waitCond, boolean handleChat) {
        this.objectId = objectId;
        this.tile = tile;
        this.objectAction = objectAction;
        this.waitCond = waitCond;
        this.handleChat = handleChat;
    }

    public ObjectStep(int objectId, String objectAction, RSTile tile, boolean waitCond) {
        this.objectId = objectId;
        this.tile = tile;
        this.objectAction = objectAction;
        this.waitCond = waitCond;
        this.handleChat = false;
    }


    public ObjectStep(int objectId, RSTile tile, String objectAction, boolean waitCond, Requirement... requirements) {
        this.objectId = objectId;
        this.tile = tile;
        this.objectAction = objectAction;
        this.waitCond = waitCond;
        this.handleChat = false;
        this.requirements.addAll(Arrays.asList(requirements));
    }

    public ObjectStep(int objectId, RSTile tile, boolean waitCond, String objectAction,
                      boolean handleChat) {
        this.objectId = objectId;
        this.tile = tile;
        this.objectAction = objectAction;
        this.waitCond = waitCond;
        this.handleChat = handleChat;
    }

    public ObjectStep(Predicate<RSObject> predicate,
                      RSTile tile, boolean waitCond,
                      String objectAction,
                      boolean handleChat) {
        this.predicate = predicate;
        this.tile = tile;
        this.objectAction = objectAction;
        this.waitCond = waitCond;
        this.handleChat = handleChat;
    }

    @Override
    public void addDialogStep(String... dialog) {
        if (this.chat == null)  //shouldnt happen as its instantiated immediately
            return;

        this.chat.addAll(Arrays.stream(dialog).collect(Collectors.toList()));
    }

    public Optional<LocalTile> getWalkableTile(LocalTile tile) {
        return Query.tiles()
                .inArea(Area.fromRadius(tile, 1))
                .filter(LocalTile::isWalkable)
                .findBestInteractable();
    }

    @Override
    public void execute() {
        if (requirements.stream().anyMatch(r -> !r.check())) {
            General.println("[ObjectStep]: We failed a requirement to execute this NPCStep");
            return;
        }

        if (this.tile != null) {
            RSArea objArea = new RSArea(this.tile, this.tileRadius);
            if (!objArea.contains(Player.getPosition())) {
                General.println("[ObjectStep]: Navigating to object area: ID " + this.objectId);
                LocalTile tile = new LocalTile(this.tile.getX(), this.tile.getY(), MyPlayer.getPosition().getPlane());
                Optional<LocalTile> walkable = getWalkableTile(tile);
                if (walkable.map(LocalWalking::walkTo).orElse(false)) {
                    General.println("[ObjectStep]: Navigating to object area - local SDK (walkable)");
                    PathingUtil.movementIdle();
                } else if (LocalWalking.walkTo(tile)) {
                    General.println("[ObjectStep]: Navigating to object area - local SDK");
                    PathingUtil.movementIdle();
                } else if (!useLocalNav && !PathingUtil.localNavigation(this.tile) &&
                        PathingUtil.walkToTile(this.tile, tileRadius, false))
                    PathingUtil.movementIdle();

                else if (PathingUtil.localNavigation(this.tile)) {
                    General.println("[ObjectStep]: Navigating to object area - local");
                    PathingUtil.movementIdle();
                }
            }
        }
        General.println("[ObjectStep]: Interacting with object: " + this.objectId + " with: " + this.objectAction);

        if (this.predicate != null) {
            Log.log("[ObjectStep]: using predicate");
            RSObject[] obj = Objects.findNearest(this.tileRadius, predicate);
            if (obj.length > 0 &&
                    Utils.clickObject(obj[0], this.objectAction, false)) {
                if (!handleChat) {
                    if (NPCInteraction.isConversationWindowUp())
                        NPCInteraction.handleConversation();
                } else {
                    General.println("[ObjectStep]: Handling chat");
                    NPCInteraction.waitForConversationWindow();
                    if (chat != null && NPCInteraction.isConversationWindowUp()) {
                        String[] s = new String[chat.size()];
                        NPCInteraction.handleConversation(chat.toArray(s));
                    } else if (NPCInteraction.isConversationWindowUp()) {
                        NPCInteraction.handleConversation();
                    }
                    return;
                }
                // will end after click if no wait condition
                Timer.waitCondition(() -> this.waitCond, 6000, 9000);

            }
        }
        //  Log.log("[ObjectStep]: Using Utils");
        if (Utils.clickObject(this.objectId, this.objectAction, false)) {
            if (!handleChat) {
                if (NPCInteraction.isConversationWindowUp())
                    NPCInteraction.handleConversation();
            } else {
                General.println("[ObjectStep]: Handling chat");
                NPCInteraction.waitForConversationWindow();
                if (chat != null && NPCInteraction.isConversationWindowUp()) {
                    String[] s = new String[chat.size()];
                    NPCInteraction.handleConversation(chat.toArray(s));
                } else if (NPCInteraction.isConversationWindowUp()) {
                    NPCInteraction.handleConversation();
                }
                return;
            }
            // will end after click if no wait condition
            Timer.waitCondition(() -> this.waitCond, 6000, 9000);
        }

    }

}