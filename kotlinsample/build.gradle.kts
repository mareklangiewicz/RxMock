
// region [[Basic JVM App Build Imports and Plugs]]

import org.jetbrains.kotlin.gradle.dsl.*
import org.jetbrains.kotlin.gradle.plugin.*
import com.vanniktech.maven.publish.*
import pl.mareklangiewicz.defaults.*
import pl.mareklangiewicz.deps.*
import pl.mareklangiewicz.utils.*
import pl.mareklangiewicz.templatefun.*

plugins {
  plugAll(
    plugs.TemplateFunNoVer, // version comes from the root: a versioned request here fails in composite builds
    plugs.KotlinJvm,
    plugs.JvmApp,
    plugs.VannikPublish,
  )
}

// endregion [[Basic JVM App Build Imports and Plugs]]

defaultBuildTemplateForBasicJvmApp()

dependencies {
    implementation(Io.ReactiveX.RxJava3.rxjava)
    implementation(Com.JakeWharton.RxRelay3.rxrelay)
    implementation(Langiewicz.abcdk)
    testImplementation(project(":rxmock"))
}
