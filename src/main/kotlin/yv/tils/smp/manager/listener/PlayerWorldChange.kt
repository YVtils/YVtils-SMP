package yv.tils.smp.manager.listener

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerChangedWorldEvent
import yv.tils.smp.manager.commands.FlyCMD

class PlayerWorldChange: Listener {
    @EventHandler
    fun onEvent(e: PlayerChangedWorldEvent) {
        FlyCMD().onWorldChange(e)
    }
}