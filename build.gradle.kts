import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "2.1.10"
    kotlin("plugin.serialization") version "2.1.10"

    id("com.github.johnrengelman.shadow") version "8.1.1"

    id("io.papermc.paperweight.userdev") version "2.0.0-SNAPSHOT"

    id("xyz.jpenilla.run-paper") version "2.3.1"
}

val yvtilsVersion = "1.1.2"
val jdaVersion = "5.2.1"
val commandAPIVersion = "9.7.0"

group = "yv.tils"
version = yvtilsVersion

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://s01.oss.sonatype.org/content/repositories/snapshots/")
}

paperweight.reobfArtifactConfiguration.set(io.papermc.paperweight.userdev.ReobfArtifactConfiguration.MOJANG_PRODUCTION)

dependencies {
    paperweight.paperDevBundle("1.21.4-R0.1-SNAPSHOT")

    implementation("dev.jorel", "commandapi-bukkit-shade-mojang-mapped", commandAPIVersion)
    implementation("dev.jorel", "commandapi-bukkit-kotlin", commandAPIVersion)

    implementation("net.dv8tion", "JDA", jdaVersion)
}

tasks.register("updateVersionFiles") {
    doLast {
        val versionFile = yvtilsVersion // Retrieve the version from your build script

        val filesToUpdate = listOf("src/main/resources/plugin.yml", "src/main/resources/paper-plugin.yml")
        filesToUpdate.forEach { file ->
            val content = file(file).readText()
            val updatedContent = content.replace(Regex("(?<=^version: )\\S+", RegexOption.MULTILINE), versionFile)
            file(file).writeText(updatedContent)
        }
    }
}

tasks {
    build {
        dependsOn(shadowJar)
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
        minecraftVersion("1.21.4")
    }
}

tasks.withType<KotlinCompile> {
    compilerOptions.jvmTarget.set(JvmTarget.JVM_21)
}

tasks.shadowJar {
    archiveBaseName.set("YVtils-SMP")
    archiveVersion.set(version.toString())
    archiveClassifier.set("")
    archiveFileName.set("YVtils-SMP_v${version}.jar")

    manifest {
        attributes["Main-Class"] = "yv.tils.smp.YVtils"
    }
}
