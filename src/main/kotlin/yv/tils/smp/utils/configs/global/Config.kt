package yv.tils.smp.utils.configs.global

import org.bukkit.configuration.file.YamlConfiguration
import yv.tils.smp.YVtils
import java.io.File

class Config {
    companion object {
        var config: MutableMap<String, Any> = HashMap()
    }

    fun loadConfig() {
        val configFile = YVtils.instance.config

        for (key in configFile.getKeys(true)) {
            config[key] = configFile.get(key) as Any
        }
    }

    fun changeValue(key: String, value: Any) {
        val file = File(YVtils.instance.dataFolder.path, "config.yml")
        val ymlFile: YamlConfiguration = YamlConfiguration.loadConfiguration(file)

        config[key] = value

        ymlFile.set(key, value)
        ymlFile.save(file)
    }
}