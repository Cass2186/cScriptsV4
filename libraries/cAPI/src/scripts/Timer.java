package scripts;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.input.Mouse;
import org.tribot.api2007.Interfaces;
import org.tribot.api2007.types.RSObject;
import org.tribot.script.sdk.Waiting;
import org.tribot.script.sdk.Widgets;
import org.tribot.script.sdk.util.TribotRandom;

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
     * Instantiates a new Timer with a given time period in milliseconds.
     *
     * @param period Time period in milliseconds.
     */
    public Timer(final long period, long startMod) {
        this.period = period;
        start = System.currentTimeMillis() - startMod;
        end = start + period;
    }

    public static boolean isLvlUpInterfaceOpen() {
        return Widgets.get(233, 2).isPresent();
    }


    public String getFormatedRuntime() {
        long millis = System.currentTimeMillis() - SCRIPT_START_TIME;
        long second = (millis / 1000) % 60;
        long minute = (millis / (1000 * 60)) % 60;
        long hour = (millis / (1000 * 60 * 60)); //% 24;
        return String.format("%02d:%02d:%02d", hour, minute, second);
    }

    /**
     * @return Elapsed time
     */
    public static long getScriptElapsedTimeMs() {
        return System.currentTimeMillis() - Timer.SCRIPT_START_TIME;
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


    public long getElapsed() {
        if (isRunning()) {
            return this.period - getRemaining();
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


    // use when you manually are going to set the reaction time after...
    // ...using something like Utils.idlePredictableAction();
    public static boolean quickWaitCondition(BooleanSupplier condition, int min, int max) {
        return Waiting.waitUntil(General.random(min, max), () -> {
            Waiting.waitNormal(15, 5);
            return (condition.getAsBoolean());
        });
    }

    public static boolean waitCondition(BooleanSupplier condition, int min, int max) {
        return Waiting.waitUntil(General.random(min, max), () -> {
            Waiting.waitNormal(300, 60);
            return (condition.getAsBoolean());
        });
    }

    public static boolean waitCondition(int min, int step, BooleanSupplier condition) {
        return Waiting.waitUntil(TribotRandom.normal(min, (min / TribotRandom.uniform(8,10))),
                TribotRandom.normal(step, step/TribotRandom.uniform(8,10)), condition::getAsBoolean);
    }


    public static boolean waitCondition(BooleanSupplier condition, int min) {
        return Timing.waitCondition(() -> {
            Waiting.waitNormal(300, 60);
            return (condition.getAsBoolean());
        }, General.random(min, min + 4000));

    }

    public static boolean slowWaitCondition(BooleanSupplier condition, int min, int max) {
        return Timing.waitCondition(() -> {
            AntiBan.timedActions();
            Waiting.waitNormal(800, 200);
            return (condition.getAsBoolean());
        }, General.random(min, max));
    }

    public static boolean skillingWaitCondition(BooleanSupplier condition, int min, int max) {
        Timing.waitCondition(() -> {
            Waiting.waitUniform(1200, 2400);
            AntiBan.timedActions();
            return (condition.getAsBoolean() || isLvlUpInterfaceOpen());
        }, General.random(min, max));

        AntiBan.resetShouldOpenMenu();
        return condition.getAsBoolean();
    }

    public static boolean agilityWaitCondition(BooleanSupplier condition, int min, int max) {
        return Waiting.waitUntil(Utils.random(min, max),
                General.random(600, 1800), () -> {
                    AntiBan.timedActions();
                    return (condition.getAsBoolean() || isLvlUpInterfaceOpen());
                }
        );
    }

    public static boolean abc2SkillingWaitCondition(BooleanSupplier condition, RSObject nextClickObj, int min, int max) {
        currentTime = System.currentTimeMillis();
        boolean cond = Waiting.waitUntil(General.random(min, max), General.random(350, 1100),
                () -> {
                    AntiBan.timedActions();

                    if (AntiBan.getShouldHover() && Mouse.isInBounds()) {
                        General.println("[ABC2]: Hovering next object");
                        AntiBan.hoverEntityObject(nextClickObj);
                        AntiBan.resetShouldHover();
                    }

                    return (condition.getAsBoolean() || isLvlUpInterfaceOpen());
                });

        AntiBan.resetShouldOpenMenu();
        Utils.abc2ReactionSleep(currentTime);
        return cond;
    }

    public static boolean abc2SkillingWaitCondition(BooleanSupplier condition, RSObject nextClickObj, int loopSleep, int min, int max) {
        currentTime = System.currentTimeMillis();
        boolean cond = Timing.waitCondition(() -> {
            General.sleep(General.random(loopSleep, loopSleep + (loopSleep / 2)));

            AntiBan.timedActions();

            if (AntiBan.getShouldHover() && Mouse.isInBounds()) {
                General.println("[ABC2]: Hovering next object");
                AntiBan.hoverEntityObject(nextClickObj);
                AntiBan.resetShouldHover();
            }

            return (condition.getAsBoolean() || isLvlUpInterfaceOpen());
        }, General.random(min, max));

        AntiBan.resetShouldOpenMenu();
        Utils.abc2ReactionSleep(currentTime);
        return cond;
    }


    public static boolean abc2SkillingWaitCondition(BooleanSupplier condition, int min, int max) {
        currentTime = System.currentTimeMillis();
        boolean cond = Waiting.waitUntil(General.random(min, max), () -> {
            Waiting.waitUniform(350, 1100);
            AntiBan.timedActions();

            return (condition.getAsBoolean() || isLvlUpInterfaceOpen());
        });
        AntiBan.resetShouldOpenMenu();
        Utils.abc2ReactionSleep(currentTime);
        return cond;
    }

    public static boolean abc2SkillingWaitCondition(boolean condition, int min, int max) {
        currentTime = System.currentTimeMillis();
        boolean cond = Waiting.waitUntil(General.random(min, max), () -> {
            Waiting.waitUniform(350, 1100);
            AntiBan.timedActions();

            return (condition || isLvlUpInterfaceOpen());
        });

        AntiBan.resetShouldOpenMenu();
        Utils.abc2ReactionSleep(currentTime);
        return cond;
    }


    public static boolean abc2WaitCondition(boolean condition, int min, int max) {
        currentTime = System.currentTimeMillis();
        boolean cond = Waiting.waitUntil(General.random(min, max), () -> {
            Waiting.waitUniform(100, 600);
            AntiBan.timedActions();
            return (condition || isLvlUpInterfaceOpen());
        });
        AntiBan.resetShouldOpenMenu();
        Utils.abc2ReactionSleep(currentTime);
        return cond;
    }


    public static boolean abc2WaitCondition(BooleanSupplier condition, int min, int max) {
        currentTime = System.currentTimeMillis();
        boolean cond = Waiting.waitUntil(General.random(min, max), () -> {
            Waiting.waitUniform(100, 600);
            AntiBan.timedActions();

            return (condition.getAsBoolean() || isLvlUpInterfaceOpen());
        });
        Utils.abc2ReactionSleep(currentTime);
        return cond;
    }

    public static boolean customWait(boolean condition, int minLoop, int maxLoop, int timeoutMin) {
        currentTime = System.currentTimeMillis();
        if (Waiting.waitUntil(timeoutMin, () -> {
            Waiting.waitUniform(minLoop, maxLoop);
            AntiBan.timedActions();

            return (condition || isLvlUpInterfaceOpen());
        })) {
            AntiBan.resetShouldOpenMenu();
            Utils.abc2ReactionSleep(currentTime);
            return true;
        }
        return false;
    }


}

