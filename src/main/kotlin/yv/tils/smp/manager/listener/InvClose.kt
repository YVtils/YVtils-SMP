package yv.tils.smp.manager.listener

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryCloseEvent
import yv.tils.smp.mods.admin.vanish.VanishGUI

class InvClose: Listener {
    @EventHandler
    fun onEvent(e: InventoryCloseEvent) {
        VanishGUI().guiClose(e)
    }
}