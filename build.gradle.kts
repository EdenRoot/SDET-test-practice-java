plugins {
    java
    alias(libs.plugins.allure)
}

group = "academy"
version = "1.0"

dependencies {
    implementation(libs.selenium.java)
    implementation(libs.webdrivermanager)
    implementation(libs.allure.junit5)

    testImplementation(libs.junit.jupiter)
    runtimeOnly(libs.aspectjweaver)
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

tasks.test {
    useJUnitPlatform()
}

allure {
    version.set(libs.versions.allure.get())
}