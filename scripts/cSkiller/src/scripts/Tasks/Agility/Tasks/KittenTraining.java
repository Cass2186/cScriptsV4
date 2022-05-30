package scripts.Tasks.Agility.Tasks;

import dax.walker_engine.interaction_handling.NPCInteraction;
import org.tribot.api.General;
import org.tribot.api2007.ext.Filters;
import org.tribot.api2007.types.RSItem;
import org.tribot.script.sdk.Inventory;
import org.tribot.script.sdk.Waiting;
import org.tribot.script.sdk.query.Query;
import org.tribot.script.sdk.types.InventoryItem;
import org.tribot.script.sdk.types.Npc;
import scripts.API.Priority;
import scripts.API.Task;
import scripts.Data.SkillTasks;
import scripts.Data.Vars;
import scripts.ItemID;
import scripts.NpcChat;
import scripts.Timer;
import scripts.Utils;

import java.util.Optional;

public class KittenTraining implements Task {


    int SHRIMPS = 315;
    int BALL_OF_WOOL = 1759;


    String[] CAT_NAMES = {"Pet cat", "Pet kitten", "Hell-cat", "Hell-kitten", "Hellcat", "Hell-kitten", "Lazy cat", "Wily cat"};

    Timer feedTimer = new Timer(General.random(1200000, 1680000)); //20-28m
    Timer playTimer = new Timer(General.random(2400000, 2880000)); //40-48min


    public boolean pickupKitten() {
        Optional<Npc> kitten =  Query.npcs().isInteractingWithMe().nameContains("Kitten").findClosest();
        return kitten.map(k->k.interact("Pick-up")).orElse(false) &&
                Waiting.waitUntil(3500,500, ()-> Inventory.contains("Kitten"));
    }

    public void playWithCat() {
        Optional<InventoryItem> wool = Query.inventory().idEquals(ItemID.BALL_OF_WOOL).findClosestToMouse();
        if (wool.map(f->  Utils.useItemOnNPC(f.getId(), CAT_NAMES)).orElse(false)) {
            NpcChat.waitForChatScreen();
            playTimer.reset();
        } else {
            Optional<Npc> kitten =  Query.npcs().isInteractingWithMe().nameContains("Kitten").findClosest();
            if (kitten.map(k->k.interact("Interact")).orElse(false)){ //TODO check string
                NpcChat.handle(true); //todo update handling
                playTimer.reset(); //todo update length
            }
        }
    }

    public void feedCat() {
        Optional<InventoryItem> food = Query.inventory().actionContains("Eat").findClosestToMouse();
        if (food.map(f->  Utils.useItemOnNPC(f.getId(), CAT_NAMES)).orElse(false)) {
            NpcChat.waitForChatScreen();
            feedTimer.reset();
        }

    }

    @Override
    public String toString() {
        return "Interacting with Kitten";
    }

    @Override
    public Priority priority() {
        return Priority.HIGHEST;
    }

    @Override
    public boolean validate() {
        return Vars.get().currentTask != null &&
                Vars.get().currentTask.equals(SkillTasks.AGILITY) &&
                !feedTimer.isRunning() || !playTimer.isRunning();
    }

    @Override
    public void execute() {
        if(!feedTimer.isRunning()){
            feedCat();
        } else if (!playTimer.isRunning()){
            playWithCat();
        }
    }

    @Override
    public String taskName() {
        return "Agility - Kitten Training";

    }
}
