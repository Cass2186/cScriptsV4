package scripts.QuestPackages.Barcrawl;

import org.tribot.api2007.Game;
import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSTile;
import org.tribot.script.sdk.Log;
import org.tribot.script.sdk.Waiting;
import scripts.GEManager.GEItem;
import scripts.ItemID;
import scripts.NpcID;
import scripts.QuestPackages.HorrorFromTheDeep.HorrorFromTheDeep;
import scripts.QuestSteps.BuyItemsStep;
import scripts.QuestSteps.NPCStep;
import scripts.QuestSteps.QuestTask;
import scripts.Requirements.*;
import scripts.Tasks.Priority;
import scripts.Tasks.Task;
import scripts.cQuesterV2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BarCrawl implements QuestTask {

    private static BarCrawl quest;

    public static BarCrawl get() {
        return quest == null ? quest = new BarCrawl() : quest;
    }


    ItemReq coins208 = new ItemReq("Coins", ItemID.COINS_995, 208);
    ItemReq coins50 = new ItemReq("Coins", ItemID.COINS_995, 50);
    ItemReq coins10 = new ItemReq("Coins", ItemID.COINS_995, 10);
    ItemReq coins70 = new ItemReq("Coins", ItemID.COINS_995, 70);
    ItemReq coins8 = new ItemReq("Coins", ItemID.COINS_995, 8);
    ItemReq coins7 = new ItemReq("Coins", ItemID.COINS_995, 7);
    ItemReq coins15 = new ItemReq("Coins", ItemID.COINS_995, 15);
    ItemReq coins18 = new ItemReq("Coins", ItemID.COINS_995, 18);
    ItemReq coins12 = new ItemReq("Coins", ItemID.COINS_995, 12);

    ItemReq gamesNecklace = new ItemReq(ItemID.GAMES_NECKLACE[0], 1, 0);
    ItemReq varrockTeleport = new ItemReq(ItemID.VARROCK_TELEPORT, 5, 1);
    ItemReq faladorTeleport = new ItemReq(ItemID.FALADOR_TELEPORT, 5, 1);
    ItemReq glory = new ItemReq(ItemID.AMULET_OF_GLORY[0], 1, 0, true);
    ItemReq combatBracelet = new ItemReq(ItemID.COMBAT_BRACELET[2], 1, 0, true);
    ItemReq camelotTeleport = new ItemReq(ItemID.CAMELOT_TELEPORT, 5, 1);
    ItemReq duelingRing = new ItemReq(ItemID.RING_OF_DUELING[0], 1, 0, true);

    ItemReq barcrawlCard = new ItemReq(455);
//		barcrawlCard.setTooltip("If you've lost it you can get another from the Barbarian Guard");


    RSArea grandTreeF1 = new RSArea(new RSTile(2437, 3474, 1), new RSTile(2493, 3511, 1));


    AreaRequirement inGrandTreeF1 = new AreaRequirement(grandTreeF1);

    VarplayerRequirement notTalkedToGuard = new VarplayerRequirement(77, false, 0);
    VarplayerRequirement notTalkedToBlueMoon = new VarplayerRequirement(77, false, 3);
    VarplayerRequirement notTalkedToJollyBoar = new VarplayerRequirement(77, false, 9);
    VarplayerRequirement notTalkedToRisingSun = new VarplayerRequirement(77, false, 11);
    VarplayerRequirement notTalkedToRustyAnchor = new VarplayerRequirement(77, false, 12);
    VarplayerRequirement notTalkedToZambo = new VarplayerRequirement(77, false, 10);
    VarplayerRequirement notTalkedToDeadMansChest = new VarplayerRequirement(77, false, 5);
    VarplayerRequirement notTalkedToFlyingHorseInn = new VarplayerRequirement(77, false, 7);
    VarplayerRequirement notTalkedToForestersArms = new VarplayerRequirement(77, false, 8);
    VarplayerRequirement notTalkedToBlurberry = new VarplayerRequirement(77, false, 4);
    VarplayerRequirement notTalkedToDragonInn = new VarplayerRequirement(77, false, 6);

    ArrayList<GEItem> itemsToBuy = new ArrayList<GEItem>(
            Arrays.asList(
                    new GEItem(ItemID.GAMES_NECKLACE[0], 1, 100),
                    new GEItem(ItemID.VARROCK_TELEPORT, 10, 50),
                    new GEItem(ItemID.AMULET_OF_GLORY[0], 1, 20),
                    new GEItem(ItemID.FALADOR_TELEPORT, 5, 50),
                    new GEItem(ItemID.COMBAT_BRACELET[2], 5, 50),
                    new GEItem(ItemID.CAMELOT_TELEPORT, 5, 50),
                    new GEItem(ItemID.RING_OF_DUELING[0], 1, 50),
                    new GEItem(ItemID.STAMINA_POTION[0], 2, 30)
            )
    );

    InventoryRequirement itemsToStart = new InventoryRequirement(new ArrayList<>(
            Arrays.asList(
                    gamesNecklace,
                    varrockTeleport,
                    faladorTeleport,
                    combatBracelet,
                    glory,
                    camelotTeleport,
                    duelingRing,
                    new ItemReq(ItemID.COINS_995, 5000, 1000),
                    new ItemReq(ItemID.STAMINA_POTION[0], 2, 0)
            ))
    );

    NPCStep talkToGuard = new NPCStep(5227, new RSTile(2544, 3568, 0),
            new String[]{"I want to come through this gate.",
                    "Looks can be deceiving, I am in fact a barbarian."});


    NPCStep talkToBlueMoon = new NPCStep(1312, new RSTile(3226, 3399, 0),
            new String[]{"I'm doing Alfred Grimhand's Barcrawl."}, coins50);

    NPCStep talkToJollyBoar = new NPCStep(1310, new RSTile(3279, 3494, 0),
            new String[]{"I'm doing Alfred Grimhands Barcrawl."}, coins10);


    NPCStep talkToRisingSun = new NPCStep(NpcID.KAYLEE, new RSTile(2956, 3370, 0),
            new String[]{"I'm doing Alfred Grimhand's Barcrawl."}, coins70);


    NPCStep talkToRustyAnchor = new NPCStep(1313, new RSTile(3046, 3257, 0),
            new String[]{"I'm doing Alfred Grimhand's Barcrawl."}, coins8);

    NPCStep talkToZambo = new NPCStep(NpcID.ZAMBO, new RSTile(2927, 3144, 0),
            new String[]{"I'm doing Alfred Grimhand's Barcrawl."}, coins7);

    NPCStep talkToDeadMansChest = new NPCStep(1314, new RSTile(2796, 3156, 0),
            new String[]{"I'm doing Alfred Grimhand's Barcrawl."}, coins15);

    NPCStep talkToFlyingHorseInn = new NPCStep(1319, new RSTile(2574, 3323, 0),
            new String[]{"I'm doing Alfred Grimhand's Barcrawl."}, coins8);

    NPCStep talkToForestersArms = new NPCStep(1318, new RSTile(2690, 3494, 0),
            new String[]{"I'm doing Alfred Grimhand's Barcrawl."}, coins18);

    NPCStep talkToBlurberry = new NPCStep(NpcID.BLURBERRY, new RSTile(2482, 3491, 1),
            new String[]{"I'm doing Alfred Grimhand's Barcrawl."}, coins10);

    NPCStep talkToDragonInn = new NPCStep(1320, new RSTile(2556, 3079, 0),
            new String[]{"I'm doing Alfred Grimhand's Barcrawl."}, coins12);


    NPCStep talkToGuardAgain = new NPCStep(5227, new RSTile(2544, 3568, 0),
            new String[]{"Yes please, I want to smash my vials."});

    BuyItemsStep buyStep = new BuyItemsStep(itemsToBuy);

    public void checkItemsAfterStart() {
        itemsToStart.add(barcrawlCard);
        if (!itemsToStart.check()) {
            cQuesterV2.status = "Buying items";
            buyStep.buyItems();
            cQuesterV2.status = "Getting items";
            itemsToStart.withdrawItems();
        }
    }

    @Override
    public String toString() {
        return "Barcrawl";
    }


    @Override
    public Priority priority() {
        return Priority.LOW;
    }

    @Override
    public boolean validate() {
        return cQuesterV2.taskList.get(0).equals(BarCrawl.get());
    }

    @Override
    public void execute() {
        Log.log("setting 77 is " + Game.getSetting(77));
        if (Game.getSetting(77) == 2 || Game.getSetting(76) == 6) {
            cQuesterV2.taskList.remove(this);
        }
        else if (notTalkedToGuard.check()) {
            if (!itemsToStart.check()) {
                cQuesterV2.status = "Buying items";
                buyStep.buyItems();
                cQuesterV2.status = "Getting items";
                itemsToStart.withdrawItems();
            }
            cQuesterV2.status = "Going to Start";
            talkToGuard.execute();
        } else if (notTalkedToBlueMoon.check()) {
            checkItemsAfterStart();
            cQuesterV2.status = "Going to bluemoon inn";
            talkToBlueMoon.execute();
            Waiting.waitNormal(6000, 600);
        } else if (notTalkedToJollyBoar.check()) {
            cQuesterV2.status = "Going to Jolly boar";
            talkToJollyBoar.execute();
            Waiting.waitNormal(6000, 600);
        } else if (notTalkedToRisingSun.check()) {
            cQuesterV2.status = "Going to Rising Sun";
            talkToRisingSun.execute();
            Waiting.waitNormal(6000, 600);
        } else if (notTalkedToRustyAnchor.check()) {
            cQuesterV2.status = "Going to Rusy anchor";
            talkToRustyAnchor.execute();
            Waiting.waitNormal(6000, 600);
        } else if (notTalkedToZambo.check()) {
            cQuesterV2.status = "Going to Zambo";
            talkToZambo.execute();
            Waiting.waitNormal(6000, 600);
        } else if (notTalkedToDeadMansChest.check()) {
            cQuesterV2.status = "Going to Dead Mans chest";
            talkToDeadMansChest.execute();
            Waiting.waitNormal(6000, 600);
        } else if (notTalkedToFlyingHorseInn.check()) {
            cQuesterV2.status = "Going to Flying horse inn";
            talkToFlyingHorseInn.execute();
            Waiting.waitNormal(6000, 600);
        } else if (notTalkedToForestersArms.check()) {
            cQuesterV2.status = "Going to Foresters Arms";
            talkToForestersArms.execute();
            Waiting.waitNormal(6000, 600);
        } else if (notTalkedToBlurberry.check()) {
            cQuesterV2.status = "Going to Blurberry";
            talkToBlurberry.execute();
            Waiting.waitNormal(6000, 600);
        } else if (notTalkedToDragonInn.check()) {
            cQuesterV2.status = "Going to Dragon inn";
            talkToDragonInn.execute();
            Waiting.waitNormal(6000, 600);
        } else if (Game.getSetting(77) == 8185) {
            cQuesterV2.status = "Going to Finish";
            talkToGuardAgain.execute();

        } else if (Game.getSetting(77) == 2 || Game.getSetting(76) == 6) {
            cQuesterV2.taskList.remove(this);
        }
    }

    @Override
    public String questName() {
        return "Barcrawl";
    }

    @Override
    public boolean checkRequirements() {
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
