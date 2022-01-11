package scripts.Tasks.Farming.FarmTasks;

import org.tribot.api2007.Skills;

import scripts.GEManager.GEItem;
import scripts.ItemId;
import scripts.QuestSteps.BuyItemsStep;
import scripts.Tasks.Farming.Data.Enums.HERBS;
import scripts.Tasks.Farming.Data.FarmConst;
import scripts.Tasks.Farming.Data.FarmVars;
import scripts.Tasks.Priority;
import scripts.Tasks.Task;
import scripts.Utils;
import scripts.Varbits;

import java.util.ArrayList;
import java.util.Arrays;

public class Restock implements Task {


    public void buyItems() {
        int herbNum = 5;
        if (Skills.getActualLevel(Skills.SKILLS.FARMING) > 65
                && Utils.getVarBitValue(Varbits.KOUREND_FAVOR_HOSIDIUS.value) > 600)
            herbNum = 6;

        ArrayList<GEItem> itemsToBuy = new ArrayList<GEItem>(
                Arrays.asList(
                        new GEItem(ItemId.SPADE, 1, 300),
                        new GEItem(FarmConst.SEED_DIBBER, 1, 300),
                        new GEItem(FarmConst.RAKE, 1, 300),
                        new GEItem(FarmConst.ULTRACOMPOST, 20, 30),
                        new GEItem(ItemId.CAMELOT_TELEPORT, 10, 50),
                        new GEItem(ItemId.FALADOR_TELEPORT, 10, 50),
                        new GEItem(ItemId.FENKENSTRAIN_TELE, 10, 50),
                        new GEItem(ItemId.SKILLS_NECKLACE[2], 2, 30),
                        new GEItem(ItemId.STAMINA_POTION[0], 5, 30)
                )
        );
        ;



        if (FarmVars.get().doingTrees) {
            itemsToBuy.addAll(Arrays.asList(
                    new GEItem(FarmVars.get().treeId, herbNum, 30),
                    new GEItem(ItemId.LUMBRIDGE_TELEPORT, 10, 30),
                    new GEItem(ItemId.VARROCK_TELEPORT, 10, 30),
                    new GEItem(FarmConst.ULTRACOMPOST, 25, 30)));

        } else if (FarmVars.get().doingAllotments) {
            itemsToBuy.add(new GEItem(FarmVars.get().currentAllotmentId, 90, 200));
        } else {
            itemsToBuy.add(new GEItem(FarmVars.get().currentHerbId, herbNum, 20));
            itemsToBuy.add(new GEItem(FarmConst.LIMPWURT_SEEDS, 50, 200));
        }
        BuyItemsStep buyStep = new BuyItemsStep(itemsToBuy);
        buyStep.buyItems();
    }


    @Override
    public void execute() {
        FarmVars.get().status = "Restocking";
        HERBS.determineHerbId();
        buyItems();
        FarmVars.get().shouldRestock = false;
        FarmVars.get().shouldBank = true;
    }

    @Override
    public Priority priority() {
        return Priority.MEDIUM;
    }

    @Override
    public boolean validate() {
        return FarmVars.get().shouldRestock;
    }
}
