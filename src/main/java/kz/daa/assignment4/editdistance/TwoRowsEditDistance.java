
package kz.daa.assignment4.editdistance;

public class TwoRowsEditDistance implements EditDistanceAlgorithm {

    private long operations;

    @Override
    public int distance(String a, String b) {
        operations = 0;


        if (b.length() > a.length()) {
            String temp = a;
            a = b;
            b = temp;
        }

        int n = a.length();
        int m = b.length();



        int[] dp = new int[m + 1];
        int[] current = new int[m + 1];


        for (int j = 0; j <= m; j++) {
            dp[j] = j;
        }


        for (int i = 1; i <= n; i++) {
            current[0] = i;

            for (int j = 1; j <= m; j++) {
                operations++;

                int cost = (a.charAt(i - 1) == b.charAt(j - 1)) ? 0 : 1;

                int deletion = dp[j] + 1;
                int insertion = current[j - 1] + 1;
                int replacement = dp[j - 1] + cost;

                current[j] = Math.min(
                        Math.min(deletion, insertion),
                        replacement
                );
            }


            int[] temp = dp;
            dp = current;
            current = temp;
        }

        return dp[m];
    }

    @Override
    public long getOperations() {
        return operations;
    }
}
