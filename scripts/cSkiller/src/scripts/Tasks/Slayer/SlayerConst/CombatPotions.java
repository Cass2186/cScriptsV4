package scripts.Tasks.Slayer.SlayerConst;

import lombok.AllArgsConstructor;
import lombok.Getter;
import scripts.ItemID;

@AllArgsConstructor
public enum CombatPotions {
    SUPER_COMBAT(ItemID.SUPER_COMBAT_POTION),
    SUPER_STRENGTH(ItemID.SUPER_STRENGTH),
    SUPER_ATTACK(ItemID.SUPER_ATTACK),
    SUPER_DEFENCE(ItemID.SUPER_DEFENCE),
    RANGING(ItemID.RANGING_POTION);

    @Getter
    private int[] ids;
}
