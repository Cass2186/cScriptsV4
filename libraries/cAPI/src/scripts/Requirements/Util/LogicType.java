package scripts.Requirements.Util;


import java.util.function.BiFunction;
import java.util.function.Predicate;
import java.util.stream.Stream;

public enum LogicType {
    /** Returns true only if all inputs match the supplied predicate. */
    AND(Stream::allMatch, (n1, n2) -> n1.doubleValue() == n2.doubleValue()),
    /** Returns true if any inputs match the supplied predicate. */
    OR(Stream::anyMatch, (n1, n2) -> n1.doubleValue() > 0.0D),
    /** The output is false is all inputs match the supplied predicate. Otherwise returns true. */
    NAND((s,p) -> !s.allMatch(p), (n1, n2) -> n1.doubleValue() < n2.doubleValue()),
    /** Returns true if all elements do not match the supplied predicate. */
    NOR(Stream::noneMatch, (n1, n2) -> n1.doubleValue() == 0.0D),
    /** Returns true if either, but not both, inputs match the given predicate.
     * This only tests the first two elements of the stream.
     */
    XOR((s,p) -> s.filter(p).limit(2).count() == 1, (n1, n2) -> {
        return (n1.doubleValue() > 0.0D && !(n2.doubleValue() > 0.0D)) || (n2.doubleValue() > 0.0D && !(n1.doubleValue() > 0.0D));
    }),
    ;

    private final BiFunction<Stream, Predicate, Boolean> function;
    private final BiFunction<Number, Number, Boolean> comparatorFunction;
    LogicType(BiFunction<Stream, Predicate, Boolean> func, BiFunction<Number, Number, Boolean> comparator)
    {
        this.function = func;
        this.comparatorFunction = comparator;
    }

    public <T> boolean test(Stream<T> stream, Predicate<T> predicate)
    {
        return function.apply(stream, predicate);
    }

    public boolean compare(Number numberToCheck, Number numberToCheckAgainst)
    {
        return comparatorFunction.apply(numberToCheck, numberToCheckAgainst);
    }
}
