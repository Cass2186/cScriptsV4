package scripts;

import org.tribot.script.sdk.query.Query;
import org.tribot.script.sdk.types.Player;

import java.util.Optional;

public class TradeUtils {

    public boolean sendTrade(String playerName){
        Optional<Player> targ = Query.players().nameContains(playerName).findBestInteractable();
        return targ.map(p-> p.interact("Trade")).orElse(false);
    }


    private boolean waitForTradeScreen(){
      //  return Timer.waitCondition(()-> TradeScreen.Stage.FIRST_WINDOW, 6000, 9000);
        return false;
    }
}
