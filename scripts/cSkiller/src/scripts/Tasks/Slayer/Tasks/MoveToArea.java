package scripts.Tasks.Slayer.Tasks;

import dax.walker.utils.AccurateMouse;
import dax.walker_engine.interaction_handling.NPCInteraction;
import org.tribot.api.DynamicClicking;
import org.tribot.api.General;
import org.tribot.api2007.*;
import org.tribot.api2007.ext.Filters;
import org.tribot.api2007.types.*;
import org.tribot.script.sdk.Log;
import scripts.*;
import scripts.API.Priority;
import scripts.API.Task;
import scripts.Data.SkillTasks;
import scripts.Data.Vars;
import scripts.Tasks.Slayer.SlayerConst.Areas;
import scripts.Tasks.Slayer.SlayerUtils.SlayerVars;


public class MoveToArea implements Task {
    public int DOOR_ID = 2631;
    int LUMBRIDGE_SWAMP_ROPE_VARBIT = 279; // this varbit changes from 0-> 1 upon placing a rope in the lumbridge swamp caves.


    public void checkLumbridgeSwampVarbit() {
        if (SlayerVars.get().assignment != null) {
            SlayerVars.get().fightArea = SlayerVars.get().assignment.getArea();
            Log.log("[MoveToArea]: Determined area from assignment");
        }
        if (SlayerVars.get().targets != null && SlayerVars.get().targets.length > 0 &&
                Utils.getVarBitValue(LUMBRIDGE_SWAMP_ROPE_VARBIT) == 0) {
            // if (SlayerVars.get().targets.length() < 3)
            //   GetTask.checkGem();

            General.println("[Move]: setFightArea() -- Target is: " + SlayerVars.get().targets[0]);
            String NPC = SlayerVars.get().targets[0].toLowerCase();
            if (SlayerVars.get().fightArea.equals(Areas.CAVE_BUG_AREA)) {
                SlayerVars.get().setLumbridgeRope = true;
                General.println("[MoveToArea]: We need to add a rope for lumbridge swamp");
            } else if (SlayerVars.get().fightArea.equals(Areas.CAVE_SLIME_AREA)) {
                SlayerVars.get().setLumbridgeRope = true;
                General.println("[MoveToArea]: We need to add a rope for lumbridge swamp");
            } else
                SlayerVars.get().setLumbridgeRope = false;

        } else {
            SlayerVars.get().setLumbridgeRope = false;
        }
    }

