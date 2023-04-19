import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.8.20"
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

group = "one.devsky.boilerplates"
version = "1.1-SNAPSHOT"

val exposedVersion = "0.41.1"
val ktorVersion = "2.2.4"
val eclipseCollectionVersion = "11.1.0"

repositories {
    mavenCentral()
    maven("https://jitpack.io")
    maven("https://repo.flawcra.cc/mirrors")
}

val shadowDependencies = listOf(
    "net.oneandone.reflections8:reflections8:0.11.7",
    "org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4",
    "org.jetbrains.kotlinx:kotlinx-collections-immutable:0.3.5",
    "com.google.code.gson:gson:2.10.1",

    "com.github.TheFruxz:Ascend:2023.1",
    "com.github.TheFruxz:Stacked:2023.1",

    // "io.github.cdimascio:dotenv-kotlin:6.4.1", - .env support

    // "io.sentry:sentry-log4j2:6.17.0", - Sentry error reporting
    // "io.sentry:sentry:6.17.0",

    "org.jetbrains.exposed:exposed-core:$exposedVersion",
    "org.jetbrains.exposed:exposed-dao:$exposedVersion",
    "org.jetbrains.exposed:exposed-jdbc:$exposedVersion",
    "org.jetbrains.exposed:exposed-java-time:$exposedVersion",

    // "io.ktor:ktor-client-core:$ktorVersion", - Ktor HTTP Client
    // "io.ktor:ktor-client-okhttp:$ktorVersion",
    // "io.ktor:ktor-client-websockets:$ktorVersion",
    // "io.ktor:ktor-serialization-kotlinx-json:$ktorVersion",
    // "io.ktor:ktor-client-content-negotiation:$ktorVersion",

    // "com.mrivanplays:conversations-paper:0.0.2-SNAPSHOT", - Conversation API
    // "io.minio:minio:8.5.2",  - Store files on MinIO
    // "redis.clients:jedis:4.3.2", - Redis for caching
    // "com.zaxxer:HikariCP:5.0.1" - Connection Pooling
)

dependencies {
// Minecraft PaperMC Dependencies
    compileOnly("io.papermc.paper:paper-api:1.19.4-R0.1-SNAPSHOT")
    compileOnly("com.mojang:authlib:1.6.25")

    compileOnly("me.clip:placeholderapi:2.11.2")

    // ProtocolLib
    compileOnly("com.comphenix.protocol:ProtocolLib:4.8.0")

    // TAB
    compileOnly("me.neznamy:tab-api:3.2.2")

    // LuckPerms
    compileOnly("net.luckperms:api:5.4")

    // Votifier
    compileOnly("com.vexsoftware:nuvotifier-bukkit:2.8.0-SNAPSHOT")

    implementation(platform("com.intellectualsites.bom:bom-1.18.x:1.25"))
    shadow(platform("com.intellectualsites.bom:bom-1.18.x:1.25"))

    compileOnly("com.fastasyncworldedit:FastAsyncWorldEdit-Core")
    compileOnly("com.fastasyncworldedit:FastAsyncWorldEdit-Bukkit") { isTransitive = false }


    shadowDependencies.forEach { dependency ->
        implementation(dependency)
        shadow(dependency)
    }
}

tasks {

    build {
        dependsOn("shadowJar")
    }

    withType<KotlinCompile> {
        kotlinOptions.jvmTarget = "17"
        kotlinOptions.freeCompilerArgs += "-opt-in=kotlin.RequiresOptIn"
    }

    withType<ProcessResources> {
        filesMatching("plugin.yml") {
            expand(project.properties)
        }
    }

    withType<ShadowJar> {
        mergeServiceFiles()
        configurations = listOf(project.configurations.shadow.get())
    }

}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(17))
}
