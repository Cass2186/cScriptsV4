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
            BankManager.withdraw(0, true, ItemId.SKILLS_NECKLACE[5]);
            BankManager.withdraw(0, true, ItemId.SKILLS_NECKLACE[4]);
            BankManager.withdraw(0, true, ItemId.SKILLS_NECKLACE[3]);
            BankManager.withdraw(0, true, ItemId.AMULET_OF_GLORY[5]);
            BankManager.withdraw(0, true, ItemId.AMULET_OF_GLORY[4]);
            BankManager.withdraw(0, true, ItemId.AMULET_OF_GLORY[3]);

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
            BankManager.withdraw(0, true, ItemId.PRAYER_POTION[0]);
            BankManager.withdraw(0, true, ItemId.PRAYER_POTION[1]);
            BankManager.withdraw(0, true, ItemId.PRAYER_POTION[2]);
            BankManager.withdraw(0, true, ItemId.SUPER_STRENGTH[0]);
            BankManager.withdraw(0, true, ItemId.SUPER_STRENGTH[1]);
            BankManager.withdraw(0, true, ItemId.SUPER_STRENGTH[2]);

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
            BankManager.withdraw(0, true, ItemId.STAMINA_POTION[0]);
            BankManager.withdraw(0, true, ItemId.STAMINA_POTION[2]);
            BankManager.withdraw(0, true, ItemId.STAMINA_POTION[3]);
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

   private static ArrayList<GEItem> itemsToBuy = new ArrayList<GEItem>(
            Arrays.asList(
                    new GEItem(ItemId.SALVE_GRAVEYARD_TELEPORT, 6, 50),
                    new GEItem(ItemId.CANDLE, 1, 500),
                    new GEItem(ItemId.PRAYER_POTION[0], SlayerVars.get().restockNumber + 5, 15),
                    new GEItem(ItemId.EXPEDITIOUS_BRACELET, 10, 50),
                    new GEItem(ItemId.FALADOR_TELEPORT, SlayerVars.get().restockNumber * 2, 50),
                    new GEItem(ItemId.VARROCK_TELEPORT, SlayerVars.get().restockNumber * 5, 50),
                    new GEItem(ItemId.CAMELOT_TELEPORT, SlayerVars.get().restockNumber * 2, 50),
                    new GEItem(ItemId.ARDOUGNE_TELEPORT, SlayerVars.get().restockNumber * 2, 50),
                    new GEItem(ItemId.LUMBRIDGE_TELEPORT, SlayerVars.get().restockNumber * 2, 50),
                    new GEItem(ItemId.SHANTAY_PASS, 20, 550),
                    new GEItem(ItemId.BRASS_KEY, 1, 250),
                    new GEItem(ItemId.ROPE, 1, 250),
                    new GEItem(ItemId.MIRROR_SHIELD, 1, 250),
                    new GEItem(ItemId.BOOTS_OF_STONE, 1, 250),
                    new GEItem(ItemId.MONKFISH, SlayerVars.get().restockNumber* 15, 25),
                    new GEItem(ItemId.SUPER_COMBAT_POTION[0], SlayerVars.get().restockNumber / 2, 15),
                    new GEItem(ItemId.ANTIDOTE_PLUS_PLUS[0], SlayerVars.get().restockNumber / 4, 50),
                    new GEItem(ItemId.MONKFISH, SlayerVars.get().restockNumber * General.random(30, 40), 50),
                    new GEItem(ItemId.AMULET_OF_GLORY[2],  SlayerVars.get().restockNumber / 3, 20),
                    new GEItem(ItemId.STAMINA_POTION[0], SlayerVars.get().restockNumber / 2, 15),
                    new GEItem(ItemId.RING_OF_DUELING[0], SlayerVars.get().restockNumber / 3, 25),
                    new GEItem(ItemId.GAMES_NECKLACE[0], SlayerVars.get().restockNumber / 3, 25),
                    new GEItem(ItemId.SKILLS_NECKLACE[0], SlayerVars.get().restockNumber / 4, 25),
                    new GEItem(ItemId.RING_OF_WEALTH[0], 1, 25),
                    new GEItem(ItemId.IORWERTH_CAMP_TELEPORT, 5, 30),
                    new GEItem(ItemId.LUNAR_ISLE_TELEPORT, 5, 30)

            )
    );

    public static  ArrayList<GEItem> getRetockList(){

        if (!Equipment.isEquipped(ItemId.SLAYER_HELM) || !Equipment.isEquipped(ItemId.SLAYER_HELM_I)) {
            itemsToBuy.add(new GEItem(ItemId.EAR_MUFFS, 1, 300));
            itemsToBuy.add(new GEItem(ItemId.SPINY_HELM, 1, 300));
        }

        if (SlayerVars.get().use_cannon){

        }
        itemsToBuy.add(new GEItem(ItemId.ROPE, 1, 300));
        itemsToBuy.add(new GEItem(ItemId.BAG_OF_SALT, 250, 30));
        itemsToBuy.add(new GEItem(ItemId.WATERSKIN[0], 15, 300));

        if (Skills.getActualLevel(Skills.SKILLS.SLAYER) >= 55) {
            if (Skills.getActualLevel(Skills.SKILLS.ATTACK) >= 65) {
                itemsToBuy.add(new GEItem(ItemId.LEAFBLADE_BATTLEAXE, 1, 30));
            } else {
                itemsToBuy.add(new GEItem(ItemId.LEAF_BLADED_SWORD, 1, 50));

            }
        }

        return  itemsToBuy;
    }

    public void buyItems() {
        SlayerVars.get().status = "Restocking";
        Log.log("[Debug]: Restocking");
        BuyItemsStep buyStep = new BuyItemsStep(getRetockList());
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
