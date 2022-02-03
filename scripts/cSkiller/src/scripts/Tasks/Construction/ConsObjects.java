package scripts.Tasks.Construction;

import org.apache.commons.lang3.StringUtils;
import scripts.ItemID;

import java.util.Locale;

public enum ConsObjects {

    WOODEN_CHAIR(58, ItemID.PLANK, 2, 1, 4),
    WOODEN_BOOKCASE(115, ItemID.PLANK, 4, 4, 22),
    OAK_DINING_TABLE(240, ItemID.OAK_PLANK, 4, 22, 31),
    CARVED_OAK_TABLE(360, ItemID.OAK_PLANK, 6, 31, 33),
    OAK_LARDER(480, ItemID.OAK_PLANK, 8, 33, 99);

    private double xp;
    private int plankType;
    private int plankNumber;
    private boolean needsNails;
    private int startLevel;
    private int endLevel;

    ConsObjects(double xp, int plankType, int plankNumber, int startLevel, int endLevel) {
        this.xp = xp;
        this.plankType = plankType;
        this.plankNumber = plankNumber;
        this.startLevel = startLevel;
        this.endLevel = endLevel;

        if (plankType == ItemID.PLANK) {
            this.needsNails = true;
        } else
            this.needsNails = false;
    }

    public String getObjectString(){
        String stringWithUnderscores = StringUtils.capitalize(this.toString().toLowerCase(Locale.ROOT));
        return stringWithUnderscores.replace("_", " ");
    }
}
