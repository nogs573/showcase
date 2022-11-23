package com.group3.movieguide;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.junit.Assert.assertEquals;

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.matcher.RootMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import com.group3.movieguide.Presentation.HomeActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
@LargeTest

public class QuizTest {

    @Rule
    public ActivityScenarioRule rule = new ActivityScenarioRule<>(HomeActivity.class);


    @Test
    public void testQuizFeature() {
        onView(withId(R.id.maiQuizButton)).perform(click());
        onView(withId(R.id.startQuizButton)).perform(click());
        // onView(withId(R.id.startQuizButton)).perform(click());
        onView(withId(R.id.button5)).perform(click());      // First Option
        onView(withId(R.id.button2)).perform(click());      // First Option
        onView(withId(R.id.button4)).perform(click());      // First Option
        onView(withId(R.id.button5)).perform(click());      // First Option

        onView(withId(R.id.adventureChip)).perform(click());// First Option
        onView(withId(R.id.dramaChip)).perform(click());    // Second Option

        // Go to question 5
        onView(withId(R.id.nextButton)).perform(click());    // Second Option

        // Select the chip
        onView(withId(R.id.rRatedChip)).perform(click());// First Option

        // Submit
        onView(withId(R.id.nextButton)).perform(click());    // Second Option

        // Check if the the contents are actually on the scree.
        onView(withId(R.id.quizRecyclerView)).check(matches(isDisplayed()));
    }

}
