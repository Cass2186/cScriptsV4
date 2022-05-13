package scripts.Data;

import lombok.Data;
import org.tribot.api.General;
import org.tribot.script.sdk.GameState;
import org.tribot.script.sdk.Skill;
import scripts.Timer;

@Data
public class SkillerSettings {

    /**
     * This is to be used to save settings in the GUI at a later date
     */

    private GuiVars vars;
}
