package scripts.Tasks;

import scripts.GEManager.GEItem;
import scripts.ItemId;
import scripts.QuestSteps.BuyItemsStep;

import java.util.ArrayList;
import java.util.Arrays;

public class BuyGear implements Task{


    ArrayList<GEItem> itemsToBuy = new ArrayList<GEItem>(
            Arrays.asList(
                    new GEItem(ItemId.SNAKESKIN_BANDANA, 1, 500),
                    new GEItem(ItemId.SNAKESKIN_BOOTS, 1, 500),
                    new GEItem(ItemId.SNAKESKIN_BODY, 1, 500),
                    new GEItem(ItemId.SNAKESKIN_CHAPS, 1, 500),
                    new GEItem(ItemId.SNAKESKIN_SHIELD, 1, 500),

                    new GEItem(ItemId.BLUE_DHIDE_BODY, 1, 50),
                    new GEItem(ItemId.BLUE_DHIDE_CHAPS, 1, 50),
                    new GEItem(ItemId.MAGIC_SHORTBOW, 1, 50),
                    new GEItem(ItemId.BLUE_DHIDE_VAMBRACES, 1, 50),

                    new GEItem(ItemId.SUPER_COMBAT_POTION[0], 2, 15),
                    new GEItem(ItemId.STAMINA_POTION[0], 3, 15),
                    new GEItem(ItemId.VARROCK_TELEPORT, 5, 40),
                    new GEItem(ItemId.RING_OF_WEALTH[0], 1, 25),
                    new GEItem(ItemId.DRAGON_SCIMITAR, 1, 25)
            )
    );

    BuyItemsStep buyStep = new BuyItemsStep(itemsToBuy);


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
