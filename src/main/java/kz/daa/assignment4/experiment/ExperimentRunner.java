
package kz.daa.assignment4.experiment;

import kz.daa.assignment4.editdistance.EditDistanceAlgorithm;
import kz.daa.assignment4.editdistance.RecursiveEditDistance;
import kz.daa.assignment4.editdistance.TableEditDistance;
import kz.daa.assignment4.editdistance.TwoRowsEditDistance;
import kz.daa.assignment4.search.KMPStringSearch;
import kz.daa.assignment4.search.NaiveStringSearch;
import kz.daa.assignment4.search.StringSearchAlgorithm;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public final class ExperimentRunner {

    private static final int RUNS = 3;

    private static final StringBuilder CSV = new StringBuilder(
            "algorithm,input,n,m,time_ms,operations\n"
    );

    private ExperimentRunner() {
    }

    @FunctionalInterface
    private interface Experiment {
        void run();
    }

    private interface Counter {
        long get();
    }

    private static void measure(
            String algorithm,
            String inputType,
            int n,
            int m,
            Experiment experiment,
            Counter counter
    ) {
        List<Double> times = new ArrayList<>();
        long operations = 0;

        for (int run = 0; run < RUNS; run++) {
            long start = System.nanoTime();

            experiment.run();

            long end = System.nanoTime();

            times.add((end - start) / 1_000_000.0);
            operations = counter.get();
        }

        Collections.sort(times);
        double median = times.get(times.size() / 2);

        CSV.append(String.format(
                Locale.US,
                "%s,%s,%d,%d,%.6f,%d%n",
                algorithm,
                inputType,
                n,
                m,
                median,
                operations
        ));

        System.out.printf(
                "%-20s %-12s n=%-6d m=%-6d time=%10.6f ms operations=%d%n",
                algorithm, inputType, n, m, median, operations
        );
    }

    private static void runEditDistance() {

        int[] recursiveSizes = {3, 6, 9, 12};
        int[] dpSizes = {100, 250, 500, 1000};

        System.out.println("\n=== EDIT DISTANCE ===");

        for (int n : recursiveSizes) {
            String a = DataGenerator.randomString(n, "ABCD");
            String b = DataGenerator.randomString(n, "ABCE");

            EditDistanceAlgorithm algorithm = new RecursiveEditDistance();

            measure(
                    "Recursive",
                    "Random",
                    n,
                    n,
                    () -> algorithm.distance(a, b),
                    algorithm::getOperations
            );
        }

        for (int n : dpSizes) {
            String a = DataGenerator.randomString(n, "ABCD");
            String b = DataGenerator.randomString(n, "ABCE");

            EditDistanceAlgorithm table = new TableEditDistance();

            measure(
                    "Table",
                    "Random",
                    n,
                    n,
                    () -> table.distance(a, b),
                    table::getOperations
            );

            EditDistanceAlgorithm twoRows = new TwoRowsEditDistance();

            measure(
                    "TwoRows",
                    "Random",
                    n,
                    n,
                    () -> twoRows.distance(a, b),
                    twoRows::getOperations
            );
        }

        for (int n : dpSizes) {
            String[] strings = DataGenerator.differentStrings(n);

            EditDistanceAlgorithm table = new TableEditDistance();

            measure(
                    "Table",
                    "Different",
                    n,
                    n,
                    () -> table.distance(strings[0], strings[1]),
                    table::getOperations
            );

            EditDistanceAlgorithm twoRows = new TwoRowsEditDistance();

            measure(
                    "TwoRows",
                    "Different",
                    n,
                    n,
                    () -> twoRows.distance(strings[0], strings[1]),
                    twoRows::getOperations
            );
        }
    }

    private static void runStringSearch() {

        int[] sizes = {1000, 5000, 10000, 20000};

        System.out.println("\n=== STRING SEARCH ===");

        for (int n : sizes) {


            String text = DataGenerator.randomString(n, "ABCD");
            String pattern = DataGenerator.randomString(20, "XYZ");

            StringSearchAlgorithm naive = new NaiveStringSearch();

            measure(
                    "Naive",
                    "Random",
                    n,
                    pattern.length(),
                    () -> naive.search(text, pattern),
                    naive::getOperations
            );

            StringSearchAlgorithm kmp = new KMPStringSearch();

            measure(
                    "KMP",
                    "Random",
                    n,
                    pattern.length(),
                    () -> kmp.search(text, pattern),
                    kmp::getOperations
            );

            String[] worstCase = DataGenerator.worstCaseSearch(n);

            String worstText = worstCase[0];
            String worstPattern = worstCase[1];

            StringSearchAlgorithm naiveWorst = new NaiveStringSearch();

            measure(
                    "Naive",
                    "WorstCase",
                    n,
                    worstPattern.length(),
                    () -> naiveWorst.search(worstText, worstPattern),
                    naiveWorst::getOperations
            );

            StringSearchAlgorithm kmpWorst = new KMPStringSearch();

            measure(
                    "KMP",
                    "WorstCase",
                    n,
                    worstPattern.length(),
                    () -> kmpWorst.search(worstText, worstPattern),
                    kmpWorst::getOperations
            );
        }
    }

    public static void main(String[] args) throws IOException {

        System.out.println("Starting experiments...");

        CSV.setLength(0);
        CSV.append("algorithm,input,n,m,time_ms,operations\n");

        runEditDistance();
        runStringSearch();

        Path output = Path.of("results", "results.csv");

        Files.createDirectories(output.getParent());
        Files.writeString(output, CSV.toString());

        System.out.println("\nExperiments completed!");
        System.out.println("Results saved to: " + output.toAbsolutePath());
    }
}

