package ui.panels;

import model.DateCode;
import model.Timeline;
import ui.HealthIO;

import javax.swing.*;
import java.awt.*;
import java.time.Month;

// Represents a panel that displays the currently selected date in a pretty format.
public class LongDatePanel extends AbstractPanel implements Drawable {

    public static final Font LONG_DATE_FONT = new java.awt.Font("Monospaced", Font.BOLD, 13);

    private JTextArea longDateTextArea;     // TextArea displaying the user-friendly representation of the current date

    // MODIFIES: this
    // EFFECTS: create a JPanel with custom parameters through the AbstractPanel class.
    // Initialize the panel with components, then updates TextArea with the current timeline date.
    public LongDatePanel(HealthIO parent, String dow, DateCode dc) {
        super(TOP_BAR_COLOR,
                new java.awt.BorderLayout(10, 20), parent);
        initPanel();
        setLongDateTextArea(dow, dc);
    }

    @Override
    // MODIFIES: this
    // EFFECTS: Creates a new JTextArea, configures and adds it to this JPanel.
    protected void initPanel() {
        longDateTextArea = new JTextArea();
        longDateTextArea.setEditable(false);
        longDateTextArea.setColumns(20);
        longDateTextArea.setRows(5);
        longDateTextArea.setFont(LONG_DATE_FONT);
        longDateTextArea.setLineWrap(true);

        add(longDateTextArea, java.awt.BorderLayout.CENTER);
    }

    @Override
    // MODIFIES: this
    // EFFECTS: Updates the JTextArea to display the timeline's selected date in the pretty format.
    public void drawPanel() {
        Timeline tl = healthIO.getTimeline();
        setLongDateTextArea(tl.getDayOfWeek(), tl.getSelectedDateCode());
    }

    // MODIFIES: this
    // EFFECTS: consumes a string representing the current day of the week (eg. "Monday") &
    //  a DateCode of the current date. Displays the current date in a pretty format.
    //
    //  EXAMPLE:
    //      Thursday
    //      12 APRIL 2020
    //
    public void setLongDateTextArea(String dow, DateCode dc) {
        String text = "";

        text += "\n" + dow + "\n" + dc.getDate();

        text += " " + Month.of(dc.getMonth())
                + " " + dc.getYear() + "\n";

        longDateTextArea.setText(text);
    }
}
