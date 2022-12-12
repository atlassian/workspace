import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.api.tasks.testing.logging.TestLogEvent

val kotlinVersion = "1.3.20"

plugins {
    kotlin("jvm").version("1.3.20")
    id("com.atlassian.performance.tools.gradle-release").version("0.7.1")
}

configurations.all {
    resolutionStrategy {
        activateDependencyLocking()
        failOnVersionConflict()
        eachDependency {
            when (requested.module.toString()) {
                "org.slf4j:slf4j-api" -> useVersion("1.8.0-alpha2")
            }
        }
    }
}

dependencies {
    listOf(
        "org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlinVersion",
        "com.atlassian.performance.tools:io:[1.0.0,2.0.0)",
        "org.eclipse.jgit:org.eclipse.jgit:4.11.0.201803080745-r"
    ).forEach { implementation(it) }

    listOf(
        "junit:junit:4.12",
        "org.assertj:assertj-core:3.23.1"
    ).forEach { testCompile(it) }
}

tasks.getByName("test", Test::class).apply {
    maxHeapSize = "2g" // Work around https://ecosystem.atlassian.net/browse/JPERF-395

    testLogging {
        events = setOf(TestLogEvent.PASSED, TestLogEvent.SKIPPED, TestLogEvent.FAILED)
        exceptionFormat = TestExceptionFormat.FULL
    }
}

tasks.getByName("wrapper", Wrapper::class).apply {
    gradleVersion = "5.2.1"
    distributionType = Wrapper.DistributionType.ALL
}
