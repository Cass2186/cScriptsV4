package scripts.QuestPackages.ShadesOfMortton;

import dax.walker.utils.AccurateMouse;
import dax.walker_engine.interaction_handling.NPCInteraction;
import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api2007.*;
import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSItem;
import org.tribot.api2007.types.RSTile;
import org.tribot.script.sdk.GameState;
import org.tribot.script.sdk.Quest;
import scripts.*;
import scripts.QuestPackages.ScorpionCatcher.ScorpionCatcher;
import scripts.QuestSteps.QuestTask;
import scripts.Requirements.ItemRequirement;
import scripts.Requirements.Requirement;
import scripts.Tasks.Priority;

import java.nio.file.Path;
import java.util.List;

public class ShadesOfMortton implements QuestTask {
    private static ShadesOfMortton quest;

    public static ShadesOfMortton get() {
        return quest == null ? quest = new ShadesOfMortton() : quest;
    }

    int TARROMIN_UNF = 95;
    int TINDERBOX = 590;
    int ASHES = 592;
    int COINS = 995;
    int FLAMTAER_HAMMER = 3678;
    int FLAMTAER_BRACELET = 21180;
    int MORTTON_TELEPORT = 12406;
    int MONKFISH = 7946;
    int RUNE_SCIMITAR = 1333;
    int ADAMANT_SCIMITAR = 1331;
    int MITHRIL_KITESHIELD = 1197;
    int MITHRIL_PLATEBODY = 1121;
    int MITHRIL_PLATELEGS = 1071;
    int[] AXE_IDS = {
            1349, // iron
            1353,//steel
            1355, //mithril
            1357,// adamant
    };

    int SMASHED_TABLE = 4064;
    int SHELF = 4062;
    int DIARY = 3395;
    int[] SERUM207 = {3410, 3412, 3414};
    int OLIVE_OIL = 3422;
    int AFFLICTED_RAZMIRE = 1289;
    int CLOSED_DOOR = 1535;
    int LOAR_SHADOW = 1276;
    int LOAR_SHADE = 1277;
    int LOAR_REMAINS = 3396;
    int SWAMP_PASTE = 1941;
    int TIMBERBEAM = 8837;
    int LIMESTONE_BRICK = 3420;
    int AFFLICTED_ULSQUIRE = 1287;

    RSArea START_AREA = new RSArea(new RSTile(3483, 3276, 0), new RSTile(3481, 3279, 0));
    RSArea GENERAL_STORE = new RSArea(new RSTile(3489, 3295, 0), new RSTile(3487, 3297, 0));
    RSArea WATER_AREA = new RSArea(new RSTile(3497, 3288, 0), new RSTile(3495, 3291, 0));
    RSArea TOWN_CENTRE = new RSArea(new RSTile(3485, 3291, 0), new RSTile(3491, 3287, 0));
    RSArea TEMPLE_INSIDE = new RSArea(new RSTile(3503, 3318, 0), new RSTile(3509, 3313, 0));

    private boolean checkLevel() {
        if (Skills.getActualLevel(Skills.SKILLS.CRAFTING) < 20 || Skills.getActualLevel(Skills.SKILLS.HERBLORE) < 15
                || Skills.getActualLevel(Skills.SKILLS.FIREMAKING) < 5 || Skills.getActualLevel(Skills.SKILLS.ATTACK) < 30
                || Skills.getActualLevel(Skills.SKILLS.DEFENCE) < 20) {
            General.println("[Debug]: Missing a skill requirement for Shades of Mort'ton.");
            return false;
        }
        return true;
    }

