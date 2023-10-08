plugins {
    id("java")
    id("me.champeau.jmh") version "0.7.1"
    id("com.diffplug.spotless") version "6.22.0"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(platform("com.fasterxml.jackson:jackson-bom:2.15.2"))
    implementation("com.fasterxml.jackson.core:jackson-databind")
    implementation("com.fasterxml.jackson.module:jackson-module-parameter-names")

    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.assertj:assertj-core:3.24.2")
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<JavaCompile>(){
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