    public void setFightArea() {
        if (SlayerVars.get().assignment != null) {
            SlayerVars.get().fightArea = SlayerVars.get().assignment.getArea();
            Log.log("[MoveToArea]: Determined area from assignment");
            return;
        }
        if (SlayerVars.get().targets != null && SlayerVars.get().targets.length > 0) {
            // if (SlayerVars.get().targets.length() < 3)
            //   GetTask.checkGem();

            General.println("[Move]: setFightArea() -- Target is: " + SlayerVars.get().targets[0]);
            String NPC = SlayerVars.get().targets[0].toLowerCase();
            General.sleep(300);
            if (NPC.contains("aberrant spectre")) {
                SlayerVars.get().fightArea = Areas.ABBERANT_SPECTRE_AREA_1;
                General.println("[Set Fight Area]: Abberant spectre area selected");

            } else if (NPC.contains("abyssal demon")) {
                SlayerVars.get().fightArea = Areas.ABYSSAL_DEMON_AREA;
                General.println("[Set Fight Area]: Abyssal demon area selected");

            } else if (NPC.contains("black demon")) {
                SlayerVars.get().fightArea = Areas.BLACK_DEMON_AREA;
                SlayerVars.get().shouldPrayMelee = true;
                General.println("[Set Fight Area]: Black demon area selected");

            } else if (NPC.contains("black dragon")) {
                SlayerVars.get().fightArea = Areas.BABY_BLACK_DRAGON_AREA;
                General.println("[Set Fight Area]: Baby Black dragons area selected");

            } else if (NPC.contains("bronze dragon")) {
                //   SlayerVars.get().fightArea = Areas.BRONZE_DRAGON_SAFE_AREA;
                SlayerVars.get().fightArea = null;
                SlayerVars.get().shouldSkipTask = true;

            } else if (NPC.contains("bloodveld")) {
                SlayerVars.get().fightArea = Areas.BLOODVELD_AREA;
                General.println("[Set Fight Area]: Bloodveld area selected");

            } else if (NPC.contains("cockatrice")) {
                SlayerVars.get().fightArea = Areas.COCKATRICE_AREA;
                General.println("[Set Fight Area]: cockatrice area selected");

            } else if (NPC.contains("elves")) {
                SlayerVars.get().fightArea = Areas.ELF_AREA;
                General.println("[Set Fight Area]: Elves area selected");

            } else if (NPC.contains("fire giant")) {
                SlayerVars.get().fightArea = Areas.FIRE_GIANT_AREA;
                General.println("[Set Fight Area]: fire Giant area selected");

            } else if (NPC.contains("fossil island")) {
                SlayerVars.get().shouldSkipTask = true;
                General.println("[Set Fight Area]: Fossil Island Wyvern Task --> Skipping");
                SlayerVars.get().fightArea = null;
            } else if (NPC.contains("gargoyle")) {
                SlayerVars.get().fightArea = Areas.GARGOYLE_AREA;
                General.println("[Set Fight Area]: Gargoyle area selected");
            } else if (NPC.contains("dagannoth")) {
                SlayerVars.get().fightArea = Areas.DAGGANOTH_AREA;
                General.println("[Set Fight Area]: Dagganoth area selected");
            } else if (NPC.contains("hellhound")) {
                SlayerVars.get().fightArea = Areas.HELLHOUND_AREA;
                General.println("[Set Fight Area]: Hellhound area selected");
            } else if (NPC.contains("infernal mage")) {
                SlayerVars.get().fightArea = Areas.INFERNAL_MAGE_AREA;

            } else if (NPC.contains("jelly") || NPC.contains("jellies")) {
                SlayerVars.get().fightArea = Areas.JELLY_AREA;
                General.println("[Set Fight Area]: Jelly area selected");
            } else if (NPC.contains("lesser demon")) {
                SlayerVars.get().fightArea = Areas.LESSER_DEMON_AREA;

            } else if (NPC.contains("mutated zygomite")) {
                SlayerVars.get().fightArea = Areas.MUTATED_ZYGOMITE_AREA;
                General.println("[Set Fight Area]: Zygomite area selected");
            } else if (NPC.contains("nechryael")) {


            } else if (NPC.contains("pyrefiend")) {
                SlayerVars.get().fightArea = Areas.PYREFIEND_AREA;

            } else if (NPC.contains("turoth")) {
                SlayerVars.get().fightArea = Areas.TUROTH_AREA;
                General.println("[Set Fight Area]: Turoth area selected");
            } else if (NPC.contains("kurask")) {
                SlayerVars.get().fightArea = Areas.KURASK_AREA;
                General.println("[Set Fight Area]: Kurask area selected");
            } else if (NPC.contains("wall beast")) {
                SlayerVars.get().fightArea = Areas.WALL_BEAST_AREA;

            } else if (NPC.contains("wyrm")) {
                SlayerVars.get().fightArea = Areas.WYRM_AREA;
                General.println("[Set Fight Area]: Wyrm area selected");
            } else if (NPC.contains("bears"))
                SlayerVars.get().fightArea = Areas.BEAR_AREA;

            else if (NPC.contains("bat"))
                SlayerVars.get().fightArea = Areas.BAT_AREA;

            else if (NPC.contains("bird"))
                SlayerVars.get().fightArea = Areas.BIRD_AREA;

            else if (NPC.contains("dwarves"))
                SlayerVars.get().fightArea = Areas.DWARF_AREA;

            else if (NPC.contains("scorpion"))
                SlayerVars.get().fightArea = Areas.SCORPION_AREA;

            else if (NPC.contains("skeleton"))
                SlayerVars.get().fightArea = Areas.SKELETON_AREA;

            else if (NPC.contains("spiders"))
                SlayerVars.get().fightArea = Areas.SPIDER_AREA;

            else if (NPC.contains("ankou")) {
                SlayerVars.get().fightArea = Areas.ANKOU_AREA;
                General.println("[Set Fight Area]: Ankou area selected");
            } else if (NPC.contains("banshee"))
                SlayerVars.get().fightArea = Areas.BANSHEE_AREA;

            else if (NPC.contains("basilisk"))
                SlayerVars.get().fightArea = Areas.BASILISK_AREA;

            else if (NPC.contains("blue dragon")) {
                SlayerVars.get().fightArea = Areas.BLUE_DRAGON_AREA;
                General.println("[Set Fight Area]: Blue dragon area selected");
            } else if (NPC.contains("cave bug")) {
                SlayerVars.get().fightArea = Areas.CAVE_SLIME_AREA; // slime area avoids the wall beasts that grab you
                SlayerVars.get().setLumbridgeRope = true;
                General.println("[Set Fight Area]: Cavebug (using slime) area selected");
            } else if (NPC.contains("cave crawler")) {
                General.println("[Set fight area]: Cave crawler area");
                SlayerVars.get().fightArea = Areas.CAVE_CRAWLER_AREA;

            } else if (NPC.contains("cave slime")) {
                SlayerVars.get().fightArea = Areas.CAVE_SLIME_AREA;
                SlayerVars.get().setLumbridgeRope = true;
            } else if (NPC.contains("cow")) {
                SlayerVars.get().fightArea = Areas.COW_AREA;

            } else if (NPC.contains("ogre")) {
                SlayerVars.get().fightArea = Areas.OGRE_AREA;

            } else if (NPC.contains("crawling hand")) {
                SlayerVars.get().fightArea = Areas.CRAWLING_HANDS_AREA;

            } else if (NPC.contains("crocodile")) {
                SlayerVars.get().fightArea = Areas.CROCODILE_AREA;

            } else if (NPC.contains("dog")) {
                SlayerVars.get().fightArea = Areas.JACKAL_AREA;

            } else if (NPC.contains("earth warrior")) {
                SlayerVars.get().fightArea = null;
                SlayerVars.get().shouldSkipTask = true;

            } else if (NPC.contains("harpie bug swarms")) {
                SlayerVars.get().fightArea = Areas.HARPIE_BUG_SWARM_AREA;
                General.println("[Set Fight Area]: Bug swarm area selected");
            } else if (NPC.contains("ghost")) {
                SlayerVars.get().fightArea = Areas.GHOST_AREA;
                General.println("[Set Fight Area]: ghost area selected");
            } else if (NPC.contains("ghoul")) {
                SlayerVars.get().fightArea = Areas.GHOUL_AREA;
                General.println("[Set Fight Area]: ghoul area selected");
            } else if (NPC.contains("green dragon")) {
                // SlayerVars.get().fightArea = Areas.GREEN_DRAGON_AREA;
                SlayerVars.get().fightArea = null;
                SlayerVars.get().shouldSkipTask = true;

            } else if (NPC.contains("goblin") && !NPC.contains("hobgoblin")) {
                SlayerVars.get().fightArea = Areas.GOBLIN_AREA;
                General.println("[Set Fight Area]: Goblin area selected");
            } else if (NPC.contains("hobgoblin")) {
                SlayerVars.get().fightArea = Areas.HOBGOBLIN_AREA;
                General.println("[Set Fight Area]: Hobgoblin area selected");
            } else if (NPC.contains("greater demon")) {
                SlayerVars.get().fightArea = Areas.GREATER_DEMON_AREA;
                General.println("[Set Fight Area]: Greater demon area selected");
            } else if (NPC.contains("hill giant")) {
                SlayerVars.get().fightArea = Areas.HILL_GIANT_AREA;
                General.println("[Set Fight Area]: Hill giant area selected");
            } else if (NPC.contains("icefiend")) {
                SlayerVars.get().fightArea = Areas.ICEFIEND_AREA;
                General.println("[Set Fight Area]: Icefiend area selected");
            } else if (NPC.contains("ice giant")) {
                SlayerVars.get().fightArea = Areas.ICE_GIANT_AREA;
                General.println("[Set Fight Area]: Ice giant area selected");
            } else if (NPC.contains("ice warrior")) {
                SlayerVars.get().fightArea = Areas.ICE_GIANT_AREA;
                General.println("[Set Fight Area]: Ice warriro area selected");
            } else if (NPC.contains("kalphite")) {
                SlayerVars.get().fightArea = Areas.KALPHITE_AREA;
                General.println("[Set Fight Area]: Kalphite area selected");
            } else if (NPC.contains("lizard")) {
                SlayerVars.get().fightArea = Areas.LIZARD_AREA;
                General.println("[Set Fight Area]: Lizard area selected");
            } else if (NPC.contains("minotaur")) {
                SlayerVars.get().fightArea = Areas.MINOTAUR_AREA;
                General.println("[Set Fight Area]: Minotaur area selected");
            } else if (NPC.contains("monkey")) {
                SlayerVars.get().fightArea = Areas.MONKEY_AREA;
                General.println("[Set Fight Area]: Monkey area selected");
            } else if (NPC.contains("moss giant")) {
                SlayerVars.get().fightArea = Areas.MOSS_GIANT_AREA;
                General.println("[Set Fight Area]: Moss giant area selected");
            } else if (NPC.contains("brine rat")) {
                SlayerVars.get().fightArea = Areas.BRINE_RAT_AREA;

            } else if (NPC.contains("rat")) {
                SlayerVars.get().fightArea = Areas.RAT_AREA;
                General.println("[Set Fight Area]: Rat area selected");
            } else if (NPC.contains("rockslug")) {
                SlayerVars.get().fightArea = Areas.ROCKSLUG_AREA;
                General.println("[Set Fight Area]: Rockslug area selected");
            } else if (NPC.contains("shade")) {
                SlayerVars.get().fightArea = Areas.SHADES_AREA;
                General.println("[Set Fight Area]: Shade area selected");

            } else if (NPC.contains("sourhog")) {
                SlayerVars.get().fightArea = Areas.SOURHOG_AREA;
                General.println("[Set Fight Area]: Sourhog area selected");
            } else if (NPC.contains("suqah")) {
                SlayerVars.get().fightArea = Areas.SUQAH_AREA;
                General.println("[Set Fight Area]: Suqah area selected");
            } else if (NPC.contains("troll")) {
                SlayerVars.get().fightArea = Areas.TROLL_AREA;
                General.println("[Set Fight Area]: Troll area selected");

            } else if (NPC.contains("tzhaar")) {
                SlayerVars.get().fightArea = Areas.TZHARR_AREA;
                General.println("[Set Fight Area]: tzhaar area selected");

            } else if (NPC.contains("werewolves")) {
                SlayerVars.get().fightArea = Areas.WEREWOLF_AREA;

            } else if (NPC.contains("wolves")) {
                SlayerVars.get().fightArea = Areas.WOLF_AREA;
                General.println("[Set Fight Area]: Wolves area selected");
            } else if (NPC.contains("zombie")) {
                SlayerVars.get().fightArea = Areas.ZOMBIE_AREA;
                General.println("[Set Fight Area]: Zombie area selected");
            } else if (SlayerVars.get().fightArea == null) {
                General.println("[Set Fight Area]: Could not generate a fight area");
                for (int i = 0; i < 5; i++) {
                    if (SlayerVars.get().fightArea != null)
                        break;

                    if (i == 4) {
                        General.println("Failed to generate an area 5x, ending script");
                        cSkiller.isRunning.set(false);
                        break;
                    } else {
                        GetTask.checkGem();
                        setFightArea();
                    }
                }
            }

        }

    }

