package scripts.Data.Enums;

import lombok.Getter;
import org.tribot.api.General;
import org.tribot.api2007.Skills;
import scripts.Data.SkillTasks;
import scripts.ItemId;
import scripts.Requirements.ItemReq;
import scripts.Utils;

import java.util.ArrayList;
import java.util.List;

public enum FiremakingItems {

    LOGS(40, ItemId.LOG_IDS[0], 1, 15),
    OAk_LOGS(60, ItemId.LOG_IDS[1], 15, 30),
    WILLOW_LOGS(90, ItemId.LOG_IDS[2], 30, 45),
    MAPLE_LOGS(135, ItemId.LOG_IDS[3], 45, SkillTasks.FIREMAKING.getEndLevel());

    double xpPer;
    @Getter
    int itemId;
    int minLevel;
    int maxLevel;

    FiremakingItems(double xpPer, int itemId, int minLevel, int levelMax) {
        this.xpPer = xpPer;
        this.itemId = itemId;
        this.minLevel = minLevel;
        this.maxLevel = levelMax;
    }

    public int determineResourcesToNextItem() {
        if (Skills.getActualLevel(Skills.SKILLS.FIREMAKING) >= this.maxLevel)
            return 0;
        int xpTillMax = Skills.getXPToLevel(Skills.SKILLS.FIREMAKING, this.maxLevel);
        return (int) (xpTillMax / this.xpPer) + 5;
    }

  /*  public static int getRequiredLogs(int itemId) {
        int i = 0;
        for (FiremakingItems b : values()) {
            if (b.getItemId() == itemId) {
                i = i + b.determineResourcesToNextItem();
                return i;
            }
        }
        return i;
    }*/

    public static List<ItemReq> getRequiredLogList() {
        List<ItemReq> i = new ArrayList<>();
        for (FiremakingItems b : values()) {
            if (SkillTasks.FIREMAKING.getEndLevel() < b.maxLevel)
                break;
            i.add(new ItemReq(b.itemId, b.determineResourcesToNextItem()));
            General.println("[Debug]: We need " + i + Utils.getItemName(b.itemId) + "  to get to level " + b.maxLevel);
        }
        i.add(new ItemReq(ItemId.TINDERBOX, 1));
        return i;
    }




}
