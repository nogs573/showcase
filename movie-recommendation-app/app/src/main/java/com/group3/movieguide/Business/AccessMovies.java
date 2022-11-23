package com.group3.movieguide.Business;

import android.util.Log;

import com.group3.movieguide.Application.*;
import com.group3.movieguide.Object.*;
import com.group3.movieguide.Persistence.*;
import java.util.List;
import java.util.Set;

public class AccessMovies {

    private IMovieDB movieDB;
    private List<MovieModel> movies;
    private MovieModel movie;

    public AccessMovies()
    {
        movieDB = DBConnection.getMovieDB();
        movies = null;
        movie = null;
    }

    public AccessMovies(IMovieDB dbImplementation)
    {
        movieDB = dbImplementation;
        movies = null;
        movie = null;
    }

    public List<MovieModel> queryMoviesQuiz(Set<String> genreSet, Set<String> contentRatingSet, int movieScore)
    {
        movies = movieDB.queryMoviesQuiz(genreSet, contentRatingSet, movieScore);
        Log.d("called", "queryQuiz");
        return movies;
    }

    public List<MovieModel> queryMovies(String title, String genre)
    {
        movies = movieDB.queryMovies(title, genre); //we can use this and a getter to remember movie list
        return movies;
    }

    public List<MovieModel> getMovies()
    {
        return movies;
    }

}
