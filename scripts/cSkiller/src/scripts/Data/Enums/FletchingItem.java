package scripts.Data.Enums;

import lombok.Getter;
import org.tribot.api.General;
import org.tribot.api2007.Skills;
import scripts.Data.SkillTasks;
import scripts.Requirements.ItemReq;

import java.util.ArrayList;
import java.util.List;

public enum FletchingItem {

    ARROW_SHAFTS(15,  scripts.ItemID.ARROW_SHAFT,  1, 20),
    OAK_SHORTBOW(16.5,  scripts.ItemID.OAK_LOGS,  20, 25),
    OAK_LONGBOW(20,  scripts.ItemID.OAK_LOGS,  25, 35),
    WILLOW_SHORTBOW(33.3,  scripts.ItemID.WILLOW_LOGS,  35, 40),
    WILLOW_LONGBOW(41.5,  scripts.ItemID.WILLOW_LOGS,  40, 50),
    MAPLE_SHORTBOW(50,  scripts.ItemID.MAPLE_LOGS,  50, 55),
    MAPLE_LONGBOW(58.3,  scripts.ItemID.MAPLE_LOGS,  55, 65),
    YEW_SHORTBOW(67.5,  scripts.ItemID.YEW_LOGS,  65, 70),
    YEW_LONGBOW(75,  scripts.ItemID.YEW_LOGS,  70, 80),
    MAGIC_SHORTBOW(83.3, scripts.ItemID.MAGIC_LOGS,  80, 85),
    MAGIC_LONGBOW(91.5,  scripts.ItemID.MAGIC_LOGS,  85, SkillTasks.FLETCHING.getEndLevel());


    @Getter
    private double xpPer;

    @Getter
    private int ItemID;

    private int minLevel;
    private int maxLevel;

    FletchingItem(double xpPer, int ItemID, int minLevel, int levelMax) {
        this.xpPer = xpPer;
        this.ItemID = ItemID;

        this.minLevel = minLevel;
        this.maxLevel = levelMax;
    }


    private int determineResourcesToNextItem() {
        if (this.maxLevel <= Skills.getActualLevel(Skills.SKILLS.FLETCHING) ||
                this.minLevel > Skills.getActualLevel(Skills.SKILLS.FLETCHING))
            return 0;

        int max = this.maxLevel;

        int xpTillMax = Skills.getXPToLevel(Skills.SKILLS.FLETCHING, SkillTasks.FLETCHING.getEndLevel());

        if (max < SkillTasks.FLETCHING.getEndLevel()){
            xpTillMax = Skills.getXPToLevel(Skills.SKILLS.FLETCHING, this.maxLevel);
        }
        return  (int) (xpTillMax / this.xpPer) +10;
    }


    public static List<ItemReq> getRequiredItemList() {
        List<ItemReq> i = new ArrayList<>();
        if (Skills.getActualLevel(Skills.SKILLS.FLETCHING) <20 ){
            i.add(new ItemReq(scripts.ItemID.FEATHER,  ARROW_SHAFTS.determineResourcesToNextItem()*15));
            i.add(new ItemReq(scripts.ItemID.ARROW_SHAFT,  ARROW_SHAFTS.determineResourcesToNextItem()*15));
            return i;
        }
        for (FletchingItem b : values()) {
            i.add(new ItemReq(b.getItemID(), b.determineResourcesToNextItem()));
        }

        i.add(new ItemReq(scripts.ItemID.KNIFE, 1));
        General.println("[FletchItems]: getRequiredLogList() ListSize is " + i.size());
        return i;
    }


}
