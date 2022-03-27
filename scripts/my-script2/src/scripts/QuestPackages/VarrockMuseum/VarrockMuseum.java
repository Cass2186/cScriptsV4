package scripts.QuestPackages.VarrockMuseum;

import dax.walker.utils.AccurateMouse;
import dax.walker_engine.interaction_handling.NPCInteraction;
import org.tribot.api.General;
import org.tribot.api2007.Game;
import org.tribot.api2007.Interfaces;
import org.tribot.api2007.Objects;
import org.tribot.api2007.types.*;
import org.tribot.script.sdk.Quest;
import scripts.*;
import scripts.QuestSteps.QuestTask;
import scripts.Requirements.ItemRequirement;
import scripts.Requirements.Requirement;
import scripts.Tasks.Priority;

import java.util.List;

public class VarrockMuseum implements QuestTask {

    private static VarrockMuseum quest;

    public static VarrockMuseum get() {
        return quest == null ? quest = new VarrockMuseum() : quest;
    }


    RSArea START_AREA = new RSArea(new RSTile(1758, 4957, 0), new RSTile(1760, 4955, 0));

    String LIZARD_Q1 = "How does a lizard regulate body heat?";
    String LIZARD_Q2 = "Who discovered how to kill lizards?";
    String LIZARD_Q3 = "How many eyes does a lizard have?";
    String LIZARD_Q4 = "What order do lizards belong to?";
    String LIZARD_Q5 = "What happens when a lizard becomes cold?";
    String LIZARD_Q6 = "Lizard skin is made of the same substance as?";

    String LIZARD_A1 = "Sunlight";
    String LIZARD_A2 = "The Slayer Masters";
    String LIZARD_A3 = "Three";
    String LIZARD_A4 = "Squamata";
    String LIZARD_A5 = "It becomes sleepy";
    String LIZARD_A6 = "Hair";

    String TORTOISE_A1 = "Mibbiwocket";
    String TORTOISE_A2 = "Vegetables";
    String TORTOISE_A3 = "Admiral Bake";
    String TORTOISE_A4 = "Hard shell";
    String TORTOISE_A5 = "Twenty years";
    String TORTOISE_A6 = "Gnomes";

    String DRAGON_A1 = "Runite";
    String DRAGON_A2 = "Anti dragon-breath shield"; // spelling might be wrong
    String DRAGON_A3 = "Unknown";
    String DRAGON_A4 = "Elemental";
    String DRAGON_A5 = "Old battle sites";
    String DRAGON_A6 = "Twelve";

    String WYRM_A1 = "Climate change";
    String WYRM_A2 = "Two";
    String WYRM_A3 = "Asgarnia";
    String WYRM_A4 = "Reptiles";
    String WYRM_A5 = "Dragons";
    String WYRM_A6 = "Below room temperature";

    //**** EAST ROOM ****//
    String SNAIL_A1 = "It is resistant to acid";
    String SNAIL_A2 = "Spitting acid";
    String SNAIL_A3 = "Fireproof oil";
    String SNAIL_A4 = "Acid-spitting snail";
    String SNAIL_A5 = "Contracting and stretching";
    String SNAIL_A6 = "An operculum";

    String SNAKE_A1 = "Stomach acid";
    String SNAKE_A2 = "Tongue";
    String SNAKE_A3 = "Seeing how you smell";
    String SNAKE_A4 = "Constriction";
    String SNAKE_A5 = "Squamata";
    String SNAKE_A6 = "Anywhere";

    String SLUG_A1 = "Nematocysts";
    String SLUG_A2 = "The researchers keep vanishing";
    String SLUG_A3 = "Seaweed";
    String SLUG_A4 = "Defense or display";
    String SLUG_A5 = "Ardougne";
    String SLUG_A6 = "They have a hard shell";

    String MONKEY_A1 = "Simian";
    String MONKEY_A2 = "Harmless";
    String MONKEY_A3 = "Bitternuts";
    String MONKEY_A4 = "Harmless";
    String MONKEY_A5 = "Red";
    String MONKEY_A6 = "Seaweed";

