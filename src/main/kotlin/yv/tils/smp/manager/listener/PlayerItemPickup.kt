package yv.tils.smp.manager.listener

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerAttemptPickupItemEvent
import yv.tils.smp.utils.invSync.new.InvSyncEvent

class PlayerItemPickup : Listener {
    @EventHandler
    fun onEvent(e: PlayerAttemptPickupItemEvent) {
        InvSyncEvent().onItemPickup(e)
    }
}