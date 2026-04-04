import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    val kotlinMonorepoVersion = "2.3.20"

    kotlin("jvm") version kotlinMonorepoVersion
    kotlin("plugin.serialization") version kotlinMonorepoVersion

    id("com.gradleup.shadow") version "9.4.1"

    id("io.papermc.paperweight.userdev") version "2.0.0-beta.21"

    id("xyz.jpenilla.run-paper") version "3.0.2"
}

val yvtilsVersion = "1.1.11"
val jdaVersion = "5.6.1"
val commandAPIVersion = "11.2.0"

group = "yv.tils"
version = yvtilsVersion

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://s01.oss.sonatype.org/content/repositories/snapshots/")
}

dependencies {
    paperweight.paperDevBundle("1.21.4-R0.1-SNAPSHOT")

    implementation("dev.jorel:commandapi-paper-shade:$commandAPIVersion")
    implementation("dev.jorel:commandapi-kotlin-paper:$commandAPIVersion")

    implementation("net.dv8tion:JDA:$jdaVersion")
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
        minecraftVersion("26.1.1")
    }
}

tasks.withType<KotlinCompile> {
    compilerOptions.jvmTarget.set(JvmTarget.JVM_21)
}

tasks.withType(xyz.jpenilla.runtask.task.AbstractRun::class) {
    javaLauncher.set(project.extensions.getByType<JavaToolchainService>().launcherFor {
        languageVersion.set(JavaLanguageVersion.of(25))
    })
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
