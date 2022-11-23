package com.group3.movieguide.Persistence.stub;

import com.group3.movieguide.Object.UserModel;
import com.group3.movieguide.Persistence.IUserDB;

import java.util.ArrayList;
import java.util.List;

public class StubUserDB implements IUserDB {

    private List<UserModel> userList;

    public StubUserDB()
    {
        userList = new ArrayList<>();
    }

    @Override
    public UserModel insertUser(UserModel newUser)
    {
        userList.add(newUser);
        return newUser;
    }

    @Override
    public UserModel queryUser(String email)
    {
        UserModel foundUser = null;
        for (int i=0; i<userList.size(); i++)
        {
            if (userList.get(i).getEmail().equals(email))
            {
                foundUser = userList.get(i);
            }
        }

        return foundUser;
    }

    public List<UserModel> getAllUsers()
    {
        return userList;
    }
}
