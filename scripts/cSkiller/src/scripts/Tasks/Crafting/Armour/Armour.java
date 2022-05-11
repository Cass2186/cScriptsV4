package scripts.Tasks.Crafting.Armour;

import lombok.Getter;
import org.tribot.api.General;
import org.tribot.api2007.Skills;
import org.tribot.script.sdk.*;
import org.tribot.script.sdk.query.Query;
import org.tribot.script.sdk.types.InventoryItem;
import scripts.BankManager;
import scripts.Data.Enums.Crafting.CraftItems;
import scripts.Data.SkillTasks;
import scripts.ItemID;
import scripts.Requirements.ItemReq;
import scripts.Utils;

import java.awt.*;
import java.util.*;
import java.util.List;

public enum Armour {

    GREEN_DHIDE_VAMBS(57, ItemID.GREEN_DHIDE_VAMBRACES,
            new ArrayList<>(List.of(new ItemReq(ItemID.GREEN_DRAGON_LEATHER, 0, 1)))),
    GREEN_DHIDE_CHAPS(60, ItemID.GREEN_DHIDE_CHAPS,
            new ArrayList<>(List.of(new ItemReq(ItemID.GREEN_DRAGON_LEATHER, 0, 2)))),
    GREEN_DHIDE_BODY(63, ItemID.GREEN_DHIDE_BODY, new ArrayList<>(List.of(new ItemReq(ItemID.GREEN_DRAGON_LEATHER, 0, 3)))),
    // BLUE_DHIDE_VAMBS(66, ItemID.BLUE_DHIDE_VAMBRACES, List.of(new ItemReq(ItemID.BLUE_DRAGON_LEATHER, 0, 1))),
    // BLUE_DHIDE_CHAPS(68, ItemID.BLUE_DHIDE_CHAPS, List.of(new ItemReq(ItemID.BLUE_DRAGON_LEATHER, 0, 2))),
    BLUE_DHIDE_BODY(71, ItemID.BLUE_DHIDE_BODY,
            new ArrayList<>(List.of(new ItemReq(ItemID.BLUE_DRAGON_LEATHER, 0, 3)))),
    RED_DHIDE_VAMBS(73, ItemID.RED_DHIDE_VAMBRACES,
            new ArrayList<>(List.of(new ItemReq(ItemID.RED_DRAGON_LEATHER, 0, 1)))),
    RED_DHIDE_CHAPS(75, ItemID.RED_DHIDE_CHAPS,
            new ArrayList<>(List.of(new ItemReq(ItemID.RED_DRAGON_LEATHER, 0, 2)))),
    RED_DHIDE_BODY(77, ItemID.RED_DHIDE_BODY,
            new ArrayList<>(List.of(new ItemReq(ItemID.RED_DRAGON_LEATHER, 0, 3)))),
    BLACK_DHIDE_VAMBS(79, ItemID.BLACK_DHIDE_VAMBRACES,
            new ArrayList<>(List.of(new ItemReq(ItemID.BLACK_DRAGON_LEATHER, 0, 1)))),
    BLACK_DHIDE_CHAPS(82, ItemID.BLACK_DHIDE_CHAPS,
            new ArrayList<>(List.of(new ItemReq(ItemID.BLACK_DRAGON_LEATHER, 0, 2)))),
    BLACK_DHIDE_BODY(84, ItemID.BLACK_DHIDE_BODY,
            new ArrayList<>(List.of(new ItemReq(ItemID.BLACK_DRAGON_LEATHER, 0, 3))));

    @Getter
    private int itemId;

    @Getter
    private int levelReq;

    @Getter
    private List<ItemReq> itemReqList = new ArrayList<>();

    @Getter
    private double xpEarned;


    Armour(int levelReq, int itemId, List<ItemReq> itemReqList) {
        this.itemId = itemId;
        this.levelReq = levelReq;
        this.itemReqList.add(new ItemReq(ItemID.NEEDLE, 1));
        this.itemReqList.add(new ItemReq(ItemID.THREAD, 0, 1));
        this.itemReqList.addAll(itemReqList);

    }


    Armour(int itemId, int levelReq, double xpEarned, List<ItemReq> itemReqList) {
        this.itemId = itemId;
        this.levelReq = levelReq;
        this.xpEarned = xpEarned;
        this.itemReqList = itemReqList;
        this.itemReqList.add(new ItemReq(ItemID.NEEDLE, 1));
        this.itemReqList.add(new ItemReq(ItemID.THREAD, 0, 1));
    }

    // checks if we have the inventory supplies to make the item
    public boolean hasRequiredItems() {
        return this.itemReqList.stream().allMatch(ItemReq::check);
    }

    // checks if we can make the item based on level
    public boolean hasRequiredLevel() {
        return Skill.CRAFTING.getActualLevel() >= this.levelReq;
    }

    // Returns the best item we can make from the enum
    public static Optional<Armour> getBestItem() {
        return Arrays.stream(Armour.values())
                .filter(Armour::hasRequiredLevel)
                .max(Comparator.comparingInt(Armour::getLevelReq));
    }

    public void makeItem() {
        if (Bank.isOpen())
            BankManager.close(true);

        Optional<InventoryItem> needle = Query.inventory().idEquals(ItemID.NEEDLE).findClosestToMouse();
        Optional<InventoryItem> leather =
                Query.inventory().idEquals(this.getItemReqList().get(2).getId()).findFirst();

        if (needle.isEmpty() || leather.isEmpty())
            return;

        if (leather.map(l -> needle.map(n -> n.useOn(l)).orElse(false)).orElse(false) &&
                Waiting.waitUntil(3000, 75, MakeScreen::isOpen)) {
            Utils.idlePredictableAction();
        }

        if (MakeScreen.makeAll(this.itemId)) {
            Waiting.waitUntil(2500, () -> !MakeScreen.isOpen());
            if (Waiting.waitUntil(45000, Utils.random(800, 2000), () -> ChatScreen.isOpen() ||
                    !this.hasRequiredItems())) {
                Utils.idleAfkAction();
            }
        }
    }

    /* public int determineResourcesToNextItem() {
         if (Skill.CRAFTING.getActualLevel() > this.levelReq)
             return 0;
         int max = this.maxLevel;
         int xpTillMax = Skills.getXPToLevel(skill, SkillTasks.CRAFTING.getEndLevel());
         if (max <=SkillTasks.CRAFTING.getEndLevel()) {
             xpTillMax = Skills.getXPToLevel(skill, this.maxLevel);
         }
         General.println("DetermineResourcesToNextItem: " + (xpTillMax / this.xpPer));
         return (int) (xpTillMax / this.xpPer) + 5;
     }

     private static Optional<CraftItems> getCurrentItem() {
         for (CraftItems i : values()) {
             if (Skills.getActualLevel(skill) < i.maxLevel) {
                 return Optional.of(i);
             }
         }
         return Optional.empty();
     }
 */
    public static List<ItemReq> getRequiredItemList() {
        List<ItemReq> i = new ArrayList<>();
      /*  if ( getCurrentItem().isPresent()){
            Log.log("Current Item: " +getCurrentItem().get().toString());
        }
     //   getCurrentItem().ifPresent(h -> i.add(new ItemReq(ItemID.MOLTEN_GLASS, h.determineResourcesToNextItem())));
*/
        Log.info("[Armour]: We need " + i.size() + " sized list for craft items");
        Log.info("[Armour]: We need " + " sized list for herblore items: " + i.get(0).getAmount());


        i.add(new ItemReq(ItemID.GLASSBLOWING_PIPE, 1));
        return i;
    }


}
