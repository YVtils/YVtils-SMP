package yv.tils.smp.utils.configs.waypoints

import org.bukkit.configuration.file.YamlConfiguration
import yv.tils.smp.YVtils
import java.io.File

class config_yml_waypoints {
    private var file = File(YVtils.instance.dataFolder.path, "waypoints/" + "config.yml")
    private var ymlFile: YamlConfiguration = YamlConfiguration.loadConfiguration(file)

    fun strings() {
        ymlFile.addDefault("documentation", "https://docs.yvtils.net/smp/waypoints/config.yml")

        ymlFile.addDefault("waypointVisibility", "public")

        ymlFile.addDefault("announcePublicWaypoints", true)

        ymlFile.addDefault("waypointOnRespawnPoint", true)
        ymlFile.addDefault("waypointOnDeath", true)

        ymlFile.options().copyDefaults(true)
        ymlFile.save(file)
    }
}