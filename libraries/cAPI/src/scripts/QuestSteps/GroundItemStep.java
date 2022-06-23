package scripts.QuestSteps;

import lombok.Getter;
import org.tribot.api.General;
import org.tribot.api2007.types.RSTile;
import org.tribot.script.sdk.Log;
import org.tribot.script.sdk.query.Query;
import scripts.NpcChat;
import scripts.PathingUtil;
import scripts.Requirements.ItemReq;
import scripts.Requirements.ItemRequirement;
import scripts.Requirements.Requirement;
import scripts.Timer;
import scripts.Utils;

import java.nio.file.Path;
import java.util.*;

public class GroundItemStep extends QuestStep {

    private int itemID = -1;
    private String itemName;
    private RSTile locationTile;
    private List<String> dialog;
    @Getter
    private final List<QuestStep> substeps = new ArrayList<>();

    @Getter
    protected final List<Requirement> requirements = new ArrayList<>();


    //Constructors
    public GroundItemStep(int ItemID) {
        this.itemID = ItemID;
    }

    public GroundItemStep(ItemReq req) {
        this.itemID = req.getId();
    }

    public GroundItemStep(ItemRequirement req) {
        this.itemID = req.getId();
    }

    public GroundItemStep(int ItemID, RSTile tile) {
        this.itemID = ItemID;
        this.locationTile = tile;
    }

    public GroundItemStep(String itemName) {
        this.itemName = itemName;
    }

    public GroundItemStep(int ItemID, String itemName) {
        this.itemID = ItemID;
        this.itemName = itemName;
    }

    public GroundItemStep(int ItemID, RSTile tile, Requirement... requirements) {
        this(ItemID, tile);
        this.requirements.addAll(Arrays.asList(requirements));
    }


    public boolean isItemOnGround() {
        if (this.itemID != -1)
            return Query.groundItems().idEquals(this.itemID).isAny();
        if (this.itemName != null)
            return Query.groundItems().nameContains(this.itemName).isAny();
        return false;
    }

    public boolean pickUpItem() {
        if (requirements.stream().anyMatch(r -> !r.check())) {
            General.println("[GroundItemStep]: We failed a requirement to execute this step");
            return false;
        }
        for (QuestStep sub : substeps){
            Log.info("Executing substep for this GroundItemStep");
        }
        if (this.locationTile != null) {
           // if (!this.locationTile.isOnScreen()) {
                Log.info("[GroundItemStep] Navigating to ground item");
                if (PathingUtil.localNav(Utils.getLocalTileFromRSTile(locationTile))) {
                    Log.info("Local nav to tile worked");
                    PathingUtil.movementIdle();
                }
                else if(PathingUtil.localNavigation(locationTile)) {
                    Log.info("Local navigation to tile worked");
                    PathingUtil.movementIdle();
                } else if (PathingUtil.walkToTile(locationTile, 2, false)) {
                    Timer.waitCondition(() -> this.locationTile.isOnScreen(), 6000, 7000);
                }
           // }
        }
        General.println("[GroundItemStep] Picking up ground Item");
        return itemID != -1 ? Utils.clickGroundItem(itemID) : Utils.clickGroundItem(itemName);
    }

    @Override
    public void execute() {
        this.pickUpItem();
        if (this.dialog != null) {
            NpcChat.handle(true, this.dialog.toArray(String[]::new));
        }
    }

    @Override
    public void addDialogStep(String... dialog) {
        if (this.dialog == null)
            this.dialog = new ArrayList<>(Arrays.asList(dialog));
        else {
            this.dialog.addAll(Arrays.asList(dialog));
        }
    }

    @Override
    public void addSubSteps(QuestStep... substep) {
        this.substeps.addAll(Arrays.asList(substep));
    }

    @Override
    public void addSubSteps(Collection<QuestStep> substeps) {
        this.substeps.addAll(substeps);
    }
}
