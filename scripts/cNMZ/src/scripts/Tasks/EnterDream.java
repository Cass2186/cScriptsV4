package scripts.Tasks;

import org.tribot.api.General;
import org.tribot.api2007.Game;
import org.tribot.api2007.Interfaces;
import org.tribot.api2007.types.RSInterface;
import org.tribot.script.sdk.*;
import org.tribot.script.sdk.query.Query;
import org.tribot.script.sdk.types.GameObject;
import org.tribot.script.sdk.types.Npc;
import org.tribot.script.sdk.types.Widget;
import scripts.InterfaceUtil;
import scripts.NmzData.NmzConst;
import scripts.NmzData.Vars;
import scripts.NpcChat;
import scripts.Requirements.InventoryRequirement;
import scripts.Utils;
import scripts.cNMZ;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class EnterDream implements Task {

    int POTION_ID = 26291; //"Drink"
    int BOSS_SELECTION_WIDGET_PARENT = 129;
    int VARBIT_FOR_READY_DREAM = 3946; //==123 when ready, 0 when not

    List<String> HARD_RUMBLE_INITIAL_STRINGS =
            List.of("Rumble", "Customisable - hard", "Yes");
    List<String> HARD_RUMBLE_PREV = List.of("Previous: Customisable Rumble (hard)", "Yes");


    private boolean isInventoryReady(){
        return Inventory.isFull();
    }

    public boolean checkBossIsSelected(String bossName) {
        RSInterface listInter = Interfaces.get(NmzConst.CHOSE_BOSSES_PARENT_ID, NmzConst.BOSS_LIST_CHILD_ID);
        if (listInter != null) {
            RSInterface[] comps = listInter.getChildren();
            // have to do this so I can access the interface that is +2
            for (int i = 0; i < comps.length; i++) {
                String txt = comps[i].getText();
                String underCaseBoss = bossName.toLowerCase();
                if (txt != null && txt.contains(underCaseBoss)) {
                    if (comps.length >= i + 1) {
                        boolean b = comps[i + 1].getTextureID() == NmzConst.CHECK_MARK_TEXTURE_ID;
                        General.println("[Debug]: Boss: " + bossName + " selected? " + b);
                        return b;
                    }
                    General.println("[Debug]: Comps.length() is too short, return false");
                    return false;
                }
            }
        }
        return false;
    }

    private boolean setUpDream() {
        if (GameState.getVarbit(VARBIT_FOR_READY_DREAM) == 123)
            return true;

        Optional<Npc> dominic = Query.npcs().actionContains("Dream").findFirst();
        if (dominic.map(npc -> npc.interact("Dream")).orElse(false)) {
            Waiting.waitUntil(4500, 125, ChatScreen::isOpen);
        }
        Utils.setChatScreenConfig(true);
        if (ChatScreen.isOpen()) {
            if (ChatScreen.getOptions().stream()
                    .anyMatch(s -> s.contains(HARD_RUMBLE_PREV.get(0)))) {
                Log.info("Using hard rumble previous");
               ChatScreen.handle(HARD_RUMBLE_PREV.toArray(String[]::new));

            } else {
                Log.info("Using hard rumble initial");
                ChatScreen.handle( HARD_RUMBLE_INITIAL_STRINGS.toArray(String[]::new));

            }
        }
        return Waiting.waitUntil(1500, 75, () ->
                GameState.getVarbit(VARBIT_FOR_READY_DREAM) == 123);
    }

    private boolean isDreamScreenOpen(){
       return Query.widgets().inIndexPath(BOSS_SELECTION_WIDGET_PARENT).isVisible().isAny();
    }


    private boolean enterDream(){
        if (setUpDream()){
            Optional<GameObject> potion = Query.gameObjects().idEquals(POTION_ID)
                    .actionContains("Drink")
                    .findFirst();
            if (!isDreamScreenOpen() &&
                    potion.map(p->p.interact("Drink")).orElse(false) &&
                Waiting.waitUntil(3500,225, this::isDreamScreenOpen)){

            }
            Optional<Widget> acceptButton = Query.widgets()
                    .inIndexPath(BOSS_SELECTION_WIDGET_PARENT)
                    .textContains("Accept").findFirst();
            if (acceptButton.map(b-> b.click()).orElse(false)){
                return Waiting.waitUntil(4500, 200, GameState::isInInstance);
            }
        }
        return Waiting.waitUntil(2500, 200, GameState::isInInstance);
    }


    @Override
    public String toString() {
        return "Entering Dream";
    }

    @Override
    public Priority priority() {
        return Priority.MEDIUM;
    }

    @Override
    public boolean validate() {
        return !Game.isInInstance() && GameState.getVarbit(VARBIT_FOR_READY_DREAM) == 0 &&
                Vars.get().invRequirement.map(InventoryRequirement::check).orElse(false);
    }

    @Override
    public void execute() {
        enterDream();
    }
}
