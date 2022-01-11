package scripts.QuestPackages.ShieldOfArrav;

import dax.walker_engine.interaction_handling.NPCInteraction;
import org.tribot.api.General;
import org.tribot.api2007.*;
import org.tribot.api2007.ext.Filters;
import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSTile;
import scripts.*;
import scripts.QuestSteps.NPCStep;
import scripts.QuestSteps.ObjectStep;
import scripts.QuestSteps.QuestStep;
import scripts.QuestSteps.QuestTask;
import scripts.Requirements.ItemReq;
import scripts.Tasks.Priority;
import scripts.Tasks.Task;

public class BlackArmsGang implements QuestTask {
    String message = "";
    int WEAPONSMASTER = 5211;
    int CURATOR_HAIG_HALEN = 5214;

    // Requirement inStoreRoom, weaponMasterAlive, isUpstairsInBase, cupboardOpen;

    NPCStep talkToCharlie, talkToKatrine;

    RSArea storeRoom = new RSArea(new RSTile(3242, 3380, 1), new RSTile(3252, 3386, 1));
    RSArea upstairsInBase = new RSArea(new RSTile(3182, 3382, 1), new RSTile(3201, 3398, 1));

    RSArea GROUND_FLOOR_OF_BASE = new RSArea(new RSTile(3250, 3385, 0), new RSTile(3252, 3382, 0));
    RSArea BEFORE_DOOR = new RSArea(new RSTile(3252, 3386, 0), new RSTile(3250, 3389, 0));
    ItemReq storeRoomKey = new ItemReq(ItemId.WEAPON_STORE_KEY);
    ItemReq twoPhoenixCrossbow = new ItemReq(ItemId.PHOENIX_CROSSBOW, 2);
    ItemReq shieldHalf = new ItemReq(ItemId.BROKEN_SHIELD_765);
    ItemReq certificateHalf = new ItemReq(ItemId.HALF_CERTIFICATE_11174);
    ItemReq phoenixCertificateHalf = new ItemReq(ItemId.HALF_CERTIFICATE);
    ItemReq certificate = new ItemReq(ItemId.CERTIFICATE);

    ObjectStep getShieldHalf = new ObjectStep(2400, new RSTile(3188, 3386, 1),
            "Open", Objects.findNearest(3, 2401).length > 0);

    ObjectStep getShieldHalf1 = new ObjectStep(2401, new RSTile(3188, 3386, 1),
            Inventory.find(ItemId.BROKEN_SHIELD_765).length > 0, "Search", true);

    NPCStep talkToHaig = new NPCStep(CURATOR_HAIG_HALEN, new RSTile(3255, 3449, 0));

    NPCStep talkToRoald = new NPCStep(5215, new RSTile(3222, 3473, 0));

