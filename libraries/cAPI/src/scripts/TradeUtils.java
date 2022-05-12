package scripts;

import org.tribot.script.sdk.TradeScreen;
import org.tribot.script.sdk.Waiting;
import org.tribot.script.sdk.query.Query;
import org.tribot.script.sdk.types.Player;

import java.util.Optional;

public class TradeUtils {

    public boolean sendTrade(String playerName) {
        Optional<Player> targ = Query.players().nameContains(playerName).findBestInteractable();
        return targ.map(p -> p.interact("Trade")).orElse(false);
    }


    private boolean waitForTradeScreen(int waitLength) {
        return Waiting.waitUntil(waitLength, 500, () ->
                TradeScreen.getStage().isPresent());
    }

    private static int getCount(int itemId) {
        return Query.trade().idEquals(itemId).includeMyItems().count();
    }


    private static boolean offerItem(int itemId, int amount) {
        //first window
        if (TradeScreen.getStage().map(stage ->
                stage.equals(TradeScreen.Stage.FIRST_WINDOW)).orElse(false)) {
            int count = getCount(itemId);
            if (count == 0 &&
                    TradeScreen.offer(itemId, amount)) {
                // nothin offered yet
                Waiting.waitUntil(3000, 500, () -> getCount(itemId) == amount);
            } else if (count < amount && TradeScreen.offer(itemId, amount - count)) {
                //item already offered but in less amount than intended
                Waiting.waitUntil(3000, 500, () -> getCount(itemId) == amount);
            } else if (count > amount && TradeScreen.remove(itemId, count - amount)) {
                //item already offered but in more amount than intended
                Waiting.waitUntil(3000, 500, () -> getCount(itemId) == amount);
            }
            if (getCount(itemId) == amount) {
                //item  offered in correct amount
                return true;
            }
        }
        return false;
    }


    private boolean acceptFirstWindow() {
        if (TradeScreen.isDelayed())
            Waiting.waitUntil(5000, 300, () -> !TradeScreen.isDelayed());

        if (!TradeScreen.isDelayed()) {
            return TradeScreen.accept() &&
                    Waiting.waitUntil(30000, 500, () ->
                            TradeScreen.getStage().map(stage ->
                                    stage.equals(TradeScreen.Stage.SECOND_WINDOW)).orElse(false));
        }
        return TradeScreen.getStage().map(stage ->
                stage.equals(TradeScreen.Stage.SECOND_WINDOW)).orElse(false);
    }

    private boolean acceptSecondWindow() {
        return TradeScreen.accept() &&
                Waiting.waitUntil(30000, 500, () ->
                        TradeScreen.getStage().isEmpty());
    }

}
