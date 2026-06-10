# bmc4j proofs — TheAlgorithms/Java

[bmc4j](https://github.com/bmc4j/bmc4j) bounded-model-checking proofs over a **vendored, attributed**
copy of selected scalar/array algorithms from
[TheAlgorithms/Java](https://github.com/TheAlgorithms/Java) (MIT-licensed; see `LICENSE`).

Each algorithm is analyzed **as it ships** — bmc4j checks the bytecode javac actually produces.
Every `@BmcProof` is a JUnit 5 test asserting a real contract over *symbolic* inputs
(`Bmc.anyInt(...)`), so a passing proof holds for **every** input in the bound, not a sampled few.

## Vendoring

Files under `src/main/java/com/thealgorithms/` are verbatim copies of TheAlgorithms/Java sources.
Every file carries a provenance header: the upstream source path plus the upstream commit it was
copied from. No upstream behavior is modified — that is the whole point.

## Findings

The headline finding is a **real logic bug in `maths/AbsoluteMin.getMinValue(int...)`** — and unlike
an `Int.MAX` overflow corner, it triggers on **tiny everyday inputs**.

`getMinValue` is documented to return the element with the **smallest absolute value** (the upstream
test asserts `getMinValue(3, -10, -2) == -2`). After filtering to the abs-≤ candidates it updates the
running winner with arithmetic `Math.min(value, number)` — the **algebraic** minimum — instead of the
candidate itself. So between two negatives it wrongly keeps the *more negative* (larger-magnitude)
one. The proof asserts the documented contract (`|result|` must be minimal) and **REFUTES** with a
two-element witness: `getMinValue(-3, -2)` returns `-3`, but `|-2| < |-3|`, so it should return `-2`.

The companion sweeps over the sorts, searches and DP algorithms **VERIFY** (the tool proves
correctness too, not only finds bugs); `AliquotSum` is recorded as a documented limitation
(VACUOUS-on-purpose) because its `Math.sqrt` branch is outside the engine's reliable model.

## Live proof reports (open PRs)

`main` is the scaffold (vendored sources + workflow) plus one trivial baseline proof. The proofs
themselves land via **pull requests** — each PR's CI posts a per-proof Expected/Actual/Counterexample
report as a PR comment, which is the shareable artifact. The PRs are intentionally left **open** as
the showcase:

- **Prove AbsoluteMin** — plain proof of the documented "smallest absolute value" contract; it
  REFUTES with the `a = -3, b = -2` counterexample. CI check: **red** (the bug is real). _(PR link below.)_
- **Prove sorts, search & DP** — sorted-permutation, search-correctness and brute-force-differential
  sweeps; all VERIFIED. CI check: **green**. _(PR link below.)_

## Run it

```
# CI / published snapshot:
./gradlew test

# Local fast loop against a publishToMavenLocal build (PowerShell needs --% for -P):
./gradlew --% test -PbmcVersion=0.0.1-local
```

CI runs every proof and posts a per-proof Expected/Actual/Counterexample report as a PR comment.
