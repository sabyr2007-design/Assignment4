
package kz.daa.assignment4.search;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class StringSearchTest {

    @Test
    void testReferenceSearchValues() {
        StringSearchAlgorithm naive = new NaiveStringSearch();
        StringSearchAlgorithm kmp = new KMPStringSearch();

        assertEquals(
                List.of(10),
                naive.search("ABABDABACDABABCABAB", "ABABCABAB")
        );

        assertEquals(
                List.of(10),
                kmp.search("ABABDABACDABABCABAB", "ABABCABAB")
        );

        assertEquals(
                List.of(0, 1, 2, 3),
                naive.search("AAAAA", "AA")
        );

        assertEquals(
                List.of(0, 1, 2, 3),
                kmp.search("AAAAA", "AA")
        );

        assertEquals(
                List.of(0, 2, 4),
                naive.search("ABABABA", "ABA")
        );

        assertEquals(
                List.of(0, 2, 4),
                kmp.search("ABABABA", "ABA")
        );
    }

    @Test
    void testLPSReferenceValues() {
        KMPStringSearch kmp = new KMPStringSearch();

        assertArrayEquals(
                new int[]{0, 1, 2, 0, 1, 2, 3, 3},
                kmp.lps("AAACAAAA")
        );

        assertArrayEquals(
                new int[]{0, 0, 1, 2, 0, 1, 2, 3, 4},
                kmp.lps("ABABCABAB")
        );
    }

    @Test
    void testEmptyPatternThrowsException() {
        StringSearchAlgorithm naive = new NaiveStringSearch();
        StringSearchAlgorithm kmp = new KMPStringSearch();

        assertThrows(
                IllegalArgumentException.class,
                () -> naive.search("ABCDE", "")
        );

        assertThrows(
                IllegalArgumentException.class,
                () -> kmp.search("ABCDE", "")
        );

        assertThrows(
                IllegalArgumentException.class,
                () -> ((KMPStringSearch) kmp).lps("")
        );
    }

    @Test
    void testPatternLongerThanText() {
        StringSearchAlgorithm naive = new NaiveStringSearch();
        StringSearchAlgorithm kmp = new KMPStringSearch();

        assertEquals(
                List.of(),
                naive.search("ABC", "ABCDEFG")
        );

        assertEquals(
                List.of(),
                kmp.search("ABC", "ABCDEFG")
        );
    }

    @Test
    void testRandomStrings() {
        StringSearchAlgorithm naive = new NaiveStringSearch();
        StringSearchAlgorithm kmp = new KMPStringSearch();

        Random random = new Random(42);

        String alphabet = "ABC";

        for (int test = 0; test < 100; test++) {

            String text = generateRandomString(
                    random, alphabet, 30
            );

            String pattern = generateRandomString(
                    random, alphabet, 8
            );

            // Pattern must not be empty.
            if (pattern.isEmpty()) {
                pattern = "A";
            }

            List<Integer> naiveResult =
                    naive.search(text, pattern);

            List<Integer> kmpResult =
                    kmp.search(text, pattern);

            assertEquals(
                    naiveResult,
                    kmpResult,
                    "Different results for text: "
                            + text + " and pattern: " + pattern
            );
        }
    }

    @Test
    void testOperationCounters() {
        StringSearchAlgorithm naive = new NaiveStringSearch();
        StringSearchAlgorithm kmp = new KMPStringSearch();

        naive.search("ABABABABA", "ABA");
        kmp.search("ABABABABA", "ABA");

        assertTrue(naive.getOperations() > 0);
        assertTrue(kmp.getOperations() > 0);
    }

    private String generateRandomString(
            Random random,
            String alphabet,
            int maxLength
    ) {
        int length = random.nextInt(maxLength + 1);

        StringBuilder result = new StringBuilder();

        for (int i = 0; i < length; i++) {
            result.append(
                    alphabet.charAt(
                            random.nextInt(alphabet.length())
                    )
            );
        }

        return result.toString();
    }
}
