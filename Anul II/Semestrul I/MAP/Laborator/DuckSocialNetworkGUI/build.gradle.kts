plugins {
    java
    application
    id("org.openjfx.javafxplugin") version "0.0.13"
}

group = "app"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

java {
    sourceCompatibility = JavaVersion.VERSION_23
    targetCompatibility = JavaVersion.VERSION_23
}

application {
    mainClass.set("app.StartApplication")
}

javafx {
    version = "17.0.2"
    modules = listOf("javafx.controls", "javafx.fxml")

}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter:5.10.0")

    implementation("org.controlsfx:controlsfx:11.1.0")
    implementation("org.kordamp.bootstrapfx:bootstrapfx-core:0.4.0")
    implementation("org.postgresql:postgresql:42.7.8")
}

tasks.test {
    useJUnitPlatform()
}

sourceSets {
    main {
        java {
            srcDirs("src/main/java")
        }
        resources {
            srcDirs("src/main/resources")
        }
    }
    test {
        java {
            srcDirs("src/test/java")
        }
        resources {
            srcDirs("src/test/resources")
        }
    }
}

tasks.processResources {
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}