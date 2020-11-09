---
layout: page
title: My Fitness Buddy Developer Guide
---
* Table of Contents
{:toc}

--------------------------------------------------------------------------------------------------------------------
<div style="page-break-after: always;"></div>

## **1. Introduction**

### 1.1 About the application

My Fitness Buddy `v1.4` is a desktop application that helps you to track your overall change in fitness level by allowing you to add daily records of your weight and calorie input/output.  
My Fitness Buddy also allows users to create a profile and can generate visual charts based on these records so that you can monitor your daily progress and help you achieve your goals.  
This application is optimized for use through a *Command Line Interface (CLI)*, meaning that you operate the application by typing commands into a command box.


### 1.2 Purpose

This document specifies the architecture, implementation and design decisions for
the application, My Fitness Buddy.

### 1.3 Intended Audience

This Developer Guide is for anyone who are interested in the implementation of My Fitness Buddy,
as well as future developers that are interested in further developing of our application.

--------------------------------------------------------------------------------------------------------------------
## **2. Design**

### 2.1 Architecture

<img src="images/ArchitectureDiagram.png" width="450" />

The ***Architecture Diagram*** given above explains the high-level design of My Fitness Buddy. Given below is a quick overview of each component.


**`Main`** has two classes called [`Main`](https://github.com/AY2021S1-CS2103T-W11-3/tp/blob/master/src/main/java/seedu/address/Main.java) and [`MainApp`](https://github.com/AY2021S1-CS2103T-W11-3/tp/blob/master/src/main/java/seedu/address/MainApp.java). It is responsible for,
* At app launch: Initializes the components in the correct sequence, and connects them up with each other.
* At shut down: Shuts down the components and invokes cleanup methods where necessary.

[**`Commons`**](#common-classes) represents a collection of classes used by multiple other components.

The rest of the App consists of four components.

* [**`UI`**](#ui-component): The UI of My Fitness Buddy.
* [**`Logic`**](#logic-component): The command executor.
* [**`Model`**](#model-component): Holds the data of My Fitness Buddy in memory.
* [**`Storage`**](#storage-component): Reads data from, and writes data to, the hard disk.

Each of the four components,

* defines its *API* in an `interface` with the same name as the Component.
* exposes its functionality using a concrete `{Component Name}Manager` class (which implements the corresponding API `interface` mentioned in the previous point.

For example, the `Logic` component (see the class diagram given below) defines its API in the `Logic.java` interface and exposes its functionality using the `LogicManager.java` class which implements the `Logic` interface.

![Class Diagram of the Logic Component](images/LogicClassDiagram.png)

**How the architecture components interact with each other**

The *Sequence Diagram* below shows how the components interact with each other for the scenario where the user issues the command `delete 1`.

<img src="images/ArchitectureSequenceDiagram.png" width="574" />

The sections below give more details of each component.

### 2.2 UI component

**API** :
[`Ui.java`](https://github.com/AY2021S1-CS2103T-W11-3/tp/blob/master/src/main/java/seedu/address/ui/Ui.java)

The `UI` component uses JavaFX UI framework. The layout of these UI parts are defined in matching `.fxml` files that are in the `src/main/resources/view` folder. 
For example, the layout of the [`MainWindow`](https://github.com/AY2021S1-CS2103T-W11-3/tp/blob/master/src/main/java/seedu/address/ui/MainWindow.java) 
is specified in [`MainWindow.fxml`](https://github.com/AY2021S1-CS2103T-W11-3/tp/blob/master/src/main/resources/view/MainWindow.fxml)

The `UI` component,

* Executes user commands using the `Logic` component.
* Listens for changes to `Model` data so that the UI can be updated with the modified data.

The UI consists of a `MainWindow` that is made up of these parts as described below with the respective class diagrams.
All these UI parts, including `MainWindow`, inherit from the abstract `UiPart` class. 

The overall UI structure is decomposed from `MainWindow` into different class diagrams for better clarity.
* `MainWindow`

![Ui_Class_Diagram1](images/UiClassDiagram1.png)

* Profile Panels:
    * `ProfileCardPanel`
    * `ProfileListPanel`
    * `ProfileCard`
    
![Ui_Class_Diagram4](images/UiClassDiagram4.png)

* Day Panel:
    * `DayListPanel`
    * `DayCard`
    
![Ui_Class_Diagram3](images/UiClassDiagram3.png)

* Calorie Panels:
    * `CalorieInputListPanel`
    * `CalorieInputCard`
    * `CalorieOutputListPanel`
    * `CalorieOutputCard`
    
![Ui_Class_Diagram5](images/UiClassDiagram5.png)

* Pop-Out Windows:
    * `CalorieStatsWindow`
    * `WeightStatsWindow`
    * `CommanderBmiStatsWindow`
    * `HelpWindow`
    
![Ui_Class_Diagram6](images/UiClassDiagram6.png)

* Other UI elements:
    * `CommandBox`
    * `ResultDisplay`
    * `StatusBarDaySelected`
    * `StatusBarFooter`
    
![Ui_Class_Diagram2](images/UiClassDiagram2.png)

### 2.3 Logic component

![Structure of the Logic Component](images/LogicClassDiagram.png)

**API** :
[`Logic.java`](https://github.com/AY2021S1-CS2103T-W11-3/tp/blob/master/src/main/java/seedu/address/logic/Logic.java)

1. `Logic` uses the `MyFitnessBuddyParser` class to parse the user command.
1. This results in a `Command` object which is executed by the `LogicManager`.
1. The command execution can affect the `Model` (e.g. adding a day).
1. The result of the command execution is encapsulated as a `CommandResult` object which is passed back to the `Ui`.
1. In addition, the `CommandResult` object can also instruct the `Ui` to perform certain actions, such as displaying help to the user.

Given below is the Sequence Diagram for interactions within the `Logic` component for the `execute("delete 1")` API call.

![Interactions Inside the Logic Component for the `delete 1` Command](images/DeleteSequenceDiagram.png)

<div markdown="span" class="alert alert-info">:information_source: **Note:** The lifeline for `DeleteCommandParser` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline reaches the end of diagram.
</div>

### 2.4 Model component

![Structure of the Model Component](images/ModelClassDiagram.png)

**API** : [`Model.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/model/Model.java)

The `Model`,

The model stores a `UserPref` object that represents the user’s preferences and stores My Fitness Buddy data.
The model also exposes an unmodifiable `ObservableList<Day>` that can be 'observed' e.g. the UI can be bound to this list so that the UI automatically updates when the data in the list change.  

`MyFitnessBuddy` is made up of a `Person` and `UniquePersonList`. The `Person` stores the current Person object that is being referenced in the application. The `UniquePersonList` contains list of
 `Person` objects which stores all the relevant information of the different person that uses MyFitnessBuddy.

  Each person contains a `UniqueDayList` which contains a list of `Day` objects. The `Day` class contains a `Date` and uses `CalorieManager` class as a data structure to store calorie `Input` and `Output`. `CalorieManager` also keeps track and can return the total calorie input and output. 

 
  The `Calorie` class contains a `Time` and `CalorieCount` which `Input` and `Output` inherits from.  `Input` contains an additional `Food` while `Output` contains an addition `Exercise`.		 The `Calorie` class contains a `Time` and `CalorieCount` which `Input` and `Output` inherits from.  `Input` contains an additional `Food` while `Output` contains an addition `Exercise`.
`CalorieManager` is used by `Day` as a data structure to contain `Input` and `Output`. It also keeps track and can return the total calorie input and output. 

### 2.5 Storage component

![Structure of the Storage Component](images/StorageClassDiagram.png)

**API** : [`Storage.java`](https://github.com/AY2021S1-CS2103T-W11-3/tp/blob/master/src/main/java/seedu/address/storage/Storage.java)

The `Storage` component,  
* can save a MyFitnessBuddy object in JSON format   
* can save a UserPref object in JSON format  
* can parse a JSON file of MyFitnessBuddy data to construct a MyFitnessBuddy object  
* can parse a JSON file of UserPref data to construct a UserPref object  

JsonMyFitnessBuddyStorage is the implementation of MyFitnessBuddyStorage interface and supports the storage of all data in the application.  

JsonAdaptedPerson, JsonAdaptedProfile, JsonAdaptedDay, JsonAdaptedCalorieManager, JsonAdaptedInput, 
JsonAdaptedOutput are JSON adapted classes to convert the specified object into a JSON file and read a JSON file to create the object.

### 2.6 Common classes

Classes used by multiple components are in the `seedu.addressbook.commons` package.

--------------------------------------------------------------------------------------------------------------------
## **3. Implementation**

This section describes some noteworthy details on how certain features are implemented.

### Feature: Creates a new Person to My Fitness Buddy

#### Implementations

This feature allows users to create a new `Profile`, consisting of their `Name`, `ID`, `Height` and starting `Weight`,
and a new `UniqueDayList` for daily calorie entries. Upon initialising an empty My Fitness Buddy application, users have to create a new person with a new profile. 
Once the profile has been set for `Person`, the user can now add
daily entries to My Fitness Buddy's `UniqueDayList`.

![CreateProfileSequenceDiagram](images/CreateProfileSequenceDiagram.png)

### Feature: Updates a profile in My Fitness Buddy 

#### Implementations

This feature allows users to update an existing `Profile`, consisting of their *Name*, *ID*, *Height* and *TargetWeight*. 
The user can modify any of the profile entries while `UniqueDayList` that records the daily entries will remain unchanged.

The mechanism utilises the UpdateCommandParser Class to parse the input into the index of the `Profile` to be retrieved where, 
at least one of the entries `Name`, `ID`, `Height` or `Weight` will be modified accordingly.

It than utilise the UpdateCommand class to execute the actual removal of the calorie. It will call the method `getProfile` which uses `MyFitnessBuddy` to locate the Profile class
from the uniquePersonList in which the profile resides in and returns it.

A new profile will be created from the given user inputs and previous relevant profile information. This new profile details will replace the previous profile in 
the uniquePersonList that resides in `MyFitnessBuddy`.

![UpdateProfileSequenceDiagram](images/UpdateProfileSequenceDiagram.png)

### Feature: Views another profile in My Fitness Buddy

#### Implementations

This feature allows users to view another existing `Profile`.

The mechanism utilises the LoginCommandParser Class to parse the input into the index of the `Profile` to be retrieved.

It than utilise the LoginCommand class to execute the actual retrieval of the profile. It will call the method `getPerson` which uses `MyFitnessBuddy` to locate the Person class
from the uniquePersonList in which the person resides in and returns it.

The currentPerson in `MyFitnessBuddy` will be changed to the recently retrieved Person by calling the method `setCurrentPerson`.

Finally, the uniqueDayList for the person's UI component will be updated accordingly and displayed.


![LoginActivityDiagram](images/LoginActivityDiagram.png)


### Feature: Add a new daily record

#### Implementation

This feature enables the user to add a new daily record which includes the daily weight.
Adding a new daily record allows the user to add specific calorie input/output entries into the app.

The mechanism utilises the `AddCommandParser` Class to parse the input into `Date` of `day.Date` Class
and `Weight` of `day.Weight` Class and a new `Day` object is instantiated.

It then utilises the `AddCommand` Class to add the `Day` through the `Model#addDay` method which will add the `Day`
into the `UniqueDayList`.

Below is a sequence diagram when the user executes `add d/2020-11-08 w/76` into My Fitness Buddy.

![Add_day_sequence](images/AddDaySequence.png)


### Feature: Edit the weight and/or date of a day

#### Implementation

This feature allows the user to edit the weight record and/or date record of a particular day that has already been 
added in the day list. 

The mechanism utilises the `EditCommandParser` Class to parse the input into `Index`. An `editDayDescriptor` 
gets created with the  `Date` of `day.Date` Class and `Weight` of `day.Weight` Class if they are included
in the input.  

It then utilises the `EditCommand` Class to create a new `Day` with the `editDayDescriptor` using 
`EditCommand#createEditedDay`. The new `Day` would then replace the old `Day` in the `Model` using the 
`Model#setDay` method. 

Below is a sequence diagram when the user executes `edit 1 d/2020-10-22 w/90` into My Fitness Buddy.

![Edit_day_sequence](images/EditDaySequence.png)


### Feature: Add Calorie feature

#### Overview

This feature allows users to add a calorie to the calorie manager of the day with the specified date.   
If no date is specified, calorie command takes the system date and adds it to the day with the date.

This is an activity diagram to demonstrate what happens when the user uses the calorie command

![AddCalorieActivity](images/AddCalorieActivity.png)

#### Implementation
Step 1: The command CalorieCommand.execute(model) is called by Logic Manager which provides a Model object as argument.  

Step 2: CalorieCommand will first check whether the Model object has a day with the date. If false, it throws an error.  

Step 3: Next, the CalorieCommand will try to get the Day object. First, it calls model.getDay(date), which calls the MyFitnessBuddy object getDay(date) which calls the Person object getDay(date) which finally calls UniqueDayList object getDate(date) and returns a Day object.  

Step 4: It will then assign the Day object to two new Day objects, editDay and targetDay.  

Step 5: CalorieCommand will then edit the Day by changing the Day object’s CalorieManager object. First, it calls editDay.getCalorieManager() to get the Day object’s CalorieManager object.  

Step 6: Depending on whether the boolean isOut is true, it adds the appropriate calorie to the CalorieManager object. If isOut is true, it calls addCalorieOutput(calorie), else it calls addCalorieInput(calorie)  

Step 7: After changing editDay, CalorieCommand will call model.setDay(editDay, targetDay) to replace the targetDay with the edited Day object which contains the new Calorie.  

Sequence diagram when CalorieCommand is executed:
![AddCalorieSequenceDiagram](images/AddCalorieSequence.png)

#### Design Considerations

Alternative 1:  
Instead of having a single CalorieCommand class, have an OutputCommand and InputCommand class  
Pros: Less confusing code  
Cons: Duplicate code as the two commands have very similar functions  

Alternative 2:  
Directly editing the CalorieManager of the Day object instead of using setDay()  
Pros: Less confusing code  
Cons: More bugs will occur, not defensive coding  

### Feature: Remove Calorie feature

#### Implementation

This feature allows user to remove a certain calorie from a particular day.

The mechanism utilises the RemoveCommandParser Class to parse the input into `Type of Calorie` to be remove, `Date` of the
day in which the calorie is in and `Index` of the particular calorie to be removed in the list. 

It than utilise the RemoveCommand class to execute the actual removal of the calorie. It will call the method `getDate` which uses `Date` to locate the Day class
from the uniqueDayList in which the calorie resides in and returns it.

The CalorieManager of the particular Day class will then be retrieved using the `getCalorieManager` method to make the relevant changes. 
CalorieManager contains a list of Input Calories and a list of Output Calories.

Finally,`Type of Calorie` is then used to determined which list the calorie to be removed is in. It will utilise 
the CalorieManager method `removeCalorieOutput` or `removeCalorieInput` depending on `Type of Calorie`. The method will than use `Index`
to remove the calorie at the specific index in the corresponding list of Calories. 

![RemoveCalorieSequenceDiagram](images/RemoveCalorieSequenceDiagram.png)

### Feature: Updated BMI

#### Implementations

![BMI](images/calorieImages/BMI.png)

This feature allows users to see their most updated BMI at their profile card

The mechanism requires a method in the person class "getCurrentBmi()". The chunk of code is shown in the diagram below.
![getCurrentBmi()](images/calorieImages/getCurrentBmi().png)

The method will take the latest weight record to calculate the most updated Bmi to be displayed. If there are no days being
added into the class, the method will take the starting weight of the person to calculate the latest Bmi. The person card
will detect any changes to the most current weight of the person and update the person card respectively.

The method invoke the static calculateBmi method from the Bmi class in the calculator package with the most current weight
and height as the parameter. The bmi calculator will use the following formula (m/h^2) to calculate the bmi.

### Feature: Progress Bar

#### Implementations

![progress_bar](images/calorieImages/progress_bar.png)

This feature allows users to see their progress towards the healthy bmi range of 23.

The mechanism requires a method in the person class "getProgress()". The chunk of code is shown in the diagram below.
![getProgress()](images/calorieImages/getProgress().png)

The method will take the starting bmi and the healthy bmi range of 23 as a bench mark. The method will take the weight
entry of each day and get the user's bmi for the particular day. It will than use it too measure how close or far is it away
from the healthy bmi of 23 and and returns the percentage. The profile card contains a fxml progress bar which will than take
the percentage to update the progress bar respectively. If the bmi is lower than 23, the progress will be 1 and any bmi larger
than the starting bmi will be 0.

### Feature: Calorie Budget

#### Implementations

![progress_bar](images/calorieImages/calorie_budget.png)

This feature allows users to see how much calorie they can afford to consume while still ensuring that they are losing
weight for that day.

The mechanism requires a method "calculateCalorieSurplus". The chunk of code is shown in the diagram below.
![getProgress()](images/calorieImages/CalorieBudget.png)

The day card will call this static method from the CalorieBudget class in the calculator package. They day card will pass in 
the total calorie input and total calorie output of the user for that particular day and the age of the user. The method will
first calculate the basal metabolic rate of the user using their weight, age and height for the day using the revised Harris-Benedict equation.
Since age does not play a significant role in the calculation and most of the recruits will be around 20, we assumed all users
age to be 20. the basal metabolic rate determines how much calorie the user will burn naturally. To get the total calorie budget,
the following formula is used. (totalCalorieOut + adjustedBasal - totalCalorieIn) This value will than be displayed to the user
in the various day card.

### Feature: View Daily Weight and Calorie Statistics

#### Implementation

This feature allows users to view their daily calorie input, 
calorie output and weight in the form of a line chart generated using JavaFX
`LineChart` and `XYChart`. 

This feature can be executed using `stats v/calorie`, `stats v/weight`
or `stats/vall` commands to show either the calorie, weight or all charts respectively.
Given below is a sequence diagram when user executes `stats v/weight` command.

![stats_sequence_diagram](images/StatsSequenceDiagram.png)

The mechanism utilises the `Logic#getFilteredDayList` method to get all
daily records of calorie input/output and weight to be used
as data points and axis of the chart. 

It also utilises JavaFX `ListChangeListner` to listen for any changes
to the list when the user adds or modifies any data, and updates the chart 
instantly.

This feature comprises the `CalorieStatsWindow` and `WeightStatsWindow` classes
and have the same behaviour, with the only difference is the type of data (calorie input/output, weight)
used to generate the chart. Given below is an activity diagram shows how the weight chart is being updated
when there is a change in `DayList`.

![weight_chart_activity_diagram](images/WeightChartActivityDiagram.png)

### Feature: View Overall Progress Statistic

#### Implementation

This feature allows users to view the overall BMI progress of the recruits
in the form of a pie chart generated using JavaFX `PieChart`.

The mechanism utilises the `logic#getFilteredPersonList` method to get all
the BMI values to be used as data for the pie chart.

It also utilises JavaFX `ListChangeListner` to listen for any changes
to the list when the user adds or modifies any data, and updates the chart 
instantly.


### Feature: View all calorie inputs and calorie outputs of a day feature

#### Implementation

This feature allows users to view each of their calorie inputs and calorie 
outputs for a particular day in a `ListView` when a view command is used.
The mechanism utilises the ViewCommandParser class to parse the input and get the 
`Index` of the particular day to be viewed.

It then utilises the ViewCommand class to display the correct message to the user,
depending on the validity of the `Index`. If the `Index` is valid, a CommandResult 
gets returned and the `MainWindow#handleView` gets called with the now zero-based 
`Index`. The `Index` is then used to get the `Day` from the `Logic#getFilteredDayList` 
method. The list of calorie inputs and calorie outputs of that `Day` would be then used 
to fill up the JavaFX `ListView`. The `ListView` is then used to replace the placeholders 
on the right side of the app in `MainWindow`. 

The JavaFx `StatusBar` will also get updated with the date of the `Day` viewed, using
the `MainWindow#setDateLabel` method. 

Whenever a new calorie gets added, the calorie lists will get automatically updated
using the `DayListViewCell#updateItem` method in `DayListPanel`.

The calorie lists can also be viewed by double clicking on a `DayCard`. When a `DayCard`
gets double clicked, the `Hbox#setOnMouseClicked` gets called and the lists and 
status bar gets updated similarly. 

Given below is the sequence diagram when a view command is used.

![ViewSequenceDiagram](images/ViewSequenceDiagram.png)

--------------------------------------------------------------------------------------------------------------------
<div style="page-break-after: always;"></div>

## **4. Documentation, logging, testing, configuration, dev-ops**

* [Documentation guide](Documentation.md)
* [Testing guide](Testing.md)
* [Logging guide](Logging.md)
* [Configuration guide](Configuration.md)
* [DevOps guide](DevOps.md)

--------------------------------------------------------------------------------------------------------------------
<div style="page-break-after: always;"></div>

## **5. Appendix: Requirements**

### Product scope

**Target user profile**:

* has a need to manage their calorie input and weight loss
* prefer desktop apps over other types
* can type fast
* prefers typing to mouse interactions
* is reasonably comfortable using CLI apps

**Value proposition**: manages weight loss/gain and calorie input/output faster than a typical mouse/GUI driven app


### User stories

Priorities: High (must have) - `* * *`, Medium (nice to have) - `* *`, Low (unlikely to have) - `*`

| Priority | As a …​                                    | I want to …​                     | So that I can…​                                                        |
| -------- | ------------------------------------------ | ------------------------------ | ---------------------------------------------------------------------- |
| `* * *`  | new user                                   | create a new profile         | set up the application |
| `* * *`  | user (recruit)                             | record my daily weight         | keep track of them   |
| `* * *`  | user (recruit)                             | record meals and the amount of calorie eaten  | keep track of them |
| `* * *`  | user (recruit)                             | record exercises and the amount of calories lost | keep track of them |
| `* * *`  | user (recruit)                              | view my calorie history   | see if I am hitting my calorie targets |
| `* * *`  | user (recruit)                                | view all my daily weights | see if I am hitting my weight targets | 
| `* * *`  | user (recruit)                                | delete a specified calorie output | remove a wrong input |
|  `* * *` | user  (recruit)                                 | delete a specified calorie input | remove a wrong output |
| `* *`   | user (recruit)                                 | see the progress of my weight and calories | keep track of them |
| `*`      | user (commander)                              | see overall progress of my recruits | manage my recruits better |


### Use cases

(For all use cases below, the **System** is the `MyFitnessBuddy` and the **Actor** is the `user`, unless specified otherwise)

**Use case: UC1 - Create a new profile**

**Preconditions** : Profile list has no profile with the same ID

**MSS**
1. User inputs the create command with the necessary fields (name, ID, height, target weight). 
2. User sees the newly created profile on the profile panel.  

**Use case: UC2 - Add a daily weight record**

**Preconditions** : User is logged in

**MSS**
1.  User inputs the date and weight using the add command.
2.  User sees the newly added weight record of the day.
    
    Use case ends.
    
**Use case: UC3 - Edit a daily weight record**

**Preconditions** : User is logged in 

**MSS**
1.  User inputs the index of the day record and the new weight with the edit command.
2.  User sees the newly edited weight record of the day.
    
    Use case ends.
    
**Extensions**
* 1a. The index of the day record does not exists
* 1b. Error message is shown

Use case resumes at 1.

**Use case: UC4 - Add a calorie input**

**Preconditions** : User is logged in and a day has already been added

**MSS**

1.  User requests to add calorie input using the calorie command. 
2.  User enters the date that he/she wants to add a calorie input.
3.  User inputs the time and name of the calorie input.
4.  User sees the newly updated total calorie input of that day.

    Use case ends.
    
**Use case: UC5 - View calorie's of a particular day recorded**

**Preconditions** : User is logged in, a day has already been added and calories have been 
added for that day

**MSS**

1.  User requests to view the calorie history of a particular day using the view 
command. 
2.  User sees his/her calories of that day.

    Use case ends.

**Extensions**

* 2a. The calorie lists are empty.

  Use case ends.

**Use case: UC6 - View weight statistics**

**Preconditions** : User is logged in

**MSS**

1. User requests to view statistics of their weight history
2. Users sees the line chart of their weight history

   Use case ends.
   
**Extensions**
* 1a. The day list is empty so an empty chart gets shown.

  Use case ends.

### Non-Functional Requirements

1.  Should work on any _mainstream OS_ as long as it has Java `11` or above installed.
2.  Should be able to hold up to 100 days of calorie input/output without a noticeable sluggishness in performance for typical usage.
3.  A user with above average typing speed for regular English text (i.e. not code, not system admin commands) should be able to accomplish most of the tasks faster using commands than using the mouse.


### Glossary

* **Mainstream OS**: Windows, Linux, Unix, OS-X
* **Calorie Input**: The amount of energy eaten from food eaten by the user
* **Calorie Output**: The amount of energy used by the user from exercises
* **Calorie History**: A collection of calorie input and output for the past months
* **Daily Weight**: The weight of the user for a specific day
* **Profile**: The personal details of the user



--------------------------------------------------------------------------------------------------------------------
<div style="page-break-after: always;"></div>

## **6. Appendix: Instructions for manual testing**

Given below are instructions to test the app manually.

<div markdown="span" class="alert alert-info">:information_source: **Note:** These instructions only provide a starting point for testers to work on;
testers are expected to do more *exploratory* testing.

</div>

### Launch and shutdown

1. Initial launch

   1. Download the jar file and copy into an empty folder

   1. Double-click the jar file Expected: Shows the GUI with a set of sample contacts. The window size may not be optimum.

1. Saving window preferences

   1. Resize the window to an optimum size. Move the window to a different location. Close the window.

   1. Re-launch the app by double-clicking the jar file.<br>
       Expected: The most recent window size and location is retained.

### Creating a profile

1. Creating a profile when the current profile list is empty

    1. Test case: `create n/John id/1111 h/170 w/80` <br>
       Expected: Profile gets created.
       
    2. Test case: `create n/John id/1121 h/170 w/80` <br>
       Expected: No profile gets created. Error details shown in the status message. Invalid id number. 
   
### Adding a day

1. Adding a day when the current day list is empty

    1. Test case: `add d/2020-10-21 w/77` <br>
       Expected: Day gets created. 
       
    2. Test case: `add d/2020-10-212 w/77` <br>
       Expected: No Day gets created. Error details shown in the status message. Invalid date.
       
    3. Test case: `add d/2020-10-21 w/sd` <br>
       Expected: No Day gets created. Error details shown in the status message. Invalid weight.

### Deleting a day

1. Deleting a day while all days are being shown

   1. Test case: `delete 1`<br>
      Expected: First contact is deleted from the list. Details of the deleted contact shown in the status message. Timestamp in the status bar is updated.

   1. Test case: `delete 0`<br>
      Expected: No day is deleted. Error details shown in the status message. Status bar remains the same.

   1. Other incorrect delete commands to try: `delete`, `delete x`, `...` (where x is larger than the list size)<br>
      Expected: Similar to previous.
      
### Adding a calorie input to a day 
Assuming the day (2020-10-21) has already been added to the day list.

1. Adding a calorie input to a day that has been added to the list already

    1. Test case: `calorie d/2020-10-21 tp/in t/1200 f/chicken c/300` <br>
       Expected: Calorie input added. Total calorie input count updated for that day. 
       
    2. Test case: `calorie d/2020-10-21 tp/in t/sdfds f/chicken c/300` <br>    
       Expected: Calorie input not added. Total calorie input count remains the same for that day. Invalid time input.  

### Adding a calorie output to a day 
Assuming the day (2020-10-21) has already been added to the day list.

1. Adding a calorie output to a day that has been added to the list already

    1. Test case: `calorie d/2020-10-21 tp/out t/1200 e/running c/300` <br>
       Expected: Calorie output added. Total calorie output count updated for that day. 
       
    2. Test case: `calorie d/2020-10-21 tp/out t/sdfds e/gym c/300` <br>    
       Expected: Calorie input not added. Total calorie input count remains the same for that day. Invalid time input.  

### Viewing the calorie lists of a day
Assuming the day list is not empty and the day at index 1 has calorie 
inputs and ouputs already.

   1. Test case: `view 1`<br>
      Expected: Calories will be shown on the calorie panels on the right. 
      Status bar will update the the date of the day being shown. 

   1. Test case: `view 0`<br>
      Expected: Calorie lists does not update. Error details shown in the status message. Status bar remains the same.

   1. Other incorrect delete commands to try: `view`, `view x`, `...` (where x is larger than the list size)<br>
      Expected: Similar to previous.
  
### Saving data

#### 1. Missing data file

If the file does not exist, the application will launch with sample data. 
This is usually the case for first time users and it is to help them familiarise themselves
with the application and features before starting on a clean application.

Logger will log: "Data file not found. Will be starting with a sample MyFitnessBuddy"  

Users can simulate this by going into the data folder of the JAR file and deleting the file named myfitnessbuddy.

#### 2. Corrupted data file

If the data inside the JSON file is corrupted, the application will launch as a clean application with no data.
An empty data file will be created.

Logger will log: "Data file not in the correct format. Will be starting with a new MyFitnessBuddy"

Users can simulate this by going into the data folder, opening the myfitnessbuddy file and editing the contents inside the file.
