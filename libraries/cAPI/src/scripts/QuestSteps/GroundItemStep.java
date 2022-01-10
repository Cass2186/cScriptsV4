package scripts.QuestSteps;

import lombok.Getter;
import org.tribot.api.General;
import org.tribot.api2007.types.RSTile;
import scripts.PathingUtil;
import scripts.Requirements.ItemReq;
import scripts.Requirements.Requirement;
import scripts.Timer;
import scripts.Utils;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GroundItemStep implements QuestStep {

    private int itemId = -1;
    private String itemName;
    private RSTile locationTile;

    @Getter
    protected final List<Requirement> requirements = new ArrayList<>();

    public GroundItemStep(int itemId){
        this.itemId = itemId;
    }

    public GroundItemStep(ItemReq req){
        this.itemId = req.getId();
    }

    public GroundItemStep(int itemId, RSTile tile){
        this.itemId = itemId;
        this.locationTile =tile;
    }

    public GroundItemStep(String itemName){
        this.itemName = itemName;
    }

    public GroundItemStep(int itemId, String itemName){
        this.itemId = itemId;
        this.itemName = itemName;
    }

    public GroundItemStep(int itemId, RSTile tile, Requirement... requirements){
        this(itemId, tile);
        this.requirements.addAll(Arrays.asList(requirements));
    }

    public boolean pickUpItem(){
        if (requirements.stream().anyMatch(r -> !r.check())) {
            General.println("[GroundItemStep]: We failed a requirement to execute this step");
            return false;
        }
        if (this.locationTile != null){
            if (!this.locationTile.isOnScreen()){
                General.println("[GroundItemStep] Navigating to ground item");
                if (PathingUtil.localNavigation(locationTile)){
                    PathingUtil.movementIdle();
                } else if (PathingUtil.walkToTile(locationTile, 2, false)){
                    Timer.waitCondition(()-> this.locationTile.isOnScreen(), 6000, 7000);
                }
            }
        }
        General.println("[GroundItemStep] Picking up ground Item");
        return itemId != -1 ?  Utils.clickGroundItem(itemId) : Utils.clickGroundItem(itemName);
    }

    @Override
    public void execute() {
        this.pickUpItem();
    }

    @Override
    public void addDialogStep(String... dialog) {

    }
}
