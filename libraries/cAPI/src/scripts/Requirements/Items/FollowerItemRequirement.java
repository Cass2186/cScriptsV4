package scripts.Requirements.Items;

import org.tribot.api2007.types.RSItem;
import org.tribot.script.sdk.query.Query;
import scripts.Requirements.ItemRequirement;
import scripts.Requirements.Requirement;

import java.util.List;

public class FollowerItemRequirement extends ItemRequirement implements Requirement {

    private final List<Integer> followerIDs;

    public FollowerItemRequirement(String itemName, List<Integer> ItemIDs, List<Integer> followerIDs) {
        super(itemName, ItemIDs);
        this.followerIDs = followerIDs;
    }

    @Override
    public boolean check(boolean checkConsideringSlotRestrictions, List<RSItem> items) {
        boolean match = Query.npcs()
                .isInteractingWithMe()
                .stream()
                .anyMatch(npc -> followerIDs.contains(npc.getId()));

        if (match) {
            return true;
        }

        return super.check( checkConsideringSlotRestrictions, items);
    }
}