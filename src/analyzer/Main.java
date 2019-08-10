package analyzer;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    public static void main(String[] args) {
        // java Main doc.pdf "%PDF-" "PDF document"
        String filepath = args[0];
        String searchPattern = args[1];
        String fileType = args[2];

        try (InputStream inputStream = new FileInputStream(filepath)) {
            Pattern p = Pattern.compile(searchPattern);


            Matcher m = p.matcher();

        } catch (IOException e) {
            e.printStackTrace();
        }


        System.out.println("Hello World!");
    }
}
