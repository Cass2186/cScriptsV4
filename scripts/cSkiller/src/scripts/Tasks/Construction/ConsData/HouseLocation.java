package scripts.Tasks.Construction.ConsData;

import lombok.Getter;
import org.tribot.script.sdk.GameState;

public enum HouseLocation {
    NONE(0),
    RIMMINGTON(1),
    TAVERLY(2),
    POLLNIVNEACH(3),
    HOSIDIUS(4),
    RELLEKKA(5),
    BRIMHAVEN(6),
    YANILLE(7),
    PRIFDDINAS(8);

    @Getter
    int varbitValue;

    HouseLocation(int setting) {
        this.varbitValue = setting;
    }

    public boolean isSetToThisLocation(){
        return GameState.getVarbit(2187) == this.varbitValue;
    }

    public static HouseLocation getCurrentHouseLocation(){
        switch ( GameState.getVarbit(2187)){
            case (1):
                return RIMMINGTON;
            case (2):
                return TAVERLY;
            case (3):
                return POLLNIVNEACH;
            case (4):
                return HOSIDIUS;
            case (5):
                return RELLEKKA;
            case (6):
                return BRIMHAVEN;
            case (7):
                return YANILLE;
            case (8):
                return PRIFDDINAS;
            default:
                return NONE;

        }
    }
}