package scripts.QuestPackages.LostCity;

import dax.walker.utils.AccurateMouse;
import dax.walker.utils.camera.DaxCamera;
import dax.walker_engine.interaction_handling.NPCInteraction;
import org.tribot.api.Clicking;
import org.tribot.api.General;
import org.tribot.api2007.*;
import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSNPC;
import org.tribot.api2007.types.RSObject;
import org.tribot.api2007.types.RSTile;
import org.tribot.script.sdk.Log;
import org.tribot.script.sdk.Waiting;
import scripts.*;
import scripts.GEManager.GEItem;
import scripts.QuestPackages.GrandTree.GrandTree;
import scripts.QuestSteps.BuyItemsStep;
import scripts.QuestSteps.QuestTask;
import scripts.Requirements.ItemRequirement;
import scripts.Requirements.Requirement;
import scripts.Tasks.Priority;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LostCity implements QuestTask {
    private static LostCity quest;

    public static LostCity get() {
        return quest == null ? quest = new LostCity() : quest;
    }




    int KNIFE = 946;
    int BRONZE_AXE = 1351;
    int AIR_RUNE = 556;
    int MIND_RUNE = 558;
    int FIRE_RUNE = 554;
    int LOBSTER = 379;
    int LUMBRIDGE_TAB = 8008;
    int DRAMEN_BRANCH = 771;
    int DRAMEN_STAFF = 772;

    RSArea START_AREA = new RSArea(new RSTile(3147, 3209, 0), new RSTile(3155, 3202, 0));
    RSArea FAKE_TREE_AREA = new RSArea(new RSTile(3141, 3213, 0), new RSTile(3135, 3209, 0));
    RSArea PORT_SARIM_AREA = new RSArea(new RSTile(3040, 3237, 0), new RSTile(3052, 3234, 0));
    RSArea DUNGEON_ENTRANCE = new RSArea(new RSTile(2821, 3372, 0), new RSTile(2819, 3375, 0));
    RSArea ALL_UNDERGROUND = new RSArea(new RSTile(2820, 9780, 0), new RSTile(2878, 9729, 0));
    RSArea ZOMBIE_AREA = new RSArea(new RSTile(2839, 9757, 0), new RSTile(2848, 9772, 0));
    RSArea TREE_AREA = new RSArea(new RSTile(2854, 9735, 0), new RSTile(2863, 9729, 0));
    RSArea SAFE_AREA_ZOMBIE = new RSArea(new RSTile(2840, 9766, 0), new RSTile(2840, 9767, 0));
    RSArea SAFE_AREA_TREE = new RSArea(new RSTile(2859, 9731, 0), new RSTile(2858, 9731, 0));
    RSArea ENTRANA = new RSArea(new RSTile(2881, 3319, 0), new RSTile(2794, 3396, 0));
    RSArea ENTRANA_LEVEL_2 = new RSArea(new RSTile(2863, 3319, 1), new RSTile(2805, 3363, 1));

    ArrayList<GEItem> itemsToBuy = new ArrayList<GEItem>(
            Arrays.asList(
                    new GEItem(ItemID.KNIFE, 1, 500),
                    new GEItem(ItemID.BRONZE_AXE, 1, 500),
               new GEItem(ItemID.MIND_RUNE, 300, 20),
                    new GEItem(ItemID.AIR_RUNE, 900, 20),
                    new GEItem(ItemID.FIRE_RUNE, 900, 20),
                    new GEItem(ItemID.LOBSTER, 20, 50),
                    new GEItem(ItemID.AMULET_OF_GLORY[2], 2, 30),
                    new GEItem(ItemID.STAMINA_POTION[0], 3, 15),
                    new GEItem(ItemID.RING_OF_WEALTH[0], 1, 25)
            )
    );

    public void buyItems() {
        cQuesterV2.status = "Buying Items";
        General.println("[Debug]: " + cQuesterV2.status);
        BuyItemsStep buyStep = new BuyItemsStep(itemsToBuy);
        buyStep.buyItems();
    }

    public void getItems1() {
        cQuesterV2.status = "Getting Items";
        General.println("[Debug]: " + cQuesterV2.status);
        BankManager.open(true);
        BankManager.depositAll(true);
        BankManager.checkEquippedGlory();
        BankManager.withdraw(1, true, KNIFE);
        BankManager.withdraw(1, true, BRONZE_AXE);
        BankManager.withdraw(3, true, LUMBRIDGE_TAB);
        BankManager.withdraw(1, true,
                ItemID.AMULET_OF_GLORY[0]);
        BankManager.withdraw(1, true, BankManager.STAMINA_POTION[0]);
        BankManager.close(true);
    }


    public void startQuest() {
        cQuesterV2.status = "Going to start";
        General.println("[Debug]: " + cQuesterV2.status);
        PathingUtil.walkToArea(START_AREA);

        if (NpcChat.talkToNPC("Warrior")) {
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation("What are you camped out here for?");
            NPCInteraction.handleConversation("Who's Zanaris?");
            NPCInteraction.handleConversation("If it's hidden how are you planning to find it?");
            NPCInteraction.handleConversation("Looks like you don't know either.");
            NPCInteraction.handleConversation();
        }
    }

    public void step2() {
        cQuesterV2.status = "Going to Tree";
        General.println("[Debug]: " + cQuesterV2.status);
        PathingUtil.walkToArea(FAKE_TREE_AREA);
        if (FAKE_TREE_AREA.contains(Player.getPosition())) {
            if (Utils.clickObj(2409, "Chop"))
                Timer.abc2WaitCondition(() -> NPCs.find("Shamus").length > 0, 5000, 9000);

            if (NpcChat.talkToNPC("Shamus")) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation("How does it fit in a shed then?");
                NPCInteraction.handleConversation();
            }
        }
    }

    public void step3() {
        if (!PORT_SARIM_AREA.contains(Player.getPosition()) && !ENTRANA_LEVEL_2.contains(Player.getPosition())
                && !ENTRANA.contains(Player.getPosition()) && !ALL_UNDERGROUND.contains(Player.getPosition())) {
            BankManager.open(true);
            BankManager.depositEquipment();
            BankManager.depositAll(true);
            BankManager.checkEquippedGlory();
            BankManager.withdraw(1, true, KNIFE);
            BankManager.withdraw(10, true, LOBSTER);
            BankManager.withdraw(300, true, MIND_RUNE);
            BankManager.withdraw(900, true, AIR_RUNE);
            BankManager.withdraw(900, true, FIRE_RUNE);
            BankManager.withdraw(3, true, LUMBRIDGE_TAB);
            BankManager.withdraw(1, true, ItemID.AMULET_OF_GLORY[0]);
            BankManager.withdraw(1, true, BankManager.STAMINA_POTION[0]);
        }

    }

    public void magicIdle() {
        Timer.waitCondition(() -> Player.getAnimation() == 711, 4000, 6000);
    }

    RSTile TREE_SPIRIT_SAFE_TILE = new RSTile(2859, 9731, 0);

    public void step4() {
        if (!PORT_SARIM_AREA.contains(Player.getPosition()) && !ENTRANA_LEVEL_2.contains(Player.getPosition())
                && !ENTRANA.contains(Player.getPosition()) && !ALL_UNDERGROUND.contains(Player.getPosition())) {
            cQuesterV2.status = "Going to Port Sarim";
            General.println("[Debug]: " + cQuesterV2.status);
            PathingUtil.walkToArea(PORT_SARIM_AREA);

        } else if (PORT_SARIM_AREA.contains(Player.getPosition())) {
            if (Utils.clickNPC("Monk of Entrana", "Take-boat")) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation();
                Utils.idle(8000, 12000);
            }
            if (Utils.clickObj("Gangplank", "Cross"))
                Utils.idle(2000, 4500);

        } else if (ENTRANA.contains(Player.getPosition())) {
            cQuesterV2.status = "Going to Dungeon";
            General.println("[Debug]: " + cQuesterV2.status);
            PathingUtil.walkToArea(DUNGEON_ENTRANCE);

            if (Utils.clickObj(2408, "Climb-down")) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation("Well that is a risk I will have to take.");
                NPCInteraction.handleConversation();
                Timer.abc2WaitCondition(() -> !DUNGEON_ENTRANCE.contains(Player.getPosition()), 8000, 15000);
            }

        } else if (ALL_UNDERGROUND.contains(Player.getPosition()) && Inventory.find(BRONZE_AXE).length < 1 && !ZOMBIE_AREA.contains(Player.getPosition())) {
            cQuesterV2.status = "Going to Zombies";
            General.println("[Debug]: " + cQuesterV2.status);
            Walking.blindWalkTo(new RSTile(2840, 9766, 0));
            Utils.idle(4000, 6000);

        } else if (ZOMBIE_AREA.contains(Player.getPosition()) && Inventory.find(BRONZE_AXE).length < 1) {
            cQuesterV2.status = "Attacking Zombies";
            General.println("[Debug]: " + cQuesterV2.status);

            while (Inventory.find(BRONZE_AXE).length < 1) {
                General.sleep(50);

                if (!SAFE_AREA_ZOMBIE.contains(Player.getPosition())) {
                    Walking.blindWalkTo(ZOMBIE_SAFE_TILE);
                    Utils.idle(2500, 4000);
                }

                RSNPC[] target = NPCs.findNearest("Zombie");
                if (target.length > 0 && GroundItems.find(BRONZE_AXE).length == 0) {
                    if (!target[0].isClickable())
                        DaxCamera.focus(target[0]);

                    if (!Magic.isSpellSelected() && Magic.selectSpell("Fire Strike")) {
                        AccurateMouse.click(target[0], "Cast");
                        magicIdle();

                    } else {
                        AccurateMouse.click(target[0], "Cast");
                        magicIdle();
                    }
                }
                if (GroundItems.find(BRONZE_AXE).length > 0) {
                    Utils.clickGroundItem(BRONZE_AXE);
                    break;
                } else if (Combat.getHPRatio() < General.random(35, 65) && Inventory.find(LOBSTER).length > 0) {
                    if (AccurateMouse.click(Inventory.find(LOBSTER)[0], "Eat"))
                        Utils.idle(300, 600);
                }
            }

        } else if (ZOMBIE_AREA.contains(Player.getPosition()) && Inventory.find(BRONZE_AXE).length > 0) {
            cQuesterV2.status = "Going to Tree";
            General.println("[Debug]: " + cQuesterV2.status);
            Utils.blindWalkToTile(new RSTile(2860, 9747, 0));
            PathingUtil.blindWalkToArea(TREE_AREA);

        } else if (TREE_AREA.contains(Player.getPosition()) && Inventory.find(BRONZE_AXE).length > 0 && Inventory.find(DRAMEN_BRANCH).length < 1) {
            cQuesterV2.status = "Attacking Tree Spirit";
            General.println("[Debug]: " + cQuesterV2.status);
            RSNPC[] treeSpirit = NPCs.findNearest("Tree spirit");

            if (treeSpirit.length < 1) {
                if (AccurateMouse.click(Objects.find(20, 1292)[0], "Chop down"))
                    Timer.waitCondition(() -> NPCs.findNearest("Tree spirit").length > 0, 8000, 12000);
            }

            treeSpirit = NPCs.findNearest("Tree spirit");
            if (treeSpirit.length > 0) {
                General.sleep(50);
                if (!TREE_SPIRIT_SAFE_TILE.equals(Player.getPosition())) {
                    PathingUtil.clickScreenWalk(TREE_SPIRIT_SAFE_TILE);
                    Utils.idle(2500, 4000);
                }

                if (Magic.selectSpell("Fire Strike"))
                    try {
                        if (!treeSpirit[0].isClickable())
                            treeSpirit[0].adjustCameraTo();

                        if (AccurateMouse.click(treeSpirit[0], "Cast"))
                            magicIdle();

                    } catch (Exception e) {

                    }
                if (Game.getSetting(147) == 3) {
                    Utils.modSleep();

                } else if (Combat.getHPRatio() < General.random(35, 65)) {
                    if (AccurateMouse.click(Inventory.find(LOBSTER)[0], "Eat"))
                        Utils.microSleep();
                }
            }

        }
    }


    RSTile ZOMBIE_SAFE_TILE = new RSTile(2840, 9766, 0);


    RSArea SWAMP_AREA = new RSArea(new RSTile(3201, 3170, 0), new RSTile(3199, 3167, 0));
    RSArea SHED_AREA = new RSArea(new RSTile(3196, 3174, 0), new RSTile(3214, 3161, 0));
    int DRAMEN_TREE_ID = 1292;

    public void getBranches() {
        RSObject[] tree = Objects.findNearest(20, DRAMEN_TREE_ID);
        if (tree.length > 0)  //use to deslect spell
            if (Magic.isSpellSelected())
                Clicking.click(tree[0]);

        Log.log("[debug]: Getting branches");
        for (int i = 0; i < 7; i++) {
            int b = i;
            if (Utils.clickObj(DRAMEN_TREE_ID, "Chop down"))
                Timer.waitCondition(() -> Inventory.find(DRAMEN_BRANCH).length > b, 6000, 8000);

            if (Inventory.isFull())
                break;
        }
        Waiting.waitNormal(500,100);
    }

    public void step5() {
        if (Inventory.find(DRAMEN_BRANCH).length > 1) {
            PathingUtil.walkToArea(SWAMP_AREA);
        } else {
            getBranches();
        }
    }

    public void finishQuest() {
        Log.log("[Debug]: Going To Shed");
        PathingUtil.walkToArea(SWAMP_AREA);
        if (SWAMP_AREA.contains(Player.getPosition())) {
            if (Inventory.find(DRAMEN_STAFF).length < 1 && Utils.useItemOnItem(KNIFE, DRAMEN_BRANCH)) {
                Timer.waitCondition(() -> Inventory.find(DRAMEN_BRANCH).length > 0, 8000, 12000);
                Utils.idle(250, 800);
            }
            if (Inventory.find(DRAMEN_STAFF).length > 0) {
                Utils.equipItem(DRAMEN_STAFF);
                Waiting.waitNormal(500,50);
            }
            if (Utils.equipItem(DRAMEN_STAFF) && Utils.clickObj(2406, "Open"))
                Timer.abc2WaitCondition(() -> Game.getSetting(147) == 6, 9000, 15000);
        }
    }

    public static void checkLevel() {

    }

    int GAME_SETTING = 147;

    @Override
    public void execute() {
        if (!checkRequirements()){
            cQuesterV2.taskList.remove(this);
            return;
        }

        if (Game.getSetting(147) == 0) {
            buyItems();
            getItems1();
            startQuest();
        }
        if (Game.getSetting(147) == 1) {
            step2();
            Utils.longSleep();
        }
        if (Game.getSetting(147) == 2) {
            step3();
            step4();
        }
        if (Game.getSetting(147) == 3) {
            getBranches();
            Utils.longSleep();
        }
        if (Game.getSetting(147) == 4) {
            step5();
            finishQuest();
        }
        if (Game.getSetting(147) == 5) {
            finishQuest();
        }
        if (Game.getSetting(147) == 6) {
            Utils.closeQuestCompletionWindow();
            cQuesterV2.taskList.remove(this);
        }

    }

    @Override
    public Priority priority() {
        return Priority.LOW;
    }

    @Override
    public boolean validate() {
        return cQuesterV2.taskList.get(0).equals(this);
    }


    @Override
    public String questName() {
        return "Lost City";
    }

    @Override
    public boolean checkRequirements() {
        if (Skills.getActualLevel(Skills.SKILLS.WOODCUTTING) < 36 ||
                Skills.getActualLevel(Skills.SKILLS.CRAFTING) < 31 ||
                Skills.getActualLevel(Skills.SKILLS.MAGIC) < 13 ||
                Skills.getActualLevel(Skills.SKILLS.HITPOINTS) < 20) {
            return false;
        }
        return true;
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
