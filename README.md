# HealthIO: A mood, sleep and activity tracking app.

## Description and Personal Interest

My goal is to design a program that can track, store and visualize my mental health in a quantifiable way over time. There exist similar programs on Android that accomplish this, however they offer expensive 'premium' plans, store my personal data on their servers, and require me to use my phone to track it. I am looking to make a program that offers the ability to easily log how I am feeling at certain points throughout the day (on a 1-5 scale), with the ability to add optional data about what events I was doing around the time, visualize this data over a week and export it to a csv file for use in other programs.

This program aims to target two main types of people: 
- Those who have suffered/do suffer with varying moods, depression, any other mental health issue. 
- Those who like to micromanage and log as many aspects of their lives as they can.

I began looking for a program that offered these features a few years ago out of willingness to log and organize as many aspects of my life as I could, as well as a mild curiosity as to how my mental health progressed over time. However, since the outbreak of Covid-19, I have found myself experiencing a much greater range of moods than I did before, and would now greatly benefit from being able to track my moods, and the events linked to them, over a period of time. Since I have a few friends who have also had their mental health deteriorate as a result of the pandemic, I thought that I would take the opportunity to create this program I want as my *CPSC 210* project.

## User Stories

[//]: # (Phase 1 user stories)
- *As a user, I want to be able to* add a mood score and optional events twice a day to my timeline.
- *As a user, I want to be able to* add new days to my timeline.
- *As a user, I want to be able to* add the amount slept the night before.
- *As a user, I want to be able to* visualize my mood and sleep data on a weekly graph.
- *As a user, I want to be able to* export my data to csv format.

[//]: # (Phase 2 user stories)
- *As a user, I want to be able to* save my timeline - which includes all moods, activities and sleep values - to a file.
- *As a user, I want to be able to* load my timeline from a file. 

## Phase 4: Task 2

I chose to select the pre-existing type hierarchy present in the `panels` package.

Parent Class: `AbstractPanel`

Subclasses:

- `CurrentDatePanel`
- `LongDatePanel`
- `MainPanel`
- `MoodPanel`
- `NavigationPanel`
- `StatsPanel`
- `WeekPanel`

Abstract method implemented in all subclasses: `void initPanel()`

## Phase 4: Task 3

After creating and reflecting on the UML diagram for this project, I can say that I am happy with the design and implementation of all the classes in the `model` and `persistence` packages. The level of coupling is appropriate and each class is responsible for one distinct thing.

One problem that is obvious after looking at the UML diagram is the amount of coupling between classes in the `ui` package. Since most of how the program's functionality is based upon the user's interaction with the model, the `ui` package naturally is more complicated than its `model` counterpart, but in the current implementation this manifests itself as significant coupling. If we compare the older cli implementation in the `cli` package to the new Swing implementation, we can see that the coupling between classes has increased significantly. 

The problem can be clearly seen by the fact that `AbstractPanel` has a field referring back to the main `HealthIO` instance and, as a result, all the subclasses of this panel also have this connection. This is mostly due to the various panel & action handler classes updating values or components on different panels, thus having to refer back to the `HealthIO` class. For example, when the user clicks on a mood button to edit a mood, the `MoodButtonClickHandler` (which is a private class of `NagivationPanel`) needs to get the CardLayout from `MainPanel`; which means it needs a connection to the `HealthIO` class and `HealthIO` needs a connection to `MainPanel`. This could be implemented with `MoodButtonClickHandler` having a connection to `MainPanel`, but that would increase the coupling and cause the UML diagram to become even more cluttered as each of these action handlers would need a different class that it has a connection to. Instead, I would like to refactor the program as follows:

- Convert the private classes (the action handlers), that need to have an effect on classes other than the one they are a part of, to their own public classes.
- Make these new classes extend the Java Observable class.
- Refactor the action handlers to set themselves as changed and notify observers whenever they need to make a change.
- Implement the Observer interface on the panels that need to be redrawn (such as `MainPanel`).
- Attach the observers (the panels) to the observables (the action handlers) when creating the UI in the `HealthIO` class.

This would remove the need for all of these panels to have a connection to `HealthIO` and would remove the need for `HealthIO` to have connections to all of these panels. It would obviously still have a dependency on these classes, but remove the need to have `final` fields for them.