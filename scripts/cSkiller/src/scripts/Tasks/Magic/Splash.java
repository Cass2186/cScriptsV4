package scripts.Tasks.Magic;

import org.tribot.script.sdk.*;
import org.tribot.script.sdk.cache.BankCache;
import scripts.API.Priority;
import scripts.API.Task;
import scripts.Requirements.ItemReq;

import java.util.HashMap;
import java.util.List;

public class Splash implements Task {

    private void withdrawInvItems(List<ItemReq> invReqs) {
        HashMap<Integer, Integer> withdrew = new HashMap();
        invReqs.stream()
                // .map(Pair(it, it.amount.getWithdrawAmount(Inventory.getCount(it.id))))
                //.filter(it.second.endInclusive > 0)
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

                        Bank.withdraw(id, amt);
                    }

                    withdrew.put(id, startInvCount);
                    Waiting.waitNormal(69, 16);
                });
        // Wait and confirm all inv items were withdrawn correctly
        for (Integer i : withdrew.keySet()){
            Waiting.waitUntil(2500, () ->
                    Inventory.getCount(i) > withdrew.get(i));
        }
    }


    @Override
    public Priority priority() {
        return null;
    }

    @Override
    public boolean validate() {
        return false;
    }

    @Override
    public void execute() {

    }

    @Override
    public String taskName() {
        return null;
    }
}
