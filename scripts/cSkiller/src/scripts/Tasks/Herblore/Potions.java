package scripts.Tasks.Herblore;

import scripts.ItemId;

public enum Potions {

    ATTACK_POTION(ItemId.EYE_OF_NEWT, ItemId.GUAM_UNF, 25, 26),
    STRENGTH_POTION(ItemId.LIMPWURT, ItemId.TARROMIN_UNF, 37, 38),
    ENERGY_POTION(ItemId.HARRALANDER_UNF, ItemId.CHOCOLATE_DUST, 67.5, 45);


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
