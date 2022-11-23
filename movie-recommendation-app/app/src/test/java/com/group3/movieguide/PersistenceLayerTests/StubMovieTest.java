package com.group3.movieguide.PersistenceLayerTests;

import static org.junit.Assert.assertEquals;

import com.group3.movieguide.Object.MovieModel;
import com.group3.movieguide.Persistence.stub.StubMovieDB;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class StubMovieTest
{
    private StubMovieDB movieDB;
    private int numMovies = 10; //numbers of movies we insert into stub database during constructor

    @Before
    public void setup()
    {
        movieDB = new StubMovieDB();
    }

    @Test
    public void searchAll()
    {
        List<MovieModel> results = movieDB.queryMovies(null, null);
        assertEquals(numMovies, results.size());
    }

    @Test
    public void searchName()
    {
        List<MovieModel> results = movieDB.queryMovies("movieTitle0", null);
        assertEquals(1, results.size());
        assertEquals(0, results.get(0).getId());
    }

    @Test
    public void searchBadName()
    {
        List<MovieModel> results = movieDB.queryMovies("badTitle", null);
        assertEquals(0, results.size());
    }

    @Test
    public void searchGenres()
    {
        //All movies in our stub database have the genres Horror and Drama
        List<MovieModel> results = movieDB.queryMovies(null, "Horror");
        List<MovieModel> results2 = movieDB.queryMovies(null, "Drama");

        assertEquals(numMovies, results.size());
        assertEquals(numMovies, results2.size());
    }

    @Test
    public void searchBadGenre()
    {
        //All movies in our stub database have the genres Horror and Drama
        List<MovieModel> results = movieDB.queryMovies(null, "Action");

        assertEquals(0, results.size());
    }

    @Test
    public void getAllMovies()
    {
        List<MovieModel> allMovies = movieDB.getAllMovies();
        assertEquals(numMovies, allMovies.size());
    }
}
