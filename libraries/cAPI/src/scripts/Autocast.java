package scripts;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api2007.Game;
import org.tribot.api2007.GameTab;
import org.tribot.api2007.Interfaces;
import org.tribot.api2007.types.RSInterfaceChild;
import org.tribot.api2007.types.RSInterfaceComponent;

public enum Autocast {


    /***************************************************************
     Author @Jamie w/ my modificaitons
     USAGE

     if (!Autocast.isAutocastEnabled(Autocast.FIRE_STRIKE)) {
     Autocast.enableAutocast(Autocast.FIRE_STRIKE);
     }
     **************************************************************/


    AIR_STRIKE(1, 3),
    WATER_STRIKE(2, 5),
    EARTH_STRIKE(3, 7),
    FIRE_STRIKE(4, 9),
    WIND_BOLT(5, 11),
    WATER_BOLT(6, 13),
    EARTH_BOLT(7, 15),
    FIRE_BOLT(8, 17),
    WIND_BLAST(9, 19),
    WATER_BLAST(10, 21),
    EARTH_BLAST(11, 23),
    FIRE_BLAST(12, 25),
    WIND_WAVE(13, 27),
    WATER_WAVE(14, 29),
    EARTH_WAVE(15, 31),
    FIRE_WAVE(16, 33),
    WIND_SURGE(17, 35),
    WATER_SURGE(18, 37),
    EARTH_SURGE(19, 39),
    FIRE_SURGE(20, 41);

    private int componentID;
    private int enabledSetting;

    Autocast(int componentID, int enabledSetting) {
        this.componentID = componentID;
        this.enabledSetting = enabledSetting;
    }

    public static boolean isAutocastEnabled(Autocast spellName) {
        int autocastSetting = Game.getSetting(108);
        return spellName.enabledSetting == autocastSetting;
    }

    public static boolean enableAutocast(Autocast spellName) {
        RSInterfaceChild autocastSelection = Interfaces.get(201, 1);
        RSInterfaceChild autocastSelectionBox = Interfaces.get(593, 26);
        if (Game.getSetting(108) != spellName.enabledSetting) {
            if (GameTab.open(GameTab.TABS.COMBAT) && autocastSelectionBox != null) {

                if (autocastSelectionBox.click()) {
                    General.println("[Autocast]: Seleciton box click");
                    Timing.waitCondition(() -> Interfaces.isInterfaceSubstantiated(201, 1), 4000);
                }

            }
            autocastSelection = Interfaces.get(201, 1);
            if (autocastSelection != null) {
                RSInterfaceComponent spellToSelect = autocastSelection.getChild(spellName.componentID);
                if (spellToSelect != null && spellToSelect.click())
                    return Timing.waitCondition(() -> Game.getSetting(108) == spellName.enabledSetting, 3000);
            }

        }
        return Game.getSetting(108) == spellName.enabledSetting;
    }


}

