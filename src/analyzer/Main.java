package analyzer;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Main {

    public static void main(String[] args) {
        final String selectedAlgorithm = "--KMP"; // Options: "--KMP", --"naive"

        // java Main "%PDF-" "PDF document" test_files
        final File directoryPath = new File(args[0]);
        final String searchPattern = args[1];
        final String fileType = args[2];

        // final ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        final ExecutorService executor = Executors.newCachedThreadPool();

        // Will be used to measure runtime of search
        long startTime = 0;
        long endTime = 0;

        startTime = System.nanoTime();
        for (final File file: directoryPath.listFiles()) {
            if (!file.isDirectory()) {
                Future future = executor.submit(new PatternMatcher(selectedAlgorithm, file, searchPattern, fileType));
                while (!future.isDone());
            }
        }
        endTime = System.nanoTime();


        executor.shutdown();
        double runtimeSeconds = (double) (endTime - startTime) / 1_000_000_000;
        // System.out.printf("Time elapsed %.5f seconds\n", runtimeSeconds);
    }

}
