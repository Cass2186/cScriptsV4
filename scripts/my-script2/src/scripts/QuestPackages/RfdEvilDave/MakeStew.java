package scripts.QuestPackages.RfdEvilDave;

import org.tribot.api2007.NPCChat;
import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSTile;
import org.tribot.script.sdk.ChatScreen;
import org.tribot.script.sdk.GameState;
import org.tribot.script.sdk.Inventory;
import org.tribot.script.sdk.Log;
import org.tribot.script.sdk.types.Area;
import org.tribot.script.sdk.types.WorldTile;
import scripts.ItemID;
import scripts.NpcID;
import scripts.ObjectID;
import scripts.QuestSteps.*;
import scripts.Requirements.AreaRequirement;
import scripts.Requirements.ItemRequirement;
import scripts.Requirements.Requirement;
import scripts.Tasks.Priority;
import scripts.Varbits;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class MakeStew implements QuestTask {

    QuestStep catchRats, enterBasement, restart;

    UseItemOnNpcStep useStewOnEvilDave;

    Requirement inEvilDaveRoom;

    Area evilDaveRoom = Area.fromRectangle(new WorldTile(3068, 9874, 0),
            new WorldTile(3086, 9904, 0));

    ItemRequirement redSpice, orangeSpice, brownSpice, yellowSpice, evilStewHighlighted, evilStew, stew;


    public void onGameTick() {
        //update varbits
    }





    public int getRedNeeded(){
        int redNeeded = GameState.getVarbit(1883);
        int redInStew = GameState.getVarbit(Varbits.SPICY_STEW_RED_SPICES.getId());
        int numRedStillNeeded = redNeeded - redInStew;
        Log.info("Red Needed: " + numRedStillNeeded);
        return numRedStillNeeded;
    }

    public int getYellowNeeded(){
        int yellowNeeded = GameState.getVarbit(1884);
        int yellowInStew = GameState.getVarbit(Varbits.SPICY_STEW_YELLOW_SPICES.getId());
        int numYellowStillNeeded = yellowNeeded - yellowInStew;
        Log.info("Yellow Needed: " + numYellowStillNeeded);
        return numYellowStillNeeded;
    }
    public int getBrownNeeded(){
        int brownNeeded = GameState.getVarbit(1885);
        int brownInStew = GameState.getVarbit(Varbits.SPICY_STEW_BROWN_SPICES.getId());
        int numBrownStillNeeded = brownNeeded - brownInStew;
        Log.info("Brown Needed: " + numBrownStillNeeded);
        return numBrownStillNeeded;
    }
    public int getOrangeNeeded(){
        int orangeInStew = GameState.getVarbit(Varbits.SPICY_STEW_ORANGE_SPICES.getId());
        int orangeNeeded = GameState.getVarbit(1886);
        int numOrangeStillNeeded = orangeNeeded - orangeInStew;
        Log.info("Orange Needed: " + numOrangeStillNeeded);
        return numOrangeStillNeeded;
    }

    protected void setupSteps() {
        setupRequirements();
        setupConditions();

        catchRats = new DetailedQuestStep( "Have your cat catch Hell-Rats for spices, and add them " +
                "to your stew to match the required quantities.");

        restart = new ClickItemStep(ItemID.SPICY_STEW, "Eat",
                !Inventory.contains(ItemID.SPICY_STEW), evilStewHighlighted);

        enterBasement = new ObjectStep( ObjectID.TRAPDOOR_12267, new RSTile(3077, 3493, 0),
                "Go back down to Evil Dave.");
        ((ObjectStep) enterBasement).addAlternateObjects(ObjectID.OPEN_TRAPDOOR);

        useStewOnEvilDave = new UseItemOnNpcStep(ItemID.SPICY_STEW,
               4806, new RSTile(3080, 9889, 0), ChatScreen.isOpen());
    }

    protected void setupRequirements() {
        redSpice = new ItemRequirement("Red spice", ItemID.RED_SPICE_1);
        redSpice.addAlternateItemID(ItemID.RED_SPICE_2, ItemID.RED_SPICE_3, ItemID.RED_SPICE_4);

        orangeSpice = new ItemRequirement("Orange spice", ItemID.ORANGE_SPICE_1);
        orangeSpice.addAlternateItemID(ItemID.ORANGE_SPICE_2, ItemID.ORANGE_SPICE_3, ItemID.ORANGE_SPICE_4);

        yellowSpice = new ItemRequirement("Yellow spice", ItemID.YELLOW_SPICE_1);
        yellowSpice.addAlternateItemID(ItemID.YELLOW_SPICE_2, ItemID.YELLOW_SPICE_3, ItemID.YELLOW_SPICE_4);

        brownSpice = new ItemRequirement("Brown spice", ItemID.BROWN_SPICE_1);
        brownSpice.addAlternateItemID(ItemID.BROWN_SPICE_2, ItemID.BROWN_SPICE_3, ItemID.BROWN_SPICE_4);


        evilStew = new ItemRequirement("Spicy stew", ItemID.SPICY_STEW);
        evilStewHighlighted = new ItemRequirement("Spicy stew", ItemID.SPICY_STEW);


        stew = new ItemRequirement("Stew", ItemID.STEW);
    }


    public void setupConditions() {
        inEvilDaveRoom = new AreaRequirement(evilDaveRoom);
    }

    public Collection<QuestStep> getSteps() {
        return Arrays.asList(catchRats, enterBasement, useStewOnEvilDave);
    }

    public List<QuestStep> getDisplaySteps() {
        return Arrays.asList(catchRats, useStewOnEvilDave);
    }

    @Override
    public Priority priority() {
        return Priority.LOW;
    }

    @Override
    public boolean validate() {
        return false;
    }

    @Override
    public void execute() {

    }

    @Override
    public String questName() {
        return null;
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
        return false;
    }
}
