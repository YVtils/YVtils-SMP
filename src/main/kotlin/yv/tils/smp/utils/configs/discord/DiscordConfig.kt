package yv.tils.smp.utils.configs.discord

import org.bukkit.configuration.file.YamlConfiguration
import yv.tils.smp.YVtils
import java.io.File

class DiscordConfig {
    companion object {
        var config: MutableMap<String, Any> = HashMap()
        var saves: MutableMap<String, Any> = HashMap()
    }

    fun loadConfig() {
        var file = File(YVtils.instance.dataFolder.path, "discord/" + "config.yml")
        var ymlFile: YamlConfiguration = YamlConfiguration.loadConfiguration(file)

        for (key in ymlFile.getKeys(true)) {
            config[key] = ymlFile.get(key) as Any
        }

        file = File(YVtils.instance.dataFolder.path, "discord/" + "save.yml")
        ymlFile = YamlConfiguration.loadConfiguration(file)

        for (key in ymlFile.getKeys(true)) {
            saves[key] = ymlFile.get(key) as Any
        }
    }

    fun changeValue(key: String, value: Any? = null) {
        val file = File(YVtils.instance.dataFolder.path, "discord/" + "save.yml")
        val ymlFile: YamlConfiguration = YamlConfiguration.loadConfiguration(file)

        if (value == null) {
            saves.remove(key)
        } else {
            saves[key] = value
        }

        ymlFile.set(key, value)
        ymlFile.save(file)
    }
}