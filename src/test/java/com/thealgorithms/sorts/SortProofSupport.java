package com.thealgorithms.sorts;

import org.bmc4j.Bmc;

/**
 * Shared helpers for sort proofs. A correct comparison sort must satisfy TWO independent
 * post-conditions that together do NOT re-encode any particular sort's logic:
 *   1. the output is non-decreasing (sorted), and
 *   2. the output is a permutation (multiset) of the input — nothing dropped, duplicated or invented.
 * Either one alone is insufficient (a sort that returns a constant array is "sorted"; a sort that
 * shuffles is a "permutation"); both together are the real contract. We build small Integer[] inputs
 * from symbolic ints with a tight value range so any violation surfaces as a tiny counterexample.
 */
final class SortProofSupport {
    private SortProofSupport() {
    }

    static boolean isSorted(Integer[] a) {
        for (int i = 1; i < a.length; i++) {
            if (a[i - 1] > a[i]) {
                return false;
            }
        }
        return true;
    }

    /**
     * True iff {@code out} is a permutation of {@code in}. Multiset equality via counting: for each
     * distinct value present, the number of occurrences must match in both arrays. Implemented with
     * an O(n^2) count so there is no dependence on hashing/sorting (which the sort-under-test might
     * itself be buggy at).
     */
    static boolean isPermutationOf(Integer[] out, Integer[] in) {
        if (out.length != in.length) {
            return false;
        }
        for (int i = 0; i < in.length; i++) {
            int cIn = 0;
            int cOut = 0;
            for (int k = 0; k < in.length; k++) {
                if (in[k].intValue() == in[i].intValue()) {
                    cIn++;
                }
                if (out[k].intValue() == in[i].intValue()) {
                    cOut++;
                }
            }
            if (cIn != cOut) {
                return false;
            }
        }
        return true;
    }

    /** Builds a fresh Integer[] of the given size from symbolic ints in [lo, hi]. */
    static Integer[] symbolicArray(int size, int lo, int hi) {
        Integer[] a = new Integer[size];
        for (int i = 0; i < size; i++) {
            a[i] = Integer.valueOf(Bmc.anyInt(lo, hi));
        }
        return a;
    }
}
