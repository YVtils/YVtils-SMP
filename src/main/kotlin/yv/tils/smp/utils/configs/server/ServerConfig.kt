package yv.tils.smp.utils.configs.server

import org.bukkit.configuration.file.YamlConfiguration
import yv.tils.smp.YVtils
import java.io.File

class ServerConfig {
    companion object {
        val config: MutableMap<String, Any> = mutableMapOf()
    }

    fun loadConfig() {
        val file = File(YVtils.instance.dataFolder.path, "server/" + "config.yml")
        val ymlFile: YamlConfiguration = YamlConfiguration.loadConfiguration(file)

        for (key in ymlFile.getKeys(true)) {
            config[key] = ymlFile.get(key) as Any
        }
    }
}