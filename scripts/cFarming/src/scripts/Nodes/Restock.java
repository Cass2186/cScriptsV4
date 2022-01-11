package scripts.Nodes;

import org.tribot.api2007.Skills;
import scripts.Data.Const;
import scripts.Data.Vars;

import scripts.GEManager.Exchange;
import scripts.GEManager.GEItem;
import scripts.ItemId;
import scripts.QuestSteps.BuyItemsStep;
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
                        new GEItem(Const.SEED_DIBBER, 1, 300),
                        new GEItem(Const.RAKE, 1, 300),
                        new GEItem(Const.ULTRACOMPOST, 20, 30),
                        new GEItem(ItemId.CAMELOT_TELEPORT, 10, 50),
                        new GEItem(ItemId.FALADOR_TELEPORT, 10, 50),
                        new GEItem(ItemId.FENKENSTRAIN_TELE, 10, 50),
                        new GEItem(ItemId.SKILLS_NECKLACE[2], 2, 30),
                        new GEItem(ItemId.STAMINA_POTION[0], 5, 30)
                )
        );
        ;



        if (Vars.get().doingTrees) {
            itemsToBuy.addAll(Arrays.asList(
                    new GEItem(Vars.get().treeId, herbNum, 30),
                    new GEItem(ItemId.LUMBRIDGE_TELEPORT, 10, 30),
                    new GEItem(ItemId.VARROCK_TELEPORT, 10, 30),
                    new GEItem(Const.ULTRACOMPOST, 25, 30)));

        } else if (Vars.get().doingAllotments) {
            itemsToBuy.add(new GEItem(Vars.get().currentAllotmentId, 90, 200));
        } else {
            itemsToBuy.add(new GEItem(Vars.get().currentHerbId, herbNum, 20));
            itemsToBuy.add(new GEItem(Const.LIMPWURT_SEEDS, 50, 200));
        }
        BuyItemsStep buyStep = new BuyItemsStep(itemsToBuy);
        buyStep.buyItems();
    }


    @Override
    public void execute() {
        Vars.get().status = "Restocking";
        Plant.determineHerbId();
        buyItems();
        Vars.get().shouldRestock = false;
        Vars.get().shouldBank = true;
    }

    @Override
    public Priority priority() {
        return Priority.MEDIUM;
    }

    @Override
    public boolean validate() {
        return Vars.get().shouldRestock;
    }
}
