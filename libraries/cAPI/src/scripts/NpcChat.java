package scripts;

import dax.walker.utils.AccurateMouse;
import dax.walker.utils.camera.DaxCamera;
import dax.walker_engine.WaitFor;
import dax.walker_engine.interaction_handling.NPCInteraction;
import dax.walker_engine.local_pathfinding.Reachable;
import org.tribot.api.General;
import org.tribot.api2007.Game;
import org.tribot.api2007.NPCChat;
import org.tribot.api2007.NPCs;
import org.tribot.api2007.Player;
import org.tribot.api2007.types.RSNPC;
import org.tribot.script.sdk.ChatScreen;
import org.tribot.script.sdk.Log;
import org.tribot.script.sdk.MyPlayer;
import org.tribot.script.sdk.Waiting;
import org.tribot.script.sdk.query.Query;
import org.tribot.script.sdk.types.LocalTile;
import org.tribot.script.sdk.types.Npc;

import java.util.*;


public class NpcChat extends NPCChat {

    private static Reachable reachable = new Reachable();

    public static boolean talkToNPC(String npcName) {
        Optional<Npc> npc = Query.npcs()
                .nameContains(npcName).findClosestByPathDistance();

        RSNPC[] targetNPC = NPCs.findNearest(npcName);
        return targetNPC.length > 0 && talkToNPC(targetNPC[0].getID());
    }

    public static boolean talkToNPC(RSNPC npc) {
        return talkToNPC(npc, "Talk-to");
    }

    public static boolean handle(boolean waitForChat, String... options){
        if (waitForChat)
            waitForChatScreen();

        return handle(options);
    }


    public static boolean handle(String... options){
        ChatScreen.setConfig(ChatScreen.Config.builder()
                .holdSpaceForContinue(true)
                .useKeysForOptions(true)
                .build());
        return ChatScreen.handle(options);
    }

    public static void handleChat(List<String> options) {
        Log.info("Handling... " + List.of(options));
        List<String> blackList = new ArrayList();
        int limit = 0;
        ChatScreen.setConfig(ChatScreen.Config.builder()
                .holdSpaceForContinue(true)
                .useKeysForOptions(true)
                .build());
        while (limit++ < 50) {
            if (WaitFor.condition(General.random(650, 800), () -> {
                return ChatScreen.isOpen() ? WaitFor.Return.SUCCESS : WaitFor.Return.IGNORE;
            }) != WaitFor.Return.SUCCESS) {
                Log.info("Conversation window not up.");
                break;
            }

            if (ChatScreen.isClickContinueOpen()) {
                ChatScreen.clickContinue();
                limit = 0;
            } else {
                List<String> selectableOptions = ChatScreen.getOptions();
                //List<RSInterface> selectableOptions = getAllOptions(options);
                if (selectableOptions != null && selectableOptions.size() != 0) {
                    Iterator var4 = selectableOptions.iterator();

                    while (var4.hasNext()) {
                        String selected = (String) var4.next();
                        if (!blackList.contains(selected)) {
                            General.sleep(General.randomSD(150, 1500,
                                    425, 130));
                            Log.info("Replying with option: " + selected);
                            blackList.add(selected);
                            ChatScreen.selectOption(selected);
                            waitForNextOption();
                            limit = 0;
                            break;
                        }
                    }

                    General.sleep(20, 40);
                } else {
                    Waiting.wait(150);
                }
            }
        }

        if (limit > 50) {
            Log.warn("Reached conversation limit.");
        }

    }

    private static void waitForNextOption() {
        Optional<String> message = ChatScreen.getMessage();
        if (message.isPresent()) {
            Waiting.waitUntil(5000, 50, () ->
                    ChatScreen.getMessage().map(m -> !m.equals(message.get())).orElse(false));
        } else { //assumes we were currently on an option page
            Waiting.waitUntil(5000, 50, () -> ChatScreen.isClickContinueOpen());
        }
    }

