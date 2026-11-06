
package kz.daa.assignment4.search;

import java.util.ArrayList;
import java.util.List;

public class KMPStringSearch implements StringSearchAlgorithm {

    private long operations;


    public int[] lps(String pattern) {

        if (pattern.isEmpty()) {
            throw new IllegalArgumentException(
                    "Pattern must not be empty"
            );
        }

        int m = pattern.length();
        int[] lps = new int[m];

        int length = 0;
        int i = 1;

        while (i < m) {
            operations++;

            if (pattern.charAt(i) == pattern.charAt(length)) {
                length++;
                lps[i] = length;
                i++;
            } else {
                if (length != 0) {
                    length = lps[length - 1];
                } else {
                    lps[i] = 0;
                    i++;
                }
            }
        }

        return lps;
    }

    @Override
    public List<Integer> search(String text, String pattern) {
        operations = 0;

        if (pattern.isEmpty()) {
            throw new IllegalArgumentException(
                    "Pattern must not be empty"
            );
        }

        List<Integer> matches = new ArrayList<>();

        int n = text.length();
        int m = pattern.length();

        if (m > n) {
            return matches;
        }

        int[] prefix = lps(pattern);

        int i = 0;
        int j = 0;

        while (i < n) {
            operations++;

            if (text.charAt(i) == pattern.charAt(j)) {
                i++;
                j++;

                if (j == m) {
                    matches.add(i - j);


                    j = prefix[j - 1];
                }

            } else {
                if (j != 0) {
                    j = prefix[j - 1];
                } else {
                    i++;
                }
            }
        }

        return matches;
    }

    @Override
    public long getOperations() {
        return operations;
    }
}
