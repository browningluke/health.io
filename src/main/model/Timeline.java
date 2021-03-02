package model;

import model.io.CSV;
import model.io.Loader;
import org.json.JSONArray;
import persistence.Writable;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;

// Represents a timeline that associates a list of days with a human calendar.
public class Timeline implements Writable {

    private ArrayList<Day> dayList;     // A list containing all days the user has created.
    private Calendar calendar;          // A Java Calendar for associating a Day with a date.
    private DateCode today;             // The DateCode representing today. Starting place for timeline.
    private DateCode selectedDate;      // The DateCode for the selected date when moving around the timeline.


    // MODIFIES: this
    // EFFECTS: creates a new timeline, creates a Day object for today and tomorrow and
    //          adds them to the DayList.
    public Timeline() {
        setupTimeline();
        dayList = new ArrayList<>();

        // New Timeline
        addDay(new Day(today)); // Add today
        DateCode tomorrowDateCode = getDateCodeOneDayForward();
        addDay(new Day(tomorrowDateCode)); // Add tomorrow
    }

    // MODIFIES: this
    // EFFECTS: creates a new timeline, sets dayList from parameter. Used for loading from JSON.
    public Timeline(ArrayList<Day> dayList) {
        setupTimeline();
        this.dayList = dayList;
    }

    private void setupTimeline() {
        calendar = Calendar.getInstance(TimeZone.getDefault());
        today = generateDateCodeOfSelectedDate();
        selectedDate = today;
    }

    /*
        DayCodes and moving around the timeline
     */

    // MODIFIES: this
    // EFFECTS: changes the current date on the Java Calendar by
    //          an int amount (either positive or negative)
    private DateCode dateDelta(int amount) {
        calendar.add(Calendar.DAY_OF_YEAR, amount);
        return generateDateCodeOfSelectedDate();
    }

