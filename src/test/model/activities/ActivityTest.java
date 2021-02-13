package model.activities;

import static org.junit.jupiter.api.Assertions.*;

import model.activities.Activity;
import org.junit.jupiter.api.Test;

public class ActivityTest {

    @Test
    void testInit() {
        Activity a1 = new Activity("Party");
        assertEquals("Party", a1.getActivityName());
    }

}
