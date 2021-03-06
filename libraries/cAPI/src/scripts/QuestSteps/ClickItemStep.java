package scripts.QuestSteps;

import dax.walker_engine.interaction_handling.NPCInteraction;
import lombok.Getter;
import obf.Ch;
import org.tribot.api.General;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Player;
import org.tribot.api2007.types.RSItem;
import org.tribot.api2007.types.RSTile;
import org.tribot.script.sdk.ChatScreen;
import org.tribot.script.sdk.Waiting;
import org.tribot.script.sdk.types.WorldTile;
import org.tribot.script.sdk.util.TribotRandom;
import scripts.NpcChat;
import scripts.PathingUtil;
import scripts.Requirements.Requirement;
import scripts.Utils;

import java.util.*;

public class ClickItemStep extends QuestStep {

    private int ItemID;
    private String itemName;
    private String interactionString;
    private RSTile tile;
    private boolean waitCond = true;
    private WorldTile worldtTile;
    private List<String> dialog;


    @Getter
    protected final List<Requirement> requirements = new ArrayList<>();

    public ClickItemStep(int ItemID, String interactionString) {
        this.ItemID = ItemID;
        this.interactionString = interactionString;
        this.tile = Player.getPosition();
        this.worldtTile = Utils.getWorldTileFromRSTile( this.tile);
    }

    public ClickItemStep(int ItemID, String interactionString, Requirement... reqs) {
        this.ItemID = ItemID;
        this.interactionString = interactionString;
        this.tile = Player.getPosition();
        this.requirements.addAll(Arrays.asList(reqs));
    }

    public ClickItemStep(int ItemID, String interactionString, boolean waitCond, Requirement... reqs) {
        this.ItemID = ItemID;
        this.interactionString = interactionString;
        this.tile = Player.getPosition();
        this.waitCond = waitCond;
        this.requirements.addAll(Arrays.asList(reqs));
    }

    public ClickItemStep(int ItemID, String interactionString, RSTile tile) {
        this.ItemID = ItemID;
        this.interactionString = interactionString;
        this.tile = tile;
        this.worldtTile = Utils.getWorldTileFromRSTile( this.tile);
    }

    public ClickItemStep(int ItemID, String interactionString, RSTile tile, Requirement... reqs) {
        this.ItemID = ItemID;
        this.interactionString = interactionString;
        this.tile = tile;
        this.worldtTile = Utils.getWorldTileFromRSTile( this.tile);
        this.requirements.addAll(Arrays.asList(reqs));

    }


    public ClickItemStep(String itemName, String interactionString, RSTile tile, Requirement... reqs) {
        this.ItemID = -1;
        this.itemName = itemName;
        this.interactionString = interactionString;
        this.tile = tile;
        this.worldtTile = Utils.getWorldTileFromRSTile( this.tile);
        this.requirements.addAll(Arrays.asList(reqs));
    }


    public void addDialogStep(String dialog) {
        if (this.dialog == null)
            this.dialog = new ArrayList<>(Collections.singletonList(dialog));
        else {
            this.dialog.add(dialog);
        }
    }

    public void addDialogStep(String[] dialog) {
        if (this.dialog == null)
            this.dialog = new ArrayList<>(Arrays.asList(dialog));
        else {
            this.dialog.addAll(Arrays.asList(dialog));
        }
    }

    @Override
    public void addSubSteps(QuestStep... substep) {

    }

    @Override
    public void addSubSteps(Collection<QuestStep> substeps) {

    }

    @Override
    public void execute() {
        if (requirements.stream().anyMatch(r -> !r.check())) {
            General.println("[ClickItemSteps]: We failed a requirement to execute this ClickItemStep");
            return;
        }
        if (this.ItemID != -1) {
            RSItem[] item = Inventory.find(this.ItemID);
            if (item.length > 0) {
                if (this.tile != null &&
                        PathingUtil.localNav(Utils.getLocalTileFromRSTile(this.tile))) {
                    PathingUtil.movementIdle();
                } else if (this.tile != null && PathingUtil.walkToTile(this.tile)) {
                    PathingUtil.movementIdle();
                }
                if (this.tile != null && !this.tile.equals(Player.getPosition())){
                    PathingUtil.clickScreenWalk(this.tile);
                    PathingUtil.movementIdle();
                }
                if (dialog == null) {
                    item[0].click(this.interactionString);

                } else if (item[0].click(this.interactionString)) {
                    NpcChat.handle(true, this.interactionString);

                }
            }
        } else if (this.itemName != null) {
            RSItem[] invItem = Inventory.find(this.itemName);
            if (invItem.length > 0) {
                if (this.tile != null) {
                    for (int i = 0; i < 4; i++) {
                        if (this.tile.isClickable() && this.tile.click("Walk here")){
                            PathingUtil.movementIdle();
                        }
                        else if (PathingUtil.localNavigation(this.tile)) {
                            PathingUtil.movementIdle();
                        } else if (PathingUtil.walkToTile(this.tile)) {
                            PathingUtil.movementIdle();
                        }
                        if (Player.getPosition().equals(this.tile))
                            break;
                    }
                }
                if (dialog == null) {
                    invItem[0].click(this.interactionString);
                } else if (invItem[0].click(this.interactionString)) {
                    NPCInteraction.waitForConversationWindow();
                    NPCInteraction.handleConversation(this.interactionString);
                }
            }
            Waiting.waitUntil(TribotRandom.uniform(2500,4000), 400, ()-> this.waitCond || ChatScreen.isOpen());
            if (ChatScreen.isOpen())
                NpcChat.handle();
        }

    }

}
