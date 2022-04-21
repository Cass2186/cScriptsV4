package scripts.Tasks.SorceressGarden;

import org.tribot.script.sdk.Log;
import org.tribot.script.sdk.query.Query;
import org.tribot.script.sdk.types.Npc;
import org.tribot.script.sdk.types.WorldTile;

import java.util.List;
import java.util.Optional;


public class ElementalCollisionDetector {

    public static final WorldTile[] RUNTILES = {
            new WorldTile(2923, 5465, 0), // 0 RSNPC 0
            new WorldTile(2923, 5459, 0), // 1 RSNPC 0
            new WorldTile(2926, 5468, 0), // 2 RSNPC 1, 2, 6
            new WorldTile(2928, 5470, 0), // 3 RSNPC 6
            new WorldTile(2930, 5470, 0), // 4 RSNPC 5, 7
            new WorldTile(2932, 5466, 0), // 5 RSNPC 5, 7
    };

    /**
     * This method returns true if the RSNPC's ID is within the bounds of Spring Elementals
     *
     * @param npc The RSNPC to be tested
     * @return Boolean on if the RSNPC is a Spring Elemental or not
     */
    public static boolean isSpringElemental(Npc npc) {
        return npc.getId() >= 2956 && npc.getId() <= 2963;
    }

    /**
     * This method sorts the inputed NPC array and returns it sorted from lowest ID to largest ID
     * @param npcs The array of NPCs to be sorted
     * @return NPC array that has been sorted
     */
    public static List<Npc> bubbleSort(List<Npc> npcs) {
        int lastPos, index;
        Npc temp;
        for (lastPos = npcs.size() - 1; lastPos >= 0; lastPos--) {
            for (index = 0; index < lastPos; index++) {
                if (npcs.get(index).getId() > npcs.get(index + 1).getId()) {
                    temp = npcs.get(index);
                    npcs.set(index, npcs.get(index + 1));
                    npcs.set(index + 1, temp);
                }
            }
        }
        return npcs;
    }

