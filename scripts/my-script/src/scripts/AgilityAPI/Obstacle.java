package scripts.AgilityAPI;


import dax.walker.utils.AccurateMouse;
import dax.walker.utils.camera.DaxCamera;
import lombok.Getter;
import lombok.Setter;
import org.tribot.api.DynamicClicking;
import org.tribot.api.General;
import org.tribot.api2007.*;
import org.tribot.api2007.ext.Filters;
import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSItem;
import org.tribot.api2007.types.RSObject;
import org.tribot.api2007.types.RSObjectDefinition;
import org.tribot.script.sdk.Log;
import org.tribot.script.sdk.Waiting;
import scripts.*;
import scripts.Data.Vars;

;

public class Obstacle {

    int obstacleId;
    String obstacleName;
    public String obstacleAction;
    RSArea obstacleArea;

    @Getter
    @Setter
    int timeOutMin = 6500;

    RSArea nextObstacleArea;

    public Obstacle(int id, String action, RSArea area) {
        this.obstacleId = id;
        this.obstacleAction = action;
        this.obstacleArea = area;
    }


    public Obstacle(int id, String action, RSArea area, RSArea nextArea) {
        this.obstacleId = id;
        this.obstacleAction = action;
        this.obstacleArea = area;
        this.nextObstacleArea = nextArea;
    }


    public Obstacle(int id, String name, String action, RSArea area) {
        this.obstacleId = id;
        this.obstacleName = name;
        this.obstacleAction = action;
        this.obstacleArea = area;
    }


    public void setRun() {
        if (AntiBan.getRunAt() < Game.getRunEnergy() &&
                !Options.isRunEnabled()) {
            General.println("[Debug]: Enabling run");
            Options.setRunEnabled(true);
        }
    }

    public boolean eat() {
        if (Combat.getHPRatio() < Vars.get().eatAt) {
            General.println("[Debug]: Eating food");
            if (!EatUtil.eatFood()) {
                General.println("[Debug]: We have no more food in inventory and need to eat. Ending");
                return cAgility.isRunning = false;
            }

            Vars.get().eatAt = AntiBan.getEatAt();
            General.println("[ABC2]: Next eating at: " + Vars.get().eatAt + "% HP");
        }
        return true;
    }

    int alchChance = General.random(0,100);

    public void alch(int ItemID) {
        if (Vars.get().shouldAlch && alchChance < 70) {
            Log.log("Alching");
           // General.sleep(General.randomSD(300, 120));
            RSItem[] alch = Inventory.find(ItemID);
            if (Magic.isSpellSelected() && alch.length > 0 && alch[0].click())
                General.sleep(General.randomSD(460, 120));
        }
        alchChance = General.random(0,100);
    }

    public boolean navigateObstacle() {
        if (this.obstacleArea.contains(Player.getPosition())) {

            if (Vars.get().afkTimer != null &&
                    !Vars.get().afkTimer.isRunning() && Vars.get().afkMode) {
                Utils.afk(General.random(Vars.get().afkMin, Vars.get().afkMax));
                Vars.get().afkTimer.reset();
            }

            if (!eat())
                return false;

            setRun();
            alch(856);
            AgilUtils.getMark(this.obstacleArea);

            RSObject[] obj = Objects.findNearest(40,
                    Filters.Objects.actionsContains(this.obstacleAction)
                            .and(Filters.Objects.idEquals(this.obstacleId)));
            if (obj.length > 0) {
                cAgility.status = this.obstacleAction + " " + getObstacleName();
                if (!obj[0].isClickable()) {
                    if (obj[0].getPosition().distanceTo(Player.getPosition()) > General.random(7, 10)) {
                        General.println("[Obstacle]: Moving to " + getObstacleName());
                        Log.log("[Debug]: Distance to obstacle: " + obj[0].getPosition().distanceTo(Player.getPosition()));
                        if (Walking.blindWalkTo(obj[0].getPosition()))
                            Timer.slowWaitCondition(() -> obj[0].isClickable(), 6000, 10000);


                    } else
                        DaxCamera.focus(obj[0]);
                }

                if (this.nextObstacleArea != null) {
                    int chance = General.random(0, 100);
                    if (chance <= Vars.get().abc2Chance) {
                        if (Player.isMoving())
                            return clickObject(obj[0], this.obstacleAction, true, true);

                        else
                            return clickObject(obj[0], this.obstacleAction, false, true);


                    } else
                        return clickObject(obj[0], this.obstacleAction, false, false);

                } else
                    return DynamicClicking.clickRSObject(obj[0], this.obstacleAction)
                            && Timer.agilityWaitCondition(() ->
                                    (!Player.isMoving()
                                            && !this.obstacleArea.contains(Player.getPosition()) ||
                                            Player.getPosition().getPlane() == 0),
                            3500, 5500);
            }
        }
        return false;
    }


