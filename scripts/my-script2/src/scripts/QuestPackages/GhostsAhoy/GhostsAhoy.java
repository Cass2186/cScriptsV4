package scripts.QuestPackages.GhostsAhoy;

import dax.api_lib.DaxWalker;
import dax.teleports.Teleport;
import dax.walker.utils.AccurateMouse;
import dax.walker.utils.camera.DaxCamera;
import dax.walker_engine.interaction_handling.NPCInteraction;
import org.tribot.api.General;
import org.tribot.api2007.*;
import org.tribot.api2007.Combat;
import org.tribot.api2007.Equipment;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Objects;
import org.tribot.api2007.ext.Filters;
import org.tribot.api2007.types.*;
import org.tribot.script.sdk.*;
import org.tribot.script.sdk.cache.BankCache;
import org.tribot.script.sdk.query.Query;
import org.tribot.script.sdk.types.GameObject;
import org.tribot.script.sdk.types.InventoryItem;
import org.tribot.script.sdk.types.Npc;
import org.tribot.script.sdk.types.WorldTile;
import scripts.*;
import scripts.EntitySelector.Entities;
import scripts.EntitySelector.finders.prefabs.ItemEntity;
import scripts.EntitySelector.finders.prefabs.NpcEntity;
import scripts.EntitySelector.finders.prefabs.ObjectEntity;
import scripts.GEManager.GEItem;
import scripts.QuestPackages.HolyGrail.HolyGrail;
import scripts.QuestSteps.*;
import scripts.Requirements.*;
import scripts.Tasks.Priority;
import scripts.Tasks.Task;
import scripts.Timer;


import java.awt.event.KeyEvent;
import java.util.*;

public class GhostsAhoy implements QuestTask {
    private static GhostsAhoy quest;

    public static GhostsAhoy get() {
        return quest == null ? quest = new GhostsAhoy() : quest;
    }

    String message;
    int VARBIT = 217;

    RSTile DIG_TILE = new RSTile(3803, 3530, 0);
    WorldTile DIG_WORLD_TILE = new WorldTile(3803, 3530, 0);

    HashMap<Integer, Integer> invMap = new HashMap<>();
    ItemReq intialEctoTokens = new ItemReq(ItemID.ECTOTOKEN, 35, 30);

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

    NPCStep claimEctoTokens = new NPCStep("Ghost disciple", GhostsAhoyConst.NECROVOUS_TILE);

    UseItemOnObjectStep openChest1 = new UseItemOnObjectStep(GhostsAhoyConst.CHEST_KEY, "Closed chest",
            new RSTile(3619, 3544, 1), Inventory.find(GhostsAhoyConst.CHEST_KEY).length == 0);


