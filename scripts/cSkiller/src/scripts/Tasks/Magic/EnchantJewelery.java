package scripts.Tasks.Magic;

import org.tribot.api.Clicking;
import org.tribot.api.General;
import org.tribot.api.input.Mouse;
import org.tribot.api2007.*;
import org.tribot.api2007.types.RSInterface;
import org.tribot.api2007.types.RSItem;
import org.tribot.script.sdk.Bank;
import org.tribot.script.sdk.GameTab;
import org.tribot.script.sdk.Log;
import org.tribot.script.sdk.Waiting;
import org.tribot.script.sdk.tasks.BankTask;
import scripts.API.Priority;
import scripts.API.Task;
import scripts.AntiBan;
import scripts.Data.SkillTasks;
import scripts.Data.Vars;
import scripts.Timer;

import java.awt.*;
import java.util.Arrays;
import java.util.Optional;

public class EnchantJewelery implements Task {


    public void enchantItems(SpellInfo spell) {
        BankTask bnk = spell.getBankTask();

        if (bnk.isSatisfied()) {

            if (Bank.isOpen())
                Bank.close();

            RSItem[] enchItem = Inventory.find(spell.getItemId());
            int clickAllChance = General.random(0, 100);
            if (clickAllChance < Vars.get().clickAllJeweleryChance) {
                Log.info("Clicking all enchants");

                for (RSItem i : enchItem) {
                    if (clickSpell(spell.getSpellString())) {

                        if (Timer.waitCondition(() -> {
                            General.sleep(General.randomSD(75, 30));
                            return GameTab.INVENTORY.isOpen();
                        }, 1550, 2200) && i.click()) {

                            Rectangle hoverPoint = MagicMethods.getScreenPosition(spell.getSpellString());
                            if (hoverPoint != null && !hoverPoint.contains(Mouse.getPos()))
                                Mouse.moveBox(hoverPoint);

                            Timer.waitCondition(() -> {
                                        AntiBan.timedActions();
                                        General.sleep(General.randomSD(180, 30));
                                        return GameTab.MAGIC.isOpen();
                                    }
                                    , 2500, 3250);
                        }
                    }
                }
            } else if (clickSpell(spell.getSpellString())) {
                Waiting.waitUntil(3250, 275,
                        GameTab.INVENTORY::isOpen);
                if (enchItem[0].click()) {
                    Waiting.waitNormal(70, 30);
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
                if (Player.getPosition().click(""))
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
                Vars.get().currentTask.equals(SkillTasks.MAGIC)) {
            if (Skills.getActualLevel(Skills.SKILLS.MAGIC) < 37 ||
                    Vars.get().preferJeweleryOverTeleports) {
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
        } else if (Skills.getActualLevel(Skills.SKILLS.MAGIC) < 57) {
            enchantItems(SpellInfo.EMERALD_ENCHANT);
        } else if (Skills.getActualLevel(Skills.SKILLS.MAGIC) > 75) {
            enchantItems(SpellInfo.DRAGONSTONE_ENCHANT);
        }
    }

    @Override
    public String taskName() {
        return "Magic Training";
    }
}
