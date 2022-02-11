package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ActivityTest {
    private Activity testActivity;

    @BeforeEach
    void runBefore() {testActivity = new Activity("Vancouver Art Gallery", "Vancouver", 3);}

    @Test
    void testConstructor() {
        assertEquals("Vancouver Art Gallery", testActivity.getDescription());
        assertEquals("Vancouver", testActivity.getLocation());
        assertEquals(3, testActivity.getHours());
    }

    @Test
    void testAddHours() {
        assertTrue(testActivity.addHours(1));
        assertEquals(4, testActivity.getHours());
    }

    @Test
    void testExceedMaxHours() {
        testActivity = new Activity("Skiing", "Whistler", 24);
        assertFalse(testActivity.addHours(1));
        assertEquals(24, testActivity.getHours());
    }

    @Test
    void testReduceHours(){
        assertTrue(testActivity.reduceHours(1));
        assertEquals(2, testActivity.getHours());
    }

    @Test
    void testBelowMinHours(){
        assertFalse(testActivity.reduceHours(3));
        assertEquals(3, testActivity.getHours());
    }

}