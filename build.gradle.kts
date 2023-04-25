import org.springframework.boot.gradle.plugin.SpringBootPlugin

plugins {
    java
    `java-test-fixtures`
    id("org.springframework.boot") version "3.0.6"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(platform(SpringBootPlugin.BOM_COORDINATES))
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("cn.hutool:hutool-all:5.8.16")
    implementation("org.flywaydb:flyway-mysql")

    testImplementation("org.springframework.boot:spring-boot-starter-test")

    runtimeOnly("com.mysql:mysql-connector-j:8.0.33")
    runtimeOnly("org.flywaydb:flyway-core:9.16.3")
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of("17"))
    }
}

tasks {
    test {
        useJUnitPlatform()
    }
}
