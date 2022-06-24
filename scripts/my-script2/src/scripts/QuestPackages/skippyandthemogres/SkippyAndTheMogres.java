package scripts.QuestPackages.skippyandthemogres;

import org.tribot.api2007.Game;
import org.tribot.api2007.Interfaces;
import org.tribot.api2007.Skills;
import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSItem;
import org.tribot.api2007.types.RSTile;
import org.tribot.script.sdk.*;
import org.tribot.script.sdk.query.Query;
import org.tribot.script.sdk.types.GameObject;
import scripts.*;
import scripts.GEManager.GEItem;
import scripts.QuestPackages.GhostsAhoy.GhostsAhoyConst;
import scripts.QuestPackages.HeroesQuest.HeroesQuestBlackArmsGang;
import scripts.QuestSteps.*;
import scripts.Requirements.*;
import scripts.Requirements.Util.ConditionalStep;
import scripts.Tasks.Priority;
import scripts.Timer;

import java.awt.event.KeyEvent;
import java.util.*;

public class SkippyAndTheMogres implements QuestTask {

    private static SkippyAndTheMogres quest;

    public static SkippyAndTheMogres get() {
        return quest == null ? quest = new SkippyAndTheMogres() : quest;
    }

    ItemRequirement bucketOfWater, nettleTea, chocolateDust, bucketOfMilk, snapeGrass, chocolateMilk, hangoverCure;

    QuestStep useChocolateDustOnMilk, useSnapeGrassOnMilk, useHangoverCure;
    NPCStep soberSkippy, useTeaOnSkippy;

    InventoryRequirement startInventory = new InventoryRequirement(new ArrayList<>(
            Arrays.asList(
                    new ItemReq(ItemID.BUCKET_OF_MILK, 1),
                    new ItemReq(ItemID.CHOCOLATE_DUST, 1),
                    new ItemReq(ItemID.SNAPE_GRASS, 1),
                    new ItemReq(ItemID.BUCKET_OF_WATER, 1),
                    new ItemReq(ItemID.LEATHER_GLOVES, 1, true, true),
                    new ItemReq(ItemID.VARROCK_TELEPORT, 5, 0),
                    new ItemReq(ItemID.COINS, 500, 200),
                    new ItemReq(ItemID.STAMINA_POTION[0], 2, 0),
                    new ItemReq(ItemID.RING_OF_WEALTH[0], 1, 0, true, true)
            )
    ));

    ArrayList<GEItem> itemsToBuy = new ArrayList<GEItem>(
            Arrays.asList(
                    new GEItem(ItemID.BUCKET_OF_MILK, 1, 500),
                    new GEItem(ItemID.CHOCOLATE_DUST, 1, 500),
                    new GEItem(ItemID.SNAPE_GRASS, 1, 500),
                    new GEItem(ItemID.VARROCK_TELEPORT, 3, 60),
                    new GEItem(ItemID.BUCKET_OF_WATER, 1, 500),
                    new GEItem(ItemID.LEATHER_GLOVES, 1, 500),
                    new GEItem(ItemID.STAMINA_POTION[0], 2, 15),
                    new GEItem(ItemID.RING_OF_WEALTH[0], 1, 25)
            )
    );
    BuyItemsStep buyStep = new BuyItemsStep(itemsToBuy);

    public void makeNettleTea() {
        int nettleTea = Inventory.getCount(ItemID.NETTLE_TEA);
        if (nettleTea == 0) {

            if (!Inventory.contains(ItemID.NETTLES))
                pickNettles();

            scripts.cQuesterV2.status = "Making Nettle tea";
            if (Utils.useItemOnItem(ItemID.NETTLES, ItemID.BOWL_OF_WATER)) {
                Waiting.waitUntil(4000, 400, MakeScreen::isOpen);
                if (MakeScreen.isOpen()) {
                    if (!MakeScreen.makeAll(ItemID.NETTLEWATER))
                        Keyboard.typeString("  ");
                    Waiting.waitUntil(6000, 1200, () -> Inventory.getCount(ItemID.NETTLEWATER) > 0);
                }
            }

            if (Inventory.contains(ItemID.NETTLEWATER) &&
                    Utils.useItemOnItem(ItemID.TINDERBOX, ItemID.LOGS))
                Timer.waitCondition(() -> org.tribot.api2007.Objects.findNearest(3, "Fire").length > 0, 7000, 10000);

            if (Utils.useItemOnObject(ItemID.NETTLEWATER, "Fire")) {
                Waiting.waitUntil(5200, 800, () -> MakeScreen.isOpen() ||
                        Inventory.getCount(ItemID.NETTLE_TEA) > 0);
                if (MakeScreen.isOpen()) {
                    if (!MakeScreen.makeAll(ItemID.NETTLE_TEA))
                        Keyboard.typeString("  ");
                    Waiting.waitUntil(6000, 1200, () -> Inventory.getCount(ItemID.NETTLE_TEA) > 0);
                }
            }
        }
    }


