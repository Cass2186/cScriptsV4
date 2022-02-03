package scripts.QuestSteps;

import dax.walker_engine.interaction_handling.NPCInteraction;
import lombok.Getter;
import lombok.Setter;
import org.tribot.api.General;
import org.tribot.api2007.Combat;
import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSNPC;
import org.tribot.api2007.types.RSTile;
import scripts.*;
import scripts.Requirements.Requirement;
import scripts.Timer;

import java.util.*;

public class KillNpcStep implements QuestStep {

    private int npcID;
    private RSArea area;
    public RSTile npcTile;
    private List<String> listChatOptions;
    private String npcName;
    private RSNPC rsnpc;
    private boolean usePrayer;
    private PrayerType prayerType;

    @Getter
    @Setter
    private String interactionString = "Attack";

    @Getter
    @Setter
    private boolean useLocalNav = false;

    @Getter
    @Setter
    private boolean useBlindWalk = false;

    @Getter
    @Setter
    private int radius = 3;

    @Getter
    protected final List<Requirement> requirements = new ArrayList<>();


    public KillNpcStep(int npcID, RSArea npcArea) {
        this.npcID = npcID;
        this.area = npcArea;
    }

    public KillNpcStep(int npcID, RSTile npcTile) {
        this.npcID = npcID;
        this.npcTile = npcTile;
    }

    public KillNpcStep(RSNPC rsnpc, RSTile npcTile) {
        this.rsnpc = rsnpc;
        this.npcTile = npcTile;
    }


    public KillNpcStep(String npcString, RSArea npcArea) {
        this.npcName = npcString;
        this.area = npcArea;
    }

    public KillNpcStep(String npcString, RSTile npcTile) {
        this.npcName = npcString;
        this.npcTile = npcTile;
    }


    public KillNpcStep(int npcID, RSArea npcArea, Requirement... requirements) {
        this.npcID = npcID;
        this.area = npcArea;
        this.requirements.addAll(Arrays.asList(requirements));
    }


    public KillNpcStep(RSNPC rsnpc, RSTile npcTile, Requirement... requirements) {
        this.rsnpc = rsnpc;
        this.npcTile = npcTile;
        this.requirements.addAll(Arrays.asList(requirements));
    }


    public KillNpcStep(String npcString, RSArea npcArea, Requirement... requirements) {
        this.npcName = npcString;
        this.area = npcArea;
        this.requirements.addAll(Arrays.asList(requirements));
    }

    public KillNpcStep(String npcString, RSTile npcTile, Requirement... requirements) {
        this.npcName = npcString;
        this.npcTile = npcTile;
        this.requirements.addAll(Arrays.asList(requirements));
    }

    public KillNpcStep(int npcID, RSTile npcTile, Requirement... requirements) {
        this.npcID = npcID;
        this.npcTile = npcTile;
        this.requirements.addAll(Arrays.asList(requirements));
    }


    public KillNpcStep(int npcID, RSArea npcArea, String[] chatText) {
        this.npcID = npcID;
        this.area = npcArea;
        this.listChatOptions = Arrays.asList(chatText);
    }


    public KillNpcStep(int npcID, RSArea npcArea, String[] chatText, Requirement... requirements) {
        this.npcID = npcID;
        this.area = npcArea;
        this.listChatOptions = Arrays.asList(chatText);
        this.requirements.addAll(Arrays.asList(requirements));
    }

    public KillNpcStep(String name, RSArea npcArea, String[] chatText) {
        this.npcName = name;
        this.area = npcArea;
        this.listChatOptions = Arrays.asList(chatText);

    }

    public KillNpcStep(String name, RSArea npcArea, String[] chatText, Requirement... requirements) {
        this.npcName = name;
        this.area = npcArea;
        this.listChatOptions = Arrays.asList(chatText);
        this.requirements.addAll(Arrays.asList(requirements));
    }


    public KillNpcStep(int npcID, RSTile npcTile, String[] chatText) {
        this.npcID = npcID;
        this.npcTile = npcTile;
        this.listChatOptions = Arrays.asList(chatText);
    }


    public KillNpcStep(int npcID, RSTile npcTile, String[] chatText, Requirement... requirements) {
        this.npcID = npcID;
        this.npcTile = npcTile;
        this.listChatOptions = Arrays.asList(chatText);
        this.requirements.addAll(Arrays.asList(requirements));
    }

    public KillNpcStep(String npcName, RSTile npcTile, String[] chatText) {
        this.npcName = npcName;
        this.npcTile = npcTile;
        this.listChatOptions = Arrays.asList(chatText);
    }


    public KillNpcStep(String npcName, RSTile npcTile, String[] chatText, Requirement... requirements) {
        this.npcName = npcName;
        this.npcTile = npcTile;
        this.listChatOptions = Arrays.asList(chatText);
        this.requirements.addAll(Arrays.asList(requirements));
    }


    public void addDialogStep(String dialog) {
        if (listChatOptions == null) {
            listChatOptions = new ArrayList<>();
        }
        listChatOptions.add(dialog);
    }

    public void addDialogSteps(String... dialog) {
        if (listChatOptions == null) {
            listChatOptions = new ArrayList<>();
        }

        Collections.addAll(listChatOptions, dialog);
    }


    public void execute() {
        if (requirements.stream().anyMatch(r -> !r.check())) {
            General.println("[KillNpcStep]: We failed a requirement to execute this NPCStep");
            return;
        }

        if (this.area != null) {
            General.println("[KillNpcStep]: Navigating to tile for KillNpcStep");
            if (!PathingUtil.localNavigation(this.area.getRandomTile(), 2))
                PathingUtil.walkToArea(this.area, false);

        } else if (this.npcTile != null) {
            General.println("[KillNpcStep]: Navigating to tile for KillNpcStep");
            if (!PathingUtil.localNavigation(this.npcTile, 2))
                PathingUtil.walkToTile(this.npcTile, 3, false);
        }

        // if we fail to click it returns otherwise handles convo & waits to be attacked
        if (this.rsnpc != null &&
                !Utils.clickNPC(this.rsnpc, this.interactionString, false)) {
            return;

        } else if (this.npcName != null &&
                !Utils.clickNPC(this.npcName, this.interactionString)) {
            return;

        } else if (!Utils.clickNPC(this.npcID, this.interactionString))
            return;

        if (this.listChatOptions.size() > 0) {
            // we are handling a conversation
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation(listChatOptions.toArray(String[]::new));
        }
        Timer.waitCondition(Combat::isUnderAttack, 3500, 4000);
        General.println("[NPCStep]: Execution finished");
    }

    @Override
    public void addDialogStep(String... dialog) {

    }

    @Override
    public void addSubSteps(QuestStep... substep) {

    }

    @Override
    public void addSubSteps(Collection<QuestStep> substeps) {

    }
}
