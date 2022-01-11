package scripts.QuestPackages.APorcineOfInterest;

import dax.walker_engine.interaction_handling.NPCInteraction;
import org.tribot.api.General;
import org.tribot.api2007.*;
import org.tribot.api2007.types.*;
import org.tribot.script.sdk.Log;
import org.tribot.script.sdk.Waiting;
import scripts.*;
import scripts.GEManager.GEItem;
import scripts.QuestPackages.GertrudesCat.GertrudesCat;
import scripts.QuestSteps.*;
import scripts.Requirements.AreaRequirement;
import scripts.Requirements.InventoryRequirement;
import scripts.Requirements.ItemReq;
import scripts.Tasks.Priority;
import scripts.Tasks.Task;

import java.util.ArrayList;
import java.util.Arrays;

public class APorcineOfInterest implements QuestTask {

    private static APorcineOfInterest quest;

    public static APorcineOfInterest get() {
        return quest == null ? quest = new APorcineOfInterest() : quest;
    }


    ArrayList<GEItem> itemsToBuy = new ArrayList<GEItem>(
            Arrays.asList(
                    new GEItem(ItemId.ADAMANT_SCIMITAR, 1, 100),
                    new GEItem(ItemId.IRON_PLATEBODY, 1, 200),
                    new GEItem(ItemId.AMULET_OF_GLORY[2], 1, 20),
                    new GEItem(ItemId.STAMINA_POTION[0], 2, 20),
                    new GEItem(ItemId.IRON_PLATELEGS, 1, 200),
                    new GEItem(ItemId.IRON_FULL_HELM, 1, 200),
                    new GEItem(ItemId.IRON_KITESHIELD, 1, 200),
                    new GEItem(ItemId.LOBSTER, 20, 200),
                    new GEItem(ItemId.PRAYER_POTION_4, 2, 15),
                    new GEItem(ItemId.ROPE, 1, 200),
                    new GEItem(ItemId.KNIFE, 1, 200)

            )
    );

    InventoryRequirement initialItems = new InventoryRequirement(new ArrayList<>(
            Arrays.asList(
                    new ItemReq(ItemId.ADAMANT_SCIMITAR, 1, true, true),
                    new ItemReq(ItemId.IRON_PLATEBODY, 1, true, true),
                    new ItemReq(ItemId.AMULET_OF_GLORY[2], 1, 0, true, true),
                    new ItemReq(ItemId.STAMINA_POTION[0], 2, 0),
                    new ItemReq(ItemId.IRON_PLATELEGS, 1, true, true),
                    new ItemReq(ItemId.IRON_FULL_HELM, 1, true, true),
                    new ItemReq(ItemId.IRON_KITESHIELD, 1, true, true),
                    new ItemReq(ItemId.LOBSTER, 14, 4),
                    new ItemReq(ItemId.PRAYER_POTION_4, 2, 0),
                    new ItemReq(ItemId.RING_OF_WEALTH[0], 1, 1, true, true),
                    new ItemReq(ItemId.ROPE, 1),
                    new ItemReq(ItemId.KNIFE, 1)
            ))
    );

    BuyItemsStep buyStep = new BuyItemsStep(itemsToBuy);


    ItemReq rope = new ItemReq("Rope", ItemId.ROPE);
    ItemReq slashItem = new ItemReq("A knife or slash weapon", ItemId.KNIFE);
    ItemReq reinforcedGoggles = new ItemReq(ItemId.REINFORCED_GOGGLES, 1, true);
    ItemReq hoof = new ItemReq("Sourhog foot", ItemId.SOURHOG_FOOT);

    RSArea cave = new RSArea(new RSTile(3152, 9669, 0), new RSTile(3181, 9720, 0));
    AreaRequirement inCave = new AreaRequirement(cave);


    ObjectStep readNotice = new ObjectStep(40307, new RSTile(3086, 3251, 0),
            "Check");

    NPCStep talkToSarah = new NPCStep("Sarah", new RSTile(3033, 3293, 0));


    UseItemOnObjectStep useRopeOnHole = new UseItemOnObjectStep(ItemId.ROPE, 40341,
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
            Log.log("[Debug]: Detected skeleton");
            if (PathingUtil.localNavigation(skel[0].getPosition()))
                PathingUtil.movementIdle();
            else if (blockage.length > 0 && Utils.clickObject(blockage[0], "Climb-over")) {
                Log.log("[Debug]: Navigating blockage");
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
        NPCInteraction.waitForConversationWindow();
        NPCInteraction.handleConversation("I think that'll be all for now.");
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
                Utils.equipItem(ItemId.REINFORCED_GOGGLES, "Wear");
                if (!Game.isInInstance()) {
                    cQuesterV2.status = "Entering hole again";
                    enterHoleAgain.execute();
                }
                RSObject[] blockage = Objects.findNearest(40, "Blockage");
                if (blockage.length > 0 && Utils.clickObject(blockage[0], "Climb-over")) {
                    Log.log("[Debug]: Navigating blockage");
                    NPCInteraction.waitForConversationWindow();
                    NPCInteraction.handleConversation("Yes");
                    if (Timer.waitCondition(() -> Player.getAnimation() != -1, 4500, 5000))
                        Timer.slowWaitCondition(() -> Player.getAnimation() == -1, 4500, 5000);
                }
            }
            cQuesterV2.status = "Killing Sourhog";
            Log.log("[Debug]: " + cQuesterV2.status);
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
                cutOffFoot.execute();
        } else if (Utils.getVarBitValue(setting) == 30 && !hoof.check()) {
            if (!Game.isInInstance()) {
                cQuesterV2.status = "Entering hole again";
                enterHoleAgain.execute();
                RSObject[] blockage = Objects.findNearest(40, "Blockage");
                if (blockage.length > 0 && Utils.clickObject(blockage[0], "Climb-over")) {
                    Log.log("[Debug]: Navigating blockage");
                    if (Timer.waitCondition(() -> Player.getAnimation() != -1, 4500, 5000))
                        Timer.slowWaitCondition(() -> Player.getAnimation() == -1, 4500, 5000);
                }
            }
            cQuesterV2.status = "Cutting off Foot";
            cutOffFoot.execute();
        } else if (Game.getSetting(setting) >= 30 && Game.getSetting(setting) < 99 || hoof.check()) {
            cQuesterV2.status = "Returning to Sarah";
            returnToSarah.execute();
        } else if (Utils.getVarBitValue(setting) == 35) {
            cQuesterV2.status = "Returning to Spira";
            returnToSpria.execute();
        }else if (Utils.getVarBitValue(setting) == 100) {
            cQuesterV2.taskList.remove(this);
        }

    }

    @Override
    public String questName() {
        return "A Porcine of Interest";
    }

    @Override
    public boolean checkRequirements() {
        return Skills.getActualLevel(Skills.SKILLS.ATTACK) >= 30;
    }
}
