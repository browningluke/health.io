package model.persistence;

import model.DateCode;
import model.Day;
import model.Timeline;
import model.activities.Activity;
import model.activities.DefaultActivities;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class JsonTest {

    private static final DateCode d1 = new DateCode("2021-03-05");
    private static final DateCode d2 = new DateCode("2021-03-06");
    private static final DateCode d3 = new DateCode("2021-03-07");


    protected void ensureTimelinesAreEqual(Timeline expectedTimeline,
                                           Timeline actualTimeline,
                                           boolean reader, boolean general) {
        // Test timeline is intact
        assertEquals(
                expectedTimeline.getSelectedDateCode().toString(),
                actualTimeline.getSelectedDateCode().toString()
        );

        DateCode dc1;
        DateCode dc2;
        DateCode dc3;

        // Assign testing DateCodes based upon calling test method
        if (reader || general) {
            dc1 = general ? d1 : d2;
            dc2 = d2;
            dc3 = d3;
        } else {
            dc1 = expectedTimeline.getSelectedDateCode();
            dc2 = expectedTimeline.getDateCodeOneDayForward();
            dc3 = dc2;
        }


        // Test days are intact
        //  (by design does not check if DateCodes are equal since
        //  JsonReader json test file will have the incorrect DateCode
        //  if ran on any other date except the day of writing this)
        assertEquals(
                expectedTimeline.getDay().getMoodListLength(),
                actualTimeline.getDay().getMoodListLength()
        );

        assertEquals(
                expectedTimeline.getDay(dc1).getSleepHours(),
                actualTimeline.getDay(dc1).getSleepHours()
        );


        // Test moods are intact
        assertEquals(
                expectedTimeline.getDay(dc2).getMood(0).getMoodScore(),
                actualTimeline.getDay(dc2).getMood(0).getMoodScore()
        );
        assertEquals(
                expectedTimeline.getDay(dc3).getMood(1).getMoodScore(),
                actualTimeline.getDay(dc3).getMood(1).getMoodScore()
        );

        // Test activities are intact
        assertEquals(
                expectedTimeline.getDay(dc1).getMood(0).getActivityListLength(),
                actualTimeline.getDay(dc1).getMood(0).getActivityListLength()
        );
        assertEquals(
                expectedTimeline.getDay(dc2).getMood(1).getActivityListLength(),
                actualTimeline.getDay(dc2).getMood(1).getActivityListLength()
        );

        for (Activity a : expectedTimeline.getDay(dc1).getMood(0).getActivityList()) {
            assertTrue(actualTimeline.getDay(dc1).getMood(0)
                    .containsActivity(a.getActivityName()));
        }

        for (Activity a : expectedTimeline.getDay(dc2).getMood(1).getActivityList()) {
            assertTrue(actualTimeline.getDay(dc2).getMood(1)
                    .containsActivity(a.getActivityName()));
        }
    }

    protected Timeline generateGeneralTimeline() {
        ArrayList<Day> dayList = new ArrayList<>();
        dayList.add(new Day(d1)); // Create days with specified DateCodes for reproducibility
        dayList.add(new Day(d2));
        dayList.add(new Day(d3));

        Timeline tl = new Timeline(dayList);

        // Set selected date's variables
        tl.getDay(d2).setSleepHours(4);
        tl.getDay(d2).getMood(0).setMoodScore(4);
        tl.getDay(d2).getMood(1).addActivity(
                DefaultActivities.getInstance().getActivity("Gaming")
        );

        // Set selected date + 1's variables
        tl.getDay(d3).setSleepHours(8);
        tl.getDay(d3).getMood(0).setMoodScore(4);

        // Adding activities
        tl.getDay(d3).getMood(0).addActivity(
                DefaultActivities.getInstance().getActivity("Music")
        );
        tl.getDay(d3).getMood(0).addActivity(
                DefaultActivities.getInstance().getActivity("Party")
        );
        tl.getDay(d3).getMood(0).addActivity(
                DefaultActivities.getInstance().getActivity("Exercise")
        );

        tl.getDay(d3).getMood(1).setMoodScore(1);

        // Adding activities
        tl.getDay(d3).getMood(1).addActivity(
                DefaultActivities.getInstance().getActivity("Music")
        );tl.getDay(d3).getMood(1).addActivity(
                DefaultActivities.getInstance().getActivity("Movie/TV")
        );

        // Create a new day
        tl.getDay(d1).getMood(0).setMoodScore(4);
        tl.getDay(d1).getMood(1).setMoodScore(2);
        tl.getDay(d1).setSleepHours(10);

        return tl;
    }

}
