package ui.panels;

import model.Mood;
import model.Timeline;
import model.activities.Activity;
import model.activities.DefaultActivities;
import ui.HealthIO;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.util.ArrayList;

// Represents a panel displaying the values of a mood & selected activities.
public class MoodPanel extends AbstractPanel implements Drawable {

    private JSlider moodSlider;             // The slider allowing the user to change the mood value.
    private JList<String> activitiesList;   // The JList displaying the available/selected activities.

    private final int selectedMoodIndex;    // Which mood value this panel is responsible for (0 or 1).

    // MODIFIES: this
    // EFFECTS: create a JPanel with custom parameters through the AbstractPanel class.
    // Sets which mood this panel is responsible for. Initialize the panel with components.
    public MoodPanel(HealthIO parent, int selectedMoodIndex) {
        super(MAIN_CARD_COLOR,
                new GridBagLayout(), parent);
        this.selectedMoodIndex = selectedMoodIndex;
        initPanel();
    }

    @Override
    // MODIFIES: this
    // EFFECTS: Creates and adds the titles to the panel (Editing Mood: x & Activities).
    //  Calls helper functions to add the slider and activity list.
    protected void initPanel() {
        GridBagConstraints gridBagConstraints;

        JLabel moodLabel = createLabel("Editing Mood " + (selectedMoodIndex + 1), BIG_FONT, Color.BLACK);
        gridBagConstraints = createGridBagConstraints(0, 0, 2, 0, 0);
        gridBagConstraints.insets = new java.awt.Insets(20, 240, 0, 0);
        add(moodLabel, gridBagConstraints);

        addMoodSlider();

        JLabel activitiesLabel = createLabel("Activities", BIG_FONT, Color.BLACK);
        gridBagConstraints = createGridBagConstraints(0, 2, 1, 0, 0);
        gridBagConstraints.insets = new java.awt.Insets(55, 270, 0, 0);
        add(activitiesLabel, gridBagConstraints);

        addActivitiesList();
    }

    @Override
    // MODIFIES: this
    // EFFECTS: Sets the mood slider's position to the mood value of the currently selected date.
    //  Highlights the activities in the mood.
    public void drawPanel() {
        Timeline tl = healthIO.getTimeline();
        Mood mood = tl.getDay().getMood(selectedMoodIndex);
        moodSlider.setValue(mood.getMoodScore());

        int[] selectedIndices = determineSelectedIndices(mood);
        activitiesList.setSelectedIndices(selectedIndices);
    }

    // EFFECTS: Helper method. Creates a new JSlider, initializes the
    //  parameters appropriately and adds it to the this panel.
    private void addMoodSlider() {
        moodSlider = createSlider(1, Mood.MAXMOODSCORE,
                Mood.MINMOODSCORE, 1, 0);
        moodSlider.addChangeListener(new MoodSliderChangeHandler());

        GridBagConstraints gridBagConstraints = createGridBagConstraints(0, 1, 4, 534, 0);
        gridBagConstraints.insets = new Insets(28, 30, 0, 20);

        add(moodSlider, gridBagConstraints);
    }

    // EFFECTS: Helper method. Creates a new JList (and parent scroll pane),
    //  sets the model and initializes the parameters appropriately and adds it to this panel.
    private void addActivitiesList() {
        activitiesList = new JList<>();
        activitiesList.setModel(new ActivityListModel());
        activitiesList.addListSelectionListener(new ActivityListSelectionListener());

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setViewportView(activitiesList);

        GridBagConstraints gridBagConstraints = createGridBagConstraints(0, 3, 1, 177, 107);
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(38, 210, 47, 0);
        add(scrollPane, gridBagConstraints);
    }

    // EFFECTS: Helper method. Returns the indices in the JList of the activities
    //  in the Mood's activities list.
    //  Allows highlighting to show user which activities they currently have selected.
    private int[] determineSelectedIndices(Mood m) {
        ActivityListModel model = (ActivityListModel) activitiesList.getModel();

        ArrayList<Integer> builderList = new ArrayList<>();

        for (int i = 0; i < model.getSize(); i++) {
            if (m.containsActivity(model.getElementAt(i))) {
                builderList.add(i);
            }
        }

        return builderList.stream().mapToInt(Number::intValue).toArray();
    }

