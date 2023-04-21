# MovieGuide Project

The purpose of this project is to create a Android application for Nexus 7 tablet while learning the standards of
software development. We were given groups to work with as a team for the entire semester, held weekly meetings, and
submitted three iterations of our MovieGuide app. The final version of this project was released on April 25th 2022
at 4:30PM.

[Link to release website](https://htmlpreview.github.io/?https://github.com/nogs573/showcase/blob/main/movie-recommendation-app/website/movie-guide-website.html)

## Team Members

Nathan Gagné

Mohammadafaz Munshi

Thomas Peters

Mostafa Raad

Chandra Hofer

Brian Lim

## *Notes from Nathan Gagné

*This is a toy application. It does not contain real movies.
For this project, I was in charge of the HSQLDB implementation for storing movies, users, and all relevant attributes.
We used Java with Android, so I wrote everything that has to do with adding/removing Movies and Users using prepared
statements to combat SQL injection. I also created the demo video on our website submission and assisted with many other
issues that came up for others during development. I also wrote nearly all of the documentation.

[Link to original repository with issues, milestones, user stories, etc.](https://code.cs.umanitoba.ca/winter-2022-a02/group-3/movie-recommendation-app.git)

Since that repository is not guaranteed to stay up, I decided to move the files here as well. Links have been updated
to point to this repository for ease of access.

## Brief Overview

Our MovieGuide shows users a list of movies that they might want to watch based on their preferences. To use this app
you can search for movies using search terms. You can also filter on genre, release date, country of origin, PG rating
and/or 5 star score. Another way to find a good move is to use the newly implemented quiz feature. Take a short quiz
and get a list of around ten movie based on the quiz question. Users can also create a account that saves their
preferred genres. 

## How to run the app

To run the app, please clone the main branch of this repository that contains the iteration 3 release of our project.

From command line, this can be done by navigating to a folder of your choice and executing this command:

git clone https://github.com/nogs573/showcase.git

Then, open the movie-recommendation-app folder in android studio, gradle build, and run the app on a Nexus 7 tablet emulator.

## Important Documentation

VISION.md contains the vision statement that we wrote at the beginning of this project to guide design decisions
as well as the branching strategy that we used for version control.

[Link to VISION.md](https://github.com/nogs573/showcase/blob/a0b565ed43cb7009d2c7ae7d089c5cbcfec7745c/movie-recommendation-app/VISION.md)

ARCHITECTURE.md contains an diagram that shows the file structure of our project.

[Link to ARCHITECTURE.md](https://github.com/nogs573/showcase/blob/a0b565ed43cb7009d2c7ae7d089c5cbcfec7745c/movie-recommendation-app/ARCHITECTURE.md)







