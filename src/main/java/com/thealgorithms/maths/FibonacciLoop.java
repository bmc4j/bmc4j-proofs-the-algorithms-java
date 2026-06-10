// Vendored from TheAlgorithms/Java (MIT). See repo-root LICENSE.
//   source: src/main/java/com/thealgorithms/maths/FibonacciLoop.java
//   upstream commit: 34079f0aa02d8f4564a21fabd4326f3fd3eb8a8e
// Verbatim copy. Analyzed by bmc4j as javac compiles it.
package com.thealgorithms.maths;

import java.math.BigInteger;

/**
 * This class provides methods for calculating Fibonacci numbers using BigInteger for large values of 'n'.
 */
public final class FibonacciLoop {

    private FibonacciLoop() {
        // Private constructor to prevent instantiation of this utility class.
    }

    /**
     * Calculates the nth Fibonacci number.
     *
     * @param n The index of the Fibonacci number to calculate.
     * @return The nth Fibonacci number as a BigInteger.
     * @throws IllegalArgumentException if the input 'n' is a negative integer.
     */
    public static BigInteger compute(final int n) {
        if (n < 0) {
            throw new IllegalArgumentException("Input 'n' must be a non-negative integer.");
        }

        if (n <= 1) {
            return BigInteger.valueOf(n);
        }

        BigInteger prev = BigInteger.ZERO;
        BigInteger current = BigInteger.ONE;

        for (int i = 2; i <= n; i++) {
            BigInteger next = prev.add(current);
            prev = current;
            current = next;
        }

        return current;
    }
}
