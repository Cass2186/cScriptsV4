package scripts.Tasks;

import dax.walker_engine.interaction_handling.NPCInteraction;
import org.tribot.api.General;
import org.tribot.api2007.Combat;
import org.tribot.api2007.Interfaces;
import org.tribot.api2007.Player;
import org.tribot.api2007.Skills;
import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSInterface;
import org.tribot.api2007.types.RSTile;
import org.tribot.script.sdk.Log;
import org.tribot.script.sdk.Prayer;
import org.tribot.script.sdk.Waiting;
import scripts.*;
import scripts.Data.Areas;
import scripts.Data.Vars;
import scripts.QuestSteps.ObjectStep;
import scripts.Requirements.AreaRequirement;
import scripts.Requirements.ItemRequirement;

public class MoveToArea implements Task {

    RSArea DUNGEON_AREA = new RSArea(new RSTile(3541, 10484, 0), new RSTile(3557, 10442, 0));
    RSTile BEFORE_DOOR_TILE = new RSTile(3549, 10482, 0);

    RSArea WHOLE_DRAGON_AREA = new RSArea(new RSTile(1598, 5059, 0), new RSTile(1538, 5116, 0));
    public static RSArea RUNE_DRAGON_AREA = new RSArea(new RSTile(1596, 5063, 0), new RSTile(1576, 5086, 0));

    String s = "Lithkren";

    public void goToRuneDragons() {
        if (!DUNGEON_AREA.contains(Player.getPosition()) && !WHOLE_DRAGON_AREA.contains(Player.getPosition())) {
            //use necklace
        }
        if (DUNGEON_AREA.contains(Player.getPosition())) {
            if (PathingUtil.localNavigation(new RSTile(3549, 10466, 0))) {
                PathingUtil.movementIdle();
                if (Utils.clickObject(32113, "Climb", false)) {
                    PathingUtil.movementIdle();
                }
            }
            if (PathingUtil.localNavigation(BEFORE_DOOR_TILE))
                Waiting.waitNormal(3000, 500);
            if (Utils.clickObject("Broken Grandiose Doors", "Enter", false)) {
                Timer.waitCondition(() -> WHOLE_DRAGON_AREA.contains(Player.getPosition()), 5500, 7000);
                Waiting.waitNormal(3000, 500);
            }
        }
        if (WHOLE_DRAGON_AREA.contains(Player.getPosition())) {
            if (!RUNE_DRAGON_AREA.contains(Player.getPosition())) {

                //doesn't need local nav, dax handles fine
                if (activateRuneDragonPrayer() &&
                        PathingUtil.walkToArea(RUNE_DRAGON_AREA, false))
                    Timer.waitCondition(() -> RUNE_DRAGON_AREA.contains(Player.getPosition()), 3000, 4000);

            }
        }

    }

    public static boolean activateRuneDragonPrayer() {
        return Skills.getActualLevel(Skills.SKILLS.PRAYER) >= 70 ?
                Prayer.enableAll(Prayer.PROTECT_FROM_MAGIC, Prayer.PIETY) :
                Prayer.enableAll(Prayer.PROTECT_FROM_MAGIC, Prayer.CHIVALRY);
    }


