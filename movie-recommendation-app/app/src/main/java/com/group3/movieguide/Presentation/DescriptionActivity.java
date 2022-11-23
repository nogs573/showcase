package com.group3.movieguide.Presentation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.*;

import com.group3.movieguide.Application.ApplicationState;
import com.group3.movieguide.Business.AccessMovies;
import com.group3.movieguide.Object.*;
import com.group3.movieguide.R;

public class DescriptionActivity extends AppCompatActivity {
    private static ApplicationState appState;
    private MovieModel movie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);
        checkAppStateClass();
        getMovieModel();
    }

    private void checkAppStateClass() {
        Intent appI = getIntent();

        if (appI != null){
            if (appState == null){
                appState = (ApplicationState)appI.getSerializableExtra("appState");
            }
        }
    }

    private void getMovieModel() {
        Intent movieItemI = getIntent();

        if (movieItemI != null) {
            movie = (MovieModel)movieItemI.getSerializableExtra("movie");
            if(movie != null){
                setMovieText();
            }else{
                Toast.makeText(this,"Movie not received", Toast.LENGTH_LONG).show();
            }

        }else{
            Toast.makeText(this,"Intent not initialize", Toast.LENGTH_LONG).show();
        }
    }

    private void setMovieText() {
        TextView title = findViewById(R.id.desTitleView);
        TextView releaseDate = findViewById(R.id.desDateView);
        TextView genres = findViewById(R.id.desGenresView);
        TextView score = findViewById(R.id.desScoreView);
        TextView country = findViewById(R.id.desCountryView);
        TextView contentRating = findViewById(R.id.desContentView);
        TextView summary = findViewById(R.id.desSummaryView);

        title.setText(movie.getTitle());
        releaseDate.setText(""+movie.getReleaseDate());
        genres.setText(movie.genreToString());
        score.setText((Double.toString(movie.getStarScore())).substring(0,3)); //changed this from 0,4 to 0,3
        country.setText(movie.getCountry());
        contentRating.setText(movie.getContentRating());
        summary.setText(movie.getSummary());
    }
}