package scripts;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class QuestUtil {
    public static <T> List<T> toArrayList(@Nonnull T... elements)
    {
        return new ArrayList<>(Arrays.asList(elements));
    }

    public static <T> Collector<T, ?, List<T>> collectToArrayList()
    {
        return Collectors.toCollection(ArrayList::new);
    }
}