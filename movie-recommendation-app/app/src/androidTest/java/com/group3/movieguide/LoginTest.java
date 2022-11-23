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
import androidx.test.espresso.Espresso;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import com.group3.movieguide.Presentation.HomeActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
@LargeTest

public class LoginTest {

    @Rule
    public ActivityScenarioRule rule = new ActivityScenarioRule<>(HomeActivity.class);



    @Test
    public void login() {
        onView(withId(R.id.maiLoginButton)).perform(click());
        onView(withId(R.id.logEmailInput)).perform(typeText("Spongebob@gmail.com"));
        onView(withId(R.id.logPasswordInput)).perform(typeText("Mommy"));
        closeSoftKeyboard();
        onView(withId(R.id.logLoginButton)).perform(click());


    }
}
