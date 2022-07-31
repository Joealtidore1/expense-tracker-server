val ktorVersion: String by project
val kotlinVersion: String by project
val logbackVersion: String by project
val ktormVersion: String by project
val koinVersion: String by project

plugins {
    application
    kotlin("jvm") version "1.7.0"
                id("org.jetbrains.kotlin.plugin.serialization") version "1.7.0"
}

group = "com.impact"
version = "0.0.1"
application {
    mainClass.set("com.impact.ApplicationKt")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

repositories {
    mavenCentral()
    maven { url = uri("https://maven.pkg.jetbrains.space/public/p/ktor/eap") }
}

tasks {
    create("stage").dependsOn("installDist")
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    kotlinOptions.jvmTarget = "11"
}

kotlin {
    jvmToolchain {
        (this as JavaToolchainSpec).languageVersion.set(JavaLanguageVersion.of(11))
    }
}

dependencies {
    implementation("io.ktor:ktor-server-content-negotiation-jvm:$ktorVersion")
    implementation("io.ktor:ktor-server-core-jvm:$ktorVersion")
    implementation("io.ktor:ktor-serialization-kotlinx-json-jvm:$ktorVersion")
    implementation("io.ktor:ktor-server-call-logging-jvm:$ktorVersion")
    implementation("io.ktor:ktor-server-host-common-jvm:$ktorVersion")
    implementation("io.ktor:ktor-server-status-pages-jvm:$ktorVersion")
    implementation("io.ktor:ktor-server-auth-jvm:$ktorVersion")
    implementation("io.ktor:ktor-server-auth-jwt-jvm:$ktorVersion")
    implementation("io.ktor:ktor-server-netty-jvm:$ktorVersion")
    implementation("ch.qos.logback:logback-classic:1.2.11")

    //Mysql connection
    implementation("mysql:mysql-connector-java:8.0.29")

    //ktorm dependency
    implementation( "org.ktorm:ktorm-core:$ktormVersion")
    implementation("org.ktorm:ktorm-support-mysql:$ktormVersion")

    //password encryption
    implementation("org.mindrot:jbcrypt:0.4")

    //validation
    implementation("org.valiktor:valiktor-core:0.12.0")

    // Koin for Ktor
    implementation("io.insert-koin:koin-core:3.2.0")
    // Koin Test features
    /*testImplementation("io.insert-koin:koin-test:$koinVersion")*/

    // Koin Core features
    implementation("io.insert-koin:koin-ktor:3.2.0")
    // SLF4J Logger
    implementation("io.insert-koin:koin-logger-slf4j:3.2.0")

    // Koin for JUnit 4
    testImplementation ("io.insert-koin:koin-test-junit4:3.2.0")
    // Koin for JUnit 5
    testImplementation ("io.insert-koin:koin-test-junit5:3.2.0")

    testImplementation("io.ktor:ktor-server-tests-jvm:$ktorVersion")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlinVersion")
}