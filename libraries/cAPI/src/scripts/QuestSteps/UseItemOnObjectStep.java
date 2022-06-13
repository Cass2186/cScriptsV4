package scripts.QuestSteps;

import dax.walker_engine.interaction_handling.NPCInteraction;
import lombok.Getter;
import lombok.Setter;
import org.tribot.api.General;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Player;
import org.tribot.api2007.types.RSObjectDefinition;
import org.tribot.api2007.types.RSTile;
import org.tribot.script.sdk.Log;
import scripts.NpcChat;
import scripts.PathingUtil;
import scripts.Requirements.Requirement;
import scripts.Timer;
import scripts.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.BooleanSupplier;

public class UseItemOnObjectStep extends QuestStep{

    RSTile tile;
    int ItemID;
    int objectId;
    String objectName;


    @Getter
    @Setter
    boolean waitCond;
    @Getter
    @Setter
    boolean handleChat;
    private String print;

    @Getter
    @Setter
    private boolean useLocalNav = false;

    String chat;
    int tileRadius = 2;

    @Getter
    protected final List<Requirement> requirements = new ArrayList<>();


    public UseItemOnObjectStep(int ItemID, int objectId, RSTile tile) {
        this.ItemID = ItemID;
        this.objectId = objectId;
        this.tile = tile;
        RSObjectDefinition def = RSObjectDefinition.get(objectId);
        if (def != null)
            this.objectName = def.getName();

        this.waitCond = Inventory.find(this.ItemID).length == 0;
        this.handleChat = false;
    }
    public UseItemOnObjectStep(int ItemID, int objectId, RSTile tile, Requirement... requirements) {
        this.ItemID = ItemID;
        this.objectId = objectId;
        this.tile = tile;
        RSObjectDefinition def = RSObjectDefinition.get(objectId);
        if (def != null)
            this.objectName = def.getName();

        this.waitCond = Inventory.find(this.ItemID).length == 0;
        this.handleChat = false;

        this.requirements.addAll(Arrays.asList(requirements));
    }
    public UseItemOnObjectStep(int ItemID, int objectId, RSTile tile, String print) {
        this.ItemID = ItemID;
        this.objectId = objectId;
        this.tile = tile;
        RSObjectDefinition def = RSObjectDefinition.get(objectId);
        if (def != null)
            this.objectName = def.getName();

        this.waitCond = Inventory.find(this.ItemID).length == 0;
        this.handleChat = false;
        this.print = print;
    }

    public UseItemOnObjectStep(int ItemID, int objectId, RSTile tile, String print, Requirement... requirements) {
        this.ItemID = ItemID;
        this.objectId = objectId;
        this.tile = tile;
        RSObjectDefinition def = RSObjectDefinition.get(objectId);
        if (def != null)
            this.objectName = def.getName();

        this.waitCond = Inventory.find(this.ItemID).length == 0;
        this.handleChat = false;
        this.print = print;
        this.requirements.addAll(Arrays.asList(requirements));
    }

    public UseItemOnObjectStep(int ItemID, int objectId, RSTile tile, boolean waitCondition) {
        this.ItemID = ItemID;
        this.objectId = objectId;
        this.tile = tile;
        RSObjectDefinition def = RSObjectDefinition.get(objectId);
        if (def != null)
            this.objectName = def.getName();

        this.waitCond = waitCondition;
        this.handleChat = false;
    }

    public UseItemOnObjectStep(int ItemID, int objectId, RSTile tile, boolean waitCondition, Requirement... requirements) {
        this.ItemID = ItemID;
        this.objectId = objectId;
        this.tile = tile;
        RSObjectDefinition def = RSObjectDefinition.get(objectId);
        if (def != null)
            this.objectName = def.getName();

        this.waitCond = waitCondition;
        this.handleChat = false;
        this.requirements.addAll(Arrays.asList(requirements));
    }

    public UseItemOnObjectStep(int ItemID, int objectId, RSTile tile, boolean waitCondition, boolean handleChat) {
        this.ItemID = ItemID;
        this.objectId = objectId;
        this.tile = tile;
        RSObjectDefinition def = RSObjectDefinition.get(objectId);
        if (def != null)
            this.objectName = def.getName();

        this.waitCond = waitCondition;
        this.handleChat = handleChat;
    }

    public UseItemOnObjectStep(int ItemID, String objectName, RSTile tile, boolean waitCondition) {
        this.ItemID = ItemID;
        this.objectName = objectName;
        this.tile = tile;
        this.objectId = -1;
        this.waitCond = waitCondition;
    }


    public boolean useItemOnObject() {
        if (requirements.stream().anyMatch(r -> !r.check())) {
            General.println("[UseItemOnObjectStep]: We failed a requirement to execute this NPCStep");
            return false;
        }
        if (this.print != null){
            General.println("[UseItemOnObjStep]: " + this.print);
        }
        if (Player.getPosition().distanceTo(this.tile) > this.tileRadius) {
            General.println("[Debug]: Moving to object");
            if (!PathingUtil.localNav(Utils.getLocalTileFromRSTile(this.tile)))
                PathingUtil.walkToTile(this.tile, this.tileRadius, false);
            else
                PathingUtil.movementIdle();
        }
        if (this.objectId != -1 && Utils.useItemOnObject(this.ItemID, this.objectId)) {
            if (this.handleChat) {
                General.println("[Debug]: Waiting for chat to handle");
                NpcChat.handle(true, "Yes", "Yes.");
            }
            Log.info("[UseItemOnObj]: Waiting for waid cond");
            return Timer.waitCondition(() -> waitCond, 5000, 8000);
        } else if (Utils.useItemOnObject(this.ItemID, this.objectName)) {
            if (this.handleChat) {
                General.println("[Debug]: Waiting for chat to handle");
                NpcChat.handle(true, "Yes", "Yes.");
            }
            return Timer.waitCondition(() -> waitCond, 5000, 8000);
        }


        return false;
    }

    public void setTileRadius(int rad) {
        this.tileRadius = rad;
    }

    @Override
    public void execute() {
        this.useItemOnObject();
    }

    @Override
    public void addDialogStep(String... dialog) {
        this.handleChat = true;

    }

    @Override
    public void addSubSteps(QuestStep... substep) {

    }

    @Override
    public void addSubSteps(Collection<QuestStep> substeps) {

    }

    ;

}
