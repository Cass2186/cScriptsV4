package scripts.QuestPackages.HeroesQuest;

import dax.api_lib.models.RunescapeBank;
import dax.walker.utils.AccurateMouse;
import dax.walker.utils.camera.DaxCamera;
import org.tribot.api.General;
import org.tribot.api2007.*;
import org.tribot.api2007.ext.Filters;
import org.tribot.api2007.types.*;
import scripts.*;
import scripts.GEManager.GEItem;
import scripts.QuestPackages.ShieldOfArrav.PheonixGang;
import scripts.QuestSteps.BuyItemsStep;
import scripts.QuestSteps.QuestStep;
import scripts.QuestSteps.QuestTask;
import scripts.Requirements.ItemRequirement;
import scripts.Requirements.Requirement;
import scripts.Tasks.Priority;
import scripts.Tasks.Task;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class GetIceGloves implements QuestTask {
    private static GetIceGloves quest;

    public static GetIceGloves get() {
        return quest == null ? quest = new GetIceGloves() : quest;
    }

    ArrayList<GEItem> itemsToBuy = new ArrayList<GEItem>(
            Arrays.asList(
                    new GEItem(ItemID.MIND_RUNE, 300, 20),
                    new GEItem(ItemID.AIR_RUNE, 600, 20),
                    new GEItem(ItemID.CAMELOT_TELEPORT, 5, 40),
                    new GEItem(ItemID.STAFF_OF_FIRE, 1, 400),
                    new GEItem(ItemID.RUNE_PICKAXE, 1, 40),
                    new GEItem(ItemID.LOBSTER, 20, 40),
                    new GEItem(ItemID.PRAYER_POTION[0], 3, 35),
                    new GEItem(ItemID.MONKS_ROBE, 1, 200),
                    new GEItem(ItemID.MONKS_ROBE_BOTTOM, 1, 50),
                    new GEItem(ItemID.STAMINA_POTION[0], 4, 35)
            )
    );
    private void buyItems() {
        BuyItemsStep buyStep = new BuyItemsStep(itemsToBuy);
        buyStep.buyItems();

    }

    private void getItems() {

        BankManager.open(true);
        BankManager.depositAll(true);
        BankManager.depositEquipment();
        BankManager.checkEquippedGlory();
        BankManager.withdraw(300, true, ItemID.CHAOS_RUNE);
        BankManager.withdraw(600, true, ItemID.AIR_RUNE);
        BankManager.withdraw(2, true, ItemID.STAMINA_POTION[0]);
        BankManager.withdraw(1, true, ItemID.STAFF_OF_FIRE);
        Utils.equipItem(ItemID.STAFF_OF_FIRE);
        BankManager.withdraw(17, true, ItemID.LOBSTER);
        BankManager.withdraw(3, true, ItemID.CAMELOT_TELEPORT);
        BankManager.withdraw(1, true, ItemID.RUNE_PICKAXE);
        BankManager.withdraw(3, true, ItemID.PRAYER_POTION[0]);
        BankManager.close(true);
    }


    private void goToQueen() {
        cQuesterV2.status = "Going to Queen";
        if (!HeroesQuestConst.POST_ROCK_SLIDE.contains(Player.getPosition()) &&
                !HeroesQuestConst.ABOVE_GROUND_LADDER_CLUSTER_3.contains(Player.getPosition())
                && !HeroesQuestConst.ABOVE_GROUND_LADDER_CLUSTER_2.contains(Player.getPosition())
                && !HeroesQuestConst.ICE_LAIR.contains(Player.getPosition()))
           PathingUtil.walkToArea(HeroesQuestConst.START_AREA_ROCK, false);

        if (HeroesQuestConst.START_AREA_ROCK.contains(Player.getPosition()) ||
                PathingUtil.walkToArea(HeroesQuestConst.START_AREA_ROCK, false)) {
            if (Utils.clickObject("Rock slide", "Mine", true))
                Timer.abc2WaitCondition(() -> HeroesQuestConst.POST_ROCK_SLIDE.contains(Player.getPosition()), 10000, 15000);
        } if (HeroesQuestConst.POST_ROCK_SLIDE.contains(Player.getPosition())) {
            checkPrayer();

            PathingUtil.localNavigation(new RSTile(2848, 3514, 0));

            RSObject[] ladder = Objects.findNearest(10, Filters.Objects.tileEquals(new RSTile(2848, 3513, 0)));
            if (ladder.length > 0) {
                if (Utils.clickObject(ladder[0], "Climb-down", true))
                    Timer.waitCondition(() -> HeroesQuestConst.ICE_LAIR.contains(Player.getPosition()), 9000, 15000);
            }

        }  if (HeroesQuestConst.FROM_LADDER1_TO_LADDER2.contains(Player.getPosition())) {
            cQuesterV2.status = "Going to Ladder 2";
            Walking.walkPath(HeroesQuestConst.PATH_FROM_LADDER1_TO_LADDER2);
            Timer.waitCondition(() -> HeroesQuestConst.LADDER_2_AREA.contains(Player.getPosition()), 5000, 10000);
            if (Utils.clickObject("Ladder", "Climb-up", true))
                Timer.waitCondition(() -> HeroesQuestConst.ABOVE_GROUND_LADDER_CLUSTER_2.contains(Player.getPosition()), 7000, 9000);

        }  if (HeroesQuestConst.ABOVE_GROUND_LADDER_CLUSTER_2.contains(Player.getPosition())) {
            RSObject[] ladder = Objects.findNearest(10, Filters.Objects.tileEquals(new RSTile(2827, 3510, 0)));
            checkPrayer();
            if (ladder.length > 0)
                if (Utils.clickObject(ladder[0], "Climb-down", true))
                    Timer.waitCondition(() -> HeroesQuestConst.LAIR_AREA_BEFORE_PATH1.contains(Player.getPosition()), 9000, 15000);
        }  if (HeroesQuestConst.LAIR_AREA_BEFORE_PATH1.contains(Player.getPosition())) {
            checkPrayer();
            Walking.walkPath(HeroesQuestConst.PATH_1);
            PathingUtil.movementIdle();
            checkPrayer();
            if (Utils.clickObject("Ladder", "Climb-up", true))
                Timer.waitCondition(() -> HeroesQuestConst.ABOVE_GROUND_LADDER_CLUSTER_3.contains(Player.getPosition()), 7000, 9000);

        }  if (HeroesQuestConst.ABOVE_GROUND_LADDER_CLUSTER_3.contains(Player.getPosition())) {
            cQuesterV2.status = "Going to Final Area";
            checkPrayer();

            RSObject[] ladder = Objects.findNearest(10, Filters.Objects.tileEquals(new RSTile(2859, 3519, 0)));

            if (ladder.length > 0)
                if (Utils.clickObject(ladder[0], "Climb-down", true))
                    Timer.waitCondition(() -> HeroesQuestConst.FINAL_LANDING_AREA.contains(Player.getPosition()), 9000, 15000);

        }
    }

    int drinkPotionAt = General.random(15,25);
    public  void checkPrayer() {
        RSItem[] pPot = Inventory.find(ItemID.PRAYER_POTION);
        if (Prayer.getPrayerPoints() <= drinkPotionAt && pPot.length > 0 &&
                pPot[0].click("Drink")) {
            Timer.waitCondition(() -> Player.getAnimation() != -1, 1250, 2000);
            drinkPotionAt = General.random(15,25);
            General.println("[Debug]: Next prayer potion at " + drinkPotionAt);
        }

        if (Prayer.getPrayerPoints() > 0 && !Prayer.isPrayerEnabled(Prayer.PRAYERS.PROTECT_FROM_MELEE)) {
            Prayer.enable(Prayer.PRAYERS.PROTECT_FROM_MELEE);
        }
    }
    private void fight() {
        org.tribot.script.sdk.Combat.setAutocastSpell(org.tribot.script.sdk.Combat.AutocastableSpell.FIRE_BOLT);

        if (HeroesQuestConst.FINAL_LANDING_AREA.contains(Player.getPosition())) {
            cQuesterV2.status = "Going to safe spot";
            checkPrayer();
            Prayer.enable(Prayer.PRAYERS.PROTECT_FROM_MELEE);
            PathingUtil.localNavigation(HeroesQuestConst.SAFE_TILE);
            checkPrayer();
        }


        RSNPC[] iceQueen = NPCs.findNearest("Ice Queen");
        if (iceQueen.length > 0) {
            cQuesterV2.status = "Attacking Queen";
            if (!iceQueen[0].isInCombat()) {
                Utils.shortSleep();

                if (!iceQueen[0].isClickable())
                    DaxCamera.focus(iceQueen[0]);

                if (AccurateMouse.click(iceQueen[0], "Attack"))
                    Timer.waitCondition(() -> iceQueen[0].isInCombat(), 7000, 10000);
            }
            checkPrayer();
            AntiBan.timedActions();
        }
    }

    public void lootGlovesAndFinish() {
        RSGroundItem[] icegloves = GroundItems.find(ItemID.ICE_GLOVES);
        if (icegloves.length > 0) {
            General.println("[Debug]: Looting ice gloves");
            if (!icegloves[0].isClickable())
                icegloves[0].adjustCameraTo();

            if (AccurateMouse.click(icegloves[0], "Take"))
                Timer.waitCondition(() -> Inventory.find(ItemID.ICE_GLOVES).length > 0, 8000, 12000);
        }

        // @TODO this is the old code, update for quest
        if (Inventory.find(ItemID.ICE_GLOVES).length > 0) {
            General.println("[Debug]: Going to bank - finishing");
            if (PathingUtil.walkToTile(RunescapeBank.CAMELOT.getPosition(), 4, true)) {
                Prayer.disable(Prayer.PRAYERS.PROTECT_FROM_MELEE);
            }
        }

    }


    // TODO will have to modify this execute to include getting right items
    @Override
    public void execute() {
        if (!HeroesQuestConst.WHOLE_QUEEN_AREA.contains(Player.getPosition())) {
            if(!BankManager.checkInventoryItems(ItemID.MIND_RUNE, ItemID.AIR_RUNE, ItemID.LOBSTER  ,ItemID.RUNE_PICKAXE)) {
                buyItems();
                getItems();
            }
            goToQueen();
        }
            fight();
            lootGlovesAndFinish();

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
    public Priority priority() {
        return Priority.MEDIUM;
    }



    // TODO will have to modify this validate() to include the quest step

    @Override
    public boolean validate() {
        return BankManager.checkInventoryItems(ItemID.MIND_RUNE, ItemID.AIR_RUNE, ItemID.LOBSTER ,ItemID.RUNE_PICKAXE)
                && (!HeroesQuestConst.FINAL_LANDING_AREA.contains(Player.getPosition())
                && !HeroesQuestConst.WHOLE_QUEEN_AREA.contains(Player.getPosition()));
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
