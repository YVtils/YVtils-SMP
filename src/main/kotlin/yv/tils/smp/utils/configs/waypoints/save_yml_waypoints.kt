package yv.tils.smp.utils.configs.waypoints

import org.bukkit.configuration.file.YamlConfiguration
import yv.tils.smp.YVtils
import java.io.File

class save_yml_waypoints {
    // File structure:
    /*
    UUID of creator.name of waypoint
       - visibility: public/private
       - x: 0
       - y: 0
       - z: 0
       - world: world
     */

    private var file = File(YVtils.instance.dataFolder.path, "waypoints/" + "save.yml")
    private var ymlFile: YamlConfiguration = YamlConfiguration.loadConfiguration(file)

    fun strings() {
        ymlFile.addDefault("documentation", "https://yvtils.net/yvtils-smp/docs")

        ymlFile.options().copyDefaults(true)
        ymlFile.save(file)
    }
}