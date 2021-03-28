package persistence;

import model.Day;
import model.activities.Activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Collection;

// Represents a CSV parser that can convert a list of days to a valid CSV string.
public class CsvWriter {

    private final Collection<Day> dayList;   // Contains the list of days to export.
    private String csvString;                // Contains the valid exported CSV string.
    private PrintWriter writer;              // The writer object.

    // MODIFIES: this
    // EFFECTS: creates a new CSV instance with access to the entire dayList.
    public CsvWriter(Collection<Day> dayList) {
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
    public void write() {
        writer.print(csvString);
    }

    // MODIFIES: this
    // EFFECTS: opens the writer at the specified path.
    //          Throws FileNotFoundException if file at path cannot be opened.
    public void open(String path) throws FileNotFoundException {
        writer = new PrintWriter(new File(path));
    }

    // MODIFIES: this
    // EFFECTS: closes the writer.
    public void close() {
        writer.close();
    }

    // EFFECTS: returns the raw csv string representing the dayList
    public String getCsvString() {
        return csvString;
    }
}
