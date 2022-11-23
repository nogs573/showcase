package com.group3.movieguide.BusinessLayerTests;

import com.group3.movieguide.Business.AccessUsers;
import com.group3.movieguide.Object.UserModel;
import com.group3.movieguide.Persistence.hsqldb.UserHSQLDB;
import com.group3.movieguide.utils.TestUtils;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/*
*   This test does not check for valid emails. It only checks that the user is a valid user model
*
 */
public class AccessUsersIT {

    private AccessUsers accessUsers;
    private File tempDB;

    @Before
    public void setUp() throws IOException {
        this.tempDB = TestUtils.copyDB();
        final UserHSQLDB persistence = new UserHSQLDB(this.tempDB.getAbsolutePath().replace(".script", ""));
        this.accessUsers = new AccessUsers(persistence);
    }

    //User table is initially empty
    @Test
    public void testCreateAccount()
    {
        String email = "testemail@gmail.com";
        String pass = "testpassword";
        UserModel newUser = accessUsers.createAccount(email, pass, "testName", new String[] {"Horror", "Mystery"});

        UserModel foundUser = accessUsers.loginUser(email, pass);

        assertEquals(newUser.getEmail(), foundUser.getEmail());
        assertEquals(newUser.getPassword(), foundUser.getPassword());
        assertEquals(newUser.getName(), foundUser.getName());
        for (int i=0; i<newUser.getGenrePrefs().length; i++)
        {
            assertEquals(newUser.getGenrePrefs()[i], foundUser.getGenrePrefs()[i]);
        }

    }

    @Test
    public void testLoginNoGenres()
    {
        String email = "testemail@gmail.com";
        String pass = "testpassword";
        accessUsers.createAccount(email, pass, "testName", new String[] {});

        UserModel foundUser = accessUsers.loginUser(email, pass);

        assertNotNull(foundUser);
    }

    @Test
    public void testCreateBadAccount()
    {
        UserModel emailInvalid = accessUsers.createAccount("", "goodpass", "testName", new String[] {"Action"});
        UserModel passInvalid = accessUsers.createAccount("goodemail", "", "testName", new String[] {"Action"});

        assertNull(emailInvalid);
        assertNull(passInvalid);
    }

    @Test
    public void queryGoodUser()
    {
        accessUsers.createAccount("testemail@gmail.com", "testpassword", "testName", new String[] {"Action"});
        UserModel findThisUser = accessUsers.queryUsers("testemail@gmail.com");
        assertNotNull(findThisUser);

        accessUsers.createAccount("testemail@gmail.ca", "testpassword", "testName", new String[] {"Action"});
        findThisUser = accessUsers.queryUsers("testemail@gmail.ca");
        assertNotNull(findThisUser);

        accessUsers.createAccount("a@g.ca", "testpassword", "testName", new String[] {"Action"});
        findThisUser = accessUsers.queryUsers("testemail@gmail.ca");
        assertNotNull(findThisUser);
    }

    @Test
    public void queryBadEmail()
    {
        String userEmail = "";
        String pwd = "";

        UserModel findThisUser = accessUsers.queryUsers("notindatabase");
        assertNull(findThisUser);

        UserModel user1 = accessUsers.createAccount(userEmail, pwd, "testName", new String[]{"Horror", "Mystery"});
        findThisUser = accessUsers.queryUsers(userEmail);
        assertNull(findThisUser);

        //bad email, no password
        userEmail = "P@.com";
        user1 = accessUsers.createAccount(userEmail, pwd, "testName", new String[]{"Horror", "Mystery"});
        findThisUser = accessUsers.queryUsers(userEmail);
        assertNull(findThisUser);

        userEmail = "P@@gmail.com";
        pwd = "testpassword";
        user1 = accessUsers.createAccount(userEmail, pwd, "testName", new String[]{"Horror", "Mystery"});
        findThisUser = accessUsers.queryUsers(userEmail);
        assertNull(findThisUser);

        userEmail = "P @gmail.com";
        pwd = "testpassword";
        user1 = accessUsers.createAccount(userEmail, pwd, "testName", new String[]{"Horror", "Mystery"});
        findThisUser = accessUsers.queryUsers(userEmail);
        assertNull(findThisUser);

        userEmail = "#$%@gmail^%$.com";
        pwd = "testpassword";
        user1 = accessUsers.createAccount(userEmail, pwd, "testName", new String[]{"Horror", "Mystery"});
        findThisUser = accessUsers.queryUsers(userEmail);
        assertNull(findThisUser);


        userEmail = "@gmail.com";
        pwd = "testpassword";
        user1 = accessUsers.createAccount(userEmail, pwd, "testName", new String[]{"Horror", "Mystery"});
        findThisUser = accessUsers.queryUsers(userEmail);
        assertNull(findThisUser);

        userEmail = "@gmail..com";
        pwd = "testpassword";
        user1 = accessUsers.createAccount(userEmail, pwd, "testName", new String[]{"Horror", "Mystery"});
        findThisUser = accessUsers.queryUsers(userEmail);
        assertNull(findThisUser);
    }
}
