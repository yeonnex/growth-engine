plugins {
    java
    id("org.springframework.boot") version "3.4.1"
    id("io.spring.dependency-management") version "1.1.7"
}

group = "me.seoyeon"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

// JAR 아티팩트 설정
tasks.jar {
    archiveBaseName.set("mail-client")  // 아티팩트 이름 설정
    archiveVersion.set("0.0.1-SNAPSHOT")  // 아티팩트 버전 설정
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-mail")
    implementation("com.atlassian.commonmark:commonmark:0.17.0")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.withType<Test> {
    useJUnitPlatform()
}
