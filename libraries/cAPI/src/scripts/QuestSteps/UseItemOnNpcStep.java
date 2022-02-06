package scripts.QuestSteps;

import dax.shared.helpers.questing.Quest;
import dax.walker_engine.interaction_handling.NPCInteraction;
import lombok.Getter;
import lombok.Setter;
import org.tribot.api.General;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Player;
import org.tribot.api2007.types.RSNPCDefinition;
import org.tribot.api2007.types.RSObjectDefinition;
import org.tribot.api2007.types.RSTile;
import scripts.PathingUtil;
import scripts.Requirements.Requirement;
import scripts.Timer;
import scripts.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.BooleanSupplier;

public class UseItemOnNpcStep extends QuestStep {

   private RSTile tile;
    private   int ItemID;
    private  int npcId;
    private  String objectName;
    private  BooleanSupplier waitCondition;
    private  boolean waitCond;
    private boolean handleChat;
    private String print;

    @Getter
    @Setter
    private boolean useLocalNav = false;
    String chat;
    int tileRadius = 2;

    @Getter
    protected final List<Requirement> requirements = new ArrayList<>();


    public UseItemOnNpcStep(int ItemID, int npcId, RSTile tile) {
        this.ItemID = ItemID;
        this.npcId = npcId;
        this.tile = tile;
        this.waitCond = Inventory.find(this.ItemID).length == 0;
        this.handleChat = false;
    }
    public UseItemOnNpcStep(int ItemID, int objectId, RSTile tile, Requirement... requirements) {
        this.ItemID = ItemID;
        this.npcId = objectId;
        this.tile = tile;
        RSNPCDefinition def = RSNPCDefinition.get(objectId);
        if (def != null)
            this.objectName = def.getName();

        this.waitCond = Inventory.find(this.ItemID).length == 0;
        this.handleChat = false;

        this.requirements.addAll(Arrays.asList(requirements));
    }
    public UseItemOnNpcStep(int ItemID, int objectId, RSTile tile, String print) {
        this.ItemID = ItemID;
        this.npcId = objectId;
        this.tile = tile;
        RSNPCDefinition def = RSNPCDefinition.get(objectId);
        if (def != null)
            this.objectName = def.getName();

        this.waitCond = Inventory.find(this.ItemID).length == 0;
        this.handleChat = false;
        this.print = print;
    }

    public UseItemOnNpcStep(int ItemID, int objectId, RSTile tile, String print, Requirement requirements) {
        this.ItemID = ItemID;
        this.npcId = objectId;
        this.tile = tile;
        RSNPCDefinition def = RSNPCDefinition.get(objectId);
        if (def != null)
            this.objectName = def.getName();

        this.waitCond = Inventory.find(this.ItemID).length == 0;
        this.handleChat = false;
        this.print = print;
        this.requirements.addAll(Arrays.asList(requirements));
    }

    public UseItemOnNpcStep(int ItemID, int objectId, RSTile tile, boolean waitCondition) {
        this.ItemID = ItemID;
        this.npcId = objectId;
        this.tile = tile;
        RSNPCDefinition def = RSNPCDefinition.get(objectId);
        if (def != null)
            this.objectName = def.getName();

        this.waitCond = waitCondition;
        this.handleChat = false;
    }

    public UseItemOnNpcStep(int ItemID, int objectId, RSTile tile, boolean waitCondition, Requirement... requirements) {
        this.ItemID = ItemID;
        this.npcId = objectId;
        this.tile = tile;
        RSNPCDefinition def = RSNPCDefinition.get(objectId);
        if (def != null)
            this.objectName = def.getName();

        this.waitCond = waitCondition;
        this.handleChat = false;
        this.requirements.addAll(Arrays.asList(requirements));
    }

    public UseItemOnNpcStep(int ItemID, int objectId, RSTile tile, boolean waitCondition, boolean handleChat) {
        this.ItemID = ItemID;
        this.npcId = objectId;
        this.tile = tile;
        RSNPCDefinition def = RSNPCDefinition.get(objectId);
        if (def != null)
            this.objectName = def.getName();

        this.waitCond = waitCondition;
        this.handleChat = handleChat;
    }

    public UseItemOnNpcStep(int ItemID, String objectName, RSTile tile, boolean waitCondition) {
        this.ItemID = ItemID;
        this.objectName = objectName;
        this.tile = tile;
        this.npcId = -1;
        this.waitCond = waitCondition;
    }
    @Override
    public void execute() {
        if (requirements.stream().anyMatch(r -> !r.check())) {
            General.println("[UseItemOnNPC]: We failed a requirement to execute this UseItemOnNpcStep");
            return;
        }
        if (this.print != null){
            General.println("[UseItemOnNPC]: " + this.print);
        }
        if (Player.getPosition().distanceTo(this.tile) > this.tileRadius) {
            General.println("[Debug]: Moving to object");
            if (!PathingUtil.localNavigation(this.tile))
                PathingUtil.walkToTile(this.tile, this.tileRadius, false);
            else
                PathingUtil.movementIdle();
        }
        if (this.npcId != -1 && Utils.useItemOnNPC(this.ItemID, this.npcId)) {
            if (this.handleChat) {
                General.println("[Debug]: Waiting for chat to handle");
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation("Yes");
            }
             Timer.waitCondition(() -> waitCond, 5000, 8000);
        } else if (Utils.useItemOnObject(this.ItemID, this.objectName)) {
            if (this.handleChat) {
                General.println("[Debug]: Waiting for chat to handle");
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation("Yes");
            }
             Timer.waitCondition(() -> waitCond, 5000, 8000);
        }

    }

    @Override
    public void addDialogStep(String... dialog) {

    }

    @Override
    public void addSubSteps(QuestStep... substep) {

    }

    @Override
    public void addSubSteps(Collection<QuestStep> substeps) {

    }
}
