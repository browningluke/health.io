package ui.cli.views;

import model.DateCode;
import model.Mood;
import model.Timeline;
import ui.cli.HealthIOcli;
import ui.cli.enums.SelectedStat;

// A View that draws the header for all subclasses. Displays stat values and selected stat.
public abstract class AbstractView implements View {
    private final String mood1;
    private final String mood2;
    private final String sleep;
    private final DateCode date;
    protected SelectedStat stat;

    // MODIFIES: this
    // EFFECTS: defines the top-level view and sets the necessary variables
    //          to print the stats panel.
    public AbstractView(Timeline tm, SelectedStat stat) {
        this.date = tm.getSelectedDateCode();
        this.mood1 = tm.getDay().getMood(0).getUIMoodString();
        this.mood2 = tm.getDay().getMood(1).getUIMoodString();
        this.sleep = tm.getDay().getUISleepHours();
        this.stat = stat;
    }

    // EFFECTS: returns a String containing the stats panel with the values
    //          substituted into it.
    protected String drawStatsPanel() {
        String panelString = "|-  =  x|---%s---------------------|\n"
                           + handleSelectedStat();
        panelString += "|----------------------------------------|";

        return String.format(panelString, HealthIOcli.PROJECTNAME);
    }

    // EFFECTS: returns a different string depending on which stat is selected.
    private String handleSelectedStat() {
        String panelString = "";
        switch (stat) {
            case MOOD1:
                panelString += "| %s |    |Mood 1|  Mood 2   Sleep    |\n"
                        + "| %s  |    | %s/%s  |   %s/%s     %s hrs    |\n";
                break;
            case MOOD2:
                panelString += "| %s |     Mood 1  |Mood 2|  Sleep    |\n"
                        + "| %s  |      %s/%s    | %s/%s  |  %s hrs    |\n";
                break;
            case SLEEP:
                panelString += "| %s |     Mood 1   Mood 2  |Sleep|   |\n"
                        + "| %s  |      %s/%s     %s/%s     |%s hrs|   |\n";
                break;
            case NONE:
                panelString += "| %s |     Mood 1   Mood 2   Sleep    |\n"
                        + "| %s  |      %s/%s      %s/%s     %s hrs    |\n";

        }
        return String.format(panelString,
                date.getDateAndMonth(), date.getYear(),
                mood1, Mood.MAXMOODSCORE,
                mood2, Mood.MAXMOODSCORE,
                sleep);
    }

    // EFFECTS: instructs the subclasses that they must implement a drawView method.
    public abstract void drawView();
}
