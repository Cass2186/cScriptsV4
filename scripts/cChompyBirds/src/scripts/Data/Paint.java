package scripts.Data;

import org.tribot.script.sdk.Skill;
import org.tribot.script.sdk.painting.template.basic.BasicPaintTemplate;
import org.tribot.script.sdk.painting.template.basic.PaintLocation;
import org.tribot.script.sdk.painting.template.basic.PaintRows;
import org.tribot.script.sdk.painting.template.basic.PaintTextRow;
import scripts.PaintUtil;
import scripts.Utils;

import java.awt.*;

public class Paint {

    public static void addMainPaint() {
        PaintTextRow template = PaintTextRow.builder().background(Color.DARK_GRAY.darker()).build();
        BasicPaintTemplate paint = BasicPaintTemplate.builder()
                .row(PaintRows.scriptName(template.toBuilder()))
                .row(PaintRows.runtime(template.toBuilder()))
                .row(template.toBuilder().label("Task").value(() -> Vars.get().status).build())
                .row(template.toBuilder().label("Kills").value(() -> Vars.get().kills + " | " +
                        Vars.get().getKillsHr() + "/hr").build())
                .row(template.toBuilder().condition(() -> Skill.RANGED.getXp() > Const.START_RANGED_XP)
                        .label("Ranged XP")
                        .value(() -> PaintUtil.formatSkillString(Skill.RANGED, Const.START_RANGED_XP,
                                Vars.get().getGainedRangedXp(),
                                PaintUtil.getXpHr(Skill.RANGED, Const.START_RANGED_XP, Vars.get().startTime)))
                        .build())

                .location(PaintLocation.BOTTOM_LEFT_VIEWPORT)
                .build();

        org.tribot.script.sdk.painting.Painting.addPaint(p -> paint.render(p));
    }

}
