package scripts.Tasks.Construction.MahoganyHomes;

import javafx.scene.text.Text;
import org.tribot.script.sdk.ChatScreen;
import org.tribot.script.sdk.Log;
import org.tribot.script.sdk.Skill;
import org.tribot.script.sdk.Waiting;
import org.tribot.script.sdk.types.WorldTile;
import scripts.API.Priority;
import scripts.API.Task;
import scripts.Data.SkillTasks;
import scripts.Data.Vars;
import scripts.NpcChat;
import scripts.PathingUtil;
import scripts.Utils;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GetAssignment implements Task {
    static final Pattern CONTRACT_PATTERN = Pattern.compile("(Please could you g|G)o see (\\w*)[ ,][\\w\\s,-]*[?.] You can get another job once you have furnished \\w* home\\.");
    private static final Pattern CONTRACT_FINISHED = Pattern.compile("You have completed [\\d,]* contracts with a total of [\\d,]* points?\\.");


    @Override
    public Priority priority() {
        return Priority.MEDIUM;
    }

    @Override
    public boolean validate() {
        return Vars.get().currentTask != null &&
                Vars.get().currentTask.equals(SkillTasks.CONSTRUCTION) && ConsVars.get().isDoingHomes &&
                (ConsVars.get().mahoganyHome.isEmpty() || Utils.getVarBitValue(5983) == 0);
    }

    @Override
    public void execute() {
        if (Skill.CONSTRUCTION.getActualLevel() < 20) {
            getAssignment("Beginner Contract");
        } else if (Skill.CONSTRUCTION.getActualLevel() < 50) {
            getAssignment("Novice Contract");
        } else if (Skill.CONSTRUCTION.getActualLevel() < 70) {
            getAssignment("Adept Contract");
        } else if (Skill.CONSTRUCTION.getActualLevel() < 100) {
            getAssignment("Expert Contract");
        }

    }

    @Override
    public String toString() {
        return "Getting Contract";
    }

    @Override
    public String taskName() {
        return "Construction-  Mahogany Homes";
    }

    // Check for NPC dialog assigning or reminding us of a contract
    private void checkForAssignmentDialog() {
        Optional<String> dialog = ChatScreen.getMessage();
        if (dialog.isEmpty()) {
            return;
        }

        final String npcText = Utils.sanitizeMultilineText(dialog.get());
        Log.info("NPC TEXT "+ npcText);
        final Matcher startContractMatcher = CONTRACT_PATTERN.matcher(npcText);
        if (startContractMatcher.matches()) {
            final String name = startContractMatcher.group(2);
            for (final Home h : Home.values()) {
                if (h.getName().equalsIgnoreCase(name)) {
                    Log.info("Set home to " + h.toString());
                    ConsVars.get().mahoganyHome = Optional.of(h);
                    break;
                }
            }
        }
    }




    private void getAssignment(String contractType) {
        PathingUtil.walkToTile(new WorldTile(2990, 3366, 0));
        if (Utils.clickNPC("Amy", "Contract")) {
            NpcChat.waitForChatScreen();
            for (int i = 0; i < 30; i++) {
                if (ChatScreen.isSelectOptionOpen() &&
                        ChatScreen.selectOption(contractType)) {
                    Waiting.waitUntil(2500, 275, () -> ChatScreen.isClickContinueOpen());


                } else {
                    if (ChatScreen.isClickContinueOpen()) {
                        checkForAssignmentDialog();
                        ChatScreen.clickContinue();
                    }
                }
                if (ChatScreen.isClickContinueOpen()) {
                    checkForAssignmentDialog();
                    ChatScreen.clickContinue();
                }
            }
        }
    }
}
