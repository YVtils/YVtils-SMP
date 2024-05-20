package yv.tils.smp.manager.listener

import com.destroystokyo.paper.event.player.PlayerSetSpawnEvent
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import yv.tils.smp.mods.waypoints.RespawnLocWaypoint

class PlayerSetSpawn: Listener {
    @EventHandler
    fun onSetSpawn(e: PlayerSetSpawnEvent) {
        RespawnLocWaypoint().onLocationChange(e)
    }
}