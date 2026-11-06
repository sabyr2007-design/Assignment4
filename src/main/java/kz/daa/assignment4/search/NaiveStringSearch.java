
package kz.daa.assignment4.search;

import java.util.ArrayList;
import java.util.List;

public class NaiveStringSearch implements StringSearchAlgorithm {

    private long operations;

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


        for (int i = 0; i <= n - m; i++) {

            int j = 0;


            while (j < m) {
                operations++;

                if (text.charAt(i + j) != pattern.charAt(j)) {
                    break;
                }

                j++;
            }


            if (j == m) {
                matches.add(i);
            }
        }

        return matches;
    }

    @Override
    public long getOperations() {
        return operations;
    }
}
