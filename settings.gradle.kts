@file:Suppress("UnstableApiUsage")

import okio.Path.Companion.toOkioPath
import pl.mareklangiewicz.evts.*

gradle.logSomeEventsToFile(rootProject.projectDir.toOkioPath() / "my.gradle.log")


pluginManagement {
    includeBuild("../deps.kt")
}

plugins {
    id("pl.mareklangiewicz.deps.settings")
}

rootProject.name = "RxMock"

include(":rxmock")
include(":kotlinsample")
