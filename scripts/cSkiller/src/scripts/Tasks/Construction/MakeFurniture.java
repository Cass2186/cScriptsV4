package scripts.Tasks.Construction;

import dax.walker_engine.interaction_handling.NPCInteraction;
import javafx.scene.paint.Stop;
import lombok.Getter;
import org.tribot.api.General;
import org.tribot.api2007.*;
import org.tribot.api2007.ext.Filters;
import org.tribot.api2007.types.RSInterface;
import org.tribot.api2007.types.RSObject;
import org.tribot.script.sdk.Log;
import org.tribot.script.sdk.Waiting;
import scripts.*;
import scripts.API.Priority;
import scripts.API.Task;
import scripts.Data.SkillTasks;
import scripts.Data.Vars;
import scripts.EntitySelector.Entities;
import scripts.EntitySelector.finders.prefabs.ObjectEntity;

public class MakeFurniture implements Task {


    int PARENT_INTERFACE = 458;
    int OAK_LARDER_ID = 13566;
    int LARDER_SPACE = 15403;
    int OAK_PLANK = 8778;
    int HOUSE_TAB = 8013;
    int MAHOGANY_PLANK = 8782;
    int HAMMER = 2347;
    int SAW = 8794;
    String larderSpace = "Larder space";
    int houseVarbit1 = 2187;
    int houseVarbit2 = 2188;

    int addRoomInterface = 422;
    int selectRoomInterface = 212;
    int roomViewerInterface = 370;

    public Timer butlerTimer = new Timer(General.random(15500, 18500));


    public boolean openHouseOptions() {
        GameTab.open(GameTab.TABS.OPTIONS);
        if (InterfaceUtil.clickInterfaceAction(116, "View House Options")) {
            Timer.waitCondition(() -> Interfaces.isInterfaceSubstantiated(370, 5), 3000, 5000);
        }
        return Interfaces.isInterfaceSubstantiated(370, 5);
    }

    public void makeRoom(String roomName) {
        if (openHouseOptions()){
            Log.log("[Debug]: Making room");
            RSInterface viewButton = Interfaces.findWhereAction("Viewer", roomViewerInterface);
            if (viewButton != null && viewButton.click()){
                 }
            RSInterface addRoom = Interfaces.get(addRoomInterface, 5, 12);
            Log.log("[Debug]: Adding room");
            if (addRoom != null && addRoom.click(rsMenuNode ->  rsMenuNode.contains("Add room"))){
                Timer.waitCondition(()-> Interfaces.isInterfaceSubstantiated(selectRoomInterface),
                        3500,5000);

            }
            RSInterface roomInter = Interfaces.findWhereAction(roomName, selectRoomInterface);
            if (roomInter != null && roomInter.click()){
                Timer.waitCondition(()-> Interfaces.isInterfaceSubstantiated(addRoomInterface), 3500,5000);

            }
            roomInter = Interfaces.findWhereAction("Done", addRoomInterface);
            if (roomInter != null && roomInter.click()){
                Waiting.waitNormal(7500,750);
            }
        }
    }

    public boolean setBuildMode() {
        RSObject[] obj = Objects.findNearest(20, Filters.Objects.nameContains("space"));
        if (obj.length > 0)
            return true;

        Log.log("[Debug]: Turning on building mode");
        GameTab.open(GameTab.TABS.OPTIONS);
        if (InterfaceUtil.clickInterfaceAction(116, "View House Options")) {
            Timer.waitCondition(() -> Interfaces.isInterfaceSubstantiated(370, 5), 3000, 5000);
        }
        if (Interfaces.isInterfaceSubstantiated(370, 5)) {
            if (Interfaces.get(370, 5).getTextureID() == 699)
                return true;

            else if (Interfaces.get(370, 5).getTextureID() == 697
                    && Interfaces.get(370, 5).click()) {
                return Timer.waitCondition(() -> Game.isInBuildingMode(), 5000, 7000);

            }
        }
        return Game.isInBuildingMode();
    }

    public void removeFurniture(String objectName) {
        RSObject obj = Entities.find(ObjectEntity::new)
                .nameContains(objectName)
                .actionsContains("Remove")
                .getFirstResult();

        if (obj != null && Utils.clickObject(obj, "Remove")) {
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation("Yes");
        }
    }

