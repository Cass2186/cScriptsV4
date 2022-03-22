package scripts.Tasks.Spidines;

import org.tribot.api.General;
import org.tribot.script.sdk.Combat;
import org.tribot.script.sdk.Inventory;
import org.tribot.script.sdk.Prayer;
import org.tribot.script.sdk.Waiting;
import org.tribot.script.sdk.query.Query;
import org.tribot.script.sdk.types.Npc;
import scripts.*;
import scripts.Data.Areas;
import scripts.Data.Vars;
import scripts.Tasks.LootItems;
import scripts.Tasks.Priority;
import scripts.Tasks.Task;

import java.util.List;
import java.util.Optional;

public class KillSpidine implements Task {

    private Optional<Npc> getSpidine() {
        return Query
                .npcs()
                .nameContains("Spidine")
                .isInteractingWithMe()
                .findBestInteractable();
    }


    private void killSpidine() {

        if (!Combat.isAutoRetaliateOn())
            Combat.setAutoRetaliate(true);

        Optional<Npc> attackingMe = getSpidine();

        if (attackingMe.isPresent()) {

            if (!attackingMe.get().isHealthBarVisible() && attackingMe.get().getHealthBarPercent() != 0 &&
                    attackingMe.map(t -> t.interact("Attack")).orElse(false)) {
                Vars.get().status = "Attacking Target";
                Vars.get().currentTime = System.currentTimeMillis();
                Waiting.waitUntil(4500, () -> attackingMe.get().isHealthBarVisible());
            } else {

            }
            if (Vars.get().drinkPotions)
                Utils.drinkPotion(Vars.get().potionNames);

            if (waitUntilOutOfCombat()) {
                int num = Utils.random(0, 100);
                if (num < 22) {
                    Utils.idleAfkAction();
                } else
                    Utils.idleNormalAction();
            }


        }
    }

    private boolean waitUntilOutOfCombat() {
        int eatAtHP = AntiBan.getEatAt() + General.random(3, 12);

        return Waiting.waitUntil(General.random(40000, 60000), () -> {
            Waiting.waitUniform(150, 450);

            AntiBan.timedActions();

            if (EatUtil.hpPercent() <= (eatAtHP))
                EatUtil.eatFood();

            Optional<Npc> attackingMe = getSpidine();

            return //!MyPlayer.isHealthBarVisible() ||
                    attackingMe.isEmpty() || Inventory.contains(ItemID.CANDLE) ||
                            !EatUtil.hasFood() || LootItems.getLootItem().isPresent();
        });
    }

    @Override
    public String toString() {
        return "Killing Spidine";
    }


    @Override
    public Priority priority() {
        return Priority.MEDIUM;
    }

    @Override
    public boolean validate() {
        return getSpidine().isPresent();
    }

    @Override
    public void execute() {
        killSpidine();
    }
}
