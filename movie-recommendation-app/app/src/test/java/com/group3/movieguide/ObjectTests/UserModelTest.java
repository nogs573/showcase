package com.group3.movieguide.ObjectTests;

import static org.junit.Assert.assertEquals;

import com.group3.movieguide.Object.UserModel;

import org.junit.Test;

public class UserModelTest {

    @Test
    public void testGetName()
    {
        UserModel normalName = new UserModel("email", "pass", "name", new String[] {});
        String expected = "name";

        assertEquals(expected, normalName.getName());
    }

    @Test
    public void testGetNoName()
    {
        UserModel noName = new UserModel("email", "pass", "", new String[] {});
        String expected = "email";

        assertEquals(expected, noName.getName());
    }

}
