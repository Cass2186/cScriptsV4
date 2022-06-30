package scripts.Tasks;

import org.tribot.api2007.types.RSItem;
import org.tribot.script.sdk.Log;
import org.tribot.script.sdk.MyPlayer;
import org.tribot.script.sdk.query.Query;
import scripts.EatUtil;

import java.io.IOException;

public class EatTask implements Task {

    @Override
    public Priority priority() {
        return Priority.MEDIUM;
    }

    @Override
    public boolean validate() {
        return (MyPlayer.getCurrentHealthPercent() < 0.5) && hasFood();
    }

    @Override
    public void execute() {
        EatUtil.eatFood(false);
    }

    public String toString() {
        return "Eating";
    }


    boolean hasFood() {
        return Query.inventory().actionContains("Eat").isAny();
    }

}

