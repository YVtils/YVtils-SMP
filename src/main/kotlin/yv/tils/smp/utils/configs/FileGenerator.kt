package yv.tils.smp.utils.configs

import yv.tils.smp.YVtils
import java.io.File

class FileGenerator {

    fun generateFiles() {
        generateFolder("language")
        generateFile("langauge/de")
        generateFile("langauge/en")

        generateFolder("discord")
        generateFile("discord/config")
        generateFile("discord/save")

        generateFolder("Status")
        generateFile("Status/config")
        generateFile("Status/save")

        generateFolder("CCR")
        generateFile("CCR/config")
        generateFile("CCR/save")

        generateFile("config")
    }

    private fun generateFolder(name: String) {
        val folder = File(YVtils.instance.dataFolder.path + "/$name")
        if (!folder.exists()) {
            folder.createNewFile()
        }
    }

    private fun generateFile(name: String) {
        val file = File(YVtils.instance.dataFolder.path + "/$name.yml")
        if (!file.exists()) {
            file.createNewFile()
        }
    }
}