package scripts.Tasks.CatTraining;

import dax.walker_engine.interaction_handling.NPCInteraction;
import org.tribot.api.DynamicClicking;
import org.tribot.api.General;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.types.RSItem;
import org.tribot.api2007.types.RSNPC;
import org.tribot.script.sdk.Log;
import org.tribot.script.sdk.MessageListening;
import scripts.*;
import scripts.API.Priority;
import scripts.API.Task;
import scripts.EntitySelector.Entities;
import scripts.EntitySelector.finders.prefabs.NpcEntity;

import java.awt.*;

public class CatTraining implements Task {

    String[] CAT_NAMES = {"Pet cat", "Pet kitten", "Hell-cat", "Hell-kitten",
            "Hellcat", "Hell-kitten", "Lazy cat", "Wily cat"};

    int SHRIMPS = 315;
    int BALL_OF_WOOL = 1759;
    long currentTime = System.currentTimeMillis();
    long nextFeedTime = 0;
    long nextInteractTime = 0;
    long logoutTimer = 0;
    long nextRandomAction = 0;

    public void feedCat() {
        RSNPC npcCat = getFollowerRSNPC();
        RSItem[] salmon = Inventory.find(ItemID.SALMON);
        int lng = salmon.length;
        if (salmon.length > 0 && npcCat != null) {
          //  cQuesterV2.status = "Feeding Cat";
            General.println("[Debug]: Feeding Cat", Color.red);
            for (int i = 0; i < 3; i++) {
                if (Utils.useItemOnNPC(ItemID.SALMON, npcCat)) {
                    if (Timer.waitCondition(() -> Inventory.find(ItemID.SALMON).length < lng, 2500, 3500)) {
                        feedTimer.reset();
                        break;
                    }
                    General.println("[Debug]: Seemingly failed to feed cat, looping (i = " + i + ")");
                }
                General.sleep(300, 900);
            }

        } else {
            pickupCat();
            General.println("[Debug]: Should be feeding cat, but we are out of food or don't have a pet.");
        }
    }

    Timer feedTimer = new Timer(General.random(900000, 1000000));
    Timer interactTimer = new Timer(General.random(420000, 720000)); //7-12 min

    public void checkFeeding() {
        if (!feedTimer.isRunning()) {
            feedCat();
        }
    }
    public RSNPC getFollowerRSNPC() {
        RSNPC[] kitten = Entities.find(NpcEntity::new)
                .actionsContains("Chase")
                .actionsContains("Interact")
                .sortByDistance()
                .getResults();

        if (kitten != null && kitten.length > 0) {
            return kitten[0];
        }

        return null;
    }

    public void pickupCat() {
        RSNPC npcCat = getFollowerRSNPC();
        if (npcCat != null && Inventory.find(CAT_NAMES).length < 1) {
            Log.log("[Debug]: Picking up cat");
            if (Inventory.isFull()) {
                EatUtil.eatFood(false);
            }
            if (DynamicClicking.clickRSNPC(npcCat, "Pick-up"))
                Timer.waitCondition(() -> Inventory.find(CAT_NAMES).length > 0, 3500, 45000);
        }
    }

    public void interactWithCat() {
        RSNPC myKitten = getFollowerRSNPC();
        if (myKitten != null) {
            for (int i = 0; i < 2; i++) {
                if (Utils.clickNPC(myKitten, "Interact", false)) {
                    NPCInteraction.waitForConversationWindow();
                    NPCInteraction.handleConversation("Stroke");
                    NPCInteraction.waitForConversationWindow();
                    interactTimer.reset();
                }
            }
        }
    }

    public void checkInteraction() {
        if (!interactTimer.isRunning()) {
            General.println("[Debug]: Stroking Cat", Color.red);
            interactWithCat();
        }
    }

    @Override
    public Priority priority() {
        return null;
    }

    @Override
    public boolean validate() {
        return false;
    }

    @Override
    public void execute() {
        MessageListening.addServerMessageListener(message -> {
            Log.log("Got a new server message:");
            Log.log(message);
        });
    }

    @Override
    public String taskName() {
        return null;
    }
}
