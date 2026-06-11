package com.thealgorithms.sorts;

import org.bmc4j.Bmc;
import org.bmc4j.BmcProof;

/**
 * Differential proof over {@link CircleSort}. CONTRACT: a sort returns its input as a sorted
 * permutation. We feed a tiny symbolic Integer[] (size 3, values in a small range) and assert both
 * post-conditions. A plain @BmcProof (no expect=) so any logic defect surfaces as a refutation with
 * a small counterexample.
 */
class CircleSortProof {

    @BmcProof(unwind = 6)
    void circleSort_size3_returns_sorted_permutation() {
        Integer[] in = SortProofSupport.symbolicArray(3, 0, 3);
        Integer[] copy = new Integer[] {in[0], in[1], in[2]};
        Integer[] out = new CircleSort().sort(in);
        Bmc.check(SortProofSupport.isSorted(out), "output not sorted");
        Bmc.check(SortProofSupport.isPermutationOf(out, copy), "output not a permutation of input");
    }
}
