package com.group3.movieguide.Persistence;

import com.group3.movieguide.Object.*;

import java.util.List;
import java.util.Set;

public interface IMovieDB
{
    List<MovieModel> queryMovies(String title, String genre);
    List<MovieModel> queryMoviesQuiz(Set<String> genreSet, Set<String> contentRatingSet, int movieScore);
}
