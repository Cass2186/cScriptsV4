package scripts.GEManager;

import lombok.Builder;
import org.tribot.api.General;

import java.util.HashMap;


public class GEItem {

    int ItemID;
    int quantity;
    double percentIncrease;

    public HashMap<Integer, Integer> itemMaps = new HashMap<Integer, Integer>();

    public GEItem(int ItemID, int quantity){
        this.ItemID = ItemID;
        this.quantity = quantity;
        this.percentIncrease = 5;

    }


    public GEItem(int ItemID, int quantity, double buyIncrease){
        this(ItemID, quantity);
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
        if (ItemID != other.getItemID())
            return false;
        return true;
    }


    //allows duploicate filtering in skills Script
    @Override
    public int hashCode() {
        return ItemID;
    }


    public int getItemID(){
        return this.ItemID;
    }

    public int getItemQuantity(){
        return this.quantity;
    }

    public double getPercentIncrease(){
        return this.percentIncrease;
    }
}
