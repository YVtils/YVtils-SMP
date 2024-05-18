package yv.tils.smp.utils.configs.waypoints

import org.bukkit.configuration.file.YamlConfiguration
import yv.tils.smp.YVtils
import java.io.File

class config_yml_waypoints {
    private var file = File(YVtils.instance.dataFolder.path, "waypoints/" + "config.yml")
    private var ymlFile: YamlConfiguration = YamlConfiguration.loadConfiguration(file)

    fun strings() {
        ymlFile.addDefault("documentation", "https://yvtils.net/yvtils-smp/docs")

        ymlFile.addDefault("waypointVisibility", "public")
        ymlFile.addDefault("waypointLimit", 5)

        ymlFile.addDefault("announcePublicWaypoints", true)

        ymlFile.addDefault("waypointOnRespawnPoint", true)
        ymlFile.addDefault("waypointOnDeath", true)

        ymlFile.options().copyDefaults(true)
        ymlFile.save(file)
    }
}