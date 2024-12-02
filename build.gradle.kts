import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    application
//    java
    kotlin("jvm") version "2.0.21"
    kotlin("plugin.serialization") version "2.1.0"

    id("com.github.johnrengelman.shadow") version "8.1.1"

    id("io.papermc.paperweight.userdev") version "1.7.4"

    id("xyz.jpenilla.run-paper") version "2.3.1"
}

group = "yv.tils"
version = "1.0.4"

val jdaVersion = "5.2.0"

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://s01.oss.sonatype.org/content/repositories/snapshots/")
}

paperweight.reobfArtifactConfiguration.set(io.papermc.paperweight.userdev.ReobfArtifactConfiguration.MOJANG_PRODUCTION)

dependencies {
    paperweight.paperDevBundle("1.21.3-R0.1-SNAPSHOT")

    implementation("dev.jorel", "commandapi-bukkit-shade-mojang-mapped", "9.6.1")
    implementation("dev.jorel", "commandapi-bukkit-kotlin", "9.6.1")

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
        minecraftVersion("1.21.1")
    }
}

tasks.withType<KotlinCompile> {
    compilerOptions.jvmTarget.set(JvmTarget.JVM_21)
}

application {
    mainClass.set("YVtils")
}

tasks.shadowJar {
    archiveBaseName.set("YVtils-SMP")
    archiveVersion.set(version.toString())
    archiveClassifier.set("")

    archiveFileName.set("YVtils-SMP_v${version}.jar")
}