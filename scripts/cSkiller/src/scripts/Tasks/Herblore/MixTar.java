package scripts.Tasks.Herblore;

import lombok.Getter;
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
import scripts.Data.SkillTasks;
import scripts.Data.Vars;
import scripts.Timer;
import scripts.Utils;

public class MixTar implements Task {


    @Override
    public Priority priority() {
        return Priority.HIGH;
    }

    @Override
    public boolean validate() {
        return Vars.get().currentTask != null  && Vars.get().currentTask.equals(SkillTasks.HERBLORE) &&
                Skills.getActualLevel(Skills.SKILLS.HERBLORE) >= 19  ;
    }

    @Override
    public void execute() {
        if (Skills.getActualLevel(Skills.SKILLS.HERBLORE) < 31) {
            mixTar(TARS.GUAM.getHerbId());
        } else if (Skills.getActualLevel(Skills.SKILLS.HERBLORE) < 39) {
            mixTar(TARS.MARRENTILL.getHerbId());
        } else if (Skills.getActualLevel(Skills.SKILLS.HERBLORE) < 44) {
            mixTar(TARS.TARROMIN.getHerbId());
        } else {
            mixTar(TARS.HARRALANDER.getHerbId());
        }
    }

    @Override
    public String taskName() {
        return "Herblore";
    }


    @Override
    public String toString() {
        return "Making Tar";
    }


    public enum TARS {
        GUAM(19, 249, 30),
        MARRENTILL(31, 251, 42.5),
        TARROMIN(39, 253, 55),
        HARRALANDER(44, 255, 72.5);

        TARS(int levelReq, int herbId, double expGained) {
            this.levelReq = levelReq;
            this.herbId = herbId;
            this.expGained = expGained;
        }

        @Getter
        private int levelReq;
        @Getter
        private int herbId;
        @Getter
        private double expGained;
    }


    public static int SWAMP_TAR = 1939;

    public boolean mixTarBank(int herbID) {
        BankTask task = BankTask.builder()
                .addInvItem(233, Amount.of(1)) // pestle & mortar
                .addInvItem(SWAMP_TAR, Amount.fill(15)) // swamp tar
                .addInvItem(herbID, Amount.fill(1))
                .build();

        for (int i = 0; i < 3; i++) {
            if (task.isSatisfied()) {
                BankManager.close(true);
                return true;
            }
            Log.log("[Debug]: Banking for tar items");
            BankManager.open(true);
            task.execute();
        }

        return task.isSatisfied();
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
