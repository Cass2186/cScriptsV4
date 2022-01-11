package scripts.Tasks.Magic;

import org.tribot.api2007.Equipment;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Player;
import org.tribot.api2007.Skills;
import org.tribot.api2007.types.RSTile;
import org.tribot.script.sdk.Magic;
import org.tribot.script.sdk.Waiting;
import scripts.API.Priority;
import scripts.API.Task;
import scripts.AntiBan;
import scripts.Data.SkillTasks;
import scripts.Data.Vars;
import scripts.ItemId;
import scripts.Timer;

import java.util.Optional;

public class Teleport implements Task {


    public void teleport(String name){
        RSTile playerTile = Player.getPosition();
        if (Magic.selectSpell(name)){
            Timer.waitCondition(()-> {
                AntiBan.timedActions();
                Waiting.waitNormal(300,75);
                return !Player.getPosition().equals(playerTile);
            }, 3200,4600);
        }
    }
    @Override
    public String toString(){
        return "Teleporting";
    }

    @Override
    public Priority priority() {
        return Priority.LOW;
    }

    @Override
    public boolean validate() {
        return  Vars.get().currentTask != null  && Vars.get().currentTask.equals(SkillTasks.MAGIC) &&
                Skills.getActualLevel(Skills.SKILLS.MAGIC) < 55 &&
                Skills.getActualLevel(Skills.SKILLS.MAGIC)  >= 37
                &&
                Inventory.find(ItemId.LAW_RUNE).length > 0 &&
                Equipment.isEquipped(ItemId.STAFF_OF_AIR);
    }

    @Override
    public void execute() {
        if (Vars.get().currentTask != null && !Vars.get().currentTask.isWithinLevelRange()) {
            Vars.get().currentTask = null;
        }
        Optional<SpellInfo> spellOptional = SpellInfo.getCurrentSpell();
        spellOptional.ifPresent(i -> teleport(i.getSpellString()));
    }

    @Override
    public String taskName() {
        return "Magic: Teleport";
    }
}
