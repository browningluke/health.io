package ui.views;

import model.Mood;
import model.activities.Activity;
import model.activities.DefaultActivities;

import java.util.ArrayList;

public class ActivitiesView implements View {

    private final Mood selectedMood;

    public ActivitiesView(Mood selectedMood) {
        this.selectedMood = selectedMood;
    }

    @Override
    public void drawView() {
        System.out.println(drawActivitiesPanel());
    }

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