    public void pickNettles() {
        int nettles = Inventory.getCount(ItemID.NETTLES, ItemID.NETTLE_TEA);
        if (nettles < 3) {
            cQuesterV2.status = "Getting nettles (x3)";
            //equip leather gloves
            if (!Equipment.contains(ItemID.LEATHER_GLOVES))
                Utils.equipItem(ItemID.LEATHER_GLOVES);

            PathingUtil.walkToTile(GhostsAhoyConst.NETTLE_TILE, 2, false);
            for (int i = 0; i < 5; i++) {
                Optional<GameObject> nettle = Query.gameObjects().nameContains("Nettle")
                        .findBestInteractable();
                nettles = Inventory.getCount(ItemID.NETTLES, ItemID.NETTLE_TEA);
                if (nettles >= 3)
                    break;
                int b = i;
                Log.info("Pick Nettles b = " + b);
                if (nettle.map(n -> n.interact("Pick")).orElse(false))
                    Timer.waitCondition(() -> Inventory.getCount(ItemID.NETTLES) > b, 3500, 4500);
                Utils.idleNormalAction(true);
            }
        }

    }

    public void setupItemRequirements() {
        bucketOfMilk = new ItemRequirement("Bucket of milk", ItemID.BUCKET_OF_MILK);
        bucketOfWater = new ItemRequirement("Bucket of water", ItemID.BUCKET_OF_WATER);
        hangoverCure = new ItemRequirement("Hangover cure", ItemID.HANGOVER_CURE);
        chocolateDust = new ItemRequirement("Chocolate dust", ItemID.CHOCOLATE_DUST);
        nettleTea = new ItemRequirement("Nettle tea", ItemID.NETTLE_TEA);
        // nettleTea.setTooltip("You can make this by using nettles on a bowl of water, then cooking it");
        snapeGrass = new ItemRequirement("Snape grass", ItemID.SNAPE_GRASS);
        chocolateMilk = new ItemRequirement("Chocolatey milk", ItemID.CHOCOLATEY_MILK);

    }

    public Map<Integer, QuestStep> loadSteps() {
        setupItemRequirements();
        setupSteps();
        Map<Integer, QuestStep> steps = new HashMap<>();

        steps.put(0, soberSkippy);
        steps.put(1, useTeaOnSkippy);

        ConditionalStep makeAndUseCure = new ConditionalStep(useChocolateDustOnMilk);
        makeAndUseCure.addStep(hangoverCure, useHangoverCure);
        makeAndUseCure.addStep(chocolateMilk, useSnapeGrassOnMilk);

        steps.put(2, makeAndUseCure);

        return steps;
    }

    public void setupSteps() {
        soberSkippy = new NPCStep("Skippy", new RSTile(2982, 3194, 0), bucketOfWater);
        soberSkippy.setInteractionString("Sober-up");
        soberSkippy.addDialogStep("Throw the water!");
        useTeaOnSkippy = new NPCStep("Skippy", new RSTile(2982, 3194, 0), nettleTea);
        useChocolateDustOnMilk = new UseItemOnItemStep(ItemID.CHOCOLATE_DUST, ItemID.BUCKET_OF_MILK,
                Inventory.contains(ItemID.CHOCOLATEY_MILK), chocolateDust, bucketOfMilk);
        useSnapeGrassOnMilk = new UseItemOnItemStep(ItemID.SNAPE_GRASS, ItemID.CHOCOLATEY_MILK,
                !Inventory.contains(ItemID.SNAPE_GRASS), snapeGrass, chocolateMilk);
        useHangoverCure = new NPCStep("Skippy", new RSTile(2982, 3194, 0), hangoverCure);
    }

    public List<ItemRequirement> getItemRequirements() {
        ArrayList<ItemRequirement> reqs = new ArrayList<>();
        reqs.add(bucketOfWater);
        reqs.add(nettleTea);
        reqs.add(bucketOfMilk);
        reqs.add(chocolateDust);
        reqs.add(snapeGrass);
        return reqs;
    }


    @Override
    public Priority priority() {
        return Priority.LOW;
    }

    @Override
    public boolean validate() {
        return cQuesterV2.taskList.size() > 0 &&
                cQuesterV2.taskList.get(0).equals(this);
    }


    @Override
    public void execute() {
        //get items
        if (Quest.SKIPPY_AND_THE_MOGRES.getStep() == 0 && !startInventory.check()) {
            buyStep.buyItems();
            startInventory.withdrawItems();
            pickNettles();
        }

        Map<Integer, QuestStep> steps = loadSteps();
        Optional<QuestStep> step = Optional.ofNullable(steps.get(Quest.SKIPPY_AND_THE_MOGRES.getStep()));
        cQuesterV2.status = step.map(s -> s.toString()).orElse("Unknown Step Name");
        step.ifPresent(QuestStep::execute);

        if (ChatScreen.isOpen())
            NpcChat.handle();
        Waiting.waitNormal(40, 80);
    }

    @Override
    public String questName() {
        return "Skippy & The Mogres";
    }

    @Override
    public boolean checkRequirements() {
        return Skill.COOKING.getActualLevel() >= 20;
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
        return Quest.SKIPPY_AND_THE_MOGRES.getState().equals(Quest.State.COMPLETE);
    }

}
