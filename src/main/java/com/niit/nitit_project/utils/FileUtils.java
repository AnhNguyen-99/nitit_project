package com.niit.nitit_project.utils;

import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;

public class FileUtils {
    public static String getResourceBasePath() {
        // Get the directory
        File path = null;
        try {
            path = new File(ResourceUtils.getURL("classpath:").getPath());
        } catch (FileNotFoundException e) {
            // nothing to do
        }
        if (path == null || !path.exists()) {
            path = new File("");
        }

        String pathStr = path.getAbsolutePath();
        // If it is running in eclipse, it will be in the same level as the target. If the jar is deployed to the server, the default is the same as the jar package.
        pathStr = pathStr.replace("\\target\\classes", "");

        return pathStr;
    }

    public static void deleteFile(String folder, String fileName) {
        Path path = FileSystems.getDefault().getPath(folder + fileName);
        try {
            Files.delete(path);
        } catch (NoSuchFileException x) {
            System.err.format("%s: no such" + " file or directory%n", path);
        } catch (IOException x) {
            System.err.println(x);
        }
    }
}
