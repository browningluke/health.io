package ui.views;

import model.Timeline;
import ui.enums.SelectedStat;

public class EditSleepView extends AbstractView {

    public EditSleepView(Timeline tm, SelectedStat stat) {
        super(tm, stat);
    }

    @Override
    public void drawView() {
        System.out.println(drawStatsPanel());
        System.out.println(drawEditPanel());
    }

    private String drawEditPanel() {
        String panelString = "|             Editing Sleep              |\n"
                           + "|                                        |\n";

        panelString += "|          < dropdown values >           |\n";

        panelString += "|                                        |\n"
                     + "|                                        |\n"
                     + "|----------------------------------------|";
        return panelString;
    }

}
