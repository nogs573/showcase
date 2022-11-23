package com.group3.movieguide.Object;

import com.google.common.base.Objects;

import java.io.Serializable;//for intents. passing object between activities
import java.util.Arrays;

public class UserModel implements Serializable{
    private String email;
    private String password;
    private String name;
    private String[] genrePrefs; //null if user didn't select genre preferences at account creation

    public UserModel(String email, String password, String name, String[] genrePrefs) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.genrePrefs = genrePrefs;
    }

    public void setGenrePrefs(String[] genrePrefs) {
        this.genrePrefs = genrePrefs;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        String result = name;
        result.trim();
        if (result.equals(""))
            result = email;
        return result;
    }

    public String[] getGenrePrefs() {
        return genrePrefs;
    }

    @Override
    public String toString() {
        return "UserModel{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", genrePrefs=" + Arrays.toString(genrePrefs) +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserModel userModel = (UserModel) o;
        return Objects.equal(getEmail(), userModel.getEmail()) && Objects.equal(getPassword(), userModel.getPassword()) && Objects.equal(getName(), userModel.getName()) && Objects.equal(getGenrePrefs(), userModel.getGenrePrefs());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getEmail(), getPassword(), getName(), getGenrePrefs());
    }
}