package com.group3.movieguide.Persistence.hsqldb;

import android.util.Log;

import com.group3.movieguide.Object.*;
import com.group3.movieguide.Persistence.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class MovieHSQLDB implements IMovieDB
{
    private String dbPath;

    //Movies table
    public static final String MOVIE_TABLE = "Movies";
    public static final String ID_ATTRIB = "id";
    public static final String TITLE_ATTRIB = "title";

    //Genres MVA table
    public static final String GENRE_TABLE = "Genres";


    public MovieHSQLDB(final String dbPathName) {this.dbPath = dbPathName;}

    private Connection connection() throws SQLException
    {
        return DriverManager.getConnection("jdbc:hsqldb:file:" + dbPath + ";shutdown=true", "SA", "");
    }

    private MovieModel parseResultSet(ResultSet rs) throws SQLException
    {
        int movieID = rs.getInt("id");
        String title = rs.getString("title");
        int releaseYear = rs.getInt("releaseYear");
        String summary = rs.getString("summary");
        String country = rs.getString("countryOfOrigin");
        String contentRating = rs.getString("contentRating");
        float starScore = rs.getFloat("starScore");
        String currGenre = rs.getString("genre");
        int numVotes = rs.getInt("numVotes");

        String[] genres = new String[1];
        genres[0] = currGenre;

        return new MovieModel(movieID, title, genres, releaseYear, summary, country, contentRating, starScore, numVotes);
    }

    private String[] arrListToArr(ArrayList<String> a)
    {
        String[] result = new String[a.size()];
        for (int i = 0; i < a.size(); i++) {
            result[i] = a.get(i);
        }
        return result;
    }

    private String strToUpper(String s)
    {
        char[] charArray = s.toCharArray();
        boolean foundSpace = true;
        for (int i=0; i<charArray.length; i++)
        {
            if (Character.isLetter(charArray[i]))
            {
                if (foundSpace)
                {
                    charArray[i] = Character.toUpperCase(charArray[i]);
                    foundSpace = false;
                }
            }
            else
                foundSpace = true;
        }
        return String.valueOf(charArray);
    }

    /**
     * This method will be used by the Quiz feature to find appropriate Movie title for the user input
     * QuizLogic class will call this method via AccessMovies class
     * This method will only be used by QuizLogic class
     * @param genreSet: Set of genres received from QuizLogic, cannot be empty
     * @param contentRatingSet: Set of content rating received form QuizLogic, can be an empty set object
     * @param movieScore: Integer value movie score received form QuizLogic, >= 0 and <= 5
     * @return: A List of MovieModel Objects
     */
    public List<MovieModel> queryMoviesQuiz(Set<String> genreSet, Set<String> contentRatingSet, int movieScore)
    {
        List<MovieModel> returnList = new ArrayList<>();
        MovieModel currMovie;
        String movieQuery;
        PreparedStatement preparedSt;
        ResultSet results;

        try(final Connection c = connection())
        {
            //Default: SELECT * FROM Movies m JOIN Genres g ON m.id = g.id
            movieQuery = "SELECT * FROM " + MOVIE_TABLE + " m INNER JOIN " + GENRE_TABLE + " g ON m." + ID_ATTRIB + " = g." + ID_ATTRIB;
            Log.d("Movie_Query", "Movie Query: " + movieQuery);
            movieQuery += " WHERE ";
            for(int i = 0; i < genreSet.size(); i++)
            {
                if(i == 0)
                    movieQuery += "(";
                if(i < genreSet.size() - 1)
                    movieQuery += "g.genre = ? OR ";
                else
                    movieQuery += "g.genre = ?) AND ";
            }
            Log.d("Movie_Query", "Movie Query: " + movieQuery);
            if(contentRatingSet != null && contentRatingSet.size() > 0)
            {
                for(int i = 0; i  < contentRatingSet.size(); i++)
                {
                    if(i == 0)
                        movieQuery += "(";
                    if(i < contentRatingSet.size() - 1)
                        movieQuery += "m.contentRating = ? OR ";
                    else
                        movieQuery += "m.contentRating = ?) AND ";

                }
            }
            Log.d("Movie_Query", "Movie Query: " + movieQuery);
            movieQuery += "m.starScore > ?";
            movieQuery += " ORDER BY " + TITLE_ATTRIB;

            preparedSt = c.prepareStatement(movieQuery);
            Log.d("The_q", preparedSt.toString());
            int i = 1;
            for(String currentGenre: genreSet)
            {
                preparedSt.setString(i, currentGenre);
                i++;
            }

            if(contentRatingSet != null)
            {
                //i = 1;
                for(String currentRating: contentRatingSet)
                {
                    preparedSt.setString(i, currentRating);
                    i++;
                }
            }

            preparedSt.setInt(i, movieScore);
            // Fetching data from the DB
            results = preparedSt.executeQuery();

            ArrayList<String> allGenres = new ArrayList<>();
            int currId = -1;
            MovieModel buildThisEntry = null;
            while (results.next())
            {
                currMovie = parseResultSet(results);
                if (currId == -1)
                {
                    currId = currMovie.getId();
                }
                if (currMovie.getId() != currId)
                {
                    buildThisEntry.setGenres(arrListToArr(allGenres));
                    returnList.add(buildThisEntry);
                    allGenres.clear();
                    currId = currMovie.getId();
                }
                buildThisEntry = currMovie;
                allGenres.add(currMovie.getGenres()[0]);
            }

            if (buildThisEntry != null)
            {
                buildThisEntry.setGenres(arrListToArr(allGenres));
                returnList.add(buildThisEntry);
            }

            results.close();
            preparedSt.close();

        }
        catch (SQLException e)
        {
            throw new PersistenceException(e);
        }

        return returnList;
    }

    public List<MovieModel> queryMovies(String title, String genre)
    {
        List<MovieModel> returnList = new ArrayList<>();
        MovieModel currMovie;
        String movieQuery;
        PreparedStatement preparedSt;
        ResultSet results;

        try(final Connection c = connection())
        {
            //Default: SELECT * FROM Movies m JOIN Genres g ON m.id = g.id
            movieQuery = "SELECT * FROM " + MOVIE_TABLE + " m INNER JOIN " + GENRE_TABLE + " g ON m." + ID_ATTRIB + " = g." + ID_ATTRIB;

            if (title != null)
            {
                movieQuery += " WHERE title LIKE ?";
                if (genre != null)
                    movieQuery += " AND genre = ?";
            }
            else
            {
                if (genre != null)
                    movieQuery += " WHERE genre = ?";
            }
            movieQuery += " ORDER BY " + TITLE_ATTRIB;

            preparedSt = c.prepareStatement(movieQuery);
            if (title != null)
            {
                title = strToUpper(title); //all movie titles have uppercase at beginning of each word
            }

            if (title != null && genre != null)
            {
                preparedSt.setString(1, title + "%");
                preparedSt.setString(2, genre);
            }
            if (title != null && genre == null)
                preparedSt.setString(1, title +  "%");
            if (title == null && genre != null)
                preparedSt.setString(1, genre);

            results = preparedSt.executeQuery();

            ArrayList<String> allGenres = new ArrayList<>();
            int currId = -1;
            MovieModel buildThisEntry = null;
            while (results.next())
            {
                currMovie = parseResultSet(results);
                if (currId == -1)
                {
                    currId = currMovie.getId();
                }
                if (currMovie.getId() != currId)
                {
                    buildThisEntry.setGenres(arrListToArr(allGenres));
                    returnList.add(buildThisEntry);
                    allGenres.clear();
                    currId = currMovie.getId();
                }
                buildThisEntry = currMovie;
                allGenres.add(currMovie.getGenres()[0]);
            }

            if (buildThisEntry != null)
            {
                buildThisEntry.setGenres(arrListToArr(allGenres));
                returnList.add(buildThisEntry);
            }

            results.close();
            preparedSt.close();
        }
        catch (SQLException e)
        {
            throw new PersistenceException(e);
        }

        return returnList;
    }

}
