package analyzer;

import kotlin.Function;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class Main {

    public static void main(String[] args) {
        final String selectedAlgorithm = "--KMP"; // Options: "--KMP", --"naive"

        // java Main test_files patterns.db
        final File directoryPath = new File(args[0]);
        final File database = new File(args[1]);

        // final ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        final ExecutorService executor = Executors.newFixedThreadPool(10); // need to pass test

        List<Callable<String>> matcherCallableList = new ArrayList<>();
        // Will be used to measure runtime of search
        long startTime = 0;
        long endTime = 0;

        startTime = System.nanoTime();
        for (final File file: directoryPath.listFiles()) {
            if (!file.isDirectory()) {
                matcherCallableList.add(new PatternMatcher(selectedAlgorithm, file, database));
            }
        }
        try {
            List<Future<String>> futures = executor.invokeAll(matcherCallableList);
            for (Future<String> future: futures) {
                System.out.println(future.get(100, TimeUnit.MILLISECONDS));
            }
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            e.printStackTrace();
        }

        endTime = System.nanoTime();


        executor.shutdown();
        double runtimeSeconds = (double) (endTime - startTime) / 1_000_000_000;
        // System.out.printf("Time elapsed %.5f seconds\n", runtimeSeconds);
    }

}
