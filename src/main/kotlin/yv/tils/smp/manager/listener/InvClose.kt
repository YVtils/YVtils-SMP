package yv.tils.smp.manager.listener

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryCloseEvent
import yv.tils.smp.mods.admin.invSee.InvSeeListener
import yv.tils.smp.mods.admin.vanish.gui.VHandler
import yv.tils.smp.mods.fusionCrafting.GUIListener
import yv.tils.smp.utils.invSync.InvClose

class InvClose : Listener {
    @EventHandler
    fun onEvent(e: InventoryCloseEvent) {
        VHandler().onInventoryClose(e)
        InvClose().onInvClose(e)
        GUIListener().onInventoryClose(e)
        InvSeeListener().onInventoryClose(e)
    }
}