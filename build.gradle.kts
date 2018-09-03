val kotlinVersion = "1.2.30"

plugins {
    kotlin("jvm").version("1.2.30")
    id("com.atlassian.performance.tools.gradle-release").version("0.4.0")
}

configurations.all {
    resolutionStrategy {
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
        "org.jetbrains.kotlin:kotlin-stdlib-jre8:$kotlinVersion",
        "com.atlassian.performance.tools:io:[1.0.0,2.0.0)",
        "org.eclipse.jgit:org.eclipse.jgit:4.11.0.201803080745-r"
    ).forEach { implementation(it) }
}

task<Wrapper>("wrapper") {
    gradleVersion = "4.9"
    distributionType = Wrapper.DistributionType.ALL
}
