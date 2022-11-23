package com.group3.movieguide.PersistenceLayerTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.group3.movieguide.Object.UserModel;
import com.group3.movieguide.Persistence.stub.StubUserDB;

import org.junit.Test;

import java.util.List;

public class StubUserTest {

    private StubUserDB userDB = new StubUserDB();
    List<UserModel> currList;

    private String email = "testemail";
    private String pass = "testpassword";

    @Test
    public void testInsert()
    {
        UserModel newUser = new UserModel(email, pass, "testName", new String[] {"Action"});
        userDB.insertUser(newUser);

        currList = userDB.getAllUsers();

        assertEquals(1, currList.size());
        assertEquals(email, currList.get(0).getEmail());
    }

    @Test
    public void testQuery()
    {
        UserModel newUser = new UserModel( email, pass, "testName", new String[] {"Action"});
        userDB.insertUser(newUser);

        UserModel foundUser = userDB.queryUser(email);

        assertNotNull(foundUser);
        assertEquals(email, foundUser.getEmail());
    }
}
