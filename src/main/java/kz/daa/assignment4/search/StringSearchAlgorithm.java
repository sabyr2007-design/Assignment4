
package kz.daa.assignment4.search;

import java.util.List;

public interface StringSearchAlgorithm {

    List<Integer> search(String text, String pattern);

    long getOperations();
}

