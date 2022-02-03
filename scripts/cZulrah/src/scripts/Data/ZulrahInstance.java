package scripts.Data;


import org.tribot.script.sdk.Prayer;
import org.tribot.script.sdk.types.LocalTile;
import org.tribot.script.sdk.types.Npc;
import scripts.Data.Patterns.ZulrahPattern;
import scripts.Data.Phase.StandLocation;
import scripts.Data.Phase.ZulrahLocation;
import scripts.Data.Phase.ZulrahPhase;
import scripts.Data.Phase.ZulrahType;

import javax.annotation.Nullable;

public class ZulrahInstance
{
    private static final ZulrahPhase NO_PATTERN_MAGIC_PHASE = new ZulrahPhase(
            ZulrahLocation.NORTH,
            ZulrahType.MAGIC,
            false,
            StandLocation.PILLAR_WEST_OUTSIDE,
            Prayer.PROTECT_FROM_MAGIC
    );
    private static final ZulrahPhase NO_PATTERN_RANGE_PHASE = new ZulrahPhase(
            ZulrahLocation.NORTH,
            ZulrahType.RANGE,
            false,
            StandLocation.TOP_EAST,
            Prayer.PROTECT_FROM_MISSILES
    );
    private static final ZulrahPhase PATTERN_A_OR_B_RANGE_PHASE = new ZulrahPhase(
            ZulrahLocation.NORTH,
            ZulrahType.RANGE,
            false,
            StandLocation.PILLAR_WEST_OUTSIDE,
            Prayer.PROTECT_FROM_MISSILES
    );

    private final LocalTile startLocation;
    private ZulrahPattern pattern;
    private int stage;
    private ZulrahPhase phase;

    ZulrahInstance(final Npc zulrah) {
        this.startLocation = zulrah.getTile().toLocalTile();
    }

    public LocalTile getStartLocation() {
        return startLocation;
    }

    public ZulrahPattern getPattern() {
        return pattern;
    }

    public void setPattern(ZulrahPattern pattern)
    {
        this.pattern = pattern;
    }

    int getStage()
    {
        return stage;
    }

    void nextStage()
    {
        ++stage;
    }

    public void reset()
    {
        pattern = null;
        stage = 0;
    }

    @Nullable
    public ZulrahPhase getPhase()
    {
        ZulrahPhase patternPhase = null;
        if (pattern != null)
        {
            patternPhase = pattern.get(stage);
        }
        return patternPhase != null ? patternPhase : phase;
    }

    public void setPhase(ZulrahPhase phase)
    {
        this.phase = phase;
    }

    @Nullable
    public ZulrahPhase getNextPhase()
    {
        if (pattern != null)
        {
            return pattern.get(stage + 1);
        }
        else if (phase != null)
        {
            ZulrahType type = phase.getType();
            StandLocation standLocation = phase.getStandLocation();
            if (type == ZulrahType.MELEE)
            {
                return standLocation == StandLocation.TOP_EAST ? NO_PATTERN_MAGIC_PHASE : NO_PATTERN_RANGE_PHASE;
            }
            if (type == ZulrahType.MAGIC)
            {
                return standLocation == StandLocation.TOP_EAST ? NO_PATTERN_RANGE_PHASE : PATTERN_A_OR_B_RANGE_PHASE;
            }
        }
        return null;
    }
}