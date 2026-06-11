package com.thealgorithms.searches;

import org.bmc4j.Bmc;
import org.bmc4j.BmcProof;

/**
 * Differential proofs over search algorithms. CONTRACT of a correct search over a sorted array:
 *   - if it returns an index i in [0,n), then a[i] == key (no wrong-index hit);
 *   - if it returns -1, then key is genuinely absent from a.
 * Both directions are independent of how the search navigates. We use a tiny SORTED symbolic array
 * (built with assume) and a key drawn from the same small range so "present" is reachable.
 * Plain @BmcProof — a refutation is a real logic defect with a tiny witness.
 */
class SearchProof {

    private static Integer[] sortedArray(int n, int lo, int hi) {
        Integer[] a = new Integer[n];
        for (int i = 0; i < n; i++) {
            a[i] = Integer.valueOf(Bmc.anyInt(lo, hi));
        }
        for (int i = 1; i < n; i++) {
            Bmc.assume(a[i - 1] <= a[i]);
        }
        return a;
    }

    private static boolean contains(Integer[] a, int key) {
        for (Integer x : a) {
            if (x.intValue() == key) {
                return true;
            }
        }
        return false;
    }

    // ---- IterativeTernarySearch ----

    @BmcProof(unwind = 6)
    void iterativeTernary_returnedIndexHoldsKey_size4() {
        Integer[] a = sortedArray(4, 0, 4);
        Integer key = Integer.valueOf(Bmc.anyInt(0, 4));
        int i = new IterativeTernarySearch().find(a, key);
        if (i >= 0) {
            Bmc.check(a[i].intValue() == key.intValue(), "returned index does not hold key");
        }
    }

    @BmcProof(unwind = 6)
    void iterativeTernary_findsPresentKey_size4() {
        Integer[] a = sortedArray(4, 0, 4);
        Integer key = Integer.valueOf(Bmc.anyInt(0, 4));
        Bmc.assume(contains(a, key.intValue()));
        int i = new IterativeTernarySearch().find(a, key);
        Bmc.check(i >= 0, "present key reported absent");
    }

    // ---- InterpolationSearch (int[] based) ----

    @BmcProof(unwind = 6)
    void interpolation_findsPresentKey_size4() {
        // assumeSorted is NON-STRICT (allows duplicates): the all-equal [1,1,1,1] corner is exactly
        // what trips InterpolationSearch's integer-division probe into a divide-by-zero, so it must
        // stay reachable for this to REFUTE.
        int[] a = Bmc.anyArrayOfInts(4, 0, 4);
        Bmc.assumeSorted(a);
        int key = Bmc.anyInt(0, 4);
        boolean present = false;
        for (int v : a) {
            if (v == key) {
                present = true;
            }
        }
        Bmc.assume(present);
        int i = new InterpolationSearch().find(a, key);
        Bmc.check(i >= 0 && a[i] == key, "present key not found or wrong index");
    }
}
