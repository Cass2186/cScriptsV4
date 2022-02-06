package scripts.QuestPackages.KourendFavour.ArceuusLibrary.LibraryTasks;

import dax.shared.helpers.WorldHelper;
import org.tribot.api.util.Sorting;
import org.tribot.api2007.Player;
import org.tribot.api2007.WorldHopper;
import org.tribot.script.sdk.Log;
;
import scripts.QuestPackages.KourendFavour.ArceuusLibrary.Library.Bookcase;
import scripts.QuestPackages.KourendFavour.ArceuusLibrary.Library.SolvedState;
import scripts.QuestPackages.KourendFavour.ArceuusLibrary.LibraryUtils;
import scripts.QuestPackages.KourendFavour.ArceuusLibrary.State;
import scripts.QuestSteps.QuestTask;

import scripts.Requirements.ItemRequirement;
import scripts.Requirements.Requirement;
import scripts.Tasks.Priority;

import java.util.Arrays;
import java.util.List;

public class SolveTask implements QuestTask {
    private static SolveTask quest;

    public static SolveTask get() {
        return quest == null ? quest = new SolveTask() : quest;
    }
   // private static final JustLogger LOGGER = new JustLogger(SolveTask.class);

    /**
     * TODO
     * - make it not check both sides of a sticking out bit, it's weird
     * - Needs trade in books for xp task
     * -  wtf it resets the library so often :(
     * - Add painting of tiles
     * - Rounds books per/h
     * - Fix sleep after misclicking
     * - Stop cpu getting crushed (Maybe background thread for books in rooms? log to see how often it's happening)
     *    - only check once per room entry
     *
     * - Before clicking for book, check we didn't just do it?
     * - Walk to actual tile, not to random one
     *
     * @return
     */

    @Override
    public Priority priority() {
        return Priority.HIGH;
    }

    @Override
    public String toString() {
        return "Solving library";
    }
    @Override
    public boolean validate() {
        return State.get().getLibrary().getState() == SolvedState.NO_DATA;
    }

    @Override
    public void execute() {
        State.get().setStatus("Solving the library");
       // LOGGER.info("Solving!");

        Bookcase[] bookcasesArray = State.get().getLibrary().getBookcasesOnLevel(Player.getPosition().getPlane())
                .stream()
                .filter(bookcase -> !bookcase.isBookSet())
                .filter(bookcase ->
                        bookcase.getPossibleBooks().stream().anyMatch(book -> !book.isDarkManuscript()) || State.get().getLibrary().getState() == SolvedState.NO_DATA)
                .toArray(Bookcase[]::new);

        if (bookcasesArray.length == 0) {
            bookcasesArray =  State.get().getLibrary().getBookcases()
                    .stream()
                    .filter(bookcase -> !bookcase.isBookSet())
                    .filter(bookcase ->
                            bookcase.getPossibleBooks().stream().anyMatch(book -> !book.isDarkManuscript()) || State.get().getLibrary().getState() == SolvedState.NO_DATA)
                    .toArray(Bookcase[]::new);
           // LOGGER.info(String.format("We are unsure on %s bookcases total", bookcasesArray.length));

            if (bookcasesArray.length == 0) {
                Log.log("[Debug]: We're confused. Hop to reset");

                int world = WorldHopper.getRandomWorld(true, false);
                if (!WorldHelper.isPvp(world)) {
                    WorldHopper.changeWorld(world);
                }
            }
        } else {

            //LOGGER.info(String.format("We are unsure on %s bookcases this floor", bookcasesArray.length));
        }


        Sorting.sortByDistance(bookcasesArray, Player.getPosition(), true);

        Arrays.stream(bookcasesArray).findFirst()
                .map(LibraryUtils::clickBookshelf)
                .orElse(false);
    }

    @Override
    public String questName() {
        return null;
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
}