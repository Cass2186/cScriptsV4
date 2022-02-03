package scripts.Tasks.Farming.FarmTasks;

import org.tribot.api.General;
import org.tribot.api2007.Interfaces;
import org.tribot.api2007.Login;
import org.tribot.api2007.types.RSInterfaceComponent;
import scripts.Tasks.Farming.Data.Enums.TREES;
import scripts.Tasks.Farming.Data.FarmVars;
import scripts.Tasks.Priority;
import scripts.Tasks.Task;
import scripts.Timer;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Break implements Task {


    public static int determineBreakLengthTree() {
        if (FarmVars.get().treeId == TREES.OAK_SAPPLING.getId()) {
            return TREES.OAK_SAPPLING.getTimeToGrowMs();

        } else if (FarmVars.get().treeId == TREES.WILLOW_SAPPLING.getId()) {
            return TREES.WILLOW_SAPPLING.getTimeToGrowMs();

        } else if (FarmVars.get().treeId == TREES.MAPLE_SAPPLING.getId()) {
            return TREES.MAPLE_SAPPLING.getTimeToGrowMs();

        } else if (FarmVars.get().treeId == TREES.YEW_SAPPLING.getId()) {
            return TREES.YEW_SAPPLING.getTimeToGrowMs();

        } else if (FarmVars.get().treeId == TREES.MAGIC_SAPPLING.getId()) {
            return TREES.MAGIC_SAPPLING.getTimeToGrowMs();
        }

        return 60000; //10 min if not specified
    }


    public void getButton() {
        RSInterfaceComponent[] buttons = Interfaces.get(52, 19).getChildren();

        List ls = Arrays.asList(buttons).stream().filter(b -> b.getActions()
                .toString().contains("Enter")).sorted().collect(Collectors.toList());

        for (int i = 0; i < ls.size(); i++){
            General.println(ls.get(i));
        }
    }


    @Override
    public void execute() {
        FarmVars.get().status = "Waiting for growth";
        if (Login.getLoginState().equals(Login.STATE.INGAME)) {
            if (FarmVars.get().doingTrees && FarmVars.get().treeTimer == null) {
                General.println("[Debug]: Starting tree timer");
              //  FarmVars.get().treeTimer = new Timer(determineBreakLengthTree() + General.random(30000, 120000)); // time for tree + 5-20 min

            } else if (FarmVars.get().doingAllotments && FarmVars.get().allotmentTimer == null) {
                General.println("[Debug]: Starting allotment timer");
              //  FarmVars.get().allotmentTimer = new Timer(General.random(4200000, 4500000)); // 70-75min
//
            } else if (FarmVars.get().herbTimer == null) {
                General.println("[Debug]: Starting herb timer");
               // FarmVars.get().herbTimer = new Timer(General.random(4800000, 5100000)); // 80-85min
            }

            Login.logout();
            Timer.waitCondition(() -> Login.getLoginState().equals(Login.STATE.LOGINSCREEN), 6000, 10000);

        }
        if (Login.getLoginState().equals(Login.STATE.LOGINSCREEN)) {
            General.sleep(General.random(25000, 90000));

        }


        if (FarmVars.get().doingAllotments
                && FarmVars.get().allotmentTimer != null){
               // && !FarmVars.get().allotmentTimer.isRunning()) {
            General.println("[Debug]: Logging in - allotment timer");
            Login.login();
            Move.populatePatchesToVisit();
            FarmVars.get().shouldBank = true;

        } else if (FarmVars.get().doingTrees
                && FarmVars.get().treeTimer != null){
              //  && !FarmVars.get().treeTimer.isRunning()) {
            General.println("[Debug]: Logging in - tree timer");
            Login.login();
            Move.populateTreePatches();
            FarmVars.get().shouldBank = true;
        } else if (FarmVars.get().herbTimer != null ){
             //   && !FarmVars.get().herbTimer.isRunning()) {
            General.println("ending");
            FarmVars.get().scriptStatus = false;
            //  General.println("[Debug]: Logging in - herb timer");
            //  Login.login();
            // Move.populatePatchesToVisit();
            // FarmVars.get().shouldBank = true;
        }
    }

    @Override
    public Priority priority() {
        return Priority.LOW;
    }

    @Override
    public boolean validate() {
        return FarmVars.get().shouldBreak;
    }
}
