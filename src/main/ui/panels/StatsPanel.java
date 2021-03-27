package ui.panels;

import model.Day;
import model.Mood;
import ui.HealthIO;

import javax.swing.*;
import java.awt.*;

// Represents a panel that displays all important stats for the currently selected date.
public class StatsPanel extends AbstractPanel implements Drawable {

    private JLabel mood1ValueLabel;     // The mood1 label containing the current mood1 value
    private JLabel mood2ValueLabel;     // The mood1 label containing the current mood1 value
    private JLabel sleepValueLabel;     // The mood1 label containing the current mood1 value

    // MODIFIES: this
    // EFFECTS: create a JPanel with custom parameters through the AbstractPanel class.
    // Initialize the panel with the sub panels and update (draw) all the stat values.
    public StatsPanel(HealthIO ui) {
        super(TOP_BAR_COLOR,
                new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 70, 20), ui);
        initPanel();
        drawPanel();
    }

    @Override
    // MODIFIES: this
    // EFFECTS: Creates the value labels, creates the sub panel with
    //  specific title (from a helper method) and adds the sub panel this Panel.
    protected void initPanel() {
        mood1ValueLabel = createLabel("", DEFAULT_FONT, Color.BLACK, SwingConstants.CENTER);
        add(createStatPanel("Mood 1", mood1ValueLabel));

        mood2ValueLabel = createLabel("", DEFAULT_FONT, Color.BLACK, SwingConstants.CENTER);
        add(createStatPanel("Mood 2", mood2ValueLabel));

        sleepValueLabel = createLabel("", DEFAULT_FONT, Color.BLACK, SwingConstants.CENTER);
        add(createStatPanel("Sleep", sleepValueLabel));
    }

    @Override
    // MODIFIES: this
    // EFFECTS: Updates all the stat labels with values from their respective variable in Timeline.
    public void drawPanel() {
        Day selectedDay = healthIO.getTimeline().getDay();

        mood1ValueLabel.setText(selectedDay.getMood(0).getUIMoodString()
                + "/" + Mood.MAXMOODSCORE);
        mood2ValueLabel.setText(selectedDay.getMood(1).getUIMoodString()
                + "/" + Mood.MAXMOODSCORE);
        sleepValueLabel.setText(selectedDay.getUISleepHours() + " hours");
    }

    // EFFECTS: Helper method. Creates a new JPanel with a title label (text from labelText)
    //  and a value label. Returns the JPanel.
    private JPanel createStatPanel(String labelText, JLabel valueLabel) {
        JPanel jpanel = new JPanel();
        jpanel.setLayout(new java.awt.GridLayout(2, 1, 0, 5));

        jpanel.add(createLabel(labelText,BIG_FONT, Color.BLACK, SwingConstants.CENTER));
        jpanel.add(valueLabel);

        return jpanel;
    }
}