    public void goToEarthWarriors() {
        if (!Areas.EARTH_WARRIOR_AREA.contains(Player.getPosition()))
            PathingUtil.walkToArea(Areas.PRE_EARTH_WARRIOR_AREA);

        if (Utils.clickObject("Monkeybars", "Swing across", false))
            Timer.waitCondition(() -> Areas.EARTH_WARRIOR_AREA.contains(Player.getPosition()), 15000);
    }

    public void setupLumbridgeSwamp() {
        if (RSVarBit.get(LUMBRIDGE_SWAMP_ROPE_VARBIT).getValue() == 0) {
            General.println("[Debug]: Setting up lumbridge swamp cave");
            if (Inventory.find(ItemID.ROPE).length < 1) {
                BankManager.open(true);
                BankManager.withdraw(1, true, ItemID.ROPE);
                BankManager.close(true);
            }
            PathingUtil.walkToArea(Areas.ABOVE_LUMBRIDGE_SWAMP_ENTRANCE);
            if (Utils.useItemOnObject(ItemID.ROPE, "Dark hole")) {
                Timer.waitCondition(() -> Inventory.find(ItemID.ROPE).length < 1, 6000, 9000);
                NPCInteraction.handleConversation();
                if (NPCInteraction.isConversationWindowUp())
                    Keyboard.typeString(" ");
                SlayerVars.get().setLumbridgeRope = false;
            }
        } else {
            SlayerVars.get().setLumbridgeRope = false;
        }
    }

