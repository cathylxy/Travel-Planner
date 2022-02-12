package model;

import java.util.ArrayList;

// Represents a list of activities
public class ActivityList {
    private ArrayList<Activity> planner;
    private int total;

    public ActivityList(ArrayList<Activity> planner) {
        this.planner = planner;
        this.total = 0;
    }

    // MODIFIES: this, total
    // EFFECTS: add an activity to travel planner
    //          add hours of activity to total hours
    public void addActivity(Activity activity) {
        planner.add(activity);
        total += activity.getHours();
    }

    // REQUIRES: planner is not empty
    // MODIFIES: this, total
    // EFFECTS: delete an activity from my planner
    //          subtract corresponding hours of total hours
    public void deleteActivity(Activity activity) {
        int pos = planner.indexOf(activity); //get index of activity
        planner.remove(pos);
        total -= activity.getHours();
    }

    // REQUIRES: total >= 0
    // EFFECTS: return total number of hours of all activities
    public int totalHours() {
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

    // EFFECTS: get activity list
    public ArrayList<Activity> getPlanner() {
        return planner;
    }

}