    public void navigateWaterBirthDungeon() {
        if (Areas.wbDungeon2.check()) {
            Log.log("[Debug]: In WB 2");
            if (PathingUtil.localNavigation(new RSTile(1918, 4367, 0)))
                PathingUtil.movementIdle();
            //if peeking
            if (Utils.clickObj("Root", "Step-over"))
                Timer.waitCondition(() -> Areas.wbDungeon3Pt4.check(), 4500, 6000);
            // go to kings
        } else if (Areas.wbDungeon3Pt4.check()) {
            Log.log("[Debug]: In WB 3 pt 4");
            // go to wbDungeon2
            if (PathingUtil.localNavigation(new RSTile(1889, 4406, 1)))
                PathingUtil.movementIdle();
            if (Utils.clickObj("Ladder", "Climb-down"))
                Timer.waitCondition(() -> Areas.wbDungeon3Pt4.check(), 4500, 6000);
        } else if (Areas.wbDungeon4Pt5.check()) {
            Log.log("[Debug]: In WB 4 pt 5");

            if (PathingUtil.localNavigation( new RSTile(1865, 4386, 2)))
                PathingUtil.movementIdle();
            if (Utils.clickObj("Ladder", "Climb-down"))
                Timer.waitCondition(() -> Areas.wbDungeon3Pt4.check(), 4500, 6000);

        }else if (Areas.wbDungeon4Pt4.check()) {
            Log.log("[Debug]: In WB 4 pt 4");

            if (PathingUtil.localNavigation(new RSTile(1812, 4394, 2)))
                PathingUtil.movementIdle();
            if (Utils.clickObj("Ladder", "Climb-down"))
                Timer.waitCondition(() -> Areas.wbDungeon3Pt4.check(), 4500, 6000);

            // go to wbDungeon3pt4
        } else if (Areas.wbDungeon3Pt3.check()) {
            Log.log("[Debug]: In WB 3 pt 3");
            if (PathingUtil.localNavigation(new RSTile(1863, 4369, 1)))
                PathingUtil.movementIdle();

            if (Utils.clickObj("Ladder", "Climb-up"))
                Timer.waitCondition(() -> Areas.wbDungeon4Pt4.check(), 4500, 6000);

        } else if (Areas.wbDungeon4Pt2.check()) {
            Log.log("[Debug]: In WB 4 pt 2");

            if (PathingUtil.localNavigation(new RSTile(1824, 4362, 2)))
                PathingUtil.movementIdle();

            if (Utils.clickObj("Ladder", "Climb-down"))
                Timer.waitCondition(() -> Areas.wbDungeon3Pt3.check(), 4500, 6000);

        } else if (Areas.wbDungeon3Pt2.check()) {
            Log.log("[Debug]: In WB 3 pt 2");
            if (PathingUtil.localNavigation(new RSTile(1803, 4369, 1)))
                PathingUtil.movementIdle();
            if (Utils.clickObj("Ladder", "Climb-up"))
                Timer.waitCondition(() -> Areas.wbDungeon4Pt2.check(), 4500, 6000);


        } else if (Areas.wbDungeon4Pt1.check()) {
            Log.log("[Debug]: In WB 4 pt 1");
            if (PathingUtil.localNavigation(new RSTile(1798, 4381, 2)))
                PathingUtil.movementIdle();
            if (Utils.clickObj("Ladder", "Climb-down"))
                Timer.waitCondition(() -> Areas.wbDungeon3Pt2.check(), 4500, 6000);

        } else if (Areas.wbDungeon3Pt1.check()) {
            Log.log("[Debug]: In WB 3 pt 1");
            if (PathingUtil.localNavigation( new RSTile(1800, 4389, 1)))
                PathingUtil.movementIdle();
            if (Utils.clickObj("Ladder", "Climb-down"))
                Timer.waitCondition(() -> Areas.wbDungeon4Pt1.check(), 4500, 6000);

        } else if (Areas.wbDungeon4Pt3.check()) {
            Log.log("[Debug]: In WB 4 pt 3");

            if (PathingUtil.localNavigation(new RSTile(1811, 4393, 2)))
                PathingUtil.movementIdle();
            if (Utils.clickObj("Ladder", "Climb-down"))
                Timer.waitCondition(() -> Areas.wbDungeon3Pt1.check(), 4500, 6000);

        } else if (Areas.wbDungeon5Pt2.check()) {
            Log.log("[Debug]: In WB 5 pt 2");
            if (PathingUtil.localNavigation(new RSTile(1834, 4391, 3)))
                PathingUtil.movementIdle();

            if (Utils.clickObj("Ladder", "Climb-down"))
                Timer.waitCondition(() -> Areas.wbDungeon4Pt3.check(), 4500, 6000);

        } else if (Areas.wbDungeon4Pt5.check()) {
            Log.log("[Debug]: In WB 4 pt 5");
            if (PathingUtil.localNavigation(new RSTile(1822, 4404, 2)))
                PathingUtil.movementIdle();
            if (Utils.clickObj("Ladder", "Climb-down"))
                Timer.waitCondition(() -> Areas.wbDungeon5Pt2.check(), 4500, 6000);

        } else if (Areas.wbDungeon5Pt1.check()) {
            Log.log("[Debug]: In WB 5 pt 1");
            if (PathingUtil.localNavigation(new RSTile(1807, 4404, 3)))
                PathingUtil.movementIdle();
            if (Utils.clickObj("Ladder", "Climb-up"))
                Timer.waitCondition(() -> Areas.wbDungeon4Pt5.check(), 4500, 6000);

        } else if (Areas.wbDungeon1Pt2.check() && Areas.wbDungeon1LadderArea.check()) {
            Log.log("[Debug]: In WB 1 ladder area");
            if (Utils.clickObj("Ladder", "Climb-down"))
                Timer.waitCondition(() -> Areas.wbDungeon5Pt1.check(), 4500, 6000);

        } else if (Areas.wbDungeon1Pt2.check() && !Areas.wbDungeon1LadderArea.check()) {
            Log.log("[Debug]: In WB 1 pt 2");

            if (PathingUtil.localNavigation(new RSTile(2545, 10148, 0)))
                PathingUtil.movementIdle();

        } else {
            Log.log("Going to waterbirth island");
            PathingUtil.walkToTile(PET_ROCK_DROP_TILE);
            if (PET_ROCK_DROP_TILE.isClickable() && PET_ROCK_DROP_TILE.click("Walk-here")){
                PathingUtil.movementIdle();
            }
            if (PET_ROCK_DROP_TILE.equals(Player.getPosition())) {
                // drop rock
            }


            //first part of dungeon is dax mapped
        }
    }

