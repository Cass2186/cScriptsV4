package scripts.Tasks.Farming.Data.Enums;

import org.tribot.api.General;
import org.tribot.api2007.Skills;
import scripts.Tasks.Farming.Data.FarmConst;
import scripts.Tasks.Farming.Data.FarmVars;

public class ALLOTMENTS {


    public static int determineAllotmentId() {
        if (Skills.getCurrentLevel(Skills.SKILLS.FARMING) < 5) {
            return FarmVars.get().currentAllotmentId = FarmConst.POTATO_SEEDS;

        } else if (Skills.getCurrentLevel(Skills.SKILLS.FARMING) < 7) {
            return FarmVars.get().currentAllotmentId = FarmConst.ONION_SEEDS;

        } else if (Skills.getCurrentLevel(Skills.SKILLS.FARMING) < 12) {
            return FarmVars.get().currentAllotmentId = FarmConst.CABBAGE_SEEDS;

        } else if (Skills.getCurrentLevel(Skills.SKILLS.FARMING) < 20) {
            return FarmVars.get().currentAllotmentId = FarmConst.TOMATO_SEEDS;

        } else if (Skills.getCurrentLevel(Skills.SKILLS.FARMING) < 31) {
            return FarmVars.get().currentAllotmentId = FarmConst.SWEETCORN_SEEDS;

        } else if (Skills.getCurrentLevel(Skills.SKILLS.FARMING) < 47) {
            return FarmVars.get().currentAllotmentId = FarmConst.STRAWBERRY_SEEDS;

        } else if (Skills.getCurrentLevel(Skills.SKILLS.FARMING) < 61) {
            return FarmVars.get().currentAllotmentId = FarmConst.WATERMELON_SEEDS;

        } else if (Skills.getCurrentLevel(Skills.SKILLS.FARMING) >= 61) {
            return FarmVars.get().currentAllotmentId = FarmConst.SNAPEGRASS_SEEDS;
        }
        return -1;
    }

}
