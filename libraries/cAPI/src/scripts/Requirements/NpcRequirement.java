package scripts.Requirements;

import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSTile;
import org.tribot.script.sdk.query.Query;
import org.tribot.script.sdk.types.Area;
import org.tribot.script.sdk.types.Npc;
import org.tribot.script.sdk.types.WorldTile;
import scripts.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class NpcRequirement implements Requirement{

    private final int npcID;
    private final RSArea area;
    private  Area zone;
    private final String displayText;
    private final boolean checkNotInZone;

    /**
     * Check for the existence of an NPC within your canvas.
     *
     * @param displayText the display text
     * @param npcID the NPC to check for
     */
    public NpcRequirement(String displayText, int npcID)
    {
        this(displayText, npcID, false, null);
    }

    /**
     * Check if a given NPC is in a specified {@link RSArea}.
     *
     * @param displayText the display text
     * @param npcID the {@link Npc} to check for
     * @param RSTile the location to check for the NPC
     */
    public NpcRequirement(String displayText, int npcID, RSTile RSTile)
    {

        this(displayText, npcID, false, new RSArea(RSTile, 1));

    }

    /**
     * Check if a given NPC is in a specified {@link RSArea}.
     *
     * @param displayText the display text
     * @param npcID the {@link Npc} to check for
     * @param zone the zone to check.
     */
    public NpcRequirement(String displayText, int npcID, RSArea zone)
    {
        this(displayText, npcID, false, zone);
    }

    /**
     * Check if a given NPC is in a specified {@link RSArea}.
     * <br>
     * If {@param checkNotInZone} is true, this will check if the NPC is NOT in the zone.
     *
     * @param displayText the display text
     * @param npcID the {@link Npc} to check for
     * @param checkNotInZone determines whether to check if the NPC is in the zone or not
     * @param zone the zone to check.
     */
    public NpcRequirement(String displayText, int npcID, boolean checkNotInZone, RSArea area)
    {
        this.displayText = displayText;
        this.npcID = npcID;
        this.area = area;
        this.zone = Utils.getAreaFromRSArea(this.area);
        this.checkNotInZone = checkNotInZone;
    }

    @Override
    public boolean check()
    {
        List<Npc> found = Query.npcs().stream().filter(npc -> npc.getId() == npcID)
                        .collect(Collectors.toList());


        if (!found.isEmpty()) {
            if (this.zone == null){
                this.zone = Utils.getAreaFromRSArea(this.area);
            }
            if (zone != null) {
                for(Npc npc : found) {
                    WorldTile npcLocation = npc.getTile();
                    if (npcLocation != null) {
                        boolean inZone = zone.contains(npcLocation);
                        return inZone && !checkNotInZone || (!inZone && checkNotInZone);
                    }
                }
            }
            return true; // the NPC exists, and we aren't checking for its location
        }
        return false; // npc not in scene
    }

}
