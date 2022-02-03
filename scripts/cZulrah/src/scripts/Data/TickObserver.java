package scripts.Data;

import dax.walker.utils.TribotUtil;
import lombok.AccessLevel;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import org.tribot.api.General;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Login;
import org.tribot.script.sdk.Log;
import org.tribot.script.sdk.Waiting;
import org.tribot.script.sdk.query.Query;
import org.tribot.script.sdk.types.Npc;
import scripts.Data.Patterns.*;
import scripts.Data.Phase.ZulrahPhase;
import scripts.cZulrah;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.BooleanSupplier;
import java.util.function.Function;
import java.util.stream.Collectors;

public class TickObserver extends Thread{

    private static final ZulrahPattern[] patterns = new ZulrahPattern[]{
            new ZulrahPatternA(),
            new ZulrahPatternB(),
            new ZulrahPatternC(),
            new ZulrahPatternD()
    };

    @Getter(AccessLevel.PACKAGE)
    public static Npc zulrah;


    public static ZulrahInstance instance;

    private final List<TickListener> listeners;
    //private final BooleanSupplier supplier;

    public TickObserver() {
        this.listeners = new ArrayList<>();
        //this.supplier = supplier;
    }

    @Override
    public void run() {

        while (cZulrah.isRunning.get()) {
            onGameTick();
            Waiting.wait(600);
        }
    }


    private void onGameTick() {
        if (!org.tribot.script.sdk.Login.isLoggedIn()) {
            return;
        }

        Optional<Npc> npc = Query.npcs().nameContains("Zulrah").findBestInteractable();
        if (npc.isPresent()) {
            zulrah = npc.get();
        }
        if (zulrah == null) {
            if (instance != null) {
                Log.log("Zulrah encounter has ended.");
                instance = null;
            }
            Log.log("Zulrah is null");
            return;
        }

        if (instance == null) {
            instance = new ZulrahInstance(zulrah);
            Log.log("[Debug]: Zulrah encounter has started.");
        }

        ZulrahPhase currentPhase = ZulrahPhase.valueOf(zulrah, instance.getStartLocation());

        if (instance.getPhase() == null) {
            instance.setPhase(currentPhase);
        } else if (!instance.getPhase().equals(currentPhase)) {
            ZulrahPhase previousPhase = instance.getPhase();
            instance.setPhase(currentPhase);
            instance.nextStage();

            Log.log("Zulrah phase has moved from {" +previousPhase +
                    "} -> {" +currentPhase +
                    "}, stage: {" +instance.getStage() +
                    "}" );
        }

        ZulrahPattern pattern = instance.getPattern();

        if (pattern == null) {
            int potential = 0;
            ZulrahPattern potentialPattern = null;

            for (ZulrahPattern p : patterns) {
                if (p.stageMatches(instance.getStage(), instance.getPhase())) {
                    potential++;
                    potentialPattern = p;
                }
            }

            if (potential == 1) {
                Log.log("[cZulrah]: Zulrah pattern identified: {" + potentialPattern + "}");

                instance.setPattern(potentialPattern);
            }
        } else if (pattern.canReset(instance.getStage()) && (instance.getPhase() == null || instance.getPhase().equals(pattern.get(0)))) {
            Log.log("[cZulrah]: Zulrah pattern has reset.");

            instance.reset();
        }
    }


    public void addListener() {

    }


}
