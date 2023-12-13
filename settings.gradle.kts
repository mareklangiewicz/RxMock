
pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}

plugins {
    id("pl.mareklangiewicz.deps.settings") version "0.2.68" // https://plugins.gradle.org/search?term=mareklangiewicz
    id("com.gradle.enterprise") version "3.16" // https://docs.gradle.com/enterprise/gradle-plugin/
}

rootProject.name = "RxMock"

include(":rxmock")
include(":kotlinsample")
