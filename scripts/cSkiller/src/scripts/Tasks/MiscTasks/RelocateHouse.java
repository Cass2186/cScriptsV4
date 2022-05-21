package scripts.Tasks.MiscTasks;

import org.apache.commons.lang3.StringUtils;
import org.tribot.script.sdk.Log;
import org.tribot.script.sdk.Waiting;
import org.tribot.script.sdk.Widgets;
import org.tribot.script.sdk.query.Query;
import org.tribot.script.sdk.types.Area;
import org.tribot.script.sdk.types.Npc;
import org.tribot.script.sdk.types.Widget;
import org.tribot.script.sdk.types.WorldTile;
import scripts.API.Priority;
import scripts.API.Task;
import scripts.PathingUtil;
import scripts.Tasks.Construction.ConsData.HouseLocation;

import java.util.Optional;

public class RelocateHouse implements Task {

    private final int MENU_WIDGET_ID = 187;
    private final int MENU_WIDGET_CHILD_ID = 3;
    private final WorldTile CAMELOT_HOUSE_BUYER_TILE = new WorldTile(2737, 3502, 0);

    private boolean openMenu() {
        if (PathingUtil.walkToArea(Area.fromRadius(CAMELOT_HOUSE_BUYER_TILE, 2),
                false)) {
            Optional<Widget> selectionWidget = Widgets.get(MENU_WIDGET_ID);
            if (selectionWidget.isEmpty()) {
                Log.info("Opening Relocation menu");
                Optional<Npc> agent = Query.npcs().actionContains("Relocate").findBestInteractable();
                if (agent.map(npc -> npc.interact("Relocate")).orElse(false)) {
                    return Waiting.waitUntil(4000, 125,
                            ()-> Widgets.get(MENU_WIDGET_ID).isPresent());
                }
            }
        }
        return Widgets.get(MENU_WIDGET_ID).isPresent();
    }

    private boolean relocate(HouseLocation location) {
        if (location.isSetToThisLocation())
            return true;

        if (openMenu()){
            Optional<Widget> selectionWidget = Query.widgets()
                    .inIndexPath(MENU_WIDGET_ID)
                    .textContains(StringUtils.capitalize(location.toString().toLowerCase()))
                    .findFirst();
            Log.info("Selecting Location");
            if (selectionWidget.map(Widget::click).orElse(false)) {
                return Waiting.waitUntil(2000, 100, location::isSetToThisLocation);
            }
        }
        return location.isSetToThisLocation();
    }

    @Override
    public Priority priority() {
        return Priority.MEDIUM;
    }

    @Override
    public boolean validate() {
        return !HouseLocation.getCurrentHouseLocation().equals(HouseLocation.RIMMINGTON);
    }

    @Override
    public void execute() {
        relocate(HouseLocation.RIMMINGTON);
    }

    @Override
    public String taskName() {
        return "Relocating house";
    }
}
