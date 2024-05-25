package yv.tils.smp.utils.configs.waypoints

import org.bukkit.configuration.file.YamlConfiguration
import yv.tils.smp.YVtils
import yv.tils.smp.utils.configs.multiMine.MultiMineConfig
import java.io.File

class WaypointConfig {
    companion object {
        val config: MutableMap<String, Any> = mutableMapOf()
        val waypoints: MutableMap<String, MutableList<Waypoint>> = mutableMapOf()

        data class Waypoint(
            val name: String,
            val visibility: String,
            val x: Double,
            val y: Double,
            val z: Double,
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
            if (key == "documentation") continue
            if (key.split(".").size != 2) continue

            var uuid: String
            var name: String

            try {
                uuid = key.split(".")[0]
                name = key.split(".")[1]
            } catch (e: IndexOutOfBoundsException) {
                continue
            }

            val visibility = ymlFile.getString("$key.visibility")!!

            val waypoint = Waypoint(
                name,
                visibility,
                ymlFile.getDouble("$key.x"),
                ymlFile.getDouble("$key.y"),
                ymlFile.getDouble("$key.z"),
                ymlFile.getString("$key.world")!!
            )

            addWaypointToList(visibility, waypoint, uuid)
        }
    }

    fun addWaypoint(uuid: String, name: String, visibility: String, x: Double, y: Double, z: Double, world: String) {
        val waypoint = Waypoint(name, visibility, x, y, z, world)

        addWaypointToList(visibility, waypoint, uuid)

        val file = File(YVtils.instance.dataFolder.path, "waypoints/" + "save.yml")
        val ymlFile: YamlConfiguration = YamlConfiguration.loadConfiguration(file)

        ymlFile.set("$uuid.$name.visibility", visibility)
        ymlFile.set("$uuid.$name.x", x)
        ymlFile.set("$uuid.$name.y", y)
        ymlFile.set("$uuid.$name.z", z)
        ymlFile.set("$uuid.$name.world", world)

        ymlFile.save(file)
    }

    fun removeWaypoint(uuid: String, name: String) {
        val file = File(YVtils.instance.dataFolder.path, "waypoints/" + "save.yml")
        val ymlFile: YamlConfiguration = YamlConfiguration.loadConfiguration(file)

        val visibility = requestVisibility(uuid, name)

        if (visibility.lowercase() == "public") {
            val waypoint = waypoints["PUBLIC"]?.find { it.name == name } ?: return

            waypoints["PUBLIC"]?.remove(waypoint)
        } else if (visibility.lowercase() == "private") {
            val waypoint = waypoints[uuid]?.find { it.name == name } ?: return

            waypoints[uuid]?.remove(waypoint)
        } else if (visibility.lowercase() == "unlisted") {
            val waypoint = waypoints["UNLISTED"]?.find { it.name == name } ?: return

            waypoints["UNLISTED"]?.remove(waypoint)
            waypoints[uuid]?.remove(waypoint)
        }

        ymlFile.set("$uuid.$name", null)

        ymlFile.save(file)
    }

    fun requestVisibility(uuid: String, name: String): String {
        try {
            val waypoint = waypoints["PUBLIC"]?.find { it.name == name } ?: waypoints["UNLISTED"]?.find { it.name == name } ?: return "private"
            return waypoint.visibility
        } catch (_: NullPointerException) {
            val waypoint = waypoints[uuid]?.find { it.name == name } ?: return "private"
            return waypoint.visibility
        }
    }

    fun requestCreator(uuid: String, name: String): Boolean {
        val file = File(YVtils.instance.dataFolder.path, "waypoints/" + "save.yml")
        val ymlFile: YamlConfiguration = YamlConfiguration.loadConfiguration(file)

        ymlFile.getString("$uuid.$name") ?: return false
        return true
    }

    private fun addWaypointToList(visibility: String, waypoint: Waypoint, uuid: String = "") {
        val currentPublicWaypoints = waypoints["PUBLIC"]
        val currentUnlistedWaypoints = waypoints["UNLISTED"]
        val currentPrivateWaypoints = waypoints[uuid]

        if (visibility.lowercase() == "public") {
            if (currentPublicWaypoints != null) {
                currentPublicWaypoints.add(waypoint)
            } else {
                waypoints["PUBLIC"] = mutableListOf(waypoint)
            }
        } else if (visibility.lowercase() == "unlisted") {
            if (currentUnlistedWaypoints != null) {
                currentUnlistedWaypoints.add(waypoint)
            } else {
                waypoints["UNLISTED"] = mutableListOf(waypoint)
            }
            if (currentPrivateWaypoints != null) {
                currentPrivateWaypoints.add(waypoint)
            } else {
                waypoints[uuid] = mutableListOf(waypoint)
            }
        } else {
            if (currentPrivateWaypoints != null) {
                currentPrivateWaypoints.add(waypoint)
            } else {
                waypoints[uuid] = mutableListOf(waypoint)
            }
        }
    }
}