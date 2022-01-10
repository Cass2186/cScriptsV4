package scripts.Gear;

import lombok.Getter;
import org.tribot.script.sdk.Equipment;

import java.util.List;
import java.util.stream.Stream;

public class GearItem {

    @Getter
    private Equipment.Slot slot;

    @Getter
    private List<Integer> itemIDs;


    @Getter
    private boolean overrideable;

    public GearItem(Equipment.Slot slot, List<Integer> itemIDs) {
        this.slot = slot;
        this.itemIDs = itemIDs;
    }

    private boolean isEquipped() {
        return itemIDs.stream().anyMatch(Equipment::contains);
    }


}
