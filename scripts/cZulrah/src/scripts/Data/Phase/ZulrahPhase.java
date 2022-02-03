package scripts.Data.Phase;

import org.tribot.script.sdk.Prayer;
import org.tribot.script.sdk.types.LocalTile;
import org.tribot.script.sdk.types.Npc;

import java.awt.Color;


public class ZulrahPhase
{
    private static final Color RANGE_COLOR = new Color(150, 255, 0, 100);
    private static final Color MAGIC_COLOR = new Color(20, 170, 200, 100);
    private static final Color MELEE_COLOR = new Color(180, 50, 20, 100);
    private static final Color JAD_COLOR = new Color(255, 115, 0, 100);

    private final ZulrahLocation zulrahLocation;
    private final ZulrahType type;
    private final boolean jad;
    private final StandLocation standLocation;
    private final Prayer prayer;

    public ZulrahPhase(ZulrahLocation zulrahLocation, ZulrahType type, boolean jad, StandLocation standLocation, Prayer prayer)
    {
        this.zulrahLocation = zulrahLocation;
        this.type = type;
        this.jad = jad;
        this.standLocation = standLocation;
        this.prayer = prayer;
    }

    public static ZulrahPhase valueOf(Npc zulrah, LocalTile start)
    {
        ZulrahLocation zulrahLocation = ZulrahLocation.valueOf(start, zulrah.getTile().toLocalTile());
        ZulrahType zulrahType = ZulrahType.valueOf(zulrah.getId());
        if (zulrahLocation == null || zulrahType == null)
        {
            return null;
        }
        StandLocation standLocation = zulrahType == ZulrahType.MAGIC ? StandLocation.PILLAR_WEST_OUTSIDE : StandLocation.TOP_EAST;
        Prayer prayer = zulrahType == ZulrahType.MAGIC ? Prayer.PROTECT_FROM_MAGIC : null;
        return new ZulrahPhase(zulrahLocation, zulrahType, false, standLocation, prayer);
    }

    @Override
    public String toString()
    {
        return "ZulrahPhase{" +
                "zulrahLocation=" + zulrahLocation +
                ", type=" + type +
                ", jad=" + jad +
                ", standLocation=" + standLocation +
                ", prayer=" + prayer +
                '}';
    }

    // world location
    public LocalTile getZulrahTile(LocalTile startTile)
    {
        // NORTH doesn't need changing because it is the start
        switch (zulrahLocation)
        {
            case SOUTH:
                return new LocalTile(startTile.getX(), startTile.getY() - (11 * 128));
            case EAST:
                return new LocalTile(startTile.getX() + (10 * 128), startTile.getY() - (2 * 128));
            case WEST:
                return new LocalTile(startTile.getX() - (10 * 128), startTile.getY() - (2 * 128));
        }
        return startTile;
    }

    // world location
    public LocalTile getStandTile(LocalTile startTile)
    {
        switch (standLocation)
        {
            case WEST:
                return  startTile.translate(-5, 0);
            case EAST:
                return startTile.translate(5, -2);
            case SOUTH:
                return startTile.translate(0, -6);
            case SOUTH_WEST:
                return startTile.translate(-4, -4);
            case SOUTH_EAST:
                return startTile.translate(0, -2);
            case TOP_EAST:
                return startTile.translate(6, 2);
            case TOP_WEST:
                return startTile.translate(-4, 3);
            case PILLAR_WEST_INSIDE:
                return startTile.translate(-4, -3);
            case PILLAR_WEST_OUTSIDE:
                return startTile.translate(-5, -3);
            case PILLAR_EAST_INSIDE:
                return startTile.translate(4, -3);
            case PILLAR_EAST_OUTSIDE:
                return startTile.translate(4, -4);
        }
        return startTile;
    }

    public ZulrahType getType()
    {
        return type;
    }

    public boolean isJad()
    {
        return jad;
    }

    public StandLocation getStandLocation()
    {
        return standLocation;
    }

    public Prayer getPrayer()
    {
        return prayer;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
        {
            return true;
        }
        if (obj == null || getClass() != obj.getClass())
        {
            return false;
        }
        ZulrahPhase other = (ZulrahPhase) obj;
        return this.jad == other.jad && this.zulrahLocation == other.zulrahLocation && this.type == other.type;
    }

    public Color getColor()
    {
        if (jad)
        {
            return JAD_COLOR;
        }
        else
        {
            switch (type)
            {
                case RANGE:
                    return RANGE_COLOR;
                case MAGIC:
                    return MAGIC_COLOR;
                case MELEE:
                    return MELEE_COLOR;
            }
        }
        return RANGE_COLOR;
    }
}