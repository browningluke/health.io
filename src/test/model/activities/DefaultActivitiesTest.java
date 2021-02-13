package model.activities;

import static org.junit.jupiter.api.Assertions.*;

import model.activities.DefaultActivities;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DefaultActivitiesTest {

    DefaultActivities da;

    @BeforeEach
    void createDefaultActivities() {
        da = new DefaultActivities();
    }

    @Test
    void testInit() {
        assertEquals(7, da.getActivityList().size());
    }

    @Test
    void testGetActivity() {
        assertEquals("Music", da.getActivity("Music").getActivityName());
        assertNull(da.getActivity("Something"));
    }

}
