package scripts.Nodes.TitheFarm.TitheData;


import org.tribot.script.sdk.types.Area;

import java.util.Optional;

public class TitheVars {

    private static TitheVars vars;

    public static TitheVars get() {
        return vars == null ? vars = new TitheVars() : vars;
    }

    public Optional<Area> workingArea = Optional.empty();


}
