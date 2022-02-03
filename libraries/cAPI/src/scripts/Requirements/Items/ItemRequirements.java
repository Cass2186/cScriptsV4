package scripts.Requirements.Items;

import lombok.Getter;
import org.tribot.api2007.types.RSItem;
import org.tribot.script.sdk.interfaces.Item;
import scripts.QuestUtil;
import scripts.Requirements.ItemRequirement;
import scripts.Requirements.Util.LogicType;

import java.util.*;
import java.util.function.Predicate;

public class ItemRequirements extends ItemRequirement {
    @Getter
    ArrayList<ItemRequirement> itemRequirements = new ArrayList<>();

    @Getter
    LogicType logicType;

    public ItemRequirements(ItemRequirement... requirements)
    {
        this("", requirements);
    }

    public ItemRequirements(String name, ItemRequirement... itemRequirements)
    {
        super(name, itemRequirements[0].getId(), -1);
        this.itemRequirements.addAll(Arrays.asList(itemRequirements));
        this.logicType = LogicType.AND;
    }

    public ItemRequirements(LogicType logicType, String name, ItemRequirement... itemRequirements)
    {
        super(name, itemRequirements[0].getId(), -1);
        this.itemRequirements.addAll(Arrays.asList(itemRequirements));
        this.logicType = logicType;
    }

    public ItemRequirements(LogicType logicType, String name, List<ItemRequirement> itemRequirements)
    {
        super(name, itemRequirements.get(0).getId(), -1);
        this.itemRequirements.addAll(itemRequirements);
        this.logicType = logicType;
    }

    public ItemRequirements(LogicType logicType, ItemRequirement... requirements)
    {
        this(logicType, "", requirements);
    }

    @Override
    public boolean isActualItem() {
        return LogicType.OR.test(getItemRequirements().stream(), item -> !item.getAllIds().contains(-1)
                && item.getAmount() >= 0);
    }




    public boolean check( boolean checkConsideringSlotRestrictions) {
        Predicate<ItemRequirement> predicate = r -> r.check( checkConsideringSlotRestrictions);
        int successes = (int) itemRequirements.stream().filter(Objects::nonNull).filter(predicate).count();
        return logicType.compare(successes, itemRequirements.size());
    }

    @Override
    public boolean check( boolean checkConsideringSlotRestrictions, List<RSItem> items)
    {
        Predicate<ItemRequirement> predicate = r -> r.check( checkConsideringSlotRestrictions, items);
        int successes = (int) itemRequirements.stream().filter(Objects::nonNull).filter(predicate).count();
        return logicType.compare(successes, itemRequirements.size());
    }






    public ItemRequirement copy() {
        ItemRequirements newItem = new ItemRequirements(getLogicType(), getItemName(), getItemRequirements());
        newItem.addAlternateItemID(getAlternateItems());
        newItem.setExclusiveToOneItemType(exclusiveToOneItemType);
        newItem.setDisplayMatchedItemName(isDisplayMatchedItemName());
        newItem.setConditionToHide(getConditionToHide());
        newItem.logicType = logicType;

        return newItem;
    }

    @Override
    public List<Integer> getAllIds()
    {
        return itemRequirements.stream()
                .map(ItemRequirement::getAllIds)
                .flatMap(Collection::stream)
                .collect(QuestUtil.collectToArrayList());
    }

    @Override
    public boolean checkBank(){
        return logicType.test(getItemRequirements().stream(), item -> item.checkBank() ||
                item.check(false));
    }
}