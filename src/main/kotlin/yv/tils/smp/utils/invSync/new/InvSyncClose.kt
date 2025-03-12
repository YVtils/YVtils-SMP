package yv.tils.smp.utils.invSync.new

import org.bukkit.event.inventory.InventoryCloseEvent
import yv.tils.smp.utils.invSync.new.handler.HandleSync

class InvSyncClose {
    fun onInventoryClose(e: InventoryCloseEvent) {
        HandleSync().removeSync(e.player)
    }
}