    //*******SOUTH ROOM******//
    String KALPHITE_A1 = "Pasha";
    String KALPHITE_A2 = "Worker";
    String KALPHITE_A3 = "Lamellae";
    String KALPHITE_A4 = "Carnivores";
    String KALPHITE_A5 = "Scarab beetles";
    String KALPHITE_A6 = "Scabaras";

    String TERROR_BIRD_A1 = "Anything";
    String TERROR_BIRD_A2 = "Gnomes";
    String TERROR_BIRD_A3 = "Eating plants";
    String TERROR_BIRD_A4 = "Four";
    String TERROR_BIRD_A5 = "Stones";
    String TERROR_BIRD_A6 = "0";

    // ******* WEST ROOM *******//
    String PENGUIN_A1 = "Sight";
    String PENGUIN_A2 = "Planning";
    String PENGUIN_A3 = "A layer of fat";
    String PENGUIN_A4 = "Cold";
    String PENGUIN_A5 = "Social";
    String PENGUIN_A6 = "During breeding";

    String MOLE_A1 = "Subterranean";
    String MOLE_A2 = "They dig holes";
    String MOLE_A3 = "Wyson the Gardener";
    String MOLE_A4 = "A labour";
    String MOLE_A5 = "Insects and other invertebrates";
    String MOLE_A6 = "The Talpidae family";

    String CAMEL_A1 = "Toxic dung";
    String CAMEL_A2 = "Two";
    String CAMEL_A3 = "Omnivore";
    String CAMEL_A4 = "Annoyed";
    String CAMEL_A5 = "Al Kharid";
    String CAMEL_A6 = "Milk";

    String LEECH_A1 = "Water";
    String LEECH_A2 = "'Y'-shaped";
    String LEECH_A3 = "Apples";
    String LEECH_A4 = "Environment";
    String LEECH_A5 = "They attack by jumping";
    String LEECH_A6 = "It doubles in size";

    public void leech() {
        PathingUtil.walkToTile(new RSTile(1744, 4962, 0), 2, false);
        PathingUtil.movementIdle();

        answerQuestion(LEECH_A1, 3685);
        answerQuestion(LEECH_A2, 3685);
        answerQuestion(LEECH_A3, 3685);
        answerQuestion(LEECH_A4, 3685);
        answerQuestion(LEECH_A5, 3685);
        answerQuestion(LEECH_A6, 3685);
    }

    public void camel() {
        cQuesterV2.status = "Camel";
        PathingUtil.walkToTile(new RSTile(1737, 4962, 0), 2, false);
        PathingUtil.movementIdle();
        answerQuestion(CAMEL_A1, 3679, 24609);
        answerQuestion(CAMEL_A2, 3679, 24609);
        answerQuestion(CAMEL_A3, 3679, 24609);
        answerQuestion(CAMEL_A4, 3679, 24609);
        answerQuestion(CAMEL_A5, 3679, 24609);
        answerQuestion(CAMEL_A6, 3679, 24609);
    }

    public void mole() {
        cQuesterV2.status = "Mole";
        PathingUtil.walkToTile(new RSTile(1735, 4958, 0), 2, false);
        PathingUtil.movementIdle();
        answerQuestion(MOLE_A1, 3678, 24611);
        answerQuestion(MOLE_A2, 3678, 24611);
        answerQuestion(MOLE_A3, 3678, 24611);
        answerQuestion(MOLE_A4, 3678, 24611);
        answerQuestion(MOLE_A5, 3678, 24611);
        answerQuestion(MOLE_A6, 3678, 24611);
    }

    public void penguin() {
        PathingUtil.walkToTile(new RSTile(1742, 4958, 0), 1, false);
        PathingUtil.movementIdle();
        answerQuestion(PENGUIN_A1, 3673);
        answerQuestion(PENGUIN_A2, 3673);
        answerQuestion(PENGUIN_A3, 3673);
        answerQuestion(PENGUIN_A4, 3673);
        answerQuestion(PENGUIN_A5, 3673);
        answerQuestion(PENGUIN_A6, 3673);
    }

