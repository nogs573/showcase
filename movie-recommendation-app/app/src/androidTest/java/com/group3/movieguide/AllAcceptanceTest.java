package com.group3.movieguide;


import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */

@RunWith(Suite.class)
@Suite.SuiteClasses({
            AccountCreationTest.class,
            SearchTest.class,
            QuizTest.class,
            LoginTest.class,
    })

public  class AllAcceptanceTest {
    }


