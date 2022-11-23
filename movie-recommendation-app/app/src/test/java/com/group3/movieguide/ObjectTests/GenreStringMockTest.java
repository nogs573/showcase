package com.group3.movieguide.ObjectTests;

import static org.junit.Assert.assertEquals;

import com.group3.movieguide.Object.MovieModel;
import com.group3.movieguide.Persistence.hsqldb.MovieHSQLDB;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

//Mockito Test
@RunWith(MockitoJUnitRunner.class)
public class GenreStringMockTest {

    @Mock
    MovieHSQLDB movieDBMock;

    @Test
    public void testGenre()
    {
        String expected = "Genres: Mystery,  Horror,  Drama";
        String actual;

        List<MovieModel> testList = new ArrayList<>();
        MovieModel testModel = new MovieModel(0, "Accomplice Of Power", new String[]{"Mystery", "Horror", "Drama"}, 2000, "Summary", "Canada", "R", 5.0f, 1);
        testList.add(testModel);

        when(movieDBMock.queryMovies("Accomplice Of Power", null)).thenReturn(testList);

        actual = movieDBMock.queryMovies("Accomplice Of Power", null).get(0).genreToString();

        assertEquals(expected, actual);
    }
}
