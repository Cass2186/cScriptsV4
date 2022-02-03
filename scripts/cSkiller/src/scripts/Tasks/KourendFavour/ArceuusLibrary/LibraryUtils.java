package scripts.Tasks.KourendFavour.ArceuusLibrary;
import dax.walker.utils.TribotUtil;
import org.tribot.api.Timing;
import org.tribot.api2007.Objects;
import dax.walker.utils.AccurateMouse;
import dax.walker.utils.camera.DaxCamera;
import org.tribot.api.General;
import org.tribot.api2007.NPCs;
import org.tribot.api2007.Player;
import org.tribot.api2007.Walking;
import org.tribot.api2007.types.RSObject;
import org.tribot.script.sdk.Log;
import org.tribot.script.sdk.Waiting;
import scripts.Tasks.KourendFavour.ArceuusLibrary.Library.Book;
import scripts.Tasks.KourendFavour.ArceuusLibrary.Library.Bookcase;
import scripts.Tasks.KourendFavour.ArceuusLibrary.Professor.Professor;
import scripts.Tasks.KourendFavour.ArceuusLibrary.Walking.LibraryWalker;
import scripts.Utils;

import java.util.*;
import java.util.function.BooleanSupplier;
import java.util.function.Supplier;

import static java.util.stream.Collectors.toSet;

public class LibraryUtils {

   // private static final JustLogger LOGGER = new JustLogger(LibraryUtils.class);
   public static boolean waitAfterWalking(BooleanSupplier supplier, long timeout) {
       return waitAfterWalking(supplier, timeout, 50);
   }

    public static boolean waitAfterWalking(BooleanSupplier supplier, long timeout, long checkEvery) {

        if (Waiting.waitUntil(1000, Player::isMoving)) {
            Waiting.waitUntil( General.random(8153, 12321), () -> !Player.isMoving());
        }
        return waitCondition(supplier, timeout, checkEvery);
    }


    public static boolean waitCondition(BooleanSupplier supplier, long timeout, long checkEvery) {
        return Timing.waitCondition(() -> {
            General.sleep(checkEvery);
            return supplier.getAsBoolean();
        }, timeout);
    }

    public static boolean clickBookshelf(Bookcase bookcase) {

        if (LibraryWalker.get().walkTo(bookcase.getRoom())) {


            Optional<RSObject> optionalRSObject = Arrays.stream(
                            Objects.getAt(bookcase.getPosition(), object ->
                                    TribotUtil.getName(object).equals(Constants.Objects.BOOKSHELF)))
                    .findFirst();

            if (!optionalRSObject.isPresent()) {
                return false;
            }

            RSObject rsObject = optionalRSObject.get();

            if (Player.getPosition().distanceTo(rsObject.getPosition()) > 8 && Walking.blindWalkTo(rsObject)) {
                Waiting.waitUntil(7000, () -> Player.getPosition().distanceTo(rsObject.getPosition()) > 5);
            }

            if (Utils.clickObject(rsObject, "Search", true)) {
                State.get().setLastBookcaseTile(bookcase.getPosition());
                return waitAfterWalking(() -> !State.get().getLastBookcaseTile().isPresent(), 7000);
            }
        }

       // LOGGER.error("We couldn't click on " + bookcase);

        return false;
    }

    public static boolean helpProfessor(Professor professor) {

        if (LibraryWalker.get().walkTo(Constants.Rooms.ROOM_BOTTOM_MIDDLE)) {

            if (Player.getPosition().distanceTo(professor.getPosition()) > 4 && Walking.blindWalkTo(professor
                    .getPosition())) {
                Waiting.waitUntil(7000,
                        () -> Player.getPosition().distanceTo(professor.getPosition()) > 5);
            }

            return Arrays.stream(NPCs.find(professor.getName())).findFirst().map(rsnpc -> {

                if (!rsnpc.isOnScreen() || !rsnpc.isClickable()) {
                    DaxCamera.focus(rsnpc);
                }

                return AccurateMouse.click(rsnpc, "Help");
            }).orElse(false);


        }

        //LOGGER.error("Failed to walk to professor");
        return false;
    }

    public static boolean bookcaseContainsNewBook(Bookcase bookcase) {
        Set<Book> booksInBookcase = bookcase.getPossibleBooks()
                .stream()
                .filter(book -> !book.isDarkManuscript())
                .filter(book -> !book.equals(State.get().getCurrentAssignment().orElse(null)))
                .collect(toSet());

        return booksInBookcase.size() > 0 && Collections.disjoint(State.get().getCurrentBooks(), booksInBookcase);
    }

    public static Bookcase findBook(Book lookingForBook) {
        int attempts = 0;

        while (attempts < 3) {

            List<Bookcase> bookcases = State.get().getLibrary().getBookcases();

            for (Bookcase bookcase : bookcases) {

                boolean isBookKnown = bookcase.isBookSet();
                Book book = bookcase.getBook();
                Set<Book> possibleBooks = bookcase.getPossibleBooks();

                if (isBookKnown && book == null) {
                    book = possibleBooks.stream()
                            .filter(b -> b != null && b.isDarkManuscript())
                            .findFirst()
                            .orElse(null);
                }

                if (!isBookKnown && possibleBooks.size() == 1) {
                    book = possibleBooks.stream().findFirst().orElse(null);
                    isBookKnown = true;
                }

                if (isBookKnown && book == lookingForBook) {
                    return bookcase;
                } else if ((book == null || !book.isDarkManuscript()) && possibleBooks.contains(lookingForBook)) {
                    return bookcase;
                }
            }
            attempts++;
            General.sleep(1500,2000);

            //LOGGER.debug("Looking for: " + lookingForBook);

            bookcases.stream()
                    .filter(bookcase -> bookcase.getPossibleBooks().size() > 0)
                    .forEach(bookcase -> Log.log(String.format("Bookcase: %s, isSet: %s, book: %s, books: %s", bookcase, bookcase.isBookSet(), bookcase.getBook(), bookcase.getPossibleBooks())));
        }

        throw new RuntimeException("Could not find bookcase containing book " + lookingForBook);

    }

}
