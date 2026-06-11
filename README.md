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


## Run it

```
# CI / published snapshot:
./gradlew test

# Local fast loop against a publishToMavenLocal build (PowerShell needs --% for -P):
./gradlew --% test -PbmcVersion=0.0.1-local
```

CI runs every proof and posts a per-proof Expected/Actual/Counterexample report as a PR comment.
