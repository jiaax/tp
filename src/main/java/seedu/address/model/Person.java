package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;
import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.model.day.Day;
import seedu.address.model.day.UniqueDayList;
import seedu.address.model.person.Profile;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSamePerson comparison)
 */
public class Person implements ReadOnlyPerson {

    private final UniqueDayList days;
    private Profile profile;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        days = new UniqueDayList();
    }

    public Person() {}

    /**
     * Creates an AddressBook using the Persons in the {@code toBeCopied}
     */
    public Person(ReadOnlyPerson toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the day list with {@code persons}.
     * {@code persons} must not contain duplicate persons.
     */
    public void setDays(List<Day> days) {
        this.days.setDays(days);
    }

    /**
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyPerson newData) {
        requireNonNull(newData);
        setProfile(newData.getProfile());
        setDays(newData.getDayList());
    }

    /**
     * Sets the profile information of current data to {@code profile}.
     */

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    /**
     * Checks if the current data {@code Person } has a profile.
     */

    public boolean hasProfile() {
        return profile != null;
    }

    //// day-level operations

    /**
     * Returns true if a day with the same identity as {@code day} exists in the address book.
     */
    public boolean hasDay(Day day) {
        requireNonNull(day);
        return days.contains(day);
    }

    /**
     * Returns true if a day in the address book with the same date as the current date.
     */
    public boolean hasDay(LocalDate date) {
        return days.contains(date);
    }

    /**
     * get a day in the address book with a specific date
     */
    public Day getDay(LocalDate date) {
        return days.getDate(date);
    }
    /**
     * Adds a day to the address book.
     * The day must not already exist in the address book.
     */
    public void addDay(Day p) {
        days.add(p);
    }

    /**
     * Replaces the given day {@code target} in the list with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The day identity of {@code editedPerson} must not be the same as another existing day in the address book.
     */
    public void setDay(Day target, Day editedDay) {
        requireNonNull(editedDay);

        days.setDay(target, editedDay);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removeDay(Day key) {
        days.remove(key);
    }

    //// util methods

    @Override
    public String toString() {
        return days.asUnmodifiableObservableList().size() + " persons";
        // TODO: refine later
    }

    @Override
    public ObservableList<Day> getDayList() {
        return days.asUnmodifiableObservableList();
    }

    @Override
    public Profile getProfile() {
        return profile;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Person // instanceof handles nulls
                && days.equals(((Person) other).days));
    }

    @Override
    public int hashCode() {
        return days.hashCode();
    }
}