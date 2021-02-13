package ui.views;

import model.Mood;
import model.Timeline;
import ui.enums.SelectedStat;

public class EditMoodView extends AbstractView {

    private final Mood selectedMood;

    public EditMoodView(Timeline tm, SelectedStat stat) {
        super(tm, stat);
        selectedMood = tm.getDay().getMood((
                stat == SelectedStat.MOOD1) ? 0 : 1);
    }


    @Override
    public void drawView() {
        System.out.println(drawStatsPanel());
        System.out.println(drawEditPanel());

        new ActivitiesView(selectedMood).drawView();
    }

    private String drawEditPanel() {
        String panelString = "|             Editing Mood %s             |\n"
                           + "|                                        |\n";

        panelString += handleSelectedMoodScore();

        panelString += "|                                        |\n"
                     + "|                                        |";

        return String.format(panelString,
                (stat == SelectedStat.MOOD1) ? 1 : 2);
    }

    private String handleSelectedMoodScore() {
        String panelString = "";
        switch (selectedMood.getMoodScore()) {
            case 1:
                panelString += "|  |1|      2       3       4       5    |\n";
                break;
            case 2:
                panelString += "|   1      |2|      3       4       5    |\n";
                break;
            case 3:
                panelString += "|   1       2      |3|      4       5    |\n";
                break;
            case 4:
                panelString += "|   1       2       3      |4|      5    |\n";
                break;
            case 5:
                panelString += "|   1       2       3       4      |5|   |\n";
                break;
            default:
                panelString += "|   1       2       3       4       5    |\n";
                break;
        }
        return panelString;
    }

}
