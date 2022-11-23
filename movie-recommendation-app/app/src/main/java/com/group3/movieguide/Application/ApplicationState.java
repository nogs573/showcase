package com.group3.movieguide.Application;

import com.group3.movieguide.Object.*;
import java.io.Serializable;
import java.util.ArrayList;

/*
This class is used to share the current state of the application
to other classes that may need. For example, if we need to display
the same list of movies that was shown to the user previously,
we keep information we need for that to be possible here. Also keeps
track of who the active user is.
 */
public class ApplicationState implements Serializable{

    //NOT BEING USED CURRENTLY. WILL BE USED IN ITERATION 3

    //Keep track of the user that is currently logged in.
    private static UserModel activeUser;
    //Keep track of the user searches through activities.
    private SearchPreferences search;

    //We store the parameters that were previously used in query when it switched to a different
    //activity. For example, from MovieSearchActivity to DescriptionActivity, we want to go back
    //to the same query -> same list of movies
    private ArrayList<String> movieQueryParams;
    private String searchBarText;

    public ApplicationState()
    {
        activeUser = null;
        movieQueryParams = new ArrayList<>();
        searchBarText = "";
        search = new SearchPreferences();
    }

    //Getters/setters
    public String getSearchBarText() { return searchBarText; }
    public void setSearchBarText(String searchBarText) { this.searchBarText = searchBarText; }
    public ArrayList<String> getMovieQueryParams() { return movieQueryParams; }
    public void setMovieQueryParams(ArrayList<String> movieQueryParams) { this.movieQueryParams = movieQueryParams; }
    public UserModel getActiveUser() { return activeUser; }
    public void setActiveUser(UserModel activeUser) { this.activeUser = activeUser; }
    public SearchPreferences getSearch() { return search; }
    public void setSearch(SearchPreferences search) { this.search = search; }
}
