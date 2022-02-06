package scripts.Requirements.Conditional;

import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSNPC;
import org.tribot.api2007.types.RSTile;
import org.tribot.script.sdk.query.Query;
import org.tribot.script.sdk.types.LocalTile;
import org.tribot.script.sdk.types.Npc;
import scripts.EntitySelector.Entities;
import scripts.Utils;

import java.util.ArrayList;


public class NpcCondition extends ConditionForStep {
    private final int npcID;
    private final ArrayList<Npc> npcs = new ArrayList<>();
    private boolean npcInScene = false;
    private final RSArea zone;

    public NpcCondition(int npcID) {
        this.npcID = npcID;
        this.zone = null;
    }

    public NpcCondition(int npcID, RSTile worldPoint) {
        this.npcID = npcID;
        this.zone = new RSArea(worldPoint, worldPoint);
    }

    public NpcCondition(int npcID, RSArea zone) {
        this.npcID = npcID;
        this.zone = zone;
    }

    //@Override
    public void initialize() {

        for (Npc npc : Query.npcs().sortedByDistance().toList()) {
            if (npcID == npc.getId()) {
                this.npcs.add(npc);
                npcInScene = true;
            }
        }
    }

    public boolean check() {
        if (zone != null) {
            for (Npc npc : npcs) {
                if (npc != null) {
                    RSTile wp = Utils.getRSTileFromWorldTile(npc.getTile());

                    if (zone.contains(wp)) {
                        return true;
                    }

                }
            }
            return false;
        } else {
            return npcInScene;
        }
    }

    public void checkNpcSpawned(Npc npc) {
        if (npc.getId() == this.npcID) {
            npcs.add(npc);
            npcInScene = true;
        }
    }

    public void checkNpcDespawned(Npc npc) {
        if (npcs.contains(npc)) {
            npcs.remove(npc);
            npcInScene = false;
        }
    }



    public void updateHandler() {
        npcInScene = false;
    }
}