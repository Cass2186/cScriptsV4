package scripts.Requirements;

import org.tribot.api2007.Player;
import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSTile;
import org.tribot.script.sdk.types.Area;
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
    private List<Area> zonesNew = new ArrayList<>();
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
    public AreaRequirement(Area area) {
        zonesNew.add(area);
        this.checkInZone = true;
    }

    private AreaRequirement(Builder builder) {
        zones = builder.zones;
        checkInZone = builder.checkInZone;
        displayText = builder.displayText;
    }

    @Override
    public boolean check() {
        for (int i = 0; i < zones.size(); i++) {
            if (zones.get(i).contains(Player.getPosition()))
                return true;
        }
        for (int i = 0; i < zonesNew.size(); i++) {
            if (zonesNew.get(i).containsMyPlayer())
                return true;
        }
        return false;
    }

    public static final class Builder {
        private List<RSArea> zones;
        private final boolean checkInZone;
        private String displayText;

        public Builder(boolean checkInZone) {
            this.checkInZone = checkInZone;
        }

        public Builder zones(List<RSArea> val) {
            zones = val;
            return this;
        }

        public Builder displayText(String val) {
            displayText = val;
            return this;
        }

        public AreaRequirement build() {
            return new AreaRequirement(this);
        }
    }
}
