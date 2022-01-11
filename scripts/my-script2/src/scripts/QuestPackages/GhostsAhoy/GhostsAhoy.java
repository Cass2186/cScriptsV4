package scripts.QuestPackages.GhostsAhoy;

import dax.walker.utils.AccurateMouse;
import dax.walker.utils.camera.DaxCamera;
import dax.walker_engine.interaction_handling.NPCInteraction;
import org.tribot.api.General;
import org.tribot.api2007.*;
import org.tribot.api2007.ext.Filters;
import org.tribot.api2007.types.*;
import org.tribot.script.sdk.Log;
import org.tribot.script.sdk.Waiting;
import org.tribot.script.sdk.cache.BankCache;
import org.tribot.script.sdk.query.Query;
import scripts.*;
import scripts.EntitySelector.Entities;
import scripts.EntitySelector.finders.prefabs.ItemEntity;
import scripts.EntitySelector.finders.prefabs.NpcEntity;
import scripts.EntitySelector.finders.prefabs.ObjectEntity;
import scripts.GEManager.GEItem;
import scripts.QuestPackages.HolyGrail.HolyGrail;
import scripts.QuestSteps.*;
import scripts.Requirements.InventoryRequirement;
import scripts.Requirements.ItemReq;
import scripts.Requirements.SkillRequirement;
import scripts.Tasks.Priority;
import scripts.Tasks.Task;


import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

public class GhostsAhoy implements QuestTask{
    private static GhostsAhoy quest;

    public static GhostsAhoy get() {
        return quest == null ? quest = new GhostsAhoy() : quest;
    }

    String message;
    int VARBIT = 217;

    HashMap<Integer, Integer> invMap = new HashMap<>();
    ItemReq intialEctoTokens =   new ItemReq(ItemId.ECTO_TOKENS, 35, 30);

    NPCStep oldManStep = new NPCStep(2997, new RSTile(3617, 3543, 1), new String[]{"Is this your toy boat?"});
    NPCStep necrovarusBoneKeyStep = new NPCStep(GhostsAhoyConst.NECROVARUS_ID, GhostsAhoyConst.NECROVOUS_TILE);
    NPCStep questStartStep = new NPCStep(GhostsAhoyConst.VELORINA_ID, GhostsAhoyConst.START_TILE,
            GhostsAhoyConst.START_DIALOG, intialEctoTokens);
    NPCStep necrovarousStep1 = new NPCStep(GhostsAhoyConst.NECROVARUS_ID, GhostsAhoyConst.NECROVOUS_TILE);
    NPCStep velorinaStep2 = new NPCStep(GhostsAhoyConst.VELORINA_ID, GhostsAhoyConst.START_TILE);
    NPCStep croneStep3 = new NPCStep(GhostsAhoyConst.CRONE_ID, GhostsAhoyConst.CRONE_TILE, GhostsAhoyConst.CRONE_DIALOG);
    NPCStep croneStep4 = new NPCStep(GhostsAhoyConst.CRONE_ID, GhostsAhoyConst.CRONE_TILE, GhostsAhoyConst.CRONE_2_DIALOG);
    NPCStep croneStepNoDialogOptions = new NPCStep(GhostsAhoyConst.CRONE_ID, GhostsAhoyConst.CRONE_TILE, GhostsAhoyConst.CRONE_DIALOG);
    NPCStep necrovarusFinal = new NPCStep(GhostsAhoyConst.NECROVARUS_ID, GhostsAhoyConst.NECROVOUS_TILE, GhostsAhoyConst.FINAL_NECROVARUS_DIALOG);
    NPCStep talkToAkHaranu = new NPCStep("Ak-Haranu", new RSTile(3689, 3499, 0), new String[]{"Okay, wait here - I'll get you your bow."});
    NPCStep talkToRobin = new NPCStep("Robin", new RSTile(3675, 3495, 0), new String[]{"Yes, I'll give you a game."});
    NPCStep takeRowingBoat = new NPCStep(3005, new RSTile(3703, 3487, 0),
            new String[]{"Okay, here's 25 ectotokens. Let's go."});

    NPCStep talkToInnkeeper = new NPCStep(3001, new RSTile(3675, 3495, 0),
            new String[]{"Do you have any jobs I can do?", "Yes, I'd be delighted."});

    NPCStep talkToGravingas = new NPCStep(5858, new RSTile(3660, 3499, 0),
            new String[]{"After hearing Velorina's story I will be happy to help out."});

    UseItemOnObjectStep openChest1 = new UseItemOnObjectStep(GhostsAhoyConst.CHEST_KEY, "Closed chest",
            new RSTile(3619, 3544, 1), Inventory.find(GhostsAhoyConst.CHEST_KEY).length == 0);


    public boolean checkSkillReqs() {
        SkillRequirement agil = new SkillRequirement(Skills.SKILLS.AGILITY, 25);
        SkillRequirement cook = new SkillRequirement(Skills.SKILLS.COOKING, 20);
        return agil.meetsSkillRequirement() && cook.meetsSkillRequirement();
    }


    private boolean hasRequirements() {
        return checkSkillReqs() &&
                Game.getSetting(GhostsAhoyConst.PRIEST_IN_PERIL_GAMESETTING) >= 61 &&
                Game.getSetting(GhostsAhoyConst.RESTLESS_GHOST_GAMESETTING) == 5;
    }

    ArrayList<GEItem> itemsToBuy = new ArrayList<GEItem>(
            Arrays.asList(
                    new GEItem(ItemId.BUCKET_OF_MILK, 1, 100),
                    new GEItem(ItemId.SILK, 1, 300),
                    new GEItem(ItemId.AMULET_OF_GLORY[2], 2, 20),
                    new GEItem(ItemId.SPADE, 1, 300),
                    new GEItem(ItemId.YELLOW_DYE, 3, 300),
                    new GEItem(ItemId.BLUE_DYE, 3, 300),
                    new GEItem(ItemId.RED_DYE, 3, 300),
                    new GEItem(ItemId.POT, 7, 300),
                    new GEItem(ItemId.BONES, 7, 100),
                    new GEItem(ItemId.EMPTY_BUCKET, 8, 300),
                    new GEItem(ItemId.SALVE_GRAVEYARD_TELEPORT, 10, 50),
                    new GEItem(ItemId.OAK_LONGBOW, 1, 300),
                    new GEItem(ItemId.KNIFE, 1, 200),
                    new GEItem(ItemId.STAMINA_POTION[0], 4, 20),
                    new GEItem(ItemId.NEEDLE, 1, 1000),
                    new GEItem(ItemId.THREAD, 1, 75),
                    new GEItem(ItemId.LOGS, 1, 25),
                    new GEItem(ItemId.TINDERBOX, 1, 250),
                    new GEItem(ItemId.LEATHER_GLOVES, 1, 500),
                    new GEItem(ItemId.BOWL_OF_WATER, 1, 500),
                    new GEItem(ItemId.STAFF_OF_FIRE, 1, 35),
                    new GEItem(ItemId.MIND_RUNE, 350, 35),
                    new GEItem(ItemId.AIR_RUNE, 700, 35),
                    new GEItem(ItemId.STAMINA_POTION[0], 4, 35),
                    new GEItem(ItemId.VARROCK_TELEPORT, 10, 35)
            )
    );

