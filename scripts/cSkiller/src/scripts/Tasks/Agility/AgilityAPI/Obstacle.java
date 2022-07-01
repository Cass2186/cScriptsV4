package scripts.Tasks.Agility.AgilityAPI;


import dax.walker.utils.AccurateMouse;
import dax.walker.utils.camera.DaxCamera;
import lombok.Getter;
import lombok.Setter;
import org.tribot.api.DynamicClicking;
import org.tribot.api.General;
import org.tribot.api2007.*;
import org.tribot.api2007.Combat;
import org.tribot.api2007.Magic;
import org.tribot.api2007.Options;
import org.tribot.api2007.ext.Filters;
import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSItem;
import org.tribot.api2007.types.RSObject;
import org.tribot.api2007.types.RSObjectDefinition;
import org.tribot.script.sdk.*;
import org.tribot.script.sdk.input.Mouse;
import org.tribot.script.sdk.interfaces.Positionable;
import org.tribot.script.sdk.query.Query;
import org.tribot.script.sdk.types.GameObject;
import org.tribot.script.sdk.types.InventoryItem;
import org.tribot.script.sdk.types.WorldTile;
import org.tribot.script.sdk.util.TribotRandom;
import org.tribot.script.sdk.walking.LocalWalking;
import scripts.*;
import scripts.Data.Vars;
import scripts.Tasks.BirdHouseRuns.Nodes.Wait;

;import java.util.Optional;

public class Obstacle {

    private int obstacleId;
    private String obstacleName;
    @Getter
    public String obstacleAction;
    private RSArea obstacleArea;

    @Getter
    @Setter
    private int timeOutMin = 7500;

