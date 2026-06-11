package com.thealgorithms.searches;

import org.bmc4j.Bmc;
import org.bmc4j.BmcProof;

class InterpolationSearchProof {

    private static boolean contains(int[] a, int key) {
        for (int v : a) {
            if (v == key) {
                return true;
            }
        }
        return false;
    }

    @BmcProof(unwind = 6)
    void interpolation_findsPresentKey_size4() {
        // InterpolationSearch divides by (a[end] - a[start]), which is 0 whenever the search window
        // collapses to a single element (start == end, so the two corners are the same value). That
        // happens for ordinary sorted inputs — e.g. searching [0, 0, 0, 2] (or even the distinct
        // [0, 1, 2, 4]) for its largest element, which the probe never lands on directly, so the
        // window narrows down to it and divides by zero. So this REFUTES with a tiny witness, not
        // just on pathological all-equal arrays. assumeSorted is the documented precondition; the
        // defect is reachable with or without duplicates.
        int[] a = Bmc.anyArrayOfInts(4, 0, 4);
        Bmc.assumeSorted(a);
        int key = Bmc.anyInt(0, 4);
        Bmc.assume(contains(a, key));
        int i = new InterpolationSearch().find(a, key);
        Bmc.check(i >= 0 && a[i] == key, "present key not found or wrong index");
    }
}
