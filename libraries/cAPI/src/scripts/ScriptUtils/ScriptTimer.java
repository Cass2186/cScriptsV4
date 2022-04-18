package scripts.ScriptUtils;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.script.sdk.Log;
import org.tribot.script.sdk.ScriptListening;

public class ScriptTimer {

    private static long startTime = System.currentTimeMillis();
    private static long pauseTime, breakTime = 0;
    private static long pauseStart = -1, breakStart = -1;

    static {
        ScriptListening.addBreakStartListener(time -> onBreakStart(time));
        ScriptListening.addBreakEndListener(() -> onBreakEnd());
        ScriptListening.addPauseListener(() -> onPause());
        ScriptListening.addResumeListener(() -> onResume());
    }

    public static long getRuntime() {
        return Timing.timeFromMark(startTime) - (getPauseTime() + getBreakTime());
    }

    public static String getRuntimeString() {
        return Timing.msToString(getRuntime());
    }

    public static long getPauseTime() {
        if (pauseStart != -1) {
            return pauseTime + Timing.timeFromMark(pauseStart);
        }
        return pauseTime;
    }

    public static long getBreakTime() {
        if (breakStart != -1) {
            return breakTime + Timing.timeFromMark(breakStart);
        }
        return breakTime;
    }

    public static void resetRuntime() {
        startTime = Timing.currentTimeMillis();
        pauseTime = breakTime = 0;
        pauseStart = breakStart = -1;
    }

    public static void onBreakStart(long l) {
        Log.info("Breaking for " + l);
        breakStart = Timing.currentTimeMillis();
    }

    public static void onBreakEnd() {
        breakTime += Timing.timeFromMark(breakStart);
        breakStart = -1;
    }

    public static void onPause() {
        Log.info("Pausing now");
        pauseStart = Timing.currentTimeMillis();
    }

    public static void onResume() {
        pauseTime += Timing.timeFromMark(pauseStart);
        pauseStart = -1;
    }
}
