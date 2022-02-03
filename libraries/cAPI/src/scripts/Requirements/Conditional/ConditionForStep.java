package scripts.Requirements.Conditional;

import lombok.Getter;
import lombok.Setter;
import scripts.Requirements.Requirement;
import scripts.Requirements.Util.LogicType;

import java.util.ArrayList;
import java.util.List;

public abstract class ConditionForStep implements Requirement{

    @Setter
    @Getter
    protected boolean hasPassed;
    protected boolean onlyNeedToPassOnce;
    protected LogicType logicType;

    @Getter
    protected List<Requirement> conditions = new ArrayList<>();

    @Override
    abstract public boolean check();




}
