package com.group3.movieguide.Business;

import android.util.Log;

import com.group3.movieguide.Object.MovieModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * This class is responsible for handling logic of the Quiz Feature
 * It will receive data from the UI Layer (Quiz Questions) and make queries to the Data Layer using Access Movies
 */
public class QuizLogic
{
    private Set<String> genreList;                              // A unique list of genres (String) received form QuizQuestion (UI Layer)
    private Set<String> pgRatingList;                           // A unique list of pgRating (String) received form QuizQuestion (UI Layer)
    private int movieScore;
    private AccessMovies accessMovies;                          // An interface that connects business and persistence layers, used to query list of movie titles

    // Mapping of All questions to respective genres
    private Map<Integer, List<String>> questionMap1;
    private Map<Integer, List<String>> questionMap2;
    private Map<Integer, List<String>> questionMap3;
    private Map<Integer, Integer> questionMap4;

    private static List<MovieModel> duplicateMovieList;

    public QuizLogic()
    {
        // Initialized when genres are added
        genreList = null;

        // Will be initialized when genres are added
        pgRatingList = null;

        // Filling up all question mapping
        questionMap1 = fillQ1();
        questionMap2 = fillQ2();
        questionMap3 = fillQ3();
        questionMap4 = fillQ4();

        // Initialize accessMovies
        accessMovies = new AccessMovies();

        duplicateMovieList = null;
    }

    /**
     * This method will take question number and option number from the UI layer of the quiz and based on that it will search in the
     * appropriate question mapping give a lis of genres
     * Here HashMaps are 0 based hence question number and option number must be >=0
     * @param questionNumber
     * @param optionNumber
     * @return List of String Genres
     */
    public List<String> getQuestionGenre(int questionNumber, int optionNumber)
    {
        List<String> res = null;
        if(questionNumber == 0)
        {
            res = questionMap1.get(optionNumber);
        }
        else if(questionNumber == 1)
        {
            res = questionMap2.get(optionNumber);
        }
        else if(questionNumber == 2)
        {
            res = questionMap3.get(optionNumber);
        }

        return res;
    }

    /**
     * This method is valid only for question 4
     * @param optionNumber
     * @return
     */
    public int getQuestionScore(int optionNumber)
    {
        return questionMap4.get(optionNumber);
    }


    private Map<Integer, List<String>> fillQ1()
    {
        Map<Integer, List<String>> map = new HashMap<>();

        List<String> al = new ArrayList<>();
        al.add( "Non Fiction");
        al.add( "Horror");
        map.put(0, al);

        al = new ArrayList<>();
        al.add( "Fantasy");
        al.add( "Humor");
        map.put(1, al);

        al = new ArrayList<>();
        al.add( "Mystery");
        al.add( "Action");
        map.put(2, al);

        al = new ArrayList<>();
        al.add( "Sci-fi");
        al.add( "Mystery");
        map.put(3, al);

        al = new ArrayList<>();
        al.add( "Romance");
        al.add( "Drama");
        map.put(4, al);

        return map;
    }

    private Map<Integer, List<String>> fillQ2()
    {
        Map<Integer, List<String>> map = new HashMap<>();

        List<String> al = new ArrayList<>();
        al.add( "Sci-fi");
        map.put(0, al);

        al = new ArrayList<>();
        al.add( "Fantasy");
        map.put(1, al);

        al = new ArrayList<>();
        al.add( "Action");
        map.put(2, al);

        al = new ArrayList<>();
        al.add( "Mystery");
        map.put(3, al);

        al = new ArrayList<>();
        al.add( "Romance");
        map.put(4, al);

        al = new ArrayList<>();
        al.add( "Non fiction");
        map.put(5, al);

        al = new ArrayList<>();
        al.add( "Humor");
        map.put(6, al);

        al = new ArrayList<>();
        al.add( "Drama");
        map.put(7, al);

        return map;
    }

    private Map<Integer, List<String>> fillQ3()
    {
        Map<Integer, List<String>> map = new HashMap<>();

        List<String> al = new ArrayList<>();
        al.add( "Mystery");
        al.add( "Non Fiction");
        map.put(0, al);

        al = new ArrayList<>();
        al.add( "Romance");
        al.add( "Humor");
        map.put(1, al);

        al = new ArrayList<>();
        al.add( "Sci-fi");
        al.add( "Drama");
        map.put(2, al);

        al = new ArrayList<>();
        al.add( "Action");
        al.add( "Humor");
        map.put(3, al);

        al = new ArrayList<>();
        al.add( "Non fiction");
        al.add( "Romance");
        map.put(4, al);

        al = new ArrayList<>();
        al.add( "Fantasy");
        al.add( "Drama");
        map.put(5, al);

        return map;
    }

    private Map<Integer, Integer> fillQ4()
    {
        Map<Integer, Integer> map = new HashMap<>();

        map.put(0, 4);                  // Search for Movies where score is > 4

        map.put(1, 3);                  // Search for Movies where score is > 3

        map.put(2, 3);                  // Search for Movies where score is > 2

        map.put(3, 1);                  // Search for Movies where score is > 1

        map.put(4, 0);                  // Search for Movies where score is > 0

        return map;
    }

    private void instantiatePGRatingList()
    {
        if(pgRatingList == null)
            pgRatingList = new HashSet<>();
    }

    private void instantiateGenreList()
    {
        if(genreList == null)
            genreList = new HashSet<>();
    }

    public void sendScore(int score)
    {
        movieScore = score;
    }

    public void sendPGRating(List<String> newRatingList)
    {
        if(newRatingList != null && newRatingList.size() > 0)
        {
            // Instantiate pgRatingList if not instantiated
            instantiatePGRatingList();
            pgRatingList.addAll(newRatingList);
        }

    }

    public void sendGenre(List<String> newGenreList)
    {
        if(newGenreList != null)
        {
            // Instantiate genreList if not instantiated
            instantiateGenreList();
            genreList.addAll(newGenreList);
        }
    }

    /**
     * This method will be called by Question 5 of the Quiz form UI Layer
     * @param newGenreList: List of genres to be removed
     */
    public void removeGenre(List<String> newGenreList)
    {
        Log.d("before_rem", "Working");
        genreList.removeAll(newGenreList);
        Log.d("after_rem", "Working");
    }
    /**
     * This method is for printing data received
     */

    private static List<MovieModel> m_list = null;
    public void printAllData()
    {
        if(genreList != null && movieScore >= 0 && movieScore <= 5)
        {
            m_list = accessMovies.queryMoviesQuiz(genreList, pgRatingList, movieScore);
            Log.d("db_prob", "No problem");
            for (MovieModel movie : m_list) {
                String title = movie.getTitle();
                Log.d("m_title", title);
            }
            Log.d("data_rec", "Score: " + movieScore + "\n GenreList: " + genreList.toString() + "\n RatingList: " + pgRatingList.toString());
        }
    }
//
    public static List<MovieModel> getMovies()
    {
        if(duplicateMovieList == null)
        {
            Collections.shuffle(m_list);
            if(m_list.size() >= 10)
                duplicateMovieList = new ArrayList<>(m_list.subList(0, 10));
            else
                duplicateMovieList = new ArrayList<>(m_list);
        }
        return duplicateMovieList;
    }

}
