
tasks.withType<Wrapper> {
    distributionType = Wrapper.DistributionType.ALL
    gradleVersion = "7.6"
}

allprojects {
    group = "sh.natty"
    version = "1.2-beta"
}
subprojects {
    tasks.withType<JavaCompile> {
        options.encoding = "UTF-8"
    }
}