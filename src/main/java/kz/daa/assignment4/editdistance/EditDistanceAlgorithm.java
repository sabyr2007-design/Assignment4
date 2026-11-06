
package kz.daa.assignment4.editdistance;

public interface EditDistanceAlgorithm {

    int distance(String a, String b);

    long getOperations();
}
