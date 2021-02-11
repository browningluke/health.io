package model;

import java.util.ArrayList;
import java.util.List;

public class Day {

    public static final int MAXMOODS = 2;

    private String dateID; // some way of encoding the date


    private List<Mood> moodList; // Should contain 2
    private int sleepHours; // Initialized to -1

    public Day(String id) {
        dateID = id;
        moodList = new ArrayList<>();
        sleepHours = -1;
    }


    // EFFECTS: returns true if a mood was successfully added.
    public boolean addMood(Mood newmood) {
        if (moodList.size() < MAXMOODS) {
            moodList.add(newmood);
            return true;
        }
        return false;
    }

    // REQUIRES: moodList.length > 0
    // EFFECTS: replaces mood in moodList, first list is index 0.
    public void replaceMood(int pos, Mood newmood) {
        moodList.add(pos, newmood);
        moodList.remove(pos + 1);
    }

    // REQUIRES: pos < moodList.size()
    // EFFECTS:
    public Mood getMood(int pos) {
        return moodList.get(pos);
    }

    // GETTERS AND SETTERS

    public void setSleepHours(int sh) {
        sleepHours = sh;
    }

    public int getMoodListLength() {
        return moodList.size();
    }

    // EFFECTS: returns sleepHours if sleepHours is initialized, else -1
    public int getSleepHours() {
        return sleepHours;
    }

    public String getDateID() {
        return dateID;
    }

}
