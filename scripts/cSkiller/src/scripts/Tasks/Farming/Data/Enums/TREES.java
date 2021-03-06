package scripts.Tasks.Farming.Data.Enums;

import lombok.Getter;
import org.tribot.api.General;
import org.tribot.api2007.Skills;
import scripts.Tasks.Farming.Data.FarmVars;

public enum TREES {

    OAK_SAPPLING(5370, 15, 12000),
    WILLOW_SAPPLING(5371, 30, 16800),
    MAPLE_SAPPLING(5372, 45, 19200),
    YEW_SAPPLING(5373, 60, 24000),
    MAGIC_SAPPLING(5374, 75, 28800),

    // FRUIT
    APPLE_SAPPLING(5496, 27),
    BANANA_SAPPLING(5497, 33),
    ORANGE_SAPPLING(5498, 39),
    CURRY_SAPPLING(5499, 42),
    PINEAPPLE_SAPPLING(5500, 51),
    PAPAYA_SAPPLING(5501, 57),
    PALM_SAPPLING(5502, 68),
    DRAGONFRUIT_SAPPLING(22866, 81);


    @Getter
    private int id;
    @Getter
    private int levelReq;
    @Getter
    private int timeToGrowMs;

    TREES(int id, int level) {
        this.id = id;
        this.levelReq = level;
    }

    TREES(int id, int level, int secToGrow) {
        this.id = id;
        this.levelReq = level;
        this.timeToGrowMs = secToGrow * 1000;
    }

    public static int getCurrentTreeId() {
        if (Skills.getCurrentLevel(Skills.SKILLS.FARMING) < 15) {
            General.println("[Plant]: Too low of a level to do trees.");
            return -1;

        } else if (Skills.getCurrentLevel(Skills.SKILLS.FARMING) < 30) {
            return FarmVars.get().treeId = TREES.OAK_SAPPLING.getId();

        } else if (Skills.getCurrentLevel(Skills.SKILLS.FARMING) < 45) {
            return FarmVars.get().treeId = TREES.WILLOW_SAPPLING.getId();

        } else if (Skills.getCurrentLevel(Skills.SKILLS.FARMING) < 60) {
            return FarmVars.get().treeId = TREES.MAPLE_SAPPLING.getId();

        } else if (Skills.getCurrentLevel(Skills.SKILLS.FARMING) < 75) {
            return FarmVars.get().treeId = TREES.YEW_SAPPLING.getId();

        } else {
            return FarmVars.get().treeId = TREES.MAGIC_SAPPLING.getId();
        }
    }

    public static int determineFruitTreeId() {
        if (Skills.getCurrentLevel(Skills.SKILLS.FARMING) < 27) {
            General.println("[Plant]: Too low of a level to do Fruit trees.");
            return -1;

        } else if (Skills.getCurrentLevel(Skills.SKILLS.FARMING) < 33) {
            return FarmVars.get().fruitTreeId = TREES.APPLE_SAPPLING.getId();

        } else if (Skills.getCurrentLevel(Skills.SKILLS.FARMING) < 39) {
            return FarmVars.get().fruitTreeId = TREES.BANANA_SAPPLING.getId();

        } else if (Skills.getCurrentLevel(Skills.SKILLS.FARMING) < 42) {
            return FarmVars.get().fruitTreeId = TREES.ORANGE_SAPPLING.getId();

        } else if (Skills.getCurrentLevel(Skills.SKILLS.FARMING) < 51) {
            return FarmVars.get().fruitTreeId = TREES.CURRY_SAPPLING.getId();

        } else if (Skills.getCurrentLevel(Skills.SKILLS.FARMING) < 57) {
            return FarmVars.get().fruitTreeId = TREES.PINEAPPLE_SAPPLING.getId();

        } else if (Skills.getCurrentLevel(Skills.SKILLS.FARMING) < 68) {
            return FarmVars.get().fruitTreeId = TREES.PAPAYA_SAPPLING.getId();

        } else if (Skills.getCurrentLevel(Skills.SKILLS.FARMING) < 99) {
            return FarmVars.get().fruitTreeId = TREES.PALM_SAPPLING.getId();

        } else {
            return FarmVars.get().fruitTreeId = TREES.DRAGONFRUIT_SAPPLING.getId();

        }
    }

}