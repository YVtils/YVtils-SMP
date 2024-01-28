package yv.tils.smp.manager.listener

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerGameModeChangeEvent
import yv.tils.smp.manager.commands.FlyCMD

class PlayerGamemodeSwitch: Listener {
    @EventHandler
    fun onEvent(e: PlayerGameModeChangeEvent) {
        FlyCMD().onGamemodeSwitch(e)
    }
}