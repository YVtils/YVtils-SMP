package yv.tils.smp.utils.configs.waypoints

import org.bukkit.configuration.file.YamlConfiguration
import yv.tils.smp.YVtils
import yv.tils.smp.utils.configs.multiMine.MultiMineConfig
import yv.tils.smp.utils.configs.multiMine.MultiMineConfig.Companion
import java.io.File

class WaypointConfig {
    companion object {
        val config: MutableMap<String, Any> = mutableMapOf()
        val waypoints: MutableMap<String, MutableList<Waypoint>> = mutableMapOf()

        data class Waypoint(
            val name: String,
            val visibility: String,
            val x: Int,
            val y: Int,
            val z: Int,
            val world: String
        )
    }

    fun loadConfig() {
        val file = File(YVtils.instance.dataFolder.path, "waypoints/" + "config.yml")
        val ymlFile: YamlConfiguration = YamlConfiguration.loadConfiguration(file)

        for (key in ymlFile.getKeys(true)) {
            MultiMineConfig.config[key] = ymlFile.get(key) as Any
        }

        loadWaypoints()
    }

    private fun loadWaypoints() {
        val file = File(YVtils.instance.dataFolder.path, "waypoints/" + "save.yml")
        val ymlFile: YamlConfiguration = YamlConfiguration.loadConfiguration(file)

        for (key in ymlFile.getKeys(true)) {
            val uuid = key.split(".")[0]
            val name = key.split(".")[1]
            val visibility = ymlFile.getString("$key.visibility")!!

            val waypoint = Waypoint(
                name,
                visibility,
                ymlFile.getInt("$key.x"),
                ymlFile.getInt("$key.y"),
                ymlFile.getInt("$key.z"),
                ymlFile.getString("$key.world")!!
            )

            if (visibility == "public") {
                waypoints["PUBLIC"]?.add(waypoint)
            } else {
                waypoints[uuid]?.add(waypoint)
            }
        }
    }

    fun addWaypoint(uuid: String, name: String, visibility: String, x: Int, y: Int, z: Int, world: String) {
        val waypoint = Waypoint(name, visibility, x, y, z, world)
        waypoints["$uuid.$name"]?.add(waypoint)

        val file = File(YVtils.instance.dataFolder.path, "waypoints/" + "save.yml")
        val ymlFile: YamlConfiguration = YamlConfiguration.loadConfiguration(file)

        ymlFile.set("$uuid.$name.visibility", visibility)
        ymlFile.set("$uuid.$name.x", x)
        ymlFile.set("$uuid.$name.y", y)
        ymlFile.set("$uuid.$name.z", z)
        ymlFile.set("$uuid.$name.world", world)
    }
}