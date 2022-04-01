package scripts.Tasks.Farming.FarmTasks;

import org.tribot.api.General;
import org.tribot.api2007.Equipment;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Login;
import org.tribot.api2007.Skills;
import org.tribot.api2007.types.RSItem;
import scripts.BankManager;
import scripts.ItemID;
import scripts.Requirements.ItemReq;
import scripts.Tasks.Farming.Data.FarmConst;
import scripts.Tasks.Farming.Data.FarmVars;
import scripts.Tasks.Farming.Data.Methods;
import scripts.Tasks.Priority;
import scripts.Tasks.Task;
import scripts.Utils;
import scripts.Varbits;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Bank implements Task {

    HashMap<Integer, Integer> invMap = new HashMap<>();

    private boolean checkSecateurs() {
        if (Equipment.isEquipped(FarmConst.MAGIC_SECATEURS))
            return FarmVars.get().usingMagicSecateurs = true;

        else {
            BankManager.open(true);
            if (BankManager.getCount(FarmConst.MAGIC_SECATEURS) >= 1) {
                return FarmVars.get().usingMagicSecateurs = true;
            }
        }

        return FarmVars.get().usingMagicSecateurs = false;
    }

    public boolean checkBottomlessCompost() {
        RSItem[] invItem = Inventory.find(FarmConst.BOTTOMLESS_COMPOST);
        if (invItem.length > 0) {
            General.println("[Bank]: Bottomless compost detected in inventory");
            return FarmVars.get().usingBottomless = true;
        } else {
            BankManager.open(true);
            if (BankManager.getCount(FarmConst.BOTTOMLESS_COMPOST) >= 1) {
                General.println("[Bank]: Bottomless compost detected in bank");
                BankManager.withdraw(1, true, FarmConst.BOTTOMLESS_COMPOST);
                return FarmVars.get().usingBottomless = true;
            }
        }
        return FarmVars.get().usingBottomless = false;
    }

    List<ItemReq> itemReqList = new ArrayList<>(
            Arrays.asList(
                    new ItemReq.Builder()
                            .id(FarmConst.FENKENSTRAINS_TAB)
                            .amount(5)
                            .build(),
                    new ItemReq.Builder()
                            .id(FarmConst.MAGIC_SECATEURS)
                            .amount(1)
                            .shouldEquip(true)
                            .build(),
                    new ItemReq(FarmConst.SEED_DIBBER, 1),
                    new ItemReq(FarmConst.SPADE, 1),
                    new ItemReq(FarmConst.RAKE, 1),
                    new ItemReq(FarmConst.CAMELOT_TAB, 5),
                    new ItemReq(FarmConst.FALADOR_TAB, 5)
            ));

    private void inventorySetUp(int herbId) {
        int herbNum = 5;
        if (Skills.getActualLevel(Skills.SKILLS.FARMING) > 65
                && Utils.getVarBitValue(Varbits.KOUREND_FAVOR_HOSIDIUS.getId()) > 600) {
            herbNum = 6;
            General.println("[Bank]: We can use the farming guild, getting an extra seed");
        }
        BankManager.open(true);
        BankManager.depositAll(true);

        if (checkSecateurs()) {
            BankManager.withdraw(1, true, FarmConst.MAGIC_SECATEURS);
            Utils.equipItem(FarmConst.MAGIC_SECATEURS); // might need to check action
        }

        if (!checkBottomlessCompost()) {
            BankManager.withdraw(herbNum * 2, true, FarmConst.ULTRACOMPOST);
        }


        invMap.put(herbId, herbNum);
        invMap.put(FarmConst.FENKENSTRAINS_TAB, 5);
        invMap.put(FarmConst.CAMELOT_TAB, 5);
        invMap.put(FarmConst.FALADOR_TAB, 5);
        invMap.put(FarmConst.RAKE, 1);
        invMap.put(FarmConst.SEED_DIBBER, 1);
        invMap.put(FarmConst.SPADE, 1);
        invMap.put(FarmConst.LIMPWURT_SEEDS, 10);
        //  BankHandler.withdrawItems(invMap);

        BankManager.withdrawArray(ItemID.SKILLS_NECKLACE, 1);
        BankManager.withdrawArray(ItemID.STAMINA_POTION, 1);
        BankManager.close(true);

        FarmVars.get().startInvValue = Utils.getInventoryValue();

      /*  if (!ItemUtil.checkInvMap(invMap)) {
            General.println("[Bank]: Missing item in invMap, restocking");
            FarmVars.get().shouldRestock = true;
            return;
        }*/
        if (!BankManager.checkInventoryItems(herbId, FarmConst.FENKENSTRAINS_TAB, FarmConst.CAMELOT_TAB,
                FarmConst.FALADOR_TAB, FarmConst.RAKE, FarmConst.SEED_DIBBER, FarmConst.SPADE)) {
            FarmVars.get().shouldRestock = true;
        }
    }


    private void allotmentInventorySetUp(int herbId) {
        int herbNum = 5;
        if (Skills.getActualLevel(Skills.SKILLS.FARMING) > 45
                && Utils.getVarBitValue(Varbits.KOUREND_FAVOR_HOSIDIUS.getId()) > 600) {
            herbNum = 6;
            General.println("[Bank]: We can use the farming guild, getting an extra seed");
        }
        BankManager.open(true);
        BankManager.depositAll(true);
        if (checkSecateurs()) {
            BankManager.withdraw(1, true, FarmConst.MAGIC_SECATEURS);
            Utils.equipItem(FarmConst.MAGIC_SECATEURS); // might need to check action
        }
        if (!BankManager.withdraw(herbNum * 10, true, herbId)) {
            General.println("[Debug]: Missing Allotment seeds");
            FarmVars.get().shouldRestock = true;
            return;
        }
        if (!checkBottomlessCompost()) {
            BankManager.withdraw(10, true, FarmConst.ULTRACOMPOST);
        }
        BankManager.withdraw(5, true, FarmConst.FENKENSTRAINS_TAB);
        BankManager.withdraw(5, true, FarmConst.CAMELOT_TAB);
        BankManager.withdraw(5, true, FarmConst.FALADOR_TAB);
        BankManager.withdraw(1, true, FarmConst.RAKE);
        BankManager.withdraw(1, true, FarmConst.SEED_DIBBER);
        BankManager.withdraw(1, true, ItemID.SPADE);
        BankManager.withdraw(1, true, ItemID.SKILLS_NECKLACE);
        BankManager.withdraw(1, true, ItemID.STAMINA_POTION);
        BankManager.close(true);
        FarmVars.get().startInvValue = Utils.getInventoryValue();

        if (!Methods.hasItem(herbId, 5))
            FarmVars.get().shouldRestock = true;

        if (!BankManager.checkInventoryItems(herbId, FarmConst.FENKENSTRAINS_TAB, FarmConst.CAMELOT_TAB,
                FarmConst.FALADOR_TAB, FarmConst.RAKE, FarmConst.SEED_DIBBER, FarmConst.SPADE)) {
            FarmVars.get().shouldRestock = true;
        }
    }


    public void fruitTreeInvSetup(int treeId) {
        BankManager.open(true);
        BankManager.depositAll(true);
        if (checkSecateurs()) {
            BankManager.withdraw(1, true, FarmConst.MAGIC_SECATEURS);
            Utils.equipItem(FarmConst.MAGIC_SECATEURS); // might need to check action
        }
        int treeNum = 4;
        if (Skills.getActualLevel(Skills.SKILLS.FARMING) > 85
                && Utils.getVarBitValue(Varbits.KOUREND_FAVOR_HOSIDIUS.getId()) > 600) {
            treeNum = 4;
            General.println("[Bank]: We can use the farming guild, getting an extra fruit sappling");
            BankManager.withdrawArray(ItemID.SKILLS_NECKLACE, 1);
        }
        if (!BankManager.withdraw(treeNum, true, treeId)) {
            General.println("[Debug]: Missing Tree sapplings");
            FarmVars.get().shouldRestock = true;
            return;
        }
        if (!checkBottomlessCompost()) {
            BankManager.withdraw(5, true, FarmConst.ULTRACOMPOST);
        }
        BankManager.withdraw(3, true, FarmConst.CAMELOT_TAB);
        BankManager.withdraw(3, true, FarmConst.ARDOUNGE_TAB);
        BankManager.withdraw(3, true, FarmConst.VARROCK_TAB);
        BankManager.withdraw(1, true, FarmConst.RAKE);
        BankManager.withdraw(3000, true, 995);
        BankManager.withdraw(1, true, FarmConst.SPADE);
        BankManager.withdraw(1, true, FarmConst.STAMINA_POTION);
        BankManager.close(true);
        FarmVars.get().startInvValue = Utils.getInventoryValue();
    }

    public void treeInvSetup(int treeId) {
        BankManager.open(true);
        BankManager.depositAll(true);
        if (checkSecateurs()) {
            BankManager.withdraw(1, true, FarmConst.MAGIC_SECATEURS);
            Utils.equipItem(FarmConst.MAGIC_SECATEURS); // might need to check action
        }
        int treeNum = 5;
        if (Skills.getActualLevel(Skills.SKILLS.FARMING) > 65
                && Utils.getVarBitValue(Varbits.KOUREND_FAVOR_HOSIDIUS.getId()) > 600) {
            treeNum = 6;
            General.println("[Bank]: We can use the farming guild, getting an extra seed");
            BankManager.withdrawArray(ItemID.SKILLS_NECKLACE, 1);
        }
        if (!BankManager.withdraw(treeNum, true, treeId)) {
            General.println("[Debug]: Missing Tree sapplings");
            FarmVars.get().shouldRestock = true;
            return;
        }
        if (!checkBottomlessCompost()) {
            BankManager.withdraw(5, true, FarmConst.ULTRACOMPOST);
        }
        BankManager.withdraw(2, true, FarmConst.FALADOR_TAB);
        BankManager.withdraw(3, true, FarmConst.VARROCK_TAB);
        BankManager.withdraw(2, true, FarmConst.LUMBRIDGE_TAB);
        BankManager.withdraw(1, true, FarmConst.RAKE);
        BankManager.withdraw(2000, true, 995);
        BankManager.withdraw(1, true, FarmConst.SPADE);
        BankManager.withdraw(1, true, FarmConst.STAMINA_POTION);
        BankManager.close(true);
        FarmVars.get().startInvValue = Utils.getInventoryValue();
    }

    public void getTreeItems() {
        //Plant.determineTreeId();
        treeInvSetup(FarmVars.get().treeId);
        if (FarmVars.get().usingBottomless) {
            if (!BankManager.checkInventoryItems(FarmVars.get().treeId, FarmConst.VARROCK_TAB,
                    FarmConst.LUMBRIDGE_TAB, FarmConst.FALADOR_TAB,
                    FarmConst.BOTTOMLESS_COMPOST, FarmConst.RAKE,
                    FarmConst.SPADE)) {
                FarmVars.get().shouldRestock = true;
            }

        } else if (!BankManager.checkInventoryItems(FarmVars.get().treeId, FarmConst.VARROCK_TAB,
                FarmConst.LUMBRIDGE_TAB, FarmConst.FALADOR_TAB,
                FarmConst.ULTRACOMPOST, FarmConst.RAKE,
                FarmConst.SPADE)) {
            FarmVars.get().shouldRestock = true;

        } else
            FarmVars.get().shouldRestock = false;
    }

    @Override
    public void execute() {
        FarmVars.get().status = "Banking";
        if (FarmVars.get().doingTrees) {
            getTreeItems();
        } else if (FarmVars.get().doingFruitTrees) {
            fruitTreeInvSetup(FarmVars.get().fruitTreeId);
        }
        /**
         * ALLOTMENTS
         *
         */
        else if (FarmVars.get().doingAllotments) {
            allotmentInventorySetUp(FarmVars.get().currentAllotmentId);
            if (FarmVars.get().usingBottomless || Inventory.find(FarmConst.BOTTOMLESS_COMPOST).length > 0) {
                if (!BankManager.checkInventoryItems(FarmVars.get().currentAllotmentId,
                        FarmConst.FENKENSTRAINS_TAB, FarmConst.CAMELOT_TAB,
                        FarmConst.FALADOR_TAB, FarmConst.RAKE,
                        FarmConst.BOTTOMLESS_COMPOST, FarmConst.SPADE)) {
                    FarmVars.get().shouldRestock = true;
                }

            } else if (!BankManager.checkInventoryItems(FarmVars.get().currentAllotmentId,
                    FarmConst.FENKENSTRAINS_TAB, FarmConst.CAMELOT_TAB,
                    FarmConst.ULTRACOMPOST, FarmConst.RAKE,
                    FarmConst.SPADE, FarmConst.SEED_DIBBER)) {
                FarmVars.get().shouldRestock = true;

            } else
                FarmVars.get().shouldRestock = false;
            /**
             * HERBS
             */
        } else {
            // Plant.determineHerbId();
            inventorySetUp(FarmVars.get().currentHerbId);

            if (!Methods.hasItem(FarmVars.get().currentHerbId, 5))
                FarmVars.get().shouldRestock = true;

            if (FarmVars.get().usingBottomless) {
                if (!BankManager.checkInventoryItems(FarmVars.get().currentHerbId, FarmConst.FENKENSTRAINS_TAB, FarmConst.CAMELOT_TAB,
                        FarmConst.FALADOR_TAB, FarmConst.RAKE, FarmConst.BOTTOMLESS_COMPOST,
                        FarmConst.SEED_DIBBER, FarmConst.SPADE)) {
                    FarmVars.get().shouldRestock = true;
                }

            } else if (!BankManager.checkInventoryItems(FarmVars.get().currentHerbId,
                    FarmConst.FENKENSTRAINS_TAB, FarmConst.CAMELOT_TAB,
                    FarmConst.FALADOR_TAB, FarmConst.ULTRACOMPOST, FarmConst.RAKE,
                    FarmConst.SEED_DIBBER, FarmConst.SPADE)) {
                FarmVars.get().shouldRestock = true;

            } else
                FarmVars.get().shouldRestock = false;

        }
        if (FarmVars.get().shouldRestock)
            return;

        FarmVars.get().shouldBank = false;
    }

    @Override
    public Priority priority() {
        return Priority.LOW;
    }

    @Override
    public boolean validate() {
        if (Login.getLoginState() == Login.STATE.INGAME) {
            //   Plant.determineHerbId();
            if (FarmVars.get().doingFruitTrees) {
                if (FarmVars.get().usingBottomless || Inventory.find(FarmConst.BOTTOMLESS_COMPOST).length > 0) {
                    if (!BankManager.checkInventoryItems(FarmVars.get().fruitTreeId, FarmConst.VARROCK_TAB,
                            FarmConst.CAMELOT_TAB, FarmConst.ARDOUNGE_TAB,
                            FarmConst.BOTTOMLESS_COMPOST, FarmConst.RAKE,
                            FarmConst.SPADE)) {
                        General.println("[Bank] Line 174", Color.RED);
                        return FarmVars.get().shouldBank = true;
                    }
                }
                return FarmVars.get().shouldBank = false;
            } else if (FarmVars.get().doingTrees) {
                // Plant.determineTreeId();
                if (FarmVars.get().usingBottomless || Inventory.find(FarmConst.BOTTOMLESS_COMPOST).length > 0) {
                    if (!BankManager.checkInventoryItems(FarmVars.get().treeId, FarmConst.VARROCK_TAB,
                            FarmConst.LUMBRIDGE_TAB, FarmConst.FALADOR_TAB,
                            FarmConst.BOTTOMLESS_COMPOST, FarmConst.RAKE,
                            FarmConst.SPADE)) {
                        General.println("[Bank] Line 174", Color.RED);
                        return FarmVars.get().shouldBank = true;
                    }

                } else if (!BankManager.checkInventoryItems(FarmVars.get().treeId, FarmConst.VARROCK_TAB,
                        FarmConst.LUMBRIDGE_TAB, FarmConst.FALADOR_TAB,
                        FarmConst.ULTRACOMPOST, FarmConst.RAKE,
                        FarmConst.SPADE)) {
                    General.println("[Bank] Line 182", Color.RED);
                    return FarmVars.get().shouldBank = true;

                } else
                    return FarmVars.get().shouldBank = false;

                /**
                 * ALLOTMENTS
                 */
            } else if (FarmVars.get().doingAllotments) {
                //  Plant.determineAllotmentId();
                if (FarmVars.get().usingBottomless || Inventory.find(FarmConst.BOTTOMLESS_COMPOST).length > 0) {
                    if (!BankManager.checkInventoryItems(FarmVars.get().currentAllotmentId,
                            FarmConst.FENKENSTRAINS_TAB, FarmConst.CAMELOT_TAB,
                            FarmConst.FALADOR_TAB, FarmConst.RAKE, FarmConst.SEED_DIBBER,
                            FarmConst.BOTTOMLESS_COMPOST, FarmConst.SPADE)) {

                        General.println("[Bank]: We need to bank line 279:" + FarmVars.get().currentAllotmentId);
                        return FarmVars.get().shouldBank = true;
                    }
                } else if (!BankManager.checkInventoryItems
                        (FarmVars.get().currentAllotmentId, FarmConst.SEED_DIBBER,
                                FarmConst.FENKENSTRAINS_TAB, FarmConst.CAMELOT_TAB,
                                FarmConst.FALADOR_TAB, FarmConst.ULTRACOMPOST,
                                FarmConst.RAKE, FarmConst.SPADE)) {
                    General.println("[Bank]: We need to bank line 287:" + FarmVars.get().currentAllotmentId);
                    return FarmVars.get().shouldBank = true;

                } else
                    return FarmVars.get().shouldBank = false;

                /**
                 * HERBS
                 */
            } else if ((FarmVars.get().usingBottomless || Inventory.find(FarmConst.BOTTOMLESS_COMPOST).length > 0)
                    && !FarmVars.get().doingTrees && !FarmVars.get().doingAllotments && FarmVars.get().herbTimer == null) {
                if (!BankManager.checkInventoryItems(FarmVars.get().currentHerbId,
                        FarmConst.FENKENSTRAINS_TAB, FarmConst.CAMELOT_TAB,
                        FarmConst.FALADOR_TAB, FarmConst.RAKE,
                        FarmConst.BOTTOMLESS_COMPOST,
                        FarmConst.SEED_DIBBER, FarmConst.SPADE)) {
                    General.println("[Bank]: We need to bank line 192:" + FarmVars.get().currentHerbId);
                    return FarmVars.get().shouldBank = true;
                }
            } else if (!FarmVars.get().doingTrees && !FarmVars.get().doingAllotments && FarmVars.get().herbTimer == null) {
                if (!BankManager.checkInventoryItems(FarmVars.get().currentHerbId, FarmConst.FENKENSTRAINS_TAB,
                        FarmConst.CAMELOT_TAB, FarmConst.ULTRACOMPOST,
                        FarmConst.FALADOR_TAB, FarmConst.RAKE,
                        FarmConst.SEED_DIBBER, FarmConst.SPADE)) {
                    return FarmVars.get().shouldBank = true;
                }
                /**
                 * ALLOTMENTS
                 */
            } else
                return FarmVars.get().shouldBank = false;
        }
        return FarmVars.get().shouldBank = false;

    }
}
