package scripts.Tasks.Fishing.FishingData;

public class FishingVars {

    private static FishingVars vars;

    public static FishingVars get(){return vars == null ? new FishingVars() : vars;}
}