    public boolean clickObject(RSObject obj, String action, boolean accurateMouse, boolean abc2Wait) {
        int plane = Player.getPosition().getPlane();
        int shouldAlch = General.random(0, 100);
        for (int i = 0; i < 3; i++) { //tries 3 times
            //    General.println("[Debug]: i: " + i);
            if (accurateMouse && AccurateMouse.click(obj, action)) {
                General.println("[Debug]: Accurate Clicking " + this.obstacleAction + " "
                        + getObstacleName() + " (ABC2 Sleep: " + abc2Wait + ")");
                if (Vars.get().shouldAlch && shouldAlch < 62) {
                    Magic.selectSpell("High Level Alchemy");
                    Waiting.waitNormal(350, 75);
                }
                Timer.waitCondition(Player::isMoving, 1200, 2200);
                if (abc2Wait)
                    return Timer.abc2WaitCondition(() ->
                            (!Player.isMoving()
                                    && this.nextObstacleArea.contains(Player.getPosition())
                                    && Player.getAnimation() == -1) || Player.getPosition().getPlane() != plane, timeOutMin, timeOutMin + 3000);
                else
                    return Timer.agilityWaitCondition(() ->
                            (!Player.isMoving()
                                    && this.nextObstacleArea.contains(Player.getPosition())
                                    && Player.getAnimation() == -1) || Player.getPosition().getPlane() != plane, timeOutMin, timeOutMin + 3000);

            } else if (!accurateMouse &&
                    DynamicClicking.clickRSObject(obj, action + " " + Utils.getObjectName(obj))) {
                General.println("[Debug]: Clicking " + this.obstacleAction + " "
                        + getObstacleName() + " (ABC2 Sleep: " + abc2Wait + ")");
                if (Vars.get().shouldAlch && shouldAlch < 62) {
                    Magic.selectSpell("High Level Alchemy");
                    Waiting.waitNormal(350, 75);
                }

                Timer.waitCondition(Player::isMoving, 1200, 2200);

                if (abc2Wait)
                    return Timer.abc2WaitCondition(() -> (!Player.isMoving()
                            && this.nextObstacleArea.contains(Player.getPosition())
                            && Player.getAnimation() == -1) || Player.getPosition().getPlane() != plane, timeOutMin, timeOutMin + 3000);

                else
                    return Timer.agilityWaitCondition(() -> (!Player.isMoving()
                            && this.nextObstacleArea.contains(Player.getPosition())
                            && Player.getAnimation() == -1) || Player.getPosition().getPlane() != plane, timeOutMin, timeOutMin + 3000);

            }
        }
        return false;
    }


    public boolean isValidObstacle() {
        if (this.obstacleArea.contains(Player.getPosition())) {
            // General.println("[Debug]: Obstacle validated: " + this.obstacleId);
            return this.obstacleArea.contains(Player.getPosition());
        }
        return this.obstacleArea.contains(Player.getPosition());
    }

    public String getObstacleName() {
        RSObjectDefinition def = RSObjectDefinition.get(this.obstacleId);
        if (def != null)
            return this.obstacleName = def.getName();

        return this.obstacleName;
    }


    public String getObstacleAction() {
        return this.obstacleAction;
    }
}
