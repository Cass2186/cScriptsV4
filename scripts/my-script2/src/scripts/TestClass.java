package scripts;

import com.google.errorprone.annotations.Var;
import dax.walker.utils.camera.DaxCamera;
import dax.walker_engine.interaction_handling.NPCInteraction;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.tribot.api.General;
import org.tribot.api.input.Mouse;
import org.tribot.api2007.*;
import org.tribot.api2007.types.*;
import org.tribot.script.sdk.*;
import org.tribot.script.sdk.Equipment;
import org.tribot.script.sdk.GameTab;
import org.tribot.script.sdk.Inventory;
import org.tribot.script.sdk.Login;
import org.tribot.script.sdk.Options;
import org.tribot.script.sdk.cache.BankCache;
import org.tribot.script.sdk.interfaces.Item;
import org.tribot.script.sdk.interfaces.Stackable;
import org.tribot.script.sdk.query.Query;
import org.tribot.script.sdk.types.GameObject;
import org.tribot.script.sdk.types.InventoryItem;
import org.tribot.script.sdk.types.Npc;
import org.tribot.script.sdk.types.Widget;
import org.tribot.script.sdk.walking.LocalWalking;
import scripts.EntitySelector.Entities;
import scripts.EntitySelector.finders.prefabs.NpcEntity;
import scripts.EntitySelector.finders.prefabs.ObjectEntity;
import scripts.QuestSteps.ObjectStep;
import scripts.QuestSteps.QuestTask;
import scripts.Requirements.ItemReq;
import scripts.Requirements.ItemRequirement;
import scripts.Requirements.Requirement;
import scripts.Requirements.VarplayerRequirement;
import scripts.Tasks.Priority;
import scripts.rsitem_services.GrandExchange;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class TestClass implements QuestTask {
    private static TestClass quest;

    public static TestClass get() {
        return quest == null ? quest = new TestClass() : quest;
    }

    RSArea herbRoom = new RSArea(
            new RSTile[]{
                    new RSTile(2865, 10086, 0),
                    new RSTile(2865, 10074, 0),
                    new RSTile(2850, 10074, 0),
                    new RSTile(2850, 10093, 0),
                    new RSTile(2862, 10093, 0),
                    new RSTile(2862, 10092, 0),
                    new RSTile(2862, 10087, 0)
            }
    );

    RSArea justOutsideHerbRoom = new RSArea(
            new RSTile[]{
                    new RSTile(2860, 10093, 0),
                    new RSTile(2860, 10091, 0),
                    new RSTile(2863, 10091, 0),
                    new RSTile(2863, 10090, 0),
                    new RSTile(2865, 10088, 0),
                    new RSTile(2867, 10088, 0),
                    new RSTile(2869, 10085, 0),
                    new RSTile(2870, 10085, 0),
                    new RSTile(2870, 10091, 0),
                    new RSTile(2866, 10091, 0),
                    new RSTile(2865, 10093, 0)
            }
    );

    RSArea doorArea = new RSArea(new RSTile(2861, 10092, 0), 2);

    int closedDoor = 3776;
    int openDoor = 3777;


    public void getGoutWeed() {
        if (justOutsideHerbRoom.contains(Player.getPosition())) {
            RSObject openDoor = Entities.find(ObjectEntity::new)
                    .inArea(doorArea)
                    .idEquals(get().openDoor)
                    .getFirstResult();

            if (openDoor != null) {
                RSObject goutWeedCrate = Entities.find(ObjectEntity::new)
                        .nameContains("Goutweed Crate")
                        .getFirstResult();
                if (goutWeedCrate != null) {
                    Log.log("[Debug]: Getting gout weed");
                    if (!goutWeedCrate.isClickable())
                        DaxCamera.focus(goutWeedCrate);
                    if (!Game.isRunOn())
                        Options.setRunEnabled(true);
                    //TODO check health and prayer
                    if (goutWeedCrate.click("Search")) {
                        Timer.waitCondition(() -> herbRoom.contains(Player.getPosition()), 3500, 4000);
                        if (Timer.waitCondition(() -> justOutsideHerbRoom.contains(Player.getPosition()), 6500,
                                9000))
                            Waiting.waitNormal(3000, 350);

                    }
                }
            } else {
                Log.log("[Debug]: Opening door");
                RSObject closedDoor = Entities.find(ObjectEntity::new)
                        .inArea(doorArea)
                        .idEquals(get().closedDoor)
                        .getFirstResult();
                if (closedDoor != null && Utils.clickObject(closedDoor, "Open")) {
                    Timer.waitCondition(() -> Entities.find(ObjectEntity::new)
                            .inArea(doorArea)
                            .idEquals(get().openDoor)
                            .getFirstResult() != null, 4000, 6000);
                }

            }
        }
    }

    int currentMagicXP = Skills.getXP(Skills.SKILLS.MAGIC);
    int gainedMagicLvl;
    int gainedMagicXP;

    public static Timer timeoutTimer = new Timer(General.random(300000, 480000)); //5-7 min

    public static void clickAlch() {
        RSInterfaceChild alchButton = Interfaces.get(218, 40);
        if (alchButton != null)
            alchButton.click();
    }

    public void autoClick() {
        if (!GameTab.MAGIC.isOpen())
            GameTab.MAGIC.open();


        currentMagicXP = Skills.getXP(Skills.SKILLS.MAGIC);
        while (timeoutTimer.isRunning()) {
            currentMagicXP = Skills.getXP(Skills.SKILLS.MAGIC);
            RSInterface alchButton = Interfaces.get(218, 40);

            if (!Inventory.contains(561)) {
                General.println("[Debug]: Error - out of nature runes");
                cQuesterV2.taskList.remove(this);
                break;
            }

            if (!timeoutTimer.isRunning()) {
                General.println("Script Timed Out");
                cQuesterV2.taskList.remove(this);
                break;
            }

            if (!Login.isLoggedIn()) {
                cQuesterV2.taskList.remove(this);
                break;
            }
            if (alchButton != null && !alchButton.getAbsoluteBounds().contains(Mouse.getPos())) {
                clickAlch();


            } else {
                General.sleep(400);
                Mouse.click(1);
            }
            if (Skills.getXP(Skills.SKILLS.MAGIC) > currentMagicXP) {
                //    General.println("[Debug]: resetting XP");
                currentMagicXP = Skills.getXP(Skills.SKILLS.MAGIC);
                timeoutTimer.reset();
            }
        }
    }


    private static void withdrawInvItems(List<ItemReq> invReqs) {
        // List<Pair<Integer, Integer>> withdrew = new ArrayList<>();
        HashMap<Integer, Integer> withdrew = new HashMap();
        invReqs.stream()
                //.map(i-> new Pair(i.getId(), (i.getAmount()- Inventory.getCount(i.getId()))))
                .filter(i -> i.getAmount() > 0)
                //  .sortedBy(it.second.endInclusive)
                .forEach(item -> {
                    int id = item.getId();
                    boolean isNoted = item.isItemNoted();
                    int amt = item.getAmount();
                    int startInvCount = Inventory.getCount(id);
                    int bankCount = BankCache.getStack(id);

                    // Check if we have the amount we need in the bank. If not, bind an error
                    if (bankCount < amt) {
                        Log.log("[Bank]: Insufficient item in bank");
                    }

                    if (bankCount < amt) {
                        // Special case: If we don't need any of this item and there is none in the bank, skip
                        //  if (bankCount == 0 && amt == 0)
                        //     return; //@forEach


                        if (BankSettings.isNoteEnabled() != isNoted) {
                            BankSettings.setNoteEnabled(isNoted);
                            Waiting.waitNormal(85, 15);
                        }

                        Bank.withdrawAll(id);
                    } else {
                        if (BankSettings.isNoteEnabled() != isNoted) {
                            BankSettings.setNoteEnabled(isNoted);
                            Waiting.waitNormal(85, 15);
                        }
                        amt = (amt - startInvCount);
                        //prevents attempting to withdraw if we have enough
                        if (amt > 0)
                            Bank.withdraw(id, amt - startInvCount);
                    }

                    withdrew.put(id, startInvCount);
                    Waiting.waitNormal(69, 16);
                });
        // Wait and confirm all inv items were withdrawn correctly
        for (Integer i : withdrew.keySet()) {
            Waiting.waitUntil(2500, () ->
                    Inventory.getCount(i) > withdrew.get(i));
        }
    }


    private static void withdrawAndEquipItems(List<ItemReq> equipReqs) {
        //filter our list to items that are supposed to be equipped but fail the check (i.e. aren't equipped or enough)
        List<ItemReq> equipmentToWithdrawAndEquip = equipReqs.stream().filter(req ->
                req.isShouldEquip() && !req.check()).collect(Collectors.toList());

        var currentInvItems = Inventory.getAll();

        if (currentInvItems.size() == 28 && equipmentToWithdrawAndEquip.size() > 0) {
            // Oh no, our inv is full of stuff we need but we need to equip things
            Bank.deposit(currentInvItems.get(currentInvItems.size() - 1).getId(), 1); // Make room
            Waiting.waitUntil(2000, () -> Inventory.getAll().size() < 28);
            Waiting.waitNormal(345, 67);
            currentInvItems = Inventory.getAll(); // reset inv to reflect current state
        }

        // We will equip items using the amount of free inv space we have.
        // So if we have 1 space, we will withdraw 1 item, equip, repeat.
        // If we have more spaces than equipment to wear, then we withdraw everything up front
        int invFreeSpaces = 28 - currentInvItems.size();
        int i = 0;
        while (i < equipmentToWithdrawAndEquip.size()) {
            Waiting.wait(25);
            List<Integer> skippedIndices = new ArrayList<>();

            int i2 = i;
            while (i2 < equipmentToWithdrawAndEquip.size() && (i2 - i - skippedIndices.size()) < invFreeSpaces) {
                Waiting.wait(25);
                int id = equipmentToWithdrawAndEquip.get(i2).getId();
                int amt = equipmentToWithdrawAndEquip.get(i2).getAmount();
                // val slot = equipReqs[i2].slot
                Optional<Item> bankOptionalItem = Bank.getAll()
                        .stream().filter(item -> item.getId() == id)
                        .findFirst();
                int bankCount = bankOptionalItem.map(Stackable::getStack).orElse(0);

                // Check if we have the amount we need in the bank. If not, bind an error
                if (bankCount < amt) {
                    Log.log("[Banking]: We have insufficient item: " + id + " | Need: " + amt);
                    //InsufficientEquipmentError(slot, id, amt.start, bankCount).toEither < Unit > ().bind()
                }

                // If we have nothing in the bank, it's okay to just continue at this point because
                // what we have equipped is sufficient
                if (bankCount != 0) {

                    if (BankSettings.isNoteEnabled()) {
                        BankSettings.setNoteEnabled(false);
                        Waiting.waitNormal(85, 15);
                    }

                    if (bankCount <= amt) {
                        Bank.withdrawAll(id);
                    } else {
                        Bank.withdraw(id, amt);
                    }

                    Waiting.waitNormal(67, 13);
                } else {
                    skippedIndices.add(i2);
                }

                i2++;
            }

            // Wait for everything to actually appear in the inventory and then equip
            for (int x = 0; x < i2; x++) {
                // If we skipped this item, we don't need to equip it
                if (skippedIndices.contains(x)) {
                    continue;
                }

                int id = equipmentToWithdrawAndEquip.get(x).getId();
                Waiting.waitUntil(2500, () -> Inventory.getCount(id) > 0);

                InventoryItem item = Query.inventory().idEquals(id).findFirst().orElse(null);
                if (item != null) {
                    if (Equipment.equip(item))
                        Waiting.waitNormal(121, 30);
                } else {
                    //UnknownError.toEither<Unit> ().bind()
                }
            }

            // Confirm everything is equipped
            for (int x = 0; x < i2; x++) {
                // If we skipped this item, we don't need to wait for it to be equipped
                if (skippedIndices.contains(x)) {
                    continue;
                }
                int y = x;
                Waiting.waitUntil(2500, () ->
                        Equipment.contains(equipmentToWithdrawAndEquip.get(y).getId()));
            }
            i = i2;
        }
    }

  /*  private void depositInventory( List<ItemReq> invReqs, List<EquipmentReq>  equipReqs) {
        // First get all the maximum withdraws. The amount will be negative if we need to deposit
        List<ItemReq> withdrawsToMake = invReqs.stream().map(it ->
                new ItemReq(it.getId(), (it.getAmount() - Inventory.getCount(it.getId()))))
                .collect(Collectors.toList());

        val equipmentWithdrawsToMake = equipReqs
                .filter { !it.isSatisfied() }
            .mapNotNull { it.item }
            .map {
            Pair(it.first, it.second.getWithdrawAmount(Inventory.getCount(it.first)).endInclusive)
        }

        // Let's calculate all the completely unrelated items that we need to deposit all of
        val unrelatedItemDepositsToMake = Inventory.getAll()
                .distinctBy { it.id }
            .filter { invItem -> withdrawsToMake.none { it.first == invItem.id } }
            .map { Pair(it.id, Inventory.getCount(it.id)) }

        // And combine them with the "withdraws" that are negative (aka deposits)
        val combined = withdrawsToMake.filter { it.second < 0 } +
                equipmentWithdrawsToMake.filter { it.second < 0 } +
                unrelatedItemDepositsToMake;

        if (withdrawsToMake.any { it.second <= 0 }) {
            // We must have something in our inv that we need if we have a negative or 0 withdraw
            val currentInvState = combined.map { Inventory.getCount(it.first) }

            // Iterate and execute on the deposits
            for (entry in combined) {
                Bank.deposit(entry.first, abs(entry.second)).bind()
                Waiting.waitNormal(192, 29)
            }

            // Wait for the deposits to register
            combined.forEachIndexed { i, entry ->
                    val expectedNewAmt = currentInvState[i] - abs(entry.second)
                Waiting.waitUntil(2500) { Inventory.getCount(entry.first) == expectedNewAmt }.bind()
            }
        }
        else if (Inventory.getAll().isNotEmpty()) {
            // We have nothing we need, so deposit all if inv contains anything
            Bank.depositInventory().bind()
            Waiting.waitUntil(2500) { Inventory.getAll().size == 0 }.bind()
            Waiting.waitNormal(254, 38)
        }
    }*/


    public static void doBank(List<ItemReq> itemReqList) {
        if (itemReqList.stream().anyMatch(i -> !i.check())) {
            if (!Bank.isOpen()) {
                Bank.open();
                Waiting.waitNormal(375, 45);
            }
            Bank.depositInventory();

            // Deposit everything that needs deposited
            //depositInventory(invReqs, equipReqs);
            //  depositEquipment(equipReqs);

            withdrawAndEquipItems(itemReqList);

            withdrawInvItems(itemReqList);

            Bank.close();
        }
    }

    public void feedCat() {
        RSNPC npcCat = getFollowerRSNPC();
        RSItem[] salmon = org.tribot.api2007.Inventory.find(ItemID.SALMON);
        int lng = salmon.length;
        if (salmon.length > 0 && npcCat != null) {
            cQuesterV2.status = "Feeding Cat";
            General.println("[Debug]: Feeding Cat", Color.red);
            for (int i = 0; i < 3; i++) {
                if (Utils.useItemOnNPC(ItemID.SALMON, npcCat)) {
                    if (Timer.waitCondition(() -> org.tribot.api2007.Inventory.find(ItemID.SALMON).length < lng, 2500, 3500)) {
                        feedTimer.reset();
                        break;
                    }
                    General.println("[Debug]: Seemingly failed to feed cat, looping (i = " + i + ")");
                }
                General.sleep(300, 900);
            }

        } else {
            cQuesterV2.taskList.remove(this);
            General.println("[Debug]: Should be feeding cat, but we are out of food or don't have a pet.");
        }
    }

    Timer feedTimer = new Timer(General.random(900000, 1000000));
    Timer interactTimer = new Timer(General.random(420000, 720000)); //7-12 min
    Timer logoutTimer = new Timer(General.random(240000, 300000));

    public void checkFeeding() {
        if (!feedTimer.isRunning()) {
            feedCat();
        }
    }

    public RSNPC getFollowerRSNPC() {
        RSNPC[] kitten = Entities.find(NpcEntity::new)
                .actionsContains("Chase")
                .actionsContains("Interact")
                .sortByDistance()
                .getResults();

        if (kitten != null && kitten.length > 0) {
            return kitten[0];
        }

        return null;
    }

    public void interactWithCat() {
        RSNPC myKitten = getFollowerRSNPC();
        if (myKitten != null) {
            for (int i = 0; i < 2; i++) {
                if (Utils.clickNPC(myKitten, "Interact", false)) {
                    NPCInteraction.waitForConversationWindow();
                    NPCInteraction.handleConversation("Stroke");
                    NPCInteraction.waitForConversationWindow();
                    interactTimer.reset();
                }
            }
        }
    }

    int stunnedAnimation = 424;

    public boolean clickFarmer() {
        if (MyPlayer.getCurrentHealthPercent() < General.random(30, 40)) {
            EatUtil.eatFood();
        }
        Optional<Npc> master = Query.npcs().nameContains("Master Farmer").findBestInteractable();
        return master.map(f -> f.interact("Pickpocket")).orElse(false);
    }

    public void pickpocket() {
        if (Inventory.isFull()) {
            Inventory.drop(ItemID.SWEETCORN_SEED, 5098, 5282,
                    5103, 5324, 5280, 5307, 5102, 5306, 5096, 5308, 5318, 5310, 5099, 5319, 5100, 5291
                    , 5309);

        } else if (clickFarmer()) {
            Timer.waitCondition(() -> Player.getAnimation() != -1, 3500, 5000);
            if (Player.getAnimation() == stunnedAnimation) {
                Waiting.waitNormal(2800, 150);
            }
        }
    }

    public void checkInteraction() {
        if (!interactTimer.isRunning()) {
            cQuesterV2.status = "Interacting with Cat";
            General.println("[Debug]: Stroking Cat", Color.red);
            interactWithCat();
        }
    }

    /**
     * @return
     */

    int STUDYING_ANIMATION = 9491;
    int ANIMATION_ID = 4068;
    int PARENT_INTERFACE_ID = 79;
    int NOTED_SOFT_CLAY = 1762;
    int SOFT_CLAY = 1761;


    int ADVERTISEMENT_ID = 29091;
    RSTile OUTSIDE_RIMMINGTON_PORTAL = new RSTile(2953, 3219, 0);
    ObjectStep clickAdvertisementStep = new ObjectStep(ADVERTISEMENT_ID,
            "View", OUTSIDE_RIMMINGTON_PORTAL,
            Interfaces.isInterfaceSubstantiated(52));

    public boolean clickAdvertisement() {
        if (!Interfaces.isInterfaceSubstantiated(52)) {
            Log.debug("[Debug]: Opening host advertisements");
            if (OUTSIDE_RIMMINGTON_PORTAL.distanceTo(Player.getPosition()) > 20)
                PathingUtil.walkToTile(OUTSIDE_RIMMINGTON_PORTAL, 2, false);


            Optional<GameObject> ad =
                    Query.gameObjects().idEquals(ADVERTISEMENT_ID).stream().findFirst();
            if (ad.isPresent() && !ad.get().isVisible()) {

                LocalWalking.walkTo(ad.get().getTile().translate(General.random(0, 1), General.random(0, 1)));
                if (Timer.waitCondition(() -> Player.isMoving(), 1500, 2000))
                    Waiting.waitNormal(2050, 420);
            }
            if (Utils.clickObject(ADVERTISEMENT_ID, "View", true)) {
                Timer.slowWaitCondition(() -> Interfaces.get(52) != null, 5000, 7000);
            }
        }
        return Interfaces.get(52) != null;
    }

    List<Widget> houseBlackList = new ArrayList<>();
    List<Widget> goodHouses = new ArrayList<>();

    public void selectHost() {
        if (!Game.isInInstance() && Inventory.contains(ItemID.SOFT_CLAY) && clickAdvertisement()) {
            List<Widget> button = Query.widgets().actionContains("Enter House")
                    .isVisible()
                    .stream()
                    .filter(wid -> !houseBlackList.contains(wid))
                    .collect(Collectors.toList());
            cQuesterV2.status = "Selecting Host";
            for (Widget w : button) {
                Log.log("[Debug]: Entering host");
                if (w.click("Enter House") && Timer.waitCondition(Game::isInInstance, 3000, 4500)) {
                    Waiting.waitNormal(2500, 50);
                    return;
                } else {
                    //  Log.log("[Debug]: Blacklisting failed host");
                    //houseBlackList.add(w);
                    clickAdvertisement();

                }
            }
        }
    }

    public boolean atLecturn() {
        return org.tribot.api2007.Objects.findNearest(30, "Lectern").length > 0;
    }

    public void studyLecturn(String teleport) {
        if (Inventory.contains(ItemID.LAW_RUNE) && Inventory.contains(SOFT_CLAY) && atLecturn()) {
            cQuesterV2.status = "Studying Lecturn";
            if (Player.getAnimation() == ANIMATION_ID) {
                Log.debug("Making Tabs");
                Timer.slowWaitCondition(() -> !Inventory.contains(SOFT_CLAY) , 60000, 69000);
                profit = Utils.getInventoryValue() - startInvValue;
                General.println("[Debug]: Current profit is " + profit);
                return;
            }
            Optional<GameObject> lectern = Query.gameObjects().nameContains("Lectern")
                    .findBestInteractable();

            if (lectern.isPresent()) {
                Log.debug("Going to Lectern");

                if (!lectern.get().isVisible() || lectern.get().getTile().distanceTo(MyPlayer.getPosition()) > 10) {
                    Log.debug("Walking closer to Lectern");
                    if (lectern.map(l -> LocalWalking.walkTo(l.getTile())).orElse(false))
                        Timer.waitCondition(() -> lectern.get().isVisible(), 6000, 9000);
                }

                if (!Interfaces.isInterfaceSubstantiated(PARENT_INTERFACE_ID) && Utils.clickObj(lectern, "Study"))
                    Timer.slowWaitCondition(() -> Interfaces.isInterfaceSubstantiated(PARENT_INTERFACE_ID), 8000, 12000);


                Optional<Widget> tabWidget = Query.widgets()
                        .inIndexPath(PARENT_INTERFACE_ID)
                        .nameContains(teleport + " Teleport")
                        .isVisible()
                        .findFirst();

                if (tabWidget.map(w -> w.click()).orElse(false))
                    Timer.waitCondition(() -> Player.getAnimation() == ANIMATION_ID, 5000, 7000);

            }
        }
    }

    public void leaveHouse() {
        if (Game.isInInstance() && Utils.clickObject(4525, "Enter", false)) {
            Timer.waitCondition(() -> !Game.isInInstance() &&
                            Query.npcs().nameContains("Phials").stream().findFirst().isPresent(),
                    7000, 9000);
        }
    }

    public void unNoteClay() {
        if (!Inventory.contains(ItemID.SOFT_CLAY)) {
            if (!atLecturn()) {
                cQuesterV2.status = "Unnoting clay";
                Optional<InventoryItem> item = Query.inventory()
                        .nameContains("Soft clay")
                        .isNoted().findFirst();
                Optional<Npc> phials = Query.npcs()
                        .nameContains("Phials")
                        .stream().findFirst();
                if (phials.isPresent() && item.isPresent()) {
                    if (!phials.get().isVisible()) {
                        LocalWalking.walkTo(phials.get().getTile().translate(0, 1));
                        Waiting.waitNormal(750, 220);
                    }
                    if (Utils.useItemOnNPC(item.get().getId(), phials.get().getId()) &&
                            Timer.waitCondition(Player::isMoving, 1200, 1800)) {
                        Timer.waitCondition(NPCInteraction::isConversationWindowUp, 6000, 8000);

                    }
                }
                if (NPCInteraction.isConversationWindowUp()) {
                    if (InterfaceUtil.clickInterfaceText(219, 1, "Exchange All"))
                        Timer.waitCondition(() -> Inventory.contains(ItemID.SOFT_CLAY),
                                2000, 4000);
                    else if (InterfaceUtil.clickInterfaceText(219, 1, "Exchange 5"))
                        Timer.waitCondition(() -> Inventory.contains(ItemID.SOFT_CLAY),
                                2000, 4000);

                    if (NPCInteraction.isConversationWindowUp())
                        ChatScreen.handle();
                }
            } else {

                leaveHouse();
            }
        }

    }

    @AllArgsConstructor
    public enum Tabs {
        VARROCK(25, ItemID.FIRE_RUNE, 25,"Varrock", ItemID.VARROCK_TELEPORT),
        LUMBRIDGE(31, ItemID.EARTH_RUNE, 37,"Lumbridge", ItemID.LUMBRIDGE_TELEPORT),
        HOUSE(40, ItemID.EARTH_RUNE, 30,"House", ItemID.TELEPORT_TO_HOUSE),
        FALADOR(37, ItemID.WATER_RUNE, 48,"Falador", ItemID.FALADOR_TELEPORT),
        CAMELOT(45, -1, 55,"Camelot", ItemID.CAMELOT_TELEPORT);

        private int levelReq;
        private int otherRuneId;
        private int xpGained;
        @Getter
        private String name;
        @Getter
        private int tabID;

    }


    public Tabs getMostProfitableTab(){
        int profit = 0;
        Tabs tab = Tabs.VARROCK;
        for (Tabs t: Tabs.values()){
            if (profitPerTab(t) >= profit) {
                profit = profitPerTab(t);
                tab = t;
            }
        }
        Log.debug("Best tab is " + tab.getName());
        return tab;
    }
    public boolean hasAnyClay() {
        return Inventory.contains(SOFT_CLAY) || Inventory.contains(NOTED_SOFT_CLAY);
    }


    private int profitPerTab(Tabs tab) {
        int lawPrice = GrandExchange.getPrice(ItemID.LAW_RUNE);
        int tabPrice = GrandExchange.getPrice(tab.getTabID());
        int profit = tabPrice - (lawPrice + 5);
        Log.debug("Profit per tab " + tab.getName() + " is " + profit + "gp");
        return profit;
    }

    public static int startInvValue = Utils.getInventoryValue();
    public static int profit = 0;


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
        //getGoutWeed();
        // autoClick();
       // profitPerTab(Tabs.CAMELOT);
       /* unNoteClay();
        selectHost();
        studyLecturn(getMostProfitableTab().getName());*/
        VarplayerRequirement req = new VarplayerRequirement(1196, false, 2);
        Log.debug("Requirement: " + req.check());

        Waiting.waitNormal(600, 10);
    }

    @Override
    public String questName() {
        return "Testing";
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
