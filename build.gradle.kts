import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.7.10"
}

group = "one.devsky.boilerplates"
version = "1.1-SNAPSHOT"

val moltenVersion: String by project
val exposedVersion: String by project

repositories {
    mavenCentral()
    maven("https://jitpack.io")
    maven {
        url = uri("https://papermc.io/repo/repository/maven-public/")
    }
}

dependencies {
    // Kotlin Base Dependencies
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.3-native-mt")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.3")

    // Minecraft PaperMC Dependencies
    compileOnly("io.papermc.paper:paper-api:1.18.2-R0.1-SNAPSHOT")

    // Reflection Dependencies for automatic registration of commands and listeners
    implementation("net.oneandone.reflections8:reflections8:0.11.7")

    // Molten Kotlin Framework (https://github.com/TheFruxz/MoltenKT)
    implementation("com.github.TheFruxz.MoltenKT:moltenkt-core:$moltenVersion")
    implementation("com.github.TheFruxz.MoltenKT:moltenkt-unfold:$moltenVersion")

    // Database Dependencies - Kotlin Exposed
    implementation("org.jetbrains.exposed:exposed-core:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-dao:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-jdbc:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-java-time:$exposedVersion")
}

tasks.withType<KotlinCompile>() {
    kotlinOptions.jvmTarget = "17"
    kotlinOptions.freeCompilerArgs += "-opt-in=kotlin.RequiresOptIn"
}
