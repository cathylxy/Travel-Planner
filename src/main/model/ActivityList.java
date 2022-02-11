package model;

import java.util.ArrayList;

public class ActivityList {
    private ArrayList<Activity> planner;
    private int total;

    public ActivityList(ArrayList<Activity> planner) {
        this.planner = planner;
        this.total = 0;
    }

    // MODIFIES: this
    // EFFECTS: add an activity to planner
    //          add corresponding hours of total hours
    public void addActivity(Activity activity) {
        planner.add(activity);
        total += activity.getHours();
    }

    // REQUIRES: planner is not empty
    // MODIFIES: this
    // EFFECTS: delete an activity from my planner
    //          subtract corresponding hours of total hours
    public void deleteActivity(Activity activity) {
        int pos = planner.indexOf(activity); //get index of activity
        planner.remove(pos);
        total -= activity.getHours();
    }

    // EFFECTS: return total number of hours
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

    public ArrayList<Activity> getPlanner() {
        return planner;
    }

    public int getTotal() {
        return total;
    }

}

