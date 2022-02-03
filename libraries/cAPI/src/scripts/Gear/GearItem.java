package scripts.Gear;

import lombok.Getter;
import org.tribot.script.sdk.Equipment;

import java.util.List;
import java.util.stream.Stream;

public class GearItem {

    @Getter
    private Equipment.Slot slot;

    @Getter
    private List<Integer> ItemIDs;


    @Getter
    private boolean overrideable;

    public GearItem(Equipment.Slot slot, List<Integer> ItemIDs) {
        this.slot = slot;
        this.ItemIDs = ItemIDs;
    }

    private boolean isEquipped() {
        return ItemIDs.stream().anyMatch(Equipment::contains);
    }


}
