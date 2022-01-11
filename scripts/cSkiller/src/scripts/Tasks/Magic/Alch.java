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
import scripts.ItemId;
import scripts.Timer;
import scripts.rsitem_services.GrandExchange;

import java.awt.*;
import java.util.Arrays;
import java.util.Optional;

public class Alch implements Task {

    public enum AlchItems {
        YEW_LONGBOW(ItemId.YEW_LONGBOW, 768),
        AIR_BATTLESTAFF(ItemId.AIR_BATTLESTAFF, 9300),
        FIRE_BATTLESTAFF(ItemId.FIRE_BATTLESTAFF,9300),
        WATER_BATTLESTAFF(ItemId.WATER_BATTLESTAFF,9300),
        EARTH_BATTLESTAFF(ItemId.EARTH_BATTLESTAFF,9300),
        ONYX_BOLTS_E(ItemId.ONYX_BOLTS_E,9000),
        RING_OF_LIFE(ItemId.RING_OF_LIFE, 2115),
        MAGIC_LONGBOW(ItemId.MAGIC_LONGBOW, 1536),
        COMBAT_BRACELET(ItemId.COMBAT_BRACELET_0,12624),
        SKILLS_NECKLACE(ItemId.SKILLS_NECKLACE_0, 12120),
        ABYSSAL_BRACELET_5(ItemId.ABYSSAL_BRACELET_5, 2520);

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
            RSItemDefinition def = RSItemDefinition.get(ItemId.NATURE_RUNE);
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

    public Optional<RSInterface> getSpellInterface(String spellName) {
        RSInterface book = Interfaces.get(218);
        if (book != null) {
            RSInterface[] spells = book.getChildren();
            return Arrays.stream(spells).filter(s -> s.getComponentName() != null)
                    .filter(s -> General.stripFormatting(s.getComponentName()).contains(spellName))
                    .findFirst();
        }
        return Optional.empty();
    }

    public int getNotedId(int itemId) {
        return itemId + 1;
    }

    public Rectangle getScreenPosition(String spellName) {
        Optional<RSInterface> spellInter = getSpellInterface(spellName);
        return spellInter.isPresent() ? spellInter.get().getAbsoluteBounds() : null;
    }

    public boolean dragItemToAlch(int itemId) {
        Rectangle hoverPoint = getScreenPosition("High Level Alchemy");
        Point p = hoverPoint.getLocation();
        RSItem[] alchItem = Inventory.find(itemId);
        if (alchItem.length > 0) {
            Rectangle itemRec = alchItem[0].getArea();
            Point itemP = new Point((int) itemRec.getMaxX(), (int) itemRec.getMaxY());
            if (!hoverPoint.contains(itemP) && GameTab.open(GameTab.TABS.INVENTORY)) {
                General.println("[Debug]: Dragging item");
                Mouse.drag(itemRec.getLocation(), p, 1);
            }
            return hoverPoint.contains(itemP);
        }
        return false;
    }

    public boolean castAlch(int itemId) {
        RSItem[] item = Inventory.find(itemId);
        if (item.length == 0) {
            General.println("[Debug]: Missing alchable item");
            return false;
        }
        AntiBan.timedActions();

        if (Magic.isSpellSelected() && !Magic.getSelectedSpellName().contains("High Level Alchemy")) {
            General.println("[Debug]: Wrong spell is selected, clicking");
            if (Player.getPosition().click())
                Timer.waitCondition(() -> !Magic.isSpellSelected(), 2500, 3000);
        }

        //select spell and wait for inv to open
        if (!isAlchSelected() && Magic.selectSpell("High Level Alchemy"))
            Timer.waitCondition(() -> GameTab.getOpen().equals(GameTab.TABS.INVENTORY), 2500, 4200);

        if (isAlchSelected() && GameTab.getOpen().equals(GameTab.TABS.INVENTORY)
                && item[0].click("Cast"))
            return Timer.waitCondition(() -> {
                AntiBan.timedActions();
                Waiting.waitNormal(275, 75);
                return GameTab.getOpen().equals(GameTab.TABS.MAGIC);
            }, 3500, 4200);

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
                Skills.getActualLevel(Skills.SKILLS.MAGIC) < 75 &&
                (Inventory.find(getNotedId(Vars.get().alchItem.getId())).length > 0 &&
                        Inventory.find(ItemId.NATURE_RUNE).length > 0);
    }

    @Override
    public void execute() {
        if (dragItemToAlch(getNotedId(Vars.get().alchItem.id)))
            castAlch(getNotedId(Vars.get().alchItem.id));

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
