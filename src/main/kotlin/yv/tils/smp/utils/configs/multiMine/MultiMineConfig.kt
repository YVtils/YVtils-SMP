package yv.tils.smp.utils.configs.multiMine

import org.bukkit.configuration.file.YamlConfiguration
import yv.tils.smp.YVtils
import java.io.File

class MultiMineConfig {
    companion object {
        val config: MutableMap<String, Any> = mutableMapOf()
    }

    fun loadConfig() {
        var file = File(YVtils.instance.dataFolder.path, "multiMine/" + "config.yml")
        var ymlFile: YamlConfiguration = YamlConfiguration.loadConfiguration(file)

        for (key in ymlFile.getKeys(true)) {
            config[key] = ymlFile.get(key) as Any
        }
    }
}