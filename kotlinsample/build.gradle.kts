import java.net.URI

plugins {
    application
    kotlin("jvm")
}

application {
    mainClassName = "pl.mareklangiewicz.rxmock.MainKt"
}

dependencies {
    implementation(Deps.kotlinStdlib8)
    implementation(Deps.rxjava)
    implementation(Deps.rxrelay)
    implementation(Deps.abcdk)
    testImplementation(Deps.junit)
    testImplementation(Deps.uspek)
    testImplementation(project(":rxmock"))
//    testImplementation("com.github.langara:RxMock:master-SNAPSHOT")
}

