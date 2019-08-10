package analyzer;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class Main {

    public static void main(String[] args) {
        // e.g.:  java Main doc.pdf "%PDF-" "PDF document"
        String filepath = args[0];
        String searchPattern = args[1];
        String fileType = args[2];

        byte[] searchBytes = searchPattern.getBytes();

        boolean found = false;

        try (InputStream inputStream = new FileInputStream(filepath)) {
            // get file size
            long fileSize = new File(filepath).length();
            // create byte array to store file
            byte[] allBytes = new byte[(int) fileSize];
            // read full file into array
            inputStream.read(allBytes);
            // get length of search argument
            int searchLength = searchBytes.length;

            int i = 0;
            int j = 0;
            // search for for subarray searchBytes in allBytes array. O(n)
            while(i < fileSize && j < searchLength) {
                if (allBytes[i] == searchBytes[j]) {
                    i++;
                    j++;

                    // if search pattern array completely traversed
                    if (j == searchLength) {
                        found = true; // subarray exists in allBytes array
                        break;
                    }
                }
                // if bytes do not match, start search with one byte forward offset in file
                else {
                    i++;
                    j = 0;
                }
            }

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
