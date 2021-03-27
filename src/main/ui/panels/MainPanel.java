package ui.panels;

import ui.HealthIO;

import java.awt.*;

// Represents the CardLayout panel that contains the week, mood1 & mood2 panels
public class MainPanel extends AbstractPanel {

    public static final String HOME_PANEL_NAME = "homePanel";
    public static final String MOOD1_PANEL_NAME = "mood1Panel";
    public static final String MOOD2_PANEL_NAME = "mood2Panel";

    protected WeekPanel weekPanel;      // The week  panel added to the main panel
    protected MoodPanel mood1Panel;     // The mood1 panel added to the main panel
    protected MoodPanel mood2Panel;     // The mood2 panel added to the main panel

    // MODIFIES: this
    // EFFECTS: create a JPanel with custom parameters through the AbstractPanel class.
    // Initialize the panel with the sub panels.
    public MainPanel(HealthIO parent) {
        super(TOP_BAR_COLOR,
                new CardLayout(), parent);
        initPanel();
    }

    @Override
    // MODIFIES: this
    // EFFECTS: Adds the sub panels with their respective names to this,
    //  for use with CardLayout.
    protected void initPanel() {
        weekPanel = new WeekPanel(healthIO);
        mood1Panel = new MoodPanel(healthIO, 0);
        mood2Panel = new MoodPanel(healthIO, 1);

        add(weekPanel, HOME_PANEL_NAME);
        add(mood1Panel, MOOD1_PANEL_NAME);
        add(mood2Panel, MOOD2_PANEL_NAME);
    }

    public WeekPanel getWeekPanel() {
        return weekPanel;
    }
}
