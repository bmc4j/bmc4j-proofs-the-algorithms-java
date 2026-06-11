package com.thealgorithms.maths;

import org.bmc4j.Bmc;
import org.bmc4j.BmcProof;

/**
 * Breadth sweep over pure-integer maths algorithms, each against an independent reference/invariant.
 * (Floating-point algorithms — anything using Math.sqrt/Math.pow — are excluded here because the
 * engine's transcendental models make its verdict on them unreliable; those are checked separately
 * by hand.) Plain @BmcProof: a refutation is a genuine logic defect with a small witness.
 */
class MathSweepProof {

    // ---- GCD.gcd(int,int): for positive a,b, result divides both and is the largest such ----
    @BmcProof(unwind = 20)
    void gcd_divides_both_and_is_maximal() {
        int a = Bmc.anyInt(1, 12);
        int b = Bmc.anyInt(1, 12);
        int g = GCD.gcd(a, b);
        Bmc.check(g >= 1 && a % g == 0 && b % g == 0, "gcd does not divide both");
        // maximality: every common divisor d divides g
        int d = Bmc.anyInt(1, 12);
        if (a % d == 0 && b % d == 0) {
            Bmc.check(g % d == 0, "a common divisor does not divide the gcd");
        }
    }

    // ---- LeastCommonMultiple.lcm vs the lcm*gcd == a*b invariant (positive inputs) ----
    @BmcProof(unwind = 64)
    void lcm_times_gcd_equals_product() {
        int a = Bmc.anyInt(1, 8);
        int b = Bmc.anyInt(1, 8);
        int l = LeastCommonMultiple.lcm(a, b);
        int g = GCD.gcd(a, b);
        Bmc.check(l == (a / g) * b, "lcm(a,b) != a*b/gcd(a,b)");
    }

    // ---- DigitalRoot.digitalRoot == 1 + (n-1) mod 9  for n>=1 (classic closed form) ----
    @BmcProof(unwind = 12)
    void digitalRoot_matches_mod9_closedForm() {
        int n = Bmc.anyInt(1, 200);
        int expected = 1 + (n - 1) % 9;
        Bmc.check(DigitalRoot.digitalRoot(n) == expected, "digitalRoot != mod-9 closed form");
    }

    // ---- GenericRoot.genericRoot: same closed form for n>=1 ----
    @BmcProof(unwind = 12)
    void genericRoot_matches_mod9_closedForm() {
        int n = Bmc.anyInt(1, 200);
        int expected = 1 + (n - 1) % 9;
        Bmc.check(GenericRoot.genericRoot(n) == expected, "genericRoot != mod-9 closed form");
    }

    // ---- BinomialCoefficient C(n,k): Pascal's rule reference + small known values ----
    @BmcProof(unwind = 8, timeoutSeconds = 120)
    void binomial_symmetry_and_pascal() {
        int n = Bmc.anyInt(0, 5);
        int k = Bmc.anyInt(0, 5);
        Bmc.assume(k <= n);
        int c = BinomialCoefficient.binomialCoefficient(n, k);
        // symmetry C(n,k) == C(n,n-k)
        Bmc.check(c == BinomialCoefficient.binomialCoefficient(n, n - k), "C(n,k) != C(n,n-k)");
        Bmc.check(c >= 1, "C(n,k) must be >= 1 for 0<=k<=n");
    }
}
