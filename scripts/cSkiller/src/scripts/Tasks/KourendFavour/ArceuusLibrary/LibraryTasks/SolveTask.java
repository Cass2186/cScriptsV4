package scripts.Tasks.KourendFavour.ArceuusLibrary.LibraryTasks;

import dax.shared.helpers.WorldHelper;
import org.tribot.api.util.Sorting;
import org.tribot.api2007.Player;
import org.tribot.api2007.WorldHopper;
import org.tribot.script.sdk.Log;
import scripts.API.Priority;
import scripts.API.Task;
import scripts.Tasks.KourendFavour.ArceuusLibrary.Library.Bookcase;
import scripts.Tasks.KourendFavour.ArceuusLibrary.Library.SolvedState;
import scripts.Tasks.KourendFavour.ArceuusLibrary.LibraryUtils;
import scripts.Tasks.KourendFavour.ArceuusLibrary.State;


import java.util.Arrays;

public class SolveTask implements Task {

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
    public String taskName() {
        return "Arceuus Library";
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
}