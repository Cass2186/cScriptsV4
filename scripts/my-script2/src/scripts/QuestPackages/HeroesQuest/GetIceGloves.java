package scripts.QuestPackages.HeroesQuest;

import dax.api_lib.models.RunescapeBank;
import dax.walker.utils.AccurateMouse;
import dax.walker.utils.camera.DaxCamera;
import org.tribot.api.General;
import org.tribot.api2007.*;
import org.tribot.api2007.ext.Filters;
import org.tribot.api2007.types.*;
import scripts.*;
import scripts.QuestSteps.QuestStep;
import scripts.QuestSteps.QuestTask;
import scripts.Tasks.Priority;
import scripts.Tasks.Task;



public class GetIceGloves implements QuestTask {

    private void goToQueen() {
        cQuesterV2.status = "Going to Queen";
        if (!HeroesQuestConst.POST_ROCK_SLIDE.contains(Player.getPosition()) && !HeroesQuestConst.ABOVE_GROUND_LADDER_CLUSTER_3.contains(Player.getPosition())
                && !HeroesQuestConst.ABOVE_GROUND_LADDER_CLUSTER_2.contains(Player.getPosition())
                && !HeroesQuestConst.ICE_LAIR.contains(Player.getPosition()))
            PathingUtil.walkToArea(HeroesQuestConst.START_AREA_ROCK, false);

        if (HeroesQuestConst.START_AREA_ROCK.contains(Player.getPosition()) ||
                PathingUtil.walkToArea(HeroesQuestConst.START_AREA_ROCK, false)) {
            if (Utils.clickObject("Rock slide", "Mine", true))
                Timer.abc2WaitCondition(() -> HeroesQuestConst.POST_ROCK_SLIDE.contains(Player.getPosition()), 10000, 15000);
        } else if (HeroesQuestConst.POST_ROCK_SLIDE.contains(Player.getPosition())) {
            checkPrayer();

            PathingUtil.localNavigation(new RSTile(2848, 3514, 0));

            RSObject[] ladder = Objects.findNearest(10, Filters.Objects.tileEquals(new RSTile(2848, 3513, 0)));
            if (ladder.length > 0) {
                if (Utils.clickObject(ladder[0], "Climb-down", true))
                    Timer.waitCondition(() -> HeroesQuestConst.ICE_LAIR.contains(Player.getPosition()), 9000, 15000);
            }

        } else if (HeroesQuestConst.FROM_LADDER1_TO_LADDER2.contains(Player.getPosition())) {
            cQuesterV2.status = "Going to Ladder 2";
            Walking.walkPath(HeroesQuestConst.PATH_FROM_LADDER1_TO_LADDER2);
            Timer.waitCondition(() -> HeroesQuestConst.LADDER_2_AREA.contains(Player.getPosition()), 5000, 10000);
            if (Utils.clickObject("Ladder", "Climb-up", true))
                Timer.waitCondition(() -> HeroesQuestConst.ABOVE_GROUND_LADDER_CLUSTER_2.contains(Player.getPosition()), 7000, 9000);

        } else if (HeroesQuestConst.ABOVE_GROUND_LADDER_CLUSTER_2.contains(Player.getPosition())) {
            RSObject[] ladder = Objects.findNearest(10, Filters.Objects.tileEquals(new RSTile(2827, 3510, 0)));
            checkPrayer();
            if (ladder.length > 0)
                if (Utils.clickObject(ladder[0], "Climb-down", true))
                    Timer.waitCondition(() -> HeroesQuestConst.LAIR_AREA_BEFORE_PATH1.contains(Player.getPosition()), 9000, 15000);
        } else if (HeroesQuestConst.LAIR_AREA_BEFORE_PATH1.contains(Player.getPosition())) {
            checkPrayer();
            Walking.walkPath(HeroesQuestConst.PATH_1);
            PathingUtil.movementIdle();
            checkPrayer();
            if (Utils.clickObject("Ladder", "Climb-up", true))
                Timer.waitCondition(() -> HeroesQuestConst.ABOVE_GROUND_LADDER_CLUSTER_3.contains(Player.getPosition()), 7000, 9000);

        } else if (HeroesQuestConst.ABOVE_GROUND_LADDER_CLUSTER_3.contains(Player.getPosition())) {
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
        RSItem[] pPot = Inventory.find(ItemId.PRAYER_POTION);
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
        org.tribot.script.sdk.Combat.setAutocastSpell(org.tribot.script.sdk.Combat.AutocastableSpell.FIRE_STRIKE);

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
        RSGroundItem[] icegloves = GroundItems.find(ItemId.ICE_GLOVES);
        if (icegloves.length > 0) {
            General.println("[Debug]: Looting ice gloves");
            if (!icegloves[0].isClickable())
                icegloves[0].adjustCameraTo();

            if (AccurateMouse.click(icegloves[0], "Take"))
                Timer.waitCondition(() -> Inventory.find(ItemId.ICE_GLOVES).length > 0, 8000, 12000);
        }

        // @TODO this is the old code, update for quest
        if (Inventory.find(ItemId.ICE_GLOVES).length > 0) {
            General.println("[Debug]: Going to bank - finishing");
            if (PathingUtil.walkToTile(RunescapeBank.CAMELOT.getPosition(), 4, true)) {
                Prayer.disable(Prayer.PRAYERS.PROTECT_FROM_MELEE);
            }
        }

    }


    // TODO will have to modify this execute to include getting right items
    @Override
    public void execute() {
        if (!HeroesQuestConst.WHOLE_QUEEN_AREA.contains(Player.getPosition()))
            goToQueen();
        else {
            fight();
            lootGlovesAndFinish();
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


    @Override
    public Priority priority() {
        return Priority.MEDIUM;
    }



    // TODO will have to modify this validate() to include the quest step

    @Override
    public boolean validate() {
        return BankManager.checkInventoryItems(ItemId.MIND_RUNE, ItemId.AIR_RUNE, ItemId.LOBSTER)
                && (!HeroesQuestConst.FINAL_LANDING_AREA.contains(Player.getPosition())
                && !HeroesQuestConst.WHOLE_QUEEN_AREA.contains(Player.getPosition()));
    }

}
