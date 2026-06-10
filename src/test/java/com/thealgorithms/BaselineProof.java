package com.thealgorithms;

import org.bmc4j.Bmc;
import org.bmc4j.BmcProof;

/**
 * Baseline smoke proof kept on {@code main}.
 *
 * <p>The real findings live in the open pull requests (see README) — each PR adds its proof group and
 * the CI report comment shows that PR's verdicts. This single trivially-true proof keeps {@code main}'s
 * proof run non-empty and green, and demonstrates the {@code @BmcProof} pipeline end to end.
 *
 * <p>CONTRACT: for any symbolic int n, {@code n + 1 > n} UNLESS n is {@code Integer.MAX_VALUE} (where
 * +1 overflows). Excluding that one point makes this hold for every input in the bound, so it VERIFIES.
 */
class BaselineProof {

    @BmcProof
    void increment_grows_below_int_max() {
        int n = Bmc.anyInt(Integer.MIN_VALUE, Integer.MAX_VALUE - 1);
        Bmc.check(n + 1 > n);
    }
}
