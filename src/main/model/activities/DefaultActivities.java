package model.activities;

import java.util.ArrayList;
import java.util.Locale;

public class DefaultActivities {

    private final ArrayList<Activity> activityList;

    // MODIFIES: this
    // EFFECTS: create DefaultActivities instance
    //          and adds the activities that will appear.
    public DefaultActivities() {
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
