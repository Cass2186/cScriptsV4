package scripts.Data.Enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
@AllArgsConstructor
public enum Bones {

    BIG_BONES(15),
    WYRM_BONES(50),
    DRAGON_BONES(72),
    LAVA_DRAGON_BONES(85);

    @Getter
    private int buryXp = -1;

    public double getGildedAltarXp(){
        return this.buryXp * 3.5;
    }
}
