package yv.tils.smp.manager.listener

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryDragEvent
import yv.tils.smp.utils.invSync.InvSync

class InvDrag : Listener {
    @EventHandler
    fun onEvent(e: InventoryDragEvent) {
        InvSync().onInvDrag(e)
    }
}