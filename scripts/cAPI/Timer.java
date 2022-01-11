import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.input.Mouse;
import org.tribot.api2007.Interfaces;
import org.tribot.api2007.types.RSObject;

import java.util.function.BooleanSupplier;

public class Timer {

    private long end;
    private final long start;
    private final long period;

    public static long currentTime = System.currentTimeMillis();

    /**
     * Our script's start time
     */
    public static final long SCRIPT_START_TIME = System.currentTimeMillis();

    /**
     * Instantiates a new Timer with a given time period in milliseconds.
     *
     * @param period Time period in milliseconds.
     */
    public Timer(final long period) {
        this.period = period;
        start = System.currentTimeMillis();
        end = start + period;
    }

    /**
     * @return Elapsed time
     */
    public static long getScriptElapsedTime() {
        return System.currentTimeMillis() - Timer.SCRIPT_START_TIME;
    }

    /**
     * Returns the number of milliseconds elapsed since the start time.
     *
     * @return The elapsed time in milliseconds.
     */
    public long getElapsed() {
        return System.currentTimeMillis() - start;
    }

    /**
     * Returns the number of milliseconds remaining until the timer is up.
     *
     * @return The remaining time in milliseconds.
     */
    public long getRemaining() {
        if (isRunning()) {
            return end - System.currentTimeMillis();
        }
        return 0;
    }

    /**
     * Returns <tt>true</tt> if this timer's time period has not yet elapsed.
     *
     * @return <tt>true</tt> if the time period has not yet passed.
     */
    public boolean isRunning() {
        return System.currentTimeMillis() < end;
    }

    /**
     * Restarts this timer using its period.
     */
    public void reset() {
        end = System.currentTimeMillis() + period;
    }

    /**
     * Sets the end time of this timer to a given number of milliseconds from the
     * time it is called. This does not edit the period of the timer (so will not
     * affect operation after reset).
     *
     * @param ms The number of milliseconds before the timer should stop running.
     * @return The new end time.
     */
    public long setEndIn(final long ms) {
        end = System.currentTimeMillis() + ms;
        return end;
    }

    public static boolean waitCondition(BooleanSupplier condition, int min, int max) {
        return Timing.waitCondition(() -> {
            General.sleep(100, 600);
            return (condition.getAsBoolean());
        }, General.random(min, max));
    }

    public static boolean bankWaitCondition(BooleanSupplier condition, int min) {
        return Timing.waitCondition(() -> {
            General.sleep(50, 250);
            return (condition.getAsBoolean());
        }, General.random(min, min+1500));
    }
    public static boolean bankWaitCondition(BooleanSupplier condition, int min, int max) {
        return Timing.waitCondition(() -> {
            General.sleep(50, 250);
            return (condition.getAsBoolean());
        }, General.random(min, max));
    }
    public static boolean waitCondition(BooleanSupplier condition, int min) {
        return Timing.waitCondition(() -> {
            General.sleep(100, 600);
            return (condition.getAsBoolean());
        }, General.random(min, min+4000));



    }






    public static boolean crabWaitCondition(BooleanSupplier condition, int min, int max) {
        return Timing.waitCondition(() -> {
            General.sleep(300, 900);

            if (Mouse.isInBounds())
                Mouse.leaveGame();

            return (condition.getAsBoolean());
        }, General.random(min, max));
    }

    public static boolean slowWaitCondition(BooleanSupplier condition, int min, int max) {
        return Timing.waitCondition(() -> {
            General.sleep(400, 1200);
            return (condition.getAsBoolean());
        }, General.random(min, max));
    }

    public static boolean skillingWaitCondition(BooleanSupplier condition, int min, int max) {
        Timing.waitCondition(() -> {
            General.sleep(1200, 2400);
            AntiBan.timedActions();
            return (condition.getAsBoolean() || Interfaces.isInterfaceSubstantiated(233, 2));
        }, General.random(min, max));

        AntiBan.resetShouldOpenMenu();
        return condition.getAsBoolean();
    }

