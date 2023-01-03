import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.util.*

plugins {
    `java-library`
    `maven-publish`
    signing
    id("io.github.gradle-nexus.publish-plugin") version "1.1.0"
    id("org.springframework.boot") version "3.0.1" apply (false)
    id("io.spring.dependency-management") version "1.1.0"
    id("com.palantir.git-version") version "0.15.0"
    kotlin("jvm") version "1.8.0"
}

group = "io.github.raphiz"
description = "Start instantly with readable and reliable feature specs for spring projects"

val gitVersion: groovy.lang.Closure<String> by extra
version = gitVersion()

repositories {
    mavenCentral()
}

dependencyManagement {
    imports {
        mavenBom(org.springframework.boot.gradle.plugin.SpringBootPlugin.BOM_COORDINATES)
    }
}

dependencies {
    api("com.microsoft.playwright:playwright:1.28.1")
    implementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.boot:spring-boot-starter-web")
}


val javaVersion = JavaLanguageVersion.of("17")

java {
    toolchain {
        languageVersion.set(javaVersion)
    }
}

kotlin {
    jvmToolchain {
        languageVersion.set(javaVersion)
    }
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        jvmTarget = javaVersion.toString()
        freeCompilerArgs = listOf("-Xjsr305=strict")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

nexusPublishing {
    repositories {
        sonatype {
            nexusUrl.set(uri("https://s01.oss.sonatype.org/service/local/"))
            snapshotRepositoryUrl.set(uri("https://s01.oss.sonatype.org/content/repositories/snapshots/"))
            username.set(project.findProperty("ossrhUsername") as String?)
            password.set(project.findProperty("ossrhPassword") as String?)
        }
    }
}


java {
    withJavadocJar()
    withSourcesJar()
}

publishing {
    publications {
        register("mavenJava", MavenPublication::class) {
            from(components["java"])
            pom {
                name.set("${project.group}:${project.name}")
                description.set(project.description)
                url.set("https://www.github.com/raphiz/spring-playwright-feature-specs")

                scm {
                    connection.set("scm:git:git://github.com/raphiz/spring-playwright-feature-specs.git")
                    developerConnection.set("scm:git:ssh://github.com:raphiz/spring-playwright-feature-specs.git")
                    url.set("https://www.github.com/raphiz/spring-playwright-feature-specs")
                }

                licenses {
                    license {
                        name.set("MIT License")
                        url.set("https://opensource.org/licenses/mit-license")
                    }
                }

                developers {
                    developer {
                        id.set("raphiz")
                        name.set("Raphael Zimmermann")
                        email.set("oss@raphael.li")
                    }
                }
            }
        }
    }
}

signing {
    if (project.hasProperty("signingKey")) {
        val signingKey: String by project
        val signingPassword: String by project
        useInMemoryPgpKeys(signingKey.base64Decode(), signingPassword)
    } else {
        useGpgCmd()
    }
    sign(publishing.publications)
}

fun String.base64Decode() = String(Base64.getDecoder().decode(this)).trim()