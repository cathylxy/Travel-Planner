package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;

// Represents a list of activities
public class ActivityList implements Writable {
    private ArrayList<Activity> planner;
    //private String name;

    public ActivityList() {
        //this.name = name;
        //this.planner = planner;
        planner = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: add an activity to travel planner
    //          add hours of activity to total hours
    public void addActivity(Activity activity) {
        planner.add(activity);
    }

    // REQUIRES: planner is not empty
    // MODIFIES: this
    // EFFECTS: delete an activity from my planner
    //          subtract corresponding hours of total hours
    public void deleteActivity(Activity activity) {
        int pos = planner.indexOf(activity); //get index of activity
        planner.remove(pos);
    }

    // REQUIRES: total >= 0
    // EFFECTS: return total number of hours of all activities
    public int totalHours() {
        int total = 0;
        for (Activity activity : planner) {
            total += activity.getHours();
        }
        return total;
    }

    // EFFECTS: return a list of activities in the same location
    public ArrayList<Activity> activitiesByLocation(String place) {
        ArrayList<Activity> sameLocation = new ArrayList<>();
        for (Activity activity : planner) {
            if (activity.getLocation().equals(place)) {
                sameLocation.add(activity);
            }
        }
        return sameLocation;
    }


    // EFFECTS: get activity with corresponding description
    public Activity findActivity(String name) {
        for (Activity activity : planner) {
            if (name.equals(activity.getDescription())) {
                int pos = planner.indexOf(activity);
                return planner.get(pos);
            }
        }
        return null;
    }

    // EFFECTS: get activity list
    public ArrayList<Activity> getPlanner() {
        return planner;
    }

    // Method taken from WorkRoom class in
    // https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("planner", plannerToJson());
        return json;
    }

    // EFFECTS: returns activity in this planner as a JSON array
    private JSONArray plannerToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Activity activity : planner) {
            jsonArray.put(activity.toJson());
        }

        return jsonArray;
    }
}

