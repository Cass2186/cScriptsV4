package scripts.Tasks.Construction.MahoganyHomes;

import com.google.common.collect.ImmutableSet;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.WordUtils;
import org.tribot.script.sdk.types.Area;
import org.tribot.script.sdk.types.WorldTile;
import scripts.NpcID;

@Getter
public enum Home
{
    // area is based on bounds of house not area at which stuff loads in for the homes
    // Ardy
    JESS(Area.fromRadius(new WorldTile(2611, 3290, 0), 7), "Upstairs of the building south of the church in East Ardougne",
            NpcID.JESS, new WorldTile(2621, 3292, 0), new RequiredMaterials(12, 15, 0, 1), 17026, 16685),
    NOELLA(Area.fromRadius(new WorldTile(2652, 3317,0), 8), "North of East Ardougne market",
            NpcID.NOELLA, new WorldTile(2659, 3322, 0), new RequiredMaterials(11, 15, 0, 0), 17026, 16685, 15645, 15648),
    ROSS(Area.fromRadius(new WorldTile(2609, 3313,0), 6), "North of the church in East Ardougne",
            NpcID.ROSS, new WorldTile(2613, 3316, 0), new RequiredMaterials(8, 11, 0, 1), 16683, 16679),

    // Falador
    LARRY(Area.fromRadius(new WorldTile(3033, 3360,0), 5), "North of the fountain in Falador",
            NpcID.LARRY_10418, new WorldTile(3038, 3364, 0), new RequiredMaterials(9, 12, 0, 1), 24075, 24076),
    NORMAN(Area.fromRadius(new WorldTile(3034, 3341,0), 4), "South of the fountain in Falador",
            NpcID.NORMAN, new WorldTile(3038, 3344, 0), new RequiredMaterials(9, 13, 0, 1), 24082, 24085),
    TAU(Area.fromRadius(new WorldTile(3043, 3340,0),5), "South east of the fountain in Falador",
            NpcID.TAU, new WorldTile(3047, 3345, 0), new RequiredMaterials(9, 13, 0, 1)),

    // Hosidus
    BARBARA(Area.fromRadius(new WorldTile(1746, 3531),6), "South of Hosidius, near the mill",
            NpcID.BARBARA, new WorldTile(1750, 3534, 0), new RequiredMaterials(9, 10, 0, 1)),
    LEELA(Area.fromRadius(new WorldTile(1781, 3589) ,5 ), "East of the town market in Hosidius",
            NpcID.LEELA_10423, new WorldTile(1785, 3592, 0), new RequiredMaterials(9, 13, 0, 1), 11794, 11802),
    MARIAH(Area.fromRadius(new WorldTile(1762, 3618),5), "West of the estate agents in Hosidius",
            NpcID.MARIAH, new WorldTile(1766, 3621, 0), new RequiredMaterials(11, 14, 0, 1), 11794, 11802),

    // Varrock
    BOB(Area.fromRadius(new WorldTile(3234, 3482),5), "North-east Varrock, opposite the church",
            NpcID.BOB_10414, new WorldTile(3238, 3486, 0), new RequiredMaterials(13, 17, 0, 0), 11797, 11799),
    JEFF(Area.fromRadius(new WorldTile(3235, 3445),6), "Middle of Varrock, west of the museum",
            NpcID.JEFF_10415, new WorldTile(3239, 3450, 0), new RequiredMaterials(11, 16, 0, 0), 11789, 11793),
    SARAH(Area.fromRadius(new WorldTile(3232, 3381),4), "Along the south wall of Varrock",
            NpcID.SARAH_10416, new WorldTile(3235, 3384, 0), new RequiredMaterials(11, 11, 0, 1));

    private final Area area;
    private final String hint;
    private final int npcId;
    private final WorldTile location;
    private final Integer[] ladders;
    private final RequiredMaterials requiredMaterials;

    Home(final Area area, final String hint, final int npcId, final WorldTile location, final RequiredMaterials requiredMaterials, final Integer... ladders)
    {
        this.area = area;
        this.hint = hint;
        this.npcId = npcId;
        this.location = location;
        this.ladders = ladders;
        this.requiredMaterials = requiredMaterials;
    }

    String getName() {
        return StringUtils.capitalize(name().toLowerCase());
    }

    String getRequiredPlanks()
    {
        if (this.requiredMaterials.MinPlanks == this.requiredMaterials.MaxPlanks)
        {
            return String.format("%d planks", this.requiredMaterials.MinPlanks);
        }

        return String.format("%d - %d planks", this.requiredMaterials.MinPlanks, this.requiredMaterials.MaxPlanks);
    }

    String getRequiredSteelBars()
    {
        if (this.requiredMaterials.MinSteelBars + this.requiredMaterials.MaxSteelBars == 0)
        {
            return null;
        }

        if (this.requiredMaterials.MinSteelBars == this.requiredMaterials.MaxSteelBars)
        {
            return String.format("%d steel bar", this.requiredMaterials.MinSteelBars);
        }

        return String.format("%d - %d steel bars", this.requiredMaterials.MinSteelBars, this.requiredMaterials.MaxSteelBars);
    }

    private static final ImmutableSet<Integer> LADDERS;
    static
    {
        final ImmutableSet.Builder<Integer> b = new ImmutableSet.Builder<>();
        for (final Home h : values())
        {
            b.add(h.getLadders());
        }
        LADDERS = b.build();
    }

    static boolean isLadder(final int objID)
    {
        return LADDERS.contains(objID);
    }
}
