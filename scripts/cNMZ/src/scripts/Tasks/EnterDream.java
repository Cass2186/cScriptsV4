package scripts.Tasks;

import org.tribot.api.General;
import org.tribot.api2007.Game;
import org.tribot.api2007.Interfaces;
import org.tribot.api2007.types.RSInterface;
import scripts.InterfaceUtil;
import scripts.NmzData.NmzConst;
import scripts.cNMZ;

public class EnterDream implements Task {

    public boolean checkBossIsSelected(String bossName){
        RSInterface listInter = Interfaces.get(NmzConst.CHOSE_BOSSES_PARENT_ID, NmzConst.BOSS_LIST_CHILD_ID);
        if (listInter != null){
            RSInterface[] comps = listInter.getChildren();
            // have to do this so I can access the interface that is +2
            for (int i = 0; i < comps.length; i++){
                String txt = comps[i].getText();
                String underCaseBoss = bossName.toLowerCase();
                if (txt != null && txt.contains(underCaseBoss)) {
                    if (comps.length >= i+1) {
                        boolean b = comps[i + 1].getTextureID() == NmzConst.CHECK_MARK_TEXTURE_ID;
                        General.println("[Debug]: Boss: " + bossName + " selected? " + b);
                        return b;
                    }
                    General.println("[Debug]: Comps.length() is too short, return false");
                    return false;
                }
            }
        }
        return false;
    }


    @Override
    public String toString() {
        return "Entering Dream";
    }

    @Override
    public Priority priority() {
        return Priority.MEDIUM;
    }

    @Override
    public boolean validate() {
        return !Game.isInInstance();
    }

    @Override
    public void execute() {
        cNMZ.isRunning.set(false);
    }
}
