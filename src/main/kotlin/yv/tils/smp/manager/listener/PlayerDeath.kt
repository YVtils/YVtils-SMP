package yv.tils.smp.manager.listener

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.PlayerDeathEvent
import yv.tils.smp.mods.waypoints.DeathWaypoint

class PlayerDeath: Listener {
    @EventHandler
    fun onPlayerDeath(e: PlayerDeathEvent) {
        DeathWaypoint().onPlayerDeath(e)
    }
}