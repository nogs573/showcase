package com.group3.movieguide;

import com.group3.movieguide.BusinessLayerTests.AccessMoviesIT;
import com.group3.movieguide.BusinessLayerTests.AccessUsersIT;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;


@RunWith(Suite.class)
@Suite.SuiteClasses({
        AccessMoviesIT.class,
        AccessUsersIT.class
})
public class AllIntegrationTests {
}
