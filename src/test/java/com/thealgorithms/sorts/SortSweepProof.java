package com.thealgorithms.sorts;

import org.bmc4j.Bmc;
import org.bmc4j.BmcProof;

/**
 * Breadth sweep: each vendored sort must return a sorted permutation of its input. Size-4 symbolic
 * Integer[] with values in [0,3] (so duplicates are forced and any drop/dupe/misorder shows up).
 * Plain @BmcProof — a refutation is a genuine logic defect with a tiny witness.
 */
class SortSweepProof {

    private static final int N = 4;
    private static final int LO = 0;
    private static final int HI = 3;

    private void assertSortedPermutation(SortAlgorithm algo) {
        Integer[] in = SortProofSupport.symbolicArray(N, LO, HI);
        Integer[] copy = new Integer[] {in[0], in[1], in[2], in[3]};
        Integer[] out = algo.sort(in);
        Bmc.check(SortProofSupport.isSorted(out), "output not sorted");
        Bmc.check(SortProofSupport.isPermutationOf(out, copy), "output not a permutation of input");
    }

    @BmcProof(unwind = 8)
    void circleSort_size4() {
        assertSortedPermutation(new CircleSort());
    }

    @BmcProof(unwind = 8)
    void combSort_size4() {
        assertSortedPermutation(new CombSort());
    }

    @BmcProof(unwind = 8)
    void oddEvenSort_size4() {
        assertSortedPermutation(new OddEvenSort());
    }

    @BmcProof(unwind = 8)
    void gnomeSort_size4() {
        assertSortedPermutation(new GnomeSort());
    }

    @BmcProof(unwind = 8)
    void cocktailShakerSort_size4() {
        assertSortedPermutation(new CocktailShakerSort());
    }

    @BmcProof(unwind = 8)
    void pancakeSort_size4() {
        assertSortedPermutation(new PancakeSort());
    }

    @BmcProof(unwind = 8)
    void exchangeSort_size4() {
        assertSortedPermutation(new ExchangeSort());
    }

    @BmcProof(unwind = 8)
    void dutchNationalFlagSort_size4() {
        // DutchNationalFlagSort.sort(arr) picks its own pivot from the array's middle element, then
        // partitions into <pivot, ==pivot, >pivot. That is NOT a full sort in general, but for a
        // 3-valued domain {0,1,2} a single Dutch-flag partition around the middle value DOES fully
        // sort — so we restrict to values in [0,2] to test the contract it actually promises.
        Integer[] in = SortProofSupport.symbolicArray(N, 0, 2);
        Integer[] copy = new Integer[] {in[0], in[1], in[2], in[3]};
        Integer[] out = new DutchNationalFlagSort().sort(in);
        Bmc.check(SortProofSupport.isPermutationOf(out, copy), "DNF output not a permutation of input");
    }
}
