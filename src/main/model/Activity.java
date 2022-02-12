package model;

// Represents an activity having a description, location and number of hours spending on the activity.
public class Activity {
    private String description;
    private String location;
    private int hours;
    public static final int MAX_HOURS = 24; // max hours can be added to each activity

    public Activity(String description, String location, int hours) {
        this.description = description;
        this.location = location;
        this.hours = hours;
    }

    // REQUIRES: hrs > 0
    // MODIFIES: this
    // EFFECTS:  if hrs <= MAX_HOURS:
    //           - add the number of hours spent on an activity
    //           - return true
    //           otherwise, return false
    public boolean addHours(int hrs) {
        if (getHours() + hrs <= MAX_HOURS) {
            this.hours += hrs;
            return true;
        }
        return false;
    }

    // REQUIRES: hrs > 0
    // MODIFIES: this
    // EFFECTS: if hrs < getHours():
    //          - reduce the number of hours spent on an activity by hrs
    //          - return true
    //          otherwise, return false
    public boolean reduceHours(int hrs) {
        if (hrs < getHours()) {
            this.hours -= hrs;
            return true;
        }
        return false;
    }

    // EFFECTS: get description of activity
    public String getDescription() {
        return description;
    }

    // EFFECTS: get hours of activity
    public int getHours() {
        return hours;
    }

    // EFFECTS: get location of activity
    public String getLocation() {
        return location;
    }

}
