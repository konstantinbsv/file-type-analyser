package analyzer;

import java.io.*;
import java.nio.file.Files;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Callable;

public class PatternMatcher implements Callable<String> {
    private File file;
    private PatternDatabase patternDatabase;
    private Finder finder;
    private String fileType;
    private byte[] patternByteArray;
    private boolean found = false;

    PatternMatcher(String selectedAlgorithm, File file, File database) {
        finder = new Finder(selectedAlgorithm);
        patternDatabase = new PatternDatabase(database);
        this.file = file;
    }

    @Override
    public String call() {
        try (InputStream inputStream = new FileInputStream(file)) {
            long fileSize = file.length(); // get size of file
            byte[] fileByteArray = new byte[(int) fileSize]; // initialize array with size of file
            inputStream.read(fileByteArray); // read file into array

            for (PatternDatabase.DatabaseLine line: patternDatabase) {
                patternByteArray = line.getPatternByteArray();

                if (finder.containsPattern(fileByteArray, patternByteArray)){
                    found = true;
                    fileType = line.getPatternFileType();
                    break;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        String output;
        if (found) {
            output = String.format("%s: %s", file.getName(), fileType);
        } else {
            output = String.format("%s: %s", file.getName(), "Unknown file type");
        }

        return output;
    }

    private class PatternDatabase implements Iterable<PatternDatabase.DatabaseLine>{
        private List<String> databaseLines;

        private PatternDatabase(File database) {
            try {
                databaseLines = Files.readAllLines(database.toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        private DatabaseLine getLine(int lineNumber) {
            if (lineNumber < 0 || lineNumber > databaseLines.size() - 1) {
                throw new IllegalArgumentException("Line index out of bounds");
            }

            return new DatabaseLine(databaseLines.get(lineNumber));
        }

         public Iterator<DatabaseLine> iterator() {
            return new DatabaseIterator();
        }

        private class DatabaseIterator implements Iterator<DatabaseLine> {
            private int currentLine;

            DatabaseIterator () {
                currentLine = databaseLines.size() - 1; // start from last line
            }

            @Override
            public boolean hasNext() {
                return currentLine >= 0;
            }

            @Override
            public DatabaseLine next() {
                return getLine(currentLine--);
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        }

        private class DatabaseLine {
            private int priority;
            private String pattern;
            private String fileType;

            private DatabaseLine(String line) {
                // e.g. of line:   4;"PK";"Zip archive"
                String[] parts = line.split(";");
                assert parts.length == 3;

                priority = Integer.parseInt(parts[0]);
                pattern = parts[1].replaceAll("\"", "");
                fileType = parts[2].replaceAll("\"", "");
            }

            private int getPatternPriority() {
                return priority;
            }

            private byte[] getPatternByteArray() {
                return pattern.getBytes();
            }

            private String getPatternFileType() {
                return fileType;
            }
        }

    }
}
