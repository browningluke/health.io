package model.persistence;

import model.Timeline;
import model.activities.DefaultActivities;
import org.junit.jupiter.api.Test;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class JsonWriterTest extends JsonTest {

    @Test
    void testWriterInvalidFile() {
        try {
            JsonWriter jw = new JsonWriter("./data/\1illegal:filename.json");
            jw.open();
            fail("Expected IOException");
        } catch (IOException e) {
            // Threw exception properly
        }
    }

    @Test
    void testWriterDefaultTimeline() {
        try {
            Timeline tl = new Timeline();
            JsonWriter jw = new JsonWriter("./data/testWriterDefaultTimeline.json");
            jw.open();
            jw.write(tl);
            jw.close();

            JsonReader jr = new JsonReader("./data/testWriterDefaultTimeline.json");
            Timeline loadedTimeline = jr.read();

            ensureTimelinesAreEqual(tl, loadedTimeline);

            // Ensure DateCodes are equal
            assertEquals(
                    tl.getDay().getDateCode().toString(),
                    loadedTimeline.getDay().getDateCode().toString()
            );

        } catch (IOException e) {
            fail("IOException should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralTimeline() {
        try {
            Timeline generalTimeline = generateGeneralTimeline();

            // Save and load JSON
            JsonWriter jw = new JsonWriter("./data/testWriterGeneralTimeline.json");
            jw.open();
            jw.write(generalTimeline);
            jw.close();

            JsonReader jr = new JsonReader("./data/testWriterGeneralTimeline.json");
            Timeline loadedTimeline = jr.read();

            ensureTimelinesAreEqual(generalTimeline, loadedTimeline);

            // Ensure DateCodes are equal
            assertEquals(
                    generalTimeline.getDay().getDateCode().toString(),
                    loadedTimeline.getDay().getDateCode().toString()
            );

        } catch (IOException e) {
            fail("IOException should not have been thrown");
        }
    }

}
