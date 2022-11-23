package com.group3.movieguide.Presentation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import com.group3.movieguide.Application.*;
import com.group3.movieguide.Business.*;
import com.group3.movieguide.Object.*;
import com.group3.movieguide.R;

public class LoginActivity extends AppCompatActivity {
    private static ApplicationState appState;

    private AccessUsers accessUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        checkAppStateClass();
    }

    private void checkAppStateClass() {
        Intent appI = getIntent();

        if (appI != null)
        {
            if (appState == null)
            {
                appState = (ApplicationState)appI.getSerializableExtra("appState");
            }
        }
    }

    public void sendLoginInfo(View v) {
        TextView emailView = findViewById(R.id.logEmailInput);
        String email = emailView.getText().toString();
        TextView passwordView = findViewById(R.id.logPasswordInput);
        String password = passwordView.getText().toString();
        UserModel loginUser;

        if (accessUsers == null)
            accessUsers = new AccessUsers();

        loginUser = accessUsers.loginUser(email, password);

        if (loginUser != null){
            Toast.makeText(this,"Logged in " + loginUser.getName(), Toast.LENGTH_LONG).show();
            appState.setActiveUser(loginUser);
            Intent i = new Intent(this, HomeActivity.class);
            startActivity(i);
        }
        else{
            Toast.makeText(this,"Incorrect email or password", Toast.LENGTH_LONG).show();
        }
    }
}