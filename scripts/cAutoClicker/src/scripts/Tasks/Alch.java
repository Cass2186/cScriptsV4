package scripts.Tasks;

import org.tribot.api.General;
import org.tribot.api.input.Mouse;
import org.tribot.api2007.Game;
import org.tribot.api2007.Interfaces;
import org.tribot.api2007.Skills;
import org.tribot.api2007.types.RSInterface;
import org.tribot.api2007.types.RSInterfaceChild;
import org.tribot.script.sdk.GameTab;
import org.tribot.script.sdk.Inventory;
import org.tribot.script.sdk.Login;
import scripts.Timer;

public class Alch implements Task{



    public void autoClick() {

    }
    @Override
    public Priority priority() {
        return Priority.MEDIUM;
    }

    @Override
    public boolean validate() {
        return Game.isInInstance();
    }

    @Override
    public void execute() {

    }
}
