package scripts.Tasks.Construction;

import dax.walker.utils.AccurateMouse;
import dax.walker_engine.interaction_handling.NPCInteraction;
import org.tribot.api.General;
import org.tribot.api2007.*;
import org.tribot.api2007.ext.Filters;
import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSInterface;
import org.tribot.api2007.types.RSObject;
import org.tribot.api2007.types.RSTile;
import org.tribot.script.sdk.Log;
import org.tribot.script.sdk.MyPlayer;
import org.tribot.script.sdk.Waiting;
import org.tribot.script.sdk.query.Query;
import org.tribot.script.sdk.types.GameObject;
import scripts.*;
import scripts.API.Priority;
import scripts.API.Task;
import scripts.Data.SkillTasks;
import scripts.Data.Vars;
import scripts.EntitySelector.Entities;
import scripts.EntitySelector.finders.prefabs.ObjectEntity;
import scripts.Requirements.ItemRequirement;
import scripts.Tasks.Construction.ConsData.Furniture;
import scripts.Tasks.Construction.ConsData.House;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;
import java.util.Optional;

public class MakeFurniture implements Task {

    private int stopLevel;
    private List<ItemRequirement> itemRequirements;
    private Furniture furniture;

    public static final RSArea RIMMINGTON = new RSArea(new RSTile(2944, 3201, 0), new RSTile(2961, 3225, 0));
    private final int CONSTRUCTION_MASTER = 458;

    public static EnumSet<Furniture> furnitureSet = EnumSet.noneOf(Furniture.class);



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
                //do stuff
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

        // extremely short wait in case we are still animating,
        // but briefly went -1 on the previous itteration
        if (Waiting.waitUntil(120,()-> MyPlayer.getAnimation() != -1)){
            Log.debug("Still animating, waiting");
            Waiting.waitUntil(1500,()-> MyPlayer.getAnimation() == -1);
            return;
        }
        RSObject[] furnitureObject =
                org.tribot.api2007.Objects.findNearest(30, furniture.getObjectName());

        RSObject[] furnitureSpace =
                org.tribot.api2007.Objects.findNearest(30, furniture.getSpaceId());

        if (furnitureObject.length > 0){
            Log.debug("Removing furniture");
            removeFurniture(furniture.getObjectName());
        }

       else if (furnitureSpace.length > 0) {
            if (Inventory.find(furniture.getPlankId()).length >= furniture.getPlankNum()) {
                if (House.isViewerOpen())
                    House.closeViewer();

                Optional<GameObject> gameObj = Query.gameObjects()
                        .idEquals(furniture.getSpaceId())
                        .actionContains("Build")
                        .findBestInteractable();
                if (gameObj.map(o->o.interact("Build")).orElse(false)){
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
                        Timer.slowWaitCondition(() -> Player.getAnimation() == -1, 5500, 7250);
                }
            }
        }else {
            Log.info("Need to build rooms");
            House.buildRoom(furniture.getRoom());
        }
    }


    public void enterHouse() {
        if (!Game.isInInstance()) {
            RSObject obj = Entities.find(ObjectEntity::new)
                    .actionsContains("Build mode")
                    .getFirstResult();
            if (obj != null && Utils.clickObject(obj, "Build mode")) {
                Timer.slowWaitCondition(() -> Game.isInInstance(), 6500, 7000);
                Waiting.waitNormal(2900,250);
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

        } else {
            Log.debug("Making larder");
            buildFurniture(FURNITURE.OAK_LARDER);
        }

    }

    @Override
    public String taskName() {
        return "Construction";
    }

    private void trainConstruction(Furniture furniture) {
        this.furniture = furniture;
        RSObject[] furnitureObject = org.tribot.api2007.Objects.findNearest(25, furniture.getObjectIds());
        if (furnitureObject.length > 0) {
            if (House.isViewerOpen())
                House.closeViewer();
            else if (!furnitureObject[0].isClickable()) {
                if (Walking.blindWalkTo(furnitureObject[0].getPosition()))
                    Waiting.waitUntil(() -> furnitureObject[0].isOnScreen());
            }
        }// else
           // House.buildRoom(furniture.getRoom());
    }
}
