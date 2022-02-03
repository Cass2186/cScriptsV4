package scripts.Data.Patterns;


import java.util.ArrayList;
import java.util.List;

import org.tribot.script.sdk.Prayer;
import scripts.Data.Phase.StandLocation;
import scripts.Data.Phase.ZulrahLocation;
import scripts.Data.Phase.ZulrahPhase;
import scripts.Data.Phase.ZulrahType;

public abstract class ZulrahPattern
{
    private final List<ZulrahPhase> pattern = new ArrayList<>();

    final void add(ZulrahLocation loc, ZulrahType type, StandLocation standLocation, Prayer prayer)
    {
        add(loc, type, standLocation, false, prayer);
    }

    final void addJad(ZulrahLocation loc, ZulrahType type, StandLocation standLocation, Prayer prayer)
    {
        add(loc, type, standLocation, true, prayer);
    }

    private void add(ZulrahLocation loc, ZulrahType type, StandLocation standLocation, boolean jad, Prayer prayer)
    {
        pattern.add(new ZulrahPhase(loc, type, jad, standLocation, prayer));
    }

    public ZulrahPhase get(int index)
    {
        if (index >= pattern.size())
        {
            return null;
        }

        return pattern.get(index);
    }

    public boolean stageMatches(int index, ZulrahPhase instance)
    {
        ZulrahPhase patternInstance = get(index);
        return patternInstance != null && patternInstance.equals(instance);
    }

    public boolean canReset(int index)
    {
        return index >= pattern.size();
    }
}