    InventoryRequirement itemsForTokens = new InventoryRequirement(new ArrayList<>(
            Arrays.asList(
                    new ItemReq(ItemId.BONES, 7, 0),
                    new ItemReq(ItemId.POT, 7, 0),
                    new ItemReq(ItemId.EMPTY_BUCKET, 8, 0),
                    new ItemReq(ItemId.STAMINA_POTION[0], 1, 0),

                    new ItemReq(ItemId.VARROCK_TELEPORT, 0),
                    new ItemReq(ItemId.SALVE_GRAVEYARD_TELEPORT, 0),
                    new ItemReq(ItemId.GHOST_SPEAK_AMULET, 1, 0, true)
            ))
    );

    InventoryRequirement itemsForStep1 = new InventoryRequirement(new ArrayList<>(
            Arrays.asList(
                    new ItemReq(ItemId.GHOST_SPEAK_AMULET, 1, 1, true),
                    new ItemReq(ItemId.SALVE_GRAVEYARD_TELEPORT, 0, 0),
                    new ItemReq(ItemId.VARROCK_TELEPORT, 0, 0),
                    new ItemReq(ItemId.STAMINA_POTION[0], 1, 0),

                    new ItemReq(ItemId.BUCKET_OF_SLIME, 1),
                    new ItemReq(ItemId.ECTO_TOKENS, 35, 10),
                    new ItemReq(ItemId.LEATHER_GLOVES, 1, 1, true),
                    new ItemReq(ItemId.LOGS, 2, 0),
                    new ItemReq(ItemId.TINDERBOX, 1, 0),
                    new ItemReq(ItemId.BOWL_OF_WATER, 2),
                    new ItemReq(ItemId.BUCKET_OF_MILK, 2),
                    new ItemReq(ItemId.NETTLES, 2)

            ))
    );

    InventoryRequirement mapRequirement = new InventoryRequirement(new ArrayList<>(
            Arrays.asList(
                    new ItemReq(GhostsAhoyConst.MAP_PIECE_1, 1),
                    new ItemReq(GhostsAhoyConst.MAP_PIECE_2, 1),
                    new ItemReq(GhostsAhoyConst.MAP_PIECE_3, 1))));

    InventoryRequirement itemsForStep2 = new InventoryRequirement(new ArrayList<>(
            Arrays.asList(
                    new ItemReq(ItemId.GHOST_SPEAK_AMULET, 1),
                    new ItemReq(ItemId.SALVE_GRAVEYARD_TELEPORT, 0),
                    new ItemReq(ItemId.VARROCK_TELEPORT, 0),
                    new ItemReq(ItemId.STAMINA_POTION[0], 1),

                    new ItemReq(ItemId.MODEL_SHIP, 1),
                    new ItemReq(ItemId.NEEDLE, 1),
                    new ItemReq(ItemId.THREAD, 1),
                    new ItemReq(ItemId.KNIFE, 1),
                    new ItemReq(ItemId.SPADE, 1),
                    new ItemReq(ItemId.MIND_RUNE, 300),
                    new ItemReq(ItemId.AIR_RUNE, 600),

                    new ItemReq(ItemId.STAFF_OF_FIRE, 1),
                    new ItemReq(ItemId.NETTLES, 1),
                    new ItemReq(ItemId.STAFF_OF_FIRE, 1),
                    new ItemReq(ItemId.BLUE_DYE, 3),
                    new ItemReq(ItemId.RED_DYE, 3),
                    new ItemReq(ItemId.YELLOW_DYE, 3),
                    new ItemReq(ItemId.ECTO_TOKENS, 0),
                    new ItemReq(ItemId.SILK, 1),
                    new ItemReq(ItemId.REPAIRED_SHIP, 1),
                    new ItemReq(ItemId.COINS, 5000, 600),
                    new ItemReq(ItemId.OAK_LONGBOW, 1),
                    new ItemReq(ItemId.BUCKET_OF_SLIME, 1)
            ))
    );


    InventoryRequirement itemsForBookOfHoracio = new InventoryRequirement(new ArrayList<>(
            Collections.singletonList(new ItemReq(GhostsAhoyConst.COMPLETE_MAP, 1))));

    InventoryRequirement bookOfHoraciaReq = new InventoryRequirement(new ArrayList<>(
            Collections.singletonList(new ItemReq(GhostsAhoyConst.BOOK_OF_HORACIO, 1))));


    public static boolean handleEnergyBarrier() {
        if (!GhostsAhoyConst.PORT_PHYSMATIS.contains(Player.getPosition())) {
            if (Utils.clickObject("Energy Barrier", "Pay-toll(2-Ecto)", true)) {
                General.println("[Debug]: Clicking Energy barrier");
                Timer.waitCondition(() -> GhostsAhoyConst.PORT_PHYSMATIS.contains(Player.getPosition()), 6000, 8000);
            }
        }
        return GhostsAhoyConst.PORT_PHYSMATIS.contains(Player.getPosition());
    }

    public static boolean enterPortPhysmatis() {
        if (!GhostsAhoyConst.PORT_PHYSMATIS.contains(Player.getPosition())) {
            General.println("[Debug]: Going to Port Physmatis");
            PathingUtil.walkToArea(GhostsAhoyConst.OUTSIDE_ENERGY_BARRIER, true);
        }
        return handleEnergyBarrier();
    }

    private void getItems3() {
        BankManager.open(true);
        BankManager.checkEquippedGlory();
        BankManager.depositAll(true);
        itemsForStep2.withdrawItems();
        Utils.equipItem(ItemId.GHOST_SPEAK_AMULET);
        Utils.equipItem(ItemId.STAFF_OF_FIRE, "Wield");
    }


    private void getItemsForQuestStep1() {
        if (Inventory.find(ItemId.ECTO_TOKENS).length > 0 ||
                (BankCache.isInitialized() && BankCache.getStack(ItemId.ECTO_TOKENS) > 25)) {
            itemsForStep1.withdrawItems();
            BankManager.close(true);
            Utils.equipItem(ItemId.GHOST_SPEAK_AMULET);
            Utils.equipItem(ItemId.LEATHER_GLOVES);
        } else {
            Log.log("[Debug]: Either bankCache is not intialiazed (" +BankCache.isInitialized() + ") or we're missing ectotokens");
        }
    }

    BuyItemsStep buyStep = new BuyItemsStep(itemsToBuy);


    public void buyAndGetItems() {
        scripts.cQuesterV2.status = "Buying items";
        General.println("[Debug]: Buying Items");
        buyStep.buyItems();
        scripts.cQuesterV2.status = "Withdrawing items";
        General.println("[Debug]: Withdrawing Items");
        itemsForTokens.withdrawItems();
    }


