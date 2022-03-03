package persistence;

import model.Activity;
import model.ActivityList;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

// Method taken from JsonWriterTest class in
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

class JsonWriterTest extends JsonTest {

    @Test
    void testWriterInvalidFile() {
        try {
            ActivityList activityList = new ActivityList();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyPlanner() {
        try {
            ActivityList activityList = new ActivityList();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyTravelPlanner.json");
            writer.open();
            writer.write(activityList);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyTravelPlanner.json");
            activityList = reader.read();
            assertEquals(0, activityList.getPlanner().size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralPlanner() {
        try {
            ActivityList activityList = new ActivityList();
            activityList.addActivity(new Activity("Granville Island", "Vancouver", 2));
            activityList.addActivity(new Activity("Vancouver Art Gallery", "Vancouver", 3));
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralTravelPlanner.json");
            writer.open();
            writer.write(activityList);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralTravelPlanner.json");
            activityList = reader.read();
            ArrayList<Activity> planner = activityList.getPlanner();
            assertEquals(2, planner.size());
            checkActivity("Granville Island", "Vancouver", 2, planner.get(0));
            checkActivity("Vancouver Art Gallery", "Vancouver", 3, planner.get(1));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

}
