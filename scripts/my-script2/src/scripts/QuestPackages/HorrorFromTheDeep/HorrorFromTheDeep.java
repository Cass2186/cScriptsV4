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
import scripts.GEManager.GEItem;
import scripts.QuestPackages.AscentOfArceuus.AscentOfArceuus;
import scripts.QuestSteps.*;
import scripts.Requirements.InventoryRequirement;
import scripts.Requirements.ItemReq;
import scripts.Requirements.SkillRequirement;
import scripts.Tasks.Priority;
import scripts.Tasks.Task;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

public class HorrorFromTheDeep implements QuestTask, QuestInterface {

    private static HorrorFromTheDeep quest;

    public static HorrorFromTheDeep get() {
        return quest == null ? quest = new HorrorFromTheDeep() : quest;
    }

    SkillRequirement magicReq = new SkillRequirement(Skills.SKILLS.MAGIC, 35);
    SkillRequirement agilityReq = new SkillRequirement(Skills.SKILLS.AGILITY, 35);

    InventoryRequirement startInventory = new InventoryRequirement(new ArrayList<>(
            Arrays.asList(
                    new ItemReq(ItemID.STEEL_NAILS, 60),
                    new ItemReq(ItemID.PLANK, 2),
                    new ItemReq(ItemID.HAMMER, 1),
                    new ItemReq(ItemID.SWAMP_TAR, 1),
                    new ItemReq(ItemID.TINDERBOX, 1),
                    new ItemReq(ItemID.MOLTEN_GLASS, 1),
                    new ItemReq(ItemID.FIRE_RUNE, 1),
                    new ItemReq(ItemID.WATER_RUNE, 1),
                    new ItemReq(ItemID.EARTH_RUNE, 1),
                    new ItemReq(ItemID.AIR_RUNE, 1),
                    new ItemReq(ItemID.BRONZE_ARROW, 1),
                    new ItemReq(ItemID.BRONZE_SWORD, 1),
                    new ItemReq(ItemID.GAMES_NECKLACE[0], 1, 0),
                    new ItemReq(ItemID.STAMINA_POTION[0], 1, 0),
                    new ItemReq(ItemID.LOBSTER, 10, 5)
            ))
    );

    public static ArrayList<GEItem> itemsToBuy = new ArrayList<GEItem>(
            Arrays.asList(
                    new GEItem(ItemID.STEEL_NAILS, 60, 50),
                    new GEItem(ItemID.PLANK, 2, 50),
                    new GEItem(ItemID.HAMMER, 1, 500),
                    new GEItem(ItemID.SWAMP_TAR, 1, 50),
                    new GEItem(ItemID.TINDERBOX, 1, 500),
                    new GEItem(ItemID.MOLTEN_GLASS, 1, 20),
                    new GEItem(ItemID.FIRE_RUNE, 1, 15),
                    new GEItem(ItemID.EARTH_RUNE, 1, 15),
                    new GEItem(ItemID.AIR_RUNE, 1, 15),
                    new GEItem(ItemID.BRONZE_ARROW, 1, 50),
                    new GEItem(ItemID.BRONZE_SWORD, 1, 50),
                    new GEItem(ItemID.GAMES_NECKLACE[0], 2, 50),
                    new GEItem(ItemID.STAMINA_POTION[0], 2, 20),
                    new GEItem(ItemID.LOBSTER, 25, 15),
                    new GEItem(ItemID.CHAOS_RUNE, 400, 15),
                    new GEItem(ItemID.WATER_RUNE, 1250, 15),
                    new GEItem(ItemID.LAVA_RUNE, 1200, 15),
                    new GEItem(ItemID.PRAYER_POTION[0], 3, 15),
                    new GEItem(ItemID.SUMMER_PIE, 1, 15),
                    new GEItem(ItemID.STAFF_OF_AIR, 1, 35),
                    new GEItem(ItemID.MONKS_ROBE_BOTTOM, 1, 350),
                    new GEItem(ItemID.MONKS_ROBE_TOP, 1, 350)
            )
    );

    String[] START_DIALOG = new String[]{
            "With what?",
            "But how can I help?",
            "Okay, I'll help!",
            "I'll see what I can do"
    };


    NPCStep larissaStep = new NPCStep("Larrissa", new RSTile(2506, 3634, 0), START_DIALOG);
    NPCStep barbarianStep = new NPCStep("Gunnjorn", new RSTile(2541, 3548, 0));

    static BuyItemsStep buyInitial = new BuyItemsStep(itemsToBuy);

    UseItemOnObjectStep plankOnBridgeOne = new UseItemOnObjectStep(ItemID.PLANK, "Broken bridge",
            HorrorConst.BRIDGE_TILE, NPCInteraction.isConversationWindowUp());

