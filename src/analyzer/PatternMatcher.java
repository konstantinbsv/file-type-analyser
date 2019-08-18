package analyzer;

import java.io.*;
import java.util.concurrent.Callable;

public class PatternMatcher implements Callable {
    private File file;
    private String fileType;
    private Finder finder;
    private byte[] patternByteArray;
    private boolean found = false;

    PatternMatcher(String selectedAlgorithm, File file, String searchPattern, String fileType) {
        finder = new Finder(selectedAlgorithm);
        patternByteArray = searchPattern.getBytes();
        this.file = file;
        this.fileType = fileType;
    }

    @Override
    public Boolean call() {
        try (InputStream inputStream = new FileInputStream(file)) {
            long fileSize = file.length(); // get size of file
            byte[] fileByteArray = new byte[(int) fileSize]; // initialize array with size of file
            inputStream.read(fileByteArray); // read file into array

            found = finder.containsPattern(fileByteArray, patternByteArray);

        } catch (IOException e) {
            e.printStackTrace();
        }

        if (found) {
            System.out.printf("%s: %s\n", file.getName(), fileType);
        } else {
            System.out.printf("%s: %s\n", file.getName(), "Unknown file type");
        }

        return found;
    }
}
