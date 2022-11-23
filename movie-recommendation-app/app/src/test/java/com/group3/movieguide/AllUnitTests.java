package com.group3.movieguide;

import com.group3.movieguide.ApplicationLayerTest.DBConnectionTest;
import com.group3.movieguide.ApplicationLayerTest.MainTest;
import com.group3.movieguide.ObjectTests.GenreStringMockTest;
import com.group3.movieguide.ObjectTests.MovieModelTest;
import com.group3.movieguide.ObjectTests.UserModelTest;
import com.group3.movieguide.PersistenceLayerTests.StubMovieTest;
import com.group3.movieguide.PersistenceLayerTests.StubUserTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        DBConnectionTest.class,
        MainTest.class,
        MovieModelTest.class,
        UserModelTest.class,
        StubMovieTest.class,
        StubUserTest.class,
        GenreStringMockTest.class
})

public class AllUnitTests {

}
