package model.io;

import model.Day;
import model.activities.Activity;

import java.util.ArrayList;

public class CSV {

    private final ArrayList<Day> dayList;
    private String csvString;

    // MODIFIES: this
    // EFFECTS: creates a new CSV instance with access to the entire dayList.
    public CSV(ArrayList<Day> dayList) {
        this.dayList = dayList;
    }

    // MODIFIES: this
    // EFFECTS: generates a CSV string filled with values from Days.
    public void convertListToString() {
        String string = "date, mood1, mood2, sleep-time, mood1-activities, mood2-activities\n";

        for (Day d : dayList) {
            string += String.format("%s, %s, %s, %s, ",
                    d.getDateCode().toString(),
                    d.getMood(0).getUIMoodString(),
                    d.getMood(1).getUIMoodString(),
                    d.getUISleepHours());
            for (Activity a : d.getMood(0).getActivityList()) {
                string += String.format("%s;", a.getActivityName());
            }

            string += ", ";

            for (Activity a : d.getMood(1).getActivityList()) {
                string += String.format("%s;", a.getActivityName());
            }

            string += "\n";
        }

        csvString = string;
    }

    // EFFECTS: desired action: saves the csvString to file at a path.
    //          temporary action: returns the string to be printed
    public String save() {
        return csvString;
    }

}