    public void buyItems() {
        cQuesterV2.status = "Buying Items";
        General.println("[Debug]: " + cQuesterV2.status);
        BankManager.open(true);
        BankManager.depositEquipment();
       /* GEManager.getCoins();
        GEManager.buyItem(TARROMIN_UNF, 50, 4);
        GEManager.buyItem(ASHES, 150, 4);
        GEManager.buyItem(TINDERBOX, 150, 4);
        GEManager.buyItem(FLAMTAER_BRACELET, 150, 4);
        GEManager.buyItem(FLAMTAER_HAMMER, 100, 1);
        GEManager.buyItem(MORTTON_TELEPORT, 15, 2);
        GEManager.buyItem(MONKFISH, 15, 20);
        GEManager.buyItem(
                Utils.COMBAT_BRACELET[0], 15, 1);
        GEManager.buyItem(
                Utils.AMULET_OF_GLORY[2], 15, 1);
        GEManager.buyItem(
                Utils.STAMINA_POTION[0], 15, 1);
        GEManager.buyItem(MITHRIL_KITESHIELD, 35, 1);
        GEManager.buyItem(MITHRIL_PLATEBODY, 35, 1);
        GEManager.buyItem(MITHRIL_PLATELEGS, 35, 1);
        if (Skills.getActualLevel(Skills.SKILLS.WOODCUTTING) >= 31) {
            GEManager.buyItem(AXE_IDS[3], 300, 1);
        } else if (Skills.getActualLevel(Skills.SKILLS.WOODCUTTING) >= 21) {
            GEManager.buyItem(AXE_IDS[2], 500, 1);
        } else if (Skills.getActualLevel(Skills.SKILLS.WOODCUTTING) >= 6) {
            GEManager.buyItem(AXE_IDS[1], 500, 1);
        } else if (Skills.getActualLevel(Skills.SKILLS.WOODCUTTING) >= 6) {
            GEManager.buyItem(AXE_IDS[0], 500, 1);
        }
        if (Skills.getActualLevel(Skills.SKILLS.ATTACK) >= 40) {
            GEManager.buyItem(RUNE_SCIMITAR, 20, 1);
        } else if (Skills.getActualLevel(Skills.SKILLS.ATTACK) >= 30) {
            GEManager.buyItem(ADAMANT_SCIMITAR, 50, 1);
        }
        GEManager.collectItems();
        GEManager.closeGE();*/
    }

    public void getItems() {
        cQuesterV2.status = "Getting Items";
        General.println("[Debug]: " + cQuesterV2.status);
        BankManager.open(true);
        BankManager.depositEquipment();
        BankManager.checkEquippedGlory();
        BankManager.depositAll(true);
        BankManager.withdraw(3, true, TARROMIN_UNF);
        BankManager.withdraw(3, true, ASHES);
        BankManager.withdraw(1, true,
                ItemID.STAMINA_POTION[0]);
        BankManager.withdraw(1, true, TINDERBOX);
        BankManager.withdraw(3, true, FLAMTAER_BRACELET);
        Utils.equipItem(FLAMTAER_BRACELET);
        BankManager.withdraw(1, true, MITHRIL_KITESHIELD);
        Utils.equipItem(MITHRIL_KITESHIELD);
        BankManager.withdraw(1, true, MITHRIL_PLATEBODY);
        Utils.equipItem(MITHRIL_PLATEBODY);
        BankManager.withdraw(1, true, MITHRIL_PLATELEGS);
        Utils.equipItem(MITHRIL_PLATELEGS);
        if (Skills.getActualLevel(Skills.SKILLS.ATTACK) >= 40) {
            BankManager.withdraw(1, true, RUNE_SCIMITAR);
            Utils.equipItem(RUNE_SCIMITAR);
        } else if (Skills.getActualLevel(Skills.SKILLS.ATTACK) >= 30) {
            BankManager.withdraw(1, true, ADAMANT_SCIMITAR);
            Utils.equipItem(ADAMANT_SCIMITAR);
        }
        BankManager.withdraw(2, true, MORTTON_TELEPORT);
        BankManager.withdraw(10000, true, COINS);

        if (Skills.getActualLevel(Skills.SKILLS.WOODCUTTING) >= 31) {
            BankManager.withdraw(1, true, AXE_IDS[3]);
        } else if (Skills.getActualLevel(Skills.SKILLS.WOODCUTTING) >= 21) {
            BankManager.withdraw(1, true, AXE_IDS[2]);
        } else if (Skills.getActualLevel(Skills.SKILLS.WOODCUTTING) >= 6) {
            BankManager.withdraw(1, true, AXE_IDS[1]);
        } else if (Skills.getActualLevel(Skills.SKILLS.WOODCUTTING) >= 6) {
            BankManager.withdraw(1, true, AXE_IDS[0]);
        }
        BankManager.withdraw(12, true, MONKFISH);
        BankManager.close(true);
    }

