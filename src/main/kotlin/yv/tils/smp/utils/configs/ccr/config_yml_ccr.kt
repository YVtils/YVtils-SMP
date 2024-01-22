package yv.tils.smp.utils.configs.ccr

import org.bukkit.configuration.file.YamlConfiguration
import yv.tils.smp.YVtils
import java.io.File

class config_yml_ccr {
    private var file = File(YVtils.instance.dataFolder.path, "ccr/" + "config.yml")
    private var ymlFile: YamlConfiguration = YamlConfiguration.loadConfiguration(file)

    fun strings() {
        ymlFile.addDefault("documentation", "https://yvnetwork.de/yvtils-smp/docs")

        ymlFile.options().copyDefaults(true)
        ymlFile.save(file)
    }
}