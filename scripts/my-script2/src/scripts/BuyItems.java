package scripts;


import scripts.GEManager.Exchange;
import scripts.GEManager.GEItem;
import scripts.Tasks.Priority;
import scripts.Tasks.Task;

import java.util.ArrayList;
import java.util.List;

public class BuyItems {

    public List<GEItem> toBuy;


    public static void buyList(List<GEItem> toBuy) {
        for (GEItem item : toBuy) {
            Exchange.selectBuyItem(item);
        }
    }


}
