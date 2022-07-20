package scripts.Tasks.Magic;

import scripts.Data.Vars;

public class MagicVars {

    private static MagicVars vars;

    public static MagicVars get() {
        return vars == null ? vars = new MagicVars() : vars;
    }


    public static void reset() {
        vars = new MagicVars();
    }

    public boolean useProgressiveStunAlch = false;
    public boolean useTeleAlch = false;

}
