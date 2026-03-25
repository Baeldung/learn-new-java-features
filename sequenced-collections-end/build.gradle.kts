plugins {
    `java-library`
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(libs.org.junit.jupiter.junit.jupiter)
    testRuntimeOnly(libs.junit.platform.launcher)
}

group = "com.baeldung"
version = "1.0.0"
description = "sequenced-collections-end"
java.sourceCompatibility = JavaVersion.VERSION_21

tasks.withType<JavaCompile>() {
    options.encoding = "UTF-8"
}

tasks.test {
    useJUnitPlatform()
}
