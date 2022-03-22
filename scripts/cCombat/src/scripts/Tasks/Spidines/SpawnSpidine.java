package scripts.Tasks.Spidines;

import dax.walker_engine.interaction_handling.NPCInteraction;
import org.tribot.script.sdk.ChatScreen;
import org.tribot.script.sdk.Chatbox;
import org.tribot.script.sdk.Inventory;
import org.tribot.script.sdk.MyPlayer;
import org.tribot.script.sdk.query.Query;
import org.tribot.script.sdk.types.GameObject;
import org.tribot.script.sdk.types.Npc;
import org.tribot.script.sdk.types.WorldTile;
import scripts.ItemID;
import scripts.Tasks.Priority;
import scripts.Tasks.Task;
import scripts.Timer;

import java.util.Optional;

public class SpawnSpidine implements Task {

    private int SYMBOL_OF_LIFE = 21893;
    private WorldTile FIGHT_TILE = new WorldTile(3044, 4363, 0);

    private void spawnSpidine() {
        Optional<Npc> spidine = getSpidine();

        Optional<GameObject> symbolOfLife = Query
                .gameObjects()
                .idEquals(SYMBOL_OF_LIFE)
                .findBestInteractable();

        if (spidine.isEmpty() && symbolOfLife.map(s -> s.interact("Activate")).orElse(false)) {
            Timer.waitCondition(ChatScreen::isOpen, 3500, 5000);
        }
        if (ChatScreen.isOpen() && ChatScreen.handle()) {
            Timer.waitCondition(() -> getSpidine().isPresent() && MyPlayer.isHealthBarVisible(), 5000, 7000);
        }
    }

    private Optional<Npc> getSpidine() {
        return Query
                .npcs()
                .nameContains("Spidine")
                .isInteractingWithMe()
                .findBestInteractable();
    }

    @Override
    public String toString() {
        return "Spawning Spidine";
    }


    @Override
    public Priority priority() {
        return Priority.LOW;
    }

    @Override
    public boolean validate() {
        return getSpidine().isEmpty() && MyPlayer.getPosition().distanceTo(FIGHT_TILE) < 10 &&
                Inventory.contains(ItemID.RED_SPIDERS_EGGS) && Inventory.contains(ItemID.RAW_SARDINE);

    }

    @Override
    public void execute() {
        spawnSpidine();
    }
}
