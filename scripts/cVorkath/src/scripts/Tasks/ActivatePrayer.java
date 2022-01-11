package scripts.Tasks;

import org.tribot.api2007.Game;
import org.tribot.api2007.types.RSObject;
import org.tribot.script.sdk.Prayer;
import scripts.EntitySelector.Entities;
import scripts.EntitySelector.finders.prefabs.ObjectEntity;

import java.util.Optional;

public class ActivatePrayer implements Task{

    public boolean isInHouse() {
        Optional<RSObject> p = Optional.ofNullable(Entities.find(ObjectEntity::new)
                .nameContains("Lunar Isle Portal")
                .actionsContains("Enter")
                .getFirstResult());
        return p.isPresent() && Game.isInInstance();
    }


    @Override
    public Priority priority() {
        return Priority.HIGHEST;
    }

    @Override
    public boolean validate() {
        return Game.isInInstance() && !isInHouse() &&
                !Prayer.isQuickPrayerEnabled();
    }

    @Override
    public void execute() {
        Prayer.enableQuickPrayer();
    }
}
