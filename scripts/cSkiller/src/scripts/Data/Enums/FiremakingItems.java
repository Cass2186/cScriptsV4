package scripts.Data.Enums;

import lombok.Getter;
import org.tribot.api.General;
import org.tribot.api2007.Skills;
import scripts.Data.SkillTasks;

import scripts.ItemID;
import scripts.Requirements.ItemReq;
import scripts.Utils;

import java.util.ArrayList;
import java.util.List;

public enum FiremakingItems {

    LOGS(40, scripts.ItemID.LOG_IDS[0], 1, 15),
    OAk_LOGS(60, scripts.ItemID.LOG_IDS[1], 15, 30),
    WILLOW_LOGS(90, scripts.ItemID.LOG_IDS[2], 30, 45),
    MAPLE_LOGS(135, scripts.ItemID.LOG_IDS[3], 45, SkillTasks.FIREMAKING.getEndLevel());

    double xpPer;
    @Getter
    int ItemID;
    int minLevel;
    int maxLevel;

    FiremakingItems(double xpPer, int ItemID, int minLevel, int levelMax) {
        this.xpPer = xpPer;
        this.ItemID = ItemID;
        this.minLevel = minLevel;
        this.maxLevel = levelMax;
    }

    public int determineResourcesToNextItem() {
        if (Skills.getActualLevel(Skills.SKILLS.FIREMAKING) >= this.maxLevel)
            return 0;
        int xpTillMax = Skills.getXPToLevel(Skills.SKILLS.FIREMAKING, this.maxLevel);
        return (int) (xpTillMax / this.xpPer) + 5;
    }

  /*  public static int getRequiredLogs(int ItemID) {
        int i = 0;
        for (FiremakingItems b : values()) {
            if (b.getItemID() == ItemID) {
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
            i.add(new ItemReq(b.ItemID, b.determineResourcesToNextItem()));
            General.println("[Debug]: We need " + i + Utils.getItemName(b.ItemID) + "  to get to level " + b.maxLevel);
        }
        i.add(new ItemReq(scripts.ItemID.TINDERBOX, 1));
        return i;
    }




}
