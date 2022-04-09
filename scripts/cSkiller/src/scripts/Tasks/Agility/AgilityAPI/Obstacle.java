package scripts.Tasks.Agility.AgilityAPI;


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
import org.tribot.script.sdk.MyPlayer;
import org.tribot.script.sdk.Waiting;
import org.tribot.script.sdk.query.Query;
import org.tribot.script.sdk.types.GameObject;
import org.tribot.script.sdk.walking.LocalWalking;
import scripts.*;
import scripts.Data.Vars;

;import java.util.Optional;

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
                cSkiller.isRunning.set(false);
                return false;
            }

            Vars.get().eatAt = AntiBan.getEatAt();
            General.println("[ABC2]: Next eating at: " + Vars.get().eatAt + "% HP");
        }
        return true;
    }

    public void alch(int ItemID) {
        if (Vars.get().shouldAlchAgil) {
            RSItem[] alch = Inventory.find(ItemID);
            if (Magic.isSpellSelected() && alch.length > 0 && alch[0].click())
                General.sleep(General.randomSD(430, 100));
        }

    }

    public boolean navigateObstacle() {
        if (this.obstacleArea.contains(Player.getPosition())) {

            if (Vars.get().afkTimer != null &&
                    !Vars.get().afkTimer.isRunning() && Vars.get().afkMode) {
                Utils.afk(General.randomSD(Vars.get().afkDurationAvg, Vars.get().afkDurationSD));
                Vars.get().afkTimer.reset();
            }

            if (!eat())
                return false;

            setRun();
            alch(1398);//TODO set this to a modifiable variable
            AgilUtils.getMark(this.obstacleArea);

            Optional<GameObject> object = Query.gameObjects()
                    .actionContains(this.obstacleAction)
                    .idEquals(this.obstacleId)
                    .sortedByDistance()
                    //TODO add is reachable
                    .findBestInteractable();

            RSObject[] obj = Objects.findNearest(40,
                    Filters.Objects.actionsContains(this.obstacleAction)
                            .and(Filters.Objects.idEquals(this.obstacleId)));


            if (object.isPresent()) {
                if (!object.get().isVisible()) {
                    // can't click and its far away
                    if (object.get().getTile().distanceTo(MyPlayer.getPosition()) > General.random(7, 10)
                            && object.map(LocalWalking::walkTo).orElse(false)) {
                        Log.info("[Obstacle]: Distance to obstacle (sdk): " + object.get().getTile().distanceTo(MyPlayer.getPosition()));
                        Timer.slowWaitCondition(() -> object.get().isVisible(), 6000, 10000);
                    } else {
                        object.get().adjustCameraTo();
                    }
                }
            }

            if (this.nextObstacleArea != null && obj.length > 0) {
                int chance = General.random(0, 100);
                if (chance <= Vars.get().abc2Chance) {
                    if (Player.isMoving())
                        return clickObject(obj[0], this.obstacleAction, true, true);

                    else
                        return clickObject(obj[0], this.obstacleAction, false, true);


                } else
                    return clickObject(obj[0], this.obstacleAction, false, false);

            } else if (obj.length > 0)
                return DynamicClicking.clickRSObject(obj[0], this.obstacleAction)
                        && Timer.agilityWaitCondition(() ->
                                (!Player.isMoving()
                                        && !this.obstacleArea.contains(Player.getPosition()) ||
                                        Player.getPosition().getPlane() == 0),
                        3500, 5500);
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
                if (Vars.get().shouldAlchAgil && shouldAlch < 62) {
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
                if (Vars.get().shouldAlchAgil && shouldAlch < 62) {
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
