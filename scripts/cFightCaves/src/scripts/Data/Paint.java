package scripts.Data;

import org.tribot.api2007.Game;
import org.tribot.api2007.NPCs;
import org.tribot.api2007.Player;
import org.tribot.api2007.Projection;
import org.tribot.api2007.types.RSNPC;
import org.tribot.api2007.types.RSTile;
import org.tribot.script.sdk.Skill;
import org.tribot.script.sdk.painting.Painting;
import org.tribot.script.sdk.painting.template.basic.BasicPaintTemplate;
import org.tribot.script.sdk.painting.template.basic.PaintLocation;
import org.tribot.script.sdk.painting.template.basic.PaintRows;
import org.tribot.script.sdk.painting.template.basic.PaintTextRow;
import org.tribot.script.sdk.query.Query;
import org.tribot.script.sdk.types.Npc;
import scripts.PaintUtil;

import java.awt.*;
import java.util.List;

public class Paint {
    public static void addMainPaint() {
        PaintTextRow template = PaintTextRow.builder().background(Color.DARK_GRAY.darker()).build();
        BasicPaintTemplate paint = BasicPaintTemplate.builder()
                .row(PaintRows.scriptName(template.toBuilder()))
                .row(PaintRows.runtime(template.toBuilder()))
                .row(template.toBuilder().label("Task").value(() -> Vars.get().status).build())
                .row(template.toBuilder().label("Wave").value(Wave::getCurrentWave).build())
                .row(template.toBuilder().condition(() -> Skill.RANGED.getXp() > Const.START_RANGED_XP)
                        .label("Ranged XP")
                        .value(() -> PaintUtil.formatSkillString(Skill.RANGED, Const.START_RANGED_XP,
                                Vars.get().getGainedRangedXp(),
                                PaintUtil.getXpHr(Skill.RANGED, Const.START_RANGED_XP, Vars.get().startTime)))
                        .build())
                .row(template.toBuilder().label("Rotation").value(Rotations::getRotation).build())
                .row(template.toBuilder().label("Should Lure").value(()->CaveNPCs.shouldLure(Wave.getCurrentWave())).build())
                .row(template.toBuilder().label("Should Prayer Range").value(()-> CaveNPCs.shouldProtectRange(Wave.getCurrentWave())).build())
                .row(template.toBuilder().label("Highest Priority Mob").value(()->  CaveNPCs.getNPCPriority(Wave.getCurrentWave()).entrySet().stream().findFirst()).build())
                .location(PaintLocation.BOTTOM_LEFT_VIEWPORT)
                .build();
        org.tribot.script.sdk.painting.Painting.addPaint(p -> paint.render(p));

    }

}
