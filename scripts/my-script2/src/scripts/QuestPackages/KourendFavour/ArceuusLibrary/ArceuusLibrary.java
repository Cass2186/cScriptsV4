package scripts.QuestPackages.KourendFavour.ArceuusLibrary;

import org.tribot.api.General;
import org.tribot.api2007.Game;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.types.RSInterfaceChild;
import org.tribot.script.sdk.Log;
import org.tribot.script.sdk.Waiting;
import scripts.ItemID;
import scripts.Listeners.InterfaceListener;
import scripts.Listeners.InterfaceObserver;
import scripts.Listeners.InventoryListener;
import scripts.Listeners.InventoryObserver;
import scripts.QuestPackages.KourendFavour.ArceuusLibrary.Library.Book;
import scripts.QuestPackages.KourendFavour.ArceuusLibrary.LibraryTasks.*;
import scripts.QuestPackages.KourendFavour.MakeCompost;
import scripts.QuestSteps.QuestTask;
import scripts.QuestUtils.TaskSet;
import scripts.Requirements.ItemRequirement;
import scripts.Requirements.Requirement;
import scripts.Tasks.Priority;
import scripts.Utils;
import scripts.cQuesterV2;

import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ArceuusLibrary implements QuestTask, InterfaceListener, InventoryListener {

    private static final Pattern NPC_BOOK_EXTRACTOR = Pattern.compile("'<col=0000ff>(.*)</col>'");
    private static final Pattern BOOKCASE_BOOK_EXTRACTOR = Pattern.compile("<col=00007f>(.*)</col>");
    private static final Pattern TAG_MATCHER = Pattern.compile("(<[^>]*>)");

    TaskSet tasks = new TaskSet(
            SolveTask.get(),
            GetNewAssignmentTask.get(),
            FindBookTask.get(),
            DeliverBookTask.get(),
            CashInBooksTask.get()
    );

    private static ArceuusLibrary quest;

    public static ArceuusLibrary get() {
        return quest == null ? quest = new ArceuusLibrary() : quest;
    }

    public void initialiseListeners() {
        InventoryObserver inventoryObserver = new InventoryObserver(() -> true);
        inventoryObserver.addListener(this);
        inventoryObserver.start();

        InterfaceObserver interfaceObserver = new InterfaceObserver(() -> true);
        interfaceObserver.addListener(this);
        interfaceObserver.addRSInterfaceChild(193, 2);
        interfaceObserver.addRSInterfaceChild(231, 6);
        interfaceObserver.start();
    }

    @Override
    public Priority priority() {
        return Priority.LOW;
    }

    @Override
    public boolean validate() {
        return cQuesterV2.taskList.get(0).equals(this);
    }

    @Override
    public void execute() {

        initialiseListeners();
        while (cQuesterV2.taskList.get(0).equals(this)) {
            Waiting.waitNormal(100,20);

            QuestTask task = tasks.getValidTask();

            if (tasks.size() == 0) {
                Log.log("[Debug]: Finished all Arceuus Tasks");
                break;
            }
            if (Game.getRunEnergy() < General.random(65, 80) && Utils.getVarBitValue(25) == 0 ) {
                Log.log("[Debug]: Need to drink stamina");
                Utils.drinkPotion(ItemID.STAMINA_POTION);
            }
            if (task != null) {
                task.execute();
            } else {
                Log.log("[Debug]: Task is null");
            }
            if (tasks.isEmpty())
                break;
        }
    }

    @Override
    public String questName() {
        return "Arceuus Library";
    }

    @Override
    public boolean checkRequirements() {
        return true;
    }

    @Override
    public void onAppear(RSInterfaceChild rsInterfaceChild) {
        String message = rsInterfaceChild.getText();
        if (message == null) {
            return;
        }


        if (message.startsWith("You find:")) {
            Matcher m = BOOKCASE_BOOK_EXTRACTOR.matcher(message);
            Log.log("Book found");
            System.out.println(message);
            if (m.find()) {
                getBookFromMatcher(m).ifPresent(book -> {
                    State.get().getLastBookcaseTile().ifPresent(tile -> State.get().getLibrary().mark(tile, book));
                    State.get().setLastBookcaseTile(null);
                });

            }
            return;
        }

        if (message.startsWith("Thanks, human") || message.startsWith("Thank you very much")) {
            State.get().swapProfessors();
            State.get().setCurrentAssignment(null);
            Statistics.get().incrementBooksGained();
            return;
        }

        if (message.startsWith("I believe you are currently")
                || message.startsWith("Thanks for finding my book")
                || message.startsWith("Thank you for finding my book")
                || message.startsWith("Aren't you helping someone")) {
            State.get().swapProfessors();
            return;
        }

        Matcher m = NPC_BOOK_EXTRACTOR.matcher(message);

        if (m.find()) {
            getBookFromMatcher(m).ifPresent(book -> {
                Log.log(String.format("Next book: %s", book.getName()));
                State.get().setCurrentAssignment(book);
            });
            return;
        }
    }

    private Optional<Book> getBookFromMatcher(Matcher m) {
        String bookName = TAG_MATCHER.matcher(m.group(1).replace("<br>", " ")).replaceAll("");
        Book book = Book.byName(bookName);

        if (book == null) {
            Log.log(String.format("Book %s is not recognised", bookName));
        }

        return Optional.ofNullable(book);
    }

    //The below is done a bit crappy as I changed the inventory listener implementation
    @Override
    public void inventoryItemGained(String itemName, Long count) {
        State.setCurrentBooksFromInventory(Inventory.getAllList());
    }

    @Override
    public void inventoryItemLost(String itemName, Long count) {
        State.setCurrentBooksFromInventory(Inventory.getAllList());
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
