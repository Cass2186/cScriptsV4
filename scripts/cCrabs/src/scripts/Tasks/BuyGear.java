package scripts.Tasks;

import org.tribot.script.sdk.Equipment;
import org.tribot.script.sdk.Log;
import org.tribot.script.sdk.query.Query;
import org.tribot.script.sdk.types.definitions.ItemDefinition;
import scripts.BankManager;
import scripts.Data.Vars;
import scripts.GEManager.GEItem;
import scripts.Gear.ProgressiveMeleeGear;
import scripts.ItemID;
import scripts.QuestSteps.BuyItemsStep;
import scripts.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class BuyGear implements Task {

    ArrayList<GEItem> meleeItems = new ArrayList<>(Arrays.asList(
            new GEItem(ItemID.SUPER_ATTACK4, 40, 50),
            new GEItem(ItemID.SUPER_STRENGTH4, 30, 50),
            new GEItem(ItemID.SKILLS_NECKLACE4, 1, 30),
            new GEItem(ItemID.MONKFISH, 25, 30)
    ));

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

    private void populateBuyItems() {
        List<Integer> bestUsableGearList = ProgressiveMeleeGear.getBestUsableGearList();
        for (Integer i : bestUsableGearList) {
            meleeItems.add(new GEItem(i, 1, 35));
        }
        BuyItemsStep buyStep2 = new BuyItemsStep(meleeItems);
        buyStep2.buyItems();
    }

    private void bankForItems() {
        List<Integer> bestUsableGearList = ProgressiveMeleeGear.getBestUsableGearList();
        BankManager.open(true);
        BankManager.depositEquipment();
        BankManager.depositAll(true);
        for (Integer i : bestUsableGearList) {
            BankManager.withdraw(1, true, i);
            Utils.equipItem(i);
        }
        BankManager.withdraw(0, true, ItemID.COINS_995);
        BankManager.withdraw(10, true, ItemID.SUPER_ATTACK4);
        BankManager.withdraw(10, true, ItemID.SUPER_STRENGTH4);
        BankManager.withdrawArray(ItemID.SKILLS_NECKLACE, 1);
        BankManager.withdraw(0, true, ItemID.MONKFISH);
        BankManager.close(true);
    }

    private String getNameWithoutTeleports(String name) {
        if (name.contains("\\(")) {
            String[] split = name.split("\\(");
            Log.info("returning " + split[0]);
            return split[0];
        }
        return name;
    }

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
        if (Query.equipment().nameContains("bow", "dart")
                .isAny() && !Vars.get().progressiveMelee)
            return false;
        List<Integer> bestUsableGearList = ProgressiveMeleeGear.getBestUsableGearList();
        for (Integer i : bestUsableGearList) {
            Optional<ItemDefinition> name = Query.itemDefinitions().idEquals(i).findFirst();
            if (!name.map(n -> Query.equipment()
                    .nameContains(getNameWithoutTeleports(n.getName())).isAny()).orElse(false)) {
                Log.info("Missing " + name.get().getName());
                return true;
            }

        }
        Log.info("return false " );
        return false;
    }

    @Override
    public void execute() {
        populateBuyItems();
        bankForItems();
    }
}
