# Car Collection Manager

The application will be for a car collector who wants to keep track of and manage
their car collection.

This project is of interest to me as my friends and I hope to have many cars in the future.
I am manifesting an abundant life.

## User Stories

- As a user, I want to be able to be able to create a car and add it to the collection  
- As a user, I want to be able to remove a car from the collection  
- As a user, I want to be able to view a list of the cars in the collection  
- As a user, I want to be able to select a category and view the cars that match   
(One of: RACECAR, SUPERCAR, SPORTSCAR, LUXURY, MUSCLE, VINTAGE, ECONOMY, OTHER)  
- As a user, I want to be able to mark a car as for sale and view the cars in a collection that are for sale  
- As a user, I want to be able to save my collection before quitting the program  
- As a user, I want to be able to load my collection from file after opening the program

## Instructions for End User

- You can load the previous state of my application by running Main.java and selecting "Yes" when asked if you would like
  to load existing car data.
- You can create and add a car to the collection by clicking "Add car", filling out the form and clicking "Add car".
- You can remove a car from the collection by clicking "Remove car", entering the car's ID as seen in the table, and clicking "Remove car".
- You can see only the cars which are for sale by clicking "Show for sale" and looking at the new subtable
- You can locate my visual component by successfully adding or removing a car. There will be a success notification with a thumbs up image.
  Source: https://pixabay.com/vectors/thumb-up-thumb-yes-okay-up-vote-297078/
- You can save the current state of the collection by closing the programm and selecting "Yes" when asked if you would like to save your progress.

## Phase 4: Task 2

- Wed Aug 06 23:34:47 PDT 2025  
Added ID: 5 -- 2002 Honda Civic -- Category: RACECAR -- For sale?: false to the collection

- Wed Aug 06 23:35:08 PDT 2025  
Added ID: 6 -- 2025 Porsche 911 GT3 -- Category: SPORTSCAR -- For sale?: true to the collection

- Wed Aug 06 23:36:00 PDT 2025  
Added ID: 7 -- 2001 Nissan Skyline GTR -- Category: SPORTSCAR -- For sale?: false to the collection

- Wed Aug 06 23:36:08 PDT 2025  
Removed ID: 7 -- 2001 Nissan Skyline GTR -- Category: SPORTSCAR -- For sale?: false

- Wed Aug 06 23:36:13 PDT 2025  
Removed ID: 5 -- 2002 Honda Civic -- Category: RACECAR -- For sale?: false

- Wed Aug 06 23:36:16 PDT 2025  
Filtered cars for sale

## Phase 4: Task 3

- If I had more time to work on this project, I would potentially look into consolidating the DialogManager, FileManager, and TableManager classes
into a single class. Right now, CarManagerUI directly manages and coordinates between three separate helper objects, which creates multiple dependencies and makes the main class responsible for coordinating their interactions. This violates the principle of minimizing dependencies and creates a somewhat complex web of relationships where CarManagerUI must know about the implementation details of each helper class. The consolidation would simplify the relationship with CarManagerUI, however it would likely end up creating a large and complex class which would
not adhere to the Single Responsibility Principle.