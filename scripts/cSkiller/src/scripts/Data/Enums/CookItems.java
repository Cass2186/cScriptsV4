package scripts.Data.Enums;

import lombok.Getter;
import org.tribot.api.General;
import org.tribot.api2007.Skills;
import org.tribot.script.sdk.Log;
import scripts.Data.SkillTasks;
import scripts.ItemID;
import scripts.Requirements.ItemReq;

import java.util.ArrayList;
import java.util.List;

public enum CookItems {

    ANCHOVIES(30, 321, 1, 15),
    TROUT(70, 335, 15, 30),
    SALMON(90, 331, 30, 35),
    WINE(200,  ItemID.GRAPES, 35, SkillTasks.COOKING.getEndLevel());

    private int xpPer;
    @Getter
    private int id;
    private int id2;
    private int minLevel;
    private int maxLevel;

    CookItems(int xp, int id, int startLevel, int endLevel) {
        this.xpPer = xp;
        this.id = id;
        this.minLevel = startLevel;
        this.maxLevel = endLevel;

        if (this.id == ItemID.GRAPES)
            this.id2 = 1937; //for wine (this ID is Jug of Water)
    }


    public int determineCookAmount() {
        if (this.maxLevel <= Skills.getActualLevel(Skills.SKILLS.COOKING) ||
                this.minLevel > Skills.getActualLevel(Skills.SKILLS.COOKING))
            return 0;

        int xpLeft = Skills.getXPToLevel(Skills.SKILLS.COOKING, this.maxLevel);
        Log.info("[CookItem]: We have " + xpLeft + "xp left to get to the end level of " + this.maxLevel);
        int successfulCooks = xpLeft / this.xpPer + 1;

        int withBurns = (int) (successfulCooks * 1.45) + 14;
        Log.info("[CookItem]: We need to buy " + withBurns + " raw food to get to the end level of " + this.maxLevel);
        return withBurns;
    }

    public static int getCookingRawFoodId(){
        int lvl = Skills.getActualLevel(Skills.SKILLS.COOKING);
        for (CookItems b : values()) {
            if (b.maxLevel > lvl && b.minLevel <= lvl){

               // Log.info();("[CookItems]: Raw food id is: " + b.getId());
                return b.getId();
            }
        }
        return -1;
    }

    public static List<ItemReq> getRequiredRawFood() {
        List<ItemReq> i = new ArrayList<>();
        for (CookItems b : values()) {
            if (b == CookItems.WINE) {
                i.add(new ItemReq(b.id, b.determineCookAmount()));
                i.add(new ItemReq(ItemID.JUG_OF_WATER, b.determineCookAmount()));
            } else {
                i.add(new ItemReq(b.getId(), b.determineCookAmount()));
            }

        }
        Log.info("[CookItems]: getRequiredRawFood ListSize is " + i.size());
        return i;
    }
}
