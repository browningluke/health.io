package ui;

import model.Mood;
import model.Timeline;
import model.activities.Activity;
import model.activities.DefaultActivities;
import model.io.CSV;
import model.io.Saver;
import ui.enums.*;
import ui.views.*;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

public class MoodWindow {

    public static final String PROJECTNAME = "HealthIO";
    Timeline timeline;
    Window currentWindow;


    // MODIFIES: this
    // EFFECTS: creates a new Timeline and sets the current window to the Summary View.
    public MoodWindow() {
        timeline = new Timeline();
        currentWindow = Window.MAIN;
    }

    // EFFECTS: calls the necessary functions to determine which commands can be called,
    //          print these on the screen, and then handle user input.
    public void drawUI() {
        drawViews();

        ArrayList<Actions> availableActions = determineAvailableCommands();
        printCommands(availableActions);
        handleCommands(availableActions);
    }

    // EFFECTS: create a new instance of one of the AbstractView subclasses depending on which
    //          window we have selected, then draw it.
    private void drawViews() {
        switch (currentWindow) {
            case MAIN:
                new SummaryView(timeline, SelectedStat.NONE).drawView();
                break;
            case EDIT_MOOD1:
                new EditMoodView(timeline, SelectedStat.MOOD1).drawView();
                break;
            case EDIT_MOOD2:
                new EditMoodView(timeline, SelectedStat.MOOD2).drawView();
                break;
            case EDIT_SLEEP:
                new EditSleepView(timeline, SelectedStat.SLEEP).drawView();
                break;
        }
    }

    // EFFECTS: returns a list of Actions that determine what the user is able to do
    //          on the selected window.
    private ArrayList<Actions> determineAvailableCommands() {
        ArrayList<Actions> actions = new ArrayList<>();

        if (currentWindow == Window.MAIN) {
            actions.add(Actions.EDITSTATS);
            actions.add(Actions.EXPORT);

            if (timeline.canGoBackOneDay()) {
                actions.add(Actions.GOBACKWARD);
            }

            if (timeline.canGoForwardOneDay()) {
                actions.add(Actions.GOFORWARD);
            }

        } else if (currentWindow == Window.EDIT_MOOD1
                || currentWindow == Window.EDIT_MOOD2) {
            actions.add(Actions.UPDATEMOODVALUE);
            actions.add(Actions.UPDATEACTIVITIES);
            actions.add(Actions.BACK);
        } else if (currentWindow == Window.EDIT_SLEEP) {
            actions.add(Actions.UPDATESLEEPVALUE);
            actions.add(Actions.BACK);
        }
        return actions;
    }

    // EFFECTS: print all available commands to the screen.
    private void printCommands(ArrayList<Actions> availableActions) {
        String message = "";

        if (availableActions.contains(Actions.UPDATEMOODVALUE)) {
            message += "Press u to update the mood value\n";
        }

        if (availableActions.contains(Actions.UPDATEACTIVITIES)) {
            message += "Press a to edit activities.\n";
        }

        if (availableActions.contains(Actions.UPDATESLEEPVALUE)) {
            message += "Press u to update the sleep value\n";
        }

        if (availableActions.contains(Actions.BACK)) {
            message += "Press b to go back to the main menu\n";
        }

        message += printMainWindowCommands(availableActions);

        message += "Press q to quit\n";
        System.out.println(message);
    }

    // EFFECTS: print all available commands relating to the Main (Summary) window to the screen.
    private String printMainWindowCommands(ArrayList<Actions> availableActions) {
        String message = "";

        if (availableActions.contains(Actions.GOBACKWARD)) {
            message += "Press < to go back one day\n";
        }

        if (availableActions.contains(Actions.GOFORWARD)) {
            message += "Press > to go forward one day\n";
        }

        if (availableActions.contains(Actions.EDITSTATS)) {
            message += "Press 1 to edit mood 1.\n"
                    + "Press 2 to edit mood 2. \n"
                    + "Press 3 to edit sleep. \n";
        }

        if (availableActions.contains(Actions.EXPORT)) {
            message += "Press x to export to csv.\n";
        }

        return message;
    }

    // EFFECTS: determines which command to run based on available actions and user input.
    //          Quit is always available, and other helper functions are called.
    private void handleCommands(ArrayList<Actions> availableActions) {
        Scanner in = new Scanner(System.in);
        String s = in.nextLine();

        if (s.equals("q")) {
            new Saver().save();
            System.exit(0); // Exit no matter what
        }

        handleMovement(availableActions, s);
        handleUpdateValues(availableActions, s);
        handleExport(availableActions, s);

        drawUI();
    }

