
package kz.daa.assignment4.experiment;

import java.util.Random;

public final class DataGenerator {

    private static final long SEED = 42L;

    private DataGenerator() {
    }


    public static String randomString(int length, String alphabet) {
        Random random = new Random(SEED + length + alphabet.hashCode());
        StringBuilder result = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            int index = random.nextInt(alphabet.length());
            result.append(alphabet.charAt(index));
        }

        return result.toString();
    }


    public static String[] differentStrings(int length) {
        String first = randomString(length, "ABC");
        String second = randomString(length, "XYZ");

        return new String[]{first, second};
    }


    public static String[] worstCaseSearch(int textLength) {
        String text = "A".repeat(textLength);

        int patternLength = Math.min(100, textLength);
        String pattern = "A".repeat(patternLength - 1) + "B";

        return new String[]{text, pattern};
    }
}
