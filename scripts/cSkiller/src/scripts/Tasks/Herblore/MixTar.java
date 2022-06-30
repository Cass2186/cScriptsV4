package scripts.Tasks.Herblore;

import lombok.Getter;
import org.tribot.api.General;
import org.tribot.api.input.Keyboard;
import org.tribot.api2007.Banking;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Skills;
import org.tribot.script.sdk.Log;
import org.tribot.script.sdk.MakeScreen;
import org.tribot.script.sdk.tasks.Amount;
import org.tribot.script.sdk.tasks.BankTask;
import scripts.BankManager;
import scripts.API.Priority;
import scripts.API.Task;
import scripts.Data.SkillBank;
import scripts.Data.SkillTasks;
import scripts.Data.Vars;
import scripts.Requirements.ItemReq;
import scripts.Tasks.MiscTasks.BuyItems;
import scripts.Timer;
import scripts.Utils;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class MixTar implements Task {


    public enum TARS {
        GUAM(19, 31, 249, 30),
        MARRENTILL(31, 39, 251, 42.5),
        TARROMIN(39, 44, 253, 55),
        HARRALANDER(44, 45, 255, 72.5);

        TARS(int levelReq, int maxLevel, int herbId, double expGained) {
            this.levelReq = levelReq;
            this.maxLevel = maxLevel;
            this.herbId = herbId;
            this.expGained = expGained;
        }

        @Getter
        private int levelReq;
        @Getter
        private int herbId;
        @Getter
        private int maxLevel;
        @Getter
        private double expGained;

        private static Skills.SKILLS skill = Skills.SKILLS.HERBLORE;

        public int determineResourcesToNextItem() {
            if (Skills.getActualLevel(skill) >= this.maxLevel)
                return 0;
            int max = this.maxLevel;
            int xpTillMax = Skills.getXPToLevel(skill, SkillTasks.HERBLORE.getEndLevel());
            if (max < SkillTasks.HERBLORE.getEndLevel()) {
                xpTillMax = Skills.getXPToLevel(skill, this.maxLevel);
            }
            General.println("DetermineResourcesToNextItem: " + (xpTillMax / this.expGained));
            return (int) (xpTillMax / this.expGained) + 15;
        }

        public static Optional<TARS> getCurrentItem() {
            for (TARS i : values()) {
                if (Skills.getActualLevel(skill) < i.maxLevel) {
                    return Optional.of(i);
                }
            }
            return Optional.empty();
        }

        public static List<ItemReq> getRequiredItemList() {
            List<ItemReq> i = new ArrayList<>();
            if (getCurrentItem().isPresent()) {
                getCurrentItem().ifPresent(h -> i.add(new ItemReq(h.getHerbId(), h.determineResourcesToNextItem())));
                getCurrentItem().ifPresent(h -> i.add(new ItemReq(SWAMP_TAR, h.determineResourcesToNextItem() * 15)));
                General.println("[TARS]: We need " + getCurrentItem().get().determineResourcesToNextItem() +
                        " items", Color.BLACK);

                General.println("[TARS]: We need " + i.size() + " sized list for herblore items", Color.BLACK);
            }
            i.add(new ItemReq(233, 1));
            return i;
        }
    }


    @Override
    public Priority priority() {
        return Priority.HIGH;
    }

    @Override
    public boolean validate() {
        return Vars.get().currentTask != null && Vars.get().currentTask.equals(SkillTasks.HERBLORE) &&
                Skills.getActualLevel(Skills.SKILLS.HERBLORE) >= 38;
    }

    @Override
    public void execute() {
        mixTar(getHerbIdForTar());
    }

    @Override
    public String taskName() {
        return "Herblore";
    }


    @Override
    public String toString() {
        return "Making Tar";
    }


    public int getHerbIdForTar() {
        if (Skills.getActualLevel(Skills.SKILLS.HERBLORE) < 31) {
            return TARS.GUAM.getHerbId();
        } else if (Skills.getActualLevel(Skills.SKILLS.HERBLORE) < 39) {
            return TARS.MARRENTILL.getHerbId();
        } else if (Skills.getActualLevel(Skills.SKILLS.HERBLORE) < 44) {
            return TARS.TARROMIN.getHerbId();
        } else {
            return TARS.HARRALANDER.getHerbId();
        }
    }

    public static int SWAMP_TAR = 1939;

    public boolean mixTarBank(int herbID) {
        BankTask task = BankTask.builder()
                .addInvItem(233, Amount.of(1)) // pestle & mortar
                .addInvItem(SWAMP_TAR, Amount.fill(15)) // swamp tar
                .addInvItem(herbID, Amount.fill(1))
                .build();
        List<ItemReq> inv = new ArrayList<>(
                Arrays.asList(
                        new ItemReq(233, 1),
                        new ItemReq(SWAMP_TAR, 0),
                        new ItemReq(herbID, 0)
                )
        );

        BankManager.open(true);
        BankManager.depositAll(true);
        List<ItemReq> newInv = SkillBank.withdraw(inv);
        if (newInv != null && newInv.size() > 0) {
            General.println("[Herblore Training]: Creating buy list");
            BuyItems.itemsToBuy = BuyItems.populateBuyList(TARS.getRequiredItemList());
            return false;
        }
        return BankManager.close(true);


        /* for (int i = 0; i < 3; i++) {
            if (task.isSatisfied()) {
                BankManager.close(true);
                return true;
            }
            Log.log("[Debug]: Banking for tar items");
            BankManager.open(true);
            task.execute();
        }

        return task.isSatisfied();*/
    }


    public void mixTar(int herbID) {
        if (mixTarBank(herbID)) { // we satisfy bankTask
            if (Banking.isBankLoaded()) {
                BankManager.close(true);
            }

            if (Utils.useItemOnItem(SWAMP_TAR, herbID)) {
                if (Timer.waitCondition(MakeScreen::isOpen, 2000, 3500)) {
                    Keyboard.typeString(" ");
                }
                Log.log("[Debug]: Mixing items");
                Timer.abc2SkillingWaitCondition(() ->
                        Inventory.find(herbID).length == 0, 47000, 55000);
            }
        }
    }


}
