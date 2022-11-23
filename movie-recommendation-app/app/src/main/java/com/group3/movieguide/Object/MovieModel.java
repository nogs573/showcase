package com.group3.movieguide.Object;

import com.google.common.base.Objects;

import java.io.Serializable;//for intents. passing object between activities
import java.util.Arrays;

public class MovieModel implements Serializable{

    //Attributes
    private int id;
    private String title;
    private String[] genres;
    private int releaseDate;
    private String summary;
    private String country;
    private String contentRating;
    private float starScore;
    private int numVotes;

    //Constructors
    public MovieModel(int id, String title, String[] genres, int releaseDate, String summary, String country, String contentRating, float starScore, int numVotes)
    {
        this.id = id;
        this.title = title;
        this.genres = genres;
        this.releaseDate = releaseDate;
        this.summary = summary;
        this.country = country;
        this.contentRating = contentRating;
        this.starScore = starScore;
        this.numVotes = numVotes;
    }

    //Getters and setters
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }
    public String[] getGenres() {
        return genres;
    }
    public void setGenres(String[] genres) {this.genres = genres;}
    public int getReleaseDate() {return releaseDate;}
    public String getSummary() {
        return summary;
    }
    public float getStarScore() { return starScore; }
    public String getCountry() { return country; }
    public String getContentRating() {return contentRating;}

    public String genreToString(){
        String result = "Genres: ";

        for(String genre: genres){
            result += genre + ",  ";
        }
        result = result.substring(0, result.length() - 3);//remove the ",  "

        return result;
    }

    public boolean containsGenre( MovieModel movie, String keyGenre ) {
        boolean containsGenre = false;

        String[] genres = movie.getGenres();

        for( int i = 0; i < genres.length ; i++ ) {

            if( genres[i].equals(keyGenre) )
                containsGenre = true;
        }
        return containsGenre;
    }

    @Override
    public String toString() {
        return "MovieModel{" +
                "title='" + title + '\'' +
                ", genres=" + Arrays.toString(genres) +
                ", releaseDate=" + releaseDate +
                ", summary='" + summary + '\'' +
                ", country='" + country + '\'' +
                ", contentRating='" + contentRating + '\'' +
                ", starScore=" + starScore +
                ", numVotes=" + numVotes +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MovieModel that = (MovieModel) o;
        return getReleaseDate() == that.getReleaseDate() && Float.compare(that.getStarScore(), getStarScore()) == 0 && numVotes == that.numVotes && Objects.equal(getTitle(), that.getTitle()) && Objects.equal(getGenres(), that.getGenres()) && Objects.equal(getSummary(), that.getSummary()) && Objects.equal(getCountry(), that.getCountry()) && Objects.equal(getContentRating(), that.getContentRating());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getTitle(), getGenres(), getReleaseDate(), getSummary(), getCountry(), getContentRating(), getStarScore(), numVotes);
    }
}
