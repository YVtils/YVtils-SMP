package yv.tils.smp.manager.listener

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerToggleFlightEvent
import yv.tils.smp.mods.other.SpawnElytra

class PlayerToggleFlight : Listener {
    @EventHandler
    fun onEvent(e: PlayerToggleFlightEvent) {
        SpawnElytra.getInstance().onDoubleJump(e)
    }
}