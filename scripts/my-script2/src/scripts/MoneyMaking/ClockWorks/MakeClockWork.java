package scripts.MoneyMaking.ClockWorks;

import dax.api_lib.models.RunescapeBank;
import dax.walker_engine.interaction_handling.NPCInteraction;
import lombok.Getter;
import org.tribot.api2007.Game;
import org.tribot.api2007.Player;
import org.tribot.api2007.types.RSItem;
import org.tribot.script.sdk.Inventory;
import org.tribot.script.sdk.Skill;
import org.tribot.script.sdk.Waiting;
import org.tribot.script.sdk.query.Query;
import org.tribot.script.sdk.types.InventoryItem;
import org.tribot.script.sdk.types.Npc;
import org.tribot.script.sdk.walking.LocalWalking;
import scripts.*;
import scripts.QuestPackages.DragonSlayer.DragonSlayer;
import scripts.QuestSteps.QuestTask;
import scripts.Requirements.ItemRequirement;
import scripts.Requirements.Requirement;
import scripts.Tasks.Priority;

import java.util.List;
import java.util.Optional;

public class MakeClockWork implements QuestTask {

    private static MakeClockWork quest;

    public static MakeClockWork get() {
        return quest == null ? quest = new MakeClockWork() : quest;
    }

    @Getter
    private static int clockworks = 0;
    private String[] chatResponses = {"Yes", "Clockwork mechanism", "Okay, here's 10,000 coins."};
    private final int AMOUNT_INTERFACE = 162;

    @Override
    public Priority priority() {
        return Priority.LOW;

    }

    @Override
    public boolean validate() {
        return cQuesterV2.taskList.get(0).equals(this) &&
                Skill.CRAFTING.getActualLevel() >= 8 && Skill.CONSTRUCTION.getActualLevel() >= 25;// &&
               // hasRequiredItems();
    }

    @Override
    public void execute() {
        RSItem[] bars = org.tribot.api2007.Inventory.find(ItemID.STEEL_BAR);
        if (Game.isInInstance() ) {
            for (RSItem b : bars) {
                int exp = Skill.CRAFTING.getXp();
                if (Utils.clickObject("Clockmaker's bench", "Craft", false)) {
                    Timer.waitCondition(()-> NPCInteraction.isConversationWindowUp(), 3000,5000);
                    Waiting.waitNormal(150,33);
                    if (NPCInteraction.isConversationWindowUp())
                        Keyboard.typeString("1");
                    //NPCInteraction.handleConversation(chatResponses);
                    Timer.waitCondition(() -> Skill.CRAFTING.getXp() > exp, 3000, 4000);
                    Waiting.waitNormal(150,33);
                }
                if (org.tribot.api2007.Inventory.find(ItemID.STEEL_BAR).length == 0)
                    break;
            }
        }

         if (bars.length == 0){
            bankBars();
        }
    }

    @Override
    public String questName() {
        return "Money Making";
    }

    @Override
    public boolean checkRequirements() {
        return Skill.CRAFTING.getActualLevel() >= 8 && Skill.CONSTRUCTION.getActualLevel() >= 25 &&
                hasRequiredItems();
    }


    public static boolean hasRequiredItems() {
        return Inventory.getCount(ItemID.COINS_995) > 367 && ((Inventory.getCount(ItemID.STEEL_BAR) > 0)
                || ClockWorkVars.useButler && Inventory.getCount(ItemID.STEEL_BAR + 1) > 0);
    }


    boolean needsButler() {
        return (Inventory.contains(ItemID.STEEL_BAR + 1) && !Inventory.contains(ItemID.STEEL_BAR)) ||
                (Inventory.isFull() && !Inventory.contains(ItemID.STEEL_BAR));
    }
    public void leaveHouse() {
        if (Game.isInInstance() && Utils.clickObject(4525, "Enter", false)) {
            Timer.waitCondition(() -> !Game.isInInstance() &&
                            Query.npcs().nameContains("Phials").stream().findFirst().isPresent(),
                    7000, 9000);

        }
    }

    private void bankBars(){
        if (!Inventory.contains(ItemID.STEEL_BAR)) {
            PathingUtil.walkToTile(RunescapeBank.VARROCK_WEST.getPosition());
            BankManager.open(true);
            BankManager.depositAllExcept(ItemID.VARROCK_TELEPORT,
                    ItemID.TELEPORT_TO_HOUSE, ItemID.COINS);
            BankManager.withdraw(0, true, ItemID.STEEL_BAR);
            BankManager.close(true);
            BankManager.close(true);
            RSItem[] tele = org.tribot.api2007.Inventory.find(ItemID.TELEPORT_TO_HOUSE);
            if (tele.length > 0 && tele[0].click("Break")) {
                Waiting.waitNormal(5000, 500);
            }
        }
    }

    public void unnoteBars() {
        Optional<InventoryItem> item = Query.inventory().nameContains("steel bar").isNoted().findFirst();
        Optional<Npc> phials = Query.npcs().nameContains("Phials").stream().findFirst();
        if (phials.isPresent() && item.isPresent()) {
            if (!phials.get().isVisible()){
                LocalWalking.walkTo(phials.get().getTile().translate(0,1));
                Waiting.waitNormal(750,220);
            }
            if(Utils.useItemOnNPC(item.get().getId(), phials.get().getId()) &&
                    Timer.waitCondition(Player::isMoving, 1200, 1800)) {
                Timer.waitCondition(NPCInteraction::isConversationWindowUp, 6000, 8000);

            }
        }
        if (NPCInteraction.isConversationWindowUp()) {
            InterfaceUtil.clickInterfaceText(219,1, "Exchange All");
            Timer.waitCondition(()-> Query.inventory().nameContains("Steel bar").isNotNoted().findFirst().isPresent(),
                    2000,4000);
        }
    }

    @Override
    public List<Requirement> getGeneralRequirements() {
        return null;
    }

    @Override
    public List<ItemRequirement> getBuyList() {
        return null;
    }

}
