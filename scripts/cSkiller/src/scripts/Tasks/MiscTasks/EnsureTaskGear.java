package scripts.Tasks.MiscTasks;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.tribot.script.sdk.Equipment;
import org.tribot.script.sdk.Log;
import org.tribot.script.sdk.query.Query;
import org.tribot.script.sdk.types.definitions.ItemDefinition;
import scripts.API.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class EnsureTaskGear {

    @Getter
    List<Integer> itemList = new ArrayList<>();

    private String getNameWithoutTeleports(String name) {
        if (name.contains("\\(")) {
            String[] split = name.split("\\(");
           // Log.info("returning " + split[0]);
            return split.length > 0 ? split[0] : name;
        }
        return name;
    }

    public boolean hasAllGear() {
        for (Integer i : itemList) {
            Query.itemDefinitions().idEquals(i).findFirst();
            Optional<ItemDefinition> name = Query.itemDefinitions().idEquals(i).findFirst();
            if (!name.map(n -> Query.equipment()
                    .nameContains(getNameWithoutTeleports(n.getName())).isAny()).orElse(false)) {
                name.ifPresent(n -> Log.warn("Missing " + n));
                return false;
            }
        }
        return true;
    }
}
