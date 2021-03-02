package model.activities;

import org.json.JSONObject;
import persistence.Writable;

// Represents an activity the user can add to a Mood.
public class Activity implements Writable {

    private final String activityName;      // The name of this Activity.

    // MODIFIES: this
    // EFFECTS: create an activity and set the final name
    public Activity(String activityName) {
        this.activityName = activityName;
    }

    public JSONObject toJson() {
        JSONObject jsonActivity = new JSONObject();
        jsonActivity.put("name", activityName);

        return jsonActivity;
    }

    // EFFECTS: return the activity name
    public String getActivityName() {
        return activityName;
    }
}
