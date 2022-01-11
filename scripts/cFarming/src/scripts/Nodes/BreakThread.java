package scripts.Nodes;

import org.tribot.api.General;
import scripts.Data.Vars;

public class BreakThread implements Runnable {





    @Override
    public void run() {
        while (Vars.get().scriptStatus){
            General.sleep(50);
        }

    }
}

