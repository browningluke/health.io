package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;

public class TimelineTest {

    Calendar calendar;
    Timeline tl;

    @BeforeEach
    void createTimeline() {
        tl = new Timeline();
        calendar = Calendar.getInstance(TimeZone.getDefault());
    }

    @Test
    void testInit() {
        assertEquals(2, tl.getDayListLength());

        // Test using TimeLine methods
        assertTrue(tl.contains(
                tl.getSelectedDateCode()
        ));
        assertTrue(tl.contains(
                tl.getDateCodeOneDayForward()
        ));

        assertEquals(tl.getSelectedDateCode(), tl.getDay().getDateCode());
        assertNull(tl.getDay(tl.getDateCodeOneDayBack()));


        // Test using manual method
        assertTrue(tl.contains(
                new DateCode(calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH) + 1,
                        calendar.get(Calendar.DATE))
        ));

        calendar.add(Calendar.DAY_OF_YEAR, 1);
        DateCode tomorrow = new DateCode(calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH) + 1,
                calendar.get(Calendar.DATE));
        calendar.add(Calendar.DAY_OF_YEAR, -1);

        assertTrue(tl.contains(tomorrow));

    }

    @Test
    void testCanGoForwardBackOneDay() {
        assertFalse(tl.canGoBackOneDay());
        assertTrue(tl.canGoForwardOneDay());
        tl.goForwardOneDay();
        assertTrue(tl.canGoBackOneDay());
        assertFalse(tl.canGoForwardOneDay());

        tl.goBackOneDay();
        assertFalse(tl.canGoBackOneDay());
        assertTrue(tl.canGoForwardOneDay());
    }

    @Test
    void testAddDay() {
        calendar.add(Calendar.DAY_OF_YEAR, 2);
        DateCode newDateCode = new DateCode(calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH) + 1,
                calendar.get(Calendar.DATE));
        calendar.add(Calendar.DAY_OF_YEAR, -2);

        assertFalse(tl.contains(newDateCode));

        tl.addDay(new Day(newDateCode));

        assertTrue(tl.contains(newDateCode));
        assertEquals(3, tl.getDayListLength());

    }

    @Test
    void testGetAllDaysInCurrentWeek() {
        DateCode today = new DateCode(calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH) + 1,
                calendar.get(Calendar.DATE));

        calendar.add(Calendar.DAY_OF_YEAR, 1);
        DateCode tomorrow = new DateCode(calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH) + 1,
                calendar.get(Calendar.DATE));
        calendar.add(Calendar.DAY_OF_YEAR, -1);

        ArrayList<Day> dayWeekList = new ArrayList<>();


        int dow = calendar.get(Calendar.DAY_OF_WEEK);
        for (int i = Calendar.SUNDAY; i <= Calendar.SATURDAY; i++) {
            int diff = i - dow;

            calendar.add(Calendar.DAY_OF_YEAR, diff);
            DateCode dc = new DateCode(calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH) + 1,
                    calendar.get(Calendar.DATE));
            calendar.add(Calendar.DAY_OF_YEAR, -1 * diff);

            if (dc.equals(today) || dc.equals(tomorrow)) {
                assertTrue(tl.contains(dc));
                assertNotNull(tl.getDay(dc));
            } else {
                assertFalse(tl.contains(dc));
                assertNull(tl.getDay(dc));
            }

            dayWeekList.add(tl.getDay(dc));
        }

        assertEquals(dayWeekList.size(), tl.getAllDaysInCurrentWeek().size());

        for (int j = 0; j < dayWeekList.size(); j++) {
            assertEquals(dayWeekList.get(j), tl.getAllDaysInCurrentWeek().get(j));
        }

    }

    @Test
    void testFindDateCodeEndOfWeek() {
        int dow = calendar.get(Calendar.DAY_OF_WEEK);
        int diffBeginning = Calendar.SUNDAY - dow;
        int diffEnd = Calendar.SATURDAY - dow;

        calendar.add(Calendar.DAY_OF_YEAR, diffBeginning);
        DateCode dateCodeBeginning = new DateCode(calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH) + 1,
                calendar.get(Calendar.DATE));
        calendar.add(Calendar.DAY_OF_YEAR, -1 * diffBeginning);

        calendar.add(Calendar.DAY_OF_YEAR, diffEnd);
        DateCode dateCodeEnd = new DateCode(calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH) + 1,
                calendar.get(Calendar.DATE));
        calendar.add(Calendar.DAY_OF_YEAR, -1 * diffEnd);

        assertTrue(dateCodeBeginning.equals(
                tl.findDateCodeEndOfWeek(true)
        ));

        assertTrue(dateCodeEnd.equals(
                tl.findDateCodeEndOfWeek(false)
        ));
    }

    @Test
    void testGetCSV() {
        String csvString = CSVTest.CSVHEADER
                         + "%s, x, x, x, , \n"
                         + "%s, x, x, x, , \n";

        CSV csv = tl.getCSV();
        csv.convertListToString();
        assertEquals(String.format(csvString,
                tl.getSelectedDateCode().toString(),
                tl.getDateCodeOneDayForward().toString()), csv.save());

    }

    @Test
    void testCreateDayOneDayBack() {
        assertFalse(tl.canGoBackOneDay());
        assertTrue(tl.canGoForwardOneDay());
        DateCode dateCodeOneDayBack = tl.getDateCodeOneDayBack();

        assertFalse(tl.contains(dateCodeOneDayBack));
        tl.createDayOneDayBack();
        assertEquals(3, tl.getDayListLength());
        assertTrue(tl.contains(dateCodeOneDayBack));
    }

    @Test
    void testCreateDayOneDayForward() {
        assertFalse(tl.canGoBackOneDay());
        assertTrue(tl.canGoForwardOneDay());
        tl.goForwardOneDay();
        DateCode dateCodeOneDayForward = tl.getDateCodeOneDayForward();

        assertFalse(tl.contains(dateCodeOneDayForward));
        tl.createDayOneDayForward();
        assertEquals(3, tl.getDayListLength());
        assertTrue(tl.contains(dateCodeOneDayForward));
    }

}
