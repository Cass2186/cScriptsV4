package scripts.API;

import org.tribot.api.General;
import org.tribot.api.input.Mouse;
import org.tribot.api2007.*;
import org.tribot.api2007.ext.Filters;
import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSInterface;
import org.tribot.api2007.types.RSPlayer;
import org.tribot.api2007.types.RSTile;
import org.tribot.script.sdk.Log;
import org.tribot.script.sdk.Waiting;
import scripts.EntitySelector.Entities;
import scripts.EntitySelector.finders.prefabs.PlayerEntity;
import scripts.Timer;
import scripts.cCombat;

import java.awt.*;

public class AntiPKThread extends Thread {

    Thread thrd;
    boolean suspended = false;
    boolean stopped = false;

    public AntiPKThread(String name) {
        thrd = new Thread(this, name);
        suspended = false;
        stopped = false;
    }

    public static AntiPKThread createAndStart(String name) {
        AntiPKThread myThrd = new AntiPKThread(name);
        myThrd.thrd.start();
        return myThrd;
    }

    public synchronized void myStop() {
        stopped = true;
        suspended = false;
        notify();
    }

    public synchronized void mySuspend() {
        suspended = true;
        notify();
    }

    public synchronized void myResume() {
        suspended = false;
        notify();
    }


    public static RSArea WHOLE_WILDERNESS = new RSArea(new RSTile(3365, 3524, 0), new RSTile(2963, 3967, 0));
    public static int ourCombatLevel = Player.getRSPlayer().getCombatLevel();
    public static int combatMin = ourCombatLevel - Combat.getWildernessLevel();
    public static int combatMax = ourCombatLevel + Combat.getWildernessLevel();
    public static int pointThreshold = 200;
    public static int points = 0;
    public static int nextWorld = WorldHopper.getRandomWorld(true, false);

    public static boolean monitorForPkers() { // ideally is acting as the wait condition while always determining players around us
        ourCombatLevel = Player.getRSPlayer().getCombatLevel();
        combatMin = ourCombatLevel - Combat.getWildernessLevel();
        combatMax = ourCombatLevel + Combat.getWildernessLevel();

        RSPlayer[] otherPlayers = Entities.find(PlayerEntity::new)
                .nameNotContains(Player.getRSPlayer().getName())
                .inArea(new RSArea(Player.getPosition(), 40))
                .getResults();

        if (WHOLE_WILDERNESS.contains(Player.getPosition())) {
            for (RSPlayer p : otherPlayers) {
                if (p.getCombatLevel() < combatMax && p.getCombatLevel() > combatMin) {
                    //potentially dangerous level
                    if (p.getSkullIcon() == 0) {
                        General.println("[AntiPkThread]: Hopping due to dangerous player to " + nextWorld);
                        return true;
                    }
                }
            }
        }

        return false;
    }

    public static int PARENT = 69;
    public static int WHOLE_BOX = 5;

    public static boolean isWorldVisible(int world){
        RSInterface worldInter = Interfaces.get(PARENT, 16, world);
        RSInterface boxInter = Interfaces.get(PARENT, WHOLE_BOX);
        if (worldInter != null && boxInter != null) {
            Rectangle boxRectangle = boxInter.getAbsoluteBounds().getBounds();
            if (boxRectangle.contains(worldInter.getAbsolutePosition())){
                return true;
            }
        }
        return false;
    }

    public static boolean scrollToWorldNoClick(int world){
        WorldHopper.openWorldSelect();
        RSInterface worldInter = Interfaces.get(PARENT, 16, world);
        RSInterface scrollInter = Interfaces.get(PARENT, 18, 1);
        RSInterface boxInter = Interfaces.get(PARENT, 15);
        if (worldInter != null && scrollInter != null) {
            Mouse.setSpeed(250);
            General.println("[AntiPkThread]: Scrolling to world to max visible:  " + nextWorld);
            for (int i = 0; i < 100; i++) {
                if (!boxInter.getAbsoluteBounds().contains(Mouse.getPos())) {
                    Mouse.moveBox(boxInter.getAbsoluteBounds());
                    Waiting.waitNormal(100, 20);
                }
                if (boxInter.getAbsoluteBounds().contains(worldInter.getAbsolutePosition())) {
                   return true;
                } else if (worldInter.getAbsolutePosition().getY() > scrollInter.getAbsolutePosition().getY()) {
                    Mouse.scroll(false);
                    Waiting.waitNormal(30, 10);
                } else if (worldInter.getAbsolutePosition().getY() < scrollInter.getAbsolutePosition().getY()) {
                    Mouse.scroll(true);
                    Waiting.waitNormal(30, 10);
                }
            }
        }
        return false;
    }

    public static boolean scrollToWorld(int world) {

        RSInterface worldInter = Interfaces.get(PARENT, 16, world);
        RSInterface scrollInter = Interfaces.get(PARENT, 18, 1);
        RSInterface boxInter = Interfaces.get(PARENT, 15);
        if (worldInter != null && scrollInter != null) {

            General.println("[AntiPkThread]: Hopping due to dangerous player to " + nextWorld);
            for (int i = 0; i < 100; i++) {
                if (!boxInter.getAbsoluteBounds().contains(Mouse.getPos())) {
                    Mouse.moveBox(boxInter.getAbsoluteBounds());
                    Waiting.waitNormal(100, 20);
                }
                if (boxInter.getAbsoluteBounds().contains(worldInter.getAbsolutePosition())) {
                    if (worldInter.click())
                        return Timer.waitCondition(() -> WorldHopper.getWorld() == nextWorld, 10000, 15000);
                } else if (worldInter.getAbsolutePosition().getY() > scrollInter.getAbsolutePosition().getY()) {
                    Mouse.scroll(false);
                    Waiting.waitNormal(30, 10);
                } else if (worldInter.getAbsolutePosition().getY() < scrollInter.getAbsolutePosition().getY()) {
                    Mouse.scroll(true);
                    Waiting.waitNormal(30, 10);

                }
            }
            if (worldInter.click())
                return Timer.waitCondition(() -> WorldHopper.getWorld() == nextWorld, 10000, 15000);
        }
        return false;
    }


    @Override
    public void run() {

        while (cCombat.isRunning.get()) {
            General.sleep(15, 40);
            System.out.println("Running, next world is " + nextWorld);// && cCombat.isRunning
            if (suspended) {
                General.sleep(500);
                return;
            }
            if (WHOLE_WILDERNESS.contains(Player.getPosition())) {

                if (monitorForPkers()) {
                    Mouse.setSpeed(250);

                    WorldHopper.openWorldSelect();
                    if (AntiPKThread.scrollToWorld(AntiPKThread.nextWorld))
                        Timer.waitCondition(() -> WorldHopper.getWorld() == AntiPKThread.nextWorld, 10000, 15000);


                    nextWorld = WorldHopper.getRandomWorld(true, false);
                }
            } else {
                General.sleep(500);
            }
        }
    }
}
