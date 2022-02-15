package scripts.Tasks.Magic;

import lombok.Builder;
import org.tribot.api.DynamicClicking;
import org.tribot.api.General;
import org.tribot.api.input.Mouse;
import org.tribot.api2007.GameTab;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.NPCs;
import org.tribot.api2007.Player;
import org.tribot.api2007.types.RSItem;
import org.tribot.api2007.types.RSNPC;
import org.tribot.script.sdk.Magic;
import org.tribot.script.sdk.query.Query;
import org.tribot.script.sdk.types.Npc;
import scripts.API.Priority;
import scripts.API.Task;
import scripts.AntiBan;
import scripts.ItemID;
import scripts.Timer;

import java.awt.*;
import java.util.Optional;

public class StunAlch implements Task {

    private int startLevel;
    private int endLevel;

    @Builder
    public StunAlch(int startLevel, int endLevel){

    }

    private boolean hasRunes(int... runeIds){
        int i = 0;
        for (int b: runeIds){
            if (Inventory.find(b).length >0)
                i++;
        }
        return i == runeIds.length;
    }

    public void castAlchAndHoverStun(int alchItem, String magicSpell, String npcName) {
        if (!hasRunes(ItemID.NATURE_RUNE)) {
            General.println("[Debug]: Missing one or more runes.");
            return;
        }

        //unselect spell if we've accidentally selected the wrong one
        if (Magic.isAnySpellSelected()) {
            Player.getPosition().click();
            General.sleep(400, 1200);
        }

        if (!Magic.isAnySpellSelected()) {
            AntiBan.timedActions();
            RSItem[] alch = Inventory.find(alchItem);

            // click alch
            if (alch.length > 0 && Magic.selectSpell("High Level Alchemy")) {
                if (Timer.waitCondition(() -> {
                    AntiBan.timedActions();
                    General.sleep(General.randomSD(60, 30));
                    return GameTab.getOpen() ==
                            GameTab.TABS.INVENTORY &&  Player.getAnimation() != 713 && alch[0].click();
                }, 1550, 2200) ) {

                    // hover stun-type spell
                    int hoverChance = General.random(0,100);
                    Rectangle hoverPoint =  MagicMethods.getScreenPosition(magicSpell);
                    if ( hoverChance < 62 && hoverPoint != null &&
                            !hoverPoint.contains(Mouse.getPos())) {
                        Mouse.moveBox(hoverPoint);
                    }

                    if(Timer.waitCondition(() -> GameTab.getOpen() == GameTab.TABS.MAGIC, 2500, 3200))
                        General.sleep(General.randomSD(170, 60));
                }
            }
           /*
           // new API version of below, haven't tested
           Optional<Npc> zomb = Query.npcs().nameContains(npcName)
                    .findBestInteractable();
            if (GameTab.getOpen() == GameTab.TABS.MAGIC  && zomb.isPresent() &&
                    Magic.selectSpell(magicSpell)) {
                General.sleep(General.randomSD(90, 30));
                if (zomb.get().interact("Cast ")){
                    General.sleep(General.randomSD(220, 65));
                }
            }*/
            RSNPC[] zombie = NPCs.findNearest(npcName);
            if (GameTab.getOpen() == GameTab.TABS.MAGIC && zombie.length > 0 &&
                    Magic.selectSpell(magicSpell)) {
                General.sleep(General.randomSD(90, 30));
                if (DynamicClicking.clickRSNPC(zombie[0], "Cast")) {
                    General.sleep(General.randomSD(220, 65));

                }
            }

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
        return "Magic Training";
    }
}
