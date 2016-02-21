package util;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * @author peaseloxes
 */
public enum IOUtil {
    INSTANCE;

    /**
     * Retrieve the full file name from a resource on the class path.
     *
     * @param fileNameOnClassPath the file name on class path.
     * @return the full path.
     */
    public static String getFullPath(final String fileNameOnClassPath) {
        ClassLoader classLoader = IOUtil.class.getClassLoader();
        URL url = classLoader.getResource(fileNameOnClassPath);
        if (url != null) {
            return new File(url.getFile()).toPath().toString();
        } else {
            throw new IllegalArgumentException("Invalid filename, file could not be found.");
        }
    }

    /**
     * Read lines from a file and return them in a list.
     *
     * @param fileName the file name to read
     * @return a list of lines, can be empty if the file contains no lines.
     */
    public static List<String> readLines(final String fileName) {
        List<String> lines;
        try {
            lines = Files.readAllLines(Paths.get(getFullPath(fileName)));
        } catch (IOException e) {
            throw new IllegalArgumentException("Invalid filename, file could not be found.");
        }
        return lines;
    }

    /**
     * Write lines from a list to file.
     *
     * @param fileName the file's name.
     * @param lines    the lines to write.
     */
    public static void writeLines(final String fileName, final List<String> lines) {
        try {
            Files.write(Paths.get(getFullPath(fileName)), lines);
        } catch (IOException e) {
            throw new IllegalArgumentException("Error writing file: " + fileName);
        }
    }

}
