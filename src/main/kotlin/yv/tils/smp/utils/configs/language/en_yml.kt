package yv.tils.smp.utils.configs.language

import org.bukkit.configuration.file.YamlConfiguration
import yv.tils.smp.YVtils
import java.io.File

class en_yml {
    private var file = File(YVtils.instance.dataFolder.path + "/Language", "en.yml")
    private var ymlFile: YamlConfiguration = YamlConfiguration.loadConfiguration(file)

    fun strings() {
        ymlFile.addDefault(
            "Language",
            "EN"
        )




        ymlFile.options().copyDefaults(true)
        ymlFile.save(file)
    }
}