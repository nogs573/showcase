package com.group3.movieguide.Object;

import com.google.common.base.Objects;

import java.io.Serializable;//for intents. passing object between activities

public class SearchPreferences implements Serializable
{
    private String selectedDate;
    private String selectedCountry;
    private String selectedGenre;
    private String selectedRating;
    private String selectedScore;
    private String searchText;

    public SearchPreferences() {
        selectedDate = null;
        selectedCountry = null;
        selectedGenre = null;
        selectedRating = null;
        selectedScore = null;
        searchText = null;
    }

    public String getSelectedDate() { return selectedDate; }
    public void setSelectedDate(String selectedDate) { this.selectedDate = selectedDate; }

    public String getSelectedCountry() { return selectedCountry; }
    public void setSelectedCountry(String selectedCountry) { this.selectedCountry = selectedCountry; }

    public String getSelectedGenre() { return selectedGenre; }
    public void setSelectedGenre(String selectedGenre) { this.selectedGenre = selectedGenre; }

    public String getSelectedRating() { return selectedRating; }
    public void setSelectedRating(String selectedRating) { this.selectedRating = selectedRating; }

    public String getSelectedScore() { return selectedScore; }
    public void setSelectedScore(String selectedScore) { this.selectedScore = selectedScore; }

    public String getSearchText() { return searchText; }
    public void setSearchText(String searchText) { this.searchText = searchText; }

    @Override
    public String toString() {
        return "SearchPreferences{" +
                "selectedDate='" + selectedDate + '\'' +
                ", selectedCountry='" + selectedCountry + '\'' +
                ", selectedGenre='" + selectedGenre + '\'' +
                ", selectedRating='" + selectedRating + '\'' +
                ", selectedScore='" + selectedScore + '\'' +
                ", searchText='" + searchText + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SearchPreferences that = (SearchPreferences) o;
        return Objects.equal(getSelectedDate(), that.getSelectedDate()) && Objects.equal(getSelectedCountry(), that.getSelectedCountry()) && Objects.equal(getSelectedGenre(), that.getSelectedGenre()) && Objects.equal(getSelectedRating(), that.getSelectedRating()) && Objects.equal(getSelectedScore(), that.getSelectedScore()) && Objects.equal(getSearchText(), that.getSearchText());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getSelectedDate(), getSelectedCountry(), getSelectedGenre(), getSelectedRating(), getSelectedScore(), getSearchText());
    }
}
