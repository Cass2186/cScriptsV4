package scripts.Tasks;

import org.tribot.api.General;
import org.tribot.api2007.NPCs;
import org.tribot.api2007.types.RSNPC;
import scripts.VorkUtils.VorkthUtil;

public class AttackVork implements Task{


    @Override
    public String toString(){
        return "Attacking Vorkath";
    }

    @Override
    public Priority priority() {
        return Priority.LOWEST;
    }

    @Override
    public boolean validate() {
        RSNPC[] vork = NPCs.findNearest(VorkthUtil.ATTACKING_VORK);
        return vork.length >0 && !vork[0].isInCombat();
    }

    @Override
    public void execute() {
        VorkthUtil.clickVorkath("Attack");
        General.sleep(General.randomSD(750,150));
    }
}