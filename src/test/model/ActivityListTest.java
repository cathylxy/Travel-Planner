package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ActivityListTest {
    private ActivityList testActivityList;
    private Activity activity1;
    private Activity activity2;
    private Activity activity3;


    @BeforeEach
    void runBefore() {
        ArrayList activityList = new ArrayList<>();
        testActivityList = new ActivityList(activityList);
        activity1 = new Activity("Granville Island", "Vancouver", 2);
        activity2 = new Activity("Suspension Bridge", " North Vancouver", 3);
        activity3 = new Activity("Vancouver Art Gallery", "Vancouver", 3);

    }

    @Test
    void testAddOneActivity() {
        testActivityList.addActivity(activity1);
        assertEquals(activity1, testActivityList.getPlanner().get(0));
        assertEquals(1, testActivityList.getPlanner().size());
    }

    @Test
    void testAddMultipleActivity() {
        testActivityList.addActivity(activity1);
        testActivityList.addActivity(activity2);
        testActivityList.addActivity(activity3);
        assertEquals(activity3, testActivityList.getPlanner().get(2));
        assertEquals(3, testActivityList.getPlanner().size());
    }

    @Test
    void testDeleteOneActivity() {
        testActivityList.addActivity(activity1);
        testActivityList.addActivity(activity2);
        testActivityList.addActivity(activity3);
        testActivityList.deleteActivity(activity1);
        assertEquals(activity2, testActivityList.getPlanner().get(0));
        assertEquals(2, testActivityList.getPlanner().size());
    }

    @Test
    void testTotalHoursIncrease() {
        assertEquals(0, testActivityList.totalHours());
        testActivityList.addActivity(activity1);
        testActivityList.addActivity(activity2);
        assertEquals(5, testActivityList.totalHours());
    }


    @Test
    void testTotalHoursReduce() {
        testActivityList.addActivity(activity1);
        testActivityList.addActivity(activity2);
        assertEquals(5, testActivityList.totalHours());
        testActivityList.deleteActivity(activity1);
        assertEquals(3, testActivityList.totalHours());
    }

    @Test
    void testActivitiesByLocation() {
        testActivityList.addActivity(activity1);
        testActivityList.addActivity(activity2);
        testActivityList.addActivity(activity3);
        ArrayList<Activity> loc = new ArrayList<>();
        loc.add(activity1);
        loc.add(activity3);
        assertEquals(loc, testActivityList.activitiesByLocation("Vancouver"));
    }

    @Test
    void testFindNoActivity() {
        testActivityList.addActivity(activity1);
        testActivityList.addActivity(activity2);
        assertEquals(null, testActivityList.findActivity("Vancouver Art Gallery"));
    }

    @Test
    void testFindActivity() {
        testActivityList.addActivity(activity1);
        testActivityList.addActivity(activity2);
        assertEquals(activity1, testActivityList.findActivity("Granville Island"));
    }


}


