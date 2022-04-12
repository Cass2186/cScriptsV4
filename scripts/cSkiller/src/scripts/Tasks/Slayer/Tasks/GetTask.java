package scripts.Tasks.Slayer.Tasks;

import dax.walker.utils.AccurateMouse;
import dax.walker.utils.camera.DaxCamera;
import dax.walker_engine.interaction_handling.NPCInteraction;
import org.tribot.api.DynamicClicking;
import org.tribot.api.General;
import org.tribot.api2007.*;
import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSItem;
import org.tribot.api2007.types.RSNPC;
import org.tribot.api2007.types.RSTile;
import org.tribot.script.sdk.ChatScreen;
import org.tribot.script.sdk.Log;
import org.tribot.script.sdk.Waiting;
import org.tribot.script.sdk.query.Query;
import org.tribot.script.sdk.types.EquipmentItem;
import org.tribot.script.sdk.types.InventoryItem;
import scripts.*;
import scripts.API.Priority;
import scripts.API.Task;
import scripts.Data.SkillTasks;
import scripts.Data.Vars;
import scripts.Tasks.Slayer.SlayerConst.Areas;
import scripts.Tasks.Slayer.SlayerConst.Assign;
import scripts.Tasks.Slayer.SlayerConst.SlayerConst;
import scripts.Tasks.Slayer.SlayerUtils.SlayerVars;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class GetTask implements Task {

    RSItem[] equipedWeapon;
    int[] NINES = {9, 19, 29, 39, 49, 59, 69, 79, 89, 99, 109, 119, 129, 139,
            149, 159, 169, 179, 189, 199, 209, 219, 229, 239, 249, 259, 269, 279, 289, 299, 309,
            319, 329};
    List<Integer> l1 = new ArrayList<Integer>();
    List<Integer> ninesList = new ArrayList<Integer>();

    String[] initialTaskStrings = {
            "Who are you?",
            "What's a slayer?",
            "Wow, can you teach me?",
            "Okay, great!"
    };

    public static void handleCompleteMessage(String message) {
        if (message != null && message.toLowerCase().contains("you have completed your task")) {
            General.println("[Message Listener]: Completed task message");
            SlayerVars.get().targets = null;
            String msg = General.stripFormatting(message);
            SlayerVars.get().remainingKills = 0;
            SlayerVars.get().fightArea = null;
            SlayerVars.get().assignment = null;
            // CannonHandler.pickupCannon();
            SlayerVars.get().cannon_location = null;
        }
    }
//<col=ef1020>You've completed 15 tasks and received 0 points, giving you a total of 24; return to a scripts.Slayer master.</col>

    public static void handleNewTaskMessage(String message) {
        if (message.contains("You need something new to hunt.")) {
            General.println("[Message Listener]: We need a new task");
            SlayerVars.get().targets = null;
            SlayerVars.get().getTask = true;
            SlayerVars.get().shouldPrayMelee = false;
            SlayerVars.get().shouldPrayMagic = false;
            SlayerVars.get().fightArea = null;
            SlayerVars.get().usingSpecialItem = false;
            SlayerVars.get().cannon_location = null;
            SlayerVars.get().assignment = null;
            //CannonHandler.pickupCannon();
        }
    }


    public static void getTaskNum(String message) {
        String strippedMsg = General.stripFormatting(message);
        if (strippedMsg != null) {
            String[] splitOne = strippedMsg.split(" tasks in a row");
            if (splitOne.length >= 2) {
                General.println("[Debug] splitone[0] " + splitOne[0]);
                String[] splitTwo = splitOne[0].split("completed ");

                if (splitTwo.length >= 2)
                    SlayerVars.get().taskStreak = Integer.parseInt(splitTwo[1]);

            }
        }
    }

    public static Assign getAssignmentFromString(String string) {
        General.println("Getting assignment from string " + string);
        for (Assign agn : Assign.values()) {
            //  Log.log("Getting assignment from string");
            for (String s : agn.getNameList()) {
                if (s.equalsIgnoreCase(string)) {
                    SlayerVars.get().assignment = agn;
                    return agn;
                }
                // Log.log("assignment" + s);
                if (s.toLowerCase().contains(string.toLowerCase())) {
                    if (agn.getNameNotContainsList() != null &&
                            !agn.getNameNotContainsList().get(0).toLowerCase().contains(string.toLowerCase())) {
                        Log.log("[GetTask]: returning assignment" + s);
                        SlayerVars.get().assignment = agn;
                        return agn;
                    } else {
                        Log.log("[GetTask]: returning assignment" + s);
                        SlayerVars.get().assignment = agn;
                        return agn;
                    }
                }
            }
        }
        General.println("Getting assignment from string returning null (" + string + ")");
        return null;
    }

    public static void handleTaskMessage(String message) {
        getTaskNum(message);
        if (message.contains("You're assigned to kill")) {
            String msg = General.stripFormatting(message);
            if (msg != null) {
                General.println("[Message Listener]: We already have a task");
                String[] msgArray = General.stripFormatting(msg).split("kill ");
                Log.log("msg Array length  " + msgArray.length);
                if (msgArray.length > 0) {
                    String currentTask1 = msgArray[1];
                    String[] targArray = currentTask1.split("; only ");
                    Log.log("Targ Array length  " + targArray.length);
                    if (targArray.length > 1) {
                        Log.log("Targ Array[0] " + targArray[0].substring(0, targArray[0].length() - 1));
                        Assign assign = getAssignmentFromString(targArray[0].substring(0, targArray[0].length() - 1));

                        if (assign != null) {
                            Log.log("[GetTask]: assignment identified");
                            SlayerVars.get().targets = assign.getNameList().toArray(String[]::new);
                            SlayerVars.get().assignment = assign;
                        }
                        General.println("[Debug]: TargArray[1] = " + targArray[1]);
                        String s = targArray[1];
                        SlayerVars.get().remainingKills = Integer.parseInt(s.split(" more")[0]);

                        General.println("[Debug]: Current Task : " + SlayerVars.get().targets[0] +
                                " || only " + SlayerVars.get().remainingKills + " left");
                        General.sleep(General.randomSD(500, 3000, 1500, 250));
                        // SlayerVars.get().npcItemID = GetTask.convertTaskToNPCImageId(SlayerVars.get().targets[0]);
                        SlayerBank.checkSpecial();
                    }
                }
            }
        }
    }

    public static void checkGem() {
        for (int i = 0; i < 3; i++) {
            SlayerVars.get().status = "Checking task";
            Optional<InventoryItem> gem = Query.inventory()
                    .idEquals(ItemID.ENCHANTED_GEM)
                    .findClosestToMouse();

            Optional<EquipmentItem> helms = Query.equipment()
                    .idEquals(ItemID.SLAYER_HELMET, ItemID.SLAYER_HELMET_I)
                    .findFirst();

            if (helms.map(h -> h.click("Check")).orElse(false)) {
                if (Waiting.waitUntil(1500, () -> SlayerVars.get().targets != null))
                    Log.debug("Targs no longer null");
                return;

            } else if (gem.isEmpty()) {
                if (BankManager.open(true)) {
                    BankManager.depositAll(true);
                    BankManager.withdraw(1, true, ItemID.ENCHANTED_GEM);
                    BankManager.close(true);
                }
            }
            gem = Query.inventory()
                    .idEquals(ItemID.ENCHANTED_GEM)
                    .findClosestToMouse();
            if (gem.map(g -> g.click("Check")).orElse(false)) {
                if (Waiting.waitUntil(1500, () -> SlayerVars.get().targets != null))
                    Log.debug("Targs no longer null");
                return;

            }
        }

        SlayerVars.get().needsGem = true;
        Log.error("Check Gem Failed");
    }

    RSArea NIEVE_AREA = new RSArea(new RSTile(2433, 3423, 0), 4);

    public void getTaskNieve() {
        if (SlayerVars.get().getTask) {
            //   CannonHandler.pickupCannon();
            PathingUtil.walkToArea(NIEVE_AREA, false);
            // goToNieve();
            RSNPC[] nieve = NPCs.findNearest("Nieve");
            if (nieve.length > 0) {

                if (!nieve[0].isClickable())
                    nieve[0].adjustCameraTo();

                if (AccurateMouse.click(nieve[0], "Assignment")) {
                    NPCInteraction.waitForConversationWindow();
                    Timer.waitCondition(() -> Interfaces.get(231, 4) != null, 15000);
                    General.sleep(General.random(500, 3000));
                    SlayerVars.get().shouldBank = true;
                    General.println("SlayerVars.get().shouldBank = " + SlayerVars.get().shouldBank, Color.RED);
                    determineTask();
                    if (NPCInteraction.isConversationWindowUp()) {
                        NPCInteraction.handleConversation(initialTaskStrings);
                    }
                }
                SlayerVars.get().shouldBank = true;
                SlayerVars.get().getTask = false;
                General.println("SlayerVars.get().shouldBank = " + SlayerVars.get().shouldBank, Color.RED);
            }
        }
    }

    // public static int convertTaskToNPCImageId(String task) {
    //      int i = Task.getNPCItemID(task);
    //      return i;
    //  }

    public void getDramenStaff() {
        if (Equipment.find(ItemID.DRAMEN_STAFF).length < 1) {
            BankManager.open(true);
            BankManager.depositAll(true);
            BankManager.withdraw(1, true, ItemID.ENCHANTED_GEM);
            BankManager.withdraw(1, true, ItemID.DRAMEN_STAFF);
            BankManager.withdraw(1, true, ItemID.LUMBRIDGE_TELEPORT);
            BankManager.withdraw(1, true, ItemID.VARROCK_TELEPORT);
            BankManager.close(true);

            equipedWeapon = Equipment.find(Equipment.SLOTS.WEAPON);
            Utils.equipItem(ItemID.DRAMEN_STAFF);
            Utils.shortSleep();
        }
    }

    private void determineTask() {
        try {
            Optional<String> taskOptional = ChatScreen.getMessage();
            if (taskOptional.isPresent()) {
                String[] task = taskOptional.get().split("kill ");
                General.println("[GetTask]: Split to get task = " + task[1]);
                String[] t = task[1].split(" ", 1);
                General.println("[GetTask]: Split to get t = " + t[0] + "|| Length = " + t.length);
                String[] potentialNum = t[0].split(" ");
                General.println("[GetTask]: Split to get potential num = " + potentialNum[0]);
                int num = Integer.parseInt(potentialNum[0]);
                String tsk = potentialNum[1].trim();

                tsk = tsk.replace(".", "");
                General.println("[Get Task]: We are assigned to kill " + tsk + " x" + num);
                SlayerVars.get().targets = new String[]{tsk.toLowerCase()};
                SlayerVars.get().remainingKills = num;
                SlayerVars.get().shouldBank = true;
                General.println("[Debug]: Current Task : " + SlayerVars.get().targets.toString());
                SlayerVars.get().fightArea = null;
                General.sleep(General.randomSD(500, 3000, 1500, 250));
                checkGem();
            } else {
                General.println("[GetTask]: failed to split string");
                checkGem();
            }
        } catch (Exception e) {
            SlayerVars.get().targets = null;
            General.println("[Debug]: Failed to read task dialogue");
            checkGem();
        }


    }

    public void getTaskChaeldar() {
        if (SlayerVars.get().getTask) {
            getDramenStaff();
            if (!Areas.WHOLE_ZANARIS.contains(Player.getPosition())) {
                General.println("[Debug]: Getting Task.");
                PathingUtil.walkToArea(Areas.SWAMP_AREA);
                if (Utils.clickObject(2406, "Open", false)) {
                    Timer.waitCondition(() -> Areas.WHOLE_ZANARIS.contains(Player.getPosition()), 7000, 12000);
                    Utils.modSleep();
                }
            }
            if (Areas.WHOLE_ZANARIS.contains(Player.getPosition())) {
                PathingUtil.walkToArea(Areas.CHAELDAR_AREA);

                RSNPC[] chaeldar = NPCs.findNearest("Chaeldar");
                if (chaeldar.length > 0) {

                    if (!chaeldar[0].isClickable())
                        chaeldar[0].adjustCameraTo();
                    for (int i = 0; i < 3; i++) {
                        if (DynamicClicking.clickRSNPC(chaeldar[0], "Assignment")) {
                            NPCInteraction.waitForConversationWindow();
                            Timer.waitCondition(() -> Interfaces.get(231, 4) != null, 15000);
                            if (NPCInteraction.isConversationWindowUp()) {
                                NPCInteraction.handleConversation(initialTaskStrings);
                            }
                            break;
                        }
                    }
                }
                determineTask();
                if (equipedWeapon != null && equipedWeapon.length > 0) {
                    RSItem[] invWeapon = Inventory.find(equipedWeapon[0].getID());
                    Utils.shortSleep();
                    if (invWeapon.length > 0)
                        invWeapon[0].click("Wield");
                }
            }
            SlayerVars.get().shouldBank = true;
            //Bank.checkSpecial();
        }
    }

    public void getTaskTurael() {
        if (SlayerVars.get().getTask) {
            RSItem[] invitem = Inventory.find(ItemID.GAMES_NECKLACE);
            if (invitem.length < 1) {
                if (!Areas.TURAEL_AREA.contains(Player.getPosition())) {
                    BankManager.open(true);

                    if (Inventory.getAll().length > 25)
                        BankManager.depositAll(true);

                    BankManager.checkEquippedGlory();
                    if (!BankManager.withdraw(1, true, ItemID.GAMES_NECKLACE))
                        SlayerVars.get().shouldRestock = true;
                    BankManager.withdraw(1, true, ItemID.VARROCK_TELEPORT);
                    BankManager.close(true);
                    General.println("[Debug]: Getting Task From Turael.");

                }
            }
            if (Inventory.find(ItemID.GAMES_NECKLACE).length > 0) {
                PathingUtil.walkToArea(Areas.TURAEL_AREA);
                RSNPC[] turael = NPCs.findNearest("Turael");
                if (turael.length > 0) {

                    if (!turael[0].isClickable())
                        turael[0].adjustCameraTo();

                    if (AccurateMouse.click(turael[0], "Assignment")) {
                        NPCInteraction.waitForConversationWindow();
                        Timer.waitCondition(() -> Interfaces.get(231, 4) != null, 15000);
                        General.sleep(General.random(1000, 3000));
                        SlayerVars.get().shouldBank = true;
                        determineTask();
                        if (NPCInteraction.isConversationWindowUp()) {
                            NPCInteraction.handleConversation(initialTaskStrings);
                        }
                    }
                }
                SlayerVars.get().shouldBank = true;
            }
        }
        SlayerVars.get().shouldBank = true;
    }


    public void getTaskVannaka() {
        if (SlayerVars.get().getTask) {
            General.println("[Debug]: Going to get task (Vannaka).");

            if (!Areas.VANNAKA_AREA.contains(Player.getPosition()))
                PathingUtil.walkToArea(Areas.VANNAKA_AREA);


            RSNPC[] vannaka = NPCs.findNearest("Vannaka");
            if (vannaka.length > 0) {

                if (!vannaka[0].isClickable())
                    DaxCamera.focus(vannaka[0]);

                if (AccurateMouse.click(vannaka[0], "Assignment")) {
                    NPCInteraction.waitForConversationWindow();
                    Timer.waitCondition(() -> Interfaces.get(231, 4) != null, 15000);
                    General.sleep(General.random(1000, 3000));

                    if (Interfaces.get(231, 4) != null) {
                        try {
                            String[] task = Interfaces.get(231, 4).getText().split("kill");
                            if (task.length > 0) {
                                SlayerVars.get().targets = new String[]{task[0].split(";")[1]};
                                General.println("[Debug]: Current Task : " + SlayerVars.get().targets[0]);
                                General.sleep(General.randomSD(500, 3000, 1500, 250));
                            }
                        } catch (Exception e) {
                            SlayerVars.get().targets = null;
                            General.println("[Debug]: Failed to read task dialogue");
                            checkGem();
                        }
                    }
                    if (NPCInteraction.isConversationWindowUp()) {
                        NPCInteraction.handleConversation(initialTaskStrings);
                    }
                }
                SlayerVars.get().shouldBank = true;
                General.println("SlayerVars.get().shouldBank = " + SlayerVars.get().shouldBank, Color.RED);
            }
        }
    }

    public void skipTask() {

    }


    public static boolean afk(int min, int max) {
        if (SlayerVars.get().shouldAfk) {
            SlayerVars.get().status = "AFKing";
            Utils.afk(General.random(min, max));
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "Getting task";
    }

    @Override
    public Priority priority() {
        return Priority.MEDIUM;
    }


    @Override
    public void execute() {
        General.println("[GetTask]: Node validated");
        checkGem();
        //  CannonHandler.pickupCannon();

        if (SlayerVars.get().assignment != null &&
                SlayerVars.get().assignment.equals(Assign.GREEN_DRAGON))
            throw new NullPointerException();

        if (SlayerVars.get().targets == null) { // checks again in case checkGem() returns a target
            SlayerVars.get().status = "Getting a Task.";

            // if (SlayerVars.get().use_cannon)
            //    CannonHandler.pickupCannon();

            AttackNpc.setPrayer();

            if (!SlayerVars.get().pointBoosting && Utils.getCombatLevel() >= 89 || (Equipment.isEquipped(
                    ItemID.SLAYER_HELMET) && Utils.getCombatLevel() > 84)) {
                getTaskNieve();
                SlayerVars.get().shouldBank = true;
                General.println("SlayerVars.get().shouldBank = " + SlayerVars.get().shouldBank, Color.RED);
                SlayerVars.get().shouldSkipTask = false;

            } else if (Equipment.isEquipped(
                    ItemID.SLAYER_HELMET) && Utils.getCombatLevel() > 70) {
                getTaskChaeldar();
                SlayerVars.get().shouldBank = true;
                General.println("SlayerVars.get().shouldBank = " + SlayerVars.get().shouldBank, Color.RED);
                SlayerVars.get().shouldSkipTask = false;
            } else if (SlayerVars.get().pointBoosting) {
                General.println("[GetTask]: Point boosting -  Task number is " + Utils.getVarBitValue(SlayerConst.TASK_STREAK_VARBIT));

                for (int i = 0; i < NINES.length; i++) {
                    if (Utils.getVarBitValue(SlayerConst.TASK_STREAK_VARBIT) == NINES[i] ||
                            Utils.getVarBitValue(SlayerConst.TASK_STREAK_VARBIT) == 9) {
                        if (Utils.getCombatLevel() >= 85)
                            getTaskNieve();

                        else if (Utils.getCombatLevel() >= 70 &&
                                Game.getSetting(Quests.LOST_CITY.getGameSetting()) >= 6) {
                            General.println("[GetTask]: Need to get a task from Chaeldar");
                            getTaskChaeldar();
                            SlayerVars.get().getTask = false;
                        } else if (Utils.getCombatLevel() >= 65) {
                            getTaskVannaka();
                        } else {
                            getTaskTurael();
                        }
                        SlayerVars.get().getTask = false;
                        SlayerVars.get().shouldBank = true;
                        General.println("SlayerVars.get().shouldBank = " + SlayerVars.get().shouldBank, Color.RED);
                        SlayerVars.get().shouldSkipTask = false;
                        return;
                    }
                }
                getTaskTurael();


                /**
                 NOT POINT BOOSTING
                 **/
            } else if (Utils.getCombatLevel() >= 85)
                getTaskNieve();

            else if (Utils.getCombatLevel() >= 70 && Game.getSetting(Quests.LOST_CITY.getGameSetting()) >= 6)
                getTaskChaeldar();

            else if (Utils.getCombatLevel() >= 55)
                getTaskVannaka();

            else getTaskTurael();


        } else
            getTaskTurael();

        SlayerVars.get().shouldBank = true;
        General.println("SlayerVars.get().shouldBank = " + SlayerVars.get().shouldBank, Color.RED);
        SlayerVars.get().shouldSkipTask = false;
        //cSkiller.safetyTimer.reset();
        afk(15000, 65000);
    }


    @Override
    public boolean validate() {
        return Vars.get().currentTask != null &&
                Vars.get().currentTask.equals(SkillTasks.SLAYER) &&
                SlayerVars.get().targets == null &&
                !SlayerShop.shouldSlayerShop();
    }


    @Override
    public String taskName() {
        return "Slayer";
    }
}
