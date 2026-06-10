plugins {
    java
    // bmc4j applies JUnit 5 and wires the proof runtime + the bundled engine, so a `@BmcProof`
    // is just a JUnit 5 test discovered by `./gradlew test`. The plugin analyzes the SHIPPED
    // bytecode of this project's `main` sources — i.e. the vendored TheAlgorithms/Java sources
    // exactly as javac compiles them. Version pinned in settings.gradle.kts (override -PbmcVersion).
    id("org.bmc4j")
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

tasks.withType<Test>().configureEach {
    useJUnitPlatform()
    // Each jbmc invocation is memory-hungry; one fork with a generous heap beats parallel forks.
    maxParallelForks = 1
    maxHeapSize = "4g"
}
