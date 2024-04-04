package yv.tils.smp.manager.listener

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryOpenEvent
import yv.tils.smp.utils.invSync.InvOpen

class InvOpen : Listener {
    @EventHandler
    fun onEvent(e: InventoryOpenEvent) {
        InvOpen().onInvOpen(e)
    }
}