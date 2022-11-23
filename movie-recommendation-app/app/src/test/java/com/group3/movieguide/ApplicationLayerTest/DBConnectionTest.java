package com.group3.movieguide.ApplicationLayerTest;

import static org.junit.Assert.assertNotNull;

import com.group3.movieguide.Application.DBConnection;
import com.group3.movieguide.Persistence.IMovieDB;
import com.group3.movieguide.Persistence.IUserDB;

import org.junit.Test;

public class DBConnectionTest
{
    @Test
    public void testGetMovieDB()
    {
        IMovieDB movieDB = DBConnection.getMovieDB();
        assertNotNull(movieDB);
    }

    @Test
    public void testGetUserDB()
    {
        IUserDB userDB = DBConnection.getUserDB();
        assertNotNull(userDB);
    }

}
