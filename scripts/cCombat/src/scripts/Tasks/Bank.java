package scripts.Tasks;

import dax.api_lib.models.RunescapeBank;
import dax.walker_engine.interaction_handling.NPCInteraction;
import org.tribot.api2007.Banking;
import org.tribot.api2007.Interfaces;
import org.tribot.api2007.Player;
import org.tribot.api2007.types.RSInterface;
import org.tribot.api2007.types.RSItem;
import org.tribot.api2007.types.RSTile;
import org.tribot.script.sdk.Inventory;
import org.tribot.script.sdk.Log;
import org.tribot.script.sdk.MyPlayer;
import org.tribot.script.sdk.Prayer;
import scripts.BankManager;
import scripts.Data.Areas;
import scripts.Data.Vars;
import scripts.ItemID;
import scripts.PathingUtil;
import scripts.Utils;

import java.nio.file.Path;

public class Bank implements Task {

    public void bank() {
        BankManager.open(true);
        BankManager.depositAllExcept(ItemID.LOOTING_BAG_22586);
        RSItem[] lootBag = org.tribot.api2007.Inventory.find(ItemID.LOOTING_BAG_22586);
        if (lootBag.length > 0 && lootBag[0].click("View")) {
            Utils.shortSleep();
            RSInterface dep = Interfaces.findWhereAction("Deposit loot");
            if (dep != null && dep.click()) {
                Utils.microSleep();
                RSInterface dis = Interfaces.findWhereAction("Dismiss");
                if (dis != null && dis.click()) {
                    Utils.microSleep();
                }
            }
        }
        BankManager.withdraw(5, true, ItemID.LOBSTER);
        BankManager.withdrawArray(ItemID.BURNING_AMULET, 1);
        BankManager.withdraw(1, true, ItemID.STAMINA_POTION[2]);
        BankManager.close(true);
    }

    public void goToScarabBank(){
        if (Areas.scarabFightAreaSdk.contains(MyPlayer.getPosition())){
            Log.debug("Going to first wall trap");
            if(PathingUtil.localNavigation(new RSTile(3322, 9250, 2)))
                PathingUtil.movementIdle();
            if (Utils.clickObject("Odd markings", "Search", false)) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation("Yes, I'll give it a go.");
                PathingUtil.movementIdle();
            }
        }
    }

    public void scarabsBank() {
        Prayer.disableAll();
        // TODO go to bank
        goToScarabBank();

        BankManager.open(true);
        BankManager.checkEquippedGlory();
        BankManager.depositAll(true);
        BankManager.withdraw(200, true, ItemID.NATURE_RUNE);
        BankManager.withdraw(1000, true, ItemID.FIRE_RUNE);
        BankManager.withdrawArray(ItemID.PRAYER_POTION, 7);
        BankManager.withdrawArray(ItemID.RANGING_POTION, 2);
        BankManager.withdrawArray(ItemID.ANTIDOTE_PLUS_PLUS, 1);
        BankManager.withdraw(6, true, ItemID.SHARK);
        BankManager.withdraw(1, true, ItemID.LIT_CANDLE);
        BankManager.withdraw(2000, true, ItemID.COINS_995);
        BankManager.withdraw(1, true, ItemID.TINDERBOX);
        BankManager.close(true);
    }

    public void undeadDruidBank() {
        Prayer.disableAll();
        BankManager.open(true);
        BankManager.depositAll(true);
        BankManager.withdrawArray(ItemID.SKILLS_NECKLACE, 1);
        BankManager.withdrawArray(ItemID.STAMINA_POTION, 1);
        BankManager.withdrawArray(ItemID.RANGING_POTION, 3);
        BankManager.withdraw(6, true, ItemID.SHARK);
        BankManager.withdraw(1, true, ItemID.KNIFE);
        BankManager.withdraw(5, true, ItemID.VARROCK_TELEPORT);
        BankManager.close(true);
    }

    @Override
    public Priority priority() {
        return Priority.HIGHEST;
    }

    @Override
    public boolean validate() {
        return Inventory.isFull() || !Inventory.contains(ItemID.SHARK);
    }

    @Override
    public void execute() {
        Prayer.disableAll();
        Prayer.disableAll(Prayer.EAGLE_EYE, Prayer.PROTECT_FROM_MAGIC);
        Log.log("[Bank]: Banking");
        if (Areas.WHOLE_WILDERNESS.contains(Player.getPosition())) {
            PathingUtil.walkToArea(Areas.lavaTeleArea);
            PathingUtil.walkToTile(RunescapeBank.EDGEVILLE.getPosition());
        }
        if (Vars.get().killingUndeadDruids) {
            undeadDruidBank();
        }  else if (Vars.get().killingScarabs)
            scarabsBank();
        else {
            bank();
        }
    }
}
