package scripts.QuestPackages.ShieldOfArrav;

import dax.walker_engine.interaction_handling.NPCInteraction;
import org.tribot.api.General;
import org.tribot.api2007.*;
import org.tribot.api2007.ext.Filters;
import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSItem;
import org.tribot.api2007.types.RSObject;
import org.tribot.api2007.types.RSTile;
import scripts.*;
import scripts.QuestSteps.*;
import scripts.Requirements.InventoryRequirement;
import scripts.Requirements.ItemReq;
import scripts.Tasks.Priority;
import scripts.Tasks.Task;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;


public class PheonixGang implements QuestTask, QuestInterface {

    int RELDO = 6203;
    int BARAEK = 2881;
    int STRAVEN = 5212;
    int INTEL_REPORT = 761;
    int JONNY_THE_BEARD = 5213;
    int CURATOR_HAIG_HALEN = 5214;

    RSArea STRAVEN_AREA = new RSArea(new RSTile(3247, 9781, 0), 10);


    NPCStep startQuest = new NPCStep(RELDO, new RSTile(3210, 3494, 0));

    ObjectStep searchBookcase = new ObjectStep(2402,
            new RSTile(3212, 3494, 0),
            "Search",
            Inventory.find(ItemId.BOOK).length > 0);


    Predicate<RSObject> predicate = Filters.Objects
            .tileEquals(new RSTile(3244, 3383, 0));

    ObjectStep goDownToPhoenixGang = new ObjectStep(predicate,
            new RSTile(3243, 3383, 0),
            Player.getAnimation() != -1,
            "Climb-down", false);
    NPCStep talkToReldoAgain = new NPCStep(RELDO, new RSTile(3210, 3494, 0));
    NPCStep talkToBaraek = new NPCStep(BARAEK, new RSTile(3218, 3435, 0));
    NPCStep talkToHaig = new NPCStep(CURATOR_HAIG_HALEN, new RSTile(3255, 3449, 0));


    ObjectStep getShieldHalf = new ObjectStep(2403, new RSTile(3235, 9762, 0),
            "Open", Objects.findNearest(3, 2404).length > 0);
    ObjectStep getShieldHalf1 = new ObjectStep(2404, new RSTile(3235, 9762, 0),
            Inventory.find(ItemId.BROKEN_SHIELD).length > 0, "Search", true);


    InventoryRequirement startInventory = new InventoryRequirement(new ArrayList<>(
            Arrays.asList(
                    new ItemReq(ItemId.VARROCK_TELEPORT, 5, 0),
                    new ItemReq(ItemId.COINS, 500, 200),
                    new ItemReq(ItemId.STAMINA_POTION[0], 1, 0)
            )
    ));

    public void getShield() {
        if (Inventory.find(ItemId.BROKEN_SHIELD).length == 0
         && Inventory.find(ItemId.HALF_CERTIFICATE).length == 0) {
            General.println("[Debug]: Getting shield");
            getShieldHalf.setUseLocalNav(true);
            getShieldHalf1.setUseLocalNav(true);
            getShieldHalf.execute();
            General.println("[Debug]: Searching shield");
            getShieldHalf1.execute();
        }
    }

    public void handleBookCase() {
        searchBookcase.execute();
        RSObject[] bookcase = Objects.findNearest(10, 2402);
        if (bookcase.length > 0 && Utils.clickObject(bookcase[0], "Search", true)){
            NPCInteraction.isConversationWindowUp();
            NPCInteraction.handleConversation();
        }
        RSItem[] book = Inventory.find(ItemId.BOOK);
        if (book.length > 0 && book[0].click("Read")) {
            Timer.waitCondition(() -> Interfaces.isInterfaceSubstantiated(397, 7), 5000, 8000);
        }
        InterfaceUtil.clickInterfaceAction(397, "Close");
    }

    public void talkToBaraek() {
        talkToBaraek.addDialogStep("Can you tell me where I can find the Phoenix Gang?");
        talkToBaraek.addDialogStep("Okay. Have 20 gold coins.");
        talkToBaraek.execute();
    }