    public void startQuest() {
        if (!BankManager.checkInventoryItems(MORTTON_TELEPORT, MONKFISH, TINDERBOX, TARROMIN_UNF, ASHES)) {
            buyItems();
            getItems();
        }
        cQuesterV2.status = "Starting Quest";
        General.println("[Debug]: " + cQuesterV2.status);
        PathingUtil.walkToArea(START_AREA);
        if (START_AREA.contains(Player.getPosition()) && Inventory.find(DIARY).length < 1) {
            Utils.clickObj(SHELF, "Search");
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation();
        }
        if (Inventory.find(DIARY).length > 0) {
            cQuesterV2.status = "Reading Diary";
            AccurateMouse.click(Inventory.find(DIARY)[0], "Read");
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation();
            Timing.waitCondition(() -> Interfaces.get(49, 113) != null, 6000);
            if (Interfaces.get(49, 113) != null) {
                Interfaces.get(49, 113).click();

                Utils.idle(500, 2500);
            }
        }
    }

    public void step2() {
        cQuesterV2.status = "Making Serum 207's";
        General.println("[Debug]: " + cQuesterV2.status);
        Utils.useItemOnItem(ASHES, TARROMIN_UNF);
        Timing.waitCondition(() -> Inventory.find(ASHES).length < 1, 10000);

        Utils.idle(500, 2500);
    }

    public void step3() {
        cQuesterV2.status = "Curing Razmire";
        General.println("[Debug]: " + cQuesterV2.status);
        Walking.blindWalkTo(new RSTile(3488, 3294, 0));

        Utils.idle(3500, 5000);
        if (Objects.findNearest(3, CLOSED_DOOR).length > 0) {
            AccurateMouse.click(Objects.findNearest(3, CLOSED_DOOR)[0], "Open");
            Timing.waitCondition(() -> Objects.findNearest(15, 1536).length > 0, 6000);
        }
        Timing.waitCondition(() -> Objects.findNearest(15, 1536).length > 0, 6000);
        if (NPCs.findNearest(1290).length < 1) {
            Utils.useItemOnNPC(SERUM207, AFFLICTED_RAZMIRE);
            NPCInteraction.waitForConversationWindow();
        }
        NpcChat.talkToNPC(1290);
        NPCInteraction.waitForConversationWindow();
        NPCInteraction.handleConversation("What are all these shadowy creatures?");
        NPCInteraction.handleConversation("Yes, I'll dispatch those dark and evil creatures."); // might be an andpersand
        NPCInteraction.handleConversation();
    }

