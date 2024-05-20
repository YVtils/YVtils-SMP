package yv.tils.smp.mods.waypoints

import org.bukkit.event.entity.PlayerDeathEvent
import yv.tils.smp.utils.color.ColorUtils
import yv.tils.smp.utils.configs.waypoints.WaypointConfig

class DeathWaypoint {
    fun onPlayerDeath(e: PlayerDeathEvent) {
        val player = e.player
        val location = player.location

        WaypointConfig().removeWaypoint(player.uniqueId.toString(), "deathPoint")
        WaypointConfig().addWaypoint(player.uniqueId.toString(), "deathPoint", "private", location.x, location.y, location.z, location.world.name)

        player.sendMessage(ColorUtils().convert("<click:run_command:/waypoint navigate deathPoint><hover:show_text:'X: ${location.x} Y: ${location.y} Z: ${location.z} World: ${location.world}'>Click here to navigate to your death point!</click>"))
    }
}