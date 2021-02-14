package model;

import java.util.ArrayList;
import java.util.List;

// Represents a day that contains moods and sleep value. It has a unique ID (DateCode) which identifies it.
public class Day {

    public static final int MAXMOODS = 2;   // Number of moods per day.

    private DateCode dateCode;              // The unique ID for this day.
    private List<Mood> moodList;            // The list containing the moods. Should contain 2
    private int sleepHours;                 // The number of hours slept. Initialized to -1


    // MODIFIES: this
    // EFFECTS: creates a new Day object and fills the moodList
    //          with empty moods which can be updated later.
    public Day(DateCode id) {
        dateCode = id;
        sleepHours = -1;

        moodList = new ArrayList<>();

        for (int i = 0; i < MAXMOODS; i++) {
            moodList.add(new Mood());
        }
    }

    // REQUIRES: 0 <= pos < moodList.size()
    // EFFECTS: returns a *reference* to a mood at position pos.
    //          The reference can be updated directly.
    public Mood getMood(int pos) {
        return moodList.get(pos);
    }

    /*
        GETTERS AND SETTERS
     */

    // EFFECTS: returns sleepHours as a string if initialized,
    //          else returns an "x".
    public String getUISleepHours() {
        if (sleepHours == -1) {
            return "x";
        }
        return Integer.toString(sleepHours);
    }

    // EFFECTS: returns sleepHours if sleepHours is initialized, else -1
    public int getSleepHours() {
        return sleepHours;
    }

    // EFFECTS: updates sleepHours value with sh.
    public void setSleepHours(int sh) {
        sleepHours = sh;
    }

    // EFFECTS: returns the number of items (moods) in moodList.
    public int getMoodListLength() {
        return moodList.size();
    }

    // EFFECTS: returns the DateCode associated with this day.
    public DateCode getDateCode() {
        return dateCode;
    }

}