    public void talkToStraven() {
        message = "Talking to Straven";
        if (!STRAVEN_AREA.contains(Player.getPosition())) {
            goDownToPhoenixGang.setTileRadius(2);
            goDownToPhoenixGang.execute();
            Timer.waitCondition(() -> STRAVEN_AREA.contains(Player.getPosition()), 8000, 9000);

        }
        NPCStep talkToStraven = new NPCStep(STRAVEN, new RSTile(3247, 9781, 0));
        talkToStraven.addDialogStep("I know who you are!");
        talkToStraven.addDialogStep("I'd like to offer you my services.");
        talkToStraven.setUseLocalNav(true);
        talkToStraven.execute();
    }

    public void killJonny() {
        message = "Kiing Jonny";
        if (Inventory.find(INTEL_REPORT).length == 0) {
            General.println("[Debug]: Going to kill Jonny");
            if (STRAVEN_AREA.contains(Player.getPosition()) &&
                    Utils.clickObject(Filters.Objects.actionsContains("Climb-up"), "Climb-up")) {
                Timer.waitCondition(() -> !STRAVEN_AREA.contains(Player.getPosition()), 8000, 9000);
            }

            PathingUtil.walkToTile(new RSTile(3222, 3395, 0), 3, true);
            Utils.clickNPC(JONNY_THE_BEARD, "Attack"); // won't return true
            Timer.waitCondition(Combat::isUnderAttack, 5000, 7000);

            if (Combat.isUnderAttack()) {
                int eatAt = General.random(30, 50);
                CombatUtil.waitUntilOutOfCombat(eatAt);
            }
            Utils.pickupItem(INTEL_REPORT);
        }
    }

    public void talkToStravenAgain() {
        message = "Talking to Straven again";
        if (Inventory.find(INTEL_REPORT).length > 0) {
            if (!STRAVEN_AREA.contains(Player.getPosition())) {
                goDownToPhoenixGang.setTileRadius(1);
               goDownToPhoenixGang.execute();
                Timer.waitCondition(() -> STRAVEN_AREA.contains(Player.getPosition()), 8000, 9000);

            }
            NPCStep talkToStraven = new NPCStep(STRAVEN, new RSTile(3247, 9781, 0));
            talkToStraven.setRadius(10);
            talkToStraven.execute();
        }
    }

    public void talkToHaigen(){
        if (Inventory.find(ItemId.BROKEN_SHIELD).length ==1) {
            General.println("[Debug]: Going to currator");
            talkToHaig.execute();
        }
    }

    public void getFullCert(){
        if ( Inventory.find(ItemId.HALF_CERTIFICATE).length == 1) {

        }
        if ( Inventory.find(ItemId.HALF_CERTIFICATE).length == 1
        && Inventory.find(ItemId.HALF_CERTIFICATE_11174).length == 1) {

            //combine
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation("Yes.");
        }
    }


    String message = "";

    @Override
    public String toString(){
        return message;
    }

    @Override
    public Priority priority() {
        return Priority.HIGH;
    }

    @Override
    public boolean validate() {
        return true;
    }

    @Override
    public void execute() {
        if (!startInventory.check())
            startInventory.withdrawItems();

        if (Game.getSetting(145) == 0) {
            General.println("[Debug]: Going to Reldo");
            startQuest.addDialogStep("I'm in search of a quest.");
            startQuest.execute();
        } else if (Game.getSetting(145) == 1) {
            handleBookCase();
        } else if (Game.getSetting(145) == 2) {
            General.println("[Debug]: Going to Reldo");
            talkToReldoAgain.execute();
        } else if (Game.getSetting(145) == 3) {
            // need 20 coins
            General.println("[Debug]: Going to Baraek");
            talkToBaraek();
        } else if (Game.getSetting(145) == 4) {
            General.println("[Debug]: Going to Hideout");
            talkToStraven();
        } else if (Game.getSetting(145) == 5) {
            killJonny();
            talkToStravenAgain();
        } else if (Game.getSetting(145) == 6) {
            getShield();
            //trade over key
            talkToHaigen();
            getFullCert();
        }
        else if (Game.getSetting(145) == 7) {
       //done
        }
    }

    @Override
    public String questName() {
        return "Shield of Arrav (" + Game.getSetting(145) + ")";
    }

    @Override
    public boolean checkRequirements() {
        return true;
    }

}
