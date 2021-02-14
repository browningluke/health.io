package model;

import model.activities.Activity;

import java.util.ArrayList;

// Represents a mood, which has a happiness score and a list of activities.
public class Mood {

    public static final int MAXMOODSCORE = 5;   // The max value that the moodScore can be

    private int moodScore;                      // The current mood score for this Mood.
    private ArrayList<Activity> activityList;   // The list of activities for this Mood.

    // MODIFIES: this
    // EFFECTS: creates a new Mood instance with its own activityList,
    //          sets the moodScore to -1 as a default.
    public Mood() {
        moodScore = -1;
        activityList = new ArrayList<>();
    }


    // REQUIRES: activity with same name not already in activityList.
    // MODIFIES: this
    // EFFECTS: adds an activity to this mood.
    public void addActivity(Activity activity) {
        activityList.add(activity);
    }

    // MODIFIES: this
    // EFFECTS: removes an activity with name matching activityName
    //          from activityList.
    public void removeActivity(String activityName) {
        for (Activity a : activityList) {
            if (a.getActivityName().equals(activityName)) {
                activityList.remove(a);
                return;
            }
        }
    }

    // EFFECTS: returns true if an activity with name
    //          matching activityName is in activityList.
    public boolean containsActivity(String activityName) {
        for (Activity a : activityList) {
            if (a.getActivityName().equals(activityName)) {
                return true;
            }
        }
        return false;
    }

    // EFFECTS: returns the activityList
    public ArrayList<Activity> getActivityList() {
        return activityList;
    }

    // EFFECTS: converts moodScore to a String, unless it is -1
    //          where it returns "x".
    public String getUIMoodString() {
        if (moodScore == -1) {
            return "x";
        }
        return Integer.toString(moodScore);
    }

    // EFFECTS: returns moodScore value.
    public int getMoodScore() {
        return moodScore;
    }

    // EFFECTS: returns the length of activityList
    public int getActivityListLength() {
        return activityList.size();
    }

    // MODIFIES: this
    // EFFECTS: returns moodScore value.
    public void setMoodScore(int ms) {
        moodScore = ms;
    }
}
