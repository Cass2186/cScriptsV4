package scripts.Tasks.Thieving;

import dax.walker.utils.camera.DaxCamera;
import org.tribot.api.DynamicClicking;
import org.tribot.api.General;
import org.tribot.api2007.*;
import org.tribot.api2007.ext.Filters;
import org.tribot.api2007.types.*;
import scripts.*;
import scripts.API.Priority;
import scripts.API.Task;
import scripts.Data.SkillTasks;
import scripts.Data.Vars;

public class NpcThieving implements Task {


    private int hp = General.random(35, 70);
    private String message = "";

    //TODO make this a thread or global var
    Timer afkTimer = new Timer(General.random(240000, 720000)); //4-12min


    public void equipDodgy() {
        if (!Equipment.isEquipped(ThievingConst.DODGY_NECKLACE)) {
            RSItem[] item = Inventory.find(ThievingConst.DODGY_NECKLACE);
            if (item.length > 0 && item[0].click("Wear")) {
                Timer.waitCondition(() -> Equipment.isEquipped(ThievingConst.DODGY_NECKLACE), 2000);
            }
        }
    }


    public void checkPlane() {
        if (Player.getPosition().getPlane() > 0) {
            RSObject[] l = Objects.findNearest(20, Filters.Objects.actionsContains("Climb-down"));
            if (l.length > 0 && Utils.clickObject(l[0], "Climb-down")) {
                Timer.waitCondition(() -> Player.getPosition().getPlane() == 0, 4000, 6000);
            }
        }
    }
    public void openCoinPouches(int numToOpenAt) {
        RSItem[] invCoinPouch = Inventory.find("Coin pouch");
        if (invCoinPouch.length > 0 && invCoinPouch[0].getStack() >= numToOpenAt) {
            General.println("[Thieving]: Need to open coin pouches");
            if (invCoinPouch[0].click("Open"))
                Utils.idleNormalAction(true);
        }
    }


    public void checkHealthAndEat() {
        RSItem[] food = Inventory.find(Filters.Items.actionsContains("Eat"));
        if (Combat.getHPRatio() < hp && food.length > 0) {
            General.println("[Thieving]: Need to Eat" + food[0].getID());
            if (EatUtil.eatFood()) {
                Utils.microSleep();
                hp = AntiBan.getEatAt() + General.random(2, 8);
                General.println("[Thieving]: Next eating at " + hp);
            }
        } else if (food.length == 0) {
            BankManager.open(true);
            BankManager.withdraw(15, true, 379);
            BankManager.close(true);
            if (Inventory.find(Filters.Items.actionsContains("Eat")).length == 0) {
                General.println("[Thieving]: Ending due to no food");
              throw new NullPointerException();
            }
        }
    }

    public void stealNPC(String npcName) {
        openCoinPouches(General.random(18, 24));

        checkPlane();

        checkHealthAndEat();
        equipDodgy();

        RSNPC[] target = NPCs.findNearest(npcName);
        if (Player.getAnimation() == 397 || Player.getAnimation() == 424) {
            message = "Stunned, waiting";
            checkHealthAndEat();
            openCoinPouches(General.random(12, 20)); //lower threshold
            Timer.abc2WaitCondition(() -> Player.getAnimation() == -1, 6000, 9000);
            Utils.idlePredictableAction();
            return;
        }

        int cameraChance = General.random(0, 100);

        if (target.length > 0) {
           // Utils.closeAllWindows(); // in case bank is open

            if (!target[0].isClickable() || cameraChance == 1) {
                General.println("[Debug]: Refocusing target");
                DaxCamera.focus(target[0]);
            }
            message = "Pickpocketing";
            if (DynamicClicking.clickRSNPC(target[0], "Pickpocket")) {
                Timer.waitCondition(() -> Player.getAnimation() == 397 ||
                        Player.getAnimation() == 881 || Player.getAnimation() == 424, 3000, 4500);
                Utils.idlePredictableAction();
            }
        }
        if (!afkTimer.isRunning()) {
            Utils.afk(General.random(10000, 40000));
            afkTimer.reset();
        }
    }


    @Override
    public String toString() {
        return message;
    }

    @Override
    public Priority priority() {
        return Priority.LOW;
    }

    @Override
    public boolean validate() {
        return Vars.get().currentTask != null && Vars.get().currentTask.equals(SkillTasks.THIEVING) &&
                (Skills.SKILLS.THIEVING.getActualLevel() < 5 ||
                Skills.SKILLS.THIEVING.getActualLevel() >= 55) ;
    }

    @Override
    public void execute() {
        if (Skills.SKILLS.THIEVING.getActualLevel() < 5){
            // men/women
        } else if (Skills.SKILLS.THIEVING.getActualLevel() >=55){
            stealNPC("Knight of Ardougne");
        }
    }

    @Override
    public String taskName() {
        return "NPC Thieving";
    }
}
