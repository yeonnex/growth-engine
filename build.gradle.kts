plugins {
    id("java")
}

group = "me.seoyeon"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

extra["springCloudVersion"] = "2024.0.0" // Spring Cloud 버전 정의


dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}