package model.activities;

// Represents an activity the user can add to a Mood.
public class Activity {

    private final String activityName;      // The name of this Activity.

    // MODIFIES: this
    // EFFECTS: create an activity and set the final name
    public Activity(String activityName) {
        this.activityName = activityName;
    }

    // EFFECTS: return the activity name
    public String getActivityName() {
        return activityName;
    }
}
