package com.group3.movieguide.Presentation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.group3.movieguide.Application.ApplicationState;
import com.group3.movieguide.Business.AccessMovies;
import com.group3.movieguide.Business.QuizLogic;
import com.group3.movieguide.Object.MovieModel;
import com.group3.movieguide.R;

import java.util.List;

public class QuizResults extends AppCompatActivity
{
    private RecyclerMovieAdapter recyclerMovieAdapter;
    private RecyclerView recyclerView;
    private RecyclerMovieAdapter.RecyclerOnClick recyclerOnClickListener;
    private List<MovieModel> moviesShowing;
    private static ApplicationState appState;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_results);

        checkAppStateClass();
        setRecyclerView();
    }

    private void setRecyclerView()
    {
        moviesShowing = QuizLogic.getMovies();
        recyclerListener();
        recyclerMovieAdapter = new RecyclerMovieAdapter(moviesShowing, recyclerOnClickListener);

        recyclerView = findViewById(R.id.quizRecyclerView);// An Adaptor to setup the RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(recyclerMovieAdapter);
    }

    private void recyclerListener() {
        recyclerOnClickListener = new RecyclerMovieAdapter.RecyclerOnClick() {
            @Override
            public void onClick(View v, int position) {
                Intent desI = new Intent(getApplicationContext(), DescriptionActivity.class);
                MovieModel movie = moviesShowing.get(position);

                desI.putExtra("movie", movie);
                startActivity(desI);
            }
        };
    }

    private void checkAppStateClass()
    {
        Intent appI = getIntent();

        if (appI != null)
        {
            if (appState == null)
            {
                appState = (ApplicationState)appI.getSerializableExtra("appState");
            }
        }
    }

    public void launchHome(View v){
        //goes to home page from button
        Intent homeI = new Intent(this, HomeActivity.class);
        startActivity(homeI);
    }
}