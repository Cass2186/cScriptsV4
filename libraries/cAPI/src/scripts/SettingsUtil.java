package scripts;

import lombok.Getter;
import org.tribot.script.sdk.*;
import org.tribot.script.sdk.query.Query;
import org.tribot.script.sdk.types.Widget;

import java.util.Optional;

public class SettingsUtil {
    private static int TAB_VARBIT = 9656;

    public static int SETTINGS_TAB_PARENT_ID = 116;
    public static int SETTINGS_WINDOW_PARENT = 134;


    public static boolean openSettingsWindow() {
        if (Widgets.isVisible(SETTINGS_WINDOW_PARENT)) {
            Log.info("Settings is open");
            return true;
        } else {
            Log.info("Opening Settings");
            if (GameTab.OPTIONS.open())
                Waiting.waitUntil(3500, 400, () -> GameTab.OPTIONS.isOpen());


            if (Query.widgets().inIndexPath(SETTINGS_TAB_PARENT_ID, 106)
                    .actionContains("Controls")
                    .findFirst().map(Widget::click).orElse(false)) // clicks the tab with the gear on it
                Utils.microSleep();

            if (Query.widgets().inIndexPath(SETTINGS_TAB_PARENT_ID).textContains("All settings")
                    .findFirst().map(Widget::click).orElse(false))
                return Waiting.waitUntil(3500, 400, () -> Widgets.isVisible(SETTINGS_WINDOW_PARENT));

        }
        return Widgets.isVisible(SETTINGS_WINDOW_PARENT);
    }

    public enum SettingsTabType {
        ACTIVITIES(0, "Activities"),
        AUDIO(1, "Audio"),
        CHAT(2, "Chat"),
        CONTROL(3, "Control"),
        DISPLAY(4, "Display"),
        GAMEPLAY(5, "Gameplay"),
        INTERFACES(6, "Interfaces");

        @Getter
        int varbitValue;

        @Getter
        String string;

        SettingsTabType(int varbitValue, String string) {
            this.varbitValue = varbitValue;
            this.string = string;
        }

        public boolean isOpen() {
            return Utils.getVarBitValue(TAB_VARBIT) == this.varbitValue;
        }

        public boolean open() {
            if (!this.isOpen() && openSettingsWindow()) {
                Optional<Widget> tab = Query.widgets()
                        .inIndexPath(SETTINGS_WINDOW_PARENT)
                        .actionContains(this.getString())
                        .stream().findFirst();
                if (tab.map(Widget::click).orElse(false)) {
                    return Waiting.waitUntil(3500, 500,
                            () -> Utils.getVarBitValue(TAB_VARBIT) == this.varbitValue);
                }

            }
            return Utils.getVarBitValue(TAB_VARBIT) == this.varbitValue;
        }

    }


    public static boolean turnEscToCloseOn() {
        for (int i = 0; i < 3; i++) {
            if (!Options.isEscapeClosingEnabled() && openSettingsWindow() &&
                    SettingsTabType.CONTROL.open()) {
                Optional<Widget> escOption = Query.widgets().inIndexPath(134, 18)
                        .textContains("Esc closes the current")
                        .findFirst();
                Log.warn("Turning on Esc to Close");

                if (escOption.map(e -> e.scrollTo() && e.click()).orElse(false) &&
                        Waiting.waitUntil(4000, 400,
                                Options::isEscapeClosingEnabled)) {
                    Waiting.waitNormal(400, 50);
                    break;
                } else {
                    Waiting.waitNormal(400, 50);
                }
            }
        }
        Query.widgets()
                .inIndexPath(134)
                .actionContains("Close")
                .findFirst()
                .map(Widget::click);
        return Options.isEscapeClosingEnabled();
    }

}
