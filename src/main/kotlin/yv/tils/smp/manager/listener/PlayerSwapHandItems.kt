package yv.tils.smp.manager.listener

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerSwapHandItemsEvent
import yv.tils.smp.mods.other.SpawnElytra

class PlayerSwapHandItems : Listener {
    @EventHandler
    fun onEvent(e: PlayerSwapHandItemsEvent) {
        SpawnElytra.getInstance().onHandSwap(e)
    }
}