package yv.tils.smp.utils.configs.global

import org.bukkit.configuration.file.YamlConfiguration
import yv.tils.smp.YVtils
import java.io.File

class config_yml {
    private var file = File(YVtils.instance.dataFolder.path, "config.yml")
    private var ymlFile: YamlConfiguration = YamlConfiguration.loadConfiguration(file)

    fun strings() {
        ymlFile.addDefault("documentation", "https://yvnetwork.de/yvtils-smp/docs")
        ymlFile.addDefault("language", "en")
        ymlFile.addDefault("prefix", " <dark_gray>[<blue>YVtils<gray>-<light_purple>SMP<dark_gray>]<white>")
        ymlFile.addDefault("modules.ccr", true)
        ymlFile.addDefault("modules.status", true)
        ymlFile.addDefault("modules.discord", true)
        ymlFile.addDefault("modules.admin", true)
        ymlFile.addDefault("modules.sit", true)
        ymlFile.addDefault("modules.server", true)
        ymlFile.addDefault("modules.position", true)
        ymlFile.addDefault("modules.oldVersion", true)
        ymlFile.addDefault("modules.multiMine", true)
        ymlFile.addDefault("debug", false)

        ymlFile.options().copyDefaults(true)
        ymlFile.save(file)
    }
}