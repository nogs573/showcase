package com.group3.movieguide.Persistence;

import com.group3.movieguide.Object.*;

public interface IUserDB
{
    UserModel insertUser(UserModel newUser);

    UserModel queryUser(String email);
}
