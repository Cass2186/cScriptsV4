package scripts.RcApi;

import org.tribot.script.sdk.Waiting;
import org.tribot.script.sdk.query.Query;
import org.tribot.script.sdk.types.GameObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RcUtils {

    public static Optional<GameObject> getAltar() {
        return  Query.gameObjects()
                .nameContains("Altar")
                .actionNotContains("Pray")
                .actionContains("Craft-rune")
                .findClosestByPathDistance();
    }

    public static boolean atAltar() {
        return  Query.gameObjects()
                .nameContains("Altar")
                .actionNotContains("Pray")
                .actionContains("Craft-rune")
                .isAny();
    }

    public static Optional<GameObject> getRuins(){
        return Query.gameObjects()
                .nameContains("Mysterious ruins")
                .actionContains("Enter")
                .findBestInteractable();
    }



    //uses a longer step because there's a slight delay once you actually arive
    public static boolean waitForAltar(){
       return Waiting.waitUntil(10000, 400, () -> RcUtils.getAltar().isPresent());
    }
}