    public void terrorBird() {
        cQuesterV2.status = "Terror Bird";
        PathingUtil.walkToTile(new RSTile(1756, 4940, 0), 1, false);
        PathingUtil.movementIdle();
        answerQuestion(TERROR_BIRD_A1, 3683);
        answerQuestion(TERROR_BIRD_A2, 3683);
        answerQuestion(TERROR_BIRD_A3, 3683);
        answerQuestion(TERROR_BIRD_A4, 3683);
        answerQuestion(TERROR_BIRD_A5, 3683);
        answerQuestion(TERROR_BIRD_A6, 3683);
    }


    public void startQuest() {
        cQuesterV2.status = "Starting miniquest.";
        PathingUtil.walkToArea(START_AREA);
        if (NpcChat.talkToNPC("Orlando Smith")) {
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation("Sure thing.");
            NPCInteraction.handleConversation();
        }
    }

    public void answerQuestion(String answer, int gameSetting) {
        if (RSVarBit.get(gameSetting).getValue() != 3) {
            if (Interfaces.get(533, 28) == null && Game.getSetting(gameSetting) != 3) {
                RSObject[] plaque = Objects.findNearest(20, "Plaque");
                if (plaque.length > 0) {
                    if (AccurateMouse.click(plaque[0], "Study"))
                        Timer.waitCondition(() -> Interfaces.get(533, 28) != null, 5000, 7000);
                }
            }
            if (Interfaces.get(533, 28) != null) {
                for (int i = 29; i < 32; i++) {
                    General.sleep(10, 30);
                    RSInterfaceChild face = Interfaces.get(533, i);
                    if (face != null && face.getText().contains(answer)) {
                        Utils.idle(200, General.randomSD(1400, 250));
                        if (Interfaces.get(533, i).click()) {
                            NPCInteraction.waitForConversationWindow();
                            NPCInteraction.handleConversation();
                            if (Game.getSetting(gameSetting) == 3) {
                                break;
                            }
                        }
                    }
                }
            }
        }
    }

    public void answerQuestion(String answer, int gameSetting, int plaqueId) {
        if (RSVarBit.get(gameSetting).getValue() != 3) {
            if (Interfaces.get(533, 28) == null && Game.getSetting(gameSetting) != 3) {
                RSObject[] plaque = Objects.findNearest(20, plaqueId);
                if (plaque.length > 0) {
                    if (AccurateMouse.click(plaque[0], "Study"))
                        Timer.waitCondition(() -> Interfaces.get(533, 28) != null, 5000, 7000);
                }
            }
            if (Interfaces.get(533, 28) != null) {
                for (int i = 29; i < 32; i++) {
                    General.sleep(10, 30);
                    RSInterfaceChild face = Interfaces.get(533, i);
                    if (face != null && face.getText().contains(answer)) {
                        Utils.idle(500, 1250);
                        if (Interfaces.get(533, i).click()) {
                            NPCInteraction.waitForConversationWindow();
                            NPCInteraction.handleConversation();
                            if (Game.getSetting(gameSetting) == 3) {
                                break;
                            }
                        }
                    }
                }
            }
        }
    }


    public void lizard() { // plaque = 24605
        PathingUtil.walkToTile(new RSTile(1742, 4977, 0), 1, true);
        PathingUtil.movementIdle();
        answerQuestion(LIZARD_A1, 3675, 24605);
        answerQuestion(LIZARD_A2, 3675, 24605);
        answerQuestion(LIZARD_A3, 3675, 24605);
        answerQuestion(LIZARD_A4, 3675);
        answerQuestion(LIZARD_A5, 3675);
        answerQuestion(LIZARD_A6, 3675);
    }

    public void tortoise() {
        PathingUtil.walkToTile(new RSTile(1753, 4977, 0), 1, true);
        PathingUtil.movementIdle();

        answerQuestion(TORTOISE_A1, 3680);
        answerQuestion(TORTOISE_A2, 3680);
        answerQuestion(TORTOISE_A3, 3680);
        answerQuestion(TORTOISE_A4, 3680);
        answerQuestion(TORTOISE_A5, 3680);
        answerQuestion(TORTOISE_A6, 3680);
    }

