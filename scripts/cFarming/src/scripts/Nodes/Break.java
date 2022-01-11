package scripts.Nodes;

import org.tribot.api.General;
import org.tribot.api2007.Interfaces;
import org.tribot.api2007.Login;
import org.tribot.api2007.types.RSInterfaceComponent;
import scripts.Data.FarmingUtils;
import scripts.Data.Enums.Trees;
import scripts.Data.Vars;
import scripts.Tasks.Priority;
import scripts.Tasks.Task;
import scripts.Timer;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Break implements Task {


    public static int determineBreakLengthTree() {
        if (Vars.get().treeId == Trees.OAK_SAPPLING.id) {
            return Trees.OAK_SAPPLING.timeToGrowMs;

        } else if (Vars.get().treeId == Trees.WILLOW_SAPPLING.id) {
            return Trees.WILLOW_SAPPLING.timeToGrowMs;

        } else if (Vars.get().treeId == Trees.MAPLE_SAPPLING.id) {
            return Trees.MAPLE_SAPPLING.timeToGrowMs;

        } else if (Vars.get().treeId == Trees.YEW_SAPPLING.id) {
            return Trees.YEW_SAPPLING.timeToGrowMs;

        } else if (Vars.get().treeId == Trees.MAGIC_SAPPLING.id) {
            return Trees.MAGIC_SAPPLING.timeToGrowMs;
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
        Vars.get().status = "Waiting for growth";
        if (Login.getLoginState().equals(Login.STATE.INGAME)) {
            if (Vars.get().doingTrees && Vars.get().treeTimer == null) {
                General.println("[Debug]: Starting tree timer");
              //  Vars.get().treeTimer = new Timer(determineBreakLengthTree() + General.random(30000, 120000)); // time for tree + 5-20 min

            } else if (Vars.get().doingAllotments && Vars.get().allotmentTimer == null) {
                General.println("[Debug]: Starting allotment timer");
              //  Vars.get().allotmentTimer = new Timer(General.random(4200000, 4500000)); // 70-75min
//
            } else if (Vars.get().herbTimer == null) {
                General.println("[Debug]: Starting herb timer");
               // Vars.get().herbTimer = new Timer(General.random(4800000, 5100000)); // 80-85min
            }

            Login.logout();
            Timer.waitCondition(() -> Login.getLoginState().equals(Login.STATE.LOGINSCREEN), 6000, 10000);

        }
        if (Login.getLoginState().equals(Login.STATE.LOGINSCREEN)) {
            General.sleep(General.random(25000, 90000));

        }


        if (Vars.get().doingAllotments
                && Vars.get().allotmentTimer != null){
               // && !Vars.get().allotmentTimer.isRunning()) {
            General.println("[Debug]: Logging in - allotment timer");
            Login.login();
            FarmingUtils.populatePatchesToVisit();
            Vars.get().shouldBank = true;

        } else if (Vars.get().doingTrees
                && Vars.get().treeTimer != null){
              //  && !Vars.get().treeTimer.isRunning()) {
            General.println("[Debug]: Logging in - tree timer");
            Login.login();
            FarmingUtils.populateTreePatches();
            Vars.get().shouldBank = true;
        } else if (Vars.get().herbTimer != null ){
             //   && !Vars.get().herbTimer.isRunning()) {
            General.println("ending");
            Vars.get().scriptStatus = false;
            //  General.println("[Debug]: Logging in - herb timer");
            //  Login.login();
            // Move.populatePatchesToVisit();
            // Vars.get().shouldBank = true;
        }
    }

    @Override
    public Priority priority() {
        return Priority.LOW;
    }

    @Override
    public boolean validate() {
        return Vars.get().shouldBreak;
    }
}
