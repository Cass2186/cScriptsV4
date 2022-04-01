package scripts.QuestPackages.TribalTotem;

import dax.walker_engine.interaction_handling.NPCInteraction;
import org.tribot.api2007.*;
import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSInterface;
import org.tribot.api2007.types.RSTile;
import org.tribot.script.sdk.Quest;
import scripts.GEManager.GEItem;
import scripts.*;
import scripts.QuestPackages.RfdGoblin.RfdGoblin;
import scripts.QuestSteps.*;
import scripts.Requirements.*;
import scripts.Tasks.Priority;
import scripts.Tasks.Task;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TribalTotem implements QuestTask {

    private static TribalTotem quest;

    public static TribalTotem get() {
        return quest == null ? quest = new TribalTotem() : quest;
    }


    ItemReq coins = new ItemReq(ItemID.COINS, 2000, 100);
    //ItemReq amuletOfGlory = new ItemReq(ollections.getAmuletOfGlories());
    ItemReq ardougneTeleports = new ItemReq(ItemID.ARDOUGNE_TELEPORT, 5, 1);
    ItemReq addressLabel = new ItemReq(ItemID.ADDRESS_LABEL);

    ItemReq totem = new ItemReq(ItemID.TOTEM);

    RSArea houseGroundFloorEntrance = new RSArea(new RSTile(2637, 3320, 0), new RSTile(2639, 3325, 0));
    RSArea houseGroundFloorMiddleRoom = new RSArea(new RSTile(2634, 3322, 0), new RSTile(2636, 3324, 0));
    RSArea houseGroundFloor = new RSArea(new RSTile(2629, 3321, 0), new RSTile(2633, 3325, 0));
    RSArea houseFirstFloor = new RSArea(new RSTile(2630, 3318, 1), new RSTile(2639, 3323, 1));


    NPCStep talkToKangaiMau = new NPCStep(NpcID.KANGAI_MAU, new RSTile(2794, 3182, 0),
            new String[]{"I'm in search of adventure!", "Ok, I will get it back.","Yes."});

    ObjectStep investigateCrate = new ObjectStep(2707, new RSTile(2650, 3273, 0), "Investigate",
            NPCInteraction.isConversationWindowUp());

    UseItemOnObjectStep useLabel = new UseItemOnObjectStep(addressLabel.getId(), 2708,
            new RSTile(2649, 3271, 0),
            NPCInteraction.isConversationWindowUp(), true);

    NPCStep talkToEmployee = new NPCStep(NpcID.GPDT_EMPLOYEE, new RSTile(2647, 3272, 0),
            new String[]{"So, when are you going to deliver this crate?"});

    NPCStep talkToCromperty = new NPCStep(NpcID.WIZARD_CROMPERTY, new RSTile(2683, 3326, 0),
            new String[]{"Chat.", "So what have you invented?", "Can I be teleported please?",
                    "Yes, that sounds good. Teleport me!"});


    ObjectStep investigateStairs = new ObjectStep(2711, new RSTile(2632, 3323, 0),
            "Investigate", NPCInteraction.isConversationWindowUp(), true);
    ObjectStep climbStairs = new ObjectStep(2711, new RSTile(2632, 3323, 0),
            "Climb-up", Player.getPosition().getPlane() == 1);

    ObjectStep openChest = new ObjectStep(2709, new RSTile(2638, 3323, 1), "Open",
            Objects.findNearest(5, 2710).length == 1);
    ObjectStep searchChest = new ObjectStep(2710, new RSTile(2638, 3323, 1), "Search",
            NPCInteraction.isConversationWindowUp(), true);
    //leaveHouse = new DetailedQuestStep( "Travel back to Brimhaven.");
    NPCStep talkToKangaiMauAgain = new NPCStep(NpcID.KANGAI_MAU, new RSTile(2794, 3182, 0),
            totem);

    SkillRequirement thievingReq = new SkillRequirement(Skills.SKILLS.THIEVING, 21);

    InventoryRequirement startInventory = new InventoryRequirement(new ArrayList<>(
            Arrays.asList(
                    new ItemReq(ItemID.COINS, 2000, 100),
                    new ItemReq(ItemID.ARDOUGNE_TELEPORT, 10, 1),
                    new ItemReq(ItemID.AMULET_OF_GLORY[2], 2, 0),
                    new ItemReq(ItemID.STAMINA_POTION[0], 2, 0)
            ))
    );

    public  ArrayList<GEItem> itemsToBuy = new ArrayList<GEItem>(
            Arrays.asList(
                    new GEItem(ItemID.ARDOUGNE_TELEPORT, 10, 50),
                    new GEItem(ItemID.AMULET_OF_GLORY[2], 2, 25),
                    new GEItem(ItemID.STAMINA_POTION[0], 3, 20)
            )
    );


    BuyItemsStep buyInitial = new BuyItemsStep(itemsToBuy);

    public boolean setLetter(String letter, int interfaceChild, int buttonNum) {
        RSInterface inter = Interfaces.get(369, interfaceChild);
        if (inter != null) {
            String s = inter.getText();
            if (!s.contains(letter)) {
                for (int i = 0; i < 50; i++) {
                    String str = s;
                    RSInterface button = Interfaces.get(369, buttonNum);
                    if (button != null && button.click()) {
                        Timer.waitCondition(() -> !str.equals(Interfaces.get(369, interfaceChild).getText()),
                                1500, 2000);
                    }
                    s = inter.getText();
                    if (s.contains(letter))
                        return true;
                }
            } else
                return true;
        }
        return false;
    }

    public void handleMansion() {
        if (!totem.check()) {
            if (houseGroundFloorEntrance.contains(Player.getPosition())) {
                if (PathingUtil.localNavigation(new RSTile(2634, 3323, 0)))
                    PathingUtil.movementIdle();
            }
            if (houseGroundFloorMiddleRoom.contains(Player.getPosition()) &&
                    Utils.clickObject(2705, "Open", false)) {
                Timer.waitCondition(() -> Interfaces.get(369) != null, 4000, 6000);
            }
            if (setLetter("K", 42, 47) &&
                    setLetter("U", 43, 49) &&
                    setLetter("R", 44, 51) &&
                    setLetter("T", 45, 53)) {
                if (InterfaceUtil.click(369, 55)) {
                    Timer.waitCondition(() -> Interfaces.get(369) == null, 4000, 6000);
                }
            }
            if (houseGroundFloorMiddleRoom.contains(Player.getPosition()) &&
                    Utils.clickObject(2705, "Open", false)) {
                Timer.waitCondition(() -> houseGroundFloor.contains(Player.getPosition()), 4000, 6000);
            } else if (houseGroundFloor.contains(Player.getPosition())) {
                investigateStairs.setHandleChat(true);
                investigateStairs.execute();
                climbStairs.execute();
            } else if (houseFirstFloor.contains(Player.getPosition())) {
                if (PathingUtil.localNavigation(new RSTile(2638, 3323, 1)))
                    PathingUtil.movementIdle();
                openChest.setTileRadius(2);
                openChest.execute();
                searchChest.execute();
            } else if (!houseFirstFloor.contains(Player.getPosition()) &&
                    !houseGroundFloor.contains(Player.getPosition()) &&
                    !houseGroundFloorMiddleRoom.contains(Player.getPosition()) &&
                    !houseGroundFloorMiddleRoom.contains(Player.getPosition()) &&
                    !houseGroundFloorEntrance.contains(Player.getPosition())) {
                cQuesterV2.status = "Talking to Wizard";
                talkToCromperty.execute();
                Timer.waitCondition(() -> houseGroundFloorEntrance.contains(Player.getPosition()), 4000, 6000);
            }
        }
    }


    @Override
    public String toString() {
        return "Tribal totem";
    }


    @Override
    public Priority priority() {
        return Priority.LOW;
    }

    @Override
    public boolean validate() {
        return thievingReq.meetsSkillRequirement() && cQuesterV2.taskList.get(0).equals(this); //change based on varbit
    }

    @Override
    public void execute() {
        if (!startInventory.check()) {
            cQuesterV2.status = "Buying & Getting items";
            buyInitial.buyItems();
            startInventory.withdrawItems();
        } else if (Game.getSetting(200) == 0) {
            cQuesterV2.status = "Going to start";
            talkToKangaiMau.execute();
        } else if (Game.getSetting(200) == 1) {
            cQuesterV2.status = "Investigating crate";
            if (!addressLabel.check())
                investigateCrate.execute();
            else {
                cQuesterV2.status = "Using label on crate";
                useLabel.setTileRadius(1);
                useLabel.useItemOnObject();
            }
        } else if (Game.getSetting(200) == 2) {
            cQuesterV2.status = "Talking to employee";
            talkToEmployee.execute();
        } else if (Game.getSetting(200) == 3) {
            cQuesterV2.status = "Talking to Wizard";
            talkToCromperty.execute();
            Timer.waitCondition(() -> houseGroundFloorEntrance.contains(Player.getPosition()), 4000, 6000);
        } else if (Game.getSetting(200) == 4) {
            cQuesterV2.status = "Navigating Mansion";
            handleMansion();
            if (totem.check()) {
                cQuesterV2.status = "Finishing quest";
                talkToKangaiMauAgain.execute();
            }
        } else if (Game.getSetting(200) == 5) {
            Utils.closeQuestCompletionWindow();
            cQuesterV2.taskList.remove(this);
        }
    }

    @Override
    public String questName() {
        return "Tribal Totem";
    }

    @Override
    public boolean checkRequirements() {
        return false;
    }


    @Override
    public List<Requirement> getGeneralRequirements() {
        return null;
    }

    @Override
    public List<ItemRequirement> getBuyList() {
        return null;
    }

    @Override
    public boolean isComplete() {
        return Quest.TRIBAL_TOTEM.getState().equals(Quest.State.COMPLETE);
    }
}