    RSArea bank = new RSArea(new RSTile(2772, 5129, 0), new RSTile(2758, 5145, 0));
    RSArea dungeon = new RSArea(new RSTile(3263, 9200, 2), new RSTile(3327, 9280, 2));
    RSArea chasm = new RSArea(new RSTile(3216, 9217, 0), new RSTile(3265, 9277, 0));

    AreaRequirement inBank = new AreaRequirement(bank);
    AreaRequirement inDungeon = new AreaRequirement(dungeon);
    RSArea AFTER_SLAYER_AREA = new RSArea(new RSTile(3294, 9256, 2), new RSTile(3301, 9238, 2));
    RSArea AFTER_CRUSHERS = new RSArea(new RSTile(3289, 9243, 2), new RSTile(3284, 9236, 2));
    RSTile BEFORE_LADDER_DOWN = new RSTile(3271, 9229, 2);
    RSArea LADDER_AREA_GOING_DOWN = new RSArea(new RSTile(3267, 9230, 2), new RSTile(3272, 9226, 2));
    RSArea WHOLE_UNDERGROUND_AREA = new RSArea(new RSTile(3326, 9218, 0), new RSTile(3203, 9277, 0));
    RSArea SCARAB_BANK_AREA = new RSArea(new RSTile(2793, 5174, 0), new RSTile(2806, 5158, 0));
    RSArea BEFORE_FIRST_TRAP = new RSArea(new RSTile(3297, 9267, 2), new RSTile(3295, 9268, 2));

    // whole area before first trap
    RSArea MAZE_PART_ONE = new RSArea(
            new RSTile[]{
                    new RSTile(3322, 9276, 2),
                    new RSTile(3295, 9276, 2),
                    new RSTile(3295, 9267, 2),
                    new RSTile(3298, 9267, 2),
                    new RSTile(3298, 9270, 2),
                    new RSTile(3307, 9270, 2),
                    new RSTile(3307, 9266, 2),
                    new RSTile(3312, 9266, 2),
                    new RSTile(3312, 9263, 2),
                    new RSTile(3321, 9263, 2)
            }
    );

