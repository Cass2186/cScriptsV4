package scripts.Tasks.Cooking;

import scripts.Data.Vars;

public class CookingVars {

    private static CookingVars vars;

    public static CookingVars get() {
        return vars == null ? vars = new CookingVars() : vars;
    }


    public static void reset() {
        vars = new CookingVars();
    }



}