    private RSArea nextObstacleArea;

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
            for (int i = 0; i < 3; i++) {
                if (MyPlayer.isMoving())
                    Waiting.waitUntil(2500, 50, () -> !MyPlayer.isMoving());

                Log.info("[Debug]: Eating food i = " + i);
                Optional<InventoryItem> eat = Query.inventory().actionContains("Eat").findClosestToMouse();
                if (eat.map(f -> f.click("Eat")).orElse(false)) {
                    Vars.get().eatAt = AntiBan.getEatAt();
                    General.println("[ABC2]: Next eating at: " + Vars.get().eatAt + "% HP");
                    return true;
                } else {
                    Waiting.waitNormal(150, 15);
                }
            }
            General.println("[Debug]: We have no more food in inventory and need to eat. Ending");
            cSkiller.isRunning.set(false);
            return false;
        }

        return true;

    }

    public void alch(int itemId) {
        if (Vars.get().shouldAlchAgil) {
            Optional<InventoryItem> closestToMouse = Query.inventory()
                    .idEquals(itemId).isNoted()
                    .findClosestToMouse();
            if (closestToMouse.map(it -> org.tribot.script.sdk
                    .Magic.castOn("High Level Alchemy", it)).orElse(false)) {
                General.sleep(General.randomSD(430, 65));
            }
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
                    .findBestInteractable();

            RSObject[] obj = Objects.findNearest(40,
                    Filters.Objects.actionsContains(this.obstacleAction)
                            .and(Filters.Objects.idEquals(this.obstacleId)));


            if (this.nextObstacleArea != null && object.isPresent()) {
                int chance = General.random(0, 100);
                if (chance <= Vars.get().abc2Chance) {
                    if (MyPlayer.isMoving())
                        return clickObject(object, this.obstacleAction, true, true);

                    else
                        return clickObject(object, this.obstacleAction, false, true);


                } else if (clickObject(object, this.obstacleAction, false, false))
                    return true; //can't just return the clickobject() because sometimes it gets stuck in canifis floating

            } else if (object.map(o -> o.interact(this.obstacleAction)).orElse(false)) {
                Log.info("interacted with");
                Timer.agilityWaitCondition(() ->
                                (!MyPlayer.isMoving()
                                        && !this.obstacleArea.contains(Player.getPosition()) ||
                                        Player.getPosition().getPlane() == 0),
                        5000, 6500);
                return true;
            }
            if (obj.length > 0 && this.obstacleArea.contains(Player.getPosition())) {
                //need this for Canifis when you're floating and stuck
                Log.info("API Dynamic clicking failsafe");
                if (!obj[0].isClickable())
                    DaxCamera.focus(obj[0]);

                return DynamicClicking.clickRSObject(obj[0], this.obstacleAction)
                        && Waiting.waitUntil(Utils.random(5000, 6500),
                        Utils.random(400, 800), () ->
                                (!MyPlayer.isMoving()
                                        && !this.obstacleArea.contains(Player.getPosition()) ||
                                        MyPlayer.getTile().getPlane() == 0));
            }
        }
        return false;
    }

    public boolean clickObject(Optional<GameObject> obj, String action, boolean accurateMouse, boolean abc2Wait) {
        int plane = MyPlayer.getTile().getPlane();
        int shouldAlch = Utils.random(0, 100);
        int shouldSpam = TribotRandom.uniform(0, 100);
        for (int i = 0; i < 3; i++) { //tries 3 times
            // Log.info("Clicking " + this.obstacleAction + " " + getObstacleName() + " (ABC2 Sleep: " + abc2Wait + ")");
            if (Vars.get().spamClickAgility && shouldSpam < TribotRandom.normal(64, 4)) {
                int num = TribotRandom.normal(2, 6, 3, 1);
                Log.info("Clicking " + num + " times");
                for (int clicks = 0; clicks < num; clicks++) {
                    if (obj.map(o -> o.isVisible() && o.click(action)).orElse(false)) {
                        Waiting.waitNormal(25, 7);
                    } else if (obj.map(o -> o.interact(action)).orElse(false)) {
                        Waiting.waitNormal(30, 7);
                    }
                }
            } else if (accurateMouse) {
                Mouse.setClickMethod(Mouse.ClickMethod.ACCURATE_MOUSE);
                if (obj.map(o -> o.interact(action)).orElse(false)) {
                    Log.info("Accurate clicked");
                    Mouse.setClickMethod(Mouse.ClickMethod.TRIBOT_DYNAMIC);
                }
            } else if (obj.map(o -> !o.isVisible()).orElse(false) && MyPlayer.getTile().getPlane() != 0) {
                Log.info("Using clickable tile");
                Optional<Positionable> furthestTile = PathingUtil
                        .getFurthestClickableTile(obj.get().getTile().toLocalTile());
                if (furthestTile.map(f -> f.getTile().interact("Walk here")).orElse(false)) {
                    Waiting.waitUntil(3500, 125, () -> obj.map(o -> o.isVisible()).orElse(false));
                    if (!obj.map(o -> o.interact(action)).orElse(false)) {
                        Log.error("Miss clicked, i: " + i);
                        Waiting.waitNormal(30, 7);
                        continue;
                    }
                }
            } else if (!obj.map(o -> o.interact(action)).orElse(false)) {
                Log.error("Miss clicked, i: " + i);
                Waiting.waitNormal(70, 12);
                continue;
            } else {
                Log.info("interacted clicked");
            }
            if (Vars.get().shouldAlchAgil && shouldAlch < 62) {
                Magic.selectSpell("High Level Alchemy");
                Waiting.waitNormal(350, 75);
            }
            WorldTile t = MyPlayer.getTile();
            if (Waiting.waitUntil(1500, 25, MyPlayer::isMoving)) {
               // Log.info("Waiting after moving");
                if (abc2Wait)
                    return Timer.abc2WaitCondition(() -> (!MyPlayer.isMoving()
                                    && this.nextObstacleArea.contains(Player.getPosition())
                                    && MyPlayer.getAnimation() == -1) ||
                                    MyPlayer.getTile().getPlane() != plane ||
                                    (MyPlayer.getTile().getPlane() == plane &&
                                            t.distance() > 30),
                            timeOutMin, timeOutMin + 3000);

                else if (Skill.AGILITY.getActualLevel() >= 85) //ardy
                    return Timer.agilityWaitCondition(() -> (!MyPlayer.isMoving()
                                    && this.nextObstacleArea.contains(Player.getPosition())
                                    && MyPlayer.getAnimation() == -1) ||
                                    (MyPlayer.getTile().getPlane() == plane &&
                                            t.distance() > 30),
                            timeOutMin, timeOutMin + 3000);

                else
                    return Timer.agilityWaitCondition(() ->
                                    (!MyPlayer.isMoving() && this.nextObstacleArea.contains(Player.getPosition())
                                            && MyPlayer.getAnimation() == -1) ||
                                            MyPlayer.getTile().getPlane() != plane 
                                            || (MyPlayer.getTile().getPlane() == plane &&
                                                    t.distance() > 30),
                            timeOutMin, timeOutMin + 3000);


            }
        }
        return false;
    }


    public boolean isValidObstacle() {
        return this.obstacleArea.contains(Player.getPosition());
    }

    public String getObstacleName() {
        RSObjectDefinition def = RSObjectDefinition.get(this.obstacleId);
        if (def != null)
            return this.obstacleName = def.getName();

        return this.obstacleName;
    }

}