    RSArea BETWEEN_FIRST_AND_SECOND_TRAP = new RSArea(
            new RSTile[]{
                    new RSTile(3298, 9267, 2),
                    new RSTile(3298, 9269, 2),
                    new RSTile(3307, 9269, 2),
                    new RSTile(3307, 9266, 2),
                    new RSTile(3310, 9266, 2),
                    new RSTile(3310, 9261, 2),
                    new RSTile(3323, 9261, 2),
                    new RSTile(3323, 9253, 2),
                    new RSTile(3321, 9253, 2),
                    new RSTile(3320, 9258, 2),
                    new RSTile(3306, 9258, 2)
            }
    );
    RSArea BETWEEN_SECOND_TRAP_AND_FIGHT_AREA = new RSArea(
            new RSTile[]{
                    new RSTile(3321, 9253, 2),
                    new RSTile(3323, 9253, 2),
                    new RSTile(3323, 9245, 2),
                    new RSTile(3311, 9245, 2),
                    new RSTile(3309, 9250, 2),
                    new RSTile(3304, 9250, 2),
                    new RSTile(3304, 9253, 2),
                    new RSTile(3310, 9253, 2),
                    new RSTile(3310, 9256, 2),
                    new RSTile(3319, 9256, 2),
                    new RSTile(3319, 9253, 2)
            }
    );

    ItemRequirement lightSource = new ItemRequirement(ItemID.LIT_CANDLE, 1);

    ObjectStep goDownToBank = new ObjectStep(20275, new RSTile(3309, 2798, 0),
                "Climb-down", lightSource);

    ObjectStep goDownToDungeon = new ObjectStep(20340, new RSTile(2766, 5129, 0),
                "Climb-down", lightSource);

    public void checkLightSource() {
        if (!lightSource.check() && Utils.useItemOnItem(ItemID.TINDERBOX, ItemID.CANDLE))
            Timer.waitCondition(() -> lightSource.check(), 1200, 2000);
    }

    public void checkPrayer(boolean on) {
        if (Combat.getHPRatio() < 40) {
            EatUtil.eatFood();
        }
        if (on) {
            if (org.tribot.api2007.Prayer.getPrayerPoints() < 10)
                PrayerUtil.drinkPrayerPotion();
            if (org.tribot.api2007.Prayer.getPrayerPoints() > 0)
                org.tribot.api2007.Prayer.enable(org.tribot.api2007.Prayer.PRAYERS.PROTECT_FROM_MAGIC);
        } else {
            org.tribot.api2007.Prayer.disable(org.tribot.api2007.Prayer.PRAYERS.PROTECT_FROM_MAGIC);
        }
    }

