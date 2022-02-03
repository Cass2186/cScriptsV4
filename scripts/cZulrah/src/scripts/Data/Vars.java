package scripts.Data;

public class Vars {

    private static Vars vars;

    public static Vars get() {
        return vars == null ? vars = new Vars() : vars;
    }

    public boolean shouldPrayMagic = false;
    public boolean shouldPrayRanged = false;
}
