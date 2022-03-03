package persistence;

import model.Activity;
import model.ActivityList;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

// Method taken from JsonReaderTest class in
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            ActivityList activityList = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyTravelPlanner() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyTravelPlanner.json");
        try {
            ActivityList activityList = reader.read();
            assertEquals(0, activityList.getPlanner().size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralTravelPlanner() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralTravelPlanner.json");
        try {
            ActivityList activityList = reader.read();
            ArrayList<Activity> planner = activityList.getPlanner();
            assertEquals(2, planner.size());
            checkActivity("Granville Island", "Vancouver", 2, planner.get(0));
            checkActivity("Vancouver Art Gallery", "Vancouver", 3, planner.get(1));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }


}
