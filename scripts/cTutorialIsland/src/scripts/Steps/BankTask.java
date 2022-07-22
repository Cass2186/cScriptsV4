package scripts.Steps;

import dax.walker_engine.interaction_handling.NPCInteraction;
import org.tribot.api.General;
import org.tribot.api2007.Game;
import org.tribot.api2007.GameTab;
import org.tribot.api2007.Interfaces;
import org.tribot.api2007.NPCChat;
import org.tribot.api2007.types.RSInterface;
import org.tribot.script.sdk.GameState;
import org.tribot.script.sdk.Options;
import org.tribot.script.sdk.Waiting;
import org.tribot.script.sdk.Widgets;
import org.tribot.script.sdk.query.Query;
import org.tribot.script.sdk.types.Widget;
import scripts.*;
import scripts.Data.Const;
import scripts.Tasks.Priority;
import scripts.Tasks.Task;

import java.util.Optional;

public class BankTask implements Task {

    private void openBank() {
        PathingUtil.walkToTile(Const.BANK_TILE);
        if (BankManager.open(true)) {
            Utils.idleNormalAction(true);
        }
    }

    private void openPollBooth() {
        BankManager.close(true);

        if (Utils.clickObject("Poll booth", "Use", false)) {
            NpcChat.handle(true);
            Timer.waitCondition(4000, 400,
                    () -> Widgets.isVisible(310));
        }
    }

    private void closePollInterface() {
        Optional<Widget> closeButton = Query.widgets().inIndexPath(310)
                .actionContains("Close").findFirst();

        if (closeButton.map(Widget::click).orElse(false)) {
            Timer.waitCondition(3500, 400,
                    () -> !Widgets.isVisible(310));
        } else {
            Query.widgets().actionContains("Close").findFirst().map(Widget::click);
        }

    }


    private void goToAccountGuide() {
        closePollInterface();
        Widgets.closeAll();
        PathingUtil.localNav(Const.ACCOUNT_INFO_TILE);
    }

    private void talkToAccountGuide() {
        closePollInterface();
        if (NpcChat.talkToNPC(Const.ACCOUNT_GUIDE_ID))
            NpcChat.handle(true);
    }

    private void openTab() {
        int SKILLS_INTERFACE = Options.isResizableModeEnabled() ? 164 : 548;
        RSInterface skills = Interfaces.findWhereAction("Account Management", SKILLS_INTERFACE);
        if (skills != null && skills.click()) {
            Timer.slowWaitCondition(() -> GameTab.getOpen() ==
                    GameTab.TABS.OPTIONS, 1250, 2000);
        }
    }


    @Override
    public String toString() {
        return "Bank Task";
    }

    @Override
    public Priority priority() {
        return Priority.LOW;
    }

    @Override
    public boolean validate() {
        return Game.getSetting(Const.GAME_SETTING) >= 500 &&
                Game.getSetting(Const.GAME_SETTING) <= 532;
    }

    @Override
    public void execute() {
        if (Game.getSetting(Const.GAME_SETTING) == 500 || Game.getSetting(Const.GAME_SETTING) == 510) {
            openBank();
        } else if (Game.getSetting(Const.GAME_SETTING) == 520) {
            openPollBooth();
        } else if (Game.getSetting(Const.GAME_SETTING) == 525) {
            goToAccountGuide();
        } else if (Game.getSetting(Const.GAME_SETTING) == 530) {
            talkToAccountGuide();
        } else if (Game.getSetting(Const.GAME_SETTING) == 531) {
            openTab();
        } else if (Game.getSetting(Const.GAME_SETTING) == 532) {
            closePollInterface();
            talkToAccountGuide();
        }
    }
}
