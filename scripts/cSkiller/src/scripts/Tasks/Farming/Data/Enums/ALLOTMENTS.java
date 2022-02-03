package scripts.Tasks.Farming.Data.Enums;

import lombok.Getter;
import org.tribot.api.General;
import org.tribot.api2007.Skills;
import org.tribot.api2007.types.RSArea;
import org.tribot.script.sdk.Log;
import scripts.Tasks.Farming.Data.Areas;
import scripts.Tasks.Farming.Data.FarmConst;
import scripts.Tasks.Farming.Data.FarmVars;

import java.util.List;

public enum ALLOTMENTS {

    POTATO_SEEDS(5318),
    ONION_SEEDS(5319),
    CABBAGE_SEEDS(5324),
    TOMATO_SEEDS(5322),
    SWEETCORN_SEEDS(5320),
    STRAWBERRY_SEEDS(5323),
    WATERMELON_SEEDS(5321),
    SNAPEGRASS_SEEDS(22879); //?


    @Getter
    private int seedId;


    ALLOTMENTS(int seedId) {
        this.seedId = seedId;
    }

    public static int determineAllotmentSeedId() {
        if (Skills.getCurrentLevel(Skills.SKILLS.FARMING) < 5) {
            return FarmVars.get().currentAllotmentId = ALLOTMENTS.POTATO_SEEDS.getSeedId();

        } else if (Skills.getCurrentLevel(Skills.SKILLS.FARMING) < 7) {
            return FarmVars.get().currentAllotmentId = ALLOTMENTS.ONION_SEEDS.getSeedId();

        } else if (Skills.getCurrentLevel(Skills.SKILLS.FARMING) < 12) {
            return FarmVars.get().currentAllotmentId = ALLOTMENTS.CABBAGE_SEEDS.getSeedId();

        } else if (Skills.getCurrentLevel(Skills.SKILLS.FARMING) < 20) {
            return FarmVars.get().currentAllotmentId = ALLOTMENTS.TOMATO_SEEDS.getSeedId();

        } else if (Skills.getCurrentLevel(Skills.SKILLS.FARMING) < 31) {
            return FarmVars.get().currentAllotmentId = ALLOTMENTS.SWEETCORN_SEEDS.getSeedId();

        } else if (Skills.getCurrentLevel(Skills.SKILLS.FARMING) < 47) {
            return FarmVars.get().currentAllotmentId = ALLOTMENTS.STRAWBERRY_SEEDS.getSeedId();

        } else if (Skills.getCurrentLevel(Skills.SKILLS.FARMING) < 61) {
            return FarmVars.get().currentAllotmentId = ALLOTMENTS.WATERMELON_SEEDS.getSeedId();

        } else if (Skills.getCurrentLevel(Skills.SKILLS.FARMING) >= 61) {
            return FarmVars.get().currentAllotmentId = ALLOTMENTS.SNAPEGRASS_SEEDS.getSeedId();
        }
        return -1;
    }

}
