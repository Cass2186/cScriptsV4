package scripts.Tasks.Magic;

import org.tribot.api.Clicking;
import org.tribot.api.General;
import org.tribot.api.input.Mouse;
import org.tribot.api2007.*;
import org.tribot.api2007.types.RSInterface;
import org.tribot.api2007.types.RSItem;
import org.tribot.script.sdk.Bank;
import org.tribot.script.sdk.Equipment;
import org.tribot.script.sdk.Log;
import org.tribot.script.sdk.Waiting;
import org.tribot.script.sdk.tasks.Amount;
import org.tribot.script.sdk.tasks.BankTask;
import org.tribot.script.sdk.tasks.EquipmentReq;
import scripts.API.Priority;
import scripts.API.Task;
import scripts.AntiBan;
import scripts.Data.SkillTasks;
import scripts.Data.Vars;
import scripts.ItemId;
import scripts.Timer;

import java.awt.*;
import java.util.Arrays;
import java.util.Optional;

public class EnchantJewelery implements Task {

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

    public boolean clickSpellString(String spellName) {
        Optional<RSInterface> spellInter = getSpellInterface(spellName);
        return spellInter.isPresent() && spellInter.get().click();
    }

    public Rectangle getScreenPosition(String spellName) {
        Optional<RSInterface> spellInter = getSpellInterface(spellName);
        return spellInter.isPresent() ? spellInter.get().getAbsoluteBounds() : null;
    }


    public void enchantItems(SpellInfo spell) {
        BankTask bnk = spell.getBankTask();

        if (bnk.isSatisfied()) {

            if (Bank.isOpen())
                Bank.close();

            RSItem[] enchItem = Inventory.find(spell.getItemId());
            int clickAllChance = General.random(0, 100);
            if (clickAllChance < General.random(25,45)) {
                General.println("[Debug]: Clicking all enchants");

                for (RSItem i : enchItem) {
                    if (clickSpell(spell.getSpellString())) {

                        if (Timer.waitCondition(() -> {
                            General.sleep(General.randomSD(75, 30));
                            return GameTab.getOpen() ==
                                    GameTab.TABS.INVENTORY;
                        }, 1550, 2200) && i.click()) {

                            Rectangle hoverPoint = getScreenPosition(spell.getSpellString());
                            if (hoverPoint != null && !hoverPoint.contains(Mouse.getPos()))
                                Mouse.moveBox(hoverPoint);

                            Timer.waitCondition(() -> {
                                        AntiBan.timedActions();
                                        General.sleep(General.randomSD(160, 30));
                                      return  GameTab.getOpen().equals(GameTab.TABS.MAGIC);
                                    }
                                 , 2500, 3250);
                        }
                    }
                }
            } else if (clickSpell(spell.getSpellString())) {
                Timer.waitCondition(() -> GameTab.getOpen().equals(GameTab.TABS.INVENTORY), 2500, 3250);

                if (enchItem[0].click()) {
                    Waiting.waitNormal(60, 30);
                    General.println("[Debug]: Slow enchanting");
                    Mouse.leaveGame();
                    Timer.abc2SkillingWaitCondition(() -> !bnk.isSatisfied(), 80000, 90000);
                }
            }

        } else {
            bnk.execute();
        }
    }


    public boolean clickSpell(String spellName) {
        if (Magic.isSpellSelected()) {

            String s = Magic.getSelectedSpellName();
            if (s != null && s.contains(spellName))
                return true;
            else if (s != null && !s.contains(spellName)) {
                Log.log("[Enchant Jewelery]: Deselecting wrong spell");
                if (Clicking.click())
                    General.sleep(250, 500);
                return Magic.selectSpell(spellName);
            }
        }
        System.out.println("[Enchant Jewelery]: Spell being selected " + spellName);
        return Magic.selectSpell(spellName);
    }

    @Override

    public String toString() {
        return "Enchanting";
    }

    @Override
    public Priority priority() {
        return Priority.LOW;
    }

    @Override
    public boolean validate() {
        if (Vars.get().currentTask != null &&
                Vars.get().currentTask.equals(SkillTasks.MAGIC)){
            if (Skills.getActualLevel(Skills.SKILLS.MAGIC) < 37) {
               return true;
            } else if (Skills.getActualLevel(Skills.SKILLS.MAGIC)  <37) { //should be > 67
                return true;
            }
        }
        return false;
    }

    @Override
    public void execute() {
        if (Skills.getActualLevel(Skills.SKILLS.MAGIC) < 27) {
            enchantItems(SpellInfo.SAPPHIRE_ENCHANT);
        } else if (Skills.getActualLevel(Skills.SKILLS.MAGIC) < 37) {
            enchantItems(SpellInfo.EMERALD_ENCHANT);
        } else if (Skills.getActualLevel(Skills.SKILLS.MAGIC) > 57) {
            enchantItems(SpellInfo.DIAMOND_ENCHANT);
        }
    }

    @Override
    public String taskName() {
        return "Magic Training";
    }
}
