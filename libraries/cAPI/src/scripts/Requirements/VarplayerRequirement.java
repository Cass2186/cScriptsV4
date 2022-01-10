package scripts.Requirements;

import org.tribot.api.General;
import org.tribot.api2007.Game;
import scripts.Requirements.Util.Operation;
import scripts.Utils;

import java.math.BigInteger;

/**
 * Modified from runelite plugin
 */
public class VarplayerRequirement implements Requirement{

    private final int varplayerId;
    private final int value;
    private final Operation operation;
    private final String displayText;

    private final int bitPosition;
    private final boolean bitIsSet;

    public VarplayerRequirement(int varplayerId, int value) {
        this.varplayerId = varplayerId;
        this.value = value;
        this.operation = Operation.EQUAL;
        this.displayText = null;

        this.bitPosition = -1;
        this.bitIsSet = false;
    }

    public VarplayerRequirement(int varplayerId, int value, String displayText) {
        this.varplayerId = varplayerId;
        this.value = value;
        this.operation = Operation.EQUAL;
        this.displayText = displayText;

        this.bitPosition = -1;
        this.bitIsSet = false;
    }

    public VarplayerRequirement(int varplayerId, int value, Operation operation) {
        this.varplayerId = varplayerId;
        this.value = value;
        this.operation = operation;
        this.displayText = null;

        this.bitPosition = -1;
        this.bitIsSet = false;
    }

    public VarplayerRequirement(int varplayerId, int value, Operation operation, String displayText) {
        this.varplayerId = varplayerId;
        this.value = value;
        this.operation = operation;
        this.displayText = displayText;

        this.bitPosition = -1;
        this.bitIsSet = false;
    }

    public VarplayerRequirement(int varplayerId, boolean bitIsSet, int bitPosition)
    {
        this.varplayerId = varplayerId;
        this.value = -1;
        this.operation = Operation.EQUAL;
        this.displayText = null;

        this.bitPosition = bitPosition;
        this.bitIsSet = bitIsSet;
    }

    public VarplayerRequirement(int varplayerId, boolean bitIsSet, int bitPosition, String displayText)
    {
        this.varplayerId = varplayerId;
        this.value = -1;
        this.operation = Operation.EQUAL;
        this.displayText = displayText;

        this.bitPosition = bitPosition;
        this.bitIsSet = bitIsSet;
    }



    @Override
    public boolean check() {
        if (bitPosition >= 0) {
            return bitIsSet == BigInteger.valueOf(Game.getSetting(varplayerId)).testBit(bitPosition);
        }
        return operation.check(Game.getSetting(varplayerId), value);
    }
}
