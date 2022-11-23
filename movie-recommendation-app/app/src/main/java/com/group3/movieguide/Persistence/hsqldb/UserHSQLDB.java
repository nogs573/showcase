package com.group3.movieguide.Persistence.hsqldb;

import static java.sql.Types.VARCHAR;

import com.group3.movieguide.Object.*;
import com.group3.movieguide.Persistence.*;

import java.sql.*;
import java.util.ArrayList;

public class UserHSQLDB implements IUserDB {

    private String dbPath;

    public UserHSQLDB(String dbPathName) {
        this.dbPath = dbPathName;
    }

    private Connection connection() throws SQLException
    {
        return DriverManager.getConnection("jdbc:hsqldb:file:" + dbPath + ";shutdown=true", "SA", "");
    }

    private UserModel parseResultSet(ResultSet rs, boolean useGenre) throws SQLException
    {
        String email = rs.getString("email");
        String pass = rs.getString("password");
        String name = rs.getString("name");
        String[] genres;

        if (useGenre)
            genres = new String[] {rs.getString("genre")};
        else
            genres = null;

        return new UserModel(email, pass, name, genres);
    }

    private String[] arrListToArr(ArrayList<String> a)
    {
        String[] result = new String[a.size()];
        for (int i = 0; i < a.size(); i++) {
            result[i] = a.get(i);
        }
        return result;
    }

    @Override
    public UserModel insertUser(UserModel newUser)
    {
        try(final Connection c = connection())
        {
            //Insert the main entry with email, password, name
            PreparedStatement st = c.prepareStatement("INSERT INTO Users VALUES(?, ?, ?)");
            st.setString(1, newUser.getEmail());
            st.setString(2, newUser.getPassword());
            if (newUser.getName().equals("")) {
                st.setNull(3, VARCHAR);
            }
            st.setString(3, newUser.getName());
            st.executeUpdate();

            //Insert into MVA UserGenres table
            for (int i = 0; i<newUser.getGenrePrefs().length; i++)
            {
                if (newUser.getGenrePrefs()[i] != null)
                {
                    st = c.prepareStatement("INSERT INTO UserGenres VALUES(?, ?)");
                    st.setString(1, newUser.getEmail());
                    st.setString(2, newUser.getGenrePrefs()[i]);
                    st.executeUpdate();
                }
            }
        }
        catch (final SQLException e)
        {
            throw new PersistenceException(e);
        }
        return newUser;
    }

    @Override
    public UserModel queryUser(String email)
    {
        UserModel user = null;
        String userQuery;
        PreparedStatement preparedSt;
        PreparedStatement genreSt;
        ResultSet results;
        ResultSet genreResults;

        try(final Connection c = connection())
        {
            userQuery = "SELECT * FROM Users WHERE email = ?";

            preparedSt = c.prepareStatement(userQuery);
            preparedSt.setString(1, email);
            results = preparedSt.executeQuery();

            if (results.next())
                user = parseResultSet(results, false);
            results.close();
            preparedSt.close();

            if (user != null) //The user does exist. Now check if they selected some preferred genres
            {
                userQuery = "SELECT * FROM Users u INNER JOIN UserGenres g on u.email = g.email WHERE email = ?";
                genreSt = c.prepareStatement(userQuery);
                genreSt.setString(1, email);
                genreResults = genreSt.executeQuery();

                ArrayList<String> allGenres = new ArrayList<>();

                UserModel currEntry;

                while (genreResults.next()) {
                    currEntry = parseResultSet(genreResults, true);
                    user = currEntry;
                    allGenres.add(currEntry.getGenrePrefs()[0]);
                }

                if (user != null)
                    user.setGenrePrefs(arrListToArr(allGenres));

                genreResults.close();
                genreSt.close();
            }

        }
        catch (SQLException e)
        {
            throw new PersistenceException(e);
        }
        return user;
    }
}
