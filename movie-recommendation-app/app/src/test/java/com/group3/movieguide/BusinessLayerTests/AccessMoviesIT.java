package com.group3.movieguide.BusinessLayerTests;

import com.group3.movieguide.Business.AccessMovies;
import com.group3.movieguide.Object.MovieModel;
import com.group3.movieguide.Persistence.IMovieDB;
import com.group3.movieguide.Persistence.hsqldb.MovieHSQLDB;
import com.group3.movieguide.utils.TestUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;
import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class AccessMoviesIT {
    private AccessMovies accessMovies;
    private File tempdb;

    @Before
    public void setUp() throws IOException {
        this.tempdb = TestUtils.copyDB();
        final IMovieDB persistenceMovie = new MovieHSQLDB(this.tempdb.getAbsolutePath().replace(".script", ""));
        this.accessMovies = new AccessMovies(persistenceMovie);
    }

    @Test
    public void testQueryGenres()
    {
        final List<MovieModel> horrorMovies = accessMovies.queryMovies(null, "Horror");

        for (int i=0; i<horrorMovies.size(); i++)
        {
            String horrorGenre = null;
            //Check that
            for (int j=0; j<horrorMovies.get(i).getGenres().length; j++)
            {
                if (horrorMovies.get(i).getGenres()[j].equals("Horror"))
                {
                    horrorGenre = "Horror";
                }
            }
            assertEquals(horrorGenre, "Horror");
        }
    }

    @Test
    public void testQueryTitle()
    {
        final String movieTitle = "Figures And Defenders";
        final List<MovieModel> specificMovie = accessMovies.queryMovies(movieTitle, null);

        assertEquals(specificMovie.size(), 1);
        assertEquals(specificMovie.get(0).getTitle(), "Figures And Defenders");
    }

    @Test
    public void queryAllMovies()
    {
        final List<MovieModel> allMovies = accessMovies.queryMovies(null, null);

        assertEquals(allMovies.size(), 450);
    }

    @Test
    public void getAllMovies()
    {
        accessMovies.queryMovies(null, null);

        List<MovieModel> allMovies = accessMovies.getMovies();

        assertEquals(allMovies.size(), 450);
    }

}

