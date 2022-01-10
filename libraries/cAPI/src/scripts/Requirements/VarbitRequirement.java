package scripts.Requirements;

import org.tribot.api2007.Game;
import scripts.Requirements.Util.Operation;
import scripts.Utils;

public class VarbitRequirement implements Requirement {

    private int varbit;
    private int value;
    private Operation operation;


    public VarbitRequirement(int varbit, int value) {
        this.varbit = varbit;
        this.value = value;
    }


    public VarbitRequirement(int varbit, int value, Operation operation) {
        this.varbit = varbit;
        this.value = value;
        this.operation = operation;
    }


    @Override
    public boolean check() {
        if (this.operation == null)
            return Utils.getVarBitValue(this.varbit) == this.value;
        return operation.check(Game.getSetting(this.varbit), value);
    }
}
