
package kz.daa.assignment4.editdistance;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class EditDistanceTest {

    @Test
    void testReferenceValues() {
        EditDistanceAlgorithm recursive = new RecursiveEditDistance();
        EditDistanceAlgorithm table = new TableEditDistance();
        EditDistanceAlgorithm twoRows = new TwoRowsEditDistance();

        String[][] inputs = {
                {"kitten", "sitting"},
                {"flaw", "lawn"},
                {"intention", "execution"},
                {"", "abc"},
                {"abc", "abc"}
        };

        int[] expected = {3, 2, 5, 3, 0};

        for (int i = 0; i < inputs.length; i++) {
            String a = inputs[i][0];
            String b = inputs[i][1];

            assertEquals(expected[i], recursive.distance(a, b));
            assertEquals(expected[i], table.distance(a, b));
            assertEquals(expected[i], twoRows.distance(a, b));
        }
    }

    @Test
    void testEmptyStrings() {
        EditDistanceAlgorithm recursive = new RecursiveEditDistance();
        EditDistanceAlgorithm table = new TableEditDistance();
        EditDistanceAlgorithm twoRows = new TwoRowsEditDistance();

        assertEquals(0, recursive.distance("", ""));
        assertEquals(0, table.distance("", ""));
        assertEquals(0, twoRows.distance("", ""));

        assertEquals(4, recursive.distance("test", ""));
        assertEquals(4, table.distance("test", ""));
        assertEquals(4, twoRows.distance("test", ""));
    }

    @Test
    void testRandomShortStrings() {
        EditDistanceAlgorithm recursive = new RecursiveEditDistance();
        EditDistanceAlgorithm table = new TableEditDistance();
        EditDistanceAlgorithm twoRows = new TwoRowsEditDistance();

        Random random = new Random(42);
        String alphabet = "abc";

        for (int test = 0; test < 100; test++) {
            String a = generateRandomString(random, alphabet, 7);
            String b = generateRandomString(random, alphabet, 7);

            int recursiveResult = recursive.distance(a, b);
            int tableResult = table.distance(a, b);
            int twoRowsResult = twoRows.distance(a, b);

            assertEquals(tableResult, recursiveResult,
                    "Recursive differs for: " + a + " and " + b);

            assertEquals(tableResult, twoRowsResult,
                    "TwoRows differs for: " + a + " and " + b);
        }
    }

    private String generateRandomString(
            Random random,
            String alphabet,
            int maxLength
    ) {
        int length = random.nextInt(maxLength + 1);

        StringBuilder result = new StringBuilder();

        for (int i = 0; i < length; i++) {
            result.append(alphabet.charAt(
                    random.nextInt(alphabet.length())
            ));
        }

        return result.toString();
    }

    @Test
    void testOperationsCounters() {
        EditDistanceAlgorithm recursive = new RecursiveEditDistance();
        EditDistanceAlgorithm table = new TableEditDistance();
        EditDistanceAlgorithm twoRows = new TwoRowsEditDistance();

        recursive.distance("abc", "abd");
        table.distance("abc", "abd");
        twoRows.distance("abc", "abd");

        assertTrue(recursive.getOperations() > 0);
        assertEquals(9, table.getOperations());
        assertEquals(9, twoRows.getOperations());
    }
}
