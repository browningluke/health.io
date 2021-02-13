package model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DateCodeTest {

    DateCode d1;
    DateCode d2;

    @BeforeEach
    void createDateCode() {
        d1 = new DateCode(2020, 2, 1);
        d2 = new DateCode(2021, 6, 5);
    }

    @Test
    void testInit() {
        assertEquals(2020, d1.getYear());
        assertEquals(2, d1.getMonth());
        assertEquals(1, d1.getDate());
        assertEquals(2021, d2.getYear());
    }

    @Test
    void testEqualsNotEqual() {
        // Matching Nothing
        assertFalse(d1.equals(d2));
        assertFalse(d2.equals(d1));

        // Matching Year
        DateCode d3 = new DateCode(2020, 6, 5);
        assertFalse(d3.equals(d1));
        assertFalse(d1.equals(d3));

        // Matching Month
        DateCode d4 = new DateCode(2021, 2, 5);
        assertFalse(d4.equals(d1));
        assertFalse(d1.equals(d4));

        // Matching Date
        DateCode d5 = new DateCode(2021, 5, 1);
        assertFalse(d5.equals(d1));
        assertFalse(d1.equals(d5));

        // Matching Year + Month
        DateCode d6 = new DateCode(2020, 2, 10);
        assertFalse(d6.equals(d1));
        assertFalse(d1.equals(d6));

        // Matching Year + Date
        DateCode d7 = new DateCode(2020, 5, 1);
        assertFalse(d7.equals(d1));
        assertFalse(d1.equals(d7));

        // Matching Month + Date
        DateCode d8 = new DateCode(2021, 2, 1);
        assertFalse(d8.equals(d1));
        assertFalse(d1.equals(d8));
    }

    @Test
    void testEqualsEqual() {
        DateCode d3 = new DateCode(2020, 2, 1);

        assertTrue(d1.equals(d3));
        assertTrue(d3.equals(d1));
        assertFalse(d2.equals(d3));
        assertFalse(d3.equals(d2));
    }

    @Test
    void testToString() {
        DateCode d3 = new DateCode(2020, 10, 11);

        assertEquals("2020-02-01", d1.toString());
        assertEquals("2021-06-05", d2.toString());
        assertEquals("2020-10-11", d3.toString());
    }

    @Test
    void testGetDateAndMonth() {
        DateCode d3 = new DateCode(2020, 10, 11);
        assertEquals("01/02", d1.getDateAndMonth());
        assertEquals("05/06", d2.getDateAndMonth());
        assertEquals("11/10", d3.getDateAndMonth());
    }

}
