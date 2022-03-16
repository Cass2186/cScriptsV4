package scripts.Tasks.BirdHouseRuns.Data;

import lombok.Getter;
import org.tribot.script.sdk.Skill;
import scripts.ItemID;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Optional;


public enum Birdhouse {

    BIRDHOUSE(5, ItemID.LOGS, ItemID.BIRD_HOUSE),
    OAK_BIRDHOUSE(14, ItemID.OAK_LOGS, ItemID.OAK_BIRD_HOUSE),
    WILLOW_BIRDHOUSE(24, ItemID.WILLOW_LOGS, ItemID.WILLOW_BIRD_HOUSE),
    TEAK_BIRDHOUSE(34, ItemID.TEAK_LOGS, ItemID.TEAK_BIRD_HOUSE),
    MAPLE_BIRDHOUSE(44, ItemID.MAPLE_LOGS, ItemID.MAPLE_BIRD_HOUSE),
    MAHOGANY_BIRDHOUSE(49, ItemID.MAHOGANY_LOGS, ItemID.MAHOGANY_BIRD_HOUSE),
    YEW_BIRDHOUSE(59, ItemID.YEW_LOGS, ItemID.YEW_BIRD_HOUSE),
    MAGIC_BIRDHOUSE(74, ItemID.MAGIC_LOGS, ItemID.MAGIC_BIRD_HOUSE),
    REDWOOD_BIRDHOUSE(89, ItemID.REDWOOD_LOGS, ItemID.REDWOOD_BIRD_HOUSE);

    @Getter
    private int hunterLevel;
    @Getter
    private int craftingLevel;

    @Getter
    private int logID;

    @Getter
    private int birdhouseID;

    Birdhouse(int hunterLevel, int logID, int birdhouseID) {
        this.hunterLevel = hunterLevel;
        this.craftingLevel = hunterLevel + 1;
        this.logID = logID;
        this.birdhouseID = birdhouseID;
    }


    public boolean canCraft() {
        return Skill.CRAFTING.getActualLevel() >= this.craftingLevel;
    }

    public static Optional<Birdhouse> getBestBirdhouse() {
        return Arrays.stream(Birdhouse.values())
                .filter(b -> Skill.HUNTER.getActualLevel() >= b.getHunterLevel())
                .max(Comparator.comparingInt(Birdhouse::getHunterLevel));

    }

    public  static Optional<Birdhouse> getBestCraftableBirdhouse() {
        return Arrays.stream(Birdhouse.values())
                .filter(b -> Skill.HUNTER.getActualLevel() >= b.getHunterLevel())
                .filter(Birdhouse::canCraft)
                .max(Comparator.comparingInt(Birdhouse::getHunterLevel));

    }



}

