package ui.panels;

import model.Day;
import ui.HealthIO;
import ui.sound.ClickSound;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Represents the panel where the user can change between the different CardLayout panels
public class NavigationPanel extends AbstractPanel implements Drawable {

    private static final Color BACKGROUND_COLOR = new Color(51, 54, 49);

    private JButton homeButton;         // The JButton for changing back to the week summary panel.
    private JButton mood1Button;        // The JButton for changing to the mood1 panel.
    private JButton mood2Button;        // The JButton for changing to the mood2 panel.
    private JSlider sleepSlider;        // The JSlider for updating the amount of sleep for the selected date.

    // MODIFIES: this
    // EFFECTS: create a JPanel with custom parameters through the AbstractPanel class.
    // Initialize the panel with components.
    public NavigationPanel(HealthIO parent) {
        super(BACKGROUND_COLOR,
                new FlowLayout(FlowLayout.CENTER, 1000, 20), parent);
        initPanel();
    }

    @Override
    // MODIFIES: this
    // EFFECTS: Creates and adds all the buttons with their respective action handlers.
    //  Adds sleep panel to this panel and attaches sleep slider action handler.
    protected void initPanel() {
        homeButton = createButton("Home", new HomeButtonClickHandler());
        mood1Button = createButton("Mood 1", new MoodButtonClickHandler(0));
        mood2Button = createButton("Mood 2", new MoodButtonClickHandler(1));

        JPanel sleepPanel = createSleepSliderPanel();

        sleepSlider.addChangeListener(new SleepSliderChangeHandler());

        add(homeButton);
        add(mood1Button);
        add(mood2Button);
        add(sleepPanel);
    }

    @Override
    // MODIFIES: this
    // EFFECTS: Sets the sleep slider's position to the sleep value of the currently selected date.
    public void drawPanel() {
        sleepSlider.setValue(
                healthIO.getTimeline().getDay().getSleepHours()
        );
    }

    // EFFECTS: Helper Method. Creates a new JPanel, adds the title label and slider.
    //  Sets the current value of the slider and returns the created JPanel.
    //  Allows for an alternate layout of the title and slider within this panel.
    private JPanel createSleepSliderPanel() {
        JPanel jpanel = new JPanel();
        jpanel.setBackground(BACKGROUND_COLOR);

        jpanel.add(createLabel("Sleep",
                BOLD_FONT, Color.WHITE));

        int sleepHours = healthIO.getTimeline().getDay().getSleepHours();
        addSleepSlider(jpanel,
                sleepHours == -1 ? 0 : sleepHours);

        return jpanel;
    }

    // EFFECTS: Helper Method. Creates a new JSlider, initializes the
    //  parameters appropriately and adds it to the passed JPanel.
    private void addSleepSlider(JPanel jpanel, int sleepHours) {
        sleepSlider = createSlider(sleepHours == -1 ? 0 : sleepHours,
                Day.MAXSLEEP, 0, 4, 1);//new JSlider();
        sleepSlider.setFont(BOLD_FONT);
        sleepSlider.setForeground(HIGHLIGHT_COLOR);
        sleepSlider.setOrientation(JSlider.VERTICAL);
        jpanel.add(sleepSlider);
    }

    /*
        Action Handlers
     */

    // Represents a click handler which shows the home panel card.
    private class HomeButtonClickHandler implements ActionListener {

        @Override
        // MODIFIES: healthIO.mainPanel
        // EFFECTS: Plays a haptic feedback sound.
        //  Displays the home panel card on mainPanel.
        public void actionPerformed(ActionEvent e) {
            new ClickSound().play();
            CardLayout cl = (CardLayout) healthIO.getMainPanel().getLayout();
            cl.show(healthIO.getMainPanel(), MainPanel.HOME_PANEL_NAME);
        }
    }

    // Represents a click handler which shows a mood panel card.
    private class MoodButtonClickHandler implements ActionListener {

        private final int moodIndex;    // The mood (1 or 2) to display.

        // MODIFIES: this
        // EFFECTS: creates a new click listener with a specific mood to display.
        public MoodButtonClickHandler(int moodIndex) {
            this.moodIndex = moodIndex;
        }

        @Override
        // MODIFIES: healthIO.mainPanel
        // EFFECTS: Plays a haptic feedback sound.
        //  Displays either the mood 1 or mood 2 panel on mainPanel depending on moodIndex.
        public void actionPerformed(ActionEvent e) {
            new ClickSound().play();
            CardLayout cl = (CardLayout) healthIO.getMainPanel().getLayout();

            if (moodIndex == 0) {
                healthIO.getMainPanel().mood1Panel.drawPanel();
                cl.show(healthIO.getMainPanel(), MainPanel.MOOD1_PANEL_NAME);
            } else {
                healthIO.getMainPanel().mood2Panel.drawPanel();
                cl.show(healthIO.getMainPanel(), MainPanel.MOOD2_PANEL_NAME);
            }
        }
    }

    // Represents a change handler which updates the Day's sleep value from the slider.
    private class SleepSliderChangeHandler implements ChangeListener {

        @Override
        // MODIFIES: healthIO, healthIO.timeline
        // EFFECTS: Sets the currently selected date's sleep hours
        //  if the user has finished moving the slider.
        //  Updates all panels.
        public void stateChanged(ChangeEvent e) {
            JSlider slider = (JSlider) e.getSource();

            if (!slider.getValueIsAdjusting()) {
                int sleepHours = slider.getValue();

                healthIO.getTimeline().getDay().setSleepHours(sleepHours);
                healthIO.drawPanels();
            }
        }
    }
}

