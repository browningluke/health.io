package ui;

import com.formdev.flatlaf.*;
import persistence.CsvWriter;
import model.Timeline;
import persistence.JsonReader;
import persistence.JsonWriter;
import ui.components.MenuBar;
import ui.panels.LongDatePanel;
import ui.panels.MainPanel;
import ui.panels.StatsPanel;
import ui.panels.CurrentDatePanel;
import ui.panels.NavigationPanel;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

// Represents the runnable root JFrame containing all sub panels & the timeline.
public class HealthIO extends JFrame {

    public static final String PROJECT_NAME = "HealthIO";   // Contains the static application name
    public static final Dimension PREFERRED_SIZE = new Dimension(800, 525); // The size of the window

    protected Timeline timeline;                        // Timeline for storing Days and moving around.

    protected CurrentDatePanel currentDatePanel;        // Panel for showing current date and moving around.
    protected NavigationPanel navigationPanel;          // Panel for changing between CardLayout panels.
    protected LongDatePanel longDatePanel;              // Panel displaying current date in pretty format.
    protected StatsPanel statsPanel;                    // Panel displaying all important stats from current day.
    protected MainPanel mainPanel;                      // Panel containing week summary panel and mood panels.

    // MODIFIES: this
    // EFFECTS: creates a new HealthIO object, initializes a the parent JFrame then itself.
    public HealthIO() {
        super(PROJECT_NAME);
        timeline = new Timeline();

        initJFrame();
        initPanels();
    }

