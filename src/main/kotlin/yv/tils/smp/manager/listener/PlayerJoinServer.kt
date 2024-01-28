package yv.tils.smp.manager.listener

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import yv.tils.smp.manager.commands.FlyCMD
import yv.tils.smp.mods.server.connect.PlayerJoin

class PlayerJoinServer: Listener {
    @EventHandler
    fun onEvent(e: PlayerJoinEvent) {
        PlayerJoin().eventReceiver(e)
        FlyCMD().onRejoin(e)
    }
}