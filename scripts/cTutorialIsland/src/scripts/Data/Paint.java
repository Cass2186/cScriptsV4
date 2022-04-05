package scripts.Data;

import org.tribot.api2007.Game;
import org.tribot.script.sdk.painting.template.basic.BasicPaintTemplate;
import org.tribot.script.sdk.painting.template.basic.PaintLocation;
import org.tribot.script.sdk.painting.template.basic.PaintRows;
import org.tribot.script.sdk.painting.template.basic.PaintTextRow;
import scripts.cTutorialIsland;

import java.awt.*;

public class Paint {

    public static void startPaint(){
        PaintTextRow template = PaintTextRow.builder().background(Color.DARK_GRAY.darker()).build();

        BasicPaintTemplate paint = BasicPaintTemplate.builder()
                .row(PaintRows.scriptName(template.toBuilder()))
                .row(PaintRows.runtime(template.toBuilder()))
                .row(template.toBuilder().label("Task").value(() -> cTutorialIsland.status).build())
                .row(template.toBuilder().label( "Game setting").value(()->
                        ""+ Game.getSetting(281)).build())
                .location(PaintLocation.BOTTOM_LEFT_VIEWPORT)
                .build();

        org.tribot.script.sdk.painting.Painting.addPaint(p -> paint.render(p));
    }

}
