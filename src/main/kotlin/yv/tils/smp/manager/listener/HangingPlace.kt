package yv.tils.smp.manager.listener

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.hanging.HangingPlaceEvent
import yv.tils.smp.mods.fusionCrafting.enchantments.Invisible

class HangingPlace : Listener {
    @EventHandler
    fun onHangingPlace(e: HangingPlaceEvent) {
        Invisible().onHangingPlace(e)
    }
}