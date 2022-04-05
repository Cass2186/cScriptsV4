package scripts.Listeners;

import org.tribot.api.General;
import org.tribot.api2007.Interfaces;
import org.tribot.api2007.Login;
import org.tribot.api2007.types.RSInterfaceChild;
import org.tribot.script.sdk.ChatScreen;
import org.tribot.script.sdk.Log;
import org.tribot.script.sdk.Waiting;
import scripts.Listeners.Model.ChatListener;
import scripts.Listeners.Model.RSInterfaceChildId;

import java.util.*;
import java.util.function.Supplier;

public class ChatObserver extends Thread {

    private final Set<ChatListener> listeners;
    private final List<String> stringList = new ArrayList<>();
    private final Map<RSInterfaceChildId, String> rsInterfaceChildMap;
    private final Supplier<Boolean> shouldNotify;

    private RSInterfaceChild lastClickContinue = null;

    public ChatObserver(Supplier<Boolean> shouldNotify) {
        this.rsInterfaceChildMap = new HashMap<>();
        this.listeners = new HashSet<>();

        this.shouldNotify = shouldNotify;
    }


    @Override
    public void run() {
        while (Login.getLoginState() != Login.STATE.INGAME) {
            General.sleep(500);
        }

        while (true) {
            Waiting.wait(75);
            if (Login.getLoginState() != Login.STATE.INGAME) continue;

            if (!ChatScreen.isOpen())
                continue;

            for (int s = 0; s < stringList.size(); s++) {
                Optional<String> msg = ChatScreen.getMessage();
                     /*   String lastInterfaceText = null;
                if (s != 0) {
                    lastInterfaceText = stringList.get(s - 1);
                }


                 if (lastInterfaceText != null && msg.isPresent() &&
                        msg.get().toLowerCase().contains(lastInterfaceText)) {

                    listeners.forEach(ChatListener::onAppear);
                } else */
                if (msg.isPresent() &&
                        msg.get().toLowerCase()
                                .contains(stringList.get(s).toLowerCase())) {
                    Log.info("Chat Message identified: " + s);
                    listeners.forEach(ChatListener::onAppear);
                }
            }
        }
    }


    public void addListener(ChatListener chatListener) {
        listeners.add(chatListener);
    }

    public void addString(String s) {
        stringList.add(s);
    }

}
