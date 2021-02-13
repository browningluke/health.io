package model;

import static org.junit.jupiter.api.Assertions.*;

import model.activities.Activity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MoodTest {

    Mood m1;
    Mood m2;

    @BeforeEach
    void createMood() {
        m1 = new Mood();
        m2 = new Mood();
    }

    @Test
    void testInit() {
        assertEquals(-1, m1.getMoodScore());
        assertEquals("x", m1.getUIMoodString());
        assertEquals(0, m1.getActivityListLength());

        assertEquals(-1, m2.getMoodScore());
        assertEquals("x", m2.getUIMoodString());
        assertEquals(0, m2.getActivityListLength());
    }

    @Test
    void testSetMoodScore() {
        assertEquals(-1, m1.getMoodScore());
        assertEquals("x", m1.getUIMoodString());
        m1.setMoodScore(4);
        assertEquals(4, m1.getMoodScore());
        assertEquals("4", m1.getUIMoodString());
    }

    @Test
    void testAddActivity() {
        Activity a1 = new Activity("test1");
        Activity a2 = new Activity("test2");

        assertEquals(0, m1.getActivityListLength());

        m1.addActivity(a1);
        assertEquals(1, m1.getActivityListLength());
        assertTrue(m1.containsActivity("test1"));
        assertFalse(m1.containsActivity("test2"));
        assertTrue(m1.getActivityList().contains(a1));
        assertFalse(m1.getActivityList().contains(a2));

        m1.addActivity(a2);
        assertEquals(2, m1.getActivityListLength());
        assertTrue(m1.containsActivity("test1"));
        assertTrue(m1.containsActivity("test2"));
    }

    @Test
    void testRemoveActivity() {
        Activity a1 = new Activity("test1");
        Activity a2 = new Activity("test2");

        assertEquals(0, m1.getActivityListLength());
        m1.removeActivity("test");
        assertEquals(0, m1.getActivityListLength());

        m1.addActivity(a1);
        assertEquals(1, m1.getActivityListLength());
        assertTrue(m1.containsActivity("test1"));

        m1.removeActivity("test2");
        assertEquals(1, m1.getActivityListLength());
        assertTrue(m1.containsActivity("test1"));

        m1.removeActivity("test1");
        assertEquals(0, m1.getActivityListLength());

        m1.addActivity(a2);
        m1.addActivity(a1);
        assertEquals(2, m1.getActivityListLength());
        m1.removeActivity("test2");
        assertEquals(1, m1.getActivityListLength());
        assertTrue(m1.containsActivity("test1"));
    }

    @Test
    void testContainsActivity() {
        Activity a1 = new Activity("test1");
        Activity a2 = new Activity("test2");
        m1.addActivity(a1);
        assertTrue(m1.containsActivity("test1"));
        assertFalse(m1.containsActivity("test2"));
        m1.addActivity(a2);
        assertTrue(m1.containsActivity("test1"));
        assertTrue(m1.containsActivity("test2"));
    }

}
