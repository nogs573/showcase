package com.group3.movieguide;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.junit.Assert.assertEquals;

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
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


public class SearchTest {


    @Rule

    public ActivityScenarioRule rule = new ActivityScenarioRule<>(HomeActivity.class);

    @Test
    public void testSearch() {
        //test the filter feature
        ActivityScenario scenario = rule.getScenario();
        onView(withId(R.id.maiSearchButton)).perform(click());
        onView(withId(R.id.seaMovFilterButton)).perform(click());
        onView(withId(R.id.filDateText)).perform(typeText("2001"));
        onView(withId(R.id.filCountryText)).perform(typeText("Canada"));
        onView(withId(R.id.filRatingText)).perform(typeText("NC-17"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.filApplyButton)).perform(click());
        onView(withText("Agents And Enemies")).check(matches(isDisplayed()));
        onView(withId(R.id.seaMovFilterButton)).perform(click());
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.filClearAllButton)).perform(click());
        onView(withId(R.id.filApplyButton)).perform(click());

        //test the dropdown menu
        onView(withId(R.id.seaMovSelGenItem)).perform(click());
        onView(withText("Horror")).inRoot(RootMatchers.isPlatformPopup()).perform(click());
        onView(withId(R.id.seaMovUpdateGenButton)).perform(click());
        onView(withText("Accomplice Of Power")).check(matches(isDisplayed()));
        onView(withId(R.id.seaMovSelGenItem)).perform(click());
        onView(withText("All Genres")).inRoot(RootMatchers.isPlatformPopup()).perform(click());

        //test searchbar
        onView(withId(R.id.seaMovRecyclerView)).check(matches(isDisplayed()));
        onView(withId(R.id.seaMovTextInput)).perform(typeText("Agent Of The"));
        onView(withText("Agent Of The Void")).check(matches(isDisplayed()));
    }


}