    public void getEctoFundus() {
        if (Inventory.find(ItemId.ECTO_TOKENS).length < 1) {
            if (!GhostsAhoyConst.SLIME_LEVEL1.contains(Player.getPosition()) && Inventory.find(ItemId.EMPTY_BUCKET).length > 0 &&
                    Inventory.find(ItemId.BONEMEAL).length < 1) {
                scripts.cQuesterV2.status = "Going to Ectofuntus";
                General.println("[Debug]: " + scripts.cQuesterV2.status);
                PathingUtil.walkToTile(new RSTile(3683, 9888, 0), 2, false);
                Timer.waitCondition(() -> GhostsAhoyConst.SLIME_LEVEL1.contains(Player.getPosition()), 15000, 20000);
            }
            if (GhostsAhoyConst.SLIME_LEVEL1.contains(Player.getPosition()) && Inventory.find(ItemId.EMPTY_BUCKET).length > 0 &&
                    Inventory.find(ItemId.BONEMEAL).length < 1) {
                scripts.cQuesterV2.status = "Getting Slime";
                General.println("[Debug]: " + scripts.cQuesterV2.status);

                if (Utils.useItemOnObject(ItemId.EMPTY_BUCKET, "Pool of Slime"))
                    Timer.abc2WaitCondition(() -> Inventory.find(ItemId.EMPTY_BUCKET).length < 1, 20000, 25000);
            }

            if (!GhostsAhoyConst.BONE_CRUSHER_AREA.contains(Player.getPosition()) && Inventory.find(ItemId.EMPTY_BUCKET).length < 1 &&
                    Inventory.find(ItemId.BONES).length > 0) {
                scripts.cQuesterV2.status = "Going to grinder";
                General.println("[Debug]: " + scripts.cQuesterV2.status);
                PathingUtil.walkToArea(GhostsAhoyConst.BONE_CRUSHER_AREA, false);
            }
            if (GhostsAhoyConst.BONE_CRUSHER_AREA.contains(Player.getPosition()) && Inventory.find(ItemId.BONEMEAL).length < 7) {
                scripts.cQuesterV2.status = "Getting bonemeal";
                General.println("[Debug]: " + scripts.cQuesterV2.status);
                if (Utils.clickObject("Bin", "Empty", false))
                    PathingUtil.movementIdle();

                if (Utils.useItemOnObject(ItemId.BONES, "Loader"))
                    Timer.abc2WaitCondition(() ->
                            Inventory.find(Filters.Items.nameContains("onemeal")).length ==7,
                            85000, 95000);
                Waiting.waitNormal(500,50);
            }

            if (GhostsAhoyConst.BONE_CRUSHER_AREA.contains(Player.getPosition()) && Inventory.find(ItemId.BONES).length < 1) {
                scripts.cQuesterV2.status = "Worshiping Ectofuntus";
                General.println("[Debug]: " + scripts.cQuesterV2.status);
                PathingUtil.walkToArea(GhostsAhoyConst.ECTOFUNTUS, false);
            }

            if (Inventory.find(ItemId.BONEMEAL).length > 0) {
                scripts.cQuesterV2.status = "Worshiping Ectofuntus";
                General.println("[Debug]: " + scripts.cQuesterV2.status);
                PathingUtil.walkToArea(GhostsAhoyConst.ECTOFUNTUS, false);

                for (int i = 0; i < 7; i++) {
                    RSItem[] invBoneMeal = Inventory.find(ItemId.BONEMEAL);

                    if (Utils.clickObject("Ectofuntus", "Worship", false))
                        Timer.waitCondition(() -> Inventory.find(ItemId.BONEMEAL).length < invBoneMeal.length, 4000, 9000);

                }
                Utils.equipItem(ItemId.GHOST_SPEAK_AMULET);

            }
            claimEctoTokens();
        }
    }

    NPCStep claimEctoTokens = new NPCStep("Ghost disciple", GhostsAhoyConst.NECROVOUS_TILE);


    public void claimEctoTokens() {
        if (Inventory.find(ItemId.BONES).length < 1 && Inventory.find(ItemId.ECTO_TOKENS).length < 1) {
            scripts.cQuesterV2.status = "Going to claim ecto-tokens";
            General.println("[Debug]: " + scripts.cQuesterV2.status);
            PathingUtil.walkToArea(GhostsAhoyConst.ECTOFUNTUS, false);
            Utils.equipItem(ItemId.GHOST_SPEAK_AMULET);
            claimEctoTokens.execute();
        }
    }


    public void makeNettleTea() {
        RSItem[] nettleTea = Inventory.find(ItemId.NETTLE_TEA);
        if (nettleTea.length == 0) {
            scripts.cQuesterV2.status = "Making Nettle tea";

            if (Utils.useItemOnItem(ItemId.NETTLES, ItemId.BOWL_OF_WATER)) {
                Timer.waitCondition(() -> Interfaces.isInterfaceSubstantiated(270), 1500, 2000);
                if (Interfaces.isInterfaceSubstantiated(270)) {
                    Keyboard.typeKeys((char) KeyEvent.VK_SPACE);
                    Timer.waitCondition(() -> Inventory.find(ItemId.NETTLE_WATER).length > 0, 1500, 2000);
                }
            }

            if (Utils.useItemOnItem(ItemId.TINDERBOX, ItemId.LOGS))
                Timer.waitCondition(() -> Objects.findNearest(3, "Fire").length > 0, 7000, 10000);

            if (Utils.useItemOnObject(ItemId.NETTLE_WATER, "Fire")) {
                Timer.waitCondition(() -> Interfaces.isInterfaceSubstantiated(270) ||
                        Inventory.find(ItemId.NETTLE_TEA).length > 0, 2500, 3200);
                if (Interfaces.isInterfaceSubstantiated(270)) {
                    Keyboard.typeKeys((char) KeyEvent.VK_SPACE);
                    Timer.waitCondition(() -> Inventory.find(ItemId.NETTLE_WATER).length > 0, 1500, 2000);
                }
            }
        }
    }

    public void pickNettles() {
        if (Inventory.find(ItemId.ECTO_TOKENS).length > 0){
        RSItem[] nettles = Inventory.find(ItemId.NETTLES, ItemId.NETTLE_TEA);
        if (nettles.length < 3) {
            scripts.cQuesterV2.status = "Getting nettles (x3)";
            //equip leather gloves
            if (Equipment.isEquipped(ItemId.LEATHER_GLOVES))
                Utils.equipItem(ItemId.LEATHER_GLOVES);
            RSArea nettleArea = new RSArea(new RSTile(3089, 3468, 0), new RSTile(3091, 3468, 0));
            PathingUtil.walkToTile(GhostsAhoyConst.NETTLE_TILE, 2, false);
            for (int i = 0; i < 3; i++) {
                RSObject net = Entities.find(ObjectEntity::new)
                        .inArea(nettleArea)
                        .idEquals(1181)
                        .getFirstResult();

                int b = i;
                General.println("[Debug]: Picknettles b = " + b);
                if (net != null && Utils.clickObject(net, "Pick", false))
                    Timer.waitCondition(() -> Inventory.find(ItemId.NETTLES).length > b, 3500, 4500);

                General.sleep(150, 450);
            }
        }
        }
    }

    public void startQuest() {
        if (Inventory.find(ItemId.ECTO_TOKENS).length > 0) {
            scripts.cQuesterV2.status = "Going to start";
            enterPortPhysmatis();
            questStartStep.execute();
        }
    }


    public void croneStep3() {
        scripts.cQuesterV2.status = "Going to Crone";
        croneStep3.execute();
    }

    public void makeCupOfTea() {
        scripts.cQuesterV2.status = "Making tea for crone";
        makeNettleTea();
        if (Inventory.find(ItemId.CUP_OF_TEA).length == 0 &&
                Utils.useItemOnItem(ItemId.NETTLE_TEA, ItemId.PORCELAIN_CUP))
            Timer.waitCondition(() -> Inventory.find(ItemId.CUP_OF_TEA).length > 0, 1500, 2000);

        if (Inventory.find(ItemId.CUP_OF_TEA).length > 0 &&
                Utils.useItemOnItem(ItemId.BUCKET_OF_MILK, ItemId.CUP_OF_TEA))
            Timer.waitCondition(() -> Inventory.find(ItemId.CUP_OF_TEA_W_MILK).length > 0, 1500, 2000);
    }

