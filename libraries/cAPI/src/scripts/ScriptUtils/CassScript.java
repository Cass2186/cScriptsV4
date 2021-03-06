package scripts.ScriptUtils;

import dax.api_lib.WebWalkerServerApi;
import dax.api_lib.models.DaxCredentials;
import dax.api_lib.models.DaxCredentialsProvider;
import dax.teleports.Teleport;
import org.tribot.script.sdk.Log;
import org.tribot.script.sdk.MyPlayer;
import org.tribot.script.sdk.Skill;
import org.tribot.script.sdk.script.ScriptConfig;
import org.tribot.script.sdk.script.TribotScript;
import scripts.Logger;

import java.util.Arrays;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicBoolean;

public class CassScript {

    private static final Logger LOGGER = new Logger(CassScript.class);

    public AtomicBoolean isRunning = new AtomicBoolean(true);

    public void populateInitialMap(HashMap<Skill, Integer> skillStartXpMap) {
        Log.debug("[Debug]: Populating initial skills xp HashMap");
        for (Skill s : Skill.values()) {
            skillStartXpMap.put(s, s.getXp());
        }
    }

    public long getRunningTimeMs(long startTime){
        return  System.currentTimeMillis() - startTime;
    }

    /**
     * Assign this to variable called running time
     * @param startTime is a var represeting start time
     * @return
     */
    public long updateRunTimeOnBreakStart(long startTime){

        return  System.currentTimeMillis() - startTime;
    }


    public void initializeDax() {
        Teleport.blacklistTeleports(Teleport.GLORY_KARAMJA, Teleport.RING_OF_WEALTH_MISCELLANIA);

        WebWalkerServerApi.getInstance().setDaxCredentialsProvider(new DaxCredentialsProvider() {
            @Override
            public DaxCredentials getDaxCredentials() {
                return new DaxCredentials("sub_DPjXXzL5DeSiPf", " PUBLIC-KEY");
            }
        });
    }

    public boolean checkBasicShouldRunChecks() {
        if (!MyPlayer.isMember()) {
            Log.error("Ran out of membership");
            return false;
        }
        return true;
    }

    public boolean checkBasicShouldRunChecks(boolean... checks) {
        if (!MyPlayer.isMember()) {
            Log.error("Ran out of membership");
            return false;
        } else {
            for (boolean c : checks) {
                if (!c)
                    return false;
            }
        }
        return true;
    }
}