    // EFFECTS: Overloaded helper method. Adds the ability to specify the x padding and anchor
    //  to the original method in AbstractPanel.
    private GridBagConstraints createGridBagConstraints(int x, int y, int gridwidth,
                                                        int ipadx, int ipady) {
        GridBagConstraints gridBagConstraints = createGridBagConstraints(x, y, gridwidth, ipady);
        gridBagConstraints.ipadx = ipadx;
        gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
        return gridBagConstraints;
    }

    // Represents a change handler which updates the Mood's value from the slider.
    private class MoodSliderChangeHandler implements ChangeListener {

        @Override
        // MODIFIES: healthIO.timeline
        // EFFECTS: Sets the assigned mood's value from the slider
        //  if the user has finished moving the slider.
        //  Updates all panels.
        public void stateChanged(ChangeEvent e) {
            JSlider slider = (JSlider) e.getSource();

            if (!slider.getValueIsAdjusting()) {
                int moodValue = slider.getValue();

                healthIO.getTimeline().getDay().getMood(selectedMoodIndex).setMoodScore(moodValue);
                healthIO.drawPanels();
            }

        }
    }

    // Represents a JList model containing all Activities in DefaultActivities
    private static class ActivityListModel extends AbstractListModel<String> {

        String[] activityStringList;        // All string representations of the DefaultActivities

        // MODIFIES: this
        // EFFECTS: creates a new Model and adds all the string
        //  representations of the DefaultActivities to the model's String list.
        public ActivityListModel() {
            ArrayList<String> builderList = new ArrayList<>();

            for (Activity a : DefaultActivities.getInstance().getActivityList()) {
                builderList.add(a.getActivityName());
            }

            activityStringList = builderList.toArray(new String[0]);
        }


        @Override
        // EFFECTS: returns the size of the String list
        public int getSize() {
            return activityStringList.length;
        }

        @Override
        // EFFECTS: returns the string at the specified index in the String list.
        public String getElementAt(int index) {
            return activityStringList[index];
        }
    }

    // Represents an action listener which handles adding and removing activities from mood.
    private class ActivityListSelectionListener implements ListSelectionListener {

        @Override
        // MODIFIES: healthIO.timeline
        // EFFECTS: If user is finished selecting items, gets the selected mood,
        //  calls a helper function to delete all activities in mood and add changed values.
        //  (Since JList selected all activities in mood when created, a change will
        //   include all old activities, so we can remove all and re-add them.)
        public void valueChanged(ListSelectionEvent e) {
            if (!e.getValueIsAdjusting()) {
                Mood selectedMood = healthIO.getTimeline()
                        .getDay().getMood(selectedMoodIndex);

                deleteAllActivitiesInAssignedMood(selectedMood);
                addSelectedActivitiesToMood(selectedMood);
            }
        }

        // MODIFIES: healthIO.timeline
        // EFFECTS: Deletes all activities in the current mood by name.
        private void deleteAllActivitiesInAssignedMood(Mood m) {
            ArrayList<String> currentActivityListNames = new ArrayList<>();
            m.getActivityList().forEach(activity ->
                    currentActivityListNames.add(activity.getActivityName()));

            currentActivityListNames.forEach(m::removeActivity);
        }

        // MODIFIES: healthIO.timeline
        // EFFECTS: Adds all selected JList items (Activity String names)
        //  to the selected mood through DefaultActivities by name.
        private void addSelectedActivitiesToMood(Mood m) {
            ActivityListModel activityListModel = (ActivityListModel) activitiesList.getModel();
            int[] selectedIndices = activitiesList.getSelectedIndices();

            for (int i : selectedIndices) {
                m.addActivity(DefaultActivities.getInstance()
                        .getActivity(activityListModel.getElementAt(i)));
            }
        }
    }
}
