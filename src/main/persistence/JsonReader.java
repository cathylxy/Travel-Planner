package persistence;

import model.Activity;
import model.ActivityList;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

// Method taken from JsonReader class in
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

// Represents a reader that reads activity list from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads activity list from file and returns it;
    // throws IOException if an error occurs reading data from file
    public ActivityList read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseActivityList(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses activity list from JSON object and returns it
    private ActivityList parseActivityList(JSONObject jsonObject) {
        ActivityList activityList = new ActivityList();
        addActivities(activityList, jsonObject);
        return activityList;
    }

    // MODIFIES: planner
    // EFFECTS: parses activities from JSON object and adds them to activity list
    private void addActivities(ActivityList planner, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("planner");
        for (Object json : jsonArray) {
            JSONObject nextActivity = (JSONObject) json;
            addActivity(planner, nextActivity);
        }
    }

    // MODIFIES: planner
    // EFFECTS: parses activity from JSON object and adds it to activity list
    private void addActivity(ActivityList planner, JSONObject jsonObject) {
        String description = jsonObject.getString("description");
        String location = jsonObject.getString("location");
        int hours = jsonObject.getInt("hours");
        Activity activity = new Activity(description, location, hours);
        planner.addActivity(activity);
    }
}
