import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    application
//    java
    kotlin("jvm") version "1.9.24"
    kotlin("plugin.serialization") version "1.9.24"

    id("com.github.johnrengelman.shadow") version "8.1.1"

    id("io.papermc.paperweight.userdev") version "1.7.1"

    id("xyz.jpenilla.run-paper") version "2.3.0"
}

group = "yv.tils"
version = "1.0.0"

val jdaVersion = "5.0.0-beta.24"

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://s01.oss.sonatype.org/content/repositories/snapshots/")
}

paperweight.reobfArtifactConfiguration.set(io.papermc.paperweight.userdev.ReobfArtifactConfiguration.MOJANG_PRODUCTION)

dependencies {
    paperweight.paperDevBundle("1.20.6-R0.1-SNAPSHOT")

    implementation("dev.jorel", "commandapi-bukkit-shade-mojang-mapped", "9.5.0-SNAPSHOT")
    implementation("dev.jorel", "commandapi-bukkit-kotlin", "9.5.0-SNAPSHOT")

    implementation("net.dv8tion:JDA:$jdaVersion")
}

tasks {
    build {
        dependsOn(shadowJar)
    }

    assemble {
        //dependsOn(reobfJar)
    }

    compileJava {
        options.encoding = Charsets.UTF_8.name()
        options.release.set(21)
    }

    javadoc {
        options.encoding = Charsets.UTF_8.name()
    }

    processResources {
        filteringCharset = Charsets.UTF_8.name()
    }

    runServer {
        minecraftVersion("1.20.6")
    }
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "21"
}

application {
    mainClass.set("YVtils")
}