package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ActivityListTest {
    private ActivityList testActivityList;
    private Activity activity1 = new Activity("Granville Island", "Vancouver", 2);
    private Activity activity2 = new Activity("Suspension Bridge", " North Vancouver", 3);
    private Activity activity3 = new Activity("Vancouver Art Gallery", "Vancouver", 3);


    @BeforeEach
    void runBefore() {
        ArrayList activityList = new ArrayList<>();
        testActivityList = new ActivityList(activityList);
    }

    @Test
    void testAddOneActivity() {
        testActivityList.addActivity(activity1);
        assertEquals(activity1, testActivityList.getPlanner().get(0));
        assertEquals(1, testActivityList.getPlanner().size());
        assertEquals(2, testActivityList.getTotal());
    }

    void testAddTwoActivity() {
        testActivityList.addActivity(activity2);
        assertEquals(activity2, testActivityList.getPlanner().get(1));
        assertEquals(2, testActivityList.getPlanner().size());
        assertEquals(5, testActivityList.getTotal());
    }

    @Test
    void testDeleteOneActivity() {
        testActivityList.addActivity(activity1);
        testActivityList.addActivity(activity2);
        testActivityList.deleteActivity(activity1);
        assertEquals(activity2, testActivityList.getPlanner().get(0));
        assertEquals(1, testActivityList.getPlanner().size());
        assertEquals(3, testActivityList.getTotal());
    }

    @Test
    void testTotalHours() {
        testActivityList.addActivity(activity1);
        testActivityList.addActivity(activity2);
        testActivityList.deleteActivity(activity1);
        assertEquals(3, testActivityList.totalHours());
    }

    @Test
    void testActivitiesByLocation() {
        testActivityList.addActivity(activity1);
        testActivityList.addActivity(activity2);
        testActivityList.addActivity(activity3);
        ArrayList<Activity> expectedResult = new ArrayList<>();
        expectedResult.add(activity1);
        expectedResult.add(activity3);
        assertEquals(expectedResult, testActivityList.activitiesByLocation("Vancouver"));
    }



}


