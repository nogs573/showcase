package com.group3.movieguide.Presentation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.*;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;

import com.group3.movieguide.Application.*;
import com.group3.movieguide.Object.*;
import com.group3.movieguide.R;

import java.io.*;

public class HomeActivity extends AppCompatActivity {
    private static ApplicationState appState;
    private UserModel activeUser;

    private Button accCreButton;
    private Button loginButton;
    private TextView userAccText;
    private Button logoutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setTitle("Home");

        checkAppStateClass();
        checkAccountPresent();

        //This is important.
        copyDatabaseToDevice();
    }

    private void checkAppStateClass() {
        if (appState == null){
            appState = new ApplicationState();
        }
    }

    private void checkAccountPresent() {
        activeUser = appState.getActiveUser();

        accCreButton = findViewById(R.id.maiAccCreButton);
        loginButton = findViewById(R.id.maiLoginButton);
        userAccText = findViewById(R.id.maiUserAccText);
        logoutButton = findViewById(R.id.maiLogoutButton);

        if (activeUser != null){
            userAccText.setText(activeUser.getName());
            accCreButton.setVisibility(View.INVISIBLE);
            loginButton.setVisibility(View.INVISIBLE);
            userAccText.setVisibility(View.VISIBLE);
            logoutButton.setVisibility(View.VISIBLE);
        }else{
            accCreButton.setVisibility(View.VISIBLE);
            loginButton.setVisibility(View.VISIBLE);
            userAccText.setVisibility(View.INVISIBLE);
            logoutButton.setVisibility(View.INVISIBLE);
        }
    }

    public void launchAccountCreation(View v){
        //goes to account creation page from button
        Intent accCreI = new Intent(this, AccountCreationActivity.class);
        accCreI.putExtra("appState", appState);
        startActivity(accCreI);
    }

    public void launchLogin(View v){
        //goes to login page from button
        Intent loginI = new Intent(this, LoginActivity.class);;
        loginI.putExtra("appState", appState);
        startActivity(loginI);
    }

    public void launchSearchMovie(View v){
        //goes to search movie page from button
        Intent searchMovI = new Intent(this, MovieSearchActivity.class);;
        searchMovI.putExtra("appState", appState);
        startActivity(searchMovI);
    }

    public void launchQuiz(View v){
        //goes to Quiz page from button
        Intent quizI = new Intent(this, Quiz.class);;
        quizI.putExtra("appState", appState);
        startActivity(quizI);
    }

    public void maiLogoutButtonClick(View v){
        activeUser = null;
        appState.setActiveUser(activeUser);

        accCreButton.setVisibility(View.VISIBLE);
        loginButton.setVisibility(View.VISIBLE);
        userAccText.setVisibility(View.INVISIBLE);
        logoutButton.setVisibility(View.INVISIBLE);

        Toast.makeText(this,"User Logout", Toast.LENGTH_LONG).show();
    }

    private void copyDatabaseToDevice()
    {
        final String DB_PATH = "db";

        String[] assetNames;
        Context context = getApplicationContext();
        File dataDirectory = context.getDir(DB_PATH, Context.MODE_PRIVATE);
        AssetManager assetManager = getAssets();

        try {

            assetNames = assetManager.list(DB_PATH);
            for (int i = 0; i < assetNames.length; i++) {
                assetNames[i] = DB_PATH + "/" + assetNames[i];
            }

            copyAssetsToDirectory(assetNames, dataDirectory);

            Main.setDBPath(dataDirectory.toString() + "/" + Main.getDBPathName());

        } catch (final IOException ioe) {
            Log.d("MyApp", "Unable to access application data: " + ioe.getMessage());
        }
    }

    public void copyAssetsToDirectory(String[] assets, File directory) throws IOException {
        AssetManager assetManager = getAssets();

        for (String asset : assets) {
            String[] components = asset.split("/");
            String copyPath = directory.toString() + "/" + components[components.length - 1];

            char[] buffer = new char[1024];
            int count;

            File outFile = new File(copyPath);

            if (!outFile.exists()) {
                InputStreamReader in = new InputStreamReader(assetManager.open(asset));
                FileWriter out = new FileWriter(outFile);

                count = in.read(buffer);
                while (count != -1) {
                    out.write(buffer, 0, count);
                    count = in.read(buffer);
                }

                out.close();
                in.close();
            }
        }
    }

}