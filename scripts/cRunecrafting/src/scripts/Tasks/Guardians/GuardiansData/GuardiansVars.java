package scripts.Tasks.Guardians.GuardiansData;

import org.tribot.script.sdk.util.TribotRandom;
import scripts.Data.Vars;

public class GuardiansVars {

    private static GuardiansVars vars;

    public static GuardiansVars get() {
        return vars == null ? vars = new GuardiansVars() : vars;
    }


    public static void reset() {
        vars = new GuardiansVars();
    }

    public int fragmentsToMine = TribotRandom.normal(150,8);

}
