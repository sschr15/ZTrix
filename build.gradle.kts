plugins {
    java
}

repositories {
    mavenCentral()
}

dependencies {
    // Annotations
    implementation("org.jetbrains:annotations:23.0.0")

    // JUnit for testing
    testImplementation("org.junit.jupiter:junit-jupiter:5.7.1")
}

tasks.test {
    useJUnitPlatform()
}