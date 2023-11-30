
pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}

plugins {
    id("pl.mareklangiewicz.deps.settings") version "0.2.61" // https://plugins.gradle.org/search?term=mareklangiewicz
    id("com.gradle.enterprise") version "3.15.1" // https://docs.gradle.com/enterprise/gradle-plugin/
}

rootProject.name = "RxMock"

include(":rxmock")
include(":kotlinsample")
