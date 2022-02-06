package scripts.QuestSteps;

import org.jfree.chart.plot.PieLabelDistributor;
import org.tribot.api.General;
import org.tribot.api2007.GameTab;
import org.tribot.api2007.Interfaces;
import org.tribot.api2007.Player;
import org.tribot.api2007.types.RSInterface;
import org.tribot.api2007.types.RSTile;
import scripts.PathingUtil;
import scripts.Timer;

import java.util.Collection;

public class EmoteStep extends QuestStep {

    private String emoteAction;
    private RSTile tile;

    private int EMOTE_PARENT_ID = 216;
    private int EMOTE_CHILD_ID = 1;

    public EmoteStep(String emoteAction) {
        this.emoteAction = emoteAction;
    }

    public EmoteStep(String emoteAction, RSTile tile) {
        this.emoteAction = emoteAction;
        this.tile = tile;
    }

    @Override
    public void execute() {
        if (this.tile != null) {
            if (PathingUtil.localNavigation(this.tile)) {
                General.println("[Debug]: Using local nav");
                PathingUtil.movementIdle();
            } else {

            }
        }
        if (GameTab.open(GameTab.TABS.EMOTES)) {
            General.println("[EmoteStep]: Performing emote: " + emoteAction);
            RSInterface emoteInter = Interfaces.findWhereAction(emoteAction, EMOTE_PARENT_ID);
            if (emoteInter != null && emoteInter.click()) {
                if (Timer.waitCondition(() -> Player.getAnimation() != -1, 1500, 2200))
                    return;
            }
        }
        General.println("[EmoteStep]: Failed to perform emote: " + emoteAction);
    }

    @Override
    public void addDialogStep(String... dialog) {

    }

    @Override
    public void addSubSteps(QuestStep... substep) {

    }

    @Override
    public void addSubSteps(Collection<QuestStep> substeps) {

    }


}
