package model;

import model.activities.Activity;

import java.util.ArrayList;

public class Mood {

    public static final int MAXMOODSCORE = 5;

    private int moodScore;
    private ArrayList<Activity> activityList;


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
