package yv.tils.smp.utils.configs.status

import org.bukkit.configuration.file.YamlConfiguration
import yv.tils.smp.YVtils
import java.io.File

class config_yml_status {
    private var file = File(YVtils.instance.dataFolder.path, "status/" + "config.yml")
    private var ymlFile: YamlConfiguration = YamlConfiguration.loadConfiguration(file)

    fun strings() {
        ymlFile.addDefault("documentation", "https://yvnetwork.de/yvtils-smp/docs")

        ymlFile.addDefault("display", "<dark_gray>[<white><status><dark_gray>] |<white> <playerName>")
        ymlFile.addDefault("maxLength", 20)
        ymlFile.addDefault("defaultStatus", defaultStatus())
        ymlFile.addDefault("blacklist", blacklist())

        ymlFile.options().copyDefaults(true)
        ymlFile.save(file)
    }

    private fun defaultStatus(): List<String> {
        val list: MutableList<String> = mutableListOf()

        list.add("<green>Online")
        list.add("<yellow>Away")
        list.add("<red>Busy")

        return list
    }

    private fun blacklist(): List<String> {
        val list: MutableList<String> = mutableListOf()

        return list
    }
}