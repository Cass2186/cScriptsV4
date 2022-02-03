package scripts.Listeners;


public interface InventoryListener {
    void inventoryItemGained(String itemName, Long count);
    void inventoryItemLost(String itemName, Long count);
}