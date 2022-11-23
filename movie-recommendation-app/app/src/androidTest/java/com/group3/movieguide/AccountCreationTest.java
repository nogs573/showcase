package com.group3.movieguide;

import static androidx.test.espresso.Espresso.closeSoftKeyboard;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isChecked;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import com.group3.movieguide.Presentation.HomeActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
@LargeTest

public class AccountCreationTest {

    @Rule

    public ActivityScenarioRule rule = new ActivityScenarioRule<>(HomeActivity.class);


    @Test
    public void createCourse() {
        ActivityScenario scenario = rule.getScenario();
        onView(withId(R.id.maiAccCreButton)).perform(click());
        onView(withId(R.id.accCreEmailInput)).perform(typeText("Spongebob@Cs.com"));
        onView(withId(R.id.accCrePasswordInput)).perform(typeText("Mommy"));
        onView(withId(R.id.accCreListView_data)).perform(click());
        onView(withText("Action")).perform(click());
        onView(withText("OK")).perform(click());
        closeSoftKeyboard();
        onView(withId(R.id.accCreCreateAccButton));


    }

}