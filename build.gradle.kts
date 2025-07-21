plugins {
    id("java")
    id("me.champeau.jmh") version "0.7.3"
    id("com.diffplug.spotless") version "7.2.0"
    `jvm-test-suite`
    id("com.adarshr.test-logger") version "4.0.0"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(platform("com.fasterxml.jackson:jackson-bom:2.19.2"))
    implementation("com.fasterxml.jackson.core:jackson-databind")
    implementation("com.fasterxml.jackson.module:jackson-module-parameter-names")
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<JavaCompile> {
    options.compilerArgs.add("-parameters")
}

spotless {
    java {
        googleJavaFormat().aosp()
    }
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

jmh {
    warmupIterations = 2
    iterations = 2
    fork = 2
    timeUnit = "ms"
    resultFormat = "json"
    jmhVersion = "1.37"
}

val isIdea = providers.systemProperty("idea.version")
testlogger {
    // idea can't handle ANSI output
    setTheme(if (isIdea.isPresent) "plain" else "mocha")
    showFullStackTraces = false

    showStandardStreams = true
    showPassedStandardStreams = false
    showSkippedStandardStreams = false
    showFailedStandardStreams = true

    showPassed = true
    showSkipped = false
    showFailed = true
}

// https://docs.gradle.org/current/userguide/jvm_test_suite_plugin.html
testing {
    suites {
        withType(JvmTestSuite::class).configureEach {
            useJUnitJupiter("5.10.1")

            dependencies {
                implementation("org.assertj:assertj-core:3.27.3")
            }
        }

        register<JvmTestSuite>("jmhTest") {
            testType = TestSuiteType.INTEGRATION_TEST

            dependencies {
                implementation(sourceSets.jmh.get().runtimeClasspath)
            }
        }
    }
}

tasks.named("check") {
    dependsOn(testing.suites.named("jmhTest"))
}