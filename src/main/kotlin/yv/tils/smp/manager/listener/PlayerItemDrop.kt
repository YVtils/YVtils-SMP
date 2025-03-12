package yv.tils.smp.manager.listener

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerDropItemEvent
import yv.tils.smp.utils.invSync.new.InvSyncEvent

class PlayerItemDrop : Listener {
    @EventHandler
    fun onEvent(e: PlayerDropItemEvent) {
        InvSyncEvent().onItemDrop(e)
    }
}