    public void useTeaOnCrone() {
        if ( Utils.useItemOnItem(ItemId.BUCKET_OF_MILK, ItemId.CUP_OF_TEA))
            Timer.waitCondition(() -> Inventory.find(ItemId.CUP_OF_TEA_W_MILK).length > 0, 1500, 2000);

        if (Utils.useItemOnNPC(ItemId.CUP_OF_TEA_W_MILK, GhostsAhoyConst.CRONE_ID)) {
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation();
        }
    }


    public void repairShip() {
        if (Utils.useItemOnItem(ItemId.THREAD, ItemId.MODEL_SHIP))
            Timer.waitCondition(() -> Inventory.find(ItemId.REPAIRED_SHIP).length > 0, 1500, 2500);
    }

    public void goToBoat() {

        if (Player.getPosition().getPlane() == 0) {
            scripts.cQuesterV2.status = "Going to boat";
            PathingUtil.walkToTile(new RSTile(3605, 3538, 0), 2, false);
            scripts.cQuesterV2.status = "Going to boat - local nav";
            PathingUtil.localNavigation(GhostsAhoyConst.BEFORE_BOAT_AREA.getRandomTile());
            if (Player.getPosition().getPlane() == 0 &&
                    Utils.clickObject("Ship's ladder", "Climb-up", true)) {
                Timer.waitCondition(() -> GhostsAhoyConst.BOAT_AREA_PLANE_1.contains(Player.getPosition()), 7000, 9000);
            }
        }
        if (GhostsAhoyConst.BOAT_AREA_PLANE_1.contains(Player.getPosition()) &&
                Utils.clickObject(16111, "Climb-up", true)) {
            Timer.waitCondition(() -> GhostsAhoyConst.BOAT_AREA_PLANE_2.contains(Player.getPosition()), 7000, 9000);

        }
    }


    int WIND_SPEED_VARBIT = 205;//1 == slow

    public void checkMast() {
        if (GhostsAhoyConst.skull == null || GhostsAhoyConst.bottomHalf == null || GhostsAhoyConst.topHalf == null) {

            for (int i = 0; i < 10; i++) {
                General.println("[Debug]: Checking mast - i = " + i);
                if (Utils.getVarBitValue(WIND_SPEED_VARBIT) != 1) {
                    scripts.cQuesterV2.status = "Waiting on windspeed";
                    Timer.waitCondition(() -> Utils.getVarBitValue(WIND_SPEED_VARBIT) == 1, 15000, 20000);
                }
                if (Utils.getVarBitValue(WIND_SPEED_VARBIT) == 1
                        && Utils.clickObject("Mast", "Search", true)) {
                    Timer.waitCondition(() -> Interfaces.isInterfaceSubstantiated(229, 1), 1500, 3000);
                    if (Interfaces.isInterfaceSubstantiated(229, 1)) {
                        String msg = Interfaces.get(229, 1).getText();
                        //General.println("[Debug]: scripts.cQuesterV2.status: " + msg);
                        if (msg != null) {
                            if (msg.contains("top half")) {
                                GhostsAhoyConst.topHalf = determineColor();
                                General.println("[Debug]: Top half is " + GhostsAhoyConst.topHalf.toString());
                            } else if (msg.contains("bottom half")) {
                                GhostsAhoyConst.bottomHalf = determineColor();
                                General.println("[Debug]: Bottom half is " + GhostsAhoyConst.bottomHalf.toString());
                            } else if (msg.contains("skull")) {
                                GhostsAhoyConst.skull = determineColor();
                                General.println("[Debug]: Skull is " + GhostsAhoyConst.skull.toString());
                            }
                        }
                        if (Player.getPosition().click())// gets rid of dialog box
                            Timer.waitCondition(() -> !NPCInteraction.isConversationWindowUp(), 2200, 3000);

                    }
                }
                if (GhostsAhoyConst.skull != null && GhostsAhoyConst.bottomHalf != null && GhostsAhoyConst.topHalf != null) {
                    General.println("[Debug]: We have all the colors");
                    break;
                }
                General.sleep(300, 1200);
            }
        }
    }


    public GhostsAhoyConst.COLOR determineColor() {
        if (Interfaces.isInterfaceSubstantiated(229, 1)) {
            String msg = Interfaces.get(229, 1).getText();
            General.println("[Debug]: scripts.cQuesterV2.status: " + msg);
            if (msg != null) {
                if (msg.contains("red.")) { //need period
                    return GhostsAhoyConst.COLOR.RED;
                } else if (msg.contains("purple")) {
                    return GhostsAhoyConst.COLOR.PURPLE;
                } else if (msg.contains("blue")) {
                    return GhostsAhoyConst.COLOR.BLUE;
                } else if (msg.contains("yellow")) {
                    return GhostsAhoyConst.COLOR.YELLOW;
                } else if (msg.contains("orange")) {
                    return GhostsAhoyConst.COLOR.ORANGE;
                } else if (msg.contains("green")) {
                    return GhostsAhoyConst.COLOR.GREEN;
                }
            }

        }
        return null;
    }


    public void makeAndDye(int dye1, int dye2, int finalDye, String partName) {
        if (Inventory.find(finalDye).length == 0 &&
                Utils.useItemOnItem(dye1, dye2)) {
            Timer.waitCondition(() -> Inventory.find(finalDye).length == 1, 1500, 2200);
        }
        dyeShip(finalDye, partName);
    }

    public void dyeShip(int finalDye, String partName) {
        if (Inventory.find(finalDye).length >= 1 &&
                Utils.useItemOnItem(finalDye, ItemId.REPAIRED_SHIP)) {
            Timer.waitCondition(() -> Interfaces.isInterfaceSubstantiated(219, 1), 2000, 4000);
            General.println("[DyeShip]: Coloring boat part: " + partName);
            int dyeNum = Inventory.find(finalDye).length;
            if (InterfaceUtil.clickInterfaceText(219, 1, partName)) {
                Timer.waitCondition(() -> Inventory.find(finalDye).length < dyeNum, 2000, 4000);
            }
        }
    }

    public void colorBoat(GhostsAhoyConst.COLOR part, String partName) {
        if (GhostsAhoyConst.skull != null && GhostsAhoyConst.bottomHalf != null && GhostsAhoyConst.topHalf != null) {
            scripts.cQuesterV2.status = "Coloring boat";
            General.println("[Debug]: Coloring boat part: " + partName);
            General.println("[Debug]: Bottomhalf: " + GhostsAhoyConst.bottomHalf);
            General.println("[Debug]: Skull: " + GhostsAhoyConst.skull);
            General.println("[Debug]: TopHalf: " + GhostsAhoyConst.topHalf);

            if (part != null) {
                if (part.equals(GhostsAhoyConst.COLOR.ORANGE)) {
                    General.println("[Debug]: Making orange");
                    makeAndDye(ItemId.YELLOW_DYE, ItemId.RED_DYE, ItemId.ORANGE_DYE, partName);
                } else if (part.equals(GhostsAhoyConst.COLOR.PURPLE)) {
                    General.println("[Debug]: Making Purple");
                    makeAndDye(ItemId.BLUE_DYE, ItemId.RED_DYE, ItemId.PURPLE_DYE, partName);
                } else if (part.equals(GhostsAhoyConst.COLOR.GREEN)) {
                    General.println("[Debug]: Making green");
                    makeAndDye(ItemId.BLUE_DYE, ItemId.YELLOW_DYE, ItemId.GREEN_DYE, partName);
                } else if (part.equals(GhostsAhoyConst.COLOR.YELLOW)) {
                    dyeShip(ItemId.YELLOW_DYE, partName);
                } else if (part.equals(GhostsAhoyConst.COLOR.RED)) {
                    dyeShip(ItemId.RED_DYE, partName);
                } else if (part.equals(GhostsAhoyConst.COLOR.BLUE)) {
                    dyeShip(ItemId.BLUE_DYE, partName);
                }
            }
        }
    }

