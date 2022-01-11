package scripts.Tasks.Farming.Data.Enums;

import lombok.Getter;
import org.tribot.api.General;
import org.tribot.api2007.Skills;
import scripts.Tasks.Farming.Data.FarmVars;
import scripts.Utils;

public enum HERBS {

    GUAM(5291),
    MARRENTILL(5292),
    TARROMIN(5293),
    HARRALANDER(5294),
    RANARR(5295),
    TOADFLAX(5296),
    IRIT(5297),
    KWUARM(5299),
    SNAPDRAGON(5300);

    @Getter
    private int id;
    @Getter
    private String name;

    private HERBS(int id) {
        this.id = id;
    }

    public static int determineHerbId() {
        if (Skills.getCurrentLevel(Skills.SKILLS.FARMING) < 9) {
            return -1;
        } else if (Skills.getCurrentLevel(Skills.SKILLS.FARMING) < 14) {
            return FarmVars.get().currentHerbId = GUAM.getId(); //guam

        } else if (Skills.getCurrentLevel(Skills.SKILLS.FARMING) < 19) {
            return FarmVars.get().currentHerbId = MARRENTILL.getId(); // marrentill

        } else if (Skills.getCurrentLevel(Skills.SKILLS.FARMING) < 26) {
            return FarmVars.get().currentHerbId = TARROMIN.getId(); //tarromin

        } else if (Skills.getCurrentLevel(Skills.SKILLS.FARMING) < 32) {
            return FarmVars.get().currentHerbId = HARRALANDER.getId(); //harralandar

        } else if (Skills.getCurrentLevel(Skills.SKILLS.FARMING) < 82) { // should be 38
            return FarmVars.get().currentHerbId = RANARR.getId(); //ranarr

        } else if (Skills.getCurrentLevel(Skills.SKILLS.FARMING) < 44) {
            return FarmVars.get().currentHerbId = TOADFLAX.getId(); // toadflax

        } else if (Skills.getCurrentLevel(Skills.SKILLS.FARMING) < 50) {
            return FarmVars.get().currentHerbId = IRIT.getId(); //irit

        } else if (Skills.getCurrentLevel(Skills.SKILLS.FARMING) < 56) {
            return FarmVars.get().currentHerbId = KWUARM.getId(); // kwuarm

        } else if (Skills.getCurrentLevel(Skills.SKILLS.FARMING) >= 62) {
            return FarmVars.get().currentHerbId = SNAPDRAGON.getId(); // snapdragon
        }

        return -1;
    }
}
