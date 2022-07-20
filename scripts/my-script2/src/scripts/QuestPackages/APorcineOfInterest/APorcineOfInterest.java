package scripts.QuestPackages.APorcineOfInterest;

import dax.walker_engine.interaction_handling.NPCInteraction;
import org.tribot.api.General;
import org.tribot.api2007.*;
import org.tribot.api2007.types.*;
import org.tribot.script.sdk.GameState;
import org.tribot.script.sdk.Log;
import org.tribot.script.sdk.Quest;
import org.tribot.script.sdk.Waiting;
import scripts.*;
import scripts.GEManager.GEItem;
import scripts.QuestPackages.GertrudesCat.GertrudesCat;
import scripts.QuestSteps.*;
import scripts.Requirements.*;
import scripts.Tasks.Priority;
import scripts.Tasks.Task;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class APorcineOfInterest implements QuestTask {

    private static APorcineOfInterest quest;

    public static APorcineOfInterest get() {
        return quest == null ? quest = new APorcineOfInterest() : quest;
    }


    ArrayList<GEItem> itemsToBuy = new ArrayList<GEItem>(
            Arrays.asList(
                    new GEItem(ItemID.ADAMANT_SCIMITAR, 1, 100),
                    new GEItem(ItemID.IRON_PLATEBODY, 1, 200),
                    new GEItem(ItemID.AMULET_OF_GLORY[2], 2, 20),
                    new GEItem(ItemID.STAMINA_POTION[0], 2, 20),
                    new GEItem(ItemID.IRON_PLATELEGS, 1, 200),
                    new GEItem(ItemID.IRON_FULL_HELM, 1, 200),
                    new GEItem(ItemID.IRON_KITESHIELD, 1, 200),
                    new GEItem(ItemID.LOBSTER, 20, 200),
                    new GEItem(ItemID.PRAYER_POTION4, 2, 15),
                    new GEItem(ItemID.ROPE, 1, 200),
                    new GEItem(ItemID.KNIFE, 1, 200)

            )
    );

    InventoryRequirement initialItems = new InventoryRequirement(new ArrayList<>(
            Arrays.asList(
                    new ItemReq(ItemID.ADAMANT_SCIMITAR, 1, true, true),
                    new ItemReq(ItemID.IRON_PLATEBODY, 1, true, true),
                    new ItemReq(ItemID.AMULET_OF_GLORY[2], 1, 0, true, true),
                    new ItemReq(ItemID.AMULET_OF_GLORY[2], 1, 0),
                    new ItemReq(ItemID.STAMINA_POTION[0], 2, 0),
                    new ItemReq(ItemID.IRON_PLATELEGS, 1, true, true),
                    new ItemReq(ItemID.IRON_FULL_HELM, 1, true, true),
                    new ItemReq(ItemID.IRON_KITESHIELD, 1, true, true),
                    new ItemReq(ItemID.LOBSTER, 14, 4),
                    new ItemReq(ItemID.PRAYER_POTION4, 2, 0),
                    new ItemReq(ItemID.RING_OF_WEALTH[0], 1, 1, true, true),
                    new ItemReq(ItemID.ROPE, 1),
                    new ItemReq(ItemID.KNIFE, 1)
            ))
    );

    BuyItemsStep buyStep = new BuyItemsStep(itemsToBuy);


    ItemReq rope = new ItemReq("Rope", ItemID.ROPE);
    ItemReq slashItem = new ItemReq("A knife or slash weapon", ItemID.KNIFE);
    ItemReq reinforcedGoggles = new ItemReq(ItemID.REINFORCED_GOGGLES, 1, true);
    ItemReq hoof = new ItemReq("Sourhog foot", ItemID.SOURHOG_FOOT);

    RSArea cave = new RSArea(new RSTile(3152, 9669, 0), new RSTile(3181, 9720, 0));
    AreaRequirement inCave = new AreaRequirement(cave);


    ObjectStep readNotice = new ObjectStep(40307, new RSTile(3086, 3251, 0),
            "Check");

    NPCStep talkToSarah = new NPCStep("Sarah", new RSTile(3033, 3293, 0));


    UseItemOnObjectStep useRopeOnHole = new UseItemOnObjectStep(ItemID.ROPE, 40341,
            new RSTile(3151, 3346, 0),
            "Use a rope on the Strange Hole east of Draynor Manor.", rope);

    ObjectStep enterHole = new ObjectStep(40341, new RSTile(3151, 3348, 0),
            "Climb-down",
            Game.isInInstance());

    ObjectStep investigateSkeleton = new ObjectStep(40350, Player.getPosition(),
            "Investigate", NPCInteraction.isConversationWindowUp());

    public void setInvestigateSkeleton() {
        Utils.cutScene();
        RSObject[] skel = Objects.findNearest(40, 40350);
        RSObject[] blockage = Objects.findNearest(40, "Blockage");
        if (skel.length > 0) {
            Log.info("Detected skeleton");
            if (PathingUtil.localNavigation(skel[0].getPosition()))
                PathingUtil.movementIdle();
            else if (blockage.length > 0 && Utils.clickObject(blockage[0], "Climb-over")) {
                Log.info("Navigating blockage");
                PathingUtil.movementIdle();
            }
        }
        cQuesterV2.status = "Investigating Skeleton";
        investigateSkeleton.setUseLocalNav(true);
        investigateSkeleton.execute();
        Timer.waitCondition(() -> Interfaces.get(222, 13) != null, 3500, 4000);
        RSInterface inter = Interfaces.get(222, 13);
        if (inter != null && inter.click()) {
            Waiting.waitNormal(1500, 75);
            Utils.cutScene();
        }
    }

    NPCStep talkToSpria = new NPCStep("Spria", new RSTile(3092, 3267, 0));

    ObjectStep enterHoleAgain = new ObjectStep(40341, new RSTile(3151, 3348, 0),
            "Climb-down", Game.isInInstance(),
            reinforcedGoggles, slashItem);


    ObjectStep enterHoleForFoot = new ObjectStep(40341, new RSTile(3151, 3348, 0),
            "Climb-down", Game.isInInstance(), slashItem);

    ObjectStep cutOffFoot = new ObjectStep(40349, Player.getPosition(), "Cut-foot", slashItem);
    //  ((ObjectStep) cutOffFoot).addAlternateObjects(NullObjectID.NULL_40348);

    NPCStep returnToSarah = new NPCStep("Sarah", new RSTile(3034, 3293, 0), hoof);
    NPCStep returnToSpria = new NPCStep("Spria", new RSTile(3092, 3267, 0));

    public void addDialogue() {
        readNotice.addDialogStep("Yes.");
        talkToSarah.addDialogStep("Talk about the bounty.", "I think that'll be all for now.");

        returnToSarah.addDialogStep("Talk about the bounty.");
    }


    public void useRopeOnHoleStep() {
        cQuesterV2.status = "Using rope on hole";
        useRopeOnHole.useItemOnObject();
        NpcChat.handle(true, "I think that'll be all for now.");
    }


    private boolean cutOffFoot() {
        cQuesterV2.status = "Getting foot";
        if (!hoof.check() &&
                QueryUtils.getObject("Dead sourhog")
                        .map(s -> s.interact("Cut-foot")).orElse(false)) {
            return Waiting.waitUntil(7500, 500, () ->
                    Inventory.getCount(ItemID.SOURHOG_FOOT) > 0);
        }
        return hoof.check();
    }

    @Override
    public String toString() {
        return "A Porcine of Interest " + Game.getSetting(Quests.A_PORCINE_OF_INTEREST.getGameSetting());
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
    public void execute() {
        addDialogue();
        int setting = Quests.A_PORCINE_OF_INTEREST.getVarbitList().get(0);
        General.println("Varbit is " + Utils.getVarBitValue(setting));
        if (Utils.getVarBitValue(setting) == 0) {
            if (!initialItems.check()) {
                cQuesterV2.status = "Buying Items";
                buyStep.buyItems();
                cQuesterV2.status = "Withdrawing items";
                initialItems.withdrawItems();
            }
            cQuesterV2.status = "Reading notice";
            readNotice.execute();
            NPCInteraction.waitForConversationWindow();
            if (NPCInteraction.isConversationWindowUp())
                NPCInteraction.handleConversation("Yes.");
        } else if (Utils.getVarBitValue(setting) == 5) {
            cQuesterV2.status = "Talking to Sarah";
            talkToSarah.execute();
        } else if (Utils.getVarBitValue(setting) == 10) {
            useRopeOnHoleStep();
        } else if (Utils.getVarBitValue(setting) == 15) {
            if (!Game.isInInstance()) {
                cQuesterV2.status = "Entering Hole";
                enterHole.execute();
            } else
                setInvestigateSkeleton();
        } else if (Utils.getVarBitValue(setting) == 20) {
            cQuesterV2.status = "Talking to Spira";
            talkToSpria.execute();
        } else if (Utils.getVarBitValue(setting) == 25) {
            if (!Combat.isUnderAttack()) {
                Utils.equipItem(ItemID.REINFORCED_GOGGLES, "Wear");
                if (!Game.isInInstance()) {
                    cQuesterV2.status = "Entering hole again";
                    enterHoleAgain.execute();
                }
                RSObject[] blockage = Objects.findNearest(40, "Blockage");
                if (blockage.length > 0 && Utils.clickObject(blockage[0], "Climb-over")) {
                    Log.info("Navigating blockage");
                    NPCInteraction.waitForConversationWindow();
                    NPCInteraction.handleConversation("Yes");
                    if (Timer.waitCondition(() -> Player.getAnimation() != -1, 4500, 5000))
                        Timer.slowWaitCondition(() -> Player.getAnimation() == -1, 4500, 5000);
                }
            }
            cQuesterV2.status = "Killing Sourhog";
            Log.info("" + cQuesterV2.status);
            NPCStep killSourhog = new NPCStep(10436, Player.getPosition(), reinforcedGoggles);


            if (Game.isInInstance() && reinforcedGoggles.check()) {
                RSNPC[] sourhog = NPCs.findNearest(10436);
                if (sourhog.length > 0) {
                    if (!sourhog[0].isInCombat() &&
                            Utils.clickNPC(sourhog[0], "Attack", false)) {
                        Timer.waitCondition(() -> Combat.isUnderAttack(), 3500, 4000);
                    }
                    if (Combat.isUnderAttack()) {
                        cQuesterV2.status = "In combat";
                        CombatUtil.waitUntilOutOfCombat(50);
                    }
                }
            }
            killSourhog.addDialogStep("Yes");
            killSourhog.setInteractionString("Attack");

            killSourhog.execute();
            Timer.waitCondition(() -> Combat.isUnderAttack(), 3500, 4000);
            if (!hoof.check())
                cutOffFoot();
        } else if (Utils.getVarBitValue(setting) == 30 && !hoof.check()) {
            if (!GameState.isInInstance()) {
                cQuesterV2.status = "Entering hole again";
                enterHoleAgain.execute();
            }
            RSObject[] blockage = Objects.findNearest(40, "Blockage");
            if (blockage.length > 0 && Utils.clickObject(blockage[0], "Climb-over")) {
                Log.info("Navigating blockage");
                if (Timer.waitCondition(() -> Player.getAnimation() != -1, 4500, 5000))
                    Timer.slowWaitCondition(() -> Player.getAnimation() == -1, 4500, 5000);
            }

            cQuesterV2.status = "Cutting off Foot";
            cutOffFoot();
        } else if (Game.getSetting(setting) >= 30 && Game.getSetting(setting) < 99 || hoof.check()) {
            cQuesterV2.status = "Returning to Sarah";
            returnToSarah.execute();
        } else if (Utils.getVarBitValue(setting) == 35) {
            cQuesterV2.status = "Returning to Spira";
            returnToSpria.execute();
        } else if (

                isComplete()) {
            Utils.closeQuestCompletionWindow();
            cQuesterV2.taskList.remove(this);
        }
        Utils.cutScene();
        NpcChat.handle();
        Waiting.waitNormal(200, 20);
    }

    @Override
    public String questName() {
        return "A Porcine of Interest (" +
                Quest.A_PORCINE_OF_INTEREST.getStep() + ")";
    }

    @Override
    public boolean checkRequirements() {
        return Skills.getActualLevel(Skills.SKILLS.ATTACK) >= 30;
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
        return Quest.A_PORCINE_OF_INTEREST.getState().equals(Quest.State.COMPLETE);
    }
}
