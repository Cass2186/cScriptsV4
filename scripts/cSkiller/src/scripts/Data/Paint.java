package scripts.Data;

import org.tribot.api.Timing;
import org.tribot.script.sdk.GameState;
import org.tribot.script.sdk.Log;
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
    private static Color backgroundColorGreen = Color.green.darker();

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
        org.tribot.script.sdk.painting.Painting.addPaint(g -> paint.render(g));
    }

    public static void setSlayerPaint() {
        BasicPaintTemplate paint = BasicPaintTemplate.builder()
                .row(template.toBuilder().label("Slayer Points")
                        .value(() -> Utils.getVarBitValue(SlayerConst.SLAYER_POINTS_VARBIT))
                        .condition(() -> Vars.get().showSlayerInfo).build())
                .row(template.toBuilder().label("Slayer Streak")
                        .value(() -> Utils.getVarBitValue(SlayerConst.TASK_STREAK_VARBIT))
                        .condition(() -> Vars.get().showSlayerInfo).build())
                .row(template.toBuilder().label("Remaining Kills")
                        .value(() -> GameState.getSetting(SlayerConst.REMAINING_KILLS_GAMESETTING))
                        .condition(() -> Vars.get().showSlayerInfo).build())
                .location(PaintLocation.TOP_RIGHT_CHATBOX)
                .row(template.toBuilder().label("AFK Timer").value(()
                                -> Timing.msToString(Vars.get().afkTimer.getRemaining()))
                        .condition(() -> Vars.get().showTimers).build())
                .row(template.toBuilder().label("Skill Timer").value(()
                                -> Timing.msToString(Vars.get().skillSwitchTimer.getRemaining()))
                        .condition(() -> Vars.get().showTimers).build())
                .build();
        org.tribot.script.sdk.painting.Painting.addPaint(g -> paint.render(g));
    }

    public static void setMainPaint() {
        BasicPaintTemplate paint = BasicPaintTemplate.builder()
                .row(PaintRows.versionedScriptName(template.toBuilder()))
                .row(PaintRows.runtime(template.toBuilder()))
                .row(template.toBuilder().label("Current Task").value(() -> cSkiller.currentSkillName).build())
                .row(template.toBuilder().label("Action").value(() -> cSkiller.status).build())
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

    public static void setExperiencePaint() {
        BasicPaintTemplate paint = BasicPaintTemplate.builder()
                .row(template.toBuilder().label("Toggle Experience Display " + Vars.get().showExperienceGained)
                        .onClick(() ->
                                Vars.get().showExperienceGained =
                                !Vars.get().showExperienceGained )

                        .build())
                .row(template.toBuilder().condition(() -> Skill.AGILITY.getXp() > Const.startAgilityXP &&
                                Vars.get().showExperienceGained)
                        .label("Agility")
                        .value(() -> PaintUtil.formatSkillString(Skill.AGILITY, Const.startAgilityLvl,
                                (Skill.AGILITY.getXp() - Const.startAgilityXP),
                                PaintUtil.getXpHr(Skill.AGILITY, Const.startAgilityXP, Vars.get().startTime)))
                        .build())
                .row(template.toBuilder().condition(() -> Skill.ATTACK.getXp() > Const.startAttXp &&
                                Vars.get().showExperienceGained)
                        .label("Attack")
                        .value(() -> PaintUtil.formatSkillString(Skill.ATTACK, Const.startAttLvl,
                                (Skill.ATTACK.getXp() - Const.startAttXp),
                                PaintUtil.getXpHr(Skill.ATTACK, Const.startAttXp, Vars.get().startTime)))
                        .build())
                .row(template.toBuilder().condition(() -> Skill.CONSTRUCTION.getXp() > Const.startConstructionXP &&
                                Vars.get().showExperienceGained)
                        .label("Construction")
                        .value(() -> PaintUtil.formatSkillString(Skill.CONSTRUCTION, Const.startConstructionLvl,
                                (Skill.CONSTRUCTION.getXp() - Const.startConstructionXP),
                                PaintUtil.getXpHr(Skill.CONSTRUCTION, Const.startConstructionXP, Vars.get().startTime)))
                        .build())
                .row(template.toBuilder().label("Cooking")
                        .condition(() -> Skill.COOKING.getXp() > Const.startCoookingXP &&
                                Vars.get().showExperienceGained)
                        .value(() -> PaintUtil.formatSkillString(Skill.COOKING, Const.startCoookingLvl,
                                (Skill.COOKING.getXp() - Const.startCoookingXP),
                                PaintUtil.getXpHr(Skill.COOKING, Const.startCoookingXP, Vars.get().startTime)))
                        .build())
                .row(template.toBuilder().label("Crafting")
                        .condition(() -> Skill.CRAFTING.getXp() > Const.startCraftingXP &&
                                Vars.get().showExperienceGained)
                        .value(() -> PaintUtil.formatSkillString(Skill.CRAFTING, Const.startCraftingLvl,
                                (Skill.CRAFTING.getXp() - Const.startCraftingXP),
                                PaintUtil.getXpHr(Skill.CRAFTING, Const.startCraftingXP, Vars.get().startTime)))
                        .build())
                .row(template.toBuilder().condition(() -> Skill.DEFENCE.getXp() > Const.startDefXp &&
                                Vars.get().showExperienceGained)
                        .label("Defence")
                        .value(() -> PaintUtil.formatSkillString(Skill.DEFENCE, Const.startDefLvl,
                                (Skill.DEFENCE.getXp() - Const.startDefXp),
                                PaintUtil.getXpHr(Skill.DEFENCE, Const.startDefXp, Vars.get().startTime)))
                        .build())
                .row(template.toBuilder().label("Firemaking")
                        .condition(() -> Skill.FIREMAKING.getXp() > Const.startFiremakingXP &&
                                Vars.get().showExperienceGained)
                        .value(() -> PaintUtil.formatSkillString(Skill.FIREMAKING, Const.startFiremakingLvl,
                                (Skill.FIREMAKING.getXp() - Const.startFiremakingXP),
                                PaintUtil.getXpHr(Skill.FIREMAKING, Const.startFiremakingXP, Vars.get().startTime)))
                        .build())
                .row(template.toBuilder().label("Fishing")
                        .condition(() -> Skill.FISHING.getXp() > Const.startFishingXP &&
                                Vars.get().showExperienceGained)
                        .value(() -> PaintUtil.formatSkillString(Skill.FISHING, Const.startFishingLvl,
                                (Skill.FISHING.getXp() - Const.startFishingXP),
                                PaintUtil.getXpHr(Skill.FISHING, Const.startFishingXP, Vars.get().startTime)))
                        .build())
                .row(template.toBuilder().condition(() -> Skill.HITPOINTS.getXp() > Const.startHPXP &&
                                Vars.get().showExperienceGained)
                        .label("Hitpoints")
                        .value(() -> PaintUtil.formatSkillString(Skill.HITPOINTS, Const.startHPLvl,
                                (Skill.HITPOINTS.getXp() - Const.startHPXP),
                                PaintUtil.getXpHr(Skill.HITPOINTS, Const.startHPXP, Vars.get().startTime)))
                        .build())
                .row(template.toBuilder().label("Herblore")
                        .condition(() -> Skill.HERBLORE.getXp() > Const.startHerbloreXP &&
                                Vars.get().showExperienceGained)
                        .value(() -> PaintUtil.formatSkillString(Skill.HERBLORE, Const.startHerbloreLvl,
                                (Skill.HERBLORE.getXp() - Const.startHerbloreXP),
                                PaintUtil.getXpHr(Skill.HERBLORE, Const.startHerbloreXP, Vars.get().startTime)))
                        .build())
                .row(template.toBuilder().label("Hunter")
                        .condition(() -> Skill.HUNTER.getXp() > Const.startHunterXP &&
                                Vars.get().showExperienceGained)
                        .value(() -> PaintUtil.formatSkillString(Skill.HUNTER, Const.startHunterLvl,
                                (Skill.HUNTER.getXp() - Const.startHerbloreXP),
                                PaintUtil.getXpHr(Skill.HUNTER, Const.startHunterXP, Vars.get().startTime)))
                        .build())
                .row(template.toBuilder().condition(() -> Skill.MAGIC.getXp() > Const.startMagicXp &&
                                Vars.get().showExperienceGained)
                        .label("Magic")
                        .value(() -> PaintUtil.formatSkillString(Skill.MAGIC, Const.startMageLvl,
                                (Skill.MAGIC.getXp() - Const.startMagicXp),
                                PaintUtil.getXpHr(Skill.MAGIC, Const.startMagicXp, Vars.get().startTime)))
                        .build())
                .row(template.toBuilder().label("Mining")
                        .condition(() -> Skill.MINING.getXp() > Const.startMiningXP &&
                                Vars.get().showExperienceGained)
                        .value(() -> PaintUtil.formatSkillString(Skill.MINING, Const.startMiningLvl,
                                (Skill.MINING.getXp() - Const.startMiningXP),
                                PaintUtil.getXpHr(Skill.MINING, Const.startMiningXP, Vars.get().startTime)))
                        .build())
                .row(template.toBuilder().label("Prayer")
                        .condition(() -> Skill.PRAYER.getXp() > Const.startPrayerXP &&
                                Vars.get().showExperienceGained)
                        .value(() -> PaintUtil.formatSkillString(Skill.PRAYER, Const.startPrayerLvl,
                                (Skill.PRAYER.getXp() - Const.startPrayerXP),
                                PaintUtil.getXpHr(Skill.PRAYER, Const.startPrayerXP, Vars.get().startTime)))
                        .build())
                .row(template.toBuilder().label("Ranged")
                        .condition(() -> Skill.RANGED.getXp() > Const.startRangeXp &&
                                Vars.get().showExperienceGained)
                        .value(() -> PaintUtil.formatSkillString(Skill.RANGED, Const.startRangeLvl,
                                (Skill.RANGED.getXp() - Const.startRangeXp),
                                PaintUtil.getXpHr(Skill.RANGED, Const.startRangeXp, Vars.get().startTime)))
                        .build())
                .row(template.toBuilder().label("Slayer")
                        .condition(() -> Skill.SLAYER.getXp() > Const.startSlayerXP &&
                                Vars.get().showExperienceGained)
                        .value(() -> PaintUtil.formatSkillString(Skill.SLAYER, Const.startSlayerLvl,
                                (Skill.SLAYER.getXp() - Const.startSlayerXP),
                                PaintUtil.getXpHr(Skill.SLAYER, Const.startSlayerXP, Vars.get().startTime)))
                        .build())
                .row(template.toBuilder().label("Strength")
                        .condition(() -> Skill.STRENGTH.getXp() > Const.startStrXp &&
                                Vars.get().showExperienceGained)
                        .value(() -> PaintUtil.formatSkillString(Skill.STRENGTH, Const.startStrLvl,
                                (Skill.STRENGTH.getXp() - Const.startStrXp),
                                PaintUtil.getXpHr(Skill.STRENGTH, Const.startStrXp, Vars.get().startTime)))
                        .build())

                .row(template.toBuilder().label("Woodcutting")
                        .condition(() -> Skill.WOODCUTTING.getXp() > Const.startWoodcuttingXP &&
                                Vars.get().showExperienceGained)
                        .value(() -> PaintUtil.formatSkillString(Skill.WOODCUTTING, Const.startWoodcuttingLvl,
                                (Skill.WOODCUTTING.getXp() - Const.startWoodcuttingXP),
                                PaintUtil.getXpHr(Skill.WOODCUTTING, Const.startWoodcuttingXP, Vars.get().startTime)))
                        .build())
                .location(PaintLocation.BOTTOM_RIGHT_VIEWPORT)
                .build();
        org.tribot.script.sdk.painting.Painting.addPaint(g -> paint.render(g));
    }
}
