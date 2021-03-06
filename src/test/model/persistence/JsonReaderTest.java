package model.persistence;

import model.Timeline;
import org.junit.jupiter.api.Test;
import persistence.JsonReader;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNoFileAtPath() {
        JsonReader jr = new JsonReader("./data/noFileHere.json");
        try {
            Timeline tl = jr.read();
            fail("Expected IOException");
        } catch (IOException e) {
            // Caught exception properly
        }
    }

    @Test
    void testReaderDefaultTimeline() {
        JsonReader jr = new JsonReader("./data/testReaderDefaultTimeline.json");
        Timeline defaultTimeline = new Timeline();

        try {
            Timeline tl = jr.read();
            ensureTimelinesAreEqual(defaultTimeline, tl);
            // Do NOT check if DateCodes are equal.
        } catch (IOException e) {
            fail("IOException should not have been thrown");
        }

    }

    @Test
    void testReaderGeneralTimeline() {
        JsonReader jr = new JsonReader("./data/testReaderGeneralTimeline.json");
        Timeline defaultTimeline = generateGeneralTimeline();

        try {
            Timeline tl = jr.read();
            ensureTimelinesAreEqual(defaultTimeline, tl);
            // Do NOT check if DateCodes are equal.
        } catch (IOException e) {
            fail("IOException should not have been thrown");
        }
    }

    @Test
    void testReaderOldTimeline() {
        // 'Old' here means the saved timeline does not include 'today' and therefore
        //  'today' must be generated.

        JsonReader jr = new JsonReader("./data/testReaderOldTimeline.json");
        Timeline timeline = new Timeline(); // Used to get today's date

        try {
            Timeline tl = jr.read();
            assertNotNull(tl.getDay(timeline.getSelectedDateCode()));
        } catch (IOException e) {
            fail("IOException should not have been thrown");
        }
    }

}
