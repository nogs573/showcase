Iteration 3 Worksheet
=====================

Technical debt
-----------------

Throughout the project new buttons and text views were added but sometimes the text was hardcoded on the button instead of storing them in the resource file for string. In iteration 3, we went through all the xml activity file and replaced the hardcoded string value with a link to a string value in the string resource file. https://code.cs.umanitoba.ca/winter-2022-a02/group-3/movie-recommendation-app/-/tree/fd9c9da532455bb4d7e11314c380e769166e529e . This technical debt is reckless and deliberate. It's deliberate because it is simple to add a string to the resource file and link the button or text view to that value if you know what to do. It's reckless because it would eventually need to be done to improve code updatablity and to reduce the warnings android studio give us.

Future Bug Fixes
-----------------

We didn't have time to create a page to view the user account movie list based on the genre list they gave when they created the account. This technical debt is prudent and deliberate. It's deliberate because we didn't have time to implement. It's prudent because we had everything in place to make a new activity for viewing the movie list.

Re-prioritization
-----------------

In iteration 3, the update user preferences feature moved to the next iteration and lowered its priority because at the moment the user can't see a list of moves for they’re account preference anyways, so changing them wouldn't change anything. https://code.cs.umanitoba.ca/winter-2022-a02/group-3/movie-recommendation-app/-/issues/15 . 

System testing
-----------------

All of the code that we wrote for this project is deterministic, meaning that there is no random element anywhere. This means that we did not have to consider flaky tests that can return pass or fail without any changes to the code due to some edge case caused by randomness. However, when we were setting up an acceptance test to verify that a user can create an account and log-in to that account, we had to remember to reset the database to its initial state after the test ends. The reason for this is that if an account was created previously (in a previous run of that system test), Espresso would fail to create a new account with hardcoded credentials because it already exists in the database. This is a type of flaky test that we had to create a workaround for.

Link to test: https://code.cs.umanitoba.ca/winter-2022-a02/group-3/movie-recommendation-app/-/blob/main/app/src/androidTest/java/com/group3/movieguide/AccountCreationTest.java

Challenges in system Testing
-----------------

Setting up the environment for acceptance tests using Espresso was a challenge in and of itself. The sample project used a deprecated test package for their acceptance tests, so we had some issues modeling our own tests after them. Specifically, the ActivityTestRule class that was used in the sample project when it was created many years ago has been replaced with ActivityScenarioRule in the androidx test package. Once the environment was set up, we also ran into some difficulties when accessing elements of a RecyclerView to confirm that the expected behavior occurs when we search for movies.

Veocity chart
-----------------

In iteration 1, we estimated 254 hours of work and spent 145, with a difference of 109.

In iteration 2, we estimated 244 hours of work and spent 198, with a difference of 46.

In iteration 3, we estimated 136 hours of work and spent 125, with a difference of 11.

As is clear from the three statements above, our estimates got better through the course. The estimate for iteration 1 that was wildly off can be explained by the fact that we were inexperienced when it came to estimating how long a single feature would take to implement. Now that we have implemented some basic building blocks of an Android app from the ground up, we have a better idea of how long things take. We overestimated the time setting up a database would take, among other things. The much more accurate estimate of iteration 3 is due to the fact that we had a very good idea of what was left to be done and had gauged how long certain types of features take.


