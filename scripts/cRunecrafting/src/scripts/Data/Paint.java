package scripts.Data;

import org.apache.commons.lang3.StringUtils;
import org.tribot.script.sdk.Skill;
import org.tribot.script.sdk.painting.Painting;
import org.tribot.script.sdk.painting.template.basic.BasicPaintTemplate;
import org.tribot.script.sdk.painting.template.basic.PaintLocation;
import org.tribot.script.sdk.painting.template.basic.PaintRows;
import org.tribot.script.sdk.painting.template.basic.PaintTextRow;
import scripts.ScriptUtils.ScriptTimer;
import scripts.Utils;
import scripts.cRunecrafting;

import java.awt.*;
import java.util.Locale;

public class Paint {

    public static void addMainPaint() {
        PaintTextRow template = PaintTextRow.builder().background(Color.darkGray.darker()).build();

        BasicPaintTemplate paint = BasicPaintTemplate.builder()
                .row(PaintRows.scriptName(template.toBuilder()))
                .row(template.toBuilder().label("Runtime").value(()->
                        ScriptTimer.getRuntimeString()).build())
                .row(template.toBuilder().label("Task").value(() -> Vars.get().status).build())
                .row(template.toBuilder().label("Current Rune").value(() -> {
                            String s = Vars.get().currentRune
                                    .map(runecraftItems -> runecraftItems.toString().toLowerCase()
                                            .replace("_", " ")).orElse("");
                            return StringUtils.capitalize(s);
                        })
                        .condition(() -> Vars.get().currentRune.isPresent())
                        .build())
                .row(template.toBuilder().label("Runecrafting").value(Paint::getXpGainedString).build())
                .row(template.toBuilder().label("Profit").value(()-> Utils.addCommaToNum(
                        Vars.get().getProfit())+
                        " | " + Utils.addCommaToNum(Vars.get().getProfitHr()) + "/hr").build())

                .location(PaintLocation.BOTTOM_LEFT_VIEWPORT)
                .build();

        Painting.addPaint(paint::render);
    }

    private static String getXpGainedString() {
        int currentLvl = Skill.RUNECRAFT.getActualLevel();
        int gainedLvl = currentLvl - Vars.get().startRcLevel;
        int gainedXp = Skill.RUNECRAFT.getXp() - Vars.get().startRcXp;

        double timeRan = System.currentTimeMillis() - Vars.get().startTime;
        double timeRanMin = (timeRan / 3600000);

        int xpHr = (int) (gainedXp / timeRanMin);
        long xpToLevel1 = Skill.RUNECRAFT.getCurrentXpToNextLevel();
        long ttl = (long) (xpToLevel1 / ((gainedXp / timeRan)));


        if (gainedLvl > 0) {
            return "[" + currentLvl + " (+" + gainedLvl + ")]: "
                    + Utils.addCommaToNum(gainedXp) + "xp (" + Utils.addCommaToNum(xpHr) + "/hr) " +
                    "|| TNL: "
                    + Utils.getRuntimeString(ttl);
        } else {
            return "[" + currentLvl + "]: "
                    + Utils.addCommaToNum(gainedXp) + "xp (" + Utils.addCommaToNum(xpHr) + "/hr) " +
                    "|| TNL: "
                    + Utils.getRuntimeString(ttl);
        }
    }
}
