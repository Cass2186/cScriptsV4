package scripts.Tasks.Herblore;

import scripts.ItemID;

public enum Potions {

    ATTACK_POTION(ItemID.EYE_OF_NEWT, ItemID.GUAM_POTION_UNF, 25, 26),
    STRENGTH_POTION(ItemID.LIMPWURT_ROOT, ItemID.TARROMIN_POTION_UNF, 37, 38),
    ENERGY_POTION(ItemID.HARRALANDER_POTION_UNF, ItemID.CHOCOLATE_DUST, 67.5, 45);


    int item1;
    int item2;
    double xpPerPot;
    int levelMax;

    Potions(int item1, int item2, int xpPerPot, int levelMax){
        this.item1 = item1;
        this.item2 = item2;
        this.xpPerPot = xpPerPot;
        this.levelMax = levelMax;
    }

    Potions(int item1, int item2, double xpPerPot, int levelMax){
        this.item1 = item1;
        this.item2 = item2;
        this.xpPerPot = xpPerPot;
        this.levelMax = levelMax;
    }

}
