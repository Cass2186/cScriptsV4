package scripts;

public class LazyTickListener {

    private static final long TICK_DELAY = 600;

    private long lastTick;

    public LazyTickListener() {
        lastTick = System.currentTimeMillis();
    }

    public void tick() {
        lastTick = System.currentTimeMillis() - 20;
    }

    public long timeRemainingTillNextTick() {
        return TICK_DELAY - ((System.currentTimeMillis() - lastTick) % TICK_DELAY);
    }

}