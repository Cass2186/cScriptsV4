package scripts.Tasks.Defender;

import org.tribot.api.General;
import org.tribot.script.sdk.*;
import org.tribot.script.sdk.query.Query;
import org.tribot.script.sdk.types.GroundItem;
import org.tribot.script.sdk.types.InventoryItem;
import org.tribot.script.sdk.types.Npc;
import scripts.*;
import scripts.API.Priority;
import scripts.API.Task;
import scripts.Data.SkillTasks;
import scripts.Data.Vars;
import scripts.Tasks.Defender.Data.DefenderConst;
import scripts.Tasks.Defender.Data.DefenderVars;

import javax.swing.text.html.Option;
import java.awt.*;
import java.util.Optional;

public class KillCyclopsUpstairs implements Task {

    int kills = 0;
    //changes after showing to person
    int CURRENT_DEFENDER_VARBIT = 2448; //1 = bronze, 2 = iron etc
    int HAS_SHOWN_DEFENDER_VARBIT = 5983; // 1 = true



    private boolean moveToArea() {
        if (Inventory.getCount(DefenderConst.DEFENDERS[1]) > 0) {
            PathingUtil.walkToTile(DefenderConst.DRAGON_DEFENDER_AREA.getCenter());
            return DefenderConst.DRAGON_DEFENDER_AREA.containsMyPlayer();
        } else if (!DefenderConst.UPPER_CYCLOPS_AREA.containsMyPlayer()){
            PathingUtil.walkToTile(DefenderConst.UPPER_CYCLOPS_AREA.getCenter());
            return DefenderConst.UPPER_CYCLOPS_AREA.containsMyPlayer();
        }
        return DefenderConst.UPPER_CYCLOPS_AREA.containsMyPlayer();
    }

    public void killCyclops() {
        if (moveToArea()) {
            Log.info("Killing Cyclops");

            checkEat();
            Optional<Npc> interacting = Query.npcs().nameContains("Cyclops")
                    .isInteractingWithMe()
                    .isHealthBarNotEmpty()
                    .findBestInteractable();
            Optional<Npc> target = Query.npcs()
                    .nameContains("Cyclops")
                    .isNotBeingInteractedWith()
                    .findBestInteractable();

            if (!MyPlayer.isHealthBarVisible() &&
                    interacting.map(t -> t.getHealthBarPercent() != 0 &&
                            t.interact("Attack")).orElse(false) ||
                    target.map(t -> t.getHealthBarPercent() != 0 &&t.interact("Attack")).orElse(false)) {
                Waiting.waitUntil(4500, 500,
                        ()-> Query.npcs().isInteractingWithMe().isMyPlayerInteractingWith().isAny());
                Vars.get().currentTime = System.currentTimeMillis();
                if (CombatUtil.waitUntilOutOfCombat("Cyclops", General.random(40, 60))) {
                    kills++;
                    Log.info("Kills = " + kills);
                    Utils.idleNormalAction();
                    checkEat();
                    checkForDefenders();
                    return;
                }

            } else if (MyPlayer.isHealthBarVisible() && CombatUtil.waitUntilOutOfCombat("Cyclops", General.random(40, 60))) {
                kills++;
                Log.info("[Debug]: Kills = " + kills);
                Utils.idleAfkAction();
            }
            checkEat();
            checkForDefenders();
        }
    }

    public void checkEat() {
        if (MyPlayer.getCurrentHealthPercent() <= DefenderVars.get().eatAtPercent &&
                EatUtil.eatFood())
            DefenderVars.get().eatAtPercent = General.random(40, 65);
    }


    public void checkForDefenders() {
        Optional<GroundItem> closest = Query.groundItems().idEquals(DefenderConst.DEFENDERS).findClosest();
        int inv = Inventory.getCount(DefenderConst.DEFENDERS);
        if (closest.isPresent() && Inventory.isFull())
            EatUtil.eatFood();

        if (closest.map(c -> c.interact("Take")).orElse(false)) {
            Log.info("[Debug]: Found Defender, looting.");
            Waiting.waitUntil(6500, 250, () ->
                    Inventory.getCount(DefenderConst.DEFENDERS) > inv);
        }
    }

    public int determineHighestDefender() {
        switch (Utils.getVarBitValue(CURRENT_DEFENDER_VARBIT)){
            case(1):
                return ItemID.BRONZE_DEFENDER;
            case(2):
                return ItemID.IRON_DEFENDER;
            case(3):
                return ItemID.STEEL_DEFENDER;
            case(4):
                return ItemID.BLACK_DEFENDER;
            case(5):
                return ItemID.MITHRIL_DEFENDER;
            case(6):
                return ItemID.ADAMANT_DEFENDER;
            case(7):
                return ItemID.RUNE_DEFENDER;
            case(8):
                return ItemID.DRAGON_DEFENDER;
        }
        return -1;
    }


    public void showDefender() {
        if (DefenderVars.get().shouldShowDefender ||
                (kills >= Utils.random(75, 85) && !Inventory.contains(ItemID.RUNE_DEFENDER))) {
            Log.info("Showing defender");
            if (kills >= 75 && !Inventory.contains(ItemID.RUNE_DEFENDER))
                General.println("[Debug]: Showing defender due to high kill count without drop", Color.red);
            else
                General.println("[Debug]: Showing defender", Color.red);

            if (!DefenderConst.BEFORE_DOOR.contains(MyPlayer.getTile())
                    && Utils.clickObj(24306, "Open"))
                Timer.waitCondition(() -> DefenderConst.BEFORE_DOOR.contains(MyPlayer.getTile()), 9000, 13000);

            if (DefenderConst.BEFORE_DOOR.contains(MyPlayer.getTile())) {
                Optional<InventoryItem> invDefender = Query.inventory()
                        .idEquals(determineHighestDefender()).findClosestToMouse();

                Optional<Npc> npc = Query.npcs().idEquals(2461).findBestInteractable();
                if (npc.map(n -> invDefender.map(i -> i.useOn(n)).orElse(false)).orElse(false)) {
                    General.println("[Debug]: Showed defender!!", Color.red);
                    if (NpcChat.waitForChatScreen()) {
                        DefenderVars.get().shouldShowDefender = false;
                        kills = 0;
                        ChatScreen.handle();
                    }
                }
            }
            if (Utils.clickObj("Door", "Open")) {
                Waiting.waitUntil(5000, 550, () -> DefenderConst.UPPER_CYCLOPS_AREA.containsMyPlayer());
            }
        }
    }


    @Override
    public Priority priority() {
        return Priority.LOW;
    }

    @Override
    public boolean validate() {
        if (!Vars.get().getDefenders ||
                Vars.get().currentTask == null ||
                !Vars.get().currentTask.equals(SkillTasks.DEFENDERS))
            return false;

        if ((DefenderConst.UPPER_CYCLOPS_AREA.containsMyPlayer() ||
                DefenderConst.DRAGON_DEFENDER_AREA.contains(MyPlayer.getTile()))) {
            // in a defender area, so check token count > 0
            return Inventory.getCount(ItemID.WARRIOR_GUILD_TOKEN) >= 0;

        } else
            return Inventory.getCount(ItemID.WARRIOR_GUILD_TOKEN) >= DefenderVars.get().minTokens;

    }

    @Override
    public void execute() {
        killCyclops();
        showDefender();
    }

    @Override
    public String toString() {
        return "Killing Upstairs Cyclops";
    }

    @Override
    public String taskName() {
        return "Defender";
    }
}
