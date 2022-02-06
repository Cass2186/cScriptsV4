package scripts.QuestPackages.HorrorFromTheDeep;

import dax.walker_engine.interaction_handling.NPCInteraction;
import org.tribot.api.General;
import org.tribot.api2007.Game;
import org.tribot.api2007.Interfaces;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Player;
import org.tribot.api2007.types.RSItem;
import scripts.*;
import scripts.QuestSteps.QuestStep;
import scripts.QuestSteps.QuestTask;
import scripts.QuestSteps.UseItemOnObjectStep;
import scripts.Requirements.ItemRequirement;
import scripts.Requirements.Requirement;
import scripts.Tasks.Priority;
import scripts.Tasks.Task;

import java.util.List;

public class LightHouseSteps implements QuestTask {


    public static void goToLightHouse(){
        if (HorrorConst.OUTSIDE_LIGHTHOUSE.contains(Player.getPosition()) ||
                (!HorrorConst.OUTSIDE_LIGHTHOUSE.contains(Player.getPosition())) &&
                        !HorrorConst.BASEMENT.contains(Player.getPosition()) &&
                        !HorrorConst.BOTTOM_OF_PIT.contains(Player.getPosition())
                        && Player.getPosition().getPlane() == 0) {
            HorrorFromTheDeep.unlockDoor();
        }
    }


