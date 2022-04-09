package scripts.Tasks.Construction;

import dax.api_lib.DaxWalker;
import dax.walker.utils.AccurateMouse;
import org.tribot.api.General;
import org.tribot.api2007.*;
import org.tribot.api2007.Objects;
import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSItem;
import org.tribot.api2007.types.RSObject;
import org.tribot.api2007.types.RSTile;
import org.tribot.script.sdk.Bank;
import org.tribot.script.sdk.Inventory;
import org.tribot.script.sdk.Waiting;
import org.tribot.script.sdk.query.Query;
import org.tribot.script.sdk.types.GameObject;
import org.tribot.script.sdk.types.WorldTile;
import scripts.*;
import scripts.Requirements.ItemRequirement;
import scripts.Tasks.Construction.ConsData.Furniture;
import scripts.Tasks.Construction.ConsData.House;
import scripts.Tasks.Construction.ConsData.Plank;
import scripts.Tasks.Construction.ConsData.Room;
import scripts.Tasks.Priority;
import scripts.Tasks.Task;

import java.util.*;

public class Construction implements Task {

    private int stopLevel;
    private List<ItemRequirement> itemRequirements;
    private Furniture furniture;

    public static final RSArea RIMMINGTON = new RSArea(new RSTile(2944, 3201, 0), new RSTile(2961, 3225, 0));
    private final int CONSTRUCTION_MASTER = 458;

    public static EnumSet<Furniture> furnitureSet = EnumSet.noneOf(Furniture.class);

    public Construction(int stopLevel) {
        this.stopLevel = stopLevel;
        itemRequirements = Furniture.getItemRequirements(stopLevel, furnitureSet);
        furnitureSet.addAll(Arrays.asList(Furniture.OAK_LARDER, Furniture.OAK_ARMCHAIR, Furniture.OAK_CHAIR, Furniture.WOODEN_CHAIR, Furniture.CRUDE_WOODEN_CHAIR));
    }

    public Construction(List<Furniture> furnitureList, int stopLevel) {
        this.stopLevel = stopLevel;
        this.furnitureSet.addAll(furnitureList);
        itemRequirements = Furniture.getItemRequirements(stopLevel, furnitureSet);
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
        Optional<Furniture> currentFurniture = Furniture.getBestFurniture(furnitureSet);
        if (Banking.isInBank()) {
            General.println("[Construction]: In bank");
            if (Banking.isBankScreenOpen())
                Bank.close();
            //    else if (Spell.TELEPORT_TO_HOUSE.canCast() && Spell.TELEPORT_TO_HOUSE.cast())
            //  Waiting.waitUntil();(() -> House.isInHouse());

        } else if (RIMMINGTON.contains(Player.getPosition())) {
            if (furniture != null && !furniture.getPlankType().equals(Plank.REGULAR)) {
                // Inventory.drop(ItemList.getIds(Arrays.asList(Plank.REGULAR.getItemRequirements())));
            }
            if (Inventory.isFull() || getNotedPlanksId() == -1) {
                Optional<GameObject> bestInteractable = Query.gameObjects()
                        .tileEquals(new WorldTile(2953, 3224, 0))
                        .idEquals(15478)
                        .findBestInteractable();

                if (Utils.clickObj(bestInteractable, "Build mode"))
                    Waiting.waitUntil(House::isInHouse);
            } else {
               currentFurniture.ifPresent(f-> UnnotePlanks.unnotePlanks(f.getPlankType().getPlankId()));
            }
        } else if (House.isInHouse()) {
                currentFurniture = Furniture.getBestFurniture(furnitureSet);
            if (currentFurniture.isPresent()) {
                if (!House.isBuildingModeOn()) {
                    House.enableBuildingMode(true);
                }
                else
                    trainConstruction(currentFurniture.get());
            } else {
                if (getNotedPlanksId() == -1) {
                    General.println("Didn't find noted planks in inventory. Going to visit a bank");
                    if (House.isOptionsOpen())
                        House.closeOptions();
                    else
                        DaxWalker.walkToBank();
                } else if (House.leaveHouse())
                    Waiting.waitUntil(() -> RIMMINGTON.contains(Player.getPosition()));
            }
        }
    }

    private void trainConstruction(Furniture furniture) {
        this.furniture = furniture;
        RSObject[] furnitureObject = org.tribot.api2007.Objects.findNearest(25, furniture.getObjectIds());
        if (furnitureObject.length > 0) {
            if (Interfaces.isInterfaceSubstantiated(CONSTRUCTION_MASTER)) {
                Keyboard.typeKeys(furniture.getBuildKey());
                Waiting.waitNormal(100, 15);
                if (Inventory.getCount(furniture.getPlankType().getItemRequirements()[0].getId()) < furniture.getNeededPlanks())
                    House.openOptions();
                Waiting.waitUntil(General.random(10000, 12000), () -> objectDisappeared(furnitureObject[0]));
            } else if (NPCChat.getOptions() != null) {
                Keyboard.typeKeys('1');
                Waiting.waitUntil(() -> objectDisappeared(furnitureObject[0]));
            } else if (House.isViewerOpen())
                House.closeViewer();
            else if (!furnitureObject[0].isClickable()) {
                if (Walking.blindWalkTo(furnitureObject[0].getPosition()))
                    Waiting.waitUntil(() -> furnitureObject[0].isOnScreen());
            } else if (AccurateMouse.click(furnitureObject[0], "Build", "Remove")) {
                Waiting.waitUntil(() -> NPCChat.getOptions() != null || Interfaces.isInterfaceSubstantiated(CONSTRUCTION_MASTER));
            }
        } else
            House.buildRoom(furniture.getRoom());
    }


    public String getStatus() {
        return "Construction lvl " + Skills.SKILLS.CONSTRUCTION.getActualLevel();
    }


    public boolean isCompleted() {
        return Skills.SKILLS.CONSTRUCTION.getActualLevel() >= stopLevel;
    }

    @Override
    public String toString() {
        return furniture != null ? "Making " + furniture.toString() : "-";
    }

    public boolean isActive() {
        return (House.isInHouse() || RIMMINGTON.contains(Player.getPosition())) && getNotedPlanksId() != -1;
    }


    public List<ItemRequirement> getItemRequirements() {
        return itemRequirements;
    }


    private int getNotedPlanksId() {
        return Arrays.stream(org.tribot.api2007.Inventory.getAll())
                .filter(item -> Utils.isItemNoted(item.getID()))
                // .filter(item -> Utils.itemNameContains(item, Furniture.getCurrentPlank(furnitureSet).itemRequirements[0].getName()))
                .map(RSItem::getID)
                .findFirst()
                .orElse(-1);
    }

    private boolean objectDisappeared(RSObject object) {
        return org.tribot.api2007.Objects.findNearest(1, object.getID()).length == 0;
    }

    private void typeKey(List<String> options) {
        Collections.reverse(options);
        for (int i = 0; i < options.size(); i++) {
            if (options.get(i).contains(" coins")) {
                Keyboard.typeKeys((char) (options.size() - i + 48));
                return;
            }
        }
    }

}
