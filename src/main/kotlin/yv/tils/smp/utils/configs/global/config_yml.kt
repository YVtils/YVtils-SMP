package yv.tils.smp.utils.configs.global

import org.bukkit.configuration.file.YamlConfiguration
import yv.tils.smp.YVtils
import java.io.File

class config_yml {
    private val file = File(YVtils.instance.dataFolder.path, "config.yml")
    private val ymlFile: YamlConfiguration = YamlConfiguration.loadConfiguration(file)

    fun strings() {
        ymlFile.addDefault("documentation", "https://yvnetwork.de/yvtils-smp/docs")
        ymlFile.addDefault("language", "en")
        ymlFile.addDefault("prefix", "<dark_gray>[<blue>YVtils-SMP<dark_gray>]<white>")
        ymlFile.addDefault("modules.ccr", true)
        ymlFile.addDefault("modules.status", true)
        ymlFile.addDefault("modules.discord", true)
        ymlFile.addDefault("modules.admin", true)
        ymlFile.addDefault("modules.sit", true)
        ymlFile.addDefault("modules.server", true)
        ymlFile.addDefault("modules.position", true)
        ymlFile.addDefault("modules.oldVersion", true)
        ymlFile.addDefault("modules.multiMine", true)

        ymlFile.addDefault("joinMessages", joinMessages())
        ymlFile.addDefault("leaveMessages", leaveMessages())

        ymlFile.addDefault("maintenance", false)
        ymlFile.addDefault("debug", false)

        ymlFile.options().copyDefaults(true)
        ymlFile.save(file)
    }

    private fun joinMessages(): List<String> {
        val list: MutableList<String> = mutableListOf()

        list.add("<green>» <white><player>")

        return list
    }

    private fun leaveMessages(): List<String> {
        val list: MutableList<String> = mutableListOf()

        list.add("<red>« <white><player>")

        return list
    }
}