    public void buildFurniture(FURNITURE furniture) {
        if (Inventory.find(furniture.getPlankId()).length >= furniture.getPlankNum()) {
            removeFurniture(furniture.getObjectName());

            RSObject obj = Entities.find(ObjectEntity::new)
                    .idEquals(furniture.getSpaceId())
                    .actionsContains("Build")
                    .getFirstResult();

            if (obj != null && Utils.clickObject(obj, "Build")) {
                Timer.quickWaitCondition(() -> Interfaces.isInterfaceSubstantiated(PARENT_INTERFACE), 4500, 6000);
                Utils.idlePredictableAction();
            } else {
                //Log.log("[Debug]: Force closing interfaces");
                RSInterface close = Interfaces.findWhereAction("Close", PARENT_INTERFACE);
                if (close != null && close.click()) {
                    Timer.quickWaitCondition(() -> !Interfaces.isInterfaceSubstantiated(PARENT_INTERFACE), 4500, 6000);
                    Utils.idlePredictableAction();
                }
            }
            if (Interfaces.isInterfaceSubstantiated(PARENT_INTERFACE)) {
                Keyboard.typeString(furniture.getKeyString());
                if (Timer.slowWaitCondition(() -> Player.getAnimation() != -1, 4500, 6000))
                    Timer.slowWaitCondition(() -> Player.getAnimation() == -1, 4500, 6000);
            }
        }
    }


    public void enterHouse() {
        if (!Game.isInInstance()) {
            RSObject obj = Entities.find(ObjectEntity::new)
                    .actionsContains("Build mode")
                    .getFirstResult();
            if (obj != null && Utils.clickObject(obj, "Build mode")) {
                Timer.slowWaitCondition(() -> Game.isInInstance(), 6500, 7000);
            }
        }
    }


    @Override
    public String toString() {
        return "Building Furniture";
    }

    @Override
    public Priority priority() {
        return Priority.LOW;
    }

    @Override
    public boolean validate() {
        if (Vars.get().currentTask != null && Vars.get().currentTask.equals(SkillTasks.CONSTRUCTION)) {
            if (Skills.getActualLevel(Skills.SKILLS.CONSTRUCTION) < FURNITURE.WOODEN_CHAIR.getReqLevl()) {
                return Inventory.find(FURNITURE.CRUDE_CHAIR.getPlankId()).length >= FURNITURE.CRUDE_CHAIR.getPlankNum();

            } else if (Skills.getActualLevel(Skills.SKILLS.CONSTRUCTION) < FURNITURE.BOOKCASE.getReqLevl()) {
                return Inventory.find(FURNITURE.WOODEN_CHAIR.getPlankId()).length >= FURNITURE.WOODEN_CHAIR.getPlankNum();

            } else if (Skills.getActualLevel(Skills.SKILLS.CONSTRUCTION) < FURNITURE.OAK_TABLE.getReqLevl()) {
                return Inventory.find(FURNITURE.BOOKCASE.getPlankId()).length >= FURNITURE.BOOKCASE.getPlankNum();

            } else if (Skills.getActualLevel(Skills.SKILLS.CONSTRUCTION) < FURNITURE.OAK_LARDER.getReqLevl()) {
                return Inventory.find(FURNITURE.OAK_TABLE.getPlankId()).length >= FURNITURE.OAK_TABLE.getPlankNum();

            } else
                return Inventory.find(FURNITURE.OAK_LARDER.getPlankId()).length >= FURNITURE.OAK_LARDER.getPlankNum();

        }
        return false;
    }

    @Override
    public void execute() {
        enterHouse();

        if (Skills.getActualLevel(Skills.SKILLS.CONSTRUCTION) < FURNITURE.WOODEN_CHAIR.getReqLevl()) {
            buildFurniture(FURNITURE.CRUDE_CHAIR);

        } else if (Skills.getActualLevel(Skills.SKILLS.CONSTRUCTION) < FURNITURE.BOOKCASE.getReqLevl()) {
            buildFurniture(FURNITURE.WOODEN_CHAIR);
            ;

        } else if (Skills.getActualLevel(Skills.SKILLS.CONSTRUCTION) < FURNITURE.OAK_TABLE.getReqLevl()) {
            buildFurniture(FURNITURE.BOOKCASE);

        } else if (Skills.getActualLevel(Skills.SKILLS.CONSTRUCTION) < FURNITURE.OAK_LARDER.getReqLevl()) {
            buildFurniture(FURNITURE.OAK_TABLE);

        } else
            buildFurniture(FURNITURE.OAK_LARDER);

    }

    @Override
    public String taskName() {
        return "Construction";
    }
}
