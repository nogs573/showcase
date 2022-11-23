package com.group3.movieguide.Application;

import com.group3.movieguide.Persistence.*;
import com.group3.movieguide.Persistence.hsqldb.*;

public class DBConnection
{
    private static IMovieDB movieDB = null;
    private static IUserDB userDB = null;

    public static synchronized IMovieDB getMovieDB()
    {
        if (movieDB == null)
        {
            movieDB = new MovieHSQLDB(Main.getDBPath());
        }

        return movieDB;
    }

    public static synchronized IUserDB getUserDB()
    {
        if (userDB == null)
        {
            userDB = new UserHSQLDB(Main.getDBPath());
        }

        return userDB;
    }

}
