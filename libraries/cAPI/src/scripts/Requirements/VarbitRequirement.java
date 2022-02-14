package scripts.Requirements;

import org.tribot.api2007.Game;
import org.tribot.script.sdk.Log;
import scripts.Requirements.Util.Operation;
import scripts.Utils;

import java.math.BigInteger;

public class VarbitRequirement implements Requirement {

    private int varbitID;
    private Operation operation;

    private final int requiredValue;

    private String displayText = "";

    // bit positions
    private boolean bitIsSet = false;
    private int bitPosition = -1;


    public VarbitRequirement(int varbit, int value) {
        this.varbitID = varbit;
        this.requiredValue = value;
    }


    public VarbitRequirement(int varbit, int value, Operation operation) {
        this.varbitID = varbit;
        this.requiredValue = value;
        this.operation = operation;
    }

    /**
     * Checks if a specified varbit value has a specific bit position set.
     *
     * @param varbitID    the varbit id
     * @param bitIsSet    if the bit should be set
     * @param bitPosition the position of the bit in question
     */
    public VarbitRequirement(int varbitID, boolean bitIsSet, int bitPosition)
    {
        this.varbitID = varbitID;
        this.requiredValue = -1;
        this.operation = Operation.EQUAL;

        this.bitPosition = bitPosition;
        this.bitIsSet = bitIsSet;
        String[] suffixes = new String[]{"th", "st", "nd", "rd", "th", "th", "th", "th", "th", "th"};
        String text = String.valueOf(bitPosition);
        switch (bitPosition % 100)
        {
            case 11:
            case 12:
            case 13:
                text += "th";
            default:
                text = bitPosition + suffixes[bitPosition % 10];
        }
        this.displayText = varbitID + " must have the " + text + " bit set.";

    }



    @Override
    public boolean check() {
        if (bitPosition >= 0) {
            return bitIsSet == BigInteger.valueOf(Utils.getVarBitValue(varbitID)).testBit(bitPosition);
        }
        if (this.operation == null) {
            if ( Utils.getVarBitValue(this.varbitID) == this.requiredValue)
                Log.debug("[VarbitReq]: Varbit req is true for varbit " + this.varbitID);
            return Utils.getVarBitValue(this.varbitID) == this.requiredValue;
        }

        return operation.check(Utils.getVarBitValue(this.varbitID), this.requiredValue);
    }
}
