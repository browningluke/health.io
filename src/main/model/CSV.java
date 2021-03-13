package model;

import model.activities.Activity;

import java.util.ArrayList;
import java.util.Collection;

// Represents a CSV parser that can convert a list of days to a valid CSV string.
public class CSV {

    private final Collection<Day> dayList;   // Contains the list of days to export.
    private String csvString;               // Contains the valid exported CSV string.

    // MODIFIES: this
    // EFFECTS: creates a new CSV instance with access to the entire dayList.
    public CSV(Collection<Day> dayList) {
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