    public void oldManStepFull() {
        if (Player.getPosition().getPlane() == 2 &&
                Utils.clickObject(16112, "Climb-down", true)) {
            Timer.waitCondition(() -> Player.getPosition().getPlane() == 1, 3400, 4800);
        }
        if (Player.getPosition().getPlane() == 1) {
            if (Utils.clickObject(5244, "Open", true))
                Timer.waitCondition(() -> Objects.findNearest(5, 5245).length > 0, 4400, 5800);

            PathingUtil.clickScreenWalk(GhostsAhoyConst.BOAT_CABIN_AREA.getRandomTile());
            PathingUtil.movementIdle();
        }

        oldManStep.execute();
    }



    public void openChest() {
        if (Player.getPosition().getPlane() == 1) {
            cQuesterV2.status = "Going to chest 1";
            if (Utils.clickObject(5244, "Open", true))
                Timer.waitCondition(() -> Objects.findNearest(5, 5245).length > 0, 4400, 5800);

            PathingUtil.clickScreenWalk(new RSTile(3619, 3544, 1));
            PathingUtil.movementIdle();

            openChest1.useItemOnObject();
        }
    }

    public void getMapScrap() {
        if (Inventory.find(GhostsAhoyConst.MAP_PIECE_1).length == 0 &&
                Player.getPosition().getPlane() == 1) {
            cQuesterV2.status = "Getting Map scrap 1";
            if (Utils.clickObject("Closed Chest", "Open", true))
                Timer.waitCondition(() -> Inventory.find(GhostsAhoyConst.MAP_PIECE_1).length > 0, 4400, 5800);
        }
    }

    public boolean searchChestForPiece3() {
        RSObject chests = Entities.find(ObjectEntity::new)
                .idEquals(16119)
                .tileEquals(new RSTile(3618, 3542, 0))
                .getFirstResult();
        return chests != null && Utils.clickObject(chests, "Search");
    }

    public void goToLobster() {
        if (Inventory.find(GhostsAhoyConst.MAP_PIECE_1).length == 1 &&
                Inventory.find(GhostsAhoyConst.MAP_PIECE_2).length == 1 &&
                Inventory.find(GhostsAhoyConst.MAP_PIECE_3).length == 0 &&
                !ALL_ROCKS_AREA.contains(Player.getPosition())) {
            if (Player.getPosition().getPlane() == 1 && Utils.clickObject(16112, "Climb-down", true)) {
                Timer.waitCondition(() -> Player.getPosition().getPlane() == 0, 5000, 7000);
            }
            if (Player.getPosition().getPlane() == 0) {
                cQuesterV2.status = "Getting Map scrap 2 - finding lobster";
                if (!Combat.isUnderAttack()) {
                    RSObject chests = Entities.find(ObjectEntity::new)
                            .nameContains("Chest")
                            .tileEquals(new RSTile(3618, 3542, 0))
                            .getFirstResult();
                    if (chests != null && Utils.clickObject(chests, "Open")) {
                        Timer.waitCondition(() -> Entities.find(ObjectEntity::new)
                                .idEquals(16119)
                                .tileEquals(new RSTile(3618, 3542, 0))
                                .getFirstResult() != null, 4000, 6000);
                    }

                    Autocast.enableAutocast(Autocast.FIRE_STRIKE);
                    org.tribot.script.sdk.Combat.setAutocastSpell(org.tribot.script.sdk.Combat.AutocastableSpell.FIRE_STRIKE);

                    if (searchChestForPiece3()) {
                        Timer.waitCondition(Combat::isUnderAttack, 5500, 7000);
                    }
                }

            }
            if (Combat.isUnderAttack()) {
                int eatAt = General.random(40, 60);
                CombatUtil.waitUntilOutOfCombat(eatAt);
            }
            if (!Combat.isUnderAttack() && searchChestForPiece3()) {
                Timer.waitCondition(() -> Inventory.find(GhostsAhoyConst.MAP_PIECE_3).length == 1, 4500, 7000);
            }
        }
    }


    public void takeRowboat() {
        // need 25 ectotokens, this currently doesn't check
        if (!bookOfHoraciaReq.check()) {
            if (itemsForBookOfHoracio.check()) {
                if (!GhostsAhoyConst.DRAGONTOOTH_ISLAND.contains(Player.getPosition())) {
                    enterPortPhysmatis();
                    cQuesterV2.status = "Going to rowboat";
                    takeRowingBoat.execute();
                    Timer.waitCondition(() -> GhostsAhoyConst.DRAGONTOOTH_ISLAND.contains(Player.getPosition()), 6000, 9000);
                }
            }
            if (GhostsAhoyConst.DRAGONTOOTH_ISLAND.contains(Player.getPosition())) {
                cQuesterV2.status = "Going to dig";
                General.println("[Debug]: Going to dig");
                if (PathingUtil.localNavigation(DIG_TILE))
                    PathingUtil.movementIdle();

                if (AccurateMouse.walkScreenTile(DIG_TILE))
                    PathingUtil.movementIdle();

                if (Utils.clickInventoryItem(ItemId.SPADE)) {
                    Timer.slowWaitCondition(() -> Inventory.find(GhostsAhoyConst.BOOK_OF_HORACIO)
                            .length == 1, 5500, 6500);
                }
            }
        }
    }

    RSTile DIG_TILE = new RSTile(3803, 3530, 0);

    public void leaveDragontoothIsland() {
        if (GhostsAhoyConst.DRAGONTOOTH_ISLAND.contains(Player.getPosition()) && bookOfHoraciaReq.check()) {
            cQuesterV2.status = "Going rowboat";
            General.println("[Debug]: Going to dig");
            if (PathingUtil.localNavigation(new RSTile(3792, 3559, 0)))
                PathingUtil.movementIdle();
            if (Utils.clickNPC("Ghost captain", "Travel")) {
                Timer.waitCondition(() -> !GhostsAhoyConst.DRAGONTOOTH_ISLAND.contains(Player.getPosition()), 4500, 5000);
            }
        }
    }


    //NPCStep talkToAkHaranu = new NPCStep(NpcID.AKHARANU, new RSTile(3689, 3499, 0), "Okay, wait here - I'll get you your bow.");
    public void setTalkToAkHaranu() {
        cQuesterV2.status = "Going to AkHaranu";
        if (!GhostsAhoyConst.DRAGONTOOTH_ISLAND.contains(Player.getPosition()) && bookOfHoraciaReq.check()) {
            talkToAkHaranu.setRadius(7);
            talkToAkHaranu.execute();
        }
    }

    public void hanldeRobin() {
        cQuesterV2.status = "Going to Robin";
        if (PathingUtil.localNavigation(new RSTile(3675, 3495, 0)))
            PathingUtil.movementIdle();
        if (Interfaces.get(9) == null) {
            talkToRobin.setRadius(10);
            talkToRobin.execute();
        }
        General.println("here");
        Timer.waitCondition(() -> Interfaces.get(9) != null, 4000, 6000);
        playRuneDraw();
    }

