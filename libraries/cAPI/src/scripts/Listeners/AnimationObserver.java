package scripts.Listeners;


import org.tribot.api.General;
import org.tribot.api2007.Login;
import org.tribot.api2007.Player;
import org.tribot.script.sdk.Log;
import org.tribot.script.sdk.query.Query;
import org.tribot.script.sdk.types.Npc;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.function.Supplier;

public class AnimationObserver extends Thread {

    private final Set<AnimationListener> listeners;
    private final Supplier<Boolean> shouldNotify;
    private final String npcName;

    private int lastAnimation = -1;

    public AnimationObserver(Supplier<Boolean> shouldNotify, String npcName) {
        this.listeners = new HashSet<>();
        this.shouldNotify = shouldNotify;
        this.npcName = npcName;
    }

    @Override
    public void run() {
        while (Login.getLoginState() != Login.STATE.INGAME) {
            General.sleep(500);
        }

        while (true) {
            General.sleep(50);
            if (Login.getLoginState() != Login.STATE.INGAME) continue;

            Optional<Npc> bestInteractable = Query.npcs().nameContains(this.npcName).findBestInteractable();

            if (bestInteractable.isPresent()){
                int currentAnimation = bestInteractable.get().getAnimation();
                if (shouldNotify.get() && lastAnimation != currentAnimation) {
                //    Log.log("Animation changed");
                    listeners.forEach(e -> e.onAnimationChanged(currentAnimation));
                }
                lastAnimation = currentAnimation;
            }

        }
    }

    public void addListener(AnimationListener animationListener) {
        listeners.add(animationListener);
    }
}