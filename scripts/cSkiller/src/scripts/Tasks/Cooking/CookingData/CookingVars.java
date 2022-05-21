package scripts.Tasks.Cooking.CookingData;

public class CookingVars {

    private static CookingVars vars;

    public static CookingVars get() {
        return vars == null ? vars = new CookingVars() : vars;
    }


    public static void reset() {
        vars = new CookingVars();
    }

    public CookAreas cookArea = CookAreas.CATHERBY;


}
