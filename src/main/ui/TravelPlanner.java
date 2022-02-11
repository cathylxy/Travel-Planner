package ui;


import model.Activity;
import model.ActivityList;

import java.util.ArrayList;
import java.util.Scanner;

//Travel Planner Application
public class TravelPlanner {
    private ActivityList travelPlan;
    private Scanner input;

    //EFFECTS: run the travel planner application
    public TravelPlanner() {
        runTravelPlanner();
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    private void runTravelPlanner() {
        boolean keepGoing = true;
        String command = null;

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
        if (command.equals("s")) {
            doStart();
        } else if (command.equals("m")) {
            doModify();
        } else if (command.equals("d")) {
            doDisplay();
        } else {
            System.out.println("Selection not valid");
        }
    }

    // MODIFIES: this
    // EFFECTS: initializes travel plan
    private void init() {
        ArrayList activityList = new ArrayList<>();
        travelPlan = new ActivityList(activityList);
        input = new Scanner(System.in);
        input.useDelimiter("\n");
    }

    // EFFECTS: displays menu of options to user
    private void displayMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\ts -> start travel plan");
        System.out.println("\tm -> modify travel plan");
        System.out.println("\td -> display travel plan");
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
            System.out.println("Enter description of activity: ");
            String name = input.next();
            System.out.println("Enter the location of this activity: ");
            String place = input.next();
            System.out.println("Enter the number of hours: ");
            int time = input.nextInt();
            Activity activity = new Activity(name, place, time);
            travelPlan.addActivity(activity);
            System.out.println("You have added an activity!");
        } else if (selection.equals("d")) {
            travelPlan.deleteActivity(findActivity());
        }
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
        int ind = findActivityIndex(); //enter description name

        ArrayList<Activity> activities = travelPlan.getPlanner();
        ArrayList<Activity> nameList = new ArrayList<>();
        for (Activity activity : activities) {
            Activity toBeModified = travelPlan.getPlanner().get(ind);
            if (selection.equals("a")) {
                System.out.println("Enter hours to add: ");
                int add = input.nextInt();
                toBeModified.addHours(add);
            } else if (selection.equals("r")) {
                System.out.println("Enter hours to reduce: ");
                int reduce = input.nextInt();
                toBeModified.reduceHours(reduce);
            }
        }
    }

    // EFFECTS: display travel plan and hours
    private void doDisplay() {
        String selection = "";
        System.out.println("\nSelect from:");
        while (!(selection.equals("h") || selection.equals("l"))) {
            System.out.println("\th -> display total hours");
            System.out.println("\tl -> display activities by location");
            selection = input.next().toLowerCase();
        }
        if (selection.equals("h")) {
            travelPlan.getTotal();
            System.out.println("Total hours: " + travelPlan.getTotal() + " hours");
        } else if (selection.equals("l")) {
            System.out.println("Enter location: ");
            String loc = input.next();
            if (travelPlan.getPlanner().isEmpty()) {
                System.out.println("No activity found");
            } else {
                System.out.println("Activities in " + loc + " are ");
                ArrayList<Activity> activities = travelPlan.activitiesByLocation(loc);
                for (Activity activity : activities) {
                    System.out.println(activity.getDescription());
                }
            }
        }
    }

    // EFFECTS: get index of activity with corresponding description
    private int findActivityIndex() {
        System.out.println("Enter description of activity: ");
        String name = input.next();
        ArrayList<Activity> activities = travelPlan.getPlanner();
        ArrayList<Activity> nameList = new ArrayList<>();
        for (Activity activity : activities) {
            if (name.equals(activity.getDescription())) {
                return travelPlan.getPlanner().indexOf(activity);
            }
        }
        return -1;
    }

    // EFFECTS: get the activity with corresponding description
    private Activity findActivity() {
        int ind = findActivityIndex(); //enter description name
        ArrayList<Activity> activities = travelPlan.getPlanner();
        for (Activity activity : activities) {
            Activity act = travelPlan.getPlanner().get(ind);
            return act;
        }
        return null;
    }
}