    private boolean checkSkillReqs() {
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
                    new GEItem(ItemID.BUCKET_OF_MILK, 2, 500),
                    new GEItem(ItemID.SILK, 1, 300),
                    new GEItem(ItemID.AMULET_OF_GLORY[2], 2, 20),
                    new GEItem(ItemID.SPADE, 1, 300),
                    new GEItem(ItemID.YELLOW_DYE, 3, 300),
                    new GEItem(ItemID.BLUE_DYE, 3, 300),
                    new GEItem(ItemID.RED_DYE, 3, 300),
                    new GEItem(ItemID.POT, 7, 300),
                    new GEItem(ItemID.BONES, 7, 100),
                    new GEItem(ItemID.BUCKET, 8, 300),
                    new GEItem(ItemID.SALVE_GRAVEYARD_TELEPORT, 10, 50),
                    new GEItem(ItemID.OAK_LONGBOW, 1, 300),
                    new GEItem(ItemID.KNIFE, 1, 200),
                    new GEItem(ItemID.STAMINA_POTION[0], 4, 20),
                    new GEItem(ItemID.NEEDLE, 1, 1000),
                    new GEItem(ItemID.THREAD, 1, 75),
                    new GEItem(ItemID.LOGS, 1, 25),
                    new GEItem(ItemID.TINDERBOX, 1, 250),
                    new GEItem(ItemID.LEATHER_GLOVES, 1, 500),
                    new GEItem(ItemID.BOWL_OF_WATER, 3, 500),
                    new GEItem(ItemID.STAFF_OF_FIRE, 1, 35),
                    new GEItem(ItemID.MIND_RUNE, 350, 35),
                    new GEItem(ItemID.AIR_RUNE, 700, 35),
                    new GEItem(ItemID.STAMINA_POTION[0], 5, 35),
                    new GEItem(ItemID.VARROCK_TELEPORT, 10, 35)
            )
    );

    InventoryRequirement itemsForTokens = new InventoryRequirement(new ArrayList<>(
            Arrays.asList(
                    new ItemReq(ItemID.BONES, 7, 7),
                    new ItemReq(ItemID.POT, 7, 7),
                    new ItemReq(ItemID.BUCKET, 8, 8),
                    new ItemReq(ItemID.STAMINA_POTION[0], 1, 0),
                    new ItemReq(ItemID.ECTOTOKEN, 35, 0),
                    new ItemReq(ItemID.VARROCK_TELEPORT, 2, 1),
                    new ItemReq(ItemID.SALVE_GRAVEYARD_TELEPORT, 0),
                    new ItemReq(ItemID.GHOSTSPEAK_AMULET, 1, 0, true)
            ))
    );

    InventoryRequirement itemsForStep1 = new InventoryRequirement(new ArrayList<>(
            Arrays.asList(
                    new ItemReq(ItemID.GHOSTSPEAK_AMULET, 1, 1, true),
                    new ItemReq(ItemID.SALVE_GRAVEYARD_TELEPORT, 0, 0),
                    new ItemReq(ItemID.VARROCK_TELEPORT, 0, 0),
                    new ItemReq(ItemID.STAMINA_POTION[0], 2, 0),

                    new ItemReq(ItemID.BUCKET_OF_SLIME, 1),
                    new ItemReq(ItemID.ECTOTOKEN, 35, 10),
                    new ItemReq(ItemID.LEATHER_GLOVES, 1, 1, true),
                    new ItemReq(ItemID.LOGS, 2, 0),
                    new ItemReq(ItemID.TINDERBOX, 1, 1),
                    new ItemReq(ItemID.BOWL_OF_WATER, 2),
                    new ItemReq(ItemID.BUCKET_OF_MILK, 2),
                    new ItemReq(ItemID.NETTLES, 2)

            ))
    );

    InventoryRequirement mapRequirement = new InventoryRequirement(new ArrayList<>(
            Arrays.asList(
                    new ItemReq(GhostsAhoyConst.MAP_PIECE_1, 1),
                    new ItemReq(GhostsAhoyConst.MAP_PIECE_2, 1),
                    new ItemReq(GhostsAhoyConst.MAP_PIECE_3, 1))));

    InventoryRequirement itemsForStep2 = new InventoryRequirement(new ArrayList<>(
            Arrays.asList(
                    new ItemReq(ItemID.GHOSTSPEAK_AMULET, 1),
                    new ItemReq(ItemID.SALVE_GRAVEYARD_TELEPORT, 0),
                    new ItemReq(ItemID.VARROCK_TELEPORT, 0),
                    new ItemReq(ItemID.STAMINA_POTION[0], 2),

                    new ItemReq(ItemID.MODEL_SHIP, 1),
                    new ItemReq(ItemID.NEEDLE, 1),
                    new ItemReq(ItemID.THREAD, 1),
                    new ItemReq(ItemID.KNIFE, 1),
                    new ItemReq(ItemID.SPADE, 1),
                    new ItemReq(ItemID.MIND_RUNE, 300),
                    new ItemReq(ItemID.AIR_RUNE, 600),

                    new ItemReq(ItemID.STAFF_OF_FIRE, 1),
                    //   new ItemReq(ItemID.NETTLES, 1),
                    new ItemReq(ItemID.STAFF_OF_FIRE, 1),
                    new ItemReq(ItemID.BLUE_DYE, 3),
                    new ItemReq(ItemID.RED_DYE, 3),
                    new ItemReq(ItemID.YELLOW_DYE, 3),
                    new ItemReq(ItemID.ECTOTOKEN, 0),
                    new ItemReq(ItemID.SILK, 1),
                    new ItemReq(ItemID.REPAIRED_SHIP, 1),
                    new ItemReq(ItemID.COINS, 5000, 600),
                    new ItemReq(ItemID.OAK_LONGBOW, 1),
                    new ItemReq(ItemID.BUCKET_OF_SLIME, 1)
            ))
    );


    InventoryRequirement itemsForBookOfHoracio = new InventoryRequirement(new ArrayList<>(
            Collections.singletonList(new ItemReq(GhostsAhoyConst.COMPLETE_MAP, 1))));

    InventoryRequirement bookOfHoraciaReq = new InventoryRequirement(new ArrayList<>(
            Collections.singletonList(new ItemReq(GhostsAhoyConst.BOOK_OF_HORACIO, 1))));


    public static boolean handleEnergyBarrier() {
        if (!GhostsAhoyConst.PORT_PHYSMATIS.contains(Player.getPosition())) {
            if (Utils.clickObject("Energy Barrier", "Pay-toll(2-Ecto)", true)) {
                Log.info("Clicking Energy barrier");
                Timer.waitCondition(() -> GhostsAhoyConst.PORT_PHYSMATIS.contains(Player.getPosition()), 8000,
                        10000);
            }
        }
        return GhostsAhoyConst.PORT_PHYSMATIS.contains(Player.getPosition());
    }


    public static boolean enterPortPhysmatis() {
        if (!GhostsAhoyConst.PORT_PHYSMATIS.contains(Player.getPosition())) {
            Log.info("Going to Port Physmatis");
            PathingUtil.walkToArea(GhostsAhoyConst.OUTSIDE_ENERGY_BARRIER, true);
        }
        return handleEnergyBarrier();
    }


    private boolean hasEctotokens(int amount) {
        int inv = org.tribot.script.sdk.Inventory.getCount(ItemID.ECTOTOKEN);
        return BankCache.getStack(ItemID.ECTOTOKEN) >= amount || inv >= amount;
    }


    private void getItems3() {
        BankManager.open(true);
        BankManager.checkEquippedGlory();
        BankManager.depositAll(true);
        itemsForStep2.withdrawItems();
        Utils.equipItem(ItemID.GHOSTSPEAK_AMULET);
        Utils.equipItem(ItemID.STAFF_OF_FIRE, "Wield");
    }


    private void getItemsForQuestStep1() {
        if (Inventory.find(ItemID.ECTOTOKEN).length > 0 ||
                (BankCache.isInitialized() && BankCache.getStack(ItemID.ECTOTOKEN) > 25)) {
            itemsForStep1.withdrawItems();
            BankManager.close(true);
            Utils.equipItem(ItemID.GHOSTSPEAK_AMULET);
            Utils.equipItem(ItemID.LEATHER_GLOVES);
        } else {
            Log.log("[Debug]: Either bankCache is not intialiazed (" + BankCache.isInitialized() + ") or we're missing ectotokens");
        }
    }


    BuyItemsStep buyStep = new BuyItemsStep(itemsToBuy);

    public void buyAndGetItems() {
        scripts.cQuesterV2.status = "Buying items";
        if (itemsForTokens.check()) {
            Log.info("Already have items");
            return;
        }
        Log.info("Buying Items");
        buyStep.buyItems();
        scripts.cQuesterV2.status = "Withdrawing items";
        Log.info("Withdrawing Items");
        itemsForTokens.withdrawItems();
    }


    public void getEctoFundus() {
        if (itemsForTokens.check() && !intialEctoTokens.check()) {
            for (int b = 0; b < 3; b++) {
                if (!GhostsAhoyConst.SLIME_LEVEL1.contains(Player.getPosition()) && Inventory.find(ItemID.BUCKET).length > 0 &&
                        Inventory.find(ItemID.BONEMEAL).length < 1) {
                    scripts.cQuesterV2.status = "Going to Ectofuntus";
                    Log.info("" + scripts.cQuesterV2.status);
                    PathingUtil.walkToTile(new WorldTile(3683, 9888, 0));
                    Timer.waitCondition(() -> GhostsAhoyConst.SLIME_LEVEL1.contains(Player.getPosition()), 15000, 20000);
                }
                if (GhostsAhoyConst.SLIME_LEVEL1.contains(Player.getPosition()) && Inventory.find(ItemID.BUCKET).length > 0 &&
                        Inventory.find(ItemID.BONEMEAL).length < 1) {
                    scripts.cQuesterV2.status = "Getting Slime";
                    Log.info("" + scripts.cQuesterV2.status);

                    if (Utils.useItemOnObject(ItemID.BUCKET, "Pool of Slime"))
                        Timer.abc2WaitCondition(() -> Inventory.find(ItemID.BUCKET).length < 1, 20000, 25000);
                }

                if (!GhostsAhoyConst.BONE_CRUSHER_AREA.contains(Player.getPosition()) && Inventory.find(ItemID.BUCKET).length < 1 &&
                        Inventory.find(ItemID.BONES).length > 0) {
                    scripts.cQuesterV2.status = "Going to grinder";
                    Log.info("" + scripts.cQuesterV2.status);
                    PathingUtil.walkToArea(GhostsAhoyConst.BONE_CRUSHER_AREA, false);
                }
                if (GhostsAhoyConst.BONE_CRUSHER_AREA.contains(Player.getPosition()) && Inventory.find(ItemID.BONEMEAL).length < 7) {
                    scripts.cQuesterV2.status = "Getting bonemeal";
                    Log.info("" + scripts.cQuesterV2.status);
                    if (Utils.clickObject("Bin", "Empty", false))
                        PathingUtil.movementIdle();

                    if (Utils.useItemOnObject(ItemID.BONES, "Loader"))
                        Timer.abc2WaitCondition(() ->
                                        Inventory.find(Filters.Items.nameContains("onemeal")).length == 7,
                                85000, 95000);
                    Waiting.waitNormal(500, 50);
                }

                if (GhostsAhoyConst.BONE_CRUSHER_AREA.contains(Player.getPosition()) && Inventory.find(ItemID.BONES).length < 1) {
                    scripts.cQuesterV2.status = "Worshiping Ectofuntus";
                    Log.info("" + scripts.cQuesterV2.status);
                    PathingUtil.walkToArea(GhostsAhoyConst.ECTOFUNTUS, false);
                }

                if (Inventory.find(ItemID.BONEMEAL).length > 0) {
                    scripts.cQuesterV2.status = "Worshiping Ectofuntus";
                    Log.info("" + scripts.cQuesterV2.status);
                    PathingUtil.walkToArea(GhostsAhoyConst.ECTOFUNTUS, false);

                    for (int i = 0; i < 7; i++) {
                        RSItem[] invBoneMeal = Inventory.find(ItemID.BONEMEAL);

                        if (Utils.clickObject("Ectofuntus", "Worship", false))
                            Timer.waitCondition(() -> Inventory.find(ItemID.BONEMEAL).length < invBoneMeal.length, 4000, 9000);

                    }
                    Utils.equipItem(ItemID.GHOSTSPEAK_AMULET);

                }
                claimEctoTokens();

                if (org.tribot.script.sdk.Inventory.getCount(ItemID.ECTOTOKEN) > 30) {
                    break;
                }
            }
        }
    }


    public void claimEctoTokens() {
        if (Inventory.find(ItemID.BONES).length < 1 && Inventory.find(ItemID.ECTOTOKEN).length < 1) {
            scripts.cQuesterV2.status = "Going to claim ecto-tokens";
            Log.info("" + scripts.cQuesterV2.status);
            PathingUtil.walkToArea(GhostsAhoyConst.ECTOFUNTUS, false);
            Utils.equipItem(ItemID.GHOSTSPEAK_AMULET);
            claimEctoTokens.execute();
        }
    }


    public void makeNettleTea() {
        RSItem[] nettleTea = Inventory.find(ItemID.NETTLE_TEA);
        if (nettleTea.length == 0) {
            if (!org.tribot.script.sdk.Inventory.contains(ItemID.NETTLES)) {
                pickNettles();
            }
            scripts.cQuesterV2.status = "Making Nettle tea";
            if (Utils.useItemOnItem(ItemID.NETTLES, ItemID.BOWL_OF_WATER)) {
                Timer.waitCondition(() -> Interfaces.isInterfaceSubstantiated(270), 1500, 2000);
                if (Interfaces.isInterfaceSubstantiated(270)) {
                    Keyboard.typeKeys((char) KeyEvent.VK_SPACE);
                    Timer.waitCondition(() -> Inventory.find(ItemID.NETTLEWATER).length > 0, 1500, 2000);
                }
            }

            if (org.tribot.script.sdk.Inventory.contains(ItemID.NETTLEWATER) &&
                    Utils.useItemOnItem(ItemID.TINDERBOX, ItemID.LOGS))
                Timer.waitCondition(() -> Objects.findNearest(3, "Fire").length > 0, 7000, 10000);

            if (Utils.useItemOnObject(ItemID.NETTLEWATER, "Fire")) {
                Timer.waitCondition(() -> Interfaces.isInterfaceSubstantiated(270) ||
                        Inventory.find(ItemID.NETTLE_TEA).length > 0, 2500, 3200);
                if (Interfaces.isInterfaceSubstantiated(270)) {
                    Keyboard.typeKeys((char) KeyEvent.VK_SPACE);
                    Timer.waitCondition(() -> Inventory.find(ItemID.NETTLEWATER).length > 0, 1500, 2000);
                }
            }
        }
    }

    public void pickNettles() {
        if (Inventory.find(ItemID.ECTOTOKEN).length > 0) {
            RSItem[] nettles = Inventory.find(ItemID.NETTLES, ItemID.NETTLE_TEA);
            if (nettles.length < 3) {
                scripts.cQuesterV2.status = "Getting nettles (x3)";
                //equip leather gloves
                if (!Equipment.isEquipped(ItemID.LEATHER_GLOVES))
                    Utils.equipItem(ItemID.LEATHER_GLOVES);

                RSArea nettleArea = new RSArea(new RSTile(3089, 3468, 0), new RSTile(3091, 3468, 0));
                PathingUtil.walkToTile(GhostsAhoyConst.NETTLE_TILE, 2, false);
                for (int i = 0; i < 5; i++) {
                    Optional<GameObject> nettle = Query.gameObjects().nameContains("Nettle")
                            .findBestInteractable();
                    nettles = Inventory.find(ItemID.NETTLES, ItemID.NETTLE_TEA);
                    if (nettles.length >= 3)
                        break;
                    int b = i;
                    Log.info("Picknettles b = " + b);
                    if (nettle.map(n -> n.interact("Pick")).orElse(false))
                        Timer.waitCondition(() -> Inventory.find(ItemID.NETTLES).length > b, 3500, 4500);
                    Utils.idleNormalAction(true);
                }
            }
        }
    }

    public void startQuest() {
        if (Inventory.find(ItemID.ECTOTOKEN).length > 0) {
            scripts.cQuesterV2.status = "Going to start";
            enterPortPhysmatis();
            questStartStep.execute();
        }
    }


    public void croneStep3() {
        if (Inventory.find(ItemID.CUP_OF_TEA_W_MILK, ItemID.CUP_OF_TEA_4245,
                ItemID.PORCELAIN_CUP).length == 0) {
            scripts.cQuesterV2.status = "Going to Crone (step 3)";
            croneStep3.execute();
        }
    }

    public void mixTeaSteps() {
        Optional<InventoryItem> cupOfTeaWithMilk = Query.inventory()
                .idEquals(ItemID.CUP_OF_TEA_W_MILK).findClosestToMouse();
        Optional<Npc> crone = Query.npcs()
                .idEquals(GhostsAhoyConst.CRONE_ID).findBestInteractable();

        if (crone.map(c -> cupOfTeaWithMilk.map(
                t -> t.useOn(c)).orElse(false)).orElse(false)) {
            Log.info("Used tea w/ milk on crone");
            NpcChat.handle(true);
        } else {
            makeCupOfTea();
        }

    }


    public void makeCupOfTea() {
        scripts.cQuesterV2.status = "Making tea for crone";
        makeNettleTea();
        if (Inventory.find(ItemID.CUP_OF_TEA_4245).length == 0 &&
                Utils.useItemOnItem(ItemID.NETTLE_TEA, ItemID.PORCELAIN_CUP))
            Timer.waitCondition(() -> Inventory.find(ItemID.CUP_OF_TEA_4245).length > 0, 1500, 2000);

        if (Inventory.find(ItemID.CUP_OF_TEA_4245).length > 0 &&
                Utils.useItemOnItem(ItemID.BUCKET_OF_MILK, ItemID.CUP_OF_TEA_4245))
            Timer.waitCondition(() -> Inventory.find(ItemID.CUP_OF_TEA_W_MILK).length > 0, 1500, 2000);
    }

    public void useTeaOnCrone() {
        if (Utils.useItemOnItem(ItemID.BUCKET_OF_MILK, ItemID.CUP_OF_TEA_4245))
            Timer.waitCondition(() -> Inventory.find(ItemID.CUP_OF_TEA_W_MILK).length > 0, 1500, 2000);

        if (Utils.useItemOnNPC(ItemID.CUP_OF_TEA_W_MILK, GhostsAhoyConst.CRONE_ID)) {
            NpcChat.handle(true);
        }
    }


    public void repairShip() {
        cQuesterV2.status = Inventory.find(ItemID.REPAIRED_SHIP).length == 0 ?
                "Repairing Ship" : "";
        if (Utils.useItemOnItem(ItemID.THREAD, ItemID.MODEL_SHIP))
            Timer.waitCondition(() -> Inventory.find(ItemID.REPAIRED_SHIP).length > 0, 1500, 2500);
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
                Log.info("Checking mast - i = " + i);
                if (Utils.getVarBitValue(WIND_SPEED_VARBIT) != 1) {
                    scripts.cQuesterV2.status = "Waiting on windspeed";
                    Timer.waitCondition(() -> Utils.getVarBitValue(WIND_SPEED_VARBIT) == 1, 15000, 20000);
                }
                if (Utils.getVarBitValue(WIND_SPEED_VARBIT) == 1
                        && Utils.clickObject("Mast", "Search", true)) {
                    Timer.waitCondition(() -> Interfaces.isInterfaceSubstantiated(229, 1), 1500, 3000);
                    if (Interfaces.isInterfaceSubstantiated(229, 1)) {
                        String msg = Interfaces.get(229, 1).getText();
                        //Log.info("scripts.cQuesterV2.status: " + msg);
                        if (msg != null) {
                            if (msg.contains("top half")) {
                                GhostsAhoyConst.topHalf = determineColor();
                                Log.info("Top half is " + GhostsAhoyConst.topHalf.toString());
                            } else if (msg.contains("bottom half")) {
                                GhostsAhoyConst.bottomHalf = determineColor();
                                Log.info("Bottom half is " + GhostsAhoyConst.bottomHalf.toString());
                            } else if (msg.contains("skull")) {
                                GhostsAhoyConst.skull = determineColor();
                                Log.info("Skull is " + GhostsAhoyConst.skull.toString());
                            }
                        }
                        if (Player.getPosition().click())// gets rid of dialog box
                            Timer.waitCondition(() -> !NPCInteraction.isConversationWindowUp(), 2200, 3000);

                    }
                }
                if (GhostsAhoyConst.skull != null && GhostsAhoyConst.bottomHalf != null && GhostsAhoyConst.topHalf != null) {
                    Log.info("We have all the colors");
                    break;
                }
                General.sleep(300, 1200);
            }
        }
    }


    public GhostsAhoyConst.COLOR determineColor() {
        if (Interfaces.isInterfaceSubstantiated(229, 1)) {
            String msg = Interfaces.get(229, 1).getText();
            Log.info("scripts.cQuesterV2.status: " + msg);
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
                Utils.useItemOnItem(finalDye, ItemID.REPAIRED_SHIP)) {
            Timer.waitCondition(() -> Interfaces.isInterfaceSubstantiated(219, 1), 2000, 4000);
            Log.info("[DyeShip]: Coloring boat part: " + partName);
            int dyeNum = Inventory.find(finalDye).length;
            if (InterfaceUtil.clickInterfaceText(219, 1, partName)) {
                Timer.waitCondition(() -> Inventory.find(finalDye).length < dyeNum, 2000, 4000);
            }
        }
    }

    public void colorBoat(GhostsAhoyConst.COLOR part, String partName) {
        if (GhostsAhoyConst.skull != null && GhostsAhoyConst.bottomHalf != null && GhostsAhoyConst.topHalf != null) {
            scripts.cQuesterV2.status = "Coloring boat";
            Log.info("Coloring boat part: " + partName);
            Log.info("Bottomhalf: " + GhostsAhoyConst.bottomHalf);
            Log.info("Skull: " + GhostsAhoyConst.skull);
            Log.info("TopHalf: " + GhostsAhoyConst.topHalf);

            if (part != null) {
                if (part.equals(GhostsAhoyConst.COLOR.ORANGE)) {
                    Log.info("Making orange");
                    makeAndDye(ItemID.YELLOW_DYE, ItemID.RED_DYE, ItemID.ORANGE_DYE, partName);
                } else if (part.equals(GhostsAhoyConst.COLOR.PURPLE)) {
                    Log.info("Making Purple");
                    makeAndDye(ItemID.BLUE_DYE, ItemID.RED_DYE, ItemID.PURPLE_DYE, partName);
                } else if (part.equals(GhostsAhoyConst.COLOR.GREEN)) {
                    Log.info("Making green");
                    makeAndDye(ItemID.BLUE_DYE, ItemID.YELLOW_DYE, ItemID.GREEN_DYE, partName);
                } else if (part.equals(GhostsAhoyConst.COLOR.YELLOW)) {
                    dyeShip(ItemID.YELLOW_DYE, partName);
                } else if (part.equals(GhostsAhoyConst.COLOR.RED)) {
                    dyeShip(ItemID.RED_DYE, partName);
                } else if (part.equals(GhostsAhoyConst.COLOR.BLUE)) {
                    dyeShip(ItemID.BLUE_DYE, partName);
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
        Optional<GameObject> chest = Query.gameObjects()
                .tileEquals(new WorldTile(3618, 3542, 0))
                .idEquals(16119)
                .findClosest();
        return chest.map(c -> c.interact("Search")).orElse(false);
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
                if (!Combat.isUnderAttack()) {
                    cQuesterV2.status = "Getting Map scrap 2 - finding lobster";
                    Optional<GameObject> chest = Query.gameObjects()
                            .tileEquals(new WorldTile(3618, 3542, 0))
                            .nameContains("Chest")
                            .findClosest();
                    if (chest.map(c -> c.interact("Open")).orElse(false)) {
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
        if (!bookOfHoraciaReq.check() && hasEctotokens(25)) {
            if (itemsForBookOfHoracio.check()) {
                if (!GhostsAhoyConst.DRAGONTOOTH_ISLAND.contains(Player.getPosition())) {
                    enterPortPhysmatis();
                    cQuesterV2.status = "Going to rowboat";
                    takeRowingBoat.execute();
                    Timer.waitCondition(() -> GhostsAhoyConst.DRAGONTOOTH_ISLAND.contains(Player.getPosition()), 6000, 9000);
                }
            }
        }
        if (!bookOfHoraciaReq.check() && GhostsAhoyConst.DRAGONTOOTH_ISLAND.contains(Player.getPosition())) {
            cQuesterV2.status = "Going to dig";
            Log.info("Going to dig");
            if (PathingUtil.localNav(DIG_WORLD_TILE))
                PathingUtil.movementIdle();


            if (DIG_WORLD_TILE.interact("Walk here")) {
                Log.warn("Clicked tile");
                PathingUtil.movementIdle();
            }

            if (DIG_WORLD_TILE.equals(MyPlayer.getTile()) &&
                    Utils.clickInventoryItem(ItemID.SPADE)) {
                Waiting.waitUntil(6000, 550, () -> Inventory.find(GhostsAhoyConst.BOOK_OF_HORACIO)
                        .length == 1);
                Utils.idleNormalAction(true);
            } else
                Utils.idlePredictableAction();
        }

    }


    public void leaveDragontoothIsland() {
        if (GhostsAhoyConst.DRAGONTOOTH_ISLAND.contains(Player.getPosition()) && bookOfHoraciaReq.check()) {
            cQuesterV2.status = "Going rowboat";
            Log.info("Going to dig");
            if (PathingUtil.localNav(new WorldTile(3792, 3559, 0)))
                PathingUtil.movementIdle();

            if (Utils.clickNPC("Ghost captain", "Travel")) {
                Timer.waitCondition(() -> !GhostsAhoyConst.DRAGONTOOTH_ISLAND.contains(Player.getPosition()), 4500, 5000);
            }
        }
    }


    //NPCStep talkToAkHaranu = new NPCStep(NpcID.AKHARANU, new RSTile(3689, 3499, 0), "Okay, wait here - I'll get you your bow.");
    public void setTalkToAkHaranu() {
        if (!GhostsAhoyConst.DRAGONTOOTH_ISLAND.contains(Player.getPosition()) && bookOfHoraciaReq.check()) {
            cQuesterV2.status = "Going to AkHaranu";
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
                cQuesterV2.status = "Talking to Inn keeper";
                Log.info("Talking to Inn keeper");
                talkToInnkeeper.setRadius(7);
                talkToInnkeeper.execute();
            }
        }
    }

    public void makeGhostSheet() {
        if (Inventory.find(GhostsAhoyConst.SHEET).length == 1 &&
                Utils.useItemOnItem(ItemID.BUCKET_OF_SLIME, GhostsAhoyConst.SHEET)) {
            cQuesterV2.status = "Making sheet";
            Log.info("Making sheet");
            Timer.waitCondition(() -> Inventory.find(GhostsAhoyConst.GHOST_SHEET).length == 1, 3500, 5000);
        }
        if (!Equipment.isEquipped(GhostsAhoyConst.GHOST_SHEET)) {
            Utils.clickInventoryItem(GhostsAhoyConst.GHOST_SHEET);
        }
    }

    public void playRuneDraw() {
        for (int i = 0; i < 10; i++) {
            cQuesterV2.status = "Playing runedraw- i = " + i;
            Log.info("Playing runedraw- i = " + i);
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
                    Log.info("Closing interfaces");
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
        if (ChatScreen.isOpen()) {
            NPCInteraction.handleConversation("Yes, I'll give you a game.");
        } else if (!Waiting.waitUntil(1500, 250, () -> Interfaces.get(9) != null
                || ChatScreen.isOpen())) {
            talkToRobin.setRadius(10);
            talkToRobin.execute();
        }

    }


    public void returnBow() {
        cQuesterV2.status = "Returning Bow";
        if (GhostsAhoyConst.BAR_AREA.contains(Player.getPosition())) {
            Log.info("Leaving bar");
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
                Log.info("Leaving bar");
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
            Log.info("Leaving bar");
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
            Log.info("going to give crone robes");
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


    private void navigatePath(List<WorldTile> tileList) {
        for (WorldTile t : tileList) {
            Optional<GameObject> obj = Query.gameObjects().tileEquals(t).isReachable()
                    .actionContains("Jump-to").findClosest();
            if (obj.map(o -> o.interact("Jump-to")).orElse(false)) {
                Waiting.waitUntil(3500, 350, () -> MyPlayer.isAnimating());
            }
        }
    }

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
        return cQuesterV2.taskList.get(0).equals(GhostsAhoy.get());
    }

    @Override
    public void execute() {
        Teleport.blacklistTeleports(Teleport.GLORY_KARAMJA);
        if (!checkRequirements()) {
            Log.warn("Missing Restless ghost or 20 cooking requirement");
            cQuesterV2.taskList.remove(GhostsAhoy.get());
            return;
        }
        General.sleep(30, 50);
        Log.info("VARBIT 217: " + Utils.getVarBitValue(VARBIT));
        Log.info("VARBIT 214: " + Utils.getVarBitValue(214));

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
            makeNettleTea();
            croneStep3();
            mixTeaSteps();
            croneStep4.execute();
        } else if (Utils.getVarBitValue(VARBIT) == 4 &&
                Utils.getVarBitValue(214) == 0) {
            RSItem[] dyes = Entities.find(ItemEntity::new)
                    .nameContains("dye")
                    .getResults();
            if (Inventory.find(ItemID.MODEL_SHIP).length == 0 && Inventory.find(4254).length == 0) {
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


            if (Inventory.find(ItemID.MODEL_SHIP).length == 0 && Inventory.find(4254).length == 0) {
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
            Log.info("Get Signatures");
            getSignatures();
        } else if (Utils.getVarBitValue(209) == 11 &&
                Utils.getVarBitValue(211) == 3 &&
                Utils.getVarBitValue(212) == 8 &&
                Utils.getVarBitValue(214) == 3 &&
                Utils.getVarBitValue(215) == 1 &&
                Utils.getVarBitValue(217) == 4) {
            Inventory.drop(GhostsAhoyConst.GHOST_SHEET); //can prevent using charter boats if dax walking
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
            Inventory.drop(GhostsAhoyConst.GHOST_SHEET);
            giveCroneRobes();

        } else if (Utils.getVarBitValue(209) == 31 &&
                Utils.getVarBitValue(211) == 3 &&
                Utils.getVarBitValue(212) == 8 &&
                Utils.getVarBitValue(214) == 3 &&
                Utils.getVarBitValue(215) == 1 &&
                Utils.getVarBitValue(217) == 6) {
            Inventory.drop(GhostsAhoyConst.GHOST_SHEET); //can prevent using charter boats if dax walking
            cQuesterV2.status = "Going to necrovarus";
            if (equipGhostspeakAmulet())
                necrovarusFinal.execute();

        } else if (Utils.getVarBitValue(209) == 31 &&
                Utils.getVarBitValue(211) == 3 &&
                Utils.getVarBitValue(212) == 8 &&
                Utils.getVarBitValue(214) == 3 &&
                Utils.getVarBitValue(215) == 1 &&
                Utils.getVarBitValue(217) == 7) {
            Inventory.drop(GhostsAhoyConst.GHOST_SHEET); //can prevent using charter boats if dax walking
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
        return "Ghosts Ahoy (" + Quest.GHOSTS_AHOY.getStep() + ")";
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
        return Quest.GHOSTS_AHOY.getState().equals(Quest.State.COMPLETE);
    }
}
