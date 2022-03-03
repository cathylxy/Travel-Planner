package ui;

import model.Activity;
import model.ActivityList;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static model.Activity.MAX_HOURS;

//Travel Planner Application
public class TravelPlannerApp {
    private ActivityList travelPlan;
    private Scanner input;
    private static final String JSON_STORE = "./data/travelplanner.json";
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    //EFFECTS: run travel planner application
    public TravelPlannerApp() {
        input = new Scanner(System.in);
        travelPlan = new ActivityList();
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        runTravelPlanner();
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    private void runTravelPlanner() {
        boolean keepGoing = true;
        String command;

        init();

        while (keepGoing) {
            displayMenu();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
                keepGoing = false;
            } else {
                processCommand(command);
            }
        }
        System.out.println("\nYou have finished your travel plan!");
    }

    // MODIFIES: this
    // EFFECTS: processes user command
    private void processCommand(String command) {
        if (command.equals("n")) {
            doStart();
        } else if (command.equals("m")) {
            doModify();
        } else if (command.equals("d")) {
            doDisplay();
        } else if (command.equals("s")) {
            saveTravelPlan();
        } else if (command.equals("l")) {
            loadTravelPlan();
        } else {
            System.out.println("Selection not valid");
        }
    }

    // MODIFIES: this
    // EFFECTS: initializes activity list
    private void init() {
        ArrayList activityList = new ArrayList<>();
        travelPlan = new ActivityList();
        input = new Scanner(System.in);
        input.useDelimiter("\n");
    }

    // EFFECTS: displays menu of options to user
    private void displayMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\tn -> new travel plan");
        System.out.println("\tm -> modify travel plan");
        System.out.println("\td -> display travel plan");
        System.out.println("\ts -> save travel plan to file");
        System.out.println("\tl -> load travel plan from file");
        System.out.println("\tq -> quit");
    }

    // MODIFIES: this
    // EFFECTS: start travel plan
    private void doStart() {
        String selection = "";
        System.out.println("\nSelect from:");
        while (!(selection.equals("a") || selection.equals("d"))) {
            System.out.println("\ta -> add activities");
            System.out.println("\td -> delete activities");
            selection = input.next().toLowerCase();
        }
        if (selection.equals("a")) {
            doAdd();
        } else {
            doDelete();
        }
    }

    // MODIFIES: this
    // EFFECTS: add activity with user input of description, location, hours
    private void doAdd() {
        System.out.println("Enter description of activity: ");
        String name = input.next();
        System.out.println("Enter the location of this activity: ");
        String place = input.next();
        System.out.println("Enter the number of hours: ");
        int time = input.nextInt();
        while (time < 0 || time > MAX_HOURS) {
            System.out.println("Hours need to be larger than 0 and smaller than 24. Please re-enter.");
            time = input.nextInt();
        }
        Activity activity = new Activity(name, place, time);
        travelPlan.addActivity(activity);
        System.out.println("You have added an activity!");
    }

    private void doDelete() {
        System.out.println("Enter description of activity: ");
        String name = input.next();
        travelPlan.deleteActivity(travelPlan.findActivity(name));  // command d
        System.out.println("You have deleted an activity!");
    }

    // MODIFIES: this
    // EFFECTS: modify travel plan
    private void doModify() {
        String selection = "";
        System.out.println("\nSelect from:");
        while (!(selection.equals("a") || selection.equals("r"))) {
            System.out.println("\ta -> add hours");
            System.out.println("\tr -> reduce hours");
            selection = input.next().toLowerCase();
        }
        System.out.println("Enter description of activity: ");
        String name = input.next();
        Activity toBeModified = travelPlan.findActivity(name);
        if (toBeModified == null) {
            System.out.println("No Activity is found.");
        }
        if (selection.equals("a")) {
            System.out.println("Enter hours to add: ");
            int add = input.nextInt();
            toBeModified.addHours(add);
        } else {
            System.out.println("Enter hours to reduce: ");
            int reduce = input.nextInt();
            toBeModified.reduceHours(reduce);
        }
        System.out.println("You will spend " + toBeModified.getHours() + " hours on " + toBeModified.getDescription());
    }

    // EFFECTS: display travel plan
    private void doDisplay() {
        String selection = "";
        System.out.println("\nSelect from:");
        while (!(selection.equals("p") || selection.equals("l") || selection.equals("h"))) {
            System.out.println("\tp -> display all activities");
            System.out.println("\tl -> display activities by location");
            System.out.println("\th -> display total hours");
            selection = input.next().toLowerCase();
        }
        if (selection.equals("p")) {
            printTravelPlan();
        } else if (selection.equals("l")) {
            printActivitiesByLocation();
        } else if (selection.equals("h")) {
            travelPlan.totalHours();
            System.out.println("Total hours: " + travelPlan.totalHours() + " hours");
        } else {
            System.out.println("Selection not valid");
        }
    }

    // EFFECTS: prints all the activities in travel plan to the console
    private void printTravelPlan() {
        ArrayList<Activity> activities = travelPlan.getPlanner();
        for (Activity activity : activities) {
            System.out.println(activity);
        }
    }

    // EFFECTS: prints activities by location in travel plan to the console
    private void printActivitiesByLocation() {
        System.out.println("Enter location: ");
        String loc = input.next();
        if (travelPlan.activitiesByLocation(loc).isEmpty()) {
            System.out.println("No activity found");
        } else {
            System.out.println("Activities in " + loc + " are ");
            ArrayList<Activity> activities = travelPlan.activitiesByLocation(loc);
            for (Activity activity : activities) {
                System.out.println(activity.getDescription());
            }
        }
    }

    // Method taken from WorkRoomApp class in
    // https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

    // EFFECTS: saves the travel plan to file
    private void saveTravelPlan() {
        try {
            jsonWriter.open();
            jsonWriter.write(travelPlan);
            jsonWriter.close();
            System.out.println("Saved " + " to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads travel plan from file
    private void loadTravelPlan() {
        try {
            travelPlan = jsonReader.read();
            System.out.println("Loaded " + " from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }


}



