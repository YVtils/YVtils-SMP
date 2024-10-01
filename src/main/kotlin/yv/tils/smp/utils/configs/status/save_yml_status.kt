package yv.tils.smp.utils.configs.status

import org.bukkit.configuration.file.YamlConfiguration
import yv.tils.smp.YVtils
import java.io.File

class save_yml_status {
    private var file = File(YVtils.instance.dataFolder.path, "status/" + "save.yml")
    private var ymlFile: YamlConfiguration = YamlConfiguration.loadConfiguration(file)

    fun strings() {
        ymlFile.addDefault("documentation", "https://docs.yvtils.net/smp/status/save.yml")

        ymlFile.options().copyDefaults(true)
        ymlFile.save(file)
    }
}