    public void handleInnKeeper() {
        if (Inventory.find(GhostsAhoyConst.SHEET).length == 0 &&
                !Equipment.isEquipped(GhostsAhoyConst.GHOST_SHEET) &&
                Inventory.find(GhostsAhoyConst.GHOST_SHEET).length == 0) {
            if (enterPortPhysmatis()) {
                if (PathingUtil.localNavigation(new RSTile(3675, 3495, 0)))
                    PathingUtil.movementIdle();
                General.println("Talking to dude");
                talkToInnkeeper.setRadius(7);
                talkToInnkeeper.execute();
            }
        }
    }

    public void makeGhostSheet() {
        if (Inventory.find(GhostsAhoyConst.SHEET).length == 1 &&
                Utils.useItemOnItem(ItemId.BUCKET_OF_SLIME, GhostsAhoyConst.SHEET)) {
            Timer.waitCondition(() -> Inventory.find(GhostsAhoyConst.GHOST_SHEET).length == 1, 3500, 5000);
        }
        if (!Equipment.isEquipped(GhostsAhoyConst.GHOST_SHEET)) {
            Utils.clickInventoryItem(GhostsAhoyConst.GHOST_SHEET);
        }
    }

    public void playRuneDraw() {
        for (int i = 0; i < 10; i++) {
            cQuesterV2.status = "Playing runedraw- i = " + i;
            General.println("Playing runedraw- i = " + i);
            Timer.waitCondition(() -> Interfaces.get(9) != null, 4000, 6000);
            if (Interfaces.get(9) != null) {
                if (i == 1 && InterfaceUtil.click(9, 5)) {
                    Timer.slowWaitCondition(() -> InterfaceUtil.checkText(9, 28, "Your turn"), 2500, 4000);
                }
                if (InterfaceUtil.checkText(9, 28, "Your turn") &&
                        InterfaceUtil.click(9, 5)) {
                    Timer.slowWaitCondition(() -> InterfaceUtil.checkText(9, 28, "Your turn"),
                            5500, 6500);
                }
                if ((InterfaceUtil.checkText(9, 28, "You win!") ||
                        InterfaceUtil.checkText(9, 28, "You lose!"))) {
                    General.println("Closing interfaces");
                    RSInterface close = Interfaces.get(9, 35);
                    if (close != null && close.click())
                        Timer.waitCondition(() -> Interfaces.get(9) == null, 3500, 5000);
                    NPCInteraction.waitForConversationWindow();
                    if (NPCInteraction.isConversationWindowUp()) {
                        NPCInteraction.handleConversation("Yes, I'll give you a game.");
                        i = 0;
                    }
                }
            }
            if (Interfaces.get(9) == null)
                break;
        }
        if (NPCInteraction.isConversationWindowUp()) {
            NPCInteraction.handleConversation("Yes, I'll give you a game.");

        }

    }


    public void returnBow() {
        cQuesterV2.status = "Returning Bow";
        if (GhostsAhoyConst.BAR_AREA.contains(Player.getPosition())) {
            General.println("[Debug]: Leaving bar");
            PathingUtil.localNavigation(new RSTile(3668, 3497, 0));
            PathingUtil.movementIdle();
        }
        talkToAkHaranu.execute();
    }

    public void goToGravingas() {
        if (Inventory.find(GhostsAhoyConst.SHEET).length == 0 &&
                Inventory.find(GhostsAhoyConst.GHOST_SHEET).length == 0 &&
                !Equipment.isEquipped(GhostsAhoyConst.BEDSHEET)) {
            handleInnKeeper();
        } else {
            cQuesterV2.status = "Going to Gravingas";
            if (GhostsAhoyConst.BAR_AREA.contains(Player.getPosition())) {
                General.println("[Debug]: Leaving bar");
                PathingUtil.localNavigation(new RSTile(3668, 3497, 0));
                PathingUtil.movementIdle();
            }
            makeGhostSheet();
            talkToGravingas.execute();
        }
    }

    /**
     *
     */


    public void getSignatures() {
        if (Inventory.find(GhostsAhoyConst.SHEET).length == 0 &&
                Inventory.find(GhostsAhoyConst.GHOST_SHEET).length == 0 &&
                !Equipment.isEquipped(GhostsAhoyConst.BEDSHEET)) {
            handleInnKeeper();
        }
        if (GhostsAhoyConst.BAR_AREA.contains(Player.getPosition())) {
            General.println("[Debug]: Leaving bar");
            PathingUtil.localNavigation(new RSTile(3668, 3497, 0));
            PathingUtil.movementIdle();
        }


        RSNPC[] villager = Entities.find(NpcEntity::new)
                .nameContains("Ghost villager")
                .inArea(new RSArea(Player.getPosition(), 7))// NPCs.findNearest("Ghost villager");
                .sortByDistance()
                .getResults();

        for (RSNPC n : villager) {
            General.sleep(50);
            makeGhostSheet();
            RSItem[] bedSheet = Inventory.find(GhostsAhoyConst.GHOST_SHEET);
            if (!Equipment.isEquipped(GhostsAhoyConst.GHOST_SHEET) && bedSheet.length > 0)
                bedSheet[0].click("Wear");


            cQuesterV2.status = "Talking to Villagers: I=" + (Utils.getVarBitValue(209) - 1);
            NPCStep signatureStep = new NPCStep(n, new RSTile(3662, 3494, 0));
            signatureStep.setRadius(6);
            signatureStep.execute();
            if (Utils.getVarBitValue(209) == 11)
                break;
            //world hop
        }
    }

    public void getBoneKey() {
        RSItem[] bedSheet = Equipment.find(GhostsAhoyConst.BEDSHEET);
        if (Equipment.isEquipped(GhostsAhoyConst.BEDSHEET) && bedSheet.length > 0)
            bedSheet[0].click("Remove");

        if (Inventory.find(GhostsAhoyConst.BONE_KEY).length == 0) {
            necrovarusBoneKeyStep.execute();
            Timer.waitCondition(() -> GroundItems.find(GhostsAhoyConst.BONE_KEY).length > 0, 3500, 5000);
            Utils.clickGroundItem(GhostsAhoyConst.BONE_KEY);
        }
    }

    public void getRobes() {
        if (Inventory.find(GhostsAhoyConst.ROBES).length == 0) {
            PathingUtil.walkToTile(new RSTile(3654, 3515, 1), 2, false);
            PathingUtil.movementIdle();
            if (Utils.useItemOnObject(GhostsAhoyConst.BONE_KEY, "Door")) {
                PathingUtil.movementIdle();
            }
            if (Utils.clickObject("Door", "Open", false)) {
                PathingUtil.movementIdle();
            }
            // if (in room){
            if (Utils.clickObject(16644, "Open", false)) {
                Timer.waitCondition(() -> Objects.findNearest(6, 16645).length > 0, 5000, 7000);
            }
            if (Utils.clickObject(16645, "Search", false)) {
                Timer.waitCondition(() -> Inventory.find(GhostsAhoyConst.ROBES).length > 0, 5000, 7000);
                if (Utils.clickObject("Door", "Open", false)) {
                    PathingUtil.movementIdle();
                }
            }
            //leaveroome
            // }
        }
    }

    public boolean equipGhostspeakAmulet() {
        if (!Equipment.isEquipped("Ghostspeak amulet")) {
            RSItem[] ammy = Inventory.find("Ghostspeak amulet");
            if (ammy.length > 0 && ammy[0].click())
                return Timer.waitCondition(() -> Equipment.isEquipped("Ghostspeak amulet"), 3000, 4000);
        }
        return Equipment.isEquipped("Ghostspeak amulet");
    }

