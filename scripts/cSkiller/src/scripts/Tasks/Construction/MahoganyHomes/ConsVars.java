package scripts.Tasks.Construction.MahoganyHomes;


import java.util.Optional;

public class ConsVars {

    private static ConsVars vars;

    public static ConsVars get() {
        return vars == null ? vars = new ConsVars() : vars;
    }


    public static void reset() {
        vars = new ConsVars();
    }

    public Optional<Home> mahoganyHome = Optional.empty();

    public boolean isDoingHomes = false;
}
