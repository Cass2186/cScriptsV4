package scripts.Tasks.Construction;

import lombok.Getter;
import org.tribot.api.General;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Skills;
import org.tribot.script.sdk.Log;
import scripts.Data.Enums.HerbloreItems;
import scripts.Data.SkillTasks;
import scripts.ItemID;
import scripts.Requirements.ItemReq;
import scripts.Tasks.Construction.ConsData.Room;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public enum FURNITURE {
    CRUDE_CHAIR("Chair", Room.PARLOUR, 4517, 58, ItemID.PLANK, 2, "1", 1, 8),
    WOODEN_CHAIR("Chair", Room.PARLOUR, 4517, 87, ItemID.PLANK, 3, "2", 8, 14),
    BOOKCASE("Bookcase", Room.PARLOUR, 4521, 110, ItemID.PLANK, 4, "1", 14, 22),
    OAK_TABLE("Oak table", Room.DINING, 15298, 240, ItemID.OAK_PLANK, 4, "2", 22, 33),
    OAK_LARDER("Larder", Room.KITCHEN, 15403, 480, ItemID.OAK_PLANK, 8, "2", 33, 99);


    FURNITURE(String name, Room room, int spaceId, int xpPer, int plankId, int plankNum, String numString, int reqLevl, int maxLevel) {
        this.objectName = name;
        this.spaceId = spaceId;
        this.xpPer = xpPer;
        this.plankId = plankId;
        this.plankNum = plankNum;
        this.keyString = numString;
        this.reqLevl = reqLevl;
        this.maxLevel = maxLevel;
        this.room = room;
    }

    @Getter
    private int spaceId;
    @Getter
    private String objectName;
    @Getter
    private int plankId;
    @Getter
    private int plankNum;
    @Getter
    private String keyString;
    @Getter
    private int reqLevl;
    @Getter
    private int maxLevel;
    @Getter
    private int xpPer;

    @Getter
    private Room room;


    private static Skills.SKILLS skill = Skills.SKILLS.CONSTRUCTION;

    public int determineResourcesToNextItem() {
        if (Skills.getActualLevel(skill) >= this.maxLevel)
            return 0;
        int max = this.maxLevel;
        int xpTillMax = Skills.getXPToLevel(skill, SkillTasks.CONSTRUCTION.getEndLevel());
        if (max < SkillTasks.CONSTRUCTION.getEndLevel()) {
            xpTillMax = Skills.getXPToLevel(skill, this.maxLevel);
        }
        General.println("[Construction] DetermineResourcesToNextItem: " + ((xpTillMax / this.xpPer)*this.plankNum));
        return (int) ((xpTillMax / this.xpPer)*this.plankNum) + 10;
    }

    public static Optional<FURNITURE> getCurrentItem() {
        for (FURNITURE i : values()) {
            if (Skills.getActualLevel(skill) < i.maxLevel) {
                return Optional.of(i);
            }
        }
        return Optional.empty();
    }

    public static List<ItemReq> getRequiredItemList() {
        List<ItemReq> i = new ArrayList<>();
        int numOfRegPlanks = 0;
        int numOfOakPlanks = 0;
        for (FURNITURE furn : FURNITURE.values()) {

            // skip the furniture piece if we're past it's max level
            if (Skills.getActualLevel(skill) >= furn.maxLevel)//||
                  //  SkillTasks.CONSTRUCTION.getEndLevel() <= furn.maxLevel)
                continue;

            Log.log("[Debug]: Item is " + furn.toString());
            int plankNum = furn.determineResourcesToNextItem();
            // add planks
            if (furn.getMaxLevel() < 23) {
                numOfRegPlanks = numOfRegPlanks + plankNum;
                General.println("[Construction Items]: We need " + numOfRegPlanks + " regular planks");
            } else {
                numOfOakPlanks = numOfOakPlanks + plankNum;
                General.println("[Construction Items]: We need " + numOfRegPlanks + " Oak planks");
            }
        }
        //make req for planks items
        i.add(new ItemReq.Builder()
                .idEquals(ItemID.PLANK)
                .isItemNoted(true)
                .amount(numOfRegPlanks).build());

        /*i.add(new ItemReq.Builder()
                .idEquals(ItemID.PLANK)
                .isItemNoted(false)
                .amount(10).build());*/

        //make req for oak planks items
        i.add(new ItemReq.Builder()
                .idEquals(ItemID.OAK_PLANK)
                .isItemNoted(true)
                .amount(numOfOakPlanks).build());


        // ADD Hammer and saw
        i.add(new ItemReq(ItemID.STEEL_NAILS, numOfRegPlanks * 5));
        i.add(new ItemReq(ItemID.HAMMER, 1));
        i.add(new ItemReq(ItemID.SAW, 1));
        i.add(new ItemReq(ItemID.TELEPORT_TO_HOUSE, 1));
        General.println("[ConstructionItems]: We need " + i.size() + " sized list for construction items", Color.BLACK);
        return i;
    }
}