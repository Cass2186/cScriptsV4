package scripts.QuestPackages.KourendFavour.ArceuusLibrary.LibraryTasks;


import org.tribot.script.sdk.Log;

import scripts.QuestPackages.KourendFavour.ArceuusLibrary.Library.Bookcase;
import scripts.QuestPackages.KourendFavour.ArceuusLibrary.LibraryUtils;
import scripts.QuestPackages.KourendFavour.ArceuusLibrary.State;
import scripts.QuestSteps.QuestTask;
import scripts.Tasks.Priority;

import java.util.List;

public class FindBookTask implements QuestTask {
    private static FindBookTask quest;

    public static FindBookTask get() {
        return quest == null ? quest = new FindBookTask() : quest;
    }

    @Override
    public Priority priority() {
        return Priority.NONE;
    }

    @Override
    public void execute() {
        State.get().setStatus("Looking for books");


        List<Bookcase> bookcases = State.get().getLibrary().getBookcases();

        State.get().getCurrentAssignment()
                .map(LibraryUtils::findBook)
                .map(LibraryUtils::clickBookshelf)
                .orElseGet(() -> {;
                    bookcases.stream()
                            .filter(bookcase -> bookcase.getPossibleBooks().size() > 0)
                    .forEach(bookcase -> Log.log(String.format("Bookcase: %s, books: %s", bookcase, bookcase.getPossibleBooks())));
                    // force reset?
                    return false;
                });
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
    public String toString() {
        return "Looking for books";
    }

    @Override
    public boolean validate() {
        return State.get().getCurrentAssignment().isPresent()
                && !State.get().getCurrentBooks().contains(State.get().getCurrentAssignment().get());
    }

}