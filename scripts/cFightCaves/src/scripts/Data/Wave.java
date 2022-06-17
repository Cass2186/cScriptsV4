package scripts.Data;

import lombok.Getter;
import lombok.Setter;
import org.tribot.api.General;

import java.awt.*;

public class Wave {
    @Getter
    @Setter
    private static int currentWave = 0;
    private static String image = null;

    public static void sendWaveWebhook(){
        switch(General.random(1,4)){
            case 1:
                image = "https://oldschool.runescape.wiki/images/b/b1/Tz-Kih.png?31e87";
            case 2:
                image = "https://oldschool.runescape.wiki/images/8/82/TzTok-Jad.png?87507";
            case 3:
                image = "https://oldschool.runescape.wiki/images/c/cc/Tok-Xil_%281%29.png?36c4b";
            case 4:
                image = "https://oldschool.runescape.wiki/images/thumb/d/d2/Ket-Zek_%281%29.png/1280px-Ket-Zek_%281%29.png?d25f0";
        }

    }


}
