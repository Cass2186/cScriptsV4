package scripts;

import org.tribot.script.sdk.query.Query;
import org.tribot.script.sdk.types.GameObject;
import org.tribot.script.sdk.types.InventoryItem;
import org.tribot.script.sdk.types.Npc;

import java.util.List;
import java.util.Optional;

public class QueryUtils {

    public static Optional<Npc> getNpc(int id){
        return Query.npcs().idEquals(id).findClosestByPathDistance();
    }

    public static Optional<Npc> getNpc(String name){
        return Query.npcs().nameContains(name).findClosestByPathDistance();
    }

    public static List<Npc> getNpcList(int... ids){
        return Query.npcs().idEquals(ids).toList();
    }

    public static List<Npc> getNpcList(String... names){
        return Query.npcs().nameContains(names).toList();
    }


    /**
     * Inventory
     */

    public static Optional<InventoryItem> getItem(int... id){
        return Query.inventory().idEquals(id).findClosestToMouse();
    }

    public static Optional<InventoryItem> getItem(String... name ){
        return Query.inventory().nameContains(name).findClosestToMouse();
    }

    public static Optional<InventoryItem> getItemList(int... ids){
        return Query.inventory().idEquals(ids).findClosestToMouse();
    }

    public static Optional<InventoryItem> getItemList(String...names){
        return Query.inventory().nameContains(names).findClosestToMouse();
    }


    public static Optional<GameObject> getObject(int... id){
        return Query.gameObjects().idEquals(id).findClosestByPathDistance();
    }

    public static Optional<GameObject> getObject(String... names){
        return Query.gameObjects().nameContains(names).findClosestByPathDistance();
    }

    public static List<GameObject> getObjectList(int... id){
        return Query.gameObjects().idEquals(id).toList();
    }

    public static List<GameObject> getObjectList(String... names){
        return Query.gameObjects().nameContains(names).toList();
    }

    public static boolean interactObject( Optional<GameObject> obj, String action){
        return obj.map(o->o.interact(action)).orElse(false);
    }




}
