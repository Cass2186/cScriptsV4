package scripts.Tasks;

import org.tribot.script.sdk.query.Query;
import org.tribot.script.sdk.types.Npc;
import scripts.Data.Const;

import java.util.Optional;

public class AttackTask implements Task{


    @Override
    public String toString() {
        return "Attacking";
    }

    @Override
    public Priority priority() {
        return null;
    }

    @Override
    public boolean validate() {
        return false;
    }

    @Override
    public void execute() {

    }


    public static Optional<Npc> getClosestShaman(){
        return Query.npcs()
                .idEquals(Const.LIZARD_SHAMAN_ID)
                .findClosestByPathDistance();
    }






}