    // MODIFIES: this
    // EFFECTS: Sets background color, size & close action. Makes window not resizeable.
    private void initJFrame() {
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(50, 50, 50));
        setPreferredSize(PREFERRED_SIZE);
        setResizable(false);
    }

    // MODIFIES: this
    // EFFECTS: Creates split pane, adds it to root JFrame and sets:
    //  left side: sidebar pane
    //  right side: main pane
    private void initPanels() {
        JSplitPane rootSplitPane = new JSplitPane();

        rootSplitPane.setDividerLocation(175);
        rootSplitPane.setDividerSize(2);
        rootSplitPane.setEnabled(false);
        rootSplitPane.setPreferredSize(PREFERRED_SIZE);

        rootSplitPane.setLeftComponent(createSideBarPane());
        rootSplitPane.setRightComponent(createMainPane());

        setJMenuBar(new MenuBar(this));

        add(rootSplitPane);
        pack();
    }


    // EFFECTS: creates a new SideBar split pane for the side bar pane, sets:
    //  top side: current date panel
    //  bottom side: navigation panel
    //  Returns the created split pane.
    private JSplitPane createSideBarPane() {
        // Sidebar Split Pane
        JSplitPane sidebarSplitPane = new JSplitPane();
        sidebarSplitPane.setBackground(new java.awt.Color(102, 102, 102));
        sidebarSplitPane.setDividerLocation(85);
        sidebarSplitPane.setDividerSize(0);
        sidebarSplitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
        sidebarSplitPane.setEnabled(false);

        currentDatePanel = new CurrentDatePanel(this);
        navigationPanel = new NavigationPanel(this);


        sidebarSplitPane.setTopComponent(currentDatePanel);
        sidebarSplitPane.setBottomComponent(navigationPanel);

        return sidebarSplitPane;
    }

    // EFFECTS: creates a new SideBar split pane for the main pane, sets:
    //  top side: top bar pane
    //  bottom side: main panel
    //  Returns the created split pane.
    private JSplitPane createMainPane() {
        // Main Split Pane
        JSplitPane mainSplitPane = new JSplitPane();
        mainSplitPane.setDividerLocation(85);
        mainSplitPane.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        mainSplitPane.setEnabled(false);

        mainSplitPane.setTopComponent(createTopBarPane());

        mainPanel = new MainPanel(this);
        mainSplitPane.setBottomComponent(mainPanel);

        return mainSplitPane;
    }

    // EFFECTS: creates a new SideBar split pane for the top bar pane, sets:
    //  left side: long date panel
    //  right side: stats panel
    //  Returns the created split pane.
    private JSplitPane createTopBarPane() {
        // Topbar Split Pane
        JSplitPane topbarSplitPane = new JSplitPane();
        topbarSplitPane.setDividerLocation(150);
        topbarSplitPane.setEnabled(false);

        longDatePanel = new LongDatePanel(this, timeline.getDayOfWeek(), timeline.getSelectedDateCode());
        statsPanel = new StatsPanel(this);

        topbarSplitPane.setLeftComponent(longDatePanel);
        topbarSplitPane.setRightComponent(statsPanel);

        return topbarSplitPane;
    }

    // MODIFIES: statsPanel, mainPanel, navigationPanel, longDatePanel
    // EFFECTS: Updates all panels that are always displayed.
    //  (ie. not CardLayout panels: week/mood panel)
    public void drawPanels() {
        statsPanel.drawPanel();
        mainPanel.getWeekPanel().drawPanel();
        navigationPanel.drawPanel();
        longDatePanel.drawPanel();
    }

    /*
        IO methods
     */

    // EFFECTS: saves the Timeline instance to a file located at path.
    //  Shows a message dialog informing the user of a success or failure.
    public void saveTimeline(String path) {
        try {
            JsonWriter jsonWriter = new JsonWriter(path);
            jsonWriter.open();
            jsonWriter.write(timeline);
            jsonWriter.close();
            showMessageDialog("Successfully saved to file.");
        } catch (IOException e) {
            showMessageDialog("Unable to write to file.");
        }
    }

    // EFFECTS: loads the Timeline instance from a file located at path.
    //  Shows a message dialog informing the user of a success or failure.
    public void loadTimeline(String path) {
        try {
            JsonReader jsonReader = new JsonReader(path);
            timeline = jsonReader.read();
            showMessageDialog("Successfully loaded from file.");
        } catch (IOException e) {
            showMessageDialog("Unable to load from file.");
        }
    }

    // EFFECTS: gets a CSVWriter object from timeline, generates the csv string
    //  and then write the string to a file located at path.
    //  Shows a message dialog informing the user of a success or failure.
    public void exportTimelineAsCSV(String path) {
        CsvWriter exportCSV = timeline.getCsvWriter();
        exportCSV.convertListToString();

        try {
            exportCSV.open(path);
            exportCSV.write();
            exportCSV.close();
            showMessageDialog("Successfully exported to CSV.");
        } catch (IOException e) {
            showMessageDialog("Unable to export to file.");
        }

    }

    // MODIFIES: this
    // EFFECTS: Creates a new timeline and overwrites the existing one.
    //  Redraws all the panels to update them.
    public void resetTimeline() {
        timeline = new Timeline();
        drawPanels();
    }

    // EFFECTS: Helper method. Shows a JOptionPane message dialog with specified message.
    private void showMessageDialog(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

    // MODIFIES: this
    // EFFECTS: Sets the Java UI LookAndFeel (FlatLaf Light if available else Nimbus).
    //  Throws a RuntimeException if neither LookAndFeels are available.
    //  Runs the HealthIO application otherwise.
    public static void main(String[] args) {
        try {
            // Try to load the FlatLaf Light LookAndFeel (external library included in project).
            //  FlatLaf: https://www.formdev.com/flatlaf/
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (Exception e) {
            // If FlatLaf could be loaded, fall back on Nimbus (Java Standard) LookAndFeel.
            try {
                for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                    if ("Nimbus".equals(info.getName())) {
                        UIManager.setLookAndFeel(info.getClassName());
                        break;
                    }
                }
            } catch (ClassNotFoundException | UnsupportedLookAndFeelException
                    | IllegalAccessException | InstantiationException ex) {
                throw new RuntimeException("An fatal error while setting the LookAndFeel", ex);
            }
        }

        new HealthIO().setVisible(true);
    }

    /*
        Getters & Setters
     */

    public Timeline getTimeline() {
        return timeline;
    }

    public MainPanel getMainPanel() {
        return mainPanel;
    }
}