    public void step4() {
        cQuesterV2.status = "Killing Shades";
        General.println("[Debug]: " + cQuesterV2.status);
        if (GENERAL_STORE.contains(Player.getPosition())) {
            AccurateMouse.click(Objects.findNearest(20, "Door")[0], "Open");

            Utils.idle(2000, 4000);
        }
        PathingUtil.walkToArea(TOWN_CENTRE);
        while (GameState.getSetting(339) <= 45 && GameState.getSetting(339) > 10 && Inventory.find(LOAR_REMAINS).length < 5) {
            General.sleep(150);
            if (Combat.getHPRatio() < General.random(35, 65) && Inventory.find(MONKFISH).length > 0) {
                AccurateMouse.click(Inventory.find(MONKFISH)[0], "Eat");
            }
            if (!Combat.isUnderAttack()) {
                NpcChat.talkToNPC(LOAR_SHADOW, "Attack");
                Timing.waitCondition(() -> Combat.isUnderAttack(), 5000);
            }
            RSItem[] invRemains = Inventory.find(LOAR_REMAINS);
            Utils.clickGroundItem(LOAR_REMAINS);
            Timing.waitCondition(() -> Inventory.find(LOAR_REMAINS).length > Inventory.find(LOAR_REMAINS).length, 7000);

            Utils.idle(500, 2500);
            if (GroundItems.find(LOAR_REMAINS).length > 0) {
                if (Inventory.isFull() && Inventory.find(MONKFISH).length > 0) {
                    AccurateMouse.click(Inventory.find(MONKFISH)[0], "Eat");
                }
                AccurateMouse.click(GroundItems.find(LOAR_REMAINS)[0], "Take");
                Timing.waitCondition(() -> Inventory.find(LOAR_REMAINS).length > invRemains.length, 7000);

                Utils.idle(500, 2500);
            }
            if (GameState.getSetting(339) == 45 && Inventory.find(LOAR_REMAINS).length > 4) {
                General.println("[Debug]: Have all 5 remains.");
                break;
            }
        }
    }

    public void step5() {
        if (Inventory.find(LOAR_REMAINS).length < 5) {
            step4();
        }
        cQuesterV2.status = "Curing Razmire";
        General.println("[Debug]: " + cQuesterV2.status);
        Walking.blindWalkTo(new RSTile(3488, 3294, 0));

        Utils.idle(3500, 5000);
        if (Objects.findNearest(5, CLOSED_DOOR).length > 0) {
            AccurateMouse.click(Objects.findNearest(5, CLOSED_DOOR)[0], "Open");
            Timing.waitCondition(() -> Objects.findNearest(15, 1536).length > 0, 6000);
        }

        Utils.useItemOnNPC(SERUM207, AFFLICTED_RAZMIRE);
        NPCInteraction.waitForConversationWindow();
        NPCInteraction.handleConversation();
    }

    int RAZMIRE = 1290;

    public void step6() {
        if (Inventory.find(OLIVE_OIL).length < 1) {
            if (NPCs.find(AFFLICTED_RAZMIRE).length > 0) {
                Utils.useItemOnNPC(SERUM207, AFFLICTED_RAZMIRE);
            }
            AccurateMouse.click(NPCs.find(RAZMIRE)[0], "Trade-General-Store");
            Timing.waitCondition(() -> Interfaces.get(300, 16, 10) != null, 6000);
            if (Interfaces.get(300, 16, 10) != null) {
                AccurateMouse.click(Interfaces.get(300, 16, 10), "Buy 1");
                Interfaces.get(300, 1, 11).click();
                Timing.waitCondition(() -> Interfaces.get(300, 16, 10) == null, 6000);
            }
        }

        if (Inventory.find(TIMBERBEAM).length < 1 || Inventory.find(LIMESTONE_BRICK).length < 1) {
            if (NPCs.find(AFFLICTED_RAZMIRE).length > 0) {
                Utils.useItemOnNPC(SERUM207, AFFLICTED_RAZMIRE);
                Timing.waitCondition(() -> NPCs.find(RAZMIRE).length > 0, 7000);
            }
            if (Inventory.getAll().length > 23 && Inventory.find(MONKFISH).length > 0) {
                if (Inventory.find(229).length > 0) {
                    Inventory.drop(229);
                }
                AccurateMouse.click(Inventory.find(MONKFISH)[0], "Eat");

                Utils.idle(100, 500);
                AccurateMouse.click(Inventory.find(MONKFISH)[0], "Eat");

                Utils.idle(100, 500);
                AccurateMouse.click(Inventory.find(MONKFISH)[0], "Eat");

                Utils.idle(100, 500);
            }
            AccurateMouse.click(NPCs.find(RAZMIRE)[0], "Trade-Builders-Store");
            Timing.waitCondition(() -> Interfaces.get(300, 16, 3) != null, 6000);

            Utils.idle(500, 1500);
            if (Interfaces.get(300, 16, 1) != null) {
                AccurateMouse.click(Interfaces.get(300, 16, 2), "Buy 1");
                AccurateMouse.click(Interfaces.get(300, 16, 2), "Buy 1");
                AccurateMouse.click(Interfaces.get(300, 16, 3), "Buy 1");
                AccurateMouse.click(Interfaces.get(300, 16, 3), "Buy 1");
                AccurateMouse.click(Interfaces.get(300, 16, 4), "Buy 50");
                Interfaces.get(300, 1, 11).click();
                Timing.waitCondition(() -> Interfaces.get(300, 16, 4) == null, 6000);
            }
        }
    }