    public void dragon() {
        PathingUtil.walkToTile(new RSTile(1768, 4977, 0), 1, true);
        PathingUtil.movementIdle();
        answerQuestion(DRAGON_A1, 3672);
        answerQuestion(DRAGON_A2, 3672);
        answerQuestion(DRAGON_A3, 3672);
        answerQuestion(DRAGON_A4, 3672);
        answerQuestion(DRAGON_A5, 3672);
        answerQuestion(DRAGON_A6, 3672);
    }

    public void wyvrn() {
        cQuesterV2.status = "Wyvrn";
        PathingUtil.walkToTile(new RSTile(1778, 4977, 0), 1, true);
        PathingUtil.movementIdle();
        answerQuestion(WYRM_A1, 3681);
        answerQuestion(WYRM_A2, 3681);
        answerQuestion(WYRM_A3, 3681);
        answerQuestion(WYRM_A4, 3681);
        answerQuestion(WYRM_A5, 3681);
        answerQuestion(WYRM_A6, 3681);
    }

    public void snail() {
        cQuesterV2.status = "Snail";
        PathingUtil.walkToTile(new RSTile(1776, 4962, 0), 1, true);
        PathingUtil.movementIdle();
        answerQuestion(SNAIL_A1, 3674);
        answerQuestion(SNAIL_A2, 3674);
        answerQuestion(SNAIL_A3, 3674);
        answerQuestion(SNAIL_A4, 3674);
        answerQuestion(SNAIL_A5, 3674);
        answerQuestion(SNAIL_A6, 3674);
    }

    public void snake() {
        cQuesterV2.status = "Snake";
        PathingUtil.walkToTile(new RSTile(1782, 4962, 0), 1, true);
        PathingUtil.movementIdle();
        answerQuestion(SNAKE_A1, 3677);
        answerQuestion(SNAKE_A2, 3677);
        answerQuestion(SNAKE_A3, 3677);
        answerQuestion(SNAKE_A4, 3677);
        answerQuestion(SNAKE_A5, 3677);
        answerQuestion(SNAKE_A6, 3677);
    }

    public void seaSlug() {
        cQuesterV2.status = "Sea Slug";
        PathingUtil.walkToTile(new RSTile(1781, 4958, 0), 1, true);
        PathingUtil.movementIdle();
        answerQuestion(SLUG_A1, 3682, 24616);
        answerQuestion(SLUG_A2, 3682);
        answerQuestion(SLUG_A3, 3682);
        answerQuestion(SLUG_A4, 3682);
        answerQuestion(SLUG_A5, 3682);
        answerQuestion(SLUG_A6, 3682);
    }

    public void monkey() {
        cQuesterV2.status = "Monkey";
        PathingUtil.walkToTile(new RSTile(1774, 4958, 0), 1, true);
        PathingUtil.movementIdle();
        answerQuestion(MONKEY_A1, 3676);
        answerQuestion(MONKEY_A2, 3676);
        answerQuestion(MONKEY_A3, 3676);
        answerQuestion(MONKEY_A4, 3676);
        answerQuestion(MONKEY_A5, 3676);
        answerQuestion(MONKEY_A6, 3676);
    }

    public void kalphite() {
        cQuesterV2.status = "Kalphite";
        PathingUtil.walkToTile(new RSTile(1761, 4938, 0), 1, true);
        answerQuestion(KALPHITE_A1, 3684);
        answerQuestion(KALPHITE_A2, 3684);
        answerQuestion(KALPHITE_A3, 3684);
        answerQuestion(KALPHITE_A4, 3684);
        answerQuestion(KALPHITE_A5, 3684);
        answerQuestion(KALPHITE_A6, 3684);
    }

    public void finishQuest() {
        cQuesterV2.status = "Finishing";
        PathingUtil.walkToArea(START_AREA);
        if (NpcChat.talkToNPC("Orlando Smith")) {
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation();
        }
    }

