package scripts;

import dax.walker.utils.AccurateMouse;
import dax.walker.utils.camera.DaxCamera;
import dax.walker_engine.interaction_handling.NPCInteraction;
import dax.walker_engine.local_pathfinding.Reachable;
import org.tribot.api2007.NPCChat;
import org.tribot.api2007.NPCs;
import org.tribot.api2007.Player;
import org.tribot.api2007.types.RSNPC;
import org.tribot.script.sdk.Log;
import org.tribot.script.sdk.MyPlayer;
import org.tribot.script.sdk.Waiting;
import org.tribot.script.sdk.query.Query;
import org.tribot.script.sdk.types.Npc;

import java.util.Optional;


public class NpcChat extends NPCChat {

    private static Reachable reachable = new Reachable();

    public static boolean talkToNPC(String npcName) {
        RSNPC[] targetNPC = NPCs.findNearest(npcName);
        return targetNPC.length > 0 && talkToNPC(targetNPC[0].getID());
    }

    public static boolean talkToNPC(RSNPC npc) {
        return talkToNPC(npc, "Talk-to");
    }


    /**
     * MAIN METHOD
     */
    public static boolean talkToNPC(RSNPC targetNPC, String interactionString) {
        if (targetNPC != null) {
            if (!targetNPC.isOnScreen()) {
                if (targetNPC.getPosition().distanceTo(Player.getPosition()) <= 5 &&
                        reachable.canReach(targetNPC.getPosition())) {
                    DaxCamera.focus(targetNPC);
                } else if (PathingUtil.walkToTile(targetNPC.getPosition(), 2, 2)) {
                    Timer.waitCondition(targetNPC::isClickable, 8000);
                } else if (PathingUtil.localNavigation(targetNPC.getPosition())) {
                    PathingUtil.movementIdle();
                }
            } else {
                if (targetNPC.getPosition().distanceTo(Player.getPosition()) <= 4 &&
                        AccurateMouse.click(targetNPC, interactionString) &&
                        NPCInteraction.waitForConversationWindow()) {
                    return true;
                }
            }
            for (int i = 0; i < 3; i++) {
                Log.log("[NpcChat]: Distance to npc is " + targetNPC.getPosition().distanceTo(Player.getPosition()));
                // if we're right beside the npc reachable gives an issue so we check if were right beside them first
                if (AccurateMouse.click(targetNPC, interactionString) &&
                        NPCInteraction.waitForConversationWindow())
                    return true;

                if (AccurateMouse.click(targetNPC, interactionString) &&
                        NPCInteraction.waitForConversationWindow())
                    return true;
                else {
                    Log.log("[Debug]: Cannot reach npc or failed to click, trying to walk");

                    if (PathingUtil.walkToTile(targetNPC.getPosition(), 1, 1)) {
                        //  || PathingUtil.localNavigation(npc.getPosition())) {
                        Log.log("[NpcChat]: Movement true");
                        PathingUtil.movementIdle();
                        Timer.slowWaitCondition(() -> targetNPC.isClickable(), 8000, 10000);
                        Waiting.waitUniform(1500, 2500);
                    }
                    Log.log("[NpcChat]: Attempting to Accurate click NPC");
                    if (AccurateMouse.click(targetNPC, interactionString) &&
                            NPCInteraction.waitForConversationWindow()) {
                        Log.log("[NpcChat]: Success");
                        return true;
                    }
                }
                Waiting.waitUniform(1500, 2500);
            }
        }
        return false;
    }

    public static boolean talkToNPC(int npcId) {
        return talkToNPC(npcId, "Talk-to");
    }

    public static boolean talkToNPC(int npcId, String interactionString) {
        Optional<Npc> npcOptional = Query.npcs().idEquals(npcId).isReachable().findBestInteractable();
        if (npcOptional.isEmpty()) {

            npcOptional = Query.npcs().idEquals(npcId).findBestInteractable();
            if (npcOptional.isEmpty()) return false;
            Log.log("[NPCChat]: Cannot find a reachable npc, walking to npc");
            Npc targetNPC = npcOptional.get();
            if (PathingUtil.walkToTile(targetNPC.getTile()))
                Timer.waitCondition(targetNPC::isVisible, 8000);

            else if (PathingUtil.localNav(targetNPC.getTile()))
                Timer.waitCondition(targetNPC::isVisible, 8000);

        }
        Npc targetNPC = npcOptional.get();
        if (!targetNPC.isVisible()) {
            if (targetNPC.getTile().distanceTo(MyPlayer.getPosition()) <= 5) {
                targetNPC.adjustCameraTo();
            } else if (PathingUtil.walkToTile(targetNPC.getTile())) {
                Timer.waitCondition(targetNPC::isVisible, 8000);
            } else if (PathingUtil.localNav(targetNPC.getTile())) {
                Timer.waitCondition(targetNPC::isVisible, 8000);
            }
        } else {
            if (targetNPC.getTile().distanceTo(MyPlayer.getPosition()) <= 4 &&
                    targetNPC.interact(interactionString) &&
                    NPCInteraction.waitForConversationWindow()) {
                return true;
            }
        }
        for (int i = 0; i < 3; i++) {
            Log.log("[NpcChat]: Distance to npc is " + targetNPC.getTile().distanceTo(MyPlayer.getPosition()));
            // if we're right beside the npc reachable gives an issue so we check if were right beside them first
            if (targetNPC.interact(interactionString) &&
                    NPCInteraction.waitForConversationWindow())
                return true;

            if (targetNPC.interact(interactionString) &&
                    NPCInteraction.waitForConversationWindow())
                return true;
            else {
                Log.log("[Debug]: Cannot reach npc or failed to click, trying to walk");

                if (PathingUtil.walkToTile(targetNPC.getTile())) {
                    Log.log("[NpcChat]: Movement true");
                    PathingUtil.movementIdle();
                    Timer.slowWaitCondition(targetNPC::isVisible, 8000, 10000);
                    Waiting.waitNormal(500, 75);
                }
                Log.log("[NpcChat]: Attempting to Accurate click NPC");
                if (targetNPC.interact(interactionString) &&
                        NPCInteraction.waitForConversationWindow()) {
                    Log.log("[NpcChat]: Success");
                    return true;
                }
            }
            Waiting.waitUniform(1500, 2500);

        }
        return false;
    }

}