    public void goToArea() {
        if (SlayerVars.get().fightArea != null) {
            General.println("[Debug]: Moving to Fight Area: " + SlayerVars.get().fightArea.toString());
            if (SlayerVars.get().fightArea == Areas.BLACK_DEMON_AREA && Prayer.getPrayerPoints() > 0) {
                General.println("Prayer walk");
                if (PathingUtil.walkToArea(SlayerVars.get().fightArea, false, "Black demon walk"))
                    PrayerUtil.setPrayer(PrayerType.MELEE);
            } else
                PathingUtil.walkToArea(SlayerVars.get().fightArea, false);

            if (SlayerVars.get().fightArea.contains(Player.getPosition()) && SlayerVars.get().shouldPrayMelee)
                Prayer.enable(Prayer.PRAYERS.PROTECT_FROM_MELEE);
        }
    }


    public static void handleCandleMessage(String message) {
        if (message.contains("candle flares brightly") || message.contains("swamp gas explodes")) {
            General.println("[Message Listener]: Candle message received");
            Timer.waitCondition(() -> Inventory.find(32).length > 0, 3500, 5000);
            Utils.useItemOnItem(590, 32);
        }
    }


    public void getDustyKey() {
        if (SlayerVars.get().needDustyKey) {
            SlayerVars.get().status = "Getting Dusty Key";
            General.println("[Debug]: Getting a dusty key");
            if (Inventory.find(ItemID.DUSTY_KEY).length < 1) {
                SlayerVars.get().status = "Getting Dusty Key";

                PathingUtil.walkToArea(Areas.JAILER_AREA);
                RSNPC[] jailer = NPCs.findNearest("Jailer");
                if (jailer.length > 0 && CombatUtil.clickTarget(jailer[0]))
                    Timer.waitCondition(() -> GroundItems.find(ItemID.JAIL_KEY).length > 0
                            || Combat.getHPRatio() < 35, 30000, 45000);

                if (Combat.getHPRatio() < 35 && Inventory.find(ItemID.FOOD_IDS).length > 0)
                    if (AccurateMouse.click(Inventory.find(ItemID.FOOD_IDS)[0], "Eat"))
                        Utils.idle(150, 600);

                if (GroundItems.find(ItemID.JAIL_KEY).length > 0) {
                    if (Inventory.isFull() && Inventory.find(ItemID.FOOD_IDS).length > 0)
                        if (AccurateMouse.click(Inventory.find(ItemID.FOOD_IDS)[0], "Eat"))
                            Utils.idle(150, 600);

                    if (AccurateMouse.click(GroundItems.find(ItemID.JAIL_KEY)[0], "Take"))
                        Timer.waitCondition(() -> Inventory.find(ItemID.JAIL_KEY).length > 0, 9000, 12000);
                }

                if (Inventory.find(ItemID.JAIL_KEY).length > 0 && Areas.JAILER_AREA.contains(Player.getPosition())) {
                    if (PathingUtil.localNavigation(new RSTile(2931, 9690, 0)))
                        Utils.shortSleep();
                    if (Utils.useItemOnObject(ItemID.JAIL_KEY, DOOR_ID))
                        Timer.waitCondition(() -> Areas.JAIL_CELL.contains(Player.getPosition()), 8000, 12000);
                }
                if (Areas.JAIL_CELL.contains(Player.getPosition())) {
                    if (Inventory.isFull() && Inventory.find(ItemID.FOOD_IDS).length > 0)
                        if (EatUtil.eatFood(false))
                            Utils.microSleep();

                    if (NpcChat.talkToNPC("Velrak")) {
                        NPCInteraction.waitForConversationWindow();
                        NPCInteraction.handleConversation("So... do you know anywhere good to explore?");
                        NPCInteraction.handleConversation("Yes please!");
                        NPCInteraction.handleConversation();
                        Utils.shortSleep();
                    }
                    if (Inventory.find(ItemID.DUSTY_KEY).length > 0) {
                        SlayerVars.get().needDustyKey = false;
                        if (Utils.useItemOnObject(ItemID.JAIL_KEY, DOOR_ID))
                            Timer.waitCondition(() -> !Areas.JAIL_CELL.contains(Player.getPosition()), 8000, 12000);
                    }
                }
            } else {
                SlayerVars.get().needDustyKey = false;
            }
        }
    }