    public void step7() {
        if (GENERAL_STORE.contains(Player.getPosition()) && Objects.findNearest(5, CLOSED_DOOR).length > 0) {
            AccurateMouse.click(Objects.findNearest(5, CLOSED_DOOR)[0], "Open");

            Utils.idle(2000, 4000);
        }
        if (!WATER_AREA.contains(Player.getPosition())) {
            PathingUtil.localNavigation(WATER_AREA);

            if (Objects.findNearest(5, CLOSED_DOOR).length > 0) {
                AccurateMouse.click(Objects.findNearest(5, CLOSED_DOOR)[0], "Open");
                Timing.waitCondition(() -> Objects.findNearest(15, 1536).length > 0, 6000);
            }
        }
        Utils.useItemOnNPC(SERUM207, AFFLICTED_ULSQUIRE);
        NPCInteraction.waitForConversationWindow();
        NPCInteraction.handleConversation();
        NpcChat.talkToNPC("Ulsquire Shauncy");
        NPCInteraction.handleConversation("What did you find out about the remains?");
        NPCInteraction.handleConversation("What can you tell me about that temple?");
        NPCInteraction.handleConversation("Ok, thanks");
        NPCInteraction.handleConversation();
    }

    int TEMPLE_WALL = 4078;
    int sactityNum = 0;
    int LIT_FIRE = 4090;
    int SACRED_OIL = 3430;

    public void step8() {
        WorldHopper.changeWorld(377);
        if (WATER_AREA.contains(Player.getPosition()) && Objects.findNearest(5, CLOSED_DOOR).length > 0) {
            AccurateMouse.click(Objects.findNearest(5, CLOSED_DOOR)[0], "Open");

            Utils.idle(2000, 4000);
        }
        PathingUtil.localNavigation(TEMPLE_INSIDE);

        if (Utils.clickObj(TEMPLE_WALL, "Reinforce"))
            Utils.idle(15000, 25000);

        if (Interfaces.get(171, 10) != null) {
            String sanctity = Interfaces.get(171, 10).getText();
            General.println("[Debug] Sanctity is at " + sanctity);
            sanctity = sanctity.replace("%", "");
            sactityNum = Integer.parseInt(sanctity);
            General.println(sactityNum);
        }
        if (sactityNum > 10 && Inventory.find(SACRED_OIL).length < 1) {
            if (Objects.findNearest(15, LIT_FIRE).length > 0) {
                Utils.useItemOnObject(OLIVE_OIL, LIT_FIRE);
                Timing.waitCondition(() -> Inventory.find(SACRED_OIL).length > 0, 6000);
            }
        }
    }

    int LOGS = 1511;
    int PYRE_LOGS = 3438;
    int FUNERAL_PYRE = 4093;

    public void step9() {
        if (Inventory.find(PYRE_LOGS).length < 1 && Inventory.find(LOGS).length < 1) {
            Utils.blindWalkToTile(new RSTile(3469, 3297, 0));
            if (Objects.findNearest(15, "Dead tree").length > 0) {
                AccurateMouse.click(Objects.findNearest(15, "Dead tree")[0], "Chop down");
                Timing.waitCondition(() -> Inventory.find(LOGS).length > 0, 10000);
            }
        }
        if (Inventory.find(LOGS).length > 0) {
            Utils.useItemOnItem(SACRED_OIL, LOGS);
        }
    }

