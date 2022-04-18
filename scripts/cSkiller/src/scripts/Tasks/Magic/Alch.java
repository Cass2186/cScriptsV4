package scripts.Tasks.Magic;

import lombok.Getter;


import org.tribot.api2007.Skills;
import org.tribot.api2007.types.RSItemDefinition;
import org.tribot.script.sdk.*;
import org.tribot.script.sdk.query.Query;
import org.tribot.script.sdk.types.InventoryItem;
import scripts.API.Priority;
import scripts.API.Task;
import scripts.AntiBan;
import scripts.Data.SkillTasks;
import scripts.Data.Vars;
import scripts.ItemID;
import scripts.Timer;
import scripts.Utils;
import scripts.rsitem_services.GrandExchange;

import java.util.Optional;

public class Alch implements Task {

    public enum AlchItems {
        YEW_LONGBOW(ItemID.YEW_LONGBOW, 768),
        AIR_BATTLESTAFF(ItemID.AIR_BATTLESTAFF, 9300),
        FIRE_BATTLESTAFF(ItemID.FIRE_BATTLESTAFF, 9300),
        WATER_BATTLESTAFF(ItemID.WATER_BATTLESTAFF, 9300),
        EARTH_BATTLESTAFF(ItemID.EARTH_BATTLESTAFF, 9300),
        ONYX_BOLTS_E(ItemID.ONYX_BOLTS_E, 9000),
        RING_OF_LIFE(ItemID.RING_OF_LIFE, 2115),
        MAGIC_LONGBOW(ItemID.MAGIC_LONGBOW, 1536),
        COMBAT_BRACELET(ItemID.COMBAT_BRACELET0, 12624),
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

        public int getProfit() {
            Optional<Integer> priceAttempt = GrandExchange.tryGetPrice(this.id);
            if (priceAttempt.isEmpty())
                return -1;
            return this.getAlchValue() - priceAttempt.get() - getNatureRuneCost();
        }
    }

    private boolean isAlchSelected() {
        return Magic.isAnySpellSelected() && Magic.getSelectedSpellName().map(
                s -> s.contains("High Level Alchemy")).orElse(false);
    }


    public int getNotedId(int ItemID) {
        return ItemID + 1;
    }


    public boolean castAlch(int itemID) {
        if (Bank.isOpen())
            Bank.close();

        if (org.tribot.script.sdk.GrandExchange.isOpen())
            org.tribot.script.sdk.GrandExchange.close();

        Optional<InventoryItem> fire = Query.inventory().idEquals(ItemID.STAFF_OF_FIRE).findClosestToMouse();
        if (!Equipment.contains(scripts.ItemID.STAFF_OF_FIRE) &&
                fire.map(f->f.click("Wield")).orElse(false)){
            Waiting.waitUntil(1500, 75, ()->
                    Equipment.contains(scripts.ItemID.STAFF_OF_FIRE));
        }
        Optional<InventoryItem> item = Query.inventory().idEquals(itemID).findClosestToMouse();
        if (item.map(i -> i.getStack() == 0).orElse(false)) {
            Log.info("Missing alchable item");
            return false;
        }

        AntiBan.timedActions();

        if (Magic.isAnySpellSelected() && !isAlchSelected()) {
            Log.info("Wrong spell is selected, clicking");
            if (MyPlayer.getTile().click(GameState.getUpText()))
                Timer.waitCondition(() -> !Magic.isAnySpellSelected(), 2500, 3000);
        }

        //select spell and wait for inv to open
        if (!isAlchSelected() && Magic.selectSpell("High Level Alchemy") &&
                Timer.waitCondition(GameTab.INVENTORY::isOpen, 2500, 4200))
            Utils.idlePredictableAction();

        if (isAlchSelected() && GameTab.INVENTORY.isOpen()
                && item.map(i -> i.click("Cast")).orElse(false))
            return Waiting.waitUntil(Utils.random(4000, 5200), 50, () -> {
                AntiBan.timedActions();
                Waiting.waitNormal(350, 75);
                return GameTab.MAGIC.isOpen();
            });

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
                (Inventory.contains(getNotedId(Vars.get().alchItem.getId())) &&
                        Inventory.contains(ItemID.NATURE_RUNE));
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
