package model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class DayTest {

    Day day;

    @BeforeEach
    void createDay() {
        day = new Day("2021-02-11");
    }

    @Test
    void testInit() {
        assertEquals(0, day.getMoodListLength());
        assertEquals(-1, day.getSleepHours());
        assertEquals("2021-02-11", day.getDateID());
    }

    @Test
    void testAddMoodValid() {
        Mood mood1 = new Mood();
        Mood mood2 = new Mood();

        assertTrue(day.addMood(mood1));
        assertEquals(mood1, day.getMood(0));

        assertTrue(day.addMood(mood2));
        assertEquals(mood1, day.getMood(0));
        assertEquals(mood2, day.getMood(1));
    }

    @Test
    void testAddMoodInvalid() {
        Mood mood1 = new Mood();
        Mood mood2 = new Mood();
        Mood mood3 = new Mood();

        assertTrue(day.addMood(mood1));
        assertEquals(mood1, day.getMood(0));

        assertTrue(day.addMood(mood2));
        assertEquals(mood1, day.getMood(0));
        assertEquals(mood2, day.getMood(1));

        assertFalse(day.addMood(mood3));
        assertEquals(mood1, day.getMood(0));
        assertEquals(mood2, day.getMood(1));
    }

    @Test
    void testReplaceMood() {
        Mood mood1 = new Mood();
        Mood mood2 = new Mood();
        Mood mood3 = new Mood();
        Mood mood4 = new Mood();

        assertTrue(day.addMood(mood1));
        assertTrue(day.addMood(mood2));

        day.replaceMood(1, mood3);
        assertEquals(mood1, day.getMood(0));
        assertEquals(mood3, day.getMood(1));

        day.replaceMood(0, mood4);
        assertEquals(mood4, day.getMood(0));
        assertEquals(mood3, day.getMood(1));
    }

}