package persistence;

import model.Activity;

import static org.junit.jupiter.api.Assertions.assertEquals;

// Method taken from JsonTest class in
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

public class JsonTest {
    protected void checkActivity(String description, String location, int hours, Activity activity) {
        assertEquals(description, activity.getDescription());
        assertEquals(location, activity.getLocation());
        assertEquals(hours, activity.getHours());
    }
}
