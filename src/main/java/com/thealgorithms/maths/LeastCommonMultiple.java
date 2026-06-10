// Vendored from TheAlgorithms/Java (MIT). See repo-root LICENSE.
//   source: src/main/java/com/thealgorithms/maths/LeastCommonMultiple.java
//   upstream commit: 34079f0aa02d8f4564a21fabd4326f3fd3eb8a8e
// Verbatim copy. Analyzed by bmc4j as javac compiles it.
package com.thealgorithms.maths;

/**
 * Is a common mathematics concept to find the smallest value number
 * that can be divide using either number without having the remainder.
 * https://maticschool.blogspot.com/2013/11/find-least-common-multiple-lcm.html
 * @author LauKinHoong
 */
public final class LeastCommonMultiple {
    private LeastCommonMultiple() {
    }

    /**
     * Finds the least common multiple of two numbers.
     *
     * @param num1 The first number.
     * @param num2 The second number.
     * @return The least common multiple of num1 and num2.
     */
    public static int lcm(int num1, int num2) {
        int high;
        int num3;
        int cmv = 0;

        if (num1 > num2) {
            high = num1;
            num3 = num1;
        } else {
            high = num2;
            num3 = num2;
        }

        while (num1 != 0) {
            if (high % num1 == 0 && high % num2 == 0) {
                cmv = high;
                break;
            }
            high += num3;
        }
        return cmv;
    }
}
