package model.activities;

public class Activity {

    private final String activityName;

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
