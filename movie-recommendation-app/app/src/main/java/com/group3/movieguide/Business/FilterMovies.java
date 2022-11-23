package com.group3.movieguide.Business;

import com.group3.movieguide.Object.MovieModel;

import java.util.List;

public class FilterMovies {

    public static List<MovieModel> applyMovieFilters(List<MovieModel> moviesShowing, String filterYear, String filterCountry, String filterRating, String filterScore) {

        if (moviesShowing != null)
        {
            MovieModel currMovie;
            int filterYearInt = -1; //default value
            float filterScoreFloat = -1.0f; //default value

            //Control the flow of execution
            boolean doDate = false;
            boolean doCountry = false;
            boolean doRating = false;
            boolean doScore = false;
            boolean movieRemoved = false;

            //If the provided fields are not empty, we should filter on them
            if (filterYear != null)
            {
                if (!filterYear.equals(""))
                {
                    filterYearInt = Integer.parseInt(filterYear);
                    doDate = true;
                }
            }
            if (filterCountry != null)
            {
                if (!filterCountry.equals(""))
                    doCountry = true;
            }
            if (filterRating != null)
            {
                if (!filterRating.equals(""))
                    doRating = true;
            }
            if (filterScore != null)
            {
                if (!filterScore.equals(""))
                {
                    filterScoreFloat = Float.parseFloat(filterScore);
                    doScore = true;
                }
            }

            //If anything needs to be filtered on...
            if (doDate || doCountry || doRating || doScore)
            {
                for (int i = moviesShowing.size() - 1; i >= 0; i--)
                {
                    currMovie = moviesShowing.get(i);
                    if (doDate)
                    {
                        if (currMovie.getReleaseDate() != filterYearInt)
                        {
                            moviesShowing.remove(i);
                            movieRemoved = true; //movie already removed after release year filter
                        }
                    }
                    if (doCountry && !movieRemoved) //don't bother checking country if the year didn't match
                    {
                        if (!currMovie.getCountry().equalsIgnoreCase(filterCountry))
                        {
                            moviesShowing.remove(i);
                            movieRemoved = true;
                        }
                    }
                    if (doRating && !movieRemoved)
                    {
                        if (!currMovie.getContentRating().equalsIgnoreCase(filterRating))
                        {
                            moviesShowing.remove(i);
                            movieRemoved = true;

                        }
                    }
                    if (doScore && !movieRemoved)
                    {
                        if (currMovie.getStarScore() < filterScoreFloat)
                        {
                            moviesShowing.remove(i);
                        }
                    }
                    movieRemoved = false;
                }
            }
        }

        return moviesShowing;
    }
}
