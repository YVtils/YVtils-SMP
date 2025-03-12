package yv.tils.smp.manager.listener

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryDragEvent
import yv.tils.smp.utils.invSync.new.InvSyncEvent

class InvDrag : Listener {
    @EventHandler
    fun onEvent(e: InventoryDragEvent) {
        InvSyncEvent().onInvDrag(e)
    }
}