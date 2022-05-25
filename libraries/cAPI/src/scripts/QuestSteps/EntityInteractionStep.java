package scripts.QuestSteps;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.tribot.script.sdk.query.Query;
import org.tribot.script.sdk.types.Area;
import org.tribot.script.sdk.types.LocalTile;
import org.tribot.script.sdk.types.WorldTile;
import scripts.PathingUtil;
import scripts.Requirements.Requirement;
import scripts.Utils;

import java.util.*;
import java.util.stream.Collectors;

@Builder
public class EntityInteractionStep extends QuestStep {

    private WorldTile worldtile;
    private int entityId;
    private boolean instanced = false;
    private Optional<String> interactionString = Optional.empty();

    @Getter
    private boolean waitUntil = true;

    @Getter
    private List<String> chat = new ArrayList<>();

    @Getter
    private List<Integer> alternateIds = new ArrayList<>();

    @Getter
    private List<Requirement> requirements = new ArrayList<>();

    @Getter
    private List<QuestStep> substeps = new ArrayList<>();


    @Override
    public void addDialogStep(String... dialog) {
        this.chat.addAll(Arrays.stream(dialog).collect(Collectors.toList()));
    }

    @Override
    public void addSubSteps(QuestStep... substep) {

        this.substeps.addAll(Arrays.asList(substep));

    }

    @Override
    public void addSubSteps(Collection<QuestStep> substeps) {
        this.substeps.addAll(substeps);
    }


    public void addAlternateObjects(int... ids) {
        for (int i : ids)
            this.alternateIds.add(i);
    }

    @Override
    public void execute() {
        LocalTile localTile = worldtile.toLocalTile();
        Optional<LocalTile> bestTile = Query.tiles()
                //.isReachable()
                .inArea(Area.fromRadius(localTile, 1))
                .filter(LocalTile::isWalkable)
                .findBestInteractable();

        if (bestTile.map(PathingUtil::localNav).orElse(false) ||
                PathingUtil.walkToTile(worldtile)) {

        } else {

        }

    }

}
