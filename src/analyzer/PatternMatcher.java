package analyzer;

import java.io.*;
import java.util.concurrent.Callable;

public class PatternMatcher implements Callable {
    private String filepath;
    private String fileType;
    private Finder finder;
    private byte[] patternByteArray;
    private boolean found = false;

    public PatternMatcher(String selectedAlgorithm, String filepath, String searchPattern, String fileType) {
        finder = new Finder(selectedAlgorithm);
        patternByteArray = searchPattern.getBytes();
        this.filepath = filepath;
        this.fileType = fileType;
    }

    @Override
    public Boolean call() {
        try (InputStream inputStream = new FileInputStream(filepath)) {
            long fileSize = new File(filepath).length(); // get size of file
            byte[] fileByteArray = new byte[(int) fileSize]; // initialize array with size of file
            inputStream.read(fileByteArray); // read file into array

            found = finder.containsPattern(fileByteArray, patternByteArray);

        } catch (IOException e) {
            e.printStackTrace();
        }

        if (found) {
            System.out.printf("%s: %s", filepath, fileType);
        } else {
            System.out.printf("%s: %s", filepath, "Unknown file type");
        }

        return found;
    }
}
