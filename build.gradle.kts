plugins {
    java
    application
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains:annotations:16.0.2")
    testImplementation(platform("org.junit:junit-bom:5.7.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

java {
    sourceSets {
        main {
            java.setSrcDirs(listOf("src/main"))
        }
        test {
            java.setSrcDirs(listOf("src/test"))
        }
    }
}

tasks.compileJava {
    options.release.set(11)
}

tasks.test {
    useJUnitPlatform()
}
