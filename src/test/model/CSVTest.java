package model;

import static org.junit.jupiter.api.Assertions.*;

import model.activities.DefaultActivities;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class CSVTest {

    public static final String CSVHEADER = "date, mood1, mood2, sleep-time, mood1-activities, mood2-activities\n";

    CSV csv;
    ArrayList<Day> dayList;

    @BeforeEach
    void createCSV() {
        dayList = new ArrayList<>();
        dayList.add(new Day(
                new DateCode(2020, 11, 12)));
        dayList.add(new Day(
                new DateCode(2020, 11, 13)));

        csv = new CSV(dayList);
    }

    @Test
    void testConvertListToStringNoActivities() {
        csv.convertListToString();
        String csvString1 = CSVHEADER + "2020-11-12, x, x, x, , \n"
                          + "2020-11-13, x, x, x, , \n";

        assertEquals(csvString1, csv.save());

        dayList.get(0).setSleepHours(5);
        dayList.get(0).getMood(0).setMoodScore(4);

        String csvString2 = CSVHEADER + "2020-11-12, 4, x, 5, , \n"
                + "2020-11-13, x, x, x, , \n";

        csv.convertListToString();
        assertEquals(csvString2, csv.save());

        dayList.get(1).setSleepHours(8);
        dayList.get(1).getMood(1).setMoodScore(2);
        String csvString3 = CSVHEADER + "2020-11-12, 4, x, 5, , \n"
                + "2020-11-13, x, 2, 8, , \n";

        csv.convertListToString();
        assertEquals(csvString3, csv.save());


    }

    @Test
    void testConvertListToStringActivities() {
        csv.convertListToString();
        String csvString1 = CSVHEADER + "2020-11-12, x, x, x, , \n"
                + "2020-11-13, x, x, x, , \n";

        assertEquals(csvString1, csv.save());

        DefaultActivities da = new DefaultActivities();
        dayList.get(0).getMood(0).addActivity(
                da.getActivity("Gaming"));
        dayList.get(0).getMood(0).addActivity(
                da.getActivity("Friends")
        );

        csv.convertListToString();
        String csvString2 = CSVHEADER + "2020-11-12, x, x, x, Gaming;Friends;, \n"
                + "2020-11-13, x, x, x, , \n";


        assertEquals(csvString2, csv.save());

        dayList.get(1).getMood(0).addActivity(
                da.getActivity("Friends"));
        dayList.get(1).getMood(1).addActivity(
                da.getActivity("Party"));
        dayList.get(1).getMood(1).addActivity(
                da.getActivity("Music")
        );

        csv.convertListToString();
        String csvString3 = CSVHEADER + "2020-11-12, x, x, x, Gaming;Friends;, \n"
                + "2020-11-13, x, x, x, Friends;, Party;Music;\n";

        assertEquals(csvString3, csv.save());

    }

}
