package com.thealgorithms.maths;

import org.bmc4j.Bmc;
import org.bmc4j.BmcProof;

/**
 * Proof over {@link AbsoluteMin#getMinValue(int...)}.
 *
 * <p>CONTRACT (from the method's javadoc and the upstream AbsoluteMinTest, which asserts
 * {@code getMinValue(3, -10, -2) == -2}): the result is the input element with the SMALLEST
 * ABSOLUTE VALUE. The independent oracle here is therefore: the returned value must have absolute
 * value <= the absolute value of EVERY input element.
 *
 * <p>This REFUTES with a tiny witness (e.g. {-3, -2}: getMinValue returns -3, but |-2| < |-3| so it
 * must return -2). Cause: on {@code |number| <= |value|} the code updates with
 * {@code Math.min(value, number)} — the ALGEBRAIC minimum — instead of {@code number}, so between two
 * negatives it wrongly keeps the more-negative (larger-magnitude) one.
 */
class AbsoluteMinProof {

    @BmcProof(unwind = 4)
    void getMinValue_returns_element_with_minimal_absolute_value() {
        // The defect is specifically that, among two NEGATIVE numbers, the code keeps the more-negative
        // (larger-magnitude) one instead of the one closest to zero. Pinning both inputs negative makes
        // the canonical witness the documented {-3, -2}: getMinValue(-3, -2) returns -3, but |-2| < |-3|.
        int a = Bmc.anyInt(-3, -1);
        int b = Bmc.anyInt(-3, -1);
        int r = AbsoluteMin.getMinValue(a, b);
        // r must be one of the inputs and have the minimal absolute value.
        int minAbs = Math.min(Math.abs(a), Math.abs(b));
        Bmc.check(Math.abs(r) == minAbs, "getMinValue did not return the element closest to zero");
    }
}
