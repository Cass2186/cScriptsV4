package scripts.QuestSteps;

import lombok.Getter;
import lombok.Setter;
import org.tribot.api.General;
import org.tribot.api2007.types.RSObjectDefinition;
import org.tribot.api2007.types.RSTile;
import scripts.Requirements.Requirement;
import scripts.Timer;
import scripts.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.BooleanSupplier;

public class UseItemOnItemStep extends QuestStep{

    private RSTile tile;
    private   int item1Id;
    private  int item2Id;
    private  String objectName;
    private  BooleanSupplier waitCondition;
    private  boolean waitCond;

    @Getter
    @Setter
    private  boolean handleChat;
    private  String chat;

    @Getter
    @Setter
    private int tileRadius = 2;
    @Getter
    protected final List<Requirement> requirements = new ArrayList<>();


    public UseItemOnItemStep(int ItemID, int item2Id, boolean waitCondition) {
        this.item1Id = ItemID;
        this.item2Id = item2Id;
        this.waitCond = waitCondition;
        this.handleChat = false;
    }
    public UseItemOnItemStep(int ItemID, int item2Id, boolean waitCondition, Requirement... requirements) {
        this.item1Id = ItemID;
        this.item2Id = item2Id;
        this.waitCond = waitCondition;
        this.handleChat = false;
        this.requirements.addAll(Arrays.asList(requirements));
    }

    @Override
    public void execute(){
        if (requirements.stream().anyMatch(r -> !r.check())) {
            General.println("[UseItemOnNPC]: We failed a requirement to execute this UseItemOnNpcStep");
            return;
        }

        if (Utils.useItemOnItem(this.item1Id, this.item2Id)){
            Timer.waitCondition(()-> waitCond, 5000,7000);
        }
    }

    @Override
    public void addDialogStep(String... dialog) {

    }

    @Override
    public void addSubSteps(QuestStep... substep) {

    }

    @Override
    public void addSubSteps(Collection<QuestStep> substeps) {

    }

}
