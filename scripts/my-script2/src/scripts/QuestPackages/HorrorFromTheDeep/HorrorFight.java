package scripts.QuestPackages.HorrorFromTheDeep;

import dax.walker.utils.AccurateMouse;
import dax.walker.utils.camera.DaxCamera;
import dax.walker_engine.interaction_handling.NPCInteraction;
import org.tribot.api.General;
import org.tribot.api2007.*;
import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSItem;
import org.tribot.api2007.types.RSNPC;
import org.tribot.api2007.types.RSTile;
import org.tribot.script.sdk.tasks.Amount;
import org.tribot.script.sdk.tasks.BankTask;
import org.tribot.script.sdk.tasks.BankTaskError;
import org.tribot.script.sdk.tasks.EquipmentReq;
import scripts.*;
import scripts.QuestSteps.NPCStep;
import scripts.QuestSteps.QuestStep;
import scripts.QuestSteps.QuestTask;
import scripts.Requirements.InventoryRequirement;
import scripts.Requirements.ItemReq;
import scripts.Requirements.ItemRequirement;
import scripts.Requirements.Requirement;
import scripts.Tasks.Priority;
import scripts.Tasks.Task;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class HorrorFight implements QuestTask {

    InventoryRequirement fightInv = new InventoryRequirement(new ArrayList<>(
            Arrays.asList(
                    new ItemReq(ItemID.CHAOS_RUNE, 400, 50),
                    new ItemReq(ItemID.LAVA_RUNE, 1200, 300),
                    new ItemReq(ItemID.WATER_RUNE, 1200, 300),
                    new ItemReq(ItemID.STAFF_OF_AIR, 1, 0),
                    new ItemReq(ItemID.LOBSTER, 15, 5),
                    new ItemReq(ItemID.GAMES_NECKLACE[0], 1, 0),
                    new ItemReq(ItemID.STAMINA_POTION[0], 1, 0),
                    new ItemReq(ItemID.PRAYER_POTION[0], 3, 0)
            ))
    );

    public static boolean shouldBuyAllBooks = false;

    BankTask bankTaskOne = BankTask.builder()
            .addInvItem(ItemID.CHAOS_RUNE, Amount.of(400))
            .addEquipmentItem(EquipmentReq.slot(org.tribot.script.sdk.Equipment.Slot.BODY).item(
                    ItemID.MONKS_ROBE_TOP, Amount.of(1)))
            .addEquipmentItem(EquipmentReq.slot(org.tribot.script.sdk.Equipment.Slot.LEGS).item(
                    ItemID.MONKS_ROBE_BOTTOM, Amount.of(1)))
            .addEquipmentItem(EquipmentReq.slot(org.tribot.script.sdk.Equipment.Slot.WEAPON).item(
                    ItemID.STAFF_OF_AIR, Amount.of(1)))
            .addInvItem(ItemID.LAVA_RUNE, Amount.of(1200))
            .addInvItem(ItemID.WATER_RUNE, Amount.of(1200))
            .addInvItem(ItemID.STAMINA_POTION[0], Amount.of(1))
            .addInvItem(ItemID.PRAYER_POTION[0], Amount.of(3))
            .addInvItem(ItemID.COINS, Amount.of(25000))
            .addInvItem(ItemID.GAMES_NECKLACE[1], Amount.of(1))
            .addInvItem(ItemID.LOBSTER, Amount.fill(1))
            .build();

    public boolean setSpell(Autocast spell) {
        if (!Autocast.isAutocastEnabled(spell)) {
            Autocast.enableAutocast(spell);
            RSNPC[] mother = NPCs.find("Dagannoth Mother");
            if (mother.length > 0) {
                if (mother[0].click("Attack"))
                    return Timer.waitCondition(mother[0]::isInCombat, 2500);
            }
            return true;
        }
        return Autocast.isAutocastEnabled(spell);
    }

    NPCStep jossik = new NPCStep("Jossik", HorrorConst.BOTTOM_OF_PIT);

    public void goToFinalFight() {
        cQuesterV2.status = "Going to final fight";
        if (!HorrorConst.BASEMENT.contains(Player.getPosition()) &&
                !HorrorConst.LEDGE.contains(Player.getPosition()) &&
                !HorrorConst.BOTTOM_OF_PIT.contains(Player.getPosition())) {
            LightHouseSteps.goDownStairs();
        }
        if (HorrorConst.BASEMENT.contains(Player.getPosition()))
            if (Utils.clickObject(4545, "Open", false))
                Timer.abc2WaitCondition(() -> HorrorConst.LEDGE.contains(Player.getPosition()), 7000, 9000);

        if (HorrorConst.LEDGE.contains(Player.getPosition()))
            if (Utils.clickObject(4485, "Climb", true)) {
                General.println("Debug: Climbing down");
                Timer.abc2WaitCondition(() -> HorrorConst.BOTTOM_OF_PIT.contains(Player.getPosition()), 5000, 8000);
            }
        if (!Combat.isUnderAttack() && HorrorConst.BOTTOM_OF_PIT.contains(Player.getPosition())) {
            setSpell(Autocast.FIRE_BOLT);
            cQuesterV2.status = "Talking to Jossik";
            jossik.setRadius(5);
            jossik.execute();

        }
    }

    int eatAtPercent = General.random(40, 65);

    public boolean checkEat() {
        return Combat.getHPRatio() <= eatAtPercent && EatUtil.eatFood();
    }

    public void handleFightDagannoth() {
        if (HorrorConst.DAGGANOTH_AREA.contains(Player.getPosition())) {
            cQuesterV2.status = "Fighting Dagganoth";
            RSNPC[] dagFinal = NPCs.find("Dagannoth");
            if (dagFinal.length > 0) {
                General.println("[Debug]: Dagganoth is present, waiting for interaction");
                Timer.waitCondition(() -> dagFinal[0].isInteractingWithMe(), 5000, 6000);
            }

            moveToSafeArea();

            int eat = General.random(40, 60);

            RSArea safeArea = (new RSArea(new RSTile(2512, 4655, 0), 3));

            RSNPC[] dag = NPCs.find("Dagannoth");
            setSpell(Autocast.FIRE_BOLT);
            while (dag.length > 0) {
                checkEat();
                General.sleep(General.random(30, 60));
                if (!safeArea.contains(Player.getPosition())) {
                    General.println("[Debug]: Moving to safe area");
                    Walking.blindWalkTo(new RSTile(2511, 4655, 0));
                    Timer.waitCondition(() -> safeArea.contains(Player.getPosition()), 3500, 5000);
                }

                clickAttackNPC(dag[0]);

                CombatUtil.waitUntilOutOfCombat(eat);

                dag = NPCs.find("Dagannoth");

            }
        }
    }

    public boolean clickAttackNPC(RSNPC npc) {
        if (npc == null)
            return false;

        if (!npc.isClickable())
            DaxCamera.focus(npc);

        if (!npc.isInCombat() || !npc.isInteractingWithMe())
            if (AccurateMouse.click(npc, "Attack"))
                return Timer.waitCondition(npc::isInCombat, 6000, 7000);
        ;

        return npc.isInCombat() && npc.isInteractingWithMe();
    }

    public boolean clickAttackNPC(RSNPC[] npc) {
        if (npc.length > 0) {
            if (!npc[0].isClickable())
                DaxCamera.focus(npc[0]);

            if (!npc[0].isInCombat() || !npc[0].isInteractingWithMe())
                if (AccurateMouse.click(npc[0], "Attack"))
                    return Timer.waitCondition(npc[0]::isInCombat, 6000, 7000);

            return npc[0].isInCombat() && npc[0].isInteractingWithMe();
        }
        return false;
    }


    public void moveToSafeArea() {
        if (!HorrorConst.MOTHER_SAFE_TILE.equals(Player.getPosition())) {
            if (HorrorConst.MOTHER_SAFE_TILE.isClickable())
                Walking.clickTileMS(HorrorConst.MOTHER_SAFE_TILE, "Walk here");

            else Walking.blindWalkTo(HorrorConst.MOTHER_SAFE_TILE);

            Timer.waitCondition(Player::isMoving, 750);
            Timer.waitCondition(() -> HorrorConst.MOTHER_SAFE_TILE.equals(Player.getPosition()) || Player.isMoving(), 4000, 6000);
        }
    }

    public void handleFightDagannothMother() {
        cQuesterV2.status = "Fighting Dagganoth Mother";
        if (NPCInteraction.isConversationWindowUp()) {
            NPCInteraction.handleConversation();
        }

        RSNPC[] mother = NPCs.find("Dagannoth Mother");
        if (mother.length < 1)
            goToFinalFight();

        moveToSafeArea();

        checkEat();
        FORMS form = getForm();
        if (form != null || mother.length > 0) {
            mother = NPCs.find("Dagannoth Mother");

            int prayDrink = General.random(10, 25);
            if (Prayer.getPrayerPoints() < prayDrink) {
                EatUtil.drinkPotion(ItemID.PRAYER_POTION);
                prayDrink = General.random(10, 25);
                General.println("[Debug]: Next drinking prayer pot at: " + prayDrink);
            }

            if (Prayer.getPrayerPoints() > 0 && Skills.getActualLevel(Skills.SKILLS.PRAYER) > 37)
                Prayer.enable(Prayer.PRAYERS.PROTECT_FROM_MISSILES);

            moveToSafeArea();
            General.sleep(General.random(50, 150));


            form = getForm();
            if (form == FORMS.WHITE_FORM) {
                setSpell(Autocast.WIND_BOLT);
                clickAttackNPC(mother);
            }
            if (form == FORMS.BLUE_FORM) {
                setSpell(Autocast.WATER_BOLT);
                clickAttackNPC(mother);
            }
            if (form == FORMS.BROWN_FORM) {
                setSpell(Autocast.EARTH_BOLT);
                clickAttackNPC(mother);
            }
            if (form == FORMS.RED_FORM) {
                setSpell(Autocast.FIRE_BOLT);
                clickAttackNPC(mother);
            }
            if (form == FORMS.ORANGE_FORM || form == FORMS.GREEN_FORM) {
                cQuesterV2.status = "Idling";
                General.println("[Debug]: Idling in orange or green form");
                AntiBan.timedActions();
                General.sleep(500, 2000);
                //    checkEatAndDrink();
            }
            if (Game.getSetting(351) == 33552394) {
                Utils.closeQuestCompletionWindow();
                Utils.continuingChat();
                //break;
            }
            FORMS finalForm = form;
            Timer.waitCondition(() -> getForm() != finalForm, 5000, 8000);
        }
    }

    public FORMS getForm() {
        RSNPC[] allForms = NPCs.find("Dagannoth Mother");
        if (allForms.length > 0) {
            for (FORMS f : FORMS.values()) {
                if (allForms[0].getID() == f.id)
                    return f;

            }
        }
        return null;

    }


    public void handleBankError() {
        Optional<BankTaskError> err = bankTaskOne.execute();
        if (err.isPresent()) {
            General.println("[Debug]: bank task gave an error: " + err.get().toString());

        }
    }

    int[] bookArrayInter = {3, 5, 6, 7, 8};
    int[] ALL_DAMAGED_BOOKS = {
            3843, // balance
            3839, // sara inter = 3
            3841, // unholy inter = 5
            12609, // arma inter = 6
            12611, // dark inter = 7
            12607 // law inter = 8
    };

    public void getDamagedBook() {
        Prayer.disable(Prayer.PRAYERS.PROTECT_FROM_MISSILES);
        if (NPCInteraction.isConversationWindowUp())
            NPCInteraction.handleConversation();

        if (Inventory.find(ALL_DAMAGED_BOOKS[0]).length < 1) {
            cQuesterV2.status = "Getting Damaged Book";
            PathingUtil.walkToArea(HorrorConst.FIXED_SECOND_FLOOR, false);
            if (Inventory.isFull())
                EatUtil.eatFood();
            if (Utils.clickNPC("Jossik", "Talk-to")) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation("Guthix");
                NPCInteraction.handleConversation("Guthix");
                NPCInteraction.handleConversation();
            }
        }
    }


    public void buyBooks() {
        if (shouldBuyAllBooks) {
            RSItem[] books = Inventory.find(ALL_DAMAGED_BOOKS);
            if (books.length < 5) {
                int space = 28 - Inventory.getAll().length;
                if (space < 5) {
                    General.println("[Debug]: Eatting for space: " + space);
                    for (int i = 0; space < 6; space++) {
                        EatUtil.eatFood();
                        General.sleep(400, 600);
                    }

                }
                if (!Interfaces.isInterfaceSubstantiated(302) &&
                        Utils.clickNPC("Jossik", "Rewards")) {
                    Timer.waitCondition(() -> Interfaces.isInterfaceSubstantiated(302), 6000, 8000);
                }
                if (Interfaces.isInterfaceSubstantiated(302)) {
                    for (int i : bookArrayInter) {
                        if (InterfaceUtil.click(302, i))
                            General.sleep(400, 1200);
                    }
                }
            }
        }
    }

    @Override
    public String toString() {
        return "(" + Game.getSetting(HorrorConst.GAME_SETTING) + ")";
    }

    @Override
    public Priority priority() {
        return Priority.HIGH;
    }

    @Override
    public boolean validate() {
        General.println("Game setting " + Game.getSetting(HorrorConst.GAME_SETTING));
        return Game.getSetting(77) == 2 && (Game.getSetting(HorrorConst.GAME_SETTING) == 33552388 ||
                Game.getSetting(HorrorConst.GAME_SETTING) == 33552388 ||
                Game.getSetting(HorrorConst.GAME_SETTING) == 33552389);
    }


    @Override
    public void execute() {
        General.sleep(10, 30);
        if (!fightInv.check()) {
            cQuesterV2.status = "Getting fight items";
            if (!fightInv.check()) {
                General.println("[Debug]: Executing bankTaskOne");
                BankManager.open(true);

                handleBankError();
            }
        }

        if (Game.getSetting(HorrorConst.GAME_SETTING) == 33552388) {
            goToFinalFight();
            handleFightDagannoth();
        }
        if (Game.getSetting(HorrorConst.GAME_SETTING) == 33552389)
            handleFightDagannothMother();

        if (Game.getSetting(351) == 33552394){
            buyBooks();
        }

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
        return false;
    }
}
