package yv.tils.smp.manager.listener

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryOpenEvent
import yv.tils.smp.utils.invSync.new.InvSyncOpen

class InvOpen : Listener {
    @EventHandler
    fun onEvent(e: InventoryOpenEvent) {
        InvSyncOpen().onInventoryOpen(e)
    }
}