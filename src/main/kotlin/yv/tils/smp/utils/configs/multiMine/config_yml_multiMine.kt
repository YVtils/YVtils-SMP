package yv.tils.smp.utils.configs.multiMine

import org.bukkit.configuration.file.YamlConfiguration
import yv.tils.smp.YVtils
import java.io.File

class config_yml_multiMine {
    private var file = File(YVtils.instance.dataFolder.path, "multiMine/" + "config.yml")
    private var ymlFile: YamlConfiguration = YamlConfiguration.loadConfiguration(file)

    fun strings() {
        ymlFile.addDefault("documentation", "https://yvnetwork.de/yvtils-smp/docs")

        ymlFile.addDefault("animationTime", 3)
        ymlFile.addDefault("cooldownTime", 3)
        ymlFile.addDefault("breakLimit", 100)

        // TODO: Add block customizability

        ymlFile.options().copyDefaults(true)
        ymlFile.save(file)
    }
}