    @Override
    public void execute() {
        if (Game.getSetting(1014) == 0) {
            startQuest();
        }
        if (RSVarBit.get(3675).getValue() == 0 || RSVarBit.get(3675).getValue() == 1 || RSVarBit.get(3675).getValue() == 2) {
            lizard();
        }
        if (RSVarBit.get(3675).getValue() == 3 && (RSVarBit.get(3680).getValue() == 0 || RSVarBit.get(3680).getValue() == 1 || RSVarBit.get(3680).getValue() == 2)) {
            tortoise();
        }
        if (RSVarBit.get(3680).getValue() == 3 && (RSVarBit.get(3672).getValue() == 0 || RSVarBit.get(3672).getValue() == 1 || RSVarBit.get(3672).getValue() == 2)) {
            dragon();
        }
        if (RSVarBit.get(3672).getValue() == 3 && (RSVarBit.get(3681).getValue() == 0 || RSVarBit.get(3681).getValue() == 1 || RSVarBit.get(3681).getValue() == 2)) {
            wyvrn();
        }
        if (RSVarBit.get(3681).getValue() == 3 && (RSVarBit.get(3674).getValue() == 0 || RSVarBit.get(3674).getValue() == 1 || RSVarBit.get(3674).getValue() == 2)) {
            snail();
        }
        if (RSVarBit.get(3674).getValue() == 3 && (RSVarBit.get(3677).getValue() == 0 || RSVarBit.get(3677).getValue() == 1 || RSVarBit.get(3677).getValue() == 2)) {
            snake();
        }
        if (RSVarBit.get(3677).getValue() == 3 && (RSVarBit.get(3682).getValue() == 0 || RSVarBit.get(3682).getValue() == 1 || RSVarBit.get(3682).getValue() == 2)) {
            seaSlug();
        }
        if (RSVarBit.get(3682).getValue() == 3 && (RSVarBit.get(3676).getValue() == 0 || RSVarBit.get(3676).getValue() == 1 || RSVarBit.get(3676).getValue() == 2)) {
            monkey();
        }
        if (RSVarBit.get(3676).getValue() == 3 && (RSVarBit.get(3684).getValue() == 0 || RSVarBit.get(3684).getValue() == 1 || RSVarBit.get(3684).getValue() == 2)) {
            kalphite();
        }
        if (RSVarBit.get(3684).getValue() == 3 && (RSVarBit.get(3683).getValue() == 0 || RSVarBit.get(3683).getValue() == 1 || RSVarBit.get(3683).getValue() == 2)) {
            terrorBird();
        }
        if (RSVarBit.get(3683).getValue() == 3 && (RSVarBit.get(3673).getValue() == 0 || RSVarBit.get(3673).getValue() == 1 || RSVarBit.get(3673).getValue() == 2)) {
            penguin();
        }
        if (RSVarBit.get(3673).getValue() == 3 && (RSVarBit.get(3678).getValue() != 3)) {
            mole();
        }
        if (RSVarBit.get(3678).getValue() == 3 && (RSVarBit.get(3679).getValue() != 3)) {
            camel();
        }
        if (RSVarBit.get(3679).getValue() == 3 && RSVarBit.get(3685).getValue() != 3) {
            leech();
        }
        if (RSVarBit.get(3685).getValue() == 3) {
            finishQuest();
            Utils.closeQuestCompletionWindow();
        }
        if (Utils.getVarBitValue(3688) != 0) {
            Utils.closeQuestCompletionWindow();
            cQuesterV2.taskList.remove(VarrockMuseum.get());
        }

    }

    @Override
    public Priority priority() {
        return Priority.LOW;
    }

    @Override
    public boolean validate() {
        return cQuesterV2.taskList.get(0).equals(VarrockMuseum.get());
    }


    @Override
    public String questName() {
        return "Varrock Museum";
    }

    @Override
    public boolean checkRequirements() {
        return true;
    }

    @Override
    public List<Requirement> getGeneralRequirements() {
        return null;
    }

    @Override
    public List<ItemRequirement> getBuyList() {
        return null;
    }
}