    public void step10() {
        if (Inventory.find(PYRE_LOGS).length > 0) {
            Utils.useItemOnObject(PYRE_LOGS, FUNERAL_PYRE);
            Utils.idle(300, 1200);
            Utils.useItemOnObject(LOAR_REMAINS, FUNERAL_PYRE);
            Timing.waitCondition(() -> Objects.findNearest(10, 4100).length > 0, 5000);
            if (Objects.findNearest(10, 4100).length > 0) {
                AccurateMouse.click(Objects.findNearest(10, 4100)[0], "Light");
                Timing.waitCondition(() -> GroundItems.find(3450).length > 0, 10000);
            }
        }
    }

    int BRONZE_KEY = 3450;

    public void step11() {
        if (GroundItems.find(BRONZE_KEY).length > 0) {
            GroundItems.find(BRONZE_KEY)[0].adjustCameraTo();
            AccurateMouse.click(GroundItems.find(BRONZE_KEY)[0], "Take");
            Timing.waitCondition(() -> Inventory.find(BRONZE_KEY).length > 0, 10000);
        }
    }

    public void step12() {
        cQuesterV2.status = "Finishing Quest";
        if (!WATER_AREA.contains(Player.getPosition())) {
            PathingUtil.blindWalkToArea(WATER_AREA);
            if (Objects.findNearest(5, CLOSED_DOOR).length > 0) {
                AccurateMouse.click(Objects.findNearest(5, CLOSED_DOOR)[0], "Open");
                Timing.waitCondition(() -> Objects.findNearest(15, 1536).length > 0, 6000);
            }
        }
        if (NPCs.find(AFFLICTED_ULSQUIRE).length > 0) {
            Utils.useItemOnNPC(SERUM207, AFFLICTED_ULSQUIRE);
            NPCInteraction.waitForConversationWindow();
        } else {
            NpcChat.talkToNPC(1288);
        }
        NPCInteraction.handleConversation();
        NpcChat.talkToNPC("Ulsquire Shauncy");
        NPCInteraction.handleConversation();
    }


    @Override
    public void execute() {
        checkLevel();
        if (Quest.SHADES_OF_MORTTON.getState().equals(Quest.State.COMPLETE)) {
            cQuesterV2.taskList.remove(this);
            return;
        }
        General.println("GameState Setting 339 " + GameState.getSetting(339));

        General.sleep(50);
        if (GameState.getSetting(339) == 0 || GameState.getSetting(339) == 5) {
            // buyItems();
            //  getItems();
            startQuest();
        }
        if (GameState.getSetting(339) == 10 || GameState.getSetting(339) == 5) {
            step2();
            step3();
        }
        if (GameState.getSetting(339) < 40 && GameState.getSetting(339) > 10) {
            step4();
        }
        if (GameState.getSetting(339) == 40) {
            step5();
        }
        if (GameState.getSetting(339) == 45) {
            step6();
            step7();
        }
        if (GameState.getSetting(339) == 50) {
            step8();
        }
        if (GameState.getSetting(339) == 60) {
            step8();
        }
        if (GameState.getSetting(339) == 65) {
            step9();
        }
        if (GameState.getSetting(339) == 70) {
            step10();
        }
        if (GameState.getSetting(339) == 75) {
            step11();
        }
        if (GameState.getSetting(339) == 80) {
            step12();
        } else if (GameState.getSetting(339) == 85) {
            Utils.closeQuestCompletionWindow();
        }

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
    public String questName() {
        return null;
    }

    @Override
    public boolean checkRequirements() {
        return false;
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
        return Quest.SHADES_OF_MORTTON.getState().equals(Quest.State.COMPLETE);
    }
}
