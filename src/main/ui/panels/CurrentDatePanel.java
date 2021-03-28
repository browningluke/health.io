package ui.panels;

import model.Timeline;
import ui.HealthIO;
import ui.sound.ClickSound;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Represents the panel showing the current date and allowing traversal around the timeline.
public class CurrentDatePanel extends AbstractPanel implements Drawable {

    private static final Color BACKGROUND_COLOR = new Color(51, 54, 49);

    private enum Direction { BACK, FORWARD }    // Directions the user can traverse the timeline.

    private JLabel currentDateLabel;            // The JLabel displaying the currently selected date.

    // MODIFIES: this
    // EFFECTS: create a JPanel with custom parameters through the AbstractPanel class.
    // Initialize the panel with components, then update (draw) the components.
    public CurrentDatePanel(HealthIO parent) {
        super(BACKGROUND_COLOR,
                new FlowLayout(FlowLayout.CENTER, 100, 25), parent);
        initPanel();
        drawPanel();
    }

    @Override
    // MODIFIES: this
    // EFFECTS: Adds the sub JPanel as a component to this JPanel.
    protected void initPanel() {
        add(createSubPanel());
    }

    @Override
    // MODIFIES: this
    // EFFECTS: Updates the JLabel to display the timeline's selected date.
    public void drawPanel() {
        currentDateLabel.setText(healthIO.getTimeline()
                .getSelectedDateCode().toString());
    }

    // EFFECTS: Helper Method. Creates a new JPanel, adds the date label and navigation buttons
    //  (with respective click handlers) returns the created JPanel.
    //  Allows for a dynamic border between buttons/label and edge of CurrentDatePanel.
    private JPanel createSubPanel() {
        JPanel jpanel = new JPanel();
        jpanel.setBackground(BACKGROUND_COLOR);

        addDirectionButton(jpanel, Direction.BACK);

        currentDateLabel = createLabel("", BOLD_FONT, Color.WHITE);
        jpanel.add(currentDateLabel);

        addDirectionButton(jpanel, Direction.FORWARD);

        return jpanel;
    }

    // EFFECTS: Helper method. Creates a new button using the AbstractPanel helper method,
    //  with text depending on specified direction (either < or >).
    private void addDirectionButton(JPanel jpanel, Direction direction) {
        jpanel.add(createButton(direction == Direction.FORWARD ? ">" : "<",
                new MovementButtonClickHandler(direction)));
    }

    // Represents a generic click handler which traverses timeline on click depending on initialized parameter.
    private class MovementButtonClickHandler implements ActionListener {

        private final Direction direction;  // The direction to travel when clicked.

        // MODIFIES: this
        // EFFECTS: creates a new click listener with a specified direction to traverse.
        public MovementButtonClickHandler(Direction direction) {
            this.direction = direction;
        }

        @Override
        // MODIFIES: healthIO, CurrentDatePanel
        // EFFECTS: Plays a haptic feedback sound.
        //  Resets the user's view to the home (Week) panel,
        //  calls a helper function to handle traversing the timeline
        //  & updates itself and all panels.
        public void actionPerformed(ActionEvent e) {
            new ClickSound().play();

            // Reset user's view to home screen
            CardLayout cl = (CardLayout) healthIO.getMainPanel().getLayout();
            cl.show(healthIO.getMainPanel(), MainPanel.HOME_PANEL_NAME);

            handleMovement();

            drawPanel();
            healthIO.drawPanels();
        }

        // MODIFIES: healthIO.timeline
        // EFFECTS: Helper function. Traverses the timeline in the appropriate direction.
        private void handleMovement() {
            Timeline timeline = healthIO.getTimeline();

            if (direction == Direction.BACK) {
                if (!timeline.canGoBackOneDay()) {
                    timeline.createDayOneDayBack();
                }
                timeline.goBackOneDay();
            } else {
                if (!timeline.canGoForwardOneDay()) {
                    timeline.createDayOneDayForward();
                }
                timeline.goForwardOneDay();
            }
        }
    }
}
