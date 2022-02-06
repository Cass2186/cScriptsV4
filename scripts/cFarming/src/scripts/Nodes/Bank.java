package scripts.Nodes;

import org.tribot.api.General;
import org.tribot.api2007.Equipment;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Login;
import org.tribot.api2007.Skills;
import org.tribot.api2007.types.RSItem;
import org.tribot.script.sdk.Log;
import scripts.BankManager;
import scripts.Data.Const;
import scripts.Data.Methods;
import scripts.Data.Vars;

import scripts.Tasks.Priority;
import scripts.Tasks.Task;
import scripts.Utils;
import scripts.Varbits;

import java.awt.*;
import java.util.HashMap;

public class Bank implements Task {

    HashMap<Integer, Integer> invMap = new HashMap<>();

    private boolean checkSecateurs() {
        if (Equipment.isEquipped(Const.MAGIC_SECATEURS))
            return Vars.get().usingMagicSecateurs = true;

        else {
            BankManager.open(true);
            if (BankManager.getCount(Const.MAGIC_SECATEURS) >= 1) {
                return Vars.get().usingMagicSecateurs = true;
            }
        }

        return Vars.get().usingMagicSecateurs = false;
    }

    public boolean checkBottomlessCompost() {
        RSItem[] invItem = Inventory.find(Const.BOTTOMLESS_COMPOST);
        if (invItem.length > 0) {
            General.println("[Bank]: Bottomless compost detected in inventory");
            return Vars.get().usingBottomless = true;
        } else {
            BankManager.open(true);
            if (BankManager.getCount(Const.BOTTOMLESS_COMPOST) >= 1) {
                General.println("[Bank]: Bottomless compost detected in bank");
                BankManager.withdraw(1, true, Const.BOTTOMLESS_COMPOST);
                return Vars.get().usingBottomless = true;
            }
        }
        return Vars.get().usingBottomless = false;
    }

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
            BankManager.withdraw(1, true, Const.MAGIC_SECATEURS);
            Utils.equipItem(Const.MAGIC_SECATEURS); // might need to check action
        }

        if (!checkBottomlessCompost()) {
            BankManager.withdraw(herbNum * 2, true, Const.ULTRACOMPOST);
        }

      //  BankHandler.withdrawItems(invMap);
        BankManager.withdraw(herbNum, true, herbId);
        BankManager.withdraw(5, true, Const.FENKENSTRAINS_TAB);
        BankManager.withdraw(5, true, Const.CAMELOT_TAB);
        BankManager.withdraw(5, true, Const.FALADOR_TAB);
        BankManager.withdraw(1, true, Const.RAKE);
        BankManager.withdraw(1, true, Const.SEED_DIBBER);
        BankManager.withdraw(1, true, Const.SPADE);
        BankManager.withdraw(20, true, Const.LIMPWURT_SEEDS);
        BankManager.withdrawArray(Const.SKILLS_NECKLACE, 1);
        BankManager.withdrawArray(Const.STAMINA_POTION, 1);
        BankManager.close(true);

        Vars.get().startInvValue = Utils.getInventoryValue();

      /*  if (!ItemUtil.checkInvMap(invMap)) {
            General.println("[Bank]: Missing item in invMap, restocking");
            Vars.get().shouldRestock = true;
            return;
        }*/
        if (!BankManager.checkInventoryItems(herbId, Const.FENKENSTRAINS_TAB, Const.CAMELOT_TAB,
                Const.FALADOR_TAB, Const.RAKE, Const.SEED_DIBBER, Const.SPADE)) {
            Vars.get().shouldRestock = true;
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
            BankManager.withdraw(1, true, Const.MAGIC_SECATEURS);
            Utils.equipItem(Const.MAGIC_SECATEURS); // might need to check action
        }
        if (!BankManager.withdraw(herbNum * 10, true, herbId)) {
            General.println("[Debug]: Missing Allotment seeds");
            Vars.get().shouldRestock = true;
            return;
        }
        if (!checkBottomlessCompost()) {
            BankManager.withdraw(10, true, Const.ULTRACOMPOST);
        }
        BankManager.withdraw(5, true, Const.FENKENSTRAINS_TAB);
        BankManager.withdraw(5, true, Const.CAMELOT_TAB);
        BankManager.withdraw(5, true, Const.FALADOR_TAB);
        BankManager.withdraw(1, true, Const.RAKE);
        BankManager.withdraw(1, true, Const.SEED_DIBBER);
        BankManager.withdraw(1, true, Const.SPADE);
        BankManager.withdraw(1, true, Const.SKILLS_NECKLACE);
        BankManager.withdraw(1, true, Const.STAMINA_POTION);
        BankManager.close(true);
        Vars.get().startInvValue = Utils.getInventoryValue();

        if (!Methods.hasItem(herbId, 5))
            Vars.get().shouldRestock = true;

        if (!BankManager.checkInventoryItems(herbId, Const.FENKENSTRAINS_TAB, Const.CAMELOT_TAB,
                Const.FALADOR_TAB, Const.RAKE, Const.SEED_DIBBER, Const.SPADE)) {
            Vars.get().shouldRestock = true;
        }
    }


    public void fruitTreeInvSetup(int treeId) {
        BankManager.open(true);
        BankManager.depositAll(true);
        if (checkSecateurs()) {
            BankManager.withdraw(1, true, Const.MAGIC_SECATEURS);
            Utils.equipItem(Const.MAGIC_SECATEURS); // might need to check action
        }
        int treeNum = 4;
        if (Skills.getActualLevel(Skills.SKILLS.FARMING) > 85
                && Utils.getVarBitValue(Varbits.KOUREND_FAVOR_HOSIDIUS.getId()) > 600) {
            treeNum = 4;
            General.println("[Bank]: We can use the farming guild, getting an extra fruit sappling");
            BankManager.withdrawArray(Const.SKILLS_NECKLACE, 1);
        }
        if (!BankManager.withdraw(treeNum, true, treeId)) {
            General.println("[Debug]: Missing Tree sapplings");
            Vars.get().shouldRestock = true;
            return;
        }
        if (!checkBottomlessCompost()) {
            BankManager.withdraw(5, true, Const.ULTRACOMPOST);
        }
        BankManager.withdraw(3, true, Const.CAMELOT_TAB);
        BankManager.withdraw(3, true, Const.ARDOUNGE_TAB);
        BankManager.withdraw(3, true, Const.VARROCK_TAB);
        BankManager.withdraw(1, true, Const.RAKE);
        BankManager.withdraw(3000, true, 995);
        BankManager.withdraw(1, true, Const.SPADE);
        BankManager.withdraw(1, true, Const.STAMINA_POTION);
        BankManager.close(true);
        Vars.get().startInvValue = Utils.getInventoryValue();
    }

    public void treeInvSetup(int treeId) {
        BankManager.open(true);
        BankManager.depositAll(true);
        if (checkSecateurs()) {
            BankManager.withdraw(1, true, Const.MAGIC_SECATEURS);
            Utils.equipItem(Const.MAGIC_SECATEURS); // might need to check action
        }
        int treeNum = 5;
        if (Skills.getActualLevel(Skills.SKILLS.FARMING) > 65
                && Utils.getVarBitValue(Varbits.KOUREND_FAVOR_HOSIDIUS.getId()) > 600) {
            treeNum = 6;
            General.println("[Bank]: We can use the farming guild, getting an extra seed");
            BankManager.withdrawArray(Const.SKILLS_NECKLACE, 1);
        }
        if (!BankManager.withdraw(treeNum, true, treeId)) {
            General.println("[Debug]: Missing Tree sapplings");
            Vars.get().shouldRestock = true;
            return;
        }
        if (!checkBottomlessCompost()) {
            BankManager.withdraw(5, true, Const.ULTRACOMPOST);
        }
        BankManager.withdraw(2, true, Const.FALADOR_TAB);
        BankManager.withdraw(3, true, Const.VARROCK_TAB);
        BankManager.withdraw(2, true, Const.LUMBRIDGE_TAB);
        BankManager.withdraw(1, true, Const.RAKE);
        BankManager.withdraw(2000, true, 995);
        BankManager.withdraw(1, true, Const.SPADE);
        BankManager.withdraw(1, true, Const.STAMINA_POTION);
        BankManager.close(true);
        Vars.get().startInvValue = Utils.getInventoryValue();
    }

    public void getTreeItems() {
        //Plant.determineTreeId();
        treeInvSetup(Vars.get().treeId);
        if (Vars.get().usingBottomless) {
            if (!BankManager.checkInventoryItems(Vars.get().treeId, Const.VARROCK_TAB,
                    Const.LUMBRIDGE_TAB, Const.FALADOR_TAB,
                    Const.BOTTOMLESS_COMPOST, Const.RAKE,
                    Const.SPADE)) {
                Vars.get().shouldRestock = true;
            }

        } else if (!BankManager.checkInventoryItems(Vars.get().treeId, Const.VARROCK_TAB,
                Const.LUMBRIDGE_TAB, Const.FALADOR_TAB,
                Const.ULTRACOMPOST, Const.RAKE,
                Const.SPADE)) {
            Vars.get().shouldRestock = true;

        } else
            Vars.get().shouldRestock = false;
    }

    @Override
    public void execute() {
        Vars.get().status = "Banking";
        if (Vars.get().doingTrees) {
            getTreeItems();
        } else if (Vars.get().doingFruitTrees){
            fruitTreeInvSetup(Vars.get().fruitTreeId);
        }
        /**
         * ALLOTMENTS
         *
         */
        else if (Vars.get().doingAllotments) {
            allotmentInventorySetUp(Vars.get().currentAllotmentId);
            if (Vars.get().usingBottomless || Inventory.find(Const.BOTTOMLESS_COMPOST).length > 0) {
                if (!BankManager.checkInventoryItems(Vars.get().currentAllotmentId,
                        Const.FENKENSTRAINS_TAB, Const.CAMELOT_TAB,
                        Const.FALADOR_TAB, Const.RAKE,
                        Const.BOTTOMLESS_COMPOST, Const.SPADE)) {
                    Vars.get().shouldRestock = true;
                }

            } else if (!BankManager.checkInventoryItems(Vars.get().currentAllotmentId,
                    Const.FENKENSTRAINS_TAB, Const.CAMELOT_TAB,
                    Const.ULTRACOMPOST, Const.RAKE,
                    Const.SPADE, Const.SEED_DIBBER)) {
                Vars.get().shouldRestock = true;

            } else
                Vars.get().shouldRestock = false;
            /**
             * HERBS
             */
        } else {
             Plant.determineHerbId();
            Log.log("[HERB BANK]");
            inventorySetUp(Vars.get().currentHerbId);

            if (!Methods.hasItem(Vars.get().currentHerbId, 5))
                Vars.get().shouldRestock = true;

            if (Vars.get().usingBottomless) {
                if (!BankManager.checkInventoryItems(Vars.get().currentHerbId, Const.FENKENSTRAINS_TAB, Const.CAMELOT_TAB,
                        Const.FALADOR_TAB, Const.RAKE, Const.BOTTOMLESS_COMPOST,
                        Const.SEED_DIBBER, Const.SPADE)) {
                    Vars.get().shouldRestock = true;
                }

            } else if (!BankManager.checkInventoryItems(Vars.get().currentHerbId,
                    Const.FENKENSTRAINS_TAB, Const.CAMELOT_TAB,
                    Const.FALADOR_TAB, Const.ULTRACOMPOST, Const.RAKE,
                    Const.SEED_DIBBER, Const.SPADE)) {
                Vars.get().shouldRestock = true;

            } else
                Vars.get().shouldRestock = false;

        }
        if (Vars.get().shouldRestock)
            return;

        Vars.get().shouldBank = false;
    }

    @Override
    public Priority priority() {
        return Priority.LOW;
    }

    @Override
    public boolean validate() {
        if (Login.getLoginState() == Login.STATE.INGAME) {
            //   Plant.determineHerbId();
            if (Vars.get().doingFruitTrees){
                if (Vars.get().usingBottomless || Inventory.find(Const.BOTTOMLESS_COMPOST).length > 0) {
                    if (!BankManager.checkInventoryItems(Vars.get().fruitTreeId, Const.VARROCK_TAB,
                            Const.CAMELOT_TAB, Const.ARDOUNGE_TAB,
                            Const.BOTTOMLESS_COMPOST, Const.RAKE,
                            Const.SPADE)) {
                        General.println("[Bank] Line 174", Color.RED);
                        return Vars.get().shouldBank = true;
                    }
                }
                return Vars.get().shouldBank = false;
            }
            else if (Vars.get().doingTrees) {
               // Plant.determineTreeId();
                if (Vars.get().usingBottomless || Inventory.find(Const.BOTTOMLESS_COMPOST).length > 0) {
                    if (!BankManager.checkInventoryItems(Vars.get().treeId, Const.VARROCK_TAB,
                            Const.LUMBRIDGE_TAB, Const.FALADOR_TAB,
                            Const.BOTTOMLESS_COMPOST, Const.RAKE,
                            Const.SPADE)) {
                        General.println("[Bank] Line 174", Color.RED);
                        return Vars.get().shouldBank = true;
                    }

                } else if (!BankManager.checkInventoryItems(Vars.get().treeId, Const.VARROCK_TAB,
                        Const.LUMBRIDGE_TAB, Const.FALADOR_TAB,
                        Const.ULTRACOMPOST, Const.RAKE,
                        Const.SPADE)) {
                    General.println("[Bank] Line 182", Color.RED);
                    return Vars.get().shouldBank = true;

                } else
                    return Vars.get().shouldBank = false;

                /**
                 * ALLOTMENTS
                 */
            } else if (Vars.get().doingAllotments) {
                //  Plant.determineAllotmentId();
                if (Vars.get().usingBottomless || Inventory.find(Const.BOTTOMLESS_COMPOST).length > 0) {
                    if (!BankManager.checkInventoryItems(Vars.get().currentAllotmentId,
                            Const.FENKENSTRAINS_TAB, Const.CAMELOT_TAB,
                            Const.FALADOR_TAB, Const.RAKE, Const.SEED_DIBBER,
                            Const.BOTTOMLESS_COMPOST, Const.SPADE)) {

                        General.println("[Bank]: We need to bank line 279:" + Vars.get().currentAllotmentId);
                        return Vars.get().shouldBank = true;
                    }
                } else if (!BankManager.checkInventoryItems
                        (Vars.get().currentAllotmentId, Const.SEED_DIBBER,
                                Const.FENKENSTRAINS_TAB, Const.CAMELOT_TAB,
                                Const.FALADOR_TAB, Const.ULTRACOMPOST,
                                Const.RAKE, Const.SPADE)) {
                    General.println("[Bank]: We need to bank line 287:" + Vars.get().currentAllotmentId);
                    return Vars.get().shouldBank = true;

                } else
                    return Vars.get().shouldBank = false;

                /**
                 * HERBS
                 */
            } else if ((Vars.get().usingBottomless || Inventory.find(Const.BOTTOMLESS_COMPOST).length > 0)
                    && !Vars.get().doingTrees && !Vars.get().doingAllotments && Vars.get().herbTimer == null) {
                if (!BankManager.checkInventoryItems(Vars.get().currentHerbId,
                        Const.FENKENSTRAINS_TAB, Const.CAMELOT_TAB,
                        Const.FALADOR_TAB, Const.RAKE,
                        Const.BOTTOMLESS_COMPOST,
                        Const.SEED_DIBBER, Const.SPADE)) {
                    General.println("[Bank]: We need to bank line 192:" + Vars.get().currentHerbId);
                    return Vars.get().shouldBank = true;
                }
            } else if (!Vars.get().doingTrees && !Vars.get().doingAllotments && Vars.get().herbTimer == null) {
                if (!BankManager.checkInventoryItems(Vars.get().currentHerbId, Const.FENKENSTRAINS_TAB,
                        Const.CAMELOT_TAB, Const.ULTRACOMPOST,
                        Const.FALADOR_TAB, Const.RAKE,
                        Const.SEED_DIBBER, Const.SPADE)) {
                    return Vars.get().shouldBank = true;
                }
                /**
                 * ALLOTMENTS
                 */
            } else
                return Vars.get().shouldBank = false;
        }
        return Vars.get().shouldBank = false;

    }
}