    RSArea area = new RSArea(new RSTile(2882, 9817, 1), new RSTile(2872, 9835, 1));

    public boolean goToBabyBlackDragons() {
        RSNPC[] npc = NPCs.find(Filters.NPCs.nameContains("Baby black"));
        if (npc.length == 0 && !Areas.BABY_BLACK_DRAGON_AREA.contains(Player.getPosition()) &&
                !area.contains(Player.getPosition())) {
            General.println("Going to Baby Black Dragons");
            PathingUtil.walkToArea(Areas.BEFORE_BABY_BLACK_DRAG_AREA, true);

            if (Utils.clickObject("Steps", "Climb", false)) {
                Timer.waitCondition(() -> area.contains(Player.getPosition()), 5000, 7000);
            }

        }
        if (area.contains(Player.getPosition())) {
            PathingUtil.webWalkToArea(Areas.BABY_BLACK_DRAGON_AREA);
        }
        return true;
    }

    public static boolean areWeCloseToArea(RSArea area) {
        RSTile[] areaTiles = SlayerVars.get().fightArea.getAllTiles();
        int lastIndex = areaTiles.length - 1;

        if (areaTiles[0].distanceTo(Player.getPosition()) < 30) {
            return true;

        } else if (areaTiles[lastIndex].distanceTo(Player.getPosition()) < 30) {
            return true;

        } else
            return false;
    }

