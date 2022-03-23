package scripts.Gear;

import lombok.Getter;
import org.tribot.api2007.Skills;
import scripts.ItemID;
import scripts.Requirements.Requirement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public enum Weapon {

    IRON_SCIMITAR(ItemID.IRON_SCIMITAR, 1),
    STEEL_SCIMITAR(ItemID.STEEL_SCIMITAR, 5),
    MITHRIL_SCIMITAR(ItemID.MITHRIL_SCIMITAR, 20),
    ADAMANT_SCIMITAR(ItemID.ADAMANT_SCIMITAR, 30),
    RUNE_SCIMITAR(ItemID.RUNE_SCIMITAR, 40),
    DRAGON_SWORD(ItemID.DRAGON_SWORD, 60),
    DRAGON_SCIMITAR(ItemID.DRAGON_SCIMITAR, 60);




    Weapon(int itemId, int minAttackLevel) {
        this.weaponId = itemId;
        this.minAttackLevel = minAttackLevel;
    }

    @Getter
    private int weaponId;
    @Getter
    private int minAttackLevel;

    @Getter
    private List<Requirement> additionalRequirements = new ArrayList<>();

}
