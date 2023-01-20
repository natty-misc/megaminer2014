import org.gradle.internal.os.OperatingSystem;

plugins {
    java
    application
}

val natives = configurations.create("natives")

repositories {
    mavenCentral()
}

configure<JavaPluginExtension> {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

val extractNatives = tasks.create("extractNatives") {
    dependsOn(tasks.compileJava)

    doLast {
        val platform = natives.resolvedConfiguration.resolvedArtifacts.find {
            "lwjgl-platform" == it.moduleVersion.id.name
        }

        copy {
            from(zipTree(platform!!.file))
            into("natives")
        }
    }
}

tasks.getByName("run") {
    dependsOn(extractNatives)
}

application {
    mainClass.set("cz.mm14.src.Game")
}

dependencies {
    implementation("org.lwjgl.lwjgl", "lwjgl", "2.9.3",)
    implementation("org.slick2d", "slick2d-core", "1.0.2")

    val lwjglNatives = when (OperatingSystem.current()) {
        OperatingSystem.LINUX -> "natives-linux"
        OperatingSystem.WINDOWS -> "natives-windows"
        OperatingSystem.MAC_OS -> "natives-osx"
        else -> throw Error("Unsupported operating system!")
    }

    natives("org.lwjgl.lwjgl", "lwjgl-platform", "2.9.3", classifier = lwjglNatives)
}