# Travel Planner


## Introduction

**Travel Planner** is an application that intends to help people plan their trip by adding activities, setting location and 
amount of time they want to spend on each activity. This application also allows people to keep track and 
make change of their plan during their trip. 
For people who want to well plan their trip can use this application as a tool to achieve their goals.

I like making plan especially for things with lots of uncertainty. Travelling is one of the things. 
I would encounter many problems if I did not make a plan. Therefore, I am very interested in designing an app that 
improve people’s travel experience by helping them make a travel plan. 


## User Stories

- As a user, I want to be able to add one or multiple activities on my travel planner.
- As a user, I want to be able to delete one or multiple activities on my travel planner.
- As a user, I want to be able to add or reduce the number of hours I spend on each activity.
- As a user, I want to be able to view total number of hours I spend on all activities.
- As a user, I want to be able to view a list of activities in the same location. 
- As a user, I want to be able to save my travel planner to file.
- As a user, I want to be able to load my travel planner from file.

## Phase 4: Task 2
Sample Event Log

Wed Mar 30 12:34:10 PDT 2022 \
An activity has been added \
Wed Mar 30 12:34:35 PDT 2022 \
An activity has been added \
Wed Mar 30 12:34:46 PDT 2022 \
An activity has been added \
Wed Mar 30 12:35:02 PDT 2022 \
An activity has been added \
Wed Mar 30 12:35:14 PDT 2022 \
An activity has been deleted 

## Phase 4: Task 3
1. I want to improve the way of searching activity by refactoring the class type of ActivityList.
- My current design searches for an activity by its description. 
Instead of using ArrayList when constructing ActivityList, I could use HashMap<description, Activity> which
uses description as the key and Activity as the value. 
- With the improvement, instead of using for-loop to look for the activity with the same name as the parameter,
I could use the key (description) to find the value (activity).

2. I want to improve the robustness of my code by adding more input validation through exception.
- Under Activity class, throw exception to check if hours are positive (instead of writing REQUIRED clause).
- Under TravelPlannerUI and TravelPlannerApp, throw and handle exception to check if users 
enter the correct type of data (String for description and location; integer for hours). 