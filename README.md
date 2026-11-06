  # Assignment 4 — Dynamic Programming and String Algorithms

## Project Overview

This project implements and evaluates dynamic programming and string-search algorithms for the Design and Analysis of Algorithms course.

The assignment focuses on Edit Distance and exact String Search, comparing algorithmic complexity, execution time, operation counts, and memory usage.

## Implemented Algorithms

### 1. Edit Distance
- **Recursive Edit Distance** — naive recursive implementation without memoization.
- **Table Edit Distance** — bottom-up dynamic programming using a full DP table.
- **Two Rows Edit Distance** — optimized dynamic programming using only two rows to reduce memory usage.

### 2. String Search
- **Naive String Search** — checks the pattern at every possible starting position.
- **Knuth-Morris-Pratt (KMP)** — uses the LPS (Longest Proper Prefix which is also a Suffix) array to avoid redundant comparisons.

## Project Structure

```text
Assignment4/
├── src/
│   ├── main/java/kz/daa/assignment4/
│   │   ├── editdistance/
│   │   ├── search/
│   │   └── experiment/
│   └── test/java/kz/daa/assignment4/
│       ├── editdistance/
│       └── search/
├── results/
│   ├── plots/
│   └── results.csv
├── pom.xml
├── README.md
├── REPORT.md
└── .gitignore
```

## Requirements

- Java 17 or newer
- Apache Maven
- IntelliJ IDEA (recommended)

## Build and Test

Run the following commands from the project root:

```bash
mvn clean compile
mvn test
```

The project uses JUnit 5 for automated testing.

The tests cover reference cases, empty strings, randomized Edit Distance comparisons, LPS arrays, overlapping matches, and comparisons between Naive and KMP search.

## Running Experiments

Run the `ExperimentRunner` class from IntelliJ IDEA:

```text
kz.daa.assignment4.experiment.ExperimentRunner
```

The experiment runner:
- Uses a fixed random seed for reproducibility.
- Measures execution time using multiple runs and median values.
- Counts algorithm-specific operations.
- Tests different input sizes and input types.
- Exports measurements to `results/results.csv`.

## Results and Plots

The `results` directory contains the experiment data and generated visualizations:

- `results.csv` — measured execution times and operation counts.
- `edit_distance_operations.png` — Edit Distance operation growth.
- `edit_distance_time.png` — Edit Distance runtime comparison.
- `string_search_operations.png` — String Search operation counts.
- `naive_kmp_ratio.png` — worst-case runtime ratio between Naive and KMP.

## AI Usage Acknowledgement

AI tools were used for assistance with understanding algorithmic concepts, debugging, reviewing explanations, and preparing documentation. The implementation, testing, and final verification were reviewed by the student, who is responsible for understanding and explaining the submitted work.

## Author

Design and Analysis of Algorithms — Assignment 4
