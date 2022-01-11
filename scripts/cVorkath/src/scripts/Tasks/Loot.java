package scripts.Tasks;

import org.tribot.api2007.types.RSGroundItem;
import scripts.EntitySelector.Entities;
import scripts.EntitySelector.finders.prefabs.GroundItemEntity;

public class Loot implements Task {

    public void getItems(){
        RSGroundItem[] items = Entities.find(GroundItemEntity::new)
                .nameNotContains("Manta ray")
                .getResults();
    }

    @Override
    public String toString(){
        return "Looting";
    }

    @Override
    public Priority priority() {
        return Priority.LOW;
    }

    @Override
    public boolean validate() {
        return false;
    }

    @Override
    public void execute() {

    }
}
