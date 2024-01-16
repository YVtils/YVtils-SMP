package yv.tils.smp.manager.listener

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerLoginEvent
import yv.tils.smp.mods.server.maintenance.PlayerLogin

class PlayerLoginServer: Listener {
    @EventHandler
    fun onPlayerLoginServer(e: PlayerLoginEvent) {
        PlayerLogin().eventReceiver(e)
    }
}