    public void getBooks() {
       goToLightHouse();

        if (HorrorConst.BOTTOM_FLOOR.contains(Player.getPosition()))
            goUpStairs();

        if (HorrorConst.SECOND_FLOOR.contains(Player.getPosition()) && Inventory.find(HorrorConst.HORROR_MANUAL).length == 0) {
            if (Utils.clickObject(4617, "Search", true)) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation("Take all three books");
                Timer.waitCondition(() -> Inventory.find(HorrorConst.HORROR_JOURNAL).length > 0, 2500, 4000);
            }
        }
    }


    public void readBook(int book) {
        RSItem[] invItem1 = Inventory.find(book);
        if (invItem1.length > 0 && invItem1[0].click("Read")) {
            Timer.waitCondition(() -> Interfaces.isInterfaceSubstantiated(392, 77), 6000, 8000);
            for (int i = 0; i < 4; i++) {
                if (Interfaces.get(392, 77) != null && Interfaces.get(392, 77).click())
                    General.sleep(400, 800);
            }
        }
        // close book interface
        if (InterfaceUtil.clickInterfaceAction(392, "Close"))
            //if (Interfaces.get(392, 7) != null && Interfaces.get(392, 7).click())
            Timer.waitCondition(() -> !Interfaces.isInterfaceSubstantiated(392, 7), 2500, 4000);
    }

    public void readAllBooks() {
        readBook(HorrorConst.HORROR_JOURNAL);
        readBook(HorrorConst.HORROR_DIARY);
    }


    public void goUpStairs() {
        if (Player.getPosition().getPlane() < 2) {
            cQuesterV2.status = "Fixing Lighthouse";
            int plane = Player.getPosition().getPlane();

            if (Utils.clickObject("Staircase", "Climb-up", false))
                Timer.waitCondition(() -> Player.getPosition().getPlane() != plane, 4000, 6000);
        }
    }


    public boolean useItemOnLight(int item) {
        cQuesterV2.status = "Using item on Light";
        if (Utils.useItemOnObject(item, "Lighting mechanism"))
            return Timer.waitCondition(() -> Inventory.find(item).length < 1, 5000, 7000);

        return false;
    }


    public static void goDownStairs() {
        goToLightHouse();
        if (Player.getPosition().getPlane() != 0) {
            cQuesterV2.status = "Going to basement";
            int plane = Player.getPosition().getPlane();

            if (Utils.clickObject("Staircase", "Climb-down", false))
                Timer.waitCondition(() -> Player.getPosition().getPlane() != plane, 4000, 6000);

        } else if (HorrorConst.BOTTOM_FLOOR.contains(Player.getPosition()) ||
                HorrorConst.FIXED_GROUND_FLOOR.contains(Player.getPosition())) {
            General.println("[Debug]: Climbing down ladder");
            if (Utils.clickObject(4383, "Climb", true))
                Timer.waitCondition(() -> !HorrorConst.BOTTOM_FLOOR.contains(Player.getPosition()) &&
                        !HorrorConst.FIXED_GROUND_FLOOR.contains(Player.getPosition()), 6000, 8000);
        }
    }

    UseItemOnObjectStep fireRuneOnDoor = new UseItemOnObjectStep(ItemID.FIRE_RUNE, HorrorConst.BASEMENT_DOOR_ID,
            HorrorConst.BASEMENT_DOOR_TILE, Inventory.find(ItemID.FIRE_RUNE).length < 1, true);

    UseItemOnObjectStep airRuneOnDoor = new UseItemOnObjectStep(ItemID.AIR_RUNE, HorrorConst.BASEMENT_DOOR_ID,
            HorrorConst.BASEMENT_DOOR_TILE, Inventory.find(ItemID.AIR_RUNE).length < 1, true);

    UseItemOnObjectStep waterRuneOnDoor = new UseItemOnObjectStep(ItemID.WATER_RUNE, HorrorConst.BASEMENT_DOOR_ID,
            HorrorConst.BASEMENT_DOOR_TILE, Inventory.find(ItemID.WATER_RUNE).length < 1, true);

    UseItemOnObjectStep earthRuneOnDoor = new UseItemOnObjectStep(ItemID.EARTH_RUNE, HorrorConst.BASEMENT_DOOR_ID,
            HorrorConst.BASEMENT_DOOR_TILE, Inventory.find(ItemID.EARTH_RUNE).length < 1, true);

    UseItemOnObjectStep arrowOnDoor = new UseItemOnObjectStep(ItemID.BRONZE_ARROW, HorrorConst.BASEMENT_DOOR_ID,
            HorrorConst.BASEMENT_DOOR_TILE, Inventory.find(ItemID.BRONZE_ARROW).length < 1, true);

    UseItemOnObjectStep swordOnDoor = new UseItemOnObjectStep(ItemID.BRONZE_SWORD, HorrorConst.BASEMENT_DOOR_ID,
            HorrorConst.BASEMENT_DOOR_TILE, Inventory.find(ItemID.BRONZE_SWORD).length < 1, true);


    public void handleBasementDoor() {
        if (Player.getPosition().getPlane() == 0) {
            goDownStairs();
            cQuesterV2.status = "Using items on door";
            if (Game.getSetting(HorrorConst.GAME_SETTING) == 29421572
                    && Utils.getVarBitValue(40) == 0) {
                fireRuneOnDoor.setTileRadius(4);
                fireRuneOnDoor.useItemOnObject();
            }
            if (Game.getSetting(HorrorConst.GAME_SETTING) == 29487108 &&
                    Utils.getVarBitValue(43) == 0) {
                airRuneOnDoor.useItemOnObject();
            }
            if (Game.getSetting(HorrorConst.GAME_SETTING) == 30011396 &&
                    Utils.getVarBitValue(41) == 0) {
                waterRuneOnDoor.useItemOnObject();
            }
            if (Game.getSetting(HorrorConst.GAME_SETTING) == 30142468 &&
                    Utils.getVarBitValue(42) == 0) {
                earthRuneOnDoor.useItemOnObject();
            }
            if (Game.getSetting(HorrorConst.GAME_SETTING) == 30404612 &&
                    Utils.getVarBitValue(45) == 0) {
                arrowOnDoor.useItemOnObject();
            }
            if (Game.getSetting(HorrorConst.GAME_SETTING) == 32501764 &&
                    Utils.getVarBitValue(44) == 0) {
                swordOnDoor.useItemOnObject();
            }
        } else{
            goDownStairs();
        }
    }


    @Override
    public String toString() {
        return "(" + Game.getSetting(HorrorConst.GAME_SETTING) + ")";
    }

    @Override
    public Priority priority() {
        return Priority.HIGH;
    }

    @Override
    public boolean validate() {
        return Game.getSetting(77) == 2 &&(Game.getSetting(351) == 61442 ||
                (Game.getSetting(351) >= 29421572 &&
                        Game.getSetting(HorrorConst.GAME_SETTING) <= 32501764));
    }

    @Override
    public void execute() {
        if (Game.getSetting(351) == 61442) {
            getBooks();
            readAllBooks();
            goUpStairs();

            if (Utils.getVarBitValue(46) == 0)
                useItemOnLight(ItemID.SWAMP_TAR);

            if (Utils.getVarBitValue(48) == 0)
                useItemOnLight(ItemID.TINDERBOX);

            if (Utils.getVarBitValue(47) == 0)
                useItemOnLight(ItemID.MOLTEN_GLASS);
        } else {
            goDownStairs();
            handleBasementDoor();
        }
    }

    @Override
    public String questName() {
        return "Horror from the Deep";
    }

    @Override
    public boolean checkRequirements() {
        return true;
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