    public void giveCroneRobes() {
        if (Inventory.find(GhostsAhoyConst.ROBES).length == 1) {
            cQuesterV2.status = "Going to Crone";
            General.println("[Debug]: going to give crone robes");
            if (equipGhostspeakAmulet())
                croneStepNoDialogOptions.execute();
        }
    }

    public void combineMap() {
        if (mapRequirement.check()) {
            Utils.useItemOnItem(GhostsAhoyConst.MAP_PIECE_1, GhostsAhoyConst.MAP_PIECE_2);
            Timer.waitCondition(() -> Inventory.find(GhostsAhoyConst.COMPLETE_MAP).length == 1, 3500, 4200);
        }
    }


    public boolean clickRockAt(RSTile tile) {
        RSObject rock = Entities.find(ObjectEntity::new)
                .tileEquals(tile)
                .getFirstResult();

        return rock != null && Utils.clickObject(rock, "Jump-To");
    }

    RSTile TO_GANGPLANK_BACK_TO_SHIP_TILE = new RSTile(3605, 3548, 0);
    RSTile ROCK_1_TILE = new RSTile(3604, 3550, 0);
    RSTile ROCK_2_TILE = new RSTile(3599, 3552, 0);
    RSTile ROCK_3_TILE = new RSTile(3595, 3554, 0);
    RSTile ROCK_4_TILE = new RSTile(3597, 3559, 0);
    RSTile ROCK_5_TILE = new RSTile(3599, 3564, 0);


    RSTile LANDING_1_TILE = new RSTile(3605, 3548, 0); //after gangplank
    RSTile LANDING_2_TILE = new RSTile(3602, 3550, 0);
    RSTile LANDING_3_TILE = new RSTile(3597, 3552, 0);
    RSTile LANDING_4_TILE = new RSTile(3595, 3556, 0);
    RSTile LANDING_5_TILE = new RSTile(3597, 3561, 0);
    RSTile LANDING_6_TILE = new RSTile(3601, 3564, 0);

    RSArea FINAL_ROCK_AREA = new RSArea(
            new RSTile[]{
                    new RSTile(3601, 3565, 0),
                    new RSTile(3602, 3566, 0),
                    new RSTile(3606, 3568, 0),
                    new RSTile(3609, 3566, 0),
                    new RSTile(3607, 3561, 0),
                    new RSTile(3604, 3562, 0),
                    new RSTile(3602, 3563, 0),
                    new RSTile(3601, 3564, 0)
            }
    );

    RSArea ALL_ROCKS_AREA = new RSArea(
            new RSTile[]{
                    new RSTile(3610, 3547, 0),
                    new RSTile(3610, 3568, 0),
                    new RSTile(3593, 3568, 0),
                    new RSTile(3594, 3549, 0),
                    new RSTile(3604, 3549, 0),
                    new RSTile(3605, 3547, 0),
                    new RSTile(3606, 3547, 0),
                    new RSTile(3606, 3550, 0),
                    new RSTile(3610, 3550, 0)
            }
    );

    public void jumpRocks(boolean towardsRock) {
        if (Player.getPosition().getPlane() == 1) {
            RSObject gangplank = Entities.find(ObjectEntity::new)
                    .tileEquals(new RSTile(3605, 3546, 1))
                    .getFirstResult();
            message = "Crossing Gangplank";
            if (gangplank != null) {
                DaxCamera.focus(gangplank);

                if (Utils.clickObject(gangplank, "Cross"))
                    Timer.slowWaitCondition(() -> Player.getPosition().getPlane() == 0, 6000, 8000);
            }
        }
        if (Inventory.find(GhostsAhoyConst.MAP_PIECE_2).length == 0) {
            if (LANDING_1_TILE.equals(Player.getPosition()) && clickRockAt(ROCK_1_TILE)) {
                message = "Crossing Rock 1";
                Timer.slowWaitCondition(() -> Player.getPosition().equals(LANDING_2_TILE), 6000, 7000);
            }
            if (LANDING_2_TILE.equals(Player.getPosition()) && clickRockAt(ROCK_2_TILE)) {
                message = "Crossing Rock 2";
                Timer.slowWaitCondition(() -> Player.getPosition().equals(LANDING_3_TILE), 6000, 7000);
            }
            if (LANDING_3_TILE.equals(Player.getPosition()) && clickRockAt(ROCK_3_TILE)) {
                message = "Crossing Rock 3";
                Timer.slowWaitCondition(() -> Player.getPosition().equals(LANDING_4_TILE), 6000, 7000);
            }
            if (LANDING_4_TILE.equals(Player.getPosition()) && clickRockAt(ROCK_4_TILE)) {
                message = "Crossing Rock 4";
                Timer.slowWaitCondition(() -> Player.getPosition().equals(LANDING_5_TILE), 6000, 7000);
            }
            if (LANDING_5_TILE.equals(Player.getPosition()) && clickRockAt(ROCK_5_TILE)) {
                message = "Crossing Rock 5";
                Timer.slowWaitCondition(() -> FINAL_ROCK_AREA.contains(Player.getPosition()), 6000, 7000);
            }
            if (FINAL_ROCK_AREA.contains(Player.getPosition())) {
                openChest2();
            }
        } else {
            if (FINAL_ROCK_AREA.contains(Player.getPosition()) && clickRockAt(LANDING_6_TILE)) {
                Timer.slowWaitCondition(() -> Player.getPosition().equals(ROCK_5_TILE), 6000, 7000);
            }
            if (Player.getPosition().equals(ROCK_5_TILE) && clickRockAt(LANDING_5_TILE)) {
                Timer.slowWaitCondition(() -> Player.getPosition().equals(ROCK_4_TILE), 6000, 7000);
            }
            if (Player.getPosition().equals(ROCK_4_TILE) && clickRockAt(LANDING_4_TILE)) {
                Timer.slowWaitCondition(() -> Player.getPosition().equals(ROCK_3_TILE), 6000, 7000);
            }
            if (Player.getPosition().equals(ROCK_3_TILE) && clickRockAt(LANDING_3_TILE)) {
                Timer.slowWaitCondition(() -> Player.getPosition().equals(ROCK_2_TILE), 6000, 7000);
            }
            if (Player.getPosition().equals(ROCK_2_TILE) && clickRockAt(LANDING_2_TILE)) {
                Timer.slowWaitCondition(() -> Player.getPosition().equals(ROCK_1_TILE), 6000, 7000);
            }
            if (Player.getPosition().equals(ROCK_1_TILE) ||
                    Player.getPosition().equals(TO_GANGPLANK_BACK_TO_SHIP_TILE)) {
                RSObject gangplank = Entities.find(ObjectEntity::new)
                        .tileEquals(new RSTile(3605, 3547, 0))
                        .getFirstResult();
                message = "Crossing Gangplank";
                if (gangplank != null && gangplank.click("Cross"))
                    Timer.slowWaitCondition(() -> Player.getPosition().getPlane() == 1, 6000, 7000);
            }
        }
    }

    public void openChest2() {
        if (Inventory.find(GhostsAhoyConst.MAP_PIECE_2).length == 0) {
            message = "Opening Chest";
            if (Utils.clickObject(16118, "Open", true))
                Timer.waitCondition(() -> Objects.findNearest(5, 16119).length > 0, 4400, 5800);
            message = "Searching Chest";
            if (Utils.clickObject(16119, "Search", true))
                Timer.waitCondition(() -> Inventory.find(GhostsAhoyConst.MAP_PIECE_2).length == 1, 4400, 5800);
        }
    }