    public void goToScarabMages(){
            checkLightSource();

            if (!inBank.check() && !inDungeon.check() &&
                    !WHOLE_UNDERGROUND_AREA.contains(Player.getPosition())) {
                Log.debug("Going to Underground bank");
                goDownToBank.execute();
                Timer.waitCondition(()->WHOLE_UNDERGROUND_AREA.contains(Player.getPosition()), 4500,5000);
            }
            if (inBank.check()) {
                General.println("[Debug]: Climbing down ladder");
                goDownToDungeon.execute();
                Timer.waitCondition(() -> Player.getPosition().getPlane() == 2 ||
                        Interfaces.isInterfaceSubstantiated(562), 6000, 9000);

                RSInterface warning = Interfaces.get(562, 17);
                if (warning != null && warning.click()) {
                    NPCInteraction.waitForConversationWindow();
                    NPCInteraction.handleConversation();
                    General.sleep(2000, 3000);
                }
            }
            checkLightSource();

            if (inDungeon.check()) {
                checkPrayer(true);

                if (!Areas.LARGE_SCARAB_FIGHT_AREA.contains(Player.getPosition())) {
                    if (MAZE_PART_ONE.contains(Player.getPosition()) &&
                            PathingUtil.localNavigation(BEFORE_FIRST_TRAP.getRandomTile(), 1)) {
                        General.println("[MoveToArea]: Going to first trap");
                        checkLightSource();
                        for (int i = 0; i < 3; i++) {
                            if (Utils.clickObject("Floor", "Search", false)) {
                                NPCInteraction.waitForConversationWindow();
                                NPCInteraction.handleConversation("Yes, I'll give it a go.");
                                Timer.waitCondition(Player::isMoving, 1500);
                            }
                            if (Player.isMoving()) {
                                Waiting.waitNormal(2000, 300);
                                Log.log("[Debug]: Breaking loop");
                                break;
                            }

                        }
                    }
                    if (BETWEEN_FIRST_AND_SECOND_TRAP.contains(Player.getPosition()) ||
                            !BEFORE_FIRST_TRAP.contains(Player.getPosition())) {
                        checkLightSource();
                        General.println("[MoveToArea]: Going to second trap");
                        if (PathingUtil.localNavigation(new RSTile(3321, 9255, 2)))
                            PathingUtil.movementIdle();
                        if (Utils.clickObject("Odd markings", "Search", false)) {
                            NPCInteraction.waitForConversationWindow();
                            NPCInteraction.handleConversation("Yes, I'll give it a go.");
                            PathingUtil.movementIdle();
                            checkPrayer(true);
                        }
                    }
                }
                checkPrayer(true);
                // might not nedd this area check if it's an else instead of if
                if (BETWEEN_SECOND_TRAP_AND_FIGHT_AREA.contains(Player.getPosition())) {
                    checkLightSource();
                    RSTile targ = new RSTile(3305, 9256, 2);
                    if (PathingUtil.localNavigation(targ))
                        Timer.waitCondition(() -> Player.getPosition().distanceTo(targ) < 4, 8000, 12000);
                }

            }


    }

    RSTile PET_ROCK_DROP_TILE = new RSTile(2490, 10164, 0);


    @Override
    public String toString() {
        return "Move to area";
    }


    @Override
    public Priority priority() {
        return Priority.MEDIUM;
    }

    @Override
    public boolean validate() {
        if (Vars.get().killingScarabs)
            return !Areas.LARGE_SCARAB_FIGHT_AREA.contains(Player.getPosition());
        return
                !Areas.UNDEAD_DRUID_AREA.contains(Player.getPosition());//!Areas.dkArea.check();//!Vars.get().safeTile.equals(Player.getPosition());

    }

    @Override
    public void execute() {
        if (Vars.get().killingScarabs){
            Log.debug("[Debug]: Move to scarb area");
            goToScarabMages();
            return;
        } else if (Vars.get().killingUndeadDruids) {
            Log.debug("[Debug]: Move to undead druid area");
            PathingUtil.walkToTile(new RSTile(1807, 9925, 0));
            org.tribot.script.sdk.Prayer.enableAll(org.tribot.script.sdk.Prayer.PROTECT_FROM_MAGIC,
                    Prayer.EAGLE_EYE);
            PathingUtil.walkToArea(Areas.UNDEAD_DRUID_AREA);
        } else {
            Log.error("Failed to get area to move to, check vars");
        }
        //goToRuneDragons();
        //navigateWaterBirthDungeon();
       /* if (Vars.get().safeTile.isClickable()) {
            Vars.get().safeTile.click("Walk here");
            if (Timer.waitCondition(() -> Player.isMoving(), 1500, 2250))
                Timer.waitCondition(() -> Vars.get().safeTile.equals(Player.getPosition()), 2000, 2250);
        } else
            PathingUtil.walkToTile(Vars.get().safeTile);*/

    }
}
