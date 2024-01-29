package yv.tils.smp.manager.listener

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerQuitEvent
import yv.tils.smp.mods.server.connect.PlayerQuit

class PlayerQuitServer: Listener {
    @EventHandler
    fun onEvent(e: PlayerQuitEvent) {
        PlayerQuit().eventReceiver(e)
    }
}