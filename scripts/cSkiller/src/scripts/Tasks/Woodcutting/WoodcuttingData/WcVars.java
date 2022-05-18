package scripts.Tasks.Woodcutting.WoodcuttingData;

import scripts.Data.Vars;

import java.util.Optional;

public class WcVars {
    private static WcVars vars;

    public static WcVars get() {
        return vars == null ? vars = new WcVars() : vars;
    }


    public static void reset() {
        vars = new WcVars();
    }

    public boolean shouldOverrideArea = false;
    public Optional<WcLocations> overrideWcLocation = Optional.empty();

}
