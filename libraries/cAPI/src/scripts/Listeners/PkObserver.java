package scripts.Listeners;

import dax.walker.utils.TribotUtil;
import org.tribot.api.General;
import org.tribot.api2007.Login;
import org.tribot.api2007.Players;
import org.tribot.script.sdk.Combat;
import org.tribot.script.sdk.MyPlayer;
import org.tribot.script.sdk.Waiting;
import org.tribot.script.sdk.query.Query;
import org.tribot.script.sdk.types.Player;

import java.util.*;
import java.util.function.Supplier;

public class PkObserver extends Thread {

    private final Set<PkListener> listeners;
    private final Supplier<Boolean> shouldNotify;

    // should notify will be "()-> true" in most cases
    public PkObserver(Supplier<Boolean> shouldNotify) {
        this.listeners = new HashSet<>();
        this.shouldNotify = shouldNotify;
    }

    @Override
    public void run() {
        while (Login.getLoginState() != Login.STATE.INGAME) {
            Waiting.wait(500);
        }

        while (true) {
            Waiting.wait(25);

            if (Login.getLoginState() != Login.STATE.INGAME) continue;

            if (!Combat.isInWilderness()) {
                Waiting.wait(500);
                continue;
            }

            if (shouldNotify.get()) {
                int maxLevel = MyPlayer.getCombatLevel() + Combat.getWildernessLevel();
                int minLevel = MyPlayer.getCombatLevel() - Combat.getWildernessLevel();

                List<Player> nearbyPlayers = Query.players()
                        .hasSkullIcon()
                        .maxLevel(maxLevel)
                        .minLevel(minLevel)
                        .toList();

                if (nearbyPlayers.size() > 0)
                    listeners.forEach(PkListener::onPkerNearby);
            }
        }
    }

    public void addListener(PkListener pkListener) {
        listeners.add(pkListener);
    }

}
