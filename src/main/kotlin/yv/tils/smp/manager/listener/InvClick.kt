package yv.tils.smp.manager.listener

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import yv.tils.smp.mods.admin.invSee.InvSeeListener
import yv.tils.smp.mods.admin.vanish.gui.VHandler
import yv.tils.smp.mods.fusionCrafting.GUIListener
import yv.tils.smp.utils.invSync.InvSync

class InvClick : Listener {
    @EventHandler
    fun onEvent(e: InventoryClickEvent) {
        VHandler().onInventoryClick(e)
        InvSync().onInvChange(e)
        InvSeeListener().onFillerInteract(e)
        GUIListener().onInventoryClick(e)
    }
}