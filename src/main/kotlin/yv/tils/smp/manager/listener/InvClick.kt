package yv.tils.smp.manager.listener

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import yv.tils.smp.mods.admin.invSee.InvSee
import yv.tils.smp.mods.admin.vanish.VanishGUI
import yv.tils.smp.mods.fusionCrafting.GUIListener
import yv.tils.smp.utils.invSync.InvSync

class InvClick : Listener {
    @EventHandler
    fun onEvent(e: InventoryClickEvent) {
        VanishGUI().invInteraction(e)
        InvSync().onInvChange(e)
        InvSee().onFillerInteract(e)
        GUIListener().onInventoryClick(e)
    }
}