    /**
     * This method takes a sorted RSNPC array and the index of the tile t obe tested and returns true if the
     * RSNPC related to that tile is in the correct position.
     *
     * @param npc          The sorted incrementing array of Elemental RSNPCs
     * @param runTileIndex The index of the tile that is being tested
     * @return Boolean of if the RSNPC related to the specific tile index is in the correct position
     */
    public boolean correctPosition(List<Npc> npc, int runTileIndex) {
        npc = bubbleSort(npc);
        Npc[] npcs = npc.toArray(Npc[]::new);
        try {
            switch (runTileIndex) {
                case 0:
                    if (npcs[0].getOrientation().getAngle() == 0 && npcs[0].getTile().getY() < 5468)
                        return true;
                    break;
                case 1:
                    if (npcs[0].getOrientation().getAngle()  == 1024 && npcs[0].getTile().getY() > 5465)
                        return true;
                    break;
                case 2:
                    if ((npcs[1].getTile().getX() > 2925 || npcs[1].getTile().getY() > 5461) &&
                            ((npcs[2].getTile().getX() > 2925 && npcs[2].getTile().getY() > 2925) || npcs[2].getTile().getX() > 2926) &&
                            (npcs[6].getOrientation().getAngle() == 1024 && (npcs[6].getTile().getY() < 5472 && npcs[6].getTile().getY() > 5463)))
                        return true;
                    break;
                case 3:
                    if ((npcs[6].getTile().getY() < 5467 || npcs[6].getTile().getY() > 5472) ||
                            (npcs[6].getOrientation().getAngle()  == 0 &&
                            npcs[6].getTile().getY()  <5467 ))//> 5473))
                        return true;
                    break;
                case 4:
                    if ((npcs[5].getOrientation().getAngle()  == 1536 && npcs[5].getTile().getX() > 2928)) //&&
                            /*((npcs[7].getOrientation().getAngle()  == 0 && npcs[7].getTile().getY() > 5472) ||
                                    npcs[7].getOrientation().getAngle()  == 1024)) */return true;
                    break;
                case 5:
                    if (((npcs[5].getOrientation().getAngle()  == 1536 && (npcs[5].getTile().getX() < 2930 || npcs[5].getTile().getX() > 2933)) ||
                            npcs[5].getOrientation().getAngle()  == 512 && npcs[5].getTile().getX() < 2931) &&
                            ((npcs[7].getOrientation().getAngle()  == 0 && npcs[7].getTile().getY() > 5472) || npcs[7].getOrientation().getAngle()  == 1024)) return true;
                    break;
            }
        } catch (Exception e) { return false; }
        return false;
    }
  /*  public boolean correctPosition(List<Npc> npc, int runTileIndex) {
        npc = bubbleSort(npc);
        Npc[] npcs = npc.toArray(Npc[]::new);
        Optional<Npc> elemental_2961 = Query.npcs().idEquals(2961).findClosest(); //replaces 5
        Optional<Npc> elemental_2956 = Query.npcs().idEquals(2956).findClosest(); //replaces 6
        Optional<Npc> elemental_2963 = Query.npcs().idEquals(2963).findClosest();// replaces 7
        Optional<Npc> elemental_2957 = Query.npcs().idEquals(2957).findClosest();
        Optional<Npc> elemental_2958 = Query.npcs().idEquals(2958).findClosest();

        try {
            switch (runTileIndex) {
                case 0:
                    if (npcs[0].getOrientation().getAngle() == 0 && npcs[0].getTile().getY() < 5468)
                        return true; // was > 5460
                    break;
                case 1:
                    if (npcs[0].getOrientation().getAngle() == 1024 && npcs[0].getTile().getY() > 5465) return true;
                    break;
                case 2:
                    if ((npcs[1].getTile().getX() > 2925 || npcs[1].getTile().getY() > 5461) &&
                            ((npcs[2].getTile().getX() > 2925 && npcs[2].getTile().getY() > 2925)
                                    || npcs[2].getTile().getX() > 2926) &&
                            (elemental_2956.get().getOrientation().getAngle() == 1024 &&
                                    (elemental_2956.get().getTile().getY() < 5472 && elemental_2956.get().getTile().getY() > 5463)))
                        return true;
                     if ((elemental_2958.get().getTile().getX() > 2925 || elemental_2958.get().getTile().getY() > 5461) &&
                            ((npcs[2].getTile().getX() > 2925 && npcs[2].getTile().getY() > 2925)
                                    || npcs[2].getTile().getX() > 2926) &&
                            (elemental_2956.get().getOrientation().getAngle() == 1024 &&
                                    (elemental_2956.get().getTile().getY() < 5472 && elemental_2956.get().getTile().getY() > 5463))) {
                         Log.info("RIGHT At CASE 2");
                         return true;
                     }
                    break;
                case 3:
                    if ((elemental_2956.get().getTile().getY() < 5467 || elemental_2956.get().getTile().getY() > 5472)
                            || (elemental_2956.get().getOrientation().getAngle() == 0 && elemental_2956.get().getTile().getY()  < 5467))//> 5473))
                        return true;
                    break;
                case 4:
                    if ((npcs[5].getOrientation().getAngle() == 1536 && npcs[5].getTile().getX() > 2929) &&
                            ((npcs[7].getOrientation().getAngle() == 1024 && npcs[7].getTile().getY() > 5471) ||
                                    npcs[7].getOrientation().getAngle() == 1024)) {
                        Log.info("In rjight position");
                        return true;
                    } else if ((elemental_2961.get().getOrientation().getAngle() == 1536 && elemental_2961.get().getTile().getX() > 2929) &&
                            ((elemental_2963.get().getOrientation().getAngle() == 1024 && elemental_2963.get().getTile().getY() > 5471) ||
                                    elemental_2963.get().getOrientation().getAngle() == 1024))
                        return true;
                    break;
                case 5:
                    if (((elemental_2961.get().getOrientation().getAngle() == 1536 && (elemental_2961.get().getTile().getX() < 2930))// || npcs[5].getTile().getX() > 2932))
                            ||
                            elemental_2961.get().getOrientation().getAngle() == 512 && elemental_2961.get().getTile().getX() < 2931) &&
                            ((elemental_2963.get().getOrientation().getAngle() == 0 && elemental_2963.get().getTile().getY() > 5472) || elemental_2963.get().getOrientation().getAngle() == 1024))
                        return true;
                    break;
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }*/
}
