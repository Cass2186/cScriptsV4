package scripts.Requirements.Util;

import lombok.Getter;
import lombok.Setter;
import org.tribot.script.sdk.Log;
import scripts.Requirements.Requirement;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import scripts.Requirements.Requirement;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Conditions implements Requirement {

    @Setter
    @Getter
    protected boolean hasPassed;
    protected boolean onlyNeedToPassOnce;

    private final LogicType logicType;
    protected Operation operation;
    protected int quantity;

    @Setter
    protected String text;


    @Getter
    protected List<Requirement> conditions = new ArrayList<>();


    public Conditions(LogicType logicType, Requirement... conditions) {
        this.conditions = new ArrayList<>();
        Collections.addAll(this.conditions, conditions);
        this.logicType = logicType;
    }

    public Conditions(Operation operation, int quantity, Requirement... conditions) {
        this.conditions = new ArrayList<>();
        Collections.addAll(this.conditions, conditions);
        this.logicType = LogicType.AND;
        this.operation = operation;
        this.quantity = quantity;
    }

    public Conditions(Requirement... conditions) {
        this.conditions = new ArrayList<>();
        Collections.addAll(this.conditions, conditions);
        this.logicType = LogicType.AND;
    }

    public Conditions(LogicType logicType, List<Requirement> conditions) {
        this.conditions = new ArrayList<>(conditions);
        this.logicType = logicType;
    }

    public Conditions(boolean onlyNeedToPassOnce, Operation operation, int quantity, Requirement... conditions) {
        this.conditions = new ArrayList<>();
        Collections.addAll(this.conditions, conditions);
        this.onlyNeedToPassOnce = onlyNeedToPassOnce;
        this.logicType = LogicType.AND;
        this.operation = operation;
        this.quantity = quantity;
    }

    public Conditions(boolean onlyNeedToPassOnce, LogicType logicType, Requirement... conditions) {
        this.conditions = new ArrayList<>();
        Collections.addAll(this.conditions, conditions);
        this.onlyNeedToPassOnce = onlyNeedToPassOnce;
        this.logicType = logicType;
    }

    public Conditions(boolean onlyNeedToPassOnce, Requirement... conditions) {
        this.conditions = new ArrayList<>();
        Collections.addAll(this.conditions, conditions);
        this.onlyNeedToPassOnce = onlyNeedToPassOnce;
        this.logicType = LogicType.AND;
    }


    @Override
    public boolean check() {
        if (onlyNeedToPassOnce && hasPassed)
            return true;


        int conditionsPassed = (int) conditions.stream().filter(c -> {
            if (c == null) {
                return true;
            }
            return c.check();
        }).count();

        if (operation != null) {
            return operation.check(conditionsPassed, quantity);
        }
        Log.log("[Conditons]: conditions.size() = " + conditions.size());

        //TODO: Replace with LogicType check, however more testing to be done to make sure nothing breaks
        if ((conditionsPassed > 0 && logicType == LogicType.OR)
                || (conditionsPassed == 0 && logicType == LogicType.NOR)
                || (conditionsPassed == conditions.size() && logicType == LogicType.AND)
                || (conditionsPassed < conditions.size() && logicType == LogicType.NAND)) {
            hasPassed = true;
            return true;
        }

        return false;
    }
}