    public void goToSourhogs() {
        if (!Areas.SOURHOG_AREA.contains(Player.getPosition()) &&
                !Areas.WHOLE_SOURHOG_CAVE.contains(Player.getPosition())) {
            PathingUtil.walkToArea(Areas.SOURHOG_ENTRANCE_AREA, false);
            if (Utils.clickObject("Strange hole", "Climb-down", false)) {
                Timer.waitCondition(() -> Areas.WHOLE_SOURHOG_CAVE.contains(Player.getPosition()),
                        9000, 11000);
            }
        }
        if (Areas.WHOLE_SOURHOG_CAVE.contains(Player.getPosition()) &&
                !Areas.SOURHOG_AREA.contains(Player.getPosition())) {
            // in area, but not fight area
            if (Utils.clickObject("Blockage", "Climb-over", false)) {
                Timer.waitCondition(() -> Areas.SOURHOG_AREA.contains(Player.getPosition()),
                        8000, 9000);
            }

        }
    }


    public void goToBrineRats() {
        RSItem[] spade = Inventory.find(ItemID.SPADE);
        if (spade.length > 0) {
            if (!Areas.BRINE_RAT_AREA.contains(Player.getPosition()) &&
                    !Areas.WHOLE_BRINE_RAT_CAVE.contains(Player.getPosition())) {
                PathingUtil.walkToTile(new RSTile(2745, 3732, 0), 1, false);
                if (spade[0].click("Dig")) {
                    Timer.waitCondition(() -> Areas.WHOLE_BRINE_RAT_CAVE.contains(Player.getPosition()),
                            9000, 11000);
                }
            }
            if (Areas.WHOLE_BRINE_RAT_CAVE.contains(Player.getPosition()) &&
                    !Areas.BRINE_RAT_AREA.contains(Player.getPosition())) {
                // in area, but not fight area
                PathingUtil.walkToArea(Areas.BRINE_RAT_AREA, false);
            }
        }
    }