    @Override
    public Priority priority() {
        return Priority.MEDIUM;
    }

    @Override
    public boolean validate() {
        return          cQuesterV2.taskList.get(0).equals(GhostsAhoy.get());
    }

    @Override
    public void execute() {
        if (!checkRequirements()) {
            Log.log("[Debug]: Missing Restless ghost requirement");
            cQuesterV2.taskList.remove(GhostsAhoy.get());
            return;
        }
        General.sleep(30, 50);
        General.println("[Debug]: VARBIT 217: " + Utils.getVarBitValue(VARBIT));
        General.println("[Debug]: VARBIT 214: " + Utils.getVarBitValue(214));

        if (Utils.getVarBitValue(VARBIT) == 0) {
         buyAndGetItems();
           getEctoFundus();
          claimEctoTokens();
            getItemsForQuestStep1();
            pickNettles();
            startQuest();
        } else if (Utils.getVarBitValue(VARBIT) == 1) {
            necrovarousStep1.execute();
        } else if (Utils.getVarBitValue(VARBIT) == 2) {
            cQuesterV2.status = "Going back to velorina";
            enterPortPhysmatis();
            velorinaStep2.execute();
        } else if (Utils.getVarBitValue(VARBIT) == 3) {
            General.println("At line 1067");
            makeNettleTea();
            croneStep3();
            makeCupOfTea();
            useTeaOnCrone();
            croneStep4.execute();
        } else if (Utils.getVarBitValue(VARBIT) == 4 &&
                Utils.getVarBitValue(214) == 0) {
        General.println("At line 1030");
            RSItem[] dyes = Entities.find(ItemEntity::new)
                    .nameContains("dye")
                    .getResults();
            if (Inventory.find(ItemId.MODEL_SHIP).length == 0 && Inventory.find(4254).length == 0) {
                cQuesterV2.status = "Crone step 4";
                croneStep4.execute();
                cQuesterV2.status = "Getting items";
                getItems3();
            } else if (dyes.length == 0) {
                getItems3();
            }
        } else if (Utils.getVarBitValue(VARBIT) == 4 &&
                Utils.getVarBitValue(214) == 1) {

            RSItem[] dyes = Entities.find(ItemEntity::new)
                    .nameContains("dye")
                    .getResults();


            if (Inventory.find(ItemId.MODEL_SHIP).length == 0 && Inventory.find(4254).length == 0) {
                croneStep4.execute();
                getItems3();
            } else if (dyes.length == 0) {
                getItems3();
            }

            repairShip();
            goToBoat();
            checkMast();
            colorBoat(GhostsAhoyConst.skull, "Skull");
            colorBoat(GhostsAhoyConst.bottomHalf, "Bottom half");
            colorBoat(GhostsAhoyConst.topHalf, "Top half");
            //  varbit doesn't change after coloring. nor does the ship ID
            oldManStepFull();
        } else if (Utils.getVarBitValue(VARBIT) == 4 &&
                Utils.getVarBitValue(214) == 2) {

            openChest();

        } else if (Utils.getVarBitValue(VARBIT) == 4 &&
                Utils.getVarBitValue(212) == 0 &&
                Utils.getVarBitValue(214) == 3) {

            getMapScrap();
            jumpRocks(true);

            goToLobster();
            combineMap();
            takeRowboat();
            leaveDragontoothIsland();
            setTalkToAkHaranu();

        } else if (Utils.getVarBitValue(VARBIT) == 4 &&
                (Utils.getVarBitValue(212) >= 1 && Utils.getVarBitValue(212) <= 6) &&
                Utils.getVarBitValue(214) == 3) {
            hanldeRobin();


        } else if (Utils.getVarBitValue(VARBIT) == 4 &&
                Utils.getVarBitValue(212) == 7 &&
                Utils.getVarBitValue(214) == 3) {
            returnBow();
        } else if (Utils.getVarBitValue(VARBIT) == 4 &&
                Utils.getVarBitValue(209) == 0 &&
                Utils.getVarBitValue(212) == 8 &&
                Utils.getVarBitValue(213) == 0 &&
                Utils.getVarBitValue(214) == 3) {
            handleInnKeeper();
            goToGravingas();

        } else if (Utils.getVarBitValue(209) < 11 &&
                Utils.getVarBitValue(211) == 3 &&
                Utils.getVarBitValue(212) == 8 &&
                Utils.getVarBitValue(214) == 3 &&
                Utils.getVarBitValue(215) == 1 &&
                Utils.getVarBitValue(217) == 4) {
            General.println("[Debug]: Get Signatures");
            getSignatures();
        } else if (Utils.getVarBitValue(209) == 11 &&
                Utils.getVarBitValue(211) == 3 &&
                Utils.getVarBitValue(212) == 8 &&
                Utils.getVarBitValue(214) == 3 &&
                Utils.getVarBitValue(215) == 1 &&
                Utils.getVarBitValue(217) == 4) {

            // talk to Necrovarus (no dialog)
            getBoneKey();
        } else if (Utils.getVarBitValue(209) == 31 &&
                Utils.getVarBitValue(211) == 3 &&
                Utils.getVarBitValue(212) == 8 &&
                Utils.getVarBitValue(214) == 3 &&
                Utils.getVarBitValue(215) == 1 &&
                Utils.getVarBitValue(217) == 4) {

            getBoneKey();
            getRobes();
            giveCroneRobes();

        } else if (Utils.getVarBitValue(209) == 31 &&
                Utils.getVarBitValue(211) == 3 &&
                Utils.getVarBitValue(212) == 8 &&
                Utils.getVarBitValue(214) == 3 &&
                Utils.getVarBitValue(215) == 1 &&
                Utils.getVarBitValue(217) == 5) {

            giveCroneRobes();

        } else if (Utils.getVarBitValue(209) == 31 &&
                Utils.getVarBitValue(211) == 3 &&
                Utils.getVarBitValue(212) == 8 &&
                Utils.getVarBitValue(214) == 3 &&
                Utils.getVarBitValue(215) == 1 &&
                Utils.getVarBitValue(217) == 6) {
            cQuesterV2.status = "Going to necrovarus";
            if (equipGhostspeakAmulet())
                necrovarusFinal.execute();

        } else if (Utils.getVarBitValue(209) == 31 &&
                Utils.getVarBitValue(211) == 3 &&
                Utils.getVarBitValue(212) == 8 &&
                Utils.getVarBitValue(214) == 3 &&
                Utils.getVarBitValue(215) == 1 &&
                Utils.getVarBitValue(217) == 7) {

            cQuesterV2.status = "Going to Velorina";
            velorinaStep2.execute();

        } else if (Utils.getVarBitValue(209) == 31 &&
                Utils.getVarBitValue(211) == 3 &&
                Utils.getVarBitValue(212) == 8 &&
                Utils.getVarBitValue(214) == 3 &&
                Utils.getVarBitValue(215) == 1 &&
                Utils.getVarBitValue(217) == 8) {
            Utils.closeQuestCompletionWindow();
            cQuesterV2.taskList.remove(GhostsAhoy.get());


        }


    }

    @Override
    public String questName() {
        return "Ghosts Ahoy";
    }

    @Override
    public boolean checkRequirements() {
        return Game.getSetting(107) == 5 &&
                Skills.getActualLevel(Skills.SKILLS.COOKING) >= 20;
    } //restless ghost done

    @Override
    public String toString() {
        return message;
    }


}