    // MODIFIES: this, timeline
    // EFFECTS: if the user entered a movement command, either move around the timeline or
    //          change active window.
    private void handleMovement(ArrayList<Actions> availableActions, String s) {
        if (availableActions.contains(Actions.GOBACKWARD) && s.equals("<")) {
            timeline.goBackOneDay();
        } else if (availableActions.contains(Actions.GOFORWARD) && s.equals(">")) {
            timeline.goForwardOneDay();
        }  else if (availableActions.contains(Actions.BACK) && s.equals("b")) {
            currentWindow = Window.MAIN;
        } else if (availableActions.contains(Actions.EDITSTATS)) {
            switch (s) {
                case "1":
                    currentWindow = Window.EDIT_MOOD1;
                    break;
                case "2":
                    currentWindow = Window.EDIT_MOOD2;
                    break;
                case "3":
                    currentWindow = Window.EDIT_SLEEP;
                    break;
            }
        }
    }

    // EFFECTS: if the user entered an update command, move to the appropriate update window.
    private void handleUpdateValues(ArrayList<Actions> availableActions, String s) {
        if (availableActions.contains(Actions.UPDATEMOODVALUE) && s.equals("u")) {
            handleEditMood((currentWindow == Window.EDIT_MOOD1) ? 0 : 1);

        } else if (availableActions.contains(Actions.UPDATESLEEPVALUE) && s.equals("u")) {
            handleEditSleep();

        } else if (availableActions.contains(Actions.UPDATEACTIVITIES) && s.equals("a")) {
            handleEditActivities((currentWindow == Window.EDIT_MOOD1) ? 0 : 1);
        }
    }

    // MODIFIES: this, timeline
    // EFFECTS: prompts the user to enter a new mood value for the currently selected mood,
    //          will keep looping until user enters value between 1 and 5.
    private void handleEditMood(int moodIndex) {
        Scanner in =  new Scanner(System.in);
        System.out.println("Please enter a new mood score (between 1 and 5): ");
        String ms = in.nextLine();
        if (Integer.parseInt(ms) >= 1 && Integer.parseInt(ms) <= 5) {
            timeline.getDay().getMood(moodIndex).setMoodScore(Integer.parseInt(ms));
            return;
        }
        handleEditMood(moodIndex);
    }

    // MODIFIES: this, timeline
    // EFFECTS: prompts the user to enter a new sleep value for selected day,
    //          will keep looping until user enters value between 0 and 24.
    private void handleEditSleep() {
        Scanner in =  new Scanner(System.in);
        System.out.println("Please enter a new sleep time: ");
        String sleep = in.nextLine();
        if (Integer.parseInt(sleep) >= 0 && Integer.parseInt(sleep) <= 24) {
            timeline.getDay().setSleepHours(Integer.parseInt(sleep));
            return;
        }
        handleEditSleep();
    }

    // MODIFIES: this, timeline
    // EFFECTS: prompts the user to enter a comma separated list of activities they wish
    //          to toggle. Will keep looping until user enters valid activity.
    private void handleEditActivities(int moodIndex) {
        Scanner in =  new Scanner(System.in);
        System.out.println("Please enter all activities you wish to toggle, "
                + "separated by a comma: ");
        String activities = in.nextLine();
        activities = activities.replace(" ", "");

        String[] activitiesList = activities.split(",");

        for (String s : activitiesList) {
            Activity newActivity = new DefaultActivities().getActivity(s);

            if (newActivity == null) {
                System.out.println("You entered an invalid activity, try again.");
                handleEditActivities(moodIndex);
                return;
            }

            Mood selectedMood = timeline.getDay().getMood(moodIndex);

            if (selectedMood.containsActivity(newActivity.getActivityName())) {
                selectedMood.removeActivity(newActivity.getActivityName());
                continue;
            }

            selectedMood.addActivity(newActivity);
        }
    }


    // EFFECTS: gets a new CSV object from timeline, generates the csv string
    //          and then prints the result to the screen.
    private void handleExport(ArrayList<Actions> availableActions, String s) {
        if (availableActions.contains(Actions.EXPORT) && s.equals("x")) {
            CSV exportCSV = timeline.getCSV();
            exportCSV.convertListToString();
            System.out.println(exportCSV.save());
        }
    }
}
