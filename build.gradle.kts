import pl.mareklangiewicz.defaults.*
import pl.mareklangiewicz.deps.*
import pl.mareklangiewicz.utils.*

plugins {
    plug(plugs.NexusPublish)
    plug(plugs.KotlinMulti) apply false
    plug(plugs.KotlinJvm) apply false
}

defaultBuildTemplateForRootProject(
    myLibDetails(
        name = "RxMock",
        description = "Tiny library for mocking RxJava calls.",
        githubUrl = "https://github.com/mareklangiewicz/RxMock",
        version = Ver(0, 0, 25),
        // https://central.sonatype.com/artifact/pl.mareklangiewicz/rxmock/versions
        // https://github.com/mareklangiewicz/RxMock/releases
        settings = LibSettings(
            withJs = false,
            withNativeLinux64 = false,
            compose = null,
            withSonatypeOssPublishing = true,
        ),
    ),
)

// region [[Root Build Template]]

fun Project.defaultBuildTemplateForRootProject(details: LibDetails? = null) {
  details?.let {
    rootExtLibDetails = it
    defaultGroupAndVerAndDescription(it)
  }
}

// endregion [[Root Build Template]]
