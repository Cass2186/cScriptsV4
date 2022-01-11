package scripts.Tasks.Firemaking;

import dax.walker_engine.interaction_handling.NPCInteraction;
import org.tribot.api.General;
import org.tribot.api2007.*;
import org.tribot.api2007.types.RSItem;
import org.tribot.api2007.types.RSObject;
import org.tribot.api2007.types.RSTile;
import scripts.API.Priority;
import scripts.API.Task;
import scripts.*;
import scripts.Data.SkillTasks;
import scripts.Data.Vars;

public class MakeFires implements Task {

    /********
     * level 1-15 = 61 logs (40xp/ea)
     * level 15-30 = 183 oak logs (60xp/ea)
     * level 30-45 = 533 willow logs (90xp/ea)
     * level 45-50 = 295 maple logs (135xp/ea)
     */

    public String message;

    public boolean shouldReset() {
        RSObject[] fires = fires = Objects.findNearest(1, 26185);
        for (int i = 0; i < fires.length; i++) {
            if (fires[i].getPosition().equals(Player.getPosition())) {
                General.println("[Debug]: Moving to an new start location");
                return true;
            }
        }
        if (FireMakingAreas.GE_WEST_WALL.contains(Player.getPosition()) ||
                FireMakingAreas.GE_BOOTH_AREA.contains(Player.getPosition())) {
            return true;
        }
        if (Vars.get().shouldResetFireMaking) {
            Vars.get().shouldResetFireMaking = false;
            return true;
        }
        return false;
    }

    public boolean isItemSelected(int itemID) {
        return Game.getItemSelectionState() == 1;
    }

    public static int getCurrentLogID(){
        if (Skills.getActualLevel(Skills.SKILLS.FIREMAKING) <15){
            return ItemId.LOG_IDS[0];
        } else  if (Skills.getActualLevel(Skills.SKILLS.FIREMAKING) <30){
            return ItemId.OAK_LOGS;
        }else  if (Skills.getActualLevel(Skills.SKILLS.FIREMAKING) <45){
            return ItemId.WILLOW_LOGS;
        }else  if (Skills.getActualLevel(Skills.SKILLS.FIREMAKING) <60){
            return ItemId.MAPLE_LOGS;
        }else  if (Skills.getActualLevel(Skills.SKILLS.FIREMAKING) <99){
            return ItemId.YEW_LOGS;
        }
        return ItemId.LOG_IDS[0];
    }

    public void lightFire(int logIndex) {
        RSItem[] t = Inventory.find(ItemId.TINDERBOX);
        RSItem[] invLogs = Inventory.find(ItemId.LOG_IDS[logIndex]);
        int invSize = Inventory.getAll().length;

        if (invLogs.length > 0 && t.length > 0) {
            RSTile startPosition = Player.getPosition();

            if (shouldReset())
                if (General.random(0, 10) > 6)
                    PathingUtil.walkToArea(FireMakingAreas.STARTING_AREA_1, false);
                else
                    PathingUtil.walkToArea(FireMakingAreas.STARTING_AREA_2, false);


            if (isItemSelected(ItemId.TINDERBOX)) {

                if (invLogs[0].click("Use")) {
                    message = "Waiting...";
                    General.println("[Debug]: Waiting...");
                    Timer.waitCondition(() -> Inventory.getAll().length < invSize, 2500, 3500);
                    if(Timer.slowWaitCondition(() -> !Player.getPosition().equals(startPosition) &&
                            Player.getAnimation() != 733, 12000, 19000)){
                        Vars.get().daxTracker.trackData("Fires", 1);
                    }

                    if (Interfaces.isInterfaceSubstantiated(233, 2))
                        NPCInteraction.handleConversation();
                }
            } else {
                message = "Lighting fire";
                General.println("[Debug]: Lighting Fire");
                if (Utils.useItemOnItem(ItemId.TINDERBOX, ItemId.LOG_IDS[logIndex]))
                    Timer.waitCondition(() -> Inventory.getAll().length < invSize, 2500, 3500);

                // hover next log
                invLogs = Inventory.find(ItemId.LOG_IDS[logIndex]);
                if (invLogs.length > 0 && t[0].click("Use")) {
                    AntiBan.waitItemInteractionDelay();
                    invLogs[0].hover();
                }
                if(Timer.slowWaitCondition(() -> !Player.getPosition().equals(startPosition) &&
                        Player.getAnimation() != 733, 12000, 19000)){
                    Vars.get().daxTracker.trackData("Fires", 1);
                }

                if (Interfaces.get(233, 2) != null)
                    NPCInteraction.handleConversation();
            }
        }
    }

    @Override
    public String toString(){
        return message;
    }

    @Override
    public Priority priority() {
        return Priority.MEDIUM;
    }

    @Override
    public boolean validate() {
        return Vars.get().currentTask != null &&
                Vars.get().currentTask.equals(SkillTasks.FIREMAKING);
    }

    @Override
    public void execute() {
        if (Skills.getActualLevel(Skills.SKILLS.FIREMAKING) < 15) {
            lightFire(0);
        } else if (Skills.getActualLevel(Skills.SKILLS.FIREMAKING) < 30) {
            lightFire(1);
        } else if (Skills.getActualLevel(Skills.SKILLS.FIREMAKING) < 45) {
            lightFire(2);
        } else if (Skills.getActualLevel(Skills.SKILLS.FIREMAKING) < 60) {
            lightFire(3);
        }   else if (Skills.getActualLevel(Skills.SKILLS.FIREMAKING) < 99) {
            lightFire(4);
        } else if (Skills.getActualLevel(Skills.SKILLS.FIREMAKING) >= Vars.get().firemakingTargetLevel) {
            cSkiller.isRunning.set(false); //ends script
            General.println("[Debug]: Done firemaking");
        }
    }

    @Override
    public String taskName() {
        return "FireMaking";
    }
}
