package ui.views;

import model.DateCode;
import model.Mood;
import model.Timeline;
import ui.MoodWindow;
import ui.enums.SelectedStat;

public abstract class AbstractView implements View {
    private final String mood1;
    private final String mood2;
    private final String sleep;
    private final DateCode date;
    protected SelectedStat stat;

    public AbstractView(Timeline tm, SelectedStat stat) {
        this.date = tm.getSelectedDateCode();
        this.mood1 = tm.getDay().getMood(0).getUIMoodString();
        this.mood2 = tm.getDay().getMood(1).getUIMoodString();
        this.sleep = tm.getDay().getUISleepHours();
        this.stat = stat;
    }

    protected String drawStatsPanel() {
        String panelString = "|-  =  x|---%s---------------------|\n"
                           + handleSelectedStat();
        panelString += "|----------------------------------------|";

        return String.format(panelString, MoodWindow.PROJECTNAME);
    }

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

    public abstract void drawView();
}
