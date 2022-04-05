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
                .row(template.toBuilder().label("Toggle Detailed Paint")
                        .onClick(() -> {
                            if (Vars.get().showDetailedPaint) {
                                Vars.get().showDetailedPaint = false;
                            } else {
                                Vars.get().showDetailedPaint = true;
                            }
                        }).build())
                .row(template.toBuilder().condition(() -> Skill.FARMING.getXp() > Const.START_FARM_XP)
                        .label("Farming XP")
                        .value(() -> PaintUtil.formatSkillString(Skill.FARMING, Const.START_FARM_XP,
                                Vars.get().getGainedFarmXp(),
                                PaintUtil.getXpHr(Skill.FARMING, Const.START_FARM_XP, Vars.get().startTime)))
                        .build())

                .location(PaintLocation.BOTTOM_LEFT_VIEWPORT)
                .build();

        org.tribot.script.sdk.painting.Painting.addPaint(p -> paint.render(p));
    }

}
