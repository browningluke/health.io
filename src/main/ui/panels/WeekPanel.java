package ui.panels;

import model.DateCode;
import model.Day;
import model.Mood;
import ui.HealthIO;
import ui.enums.BarType;
import ui.enums.WeekDay;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

// Represents a summary of all mood and sleep values in the current week.
public class WeekPanel extends AbstractPanel implements Drawable {

    public static final Color MOOD1_COLOR = new Color(51, 255, 51);
    public static final Color MOOD2_COLOR = new Color(51, 153, 255);
    public static final Color SLEEP_COLOR = new Color(65, 62, 151);

    public static final List<WeekDay> WEEK = Collections.unmodifiableList(
            Arrays.asList(WeekDay.SUNDAY, WeekDay.MONDAY,   // An ordered list of the days in the week. (WeekDay enum)
                    WeekDay.TUESDAY, WeekDay.WEDNESDAY,
                    WeekDay.THURSDAY, WeekDay.FRIDAY,
                    WeekDay.SATURDAY));

    private final HashMap<WeekDay, HashMap<BarType, JProgressBar>> weekBars;    // Weekday mapped to all 3 bar types.
    private JLabel currentWeekLabel;                        // Displays the current week range. (DD/MM - DD/MM)

    // MODIFIES: this
    // EFFECTS: create a JPanel with custom parameters through the AbstractPanel class.
    // Initialize the empty hashmap and the panel with components, then draws the components.
    public WeekPanel(HealthIO parent) {
        super(MAIN_CARD_COLOR,
                new GridBagLayout(), parent);
        weekBars = new HashMap<>();
        initPanel();
        drawPanel();
    }

    @Override
    // MODIFIES: this
    // EFFECTS: Creates, locates and adds the week label to this panel.
    //  Calls a helper method to add the bars.
    protected void initPanel() {
        currentWeekLabel = createLabel("", BIG_FONT, Color.BLACK, SwingConstants.CENTER);
        GridBagConstraints gridBagConstraints = createGridBagConstraints(0, 0, 26, 20);
        add(currentWeekLabel, gridBagConstraints);

        addAllBars();
    }

    @Override
    // MODIFIES: this
    // EFFECTS: Iterates through all Days in the week and updates all bar types with their respective stats.
    public void drawPanel() {
        ArrayList<Day> dayWeekList = healthIO.getTimeline().getAllDaysInCurrentWeek();
        DateCode weekBeginningCode = healthIO.getTimeline().findDateCodeEndOfWeek(true);
        DateCode weekEndCode = healthIO.getTimeline().findDateCodeEndOfWeek(false);

        updateWeekLabel(String.format("Week %s - %s",
                weekBeginningCode.getDateAndMonth(),
                weekEndCode.getDateAndMonth()));

        int currentIndex = 0;
        for (WeekDay wd : WEEK) {
            Day d = dayWeekList.get(currentIndex);

            updateBar(wd, BarType.MOOD1,
                    d != null ? d.getMood(0).getMoodScore() : -1);
            updateBar(wd, BarType.MOOD2,
                    d != null ? d.getMood(1).getMoodScore() : -1);
            updateBar(wd, BarType.SLEEP,
                    d != null ? d.getSleepHours() : -1);

            currentIndex++;
        }

    }

    // MODIFIES: this
    // EFFECTS: Helper method. Iterates through all WeekDays,
    //  creates, locates and adds the bars and the day labels.
    private void addAllBars() {
        int currentPos = 0;

        for (WeekDay wd : WEEK) {
            addLabel(wd.shortName, currentPos);

            weekBars.put(wd, addBars(currentPos));
            currentPos += 3;

            Dimension zeroDimension = new Dimension(0, 0);
            Box.Filler filler1 = new Box.Filler(zeroDimension, zeroDimension, zeroDimension);
            GridBagConstraints gridBagConstraints = createGridBagConstraints(currentPos, 1);
            gridBagConstraints.ipadx = 10;
            add(filler1, gridBagConstraints);

            currentPos += 1;
        }
    }

    // MODIFIES: this
    // EFFECTS: Helper method. Creates and adds the label with specified text and position.
    private void addLabel(String labelText, int xpos) {
        GridBagConstraints gridBagConstraints = createGridBagConstraints(xpos, 2, 3, 10);
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;

        add(createLabel(labelText, DEFAULT_FONT, Color.BLACK, SwingConstants.CENTER),
                gridBagConstraints);
    }

    // MODIFIES: this
    // EFFECTS: Helper method. Creates all 3 bar types for a specific day,
    //  adds them with specified position to this panel.
    private HashMap<BarType, JProgressBar> addBars(int currentPos) {
        HashMap<BarType, JProgressBar> hashMap = new HashMap<>();

        JProgressBar mood1Bar = createBar(Mood.MAXMOODSCORE, MOOD1_COLOR);
        JProgressBar mood2Bar = createBar(Mood.MAXMOODSCORE, MOOD2_COLOR);
        JProgressBar sleepBar = createBar(Day.MAXSLEEP, SLEEP_COLOR);

        addBar(mood1Bar, currentPos);
        addBar(mood2Bar, currentPos + 1);
        addBar(sleepBar, currentPos + 2);

        hashMap.put(BarType.MOOD1, mood1Bar);
        hashMap.put(BarType.MOOD2, mood2Bar);
        hashMap.put(BarType.SLEEP, sleepBar);

        return hashMap;
    }

    // EFFECTS: Helper method. Creates and styles a JProgressBar
    //  with specified max value and color parameters.
    private JProgressBar createBar(int maxValue, Color color) {
        JProgressBar bar = new JProgressBar();

        bar.setFont(BOLD_FONT);
        bar.setForeground(color);
        bar.setMaximum(maxValue);
        bar.setOrientation(1);
        bar.setValue(0);
        bar.setPreferredSize(new java.awt.Dimension(17, 250));
        bar.setString("N/A");
        bar.setStringPainted(true);
        return bar;
    }

    // MODIFIES: this
    // EFFECTS: Helper method. Adds a JProgressBar to this panel at a specified position.
    private void addBar(JProgressBar jpb, int currentPos) {
        GridBagConstraints gridBagConstraints = createGridBagConstraints(currentPos, 1);
        add(jpb, gridBagConstraints);
    }

    // MODIFIES: this
    // EFFECTS: Updates the value and text of a bar. If the new value is -1,
    //  set value to 0 and text to "N/A".
    public void updateBar(WeekDay wd, BarType bt, int newValue) {
        JProgressBar jpb = weekBars.get(wd).get(bt);

        if (newValue == -1) {
            jpb.setString("N/A");
        } else if (bt == BarType.SLEEP) {
            jpb.setString(newValue + " hours");
        } else {
            jpb.setString(Integer.toString(newValue));
        }

        jpb.setValue(newValue == -1 ? 0 : newValue);
    }

    // MODIFIES: this
    // EFFECTS: Updates the text of the week label.
    public void updateWeekLabel(String text) {
        currentWeekLabel.setText(text);
    }
}
