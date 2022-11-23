package com.group3.movieguide.Presentation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.*;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import com.group3.movieguide.Application.*;
import com.group3.movieguide.Business.*;
import com.group3.movieguide.Object.*;
import com.group3.movieguide.R;

import java.util.List;

/**
 * This Class will be used to add functionality to the SearchView Widget(the search bar) in the MoverSearch Activity.
 * It reads the user-input form the search bar on run time and queries it to the database using an interface to the Database (DataBaseHelper) object.
 * This Activity can only be called from the MainActivity by click the search button.
 */

public class MovieSearchActivity extends AppCompatActivity {
    private static ApplicationState appState;
    private AccessMovies accessMovies;
    private SearchPreferences search;
    private List<MovieModel> moviesShowing;

    //for dropdown menu
    private String[] categories;
    private AutoCompleteTextView genreList;
    private SearchView searchBar;

    //recycler view
    private RecyclerMovieAdapter recyclerMovieAdapter;
    private RecyclerView recyclerView;
    private RecyclerMovieAdapter.RecyclerOnClick recyclerOnClickListener;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_search);

        checkAppStateClass();

        setRecyclerView();
        setDropDown();
        setSearchBar();
        setMovieFilter();
    }

    private void checkAppStateClass() {
        Intent appI = getIntent();

        if (appI != null){
            if (appState == null){
                appState = (ApplicationState)appI.getSerializableExtra("appState");
            }
        }

        if (appState == null){
            Toast.makeText(this,"Critical Error with linking App State", Toast.LENGTH_LONG).show();
        } else {
            search = appState.getSearch();
        }

        if(accessMovies == null) {
            accessMovies = new AccessMovies();
        }
        moviesShowing = accessMovies.queryMovies(null, null);//get all movies
    }

    private void setRecyclerView() {
        moviesShowing = accessMovies.queryMovies(null, null);//get all movies
        recyclerListener();
        recyclerMovieAdapter = new RecyclerMovieAdapter(moviesShowing, recyclerOnClickListener);

        recyclerView = findViewById(R.id.seaMovRecyclerView);// An Adaptor to setup the RecyclerView
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
                desI.putExtra("appState", appState);
                startActivity(desI);
            }
        };
    }

    private void setDropDown() {
        //dropdown menu
        categories = getResources().getStringArray(R.array.genre_array);
        if(search.getSelectedGenre() == null) {
            search.setSelectedGenre(categories[0]);
        }//else it get set to the last search

        genreList = findViewById(R.id.seaMovSelGenItem);
        genreList.setText(search.getSelectedGenre(), false);
        ArrayAdapter<String> adapterItems;

        adapterItems = new ArrayAdapter<>(this,R.layout.dropdown_item,categories);
        genreList.setAdapter(adapterItems);

        genreList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                String item = parent.getItemAtPosition(position).toString();
                search.setSelectedGenre(item);
                Toast.makeText(getApplicationContext(), "Genre: " + item, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setSearchBar() {
        searchBar = findViewById(R.id.seaMovTextInput);
        searchBar.setIconified(false);
        searchBar.setQuery(search.getSearchText(), false);//set text from last search

        /* This is Listener method that will be called whenever SearchView (Search Bar)
         Widget will be activated*/
        searchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener()
        {
            /* This method will be called by the SearchView once the user has submitted their query*/
            @Override
            public boolean onQueryTextSubmit(String s)
            {
                search.setSearchText(s);
                updateMoviesShowing();
                return false;
            }

            /* This method will be called whenever there is a change (of text) in the SearchView (search bar)*/
            @Override
            public boolean onQueryTextChange(String s)
            {
                search.setSearchText(s);
                updateMoviesShowing();
                return false;
            }
        });
    }

    public void updateMoviesShowing() {
        if (search.getSearchText().equals("")){
            search.setSearchText(null);
        }

        if (search.getSelectedGenre() != null) {
            if (search.getSelectedGenre().equals(categories[0])){
                search.setSelectedGenre(null);
            }
        }

        moviesShowing = accessMovies.queryMovies(search.getSearchText(), search.getSelectedGenre());
        applyMovieFilter(moviesShowing, search.getSelectedDate(), search.getSelectedCountry(), search.getSelectedRating(), search.getSelectedScore());
        recyclerMovieAdapter.setMovieList(moviesShowing);
        recyclerView.setAdapter(recyclerMovieAdapter);
    }

    public void genreButton(View v){
        search.setSearchText(searchBar.getQuery().toString());
        search.setSelectedGenre(genreList.getText().toString());

        updateMoviesShowing();
    }

    public void setMovieFilter() {
        //movie filter stuff
        TextView dateInput = findViewById(R.id.seaMovDateText);
        TextView countryInput = findViewById(R.id.seaMovCountryText);
        TextView ratingInput = findViewById(R.id.seaMovRatingText);
        TextView scoreInput = findViewById(R.id.seaMovScoreText);

        //get the date/country from the movie filter
        Intent i = getIntent();

        //set date and country if it needs updating
        String date = i.getStringExtra("DATE");
        if (date != null) {
            search.setSelectedDate(date);
        }
        String country = i.getStringExtra("COUNTRY");
        if (country != null) {
            search.setSelectedCountry(country);
        }
        String rating = i.getStringExtra("RATING");
        if (rating != null) {
            search.setSelectedRating(rating);
        }
        String score = i.getStringExtra("SCORE");
        if (score != null) {
            search.setSelectedScore(score);
        }

        //display the filter if user inputs filter, or else keep it blank (to get rid of it showing null)
        if (search.getSelectedDate() == null || search.getSelectedDate().equals(""))
            dateInput.setText("Year: Any");
        else
            dateInput.setText("Year: " + search.getSelectedDate());
        //=============================================
        if (search.getSelectedCountry() == null || search.getSelectedCountry().equals(""))
            countryInput.setText("Country: Any");
        else
            countryInput.setText("Country: " + search.getSelectedCountry());
        //=============================================
        if (search.getSelectedRating() == null || search.getSelectedRating().equals(""))
            ratingInput.setText("Rating: Any");
        else
            ratingInput.setText("Rating: " + search.getSelectedRating());
        //=============================================
        if(search.getSelectedScore() == null || search.getSelectedScore().equals(""))
            scoreInput.setText("Score Above: Any");
        else
            scoreInput.setText("Score Above: " + search.getSelectedScore());

        //change the list
        applyMovieFilter(moviesShowing, search.getSelectedDate(), search.getSelectedCountry(), search.getSelectedRating(), search.getSelectedScore());
    }

    public void launchMovieFilters(View v) {
        //launches an filter page with extra movie filters besides movie category
        Intent i = new Intent(this, MovieFilterActivity.class);
        i.putExtra("appState", appState);
        startActivity(i);
    }

    private void applyMovieFilter(List<MovieModel> movies, String date, String country, String rating, String score) {
        FilterMovies.applyMovieFilters(movies, date, country, rating, score);

        recyclerMovieAdapter.setMovieList(movies);
        recyclerView.setAdapter(recyclerMovieAdapter);
    }
}
