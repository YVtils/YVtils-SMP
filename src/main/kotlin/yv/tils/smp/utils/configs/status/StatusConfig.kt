package yv.tils.smp.utils.configs.status

import org.bukkit.configuration.file.YamlConfiguration
import yv.tils.smp.YVtils
import java.io.File
import java.util.HashMap
import kotlin.collections.set

class StatusConfig {
    companion object {
        var config: MutableMap<String, Any> = HashMap()
        var saves: MutableMap<String, Any> = HashMap()
    }

    fun loadConfig() {
        var file = File(YVtils.instance.dataFolder.path, "status/" + "config.yml")
        var ymlFile: YamlConfiguration = YamlConfiguration.loadConfiguration(file)

        for (key in ymlFile.getKeys(true)) {
            config[key] = ymlFile.get(key) as Any
        }

        file = File(YVtils.instance.dataFolder.path, "status/" + "save.yml")
        ymlFile = YamlConfiguration.loadConfiguration(file)

        for (key in ymlFile.getKeys(true)) {
            saves[key] = ymlFile.get(key) as Any
        }
    }

    fun changeValue(key: String, value: Any) {
        val file = File(YVtils.instance.dataFolder.path, "status/" + "save.yml")
        val ymlFile: YamlConfiguration = YamlConfiguration.loadConfiguration(file)

        saves[key] = value

        ymlFile.set(key, value)
        ymlFile.save(file)
    }

    fun saveMap() {
        var file = File(YVtils.instance.dataFolder.path, "status/" + "save.yml")
        var ymlFile: YamlConfiguration = YamlConfiguration.loadConfiguration(file)

        for ((key, value) in saves) {
            ymlFile.set(key, value)
        }

        ymlFile.save(file)

        file = File(YVtils.instance.dataFolder.path, "status/" + "config.yml")
        ymlFile = YamlConfiguration.loadConfiguration(file)

        for ((key, value) in config) {
            ymlFile.set(key, value)
        }

        ymlFile.save(file)
    }
}