plugins {
    java
    eclipse
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

java {
    // Java 17
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

tasks.test {
    useJUnitPlatform()
}
