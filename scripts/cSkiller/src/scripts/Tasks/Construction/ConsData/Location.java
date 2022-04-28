package scripts.Tasks.Construction.ConsData;

import lombok.Getter;
import org.tribot.script.sdk.GameState;

public enum Location {
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

    Location(int setting) {
        this.varbitValue = setting;
    }

    public boolean isSetToThisLocation(){
        return GameState.getVarbit(2187) == this.varbitValue;
    }

}