package scripts.Requirements;

import org.tribot.api2007.Player;
import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSTile;
import scripts.QuestUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;


/**
 * Modified from Runelite code
 * https://github.com/Zoinkwiz/quest-helper/blob/54aff3ddd0bfee9fc629b46a399ffa062357332d/src/main/java/com/questhelper/requirements/ZoneRequirement.java#L40
 */
public class AreaRequirement implements Requirement {

    private List<RSArea> zones = new ArrayList<>();
    private final boolean checkInZone;
    private String displayText;

    /**
     * Check if the player is either in the specified zone.
     *
     * @param displayText display text
     * @param zone        the zone to check
     */
    public AreaRequirement(String displayText, RSArea zone) {
        this(displayText, false, zone);
    }

    /**
     * Check if the player is either in, or not in, the specified zone.
     *
     * @param displayText    display text
     * @param checkNotInZone true to negate this requirement check (i.e. it will check if the player is NOT in the zone)
     * @param zone           the zone to check
     */
    public AreaRequirement(String displayText, boolean checkNotInZone, RSArea zone) {
        this.displayText = displayText;
        this.checkInZone = !checkNotInZone; // This was originally 'checkNotInZone' so we have to maintain that behavior
        this.zones = QuestUtil.toArrayList(zone);
    }

    public AreaRequirement(RSTile... worldPoints) {

        this.zones = Stream.of(worldPoints).map(wp -> new RSArea(wp, 1))
                .collect(QuestUtil.collectToArrayList());
        this.checkInZone = true;
    }

    public AreaRequirement(RSArea... zone) {
        this.zones = QuestUtil.toArrayList(zone);
        this.checkInZone = true;
    }

    public AreaRequirement(boolean checkInZone, RSArea... zone) {
        this.zones = QuestUtil.toArrayList(zone);
        this.checkInZone = checkInZone;
    }

    public AreaRequirement(boolean checkInZone, RSTile... worldPoints) {
        this.zones = Stream.of(worldPoints).map(wp -> new RSArea(wp, 1))
                .collect(QuestUtil.collectToArrayList());
        this.checkInZone = checkInZone;
    }

    public AreaRequirement(RSArea area) {
        zones.add(area);
        this.checkInZone = true;
    }

    @Override
    public boolean check() {
        for (int i = 0; i < zones.size(); i++) {
            if (zones.get(i).contains(Player.getPosition()))
                return true;
        }
        return false;
    }
}