    public void talkToKaterineStep() {
        if (Game.getSetting(146) == 1) {
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
        if (Game.getSetting(146) == 0) {
            talkToCharlie = new NPCStep(5209, new RSTile(3208, 3392, 0));
            talkToCharlie.addDialogStep("Is there anything down this alleyway?");
            talkToCharlie.addDialogStep("Do you think they would let me join?");
            talkToCharlie.execute();
        }
    }

    public void getWeaponStoreKey() {
        if (Inventory.find(ItemId.WEAPON_STORE_KEY).length == 0) {

        }
    }

    public void getCrossbows() {
        if (Inventory.find(ItemId.WEAPON_STORE_KEY).length == 1
                && Inventory.find(ItemId.PHOENIX_CROSSBOW).length < 2) {
            if (Player.getPosition().getPlane() != 1) {
                if (!GROUND_FLOOR_OF_BASE.contains(Player.getPosition())) {
                    General.println("[Debug]: Going to Outside Pheonix base");
                    PathingUtil.walkToArea(BEFORE_DOOR, false);
                    if (Utils.clickObject(2398, "Open", false))
                        Timer.waitCondition(() -> GROUND_FLOOR_OF_BASE.contains(Player.getPosition()), 6000, 9000);
                } else if (Utils.clickObject(11794, "Climb-up", false))
                    Timer.waitCondition(() -> Player.getPosition().getPlane() == 1, 6000, 9000);

            }


            if (Player.getPosition().getPlane() == 1) {
                General.println("[Debug]: Killing weapon master");
                if (Utils.clickNPC(WEAPONSMASTER, "Attack")) {
                    General.println("TRUE");
                    Timer.waitCondition(() -> Player.getRSPlayer().isInCombat(), 5000, 7000);

                }
                if (Combat.isUnderAttack()) {
                    General.println("TRUE - in combat");
                    int eatAt = General.random(30, 50);
                    CombatUtil.waitUntilOutOfCombat(eatAt, false);
                }
            }
            if (!Combat.isUnderAttack()) {
                Utils.pickupItem(ItemId.PHOENIX_CROSSBOW);
                Utils.pickupItem(ItemId.PHOENIX_CROSSBOW);
            }
        }

    }

    public void talkToKaterineStepAgain() {
        if (Inventory.find(ItemId.PHOENIX_CROSSBOW).length >= 2) {
            message = "Going to Katerine";
            General.println("[Debug]: Going to Katerine");
            talkToKatrine = new NPCStep(5210, new RSTile(3185, 3385, 0));
            talkToKatrine.execute();
        }
    }

    public void getShield(){
        if (Inventory.find(ItemId.CERTIFICATE).length == 0 &&
                Inventory.find(ItemId.BROKEN_SHIELD_765).length ==0
        && Inventory.find(ItemId.HALF_CERTIFICATE_11174).length ==0){
            if (Player.getPosition().getPlane() ==0){
                PathingUtil.walkToTile(new RSTile(3188,3388,0), 1 ,false);
                if (Utils.clickObject(Filters.Objects.actionsContains("Climb-up"), "Climb-up")){
                    Timer.waitCondition(()->Player.getPosition().getPlane() ==1,5000,8000);
                }
            }
            getShieldHalf.setUseLocalNav(true);
            getShieldHalf.execute();
            getShieldHalf1.execute();
        }
        if (Inventory.find(ItemId.BROKEN_SHIELD_765).length ==1 && Player.getPosition().getPlane() == 1){
            if (Utils.clickObject(Filters.Objects.actionsContains("Climb-down"), "Climb-down")){
                Timer.waitCondition(()->Player.getPosition().getPlane() ==0,7000,9000);
            }
        }
    }
    public void talkToHaigen(){
        if (Inventory.find(ItemId.BROKEN_SHIELD_765).length ==1) {
            General.println("[Debug]: Going to currator");
            talkToHaig.execute();
        }
    }

    public void getFullCert(){
        if (Inventory.find(ItemId.HALF_CERTIFICATE_11174).length ==1){
            //trade ppl
        }
        if ( Inventory.find(ItemId.HALF_CERTIFICATE).length == 1
                && Inventory.find(ItemId.HALF_CERTIFICATE_11174).length == 1) {

            //combine
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation("Yes.");
        }
        if (Inventory.find(ItemId.CERTIFICATE).length == 1){
            talkToRoald.execute();
        }
    }


    @Override
    public String toString() {
        return "" + Player.getRSPlayer().isInCombat();
    }

    @Override
    public Priority priority() {
        return Priority.HIGH;
    }

    @Override
    public boolean validate() {
        return Game.getSetting(146) <= 4;
    }

    @Override
    public void execute() {
        talkToCharilieStep();
        talkToKaterineStep();
        if (Game.getSetting(146) == 2) {
            getWeaponStoreKey();
            getCrossbows();
            talkToKaterineStepAgain();
        } else if (Game.getSetting(146) == 3) {
            getShield();
            talkToHaigen();
            getFullCert();
        }
        else if (Game.getSetting(146) == 4) {
            //finisehd
        }
    }

    @Override
    public String questName() {
        return null;
    }

    @Override
    public boolean checkRequirements() {
        return false;
    }

}
