package scripts;

import org.tribot.script.sdk.TradeScreen;
import org.tribot.script.sdk.Waiting;
import org.tribot.script.sdk.interfaces.Item;
import org.tribot.script.sdk.query.Query;
import org.tribot.script.sdk.types.Player;
import scripts.Requirements.ItemReq;

import java.util.List;
import java.util.Optional;

public class TradeUtils {

    public static boolean tradeItems(String playerName, List<ItemReq> items){
        if (sendTrade(playerName) && waitForTradeScreen(8000)){
            for (ItemReq i :items){
                if(offerItem(i.getId(), i.getAmount()))
                    Waiting.waitNormal(350, 45);
            }
        }
        return false;
    }


    public static boolean sendTrade(String playerName) {
        Optional<Player> targ = Query.players().nameContains(playerName).findBestInteractable();
        return isOpen() || targ.map(p -> p.interact("Trade")).orElse(false);
    }

    private static boolean isOpen(){
        return TradeScreen.getStage().isPresent();
    }

    private static boolean waitForTradeScreen(int waitLength) {
        return Waiting.waitUntil(waitLength, 500, () -> isOpen());
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
        if (!TradeScreen.getStage().map(stage ->
                stage.equals(TradeScreen.Stage.FIRST_WINDOW)).orElse(false))
            return false;

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
        return TradeScreen.getStage().map(stage ->
                stage.equals(TradeScreen.Stage.SECOND_WINDOW)).orElse(false) &&
                TradeScreen.accept() &&
                Waiting.waitUntil(30000, 500, () ->
                        TradeScreen.getStage().isEmpty());
    }




}
