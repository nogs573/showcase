package com.group3.movieguide.Application;

import com.group3.movieguide.Object.*;

public class Main {

    private static String dbName="movieGuide";
    private static String dbPath;
    private static UserModel activeUser = null;

    public static void main(String[] args)
    {
        //if we wanted output to be sent to terminal call something like Output.run()...
        System.out.println("All done");
    }

    public static UserModel getActiveUser() { return activeUser; }
    public static void setActiveUser(UserModel user) { activeUser = user; }

    public static void setDBPath(final String name) {
        try {
            Class.forName("org.hsqldb.jdbcDriver").newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        dbPath = name;
    }

    public static String getDBPathName() {
        return dbName;
    }

    public static String getDBPath() { return dbPath; }


}
