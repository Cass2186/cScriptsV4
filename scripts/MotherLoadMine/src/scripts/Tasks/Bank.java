package scripts.Tasks;


import org.tribot.api2007.types.RSObject;
import scripts.EntitySelector.Entities;
import scripts.EntitySelector.finders.prefabs.ObjectEntity;

public class Bank implements Task{


    public void collectOre(){
        RSObject hopper = Entities.find(ObjectEntity::new)
                .actionsContains("Deposit")
                .nameContains("Hopper")
                //   .nameNotContains("Rockfall")
                .getFirstResult();

    }

    @Override
    public Priority priority() {
        return Priority.HIGH;
    }

    @Override
    public boolean validate() {
        return false;
    }

    @Override
    public void execute() {

    }
}
