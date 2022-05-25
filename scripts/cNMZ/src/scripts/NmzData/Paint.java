package scripts.NmzData;

import org.tribot.script.sdk.Log;
import org.tribot.script.sdk.Skill;
import org.tribot.script.sdk.painting.template.basic.*;
import scripts.PaintUtil;
import scripts.Utils;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;

public class Paint {

    public static void addPaint() {
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
                .row(template.toBuilder().label("NMZ Points Gained").value(() ->
                                Utils.addCommaToNum(Vars.get().getGainedNmzPoints())
                                        + " (" + Utils.addCommaToNum(
                                Vars.get().getNmzPointsHr(Vars.get().startTime)) +
                                "/hr)")
                        .build())
                .row(template.toBuilder().condition(() -> Skill.RANGED.getXp() > Const.startRangeXp)
                        .label("Ranged")
                        .value(() -> PaintUtil.formatSkillString(Skill.RANGED, Const.startRangeLvl,
                                (Skill.RANGED.getXp() - Const.startRangeXp),
                                PaintUtil.getXpHr(Skill.RANGED, Const.startRangeXp, Vars.get().startTime)))
                        .build())

                .row(template.toBuilder().condition(() -> Skill.STRENGTH.getXp() > Const.startStrXp)
                        .label("Strength")
                        .value(() -> PaintUtil.formatSkillString(Skill.STRENGTH, Const.startStrLvl,
                                (Skill.STRENGTH.getXp() - Const.startStrXp),
                                PaintUtil.getXpHr(Skill.STRENGTH, Const.startStrXp, Vars.get().startTime)))
                        .build())

                .row(template.toBuilder().condition(() -> Skill.ATTACK.getXp() > Const.startAttXp)
                        .label("Attack")
                        .value(() -> PaintUtil.formatSkillString(Skill.ATTACK, Const.startAttLvl,
                                (Skill.ATTACK.getXp() - Const.startAttXp),
                                PaintUtil.getXpHr(Skill.ATTACK, Const.startAttXp, Vars.get().startTime)))
                        .build())


                .row(template.toBuilder().condition(() -> Skill.DEFENCE.getXp() > Const.startDefXp)
                        .label("Defence")
                        .value(() -> PaintUtil.formatSkillString(Skill.DEFENCE, Const.startDefLvl,
                                (Skill.DEFENCE.getXp() - Const.startDefXp),
                                PaintUtil.getXpHr(Skill.DEFENCE, Const.startDefXp, Vars.get().startTime)))
                        .build())

                .row(template.toBuilder().condition(() -> Skill.MAGIC.getXp() > Const.startMageXp)
                        .label("Magic")
                        .value(() -> PaintUtil.formatSkillString(Skill.MAGIC, Const.startMageLvl,
                                (Skill.MAGIC.getXp() - Const.startMageXp),
                                PaintUtil.getXpHr(Skill.MAGIC, Const.startMageXp, Vars.get().startTime)))
                        .build())

                .row(template.toBuilder().condition(() -> Skill.HITPOINTS.getXp() > Const.startHPXP)
                        .label("Hitpoints")
                        .value(() -> PaintUtil.formatSkillString(Skill.HITPOINTS, Const.startHPLvl,
                                (Skill.HITPOINTS.getXp() - Const.startHPXP),
                                PaintUtil.getXpHr(Skill.HITPOINTS, Const.startHPXP, Vars.get().startTime)))
                        .build())
                .location(PaintLocation.BOTTOM_LEFT_VIEWPORT)
                .build();

        org.tribot.script.sdk.painting.Painting.addPaint(p -> paint.render(p));
    }

    public static void initializeDetailedPaint() {
        PaintTextRow template = PaintTextRow.builder().background(Color.DARK_GRAY.darker()).build();

        BasicPaintTemplate paint = BasicPaintTemplate.builder()
                .row(template.toBuilder().label("Using Prayer Potions")
                        .value(() -> Vars.get().usingPrayerPots)
                        .condition(() -> Vars.get().showDetailedPaint)
                        .build())
                .row(template.toBuilder().label("Using Overload Potions")
                        .value(() -> Vars.get().usingOverloadPots)
                        .condition(() -> Vars.get().showDetailedPaint)
                        .build())
                .row(template.toBuilder().label("Using Absorption Potions")
                        .value(() -> Vars.get().usingAbsorptions)
                        .condition(() -> Vars.get().showDetailedPaint)
                        .build())
                .row(template.toBuilder().label("Using Super Combat Potions")
                        .value(() -> Vars.get().usingSuperCombat)
                        .condition(() -> Vars.get().showDetailedPaint)
                        .build())
                .row(template.toBuilder().label("Using Locator Orb")
                        .value(() -> Vars.get().usingLocatorOrb)
                        .condition(() -> Vars.get().showDetailedPaint)
                        .build())
                .row(template.toBuilder().label("Eat Rockcake at")
                        .value(() -> Vars.get().eatRockCakeAt)
                        .condition(() -> Vars.get().showDetailedPaint &&
                                Vars.get().usingAbsorptions)
                        .build())
                .row(template.toBuilder().label("Drink Absorption At")
                        .value(() -> Vars.get().drinkAbsorptionAt)
                        .condition(() -> Vars.get().showDetailedPaint &&
                                Vars.get().usingAbsorptions)
                        .build())
                .row(template.toBuilder().label("Drink Absorption to")
                        .value(() -> Vars.get().drinkAbsorptionUpTo)
                        .condition(() -> Vars.get().showDetailedPaint &&
                                Vars.get().usingAbsorptions)
                        .build())
                .row(template.toBuilder().label("Drink Prayer at %")
                        .value(() -> Vars.get().drinkPrayAtPercentage)
                        .condition(() -> Vars.get().showDetailedPaint &&
                                Vars.get().usingPrayerPots)
                        .build())

                .row(template.toBuilder().label("Drink Super Cb at")
                        .value(() -> (Skill.STRENGTH.getActualLevel()  + Vars.get().superCombatAdd)
                        + " (" + Vars.get().superCombatPotionAddMean + "+/-" + Vars.get().superCombatPotionAddSd
                        + ")")
                        .condition(() -> Vars.get().showDetailedPaint &&
                                Vars.get().usingSuperCombat)
                        .build())
                .location(PaintLocation.BOTTOM_RIGHT_VIEWPORT)
                .build();

        org.tribot.script.sdk.painting.Painting.addPaint(p -> paint.render(p));
    }

}
