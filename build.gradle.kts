import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.6.20"
}

group = "one.devsky.boilerplates"
version = "1.0-SNAPSHOT"
var moltenVersion = "1.0-PRE-9.4"

repositories {
    mavenCentral()
    maven("https://jitpack.io")
    maven {
        url = uri("https://papermc.io/repo/repository/maven-public/")
    }
}

dependencies {
    // Kotlin Base Dependencies
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.1-native-mt")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.2")

    // Minecraft PaperMC Dependencies
    compileOnly("io.papermc.paper:paper-api:1.18.2-R0.1-SNAPSHOT")

    // Reflection Dependencies for automatic registration of commands and listeners
    implementation("net.oneandone.reflections8:reflections8:0.11.7")

    // Molten Kotlin Framework (https://github.com/TheFruxz/MoltenKT)
    implementation("com.github.TheFruxz.MoltenKT:moltenkt-core:$moltenVersion")
    implementation("com.github.TheFruxz.MoltenKT:moltenkt-unfold:$moltenVersion")

    // Database Dependencies - Kotlin Exposed
    implementation("org.jetbrains.exposed:exposed-core:0.37.3")
    implementation("org.jetbrains.exposed:exposed-dao:0.37.3")
    implementation("org.jetbrains.exposed:exposed-jdbc:0.37.3")
    implementation("org.jetbrains.exposed:exposed-java-time:0.37.3")
}

tasks.withType<KotlinCompile>() {
    kotlinOptions.jvmTarget = "17"
    kotlinOptions.freeCompilerArgs += "-opt-in=kotlin.RequiresOptIn"
}
