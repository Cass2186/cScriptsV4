package scripts.QuestSteps;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import lombok.Getter;
import org.tribot.script.sdk.MyPlayer;
import org.tribot.script.sdk.query.Query;
import org.tribot.script.sdk.types.Area;
import org.tribot.script.sdk.types.GroundItem;
import org.tribot.script.sdk.types.WorldTile;
import scripts.QuestSteps.Choice.DialogChoiceStep;
import scripts.QuestSteps.Choice.DialogChoiceSteps;
import scripts.Requirements.ItemReq;
import scripts.Requirements.ItemRequirement;
import scripts.Requirements.Requirement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class DetailedQuestStep implements QuestStep {

    @Getter
    protected DialogChoiceSteps choices = new DialogChoiceSteps();


    @Getter
    protected final List<Requirement> requirements = new ArrayList<>();

    private final List<WorldTile> markedTiles = new ArrayList<>();

    protected Multimap<WorldTile, Integer> tileHighlights = ArrayListMultimap.create();


    public void addRequirement(Requirement requirement)
    {
        requirements.add(requirement);
    }

    public void addRequirement(Requirement... requirements)
    {
        this.requirements.addAll(Arrays.asList(requirements));
    }

    public void addItemRequirements(List<ItemReq> requirement)
    {
        requirements.addAll(requirement);
    }

    public void emptyRequirements()
    {
        requirements.clear();
    }

    public void setRequirements(List<Requirement> newRequirements) {
        requirements.clear();
        requirements.addAll(newRequirements);
    }

    public void addSafeSpots(WorldTile... worldPoints) {
        for (WorldTile worldPoint : worldPoints) {
            markedTiles.add(worldPoint);
        }
    }

    protected void addItemTiles(Collection<Requirement> requirements) {
        if (requirements == null || requirements.isEmpty()) {
            return;
        }
        List<WorldTile> tiles = Area.fromRadius(MyPlayer.getPosition(), 35).getAllTiles();
        for (WorldTile tile : tiles) {
            List<GroundItem> items = Query.groundItems().tileEquals(tile).toList();
            if (items != null) {
                for (GroundItem item : items) {
                    if (item == null) {
                        continue;
                    }
                    for (Requirement requirement : requirements) {
                        if (isValidRequirementForTileItem(requirement, item)) {
                            tileHighlights.get(tile).add(item.getId());
                            break;
                        }
                    }
                }
            }
        }
    }
    private boolean isValidRequirementForTileItem(Requirement requirement, GroundItem item) {
        return isItemRequirement(requirement) && requirementMatchesTileItem((ItemRequirement) requirement, item);
    }

    private boolean isItemRequirement(Requirement requirement) {
        return requirement != null && requirement.getClass() == ItemRequirement.class;
    }

    private boolean requirementMatchesTileItem(ItemRequirement requirement, GroundItem item) {
        return requirementIsItem(requirement) && requirementContainsID(requirement, item.getId());
    }

    private boolean requirementIsItem(ItemRequirement requirement) {
        return requirement.isActualItem();
    }

    private boolean requirementContainsID(ItemRequirement requirement, int id) {
        return requirement.getAllIds().contains(id);
    }

    private boolean requirementContainsID(ItemRequirement requirement, Collection<Integer> ids) {
        return ids.stream().anyMatch(id -> requirementContainsID(requirement, id));
    }

    private boolean isReqValidForHighlighting(Requirement requirement, Collection<Integer> ids) {
        return isItemRequirement(requirement)
                && requirementIsItem((ItemRequirement) requirement)
                && requirementContainsID((ItemRequirement) requirement, ids)
                && !requirement.check();
    }


    public void addDialogStepWithExclusion(String choice, String exclusionString) {
        choices.addDialogChoiceWithExclusion(new DialogChoiceStep( choice), exclusionString);
    }

    public void addDialogStepWithExclusions(String choice, String... exclusionString) {
        choices.addDialogChoiceWithExclusions(new DialogChoiceStep( choice), exclusionString);
    }

    @Override
    public void execute() {

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
