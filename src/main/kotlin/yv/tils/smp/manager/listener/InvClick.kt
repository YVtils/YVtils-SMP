package yv.tils.smp.manager.listener

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import yv.tils.smp.mods.admin.vanish.VanishGUI

class InvClick: Listener {
    @EventHandler
    fun onEvent(e: InventoryClickEvent) {
        VanishGUI().invInteraction(e)
    }
}