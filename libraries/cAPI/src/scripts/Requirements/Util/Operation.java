package scripts.Requirements.Util;

import lombok.Getter;

import java.util.function.BiFunction;

public enum Operation {

    LESS_EQUAL("<=", (x,y) -> x <= y),
    EQUAL("==", Integer::equals),
    GREATER_EQUAL(">=", (x,y) -> x >= y),
    NOT_EQUAL("=/=", (x,y) -> !x.equals(y));

    private final BiFunction<Integer, Integer, Boolean> operation;
    @Getter
    private String displayText;

    Operation(String displayText, BiFunction<Integer, Integer, Boolean> operation) {
        this.displayText = displayText;
        this.operation = operation;
    }

    public boolean check(int numberToCheck, int numberToCheckAgainst) {
        return operation.apply(numberToCheck, numberToCheckAgainst);
    }

}
