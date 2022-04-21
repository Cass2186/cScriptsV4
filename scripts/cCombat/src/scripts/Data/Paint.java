package scripts.Data;

import org.tribot.script.Script;
import org.tribot.script.sdk.Skill;
import org.tribot.script.sdk.painting.Painting;
import org.tribot.script.sdk.painting.template.basic.BasicPaintTemplate;
import org.tribot.script.sdk.painting.template.basic.PaintLocation;
import org.tribot.script.sdk.painting.template.basic.PaintRows;
import org.tribot.script.sdk.painting.template.basic.PaintTextRow;
import scripts.PaintUtil;
import scripts.ScriptUtils.ScriptTimer;
import scripts.Utils;
import scripts.cCombat;

import java.awt.*;

public class Paint {

    private static Color backgroundColor = new Color(93, 140, 245, 160);

    private static PaintTextRow template = PaintTextRow.builder().background(backgroundColor).build();

    private static String getLootHr() {
        double timeRan = ScriptTimer.getRuntime();
        double timeRanMin = (timeRan / 3600000);

        return Utils.addCommaToNum(Math.round(Vars.get().lootValue / timeRanMin));
    }

    public static void setPaint() {
        BasicPaintTemplate paint = BasicPaintTemplate.builder()
                .row(PaintRows.scriptName(template.toBuilder()))
                .row(template.toBuilder().label("Runtime").value(() -> ScriptTimer.getRuntimeString()).build())
                .row(template.toBuilder().label("Task").value(() -> cCombat.status).build())
                .row(template.toBuilder().label("Loot Value").value(() ->
                        Utils.addCommaToNum(Vars.get().lootValue) + " || hr: " +
                                getLootHr()).build())
                .row(template.toBuilder().condition(() -> Skill.RANGED.getXp() > Const.startRangeXp)
                        .label("Ranged")
                        .value(() -> PaintUtil.formatSkillString(Skill.RANGED, Const.startRangeLvl,
                                (Skill.RANGED.getXp() - Const.startRangeXp),
                                PaintUtil.getXpHr(Skill.RANGED, Vars.get().startRangeXp, Vars.get().startTime)))
                        .build())

                .row(template.toBuilder().condition(() -> Skill.STRENGTH.getXp() > Const.startStrXp)
                        .label("Strength")
                        .value(() -> PaintUtil.formatSkillString(Skill.STRENGTH, Const.startStrLvl,
                                (Skill.STRENGTH.getXp() - Const.startStrXp),
                                PaintUtil.getXpHr(Skill.STRENGTH, Vars.get().startStrXp, Vars.get().startTime)))
                        .build())

                .row(template.toBuilder().condition(() -> Skill.ATTACK.getXp() > Const.startAttXp)
                        .label("Attack")
                        .value(() -> PaintUtil.formatSkillString(Skill.ATTACK, Const.startAttLvl,
                                (Skill.ATTACK.getXp() - Const.startAttXp),
                                PaintUtil.getXpHr(Skill.ATTACK, Vars.get().startAttXp, Vars.get().startTime)))
                        .build())


                .row(template.toBuilder().condition(() -> Skill.DEFENCE.getXp() > Const.startDefXp)
                        .label("Defence")
                        .value(() -> PaintUtil.formatSkillString(Skill.DEFENCE, Const.startDefLvl,
                                (Skill.DEFENCE.getXp() - Const.startDefXp),
                                PaintUtil.getXpHr(Skill.DEFENCE, Vars.get().startDefXp, Vars.get().startTime)))
                        .build())

                .row(template.toBuilder().condition(() -> Skill.MAGIC.getXp() > Const.startMageXp)
                        .label("Magic")
                        .value(() -> PaintUtil.formatSkillString(Skill.MAGIC, Const.startMageLvl,
                                (Skill.MAGIC.getXp() - Const.startMageXp),
                                PaintUtil.getXpHr(Skill.MAGIC, Vars.get().startMageXp, Vars.get().startTime)))
                        .build())

                /*  .row(template.toBuilder().condition(() -> Skill.SLAYER.getXp() > Const.startSlayerXP)
                          .label("Slayer")
                          .value(() -> PaintUtil.formatSkillString(Skill.SLAYER,Const.startSlayerLvl,
                                  (Skill.SLAYER.getXp() - Const.startSlayerXP),
                                  PaintUtil.getXpHr(Skill.SLAYER, Vars.get().startSlayerXP, Vars.get().startTime)))
                          .build())*/

                .row(template.toBuilder().condition(() -> Skill.HITPOINTS.getXp() > Const.startHPXP)
                        .label("Hitpoints")
                        .value(() -> PaintUtil.formatSkillString(Skill.HITPOINTS, Const.startHPLvl,
                                (Skill.HITPOINTS.getXp() - Const.startHPXP),
                                PaintUtil.getXpHr(Skill.HITPOINTS, Vars.get().startHPXP, Vars.get().startTime)))
                        .build())


                .location(PaintLocation.BOTTOM_LEFT_VIEWPORT)
                .build();
        Painting.addPaint(paint::render);
    }
}
