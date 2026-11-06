
package kz.daa.assignment4.editdistance;

public class RecursiveEditDistance implements EditDistanceAlgorithm {

    private long operations;

    @Override
    public int distance(String a, String b) {
        operations = 0;
        return calculate(a, b, a.length(), b.length());
    }

    private int calculate(String a, String b, int n, int m) {
        operations++;


        if (n == 0) {
            return m;
        }

        if (m == 0) {
            return n;
        }


        int cost = (a.charAt(n - 1) == b.charAt(m - 1)) ? 0 : 1;


        int deletion = calculate(a, b, n - 1, m);
        int insertion = calculate(a, b, n, m - 1);
        int replacement = calculate(a, b, n - 1, m - 1);

        return Math.min(
                Math.min(deletion + 1, insertion + 1),
                replacement + cost
        );
    }

    @Override
    public long getOperations() {
        return operations;
    }
}