    public static boolean waitForChatScreen() {
        int timeout = Game.isRunOn() ? 8000 : 13000;
        return Waiting.waitUntil(timeout, 325, () -> ChatScreen.isOpen());
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
                        NpcChat.waitForChatScreen()) {
                    return true;
                }
            }
            for (int i = 0; i < 3; i++) {
                Log.log("[NpcChat]: Distance to npc is " + targetNPC.getPosition().distanceTo(Player.getPosition()));
                // if we're right beside the npc reachable gives an issue so we check if were right beside them first
                if (AccurateMouse.click(targetNPC, interactionString) &&
                        NpcChat.waitForChatScreen())
                    return true;

                if (AccurateMouse.click(targetNPC, interactionString) &&
                        NpcChat.waitForChatScreen())
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
                            NpcChat.waitForChatScreen()) {
                        Log.log("[NpcChat]: Success");
                        return true;
                    }
                }
                Waiting.waitUniform(1500, 2500);
            }
        }
        return false;
    }


    public static boolean talkToNPC(Optional<Npc> targetNPC, String interactionString) {
        //no target
        if (targetNPC.isEmpty())
            return false;

        // Npc is > 5 tiles and isn't visible, so we try walking
        if (targetNPC.map(t -> t.distance() > 5 && !t.isVisible()).orElse(false)) {
            Optional<LocalTile> walkable = Utils.getWalkableTile(targetNPC.get().getTile().toLocalTile());
            if (walkable.map(PathingUtil::localNav).orElse(false)) {
                Waiting.waitUntil(7500, 350,
                        () -> targetNPC.get().getTile().distance() < 5);
            } else if (PathingUtil.walkToTile(targetNPC.get().getTile())) {
                Waiting.waitUntil(7500, 350,
                        () -> targetNPC.get().getTile().distance() < 5);
            }
        } else if (targetNPC.get().getTile().distance() < 5 &&
                targetNPC.get().interact(interactionString) &&
                NpcChat.waitForChatScreen()) {
            return true;
        }

        for (int i = 0; i < 3; i++) {
            Log.info("[NpcChat]: Distance to npc is " + targetNPC.get().distance());
            // if we're right beside the npc reachable gives an issue so we check if were right beside them first
            if (interactionString.equalsIgnoreCase("attack")) {
                return targetNPC.get().interact(interactionString);
            } else if (targetNPC.get().interact(interactionString) &&
                    NpcChat.waitForChatScreen()) {
                return true;
            } else {
                Log.log("[Debug]: Cannot reach npc or failed to click, trying to walk");

                if (PathingUtil.walkToTile(targetNPC.get().getTile())) {
                    //  || PathingUtil.localNavigation(npc.getPosition())) {
                    Log.log("[NpcChat]: Movement true");
                    PathingUtil.movementIdle();
                    Timer.slowWaitCondition(() -> targetNPC.get().isVisible(), 8000, 10000);
                    Waiting.waitUniform(1500, 2500);
                }
                Log.log("[NpcChat]: Attempting to Accurate click NPC");
                if (targetNPC.get().interact(interactionString) &&
                        NpcChat.waitForChatScreen()) {
                    Log.info("[NpcChat]: Success");
                    return true;
                }
            }
            Waiting.waitUniform(500, 1500);
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
                    NpcChat.waitForChatScreen()) {
                return true;
            }
        }
        for (int i = 0; i < 3; i++) {
            Log.log("[NpcChat]: Distance to npc is " + targetNPC.getTile().distanceTo(MyPlayer.getPosition()));
            // if we're right beside the npc reachable gives an issue so we check if were right beside them first
            if (targetNPC.interact(interactionString) &&
                    NpcChat.waitForChatScreen())
                return true;

            if (targetNPC.interact(interactionString) &&
                    NpcChat.waitForChatScreen())
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
                        NpcChat.waitForChatScreen()) {
                    Log.log("[NpcChat]: Success");
                    return true;
                }
            }
            Waiting.waitUniform(1500, 2500);

        }
        return false;
    }

}
