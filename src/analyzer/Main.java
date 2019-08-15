package analyzer;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class Main {

    public static void main(String[] args) {
        // java Main --KMP huge_doc.pdf "%PDF-" "PDF document"
        String selectedAlgorithm = args[0];
        String filepath = args[1];
        String searchPattern = args[2];
        String fileType = args[3];

        Finder finder;
        byte[] patternByteArray = searchPattern.getBytes();
        boolean found = false;

        switch (selectedAlgorithm) {
            case "--naive":
                finder = new Finder(new NaiveStrategy());
                break;
            case "--KMP":
                finder = new Finder(new KnuthMorrisPrattStrategy());
                break;
            default:
                System.out.println("Invalid algorithm selected.");
                return;
        }

        try (InputStream inputStream = new FileInputStream(filepath)) {
            long fileSize = new File(filepath).length(); // get size of file
            byte[] fileByteArray = new byte[(int) fileSize]; // initialize array with size of file
            inputStream.read(fileByteArray); // read file into array

            found = finder.containsPattern(fileByteArray, patternByteArray);

        } catch (IOException e) {
            e.printStackTrace();
        }

        if (found) {
            System.out.println(fileType);
        } else {
            System.out.println("Unknown file type");
        }
    }
}