    UseItemOnObjectStep plankOnBridgeTwo = new UseItemOnObjectStep(ItemID.PLANK, "Broken bridge",
            HorrorConst.BRIDGE_TILE_2, NPCInteraction.isConversationWindowUp());


    public static void unlockDoor() {
        if (!HorrorConst.BOTTOM_FLOOR.contains(Player.getPosition()) && !HorrorConst.BASEMENT.contains(Player.getPosition())
                && !HorrorConst.DAGGANOTH_AREA.contains(Player.getPosition()) && !HorrorConst.SECOND_FLOOR.contains(Player.getPosition())) {
            cQuesterV2.status = "Going to unlock door";
            PathingUtil.walkToArea(HorrorConst.START_AREA, false);

            if (Utils.clickObject("Doorway", "Walk-through", false))
                Timer.waitCondition(() -> HorrorConst.INSIDE_LIGHTHOUSE_DOOR.contains(Player.getPosition()), 8000, 12000);

        }
    }


    public static void goToLightHouse() {
        if (HorrorConst.OUTSIDE_LIGHTHOUSE.contains(Player.getPosition()) ||
                (!HorrorConst.OUTSIDE_LIGHTHOUSE.contains(Player.getPosition())) &&
                        !HorrorConst.BASEMENT.contains(Player.getPosition()) &&
                        !HorrorConst.BOTTOM_OF_PIT.contains(Player.getPosition())
                        && Player.getPosition().getPlane() == 0) {
            HorrorFromTheDeep.unlockDoor();
        }
    }


