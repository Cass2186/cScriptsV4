package scripts.Tasks;

import scripts.GEManager.GEItem;
import scripts.ItemID;
import scripts.QuestSteps.BuyItemsStep;

import java.util.ArrayList;
import java.util.Arrays;

public class BuyGear implements Task{


    ArrayList<GEItem> rangedItemToBuy = new ArrayList<GEItem>(
            Arrays.asList(
                    new GEItem(ItemID.SNAKESKIN_BANDANA, 1, 500),
                    new GEItem(ItemID.SNAKESKIN_BOOTS, 1, 500),
                    new GEItem(ItemID.SNAKESKIN_BODY, 1, 500),
                    new GEItem(ItemID.SNAKESKIN_CHAPS, 1, 500),
                    new GEItem(ItemID.SNAKESKIN_SHIELD, 1, 500),

                    new GEItem(ItemID.BLUE_DHIDE_BODY, 1, 50),
                    new GEItem(ItemID.BLUE_DHIDE_CHAPS, 1, 50),
                    new GEItem(ItemID.MAGIC_SHORTBOW, 1, 50),
                    new GEItem(ItemID.BLUE_DHIDE_VAMBRACES, 1, 50),

                    new GEItem(ItemID.SUPER_COMBAT_POTION[0], 2, 15),
                    new GEItem(ItemID.STAMINA_POTION[0], 3, 15),
                    new GEItem(ItemID.VARROCK_TELEPORT, 5, 40),
                    new GEItem(ItemID.RING_OF_WEALTH[0], 1, 25),
                    new GEItem(ItemID.DRAGON_SCIMITAR, 1, 25)
            )
    );

    BuyItemsStep buyStep = new BuyItemsStep(rangedItemToBuy);


    @Override
    public String toString() {
        return "Buying gear";
    }


    @Override
    public Priority priority() {
        return Priority.MEDIUM;
    }

    @Override
    public boolean validate() {
        return false;
    }

    @Override
    public void execute() {

    }
}
