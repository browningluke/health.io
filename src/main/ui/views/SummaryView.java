package ui.views;

import model.DateCode;
import model.Day;
import model.Timeline;
import ui.enums.SelectedStat;

import java.util.ArrayList;

public class SummaryView extends AbstractView {

    private ArrayList<Day> dayWeekList;
    private DateCode weekBeginningCode;
    private DateCode weekEndCode;


    // MODIFIES: this
    // EFFECTS: creates a new instance of a SummaryView, calls the AbstractView constructor and
    //          sets the variables it needs to draw a timeline.
    public SummaryView(Timeline tn, SelectedStat stat) {
        super(tn, stat);
        this.dayWeekList = tn.getAllDaysInCurrentWeek();
        this.weekBeginningCode = tn.findDateCodeEndOfWeek(true);
        this.weekEndCode = tn.findDateCodeEndOfWeek(false);
    }

    @Override
    // EFFECTS: prints out the Strings returned from the helper functions to draw the view.
    public void drawView() {
        System.out.println(drawStatsPanel());
        System.out.println(drawSummaryPanel());
    }

    // EFFECTS: returns a string containing the static header and footer for the timeline,
    //          also calls the helper functions to dynamically add the stats.
    private String drawSummaryPanel() {
        String panelString = "|          < week %s-%s >          |\n"
                           + "|                                        |";
        ArrayList<String> rows = new ArrayList<>();
        rows.add("| 1  ");
        rows.add("| 2  ");
        rows.add("| 3  ");
        rows.add("| 4  ");
        rows.add("| 5  ");

        addDayStatsToRows(rows);

        for (int i = 0; i < rows.size(); i++) {
            appendToRowInRowList((i + 1) * 2 + "|", i, rows);
        }

        panelString += "\n" + rows.get(4) + "\n"
                + rows.get(3) + "\n" + rows.get(2) + "\n" + rows.get(1) + "\n" + rows.get(0) + "\n";

        panelString += "| M  M S  M S  M S  M S  M S  M S  M S  S|\n"
                    +  "|    Sun  Mon  Tue  Wed  Thu  Fri  Sat   |\n"
                    +  "|----------------------------------------|";

        return String.format(panelString, weekBeginningCode.getDateAndMonth(),
                weekEndCode.getDateAndMonth());

    }

    // EFFECTS: takes a list of rows and, for each day, calls the helper functions
    //          required to draw the stats for said day.
    private void addDayStatsToRows(ArrayList<String> rows) {
        for (Day d : dayWeekList) {
            if (d == null) {
                for (int i = 0; i < rows.size(); i++) {
                    appendToRowInRowList("     ", i, rows);
                }
                continue;
            }

            addMoodToRow(
                    d.getMood(0).getMoodScore(),
                    d.getMood(1).getMoodScore(), rows
            );

            addSleepToRow(d.getSleepHours(), rows);
        }
    }

    // EFFECTS: takes the height of the two bars, and a list of rows,
    //          adds the mood bars to the rows for a specific day.
    private void addMoodToRow(int mood0Height, int mood1Height, ArrayList<String> rows) {
        for (int i = 0; i < rows.size(); i++) {
            if (mood0Height > 0 && mood1Height > 0) {
                appendToRowInRowList("∥ ", i, rows);
            } else if (mood0Height > 0 || mood1Height > 0) {
                appendToRowInRowList("| ", i, rows);
            } else {
                appendToRowInRowList("  ", i, rows);
            }
            mood0Height--;
            mood1Height--;
        }
    }

    // EFFECTS: takes the height of the sleep bar, and a list of rows,
    //          adds the sleep bar to the rows for a specific day.
    private void addSleepToRow(int sleepHeight, ArrayList<String> rows) {
        if (sleepHeight >= 0) {
            for (int i = 0; i < rows.size(); i++) {
                if (sleepHeight >= 2) {
                    appendToRowInRowList("|  ", i, rows);
                    sleepHeight -= 2;
                } else {
                    appendToRowInRowList("   ", i, rows);
                }
            }
        } else {
            for (int i = 0; i < rows.size(); i++) {
                appendToRowInRowList("   ", i, rows);
            }
        }
    }

    // EFFECTS: helper function. Replaces a row in the rowlist with a new string.
    private void appendToRowInRowList(String text, int index, ArrayList<String> rows) {
        String row = rows.get(index);
        row += text;
        rows.add(index, row);
        rows.remove(index + 1);
    }

}
