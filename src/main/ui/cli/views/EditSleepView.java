package ui.cli.views;

import model.Timeline;
import ui.cli.enums.SelectedStat;

// The View allowing a user to edit the sleep value of the currently selected day.
public class EditSleepView extends AbstractView {

    // MODIFIES: this
    // EFFECTS: creates a new instance of a EditSleepView, calls the AbstractView constructor and
    //          sets the variables it needs to draw the edit screen.
    public EditSleepView(Timeline tm, SelectedStat stat) {
        super(tm, stat);
    }

    @Override
    // EFFECTS: prints out the Strings returned from the helper functions to draw the view.
    public void drawView() {
        System.out.println(drawStatsPanel());
        System.out.println(drawEditPanel());
    }

    // EFFECTS: draws the header + footer, and the placeholder dropdown box
    //          where a JPanel UI element will be placed.
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
