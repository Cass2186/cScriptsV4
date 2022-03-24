package scripts.Paint;

import org.tribot.api2007.Login;
import org.tribot.script.sdk.Skill;

import java.awt.*;
import java.util.Optional;

// taken from JustJ and modified to SDK
public class XpGainedPaint implements PaintLine{

    private final Skill skill;

    private final long startTime;
    private long runTime;
    private Integer startXp;
    private Integer xpGained;

    public XpGainedPaint(Skill skill) {
        this.skill = skill;
        this.startTime = System.currentTimeMillis();
        update();
    }


    public static String numberFormat(double num) {
        if (num < 1000.0) {
            return Integer.toString((int) num);
        } else if (Math.round(num) / 10000.0 < 100.0) {
            return String.format("%.1fk", Math.round(num) / 1000.0);
        } else {
            return String.format("%.1fm", Math.round(num) / 1000000.0);
        }
    }


    @Override
    public Optional<Image> getLineImage() {
        return Optional.empty();
    }

    @Override
    public String getLineText() {
        return String.format("XP Gained: %s (%s/h)", numberFormat(xpGained),
                numberFormat((xpGained * 3600000D / runTime)));
    }

    @Override
    public void update() {
        runTime = System.currentTimeMillis() - startTime;
        if (Login.getLoginState() != Login.STATE.INGAME) {
            return;
        }
        if (startXp == null) {
            startXp = skill.getXp();
        }
        xpGained = skill.getXp() - startXp;
    }


}