package scripts.Tasks.Hunter.HunterData;

import scripts.Tasks.Cooking.CookingVars;

public class HunterVars {

    private static HunterVars vars;

    public static HunterVars get() {
        return vars == null ? vars = new HunterVars() : vars;
    }

    public TrapTypes currentTrapType = TrapTypes.BIRD_TRAP;

    public TrapTypes updateCurrentTrapType(){


        return TrapTypes.BIRD_TRAP;
    }


}
