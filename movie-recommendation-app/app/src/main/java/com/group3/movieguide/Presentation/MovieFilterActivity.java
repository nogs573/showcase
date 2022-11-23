package com.group3.movieguide.Presentation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.*;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;

import com.google.android.material.textfield.*;
import com.group3.movieguide.R;

public class MovieFilterActivity extends AppCompatActivity {
    private TextInputLayout dateInput, countryInput, ratingInput, scoreInput;
    private String dateText;
    private String countryText;
    private String ratingText;
    private String scoreText;
    private Button clear;
    private static boolean firstRun = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_filter);

        setup();
        setInput();
        if(firstRun)
        {
            clearAll();
            firstRun = false;
        }
        clearAllOnClick();
    }

    public void setup(){
        //initialize text inputs and buttons
        dateInput = findViewById(R.id.filDateInput);
        countryInput = findViewById(R.id.filCountryInput);
        ratingInput = findViewById(R.id.filRatingInput);
        scoreInput = findViewById(R.id.filScoreInput);
    }

    public void setInput(){
        //initialize buttons
        TextInputEditText dateText = findViewById(R.id.filDateText);
        TextInputEditText countryText = findViewById(R.id.filCountryText);
        TextInputEditText ratingText = findViewById(R.id.filRatingText);
        TextInputEditText scoreText = findViewById(R.id.filScoreText);

        //retrieve the data from sharedpreferences
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

        this.dateText = prefs.getString("DATE", "");
        dateText.setText(this.dateText);
        this.countryText = prefs.getString("COUNTRY", "");
        countryText.setText(this.countryText);
        this.ratingText = prefs.getString("RATING", "");
        ratingText.setText(this.ratingText);
        this.scoreText = prefs.getString("SCORE", "");
        scoreText.setText(this.scoreText);
    }

    public void sendData(View v) {

        //get the data from the edit text
        dateText = (String) dateInput.getEditText().getText().toString().trim();
        countryText = countryInput.getEditText().getText().toString().trim();
        ratingText = ratingInput.getEditText().getText().toString().trim();
        scoreText = scoreInput.getEditText().getText().toString().trim();

        //saving the data
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(MovieFilterActivity.this);
        SharedPreferences.Editor editor = prefs.edit();

        editor.putString("DATE", dateText);
        editor.putString("COUNTRY", countryText);
        editor.putString("RATING", ratingText);
        editor.putString("SCORE", scoreText);
        editor.apply();

        //send the movie filter info to the search activity class
        Intent i = new Intent(MovieFilterActivity.this, MovieSearchActivity.class);

        i.putExtra("DATE", dateText);
        i.putExtra("COUNTRY", countryText);
        i.putExtra("RATING", ratingText);
        i.putExtra("SCORE", scoreText);

        startActivity(i);
    }

    public void clearAllOnClick(){
        clear = findViewById(R.id.filClearAllButton);

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dateInput.getEditText().getText().clear();
                countryInput.getEditText().getText().clear();
                ratingInput.getEditText().getText().clear();
                scoreInput.getEditText().getText().clear();
                dateText = "";
                countryText = "";
                ratingText = "";
                scoreText = "";

            }
        });
    }

    public void clearAll(){
        dateInput.getEditText().getText().clear();
        countryInput.getEditText().getText().clear();
        ratingInput.getEditText().getText().clear();
        scoreInput.getEditText().getText().clear();
        dateText = "";
        countryText = "";
        ratingText = "";
        scoreText = "";
    }
}