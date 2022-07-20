package scripts;

import org.tribot.script.sdk.*;
import org.tribot.script.sdk.types.WorldTile;
import scripts.QuestSteps.QuestStep;
import scripts.Requirements.ItemReq;

import javax.annotation.Nonnull;
import java.util.*;
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

    public static void handleQuest(Map<Integer, QuestStep> stepMap){

    }

}