    public void goToZygomites() {
        RSItem weapon = Equipment.getItem(Equipment.SLOTS.WEAPON);

        if (!Areas.WHOLE_ZANARIS.contains(Player.getPosition())) {
            General.println("[MoveToArea]: Going to zanaris");
            Utils.equipItem(ItemID.DRAMEN_STAFF);
            shouldMoveDoubleCheck(Areas.MUTATED_ZYGOMITE_AREA);

        }
        RSItem[] i = Inventory.find(weapon.getID());
        if (Areas.WHOLE_ZANARIS.contains(Player.getPosition()) && i.length > 0) {
            General.println("[MoveToArea]: Re-equipping weapon");
            if (i[0].click())
                Timer.waitCondition(() -> Equipment.isEquipped(weapon.getID()), 3500, 5000);
        }
    }

    public static boolean clickScreenWalkArea(RSArea area) {
        for (RSTile t : area.getAllTiles()) {
            if (t.isClickable())
                return DynamicClicking.clickRSTile(t, "Walk here");
        }
        return false;
    }


    public boolean shouldMoveDoubleCheck(RSArea area) {
        RSTile[] areaTiles = SlayerVars.get().fightArea.getAllTiles();
        int lastIndex = areaTiles.length - 1;
        if (!clickScreenWalkArea(area)) {
            if (areWeCloseToArea(area) && PathFinding.canReach(areaTiles[0], false)) {
                General.println("[Debug]: We are close to our area, don't need to move with Dax");
                if (!PathingUtil.localNavigation(area.getRandomTile()))
                    return false;

                return true;
            } else if (areaTiles[lastIndex].distanceTo(Player.getPosition()) < 25) {
                General.println("[Debug]: We are close to our area, don't need to move with Dax");
                if (!PathingUtil.localNavigation(area.getRandomTile())) {
                    return false;

                } else
                    return true;
            }
        }

        return false;
    }

    public void goToElves() {
        RSItem[] i = Inventory.find(ItemID.IORWERTH_CAMP_TELEPORT);
        if (Banking.isInBank() && i.length > 0) {
            RSTile current = Player.getPosition();
            if (i[0].click())
                Timer.abc2WaitCondition(() -> !current.equals(Player.getPosition()), 7000, 10000);
        }
    }


    public static boolean checkForOtherCannon() {
        if (SlayerVars.get().use_cannon && SlayerVars.get().fightArea != null) {
            if (SlayerVars.get().fightArea.getRandomTile().distanceTo(Player.getPosition()) < 35) {
                RSObject[] cannon = Objects.findNearest(30, Filters.Objects.inArea(SlayerVars.get().fightArea));
                RSItem[] inv = Inventory.find(ItemID.CANNON_IDS);
                if (cannon.length > 0 && inv.length == 4) {
                    General.println("[MoveToArea] There appears to be a cannon that is not ours in our fight area.");
                    return SlayerVars.get().otherCannonInOurArea = true;
                }
            }
        }
        return SlayerVars.get().otherCannonInOurArea = false;
    }


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
        if (SlayerVars.get().fightArea == null) {
            setFightArea();
        }
        return Vars.get().currentTask != null &&
                Vars.get().currentTask.equals(SkillTasks.SLAYER) &&
                SlayerVars.get().fightArea != null &&
                !SlayerVars.get().shouldBank &&
                !SlayerVars.get().fightArea.contains(Player.getPosition()) &&
                !SlayerShop.shouldSlayerShop();
    }

    @Override
    public void execute() {
        if (SlayerVars.get().fightArea == null) {
            setFightArea();
            return;
        }
        getDustyKey();

        checkLumbridgeSwampVarbit();
        if (SlayerVars.get().setLumbridgeRope)
            setupLumbridgeSwamp();
        if (SlayerVars.get().fightArea.equals(Areas.SOURHOG_AREA)) {
            goToSourhogs();
            return;
        }

        PathingUtil.walkToArea(SlayerVars.get().fightArea, false);
    }

    @Override
    public String taskName() {
        return "Slayer";
    }
}
