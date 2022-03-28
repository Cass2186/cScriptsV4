package scripts.Tasks.Slayer.SlayerConst;

import lombok.AllArgsConstructor;
import lombok.Getter;
import scripts.ItemID;

@AllArgsConstructor
public enum CombatPotions {
    SUPER_COMBAT(ItemID.SUPER_COMBAT_POTION),
    SUPER_STRENGTH(ItemID.SUPER_STRENGTH),
    SUPER_ATTACK(ItemID.SUPER_ATTACK);

    @Getter
    private int[] ids;
}
