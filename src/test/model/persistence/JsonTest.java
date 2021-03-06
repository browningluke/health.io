package model.persistence;

import model.Timeline;
import model.activities.Activity;
import model.activities.DefaultActivities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class JsonTest {

    protected void ensureTimelinesAreEqual(Timeline expectedTimeline,
                                           Timeline actualTimeline) {
        // Test timeline is intact
        assertEquals(expectedTimeline.getDayListLength(), actualTimeline.getDayListLength());
        assertEquals(
                expectedTimeline.getSelectedDateCode().toString(),
                actualTimeline.getSelectedDateCode().toString()
        );

        // Test days are intact
        //  (by design does not check if DateCodes are equal since
        //  JsonReader json test file will have the incorrect DateCode
        //  if ran on any other date except the day of writing this)
        assertEquals(
                expectedTimeline.getDay().getMoodListLength(),
                actualTimeline.getDay().getMoodListLength()
        );

        assertEquals(
                expectedTimeline.getDay().getSleepHours(),
                actualTimeline.getDay().getSleepHours()
        );


        // Test moods are intact
        assertEquals(
                expectedTimeline.getDay().getMood(0).getMoodScore(),
                actualTimeline.getDay().getMood(0).getMoodScore()
        );
        assertEquals(
                expectedTimeline.getDay().getMood(1).getMoodScore(),
                actualTimeline.getDay().getMood(1).getMoodScore()
        );

        // Test activities are intact
        assertEquals(
                expectedTimeline.getDay().getMood(0).getActivityListLength(),
                actualTimeline.getDay().getMood(0).getActivityListLength()
        );
        assertEquals(
                expectedTimeline.getDay().getMood(1).getActivityListLength(),
                actualTimeline.getDay().getMood(1).getActivityListLength()
        );

        for (Activity a : expectedTimeline.getDay().getMood(0).getActivityList()) {
            assertTrue(actualTimeline.getDay().getMood(0)
                    .containsActivity(a.getActivityName()));
        }

        for (Activity a : expectedTimeline.getDay().getMood(1).getActivityList()) {
            assertTrue(actualTimeline.getDay().getMood(1)
                    .containsActivity(a.getActivityName()));
        }
    }

    protected Timeline generateGeneralTimeline() {
        Timeline tl = new Timeline();

        // Set selected date's variables
        tl.getDay().setSleepHours(4);
        tl.getDay().getMood(0).setMoodScore(4);
        tl.getDay().getMood(1).addActivity(
                DefaultActivities.getInstance().getActivity("Gaming")
        );

        // Set selected date + 1's variables
        tl.getDay(tl.getDateCodeOneDayForward()).setSleepHours(8);
        tl.getDay(tl.getDateCodeOneDayForward()).getMood(0).setMoodScore(4);

        // Adding activities
        tl.getDay(tl.getDateCodeOneDayForward()).getMood(0).addActivity(
                DefaultActivities.getInstance().getActivity("Music")
        );
        tl.getDay(tl.getDateCodeOneDayForward()).getMood(0).addActivity(
                DefaultActivities.getInstance().getActivity("Party")
        );
        tl.getDay(tl.getDateCodeOneDayForward()).getMood(0).addActivity(
                DefaultActivities.getInstance().getActivity("Exercise")
        );

        tl.getDay(tl.getDateCodeOneDayForward()).getMood(1).setMoodScore(1);

        // Adding activities
        tl.getDay(tl.getDateCodeOneDayForward()).getMood(1).addActivity(
                DefaultActivities.getInstance().getActivity("Music")
        );tl.getDay(tl.getDateCodeOneDayForward()).getMood(1).addActivity(
                DefaultActivities.getInstance().getActivity("Movie/TV")
        );

        // Create a new day
        tl.createDayOneDayBack();
        tl.getDay(tl.getDateCodeOneDayBack()).getMood(0).setMoodScore(4);
        tl.getDay(tl.getDateCodeOneDayBack()).getMood(1).setMoodScore(2);
        tl.getDay(tl.getDateCodeOneDayBack()).setSleepHours(10);

        return tl;
    }

}
