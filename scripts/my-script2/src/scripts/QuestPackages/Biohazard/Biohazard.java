package scripts.QuestPackages.Biohazard;

import dax.walker.utils.AccurateMouse;
import dax.walker_engine.interaction_handling.NPCInteraction;
import lombok.Singular;
import org.tribot.api.General;
import org.tribot.api2007.*;
import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSGroundItem;
import org.tribot.api2007.types.RSTile;
import org.tribot.api2007.types.RSVarBit;
import org.tribot.script.sdk.Log;
import org.tribot.script.sdk.Quest;
import scripts.*;
import scripts.GEManager.GEItem;
import scripts.QuestPackages.RomeoAndJuliet.RomeoAndJuliet;
import scripts.QuestSteps.BuyItemsStep;
import scripts.QuestSteps.QuestTask;
import scripts.Requirements.ItemRequirement;
import scripts.Requirements.Requirement;
import scripts.Tasks.Priority;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Biohazard implements QuestTask {

    private static Biohazard quest;

    public static Biohazard get() {
        return quest == null ? quest = new Biohazard() : quest;
    }

    int GOWN_TOP = 426;
    int GOWN_BOTTOM = 428;
    int GAS_MASK = 1506;
    int ARDOUNGE_TELEPORT = 8011;
    int FALADOR_TELEPORT = 8009;
    int BIRD_CAGE = 424;
    int BIRD_FEED = 422;
    int ROTTEN_APPLE = 1984;
    int MEDICAL_GOWN = 430;
    int MOURNER_KEY = 423;
    int TOUCH_PAPER = 419;
    int LIQUID_HONEY = 416;
    int ETHENEA = 415;
    int SULPHURIC_BROLINE = 417;


    RSArea ELENA_HOUSE = new RSArea(new RSTile(2590, 3338, 0), new RSTile(2594, 3334, 0));
    RSArea JERICO_HOUSE = new RSArea(new RSTile(2617, 3323, 0), new RSTile(2611, 3326, 0));
    RSArea OMART_AREA = new RSArea(new RSTile(2557, 3260, 0), new RSTile(2562, 3264, 0));
    RSArea SEED_AREA = new RSArea(new RSTile(2561, 3302, 0), new RSTile(2564, 3300, 0));
    RSArea HQ_INSIDE = new RSArea(new RSTile(2555, 3321, 0), new RSTile(2547, 3327, 0));
    RSArea HQ_DOOR = new RSArea(new RSTile(2549, 3320, 0), new RSTile(2553, 3319, 0));
    RSArea OUTSIDE_BACKYARD = new RSArea(new RSTile(2541, 3333, 0), new RSTile(2534, 3329, 0));
    RSArea HQ_BACKYARD = new RSArea(new RSTile(2555, 3328, 0), new RSTile(2542, 3333, 0));
    RSArea NURSE_HOUSE = new RSArea(new RSTile(2515, 3276, 0), new RSTile(2517, 3270, 0));
    RSArea HQ_UPSTAIRS = new RSArea(new RSTile(2542, 3327, 1), new RSTile(2546, 3324, 1));
    RSArea HQ_UPSTAIRS_PT2 = new RSArea(new RSTile(2547, 3327, 1), new RSTile(2551, 3321, 1));
    RSArea CHEMIST_BUILDING = new RSArea(new RSTile(2929, 3213, 0), new RSTile(2936, 3207, 0));
    RSArea DANCING_DONKEY_INN = new RSArea(new RSTile(3274, 3388, 0), new RSTile(3266, 3392, 0));
    RSArea GUIDORS_HOUSE = new RSArea(new RSTile(3283, 3384, 0), new RSTile(3285, 3380, 0));
    RSArea KING_AREA = new RSArea(new RSTile(2575, 3294, 1), new RSTile(2580, 3292, 1));


    RSTile N_BIRD_SEED_TILE = new RSTile(2564, 3302, 0);
    RSTile S_BIRD_SEED_TILE = new RSTile(2562, 3300, 0);

    ArrayList<GEItem> itemsToBuy = new ArrayList<GEItem>(
            Arrays.asList(
                    new GEItem(ItemID.PRIEST_GOWN, 1, 900),
                    new GEItem(ItemID.PRIEST_GOWN_428, 1, 900),
                    new GEItem(ItemID.VARROCK_TELEPORT, 5, 40),
                    new GEItem(ItemID.ARDOUGNE_TELEPORT, 5, 40),
                    new GEItem(ItemID.FALADOR_TELEPORT, 5, 40),

                    new GEItem(ItemID.MIND_RUNE, 300, 20),
                    new GEItem(ItemID.FIRE_RUNE, 900, 20),
                    new GEItem(ItemID.STAFF_OF_AIR, 1, 200),
                    new GEItem(ItemID.AMULET_OF_GLORY[0], 2, 20),

                    new GEItem(ItemID.STAMINA_POTION[0], 3, 15),
                    new GEItem(ItemID.RING_OF_WEALTH[0], 1, 25)
            )
    );

    BuyItemsStep buyStep = new BuyItemsStep(itemsToBuy);

    public void buyItems() {
        cQuesterV2.status = "Buying Items";
        General.println("[Debug]: " + cQuesterV2.status);
        buyStep.buyItems();
    }

    public void getItems() {
        cQuesterV2.status = "Getting Items";
        General.println("[Debug]: Getting Items");

        BankManager.open(true);
        BankManager.checkEquippedGlory();
        BankManager.depositAll(true);
        BankManager.withdraw(1, true, ItemID.AMULET_OF_GLORY[0]);
        BankManager.withdraw(1, true, GOWN_BOTTOM);
        BankManager.withdraw(1, true, GOWN_TOP);
        BankManager.withdraw(5, true, ARDOUNGE_TELEPORT);
        BankManager.withdraw(5, true, FALADOR_TELEPORT);
        BankManager.withdraw(1, true, GAS_MASK);
        BankManager.withdraw(1, true, ItemID.STAFF_OF_AIR);
        Utils.equipItem(ItemID.STAFF_OF_AIR);
        BankManager.withdraw(250, true, ItemID.MIND_RUNE);
        BankManager.withdraw(750, true, ItemID.FIRE_RUNE);
        BankManager.withdraw(1, true, GAS_MASK);
        BankManager.withdraw(1, true, ItemID.RING_OF_DUELING[0]);
        BankManager.withdraw(2, true, BankManager.STAMINA_POTION[0]);
        BankManager.close(true);
        Utils.equipItem(GOWN_TOP);
        Utils.equipItem(GAS_MASK);
        Utils.equipItem(GOWN_BOTTOM);
        Autocast.enableAutocast(Autocast.FIRE_STRIKE);
    }

    public void startQuest() {
        cQuesterV2.status = "Starting Quest";
        General.println("[Debug]: " + cQuesterV2.status);
        PathingUtil.walkToArea(ELENA_HOUSE);
        if (ELENA_HOUSE.contains(Player.getPosition())) {
            if (NpcChat.talkToNPC("Elena")) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation("Yes.");
                NPCInteraction.handleConversation();
            }
        }
    }

    public void goToJerico() {
        cQuesterV2.status = "Going to Jerico";
        General.println("[Debug]: " + cQuesterV2.status);
        PathingUtil.walkToArea(JERICO_HOUSE);
        if (JERICO_HOUSE.contains(Player.getPosition())) {
            if (NpcChat.talkToNPC("Jerico")) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation();
                Utils.shortSleep();
            }

            if (Utils.clickObj(2056, "Open"))
                Timer.abc2WaitCondition(() -> Objects.findNearest(20, 2057).length > 0, 5000, 9000);

            if (Utils.clickObj(2057, "Search")) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation();
                Utils.modSleep();
            }
        }
    }

    public void getCage() {
        if (Inventory.find(BIRD_FEED).length > 0 && Inventory.find(BIRD_CAGE).length < 1) {
            cQuesterV2.status = "Getting Cage";
            Log.log("[Debug]: Getting Cage");

            if (PathingUtil.walkToTile(new RSTile(2619, 3324, 0))) {
                PathingUtil.movementIdle();
            }
            if (Utils.clickGroundItem(424))
                Utils.idleNormalAction();
        }
    }

    public void goToOmart() {
        if (Inventory.find(BIRD_CAGE).length > 0) {

            cQuesterV2.status = "Going to Omart";
            General.println("[Debug]: " + cQuesterV2.status);

            PathingUtil.walkToArea(OMART_AREA);

            if (NpcChat.talkToNPC("Omart")) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation();
                Utils.modSleep();
            }

        }
    }

    public void plantSeed() {
        cQuesterV2.status = "Going to plant seeds";
        General.println("[Debug]: " + cQuesterV2.status);

        PathingUtil.walkToArea(SEED_AREA);
        if (SEED_AREA.contains(Player.getPosition())) {

            if (Walking.clickTileMS(N_BIRD_SEED_TILE, "Walk here"))
                General.sleep(General.random(2500, 4000));

            if (Inventory.find(BIRD_FEED).length > 0) {
                if (AccurateMouse.click(Inventory.find(BIRD_FEED)[0], "Throw"))
                    General.sleep(General.random(2500, 4000));
            }

            if (Inventory.find(BIRD_CAGE).length > 0) {
                if (AccurateMouse.click(Inventory.find(BIRD_CAGE)[0], "Open")) {
                    NPCInteraction.waitForConversationWindow();
                    Utils.modSleep();
                }
            }
        }
    }

    public void goToOmart2() {
        cQuesterV2.status = "Going to Omart";
        General.println("[Debug]: " + cQuesterV2.status);

        PathingUtil.walkToArea(OMART_AREA);


        if (NpcChat.talkToNPC("Omart")) {
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation("Okay, lets do it.");
            NPCInteraction.handleConversation();

            General.sleep(General.random(3500, 6000));
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation();
        }

    }

    public void goToHQ() {
        if (Inventory.find(ROTTEN_APPLE).length < 1) {
            cQuesterV2.status = "Going to Mourner HQ";
            General.println("[Debug]: " + cQuesterV2.status);
            PathingUtil.walkToArea(HQ_DOOR);
            if (Utils.clickObj("Door", "Open")) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation();
            }
        }
    }

    public void goToBackyard() {
        cQuesterV2.status = "Going to Mourner Back yard";
        General.println("[Debug]: " + cQuesterV2.status);
        PathingUtil.walkToArea(OUTSIDE_BACKYARD);
        if (OUTSIDE_BACKYARD.contains(Player.getPosition()) && Inventory.find(ROTTEN_APPLE).length < 1) {
            RSGroundItem[] apple = GroundItems.find(ROTTEN_APPLE);
            if (Utils.clickGroundItem(ROTTEN_APPLE)) {
                Utils.idleNormalAction();
            }
           /* if (apple.length > 0) {
                if (!apple[0].isClickable())
                    apple[0].adjustCameraTo();

                if (AccurateMouse.click(apple[0], "Take"))
                    Timer.waitCondition(() -> Inventory.find(ROTTEN_APPLE).length > 0, 6000, 9000);
            }*/
        }
        if (Inventory.find(ROTTEN_APPLE).length > 0 && !HQ_BACKYARD.contains(Player.getPosition())) {
            if (Utils.clickObj(2068, "Squeeze-through"))
                Timer.waitCondition(() -> HQ_BACKYARD.contains(Player.getPosition()), 8000, 12000);

        }
        if (Inventory.find(ROTTEN_APPLE).length > 0 && HQ_BACKYARD.contains(Player.getPosition())) {

            if (Utils.useItemOnObject(ROTTEN_APPLE, "Cauldron")) {
                Timer.waitCondition(() -> Inventory.find(ROTTEN_APPLE).length == 0, 6000, 9000);
                NPCInteraction.handleConversation();
                Utils.shortSleep();
            }
        }
    }

    public void goToNurse() {
        if (Inventory.find(MEDICAL_GOWN).length < 1 && Equipment.find(MEDICAL_GOWN).length < 1) {
            cQuesterV2.status = "Going to Nurse house";
            General.println("[Debug]: " + cQuesterV2.status);
            PathingUtil.walkToArea(NURSE_HOUSE);
            if (NURSE_HOUSE.contains(Player.getPosition())) {
                if (Utils.clickObj(2062, "Open"))
                    Timer.waitCondition(() -> Objects.findNearest(20, 2063).length > 0, 5000, 9000);

                if (Utils.clickObj(2063, "Search")) {
                    NPCInteraction.waitForConversationWindow();
                    NPCInteraction.handleConversation();
                    Utils.equipItem(MEDICAL_GOWN);
                    Utils.shortSleep();
                }
            }
        }
    }

    public void goToHQUpstairs() {
        cQuesterV2.status = "Going to Mourner HQ";
        General.println("[Debug]: " + cQuesterV2.status);
        if (!HQ_INSIDE.contains(Player.getPosition())) {
            goToHQ();
        }
        if (HQ_INSIDE.contains(Player.getPosition())) {
            Walking.blindWalkTo(new RSTile(2547, 3325, 0));
            General.sleep(General.random(3500, 5000));

            if (Utils.clickObj(1535, "Open"))
                General.sleep(General.random(1500, 4000));

            if (Utils.clickObj("Staircase", "Climb-up"))
                Timer.waitCondition(() -> HQ_UPSTAIRS.contains(Player.getPosition()), 8000, 12000);
        }
        if (HQ_UPSTAIRS.contains(Player.getPosition()) && Objects.findNearest(20, 2034).length > 0) {
            if (Utils.clickObj(2034, "Open"))
                General.sleep(General.random(2000, 4000));
        }
        if (HQ_UPSTAIRS_PT2.contains(Player.getPosition()) && Inventory.find(MOURNER_KEY).length < 1) {
            if (Utils.clickNPC(9230, "Attack")) {
                Timer.waitCondition(() -> Combat.isUnderAttack(), 8000, 12000);
                Timer.abc2WaitCondition(() -> Inventory.find(MOURNER_KEY).length > 0, 45000, 60000);
            }
        }
        if (HQ_UPSTAIRS_PT2.contains(Player.getPosition()) && Inventory.find(MOURNER_KEY).length > 0) {
            if (AccurateMouse.click(Objects.findNearest(20, 2058)[0], "Open")) {
                General.sleep(General.random(3500, 5000));
                if (Utils.clickObj(2064, "Search")) {
                    NPCInteraction.waitForConversationWindow();
                    NPCInteraction.handleConversation();
                }
            }
        }
    }

    public void goToElena() {
        cQuesterV2.status = "Going to elena";
        General.println("[Debug]: " + cQuesterV2.status);
        PathingUtil.walkToArea(ELENA_HOUSE);
        if (NpcChat.talkToNPC("Elena")) {
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation();
            NPCInteraction.handleConversation();
        }
    }

    public void goToChemist() {
        cQuesterV2.status = "Going to chemist";
        General.println("[Debug]: " + cQuesterV2.status);
        PathingUtil.walkToArea(CHEMIST_BUILDING, false);
        if (NpcChat.talkToNPC("Chemist")) {
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation("Your quest.");
            NPCInteraction.handleConversation();
        }
    }

    public void talkToHops() {
        if (Inventory.find(SULPHURIC_BROLINE).length > 0) {
            cQuesterV2.status = "Talking to Hops";
            General.println("[Debug]: " + cQuesterV2.status);
            if (NpcChat.talkToNPC("Hops")) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation("You give him the vial of sulphuric broline...");
                NPCInteraction.handleConversation();
            }
        }
    }

    public void talkToDaVinci() {
        if (Inventory.find(ETHENEA).length > 0) {
            cQuesterV2.status = "Talking to Da Vinci";
            General.println("[Debug]: " + cQuesterV2.status);
            if (NpcChat.talkToNPC("Da Vinci")) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation("You give him the vial of ethenea...");
                NPCInteraction.handleConversation();
            }
        }
    }

    public void talkToChancy() {
        if (Inventory.find(LIQUID_HONEY).length > 0) {
            cQuesterV2.status = "Talking to Chancy";
            General.println("[Debug]: " + cQuesterV2.status);
            if (NpcChat.talkToNPC("Chancy")) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation("You give him the vial of liquid honey...");
                NPCInteraction.handleConversation();
            }
        }
    }

    public void goToInn() {
        cQuesterV2.status = "Going to Inn";
        General.println("[Debug]: " + cQuesterV2.status);
        if (!DANCING_DONKEY_INN.contains(Player.getPosition())) {
            Utils.equipItem(GOWN_TOP);
            PathingUtil.walkToArea(DANCING_DONKEY_INN);
        }
        if (DANCING_DONKEY_INN.contains(Player.getPosition())) {
            if (Inventory.find(ETHENEA).length < 1) {
                NpcChat.talkToNPC("Da Vinci");
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation();
            }
            if (Inventory.find(LIQUID_HONEY).length < 1) {
                NpcChat.talkToNPC("Chancy");
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation();
            }
            if (Inventory.find(SULPHURIC_BROLINE).length < 1) {
                NpcChat.talkToNPC("Hops");
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation();
            }
        }
    }

    public void goToGuidor() {
        cQuesterV2.status = "Going to Guidor";
        General.println("[Debug]: " + cQuesterV2.status);
        PathingUtil.walkToArea(GUIDORS_HOUSE);

        if (GUIDORS_HOUSE.contains(Player.getPosition())) {
            NpcChat.talkToNPC("Guidor");
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation("I've come to ask your assistance in stopping a plague.");
            NPCInteraction.handleConversation();
            Timer.abc2WaitCondition(() -> Game.getSetting(68) == 14, 8000, 12000);
        }
    }

    public void goToKing() {
        cQuesterV2.status = "Going to King";
        General.println("[Debug]: " + cQuesterV2.status);
        PathingUtil.walkToArea(KING_AREA);
        if (KING_AREA.contains(Player.getPosition())) {
            NpcChat.talkToNPC("King Lathas");
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation("I've come to ask your assistance in stopping a plague.");
            NPCInteraction.handleConversation();
        }
    }


    @Override
    public void execute() {
        if (Game.getSetting(68) == 0) {
            buyItems();
            getItems();
            startQuest();
        } else if (Game.getSetting(68) == 1) {
            goToJerico();

        } else if (Game.getSetting(68) == 2) {
            if (RSVarBit.get(9104).getValue() == 0) {
                getCage();
                goToOmart();
            }
            if (RSVarBit.get(9104).getValue() == 1) {
                plantSeed();
            }

        } else if (Game.getSetting(68) == 4) {
            goToOmart2();

        } else if (Game.getSetting(68) == 5) {
            goToHQ();
            goToBackyard();
            goToHQ();

        } else if (Game.getSetting(68) == 6) {
            goToHQ();
            goToNurse();
            goToHQ();
            goToHQUpstairs();

        } else if (Game.getSetting(68) == 7) {
            goToElena();

        } else if (Game.getSetting(68) == 10) {
            goToChemist();

        } else if (Game.getSetting(68) == 12) {
            talkToHops();
            talkToChancy();
            talkToDaVinci();
            goToInn();
            goToGuidor();

        } else if (Game.getSetting(68) == 14) {
            goToElena();

        } else if (Game.getSetting(68) == 15) {
            goToKing();

        }
        if (Game.getSetting(68) == 16) {
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
        return "Biohazard";
    }

    @Override
    public boolean checkRequirements() {
        return Quest.PLAGUE_CITY.getState().equals(Quest.State.COMPLETE);
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
