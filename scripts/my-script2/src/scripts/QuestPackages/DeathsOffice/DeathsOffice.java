package scripts.QuestPackages.DeathsOffice;

import dax.walker.utils.AccurateMouse;
import dax.walker_engine.interaction_handling.NPCInteraction;
import org.tribot.api.General;
import org.tribot.api2007.*;
import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSNPC;
import org.tribot.api2007.types.RSObject;
import org.tribot.api2007.types.RSTile;
import org.tribot.script.sdk.ChatScreen;
import org.tribot.script.sdk.Widgets;
import org.tribot.script.sdk.types.Npc;
import org.tribot.script.sdk.types.Widget;
import scripts.*;
import scripts.QuestSteps.QuestTask;
import scripts.Requirements.ItemRequirement;
import scripts.Requirements.Requirement;
import scripts.Tasks.Priority;

import java.util.List;
import java.util.Optional;

public class DeathsOffice implements QuestTask {

    private static DeathsOffice quest;

    public static DeathsOffice get() {
        return quest == null ? quest = new DeathsOffice() : quest;
    }
   public static int[] GRAVESTONE_TIMER = { 548, 39 ,0};

    public static int DEATHS_DOMAIN_ID = 38426;
    public static int DEATHS_ID = 9855;
    public int GAME_SETTING_FOR_TIMER = 1697; // value is greater than 1 if the timer is present

    public static RSArea DEATH_ENTRANCE_AREA = new RSArea(new RSTile(3238, 3195, 0), new RSTile(3240, 3191, 0));
    public static RSArea LUMBRIDGE_SPAWN_AREA = new RSArea(new RSTile(3218, 3222, 0), new RSTile(3224, 3215, 0));

    private static final int
            ITEM_ACTION_INTERFACE_WINDOW = 193,
            NPC_TALKING_INTERFACE_WINDOW = 231,
            PLAYER_TALKING_INTERFACE_WINDOW = 217,
            SELECT_AN_OPTION_INTERFACE_WINDOW = 219,
            ITEM_IMAGE_ON_LEFT_WINDOW = 11,
            SINGLE_OPTION_DIALOGUE_WINDOW = 229;

    private static final int[] ALL_WINDOWS = new int[]{ITEM_ACTION_INTERFACE_WINDOW, ITEM_IMAGE_ON_LEFT_WINDOW, NPC_TALKING_INTERFACE_WINDOW, PLAYER_TALKING_INTERFACE_WINDOW, SELECT_AN_OPTION_INTERFACE_WINDOW, SINGLE_OPTION_DIALOGUE_WINDOW};


    private static String[] FIRST_TIME_ARRAY = {
            "How do I pay a gravestone fee?",
            "How long do I have to return to my gravestone?",
            "How do I know what will happen to my items when I die?",
            "I think I'm done here."
    };

    public static boolean shouldHandleDeath() {
        Optional<Widget> w = Widgets.get(GRAVESTONE_TIMER[0], GRAVESTONE_TIMER[1], GRAVESTONE_TIMER[2]);
        return w.isPresent() && w.get().isVisible();//Game.getSetting(1697) > 0;

    }

    private static boolean areItemsCollected() {
        RSNPC[] death = NPCs.findNearest("Death");
        if (death.length > 0)
            if (NpcChat.talkToNPC("Death")) {
                NPCInteraction.waitForConversationWindow();
                List<String> options = ChatScreen.getOptions();
                for (String s : options) {
                    if (s.contains("Can I collect the items from that gravestone now?"))
                        return true;
                }
            }
        return false;
    }


    private static boolean isFirstDeathChat() {
        RSNPC[] death = NPCs.findNearest("Death");
        if (death.length > 0)
            if (NpcChat.talkToNPC("Death")) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation();
                if (Interfaces.isInterfaceSubstantiated(219, 1)) {
                    List<String> options = ChatScreen.getOptions();
                    for (String s : options) {
                        if (s.contains("I think I'm done here.")) {
                            General.println("First chat = true");
                            return true;
                        }
                    }
                }
            }
        return false;
    }

    private static void handleFirstDeath() {
        RSNPC[] death = NPCs.findNearest("Death");
        if (death.length > 0) {

            if (isFirstDeathChat()) {
                General.println("[DeathUtil]: Handling first death conversation.");
                NPCInteraction.handleConversation(FIRST_TIME_ARRAY[0]);
                NPCInteraction.handleConversation(FIRST_TIME_ARRAY[1]);
                NPCInteraction.handleConversation(FIRST_TIME_ARRAY[2]);
                NPCInteraction.handleConversation(FIRST_TIME_ARRAY[3]);
                NPCInteraction.handleConversation();


                RSObject[] portal = Objects.findNearest(15, "Portal");
                if (portal.length > 0) {
                    General.println("[DeathUtil]: Leaving...");
                    if (!portal[0].isClickable())
                        portal[0].adjustCameraTo();

                    if (AccurateMouse.click(portal[0], "Use")) {
                        Timer.waitCondition(() -> Objects.findNearest(15, "Portal").length < 1, 8000, 12000);
                        Utils.modSleep();

                    }
                }
            }

        }
    }

    public static void collectItems() {
        if (shouldHandleDeath()) {
            General.println("[DeathUtil]: Getting items from death");

            handleFirstDeath();

            RSNPC[] death = NPCs.findNearest("Death");

            if (death.length < 1) {
                PathingUtil.walkToArea(DEATH_ENTRANCE_AREA);

                if (Utils.clickObj(DEATHS_DOMAIN_ID, "Enter")) {
                    Timer.abc2WaitCondition(() -> NPCs.findNearest("Death").length > 0, 7000, 10000);
                    Utils.modSleep();
                }
            }
            death = NPCs.findNearest("Death");
            if (death.length > 0) {
                if (NpcChat.talkToNPC("Death")) {
                    NPCInteraction.waitForConversationWindow();
                    NPCInteraction.handleConversation("Can I collect the items from that gravestone now?");
                    NPCInteraction.handleConversation("Bring my items here now; I'll pay your fee.");
                    NPCInteraction.handleConversation();
                    Timer.waitCondition(() -> Interfaces.isInterfaceSubstantiated(669, 10), 6000, 9000);
                }

                if (Interfaces.isInterfaceSubstantiated(669, 10))
                    if (Interfaces.get(669, 10, 0).click())
                        Utils.modSleep();

                if (Interfaces.isInterfaceSubstantiated(669, 1, 11))
                    if (Interfaces.get(669, 1, 11).click()) // closes
                        Utils.modSleep();

                if (!Interfaces.isInterfaceSubstantiated(661, 1, 11))
                    if (Utils.clickObj("Portal", "Use"))
                        Timer.abc2WaitCondition(() -> NPCs.findNearest("Death").length < 1, 6000, 9000);

            }
        }

    }
    @Override
    public Priority priority() {
        return Priority.HIGH;
    }

    @Override
    public boolean validate() {
        return shouldHandleDeath();
    }

    @Override
    public void execute() {
        collectItems();
        cQuesterV2.taskList.remove(this);
    }

    @Override
    public String questName() {
        return "Death's office";
    }

    @Override
    public boolean checkRequirements() {
        return false;
    }
    @Override
    public List<Requirement> getGeneralRequirements() {
        return null;
    }

    @Override
    public List<ItemRequirement> getBuyList() {
        return null;
    }

    @Override
    public boolean isComplete() {
        return false;
    }
}
