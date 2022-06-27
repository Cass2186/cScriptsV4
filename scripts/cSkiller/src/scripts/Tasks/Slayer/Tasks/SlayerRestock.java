package scripts.Tasks.Slayer.Tasks;

import dax.walker.utils.AccurateMouse;
import dax.walker_engine.interaction_handling.NPCInteraction;
import org.tribot.api.General;
import org.tribot.api2007.Equipment;
import org.tribot.api2007.Interfaces;
import org.tribot.api2007.NPCs;
import org.tribot.api2007.Skills;
import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSNPC;
import org.tribot.api2007.types.RSTile;
import org.tribot.script.sdk.Log;
import scripts.*;
import scripts.API.Priority;
import scripts.API.Task;
import scripts.Data.SkillTasks;
import scripts.Data.Vars;
import scripts.GEManager.GEItem;
import scripts.QuestSteps.BuyItemsStep;
import scripts.Tasks.Slayer.SlayerUtils.SlayerUtils;
import scripts.Tasks.Slayer.SlayerUtils.SlayerVars;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Arrays;

public class SlayerRestock implements Task {


    public static RSTile BOB_BARTER_TILE = new RSTile(3156, 3481, 0);
    public static RSTile MURKY_MATT_TILE = new RSTile(3173, 3481, 0);

    public static RSArea bobBarterArea = new RSArea(BOB_BARTER_TILE, 4);
    public static RSArea murkyMattArea = new RSArea(MURKY_MATT_TILE, 4);

    public static RSNPC[] bobBarter = NPCs.find("Bob Barter (herbs)");
    public static RSNPC[] murkyMatt = NPCs.find("Murky Matt (runes)");

    public static final char VK_1 = KeyEvent.VK_1;
    public static final char VK_2 = KeyEvent.VK_2;
    public static final char VK_3 = KeyEvent.VK_3;
    public static final char VK_4 = KeyEvent.VK_4;

    public void decantJewelery() {
        General.println("[Debug]: Decanting jewlery");
        if (BankManager.open(true)) {
            BankManager.turnNotesOn();
            BankManager.depositAll(true);
            BankManager.withdraw(0, true, ItemID.SKILLS_NECKLACE[5]);
            BankManager.withdraw(0, true, ItemID.SKILLS_NECKLACE[4]);
            BankManager.withdraw(0, true, ItemID.SKILLS_NECKLACE[3]);
            BankManager.withdraw(0, true, ItemID.AMULET_OF_GLORY[5]);
            BankManager.withdraw(0, true, ItemID.AMULET_OF_GLORY[4]);
            BankManager.withdraw(0, true, ItemID.AMULET_OF_GLORY[3]);

            BankManager.close(true);
        }

        murkyMatt = NPCs.find("Murky Matt (runes)");

        if (murkyMatt.length > 0 && Utils.clickNPC(murkyMatt[0], "Decant", false)) {
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation();
        }

        BankManager.open(true);
        BankManager.depositAll(true);
    }

    public void decantPotions() {
        General.println("[Debug]: Decanting Super Strength and Prayer Potions to 4 doses.");
        if (BankManager.open(true)) {
            BankManager.turnNotesOn();
            BankManager.depositAll(true);
            BankManager.withdraw(0, true, ItemID.PRAYER_POTION[0]);
            BankManager.withdraw(0, true, ItemID.PRAYER_POTION[1]);
            BankManager.withdraw(0, true, ItemID.PRAYER_POTION[2]);
            BankManager.withdraw(0, true, ItemID.SUPER_STRENGTH[0]);
            BankManager.withdraw(0, true, ItemID.SUPER_STRENGTH[1]);
            BankManager.withdraw(0, true, ItemID.SUPER_STRENGTH[2]);

            BankManager.close(true);
        }

        PathingUtil.walkToArea(bobBarterArea);

        bobBarter = NPCs.find("Bob Barter (herbs)");
        if (bobBarter.length > 0) {

            if (!bobBarter[0].isClickable())
                bobBarter[0].adjustCameraTo();

            if (AccurateMouse.click(bobBarter[0], "Decant")) {
                Timer.waitCondition(() -> Interfaces.isInterfaceSubstantiated(582), 6000, 8000);
                if (Interfaces.isInterfaceSubstantiated(582, 6)) {
                    Interfaces.get(582, 6).click();
                    General.sleep(400, 2500);
                }
            }
        }
        BankManager.open(true);
        BankManager.depositAll(true);
    }

    public void decantStamina() {
        General.println("[Debug]: Decanting Stamina potions to (2) doses");
        if (BankManager.open(true)) {
            BankManager.turnNotesOn();
            BankManager.withdraw(0, true, ItemID.STAMINA_POTION[0]);
            BankManager.withdraw(0, true, ItemID.STAMINA_POTION[2]);
            BankManager.withdraw(0, true, ItemID.STAMINA_POTION[3]);
            BankManager.withdraw(0, 995);
            BankManager.close(true);
        }

        PathingUtil.walkToArea(bobBarterArea);

        bobBarter = NPCs.find("Bob Barter (herbs)");
        if (bobBarter.length > 0) {
            if (!bobBarter[0].isClickable())
                bobBarter[0].adjustCameraTo();

            if (AccurateMouse.click(bobBarter[0], "Decant")) {
                NPCInteraction.waitForConversationWindow();
                Timer.waitCondition(() -> Interfaces.isInterfaceSubstantiated(582), 5000);
                if (Interfaces.isInterfaceSubstantiated(582, 2)) {
                    Interfaces.get(582, 4).click();
                    General.sleep(General.random(1000, 2500));
                }
            }
        }
        if (BankManager.open(true))
            BankManager.depositAll(true);
    }

    public void buyItems() {
        SlayerVars.get().status = "Restocking";
        Log.info("Restocking");
        BuyItemsStep buyStep = new BuyItemsStep(SlayerUtils.getPurchaseList());
        buyStep.buyItems();
        BankManager.open(true);
        BankManager.depositAll(true);
        // BankManager.close(true);
    }


    @Override
    public Priority priority() {
        return Priority.HIGH;
    }

    @Override
    public void execute() {
        SlayerVars.get().status = "Restocking";
     //   decantPotions();
        decantJewelery();
        //   decantStamina();
        SlayerVars.get().status = "Restocking";
        buyItems();
        SlayerVars.get().shouldRestock = false;
        SlayerVars.get().shouldBank = true;
        cSkiller.safetyTimer.reset();
    }

    @Override
    public boolean validate() {
        return Vars.get().currentTask != null &&
                Vars.get().currentTask.equals(SkillTasks.SLAYER) &&
                SlayerVars.get().shouldRestock && !SlayerVars.get().slayerShopRestock;
    }

    @Override
    public String taskName() {
        return "Slayer";
    }

    @Override
    public String toString() {
        return "GE Restocking";
    }
}
