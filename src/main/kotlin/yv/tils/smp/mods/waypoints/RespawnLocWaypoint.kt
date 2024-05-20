package yv.tils.smp.mods.waypoints

import com.destroystokyo.paper.event.player.PlayerSetSpawnEvent
import yv.tils.smp.utils.configs.waypoints.WaypointConfig

class RespawnLocWaypoint {
    fun onLocationChange(e: PlayerSetSpawnEvent) {
        val player = e.player
        val location = e.location ?: return

        WaypointConfig().removeWaypoint(player.uniqueId.toString(), "respawnPoint")
        WaypointConfig().addWaypoint(player.uniqueId.toString(), "respawnPoint", "private", location.x, location.y, location.z, location.world.name)
    }
}