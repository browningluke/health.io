package ui.views;

import model.Mood;
import model.activities.Activity;
import model.activities.DefaultActivities;

public class ActivitiesView implements View {

    private final Mood selectedMood;

    // MODIFIES: this
    // EFFECTS: creates a new instances of an ActivitiesView, sets the final selectedMood appropriately
    public ActivitiesView(Mood selectedMood) {
        this.selectedMood = selectedMood;
    }

    @Override
    // EFFECTS: prints out the String returned from the helper function to draw the view.
    public void drawView() {
        System.out.println(drawActivitiesPanel());
    }

    // EFFECTS: returns a string containing the header + footer of the view,
    //          as well as the dynamically generated list of activities from DefaultActivities.
    private String drawActivitiesPanel() {
        String panelString = "|               Activities               |\n"
                           + "|                                        |\n";

        int activityCounter = 3;
        panelString += "|    ";
        for (Activity a : new DefaultActivities().getActivityList()) {
            if (activityCounter == 0) {
                activityCounter = 3;
                panelString += "|\n|     ";
                continue;
            }

            panelString += String.format("%s%s%s   ",
                    selectedMood.containsActivity(a.getActivityName()) ? "|" : " ",
                    a.getActivityName(),
                    selectedMood.containsActivity(a.getActivityName()) ? "|" : " ");

            activityCounter--;
        }


        panelString  += "  |\n|----------------------------------------|";
        return panelString;
    }
}
