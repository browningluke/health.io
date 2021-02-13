package model;

import model.io.CSV;
import model.io.Loader;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

public class Timeline {

    private ArrayList<Day> dayList;
    private final Calendar calendar;
    private final DateCode today;
    private DateCode selectedDate;


    // MODIFIES: this
    // EFFECTS: creates a new timeline, attempts to load DayList (not yet implemented),
    //          runs the setup function firstTimeCreate if load fails.
    public Timeline() {
        calendar = Calendar.getInstance(TimeZone.getDefault());
        today = generateDateCodeOfSelectedDate();
        selectedDate = today;

        dayList = loadDayList();

        if (dayList == null) {
            dayList = new ArrayList<>();
            firstTimeCreate();
        }
    }

    /*
        Variable IO
     */

    // MODIFIES: this
    // EFFECTS: creates a Day object for today and tomorrow and
    //          adds them to the DayList
    private void firstTimeCreate() {
        addDay(new Day(today)); // Add today
        DateCode tomorrowDateCode = getDateCodeOneDayForward();
        addDay(new Day(tomorrowDateCode)); // Add tomorrow
    }

    // MODIFIES: this
    // EFFECTS: creates a new Loader object (not yet implemented) and
    //          returns an ArrayList of days (null for now).
    private ArrayList<Day> loadDayList() {
        Loader loader = new Loader();
        return loader.load();
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

    // EFFECTS: returns a *reference* to the currently selected date.
    //          returns null if there is no date with such a DayCode.
    public Day getDay() {
        for (Day d : dayList) {
            if (d.getDateCode().equals(selectedDate)) {
                return d;
            }
        }
        return null;
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
