package scripts.Tasks;

import javafx.print.PageLayout;
import org.apache.commons.lang3.ArrayUtils;
import org.tribot.api.General;
import org.tribot.api2007.Player;
import org.tribot.api2007.Skills;
import org.tribot.script.sdk.Log;
import org.tribot.script.sdk.Prayer;
import org.tribot.script.sdk.Skill;
import org.tribot.script.sdk.query.Query;
import org.tribot.script.sdk.types.Npc;
import scripts.Data.Areas;
import scripts.Data.Vars;
import scripts.ItemID;
import scripts.Timer;
import scripts.Utils;

import java.util.Optional;

public class RechargePrayer implements Task {

    public boolean prayAtAltar() {
        int p = Prayer.getPrayerPoints();

        int maxLevel =  Skill.RANGED.getActualLevel() + (int) Utils.getLevelBoost(Skills.SKILLS.RANGED);
        if (Skill.RANGED.getCurrentLevel() <= (maxLevel - General.random(4,6)) ||
                Skill.RANGED.getCurrentLevel() == Skill.RANGED.getActualLevel()){
            Utils.drinkPotion(ItemID.RANGING_POTION_INVERSE);

        }
        Log.debug("Recharging prayer");
        if (Utils.clickObj(34900, "Pray-at")) {
            return Timer.waitCondition(() -> Prayer.getPrayerPoints() > p, 7000, 9000);
        }
        return false;
    }


    public void reengageTarget(){
        Optional<Npc> attackingMe = Query.npcs()
                .nameContains(Vars.get().targets)
                .isInteractingWithMe()
                .findBestInteractable();

        if (attackingMe.isPresent() && !attackingMe.get().isHealthBarVisible()){
            Log.debug("Attacking NPC");
            attackingMe.map(n -> n.interact("Attack"));
        }

    }

    @Override
    public Priority priority() {
        return Priority.HIGHEST;
    }

    @Override
    public boolean validate() {
        return Areas.UNDEAD_DRUID_AREA.contains(Player.getPosition()) &&
                Prayer.getPrayerPoints() < General.random(10,17);
    }

    @Override
    public void execute() {
        Log.debug("Praying at altar");
        if(prayAtAltar())
            reengageTarget();
    }
}
