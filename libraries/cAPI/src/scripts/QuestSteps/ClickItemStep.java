package scripts.QuestSteps;

import dax.walker_engine.interaction_handling.NPCInteraction;
import lombok.Getter;
import org.tribot.api.General;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Player;
import org.tribot.api2007.types.RSItem;
import org.tribot.api2007.types.RSTile;
import scripts.PathingUtil;
import scripts.Requirements.Requirement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ClickItemStep implements QuestStep{

    private int itemId;
    private String interactionString;
    private RSTile tile;
    private List<String> dialog;


    @Getter
    protected final List<Requirement> requirements = new ArrayList<>();

    public ClickItemStep(int itemId, String interactionString) {
        this.itemId = itemId;
        this.interactionString = interactionString;
        this.tile = Player.getPosition();
    }

    public ClickItemStep(int itemId, String interactionString, Requirement... reqs) {
        this.itemId = itemId;
        this.interactionString = interactionString;
        this.tile = Player.getPosition();
        this.requirements.addAll(Arrays.asList(reqs));
    }

    public ClickItemStep(int itemId, String interactionString, RSTile tile) {
        this.itemId = itemId;
        this.interactionString = interactionString;
        this.tile = tile;
    }

    public ClickItemStep(int itemId, String interactionString, RSTile tile, Requirement... reqs) {
        this.itemId = itemId;
        this.interactionString = interactionString;
        this.tile = tile;
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
    public void execute() {
        if (requirements.stream().anyMatch(r -> !r.check())) {
            General.println("[ClickItemSteps]: We failed a requirement to execute this ClickItemStep");
            return;
        }

        RSItem[] item = Inventory.find(this.itemId);
        if (item.length > 0) {
            if (this.tile != null && PathingUtil.localNavigation(this.tile)) {
                PathingUtil.movementIdle();
            } else if (this.tile != null && PathingUtil.walkToTile(this.tile)){
                PathingUtil.movementIdle();
            }
            if (dialog == null) {
                item[0].click(this.interactionString);

            } else if (item[0].click(this.interactionString)) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation(this.interactionString);

            }
        }

    }

}
