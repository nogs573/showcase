package com.group3.movieguide.utils;

import com.google.common.io.Files;
import java.io.File;
import java.io.IOException;

import com.group3.movieguide.Application.Main;

public class TestUtils {
    private static final File DB_SRC = new File("src/main/assets/db/movieGuide.script");

    public static File copyDB() throws IOException {
        final File target = File.createTempFile("temp-db", ".script");
        Files.copy(DB_SRC, target);
        Main.setDBPath(target.getAbsolutePath().replace(".script", ""));
        return target;
    }
}