    public void getBooks() {
        goToLightHouse();

        if (HorrorConst.BOTTOM_FLOOR.contains(Player.getPosition()))
            goUpStairs();

        if (HorrorConst.SECOND_FLOOR.contains(Player.getPosition()) && Inventory.find(HorrorConst.HORROR_MANUAL).length == 0) {
            if (Utils.clickObject(4617, "Search", true)) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation("Take all three books");
                Timer.abc2WaitCondition(() -> Inventory.find(HorrorConst.HORROR_JOURNAL).length > 0, 5000, 8000);
            }
        }
    }


    public void readBook(int book) {
        RSItem[] invItem1 = Inventory.find(book);
        if (invItem1.length > 0 && invItem1[0].click("Read")) {
            Timer.waitCondition(() -> Interfaces.isInterfaceSubstantiated(392, 77), 6000, 8000);
            for (int i = 0; i < 4; i++) {
                if (Interfaces.get(392, 77) != null && Interfaces.get(392, 77).click())
                    General.sleep(400, 800);
            }
        }
        // close book interface
        if (InterfaceUtil.clickInterfaceAction(392, "Close"))
            //if (Interfaces.get(392, 7) != null && Interfaces.get(392, 7).click())
            Timer.waitCondition(() -> !Interfaces.isInterfaceSubstantiated(392, 7), 2500, 4000);
    }

    public void readAllBooks() {
        readBook(HorrorConst.HORROR_JOURNAL);
        readBook(HorrorConst.HORROR_DIARY);
    }


    public void goUpStairs() {
        if (Player.getPosition().getPlane() < 2) {
            cQuesterV2.status = "Fixing Lighthouse";
            int plane = Player.getPosition().getPlane();

            if (Utils.clickObject("Staircase", "Climb-up", false))
                Timer.waitCondition(() -> Player.getPosition().getPlane() != plane, 4000, 6000);
        }
    }


    public boolean useItemOnLight(int item) {
        cQuesterV2.status = "Using item on Light";
        if (Utils.useItemOnObject(item, "Lighting mechanism"))
            return Timer.waitCondition(() -> Inventory.find(item).length < 1, 5000, 7000);

        return false;
    }


    public static void goDownStairs() {
        goToLightHouse();
        if (Player.getPosition().getPlane() != 0) {
            cQuesterV2.status = "Going to basement";
            int plane = Player.getPosition().getPlane();

            if (Utils.clickObject("Staircase", "Climb-down", false))
                Timer.waitCondition(() -> Player.getPosition().getPlane() != plane, 4000, 6000);

        } else if (HorrorConst.BOTTOM_FLOOR.contains(Player.getPosition()) ||
                HorrorConst.FIXED_GROUND_FLOOR.contains(Player.getPosition())) {
            General.println("[Debug]: Climbing down ladder");
            if (Utils.clickObject(4383, "Climb", true))
                Timer.waitCondition(() -> !HorrorConst.BOTTOM_FLOOR.contains(Player.getPosition()) &&
                        !HorrorConst.FIXED_GROUND_FLOOR.contains(Player.getPosition()), 6000, 8000);
        }
    }

    UseItemOnObjectStep fireRuneOnDoor = new UseItemOnObjectStep(ItemID.FIRE_RUNE, HorrorConst.BASEMENT_DOOR_ID,
            HorrorConst.BASEMENT_DOOR_TILE, Inventory.find(ItemID.FIRE_RUNE).length < 1, true);

    UseItemOnObjectStep airRuneOnDoor = new UseItemOnObjectStep(ItemID.AIR_RUNE, HorrorConst.BASEMENT_DOOR_ID,
            HorrorConst.BASEMENT_DOOR_TILE, Inventory.find(ItemID.AIR_RUNE).length < 1, true);

    UseItemOnObjectStep waterRuneOnDoor = new UseItemOnObjectStep(ItemID.WATER_RUNE, HorrorConst.BASEMENT_DOOR_ID,
            HorrorConst.BASEMENT_DOOR_TILE, Inventory.find(ItemID.WATER_RUNE).length < 1, true);

    UseItemOnObjectStep earthRuneOnDoor = new UseItemOnObjectStep(ItemID.EARTH_RUNE, HorrorConst.BASEMENT_DOOR_ID,
            HorrorConst.BASEMENT_DOOR_TILE, Inventory.find(ItemID.EARTH_RUNE).length < 1, true);

    UseItemOnObjectStep arrowOnDoor = new UseItemOnObjectStep(ItemID.BRONZE_ARROW, HorrorConst.BASEMENT_DOOR_ID,
            HorrorConst.BASEMENT_DOOR_TILE, Inventory.find(ItemID.BRONZE_ARROW).length < 1, true);

    UseItemOnObjectStep swordOnDoor = new UseItemOnObjectStep(ItemID.BRONZE_SWORD, HorrorConst.BASEMENT_DOOR_ID,
            HorrorConst.BASEMENT_DOOR_TILE, Inventory.find(ItemID.BRONZE_SWORD).length < 1, true);


    public void handleBasementDoor() {
        if (Player.getPosition().getPlane() == 0) {
            goDownStairs();
            cQuesterV2.status = "Using items on door";
            if (Game.getSetting(HorrorConst.GAME_SETTING) == 29421572
                    && Utils.getVarBitValue(40) == 0) {
                fireRuneOnDoor.setTileRadius(4);
                fireRuneOnDoor.useItemOnObject();
            }
            if (Game.getSetting(HorrorConst.GAME_SETTING) == 29487108 &&
                    Utils.getVarBitValue(43) == 0) {
                airRuneOnDoor.useItemOnObject();
            }
            if (Game.getSetting(HorrorConst.GAME_SETTING) == 30011396 &&
                    Utils.getVarBitValue(41) == 0) {
                waterRuneOnDoor.useItemOnObject();
            }
            if (Game.getSetting(HorrorConst.GAME_SETTING) == 30142468 &&
                    Utils.getVarBitValue(42) == 0) {
                earthRuneOnDoor.useItemOnObject();
            }
            if (Game.getSetting(HorrorConst.GAME_SETTING) == 30404612 &&
                    Utils.getVarBitValue(45) == 0) {
                arrowOnDoor.useItemOnObject();
            }
            if (Game.getSetting(HorrorConst.GAME_SETTING) == 32501764 &&
                    Utils.getVarBitValue(44) == 0) {
                swordOnDoor.useItemOnObject();
            }
        } else {
            goDownStairs();
        }
    }

    InventoryRequirement fightInv = new InventoryRequirement(new ArrayList<>(
            Arrays.asList(
                    new ItemReq(ItemID.CHAOS_RUNE, 400, 50),
                    new ItemReq(ItemID.LAVA_RUNE, 1200, 300),
                    new ItemReq(ItemID.WATER_RUNE, 1200, 300),
                    new ItemReq(ItemID.STAFF_OF_AIR, 1, 0),
                    new ItemReq(ItemID.LOBSTER, 15, 5),
                   // new ItemReq(ItemID.GAMES_NECKLACE[0], 1, 0, true, true),
                    new ItemReq(ItemID.STAMINA_POTION[0], 1, 0),
                    new ItemReq(ItemID.PRAYER_POTION[0], 3, 0)
            ))
    );

    public static boolean shouldBuyAllBooks = false;

    BankTask bankTaskOne = BankTask.builder()
            .addInvItem(ItemID.CHAOS_RUNE, Amount.of(400))
            .addEquipmentItem(EquipmentReq.slot(org.tribot.script.sdk.Equipment.Slot.BODY).item(ItemID.MONKS_ROBE_TOP, Amount.of(1)))
           .addEquipmentItem(EquipmentReq.slot(org.tribot.script.sdk.Equipment.Slot.LEGS).item(ItemID.MONKS_ROBE_BOTTOM, Amount.of(1)))
            .addEquipmentItem(EquipmentReq.slot(org.tribot.script.sdk.Equipment.Slot.WEAPON).item(
                    ItemID.STAFF_OF_AIR, Amount.of(1)))
            .addEquipmentItem(EquipmentReq.slot(org.tribot.script.sdk.Equipment.Slot.NECK)
                    .chargedItem("Games necklace", 2))
            .addInvItem(ItemID.LAVA_RUNE, Amount.of(1200))
            .addInvItem(ItemID.WATER_RUNE, Amount.of(1200))
            .addInvItem(ItemID.STAMINA_POTION[0], Amount.of(1))
            .addInvItem(ItemID.PRAYER_POTION[0], Amount.of(3))
            .addInvItem(ItemID.LOBSTER, Amount.fill(1))
            .addInvItem(ItemID.COINS, Amount.of(25000))
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
            jossik.setRadius(7);
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
            Prayer.disable(Prayer.PRAYERS.PROTECT_FROM_MISSILES);
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

    public static String message;


    @Override
    public Priority priority() {
        return Priority.HIGH;
    }

    @Override
    public boolean validate() {
        return cQuesterV2.taskList.get(0).equals(HorrorFromTheDeep.get());
    }

    @Override
    public void execute() {
        if (!startInventory.check() && Game.getSetting(HorrorConst.GAME_SETTING) == 0) {
            cQuesterV2.status = "Buying/Getting items";
            buyInitial.buyItems();
            startInventory.withdrawItems();
        } else if (Game.getSetting(HorrorConst.GAME_SETTING) == 0) {
            cQuesterV2.status = "Starting quest";
            General.println("[Debug]: talking to Larrissa");
            larissaStep.execute();
        } else if (Game.getSetting(HorrorConst.GAME_SETTING) == 1) {
            cQuesterV2.status = "Talking to Barbarian";
            barbarianStep.setRadius(5);
            barbarianStep.setUseLocalNav(false);
            barbarianStep.execute();
        } else if (Game.getSetting(HorrorConst.GAME_SETTING) == 16385) {
            // could get items here too
            cQuesterV2.status = "Fixing bridge";

            if (plankOnBridgeOne.useItemOnObject())
                NPCInteraction.handleConversation();
        } else if (Game.getSetting(HorrorConst.GAME_SETTING) == 24577) {
            cQuesterV2.status = "Fixing bridge pt 2";
            if (plankOnBridgeTwo.useItemOnObject())
                NPCInteraction.handleConversation();
        } else if (Game.getSetting(HorrorConst.GAME_SETTING) == 28673) {
            startInventory.remove(0);
            startInventory.remove(1);
            startInventory.add(new ItemReq(HorrorConst.LIGHTHOUSE_KEY, 1, 1));
            unlockDoor();
        }
        if (Game.getSetting(HorrorConst.GAME_SETTING) == 61442) {
            getBooks();
            readAllBooks();
            goUpStairs();

            if (Utils.getVarBitValue(46) == 0)
                useItemOnLight(ItemID.SWAMP_TAR);

            if (Utils.getVarBitValue(48) == 0)
                useItemOnLight(ItemID.TINDERBOX);

            if (Utils.getVarBitValue(47) == 0)
                useItemOnLight(ItemID.MOLTEN_GLASS);
        } else if (Game.getSetting(351) >= 29421572 &&
                Game.getSetting(HorrorConst.GAME_SETTING) <= 32501764) {
            goDownStairs();
            handleBasementDoor();
        } else if (Game.getSetting(HorrorConst.GAME_SETTING) == 33552388) {
            cQuesterV2.status = "Getting fight items";
            if (!bankTaskOne.isSatisfied()) {
                General.println("[Debug]: Executing bankTaskOne");
                BankManager.open(true);
                BankManager.depositEquipment();
                handleBankError();
            } else if (Game.getSetting(HorrorConst.GAME_SETTING) == 33552388) {
                goToFinalFight();
                handleFightDagannoth();
            }
        }
        if (Game.getSetting(HorrorConst.GAME_SETTING) == 33552389)
            handleFightDagannothMother();

        if (Game.getSetting(351) == 33552394) {
            getDamagedBook();
            buyBooks();
            cQuesterV2.taskList.remove(this);
        }

    }

    @Override
    public String toString() {
        return "Horror from the deep";
    }

    @Override
    public String questName() {
        return "Horror from the Deep";
    }

    @Override
    public boolean checkRequirements() {
        return true;
    }

}
