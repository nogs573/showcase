package com.group3.movieguide.Persistence.stub;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.group3.movieguide.Object.MovieModel;
import com.group3.movieguide.Persistence.IMovieDB;
import com.group3.movieguide.Persistence.IUserDB;

public class StubMovieDB implements IMovieDB {
	
	static private List<MovieModel> movieList;

	@Override
	public List<MovieModel> queryMoviesQuiz(Set<String> genreSet, Set<String> contentRatingSet, int movieScore) {
		return null;
	}

	public StubMovieDB()
	{
		movieList = new ArrayList<>();
		fillDatabase();
	}
	
	public List<MovieModel> getAllMovies(){
		
		return movieList;
	}
	
	public List<MovieModel> queryMovies( String title, String genre ) {
		
		List<MovieModel> result;
		
		if (title != null)
		{
			result = searchByName(title);
		}
		else if (genre != null)
		{
			result = searchByGenre(genre);
		}
		else
		{
			result = getAllMovies();
		}

		return result;
	}
	
	private List<MovieModel> searchByGenre( String searchGenre ) {
		
		List<MovieModel> result = new ArrayList<>();
		
		for( int i = 0; i < movieList.size() ; i++ )
		{
			if (movieList.get(i).containsGenre(movieList.get(i), searchGenre))
				result.add(movieList.get(i));
		}
		return result;
	}

	private List<MovieModel> searchByName( String searchName ) {
		
		List<MovieModel> result = new ArrayList<>();
		
		for( int i = 0; i < movieList.size() ; i++ )
		{
			if( searchName.equals( movieList.get(i).getTitle() ) )
				result.add( movieList.get(i) );
		}

		return result;
	}

	private void fillDatabase() {

		String[] newGenres = {"Horror","Drama"};

		MovieModel newMovie;

		for (int i=0; i<10; i++)
		{
			newMovie = new MovieModel(i, "movieTitle" + i, newGenres, i+2000, "summary" + i,
					"country"+i, "contentR"+i, (float)i, i);
			movieList.add(newMovie);
		}
	}

} // StubDatabase class ends


