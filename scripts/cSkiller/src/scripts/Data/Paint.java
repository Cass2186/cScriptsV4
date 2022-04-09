package scripts.Data;

import org.tribot.api.Timing;
import org.tribot.script.sdk.GameState;
import org.tribot.script.sdk.Skill;
import org.tribot.script.sdk.painting.template.basic.BasicPaintTemplate;
import org.tribot.script.sdk.painting.template.basic.PaintLocation;
import org.tribot.script.sdk.painting.template.basic.PaintRows;
import org.tribot.script.sdk.painting.template.basic.PaintTextRow;
import scripts.PaintUtil;
import scripts.Tasks.Slayer.SlayerConst.SlayerConst;
import scripts.Utils;
import scripts.cSkiller;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Paint {

    private static Color backgroundColor = new Color(93, 140, 245, 160);
    private static PaintTextRow template = PaintTextRow.builder().background(backgroundColor).build();

    public static void setToggleablePaint() {
        BasicPaintTemplate paint = BasicPaintTemplate.builder()
                .row(template.toBuilder().label("Toggle Slayer Info")
                        .onClick(() ->
                                Vars.get().showSlayerInfo = !Vars.get().showSlayerInfo)
                        .condition(() ->
                                Vars.get().currentTask != null &&
                                        Vars.get().currentTask.equals(SkillTasks.SLAYER))
                        .build())
                .row(template.toBuilder().label("Toggle Timers").onClick(() ->
                        Vars.get().showTimers = !Vars.get().showTimers).build())
                .location(PaintLocation.TOP_LEFT_CHATBOX)
                .build();

        BasicPaintTemplate timers = BasicPaintTemplate.builder()
                .row(template.toBuilder().label("AFK Timer").value(()
                                -> Timing.msToString(Vars.get().afkTimer.getRemaining()))
                        .condition(()-> Vars.get().showTimers).build())
                .row(template.toBuilder().label("Skill Timer").value(()
                                ->Timing.msToString(Vars.get().skillSwitchTimer.getRemaining()))
                        .condition(()-> Vars.get().showTimers).build())
                .location(PaintLocation.TOP_RIGHT_CHATBOX)
                .build();
        org.tribot.script.sdk.painting.Painting.addPaint(g -> paint.render(g));
        org.tribot.script.sdk.painting.Painting.addPaint(g -> timers.render(g));
    }

    public static void setSlayerPaint() {

        BasicPaintTemplate paint = BasicPaintTemplate.builder()
                .row(template.toBuilder().label("Slayer Points")
                        .value(()-> Utils.getVarBitValue(SlayerConst.SLAYER_POINTS_VARBIT))
                        .condition(() -> Vars.get().showSlayerInfo).build())
                .row(template.toBuilder().label("Slayer Streak")
                        .value(()-> Utils.getVarBitValue(SlayerConst.TASK_STREAK_VARBIT))
                        .condition(() -> Vars.get().showSlayerInfo).build())
                .row(template.toBuilder().label("Remaining Kills")
                        .value(()-> GameState.getSetting(SlayerConst.REMAINING_KILLS_GAMESETTING))
                        .condition(() -> Vars.get().showSlayerInfo).build())
                .location(PaintLocation.BOTTOM_RIGHT_VIEWPORT)
                .build();
        org.tribot.script.sdk.painting.Painting.addPaint(g -> paint.render(g));
    }

    public static void setMainPaint(){
        BasicPaintTemplate paint = BasicPaintTemplate.builder()
                .row(PaintRows.versionedScriptName(template.toBuilder()))
                .row(PaintRows.runtime(template.toBuilder()))
                .row(template.toBuilder().label("Current Task").value(() ->cSkiller.currentSkillName).build())
                .row(template.toBuilder().label("Action").value(() ->cSkiller.status).build())
               /*.row(template.toBuilder().condition(() -> Skill.FARMING.getXp() > Const.START_FARM_XP)
                        .label("Farming XP")
                        .value(() -> PaintUtil.formatSkillString(Skill.FARMING, Const.START_FARM_XP,
                                Vars.get().getGainedFarmXp(),
                                PaintUtil.getXpHr(Skill.FARMING, Const.START_FARM_XP, Vars.get().startTime)))
                        .build())*/

                .location(PaintLocation.BOTTOM_LEFT_VIEWPORT)
                .build();
        org.tribot.script.sdk.painting.Painting.addPaint(g -> paint.render(g));

    }
}
