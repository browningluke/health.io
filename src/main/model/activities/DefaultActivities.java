package model.activities;

import java.util.ArrayList;
import java.util.Locale;

// Represents the default activities that will show when a user edits a mood.
public class DefaultActivities {

    private static DefaultActivities defaultActivities; // The only defaultActivities instance (singleton)
    private final ArrayList<Activity> activityList;     // The list containing the default activities.

    // MODIFIES: this
    // EFFECTS: create DefaultActivities instance
    //          and adds the activities that will appear.
    private DefaultActivities() {
        activityList = new ArrayList<>();
        activityList.add(
                new Activity("Gaming")
        );

        activityList.add(
                new Activity("Movie/TV")
        );

        activityList.add(
                new Activity("Friends")
        );

        activityList.add(
                new Activity("Family")
        );

        activityList.add(
                new Activity("Party")
        );

        activityList.add(
                new Activity("Music")
        );

        activityList.add(
                new Activity("Exercise")
        );

    }

    // MODIFIES: this
    // EFFECTS: Gets the instance of DefaultActivities, creates it if it does not exit.
    //          Singleton design.
    public static DefaultActivities getInstance() {
        if (defaultActivities == null) {
            defaultActivities = new DefaultActivities();
        }
        return defaultActivities;
    }


    // EFFECTS: returns the activityList
    //          containing the default activities
    public ArrayList<Activity> getActivityList() {
        return activityList;
    }

    // EFFECTS: returns an activity from the defaults with a name that
    //          matches activityName.
    public Activity getActivity(String activityName) {
        for (Activity a: activityList) {
            if (a.getActivityName().toLowerCase(Locale.ROOT)
                    .equals(activityName.toLowerCase(Locale.ROOT))) {
                return a;
            }
        }
        return null;
    }

}
