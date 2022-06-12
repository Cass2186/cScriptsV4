package scripts.QuestUtils;

import org.tribot.api.General;
import org.tribot.script.sdk.Log;
import org.tribot.script.sdk.Login;
import org.tribot.script.sdk.MyPlayer;
import org.tribot.script.sdk.query.Query;
import org.tribot.script.sdk.types.WorldTile;
import scripts.Timer;
import scripts.cQuesterV2;

import java.awt.*;

public class SafetyThread extends Thread {

    private Timer timer = new Timer(180000); //2min
    private WorldTile myTile = MyPlayer.getTile();

    @Override
    public void run() {
        while (cQuesterV2.isRunning.get()) {
            General.sleep(40, 80);
            if (!Login.isLoggedIn()) {
                General.sleep(5000, 10000);
            } else if (!MyPlayer.getTile().equals(myTile) || //we have moved or fighting someone
                    Query.npcs().isMyPlayerInteractingWith().isAny()) {
                myTile = MyPlayer.getTile(); //update tile
                timer.reset();
            }
            if (!timer.isRunning()) { //timed out
                Log.error("Stuck on same tile for 3min, ending");
                cQuesterV2.isRunning.set(false);
            }

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
