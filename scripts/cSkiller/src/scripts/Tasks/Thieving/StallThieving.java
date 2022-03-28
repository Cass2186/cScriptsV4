package scripts.Tasks.Thieving;

import org.tribot.api.DynamicClicking;
import org.tribot.api.General;
import org.tribot.api.input.Mouse;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Objects;
import org.tribot.api2007.Player;
import org.tribot.api2007.Skills;
import org.tribot.api2007.types.RSItem;
import org.tribot.api2007.types.RSObject;
import org.tribot.api2007.types.RSTile;
import org.tribot.script.sdk.Waiting;
import scripts.*;
import scripts.API.Priority;
import scripts.API.Task;
import scripts.Data.SkillTasks;
import scripts.Data.Vars;
import scripts.EntitySelector.Entities;
import scripts.EntitySelector.finders.prefabs.ItemEntity;

import java.awt.*;

public class StallThieving implements Task {


    private Timer afkTimer = new Timer(General.random(240000, 720000)); //4-12min

    String message = "";

    public void stealFruitStall() {
        if (!afkTimer.isRunning()) {
            message = "AFKing";
            Mouse.leaveGame();
            Waiting.waitNormal(25000, 7500);
            afkTimer = new Timer(General.random(240000, 720000)); //4-12min
        }
        if (Inventory.isFull()) {
            Inventory.setDroppingMethod(Inventory.DROPPING_METHOD.SHIFT);
            Utils.dropItem(ThievingConst.FRUIT_IDS);
        }
        int chance = General.random(0, 100);

        if (steal(ThievingConst.FRUIT_STALL_ID)) {
            message = "Stealing from Fruit stall";
            if (Timer.waitCondition(() -> Player.getAnimation() != -1, 4500, 7000))
                Timer.waitCondition(() -> Player.getAnimation() == -1, 4500, 7000);
            clicksToNextLevel(28);
        } else if (ThievingConst.ABC2CHANCE > chance) {
            message = "Waiting for Fruit stall (ABC2)";
            Timer.abc2SkillingWaitCondition(() -> Objects.findNearest(4, ThievingConst.FRUIT_STALL_ID).length > 0, 10000, 15000);

        } else {
            message = "Waiting for Fruit stall";
            Utils.hoverXp(Skills.SKILLS.THIEVING, 2);
            Timer.waitCondition(() -> Objects.findNearest(4, ThievingConst.FRUIT_STALL_ID).length > 0, 10000, 15000);
        }
    }

    public int clicksToNextLevel(int xpPerAction) {
        int xpToNext = Skills.getXPToLevel(Skills.SKILLS.THIEVING, Skills.getActualLevel(Skills.SKILLS.THIEVING) + 1);
        int clicks = xpToNext / xpPerAction;
        if (clicks == 100 || clicks == 50) {
            General.println("[Stats]: Actions until next level: " + clicks);
        }
        return clicks;
    }

    public boolean steal(int id) {
        int chance = General.random(0, 100);
        RSObject[] obj = Objects.findNearest(4, id);
        if (obj.length > 0) {
            Point pnt = Mouse.getPos();
            if (obj[0].getModel().getEnclosedArea().contains(pnt) && chance > 37) { // still sometimes moves a bit
                return obj[0].click("Steal-from");
            } else {
                return Utils.clickObject(obj[0], "Steal-from", false); //moves far more
            }
        } else {
            message = "Going to Fruit stall";
            PathingUtil.walkToTile(ThievingConst.FRUIT_STALL_TILE, 4, false);
        }
        return false;
    }

    public void stealBakersStall() {
        if (!afkTimer.isRunning()) {
            message = "AFKing";
            Mouse.leaveGame();
            Waiting.waitNormal(25000, 7500);
            afkTimer = new Timer(General.random(240000, 720000)); //4-12min
        }
        if (Inventory.isFull()) {
            Inventory.setDroppingMethod(Inventory.DROPPING_METHOD.SHIFT);
            Inventory.setDroppingPattern(Inventory.DROPPING_PATTERN.TOP_TO_BOTTOM);
            RSItem[] cakes = Entities.find(ItemEntity::new)
                    .actionsContains("Eat")
                    .getResults();
            int sp = Mouse.getSpeed();
            Mouse.setSpeed(General.random(180, 220));
            Inventory.drop(cakes);
            Mouse.setSpeed(sp);
            Utils.unselectItem();
        }
        if (stealFromStall(ThievingConst.BAKERS_STALL_ID, ThievingConst.BAKERS_STALL_TILE)) {
            message = "Stealing from stall";
            if (Timer.waitCondition(() -> Player.getAnimation() != -1, 2000, 3000))
                Timer.waitCondition(() -> Player.getAnimation() == -1, 4500, 7000);
            clicksToNextLevel(16);
        }
    }

    public boolean stealFromStall(int id, RSTile tile) {
        int chance = General.random(0, 100);
        RSObject[] obj = Objects.findNearest(2, id);
        if (tile.equals(Player.getPosition())) {
            if (obj.length > 0) {
                Point pnt = Mouse.getPos();
                if (obj[0].getModel().getEnclosedArea().contains(pnt) && chance > 37) { // still sometimes moves a bit
                    return obj[0].click("Steal-from");
                } else {
                    return Utils.clickObject(obj[0], "Steal-from", false); //moves far more
                }
            } else if (ThievingConst.ABC2CHANCE > chance) {
                message = "Waiting for stall (ABC2)";
                Timer.abc2SkillingWaitCondition(() -> Objects.findNearest(2, ThievingConst.BAKERS_STALL_ID).length > 0, 10000, 15000);

            } else {
                message = "Waiting for stall";
                Utils.hoverXp(Skills.SKILLS.THIEVING, 1);
                Timer.waitCondition(() -> Objects.findNearest(4, ThievingConst.BAKERS_STALL_ID).length > 0, 10000, 15000);
            }
        } else {
            message = "Going to stall tile";

            PathingUtil.walkToTile(tile, 1, false);
            if (tile.isClickable() && DynamicClicking.clickRSTile(tile, "Walk here")) {
                Timer.waitCondition(() -> tile.equals(Player.getPosition()), 3000, 5000);
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "Stall thieving";
    }

    @Override
    public Priority priority() {
        return Priority.LOW;
    }

    @Override
    public boolean validate() {
        return Vars.get().currentTask != null && Vars.get().currentTask.equals(SkillTasks.THIEVING) &&
                (Skills.SKILLS.THIEVING.getActualLevel() >= 5 && Skills.SKILLS.THIEVING.getActualLevel() < 55);
    }

    @Override
    public void execute() {
        if ((!Vars.get().useFruitStalls || Skills.SKILLS.THIEVING.getActualLevel() < 25)
                && Skills.SKILLS.THIEVING.getActualLevel() >= 5) {
            stealBakersStall();
        } else if (Utils.getVarBitValue(Varbits.KOUREND_FAVOR_HOSIDIUS.getId()) > 150 && //15% favour
                Skills.SKILLS.THIEVING.getActualLevel() >= 25 && Skills.SKILLS.THIEVING.getActualLevel() < 55 &&
                Vars.get().useFruitStalls) {
            stealFruitStall();
        }
    }

    @Override
    public String taskName() {
        return "Thieving";
    }
}
