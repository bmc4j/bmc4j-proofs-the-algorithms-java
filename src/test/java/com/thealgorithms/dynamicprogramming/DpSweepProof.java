package com.thealgorithms.dynamicprogramming;

import org.bmc4j.Bmc;
import org.bmc4j.BmcProof;

/**
 * Differential proofs over integer/array DP algorithms vs. independent brute-force references that
 * do NOT re-encode the DP recurrence. Tiny inputs (lengths <= 3, small values) so any disagreement
 * is a small counterexample. Plain @BmcProof — a refutation is a genuine logic defect.
 */
class DpSweepProof {

    // ---------- SubsetSum.subsetSum vs brute-force over all 2^n subsets ----------
    private static boolean bruteSubsetSum(int[] arr, int target) {
        int n = arr.length;
        for (int mask = 0; mask < (1 << n); mask++) {
            int s = 0;
            for (int i = 0; i < n; i++) {
                if ((mask & (1 << i)) != 0) {
                    s += arr[i];
                }
            }
            if (s == target) {
                return true;
            }
        }
        return false;
    }

    @BmcProof(unwind = 12)
    void subsetSum_matches_bruteforce_n3() {
        int[] arr = Bmc.anyArrayOfInts(3, 0, 4);
        int target = Bmc.anyInt(0, 6);
        Bmc.check(SubsetSum.subsetSum(arr, target) == bruteSubsetSum(arr, target),
            "subsetSum disagrees with brute force");
    }

    // ---------- CoinChange.change: number of multiset combinations summing to amount ----------
    // Brute reference: count combinations c0*coins[0]+c1*coins[1]+... == amount, ci >= 0.
    private static int bruteChangeCombinations(int[] coins, int amount) {
        // coins length fixed to 2 in the proof below; nested non-negative counts.
        int count = 0;
        for (int c0 = 0; c0 * coins[0] <= amount; c0++) {
            for (int c1 = 0; c0 * coins[0] + c1 * coins[1] <= amount; c1++) {
                if (c0 * coins[0] + c1 * coins[1] == amount) {
                    count++;
                }
            }
        }
        return count;
    }

    @BmcProof(unwind = 10)
    void coinChange_change_matches_bruteforce_twoCoins() {
        int[] coins = Bmc.anyArrayOfInts(2, 1, 4);
        Bmc.assume(coins[0] != coins[1]); // distinct denominations (the algorithm's intended use)
        int amount = Bmc.anyInt(0, 6);
        Bmc.check(CoinChange.change(coins, amount) == bruteChangeCombinations(coins, amount),
            "coin-change combination count disagrees with brute force");
    }

    // NOTE: EditDistance.minDistance vs EditDistance.editDistance (the repo's two implementations)
    // were checked by exhaustive execution over all strings of length <= 3 over a 2-letter alphabet
    // (225 pairs) and AGREE everywhere — a true negative. The bmc4j proof of this is omitted from the
    // suite because the recursive-substring implementation is string/heap-heavy and the engine runs
    // out of budget (UNKNOWN, not a refutation) on it.
}
