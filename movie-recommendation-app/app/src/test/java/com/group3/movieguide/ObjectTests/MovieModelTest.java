package com.group3.movieguide.ObjectTests;

import static org.junit.Assert.assertEquals;

import com.group3.movieguide.Object.MovieModel;

import org.junit.Test;

public class MovieModelTest {

    @Test
    public void testGenreString()
    {
        MovieModel newMovie = new MovieModel(0, "title", new String[] {"Action", "Horror", "Mystery"}, 2000, "Story with many genres", "Canada", "PG-13", 5.0f, 1);
        String expected = "Genres: Action,  Horror,  Mystery";

        assertEquals(expected, newMovie.genreToString());
    }
}