    public static boolean agilityWaitCondition(BooleanSupplier condition, int min, int max) {
        Timing.waitCondition(() -> {
            AntiBan.timedActions();
            General.sleep(1200, 2800);
            return (condition.getAsBoolean() || Interfaces.isInterfaceSubstantiated(233, 2));
        }, General.random(min, max));

        AntiBan.resetShouldOpenMenu();
        return condition.getAsBoolean();
    }

    public static boolean abc2SkillingWaitCondition(BooleanSupplier condition, RSObject nextClickObj, int min, int max) {
        currentTime = System.currentTimeMillis();
        Timing.waitCondition(() -> {
            General.sleep(General.random(350,900));

            AntiBan.timedActions();

            if (AntiBan.getShouldHover() && Mouse.isInBounds()) {
                General.println("[ABC2]: Hovering next object");
                AntiBan.hoverEntityObject(nextClickObj);
                AntiBan.resetShouldHover();
            }

            return (condition.getAsBoolean() || Interfaces.isInterfaceSubstantiated(233, 2));
        }, General.random(min, max));

        AntiBan.resetShouldOpenMenu();
        boolean cond = condition.getAsBoolean();
        scripts.Utility.Utils.abc2ReactionSleep(currentTime);
        return cond;
    }

    public static boolean abc2SkillingWaitCondition(BooleanSupplier condition, RSObject nextClickObj, int loopSleep, int min, int max) {
        currentTime = System.currentTimeMillis();
        Timing.waitCondition(() -> {
            General.sleep(General.random(loopSleep, loopSleep + (loopSleep/2)));

            AntiBan.timedActions();

            if (AntiBan.getShouldHover() && Mouse.isInBounds()) {
                General.println("[ABC2]: Hovering next object");
                AntiBan.hoverEntityObject(nextClickObj);
                AntiBan.resetShouldHover();
            }

            return (condition.getAsBoolean() || Interfaces.isInterfaceSubstantiated(233, 2));
        }, General.random(min, max));

        AntiBan.resetShouldOpenMenu();
        boolean cond = condition.getAsBoolean();
        scripts.Utility.Utils.abc2ReactionSleep(currentTime);
        return cond;
    }


    public static boolean abc2SkillingWaitCondition(BooleanSupplier condition, int min, int max) {
        currentTime = System.currentTimeMillis();
        Timing.waitCondition(() -> {
            General.sleep(General.random(350,900));

            AntiBan.timedActions();

            return (condition.getAsBoolean() || Interfaces.isInterfaceSubstantiated(233, 2));
        }, General.random(min, max));

        AntiBan.resetShouldOpenMenu();
        boolean cond = condition.getAsBoolean();
        scripts.Utility.Utils.abc2ReactionSleep(currentTime);
        return cond;
    }

    public static boolean abc2SkillingWaitCondition(boolean condition, int min, int max) {
        currentTime = System.currentTimeMillis();
        Timing.waitCondition(() -> {
            General.sleep(General.random(350,900));
           AntiBan.timedActions();

            return (condition || Interfaces.isInterfaceSubstantiated(233, 2));
        }, General.random(min, max));

        AntiBan.resetShouldOpenMenu();
        scripts.Utility.Utils.abc2ReactionSleep(currentTime);
        return condition;
    }


    public static boolean abc2WaitCondition(boolean condition, int min, int max) {
        currentTime = System.currentTimeMillis();
        Timing.waitCondition(() -> {
            General.sleep(General.random(350,900));
            AntiBan.timedActions();
            return (condition);
        }, General.random(min, max));
        AntiBan.resetShouldOpenMenu();
        scripts.Utility.Utils.abc2ReactionSleep(currentTime);
        return true;
    }


    public static boolean abc2WaitCondition(BooleanSupplier condition, int min, int max) {
        currentTime = System.currentTimeMillis();
        Timing.waitCondition(() -> {
            General.sleep(100, 600);
            return (condition.getAsBoolean());
        }, General.random(min, max));

        scripts.Utility.Utils.abc2ReactionSleep(currentTime);
        return condition.getAsBoolean();
    }


}

