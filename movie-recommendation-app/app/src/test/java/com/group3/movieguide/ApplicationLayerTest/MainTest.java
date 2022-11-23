package com.group3.movieguide.ApplicationLayerTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.group3.movieguide.Application.Main;
import com.group3.movieguide.Object.UserModel;

import org.junit.Test;

public class MainTest {

    @Test
    public void testSetUser()
    {
        UserModel activeUser = new UserModel("LoggedInUser", "password", "name", new String[] {});
        Main.setActiveUser(activeUser);

        assertNotNull(Main.getActiveUser());
        assertEquals(activeUser.getEmail(), "LoggedInUser");
    }

    @Test
    public void setDBPath()
    {
        String dataDirectory = "/data/user/0/com.group3.movieguide/app_db";
        String expectedPath = "/data/user/0/com.group3.movieguide/app_db/movieGuide";
        Main.setDBPath(dataDirectory + "/" + Main.getDBPathName());

        assertEquals(expectedPath, Main.getDBPath());
    }

}
