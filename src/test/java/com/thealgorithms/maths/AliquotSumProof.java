package com.thealgorithms.maths;

import org.bmc4j.Bmc;
import org.bmc4j.BmcProof;
import org.bmc4j.Verdict;

/**
 * NOTE — this is a HAND-VERIFIED logic bug that the engine currently CANNOT prove, kept here as a
 * documented limitation rather than a misleading green proof.
 *
 * <p>{@link AliquotSum#getAliquotSum(int)} (the sqrt-based implementation) is WRONG for two ordinary
 * inputs, confirmed by running the real JVM (see the project notes):
 * <ul>
 *   <li>n = 1: returns 1, but the aliquot sum (sum of proper divisors {@code 1<=d<n}) of 1 is 0.</li>
 *   <li>n = 4 (and every perfect square): returns 5, but the correct aliquot sum is 3 — it adds the
 *       square root {@code i + n/i} twice without the de-duplication that the sibling method
 *       {@code AliquotSum.getAliquotValue} and the repo's own {@code PerfectNumber.isPerfectNumber2}
 *       both perform.</li>
 * </ul>
 *
 * <p>The engine cannot witness this because the method branches on {@code Math.sqrt(n)} (a double),
 * and the bundled transcendental model makes the verdict on sqrt-dependent control flow unreliable —
 * it reports {@code getAliquotSum(4) == 3} as VERIFIED, which is FALSE against the real JVM. So we do
 * NOT assert a verdict here; the defect is established by execution, not by the engine. Documented so
 * a future sound {@code Math.sqrt} model turns this into a real refutation.
 */
class AliquotSumProof {

    /**
     * Recorded as VACUOUS-on-purpose only to keep the suite honest: we assume {@code false} so the
     * proof is trivially vacuous and never claims to have verified anything about the broken method.
     * The real evidence is the executed counterexamples in this class's javadoc.
     */
    @BmcProof(expect = Verdict.VACUOUS)
    void getAliquotSum_defect_is_documented_not_engine_proven() {
        int n = Bmc.anyInt(1, 16);
        Bmc.assume(false);
        AliquotSum.getAliquotSum(n);
    }
}
