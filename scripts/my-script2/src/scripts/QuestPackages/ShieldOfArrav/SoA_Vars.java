package scripts.QuestPackages.ShieldOfArrav;


import javax.swing.text.html.Option;
import java.util.Optional;

public class SoA_Vars {

    private static SoA_Vars vars;

    public static SoA_Vars get() {
        return vars == null ? vars = new SoA_Vars() : vars;
    }

    public Optional<String> partnerName = Optional.empty();

}
