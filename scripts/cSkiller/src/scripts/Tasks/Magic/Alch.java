package scripts.Tasks.Magic;

import lombok.Getter;
import org.tribot.api.General;
import org.tribot.api.input.Mouse;
import org.tribot.api2007.*;
import org.tribot.api2007.types.RSInterface;
import org.tribot.api2007.types.RSItem;
import org.tribot.api2007.types.RSItemDefinition;
import org.tribot.script.sdk.Waiting;
import scripts.API.Priority;
import scripts.API.Task;
import scripts.AntiBan;
import scripts.Data.SkillTasks;
import scripts.Data.Vars;
import scripts.ItemID;
import scripts.Timer;
import scripts.Utils;
import scripts.rsitem_services.GrandExchange;

import java.awt.*;
import java.util.Arrays;
import java.util.Optional;

public class Alch implements Task {

    public enum AlchItems {
        YEW_LONGBOW(ItemID.YEW_LONGBOW, 768),
        AIR_BATTLESTAFF(ItemID.AIR_BATTLESTAFF, 9300),
        FIRE_BATTLESTAFF(ItemID.FIRE_BATTLESTAFF,9300),
        WATER_BATTLESTAFF(ItemID.WATER_BATTLESTAFF,9300),
        EARTH_BATTLESTAFF(ItemID.EARTH_BATTLESTAFF,9300),
        ONYX_BOLTS_E(ItemID.ONYX_BOLTS_E,9000),
        RING_OF_LIFE(ItemID.RING_OF_LIFE, 2115),
        MAGIC_LONGBOW(ItemID.MAGIC_LONGBOW, 1536),
        COMBAT_BRACELET(ItemID.COMBAT_BRACELET0,12624),
        SKILLS_NECKLACE(ItemID.SKILLS_NECKLACE0, 12120),
        ABYSSAL_BRACELET_5(ItemID.ABYSSAL_BRACELET5, 2520);

        AlchItems(int id) {
            this.id = id;
        }

        AlchItems(int id, int value) {
            this.id = id;
            this.alchValue = value;
        }

        @Getter
        private int id;
        private int limit;
        private int alchValue = -1;

        public int getAlchValue() {
            RSItemDefinition def = RSItemDefinition.get(this.id);
            return def != null ? def.getHighAlchemyValue() : this.alchValue;
        }

        public int getNatureRuneCost() {
            RSItemDefinition def = RSItemDefinition.get(ItemID.NATURE_RUNE);
            return def != null ? def.getHighAlchemyValue() : 220;
        }

        public int getProfit(){
            Optional<Integer> priceAttempt = GrandExchange.tryGetPrice(this.id);
            if (priceAttempt.isEmpty())
                return -1;
            return this.getAlchValue() - priceAttempt.get()- getNatureRuneCost();
        }
    }

    private boolean isAlchSelected() {
        return Magic.isSpellSelected() && Magic.getSelectedSpellName().contains("High Level Alchemy");
    }


    public int getNotedId(int ItemID) {
        return ItemID + 1;
    }




    public boolean castAlch(int ItemID) {
        RSItem[] item = Inventory.find(ItemID);
        if (item.length == 0) {
            General.println("[Debug]: Missing alchable item");
            return false;
        }
        AntiBan.timedActions();

        if (Magic.isSpellSelected() && !Magic.getSelectedSpellName().contains("High Level Alchemy")) {
            General.println("[Debug]: Wrong spell is selected, clicking");
            if (Player.getPosition().click("Cast"))
                Timer.waitCondition(() -> !Magic.isSpellSelected(), 2500, 3000);
        }

        //select spell and wait for inv to open
        if (!isAlchSelected() && Magic.selectSpell("High Level Alchemy") &&
            Timer.waitCondition(() -> GameTab.getOpen().equals(GameTab.TABS.INVENTORY), 2500, 4200))
            Utils.idlePredictableAction();

        if (isAlchSelected() && GameTab.getOpen().equals(GameTab.TABS.INVENTORY)
                && item[0].click("Cast"))
            return Timer.waitCondition(() -> {
                AntiBan.timedActions();
                Waiting.waitNormal(350, 75);
                return GameTab.getOpen().equals(GameTab.TABS.MAGIC);
            }, 4000, 5200);

        return false;
    }

    @Override
    public Priority priority() {
        return Priority.LOW;
    }

    @Override
    public boolean validate() {
        return Vars.get().currentTask != null && Vars.get().currentTask.equals(SkillTasks.MAGIC) &&
                Skills.getActualLevel(Skills.SKILLS.MAGIC) >= 55 &&
              //  Skills.getActualLevel(Skills.SKILLS.MAGIC) < 75 &&
                (Inventory.find(getNotedId(Vars.get().alchItem.getId())).length > 0 &&
                        Inventory.find(ItemID.NATURE_RUNE).length > 0);
    }

    @Override
    public void execute() {
        if (MagicMethods.dragItemToAlch(getNotedId(Vars.get().alchItem.getId())))
            castAlch(getNotedId(Vars.get().alchItem.getId()));

    }

    @Override
    public String toString() {
        return "Alching";
    }
    @Override
    public String taskName() {
        return "Magic";
    }
}
