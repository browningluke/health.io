package model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class DayTest {

    Day day;

    @BeforeEach
    void createDay() {
        day = new Day(new DateCode(2021, 2, 11));
    }

    @Test
    void testInit() {
        assertEquals(2, day.getMoodListLength());
        assertEquals(-1, day.getSleepHours());
        assertEquals("2021-02-11", day.getDateCode().toString());
    }

    @Test
    void testGetMood() {
        assertEquals("x", day.getMood(0).getUIMoodString());
        assertEquals(-1, day.getMood(0).getMoodScore());

        assertEquals("x", day.getMood(1).getUIMoodString());
        assertEquals(-1, day.getMood(1).getMoodScore());
    }

    @Test
    void testGetMoodUpdate() {
        day.getMood(0).setMoodScore(4);
        assertEquals(4,day.getMood(0).getMoodScore());
        assertEquals("4",day.getMood(0).getUIMoodString());
        assertEquals("x", day.getMood(1).getUIMoodString());
        assertEquals(-1, day.getMood(1).getMoodScore());

        day.getMood(1).setMoodScore(2);
        assertEquals(4,day.getMood(0).getMoodScore());
        assertEquals("4",day.getMood(0).getUIMoodString());
        assertEquals("2", day.getMood(1).getUIMoodString());
        assertEquals(2, day.getMood(1).getMoodScore());
    }

    @Test
    void testSetSleepHours() {
        assertEquals(-1, day.getSleepHours());
        assertEquals("x", day.getUISleepHours());
        day.setSleepHours(5);
        assertEquals(5, day.getSleepHours());
        assertEquals("5", day.getUISleepHours());
    }




//    @Test
//    void testAddMoodValid() {
//        Mood mood1 = new Mood();
//        Mood mood2 = new Mood();
//
//        assertTrue(day.addMood(mood1));
//        assertEquals(mood1, day.getMood(0));
//
//        assertTrue(day.addMood(mood2));
//        assertEquals(mood1, day.getMood(0));
//        assertEquals(mood2, day.getMood(1));
//    }
//
//    @Test
//    void testAddMoodInvalid() {
//        Mood mood1 = new Mood();
//        Mood mood2 = new Mood();
//        Mood mood3 = new Mood();
//
//        assertTrue(day.addMood(mood1));
//        assertEquals(mood1, day.getMood(0));
//
//        assertTrue(day.addMood(mood2));
//        assertEquals(mood1, day.getMood(0));
//        assertEquals(mood2, day.getMood(1));
//
//        assertFalse(day.addMood(mood3));
//        assertEquals(mood1, day.getMood(0));
//        assertEquals(mood2, day.getMood(1));
//    }
//
//    @Test
//    void testReplaceMood() {
//        Mood mood1 = new Mood();
//        Mood mood2 = new Mood();
//        Mood mood3 = new Mood();
//        Mood mood4 = new Mood();
//
//        assertTrue(day.addMood(mood1));
//        assertTrue(day.addMood(mood2));
//
//        day.replaceMood(1, mood3);
//        assertEquals(mood1, day.getMood(0));
//        assertEquals(mood3, day.getMood(1));
//
//        day.replaceMood(0, mood4);
//        assertEquals(mood4, day.getMood(0));
//        assertEquals(mood3, day.getMood(1));
//    }

}