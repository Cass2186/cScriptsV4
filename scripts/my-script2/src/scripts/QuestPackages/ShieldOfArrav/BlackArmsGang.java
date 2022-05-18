package scripts.QuestPackages.ShieldOfArrav;

import dax.walker_engine.interaction_handling.NPCInteraction;
import org.tribot.api.General;

import org.tribot.api2007.Combat;
import org.tribot.api2007.Objects;
import org.tribot.api2007.ext.Filters;
import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSTile;
import org.tribot.script.sdk.*;

import org.tribot.script.sdk.Inventory;
import org.tribot.script.sdk.query.Query;
import org.tribot.script.sdk.query.TradeQuery;
import org.tribot.script.sdk.types.Area;
import org.tribot.script.sdk.types.Player;
import org.tribot.script.sdk.types.WorldTile;
import scripts.*;
import scripts.Listeners.MuleListener;
import scripts.QuestPackages.ShadowOfTheStorm.ShadowOfTheStorm;
import scripts.QuestSteps.NPCStep;
import scripts.QuestSteps.ObjectStep;
import scripts.QuestSteps.QuestStep;
import scripts.QuestSteps.QuestTask;
import scripts.Requirements.InventoryRequirement;
import scripts.Requirements.ItemReq;
import scripts.Requirements.ItemRequirement;
import scripts.Requirements.Requirement;
import scripts.Tasks.Priority;
import scripts.Tasks.Task;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class BlackArmsGang implements QuestTask, MuleListener {
    private static BlackArmsGang quest;

    public static BlackArmsGang get() {
        return quest == null ? quest = new BlackArmsGang() : quest;
    }

    String message = "";
    int WEAPONSMASTER = 5211;
    int CURATOR_HAIG_HALEN = 5214;

    // Requirement inStoreRoom, weaponMasterAlive, isUpstairsInBase, cupboardOpen;

    NPCStep talkToCharlie, talkToKatrine;

    RSArea storeRoom = new RSArea(new RSTile(3242, 3380, 1), new RSTile(3252, 3386, 1));
    RSArea upstairsInBase = new RSArea(new RSTile(3182, 3382, 1), new RSTile(3201, 3398, 1));

    Area GROUND_FLOOR_OF_BASE = Area.fromRectangle(new WorldTile(3250, 3385, 0), new WorldTile(3252, 3382, 0));
    Area BEFORE_DOOR = Area.fromRectangle(new WorldTile(3252, 3386, 0), new WorldTile(3250, 3389, 0));
    ItemReq storeRoomKey = new ItemReq(ItemID.WEAPON_STORE_KEY);
    ItemReq twoPhoenixCrossbow = new ItemReq(ItemID.PHOENIX_CROSSBOW, 2);
    ItemReq shieldHalf = new ItemReq(ItemID.BROKEN_SHIELD_765);
    ItemReq certificateHalf = new ItemReq(ItemID.HALF_CERTIFICATE_11174);
    ItemReq phoenixCertificateHalf = new ItemReq(ItemID.HALF_CERTIFICATE);
    ItemReq certificate = new ItemReq(ItemID.CERTIFICATE);

    ObjectStep getShieldHalf = new ObjectStep(2400, new RSTile(3188, 3386, 1),
            "Open", Objects.findNearest(3, 2401).length > 0);

    ObjectStep getShieldHalf1 = new ObjectStep(2401, new RSTile(3188, 3386, 1),
            Inventory.contains(ItemID.BROKEN_SHIELD_765), "Search", true);

    NPCStep talkToHaig = new NPCStep(CURATOR_HAIG_HALEN, new RSTile(3255, 3449, 0));

    NPCStep talkToRoald = new NPCStep(5215, new RSTile(3222, 3473, 0), certificate);

    InventoryRequirement startInventory = new InventoryRequirement(new ArrayList<>(
            Arrays.asList(
                    new ItemReq(ItemID.VARROCK_TELEPORT, 5, 0),
                    new ItemReq(ItemID.COINS, 500, 200),
                    new ItemReq(ItemID.STAMINA_POTION[0], 1, 0)
            )
    ));

    public void talkToKaterineStep() {
        if (GameState.getSetting(146) == 1) {
            message = "Going to Katerine";
            General.println("[Debug]: Going to Katerine");
            talkToKatrine = new NPCStep(5210, new RSTile(3185, 3385, 0));
            talkToKatrine.addDialogStep("I've heard you're the Black Arm Gang.");
            talkToKatrine.addDialogStep("I'd rather not reveal my sources.");
            talkToKatrine.addDialogStep("I want to become a member of your gang.");
            talkToKatrine.addDialogStep("Well, you can give me a try can't you?");
            talkToKatrine.addDialogStep("Ok, no problem.");
            talkToKatrine.execute();
        }
    }

    public void talkToCharilieStep() {
        if (GameState.getSetting(146) == 0) {
            talkToCharlie = new NPCStep(5209, new RSTile(3208, 3392, 0));
            talkToCharlie.addDialogStep("Is there anything down this alleyway?");
            talkToCharlie.addDialogStep("Yes.");
            talkToCharlie.execute();
        }
    }


    public void getWeaponStoreKey() {
        if (!Inventory.contains(ItemID.WEAPON_STORE_KEY)) {
            Log.info("Trading partner for the weapon store key");
            if (SoA_Vars.get().partnerName.isEmpty()) {
                Log.warn("No partner specified!!");
                return;
            } else {
                // go to area
                if (!BEFORE_DOOR.containsMyPlayer())
                    PathingUtil.walkToArea(BEFORE_DOOR, false);

                Optional<Player> partner = Query.players().nameContains(SoA_Vars.get().partnerName.get()).findBestInteractable();
                if (partner.map(p->p.interact("Trade")).orElse(false)){
                    Waiting.waitUntil(25000,500, ()->
                            TradeScreen.getStage().isPresent());
                }
                if(TradeScreen.getStage().isPresent()){
                    Waiting.waitUntil(8000, ()-> !TradeScreen.isDelayed());
                }

                Waiting.waitNormal(5000, 500);
            }
        }
    }

    public void getCrossbows() {
        if (Inventory.contains(ItemID.WEAPON_STORE_KEY)
                && Inventory.getCount(ItemID.PHOENIX_CROSSBOW) < 2) {


            if (MyPlayer.getTile().getPlane() != 1) {
                if (!GROUND_FLOOR_OF_BASE.containsMyPlayer()) {
                    General.println("[Debug]: Going to Outside Pheonix base");
                    PathingUtil.walkToArea(BEFORE_DOOR, false);
                    if (Utils.clickObject(2398, "Open", false))
                        Timer.waitCondition(() -> GROUND_FLOOR_OF_BASE.containsMyPlayer(), 6000, 9000);
                } else if (Utils.clickObject(11794, "Climb-up", false))
                    Timer.waitCondition(() -> MyPlayer.getTile().getPlane() == 1, 6000, 9000);

            }


            if (MyPlayer.getTile().getPlane() == 1) {
                General.println("[Debug]: Killing weapon master");
                if (Utils.clickNPC(WEAPONSMASTER, "Attack")) {
                    General.println("TRUE");
                    Timer.waitCondition(() -> MyPlayer.isHealthBarVisible(), 5000, 7000);

                }
                if (MyPlayer.isHealthBarVisible()) {
                    General.println("TRUE - in combat");
                    int eatAt = General.random(30, 50);
                    CombatUtil.waitUntilOutOfCombat(eatAt, false);
                }
            }
            if (!MyPlayer.isHealthBarVisible()) {
                Utils.pickupItem(ItemID.PHOENIX_CROSSBOW);
                Utils.pickupItem(ItemID.PHOENIX_CROSSBOW);
            }
        }

    }

    public void talkToKaterineStepAgain() {
        if (Inventory.getCount(ItemID.PHOENIX_CROSSBOW) >= 2) {
            message = "Going to Katerine";
            General.println("[Debug]: Going to Katerine");
            talkToKatrine = new NPCStep(5210, new RSTile(3185, 3385, 0));
            talkToKatrine.execute();
        }
    }

    public void getShield() {
        if (!Inventory.contains(ItemID.CERTIFICATE) &&
                !Inventory.contains(ItemID.BROKEN_SHIELD_765)
                && !Inventory.contains(ItemID.HALF_CERTIFICATE_11174)) {
            if (MyPlayer.getTile().getPlane() == 0) {
                PathingUtil.walkToTile(new RSTile(3188, 3388, 0), 1, false);
                if (Utils.clickObject(Filters.Objects.actionsContains("Climb-up"), "Climb-up")) {
                    Timer.waitCondition(() -> MyPlayer.getTile().getPlane() == 1, 5000, 8000);
                }
            }
            getShieldHalf.setUseLocalNav(true);
            getShieldHalf.execute();
            getShieldHalf1.execute();
        }
        if (Inventory.contains(ItemID.BROKEN_SHIELD_765) && MyPlayer.getTile().getPlane() == 1) {
            if (Utils.clickObject(Filters.Objects.actionsContains("Climb-down"), "Climb-down")) {
                Timer.waitCondition(() -> MyPlayer.getTile().getPlane() == 0, 7000, 9000);
            }
        }
    }

    public void talkToHaigen() {
        if (Inventory.contains(ItemID.BROKEN_SHIELD_765)) {
            General.println("[Debug]: Going to currator");
            talkToHaig.execute();
        }
    }

    public void getFullCert() {
        if (Inventory.contains(ItemID.HALF_CERTIFICATE_11174)) {
            //trade ppl
        }
        if (Inventory.contains(ItemID.HALF_CERTIFICATE)
                && Inventory.contains(ItemID.HALF_CERTIFICATE_11174)) {

            //combine
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation("Yes.");
        }
        if (Inventory.contains(ItemID.CERTIFICATE)) {
            talkToRoald.execute();
        }
    }


    @Override
    public String toString() {
        return "" + MyPlayer.isHealthBarVisible();
    }

    @Override
    public Priority priority() {
        return Priority.HIGH;
    }

    @Override
    public boolean validate() {
        return GameState.getSetting(146) <= 4;
    }

    @Override
    public void execute() {
        if (!startInventory.check())
            startInventory.withdrawItems();
        else {
            talkToCharilieStep();
            talkToKaterineStep();
        }
        if (GameState.getSetting(146) == 2) {
            getWeaponStoreKey();
            getCrossbows();
            talkToKaterineStepAgain();
        } else if (GameState.getSetting(146) == 3) {
            getShield();
            talkToHaigen();
            getFullCert();
        }
        if (certificate.check()) {
            talkToRoald.execute();

        } else if (GameState.getSetting(146) == 4) {
            cQuesterV2.taskList.remove(this);
        }
    }

    @Override
    public String questName() {
        return "Shield of Arrav";
    }

    @Override
    public boolean checkRequirements() {
        return true;
    }

    @Override
    public void onMuleNearby(String muleName) {

    }

    @Override
    public void onMuleLeave(String muleName) {

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
        return Quest.SHIELD_OF_ARRAV.getState().equals(Quest.State.COMPLETE);
    }
}