    // EFFECTS: calculate and create a DateCode of the currently selected date.
    private DateCode generateDateCodeOfSelectedDate() {
        return new DateCode(calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH) + 1,
                calendar.get(Calendar.DATE));
    }

    // Checking if movement is possible

    // EFFECTS: returns true if it is possible to go backwards by one day.
    public boolean canGoBackOneDay() {
        return getDay(getDateCodeOneDayBack()) != null;
    }

    // EFFECTS: returns true if it is possible to go forwards by one day.
    public boolean canGoForwardOneDay() {
        return getDay(getDateCodeOneDayForward()) != null;
    }


    // Creating new days

    // REQUIRES: !canGoBackOneDay
    // MODIFIES: this
    // EFFECTS: creates a new day and adds it to the front of dayList
    public void createDayOneDayBack() {
        DateCode dateCodeOneDayBack = getDateCodeOneDayBack();
        dayList.add(0, new Day(dateCodeOneDayBack));
    }

    // REQUIRES: !canGoForwardOneDay
    // MODIFIES: this
    // EFFECTS: creates a new day and adds it to the end of dayList
    public void createDayOneDayForward() {
        DateCode dateCodeOneDayForward = getDateCodeOneDayForward();
        dayList.add(new Day(dateCodeOneDayForward));
    }

    // Moving around

    // MODIFIES: this
    // EFFECTS: moves the Java Calendar and the timeline back by one day.
    public void goBackOneDay() {
        selectedDate = dateDelta(-1);
    }

    // MODIFIES: this
    // EFFECTS: moves the Java Calendar and the timeline forward by one day.
    public void goForwardOneDay() {
        selectedDate = dateDelta(1);
    }


    // Getting DateCodes

    // EFFECTS: returns the DateCode of one day forward of currently selected date.
    //          Java Calendar + Timeline position remains the same.
    public DateCode getDateCodeOneDayForward() {
        DateCode dc = dateDelta(1);
        calendar.add(Calendar.DAY_OF_YEAR, -1);
        return dc;
    }

    // EFFECTS: returns the DateCode of one day back of currently selected date.
    //          Java Calendar + Timeline position remains the same.
    public DateCode getDateCodeOneDayBack() {
        DateCode dc = dateDelta(-1);
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        return dc;
    }

    // EFFECTS: returns the DateCode of currently selected date.
    public DateCode getSelectedDateCode() {
        return selectedDate;
    }


    /*
        Handle everything to do with the day list
     */

    // REQUIRES: newDay dateID is unique (not already in dayList).
    // MODIFIES: this
    // EFFECTS: add a new day to the dayList.
    public void addDay(Day newDay) {
        dayList.add(newDay);
    }

    // EFFECTS: returns a *reference* to a day, which can then be changed.
    //          returns null if there is no date with such a DayCode.
    public Day getDay(DateCode dc) {
        for (Day d : dayList) {
            if (d.getDateCode().equals(dc)) {
                return d;
            }
        }
        return null;
    }

    // REQUIRES: contains(selectedDate) is true
    // EFFECTS: returns a *reference* to the currently selected date.
    //          returns null if there is no date with such a DayCode.
    public Day getDay() {
        Day selectedDay = null;
        for (Day d : dayList) {
            if (d.getDateCode().equals(selectedDate)) {
                selectedDay = d;
            }
        }
        return selectedDay;
    }

    // EFFECTS: returns a list containing all Day instances in the current week.
    //          if Day instance does not exist, inserts null into list.
    public ArrayList<Day> getAllDaysInCurrentWeek() {
        ArrayList<DateCode> dateCodeWeekList = findAllDateCodeInWeek();
        ArrayList<Day> dayWeekList = new ArrayList<>();

        for (DateCode dc : dateCodeWeekList) {
            dayWeekList.add(getDay(dc));
        }
        return dayWeekList;
    }

    // EFFECTS: returns a list containing all DayCodes in the current week.
    private ArrayList<DateCode> findAllDateCodeInWeek() {
        int dow = calendar.get(Calendar.DAY_OF_WEEK);
        ArrayList<DateCode> dateCodeWeekList = new ArrayList<>();
        for (int i = Calendar.SUNDAY; i <= Calendar.SATURDAY; i++) {
            int diff = i - dow;
            DateCode dc = dateDelta(diff);
            calendar.add(Calendar.DAY_OF_YEAR, -1 * diff);
            dateCodeWeekList.add(dc);
        }
        return dateCodeWeekList;
    }

    // EFFECTS: returns DateCode for either beginning or end of the week.
    //          Java Calendar and Timeline position remains the same.
    public DateCode findDateCodeEndOfWeek(boolean beginning) {
        int dow = calendar.get(Calendar.DAY_OF_WEEK);
        int diff;

        if (beginning) {
            diff = Calendar.SUNDAY - dow;
        } else {
            diff = Calendar.SATURDAY - dow;
        }

        DateCode dc = dateDelta(diff);
        calendar.add(Calendar.DAY_OF_YEAR, -1 * diff);

        return dc;
    }

    /*
        Persistence
     */

    public JSONObject toJson() {
        JSONObject jsonTimeline = new JSONObject();

        JSONArray jsonDays = new JSONArray();

        for (Day d : dayList) {
            jsonDays.put(d.toJson());
        }

        jsonTimeline.put("timeline", jsonDays);

        return jsonTimeline;
    }


    /*
        Getters & Setters
     */

    // EFFECTS: returns an exported CSV object
    public CSV getCSV() {
        return new CSV(dayList);
    }

    // EFFECTS: returns the size of the dayList.
    public int getDayListLength() {
        return dayList.size();
    }

    // EFFECTS: searches for Day with specified DateCode, returns true if found,
    //          else otherwise.
    public boolean contains(DateCode dc) {
        for (Day d : dayList) {
            if (d.getDateCode().equals(dc)) {
                return true;
            }
        }
        return false;
    }

}
