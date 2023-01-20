plugins {
    java
    application
}

configure<JavaPluginExtension> {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

tasks.getByName<JavaExec>("run") {
    standardInput = System.`in`
}

application {
    mainClass.set("cz.mm14.src.Game")
}
