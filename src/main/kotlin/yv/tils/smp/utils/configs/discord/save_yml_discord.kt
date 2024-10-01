package yv.tils.smp.utils.configs.discord

import org.bukkit.configuration.file.YamlConfiguration
import yv.tils.smp.YVtils
import java.io.File

class save_yml_discord {
    private var file = File(YVtils.instance.dataFolder.path, "discord/" + "save.yml")
    private var ymlFile: YamlConfiguration = YamlConfiguration.loadConfiguration(file)

    fun strings() {
        ymlFile.addDefault("documentation", "https://docs.yvtils.net/smp/discord/save.yml")

        ymlFile.options().copyDefaults(true)
        ymlFile.save(file)
    }
}