package scripts.Tasks.Defender.Data;

import scripts.Data.Vars;
import scripts.Utils;

public class DefenderVars {

    private static DefenderVars vars;

    public static DefenderVars get() {
        return vars == null ? vars = new DefenderVars() : vars;
    }

    public boolean shouldShowDefender = false;
    public boolean hasRuneDefender = false;
    public int eatAtPercent = Utils.random(35,65);
    public int minTokens = 100;
}

