package com.group3.movieguide.Business;

import android.util.Log;

import com.group3.movieguide.Application.*;
import com.group3.movieguide.Object.*;
import com.group3.movieguide.Persistence.*;

public class AccessUsers
{
    private IUserDB userDB;

    public AccessUsers()
    {
        userDB = DBConnection.getUserDB();
    }

    public AccessUsers(IUserDB db)
    {
        userDB = db;
    }

    public UserModel queryUsers(String email)
    {
        UserModel user = userDB.queryUser(email);

        return user;
    }

    public UserModel loginUser(String email, String pass)
    {
        UserModel emailExists = queryUsers(email);

        if (emailExists != null)
        {
            if (!emailExists.getPassword().equals(pass))
                emailExists = null;
        }
        return emailExists;
    }

    public UserModel createAccount(String email, String password, String name, String[] genre_liked) {
        UserModel user = null;

        if (ValidateEmail.validate(email) && !password.equals("")) {
            //make: check if user already exists
            user = new UserModel(email, password, name, genre_liked);
            userDB.insertUser(user);//make: return user or null if duplicate
        }
        return user;
    }
}
