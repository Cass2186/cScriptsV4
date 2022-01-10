package scripts.GEManager;

import org.tribot.api.General;

import java.util.HashMap;

public class GEItem {

    int itemId;
    int quantity;
    double percentIncrease;

    public HashMap<Integer, Integer> itemMaps = new HashMap<Integer, Integer>();

    public GEItem(int itemId, int quantity){
        this.itemId = itemId;
        this.quantity = quantity;
        this.percentIncrease = 5;

    }


    public GEItem(int itemId, int quantity, double buyIncrease){
        this(itemId, quantity);
        this.percentIncrease = buyIncrease;
    }

    //allows duploicate filtering in skills Script
    @Override
    public boolean equals(Object obj) {

        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        GEItem other = (GEItem) obj;
        if (itemId != other.getItemId())
            return false;
        //General.println("[GEItem]: Removing duplicate item");
        return true;
    }


    //allows duploicate filtering in skills Script
    @Override
    public int hashCode() {
        return itemId;
    }


    public int getItemId(){
        return this.itemId;
    }

    public int getItemQuantity(){
        return this.quantity;
    }

    public double getPercentIncrease(){
        return this.percentIncrease;
    }
}
